package com.tbg.mapbox_plugin;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

class MarkersController {

    private final Map<String, MarkerController> markerIdToController;
    private final Map<String, String> mapboxMapsMarkerIdToDartMarkerId;
    private final MethodChannel methodChannel;
    private MapboxMap mapboxMap;

    MarkersController(MethodChannel methodChannel) {
        this.markerIdToController = new HashMap<>();
        this.mapboxMapsMarkerIdToDartMarkerId = new HashMap<>();
        this.methodChannel = methodChannel;
    }

    @SuppressWarnings("unchecked")
    private static String getMarkerId(Object marker) {
        Map<String, Object> markerMap = (Map<String, Object>) marker;
        return (String) markerMap.get("markerId");
    }

    void setmapboxMap(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
    }

    void addMarkers(List<Object> markersToAdd) {
        if (markersToAdd != null) {
            for (Object markerToAdd : markersToAdd) {
                addMarker(markerToAdd);
            }
        }
    }

    void changeMarkers(List<Object> markersToChange) {
        if (markersToChange != null) {
            for (Object markerToChange : markersToChange) {
                changeMarker(markerToChange);
            }
        }
    }

    void removeMarkers(List<Object> markerIdsToRemove) {
        if (markerIdsToRemove == null) {
            return;
        }
        for (Object rawMarkerId : markerIdsToRemove) {
            if (rawMarkerId == null) {
                continue;
            }
            String markerId = (String) rawMarkerId;
            final MarkerController markerController = markerIdToController.remove(markerId);
            if (markerController != null) {
                markerController.remove();
                mapboxMapsMarkerIdToDartMarkerId.remove(markerController.getmapboxMapsMarkerId());
            }
        }
    }

    boolean onMarkerTap(String mapboxMarkerId) {
        String markerId = mapboxMapsMarkerIdToDartMarkerId.get(mapboxMarkerId);
        if (markerId == null) {
            return false;
        }
        methodChannel.invokeMethod("marker#onTap", Convert.markerIdToJson(markerId));
        MarkerController markerController = markerIdToController.get(markerId);
        if (markerController != null) {
            return markerController.consumeTapEvents();
        }
        return false;
    }

    void onMarkerDragEnd(String googleMarkerId, LatLng latLng) {
        String markerId = mapboxMapsMarkerIdToDartMarkerId.get(googleMarkerId);
        if (markerId == null) {
            return;
        }
        final Map<String, Object> data = new HashMap<>();
        data.put("markerId", markerId);
        data.put("position", Convert.latLngToJson(latLng));
        methodChannel.invokeMethod("marker#onDragEnd", data);
    }

    void onInfoWindowTap(String googleMarkerId) {
        String markerId = mapboxMapsMarkerIdToDartMarkerId.get(googleMarkerId);
        if (markerId == null) {
            return;
        }
        methodChannel.invokeMethod("infoWindow#onTap", Convert.markerIdToJson(markerId));
    }

    private void addMarker(Object marker) {
        if (marker == null) {
            return;
        }
        MarkerBuilder markerBuilder = new MarkerBuilder();
        String markerId = Convert.interpretMarkerOptions(marker, markerBuilder);
        MarkerOptions options = markerBuilder.build();
        addMarker(markerId, options, markerBuilder.consumeTapEvents());
    }

    private void addMarker(String markerId, MarkerOptions markerOptions, boolean consumeTapEvents) {
        final Marker marker = mapboxMap.addMarker(markerOptions);
        MarkerController controller = new MarkerController(marker, consumeTapEvents);
        markerIdToController.put(markerId, controller);
        Long id = marker.getId();
        String m = id.toString();
        mapboxMapsMarkerIdToDartMarkerId.put(m, markerId);
    }

    private void changeMarker(Object marker) {
        if (marker == null) {
            return;
        }
        String markerId = getMarkerId(marker);
        MarkerController markerController = markerIdToController.get(markerId);
        if (markerController != null) {
            Convert.interpretMarkerOptions(marker, markerController);
        }
    }
}

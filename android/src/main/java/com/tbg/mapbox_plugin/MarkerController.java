package com.tbg.mapbox_plugin;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;

class MarkerController implements MarkerOptionsSink {

    private final Marker marker;
    private final String mapboxMapsMarkerId;
    private boolean consumeTapEvents;

    MarkerController(Marker marker, boolean consumeTapEvents) {
        this.marker = marker;
        this.consumeTapEvents = consumeTapEvents;
        Long id = marker.getId();
        String m = id.toString();
        this.mapboxMapsMarkerId = m;
    }

    void remove() {
        marker.remove();
    }


    @Override
    public void setConsumeTapEvents(boolean consumeTapEvents) {
        this.consumeTapEvents = consumeTapEvents;
    }


    @Override
    public void setIcon(Icon icon) {
        marker.setIcon(icon);
    }


    @Override
    public void setInfoWindowText(String title, String snippet) {
        marker.setTitle(title);
        marker.setSnippet(snippet);
    }

    @Override
    public void setPosition(LatLng position) {
        marker.setPosition(position);
    }


    String getmapboxMapsMarkerId() {
        return mapboxMapsMarkerId;
    }

    boolean consumeTapEvents() {
        return consumeTapEvents;
    }
}

package com.tbg.mapbox_plugin;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;

class MarkerBuilder implements MarkerOptionsSink {
    private final MarkerOptions markerOptions;
    private boolean consumeTapEvents;

    MarkerBuilder() {
        this.markerOptions = new MarkerOptions();
    }

    MarkerOptions build() {
        return markerOptions;
    }

    boolean consumeTapEvents() {
        return consumeTapEvents;
    }


    @Override
    public void setConsumeTapEvents(boolean consumeTapEvents) {
        this.consumeTapEvents = consumeTapEvents;
    }

    @Override
    public void setIcon(Icon icon) {
        markerOptions.icon(icon);
    }

    @Override
    public void setInfoWindowText(String title, String snippet) {
        markerOptions.title(title);
        markerOptions.snippet(snippet);
    }

    @Override
    public void setPosition(LatLng position) {
        markerOptions.position(position);
    }
}

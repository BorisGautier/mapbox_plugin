package com.tbg.mapbox_plugin;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.geometry.LatLng;

interface MarkerOptionsSink {


    void setConsumeTapEvents(boolean consumeTapEvents);

    void setIcon(Icon icon);

    void setInfoWindowText(String title, String snippet);

    void setPosition(LatLng position);
}

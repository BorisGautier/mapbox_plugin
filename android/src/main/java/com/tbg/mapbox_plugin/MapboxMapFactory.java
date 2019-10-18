package com.tbg.mapbox_plugin;

import android.content.Context;

import com.mapbox.mapboxsdk.camera.CameraPosition;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

import static io.flutter.plugin.common.PluginRegistry.Registrar;

public class MapboxMapFactory extends PlatformViewFactory {

    private final AtomicInteger mActivityState;
    private final Registrar mPluginRegistrar;

    public MapboxMapFactory(AtomicInteger state, Registrar registrar) {
        super(StandardMessageCodec.INSTANCE);
        mActivityState = state;
        mPluginRegistrar = registrar;
    }

    @Override
    public PlatformView create(Context context, int id, Object args) {
        Map<String, Object> params = (Map<String, Object>) args;
        final MapboxMapBuilder builder = new MapboxMapBuilder();

        Convert.interpretMapboxMapOptions(params.get("options"), builder);
        if (params.containsKey("initialCameraPosition")) {
            CameraPosition position = Convert.toCameraPosition(params.get("initialCameraPosition"));
            builder.setInitialCameraPosition(position);
        }
        if (params.containsKey("markersToAdd")) {
            builder.setInitialMarkers(params.get("markersToAdd"));
        }
        return builder.build(id, context, mActivityState, mPluginRegistrar);
    }
}

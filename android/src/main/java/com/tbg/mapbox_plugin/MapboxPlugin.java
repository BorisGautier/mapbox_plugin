package com.tbg.mapbox_plugin;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import java.util.concurrent.atomic.AtomicInteger;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** MapboxPlugin */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MapboxPlugin implements Application.ActivityLifecycleCallbacks {
  static final int CREATED = 1;
  static final int STARTED = 2;
  static final int RESUMED = 3;
  static final int PAUSED = 4;
  static final int STOPPED = 5;
  static final int DESTROYED = 6;
  private final AtomicInteger state = new AtomicInteger(0);
  private final int registrarActivityHashCode;

  public static void registerWith(Registrar registrar) {
    final MapboxPlugin plugin = new MapboxPlugin(registrar);
    registrar.activity().getApplication().registerActivityLifecycleCallbacks(plugin);
    registrar
            .platformViewRegistry()
            .registerViewFactory(
                    "plugins.flutter.io/mapbox_plugin", new MapboxMapFactory(plugin.state, registrar));

    MethodChannel methodChannel =
            new MethodChannel(registrar.messenger(), "plugins.flutter.io/mapbox_plugin");
    methodChannel.setMethodCallHandler(new GlobalMethodHandler(registrar));
  }

  @Override
  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    if (activity.hashCode() != registrarActivityHashCode) {
      return;
    }
    state.set(CREATED);
  }

  @Override
  public void onActivityStarted(Activity activity) {
    if (activity.hashCode() != registrarActivityHashCode) {
      return;
    }
    state.set(STARTED);
  }

  @Override
  public void onActivityResumed(Activity activity) {
    if (activity.hashCode() != registrarActivityHashCode) {
      return;
    }
    state.set(RESUMED);
  }

  @Override
  public void onActivityPaused(Activity activity) {
    if (activity.hashCode() != registrarActivityHashCode) {
      return;
    }
    state.set(PAUSED);
  }

  @Override
  public void onActivityStopped(Activity activity) {
    if (activity.hashCode() != registrarActivityHashCode) {
      return;
    }
    state.set(STOPPED);
  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
  }

  @Override
  public void onActivityDestroyed(Activity activity) {
    if (activity.hashCode() != registrarActivityHashCode) {
      return;
    }
    state.set(DESTROYED);
  }

  private MapboxPlugin(Registrar registrar) {
    this.registrarActivityHashCode = registrar.activity().hashCode();
  }
}

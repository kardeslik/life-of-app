package com.kardeslik.lifeofapp.sample;

import android.app.Application;
import android.util.Log;

import com.kardeslik.lifeofapp.AppLifecycleManager;

public class App extends Application implements AppLifecycleManager.Listener {

  private static final String TAG = "Life.of.App";

  private Long foregroundTime;
  private Long backgroundTime;

  @Override
  public void onCreate() {
    super.onCreate();

    AppLifecycleManager appLifecycleManager = new AppLifecycleManager(this);
    registerActivityLifecycleCallbacks(appLifecycleManager);
  }

  @Override
  public void onAppLunched() {
    Log.d(TAG, "Application launched!");
  }

  @Override
  public void onAppCameForeground() {
    Log.d(TAG, "Application came to foreground!");
    foregroundTime = System.currentTimeMillis();

    if (backgroundTime != null) {
      Log.d(TAG, "Time spent on background is " + (foregroundTime - backgroundTime) + "ms");
    }
  }

  @Override
  public void onAppWentBackground() {
    Log.d(TAG, "Application went to background!");
    backgroundTime = System.currentTimeMillis();

    Log.d(TAG, "Time spent in app is " + (backgroundTime - foregroundTime) + "ms");
  }

  @Override
  public void onAppClosed() {
    Log.d(TAG, "Application closed!");

    foregroundTime = null;
    backgroundTime = null;
  }
}

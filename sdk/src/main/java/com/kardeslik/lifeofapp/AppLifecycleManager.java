package com.kardeslik.lifeofapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class AppLifecycleManager implements Application.ActivityLifecycleCallbacks {

  public interface Listener {
    void onAppLaunched();

    void onAppCameForeground();

    void onAppWentBackground();

    void onAppClosed();
  }

  private static final int COUNTER_LIMIT = 0;

  private Listener listener;

  private boolean isChangingConfigurations = false;
  private int counter = COUNTER_LIMIT;

  public AppLifecycleManager(Listener listener) {
    this.listener = listener;
  }

  @Override
  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    if (counter == COUNTER_LIMIT && !isChangingConfigurations && savedInstanceState == null) {
      listener.onAppLaunched();
    }
  }

  @Override
  public void onActivityStarted(Activity activity) {
    if (counter == COUNTER_LIMIT && !isChangingConfigurations) {
      listener.onAppCameForeground();
    }

    counter++;
  }

  @Override
  public void onActivityResumed(Activity activity) {
    isChangingConfigurations = false;
  }

  @Override
  public void onActivityPaused(Activity activity) {
    isChangingConfigurations = activity.isChangingConfigurations();
  }

  @Override
  public void onActivityStopped(Activity activity) {
    counter--;

    if (counter == COUNTER_LIMIT && !isChangingConfigurations) {
      listener.onAppWentBackground();
    }
  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

  }

  @Override
  public void onActivityDestroyed(Activity activity) {
    if (counter == COUNTER_LIMIT && activity.isFinishing()) {
      listener.onAppClosed();
    }
  }
}

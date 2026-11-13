package org.liberty.android.classiccalc.ui;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Application_12c extends Application {

    public static String filesDirPath;
    private int startedActivityCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        filesDirPath = getFilesDir().toString();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Application_12c.this.startedActivityCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Application_12c.this.startedActivityCount =
                        Math.max(0, Application_12c.this.startedActivityCount - 1);
                if (Application_12c.this.startedActivityCount == 0
                        && !activity.isChangingConfigurations()) {
                    SharedPreferences preferences =
                            getSharedPreferences(SettingsActivity.PREFS_NAME, 0);
                    CalculatorMode savedMode =
                            CalculatorMode.fromIndex(preferences.getInt("CalcMode", 0));
                    SettingsActivity.updateLauncherIcon(Application_12c.this, savedMode);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }
}

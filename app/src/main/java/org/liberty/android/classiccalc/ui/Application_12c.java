package org.liberty.android.classiccalc.ui;

import android.app.Application;

public class Application_12c extends Application {

    public static String filesDirPath;

    @Override
    public void onCreate() {
        super.onCreate();
        filesDirPath = getFilesDir().toString();
    }
}

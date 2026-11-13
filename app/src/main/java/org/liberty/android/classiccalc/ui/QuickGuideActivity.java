package org.liberty.android.classiccalc.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.ImageView;
import org.liberty.android.classiccalc.R;

public class QuickGuideActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.back_layout);

        View rootView = findViewById(R.id.quickGuideRootLayout);
        if (rootView != null) {
            rootView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    if (Build.VERSION.SDK_INT >= 30) {
                        Insets bars = insets.getInsets(
                                WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                        v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
                    } else {
                        v.setPadding(
                                insets.getSystemWindowInsetLeft(),
                                insets.getSystemWindowInsetTop(),
                                insets.getSystemWindowInsetRight(),
                                insets.getSystemWindowInsetBottom());
                    }
                    return insets;
                }
            });
        }

        SharedPreferences preferences = getSharedPreferences(SettingsActivity.PREFS_NAME, 0);
        CalculatorMode mode = CalculatorMode.fromIndex(preferences.getInt("CalcMode", 0));

        int orientationMode = SettingsActivity.readOrientationMode(preferences);
        if (orientationMode == 2) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        } else if (orientationMode == 1) {
            int lockViewSetting = SettingsActivity.readLockViewSetting(preferences);
            if (lockViewSetting != ActivityInfo.SCREEN_ORIENTATION_USER
                    && lockViewSetting != ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR) {
                setRequestedOrientation(lockViewSetting);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        }

        if (preferences.getBoolean("AlwaysOn", false)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        View.OnClickListener closeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };

        View backButton = findViewById(R.id.quickGuideBackButton);
        if (backButton != null) {
            backButton.setOnClickListener(closeClickListener);
        }

        ImageView backCoverImage = (ImageView) findViewById(R.id.backCoverImage);
        if (backCoverImage != null) {
            backCoverImage.setImageResource(mode.backCoverDrawableId);
            backCoverImage.setOnClickListener(closeClickListener);
        }
    }
}

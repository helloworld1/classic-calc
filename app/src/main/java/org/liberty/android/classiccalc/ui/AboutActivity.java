package org.liberty.android.classiccalc.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Insets;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import org.liberty.android.classiccalc.R;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);

        View rootView = findViewById(R.id.aboutRootLayout);
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

        View backButton = findViewById(R.id.aboutBackButton);
        if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        String appName = getString(R.string.app_name);
        TextView headerTitle = (TextView) findViewById(R.id.aboutHeaderTitleText);
        if (headerTitle != null) {
            headerTitle.setText(getString(R.string.About_App, appName));
        }

        ImageView appIcon = (ImageView) findViewById(R.id.aboutAppIcon);
        if (appIcon != null) {
            int iconResId = R.drawable.icon;
            if (mode == CalculatorMode.CC_12CP) {
                iconResId = R.drawable.icon_12cp;
            } else if (mode == CalculatorMode.CC_15C) {
                iconResId = R.drawable.icon_15c;
            }
            appIcon.setImageResource(iconResId);
        }

        TextView versionNameValue = (TextView) findViewById(R.id.aboutVersionNameValue);
        TextView versionCodeValue = (TextView) findViewById(R.id.aboutVersionCodeValue);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            if (versionNameValue != null) {
                versionNameValue.setText(packageInfo.versionName);
            }
            if (versionCodeValue != null) {
                long code = Build.VERSION.SDK_INT >= 28
                        ? packageInfo.getLongVersionCode()
                        : packageInfo.versionCode;
                versionCodeValue.setText(String.valueOf(code));
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }

        final String githubUrl = getString(R.string.github_url);
        TextView githubUrlText = (TextView) findViewById(R.id.aboutGithubUrlText);
        if (githubUrlText != null) {
            githubUrlText.setPaintFlags(githubUrlText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        View githubRow = findViewById(R.id.aboutGithubRow);
        if (githubRow != null) {
            githubRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl)));
                    } catch (ActivityNotFoundException unused) {
                    }
                }
            });
        }
    }
}

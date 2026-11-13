package org.liberty.android.classiccalc.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import org.liberty.android.classiccalc.R;

public class SettingsActivity extends Activity
        implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    static final String PREFS_NAME = "CalcSettings";
    static final String PREF_PENDING_RESET = "PendingReset";

    private CalculatorMode currentMode = CalculatorMode.CC_12C;
    private int vibrationLevel = 1;
    private Vibrator vibrator;

    private Spinner modeSpinner;
    private Spinner vibrationSpinner;
    private Spinner orientationSpinner;
    private Switch alwaysOnSwitch;
    private Switch soundSwitch;
    private Switch decimalSwapSwitch;

    private Dialog backCoverDialog;
    private AlertDialog resetConfirmDialog;

    static String getDecimalSwapPrefKey(CalculatorMode mode) {
        if (mode == CalculatorMode.CC_12C) {
            return "CalcSwap";
        }
        return "CalcSwap_" + mode.name();
    }

    static String getPendingDecimalSwapPrefKey(CalculatorMode mode) {
        return "PendingDecimalSwap_" + mode.name();
    }

    static int readVibrationLevel(SharedPreferences preferences) {
        if (preferences.contains("VibrationLevel")) {
            return preferences.getInt("VibrationLevel", 1);
        }
        if (preferences.contains("Vibration")) {
            return preferences.getBoolean("Vibration", false) ? 2 : 0;
        }
        return 1;
    }

    static int readOrientationMode(SharedPreferences preferences) {
        if (preferences.contains("OrientationMode")) {
            return preferences.getInt("OrientationMode", 2);
        }
        if (preferences.contains("LockView")) {
            int lockView = preferences.getInt("LockView", ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            if (lockView == ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR) {
                return 2;
            }
            if (lockView != ActivityInfo.SCREEN_ORIENTATION_USER
                    && lockView != ActivityInfo.SCREEN_ORIENTATION_SENSOR) {
                return 1;
            }
            return 0;
        }
        return 2;
    }

    static int readLockViewSetting(SharedPreferences preferences) {
        int lockView = preferences.getInt("LockView", ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        if (lockView == ActivityInfo.SCREEN_ORIENTATION_SENSOR) {
            return ActivityInfo.SCREEN_ORIENTATION_USER;
        }
        return lockView;
    }

    static int getCurrentScreenOrientationLock(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        int isLandscapeFlag = activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 1 : 0;
        int rotation = defaultDisplay.getRotation();
        if (rotation == Surface.ROTATION_0) {
            return 1 ^ isLandscapeFlag;
        }
        if (rotation == Surface.ROTATION_90) {
            return isLandscapeFlag == 0
                    ? ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                    : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        }
        if (rotation == Surface.ROTATION_180) {
            return isLandscapeFlag != 0
                    ? ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                    : ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
        }
        if (rotation == Surface.ROTATION_270) {
            return isLandscapeFlag != 0
                    ? ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                    : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        }
        return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    static void triggerVibrationForLevel(Vibrator deviceVibrator, int level) {
        if (level <= 0 || deviceVibrator == null) {
            return;
        }
        long durationMs;
        int amplitude;
        if (level == 1) {
            durationMs = 15L;
            amplitude = 64;
        } else if (level == 2) {
            durationMs = 30L;
            amplitude = 150;
        } else {
            durationMs = 60L;
            amplitude = 255;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            int effectAmplitude = deviceVibrator.hasAmplitudeControl()
                    ? amplitude
                    : VibrationEffect.DEFAULT_AMPLITUDE;
            deviceVibrator.vibrate(VibrationEffect.createOneShot(durationMs, effectAmplitude));
        } else {
            deviceVibrator.vibrate(durationMs);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_dlg);

        View rootView = findViewById(R.id.settingsRootLayout);
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

        this.vibrator = (Vibrator) getSystemService("vibrator");
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
        this.currentMode = CalculatorMode.fromIndex(preferences.getInt("CalcMode", 0));
        this.vibrationLevel = readVibrationLevel(preferences);

        applySavedOrientation(preferences);
        applyAlwaysOnSetting(preferences.getBoolean("AlwaysOn", false));

        View backButton = findViewById(R.id.settingsBackButton);
        if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        TextView versionText = (TextView) findViewById(R.id.settingsVersionText);
        if (versionText != null) {
            String appLabel = getString(R.string.app_name);
            try {
                PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                versionText.setText(appLabel + " " + packageInfo.versionName + "(" + packageInfo.versionCode + ")");
            } catch (PackageManager.NameNotFoundException unused) {
                versionText.setText(appLabel);
            }
        }

        this.modeSpinner = (Spinner) findViewById(R.id.modeSpinner);
        this.modeSpinner.setAdapter(createSpinnerAdapter(this.modeSpinner, R.array.mode_array));
        this.modeSpinner.setSelection(this.currentMode.index, false);
        this.modeSpinner.setOnItemSelectedListener(this);

        this.orientationSpinner = (Spinner) findViewById(R.id.myCurrentView);
        this.orientationSpinner.setAdapter(
                createSpinnerAdapter(this.orientationSpinner, R.array.orientation_array));
        this.orientationSpinner.setSelection(readOrientationMode(preferences), false);
        this.orientationSpinner.setOnItemSelectedListener(this);

        this.vibrationSpinner = (Spinner) findViewById(R.id.myVibration);
        this.vibrationSpinner.setAdapter(
                createSpinnerAdapter(this.vibrationSpinner, R.array.vibration_array));
        this.vibrationSpinner.setSelection(this.vibrationLevel, false);
        this.vibrationSpinner.setOnItemSelectedListener(this);

        this.alwaysOnSwitch = (Switch) findViewById(R.id.spinner1);
        this.soundSwitch = (Switch) findViewById(R.id.mySound);
        this.decimalSwapSwitch = (Switch) findViewById(R.id.DecimalSwap);

        this.alwaysOnSwitch.setChecked(preferences.getBoolean("AlwaysOn", false));
        this.soundSwitch.setChecked(preferences.getBoolean("Sound", false));
        this.decimalSwapSwitch.setChecked(
                preferences.getBoolean(getDecimalSwapPrefKey(this.currentMode), false));

        this.alwaysOnSwitch.setOnCheckedChangeListener(this);
        this.soundSwitch.setOnCheckedChangeListener(this);
        this.decimalSwapSwitch.setOnCheckedChangeListener(this);

        View modeRow = findViewById(R.id.LinearLayoutMode);
        if (modeRow != null) {
            modeRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SettingsActivity.this.modeSpinner.performClick();
                }
            });
        }

        View quickGuideRow = findViewById(R.id.LinearLayoutQuickGuide);
        if (quickGuideRow != null) {
            quickGuideRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBackCoverDialog();
                }
            });
        }

        View alwaysOnRow = findViewById(R.id.LinearLayout066);
        if (alwaysOnRow != null) {
            alwaysOnRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SettingsActivity.this.alwaysOnSwitch.toggle();
                }
            });
        }

        View soundRow = findViewById(R.id.LinearLayout00);
        if (soundRow != null) {
            soundRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SettingsActivity.this.soundSwitch.toggle();
                }
            });
        }

        View vibrationRow = findViewById(R.id.LinearLayout033);
        if (vibrationRow != null) {
            vibrationRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SettingsActivity.this.vibrationSpinner.performClick();
                }
            });
        }

        View orientationRow = findViewById(R.id.LinearLayout044);
        if (orientationRow != null) {
            orientationRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SettingsActivity.this.orientationSpinner.performClick();
                }
            });
        }

        View decimalSwapRow = findViewById(R.id.LinearLayout055);
        if (decimalSwapRow != null) {
            decimalSwapRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SettingsActivity.this.decimalSwapSwitch.toggle();
                }
            });
        }

        View resetRow = findViewById(R.id.LinearLayoutReset);
        if (resetRow != null) {
            resetRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showResetConfirmDialog();
                }
            });
        }
    }

    @Override
    protected void onPause() {
        if (this.backCoverDialog != null && this.backCoverDialog.isShowing()) {
            this.backCoverDialog.dismiss();
        }
        if (this.resetConfirmDialog != null && this.resetConfirmDialog.isShowing()) {
            this.resetConfirmDialog.dismiss();
        }
        super.onPause();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
        if (parent == this.modeSpinner) {
            CalculatorMode selectedMode = CalculatorMode.fromIndex(position);
            if (selectedMode == this.currentMode) {
                return;
            }
            this.currentMode = selectedMode;
            preferences.edit().putInt("CalcMode", selectedMode.index).commit();
            if (this.decimalSwapSwitch != null) {
                this.decimalSwapSwitch.setOnCheckedChangeListener(null);
                this.decimalSwapSwitch.setChecked(
                        preferences.getBoolean(getDecimalSwapPrefKey(selectedMode), false));
                this.decimalSwapSwitch.setOnCheckedChangeListener(this);
            }
            return;
        }
        if (parent == this.orientationSpinner) {
            if (position != readOrientationMode(preferences)) {
                applyOrientationMode(position);
            }
            return;
        }
        if (parent == this.vibrationSpinner) {
            if (position == this.vibrationLevel) {
                return;
            }
            this.vibrationLevel = position;
            preferences.edit().putInt("VibrationLevel", position).commit();
            triggerVibrationForLevel(this.vibrator, position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        int viewId = compoundButton.getId();
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
        if (viewId == R.id.DecimalSwap) {
            String swapKey = getDecimalSwapPrefKey(this.currentMode);
            String pendingKey = getPendingDecimalSwapPrefKey(this.currentMode);
            boolean wasPending = preferences.getBoolean(pendingKey, false);
            preferences.edit()
                    .putBoolean(swapKey, isChecked)
                    .putBoolean(pendingKey, !wasPending)
                    .commit();
            return;
        }
        if (viewId == R.id.mySound) {
            preferences.edit().putBoolean("Sound", isChecked).commit();
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            return;
        }
        if (viewId == R.id.spinner1) {
            preferences.edit().putBoolean("AlwaysOn", isChecked).commit();
            applyAlwaysOnSetting(isChecked);
        }
    }

    private SpinnerAdapter createSpinnerAdapter(final Spinner spinner, int arrayResId) {
        final CharSequence[] items = getResources().getTextArray(arrayResId);
        final LayoutInflater inflater = LayoutInflater.from(this);
        final int accentColor = getColor(R.color.settings_accent);
        final int primaryTextColor = getColor(R.color.settings_text_primary);
        final int selectedBgColor = getColor(R.color.settings_selected_item_bg);

        return new ArrayAdapter<CharSequence>(this, R.layout.spinner_item, items) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View row = convertView != null
                        ? convertView
                        : inflater.inflate(R.layout.spinner_dropdown_item, parent, false);
                TextView label = (TextView) row.findViewById(android.R.id.text1);
                View checkMark = row.findViewById(R.id.spinnerDropdownCheck);
                boolean isSelected = position == spinner.getSelectedItemPosition();

                if (label != null) {
                    label.setText(items[position]);
                    label.setTextColor(isSelected ? accentColor : primaryTextColor);
                    label.setTypeface(isSelected ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                }
                if (checkMark != null) {
                    checkMark.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
                }
                row.setBackgroundColor(isSelected ? selectedBgColor : Color.TRANSPARENT);
                return row;
            }
        };
    }

    private void applyAlwaysOnSetting(boolean enabled) {
        if (enabled) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private void applySavedOrientation(SharedPreferences preferences) {
        int orientationMode = readOrientationMode(preferences);
        if (orientationMode == 2) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        } else if (orientationMode == 1) {
            int lockViewSetting = readLockViewSetting(preferences);
            if (lockViewSetting != ActivityInfo.SCREEN_ORIENTATION_USER
                    && lockViewSetting != ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR) {
                setRequestedOrientation(lockViewSetting);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        }
    }

    private void applyOrientationMode(int mode) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putInt("OrientationMode", mode);
        if (mode == 0) {
            editor.putInt("LockView", ActivityInfo.SCREEN_ORIENTATION_USER);
            editor.commit();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        } else if (mode == 1) {
            int lockedOrientation;
            if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
                lockedOrientation = getCurrentScreenOrientationLock(this);
            } else {
                lockedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            }
            editor.putInt("LockView", lockedOrientation);
            editor.commit();
            setRequestedOrientation(lockedOrientation);
        } else if (mode == 2) {
            editor.putInt("LockView", ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            editor.commit();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        } else {
            editor.commit();
        }
    }

    private void showBackCoverDialog() {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.backCoverDialog = dialog;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View backView = getLayoutInflater().inflate(R.layout.back_layout, (ViewGroup) null);
        View.OnClickListener closeBackCoverClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SettingsActivity.this.backCoverDialog != null
                        && SettingsActivity.this.backCoverDialog.isShowing()) {
                    SettingsActivity.this.backCoverDialog.dismiss();
                }
            }
        };
        ImageView backCoverImage = (ImageView) backView.findViewById(R.id.backCoverImage);
        if (backCoverImage != null) {
            backCoverImage.setImageResource(this.currentMode.backCoverDrawableId);
            backCoverImage.setOnClickListener(closeBackCoverClickListener);
        }
        backView.setOnClickListener(closeBackCoverClickListener);
        dialog.setContentView(backView);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        }
        dialog.show();
    }

    private void showResetConfirmDialog() {
        this.resetConfirmDialog = new AlertDialog.Builder(this)
                .setIcon((Drawable) null)
                .setTitle(R.string.Reset_Calculator)
                .setMessage(R.string.Reset_Message)
                .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performCalculatorReset();
                    }
                })
                .setNegativeButton(R.string.No, (DialogInterface.OnClickListener) null)
                .create();
        this.resetConfirmDialog.show();
    }

    private void performCalculatorReset() {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putBoolean("Sound", false);
        editor.putBoolean("Vibration", false);
        editor.putBoolean("AlwaysOn", false);
        editor.putBoolean(getDecimalSwapPrefKey(this.currentMode), false);
        editor.putBoolean(getPendingDecimalSwapPrefKey(this.currentMode), false);
        editor.putBoolean("CalcLock", false);
        editor.putInt("LockView", ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        editor.putInt("OrientationMode", 2);
        editor.putInt("VibrationLevel", 1);
        editor.putInt("Timeout", 0);
        editor.putBoolean(PREF_PENDING_RESET, true);
        editor.commit();
        finish();
    }
}

package org.liberty.android.classiccalc.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ViewSwitcher;
import java.io.File;
import java.util.Date;
import org.liberty.android.classiccalc.R;
import org.liberty.android.classiccalc.engine.CalcEngine;
import org.liberty.android.classiccalc.engine.CalcMemory;
import org.liberty.android.classiccalc.engine.NutEmulator;

public class Calculator extends Activity {
    static final int[] SLEEP_TIMEOUT_MS = {0, 300000, 600000, 900000, 1800000, 3600000};

    KeypadInput keypadInput;
    LcdView lcdView;
    CalculationThread calculationThread;
    private ViewSwitcher viewSwitcher;
    private int calculatorWidth;
    private int calculatorHeight;
    private boolean isRunningProgram;
    private String savedRunningLcdText;
    boolean isKeepScreenOnSet;

    private CalculatorMode calculatorMode = CalculatorMode.CC_12C;
    private NutEmulator emulator12C;
    private CalcEngine engine12CP;
    private NutEmulator emulator15C;

    boolean isCalculationThreadRunning = true;
    boolean isFullScreen = false;
    Context context = this;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    boolean isSoundEnabled = false;
    int vibrationLevel = 1;

    @SuppressLint({"HandlerLeak"})
    Handler lcdUpdateHandler = new LcdUpdateHandler();
    long lastInteractionTimeMs = 0;
    Thread idleTimeoutThread = null;
    boolean isIdleThreadRunning = true;

    class LcdUpdateHandler extends Handler {
        LcdUpdateHandler() {
        }

        @Override
        public void handleMessage(Message message) {
            if (message.what == 1 && Calculator.this.lcdView != null) {
                Calculator.this.lcdView.updateDisplayText((String) message.obj);
            }
            super.handleMessage(message);
        }
    }

    class IdleTimeoutThread extends Thread {

        class IdleCheckRunnable implements Runnable {
            IdleCheckRunnable() {
            }

            @Override
            public void run() {
                long currentTimeMs = new Date().getTime();
                int timeoutIndex = Calculator.this.getSleepTimeoutIndex();
                if (timeoutIndex != 6) {
                    Calculator activity = Calculator.this;
                    if (currentTimeMs - activity.lastInteractionTimeMs >= Calculator.SLEEP_TIMEOUT_MS[timeoutIndex]) {
                        if (activity.isKeepScreenOnSet) {
                            Log.d("ClassicCalc.Calculator", "getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)");
                            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                            activity.isKeepScreenOnSet = false;
                            if (timeoutIndex == 0) {
                                activity.isIdleThreadRunning = false;
                                return;
                            }
                            return;
                        }
                        return;
                    }
                }
                double idleDurationMs = currentTimeMs - Calculator.this.lastInteractionTimeMs;
                Log.d("ClassicCalc.Calculator", "Idle time:" + Math.round(idleDurationMs / 60000.0d) + " mins");
                if (Calculator.this.isKeepScreenOnSet) {
                    return;
                }
                Log.d("ClassicCalc.Calculator", "getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)");
                Calculator.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                Calculator.this.isKeepScreenOnSet = true;
            }
        }

        IdleTimeoutThread() {
        }

        @Override
        public void run() {
            while (Calculator.this.isIdleThreadRunning) {
                try {
                    Calculator.this.runOnUiThread(new IdleCheckRunnable());
                    Thread.sleep(15000L);
                } catch (InterruptedException unused) {
                    Calculator.this.isKeepScreenOnSet = false;
                    Log.d("ClassicCalc.Calculator", "Interrupted.");
                }
            }
            Calculator.this.idleTimeoutThread = null;
        }
    }

    private void ensureCc12cLoaded() {
        if (this.emulator12C == null) {
            String statePath = "/data/data/org.liberty.android.classiccalc/calc";
            if (getApplicationInfo() != null && getApplicationInfo().dataDir != null) {
                statePath = new File(getApplicationInfo().dataDir, "calc").getAbsolutePath();
            }
            this.emulator12C = new NutEmulator(false, statePath, new NutEmulator.Callback() {
                @Override
                public int handleKeybuffer(int command) {
                    return Calculator.this.handleKeybuffer(command);
                }

                @Override
                public void updateLcd(String lcdText) {
                    Calculator.this.updateLcd(lcdText);
                }
            });
        }
    }

    private void ensureCc12cpLoaded() {
        if (this.engine12CP == null) {
            String statePath = "/data/data/org.liberty.android.classiccalc/p";
            if (getApplicationInfo() != null && getApplicationInfo().dataDir != null) {
                statePath = new File(getApplicationInfo().dataDir, "p").getAbsolutePath();
            }
            this.engine12CP = new CalcEngine(new CalcMemory.Callback() {
                @Override
                public int handleKeybuffer(int command) {
                    return Calculator.this.handleKeybuffer(command);
                }

                @Override
                public void updateLcd(String lcdText) {
                    Calculator.this.updateLcd(lcdText);
                }
            }, statePath);
        }
    }

    private void ensureCc15cLoaded() {
        if (this.emulator15C == null) {
            String statePath = "/data/data/org.liberty.android.classiccalc/calc15";
            if (getApplicationInfo() != null && getApplicationInfo().dataDir != null) {
                statePath = new File(getApplicationInfo().dataDir, "calc15").getAbsolutePath();
            }
            this.emulator15C = new NutEmulator(true, statePath, new NutEmulator.Callback() {
                @Override
                public int handleKeybuffer(int command) {
                    return Calculator.this.handleKeybuffer(command);
                }

                @Override
                public void updateLcd(String lcdText) {
                    Calculator.this.updateLcd(lcdText);
                }
            });
        }
    }

    private void ensureActiveModeBridge() {
        if (this.calculatorMode == CalculatorMode.CC_12C) {
            ensureCc12cLoaded();
        } else if (this.calculatorMode == CalculatorMode.CC_12CP) {
            ensureCc12cpLoaded();
        } else if (this.calculatorMode == CalculatorMode.CC_15C) {
            ensureCc15cLoaded();
        }
    }

    public CalculatorMode getCalculatorMode() {
        return this.calculatorMode;
    }

    public int handleKeybufferFromNative(int command) {
        return handleKeybuffer(command);
    }

    // Called from native libcc12c.so via JNI (and delegated from bridge classes)
    private int handleKeybuffer(int command) {
        if (command == 0) {
            CalculationThread thread = this.calculationThread;
            if (thread != null) {
                thread.clearBuffer();
            }
        } else if (command == 1) {
            CalculationThread thread = this.calculationThread;
            if (thread != null) {
                return thread.dequeueKey();
            }
        } else if (command == 2) {
            CalculationThread thread = this.calculationThread;
            if (thread != null) {
                return thread.peekKey();
            }
        } else if (command == 3) {
            CalculationThread thread = this.calculationThread;
            return (thread == null || thread.isBufferEmpty()) ? 1 : 0;
        }
        return -1;
    }

    private void updateLcdMargins(View lcdView) {
        int leftMargin;
        int baseTopMargin;
        if (lcdView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) lcdView.getLayoutParams();
            boolean is12CP = this.calculatorMode == CalculatorMode.CC_12CP;
            if (isLandscape()) {
                leftMargin = scaleLeftMargin(is12CP ? 85 : 69);
                baseTopMargin = 7;
            } else {
                leftMargin = scaleLeftMargin(is12CP ? 16 : 14);
                baseTopMargin = is12CP ? 20 : 17;
            }
            marginLayoutParams.setMargins(leftMargin, scaleTopMargin(baseTopMargin), 0, 0);
            lcdView.requestLayout();
        }
    }

    public void updateLcdFromNative(String lcdText) {
        updateLcd(lcdText);
    }

    // Called from native libcc12c.so via JNI (and delegated from bridge classes)
    private void updateLcd(String lcdText) {
        Handler handler = this.lcdUpdateHandler;
        handler.sendMessage(Message.obtain(handler, 1, lcdText));
    }

    public String ExecuteCalcCC15c() {
        ensureCc12cLoaded();
        return this.emulator12C.executeCalc();
    }

    String executeCalcCycle() {
        ensureActiveModeBridge();
        if (this.calculatorMode == CalculatorMode.CC_15C) {
            return this.emulator15C.executeCalc();
        }
        if (this.calculatorMode == CalculatorMode.CC_12C) {
            return ExecuteCalcCC15c();
        }
        return "";
    }

    String executeKeyPressed12CP(int keyCode, boolean isKeyUp) {
        ensureActiveModeBridge();
        return this.engine12CP.keyPressed(keyCode, (byte) (isKeyUp ? 1 : 0));
    }

    void keyPressed(int keyCode, boolean isKeyUp) {
        ensureActiveModeBridge();
        if (this.calculatorMode == CalculatorMode.CC_15C) {
            this.emulator15C.keyPressed(keyCode, isKeyUp);
        } else if (this.calculatorMode == CalculatorMode.CC_12C) {
            keyPressedCC15c(keyCode, isKeyUp);
        }
    }

    String resetActiveCalc() {
        ensureActiveModeBridge();
        if (this.calculatorMode == CalculatorMode.CC_12CP) {
            return this.engine12CP.resetCalc();
        }
        if (this.calculatorMode == CalculatorMode.CC_15C) {
            return this.emulator15C.resetCalc();
        }
        return resetCalcCC15c();
    }

    String restoreActiveCalcData() {
        ensureActiveModeBridge();
        if (this.calculatorMode == CalculatorMode.CC_12CP) {
            return this.engine12CP.restoreCalcData();
        }
        if (this.calculatorMode == CalculatorMode.CC_15C) {
            return this.emulator15C.restoreCalcData();
        }
        return restoreCalcDataCC15c();
    }

    int saveActiveCalcData() {
        ensureActiveModeBridge();
        if (this.calculatorMode == CalculatorMode.CC_12CP) {
            return this.engine12CP.saveCalcData();
        }
        if (this.calculatorMode == CalculatorMode.CC_15C) {
            return this.emulator15C.saveCalcData();
        }
        return saveCalcDataCC15c();
    }

    String swapActiveDecimalSep() {
        ensureActiveModeBridge();
        if (this.calculatorMode == CalculatorMode.CC_12CP) {
            return this.engine12CP.swapDecimalSep();
        }
        if (this.calculatorMode == CalculatorMode.CC_15C) {
            return this.emulator15C.swapDecimalSepCC15c();
        }
        return swapDecimalSepCC12c();
    }

    private void swapDecimalSepForMode(CalculatorMode mode) {
        if (mode == CalculatorMode.CC_12CP) {
            ensureCc12cpLoaded();
            this.engine12CP.restoreCalcData();
            this.engine12CP.swapDecimalSep();
            this.engine12CP.saveCalcData();
        } else if (mode == CalculatorMode.CC_15C) {
            ensureCc15cLoaded();
            this.emulator15C.restoreCalcData();
            this.emulator15C.swapDecimalSepCC15c();
            this.emulator15C.saveCalcData();
        } else {
            ensureCc12cLoaded();
            this.emulator12C.restoreCalcData();
            this.emulator12C.swapDecimalSepCC12c();
            this.emulator12C.saveCalcData();
        }
    }

    private void stopCalculationThread() {
        this.isCalculationThreadRunning = false;
        if (this.keypadInput != null) {
            this.keypadInput.releaseActiveKey();
        }
        CalculationThread calcThread = this.calculationThread;
        if (calcThread != null) {
            synchronized (calcThread) {
                calcThread.notify();
            }
            try {
                calcThread.join(50L);
            } catch (InterruptedException unused) {
            }
            if (calcThread.isAlive() && calcThread.isExecuting && this.keypadInput != null) {
                Log.d("ClassicCalc.Calculator", "Sending 'Enter' Key to abort calculation");
                this.keypadInput.activeKeyIndex = (this.calculatorMode == CalculatorMode.CC_12CP)
                        ? (isLandscape() ? 22 : 0)
                        : 25;
                this.keypadInput.handleKeyDown();
                this.keypadInput.handleKeyUp();
                this.keypadInput.activeKeyIndex = -1;
                try {
                    calcThread.join(1000L);
                } catch (InterruptedException unused) {
                }
            }
            this.calculationThread = null;
        }
    }

    void startCalculationThread() {
        Log.d("ClassicCalc.Calculator", "Starting Clac Thread");
        if (this.calculationThread == null) {
            this.calculationThread = new CalculationThread(this);
        }
        CalculationThread thread = this.calculationThread;
        if (thread == null || thread.isAlive()) {
            return;
        }
        this.isCalculationThreadRunning = true;
        this.calculationThread.start();
    }

    void openSettingsScreen() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    int scaleLeftMargin(int baseMargin) {
        float densityDpi = this.displayMetrics.densityDpi;
        float scaledMargin = baseMargin * this.displayMetrics.density;
        float widthInInches = this.calculatorWidth / densityDpi;
        float orientationDivisor = isLandscape() ? 3.0f : 2.0f;
        int leftMargin = (int) ((widthInInches / orientationDivisor) * scaledMargin);
        Log.d("ClassicCalc.Calculator", "leftMargin:" + leftMargin);
        return leftMargin;
    }

    int getCalculatorHeight() {
        return this.calculatorHeight;
    }

    int getCalculatorWidth() {
        return this.calculatorWidth;
    }

    boolean isRunningProgram() {
        return this.isRunningProgram;
    }

    String getSavedRunningLcdText() {
        return this.savedRunningLcdText;
    }

    CalculationThread getCalculationThread() {
        if (this.calculationThread == null) {
            startCalculationThread();
        }
        return this.calculationThread;
    }

    public void keyPressedCC15c(int keyCode, boolean isKeyUp) {
        ensureCc12cLoaded();
        this.emulator12C.keyPressed(keyCode, isKeyUp);
    }

    int scaleTopMargin(int baseMargin) {
        double orientationDivisor;
        float densityDpi = this.displayMetrics.densityDpi;
        if (isLandscape()) {
            orientationDivisor = this.isFullScreen ? 2.0d : 295.0d / 160.0d;
        } else {
            orientationDivisor = this.isFullScreen ? 3.0d : 455.0d / 160.0d;
        }
        double scaledMargin = baseMargin * this.displayMetrics.density;
        double heightInInches = this.calculatorHeight / densityDpi;
        int topMargin = (int) ((heightInInches / orientationDivisor) * scaledMargin);
        Log.d("ClassicCalc.Calculator", "topMargin:" + topMargin);
        return topMargin;
    }

    int getLockViewSetting() {
        return SettingsActivity.readLockViewSetting(
                getSharedPreferences(SettingsActivity.PREFS_NAME, 0));
    }

    int getOrientationMode() {
        return SettingsActivity.readOrientationMode(
                getSharedPreferences(SettingsActivity.PREFS_NAME, 0));
    }

    boolean isAlwaysOnEnabled() {
        return getSharedPreferences(SettingsActivity.PREFS_NAME, 0).getBoolean("AlwaysOn", false);
    }

    void applyAlwaysOnSetting(boolean enabled) {
        if (enabled) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            this.isKeepScreenOnSet = true;
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            this.isKeepScreenOnSet = false;
        }
    }

    int getSleepTimeoutIndex() {
        return getSharedPreferences(SettingsActivity.PREFS_NAME, 0).getInt("Timeout", 0);
    }

    boolean isLandscape() {
        getWindowManager().getDefaultDisplay().getMetrics(this.displayMetrics);
        return this.displayMetrics.widthPixels >= this.displayMetrics.heightPixels;
    }

    private void updateStatusBarForOrientation() {
        this.isFullScreen = isLandscape();
        if (this.isFullScreen) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        int displayedChildIndex;
        super.onConfigurationChanged(configuration);
        if (this.keypadInput != null) {
            this.keypadInput.detachOverlayView();
            this.keypadInput = null;
        }
        this.lcdView = null;
        updateStatusBarForOrientation();
        if (isLandscape()) {
            Log.d("ClassicCalc.Calculator", "onConfigurationChanged:Landscape " + this.displayMetrics.widthPixels + ":" + this.displayMetrics.heightPixels);
            this.lcdView = (LcdView) findViewById(R.id.LcdView);
            displayedChildIndex = 0;
        } else {
            Log.d("ClassicCalc.Calculator", "onConfigurationChanged:Portrait " + this.displayMetrics.widthPixels + ":" + this.displayMetrics.heightPixels);
            this.lcdView = (LcdView) findViewById(R.id.LcdView2);
            displayedChildIndex = 1;
        }
        this.viewSwitcher.setDisplayedChild(displayedChildIndex);
        this.keypadInput = new KeypadInput(this);
        this.lcdView.initializeLayout(this);
        updateLcdMargins(this.lcdView);
        this.lcdView.invalidate();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences(SettingsActivity.PREFS_NAME, 0);
        this.calculatorMode = CalculatorMode.fromIndex(preferences.getInt("CalcMode", 0));
        ensureActiveModeBridge();
        this.isSoundEnabled = preferences.getBoolean("Sound", false);
        this.vibrationLevel = SettingsActivity.readVibrationLevel(preferences);
        getWindowManager().getDefaultDisplay().getMetrics(this.displayMetrics);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        updateStatusBarForOrientation();
        setContentView(R.layout.main);
        this.viewSwitcher = (ViewSwitcher) findViewById(R.id.ViewSwitcher);
        this.viewSwitcher.setBackgroundColor(Color.BLACK);
        if (isLandscape()) {
            this.lcdView = (LcdView) findViewById(R.id.LcdView);
            this.viewSwitcher.setDisplayedChild(0);
        } else {
            this.lcdView = (LcdView) findViewById(R.id.LcdView2);
            this.viewSwitcher.setDisplayedChild(1);
        }
        this.keypadInput = new KeypadInput(this);
        this.lcdView.initializeLayout(this);
        updateLcdMargins(this.lcdView);
        this.viewSwitcher.requestLayout();
    }

    @Override
    protected void onDestroy() {
        Log.d("ClassicCalc.Calculator", "App closing...");
        super.onDestroy();
    }

    private void updateLauncherIcon() {
        PackageManager packageManager = getPackageManager();
        String pkgName = getPackageName();
        ComponentName targetComponent = new ComponentName(pkgName, this.calculatorMode.launcherAliasName);
        int currentState = packageManager.getComponentEnabledSetting(targetComponent);
        boolean isTargetAlreadyEnabled = (currentState == PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
                || (currentState == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT && this.calculatorMode == CalculatorMode.CC_12C);
        if (isTargetAlreadyEnabled) {
            return;
        }
        for (CalculatorMode mode : CalculatorMode.values()) {
            ComponentName componentName = new ComponentName(pkgName, mode.launcherAliasName);
            int newState = (mode == this.calculatorMode)
                    ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                    : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
            packageManager.setComponentEnabledSetting(componentName, newState, PackageManager.DONT_KILL_APP);
        }
    }

    @Override
    protected void onPause() {
        Log.d("ClassicCalc.Calculator", "Pausing app...");
        Log.d("ClassicCalc.Calculator", "Stopping Clac Thread");
        stopCalculationThread();
        saveActiveCalcData();
        updateLauncherIcon();
        Thread idleThread = this.idleTimeoutThread;
        if (idleThread != null && idleThread.isAlive()) {
            Log.d("ClassicCalc.Calculator", "Stopping idle thread.");
            this.isIdleThreadRunning = false;
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("ClassicCalc.Calculator", "Resuming app...");
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(SettingsActivity.PREFS_NAME, 0);
        this.isSoundEnabled = preferences.getBoolean("Sound", false);
        if (this.isSoundEnabled) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
        }
        this.vibrationLevel = SettingsActivity.readVibrationLevel(preferences);

        CalculatorMode savedMode = CalculatorMode.fromIndex(preferences.getInt("CalcMode", 0));
        if (savedMode != this.calculatorMode) {
            this.calculatorMode = savedMode;
            ensureActiveModeBridge();
            if (this.keypadInput != null) {
                this.keypadInput.updateLayoutDimensions();
                this.keypadInput.initializeKeyBounds();
            }
            if (this.lcdView != null) {
                this.lcdView.initializeLayout(this);
                updateLcdMargins(this.lcdView);
            }
            if (this.viewSwitcher != null) {
                this.viewSwitcher.requestLayout();
            }
        }

        String lcdText = restoreActiveCalcData();
        if (preferences.getBoolean(SettingsActivity.PREF_PENDING_RESET, false)) {
            preferences.edit().putBoolean(SettingsActivity.PREF_PENDING_RESET, false).commit();
            lcdText = resetActiveCalc();
            saveActiveCalcData();
        }
        for (CalculatorMode mode : CalculatorMode.values()) {
            String pendingSwapKey = SettingsActivity.getPendingDecimalSwapPrefKey(mode);
            if (preferences.getBoolean(pendingSwapKey, false)) {
                preferences.edit().putBoolean(pendingSwapKey, false).commit();
                if (mode == this.calculatorMode) {
                    lcdText = swapActiveDecimalSep();
                    saveActiveCalcData();
                } else {
                    swapDecimalSepForMode(mode);
                }
            }
        }

        startCalculationThread();
        this.lcdView.updateDisplayText(lcdText);
        this.lcdView.invalidate();

        int orientationMode = getOrientationMode();
        if (orientationMode == 2) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        } else if (orientationMode == 1) {
            int lockViewSetting = getLockViewSetting();
            if (lockViewSetting != ActivityInfo.SCREEN_ORIENTATION_USER
                    && lockViewSetting != ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR) {
                setRequestedOrientation(lockViewSetting);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        }
        applyAlwaysOnSetting(isAlwaysOnEnabled());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ClassicCalc.Calculator", "onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ClassicCalc.Calculator", "onStop() called");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        this.lastInteractionTimeMs = new Date().getTime();
    }

    void setCalculatorDimensions(int width, int height) {
        this.calculatorWidth = width;
        this.calculatorHeight = height;
    }

    public String resetCalcCC15c() {
        ensureCc12cLoaded();
        return this.emulator12C.resetCalc();
    }

    public String restoreCalcDataCC15c() {
        ensureCc12cLoaded();
        return this.emulator12C.restoreCalcData();
    }

    void setRunningProgram(boolean running) {
        this.isRunningProgram = running;
    }

    public int saveCalcDataCC15c() {
        ensureCc12cLoaded();
        return this.emulator12C.saveCalcData();
    }

    public String swapDecimalSepCC12c() {
        ensureCc12cLoaded();
        return this.emulator12C.swapDecimalSepCC12c();
    }

    void setSavedRunningLcdText(String text) {
        this.savedRunningLcdText = text;
    }
}

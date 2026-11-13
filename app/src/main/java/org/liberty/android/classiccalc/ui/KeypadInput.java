package org.liberty.android.classiccalc.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.Arrays;
import org.liberty.android.classiccalc.R;

public class KeypadInput {
    private static final int[] PORTRAIT_12CP_KEY_CODES = {
            32, 51, 25, 29, 20, 28, 34, 35, 36, 37, 15, 38,
            39, 40, 41, 24, 42, 43, 44, 30, 46, 47, 48, 49
    };

    private static AudioManager audioManager;

    float touchX;
    float touchY;
    private View highlightOverlayView;
    Calculator calculatorActivity;
    FrameLayout mainLayout;
    private Vibrator vibrator;

    boolean isHardwareKeyPressed = false;
    boolean isLogoPressed = false;
    int activeKeyIndex = -1;
    KeyState keyState = KeyState.IDLE;
    Rect landscapeLogoRect = new Rect();
    Rect portraitLogoRect = new Rect();
    Rect[] portraitKeyRects = new Rect[40];
    Rect[] landscapeKeyRects = new Rect[40];
    RectF highlightedKeyBounds = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
    int scaledLayoutWidth = 0;
    int horizontalMargin = 0;
    int scaledLayoutHeight = 0;
    int verticalMargin = 0;
    private int statusBarHeightDp = 25;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    View.OnKeyListener hardwareKeyListener = new HardwareKeyListener();
    View.OnTouchListener keypadTouchListener = new KeypadTouchListener();
    private View.OnClickListener emptyClickListener = new NoOpClickListener(this);

    class HardwareKeyListener implements View.OnKeyListener {
        HardwareKeyListener() {
        }

        private int mapHardwareKey12CP(int keyCode) {
            int mappedKeyIndex = 30;
            if (keyCode == 29) {
                mappedKeyIndex = 1;
            } else if (keyCode == 30) {
                mappedKeyIndex = 2;
            } else if (keyCode == 51) {
                mappedKeyIndex = 4;
            } else if (keyCode == 53) {
                mappedKeyIndex = 35;
            } else if (keyCode == 59) {
                mappedKeyIndex = 7;
            } else if (keyCode == 66) {
                mappedKeyIndex = 22;
            } else if (keyCode != 67) {
                switch (keyCode) {
                    case 7:
                        mappedKeyIndex = 26;
                        break;
                    case 8:
                        mappedKeyIndex = 25;
                        break;
                    case 9:
                        mappedKeyIndex = 29;
                        break;
                    case 10:
                        mappedKeyIndex = 33;
                        break;
                    case 11:
                        mappedKeyIndex = 24;
                        break;
                    case 12:
                        mappedKeyIndex = 28;
                        break;
                    case 13:
                        mappedKeyIndex = 32;
                        break;
                    case 14:
                        mappedKeyIndex = 23;
                        break;
                    case 15:
                        mappedKeyIndex = 27;
                        break;
                    case 16:
                        mappedKeyIndex = 31;
                        break;
                    default:
                        switch (keyCode) {
                            case 32:
                                mappedKeyIndex = 9;
                                break;
                            case 33:
                                mappedKeyIndex = 8;
                                break;
                            case 34:
                                mappedKeyIndex = 13;
                                break;
                            case 35:
                                mappedKeyIndex = 17;
                                break;
                            case 36:
                                mappedKeyIndex = 20;
                                break;
                            case 37:
                                break;
                            case 38:
                                mappedKeyIndex = 21;
                                break;
                            case 39:
                                mappedKeyIndex = 15;
                                break;
                            case 40:
                                mappedKeyIndex = 19;
                                break;
                            case 41:
                                mappedKeyIndex = 10;
                                break;
                            case 42:
                                mappedKeyIndex = 6;
                                break;
                            case 43:
                                mappedKeyIndex = 38;
                                break;
                            case 44:
                                mappedKeyIndex = 34;
                                break;
                            case 45:
                                mappedKeyIndex = 0;
                                break;
                            case 46:
                                mappedKeyIndex = 12;
                                break;
                            case 47:
                                mappedKeyIndex = 5;
                                break;
                            case 48:
                                mappedKeyIndex = 16;
                                break;
                            case 49:
                                mappedKeyIndex = 36;
                                break;
                            default:
                                switch (keyCode) {
                                    case 55:
                                        mappedKeyIndex = 14;
                                        break;
                                    case 56:
                                        mappedKeyIndex = 30;
                                        break;
                                    case 57:
                                        mappedKeyIndex = 11;
                                        break;
                                    default:
                                        mappedKeyIndex = -1;
                                        break;
                                }
                                break;
                        }
                        break;
                }
            } else {
                mappedKeyIndex = 18;
            }
            return mappedKeyIndex;
        }

        private int mapHardwareKey12C15C(int keyCode) {
            int mappedKeyIndex = 29;
            if (keyCode == 29) {
                mappedKeyIndex = 10;
            } else if (keyCode == 30) {
                mappedKeyIndex = 20;
            } else if (keyCode == 51) {
                mappedKeyIndex = 1;
            } else if (keyCode == 53) {
                mappedKeyIndex = 9;
            } else if (keyCode == 59) {
                mappedKeyIndex = 31;
            } else if (keyCode == 66) {
                mappedKeyIndex = 25;
            } else if (keyCode != 67) {
                switch (keyCode) {
                    case 7:
                        mappedKeyIndex = 36;
                        break;
                    case 8:
                        mappedKeyIndex = 26;
                        break;
                    case 9:
                        mappedKeyIndex = 27;
                        break;
                    case 10:
                        mappedKeyIndex = 28;
                        break;
                    case 11:
                        mappedKeyIndex = 16;
                        break;
                    case 12:
                        mappedKeyIndex = 17;
                        break;
                    case 13:
                        mappedKeyIndex = 18;
                        break;
                    case 14:
                        mappedKeyIndex = 6;
                        break;
                    case 15:
                        mappedKeyIndex = 7;
                        break;
                    case 16:
                        mappedKeyIndex = 8;
                        break;
                    default:
                        switch (keyCode) {
                            case 32:
                                mappedKeyIndex = 12;
                                break;
                            case 33:
                                mappedKeyIndex = 2;
                                break;
                            case 34:
                                mappedKeyIndex = 13;
                                break;
                            case 35:
                                mappedKeyIndex = 14;
                                break;
                            case 36:
                                mappedKeyIndex = 5;
                                break;
                            case 37:
                                break;
                            case 38:
                                mappedKeyIndex = 15;
                                break;
                            case 39:
                                mappedKeyIndex = 33;
                                break;
                            case 40:
                                mappedKeyIndex = 34;
                                break;
                            case 41:
                                mappedKeyIndex = 22;
                                break;
                            case 42:
                                mappedKeyIndex = 21;
                                break;
                            case 43:
                                mappedKeyIndex = 39;
                                break;
                            case 44:
                                mappedKeyIndex = 38;
                                break;
                            case 45:
                                mappedKeyIndex = 0;
                                break;
                            case 46:
                                mappedKeyIndex = 3;
                                break;
                            case 47:
                                mappedKeyIndex = 11;
                                break;
                            case 48:
                                mappedKeyIndex = 4;
                                break;
                            case 49:
                                mappedKeyIndex = 19;
                                break;
                            default:
                                switch (keyCode) {
                                    case 55:
                                        mappedKeyIndex = 23;
                                        break;
                                    case 56:
                                        mappedKeyIndex = 37;
                                        break;
                                    case 57:
                                        mappedKeyIndex = 32;
                                        break;
                                    default:
                                        mappedKeyIndex = -1;
                                        break;
                                }
                                break;
                        }
                        break;
                }
            } else {
                mappedKeyIndex = 24;
            }
            return mappedKeyIndex;
        }

        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            KeypadInput keypadInput = KeypadInput.this;
            int mappedKeyIndex = keypadInput.calculatorActivity.getCalculatorMode() == CalculatorMode.CC_12CP
                    ? mapHardwareKey12CP(keyCode)
                    : mapHardwareKey12C15C(keyCode);
            if (mappedKeyIndex != -1 && keypadInput.landscapeKeyRects[mappedKeyIndex] != null) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (!keypadInput.isHardwareKeyPressed) {
                        keypadInput.showKeyPressedFeedback(keypadInput.landscapeKeyRects[mappedKeyIndex], mappedKeyIndex);
                        keypadInput.isHardwareKeyPressed = true;
                    }
                } else if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    keypadInput.clearKeyHighlight();
                    keypadInput.isHardwareKeyPressed = false;
                    keypadInput.registerActiveKey();
                }
            }
            return false;
        }
    }

    class KeypadTouchListener implements View.OnTouchListener {
        KeypadTouchListener() {
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            KeypadInput keypadInput = KeypadInput.this;
            int action = motionEvent.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                keypadInput.touchX = motionEvent.getX();
                keypadInput.touchY = motionEvent.getY();
                keypadInput.activeKeyIndex = -1;
                keypadInput.isLogoPressed = false;
                if (!keypadInput.calculatorActivity.isLandscape()) {
                    if (keypadInput.isTouchInside(keypadInput.portraitLogoRect)) {
                        keypadInput.isLogoPressed = true;
                    } else {
                        for (int keyIndex = 0; keyIndex < keypadInput.portraitKeyRects.length; keyIndex++) {
                            if (keypadInput.isTouchInside(keypadInput.portraitKeyRects[keyIndex])) {
                                keypadInput.showKeyPressedFeedback(keypadInput.portraitKeyRects[keyIndex], keyIndex);
                                break;
                            }
                        }
                    }
                } else {
                    if (keypadInput.isTouchInside(keypadInput.landscapeLogoRect)) {
                        keypadInput.isLogoPressed = true;
                    } else {
                        for (int keyIndex = 0; keyIndex < keypadInput.landscapeKeyRects.length; keyIndex++) {
                            if (keypadInput.isTouchInside(keypadInput.landscapeKeyRects[keyIndex])) {
                                keypadInput.showKeyPressedFeedback(keypadInput.landscapeKeyRects[keyIndex], keyIndex);
                                break;
                            }
                        }
                    }
                }
            } else if (action == MotionEvent.ACTION_MOVE) {
                keypadInput.touchX = motionEvent.getX();
                keypadInput.touchY = motionEvent.getY();
                if (keypadInput.isLogoPressed) {
                    Rect logoRect = keypadInput.calculatorActivity.isLandscape()
                            ? keypadInput.landscapeLogoRect
                            : keypadInput.portraitLogoRect;
                    if (!keypadInput.isTouchInside(logoRect)) {
                        keypadInput.isLogoPressed = false;
                    }
                }
                if (keypadInput.activeKeyIndex != -1) {
                    Rect activeRect = keypadInput.getActiveKeyRect(keypadInput.activeKeyIndex);
                    if (!keypadInput.isTouchInside(activeRect)) {
                        keypadInput.clearKeyHighlight();
                        keypadInput.activeKeyIndex = -1;
                    }
                }
            } else if (action == MotionEvent.ACTION_UP) {
                keypadInput.touchX = motionEvent.getX();
                keypadInput.touchY = motionEvent.getY();
                if (keypadInput.isLogoPressed) {
                    keypadInput.isLogoPressed = false;
                    Rect logoRect = keypadInput.calculatorActivity.isLandscape()
                            ? keypadInput.landscapeLogoRect
                            : keypadInput.portraitLogoRect;
                    if (keypadInput.isTouchInside(logoRect)) {
                        keypadInput.calculatorActivity.openSettingsScreen();
                    }
                }
                keypadInput.clearKeyHighlight();
                if (keypadInput.activeKeyIndex != -1) {
                    Rect activeRect = keypadInput.getActiveKeyRect(keypadInput.activeKeyIndex);
                    if (keypadInput.isTouchInside(activeRect)) {
                        keypadInput.registerActiveKey();
                    } else {
                        keypadInput.activeKeyIndex = -1;
                    }
                }
            } else if (action == MotionEvent.ACTION_CANCEL) {
                keypadInput.releaseActiveKey();
            }
            return false;
        }
    }

    class NoOpClickListener implements View.OnClickListener {
        NoOpClickListener(KeypadInput keypadInput) {
        }

        @Override
        public void onClick(View view) {
        }
    }

    public class KeyHighlightOverlayView extends View {
        public KeyHighlightOverlayView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            KeyState currentKeyState = KeypadInput.this.keyState;
            if (currentKeyState == KeyState.PRESSED || currentKeyState == KeyState.RELEASED) {
                Paint paint = new Paint();
                RectF highlightRect = new RectF(KeypadInput.this.highlightedKeyBounds);
                if (!KeypadInput.this.calculatorActivity.isLandscape()
                        && KeypadInput.this.calculatorActivity.getCalculatorMode() == CalculatorMode.CC_12C) {
                    highlightRect.top += 5.0f;
                    highlightRect.bottom -= 5.0f;
                }
                if (currentKeyState == KeyState.PRESSED) {
                    paint.setColor(Color.argb(180, 38, 38, 38));
                } else {
                    paint.setColor(Color.argb(0, 0, 0, 255));
                    KeypadInput.this.keyState = KeyState.IDLE;
                }
                canvas.drawRoundRect(highlightRect, 5.0f, 5.0f, paint);
            }
        }
    }

    KeypadInput(Calculator calculatorActivity) {
        this.calculatorActivity = calculatorActivity;
        calculatorActivity.getWindowManager().getDefaultDisplay().getMetrics(this.displayMetrics);
        this.vibrator = (Vibrator) this.calculatorActivity.getSystemService("vibrator");
        int layoutId = this.calculatorActivity.isLandscape() ? R.id.mainLayoutLandscape : R.id.mainLayoutPortrait;
        this.mainLayout = (FrameLayout) this.calculatorActivity.findViewById(layoutId);
        if (this.calculatorActivity.isLandscape()) {
            this.mainLayout.setOnKeyListener(this.hardwareKeyListener);
        }
        updateLayoutDimensions();
        initializeKeyBounds();
        KeyHighlightOverlayView overlayView = new KeyHighlightOverlayView(this.calculatorActivity);
        this.highlightOverlayView = overlayView;
        this.mainLayout.addView(overlayView);
        this.mainLayout.setOnTouchListener(this.keypadTouchListener);
        this.mainLayout.setOnClickListener(this.emptyClickListener);
        audioManager = (AudioManager) calculatorActivity.context.getSystemService("audio");
    }

    void initializeKeyBounds() {
        Arrays.fill(this.portraitKeyRects, null);
        Arrays.fill(this.landscapeKeyRects, null);
        CalculatorMode mode = this.calculatorActivity.getCalculatorMode();
        if (mode == CalculatorMode.CC_12CP) {
            initializeKeyBounds12CP();
        } else if (mode == CalculatorMode.CC_15C) {
            initializeKeyBounds15C();
        } else {
            initializeKeyBounds12C();
        }
    }

    private void initializeKeyBounds12CP() {
        if (!this.calculatorActivity.isLandscape()) {
            this.portraitLogoRect = scaleRect(270, 10, 310, 75);
            this.portraitKeyRects[0] = scaleRect(14, 328, 66, 443);
            this.portraitKeyRects[1] = scaleRect(14, 262, 66, 310);
            this.portraitKeyRects[2] = scaleRect(14, 129, 66, 177);
            this.portraitKeyRects[3] = scaleRect(74, 129, 126, 177);
            this.portraitKeyRects[4] = scaleRect(134, 129, 186, 177);
            this.portraitKeyRects[5] = scaleRect(255, 129, 307, 177);
            this.portraitKeyRects[6] = scaleRect(74, 196, 126, 244);
            this.portraitKeyRects[7] = scaleRect(74, 262, 126, 310);
            this.portraitKeyRects[8] = scaleRect(74, 328, 126, 376);
            this.portraitKeyRects[9] = scaleRect(74, 394, 126, 442);
            this.portraitKeyRects[10] = scaleRect(14, 196, 66, 244);
            this.portraitKeyRects[11] = scaleRect(134, 196, 186, 244);
            this.portraitKeyRects[12] = scaleRect(134, 262, 186, 310);
            this.portraitKeyRects[13] = scaleRect(134, 328, 186, 376);
            this.portraitKeyRects[14] = scaleRect(134, 394, 186, 442);
            this.portraitKeyRects[15] = scaleRect(195, 129, 247, 177);
            this.portraitKeyRects[16] = scaleRect(195, 196, 247, 244);
            this.portraitKeyRects[17] = scaleRect(195, 262, 247, 310);
            this.portraitKeyRects[18] = scaleRect(195, 328, 247, 376);
            this.portraitKeyRects[19] = scaleRect(195, 394, 247, 442);
            this.portraitKeyRects[20] = scaleRect(255, 196, 307, 244);
            this.portraitKeyRects[21] = scaleRect(255, 262, 307, 310);
            this.portraitKeyRects[22] = scaleRect(255, 328, 307, 376);
            this.portraitKeyRects[23] = scaleRect(255, 394, 307, 442);
        } else {
            this.landscapeLogoRect = scaleRect(410, 0, 470, 50);
            this.landscapeKeyRects[0] = scaleRect(22, 86, 59, 121);
            this.landscapeKeyRects[1] = scaleRect(22, 138, 59, 173);
            this.landscapeKeyRects[2] = scaleRect(22, 191, 59, 226);
            this.landscapeKeyRects[3] = scaleRect(22, 243, 59, 278);
            this.landscapeKeyRects[4] = scaleRect(66, 86, 103, 121);
            this.landscapeKeyRects[5] = scaleRect(66, 138, 103, 173);
            this.landscapeKeyRects[6] = scaleRect(66, 191, 103, 226);
            this.landscapeKeyRects[7] = scaleRect(66, 243, 103, 278);
            this.landscapeKeyRects[8] = scaleRect(110, 86, 148, 121);
            this.landscapeKeyRects[9] = scaleRect(110, 138, 148, 173);
            this.landscapeKeyRects[10] = scaleRect(110, 191, 148, 226);
            this.landscapeKeyRects[11] = scaleRect(110, 243, 148, 278);
            this.landscapeKeyRects[12] = scaleRect(155, 86, 192, 121);
            this.landscapeKeyRects[13] = scaleRect(155, 138, 192, 173);
            this.landscapeKeyRects[14] = scaleRect(155, 191, 192, 226);
            this.landscapeKeyRects[15] = scaleRect(155, 243, 192, 278);
            this.landscapeKeyRects[16] = scaleRect(199, 86, 236, 121);
            this.landscapeKeyRects[17] = scaleRect(199, 138, 236, 173);
            this.landscapeKeyRects[18] = scaleRect(199, 191, 236, 226);
            this.landscapeKeyRects[19] = scaleRect(199, 243, 236, 278);
            this.landscapeKeyRects[20] = scaleRect(243, 86, 280, 121);
            this.landscapeKeyRects[21] = scaleRect(243, 138, 280, 173);
            this.landscapeKeyRects[22] = scaleRect(243, 191, 280, 278);
            this.landscapeKeyRects[23] = scaleRect(287, 86, 324, 121);
            this.landscapeKeyRects[24] = scaleRect(287, 138, 324, 173);
            this.landscapeKeyRects[25] = scaleRect(287, 191, 324, 226);
            this.landscapeKeyRects[26] = scaleRect(287, 243, 324, 278);
            this.landscapeKeyRects[27] = scaleRect(331, 86, 368, 121);
            this.landscapeKeyRects[28] = scaleRect(331, 138, 368, 173);
            this.landscapeKeyRects[29] = scaleRect(331, 191, 368, 226);
            this.landscapeKeyRects[30] = scaleRect(331, 243, 368, 278);
            this.landscapeKeyRects[31] = scaleRect(376, 86, 413, 121);
            this.landscapeKeyRects[32] = scaleRect(376, 138, 413, 173);
            this.landscapeKeyRects[33] = scaleRect(376, 191, 413, 226);
            this.landscapeKeyRects[34] = scaleRect(376, 243, 413, 278);
            this.landscapeKeyRects[35] = scaleRect(419, 86, 456, 121);
            this.landscapeKeyRects[36] = scaleRect(419, 138, 456, 173);
            this.landscapeKeyRects[37] = scaleRect(419, 191, 456, 226);
            this.landscapeKeyRects[38] = scaleRect(419, 243, 456, 278);
        }
    }

    private void initializeKeyBounds15C() {
        if (!this.calculatorActivity.isLandscape()) {
            this.portraitLogoRect = scaleRect(270, 0, 310, 70);
            this.portraitKeyRects[0] = scaleRect(193, 127, 248, 178);
            this.portraitKeyRects[4] = scaleRect(133, 127, 188, 178);
            this.portraitKeyRects[5] = scaleRect(193, 392, 248, 443);
            this.portraitKeyRects[6] = scaleRect(72, 194, 127, 245);
            this.portraitKeyRects[7] = scaleRect(133, 194, 188, 245);
            this.portraitKeyRects[8] = scaleRect(193, 194, 248, 245);
            this.portraitKeyRects[9] = scaleRect(254, 194, 309, 245);
            this.portraitKeyRects[16] = scaleRect(72, 260, 127, 311);
            this.portraitKeyRects[17] = scaleRect(133, 260, 188, 311);
            this.portraitKeyRects[18] = scaleRect(193, 260, 248, 311);
            this.portraitKeyRects[19] = scaleRect(254, 260, 309, 311);
            this.portraitKeyRects[22] = scaleRect(12, 260, 67, 311);
            this.portraitKeyRects[23] = scaleRect(12, 194, 67, 245);
            this.portraitKeyRects[24] = scaleRect(254, 127, 309, 178);
            this.portraitKeyRects[25] = scaleRect(12, 326, 67, 445);
            this.portraitKeyRects[26] = scaleRect(72, 326, 127, 377);
            this.portraitKeyRects[27] = scaleRect(133, 326, 188, 377);
            this.portraitKeyRects[28] = scaleRect(193, 326, 248, 377);
            this.portraitKeyRects[29] = scaleRect(254, 326, 309, 377);
            this.portraitKeyRects[33] = scaleRect(12, 127, 67, 178);
            this.portraitKeyRects[34] = scaleRect(72, 127, 127, 178);
            this.portraitKeyRects[36] = scaleRect(72, 392, 127, 443);
            this.portraitKeyRects[37] = scaleRect(133, 392, 188, 443);
            this.portraitKeyRects[39] = scaleRect(254, 392, 309, 443);
        } else {
            initializeStandard40KeyLandscapeBounds();
        }
    }

    private void initializeKeyBounds12C() {
        if (!this.calculatorActivity.isLandscape()) {
            this.portraitLogoRect = scaleRect(270, 0, 310, 70);
            this.portraitKeyRects[5] = scaleRect(194, 388, 247, 449);
            this.portraitKeyRects[6] = scaleRect(73, 189, 126, 250);
            this.portraitKeyRects[7] = scaleRect(134, 189, 187, 250);
            this.portraitKeyRects[8] = scaleRect(194, 189, 247, 250);
            this.portraitKeyRects[9] = scaleRect(255, 189, 308, 250);
            this.portraitKeyRects[10] = scaleRect(194, 122, 247, 183);
            this.portraitKeyRects[11] = scaleRect(134, 122, 187, 183);
            this.portraitKeyRects[16] = scaleRect(73, 256, 126, 317);
            this.portraitKeyRects[17] = scaleRect(134, 256, 187, 317);
            this.portraitKeyRects[18] = scaleRect(194, 256, 247, 317);
            this.portraitKeyRects[19] = scaleRect(255, 256, 308, 317);
            this.portraitKeyRects[22] = scaleRect(13, 256, 66, 317);
            this.portraitKeyRects[23] = scaleRect(13, 189, 66, 250);
            this.portraitKeyRects[24] = scaleRect(255, 122, 308, 183);
            this.portraitKeyRects[25] = scaleRect(13, 322, 66, 451);
            this.portraitKeyRects[26] = scaleRect(73, 322, 126, 383);
            this.portraitKeyRects[27] = scaleRect(134, 322, 187, 383);
            this.portraitKeyRects[28] = scaleRect(194, 322, 247, 383);
            this.portraitKeyRects[29] = scaleRect(255, 322, 308, 383);
            this.portraitKeyRects[33] = scaleRect(13, 122, 66, 183);
            this.portraitKeyRects[34] = scaleRect(73, 122, 126, 183);
            this.portraitKeyRects[36] = scaleRect(73, 388, 126, 449);
            this.portraitKeyRects[37] = scaleRect(134, 388, 187, 449);
            this.portraitKeyRects[39] = scaleRect(255, 388, 308, 449);
        } else {
            initializeStandard40KeyLandscapeBounds();
        }
    }

    private void initializeStandard40KeyLandscapeBounds() {
        this.landscapeLogoRect = scaleRect(410, 0, 470, 50);
        this.landscapeKeyRects[0] = scaleRect(22, 86, 60, 121);
        this.landscapeKeyRects[1] = scaleRect(66, 86, 104, 121);
        this.landscapeKeyRects[2] = scaleRect(110, 86, 148, 121);
        this.landscapeKeyRects[3] = scaleRect(154, 86, 192, 121);
        this.landscapeKeyRects[4] = scaleRect(198, 86, 236, 121);
        this.landscapeKeyRects[5] = scaleRect(243, 86, 281, 121);
        this.landscapeKeyRects[6] = scaleRect(287, 86, 325, 121);
        this.landscapeKeyRects[7] = scaleRect(331, 86, 369, 121);
        this.landscapeKeyRects[8] = scaleRect(376, 86, 414, 121);
        this.landscapeKeyRects[9] = scaleRect(419, 86, 457, 121);
        this.landscapeKeyRects[10] = scaleRect(22, 139, 60, 174);
        this.landscapeKeyRects[11] = scaleRect(66, 139, 104, 174);
        this.landscapeKeyRects[12] = scaleRect(110, 139, 148, 174);
        this.landscapeKeyRects[13] = scaleRect(154, 139, 192, 174);
        this.landscapeKeyRects[14] = scaleRect(198, 139, 236, 174);
        this.landscapeKeyRects[15] = scaleRect(243, 139, 281, 174);
        this.landscapeKeyRects[16] = scaleRect(287, 139, 325, 174);
        this.landscapeKeyRects[17] = scaleRect(331, 139, 369, 174);
        this.landscapeKeyRects[18] = scaleRect(376, 139, 414, 174);
        this.landscapeKeyRects[19] = scaleRect(419, 139, 457, 174);
        this.landscapeKeyRects[20] = scaleRect(22, 191, 60, 226);
        this.landscapeKeyRects[21] = scaleRect(66, 191, 104, 226);
        this.landscapeKeyRects[22] = scaleRect(110, 191, 148, 226);
        this.landscapeKeyRects[23] = scaleRect(154, 191, 192, 226);
        this.landscapeKeyRects[24] = scaleRect(198, 191, 236, 226);
        this.landscapeKeyRects[25] = scaleRect(243, 191, 281, 278);
        this.landscapeKeyRects[26] = scaleRect(287, 191, 325, 226);
        this.landscapeKeyRects[27] = scaleRect(331, 191, 369, 226);
        this.landscapeKeyRects[28] = scaleRect(376, 191, 414, 226);
        this.landscapeKeyRects[29] = scaleRect(419, 191, 457, 226);
        this.landscapeKeyRects[30] = scaleRect(22, 243, 60, 278);
        this.landscapeKeyRects[31] = scaleRect(66, 243, 104, 278);
        this.landscapeKeyRects[32] = scaleRect(110, 243, 148, 278);
        this.landscapeKeyRects[33] = scaleRect(154, 243, 192, 278);
        this.landscapeKeyRects[34] = scaleRect(198, 243, 236, 278);
        this.landscapeKeyRects[36] = scaleRect(287, 243, 325, 278);
        this.landscapeKeyRects[37] = scaleRect(331, 243, 369, 278);
        this.landscapeKeyRects[38] = scaleRect(376, 243, 414, 278);
        this.landscapeKeyRects[39] = scaleRect(419, 243, 457, 278);
    }

    boolean isTouchInside(Rect rect) {
        if (rect == null) {
            return false;
        }
        if (this.touchX <= rect.left || this.touchX >= rect.right) {
            return false;
        }
        return this.touchY > ((float) rect.top) && this.touchY < ((float) rect.bottom);
    }

    public void detachOverlayView() {
        releaseActiveKey();
        this.mainLayout.removeView(this.highlightOverlayView);
        this.mainLayout = null;
    }

    private Rect getActiveKeyRect(int keyIndex) {
        if (keyIndex < 0) {
            return null;
        }
        Rect[] rects = this.calculatorActivity.isLandscape() ? this.landscapeKeyRects : this.portraitKeyRects;
        return keyIndex < rects.length ? rects[keyIndex] : null;
    }

    private void invalidateHighlightBounds() {
        if (this.highlightOverlayView == null) {
            return;
        }
        Rect dirtyRect = new Rect(
                (int) Math.floor(this.highlightedKeyBounds.left),
                (int) Math.floor(this.highlightedKeyBounds.top),
                (int) Math.ceil(this.highlightedKeyBounds.right),
                (int) Math.ceil(this.highlightedKeyBounds.bottom));
        this.highlightOverlayView.invalidate(dirtyRect);
    }

    private void showKeyPressedFeedback(Rect keyRect, int keyIndex) {
        this.highlightedKeyBounds.set(keyRect);
        this.activeKeyIndex = keyIndex;
        this.keyState = KeyState.PRESSED;
        invalidateHighlightBounds();
        playClickSound();
        triggerVibration();
    }

    private void clearKeyHighlight() {
        this.keyState = KeyState.RELEASED;
        invalidateHighlightBounds();
    }

    private void registerActiveKey() {
        if (this.activeKeyIndex != -1) {
            handleKeyDown();
            handleKeyUp();
            this.activeKeyIndex = -1;
        }
    }

    public void releaseActiveKey() {
        this.isLogoPressed = false;
        this.isHardwareKeyPressed = false;
        if (this.activeKeyIndex != -1) {
            this.activeKeyIndex = -1;
            clearKeyHighlight();
        }
    }

    void triggerVibration() {
        SettingsActivity.triggerVibrationForLevel(this.vibrator, this.calculatorActivity.vibrationLevel);
    }

    Rect scaleRect(int left, int top, int right, int bottom) {
        int scaledLeft;
        int scaledTop;
        int scaledRight;
        int scaledBottom;
        float density = this.displayMetrics.density;
        float densityDpi = this.displayMetrics.densityDpi;
        if (this.calculatorActivity.isLandscape()) {
            int layoutWidth = this.scaledLayoutWidth;
            int layoutHeight = this.scaledLayoutHeight;
            scaledLeft = (int) (((layoutWidth / densityDpi) / 3.0f) * left * density);
            double verticalScaleBase = (320 - this.statusBarHeightDp) / 160.0d;
            scaledTop = (int) (((layoutHeight / densityDpi) / verticalScaleBase) * (top * density));
            scaledRight = (int) (((layoutWidth / densityDpi) / 3.0f) * right * density);
            scaledBottom = (int) (((layoutHeight / densityDpi) / verticalScaleBase) * (bottom * density));
        } else {
            int layoutWidth = this.scaledLayoutWidth;
            int layoutHeight = this.scaledLayoutHeight;
            double verticalScaleBase = (480 - this.statusBarHeightDp) / 160.0d;
            scaledLeft = (int) (((layoutWidth / densityDpi) / 2.0f) * left * density);
            scaledTop = (int) (((layoutHeight / densityDpi) / verticalScaleBase) * (top * density));
            scaledRight = (int) (((layoutWidth / densityDpi) / 2.0f) * right * density);
            scaledBottom = (int) (((layoutHeight / densityDpi) / verticalScaleBase) * (bottom * density));
        }
        return new Rect(scaledLeft, scaledTop, scaledRight, scaledBottom);
    }

    private int map12CPKeyIndexToNativeCode(int keyIndex, boolean isLandscape) {
        if (!isLandscape) {
            if (keyIndex >= 0 && keyIndex < PORTRAIT_12CP_KEY_CODES.length) {
                return PORTRAIT_12CP_KEY_CODES[keyIndex];
            }
            return -1;
        }
        if (keyIndex == 3 || keyIndex < 0 || keyIndex > 38) {
            return -1;
        }
        return keyIndex < 23 ? (keyIndex + 10) : (keyIndex + 11);
    }

    public void handleKeyDown() {
        CalculatorMode mode = this.calculatorActivity.getCalculatorMode();
        if (mode == CalculatorMode.CC_12CP) {
            int nativeCode = map12CPKeyIndexToNativeCode(this.activeKeyIndex, this.calculatorActivity.isLandscape());
            if (nativeCode != -1) {
                this.calculatorActivity.getCalculationThread().enqueueKey(nativeCode);
                synchronized (this.calculatorActivity.getCalculationThread()) {
                    this.calculatorActivity.getCalculationThread().notify();
                }
            }
            return;
        }

        switch (this.activeKeyIndex) {
            case 9:
                this.calculatorActivity.getCalculationThread().enqueueKey(9);
                updateLayoutDimensions();
                break;
            case 10:
                if (mode == CalculatorMode.CC_12C && !this.calculatorActivity.isLandscape()) {
                    this.calculatorActivity.getCalculationThread().enqueueKey(32);
                    this.calculatorActivity.keyPressed(32, false);
                    synchronized (this.calculatorActivity.getCalculationThread()) {
                        this.calculatorActivity.getCalculationThread().notify();
                    }
                    this.calculatorActivity.keyPressed(32, true);
                }
                this.calculatorActivity.getCalculationThread().enqueueKey(10);
                break;
            case 30:
            case 35:
                break;
            default:
                if (this.activeKeyIndex >= 0 && this.activeKeyIndex <= 39) {
                    this.calculatorActivity.getCalculationThread().enqueueKey(this.activeKeyIndex);
                }
                break;
        }
        int keyIndex = this.activeKeyIndex;
        if (keyIndex != 30) {
            this.calculatorActivity.keyPressed(keyIndex, false);
        }
        synchronized (this.calculatorActivity.getCalculationThread()) {
            if (this.activeKeyIndex != 30) {
                this.calculatorActivity.getCalculationThread().notify();
            }
        }
    }

    public void handleKeyUp() {
        CalculatorMode mode = this.calculatorActivity.getCalculatorMode();
        if (mode == CalculatorMode.CC_12CP) {
            boolean isLandscape = this.calculatorActivity.isLandscape();
            if (isLandscape && this.activeKeyIndex == 3) {
                if (isTouchInside(this.landscapeKeyRects[3])) {
                    this.calculatorActivity.finish();
                }
                return;
            }
            int nativeCode = map12CPKeyIndexToNativeCode(this.activeKeyIndex, isLandscape);
            if (nativeCode != -1) {
                this.calculatorActivity.getCalculationThread().enqueueKey(nativeCode + 128);
                synchronized (this.calculatorActivity.getCalculationThread()) {
                    this.calculatorActivity.getCalculationThread().notify();
                }
            }
            return;
        }

        int keyIndex = this.activeKeyIndex;
        if (keyIndex != 30) {
            this.calculatorActivity.keyPressed(keyIndex, true);
            return;
        }
        if (isTouchInside(this.landscapeKeyRects[30])) {
            this.calculatorActivity.finish();
        }
    }

    void playClickSound() {
        if (this.calculatorActivity.isSoundEnabled) {
            audioManager.playSoundEffect(0, -1.0f);
        }
    }

    public void updateLayoutDimensions() {
        DisplayMetrics metrics = this.calculatorActivity.displayMetrics;
        int screenHeight = metrics.heightPixels;
        int screenWidth = metrics.widthPixels;
        int statusBarHeightPx = (int) (metrics.density * 25.0f);
        if (!this.calculatorActivity.isFullScreen) {
            screenHeight -= statusBarHeightPx;
        }
        CalculatorMode mode = this.calculatorActivity.getCalculatorMode();
        View landscapeLayout = this.calculatorActivity.findViewById(R.id.mainLayoutLandscape);
        View portraitLayout = this.calculatorActivity.findViewById(R.id.mainLayoutPortrait);
        if (landscapeLayout != null) {
            landscapeLayout.setBackgroundResource(mode.landscapeDrawableId);
        }
        if (portraitLayout != null) {
            portraitLayout.setBackgroundResource(mode.portraitDrawableId);
        }
        boolean isLandscape = this.calculatorActivity.isLandscape();
        View layoutView = isLandscape ? landscapeLayout : portraitLayout;
        Resources resources = this.calculatorActivity.getResources();
        int drawableId = isLandscape ? mode.landscapeDrawableId : mode.portraitDrawableId;
        BitmapDrawable backgroundDrawable = (BitmapDrawable) resources.getDrawable(drawableId);
        float bitmapWidth = backgroundDrawable.getBitmap().getWidth();
        float bitmapHeight = backgroundDrawable.getBitmap().getHeight();
        Log.d("ClassicCalc.Input", "bmapWidth:" + bitmapWidth);
        Log.d("ClassicCalc.Input", "bmapHeight:" + bitmapHeight);
        float widthScale = ((float) screenWidth) / bitmapWidth;
        float heightScale = ((float) screenHeight) / bitmapHeight;
        float scaleFactor = heightScale < widthScale ? heightScale : widthScale;
        int targetWidth = (int) (bitmapWidth * scaleFactor);
        int targetHeight = (int) (bitmapHeight * scaleFactor);
        layoutView.setLayoutParams(new FrameLayout.LayoutParams(targetWidth, targetHeight));
        this.scaledLayoutWidth = targetWidth;
        this.horizontalMargin = Math.max(0, (screenWidth - targetWidth) / 2);
        this.scaledLayoutHeight = targetHeight;
        this.verticalMargin = Math.max(0, (screenHeight - targetHeight) / 2);
        Log.d("ClassicCalc.Input", "mApplicationInstance.fullScreen:" + this.calculatorActivity.isFullScreen);
        Log.d("ClassicCalc.Input", "marginWidth:marginHeight" + this.horizontalMargin + ":" + this.verticalMargin);
        this.calculatorActivity.setCalculatorDimensions(this.scaledLayoutWidth, this.scaledLayoutHeight);
        if (layoutView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutView.getLayoutParams();
            Log.d("ClassicCalc.Input", "l, t, r, b : " + this.horizontalMargin + ", " + this.verticalMargin + ", " + this.horizontalMargin + ", " + this.verticalMargin);
            marginLayoutParams.setMargins(this.horizontalMargin, this.verticalMargin, this.horizontalMargin, this.verticalMargin);
            layoutView.requestLayout();
        }
    }
}

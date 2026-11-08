package org.liberty.android.classiccalc.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class LcdView extends View {
    private static String digitsText = "";
    private static String indicatorFlags = "";

    private Calculator calculatorActivity;
    private String lastRawLcdData;
    int digitSpacingWidth;
    int decimalPointOffsetX;
    int decimalPointOffsetY;
    int commaOffsetX;
    int commaOffsetY;
    private Point[] digitOriginPoints;
    private Point[] indicatorPoints;
    int calculatorWidth;
    int calculatorHeight;
    int statusBarHeightDp;
    int portraitVerticalOffset;
    Paint digitPaint;
    Paint indicatorPaint;
    Paint prgmBarPaint;
    String[] indicatorLabels;
    DisplayMetrics displayMetrics;
    boolean skipNextRunningSleep;

    public LcdView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.calculatorActivity = null;
        this.lastRawLcdData = "";
        this.digitOriginPoints = new Point[1];
        this.indicatorPoints = new Point[11];
        this.calculatorWidth = 0;
        this.calculatorHeight = 0;
        this.statusBarHeightDp = 25;
        this.portraitVerticalOffset = 12;
        this.digitPaint = new Paint();
        this.indicatorPaint = new Paint();
        this.prgmBarPaint = new Paint();
        this.indicatorLabels = new String[11];
        this.displayMetrics = new DisplayMetrics();
    }

    private void drawDigits(Canvas canvas) {
        int charIndex = 0;
        int digitSlot = 0;
        while (charIndex < digitsText.length() && digitSlot != 10) {
            char currentChar = digitsText.charAt(charIndex);
            if (currentChar == '.') {
                canvas.drawText(".", (this.digitSpacingWidth * digitSlot) + this.decimalPointOffsetX, this.decimalPointOffsetY, this.digitPaint);
                digitSlot++;
                charIndex++;
            } else if (currentChar == ';') {
                canvas.drawText(".", (this.digitSpacingWidth * digitSlot) + this.decimalPointOffsetX, this.decimalPointOffsetY, this.digitPaint);
                canvas.drawText(";", (this.digitSpacingWidth * digitSlot) + this.commaOffsetX, this.commaOffsetY, this.digitPaint);
                digitSlot++;
                charIndex++;
            } else {
                String digitStr = String.valueOf(digitsText.charAt(charIndex));
                canvas.drawText(digitStr, (this.digitSpacingWidth * digitSlot) + this.digitOriginPoints[0].x, this.digitOriginPoints[0].y, this.digitPaint);
                charIndex++;
                if (charIndex < digitsText.length()) {
                    char nextChar = digitsText.charAt(charIndex);
                    if (nextChar == '.') {
                        canvas.drawText(".", (this.digitSpacingWidth * digitSlot) + this.decimalPointOffsetX, this.decimalPointOffsetY, this.digitPaint);
                        digitSlot++;
                        charIndex++;
                    } else if (nextChar == ';') {
                        canvas.drawText(";", (this.digitSpacingWidth * digitSlot) + this.commaOffsetX, this.commaOffsetY, this.digitPaint);
                        digitSlot++;
                        charIndex++;
                    } else {
                        digitSlot++;
                    }
                }
            }
        }
    }

    int scaleHorizontal(int value) {
        DisplayMetrics metrics = this.displayMetrics;
        if (metrics.densityDpi == 0) {
            return 0;
        }
        float orientationDivisor = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3.0f : 2.0f;
        return (int) (((((float) this.calculatorWidth) / ((float) metrics.densityDpi)) / orientationDivisor) * ((float) value) * metrics.density);
    }

    public int scaleVertical(int value) {
        DisplayMetrics metrics = this.displayMetrics;
        if (metrics.densityDpi == 0) {
            return 0;
        }
        float density = metrics.density;
        float densityDpi = metrics.densityDpi;
        double baseHeightDp = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 320 - this.statusBarHeightDp : 480 - this.statusBarHeightDp;
        double scaledValue = value * density;
        double heightInInches = this.calculatorHeight / densityDpi;
        return (int) ((heightInInches / (baseHeightDp / 160.0d)) * scaledValue);
    }

    public void initializeLayout(Calculator activity) {
        this.calculatorActivity = activity;
        this.lastRawLcdData = "";
        activity.getWindowManager().getDefaultDisplay().getMetrics(this.displayMetrics);
        this.calculatorHeight = this.calculatorActivity.getCalculatorHeight();
        this.calculatorWidth = this.calculatorActivity.getCalculatorWidth();
        boolean is12CP = this.calculatorActivity.getCalculatorMode() == CalculatorMode.CC_12CP;
        this.portraitVerticalOffset = is12CP ? 5 : 12;

        Typeface lcdFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/calc_font.ttf");
        this.digitPaint.setTypeface(lcdFont);
        this.digitPaint.setTextSize(scaleHorizontal(29));
        this.digitPaint.setAntiAlias(true);
        this.digitPaint.setColor(Color.rgb(40, 40, 40));
        this.digitPaint.setShadowLayer(2.0f, 1.0f, 1.0f, Color.rgb(100, 100, 100));

        this.prgmBarPaint.setTypeface(lcdFont);
        this.prgmBarPaint.setTextSize(scaleHorizontal(40));
        this.prgmBarPaint.setAntiAlias(true);
        this.prgmBarPaint.setColor(Color.rgb(40, 40, 40));

        this.indicatorPaint.setTextSize(scaleHorizontal(7));
        this.indicatorPaint.setAntiAlias(true);
        this.indicatorPaint.setShadowLayer(1.0f, 1.0f, 1.0f, Color.rgb(100, 100, 100));

        int horizontalPadding = Math.max(0, scaleHorizontal(0));
        for (int index = 0; index < 1; index++) {
            this.digitOriginPoints[index] = new Point();
        }
        for (int index = 0; index < 11; index++) {
            this.indicatorPoints[index] = new Point();
        }

        if (is12CP) {
            this.indicatorLabels[0] = "-";
            this.indicatorLabels[1] = "|";
            this.indicatorLabels[2] = "RPN";
            this.indicatorLabels[3] = "ALG";
            this.indicatorLabels[4] = "( )";
            this.indicatorLabels[5] = "f";
            this.indicatorLabels[6] = "g";
            this.indicatorLabels[7] = "BEGIN";
            this.indicatorLabels[8] = "D.MY";
            this.indicatorLabels[9] = "C";
            this.indicatorLabels[10] = "PRGM";

            if (this.calculatorActivity.isLandscape()) {
                this.digitSpacingWidth = scaleHorizontal(21);
                this.decimalPointOffsetX = scaleHorizontal(31);
                this.decimalPointOffsetY = scaleVertical(35);
                this.commaOffsetX = scaleHorizontal(31);
                this.commaOffsetY = scaleVertical(35);
                this.digitOriginPoints[0].set(scaleHorizontal(17) + horizontalPadding, scaleVertical(35));

                this.indicatorPoints[0].set(scaleHorizontal(1), scaleVertical(35));
                this.indicatorPoints[1].set(scaleHorizontal(1), scaleVertical(45));
                this.indicatorPoints[2].set(scaleHorizontal(13), scaleVertical(47));
                this.indicatorPoints[3].set(scaleHorizontal(36), scaleVertical(47));
                this.indicatorPoints[4].set(scaleHorizontal(59), scaleVertical(47));
                this.indicatorPoints[5].set(scaleHorizontal(82), scaleVertical(47));
                this.indicatorPoints[6].set(scaleHorizontal(105), scaleVertical(46));
                this.indicatorPoints[7].set(scaleHorizontal(128), scaleVertical(47));
                this.indicatorPoints[8].set(scaleHorizontal(162), scaleVertical(47));
                this.indicatorPoints[9].set(scaleHorizontal(186), scaleVertical(47));
                this.indicatorPoints[10].set(scaleHorizontal(197), scaleVertical(47));
            } else {
                int verticalOffset = this.portraitVerticalOffset;
                int digitBaseYOffset = verticalOffset - 1;
                int indicatorBaseYOffset = verticalOffset + 6;
                this.digitSpacingWidth = scaleHorizontal(22);
                this.decimalPointOffsetX = scaleHorizontal(31) + horizontalPadding;
                this.decimalPointOffsetY = scaleVertical(verticalOffset + 33);
                this.commaOffsetX = scaleHorizontal(31) + horizontalPadding;
                this.commaOffsetY = scaleVertical(verticalOffset + 33);
                int originX = scaleHorizontal(-5 + 22) + horizontalPadding;
                this.digitOriginPoints[0].set(originX + horizontalPadding, scaleVertical(digitBaseYOffset + 34));

                int adjustedIndicatorBaseY = indicatorBaseYOffset - 2;
                this.indicatorPoints[0].set(scaleHorizontal(1), scaleVertical(verticalOffset + 33));
                this.indicatorPoints[1].set(scaleHorizontal(1), scaleVertical(verticalOffset + 43));
                int portraitIndicatorRowY = adjustedIndicatorBaseY + 42;
                this.indicatorPoints[2].set(scaleHorizontal(23), scaleVertical(portraitIndicatorRowY));
                this.indicatorPoints[3].set(scaleHorizontal(46), scaleVertical(portraitIndicatorRowY));
                this.indicatorPoints[4].set(scaleHorizontal(69), scaleVertical(portraitIndicatorRowY));
                this.indicatorPoints[5].set(scaleHorizontal(92), scaleVertical(portraitIndicatorRowY));
                this.indicatorPoints[6].set(scaleHorizontal(115), scaleVertical(adjustedIndicatorBaseY + 41));
                this.indicatorPoints[7].set(scaleHorizontal(138), scaleVertical(portraitIndicatorRowY));
                this.indicatorPoints[8].set(scaleHorizontal(172), scaleVertical(portraitIndicatorRowY));
                this.indicatorPoints[9].set(scaleHorizontal(196), scaleVertical(portraitIndicatorRowY));
                this.indicatorPoints[10].set(scaleHorizontal(207), scaleVertical(portraitIndicatorRowY));
            }
            return;
        }

        int digitBaseYOffset;
        int indicatorBaseYOffset;
        int digitBaseXOffset;
        int originX;
        Point originPoint;
        Point prgmIndicatorPoint;
        int prgmIndicatorX;
        int indicatorRowY;
        if (this.calculatorActivity.isLandscape()) {
            this.digitSpacingWidth = scaleHorizontal(21);
            this.decimalPointOffsetX = scaleHorizontal(45);
            this.decimalPointOffsetY = scaleVertical(36);
            this.commaOffsetX = scaleHorizontal(45);
            this.commaOffsetY = scaleVertical(36);
            digitBaseXOffset = 11;
            indicatorBaseYOffset = 10;
            digitBaseYOffset = 5;
        } else {
            int verticalOffset = this.portraitVerticalOffset;
            digitBaseYOffset = verticalOffset - 1;
            indicatorBaseYOffset = verticalOffset + 6;
            this.digitSpacingWidth = scaleHorizontal(22);
            this.decimalPointOffsetX = scaleHorizontal(31) + horizontalPadding;
            this.decimalPointOffsetY = scaleVertical(this.portraitVerticalOffset + 30);
            this.commaOffsetX = scaleHorizontal(31) + horizontalPadding;
            this.commaOffsetY = scaleVertical(this.portraitVerticalOffset + 30);
            digitBaseXOffset = -5;
        }
        this.indicatorLabels[0] = "-";
        this.indicatorLabels[1] = "*";
        this.indicatorLabels[2] = "USER";
        this.indicatorLabels[3] = "f";
        this.indicatorLabels[4] = "g";
        this.indicatorLabels[5] = "BEGIN";
        this.indicatorLabels[6] = "G";
        this.indicatorLabels[7] = "RAD";
        this.indicatorLabels[8] = "D.MY";
        this.indicatorLabels[9] = "C";
        this.indicatorLabels[10] = "PRGM";
        if (this.calculatorActivity.isLandscape()) {
            originPoint = this.digitOriginPoints[0];
            originX = scaleHorizontal(digitBaseXOffset + 20);
        } else {
            originPoint = this.digitOriginPoints[0];
            originX = scaleHorizontal(digitBaseXOffset + 22) + horizontalPadding;
        }
        originPoint.set(originX + horizontalPadding, scaleVertical(digitBaseYOffset + 31));
        if (this.calculatorActivity.isLandscape()) {
            this.indicatorPoints[0].set(scaleHorizontal(17), scaleVertical(36));
            this.indicatorPoints[1].set(scaleHorizontal(16), scaleVertical(30));
            indicatorRowY = indicatorBaseYOffset + 39;
            this.indicatorPoints[2].set(scaleHorizontal(23), scaleVertical(indicatorRowY));
            this.indicatorPoints[3].set(scaleHorizontal(65), scaleVertical(indicatorBaseYOffset + 40));
            this.indicatorPoints[4].set(scaleHorizontal(72), scaleVertical(indicatorRowY));
            this.indicatorPoints[5].set(scaleHorizontal(94), scaleVertical(indicatorRowY));
            this.indicatorPoints[6].set(scaleHorizontal(128), scaleVertical(indicatorRowY));
            this.indicatorPoints[7].set(scaleHorizontal(138), scaleVertical(indicatorRowY));
            this.indicatorPoints[8].set(scaleHorizontal(166), scaleVertical(indicatorRowY));
            this.indicatorPoints[9].set(scaleHorizontal(194), scaleVertical(indicatorRowY));
            prgmIndicatorPoint = this.indicatorPoints[10];
            prgmIndicatorX = 212;
        } else {
            int adjustedIndicatorBaseY = indicatorBaseYOffset - 2;
            this.indicatorPoints[0].set(scaleHorizontal(4), scaleVertical(this.portraitVerticalOffset + 30));
            this.indicatorPoints[1].set(3, scaleVertical(this.portraitVerticalOffset + 20));
            int portraitIndicatorRowY = adjustedIndicatorBaseY + 39;
            this.indicatorPoints[2].set(scaleHorizontal(23), scaleVertical(portraitIndicatorRowY));
            this.indicatorPoints[3].set(scaleHorizontal(46), scaleVertical(portraitIndicatorRowY));
            int shiftIndicatorRowY = adjustedIndicatorBaseY + 40;
            this.indicatorPoints[4].set(scaleHorizontal(79), scaleVertical(shiftIndicatorRowY));
            this.indicatorPoints[5].set(scaleHorizontal(95), scaleVertical(shiftIndicatorRowY));
            this.indicatorPoints[6].set(scaleHorizontal(100), scaleVertical(portraitIndicatorRowY));
            this.indicatorPoints[7].set(scaleHorizontal(130), scaleVertical(portraitIndicatorRowY));
            this.indicatorPoints[8].set(scaleHorizontal(165), scaleVertical(portraitIndicatorRowY));
            this.indicatorPoints[9].set(scaleHorizontal(195), scaleVertical(portraitIndicatorRowY));
            prgmIndicatorPoint = this.indicatorPoints[10];
            prgmIndicatorX = 206;
            indicatorRowY = portraitIndicatorRowY;
        }
        prgmIndicatorPoint.set(scaleHorizontal(prgmIndicatorX), scaleVertical(indicatorRowY));
    }

    public void updateDisplayText(String rawLcdText) {
        if (rawLcdText == null || rawLcdText.length() < 11 || this.lastRawLcdData.equals(rawLcdText)) {
            return;
        }
        this.lastRawLcdData = rawLcdText;
        int digitsEndIndex = rawLcdText.length() - 11;
        indicatorFlags = this.lastRawLcdData.substring(digitsEndIndex);
        String digitsPortion = this.lastRawLcdData.substring(0, digitsEndIndex);
        digitsText = digitsPortion;
        String trimmedDigits = digitsPortion.trim();
        if (trimmedDigits.equalsIgnoreCase("runnin9") || trimmedDigits.equalsIgnoreCase("running")) {
            this.calculatorActivity.setRunningProgram(true);
            this.skipNextRunningSleep = true;
        } else {
            this.calculatorActivity.setRunningProgram(false);
        }
        invalidate();
    }

    @Override
    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.calculatorActivity != null) {
            Log.d("ClassicCalc.LcdView", "Received data: " + digitsText);
            String formattedDigits = digitsText.replace(',', ';');
            digitsText = formattedDigits;
            digitsText = (!formattedDigits.contains("r") || this.calculatorActivity.isRunningProgram())
                    ? digitsText.replace('R', 'r').replace('G', '9').replace('g', '9')
                    : digitsText.replace('r', 'R');
            digitsText = digitsText.replace('U', 'u').replace('I', 'i').replace('N', 'n');
            Log.d("ClassicCalc.LcdView", "Display on LCD: " + digitsText);
            boolean isRunning = this.calculatorActivity.isRunningProgram();
            drawDigits(canvas);
            if (isRunning) {
                try {
                    if (!this.skipNextRunningSleep) {
                        Thread.sleep(500L);
                    }
                    this.skipNextRunningSleep = false;
                } catch (InterruptedException exception) {
                    Log.w("ClassicCalc.LcdView", exception);
                }
                if (this.calculatorActivity.getSavedRunningLcdText() == null) {
                    this.calculatorActivity.setSavedRunningLcdText(digitsText);
                }
                digitsText = digitsText.length() == 0 ? this.calculatorActivity.getSavedRunningLcdText() : "";
                invalidate();
            }
            if (indicatorFlags.length() == 11) {
                boolean is12CP = this.calculatorActivity.getCalculatorMode() == CalculatorMode.CC_12CP;
                for (int index = 0; index < 11; index++) {
                    if (indicatorFlags.charAt(index) == '1') {
                        Paint activePaint;
                        if (index == 0) {
                            activePaint = this.digitPaint;
                        } else if (index == 1 && is12CP) {
                            activePaint = this.prgmBarPaint;
                        } else {
                            activePaint = this.indicatorPaint;
                        }
                        Point position = this.indicatorPoints[index];
                        canvas.drawText(this.indicatorLabels[index], position.x, position.y, activePaint);
                    }
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(scaleHorizontal(270), scaleVertical(60));
    }
}

package org.liberty.android.classiccalc.ui;

import android.util.Log;

class CalculationThread extends Thread {

    public boolean isExecuting;
    protected Calculator calculatorActivity;
    private volatile int[] keyBuffer = new int[16];
    private volatile char writeIndex = 0;
    private volatile char readIndex = 0;

    CalculationThread(Calculator calculatorActivity) {
        this.calculatorActivity = calculatorActivity;
        Log.d("ClassicCalc.CalculationThread", "Initializing Calculation Thread.");
    }

    void enqueueKey(int keyCode) {
        Log.d("ClassicCalc.CalculationThread", "Adding KeyCode To Buffer:" + keyCode);
        char currentReadIndex = this.readIndex;
        char currentWriteIndex = this.writeIndex;
        if (currentWriteIndex != currentReadIndex - 1) {
            if (currentWriteIndex == 16 && currentReadIndex == 0) {
                return;
            }
            this.keyBuffer[currentWriteIndex] = keyCode;
            int nextWriteIndex = currentWriteIndex + 1;
            if (nextWriteIndex == 16) {
                nextWriteIndex = 0;
            }
            this.writeIndex = (char) nextWriteIndex;
        }
    }

    boolean isBufferEmpty() {
        return this.readIndex == this.writeIndex;
    }

    int dequeueKey() {
        char currentReadIndex = this.readIndex;
        if (currentReadIndex == this.writeIndex) {
            return -1;
        }
        char nextReadIndex = (char) (currentReadIndex + 1);
        int keyCode = this.keyBuffer[currentReadIndex];
        if (nextReadIndex == 16) {
            nextReadIndex = 0;
        }
        this.readIndex = nextReadIndex;
        return keyCode;
    }

    int peekKey() {
        if (isBufferEmpty()) {
            return -1;
        }
        return this.keyBuffer[this.readIndex];
    }

    void clearBuffer() {
        this.writeIndex = (char) 0;
        this.readIndex = (char) 0;
    }

    @Override
    public void run() {
        while (this.calculatorActivity.isCalculationThreadRunning) {
            synchronized (this) {
                try {
                    wait();
                    if (!this.calculatorActivity.isCalculationThreadRunning) {
                        break;
                    }
                } catch (InterruptedException exception) {
                    Log.w("ClassicCalc.CalculationThread", "Error in Thread", exception);
                }
            }
            if (this.calculatorActivity.getCalculatorMode() == CalculatorMode.CC_12CP) {
                while (this.calculatorActivity.isCalculationThreadRunning && !isBufferEmpty()) {
                    int rawCode = dequeueKey();
                    if (rawCode != -1) {
                        boolean isKeyUp = (rawCode & 128) > 0;
                        int keyCode = isKeyUp ? (rawCode - 128) : rawCode;
                        this.isExecuting = true;
                        String lcdText = this.calculatorActivity.executeKeyPressed12CP(keyCode, isKeyUp);
                        this.calculatorActivity.updateLcdFromNative(lcdText);
                        this.isExecuting = false;
                    }
                }
            } else {
                while (this.calculatorActivity.isCalculationThreadRunning && !isBufferEmpty()) {
                    if ((isBufferEmpty() ? -1 : this.keyBuffer[this.readIndex]) != -1) {
                        this.isExecuting = true;
                        this.calculatorActivity.executeCalcCycle();
                        this.isExecuting = false;
                    }
                }
            }
        }
        super.run();
    }
}

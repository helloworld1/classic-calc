package org.liberty.android.classiccalc.engine;

/**
 * Public entry point for the 12C Platinum calculator engine.
 */
public final class CalcEngine extends KeyHandlerEngine {
    public CalcEngine(Callback callback, String stateFilePath) {
        initMemory(callback, stateFilePath);
    }

    public synchronized String keyPressed(int key, byte isDown) {
        sysMainLoop((long) key, (isDown != 0) ? 1L : 0L);
        return getResultPlusStatus();
    }

    public synchronized String testKeyPressed() {
        return "";
    }

    public synchronized String resetCalc() {
        putI64(g_StatusIndicators, 0x3030303030303030L);
        putI32(g_StatusIndicators + 8L, 0x00303030L);
        sysInit();
        sysMainLoop(0x1cL, 0L);
        return getResultPlusStatus();
    }

    public synchronized String swapDecimalSep() {
        putU32(g_com_decimal_mode, (getU32(g_com_decimal_mode) == 0L) ? 1L : 0L);
        int savedSp = sp;
        long framePtr = allocFrame(32);
        try {
            CC_Real x = Stack_GetX();
            putCCReal(framePtr, x);
            Basic_PrintResult(framePtr);
        } finally {
            sp = savedSp;
        }
        return getResultPlusStatus();
    }

    public synchronized String restoreCalcData() {
        if (!isFileRead && getU32(g_once) == 0L) {
            putI32(g_StatusIndicators + 8L, 0x00303030L);
            putI64(g_StatusIndicators, 0x3030303030303030L);
            putU32(g_once, 1L);
            sysInit();
            sysMainLoop(0x1cL, 0L);
        }
        return getResultPlusStatus();
    }

    public synchronized int saveCalcData() {
        return writeStateFile();
    }

}

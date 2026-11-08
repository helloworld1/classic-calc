package org.liberty.android.classiccalc.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;

/**
 * End-to-end integration tests for {@link CalcEngine} (CC 12C Platinum) exercising
 * keystroke handling across RPN mode, Algebraic (ALG) mode, TVM financial workflows,
 * Cash Flow (NPV/IRR), Statistics, Date calculations, STO/RCL register arithmetic,
 * keystroke programming, decimal separator swap, and state file persistence.
 */
public class CalcEngineIntegrationTest {

    // CC 12CP key codes (from KeyHandlerEngine / Keypad mapping)
    private static final int KEY_N = 10;
    private static final int KEY_YX = 11;
    private static final int KEY_RS = 12;
    private static final int KEY_I = 14;
    private static final int KEY_INVX = 15;
    private static final int KEY_SST = 16;
    private static final int KEY_F = 17;
    private static final int KEY_PV = 18;
    private static final int KEY_PCT_T = 19;
    private static final int KEY_RDN = 20;
    private static final int KEY_G = 21;
    private static final int KEY_PMT = 22;
    private static final int KEY_DELTA_PCT = 23;
    private static final int KEY_XY = 24;
    private static final int KEY_STO = 25;
    private static final int KEY_FV = 26;
    private static final int KEY_PCT = 27;
    private static final int KEY_CLX = 28;
    private static final int KEY_RCL = 29;
    private static final int KEY_CHS = 30;
    private static final int KEY_EEX = 31;
    private static final int KEY_ENTER = 32;
    private static final int KEY_7 = 34;
    private static final int KEY_4 = 35;
    private static final int KEY_1 = 36;
    private static final int KEY_0 = 37;
    private static final int KEY_8 = 38;
    private static final int KEY_5 = 39;
    private static final int KEY_2 = 40;
    private static final int KEY_DOT = 41;
    private static final int KEY_9 = 42;
    private static final int KEY_6 = 43;
    private static final int KEY_3 = 44;
    private static final int KEY_SIGMA = 45;
    private static final int KEY_DIV = 46;
    private static final int KEY_MUL = 47;
    private static final int KEY_SUB = 48;
    private static final int KEY_ADD = 49;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    private CalcEngine engine;
    private File stateFile;

    @Before
    public void setUp() throws Exception {
        stateFile = tempFolder.newFile("state12cp.dat");
        stateFile.delete();

        CalcMemory.Callback callback = new CalcMemory.Callback() {
            @Override
            public int handleKeybuffer(int op) {
                return -1;
            }

            @Override
            public void updateLcd(String display) {
            }
        };

        engine = new CalcEngine(callback, stateFile.getAbsolutePath());
        engine.resetCalc();
    }

    private void pressKey(int keyCode) {
        engine.keyPressed(keyCode, (byte) 0);
        engine.keyPressed(keyCode, (byte) 1);
    }

    private void pressKeys(int... keyCodes) {
        for (int keyCode : keyCodes) {
            pressKey(keyCode);
        }
    }

    private String getDisplayDigits() {
        String raw = engine.getResultPlusStatus();
        assertNotNull(raw);
        assertTrue(raw.length() >= 11);
        return raw.substring(0, raw.length() - 11).trim();
    }

    private String getIndicators() {
        String raw = engine.getResultPlusStatus();
        assertNotNull(raw);
        assertTrue(raw.length() >= 11);
        return raw.substring(raw.length() - 11);
    }

    private double getDisplayDouble() {
        String digits = getDisplayDigits().replace(",", "").replace(" ", "");
        double val = Double.parseDouble(digits);
        return getIndicators().charAt(0) == '1' ? -val : val;
    }

    @Test
    public void testInitialResetAndRpnIndicator() {
        assertEquals("0.00", getDisplayDigits());
        // On 12CP, indicator index 2 is RPN ('1' by default) and index 3 is ALG ('0')
        assertEquals('1', getIndicators().charAt(2));
        assertEquals('0', getIndicators().charAt(3));
    }

    @Test
    public void testRpnArithmeticAndScientificKeys() {
        // (15 + 25) * 3 / 4 = 30.00
        pressKeys(KEY_1, KEY_5, KEY_ENTER, KEY_2, KEY_5, KEY_ADD, KEY_3, KEY_MUL, KEY_4, KEY_DIV);
        assertEquals(30.00, getDisplayDouble(), 1e-6);

        // 1/x of 4 = 0.25
        pressKeys(KEY_4, KEY_INVX);
        assertEquals(0.25, getDisplayDouble(), 1e-6);

        // y^x: 3^4 = 81.00
        pressKeys(KEY_3, KEY_ENTER, KEY_4, KEY_YX);
        assertEquals(81.00, getDisplayDouble(), 1e-6);

        // g sqrt(x): sqrt(81) = 9.00 (g + y^x)
        pressKeys(KEY_G, KEY_YX);
        assertEquals(9.00, getDisplayDouble(), 1e-6);

        // g x^2: 9^2 = 81.00 (g + MUL)
        pressKeys(KEY_G, KEY_MUL);
        assertEquals(81.00, getDisplayDouble(), 1e-6);

        // g n!: 5! = 120.00 (g + 3)
        pressKeys(KEY_5, KEY_G, KEY_3);
        assertEquals(120.00, getDisplayDouble(), 1e-6);
    }

    @Test
    public void testPercentageKeys() {
        // 200 ENTER 15 % -> 30.00 (15% of 200)
        pressKeys(KEY_2, KEY_0, KEY_0, KEY_ENTER, KEY_1, KEY_5, KEY_PCT);
        assertEquals(30.00, getDisplayDouble(), 1e-6);

        // Δ%: from 80 to 100 is +25.00%
        pressKeys(KEY_8, KEY_0, KEY_ENTER, KEY_1, KEY_0, KEY_0, KEY_DELTA_PCT);
        assertEquals(25.00, getDisplayDouble(), 1e-6);

        // %T: 50 as % of 250 is 20.00%
        pressKeys(KEY_2, KEY_5, KEY_0, KEY_ENTER, KEY_5, KEY_0, KEY_PCT_T);
        assertEquals(20.00, getDisplayDouble(), 1e-6);
    }

    @Test
    public void testAlgebraicModeWithParentheses() {
        // Switch to ALG mode: f + EEX (KEY_EEX = 31)
        pressKeys(KEY_F, KEY_EEX);
        assertEquals('1', getIndicators().charAt(3)); // ALG indicator on
        assertEquals('0', getIndicators().charAt(2)); // RPN indicator off

        // In ALG mode: 2 * ( 3 + 4 ) = 14.00
        // '(' is g + STO (25), ')' is g + RCL (29), '=' is ENTER (32)
        pressKeys(KEY_2, KEY_MUL, KEY_G, KEY_STO, KEY_3, KEY_ADD, KEY_4, KEY_G, KEY_RCL, KEY_ENTER);
        assertEquals(14.00, getDisplayDouble(), 1e-6);

        // Switch back to RPN mode: f + CHS (KEY_CHS = 30)
        pressKeys(KEY_F, KEY_CHS);
        assertEquals('0', getIndicators().charAt(3));
        assertEquals('1', getIndicators().charAt(2));
    }

    @Test
    public void testRegisterStorageAndArithmetic() {
        // Store 50 in R2
        pressKeys(KEY_5, KEY_0, KEY_STO, KEY_2);
        // Add 25 to R2 using STO + 2
        pressKeys(KEY_2, KEY_5, KEY_STO, KEY_ADD, KEY_2);
        // Multiply R2 by 2 using STO * 2 -> 150
        pressKeys(KEY_2, KEY_STO, KEY_MUL, KEY_2);
        // Clear X and recall R2
        pressKey(KEY_CLX);
        assertEquals(0.00, getDisplayDouble(), 1e-6);

        pressKeys(KEY_RCL, KEY_2);
        assertEquals(150.00, getDisplayDouble(), 1e-6);
    }

    @Test
    public void testFinancialTvmCalculation() {
        // Clear FIN: f + PMT (KEY_PMT = 22)
        pressKeys(KEY_F, KEY_PMT);

        // N = 360, i = 0.5, PV = 100000, FV = 0 -> solve PMT = -599.55
        pressKeys(KEY_3, KEY_6, KEY_0, KEY_N);
        pressKeys(KEY_DOT, KEY_5, KEY_I);
        pressKeys(KEY_1, KEY_0, KEY_0, KEY_0, KEY_0, KEY_0, KEY_PV);
        pressKeys(KEY_0, KEY_FV);
        pressKey(KEY_PMT);

        assertEquals(-599.55, getDisplayDouble(), 0.01);
    }

    @Test
    public void testCashFlowNpvAndIrr() {
        // Clear REG: f + CLx (key 28) clears all registers
        pressKeys(KEY_F, KEY_CLX);

        // Initial investment CF0 = -1000: 1000 CHS g CF0 (g + PV)
        pressKeys(KEY_1, KEY_0, KEY_0, KEY_0, KEY_CHS, KEY_G, KEY_PV);
        // Cash flow 1: CF1 = 600: 600 g CFj (g + PMT)
        pressKeys(KEY_6, KEY_0, KEY_0, KEY_G, KEY_PMT);
        // Cash flow 2: CF2 = 600: 600 g CFj (g + PMT)
        pressKeys(KEY_6, KEY_0, KEY_0, KEY_G, KEY_PMT);
        // Interest rate i = 10%: 10 i
        pressKeys(KEY_1, KEY_0, KEY_I);

        // Compute NPV: f NPV (f + PV) -> -1000 + 600/1.1 + 600/1.21 = 41.32
        pressKeys(KEY_F, KEY_PV);
        assertEquals(41.32, getDisplayDouble(), 0.01);

        // Compute IRR: f IRR (f + FV) -> ~13.07%
        pressKeys(KEY_F, KEY_FV);
        assertEquals(13.07, getDisplayDouble(), 0.01);
    }

    @Test
    public void testStatisticsMeanAndStdDev() {
        // Clear statistics: f + SST (CLEAR Σ)
        pressKeys(KEY_F, KEY_SST);

        // Enter pairs (y, x): (2, 10), (4, 20), (6, 30)
        pressKeys(KEY_2, KEY_ENTER, KEY_1, KEY_0, KEY_SIGMA);
        assertEquals(1.00, getDisplayDouble(), 1e-6);

        pressKeys(KEY_4, KEY_ENTER, KEY_2, KEY_0, KEY_SIGMA);
        assertEquals(2.00, getDisplayDouble(), 1e-6);

        pressKeys(KEY_6, KEY_ENTER, KEY_3, KEY_0, KEY_SIGMA);
        assertEquals(3.00, getDisplayDouble(), 1e-6);

        // Mean x̄ (g + 0): X = 20.00, Y = 4.00
        pressKeys(KEY_G, KEY_0);
        assertEquals(20.00, getDisplayDouble(), 1e-6);
        pressKey(KEY_XY);
        assertEquals(4.00, getDisplayDouble(), 1e-6);

        // Sample standard deviation s (g + .): sx = 10.00, sy = 2.00
        pressKeys(KEY_G, KEY_DOT);
        assertEquals(10.00, getDisplayDouble(), 1e-6);
        pressKey(KEY_XY);
        assertEquals(2.00, getDisplayDouble(), 1e-6);
    }

    @Test
    public void testDateCalculations() {
        // ΔDYS (g + EEX) in M.DY mode (default):
        // From 01.012024 (Jan 1, 2024) to 03.012024 (Mar 1, 2024, leap year) = 60 days
        pressKeys(KEY_1, KEY_DOT, KEY_0, KEY_1, KEY_2, KEY_0, KEY_2, KEY_4, KEY_ENTER);
        pressKeys(KEY_3, KEY_DOT, KEY_0, KEY_1, KEY_2, KEY_0, KEY_2, KEY_4, KEY_G, KEY_EEX);
        assertEquals(60.00, getDisplayDouble(), 1e-6);
    }

    @Test
    public void testProgramRecordingAndExecution() {
        // Enter Program mode: f P/R (f + R/S)
        pressKeys(KEY_F, KEY_RS);
        // On 12CP, indicator index 10 is PRGM ('1')
        assertEquals('1', getIndicators().charAt(10));

        // Clear program memory: f PRGM (f + R↓)
        pressKeys(KEY_F, KEY_RDN);

        // Record program to compute 2 * X: ENTER, +
        pressKeys(KEY_ENTER, KEY_ADD);

        // Exit Program mode: f P/R (f + R/S)
        pressKeys(KEY_F, KEY_RS);
        assertEquals('0', getIndicators().charAt(10));

        // Enter 21 and run program via R/S -> 42.00
        pressKeys(KEY_2, KEY_1, KEY_RS);
        assertEquals(42.00, getDisplayDouble(), 1e-6);
    }

    @Test
    public void testDecimalSeparatorSwapAndStatePersistence() {
        pressKeys(KEY_1, KEY_2, KEY_3, KEY_4, KEY_DOT, KEY_5, KEY_6, KEY_ENTER);
        assertEquals("1,234.56", getDisplayDigits());

        engine.swapDecimalSep();
        assertEquals("1.234,56", getDisplayDigits());

        engine.swapDecimalSep();
        assertEquals("1,234.56", getDisplayDigits());

        // Save state to disk
        engine.saveCalcData();
        assertTrue(stateFile.exists());

        // Create a new CalcEngine instance pointing to the saved stateFile and restore
        CalcEngine restoredEngine = new CalcEngine(null, stateFile.getAbsolutePath());
        restoredEngine.restoreCalcData();
        String restoredRaw = restoredEngine.getResultPlusStatus();
        assertEquals("1,234.56", restoredRaw.substring(0, restoredRaw.length() - 11).trim());
    }
}

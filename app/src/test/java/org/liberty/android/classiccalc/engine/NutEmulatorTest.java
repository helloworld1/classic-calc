package org.liberty.android.classiccalc.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;

/**
 * Comprehensive unit tests for {@link NutEmulator} covering CC 12C and CC 15C
 * ROM execution, RPN arithmetic, financial TVM calculations, scientific functions,
 * register storage/recall, shift annunciators, decimal separator swap, and state persistence.
 */
public class NutEmulatorTest {

    // NutEmulator key codes (shared layout for 12C and 15C)
    private static final int KEY_N_OR_SQRT = 0;
    private static final int KEY_I_OR_EXP = 1;
    private static final int KEY_PV_OR_10X = 2;
    private static final int KEY_PMT_OR_YX = 3;
    private static final int KEY_FV_OR_INVX = 4;
    private static final int KEY_CHS = 5;
    private static final int KEY_7 = 6;
    private static final int KEY_8 = 7;
    private static final int KEY_9 = 8;
    private static final int KEY_DIV = 9;

    private static final int KEY_SIN = 12;
    private static final int KEY_COS = 13;
    private static final int KEY_TAN = 14;
    private static final int KEY_EEX = 15;
    private static final int KEY_4 = 16;
    private static final int KEY_5 = 17;
    private static final int KEY_6 = 18;
    private static final int KEY_MUL = 19;

    private static final int KEY_SST = 21;
    private static final int KEY_RDN = 22;
    private static final int KEY_XY = 23;
    private static final int KEY_CLX = 24;
    private static final int KEY_ENTER = 25;
    private static final int KEY_1 = 26;
    private static final int KEY_2 = 27;
    private static final int KEY_3 = 28;
    private static final int KEY_SUB = 29;

    private static final int KEY_ON = 30;
    private static final int KEY_F = 31;
    private static final int KEY_G = 32;
    private static final int KEY_STO = 33;
    private static final int KEY_RCL = 34;
    private static final int KEY_RS = 35;
    private static final int KEY_0 = 36;
    private static final int KEY_DOT = 37;
    private static final int KEY_SIGMA = 38;
    private static final int KEY_ADD = 39;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    private final AtomicReference<String> lastLcd12c = new AtomicReference<>("");
    private final AtomicReference<String> lastLcd15c = new AtomicReference<>("");
    private NutEmulator emulator12c;
    private NutEmulator emulator15c;
    private File stateFile12c;
    private File stateFile15c;

    @Before
    public void setUp() throws Exception {
        stateFile12c = tempFolder.newFile("state12c.dat");
        stateFile15c = tempFolder.newFile("state15c.dat");
        stateFile12c.delete();
        stateFile15c.delete();

        NutEmulator.Callback callback12c = new NutEmulator.Callback() {
            @Override
            public int handleKeybuffer(int command) {
                return 0;
            }

            @Override
            public void updateLcd(String lcdText) {
                lastLcd12c.set(lcdText);
            }
        };

        emulator12c = new NutEmulator(false, stateFile12c.getAbsolutePath(), callback12c);
        emulator12c.resetCalc();
        emulator12c.executeCalc();
    }

    private NutEmulator getEmulator15c() {
        if (emulator15c == null) {
            NutEmulator.Callback callback15c = new NutEmulator.Callback() {
                @Override
                public int handleKeybuffer(int command) {
                    return 0;
                }

                @Override
                public void updateLcd(String lcdText) {
                    lastLcd15c.set(lcdText);
                }
            };
            emulator15c = new NutEmulator(true, stateFile15c.getAbsolutePath(), callback15c);
            emulator15c.resetCalc();
            emulator15c.executeCalc();
        }
        return emulator15c;
    }

    private String pressKey(NutEmulator emu, int keyCode) {
        emu.keyPressed(keyCode, false);
        emu.keyPressed(keyCode, true);
        return emu.executeCalc();
    }

    private String pressKeys(NutEmulator emu, int... keyCodes) {
        String res = "";
        for (int keyCode : keyCodes) {
            res = pressKey(emu, keyCode);
        }
        return res;
    }

    private String getRawLcd(NutEmulator emu) {
        String raw = (emu == emulator12c) ? lastLcd12c.get() : lastLcd15c.get();
        if (raw == null || raw.isEmpty()) {
            raw = emu.executeCalc();
        }
        assertNotNull(raw);
        assertTrue(raw.length() >= 21);
        return raw;
    }

    private String getDisplayDigits(NutEmulator emu) {
        String raw = getRawLcd(emu);
        return raw.substring(0, raw.length() - 11).trim();
    }

    private String getIndicators(NutEmulator emu) {
        String raw = getRawLcd(emu);
        return raw.substring(raw.length() - 11);
    }

    private double getDisplayDouble(NutEmulator emu) {
        String digits = getDisplayDigits(emu).replace(",", "").replace(" ", "");
        double val = Double.parseDouble(digits);
        return getIndicators(emu).charAt(0) == '1' ? -val : val;
    }

    @Test
    public void test12cInitialResetAndDisplay() {
        assertEquals("0.00", getDisplayDigits(emulator12c));
        assertEquals("00000000000", getIndicators(emulator12c));
        assertEquals(22, lastLcd12c.get().length());
    }

    @Test
    public void test12cBasicRpnArithmetic() {
        // 12 + 30 = 42.00
        pressKeys(emulator12c, KEY_1, KEY_2, KEY_ENTER, KEY_3, KEY_0, KEY_ADD);
        assertEquals(42.00, getDisplayDouble(emulator12c), 1e-6);

        // 42 - 15 = 27.00
        pressKeys(emulator12c, KEY_1, KEY_5, KEY_SUB);
        assertEquals(27.00, getDisplayDouble(emulator12c), 1e-6);

        // 27 * 3 = 81.00
        pressKeys(emulator12c, KEY_3, KEY_MUL);
        assertEquals(81.00, getDisplayDouble(emulator12c), 1e-6);

        // 81 / 9 = 9.00
        pressKeys(emulator12c, KEY_9, KEY_DIV);
        assertEquals(9.00, getDisplayDouble(emulator12c), 1e-6);
    }

    @Test
    public void test12cChsEexAndClx() {
        // Enter 1.5 CHS -> -1.5
        pressKeys(emulator12c, KEY_1, KEY_DOT, KEY_5, KEY_CHS, KEY_ENTER);
        assertEquals(-1.50, getDisplayDouble(emulator12c), 1e-6);

        // Enter 2 EEX 3, then CLx -> 0.00, then enter 4 EEX 1 (40) and multiply: -1.5 * 40 = -60.00
        pressKeys(emulator12c, KEY_2, KEY_EEX, KEY_3);
        pressKey(emulator12c, KEY_CLX);
        assertEquals(0.00, getDisplayDouble(emulator12c), 1e-6);

        pressKeys(emulator12c, KEY_4, KEY_EEX, KEY_1, KEY_MUL);
        assertEquals(-60.00, getDisplayDouble(emulator12c), 1e-6);
    }

    @Test
    public void test12cStackSwapAndRollDown() {
        // Push 10, 20; swap X and Y -> 10 in X, 20 in Y; divide -> 20 / 10 = 2.00
        pressKeys(emulator12c, KEY_1, KEY_0, KEY_ENTER, KEY_2, KEY_0, KEY_XY);
        assertEquals(10.00, getDisplayDouble(emulator12c), 1e-6);
        pressKey(emulator12c, KEY_DIV);
        assertEquals(2.00, getDisplayDouble(emulator12c), 1e-6);
    }

    @Test
    public void test12cStoreAndRecall() {
        // Store 123.45 in register 1, clear X, recall register 1
        pressKeys(emulator12c, KEY_1, KEY_2, KEY_3, KEY_DOT, KEY_4, KEY_5, KEY_STO, KEY_1);
        pressKey(emulator12c, KEY_CLX);
        assertEquals(0.00, getDisplayDouble(emulator12c), 1e-6);

        pressKeys(emulator12c, KEY_RCL, KEY_1);
        assertEquals(123.45, getDisplayDouble(emulator12c), 1e-6);
    }

    @Test
    public void test12cShiftAnnunciators() {
        // Pressing f sets the f indicator (index 3 of indicator string on 12C/15C)
        pressKey(emulator12c, KEY_F);
        assertEquals('1', getIndicators(emulator12c).charAt(3));

        // Pressing f 4 sets 4 decimal places and clears f indicator
        pressKey(emulator12c, KEY_4);
        assertEquals('0', getIndicators(emulator12c).charAt(3));
        assertEquals("0.0000", getDisplayDigits(emulator12c));

        // Pressing g sets the g indicator (index 4 of indicator string on 12C/15C)
        pressKey(emulator12c, KEY_G);
        assertEquals('1', getIndicators(emulator12c).charAt(4));

        // Restore 2 decimal places: f 2
        pressKeys(emulator12c, KEY_F, KEY_2);
        assertEquals("0.00", getDisplayDigits(emulator12c));
    }

    @Test
    public void test12cDecimalSeparatorSwap() {
        pressKeys(emulator12c, KEY_1, KEY_2, KEY_3, KEY_4, KEY_DOT, KEY_5, KEY_6, KEY_ENTER);
        assertEquals("1,234.56", getDisplayDigits(emulator12c));

        emulator12c.swapDecimalSepCC12c();
        assertEquals("1.234,56", getDisplayDigits(emulator12c));

        emulator12c.swapDecimalSepCC12c();
        assertEquals("1,234.56", getDisplayDigits(emulator12c));
    }

    @Test
    public void test12cFinancialTvmCalculation() {
        // Clear FIN registers: f FIN (f + x≷y)
        pressKeys(emulator12c, KEY_F, KEY_XY);

        // Loan: n = 360, i = 0.5% per month, PV = 100000, FV = 0 -> solve PMT = -599.55
        pressKeys(emulator12c, KEY_3, KEY_6, KEY_0, KEY_N_OR_SQRT);
        pressKeys(emulator12c, KEY_DOT, KEY_5, KEY_I_OR_EXP);
        pressKeys(emulator12c, KEY_1, KEY_0, KEY_0, KEY_0, KEY_0, KEY_0, KEY_PV_OR_10X);
        pressKeys(emulator12c, KEY_0, KEY_FV_OR_INVX);
        pressKey(emulator12c, KEY_PMT_OR_YX);

        assertEquals(-599.55, getDisplayDouble(emulator12c), 0.01);
    }

    @Test
    public void test12cSaveAndRestoreState() {
        pressKeys(emulator12c, KEY_7, KEY_7, KEY_7, KEY_ENTER);
        assertEquals(777.00, getDisplayDouble(emulator12c), 1e-6);

        emulator12c.saveCalcData();
        assertTrue(stateFile12c.exists());

        // Reset calculator back to 0.00, then restore from state file
        emulator12c.resetCalc();
        emulator12c.executeCalc();
        assertEquals(0.00, getDisplayDouble(emulator12c), 1e-6);

        emulator12c.restoreCalcData();
        assertEquals(777.00, getDisplayDouble(emulator12c), 1e-6);
    }

    @Test
    public void test15cResetAndScientificFunctions() {
        NutEmulator emu15 = getEmulator15c();
        assertEquals("0.0000", getDisplayDigits(emu15));

        // sqrt(9) = 3.0000
        pressKeys(emu15, KEY_9, KEY_N_OR_SQRT);
        assertEquals(3.0000, getDisplayDouble(emu15), 1e-6);

        // 2^10 = 1024.0000 (enter 2, ENTER, 10, y^x)
        pressKeys(emu15, KEY_2, KEY_ENTER, KEY_1, KEY_0, KEY_PMT_OR_YX);
        assertEquals(1024.0, getDisplayDouble(emu15), 1e-6);

        // 1/4 = 0.2500
        pressKeys(emu15, KEY_4, KEY_FV_OR_INVX);
        assertEquals(0.2500, getDisplayDouble(emu15), 1e-6);

        // Trigonometry in Degrees mode (default on 15C): sin(30) = 0.5000, cos(60) = 0.5000, tan(45) = 1.0000
        pressKeys(emu15, KEY_3, KEY_0, KEY_SIN);
        assertEquals(0.5000, getDisplayDouble(emu15), 1e-4);

        pressKeys(emu15, KEY_6, KEY_0, KEY_COS);
        assertEquals(0.5000, getDisplayDouble(emu15), 1e-4);

        pressKeys(emu15, KEY_4, KEY_5, KEY_TAN);
        assertEquals(1.0000, getDisplayDouble(emu15), 1e-4);
    }

    @Test
    public void test15cDecimalSeparatorSwapAndStatePersistence() {
        NutEmulator emu15 = getEmulator15c();
        pressKeys(emu15, KEY_1, KEY_2, KEY_3, KEY_4, KEY_DOT, KEY_5, KEY_ENTER);
        assertEquals("1,234.5000", getDisplayDigits(emu15));

        emu15.swapDecimalSepCC15c();
        assertEquals("1.234,5000", getDisplayDigits(emu15));

        emu15.swapDecimalSepCC15c();
        assertEquals("1,234.5000", getDisplayDigits(emu15));

        emu15.saveCalcData();
        assertTrue(stateFile15c.exists());

        emu15.resetCalc();
        emu15.executeCalc();
        assertEquals(0.0000, getDisplayDouble(emu15), 1e-6);

        emu15.restoreCalcData();
        assertEquals(1234.5000, getDisplayDouble(emu15), 1e-6);
        assertFalse(lastLcd15c.get().isEmpty());
    }
}

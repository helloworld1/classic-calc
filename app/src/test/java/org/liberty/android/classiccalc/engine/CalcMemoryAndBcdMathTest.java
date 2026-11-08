package org.liberty.android.classiccalc.engine;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Unit tests for {@link CalcMemory} and {@link BcdMathEngine} verifying memory layout,
 * primitive load/store operations, C string/memory helpers, `.rodata` constants,
 * BCD conversions, arithmetic, rounding/truncation, and comparison functions.
 */
public class CalcMemoryAndBcdMathTest {

    // Scratch memory region inside RAM (between ADDR_STRPOOL=0x160000 and STACK_TOP=0x1f0000)
    private static final long SCRATCH_BASE = 0x00180000L;
    private static final long REAL_A = SCRATCH_BASE;
    private static final long REAL_B = SCRATCH_BASE + 32L;
    private static final long REAL_RES = SCRATCH_BASE + 64L;
    private static final long STR_BUF = SCRATCH_BASE + 256L;

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    private CalcEngine engine;

    @Before
    public void setUp() {
        engine = new CalcEngine(null, null);
        engine.resetCalc();
    }

    private void writeCString(long addr, String text) {
        byte[] bytes = text.getBytes(StandardCharsets.ISO_8859_1);
        for (int i = 0; i < bytes.length; i++) {
            engine.putU8(addr + i, bytes[i] & 0xffL);
        }
        engine.putU8(addr + bytes.length, 0L);
    }

    private void parseReal(String text, long destPtr) {
        writeCString(STR_BUF, text);
        long err = engine.fStringToCC_er(STR_BUF, destPtr, 0L, 1L, (long) '.', (long) ',');
        assertEquals("Failed to parse real: " + text, 0L, err);
    }

    private String formatReal(long realPtr, long decimals) {
        engine.fCCToString(realPtr, STR_BUF, (long) '.', 0L, 1L, decimals, 14L, 0L);
        return engine.readCString(STR_BUF, 64).trim();
    }

    private double realToDouble(long realPtr) {
        String s = formatReal(realPtr, 8L);
        return Double.parseDouble(s);
    }

    @Test
    public void testCalcMemoryPrimitiveReadsAndWrites() {
        long addr = SCRATCH_BASE + 512L;

        engine.putI8(addr, 0x7FL);
        assertEquals(0x7FL, engine.getI8(addr));
        assertEquals(0x7FL, engine.getU8(addr));

        engine.putI8(addr, 0xFFL);
        assertEquals(-1L, engine.getI8(addr));
        assertEquals(255L, engine.getU8(addr));

        engine.putI16(addr, 0x8001L);
        assertEquals(-32767L, engine.getI16(addr));
        assertEquals(0x8001L, engine.getU16(addr));

        engine.putI32(addr, 0xDEADBEEFL);
        assertEquals((long) 0xDEADBEEF, engine.getI32(addr));
        assertEquals(0xDEADBEEFL, engine.getU32(addr));

        long val64 = 0x123456789ABCDEF0L;
        engine.putI64(addr, val64);
        assertEquals(val64, engine.getI64(addr));

        engine.putBool(addr, 5L);
        assertEquals(1L, engine.getBool(addr));
        engine.putBool(addr, 0L);
        assertEquals(0L, engine.getBool(addr));
    }

    @Test
    public void testCalcMemoryCStringAndMemHelpers() {
        long buf1 = SCRATCH_BASE + 600L;
        long buf2 = SCRATCH_BASE + 700L;

        writeCString(buf1, "ClassicCalc");
        assertEquals("ClassicCalc", engine.readCString(buf1, 64));
        assertEquals(11L, engine.strlen(buf1));

        engine.strcpy(buf2, buf1);
        assertEquals("ClassicCalc", engine.readCString(buf2, 64));

        engine.memset(buf2, (long) 'A', 5L);
        engine.putI8(buf2 + 5L, 0L);
        assertEquals("AAAAA", engine.readCString(buf2, 64));

        engine.memcpy(buf2, buf1, 7L);
        engine.putI8(buf2 + 7L, 0L);
        assertEquals("Classic", engine.readCString(buf2, 64));

        writeCString(buf1, "  -4567");
        assertEquals(-4567L, engine.atoi(buf1));

        engine.memset(buf2, 0L, 16L);
        engine.inttostr_t(1234L, buf2);
        assertEquals("1234", engine.readCString(buf2, 64).trim());
    }

    @Test
    public void testRodataConstantsIntegrity() {
        // CC_0: sign=1, exp=0, mantissa=0
        assertEquals(1L, engine.getI32(CalcMemory.CC_0));
        assertEquals(0L, engine.getI32(CalcMemory.CC_0 + 4L));
        assertEquals(0L, engine.getI64(CalcMemory.CC_0 + 8L));

        // CC_1: sign=1, exp=0, mantissa=0x0100000000000000L
        assertEquals(1L, engine.getI32(CalcMemory.CC_1));
        assertEquals(0L, engine.getI32(CalcMemory.CC_1 + 4L));
        assertEquals(0x0100000000000000L, engine.getI64(CalcMemory.CC_1 + 8L));
        assertEquals(1L, engine.fCCToInt(CalcMemory.CC_1));

        // CC_N1: -1
        assertEquals(-1L, engine.fCCToInt(CalcMemory.CC_N1));

        // CC_100: 100
        assertEquals(100L, engine.fCCToInt(CalcMemory.CC_100));

        // CC_PI: ~3.14159265
        assertEquals(Math.PI, realToDouble(CalcMemory.CC_PI), 1e-8);
    }

    @Test
    public void testBcdIntAndU64Conversions() {
        engine.fIntToCC(0L, REAL_A);
        assertEquals(0L, engine.fCCToInt(REAL_A));

        engine.fIntToCC(42L, REAL_A);
        assertEquals(42L, engine.fCCToInt(REAL_A));

        engine.fIntToCC(-987654L, REAL_A);
        assertEquals(-987654L, engine.fCCToInt(REAL_A));

        engine.fu64ToCC(123456789012L, REAL_A);
        assertEquals(123456789012L, engine.fCCTou64(REAL_A));

        // Binary-to-BCD and BCD-to-binary nibble helpers
        long bcdNibbles = engine.bintobcd(12345678L);
        assertEquals(0x12345678L, bcdNibbles);
        assertEquals(12345678L, engine.dcbtobin(bcdNibbles));
    }

    @Test
    public void testBcdStringParsingAndFormatting() {
        parseReal("12345.6789", REAL_A);
        assertEquals(12345.6789, realToDouble(REAL_A), 1e-6);

        parseReal("-0.0025", REAL_B);
        assertEquals(-0.0025, realToDouble(REAL_B), 1e-8);

        parseReal("1.5E4", REAL_A);
        assertEquals(15000L, engine.fCCToInt(REAL_A));
    }

    @Test
    public void testBcdPackAndUnpackRoundTrip() {
        parseReal("-31415.926535", REAL_A);
        long packed = engine.fPack(REAL_A);
        engine.fUnpack(packed, REAL_RES);

        assertEquals(-31415.926535, realToDouble(REAL_RES), 1e-5);
    }

    @Test
    public void testBcdArithmeticOperations() {
        parseReal("125.5", REAL_A);
        parseReal("34.25", REAL_B);

        // Addition: 125.5 + 34.25 = 159.75 via fadd and firadd(isSubtract=0)
        engine.fadd(REAL_A, REAL_B, REAL_RES, 0L);
        assertEquals(159.75, realToDouble(REAL_RES), 1e-8);

        engine.firadd(REAL_A, REAL_B, REAL_RES, 0L);
        assertEquals(159.75, realToDouble(REAL_RES), 1e-8);

        // Subtraction: 125.5 - 34.25 = 91.25 via fradd(isSubtract=1) and firadd(isSubtract=1)
        engine.fradd(REAL_A, REAL_B, REAL_RES, 1L);
        assertEquals(91.25, realToDouble(REAL_RES), 1e-8);

        engine.firadd(REAL_A, REAL_B, REAL_RES, 1L);
        assertEquals(91.25, realToDouble(REAL_RES), 1e-8);

        // Multiplication: 12.5 * -4.0 = -50.0
        parseReal("12.5", REAL_A);
        parseReal("-4.0", REAL_B);
        engine.fmul(REAL_A, REAL_B, REAL_RES);
        assertEquals(-50.0, realToDouble(REAL_RES), 1e-8);

        // Division: 355 / 113 = 3.14159292...
        engine.fIntToCC(355L, REAL_A);
        engine.fIntToCC(113L, REAL_B);
        engine.fdiv(REAL_A, REAL_B, REAL_RES);
        assertEquals(355.0 / 113.0, realToDouble(REAL_RES), 1e-8);

        // Square root: sqrt(2) = 1.41421356...
        engine.fIntToCC(2L, REAL_A);
        engine.fisqrt(REAL_A, REAL_RES);
        assertEquals(Math.sqrt(2.0), realToDouble(REAL_RES), 1e-8);

        // Modulo: 17.5 mod 5 = 2.5
        parseReal("17.5", REAL_A);
        parseReal("5.0", REAL_B);
        engine._fmod(REAL_A, REAL_B, REAL_RES);
        assertEquals(2.5, realToDouble(REAL_RES), 1e-8);
    }

    @Test
    public void testBcdRoundingTruncationAndParts() {
        parseReal("-123.4567", REAL_A);

        // Integer part (fiIP) -> -123
        engine.fiIP(REAL_A, REAL_RES);
        assertEquals(-123.0, realToDouble(REAL_RES), 1e-8);

        // Fractional part (fiFP) -> -0.4567
        engine.fiFP(REAL_A, REAL_RES);
        assertEquals(-0.4567, realToDouble(REAL_RES), 1e-8);

        // Round to 2 decimal places (mode=0) -> -123.46
        engine.firnd(REAL_A, 2L, REAL_RES, 0L);
        assertEquals(-123.46, realToDouble(REAL_RES), 1e-8);

        // Truncate to 2 decimal places (mode=0) -> -123.45
        engine.fitrunc(REAL_A, 2L, REAL_RES, 0L);
        assertEquals(-123.45, realToDouble(REAL_RES), 1e-8);

        // Negate (fichs2) and absolute value (fiabs2)
        engine.fichs2(REAL_A, REAL_RES);
        assertEquals(123.4567, realToDouble(REAL_RES), 1e-8);

        engine.fiabs2(REAL_A, REAL_RES);
        assertEquals(123.4567, realToDouble(REAL_RES), 1e-8);

        // Floor and Ceil on positive 123.4567: floor = 123, ceil = 124
        parseReal("123.4567", REAL_B);
        engine.fifloor(REAL_B, REAL_RES);
        assertEquals(123.0, realToDouble(REAL_RES), 1e-8);

        engine.ficeil(REAL_B, REAL_RES);
        assertEquals(124.0, realToDouble(REAL_RES), 1e-8);
    }

    @Test
    public void testBcdComparisonsAndMinMax() {
        parseReal("-10.5", REAL_A);
        parseReal("20.25", REAL_B);

        assertEquals(1L, engine.fLT(REAL_A, REAL_B));
        assertEquals(0L, engine.fLT(REAL_B, REAL_A));
        assertEquals(1L, engine.fLE(REAL_A, REAL_B));
        assertEquals(1L, engine.fLE(REAL_A, REAL_A));
        assertEquals(1L, engine.fEQ(REAL_A, REAL_A));
        assertEquals(0L, engine.fEQ(REAL_A, REAL_B));
        assertEquals(1L, engine.fNE(REAL_A, REAL_B));

        assertEquals(1L, engine.fZero(CalcMemory.CC_0));
        assertEquals(0L, engine.fZero(REAL_A));

        // fimax and fimin write larger/smaller operand to resultPtr
        engine.fimax(REAL_A, REAL_B, REAL_RES);
        assertEquals(20.25, realToDouble(REAL_RES), 1e-8);

        engine.fimin(REAL_A, REAL_B, REAL_RES);
        assertEquals(-10.5, realToDouble(REAL_RES), 1e-8);

        // Integer and parity checks
        assertEquals(0L, engine.fiInteger(REAL_A));
        engine.fIntToCC(14L, REAL_A);
        engine.fIntToCC(15L, REAL_B);
        assertEquals(1L, engine.fiInteger(REAL_A));
        assertEquals(1L, engine.fiParity(REAL_A)); // even -> 1
        assertEquals(0xffffffffL, engine.fiParity(REAL_B)); // odd -> -1 (0xffffffffL)
    }
}

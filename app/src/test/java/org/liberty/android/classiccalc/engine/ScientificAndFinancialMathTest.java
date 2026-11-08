package org.liberty.android.classiccalc.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Unit tests for {@link ScientificMathEngine}, {@link ProbabilityMathEngine},
 * {@link FinancialMathEngine}, and {@link StackAndStatsEngine}.
 */
public class ScientificAndFinancialMathTest {

    // Scratch memory region inside RAM (between ADDR_STRPOOL=0x160000 and STACK_TOP=0x1f0000)
    private static final long SCRATCH_BASE = 0x00180000L;
    private static final long REAL_A = SCRATCH_BASE;
    private static final long REAL_B = SCRATCH_BASE + 32L;
    private static final long REAL_C = SCRATCH_BASE + 64L;
    private static final long REAL_D = SCRATCH_BASE + 96L;
    private static final long REAL_E = SCRATCH_BASE + 128L;
    private static final long REAL_F = SCRATCH_BASE + 160L;
    private static final long REAL_G = SCRATCH_BASE + 192L;
    private static final long REAL_RES = SCRATCH_BASE + 224L;
    private static final long REAL_RES2 = SCRATCH_BASE + 256L;
    private static final long STR_BUF = SCRATCH_BASE + 320L;

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

    private void setDouble(long ptr, double val) {
        writeCString(STR_BUF, Double.toString(val));
        long err = engine.fStringToCC_er(STR_BUF, ptr, 0L, 1L, (long) '.', (long) ',');
        assertEquals(0L, err);
    }

    private double getDouble(long ptr) {
        engine.fCCToString(ptr, STR_BUF, (long) '.', 0L, 1L, 8L, 14L, 0L);
        return Double.parseDouble(engine.readCString(STR_BUF, 64).trim());
    }

    @Test
    public void testFactorialAndPermComb() {
        // 0! = 1, 5! = 120, 10! = 3628800
        engine.fIntToCC(0L, REAL_A);
        engine.fifact_L(REAL_A, REAL_RES);
        assertEquals(1.0, getDouble(REAL_RES), 1e-8);

        engine.fIntToCC(5L, REAL_A);
        engine.fifact_L(REAL_A, REAL_RES);
        assertEquals(120.0, getDouble(REAL_RES), 1e-8);

        engine.fIntToCC(10L, REAL_A);
        engine.fifact_L(REAL_A, REAL_RES);
        assertEquals(3628800.0, getDouble(REAL_RES), 1e-4);

        // Permutations & Combinations: n=10, r=3 -> 10C3 = 120, 10P3 = 720
        // Note: fipermComb_L(rPtr, nPtr, resultPtr, isPerm)
        engine.fIntToCC(3L, REAL_A); // r = 3
        engine.fIntToCC(10L, REAL_B); // n = 10
        engine.fipermComb_L(REAL_A, REAL_B, REAL_RES, 0L); // nCr
        assertEquals(120.0, getDouble(REAL_RES), 1e-8);

        engine.fipermComb_L(REAL_A, REAL_B, REAL_RES, 1L); // nPr
        assertEquals(720.0, getDouble(REAL_RES), 1e-8);
    }

    @Test
    public void testTrigonometricAndInverseTrig() {
        // Degrees mode (angleMode = 1): trigOp: 0=tan, 1=cot, 2=cos, 3=sin
        engine.fIntToCC(30L, REAL_A);
        engine.fitrig(REAL_A, REAL_RES, 3L, 1L); // sin(30 deg) = 0.5
        assertEquals(0.5, getDouble(REAL_RES), 1e-8);

        engine.fIntToCC(60L, REAL_A);
        engine.fitrig(REAL_A, REAL_RES, 2L, 1L); // cos(60 deg) = 0.5
        assertEquals(0.5, getDouble(REAL_RES), 1e-8);

        engine.fIntToCC(45L, REAL_A);
        engine.fitrig(REAL_A, REAL_RES, 0L, 1L); // tan(45 deg) = 1.0
        assertEquals(1.0, getDouble(REAL_RES), 1e-8);

        // Inverse trig in Degrees mode (angleMode = 1): atrigOp: 0=atan, 2=acos, 3=asin
        setDouble(REAL_A, 0.5);
        engine.fiatrig(REAL_A, REAL_RES, 3L, 1L); // asin(0.5) = 30 deg
        assertEquals(30.0, getDouble(REAL_RES), 1e-7);

        engine.fiatrig(REAL_A, REAL_RES, 2L, 1L); // acos(0.5) = 60 deg
        assertEquals(60.0, getDouble(REAL_RES), 1e-7);

        engine.fIntToCC(1L, REAL_A);
        engine.fiatrig(REAL_A, REAL_RES, 0L, 1L); // atan(1.0) = 45 deg
        assertEquals(45.0, getDouble(REAL_RES), 1e-7);
    }

    @Test
    public void testHyperbolicAndInverseHyperbolic() {
        setDouble(REAL_A, 1.0);
        engine.fisincosh(REAL_A, REAL_RES, REAL_RES2);
        assertEquals(Math.sinh(1.0), getDouble(REAL_RES), 1e-8);
        assertEquals(Math.cosh(1.0), getDouble(REAL_RES2), 1e-8);

        engine.fitanh(REAL_A, REAL_RES);
        assertEquals(Math.tanh(1.0), getDouble(REAL_RES), 1e-8);

        // Round-trip via inverse hyperbolic functions
        setDouble(REAL_A, Math.sinh(1.5));
        engine.fiasinh(REAL_A, REAL_RES);
        assertEquals(1.5, getDouble(REAL_RES), 1e-7);

        setDouble(REAL_A, Math.cosh(1.5));
        engine.fiacosh(REAL_A, REAL_RES);
        assertEquals(1.5, getDouble(REAL_RES), 1e-7);

        setDouble(REAL_A, 0.5);
        engine.fiatanh(REAL_A, REAL_RES);
        assertEquals(0.5 * Math.log(1.5 / 0.5), getDouble(REAL_RES), 1e-7);
    }

    @Test
    public void testLogsExponentialsPowersAndRoots() {
        // exp(1) = e, expm1(1) = e - 1
        engine.fIntToCC(1L, REAL_A);
        engine.fiexpm(REAL_A, REAL_RES, REAL_RES2);
        assertEquals(Math.E, getDouble(REAL_RES), 1e-8);
        assertEquals(Math.E - 1.0, getDouble(REAL_RES2), 1e-8);

        // ln(e) = 1
        engine.filn(REAL_RES, REAL_B);
        assertEquals(1.0, getDouble(REAL_B), 1e-8);

        // log10(1000) = 3
        engine.fIntToCC(1000L, REAL_A);
        engine.filog(REAL_A, REAL_RES);
        assertEquals(3.0, getDouble(REAL_RES), 1e-8);

        // lnp1(0.5) = ln(1.5)
        setDouble(REAL_A, 0.5);
        engine.flnp1(REAL_A, REAL_RES);
        assertEquals(Math.log1p(0.5), getDouble(REAL_RES), 1e-8);

        // fpow: 2^10 = 1024
        engine.fIntToCC(2L, REAL_A);
        engine.fIntToCC(10L, REAL_B);
        engine.fpow(REAL_A, REAL_B, REAL_RES, 1L);
        assertEquals(1024.0, getDouble(REAL_RES), 1e-8);

        // finthroot: cube root of 27 = 3
        engine.fIntToCC(27L, REAL_A);
        engine.fIntToCC(3L, REAL_B);
        engine.finthroot(REAL_A, REAL_B, REAL_RES);
        assertEquals(3.0, getDouble(REAL_RES), 1e-8);
    }

    @Test
    public void testProbabilityUpperTailNormal() {
        // Standard normal upper tail Q(x) with mean=0, variance=1:
        // Q(0) = 0.5, Q(1.95996398) = 0.025, Q(-1.95996398) = 0.975
        engine.fIntToCC(0L, REAL_A);
        engine.fiUTPN(CalcMemory.CC_0, CalcMemory.CC_1, REAL_A, REAL_RES);
        assertEquals(0.5, getDouble(REAL_RES), 1e-7);

        setDouble(REAL_A, 1.95996398454);
        engine.fiUTPN(CalcMemory.CC_0, CalcMemory.CC_1, REAL_A, REAL_RES);
        assertEquals(0.025, getDouble(REAL_RES), 1e-6);

        setDouble(REAL_A, -1.95996398454);
        engine.fiUTPN(CalcMemory.CC_0, CalcMemory.CC_1, REAL_A, REAL_RES);
        assertEquals(0.975, getDouble(REAL_RES), 1e-6);
    }

    @Test
    public void testFinancialTvmFunctions() {
        // Mortgage: N = 360, I = 0.5% per period, PV = 100000, FV = 0, PYR = 1, CYR = 1, END mode (beginMode = 0)
        setDouble(REAL_A, 360.0);    // N
        setDouble(REAL_B, 0.5);      // I% per period
        setDouble(REAL_C, 100000.0); // PV
        setDouble(REAL_D, 0.0);      // PMT (to be solved)
        setDouble(REAL_E, 0.0);      // FV
        setDouble(REAL_F, 1.0);      // PYR
        setDouble(REAL_G, 1.0);      // CYR

        // Solve PMT (return status 0 or 1 means success; >1 means error)
        long err = engine.TVMPMT(REAL_A, REAL_B, REAL_C, REAL_D, REAL_E, REAL_F, REAL_G, 0L);
        assertTrue(err <= 1L);
        assertEquals(-599.550525, getDouble(REAL_D), 1e-4);

        // Solve PV back from N, I, PMT, FV
        setDouble(REAL_C, 0.0);
        err = engine.TVMPV(REAL_A, REAL_B, REAL_C, REAL_D, REAL_E, REAL_F, REAL_G, 0L);
        assertTrue(err <= 1L);
        assertEquals(100000.0, getDouble(REAL_C), 1e-2);

        // Solve N back from I, PV, PMT, FV
        setDouble(REAL_A, 0.0);
        err = engine.TVMN(REAL_A, REAL_B, REAL_C, REAL_D, REAL_E, REAL_F, REAL_G, 0L);
        assertTrue(err <= 1L);
        assertEquals(360.0, getDouble(REAL_A), 1e-4);

        // Solve I back from N, PV, PMT, FV
        setDouble(REAL_B, 0.0);
        err = engine.TVMI(REAL_A, REAL_B, REAL_C, REAL_D, REAL_E, REAL_F, REAL_G, 0L, 0L, 0L);
        assertTrue(err <= 1L);
        assertEquals(0.5, getDouble(REAL_B), 1e-5);

        // Solve FV for compound savings: N=10, I=5%, PV=-1000, PMT=0 -> FV = 1000 * (1.05)^10 = 1628.8946
        setDouble(REAL_A, 10.0);
        setDouble(REAL_B, 5.0);
        setDouble(REAL_C, -1000.0);
        setDouble(REAL_D, 0.0);
        setDouble(REAL_E, 0.0);
        err = engine.TVMFV(REAL_A, REAL_B, REAL_C, REAL_D, REAL_E, REAL_F, REAL_G, 0L);
        assertTrue(err <= 1L);
        assertEquals(1628.894627, getDouble(REAL_E), 1e-4);
    }

    @Test
    public void testFinancialPercentagesBreakEvenAndLeapYear() {
        // DateLeap: 2024 is leap (1), 2023 is not (0), 2000 is leap (1), 1900 is not (0)
        assertEquals(1L, engine.DateLeap(2024L));
        assertEquals(0L, engine.DateLeap(2023L));
        assertEquals(1L, engine.DateLeap(2000L));
        assertEquals(0L, engine.DateLeap(1900L));

        // PartAsPercentOfTotal: total=250, part=50 -> pct = 20% (solveTarget = 2)
        setDouble(REAL_A, 250.0);
        setDouble(REAL_B, 50.0);
        setDouble(REAL_C, 0.0);
        assertEquals(0L, engine.PartAsPercentOfTotal(REAL_A, REAL_B, REAL_C, 2L));
        assertEquals(20.0, getDouble(REAL_C), 1e-6);

        // MarkupAsPercentOfCost: cost=80, price=100 -> markup = 25% (solveTarget = 0 solves 1st arg)
        setDouble(REAL_C, 0.0);
        setDouble(REAL_A, 80.0);
        setDouble(REAL_B, 100.0);
        assertTrue(engine.MarkupAsPercentOfCost(REAL_C, REAL_A, REAL_B, 0L) <= 1L);
        assertEquals(25.0, getDouble(REAL_C), 1e-6);

        // MarkupAsPercentOfSale (Margin): cost=80, price=100 -> margin = 20% (solveTarget = 0 solves 1st arg)
        setDouble(REAL_C, 0.0);
        assertTrue(engine.MarkupAsPercentOfSale(REAL_C, REAL_A, REAL_B, 0L) <= 1L);
        assertEquals(20.0, getDouble(REAL_C), 1e-6);

        // BreakEven: fixedCost=10000, varCost=15, price=25, profit=5000 -> quantity = (10000+5000)/(25-15) = 1500
        setDouble(REAL_A, 10000.0);
        setDouble(REAL_B, 15.0);
        setDouble(REAL_C, 25.0);
        setDouble(REAL_D, 5000.0);
        setDouble(REAL_E, 0.0);
        assertEquals(0L, engine.BreakEven(REAL_A, REAL_B, REAL_C, REAL_D, REAL_E, 4L));
        assertEquals(1500.0, getDouble(REAL_E), 1e-6);
    }

    @Test
    public void testRpnStackOperations() {
        engine.Stack_ClearAll();

        // Set X=10, insert 20 to X -> X=20, Y=10
        setDouble(REAL_A, 10.0);
        engine.Stack_SetX(REAL_A);
        setDouble(REAL_A, 20.0);
        engine.Stack_InsertToX(REAL_A);

        engine.putCCReal(REAL_RES, engine.Stack_GetX());
        assertEquals(20.0, getDouble(REAL_RES), 1e-8);
        engine.putCCReal(REAL_RES, engine.Stack_GetY());
        assertEquals(10.0, getDouble(REAL_RES), 1e-8);

        // Stack_SwapXY -> X=10, Y=20
        engine.Stack_SwapXY();
        engine.putCCReal(REAL_RES, engine.Stack_GetX());
        assertEquals(10.0, getDouble(REAL_RES), 1e-8);
        engine.putCCReal(REAL_RES, engine.Stack_GetY());
        assertEquals(20.0, getDouble(REAL_RES), 1e-8);

        // Stack_RotateDownOneStep moves X (10) into T and Y (20) into X
        engine.Stack_RotateDownOneStep();
        engine.putCCReal(REAL_RES, engine.Stack_GetT());
        assertEquals(10.0, getDouble(REAL_RES), 1e-8);
        engine.putCCReal(REAL_RES, engine.Stack_GetX());
        assertEquals(20.0, getDouble(REAL_RES), 1e-8);
    }
}

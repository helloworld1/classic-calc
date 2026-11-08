package org.liberty.android.classiccalc.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public abstract class CalcMemory {
    public interface Callback {
        int handleKeybuffer(int op);
        void updateLcd(String display);
    }

    static final class CC_Real {
        long s;
        long e;
        long m;

        CC_Real() {}

        CC_Real(long s, long e, long m) {
            this.s = (int) s;
            this.e = (int) e;
            this.m = m;
        }

        CC_Real copy() {
            return new CC_Real(this.s, this.e, this.m);
        }
    }

    static final long ADDR_RODATA = 0x0010ecf0L;
    static final long ADDR_RWDATA = 0x00154050L;
    static final long ADDR_STRPOOL = 0x00160000L;
    static final int STACK_TOP = 0x001f0000;
    private static final int STATE_MAGIC = 0x31324350;

    // .rodata constants
    static final long CC_0 = ADDR_RODATA + 0x470L;
    static final long CC_N0 = ADDR_RODATA + 0x480L;
    static final long CC_N1 = ADDR_RODATA + 0x490L;
    static final long CC_1 = ADDR_RODATA + 0x4a0L;
    static final long CC_2 = ADDR_RODATA + 0x4b0L;
    static final long CC_N2 = ADDR_RODATA + 0x4c0L;
    static final long CC_3 = ADDR_RODATA + 0x4d0L;
    static final long CC_N3 = ADDR_RODATA + 0x4e0L;
    static final long CC_4 = ADDR_RODATA + 0x4f0L;
    static final long CC_5 = ADDR_RODATA + 0x500L;
    static final long CC_6 = ADDR_RODATA + 0x510L;
    static final long CC_7 = ADDR_RODATA + 0x520L;
    static final long CC_8 = ADDR_RODATA + 0x530L;
    static final long CC_9 = ADDR_RODATA + 0x540L;
    static final long CC_N4 = ADDR_RODATA + 0x550L;
    static final long CC_N5 = ADDR_RODATA + 0x560L;
    static final long CC_N6 = ADDR_RODATA + 0x570L;
    static final long CC_N7 = ADDR_RODATA + 0x580L;
    static final long CC_N8 = ADDR_RODATA + 0x590L;
    static final long CC_N9 = ADDR_RODATA + 0x5a0L;
    static final long CC_N10 = ADDR_RODATA + 0x5b0L;
    static final long CC_10 = ADDR_RODATA + 0x5c0L;
    static final long CC_11 = ADDR_RODATA + 0x5d0L;
    static final long CC_50 = ADDR_RODATA + 0x5e0L;
    static final long CC_60 = ADDR_RODATA + 0x5f0L;
    static final long CC_PInf = ADDR_RODATA + 0x600L;
    static final long CC_NInf = ADDR_RODATA + 0x610L;
    static final long CC_Max = ADDR_RODATA + 0x620L;
    static final long CC_Min = ADDR_RODATA + 0x630L;
    static final long CC_NMax = ADDR_RODATA + 0x640L;
    static final long CC_NMin = ADDR_RODATA + 0x650L;
    static final long CC_NaN = ADDR_RODATA + 0x660L;
    static final long CC_PBig_L = ADDR_RODATA + 0x678L;
    static final long CC_PInf_L = ADDR_RODATA + 0x688L;
    static final long CC_NInf_L = ADDR_RODATA + 0x698L;
    static final long CC_Max_L = ADDR_RODATA + 0x6a8L;
    static final long CC_Min_L = ADDR_RODATA + 0x6b8L;
    static final long CC_NMax_L = ADDR_RODATA + 0x6c8L;
    static final long CC_NMin_L = ADDR_RODATA + 0x6d8L;
    static final long PCC_0 = ADDR_RODATA + 0x6e8L;
    static final long PCC_1 = ADDR_RODATA + 0x6f0L;
    static final long PCC_2 = ADDR_RODATA + 0x6f8L;
    static final long PCC_3 = ADDR_RODATA + 0x700L;
    static final long PCC_100 = ADDR_RODATA + 0x708L;
    static final long PCC_200 = ADDR_RODATA + 0x710L;
    static final long PCC_12 = ADDR_RODATA + 0x718L;
    static final long PCC_NaN = ADDR_RODATA + 0x720L;
    static final long CC_MaxInt = ADDR_RODATA + 0x728L;
    static final long CC_0p99999 = ADDR_RODATA + 0x740L;
    static final long CC_N0p9999999999 = ADDR_RODATA + 0x750L;
    static final long CC_0p00001 = ADDR_RODATA + 0x760L;
    static final long CC_1p1 = ADDR_RODATA + 0x770L;
    static final long CC_N0p11 = ADDR_RODATA + 0x780L;
    static final long CC_12 = ADDR_RODATA + 0x790L;
    static final long CC_1p002 = ADDR_RODATA + 0x7a0L;
    static final long CC_0p012 = ADDR_RODATA + 0x7b0L;
    static final long CC_13 = ADDR_RODATA + 0x7c0L;
    static final long CC_360 = ADDR_RODATA + 0x7d0L;
    static final long CC_480 = ADDR_RODATA + 0x7e0L;
    static final long CC_253 = ADDR_RODATA + 0x878L;
    static final long CC_n253 = ADDR_RODATA + 0x888L;
    static final long CC_n495p920119017593 = ADDR_RODATA + 0x898L;
    static final long CC_30p3479606073615 = ADDR_RODATA + 0x8a8L;
    static final long CC_n76p5594818140208 = ADDR_RODATA + 0x8b8L;
    static final long CC_9p33584905660377 = ADDR_RODATA + 0x8c8L;
    static final long CC_n1p21142857142857 = ADDR_RODATA + 0x8d8L;
    static final long CC_0p918938533204673 = ADDR_RODATA + 0x8e8L;
    static final long CC_0p4 = ADDR_RODATA + 0x8f8L;
    static final long CC_0p5 = ADDR_RODATA + 0x908L;
    static final long CC_N0p5 = ADDR_RODATA + 0x918L;
    static final long CC_0p398942280401432 = ADDR_RODATA + 0x928L;
    static final long CC_2p32 = ADDR_RODATA + 0x938L;
    static final long CC_3p5 = ADDR_RODATA + 0x948L;
    static final long CC_ExpN2 = ADDR_RODATA + 0x958L;
    static final long CC_1mEXPN2 = ADDR_RODATA + 0x968L;
    static final long CC_Sqrt2PI = ADDR_RODATA + 0x978L;
    static final long CC_32 = ADDR_RODATA + 0x988L;
    static final long CC_MachEp = ADDR_RODATA + 0x998L;
    static final long CC_1divMachEp = ADDR_RODATA + 0x9a8L;
    static final long CC_MaxGamma = ADDR_RODATA + 0x9b8L;
    static final long CC_MaxlnGamma = ADDR_RODATA + 0x9c8L;
    static final long CC_MaxLog = ADDR_RODATA + 0x9d8L;
    static final long CC_MinLog = ADDR_RODATA + 0x9e8L;
    static final long CC_0p95 = ADDR_RODATA + 0x9f8L;
    static final long CC_1024 = ADDR_RODATA + 0xa08L;
    static final long CC_1E10 = ADDR_RODATA + 0xa18L;
    static final long CC_34 = ADDR_RODATA + 0xa28L;
    static final long CC_lnPI = ADDR_RODATA + 0xa38L;
    static final long CC_0p25 = ADDR_RODATA + 0xa48L;
    static final long CC_0p75 = ADDR_RODATA + 0xa58L;
    static final long CC_Sqrt2div2 = ADDR_RODATA + 0xa68L;
    static final long CC_invSqrt2Pi = ADDR_RODATA + 0xa78L;
    static final long CC_1Em6 = ADDR_RODATA + 0xa88L;
    static final long CC_1Em4 = ADDR_RODATA + 0xa98L;
    static final long CC_5div6 = ADDR_RODATA + 0xaa8L;
    static final long CC_0p999999999999999 = ADDR_RODATA + 0xab8L;
    static final long CC_0p2 = ADDR_RODATA + 0xac8L;
    static final long CC_0p0625 = ADDR_RODATA + 0xad8L;
    static final long CC_0p1 = ADDR_RODATA + 0x15b0L;
    static final long CC_0p05 = ADDR_RODATA + 0x15c0L;
    static final long CC_PIDiv180 = ADDR_RODATA + 0x1618L;
    static final long CC_0p9 = ADDR_RODATA + 0x1628L;
    static final long CC_45 = ADDR_RODATA + 0x1638L;
    static final long CC_30 = ADDR_RODATA + 0x1648L;
    static final long CC_PI = ADDR_RODATA + 0x1658L;
    static final long CC_PI_L = ADDR_RODATA + 0x1668L;
    static final long PCC_PI = ADDR_RODATA + 0x1678L;
    static final long CC_PIdiv2 = ADDR_RODATA + 0x1680L;
    static final long CC_PIdiv4 = ADDR_RODATA + 0x1690L;
    static final long CC_3PIdiv4 = ADDR_RODATA + 0x16a0L;
    static final long CC_100 = ADDR_RODATA + 0x16b0L;
    static final long CC_n100 = ADDR_RODATA + 0x16c0L;
    static final long CC_135 = ADDR_RODATA + 0x16d0L;
    static final long CC_150 = ADDR_RODATA + 0x16e0L;
    static final long CC_90 = ADDR_RODATA + 0x16f0L;
    static final long CC_200 = ADDR_RODATA + 0x1700L;
    static final long CC_180 = ADDR_RODATA + 0x1710L;
    static final long CC_E_L = ADDR_RODATA + 0x1798L;
    static final long CC_ln10 = ADDR_RODATA + 0x17e8L;
    static final long ProgKeyCodeDefine = ADDR_RODATA + 0x1ae3L;

    // .rwdata / .bss globals
    static final long CC_LOG2 = ADDR_RWDATA;
    static final long pCodeTable = ADDR_RWDATA + 0x10L;
    static final long g_ResultStub = ADDR_RWDATA + 0x18L;
    static final long g_StatusIndicators = ADDR_RWDATA + 0x40L;
    static final long g_once = ADDR_RWDATA + 0x4cL;
    static final long g_JniEnv = ADDR_RWDATA + 0x50L;
    static final long g_obj = ADDR_RWDATA + 0x58L;
    static final long g_Lcd_method = ADDR_RWDATA + 0x60L;
    static final long g_Key_Method = ADDR_RWDATA + 0x68L;
    static final long g_isFileRead = ADDR_RWDATA + 0x70L;
    static final long cached_jvm = ADDR_RWDATA + 0x78L;
    static final long g_fKeypadDriverCallBack = ADDR_RWDATA + 0x80L;
    static final long g_nKeypadDriverCallBackCount = ADDR_RWDATA + 0x88L;
    static final long g_com_uimode = ADDR_RWDATA + 0x8cL;
    static final long g_com_shiftmode = ADDR_RWDATA + 0x90L;
    static final long g_com_operationmode = ADDR_RWDATA + 0x94L;
    static final long g_com_datemode = ADDR_RWDATA + 0x98L;
    static final long g_com_keyinmode = ADDR_RWDATA + 0x9cL;
    static final long g_com_eex_forbackspace = ADDR_RWDATA + 0xa0L;
    static final long g_com_resultstate = ADDR_RWDATA + 0xa1L;
    static final long g_com_errmode = ADDR_RWDATA + 0xa3L;
    static final long g_com_decimal_mode = ADDR_RWDATA + 0xa4L;
    static final long g_com_machine_mode = ADDR_RWDATA + 0xa8L;
    static final long g_com_keydown_state = ADDR_RWDATA + 0xacL;
    static final long g_com_prevkeycode = ADDR_RWDATA + 0xb0L;
    static final long g_com_Xinsertmode = ADDR_RWDATA + 0xb4L;
    static final long g_com_needshowrunning = ADDR_RWDATA + 0xb5L;
    static final long g_com_canundo = ADDR_RWDATA + 0xb6L;
    static final long g_com_bracketmode = ADDR_RWDATA + 0xb7L;
    static final long g_com_keycodeincallback = ADDR_RWDATA + 0xb8L;
    static final long g_com_prevdown_keycode = ADDR_RWDATA + 0xbcL;
    static final long g_com_show_prerror = ADDR_RWDATA + 0xc0L;
    static final long g_com_fincal_count = ADDR_RWDATA + 0xc1L;
    static final long g_com_fincal_prev = ADDR_RWDATA + 0xc2L;
    static final long g_com_interruptbykey = ADDR_RWDATA + 0xc3L;
    static final long g_com_result_overmax = ADDR_RWDATA + 0xc4L;
    static final long g_com_prog_share_gap = ADDR_RWDATA + 0xc5L;
    static final long g_com_fin_lastcflo_times = ADDR_RWDATA + 0xc6L;
    static final long g_com_fin_lastcflo = ADDR_RWDATA + 0xc8L;
    static final long g_com_onkeyup_mode = ADDR_RWDATA + 0xe0L;
    static final long g_com_ignore_sub_key = ADDR_RWDATA + 0xe4L;
    static final long g_com_algreg_valid = ADDR_RWDATA + 0xe5L;
    static final long g_com_rounddigit = ADDR_RWDATA + 0xe6L;
    static final long g_com_insert_for_finreg = ADDR_RWDATA + 0xe7L;
    static final long g_com_reg_XYZT = ADDR_RWDATA + 0xe8L;
    static final long g_com_reg_lastx = ADDR_RWDATA + 0x128L;
    static final long g_com_reg_X1 = ADDR_RWDATA + 0x138L;
    static final long g_basic_op = ADDR_RWDATA + 0x148L;
    static final long g_basic_edit = ADDR_RWDATA + 0x14cL;
    static final long g_basic_displaymode = ADDR_RWDATA + 0x160L;
    static final long g_result_state = ADDR_RWDATA + 0x164L;
    static final long g_reg_state = ADDR_RWDATA + 0x168L;
    static final long g_gto_state = ADDR_RWDATA + 0x16bL;
    static final long g_undo_buffer = ADDR_RWDATA + 0x170L;
    static final long g_undo_list_pUndoBuffer = ADDR_RWDATA + 0x188L;
    static final long g_undo_list = ADDR_RWDATA + 0x2d8L;
    static final long g_undo_index = ADDR_RWDATA + 0x428L;
    static final long g_undo_flag = ADDR_RWDATA + 0x429L;
    static final long g_basic_lastx = ADDR_RWDATA + 0x430L;
    static final long g_bracket_flag = ADDR_RWDATA + 0x448L;
    static final long g_expression_state = ADDR_RWDATA + 0x44cL;
    static final long g_stack_element = ADDR_RWDATA + 0x450L;
    static final long g_stack_depth = ADDR_RWDATA + 0x588L;
    static final long g_fin_n = ADDR_RWDATA + 0x590L;
    static final long g_fin_i = ADDR_RWDATA + 0x5a0L;
    static final long g_fin_npv = ADDR_RWDATA + 0x5b0L;
    static final long g_fin_irr = ADDR_RWDATA + 0x5c0L;
    static final long g_fin_pv = ADDR_RWDATA + 0x5d0L;
    static final long g_fin_pmt = ADDR_RWDATA + 0x5e0L;
    static final long g_fin_fv = ADDR_RWDATA + 0x5f0L;
    static final long g_fin_p_yr = ADDR_RWDATA + 0x600L;
    static final long g_fin_begin_of_period = ADDR_RWDATA + 0x610L;
    static final long g_fin_tvm_pinput = ADDR_RWDATA + 0x618L;
    static final long g_fin_compoundinterest = ADDR_RWDATA + 0x648L;
    static final long g_fin_maxcflo = ADDR_RWDATA + 0x649L;
    static final long g_tip_bill = ADDR_RWDATA + 0x650L;
    static final long g_tip_tippercent = ADDR_RWDATA + 0x660L;
    static final long g_tip_split = ADDR_RWDATA + 0x670L;
    static final long g_tip_tip = ADDR_RWDATA + 0x680L;
    static final long g_tip_total = ADDR_RWDATA + 0x690L;
    static final long g_prog_bPause = ADDR_RWDATA + 0x6a0L;
    static final long g_prog_nMemState = ADDR_RWDATA + 0x6a2L;
    static final long g_prog_currpos = ADDR_RWDATA + 0x6a4L;
    static final long g_prog_maxstep = ADDR_RWDATA + 0x6a6L;
    static final long g_prog_nKey = ADDR_RWDATA + 0x6a8L;
    static final long g_prog_nDot = ADDR_RWDATA + 0x6a9L;
    static final long g_prog_TempCount = ADDR_RWDATA + 0x6aaL;
    static final long g_prog_TempBuf = ADDR_RWDATA + 0x6abL;
    static final long g_fin_cflo_num = ADDR_RWDATA + 0x6b0L;
    static final long g_prog_totalstep_num = ADDR_RWDATA + 0x6b2L;
    static final long g_prog_storedata = ADDR_RWDATA + 0x6b4L;
    static final long g_fin_cflodata = ADDR_RWDATA + 0xc90L;
    static final long g_com_reg = ADDR_RWDATA + 0x1428L;
    static final long g_stat_xy_values = ADDR_RWDATA + 0x1568L;
    static final long Callback_Result = ADDR_RWDATA + 0x1590L;

    // Function pointer IDs
    static final long FPTR_myTGetEntryIRR = 1001L;
    static final long FPTR_UserStop = 1002L;
    static final long FPTR_IRRMIRRNextEntry = 1003L;
    static final long FPTR_TVMIConvRateFunc = 1004L;
    static final long FPTR_sPolyEvalFunc = 1005L;
    static final long FPTR_sRegTransformLogLogistic = 1006L;
    static final long FPTR_sRegTransformLogistic = 1007L;
    static final long FPTR_sRegTransformLnX = 1008L;
    static final long FPTR_sRegTransformExpY = 1009L;
    static final long FPTR_sRegTransformInvY = 1010L;
    static final long FPTR_sRegTransformPowXY = 1011L;
    static final long FPTR_RegStatsGetEntry = 1012L;

    final byte[] memBytes = new byte[0x200000];
    final ByteBuffer mem = ByteBuffer.wrap(memBytes).order(ByteOrder.LITTLE_ENDIAN);
    int sp = STACK_TOP;
    Callback callback;
    String stateFilePath;
    boolean isFileRead = false;

    private static final String[] RODATA_CHUNKS = new String[] {
        "ER: Edit Exponent\u0000ER: Edit No Digits\u0000ER: Invalid I%\u0000ER: \u009f Result\u0000-100\u0000ER: Inexact\u0000ER: Constant Function\u00001E-99\u0000(Ljava/lang/String;)V\u0000ER: Stack\u0000ER: Invalid P/YR\u00004333\u0000ER: Variables\u0000ER: insufficient Memory\u0000ER: Overflow\u0000ER: Ln(0)\u0000ER: Invalid N\u0000(I)I\u0000-9.999999999E99\u0000ER: \u009f/\u009f\u0000ER: (x<0)^(Frac)\u0000ER: Many or No Solutions\u0000ER: No Payback\u0000ER: Parenthesis\u0000rb\u0000ER: Mainy Solutils\u0000handleKeybuffer\u0000ER: Extemum Found\u0000 RUNNING\u0000ER: 1^\u009f\u0000ER: (x<0)!\u0000ER: ATrig(|x|>1)\u0000NO FILE00000000000\u0000ER: User Abort\u000000000000000\u0000/data/data/com.cc.cc12cpt/calcstate.txt\u0000ER: Edit Two Coma\u0000+0.000000000E+00000\u0000ER: No Solution\u0000ER: Gap found\u00000.005\u0000ER: insufficient Data\u0000ER: No Solution Found\u00009999999999\u0000ER: 0/0\u0000ER: Out Of Bounds\u0000ER: Invalid Input\u0000 Pr ErrOr\u000000-\u0000ER: \u009f^0\u0000ER: Underflow\u00009.999999999E99\u0000000\u0000com/cc/cc12cpt/Calculator\u0000wb\u0000ER: None\u0000ER: Ln(x<0)\u0000ER: Edit Syntax\u0000ER: Pole Found\u0000\u0000ER: \u0083(x<0)\u0000ER: Irr0\u0000ER: Bad Guesses\u0000ER: \u009f*0\u0000ER: \u009f^(Frac)\u0000updateLcd\u0000ER: X/0\u0000ER: (x<0)^\u009f\u0000ER: Unique solution to IRR Not Found\u0000ER: Bad Arguement count\u0000\u00003\u0000\u000b\u0000\u0000\u0000\u0090\u0099\u0099\u0099\u0099\u0099\u0009\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00002\u0000\n\u0000\u0000E\u00002\u0000\u0000!\u0000\n\u0000\u0000\u0010\u0005\u001f\u0000b\u0000A\u0000\u0000\u00b8\u0000c\u0000\u0000\u0004\u0000\u0003\u0000\u0005\u0002\u0013\u0002\u0005\u0000\u0000\u000c\u0000\u0003\u0000\u0000.\u0000\r\u0000\u0000.\u0000\r\u0000\u0000.\u0000\r\u0000\u00002\u0000\r\u0000\u0000\u0013\u0000\u001e\u0000\u0000/\u0000\r\u0000\u0000/\u0000\r\u0000\u0000/\u0000\r\u0000\u00002\u0000\r\u0000\u0000p\u0000@\u0000\u00004\u0000\u000f\u0000NaN\u0000Inf\u0000-Inf\u0000+Inf\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0002\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0002\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0003\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0004\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0006\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0008\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0009\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0004\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0005\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0006\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0007\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0008\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0009\u00ff\u00ff\u00ff\u00ff\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0010\u0001\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0006\u0002\u0000\u0000\u0000O\u00c3\u0000\u0000\u0099\u0099\u0099\u0099\u0099\u0099\u0099\u0009\u00fe\u00ff\u00ff\u00ffO\u00c3\u0000\u0000\u0099\u0099\u0099\u0099\u0099\u0099\u0099\u0009\u0001\u0000\u0000\u0000O\u00c3\u0000\u0000\u0099\u0099\u0099\u0099\u0099\u0099\u0099\u0009\u0001\u0000\u0000\u0000\u00b1<\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u00ff\u00ff\u00ff\u00ffO\u00c3\u0000\u0000\u0099\u0099\u0099\u0099\u0099\u0099\u0099\u0009\u00ff\u00ff\u00ff\u00ff\u00b1<\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u00f2\u0001\u0000\u0000\u0000\u0090\u0099\u0099\u0099\u0099\u0099\u0009\u0002\u0000\u0000\u0000\u00f3\u0001\u0000\u0000\u0000\u0090\u0099\u0099\u0099\u0099\u0099\u0009\u00fe\u00ff\u00ff\u00ff\u00f3\u0001\u0000\u0000\u0000\u0090\u0099\u0099\u0099\u0099\u0099\u0009\u0001\u0000\u0000\u0000\u00f3\u0001\u0000\u0000\u0000\u0090\u0099\u0099\u0099\u0099\u0099\u0009\u0001\u0000\u0000\u0000\r\u00fe\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u00ff\u00ff\u00ff\u00ff\u00f3\u0001\u0000\u0000\u0000\u0090\u0099\u0099\u0099\u0099\u0099\u0009\u00ff\u00ff\u00ff\u00ff\r\u00fe\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0003\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0002\u0001\u0000\u0000\u0000\u0000\u0000 \u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000@\u0001\u0000\u0000\u0000\u0008\u0000\u0000\u0000\u0000\u0000pd\u0083t\u0014\u0002\u0000\u00aeW{3\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0099\u0099\u0009\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0090\u0099\u0099\u0099\u0099\u0009\u0001\u0000\u0000\u0000\u00fb\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0010\u0001\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0010\u0001\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000 \u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000 \u0000\u0001\u0001\u0000\u0000\u0000\u00fe\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000 \u0001\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00000\u0001\u0001\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000`\u0003\u0001\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0004\u0001\u0000\u0000\u0000\u00fd\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000P\u0001\u0099\u0081E\u0008f\u0015\u00960Ht9c\u00819\u0085\u0007\u0001\u0000\u0000\u0000\u0004\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000`\u0003\u0001\u0000\u0000\u0000\u0004\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000e\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000P\u0004\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000P\u0002\u0001\u0000\u0000\u0000\u0006\u0000\u0000\u0000\u0000\u0000\u0000\u0000#C\u0007\u0003\u0003P\u0099\u000935\u00083\u0001\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000S\u0002\u00ff\u00ff\u00ff\u00ff\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000S\u0002\u00ff\u00ff\u00ff\u00ff\u0002\u0000\u0000\u0000\u0093u\u0001\u0019\u0001\u0092\u0095\u0004\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u00156\u0007\u0006\u0096G\u0003\u0003\u00ff\u00ff\u00ff\u00ff\u0001\u0000\u0000\u0000\u0008\u0002\u0014\u0018HYe\u0007\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000w\u0003f\u0005IX3\u0009\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000W(\u0014W(\u0014!\u0001\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ffsF 3\u0085\u0093\u0018\u0009\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0004\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0005\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff2\u0014@\u0080\"\u0094\u0098\u0003\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00002\u0002\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000P\u0003\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0013f#\u0083R35\u0001\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u00873v\u0016Gfd\u0008\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00001F'(fP\u0002\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000 \u0003\u0001\u0000\u0000\u0000\u00f4\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u000c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u0003\u0000\u0000\u0000\u0000e\u00007hdw\u0001\u0001\u0000\u0000\u0000\u00e8\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0005\u0000\u0000\u0000\u0002\u0097dT\u0092\u0012\u0015\u0001\u00ff\u00ff\u00ff\u00ff\u0005\u0000\u0000\u0000\u0009F\u0006Ri\u0012\u0015\u0001\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000P\u0009\u0001\u0000\u0000\u0000\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000@\u0002\u0001\u0001\u0000\u0000\u0000\n\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000@\u0003\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000@IX\u0088)G\u0014\u0001\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000P\u0002\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000P\u0007\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ffHe\u0018\u0081g\u0010\u0007\u0007\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff3\u0014@\u0080\"\u0094\u0098\u0003\u0001\u0000\u0000\u0000\u00fa\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u00fc\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff3333333\u0008\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0099\u0099\u0099\u0099\u0099\u0099\u0099\u0009\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0002\u0001\u0000\u0000\u0000\u00fe\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000%\u0006\u0001\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000pCU\u0090\u0011S\u0002\u0001\u0000\u0000\u0000\u00fd\u00ff\u00ff\u00ff\u0007U\u0000By\u0096w\u0008\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff8\u0084w\u0096D\u0095d\u0007\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000U\u0011gg\u0093\u0014\u0097\u0002\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000u3i\u0003\u0080I\u0014\u0004\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0083\u00000\u0091YSv\u0002\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff'\u0094w\u0081V\u0004W\u0009\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0096\u0097P7\u0019\u0092e\u0001\u00ff\u00ff\u00ff\u00ff\u00fe\u00ff\u00ff\u00ff6\u0085\u0098\u0096\u0013\u0000\u0014\u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000r\u0003F\u0096F80\u0005\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000rVR7u\u0088\u0090\u0009\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u00008YTe\u0018\u0013\u0003\u0009\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000!#\u0085P\u0018aI\u0004\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0082$\u0014\u0092\u0016\u0000%\u0001\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0004\u0000Pr@8\u0082\u0001\u00ff\u00ff\u00ff\u00ff\u00fe\u00ff\u00ff\u00ffB\u0006\u0010\u00153\u0086\u0008\u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000U5\u0004uI(0\u0004\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u00097\u0018E\u0009\u00026\u0004\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000vD\u00882\u0013FE\u0009\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0087Q1e5g3\u0009\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0085\u0091!GFP0\u0005\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000F\u0088b\u0083QXw\u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00017\u00014\u0008\u0003d\u0003\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\"q\u0001\u0090T\u0013i\u0003\u0001\u0000\u0000\u0000\u00fe\u00ff\u00ff\u00ff\u0007\u0098I'05@\u0001\u0001\u0000\u0000\u0000\u00fc\u00ff\u00ff\u00ff\u0096\u0080\u0013\u0011Eq7\u0001\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000Sp\u0091\u0010%\u0014\u0000\u0002\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000I\u00918\u0096\u0093\u0098\u0007\u0007\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000g\u0094Q&w2\u0003\u0008\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000fS\u0015\u0012\u0015G\u0003\u0005\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000cBs\u0013 \u0098w\u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000pTI\u0094TU\u0084\u0003\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff$\u0081\u00019'6\u0099\u0003\u0001\u0000\u0000\u0000\u00fe\u00ff\u00ff\u00ff\u0019\"\u0095hphR\u0001\u0001\u0000\u0000\u0000\u00fc\u00ff\u00ff\u00ffh\u0086bg\u0000\u0087I\u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0091\u0012Sr%E$\u0003\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000B(\u0081HVb\u0085\u0006\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0014#\u00044yTv\u0003\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000T4\u00170\u0093\u0008$\u0001\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff7\u0091')\u0082\u0002t\u0001\u0001\u0000\u0000\u0000\u00fd\u00ff\u00ff\u00ff\u0011\u0093\u0009 4(\u0008\u0009\u0001\u0000\u0000\u0000\u00fc\u00ff\u00ff\u00ffx\"\u0018\u0012pxa\u0001\u0001\u0000\u0000\u0000\u00f9\u00ff\u00ff\u00ffPT0d\u0005t7\u0007\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000Q'\u0017H\u0009\u0015\u0002\u0006\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0094Vq\u0085c\u0084R\u0003\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u00000VV1\u0085\u0091(\u0001\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ffp\u0015&\u0014\u0090B\u0087\u0001\u0001\u0000\u0000\u0000\u00fd\u00ff\u00ff\u00ffd\u0099\u0008\u0092Uv\u0086\u0009\u0001\u0000\u0000\u0000\u00fc\u00ff\u00ff\u00ff&\u0084@CR\u0004v\u0001\u0001\u0000\u0000\u0000\u00f9\u00ff\u00ff\u00ffT\u0088\u0006P\u0088\u0082\u0002\u0008\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000w\u0002\u0013\u00091\u0003\u0002\u0002\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000A\u0087\u0015f 0\u0013\u0002\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ffq\u0098x!\"H\u0011\u0002\u00ff\u00ff\u00ff\u00ff\u00fd\u00ff\u00ff\u00ff\u0007FRa\u0009\u0009P\u0006\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ff47\u0007 \u0015\u0093'\u0007\u00ff\u00ff\u00ff\u00ff\u00fb\u00ff\u00ff\u00ff(\u0010Vg\u0004T'\u0001\u00ff\u00ff\u00ff\u00ff\u00f8\u00ff\u00ff\u00ff4\u0013v8f9C\u0006\u00ff\u00ff\u00ff\u00ff\u00f5\u00ff\u00ff\u00ff\u0016H\u00098((w\u0007\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000ESq\u0099\u0010\u0082'\u0002\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ffDp\u0088\u0083!S4\u0002\u00ff\u00ff\u00ff\u00ff\u00fd\u00ff\u00ff\u00ff\u0096\u0019\u0097\u0089\u0008g\u0091\u0006\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ff\u00867\u0087\u0008B\u0085\u0090\u0007\u00ff\u00ff\u00ff\u00ff\u00fb\u00ff\u00ff\u00ff\"\u0080\u00948Rv8\u0001\u00ff\u00ff\u00ff\u00ff\u00f8\u00ff\u00ff\u00ff\u0019Yu\u0086v\u0014\u0000\u0007\u00ff\u00ff\u00ff\u00ff\u00f5\u00ff\u00ff\u00ffh\u00877&\u0094\u0084E\u0008\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u00fb\u00ff\u00ff\u00ff\u0086e\u0083u\u0013(\u0097\u0006\u0001\u0000\u0000\u0000\u00fc\u00ff\u00ff\u00ffg\u0000r!\u0092\u0003\u0084\u0007\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ff\u0099\u0013b\u0093 G)\u0002\u00ff\u00ff\u00ff\u00ff\u00fd\u00ff\u00ff\u00ff\u0083\u0093\u0004\u0016'\u0013h\u0002\u0001\u0000\u0000\u0000\u00fd\u00ff\u00ff\u00ff\"\"\"\"\"\"G\u0003\u0001\u0000\u0000\u0000\u00fe\u00ff\u00ff\u00ff3333333\u0008\u0001\u0000\u0000\u0000\u00fc\u00ff\u00ff\u00ffaC\u00817\u0091s\u0014\u0007\u00ff\u00ff\u00ff\u00ff\u00fb\u00ff\u00ff\u00ffv\u0001\u0095\u0080H86\u0002\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ff3V@U7\u0002\u0095\u0005\u0001\u0000\u0000\u0000\u00fb\u00ff\u00ff\u00ff\u0019#\u0006&2\u0093\u0098\u0006\u0001\u0000\u0000\u0000\u00fc\u00ff\u00ff\u00ffuD'\u00844\u0003\u0084\u0007\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ff\u0019sxt\u0019G)\u0002\u00ff\u00ff\u00ff\u00ff\u00fd\u00ff\u00ff\u00ff0v\u0018\u0016'\u0013h\u0002\u0001\u0000\u0000\u0000\u00fd\u00ff\u00ff\u00ff\u00080\"\"\"\"G\u0003\u0001\u0000\u0000\u0000\u00fe\u00ff\u00ff\u00ff3333333\u0008\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u00fe\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000P\u0012\u0003\u0001\u0000\u0000\u0000\u00fe\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000P\u0012\u0003\u0001\u0000\u0000\u0000\u00fd\u00ff\u00ff\u00ff\u0089Cr\u0016t3\u0013\u0001\u0001\u0000\u0000\u0000\u00fd\u00ff\u00ff\u00ff\u0017\u0093\u0018&7\u0008\"\u0007\u0001\u0000\u0000\u0000\u00fd\u00ff\u00ff\u00ff\u00985P\u0015\u0011\u0019b\u0009\u00ff\u00ff\u00ff\u00ff\u00fe\u00ff\u00ff\u00ff\u0019174s\u0097!\u0004\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ffAD9\u0011\u0086Sf\u0001\u00ff\u00ff\u00ff\u00ff\u00fe\u00ff\u00ff\u00ff\u0011\u00024Pc\u0002 \u0004\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ffT\u0002Rq\u0080\u0087U\u0006\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff3\u0015\u0090dV!w\u0005\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u00fd\u00ff\u00ff\u00ffQ\u0081\u0013\u0005E9\u0019\u0001\u0001\u0000\u0000\u0000\u00fd\u00ff\u00ff\u00ff\u00916\u0080G\u0099\u0005\"\u0007\u00ff\u00ff\u00ff\u00ff\u00fd\u00ff\u00ff\u00ff'\u0006\u00046# b\u0009\u00ff\u00ff\u00ff\u00ff\u00fe\u00ff\u00ff\u00ff\u0092\u0005\u00076s\u0097!\u0004\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0081 7\u0011\u0086Sf\u0001\u00ff\u00ff\u00ff\u00ff\u00fe\u00ff\u00ff\u00ff4\u00034Pc\u0002 \u0004\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ffT\u0002Rq\u0080\u0087U\u0006\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff3\u0015\u0090dV!w\u0005\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u00fb\u00ff\u00ff\u00ffbqtH`'!\u0004\u0001\u0000\u0000\u0000\u00fc\u00ff\u00ff\u00ff\u0001\u0008\u0006\u00961)T\u0004\u0001\u0000\u0000\u0000\u00fd\u00ff\u00ff\u00ff\u0004\u0094\u0083\u0082f&\u0009\u0004\u0001\u0000\u0000\u0000\u00fe\u00ff\u00ff\u00ff\u0011a4$cS8\u0002\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff6\u0019`\u0081b0\u0011\u0001\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff$@fC\u0015\u0095b\u0003\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0013s\u00150\u0004\u00807\u0008\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u00fb\u00ff\u00ff\u00ff\u0017vtQHq9\u0001\u0001\u0000\u0000\u0000\u00fc\u00ff\u00ff\u00ffe`\u0091\u0005\u0084e4\u0002\u00ff\u00ff\u00ff\u00ff\u00fd\u00ff\u00ff\u00ff\u0015Sf$\u0099w#\u0001\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ffu\u0094$h3Y\u0095\u0007\u0001\u0000\u0000\u0000\u00fe\u00ff\u00ff\u00ff\u0007@XV\u00067w\u0002\u00ff\u00ff\u00ff\u00ff\u00fe\u00ff\u00ff\u00ffSD\u0012g\u00878c\u0004\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff4pV\u0090\u00105$\u0002\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ffG\u0088\u0005\u0095`\u0001\u0015\u0004\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ffsF 3\u0085\u0093\u0018\u0009\u0001\u0000\u0000\u0000\u00fd\u00ff\u00ff\u00ff'2$\u0014&P\u0088\u0004\u00ff\u00ff\u00ff\u00ff\u00fd\u00ff\u00ff\u00ff8\u0019\u0081\u0093\u0001\u0008\u0088\u0001\u0001\u0000\u0000\u0000\u00fc\u00ff\u00ff\u00ffP\"s)#'A\u0008\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ffie\u0017\u0085E#\u0095\u0005\u0001\u0000\u0000\u0000\u00fc\u00ff\u00ff\u00ff\u0007UXy\u0007e\u0093\u0007\u00ff\u00ff\u00ff\u00ff\u00fd\u00ff\u00ff\u00ff5Pwwwww\u0002\u0001\u0000\u0000\u0000\u00fe\u00ff\u00ff\u00ff3333333\u0008\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0003\u0000\u0000\u0000\u0081Cv\u0082\u00906\u0016\u0002\u00ff\u00ff\u00ff\u00ff\u0004\u0000\u0000\u0000QC(Rq8r\u0008\u00ff\u00ff\u00ff\u00ff\u0006\u0000\u0000\u0000F\u0091F\u0081&C\u0010\u0001\u00ff\u00ff\u00ff\u00ff\u0006\u0000\u0000\u0000!\u0005 \u0001%\u0012\u0011\u0006\u00ff\u00ff\u00ff\u00ff\u0007\u0000\u0000\u0000pC%\u0006hUb\u0001\u00ff\u00ff\u00ff\u00ff\u0007\u0000\u0000\u0000\u0081\u0003\u0081A79\u0000\u0002\u00ff\u00ff\u00ff\u00ff\u0006\u0000\u0000\u0000pP6xfV\u0087\u0008\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0002\u0000\u0000\u000075DH\u0081\u0094\u0013\u0005\u00ff\u00ff\u00ff\u00ff\u0004\u0000\u0000\u000004\u0005\u0084p5@\u0003\u00ff\u00ff\u00ff\u00ff\u0005\u0000\u0000\u0000!f@\u0016At\"\u0006\u00ff\u00ff\u00ff\u00ff\u0006\u0000\u0000\u0000\u0088\u0011\u00947@I\u0081\u0004\u00ff\u00ff\u00ff\u00ff\u0007\u0000\u0000\u0000\u0007Ep(3Tx\u0001\u00ff\u00ff\u00ff\u00ff\u0007\u0000\u0000\u0000\u0018Vv@F\u0086\u0013\u0003\u00ff\u00ff\u00ff\u00ff\u0007\u0000\u0000\u0000\u0089Wwq6\u0093\u0009\u0002\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u00f6\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0085\u0000\u0081\u0000O\u0005\u0000\u0012\u001f\u000c+\u00007&\u0008\u0012K\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u00fe\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0005\u001f\u001c\u001f\u001e\u001f\u001e\u001f\u001f\u001e\u001f\u001e\u001f\u0000\u0000\u0000\u0000\u001f\u0000\u0000\u0000;\u0000\u0000\u0000Z\u0000\u0000\u0000x\u0000\u0000\u0000\u0097\u0000\u0000\u0000\u00b5\u0000\u0000\u0000\u00d4\u0000\u0000\u0000\u00f3\u0000\u0000\u0000\u0011\u0001\u0000\u00000\u0001\u0000\u0000N\u0001\u0000\u0000\u0000\u000b\u000f\u0013\u0000\u0012\u0012\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u00fe\u00ff\u00ff\u00ff3\u0094\u0019%)St\u0001\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0009\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000P\u0004\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000y\u00895e\u0092\u0015\u0014\u0003\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00905e\u0092\u0015\u0014\u0003\u0000\u00905e\u0092\u0015\u0014\u0003\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0090\u0094g2\u0096\u0007W\u0001\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ffHt9c\u00819\u0085\u0007\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u00004\u0092\u0001I\u0094a5\u0002\u0001\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u00ff\u00ff\u00ff\u00ff\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00005\u0001\u0001\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000P\u0001\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0009\u0001\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0002\u0001\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0001Yev\u0086R\u0092vdXyq0\u00851(\u0006\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000`\u00039\u0016i!\u0013#\u0019f\u0089\u0094g2\u0096\u0007W\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0009\u0097\u0099\u0099\u0099\u0099\u0099\u0099\u0009g\u0096\u0099\u0099\u0099\u0099\u0099\u0009gf\u0096\u0099\u0099\u0099\u0099\u0009gff\u0096\u0099\u0099\u0099\u0009ghff\u0096\u0099\u0099\u0009$efhf\u0096\u0099\u0009 \u0016\u0091$eh\u0096\u0009\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000`\u0084\u0082\u0081\u0082q\u0002\u0003\u0000\u0000P\u0099\u0099\u0099\u00093\u0003\u0000\u0000\u0095\u0099\u0099\u000933\u0003\u0000P\u0099\u0099\u0009\u008303\u0003\u0000\u0095\u0099\u000935\u00083\u0003P\u0099\u0009\u0008h1\u00850\u0003\u0095\u0009I2\u0004\u0098\u0017\u0010S\u0009E\u0099U\u0080q\u0014\u0093\u0006\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0005\u0094)\u0009\u0085%0\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0084V\u0004\u0094)\u0009\u0085%0\u0002-Inf\u0000\u0000\n\u0013\u0018\u0000\r\r\r\u0000\r\r\r\u0000\r\r\r\u0000\r\r\r\u0000\r\r\r\r\u0000\r\r\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\r\u0000\u0000\u0000\u0000\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\u0000\u0000\u0000\u0000\u0000\r\r\r\u0000\r\r\r\u0000\r\r\r\u0000\r\r\r\u0000\r\r\r\r\r\r\r\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\u0000\r\u0000\r\u0000\r\r\r\u0000\r\r\r\u0000\r\r\r\u0000\u0000\u0000\u008b\u0000\u0000\u0000\u008f\u0000\u0000\u0000\u0090\u0000\u0000\u0000\u0092\u0000\u0000\u0000\u0093\u0000\u0000\u0000\u0094\u0000\u0000\u0000\u0097\u0000\u0000\u0000\u0098\u0000\u0000\u0000\u0099\u0000\u0000\u0000\u009b\u0000\u0000\u0000\u009c\u0000\u0000\u0000\u009d\u0000\u0000\u0000\u009e\u0000\u0000\u0000\u009f\u0000\u0000\u0000\u00a0\u0000\u0000\u0000\u00a2\u0000\u0000\u0000\u00a3\u0000\u0000\u0000\u00a4\u0000\u0000\u0000\u00a5\u0000\u0000\u0000\u00a6\u0000\u0000\u0000\u00a7\u0000\u0000\u0000\u00a8\u0000\u0000\u0000\u00a9\u0000\u0000\u0000\u00aa\u0000\u0000\u0000\u00ab\u0000\u0000\u0000\u00ac\u0000\u0000\u0000\u00ad\u0000\u0000\u0000\u00ae\u0000\u0000\u0000\u00af\u0000\u0000\u0000\u00b0\u0000\u0000\u0000\u00b1\u0000\u0000\u0000\u0000\u0017\u0017\u0017\u0017\u0006\u0017\u0017\u0017\u0008\n\u0017\u0017\u0017\u000c\u0017\u0017\u000e\u0017\u0017\u0017\u0017\u0017\u0017\u0017\u0017\u0017\u0017\u0017\u0017\u0017\u0010\u0012\u0014\u001655\u0000\u0000\u0000\u0000\u0000\u0000\u0000::::::::::::::::::::::::::::::::::::::::\u0000\u0000\u0000\u0000\u0006\u0000\u0006\u000f\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0006\u001d000\u0000\u0002\u00040000000000000\u0006\u0008\n\u000c\u000e000000000000000000000000000000000000000\u0010000\u001200\u00140000000000000\u0016\u0018\u001a\u001c\u0000\u0000Y\u0000\u0000\u0000\u0019\u0000\u0000\u0000Y\u0000\u0000\u0000]\u0000\u0000\u0000\u001d\u0000\u0000\u0000]\u0000\u0000\u0000^\u0000\u0000\u0000\u001e\u0000\u0000\u0000^\u0000\u0000\u0000_\u0000\u0000\u0000\u001f\u0000\u0000\u0000_\u0000\u0000\u0000m\u0000\u0000\u0000-\u0000\u0000\u0000m\u0000\u0000\u0000n\u0000\u0000\u0000.\u0000\u0000\u0000n\u0000\u0000\u0000o\u0000\u0000\u0000/\u0000\u0000\u0000o\u0000\u0000\u0000p\u0000\u0000\u00000\u0000\u0000\u0000p\u0000\u0000\u0000q\u0000\u0000\u00001\u0000\u0000\u0000q\u0000\u0000\u0000\u0099\u0000\u0000\u0000\u0019\u0000\u0000\u0000\u0019\u0000\u0000\u0000\u009d\u0000\u0000\u0000\u001d\u0000\u0000\u0000\u001d\u0000\u0000\u0000\u00a0\u0000\u0000\u0000\u00b1\u0000\u0000\u0000\u00a0\u0000\u0000\u0000\u00ae\u0000\u0000\u0000.\u0000\u0000\u0000.\u0000\u0000\u0000\u00af\u0000\u0000\u0000/\u0000\u0000\u0000\u00af\u0000\u0000\u0000\u00b0\u0000\u0000\u00000\u0000\u0000\u00000\u0000\u0000\u0000\u00b1\u0000\u0000\u00001\u0000\u0000\u0000\u00b1\u0000\u0000\u0000e\u0000\u009e\u0000\u00a1\u0000I\u0001\u00a4\u0000\u00a7\u0000\u00aa\u0000I\u0001\u00ad\u0000h\u0000\u00b0\u0000I\u0001k\u0000n\u0000\u00b3\u0000,\u0000\u00b6\u0000\u00b9\u0000\u00bc\u0000\u0000\u0000\u00bf\u0000\u00c2\u0000\u0003\u0000I\u0001q\u0000\u00c5\u0000t\u0000w\u0000z\u0000}\u0000\u00c8\u0000\u00cb\u0000\u00ce\u0000>\u0000\u0080\u0000\u0006\u0000\u0009\u0000\u000c\u0000\u000f\u0000\u0012\u0000I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001\u0083\u0000\u00d1\u0000\u00d4\u0000I\u0001\u0009\u0001\u00d7\u0000\u0086\u0000I\u0001A\u0000\u000c\u0001\u000f\u0001I\u0001\u0089\u0000\u00da\u0000\u00dd\u0000,\u0000D\u0000\u00e0\u0000\u00e3\u0000\u0000\u0000\u001b\u0000\u0012\u0001\u0015\u0001I\u0001\u00e6\u0000\u00e9\u0000\u008c\u0000\u0018\u0001G\u0000\u001b\u0001\u001e\u0001\u00ec\u0000!\u0001\u00ef\u0000J\u0000\u0006\u0000\u0009\u0000\u000c\u0000\u000f\u0000\u0012\u0000I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001\u008f\u0000\u00f2\u0000M\u0000I\u00010\u0001\u001e\u0000\u0092\u0000I\u0001!\u0000$\u0000\u0095\u0000I\u0001\u00f5\u0000P\u0000S\u0000'\u0000$\u0001\u0098\u0000V\u0000\u00f8\u0000/\u00003\u0001\u0003\u0000I\u00012\u00005\u0000Y\u00008\u00006\u0001?\u0001'\u0001\u0015\u00009\u0001\u009b\u0000<\u0001\\\u0000*\u0001;\u0000\u0000\u0001\u0003\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001I\u0001_\u0000-\u0001I\u0001I\u0001\u0006\u0001\u0018\u0000I\u0001I\u0001b\u0000+.0000000000E+00\u0000\u0000\u0015\r\u0000%\u00007\u0015)\u0000\u0017\u000b\u0011\u0005\u0000\u001c\n\u000f\u0006 $\u0013\u0000\u0000%\u0006\u0083+.0000000000E+00\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0003\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0003\u0000\u0000\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u0000\u0000+.0000000000E+00\u0000\u0000\u0000\u0000\u0005\u0000\u0000\u0000\u0007\u0000\u0000\u0000\u0007\u0000\u0000\u0000\u0003\u0000\u0000\u0000\u0007\u0000\u0000\u0000\u0003\u0000\u0000\u0000%\u0000\u0000\u0000\u0000\u0000\u0000\u0000$\u0000\u0000\u0000\u0001\u0000\u0000\u0000(\u0000\u0000\u0000\u0002\u0000\u0000\u0000,\u0000\u0000\u0000\u0003\u0000\u0000\u0000#\u0000\u0000\u0000\u0004\u0000\u0000\u0000'\u0000\u0000\u0000\u0005\u0000\u0000\u0000+\u0000\u0000\u0000\u0006\u0000\u0000\u0000\"\u0000\u0000\u0000\u0007\u0000\u0000\u0000&\u0000\u0000\u0000\u0008\u0000\u0000\u0000*\u0000\u0000\u0000\u0009\u0000\u0000\u0000.\u0000\u0000\u0000\n\u0000\u0000\u0000\n\u0000\u0000\u0000\u000b\u0000\u0000\u0000\u000e\u0000\u0000\u0000\u000c\u0000\u0000\u0000\u0012\u0000\u0000\u0000\r\u0000\u0000\u0000\u0016\u0000\u0000\u0000\u000e\u0000\u0000\u0000\u001a\u0000\u0000\u0000\u000f\u0000\u0000\u0000\u001e\u0000\u0000\u0000\u0010\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0011\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0012\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0013\u0000\u0000\u0000/\u0000\u0000\u0000\u0014\u0000\u0000\u0000\u000b\u0000\u0000\u0000\u0015\u0000\u0000\u0000\u000f\u0000\u0000\u0000\u0016\u0000\u0000\u0000\u0013\u0000\u0000\u0000\u0017\u0000\u0000\u0000\u0017\u0000\u0000\u0000\u0018\u0000\u0000\u0000\u001b\u0000\u0000\u0000\u0019\u0000\u0000\u0000\u001f\u0000\u0000\u0000\u001a\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u001b\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u001c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u001d\u0000\u0000\u00000\u0000\u0000\u0000\u001e\u0000\u0000\u0000\u000c\u0000\u0000\u0000\u001f\u0000\u0000\u0000\u0000\u0000\u0000\u0000 \u0000\u0000\u0000\u0014\u0000\u0000\u0000!\u0000\u0000\u0000\u0018\u0000\u0000\u0000\"\u0000\u0000\u0000\u001c\u0000\u0000\u0000#\u0000\u0000\u0000 \u0000\u0000\u0000$\u0000\u0000\u0000",
        "\u0000\u0000\u0000\u0000%\u0000\u0000\u0000\u0000\u0000\u0000\u0000&\u0000\u0000\u0000\u0000\u0000\u0000\u0000'\u0000\u0000\u00001\u0000\u0000\u0000(\u0000\u0000\u0000\u0000\u0000\u0000\u0000)\u0000\u0000\u0000\u0000\u0000\u0000\u0000*\u0000\u0000\u0000\u0000\u0000\u0000\u0000+\u0000\u0000\u0000\u0000\u0000\u0000\u0000,\u0000\u0000\u0000\u0000\u0000\u0000\u0000-\u0000\u0000\u0000\u0000\u0000\u0000\u0000.\u0000\u0000\u0000\u0000\u0000\u0000\u0000/\u0000\u0000\u0000)\u0000\u0000\u00000\u0000\u0000\u0000-\u0000\u0000\u00001\u0000\u0000\u0000e\u0000\u0000\u00002\u0000\u0000\u0000J\u0000\u0000\u00003\u0000\u0000\u0000N\u0000\u0000\u00004\u0000\u0000\u0000R\u0000\u0000\u00005\u0000\u0000\u0000V\u0000\u0000\u00006\u0000\u0000\u0000Z\u0000\u0000\u00007\u0000\u0000\u0000^\u0000\u0000\u00008\u0000\u0000\u0000b\u0000\u0000\u00009\u0000\u0000\u0000f\u0000\u0000\u0000:\u0000\u0000\u0000j\u0000\u0000\u0000;\u0000\u0000\u0000i\u0000\u0000\u0000<\u0000\u0000\u0000K\u0000\u0000\u0000=\u0000\u0000\u0000O\u0000\u0000\u0000>\u0000\u0000\u0000S\u0000\u0000\u0000?\u0000\u0000\u0000W\u0000\u0000\u0000@\u0000\u0000\u0000[\u0000\u0000\u0000A\u0000\u0000\u0000_\u0000\u0000\u0000B\u0000\u0000\u0000c\u0000\u0000\u0000C\u0000\u0000\u0000g\u0000\u0000\u0000D\u0000\u0000\u0000k\u0000\u0000\u0000E\u0000\u0000\u0000l\u0000\u0000\u0000F\u0000\u0000\u0000\u0000\u0000\u0000\u0000G\u0000\u0000\u0000P\u0000\u0000\u0000H\u0000\u0000\u0000\u0000\u0000\u0000\u0000I\u0000\u0000\u0000X\u0000\u0000\u0000J\u0000\u0000\u0000\u0000\u0000\u0000\u0000K\u0000\u0000\u0000\u0000\u0000\u0000\u0000L\u0000\u0000\u0000d\u0000\u0000\u0000M\u0000\u0000\u0000h\u0000\u0000\u0000N\u0000\u0000\u0000\u0000\u0000\u0000\u0000O\u0000\u0000\u0000\u00a5\u0000\u0000\u0000P\u0000\u0000\u0000\u00a4\u0000\u0000\u0000Q\u0000\u0000\u0000\u00a8\u0000\u0000\u0000R\u0000\u0000\u0000\u00ac\u0000\u0000\u0000S\u0000\u0000\u0000\u0000\u0000\u0000\u0000T\u0000\u0000\u0000\u0000\u0000\u0000\u0000U\u0000\u0000\u0000\u00ab\u0000\u0000\u0000V\u0000\u0000\u0000\u00a2\u0000\u0000\u0000W\u0000\u0000\u0000\u00a6\u0000\u0000\u0000X\u0000\u0000\u0000\u0000\u0000\u0000\u0000Y\u0000\u0000\u0000\u00ae\u0000\u0000\u0000Z\u0000\u0000\u0000\u008a\u0000\u0000\u0000[\u0000\u0000\u0000\u008e\u0000\u0000\u0000\\\u0000\u0000\u0000\u0092\u0000\u0000\u0000]\u0000\u0000\u0000\u0096\u0000\u0000\u0000^\u0000\u0000\u0000\u009a\u0000\u0000\u0000_\u0000\u0000\u0000\u009e\u0000\u0000\u0000`\u0000\u0000\u0000\u0000\u0000\u0000\u0000a\u0000\u0000\u0000\u0000\u0000\u0000\u0000b\u0000\u0000\u0000\u0000\u0000\u0000\u0000c\u0000\u0000\u0000\u00af\u0000\u0000\u0000d\u0000\u0000\u0000\u008b\u0000\u0000\u0000e\u0000\u0000\u0000\u008f\u0000\u0000\u0000f\u0000\u0000\u0000\u0093\u0000\u0000\u0000g\u0000\u0000\u0000\u0097\u0000\u0000\u0000h\u0000\u0000\u0000\u009b\u0000\u0000\u0000i\u0000\u0000\u0000\u009f\u0000\u0000\u0000j\u0000\u0000\u0000\u0000\u0000\u0000\u0000k\u0000\u0000\u0000\u0000\u0000\u0000\u0000l\u0000\u0000\u0000\u0000\u0000\u0000\u0000m\u0000\u0000\u0000\u00b0\u0000\u0000\u0000n\u0000\u0000\u0000\u008c\u0000\u0000\u0000o\u0000\u0000\u0000\u0000\u0000\u0000\u0000p\u0000\u0000\u0000\u0000\u0000\u0000\u0000q\u0000\u0000\u0000\u0098\u0000\u0000\u0000r\u0000\u0000\u0000\u009c\u0000\u0000\u0000s\u0000\u0000\u0000\u00a0\u0000\u0000\u0000t\u0000\u0000\u0000\u0000\u0000\u0000\u0000u\u0000\u0000\u0000\u0000\u0000\u0000\u0000v\u0000\u0000\u0000\u0000\u0000\u0000\u0000w\u0000\u0000\u0000\u00b1\u0000\u0000\u0000x\u0000\u0000\u0000\u0000\u0000\u0000\u0000y\u0000\u0000\u0000\u0000\u0000\u0000\u0000z\u0000\u0000\u0000\u0000\u0000\u0000\u0000{\u0000\u0000\u0000\u0099\u0000\u0000\u0000|\u0000\u0000\u0000\u009d\u0000\u0000\u0000}\u0000\u0000\u0000\u0000\u0000\u0000\u0000~\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u007f\u0000\u0000\u0000\u00a9\u0000\u0000\u0000\u0080\u0000\u0000\u0000\u00ad\u0000\u0000\u0000\u0081\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0082\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0083\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0084\u0000\u0000\u0000n\u0000\u0000\u0000\n\u0000\u0000\u0000o\u0000\u0000\u0000\u0014\u0000\u0000\u0000p\u0000\u0000\u0000\u001e\u0000\u0000\u0000q\u0000\u0000\u0000(\u0000\u0000\u0000m\u0000\u0000\u00001\u0000\u0000\u0000\u0000WWWWWWEH<?WWWWWWBKNQWWWWWWT9\u0000\u00af\u00b2\u00b5\u00b8\u00bb\u00be\u00c1\u00c4\u00c7\u0003\u000b\u0013\u001b#+3;CKVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV`cVfi\u0000\u001d #&\u0013=========\u0000=========\u0010=========\u0012\u0000\u00af\u00b2\u00b5\u00b8\u00bb\u00be\u00c1\u00c4\u00c7\u0000\u0000}}}}}}}}}}}.}}}}}}!\u00148\u0000\u0000i\u0001i\u0001i\u0001i\u0001i\u0001i\u0001i\u0001i\u0001i\u0001i\u0001i\u0001\u001f\u0001i\u0001i\u0001i\u0001i\u0001i\u0001i\u0001/\u0001\u0010\u0001>\u0001\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0000\u0000;\u0000\u0000;;;;;;;;;;;;;;;\u0000;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;3;;;;;;;;;;;;;;5;;;8\u0000\u0000\u000f\u0005\u0017\u001c\n\u0000FJNN\u0005\u0005\u0005\u0000\u0005\u00057\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u0005\u00059\u0005\"\u0000\u0013\u0013\u0013\u000e\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u0013\u00130U0\\Moooooooo\u0000ooo+oooooooooooooooooooooooooooooooooooooooooooooo3ooooooocoooo\u0000oo +oo \u0000bbbfbbbbbbbbbbbbbbwbbb|bbk",
    };

    private static final String[] RWDATA_CHUNKS = new String[] {
        "\u0001\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff\u0000@f\u0095\u0099\u0002\u0001\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000",
    };

    private static final String[] STRPOOL_CHUNKS = new String[] {
        " Pr ErrOr\u0000 RUNNING\u00009999999999\u0000\u0000000\u000000-\u0000",
    };

    private static void unpackChunks(String[] chunks, byte[] dest, int destOff) {
        int pos = destOff;
        for (String chunk : chunks) {
            byte[] b = chunk.getBytes(StandardCharsets.ISO_8859_1);
            System.arraycopy(b, 0, dest, pos, b.length);
            pos += b.length;
        }
    }

    protected void initMemory(Callback cb, String statePath) {
        this.callback = cb;
        this.stateFilePath = statePath;
        Arrays.fill(memBytes, (byte) 0);
        unpackChunks(RODATA_CHUNKS, memBytes, (int) ADDR_RODATA);
        unpackChunks(RWDATA_CHUNKS, memBytes, (int) ADDR_RWDATA);
        unpackChunks(STRPOOL_CHUNKS, memBytes, (int) ADDR_STRPOOL);
        initInternalPointers();
        putI64(g_StatusIndicators, 0x3030303030303030L);
        putI32(g_StatusIndicators + 8L, 0x00303030L);
        if (statePath != null) {
            File f = new File(statePath);
            if (f.exists() && f.length() == 4 + 0x15b0) {
                try (FileInputStream fis = new FileInputStream(f)) {
                    byte[] hdr = new byte[4];
                    if (fis.read(hdr) == 4 && ByteBuffer.wrap(hdr).order(ByteOrder.LITTLE_ENDIAN).getInt(0) == STATE_MAGIC) {
                        int read = fis.read(memBytes, (int) ADDR_RWDATA, 0x15b0);
                        if (read == 0x15b0) {
                            initInternalPointers();
                            isFileRead = true;
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }
    }

    protected void initInternalPointers() {
        // pCodeTable = g_rodata + 0x1ae3
        putI64(pCodeTable, ProgKeyCodeDefine);
        // g_undo_list pointers
        putI64(g_undo_list + 8L, g_undo_list_pUndoBuffer);
        for (int i = 1; i < 14; i++) {
            putI64(g_undo_list + 8L + i * 24L, g_undo_list_pUndoBuffer + i * 24L);
        }
        // g_fin_tvm_pinput pointers
        putI64(g_fin_tvm_pinput, g_fin_n);
        putI64(g_fin_tvm_pinput + 8L, g_fin_i);
        putI64(g_fin_tvm_pinput + 16L, g_fin_pv);
        putI64(g_fin_tvm_pinput + 24L, g_fin_pmt);
        putI64(g_fin_tvm_pinput + 32L, g_fin_fv);
        putI64(g_fin_tvm_pinput + 40L, g_fin_p_yr);
    }

    protected int writeStateFile() {
        if (stateFilePath == null) return 0;
        try {
            File f = new File(stateFilePath);
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            try (FileOutputStream fos = new FileOutputStream(f)) {
                byte[] hdr = new byte[4];
                ByteBuffer.wrap(hdr).order(ByteOrder.LITTLE_ENDIAN).putInt(0, STATE_MAGIC);
                fos.write(hdr);
                fos.write(memBytes, (int) ADDR_RWDATA, 0x15b0);
            }
        } catch (Exception ignored) {
        }
        return 0;
    }

    long allocFrame(int size) {
        int aligned = (size + 15) & ~15;
        sp -= aligned;
        Arrays.fill(memBytes, sp, sp + aligned, (byte) 0);
        return (long) sp;
    }

    long getI64(long addr) { return mem.getLong((int) addr); }
    long getI32(long addr) { return (long) mem.getInt((int) addr); }
    long getU32(long addr) { return ((long) mem.getInt((int) addr)) & 0xffffffffL; }
    long getI16(long addr) { return (long) mem.getShort((int) addr); }
    long getU16(long addr) { return ((long) mem.getShort((int) addr)) & 0xffffL; }
    long getI8(long addr) { return (long) memBytes[(int) addr]; }
    long getU8(long addr) { return ((long) memBytes[(int) addr]) & 0xffL; }
    long getBool(long addr) { return (memBytes[(int) addr] != 0) ? 1L : 0L; }

    long putI64(long addr, long val) { mem.putLong((int) addr, val); return val; }
    long putI32(long addr, long val) { mem.putInt((int) addr, (int) val); return (long) (int) val; }
    long putU32(long addr, long val) { mem.putInt((int) addr, (int) val); return val & 0xffffffffL; }
    long putI16(long addr, long val) { mem.putShort((int) addr, (short) val); return (long) (short) val; }
    long putU16(long addr, long val) { mem.putShort((int) addr, (short) val); return val & 0xffffL; }
    long putI8(long addr, long val) { memBytes[(int) addr] = (byte) val; return (long) (byte) val; }
    long putU8(long addr, long val) { memBytes[(int) addr] = (byte) val; return val & 0xffL; }
    long putBool(long addr, long val) { byte b = (byte) ((val != 0L) ? 1 : 0); memBytes[(int) addr] = b; return (long) b; }

    CC_Real getCCReal(long addr) {
        int a = (int) addr;
        return new CC_Real((long) mem.getInt(a), (long) mem.getInt(a + 4), mem.getLong(a + 8));
    }

    CC_Real putCCReal(long addr, CC_Real val) {
        int a = (int) addr;
        mem.putInt(a, (int) val.s);
        mem.putInt(a + 4, (int) val.e);
        mem.putLong(a + 8, val.m);
        return val.copy();
    }

    static long comma(long a, long b) { return b; }
    static long comma(CC_Real a, long b) { return b; }
    static CC_Real comma(long a, CC_Real b) { return b; }
    static CC_Real comma(CC_Real a, CC_Real b) { return b; }
    static void sink(long v) {}
    static void sink(CC_Real v) {}

    long memcpy(long dst, long src, long n) {
        int len = (int) n;
        if (len > 0) {
            System.arraycopy(memBytes, (int) src, memBytes, (int) dst, len);
        }
        return dst;
    }

    long memset(long dst, long c, long n) {
        int len = (int) n;
        if (len > 0) {
            Arrays.fill(memBytes, (int) dst, (int) dst + len, (byte) c);
        }
        return dst;
    }

    long strlen(long s) {
        int p = (int) s;
        while (memBytes[p] != 0) {
            p++;
        }
        return (long) (p - (int) s);
    }

    long strcpy(long dst, long src) {
        int d = (int) dst;
        int s = (int) src;
        while (true) {
            byte b = memBytes[s++];
            memBytes[d++] = b;
            if (b == 0) break;
        }
        return dst;
    }

    long atoi(long s) {
        int p = (int) s;
        while (memBytes[p] == ' ' || memBytes[p] == '\t' || memBytes[p] == '\n') p++;
        int sign = 1;
        if (memBytes[p] == '-') { sign = -1; p++; }
        else if (memBytes[p] == '+') { p++; }
        int val = 0;
        while (memBytes[p] >= '0' && memBytes[p] <= '9') {
            val = val * 10 + (memBytes[p] - '0');
            p++;
        }
        return (long) (sign * val);
    }

    long usleep(long usec) {
        try {
            Thread.sleep(Math.max(1L, usec / 1000L));
        } catch (InterruptedException ignored) {
        }
        return 0L;
    }

    long cc_wait(long count) {
        return (long) (int) count;
    }

    String readCString(long addr, int maxLen) {
        int start = (int) addr;
        int len = 0;
        while (len < maxLen && memBytes[start + len] != 0) {
            len++;
        }
        return new String(memBytes, start, len, StandardCharsets.ISO_8859_1);
    }

    String getResultPlusStatus() {
        String res = readCString(g_ResultStub, 0x28);
        String status = readCString(g_StatusIndicators, 0x0c);
        return res + status;
    }

    long handleKeyBuffer(long command) {
        return (callback != null) ? (long) callback.handleKeybuffer((int) command) : -1L;
    }

    long KeypadDriver_ClearKeyBuffer() {
        if (callback != null) callback.handleKeybuffer(0);
        return 0L;
    }

    long KeypadDriver_GetKey() {
        int r = (callback != null) ? callback.handleKeybuffer(1) : -1;
        return (r != -1) ? (long) r : 0L;
    }

    long KeypadDriver_PeekKey() {
        int r = (callback != null) ? callback.handleKeybuffer(2) : -1;
        return (r != -1) ? (long) r : 0L;
    }

    void UpdateLcdDisplay() {
        if (callback != null) {
            callback.updateLcd(getResultPlusStatus());
        }
    }

    void LcdDriver_PrintCalculatorLine(long strPtr, long cursorMode) {
        strcpy(g_ResultStub, strPtr);
        UpdateLcdDisplay();
    }

    long callIndirect0(long fn) {
        return 0L;
    }

    long callIndirect1(long fn, long a0) {
        switch ((int) fn) {
            case (int) FPTR_IRRMIRRNextEntry: IRRMIRRNextEntry(a0); return a0 + 24L;
            case (int) FPTR_sRegTransformLogLogistic: return sRegTransformLogLogistic(a0);
            case (int) FPTR_sRegTransformLogistic: return sRegTransformLogistic(a0);
            case (int) FPTR_sRegTransformLnX: return sRegTransformLnX(a0);
            case (int) FPTR_sRegTransformExpY: return sRegTransformExpY(a0);
            case (int) FPTR_sRegTransformInvY: return sRegTransformInvY(a0);
            case (int) FPTR_sRegTransformPowXY: return sRegTransformPowXY(a0);
            default: return 0L;
        }
    }

    long callIndirect2(long fn, long a0, long a1) {
        switch ((int) fn) {
            case (int) FPTR_UserStop: return UserStop(a0, a1);
            default: return 0L;
        }
    }

    long callIndirect3(long fn, long a0, long a1, long a2) {
        switch ((int) fn) {
            case (int) FPTR_myTGetEntryIRR: return myTGetEntryIRR(a0, a1, a2);
            case (int) FPTR_IRRMIRRNextEntry: IRRMIRRNextEntry(a0); return a0 + 24L;
            case (int) FPTR_TVMIConvRateFunc: return TVMIConvRateFunc(a0, a1, a2);
            case (int) FPTR_sPolyEvalFunc: return sPolyEvalFunc(a0, a1, a2);
            case (int) FPTR_sRegTransformLogLogistic: return sRegTransformLogLogistic(a0);
            case (int) FPTR_sRegTransformLogistic: return sRegTransformLogistic(a0);
            case (int) FPTR_sRegTransformLnX: return sRegTransformLnX(a0);
            case (int) FPTR_sRegTransformExpY: return sRegTransformExpY(a0);
            case (int) FPTR_sRegTransformInvY: return sRegTransformInvY(a0);
            case (int) FPTR_sRegTransformPowXY: return sRegTransformPowXY(a0);
            case (int) FPTR_RegStatsGetEntry: return RegStatsGetEntry(a0, a1, a2);
            default: return 0L;
        }
    }

    long callIndirect4(long fn, long a0, long a1, long a2, long a3) {
        return 0L;
    }

    long callIndirect5(long fn, long a0, long a1, long a2, long a3, long a4) {
        return 0L;
    }

    long callIndirect6(long fn, long a0, long a1, long a2, long a3, long a4, long a5) {
        return 0L;
    }

    void strend(long strPtr) {
        long strPtr1 = 0L;
        strPtr1 = strPtr - 1L;
        do {
            strPtr1 = strPtr1 + 1L;
        } while (getI8(strPtr1) != 0L);
    }

    void memset4(long destPtr, long wordPairVal, long byteCount) {
        while (comma(byteCount = (byteCount - 4L) & 0xffffffffL, (byteCount & 0xffffffffL) != 0L ? 1L : 0L) != 0L) {
            putI64(destPtr, wordPairVal);
            destPtr = destPtr + 8L;
        }
    }

    void memclr4(long destPtr, long byteCount) {
        while (comma(byteCount = (byteCount - 4L) & 0xffffffffL, (byteCount & 0xffffffffL) != 0L ? 1L : 0L) != 0L) {
            putI64(destPtr, 0L);
            destPtr = destPtr + 8L;
        }
    }

    long ClearStackAndSize() {
        return 0xffL;
    }

    long itoa_(long value, long outStr, long radix) {
        long tempVal1 = 0L;
        long byteVal = 0L;
        long intVal1 = 0L;
        long intVal2 = 0L;
        long tempVal2 = 0L;
        long strPtr = 0L;
        long srcPtr = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            if (((radix - 2L) & 0xffffL) < 35L) {
                intVal2 = (short) radix;
                tempVal2 = -(value & 0xffffL) & 0xffffffffL;
                if (-1L < (short) value || (short) radix != 10L) {
                    tempVal2 = value & 0xffffL;
                }
                putI8(framePtr + 37L, 0L);
                strPtr = framePtr + 36L;
                do {
                    srcPtr = strPtr;
                    tempVal1 = tempVal2 & 0xffffL;
                    tempVal2 = 0L;
                    if (intVal2 != 0L) {
                        tempVal2 = ((long) (int) tempVal1 / intVal2) & 0xffffffffL;
                    }
                    intVal1 = (long) (int) (tempVal1 - tempVal2 * intVal2);
                    tempVal1 = (intVal1 + 48L) & 0xffffffffL;
                    byteVal = (long) (byte) (intVal1 + 87L);
                    if ((tempVal1 & 0xffL) < 58L) {
                        byteVal = (long) (byte) tempVal1;
                    }
                    strPtr = srcPtr - 1L;
                    putI8(srcPtr, byteVal);
                } while ((tempVal2 & 0xffffL) != 0L);
                if (-1L >= (short) value && (short) radix == 10L) {
                    putI8(strPtr, 45L);
                    srcPtr = strPtr;
                }
                memcpy(outStr, srcPtr, (framePtr + 37L - srcPtr) & 0xffffffffL);
            } else {
                putI8(outStr, 0L);
            }
            return outStr;
        } finally {
            sp = savedSp;
        }
    }

    abstract void IRRMIRRNextEntry(long iterStatePtr);
    abstract long sRegTransformLogLogistic(long entryPtr);
    abstract long sRegTransformLogistic(long entryPtr);
    abstract long sRegTransformLnX(long entryPtr);
    abstract long sRegTransformExpY(long entryPtr);
    abstract long sRegTransformInvY(long entryPtr);
    abstract long sRegTransformPowXY(long entryPtr);
    abstract long UserStop(long iterCount, long currentGuessPtr);
    abstract long myTGetEntryIRR(long cfloDataPtr, long entryIndex, long outEntryPtr);
    abstract long TVMIConvRateFunc(long ratePtr, long contextPtr, long outValPtr);
    abstract long sPolyEvalFunc(long xValPtr, long contextPtr, long outDiffPtr);
    abstract long RegStatsGetEntry(long statRegsPtr, long entryIndex, long outPairPtr);
    abstract long DateValidExtract(long datePtr, long outDayPtr, long outMonthPtr, long outYearPtr, long outDayOfWeekPtr, long isDmy, long checkGregorian);
    abstract long BF123_CheckRealData_Ex(long realPtr);
    abstract long Basic_GetExpressState();
    abstract void Basic_ChangeExpressState(long newState);
    abstract void Basic_SetExpressState(long exprState);
    abstract long LcdDriver_ClearAnnunciator(long annunciatorId);
    abstract long Basic_PrintResult(long realPtr);
    abstract void Prog_Init();
    abstract long Basic_InitBasicVar();
    abstract long Basic_PrintReal(long realPtr);
    abstract long Basic_EndEditMode();
    abstract long Basic_EndCaculateNumBefore();
    abstract long Basic_PrintEditLine(long editBufPtr);
    abstract long Basic_ProcessUndoModeKeys();
    abstract long Basic_DisableUndoMode();
    abstract long Prog_ClearWarning();
    abstract long Base_HandleKeyUp(long keyCode, long shouldPrint);
    abstract long Base_HandleKeyDown(long keyCode, long shouldPrint);
    abstract long Prog_HandleKeyUp(long keyCode);
    abstract long Prog_HandleKeyDown(long keyCode);
    abstract long Prog_SetGTOVal(long digitKey);
    abstract long Fin_IRR(long outIrrPtr, long hasGuess);
}

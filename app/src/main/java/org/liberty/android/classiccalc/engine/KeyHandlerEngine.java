package org.liberty.android.classiccalc.engine;

/**
 * Key press handlers for standard, financial, statistical, and date keys.
 */
abstract class KeyHandlerEngine extends ProgramEngine {
    long Base_HandleKeyUp(long keyCode, long shouldPrint) {
        long result = 0L;
        if ((long) (int) keyCode >= 140L) {
            if ((long) (int) keyCode == 140L) {
                Base_KeyUp_PSE(shouldPrint & 0xffL);
            } else {
                if ((long) (int) keyCode == 144L) {
                    Base_KeyUp_BST(shouldPrint & 0xffL);
                } else {
                    if ((long) (int) keyCode != 170L) {
                        return 99L;
                    }
                    Base_KeyUp_MEM(shouldPrint & 0xffL);
                }
            }
            result = 99L;
            return result;
        }
        if ((long) (int) keyCode == 12L) {
            result = Base_KeyUp_R_S(shouldPrint & 0xffL) & 0xffffffffL;
        } else {
            if ((long) (int) keyCode != 16L) {
                if ((long) (int) keyCode != 96L) {
                    return 99L;
                }
                Base_KeyUp_PREFIX(shouldPrint & 0xffL);
                result = 99L;
                return result;
            }
            result = Base_KeyUp_SST(shouldPrint & 0xffL) & 0xffffffffL;
        }
        if (result != 99L) {
            com_show_errorcode(result);
        }
        return result;
    }

    long Base_HandleKeyDown(long keyCode, long shouldPrint) {
        long result = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        putU32(framePtr + 20L, keyCode);
                        ComSpecialTransForRcl(framePtr + 20L);
                        ComKeyCheckState((long) (int) getU32(framePtr + 20L));
                        if (((getI32(g_com_machine_mode) - 1L) & 0xffffffffL) < 2L) {
                            Com_ConvertKeyForMachine(framePtr + 20L);
                        }
                        ComKeyPreHandle((long) (int) getU32(framePtr + 20L));
                        result = 99L;
                        switch ((int) (getU32(framePtr + 20L))) {
                            case 10: {
                                tempVal1 = Base_KeyDown_N(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 11: {
                                result = Base_KeyDown_Y_X(shouldPrint & 0xffL) & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 12: {
                                tempVal2 = Base_KeyDown_R_S(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 14: {
                                tempVal1 = Base_KeyDown_I(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 15: {
                                result = Base_KeyDown_1_X(shouldPrint & 0xffL) & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 16: {
                                tempVal2 = Base_KeyDown_SST(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 18: {
                                tempVal1 = Base_KeyDown_PV(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 19: {
                                tempVal1 = Base_KeyDown_PERCENT_T(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 20: {
                                tempVal2 = Base_KeyDown_R(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 22: {
                                tempVal1 = Base_KeyDown_PMT(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 23: {
                                tempVal1 = Base_KeyDown_DELTA_PERCENT(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 24: {
                                tempVal2 = Base_KeyDown_X_NOTEQUAL_Y(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 25: {
                                step = 3;
                                continue stepLoop;
                            }
                            case 89: {
                                step = 3;
                                continue stepLoop;
                            }
                            case 26: {
                                tempVal1 = Base_KeyDown_FV(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 27: {
                                result = Base_KeyDown_PERCENT(shouldPrint & 0xffL) & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 28: {
                                tempVal2 = Base_KeyDown_CLX(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 29: {
                                step = 4;
                                continue stepLoop;
                            }
                            case 93: {
                                step = 4;
                                continue stepLoop;
                            }
                            case 30: {
                                tempVal2 = Base_KeyDown_CHS(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 31: {
                                result = Base_KeyDown_EEX(shouldPrint & 0xffL) & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 32: {
                                step = 5;
                                continue stepLoop;
                            }
                            case 160: {
                                step = 5;
                                continue stepLoop;
                            }
                            case 34: {
                                tempVal1 = Base_KeyDown_7(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 35: {
                                tempVal1 = Base_KeyDown_4(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 36: {
                                tempVal1 = Base_KeyDown_1(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 37: {
                                tempVal1 = Base_KeyDown_0(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 38: {
                                tempVal1 = Base_KeyDown_8(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 39: {
                                tempVal1 = Base_KeyDown_5(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 40: {
                                tempVal1 = Base_KeyDown_2(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 41: {
                                tempVal1 = Base_KeyDown_DECIMALSEP(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 42: {
                                tempVal1 = Base_KeyDown_9(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 43: {
                                tempVal1 = Base_KeyDown_6(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 44: {
                                result = Base_KeyDown_3(shouldPrint & 0xffL) & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 45: {
                                step = 6;
                                continue stepLoop;
                            }
                            case 109: {
                                step = 6;
                                continue stepLoop;
                            }
                            case 46: {
                                step = 7;
                                continue stepLoop;
                            }
                            case 110: {
                                step = 7;
                                continue stepLoop;
                            }
                            case 47: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 111: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 48: {
                                step = 9;
                                continue stepLoop;
                            }
                            case 112: {
                                step = 9;
                                continue stepLoop;
                            }
                            case 49: {
                                step = 10;
                                continue stepLoop;
                            }
                            case 113: {
                                step = 10;
                                continue stepLoop;
                            }
                            case 74: {
                                tempVal1 = Base_KeyDown_AMORT(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 75: {
                                tempVal1 = Base_KeyDown_PRICE(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 76: {
                                tempVal2 = Base_KeyDown_P_R(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 78: {
                                tempVal1 = Base_KeyDown_INT(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 79: {
                                tempVal1 = Base_KeyDown_YTM(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 80: {
                                tempVal2 = Base_KeyDown_SIGMA(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 82: {
                                tempVal1 = Base_KeyDown_NPV(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 83: {
                                tempVal1 = Base_KeyDown_SL(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 84: {
                                tempVal2 = Base_KeyDown_PRGM(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 86: {
                                tempVal2 = Base_KeyDown_RND(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 87: {
                                tempVal1 = Base_KeyDown_SOYD(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 88: {
                                tempVal2 = Base_KeyDown_FIN(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 90: {
                                tempVal1 = Base_KeyDown_IRR(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 91: {
                                tempVal1 = Base_KeyDown_DB(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 92: {
                                tempVal2 = Base_KeyDown_REG(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 94: {
                                tempVal2 = Base_KeyDown_RPN(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 95: {
                                tempVal2 = Base_KeyDown_ALG(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 96: {
                                tempVal2 = Base_KeyDown_PREFIX(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 98: {
                                tempVal2 = Base_KeyDown_f_7(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 99: {
                                tempVal2 = Base_KeyDown_f_4(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 100: {
                                tempVal2 = Base_KeyDown_f_1(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 101: {
                                tempVal2 = Base_KeyDown_f_0(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 102: {
                                tempVal2 = Base_KeyDown_f_8(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 103: {
                                tempVal2 = Base_KeyDown_f_5(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 104: {
                                tempVal2 = Base_KeyDown_f_2(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 105: {
                                tempVal2 = Base_KeyDown_f_DECIMALSEP(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 106: {
                                tempVal2 = Base_KeyDown_f_9(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 107: {
                                tempVal2 = Base_KeyDown_f_6(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 108: {
                                tempVal2 = Base_KeyDown_f_3(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 138: {
                                tempVal1 = Base_KeyDown_12_MUL(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 139: {
                                result = Base_KeyDown_SQRT(shouldPrint & 0xffL) & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 140: {
                                tempVal1 = Base_KeyDown_PSE(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 142: {
                                tempVal1 = Base_KeyDown_12_DIV(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 143: {
                                result = Base_KeyDown_E_X(shouldPrint & 0xffL) & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 144: {
                                tempVal2 = Base_KeyDown_BST(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 146: {
                                tempVal1 = Base_KeyDown_CF0(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 147: {
                                result = Base_KeyDown_LN(shouldPrint & 0xffL) & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 148: {
                                tempVal2 = Base_KeyDown_GTO(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 150: {
                                tempVal1 = Base_KeyDown_CFj(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 151: {
                                result = Base_KeyDown_FRAC(shouldPrint & 0xffL) & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 152: {
                                tempVal2 = Base_KeyDown_X_NOTLAGER_Y(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 153: {
                                if (getI32(g_com_operationmode) == 1L) {
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    tempVal2 = Base_KeyDown_BRACKET_LEFT(shouldPrint & 0xffL);
                                    result = tempVal2 & 0xffffffffL;
                                    step = 1;
                                    continue stepLoop;
                                }
                            }
                            case 154: {
                                tempVal1 = Base_KeyDown_Nj(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 155: {
                                result = Base_KeyDown_INTG(shouldPrint & 0xffL) & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 156: {
                                tempVal2 = Base_KeyDown_X_EQUAL_0(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 157: {
                                if (getI32(g_com_operationmode) == 1L) {
                                    step = 4;
                                    continue stepLoop;
                                } else {
                                    tempVal1 = Base_KeyDown_BRACKET_RIGHT(shouldPrint & 0xffL);
                                    result = tempVal1 & 0xffffffffL;
                                    step = 1;
                                    continue stepLoop;
                                }
                            }
                            case 158: {
                                tempVal1 = Base_KeyDown_DATE(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 159: {
                                tempVal1 = Base_KeyDown_DELTA_DYS(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 162: {
                                tempVal2 = Base_KeyDown_BEG(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 163: {
                                tempVal2 = Base_KeyDown_D_MY(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 164: {
                                tempVal1 = Base_KeyDown_X_R(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 165: {
                                tempVal1 = Base_KeyDown_MEAN(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 166: {
                                tempVal2 = Base_KeyDown_END(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 167: {
                                tempVal2 = Base_KeyDown_M_DY(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 168: {
                                tempVal1 = Base_KeyDown_Y_R(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 169: {
                                tempVal1 = Base_KeyDown_S(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 170: {
                                tempVal2 = Base_KeyDown_MEM(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 171: {
                                tempVal1 = Base_KeyDown_MEAN_W(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 172: {
                                result = Base_KeyDown_N_MUL(shouldPrint & 0xffL) & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 173: {
                                tempVal1 = Base_KeyDown_SIGMA_SUB(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 174: {
                                tempVal2 = Base_KeyDown_UNDO(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 175: {
                                result = Base_KeyDown_SQUARE(shouldPrint & 0xffL) & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 176: {
                                tempVal2 = Base_KeyDown_BACKSPACE(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 177: {
                                tempVal2 = Base_KeyDown_LSTX(shouldPrint & 0xffL);
                                result = tempVal2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 291: {
                                tempVal1 = Base_KeyDown_BILL(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 292: {
                                tempVal1 = Base_KeyDown_TIP(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 295: {
                                tempVal1 = Base_KeyDown_TIP_PERCENT(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 296: {
                                tempVal1 = Base_KeyDown_TOTAL(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 299: {
                                tempVal1 = Base_KeyDown_SPLIT(shouldPrint & 0xffL);
                                result = tempVal1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            default: {
                                step = 2;
                                continue stepLoop;
                            }
                        }
                    }
                    case 1: {
                        if (result != 99L) {
                            com_show_errorcode(result);
                        }
                        step = 2;
                        continue stepLoop;
                    }
                    case 2: {
                        putU32(g_com_prevkeycode, getU32(framePtr + 20L));
                        if (getI32(g_com_keyinmode) == 5L) {
                            putU8(g_com_fincal_count, 1L);
                        }
                        if ((getU32(framePtr + 20L) & 0xffffffbfL) != 29L && (getU32(framePtr + 20L) != 157L || getI32(g_com_operationmode) != 1L)) {
                            putU8(g_com_fincal_prev, 0L);
                        }
                        if (getI32(g_com_keyinmode) == 5L) {
                            putU8(g_com_fincal_prev, 1L);
                        }
                        return result;
                    }
                    case 3: {
                        tempVal2 = Base_KeyDown_STO(shouldPrint & 0xffL);
                        result = tempVal2 & 0xffffffffL;
                        step = 1;
                        continue stepLoop;
                    }
                    case 4: {
                        tempVal2 = Base_KeyDown_RCL(shouldPrint & 0xffL);
                        result = tempVal2 & 0xffffffffL;
                        step = 1;
                        continue stepLoop;
                    }
                    case 5: {
                        result = Base_KeyDown_EQUAL(shouldPrint & 0xffL) & 0xffffffffL;
                        step = 1;
                        continue stepLoop;
                    }
                    case 6: {
                        tempVal1 = Base_KeyDown_SIGMA_PLUS(shouldPrint & 0xffL);
                        result = tempVal1 & 0xffffffffL;
                        step = 1;
                        continue stepLoop;
                    }
                    case 7: {
                        tempVal1 = Base_KeyDown_DIV(shouldPrint & 0xffL);
                        result = tempVal1 & 0xffffffffL;
                        step = 1;
                        continue stepLoop;
                    }
                    case 8: {
                        tempVal1 = Base_KeyDown_MUL(shouldPrint & 0xffL);
                        result = tempVal1 & 0xffffffffL;
                        step = 1;
                        continue stepLoop;
                    }
                    case 9: {
                        tempVal1 = Base_KeyDown_SUB(shouldPrint & 0xffL);
                        result = tempVal1 & 0xffffffffL;
                        step = 1;
                        continue stepLoop;
                    }
                    case 10: {
                        tempVal1 = Base_KeyDown_PLUS(shouldPrint & 0xffL);
                        result = tempVal1 & 0xffffffffL;
                        step = 1;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_Y_X(long keyCode) {
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            Com_EndEditMode_ForBasic();
            result = (long) (int) Basic_OperateKeyEntry(4L);
            if (result == 99L) {
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_1_X(long keyCode) {
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            Com_EndEditMode_ForBasic();
            status = Basic_CaculateWithOneParam(5L) & 0xffffffffL;
            if (status == 99L) {
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_PERCENT_T(long keyCode) {
        long flag = 0L;
        long errCode1 = 0L;
        long errCode2 = 0L;
        long status = 0L;
        CC_Real regX = new CC_Real();
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        Com_EndEditMode_ForBasic();
                        if (getI32(g_com_operationmode) == 1L) {
                            regX = Stack_GetX().copy();
                            putI64(framePtr + 64L, regX.m);
                            putI64(framePtr + 56L, (((regX.e & 0xffffffffL)) << 32) | (regX.s & 0xffffffffL));
                            putI64(g_basic_lastx + 8L, getI64(framePtr + 64L));
                            putI32(g_basic_lastx, getI32(framePtr + 56L));
                            putI32(g_basic_lastx + 4L, getI32(framePtr + 60L));
                            putCCReal(framePtr + 56L, regX);
                            putCCReal(framePtr + 32L, Stack_GetY());
                            flag = fdiv(framePtr + 56L, framePtr + 32L, framePtr + 16L) & 0xffL;
                            errCode2 = Basic_GetMiddleCalculateErr(flag, framePtr + 16L);
                            if ((long) (int) errCode2 == 0L) {
                                fIntToCC(100L, framePtr + 56L);
                                putI64(framePtr + 40L, getI64(framePtr + 24L));
                                putI32(framePtr + 32L, getI32(framePtr + 16L));
                                putI32(framePtr + 36L, getI32(framePtr + 20L));
                                errCode1 = fmul(framePtr + 56L, framePtr + 32L, framePtr + 16L) & 0xffffffffL;
                                errCode1 = Basic_GetCalculateErr(errCode1, framePtr + 16L) & 0xffffffffL;
                                if (errCode1 == 0L) {
                                    Stack_SetX(framePtr + 16L);
                                    Com_SetResult(framePtr + 16L, keyCode & 0xffL);
                                    status = 99L;
                                    putI32(g_result_state, 0L);
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    step = 1;
                                    continue stepLoop;
                                }
                            } else {
                                step = 1;
                                continue stepLoop;
                            }
                        } else {
                            status = 0L;
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        status = 0L;
                        step = 2;
                        continue stepLoop;
                    }
                    case 2: {
                        if (getI32(g_com_operationmode) != 0L) {
                            return status;
                        }
                        status = Basic_CaculateWithBracket();
                        if ((long) (int) status != 99L) {
                            putI32(g_result_state, 0L);
                            putI64(g_basic_lastx + 16L, 1L);
                            return status;
                        }
                        putCCReal(framePtr + 56L, Stack_GetX());
                        putCCReal(framePtr + 32L, Stack_GetY());
                        flag = fdiv(framePtr + 56L, framePtr + 32L, framePtr + 16L) & 0xffL;
                        errCode2 = Basic_GetMiddleCalculateErr(flag, framePtr + 16L);
                        if ((long) (int) errCode2 != 0L) {
                            status = 0L;
                            putI32(g_result_state, 0L);
                            putI64(g_basic_lastx + 16L, 1L);
                            return status;
                        }
                        fIntToCC(100L, framePtr + 56L);
                        putI64(framePtr + 40L, getI64(framePtr + 24L));
                        putI32(framePtr + 32L, getI32(framePtr + 16L));
                        putI32(framePtr + 36L, getI32(framePtr + 20L));
                        errCode1 = fmul(framePtr + 56L, framePtr + 32L, framePtr + 16L) & 0xffffffffL;
                        errCode1 = Basic_GetCalculateErr(errCode1, framePtr + 16L) & 0xffffffffL;
                        if (errCode1 == 0L) {
                            Stack_SetX(framePtr + 16L);
                            Com_SetResult(framePtr + 16L, keyCode & 0xffL);
                            status = 99L;
                            putI32(g_result_state, 0L);
                            putI64(g_basic_lastx + 16L, 1L);
                            return status;
                        } else {
                            status = 0L;
                            putI32(g_result_state, 0L);
                            putI64(g_basic_lastx + 16L, 1L);
                            return status;
                        }
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_DELTA_PERCENT(long keyCode) {
        long errCode = 0L;
        long status = 0L;
        CC_Real regX = new CC_Real();
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            Com_EndEditMode_ForBasic();
            if (getI32(g_com_operationmode) == 1L) {
                regX = Stack_GetX().copy();
                putI64(framePtr + 64L, regX.m);
                putI64(framePtr + 56L, (((regX.e & 0xffffffffL)) << 32) | (regX.s & 0xffffffffL));
                putI64(g_basic_lastx + 8L, getI64(framePtr + 64L));
                putI32(g_basic_lastx, getI32(framePtr + 56L));
                putI32(g_basic_lastx + 4L, getI32(framePtr + 60L));
                putCCReal(framePtr + 56L, regX);
                putCCReal(framePtr + 40L, Stack_GetY());
                status = MarkupAsPercentOfCost(framePtr + 24L, framePtr + 40L, framePtr + 56L, 0L);
                errCode = Basic_GetCalculateErr(status & 0xffffffffL, framePtr + 24L) & 0xffffffffL;
                status = 0L;
                if (errCode == 0L) {
                    Stack_SetX(framePtr + 24L);
                    Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                    status = 99L;
                    putI32(g_result_state, 0L);
                }
            } else {
                status = 0L;
            }
            if (getI32(g_com_operationmode) != 0L) {
                return status;
            }
            status = Basic_CaculateWithBracket();
            if ((long) (int) status != 99L) {
                putI32(g_result_state, 0L);
                putI64(g_basic_lastx + 16L, 1L);
                return status;
            }
            putCCReal(framePtr + 56L, Stack_GetX());
            putCCReal(framePtr + 40L, Stack_GetY());
            status = MarkupAsPercentOfCost(framePtr + 24L, framePtr + 40L, framePtr + 56L, 0L);
            errCode = Basic_GetCalculateErr(status & 0xffffffffL, framePtr + 24L) & 0xffffffffL;
            status = 0L;
            if (errCode == 0L) {
                Stack_SetX(framePtr + 24L);
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                status = 99L;
            }
            putI32(g_result_state, 0L);
            putI64(g_basic_lastx + 16L, 1L);
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_PERCENT(long keyCode) {
        long flag = 0L;
        long stackDepth = 0L;
        long errCode1 = 0L;
        long exprState = 0L;
        long errCode2 = 0L;
        long realPtr1 = 0L;
        long realPtr2 = 0L;
        long result = 0L;
        CC_Real regX = new CC_Real();
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        Com_EndEditMode_ForBasic();
                        if (getI32(g_com_operationmode) == 1L) {
                            regX = Stack_GetX().copy();
                            putI64(framePtr + 56L, regX.m);
                            putI64(framePtr + 48L, (((regX.e & 0xffffffffL)) << 32) | (regX.s & 0xffffffffL));
                            putI64(g_basic_lastx + 8L, getI64(framePtr + 56L));
                            putI32(g_basic_lastx, getI32(framePtr + 48L));
                            putI32(g_basic_lastx + 4L, getI32(framePtr + 52L));
                            putCCReal(framePtr + 48L, regX);
                            putCCReal(framePtr + 32L, Stack_GetY());
                            errCode1 = fmul(framePtr + 48L, framePtr + 32L, framePtr + 16L) & 0xffffffffL;
                            errCode2 = Basic_GetMiddleCalculateErr(errCode1 & 0xffffffffL, framePtr + 16L);
                            if ((long) (int) errCode2 == 0L) {
                                putI64(framePtr + 56L, getI64(framePtr + 24L));
                                putI32(framePtr + 48L, getI32(framePtr + 16L));
                                putI32(framePtr + 52L, getI32(framePtr + 20L));
                                fIntToCC(100L, framePtr + 32L);
                                flag = fdiv(framePtr + 48L, framePtr + 32L, framePtr + 16L) & 0xffL;
                                errCode1 = Basic_GetCalculateErr(flag, framePtr + 16L) & 0xffffffffL;
                                if ((errCode1 & 0xffffffffL) == 0L) {
                                    Stack_SetX(framePtr + 16L);
                                    Com_SetResult(framePtr + 16L, keyCode & 0xffL);
                                    result = 99L;
                                    putI32(g_result_state, 0L);
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    step = 1;
                                    continue stepLoop;
                                }
                            } else {
                                step = 1;
                                continue stepLoop;
                            }
                        } else {
                            result = 0L;
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        result = 0L;
                        step = 2;
                        continue stepLoop;
                    }
                    case 2: {
                        if (getI32(g_com_operationmode) != 0L) {
                            return result;
                        }
                        exprState = (long) (int) Basic_GetExpressState();
                        if (exprState == 0L) {
                            Basic_DiscardDuplicateBracket();
                            step = 4;
                            continue stepLoop;
                        } else {
                            exprState = (long) (int) Basic_GetExpressState();
                            if (exprState == 1L || comma(exprState = (long) (int) Basic_GetExpressState(), exprState == 2L ? 1L : 0L) != 0L) {
                                step = 4;
                                continue stepLoop;
                            } else {
                                errCode1 = getU32(g_basic_op) & 0xfffffffeL;
                                regX = Stack_GetX().copy();
                                if ((errCode1 & 0xffffffffL) != 2L) {
                                    step = 5;
                                    continue stepLoop;
                                } else {
                                    putCCReal(framePtr + 48L, regX);
                                    putCCReal(framePtr + 32L, Stack_GetX1());
                                    errCode1 = fmul(framePtr + 48L, framePtr + 32L, framePtr + 16L) & 0xffffffffL;
                                    errCode2 = Basic_GetMiddleCalculateErr(errCode1 & 0xffffffffL, framePtr + 16L);
                                    if ((long) (int) errCode2 != 0L) {
                                        return result;
                                    }
                                    putI64(framePtr + 56L, getI64(framePtr + 24L));
                                    putI32(framePtr + 48L, getI32(framePtr + 16L));
                                    putI32(framePtr + 52L, getI32(framePtr + 20L));
                                    fIntToCC(100L, framePtr + 32L);
                                    realPtr1 = framePtr + 48L;
                                    realPtr2 = framePtr + 32L;
                                    step = 3;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 3: {
                        flag = fdiv(realPtr1, realPtr2, framePtr + 16L) & 0xffL;
                        errCode1 = Basic_GetCalculateErr(flag, framePtr + 16L) & 0xffffffffL;
                        if ((errCode1 & 0xffffffffL) == 0L) {
                            Stack_SetX(framePtr + 16L);
                            Com_SetResult(framePtr + 16L, keyCode & 0xffL);
                            result = 99L;
                        }
                        return result;
                    }
                    case 4: {
                        stackDepth = (long) (byte) Stack_GetRegistersDepth();
                        putU32(g_basic_op, 99L);
                        if ((byte) stackDepth == 0L) {
                            putI32(g_result_state, 0L);
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        Basic_SetExpressState(1L);
                        regX = Stack_GetX().copy();
                        step = 5;
                        continue stepLoop;
                    }
                    case 5: {
                        putCCReal(framePtr + 32L, regX);
                        fIntToCC(100L, framePtr + 48L);
                        realPtr1 = framePtr + 32L;
                        realPtr2 = framePtr + 48L;
                        step = 3;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_CHS(long keyCode) {
        long isEqual = 0L;
        long tempVal = 0L;
        long stackDepth = 0L;
        long exprState = 0L;
        long errCode = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        putCCReal(framePtr + 24L, Stack_GetX());
                        if (getU32(g_basic_displaymode) == 0L) {
                            if (getU8(g_com_eex_forbackspace) == 0L) {
                                if (((getI32(g_com_keyinmode) - 2L) & 0xffffffffL) < 2L) {
                                    return 99L;
                                }
                                if (getI32(g_com_keyinmode) != 1L) {
                                    if (getI8(g_basic_edit) != 43L || (long) (int) comma(isEqual = fEQ(framePtr + 24L, CC_0) != 0L ? 1L : 0L, isEqual) != 0L) {
                                        putI8(g_basic_edit, 43L);
                                        LcdDriver_ClearAnnunciator(0L);
                                    } else {
                                        putI8(g_basic_edit, 45L);
                                        if ((keyCode & 0xffL) != 0L) {
                                            LcdDriver_SetAnnunciator(0L);
                                        }
                                    }
                                    return 99L;
                                }
                            } else {
                                putI32(g_com_keyinmode, 1L);
                            }
                            if (getI8(g_basic_edit + 13L) != 43L || (long) (int) comma(isEqual = fEQ(framePtr + 24L, CC_0) != 0L ? 1L : 0L, isEqual) != 0L) {
                                putI8(g_basic_edit + 13L, 43L);
                                fStringToCC_er(g_basic_edit, framePtr + 24L, 0L, 1L, 46L, 44L);
                                errCode = Basic_CheckRealData(framePtr + 24L);
                                if ((errCode & 1L) == 0L) {
                                    if ((keyCode & 0xffL) == 0L) {
                                        return 99L;
                                    }
                                    tempVal = 32L;
                                    LcdDriver_PutChar(tempVal, 7L);
                                    LcdDriver_PutChar(getI8(g_basic_edit + 14L) & 0xffL, 8L);
                                    LcdDriver_PutChar(getI8(g_basic_edit + 15L) & 0xffL, 9L);
                                    return 99L;
                                }
                            } else {
                                putI8(g_basic_edit + 13L, 45L);
                                fStringToCC_er(g_basic_edit, framePtr + 24L, 0L, 1L, 46L, 44L);
                                errCode = Basic_CheckRealData(framePtr + 24L);
                                if ((errCode & 1L) == 0L) {
                                    if ((keyCode & 0xffL) == 0L) {
                                        return 99L;
                                    }
                                    tempVal = 45L;
                                    LcdDriver_PutChar(tempVal, 7L);
                                    LcdDriver_PutChar(getI8(g_basic_edit + 14L) & 0xffL, 8L);
                                    LcdDriver_PutChar(getI8(g_basic_edit + 15L) & 0xffL, 9L);
                                    return 99L;
                                }
                            }
                            Com_EndEditMode_ForBasic();
                            Stack_SetX(framePtr + 24L);
                            putI32(g_result_state, 0L);
                            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                            return 99L;
                        }
                        if (getI32(g_com_operationmode) == 1L) {
                            isEqual = fEQ(framePtr + 24L, CC_0) != 0L ? 1L : 0L;
                            if (isEqual != 0L) {
                                putI32(framePtr + 24L, 1L);
                            } else {
                                exprState = 1L;
                                if (getI32(framePtr + 24L) == 1L) {
                                    exprState = -1L;
                                }
                                putI32(framePtr + 24L, exprState);
                                Stack_SetX(framePtr + 24L);
                            }
                            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                        }
                        if (getI32(g_com_operationmode) != 0L) {
                            return 99L;
                        }
                        exprState = (long) (int) Basic_GetExpressState();
                        if (exprState == 0L) {
                            Basic_DiscardDuplicateBracket();
                            step = 2;
                            continue stepLoop;
                        } else {
                            exprState = (long) (int) Basic_GetExpressState();
                            if (exprState != 1L && comma(exprState = (long) (int) Basic_GetExpressState(), exprState == 2L ? 1L : 0L) != 0L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                step = 1;
                                continue stepLoop;
                            }
                        }
                    }
                    case 1: {
                        isEqual = fEQ(framePtr + 24L, CC_0) != 0L ? 1L : 0L;
                        if (isEqual != 0L) {
                            putI32(framePtr + 24L, 1L);
                        } else {
                            exprState = 1L;
                            if (getI32(framePtr + 24L) == 1L) {
                                exprState = -1L;
                            }
                            putI32(framePtr + 24L, exprState);
                            Stack_SetX(framePtr + 24L);
                        }
                        Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                        return 99L;
                    }
                    case 2: {
                        stackDepth = (long) (byte) Stack_GetRegistersDepth();
                        putU32(g_basic_op, 99L);
                        if ((byte) stackDepth == 0L) {
                            putI32(g_result_state, 0L);
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        Basic_SetExpressState(1L);
                        step = 1;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_EEX(long keyCode) {
        long tempVal1 = 0L;
        long isEqual = 0L;
        long tempVal2 = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            if (getI32(g_com_keyinmode) == 3L) {
                Com_EndEditMode_ForBasic();
                putI32(g_com_keyinmode, 0L);
                if (getI8(g_fin_compoundinterest) == 0L) {
                    if ((keyCode & 0xffL) != 0L) {
                        LcdDriver_SetAnnunciator(9L);
                    }
                    putI8(g_fin_compoundinterest, 1L);
                } else {
                    LcdDriver_ClearAnnunciator(9L);
                    putI8(g_fin_compoundinterest, 0L);
                }
                putCCReal(framePtr + 24L, Stack_GetX());
                if (getU32(g_basic_op) == 99L) {
                    putI32(g_result_state, 0L);
                }
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                result = 99L;
            } else {
                if (getU32(g_basic_displaymode) == 0L) {
                    putCCReal(framePtr + 24L, Stack_GetX());
                    isEqual = fEQ(framePtr + 24L, CC_0) != 0L ? 1L : 0L;
                    if (isEqual != 0L) {
                        putU32(g_basic_edit + 16L, 0L);
                        putU8(g_basic_edit + 8L, 48L);
                        putU8(g_basic_edit + 9L, 48L);
                        putU8(g_basic_edit + 10L, 48L);
                        putU8(g_basic_edit + 11L, 48L);
                        putU8(g_basic_edit + 12L, 69L);
                        putI8(g_basic_edit + 13L, 43L);
                        putI8(g_basic_edit + 14L, 48L);
                        putI8(g_basic_edit + 15L, 48L);
                        putI8(g_basic_edit, 43L);
                        putU8(g_basic_edit + 1L, 46L);
                        putU8(g_basic_edit + 2L, 48L);
                        putU8(g_basic_edit + 3L, 48L);
                        putU8(g_basic_edit + 4L, 48L);
                        putU8(g_basic_edit + 5L, 48L);
                        putU8(g_basic_edit + 6L, 48L);
                        putU8(g_basic_edit + 7L, 48L);
                        tempVal2 = Basic_NewNumberKeyEntry(49L, keyCode & 0xffL);
                        tempVal1 = tempVal2 & 0xffffffffL;
                        result = tempVal1;
                        putI32(g_com_keyinmode, 1L);
                        Basic_PrintEditLine(keyCode & 0xffL);
                    } else {
                        result = 99L;
                        tempVal1 = 99L;
                        if (((getI32(framePtr + 28L) + 6L) & 0xffffffffL) < 13L) {
                            result = tempVal1;
                            putI32(g_com_keyinmode, 1L);
                        }
                        Basic_PrintEditLine(keyCode & 0xffL);
                    }
                } else {
                    tempVal2 = Basic_NewNumberKeyEntry(49L, keyCode & 0xffL);
                    tempVal1 = tempVal2 & 0xffffffffL;
                    result = tempVal1;
                    putI32(g_com_keyinmode, 1L);
                    Basic_PrintEditLine(keyCode & 0xffL);
                }
            }
            if (getI32(g_com_keyinmode) == 1L) {
                putU8(g_com_eex_forbackspace, 1L);
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_7(long keyCode) {
        long result = 0L;
        result = Basic_NewNumberKeyEntry(55L, keyCode & 0xffL);
        return result;
    }

    long Base_KeyDown_4(long keyCode) {
        long result = 0L;
        result = Basic_NewNumberKeyEntry(52L, keyCode & 0xffL);
        return result;
    }

    long Base_KeyDown_1(long keyCode) {
        long result = 0L;
        result = Basic_NewNumberKeyEntry(49L, keyCode & 0xffL);
        return result;
    }

    long Base_KeyDown_0(long keyCode) {
        long result = 0L;
        result = Basic_NewNumberKeyEntry(48L, keyCode & 0xffL);
        return result;
    }

    long Base_KeyDown_8(long keyCode) {
        long result = 0L;
        result = Basic_NewNumberKeyEntry(56L, keyCode & 0xffL);
        return result;
    }

    long Base_KeyDown_5(long keyCode) {
        long result = 0L;
        result = Basic_NewNumberKeyEntry(53L, keyCode & 0xffL);
        return result;
    }

    long Base_KeyDown_2(long keyCode) {
        long result = 0L;
        result = Basic_NewNumberKeyEntry(50L, keyCode & 0xffL);
        return result;
    }

    long Base_KeyDown_DECIMALSEP(long keyCode) {
        long result = 0L;
        result = Basic_NewNumberKeyEntry(46L, keyCode & 0xffL);
        return result;
    }

    long Base_KeyDown_9(long keyCode) {
        long result = 0L;
        result = Basic_NewNumberKeyEntry(57L, keyCode & 0xffL);
        return result;
    }

    long Base_KeyDown_6(long keyCode) {
        long result = 0L;
        result = Basic_NewNumberKeyEntry(54L, keyCode & 0xffL);
        return result;
    }

    long Base_KeyDown_3(long keyCode) {
        return Basic_NewNumberKeyEntry(51L, keyCode & 0xffL);
    }

    long Base_KeyDown_DIV(long keyCode) {
        long tempVal = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            if (getI32(g_com_keyinmode) == 3L) {
                if (getI8(g_reg_state + 1L) == 1L) {
                    putU16(g_reg_state + 1L, 1030L);
                    result = 99L;
                    return result;
                }
                putI32(g_com_keyinmode, 0L);
            }
            Com_EndEditMode_ForBasic();
            tempVal = Basic_OperateKeyEntry(0L) & 0xffffffffL;
            result = tempVal;
            if (tempVal != 99L) {
                return result;
            }
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            result = 99L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_MUL(long keyCode) {
        long tempVal = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            if (getI32(g_com_keyinmode) == 3L) {
                if (getI8(g_reg_state + 1L) == 1L) {
                    putU16(g_reg_state + 1L, 773L);
                    result = 99L;
                    return result;
                }
                putI32(g_com_keyinmode, 0L);
            }
            Com_EndEditMode_ForBasic();
            tempVal = Basic_OperateKeyEntry(1L) & 0xffffffffL;
            result = tempVal;
            if (tempVal != 99L) {
                return result;
            }
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            result = 99L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_SUB(long keyCode) {
        long tempVal = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            if (getI32(g_com_keyinmode) == 3L) {
                if (getI8(g_reg_state + 1L) == 1L) {
                    putU16(g_reg_state + 1L, 516L);
                    result = 99L;
                    return result;
                }
                putI32(g_com_keyinmode, 0L);
            }
            Com_EndEditMode_ForBasic();
            tempVal = Basic_OperateKeyEntry(2L) & 0xffffffffL;
            result = tempVal;
            if (tempVal != 99L) {
                return result;
            }
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            result = 99L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_PLUS(long keyCode) {
        long tempVal = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            if (getI32(g_com_keyinmode) == 3L) {
                if (getI8(g_reg_state + 1L) == 1L) {
                    putU16(g_reg_state + 1L, 259L);
                    result = 99L;
                    return result;
                }
                putI32(g_com_keyinmode, 0L);
            }
            Com_EndEditMode_ForBasic();
            tempVal = Basic_OperateKeyEntry(3L) & 0xffffffffL;
            result = tempVal;
            if (tempVal != 99L) {
                return result;
            }
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            result = 99L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_RPN(long keyCode) {
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            Com_EndEditMode_ForBasic();
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            Basic_SetCaculateMode(1L, keyCode & 0xffL);
            Basic_SetExpressState(1L);
            Stack_EmptyRegisters();
            putI32(g_result_state, 0L);
            putI64(g_basic_lastx + 16L, 0L);
            putU32(g_basic_op, 99L);
            putI64(g_basic_lastx + 8L, 0L);
            putI64(g_basic_lastx, 1L);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_ALG(long keyCode) {
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            Com_EndEditMode_ForBasic();
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            Basic_SetCaculateMode(0L, keyCode & 0xffL);
            Basic_SetExpressState(1L);
            Stack_EmptyRegisters();
            putI32(g_result_state, 0L);
            putI64(g_basic_lastx + 16L, 0L);
            putU32(g_basic_op, 99L);
            putI64(g_basic_lastx + 8L, 0L);
            putI64(g_basic_lastx, 1L);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_PREFIX(long keyCode) {
        long bytePtr1 = 0L;
        long flag = 0L;
        long bytePtr2 = 0L;
        long tempVal = 0L;
        CC_Real regX = new CC_Real();
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            Com_EndEditMode_ForBasic();
            regX = Stack_GetX().copy();
            putI64(framePtr + 24L, regX.m);
            putI32(framePtr + 16L, regX.s);
            putI32(framePtr + 20L, 0L);
            firnd(framePtr + 16L, 10L, framePtr + 16L, 1L);
            bytePtr1 = (framePtr + 32L) | 1L;
            if ((getI64(framePtr + 16L) & 0xffffffffL) != 0xffffffffL) {
                bytePtr1 = framePtr + 32L;
            }
            tempVal = (getI64(framePtr + 16L) & 0xffffffffL) == 0xffffffffL ? 1L : 0L;
            putU8(bytePtr1 + 8L, 0L);
            putU8(bytePtr1 + 9L, 0L);
            putU8(bytePtr1, 0L);
            putU8(bytePtr1 + 1L, 0L);
            putU8(bytePtr1 + 2L, 0L);
            putU8(bytePtr1 + 3L, 0L);
            putU8(bytePtr1 + 4L, 0L);
            putU8(bytePtr1 + 5L, 0L);
            putU8(bytePtr1 + 6L, 0L);
            putU8(bytePtr1 + 7L, 0L);
            fCCToString(framePtr + 16L, framePtr + 32L, 46L, 44L, 0L, 0L, 10L, 0L);
            bytePtr2 = (framePtr + 32L) | tempVal;
            flag = getU8(bytePtr2);
            if ((long) (int) (flag | 2L) == 46L) {
                flag = getU8(bytePtr1 + 1L);
                putU8(bytePtr2, flag);
                putU8(bytePtr1 + 1L, 46L);
            }
            if (flag == 0L) {
                putU8((framePtr + 32L) | tempVal, 48L);
            }
            flag = getU8(bytePtr1 + 1L);
            if ((long) (int) (flag | 2L) == 46L) {
                flag = getU8(bytePtr1 + 2L);
                putU8(bytePtr1 + 2L, 46L);
                putU8(bytePtr1 + 1L, flag);
            }
            if (flag == 0L) {
                putU8(bytePtr1 + 1L, 48L);
            }
            flag = getU8(bytePtr1 + 2L);
            if ((long) (int) (flag | 2L) == 46L) {
                flag = getU8(bytePtr1 + 3L);
                putU8(bytePtr1 + 3L, 46L);
                putU8(bytePtr1 + 2L, flag);
            }
            if (flag == 0L) {
                putU8(bytePtr1 + 2L, 48L);
            }
            flag = getU8(bytePtr1 + 3L);
            if ((long) (int) (flag | 2L) == 46L) {
                flag = getU8(bytePtr1 + 4L);
                putU8(bytePtr1 + 4L, 46L);
                putU8(bytePtr1 + 3L, flag);
            }
            if (flag == 0L) {
                putU8(bytePtr1 + 3L, 48L);
            }
            flag = getU8(bytePtr1 + 4L);
            if ((long) (int) (flag | 2L) == 46L) {
                flag = getU8(bytePtr1 + 5L);
                putU8(bytePtr1 + 5L, 46L);
                putU8(bytePtr1 + 4L, flag);
            }
            if (flag == 0L) {
                putU8(bytePtr1 + 4L, 48L);
            }
            flag = getU8(bytePtr1 + 5L);
            if ((long) (int) (flag | 2L) == 46L) {
                flag = getU8(bytePtr1 + 6L);
                putU8(bytePtr1 + 6L, 46L);
                putU8(bytePtr1 + 5L, flag);
            }
            if (flag == 0L) {
                putU8(bytePtr1 + 5L, 48L);
            }
            flag = getU8(bytePtr1 + 6L);
            if ((long) (int) (flag | 2L) == 46L) {
                flag = getU8(bytePtr1 + 7L);
                putU8(bytePtr1 + 7L, 46L);
                putU8(bytePtr1 + 6L, flag);
            }
            if (flag == 0L) {
                putU8(bytePtr1 + 6L, 48L);
            }
            flag = getU8(bytePtr1 + 7L);
            if ((long) (int) (flag | 2L) == 46L) {
                flag = getU8(bytePtr1 + 8L);
                putU8(bytePtr1 + 8L, 46L);
                putU8(bytePtr1 + 7L, flag);
            }
            if (flag == 0L) {
                putU8(bytePtr1 + 7L, 48L);
            }
            flag = getU8(bytePtr1 + 8L);
            if ((long) (int) (flag | 2L) == 46L) {
                flag = getU8(bytePtr1 + 9L);
                putU8(bytePtr1 + 9L, 46L);
                putU8(bytePtr1 + 8L, flag);
            }
            if (flag == 0L) {
                putU8(bytePtr1 + 8L, 48L);
            }
            flag = getU8(bytePtr1 + 9L);
            if ((long) (int) (flag | 2L) == 46L) {
                flag = getU8(bytePtr1 + 10L);
                putU8(bytePtr1 + 10L, 46L);
                putU8(bytePtr1 + 9L, flag);
            }
            if (flag == 0L) {
                putU8(bytePtr1 + 9L, 48L);
                putU8(bytePtr1 + 10L, 0L);
            } else {
                putU8(bytePtr1 + 10L, 0L);
            }
            if ((keyCode & 0xffL) != 0L) {
                LcdDriver_ClearAnnunciator(0L);
                LcdDriver_PrintCalculatorLine(bytePtr1, 0L);
            }
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyUp_PREFIX(long keyCode) {
        long exprState = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            TimerDriver_WaitForTime(300L);
            if (getI32(g_com_operationmode) == 0L) {
                exprState = (long) (int) Basic_GetExpressState();
                if (exprState == 2L || comma(exprState = (long) (int) Basic_GetExpressState(), exprState == 3L ? 1L : 0L) != 0L) {
                    putCCReal(framePtr + 24L, Stack_GetX());
                    Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                    KeypadDriver_ClearKeyBuffer();
                    return 99L;
                }
            } else {
                if (getI32(g_com_operationmode) != 1L) {
                    putCCReal(framePtr + 24L, Stack_GetX());
                    Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                    KeypadDriver_ClearKeyBuffer();
                    return 99L;
                }
            }
            putI32(g_result_state, 0L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            KeypadDriver_ClearKeyBuffer();
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_SQRT(long keyCode) {
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            Com_EndEditMode_ForBasic();
            status = Basic_CaculateWithOneParam(6L) & 0xffffffffL;
            if (status == 99L) {
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_E_X(long keyCode) {
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            Com_EndEditMode_ForBasic();
            status = Basic_CaculateWithOneParam(7L) & 0xffffffffL;
            if (status == 99L) {
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_LN(long keyCode) {
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            Com_EndEditMode_ForBasic();
            status = Basic_CaculateWithOneParam(8L) & 0xffffffffL;
            if (status == 99L) {
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_FRAC(long keyCode) {
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            Com_EndEditMode_ForBasic();
            status = Basic_CaculateWithOneParam(9L) & 0xffffffffL;
            if (status == 99L) {
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_BRACKET_LEFT(long keyCode) {
        long stackDepth = 0L;
        long exprState = 0L;
        long result = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if (getI32(g_com_operationmode) != 0L) {
                        return 99L;
                    }
                    exprState = (long) (int) Basic_GetExpressState();
                    if (exprState != 0L) {
                        exprState = (long) (int) Basic_GetExpressState();
                        if (exprState != 1L) {
                            exprState = (long) (int) Basic_GetExpressState();
                            if (exprState != 2L) {
                                return 99L;
                            }
                            Basic_EndEditMode();
                            Stack_PushXToX1();
                            result = Stack_PushRegisters();
                            if ((long) (int) result != 99L) {
                                return result;
                            }
                            Basic_ChangeExpressState(2L);
                            step = 2;
                            continue stepLoop;
                        } else {
                            if (getU32(g_basic_displaymode) == 0L) {
                                return 99L;
                            }
                            stackDepth = (long) (byte) Stack_GetRegistersDepth();
                            if ((byte) stackDepth != 0L) {
                                return 99L;
                            }
                            if (getI32(g_result_state) == 0L) {
                                putI32(g_result_state, 1L);
                                Stack_ShiftUpOneStep();
                            }
                            step = 1;
                            continue stepLoop;
                        }
                    } else {
                        step = 1;
                        continue stepLoop;
                    }
                }
                case 1: {
                    result = Stack_PushRegisters();
                    if ((long) (int) result != 99L) {
                        return result;
                    }
                    step = 2;
                    continue stepLoop;
                }
                case 2: {
                    if ((keyCode & 0xffL) != 0L) {
                        LcdDriver_SetAnnunciator(4L);
                    }
                    putU8(g_com_bracketmode, 1L);
                    return 99L;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long Base_KeyDown_INTG(long keyCode) {
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            Com_EndEditMode_ForBasic();
            status = Basic_CaculateWithOneParam(10L) & 0xffffffffL;
            if (status == 99L) {
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_BRACKET_RIGHT(long keyCode) {
        long stackDepth = 0L;
        long exprState = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            if (!(getI32(g_com_operationmode) == 0L && comma(exprState = (long) (int) Basic_GetExpressState(), exprState != 2L ? 1L : 0L) != 0L && comma(stackDepth = (long) (byte) Stack_GetRegistersDepth(), (byte) stackDepth != 0L ? 1L : 0L) != 0L)) {
                status = 99L;
                return status;
            }
            Basic_EndEditMode();
            status = Basic_Caculate();
            if ((long) (int) status != 99L) {
                return status;
            }
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            status = Stack_PopRegisters();
            if ((status & 0xffL) == 0L) {
                LcdDriver_ClearAnnunciator(4L);
                putU8(g_com_bracketmode, 0L);
            }
            status = 99L;
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_N_MUL(long keyCode) {
        long isLessOrEqual = 0L;
        long stackDepth = 0L;
        long exprState = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        Com_EndEditMode_ForBasic();
                        putCCReal(framePtr + 40L, Stack_GetX());
                        fIntToCC(70L, framePtr + 24L);
                        isLessOrEqual = fLE(framePtr + 24L, framePtr + 40L) != 0L ? 1L : 0L;
                        if (!(isLessOrEqual != 0L && (long) (int) comma(isLessOrEqual = fiInteger(framePtr + 40L) != 0L ? 1L : 0L, isLessOrEqual) != 0L)) {
                            status = Basic_CaculateWithOneParam(11L) & 0xffffffffL;
                            if (status != 99L) {
                                return status;
                            }
                            putCCReal(framePtr + 40L, Stack_GetX());
                            Com_SetResult(framePtr + 40L, keyCode & 0xffL);
                            status = 99L;
                            return status;
                        }
                        if (getI32(g_com_operationmode) == 1L) {
                            putCCReal(g_basic_lastx, Stack_GetX());
                        }
                        if (getI32(g_com_operationmode) == 0L) {
                            exprState = (long) (int) Basic_GetExpressState();
                            if (exprState == 0L) {
                                Basic_DiscardDuplicateBracket();
                                step = 1;
                                continue stepLoop;
                            } else {
                                exprState = (long) (int) Basic_GetExpressState();
                                if (exprState != 1L && comma(exprState = (long) (int) Basic_GetExpressState(), exprState != 2L ? 1L : 0L) != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    step = 1;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        stackDepth = (long) (byte) Stack_GetRegistersDepth();
                        putU32(g_basic_op, 99L);
                        if ((byte) stackDepth == 0L) {
                            putI32(g_result_state, 0L);
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        Basic_SetExpressState(1L);
                        step = 2;
                        continue stepLoop;
                    }
                    case 2: {
                        putU8(framePtr + 56L, 57L);
                        putU8(framePtr + 57L, 46L);
                        putU8(framePtr + 58L, 57L);
                        putU8(framePtr + 59L, 57L);
                        putU8(framePtr + 60L, 57L);
                        putU8(framePtr + 61L, 57L);
                        putU8(framePtr + 62L, 57L);
                        putU8(framePtr + 63L, 57L);
                        putU8(framePtr + 64L, 57L);
                        putU8(framePtr + 65L, 57L);
                        putU8(framePtr + 66L, 57L);
                        putU8(framePtr + 67L, 69L);
                        putU8(framePtr + 68L, 57L);
                        putU8(framePtr + 69L, 57L);
                        putU8(framePtr + 70L, 0L);
                        fStringToCC_er(framePtr + 56L, framePtr + 40L, 0L, 1L, 46L, 44L);
                        Stack_SetX(framePtr + 40L);
                        Com_SetResult(framePtr + 40L, keyCode & 0xffL);
                        putU8(g_com_result_overmax, 1L);
                        status = 99L;
                        return status;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_UNDO(long keyCode) {
        long flag = 0L;
        long tempVal = 0L;
        long dataPtr = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        if (getU8(g_undo_index) == 0L) {
                            return 99L;
                        }
                        putU8(g_undo_index, getU8(g_undo_index) - 1L);
                        if (getU8(g_undo_index) == 0L) {
                            LcdDriver_ClearAnnunciator(1L);
                            putU8(g_com_canundo, 0L);
                        }
                        tempVal = getU8(g_undo_index);
                        flag = getU8(g_undo_list + tempVal * 24L);
                        if ((long) (int) ((flag & 63L) - 2L) < 3L) {
                            putCCReal(framePtr + 16L, Stack_GetX());
                            Com_SetResult(framePtr + 16L, keyCode & 0xffL);
                            step = 1;
                            continue stepLoop;
                        } else {
                            if ((flag & 63L) == 1L) {
                                if (((flag >> 6) & 1L) != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    putU32(g_basic_displaymode, 0L);
                                    putI32(g_com_keyinmode, flag >> 7);
                                    if (getU8(g_basic_edit + 17L) == 0L) {
                                        dataPtr = getI64(g_undo_list + 8L + tempVal * 24L);
                                        putU32(g_basic_edit + 16L, getU16(dataPtr + 16L));
                                        putI64(g_basic_edit + 8L, getI64(dataPtr + 8L));
                                        putI64(g_basic_edit, getI64(dataPtr));
                                    } else {
                                        if (getI8(g_undo_list + 16L + tempVal * 24L) == 0L) {
                                            Basic_NewNumberKeyEntry((long) (byte) getU8(g_undo_list + 1L + tempVal * 24L), keyCode & 0xffL);
                                        } else {
                                            putI32(g_com_keyinmode, 1L);
                                            putU8(g_basic_edit + 13L, getI8(g_undo_list + 16L + tempVal * 24L));
                                            putU8(g_com_eex_forbackspace, 1L);
                                        }
                                    }
                                    Basic_PrintEditLine(keyCode & 0xffL);
                                    step = 1;
                                    continue stepLoop;
                                }
                            } else {
                                if ((flag & 63L) != 0L) {
                                    return 99L;
                                }
                                if (((flag >> 6) & 1L) != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    dataPtr = getI64(g_undo_list + 8L + tempVal * 24L);
                                    putU32(g_basic_displaymode, 0L);
                                    putI32(g_com_keyinmode, flag >> 7);
                                    putU32(g_basic_edit + 16L, getU16(dataPtr + 16L));
                                    putI64(g_basic_edit + 8L, getI64(dataPtr + 8L));
                                    putI64(g_basic_edit, getI64(dataPtr));
                                    fStringToCC_er(g_basic_edit, framePtr + 16L, 0L, 1L, 46L, 44L);
                                    BF123_CheckRealData(framePtr + 16L);
                                    Stack_SetX(framePtr + 16L);
                                    Basic_PrintEditLine(keyCode & 0xffL);
                                    step = 1;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 1: {
                        if (getU8(g_com_bracketmode) != 0L && (keyCode & 0xffL) != 0L) {
                            LcdDriver_SetAnnunciator(4L);
                        }
                        putI8(g_undo_flag, 0L);
                        return 99L;
                    }
                    case 2: {
                        putU32(g_basic_displaymode, 1L);
                        putCCReal(framePtr + 16L, getCCReal(getI64(g_undo_list + 8L + tempVal * 24L)));
                        Stack_SetX(framePtr + 16L);
                        Com_SetResult(framePtr + 16L, keyCode & 0xffL);
                        step = 1;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_SQUARE(long keyCode) {
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            Com_EndEditMode_ForBasic();
            status = Basic_CaculateWithOneParam(12L) & 0xffffffffL;
            if (status == 99L) {
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_BACKSPACE(long keyCode) {
        long tempVal1 = 0L;
        long mantissa = 0L;
        long tempVal2 = 0L;
        long byteVal = 0L;
        long flag1 = 0L;
        long flag2 = 0L;
        long isEqual = 0L;
        long stackDepth = 0L;
        long exprState = 0L;
        long tempVal3 = 0L;
        long dataPtr = 0L;
        long strPtr = 0L;
        long tempVal4 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            flag2 = getI32(g_com_machine_mode) == 0L ? 1L : 0L;
            if (getU8(g_undo_index) != 0L && (getU8(g_undo_list + (long) (int) (getU8(g_undo_index) - 1L) * 24L) & 63L) == 0L) {
                Basic_DisableUndoMode();
            }
            putCCReal(framePtr + 24L, Stack_GetX());
            byteVal = getI8(g_basic_edit + 15L);
            stackDepth = getI8(g_basic_edit + 14L);
            if (getU32(g_basic_displaymode) == 0L) {
                if (getU8(g_com_eex_forbackspace) == 0L) {
                    if (getI32(g_com_keyinmode) != 1L) {
                        tempVal3 = getU8(g_undo_index);
                        flag1 = getU8(g_basic_edit + 17L);
                        tempVal4 = getU8(g_basic_edit + 17L);
                        putU8(g_undo_list + tempVal3 * 24L, (getI32(g_com_keyinmode) << 7) | 1L);
                        putI8(g_undo_list + 16L + tempVal3 * 24L, 0L);
                        tempVal2 = getI64(g_basic_edit);
                        if (tempVal4 == 1L) {
                            dataPtr = getI64(g_undo_list + 8L + tempVal3 * 24L);
                            mantissa = (((getI8(g_basic_edit + 15L) & 0xffL)) << 56) | ((((((getI8(g_basic_edit + 14L) & 0xffL)) << 48) | ((((((getI8(g_basic_edit + 13L) & 0xffL)) << 40) | (getI64(g_basic_edit + 8L) & 0xffffffffffL))) & 0xffffffffffffL))) & 0xffffffffffffffL);
                            putU16(dataPtr + 16L, getU32(g_basic_edit + 16L));
                            putI64(dataPtr + 8L, mantissa);
                            putI64(dataPtr, tempVal2);
                            Basic_EndEditMode();
                            Stack_SetX(CC_0);
                            Com_SetResult(CC_0, keyCode & 0xffL);
                        } else {
                            strPtr = g_basic_edit + 1L + tempVal4;
                            putU32(g_basic_edit + 16L, ((((flag1 - 1L) & 0xffL)) << 8) | (getU32(g_basic_edit + 16L) & 0xffL));
                            if (getI8(strPtr) == 46L) {
                                putI8(strPtr, 48L);
                                tempVal1 = getU8(g_basic_edit + 1L + getU8(g_basic_edit + 17L));
                                putU8(g_basic_edit + 1L + getU8(g_basic_edit + 17L), 46L);
                                putU8(g_undo_list + 1L + tempVal3 * 24L, tempVal1);
                            } else {
                                strPtr = g_basic_edit + 1L + ((flag1 - 1L) & 0xffL);
                                stackDepth = getI8(strPtr);
                                putU8(g_undo_list + 1L + tempVal3 * 24L, stackDepth);
                                if ((byte) stackDepth != 46L) {
                                    putI8(strPtr, 48L);
                                }
                            }
                            fStringToCC_er(g_basic_edit, framePtr + 24L, 0L, 1L, 46L, 44L);
                            BF123_CheckRealData(framePtr + 24L);
                            Stack_SetX(framePtr + 24L);
                            Basic_PrintEditLine(keyCode & 0xffL);
                        }
                        putU8(g_undo_index, getU8(g_undo_index) + 1L);
                        if (flag2 != 0L && (keyCode & 0xffL) != 0L) {
                            LcdDriver_SetAnnunciator(1L);
                        }
                        putU8(g_com_canundo, 1L);
                        return 99L;
                    }
                } else {
                    putI32(g_com_keyinmode, 1L);
                }
                tempVal3 = getU8(g_undo_index);
                isEqual = getI8(g_basic_edit + 14L) == 48L ? 1L : 0L;
                putU8(g_undo_list + tempVal3 * 24L, 129L);
                tempVal1 = getI8(g_basic_edit + 13L) & 0xffL;
                if (isEqual != 0L && (byte) byteVal == 48L) {
                    putI32(g_com_keyinmode, 0L);
                    putU8(g_undo_list + 1L + tempVal3 * 24L, 0L);
                    putI8(g_undo_list + 16L + tempVal3 * 24L, tempVal1);
                    putU8(g_com_eex_forbackspace, 0L);
                } else {
                    putI8(g_basic_edit + 15L, stackDepth);
                    putI8(g_undo_list + 16L + tempVal3 * 24L, 0L);
                    putU8(g_undo_list + 1L + tempVal3 * 24L, byteVal);
                    putI8(g_basic_edit + 14L, 48L);
                }
                fStringToCC_er(g_basic_edit, framePtr + 24L, 0L, 1L, 46L, 44L);
                BF123_CheckRealData(framePtr + 24L);
                Stack_SetX(framePtr + 24L);
                Basic_PrintEditLine(keyCode & 0xffL);
                putU8(g_undo_index, getU8(g_undo_index) + 1L);
                if (flag2 != 0L && (keyCode & 0xffL) != 0L) {
                    LcdDriver_SetAnnunciator(1L);
                }
                putU8(g_com_canundo, 1L);
                return 99L;
            }
            if (getU8(g_undo_index) == 0L) {
                isEqual = fEQ(framePtr + 24L, CC_0) != 0L ? 1L : 0L;
                if (isEqual != 0L && comma(isEqual = fEQ(framePtr + 24L, CC_0) != 0L ? 1L : 0L, isEqual == 0L || comma(exprState = (long) (int) Basic_GetExpressState(), exprState != 2L ? 1L : 0L) != 0L ? 1L : 0L) != 0L && comma(exprState = (long) (int) Basic_GetExpressState(), exprState != 0L ? 1L : 0L) != 0L) {
                    putI32(g_result_state, 1L);
                    Basic_DisableUndoMode();
                } else {
                    dataPtr = getI64(g_undo_list + 8L + getU8(g_undo_index) * 24L);
                    putU8(g_undo_list + getU8(g_undo_index) * 24L, (getU8(g_undo_list + getU8(g_undo_index) * 24L) & 128L) | 65L);
                    putI64(dataPtr + 8L, getI64(framePtr + 32L));
                    putI64(dataPtr, getI64(framePtr + 24L));
                    putU8(g_undo_index, getU8(g_undo_index) + 1L);
                    if (flag2 != 0L && (keyCode & 0xffL) != 0L) {
                        LcdDriver_SetAnnunciator(1L);
                    }
                    putU8(g_com_canundo, 1L);
                }
            } else {
                if (getI8(g_undo_flag) != 0L || (getU8(g_undo_list + (long) (int) (getU8(g_undo_index) - 1L) * 24L) & 63L) == 0L || getU32(g_basic_displaymode) == 1L && (getU8(g_undo_list + (long) (int) (getU8(g_undo_index) - 1L) * 24L) & 63L) == 1L) {
                    Basic_ProcessUndoModeKeys();
                }
                Basic_DisableUndoMode();
            }
            Stack_SetX(CC_0);
            Com_SetResult(CC_0, keyCode & 0xffL);
            if (getI32(g_com_operationmode) == 0L) {
                exprState = (long) (int) Basic_GetExpressState();
                if (exprState == 2L || comma(stackDepth = (long) (byte) Stack_GetRegistersDepth(), (byte) stackDepth != 0L ? 1L : 0L) != 0L) {
                    putI8(g_undo_flag, 1L);
                    LcdDriver_ClearAnnunciator(4L);
                    putU8(g_com_canundo, 1L);
                    return 99L;
                }
            } else {
                if (getI32(g_com_operationmode) != 1L) {
                    return 99L;
                }
            }
            putI8(g_undo_flag, 0L);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_EQUAL(long keyCode) {
        long status = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            Com_EndEditMode_ForBasic();
            if (getI32(g_com_operationmode) == 1L) {
                Stack_ShiftUpOneStep();
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                putI32(g_result_state, 1L);
            }
            if (getI32(g_com_operationmode) == 0L) {
                status = Basic_CaculateWithBracket();
                result = (long) (int) status;
                if (result == 99L) {
                    putCCReal(framePtr + 24L, Stack_GetX());
                    Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                    Basic_SetExpressState(1L);
                }
                putI32(g_result_state, 0L);
                putI64(g_basic_lastx + 16L, 1L);
            } else {
                result = 99L;
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_f_1(long keyCode) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal = (getU32(g_basic_displaymode) != 0L || getU32(g_basic_op) != 99L) && getI32(g_result_state) != 0L ? 1L : 0L;
            Basic_EndEditMode();
            Basic_SetAccurate(1L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putI32(g_result_state, tempVal);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_f_2(long keyCode) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal = (getU32(g_basic_displaymode) != 0L || getU32(g_basic_op) != 99L) && getI32(g_result_state) != 0L ? 1L : 0L;
            Basic_EndEditMode();
            Basic_SetAccurate(2L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putI32(g_result_state, tempVal);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_f_3(long keyCode) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal = (getU32(g_basic_displaymode) != 0L || getU32(g_basic_op) != 99L) && getI32(g_result_state) != 0L ? 1L : 0L;
            Basic_EndEditMode();
            Basic_SetAccurate(3L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putI32(g_result_state, tempVal);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_f_4(long keyCode) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal = (getU32(g_basic_displaymode) != 0L || getU32(g_basic_op) != 99L) && getI32(g_result_state) != 0L ? 1L : 0L;
            Basic_EndEditMode();
            Basic_SetAccurate(4L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putI32(g_result_state, tempVal);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_f_5(long keyCode) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal = (getU32(g_basic_displaymode) != 0L || getU32(g_basic_op) != 99L) && getI32(g_result_state) != 0L ? 1L : 0L;
            Basic_EndEditMode();
            Basic_SetAccurate(5L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putI32(g_result_state, tempVal);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_f_6(long keyCode) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal = (getU32(g_basic_displaymode) != 0L || getU32(g_basic_op) != 99L) && getI32(g_result_state) != 0L ? 1L : 0L;
            Basic_EndEditMode();
            Basic_SetAccurate(6L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putI32(g_result_state, tempVal);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_f_7(long keyCode) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal = (getU32(g_basic_displaymode) != 0L || getU32(g_basic_op) != 99L) && getI32(g_result_state) != 0L ? 1L : 0L;
            Basic_EndEditMode();
            Basic_SetAccurate(7L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putI32(g_result_state, tempVal);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_f_8(long keyCode) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal = (getU32(g_basic_displaymode) != 0L || getU32(g_basic_op) != 99L) && getI32(g_result_state) != 0L ? 1L : 0L;
            Basic_EndEditMode();
            Basic_SetAccurate(8L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putI32(g_result_state, tempVal);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_f_9(long keyCode) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal = (getU32(g_basic_displaymode) != 0L || getU32(g_basic_op) != 99L) && getI32(g_result_state) != 0L ? 1L : 0L;
            Basic_EndEditMode();
            Basic_SetAccurate(9L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putI32(g_result_state, tempVal);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_f_0(long keyCode) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal = (getU32(g_basic_displaymode) != 0L || getU32(g_basic_op) != 99L) && getI32(g_result_state) != 0L ? 1L : 0L;
            Basic_EndEditMode();
            Basic_SetAccurate(0L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putI32(g_result_state, tempVal);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_f_DECIMALSEP(long keyCode) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal = (getU32(g_basic_displaymode) != 0L || getU32(g_basic_op) != 99L) && getI32(g_result_state) != 0L ? 1L : 0L;
            Basic_EndEditMode();
            Basic_SetAccurate(10L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putI32(g_result_state, tempVal);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_RND(long keyCode) {
        long stackDepth = 0L;
        long exprState = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        Com_EndEditMode_ForBasic();
                        if (getI32(g_com_operationmode) == 1L) {
                            putCCReal(g_basic_lastx, Stack_GetX());
                        }
                        if (getI32(g_com_operationmode) == 0L) {
                            exprState = (long) (int) Basic_GetExpressState();
                            if (exprState == 0L) {
                                Basic_DiscardDuplicateBracket();
                                step = 1;
                                continue stepLoop;
                            } else {
                                exprState = (long) (int) Basic_GetExpressState();
                                if (exprState != 1L && comma(exprState = (long) (int) Basic_GetExpressState(), exprState != 2L ? 1L : 0L) != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    step = 1;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        stackDepth = (long) (byte) Stack_GetRegistersDepth();
                        putU32(g_basic_op, 99L);
                        if ((byte) stackDepth == 0L) {
                            putI32(g_result_state, 0L);
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        Basic_SetExpressState(1L);
                        step = 2;
                        continue stepLoop;
                    }
                    case 2: {
                        putCCReal(framePtr + 40L, Stack_GetX());
                        tempVal = getU8(g_com_resultstate);
                        exprState = getI32(framePtr + 44L);
                        if (9L >= (long) (int) (exprState + getU8(g_com_resultstate))) {
                            firnd(framePtr + 40L, tempVal & 0xffL, framePtr + 24L, 0L);
                            Stack_SetX(framePtr + 24L);
                            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                            putI32(g_result_state, 0L);
                            return 99L;
                        }
                        if (exprState < 9L) {
                            tempVal = (9L - exprState) & 0xffffffffL;
                        } else {
                            tempVal = 10L;
                        }
                        firnd(framePtr + 40L, tempVal & 0xffL, framePtr + 24L, 0L);
                        Stack_SetX(framePtr + 24L);
                        Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                        putI32(g_result_state, 0L);
                        return 99L;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_SIGMA(long keyCode) {
        long intVal = 0L;
        long tempVal = 0L;
        intVal = getI32(g_com_machine_mode);
        if (getU32(g_com_prevkeycode) == 80L) {
            return 99L;
        }
        Basic_EndEditMode();
        tempVal = getU8(g_undo_index);
        putU8(g_undo_index, getU8(g_undo_index) + 1L);
        putU8(g_undo_list + tempVal * 24L, (getU8(g_undo_list + tempVal * 24L) & 192L) | 3L);
        if (intVal == 0L && (keyCode & 0xffL) != 0L) {
            LcdDriver_SetAnnunciator(1L);
        }
        putU8(g_com_canundo, 1L);
        if (getI32(g_com_machine_mode) == 0L) {
            LcdDriver_ClearAnnunciator(4L);
        }
        Com_SetResult(CC_0, keyCode & 0xffL);
        putI8(g_undo_flag, 1L);
        return 99L;
    }

    long Base_KeyDown_FIN(long keyCode) {
        long intVal = 0L;
        long flag1 = 0L;
        long flag2 = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            intVal = getI32(g_com_machine_mode);
            if (getU32(g_com_prevkeycode) == 88L) {
                return 99L;
            }
            flag1 = getU32(g_basic_displaymode) == 0L ? 1L : 0L;
            flag2 = getU32(g_basic_op) == 99L ? 1L : 0L;
            Basic_EndEditMode();
            tempVal = getU8(g_undo_index);
            putU8(g_undo_index, getU8(g_undo_index) + 1L);
            putU8(g_undo_list + tempVal * 24L, (getU8(g_undo_list + tempVal * 24L) & 192L) | 4L);
            if (intVal == 0L && (keyCode & 0xffL) != 0L) {
                LcdDriver_SetAnnunciator(1L);
            }
            putU8(g_com_canundo, 1L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putI8(g_undo_flag, 1L);
            if (flag1 != 0L && flag2 != 0L) {
                putI32(g_result_state, 0L);
            }
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_DATE(long keyCode) {
        long dataPtr1 = 0L;
        long byteVal1 = 0L;
        long byteVal2 = 0L;
        long dataPtr2 = 0L;
        long isLess = 0L;
        long tempVal = 0L;
        long intVal = 0L;
        long dayOfWeek = 0L;
        long status = 0L;
        CC_Real regY = new CC_Real();
        int savedSp = sp;
        long framePtr = allocFrame(272);
        try {
            status = Com_EndCalculateMode();
            dayOfWeek = getI32(g_com_datemode);
            if ((long) (int) status != 99L) {
                return status;
            }
            regY = Stack_GetY().copy();
            putCCReal(framePtr + 48L, Stack_GetX());
            isLess = Com_GetXInsertMode() != 0L ? 1L : 0L;
            Com_SetXInsertMode(isLess);
            intVal = getI32(g_com_datemode);
            putCCReal(framePtr + 32L, regY);
            putU32(framePtr + 32L, 3L);
            tempVal = DateValide(framePtr + 32L, getI32(g_com_datemode) == 1L ? 1L : 0L, 0L) & 0xffffffffL;
            if (!((tempVal & 1L) != 0L && comma(status = DateValidExtract(framePtr + 32L, framePtr + 80L, framePtr + 104L, framePtr + 76L, framePtr + 72L, intVal == 1L ? 1L : 0L, 0L), status & 1L) != 0L && getU32(framePtr + 76L) < 4047L && (getU32(framePtr + 76L) != 4046L || getU32(framePtr + 104L) < 12L && (getU32(framePtr + 104L) != 11L || (getI32(framePtr + 80L) & 0xffffffffL) < 26L)))) {
                status = 8L;
                return status;
            }
            putI64(framePtr + 88L, 0x307432300000000L);
            putI32(framePtr + 80L, 1L);
            putI32(framePtr + 84L, 6L);
            isLess = fiInteger(framePtr + 48L) != 0L ? 1L : 0L;
            if (!(isLess != 0L && comma(isLess = fLT(framePtr + 80L, framePtr + 48L) != 0L ? 1L : 0L, isLess == 0L ? 1L : 0L) != 0L)) {
                status = 8L;
                return status;
            }
            putI32(framePtr + 80L, -1L);
            isLess = fLT(framePtr + 48L, framePtr + 80L) != 0L ? 1L : 0L;
            if (isLess != 0L) {
                status = 8L;
                return status;
            }
            intVal = (long) (int) fCCToInt(framePtr + 48L);
            putCCReal(framePtr + 48L, DatePlus(framePtr + 32L, intVal, dayOfWeek == 1L ? 1L : 0L, 0L));
            intVal = getI32(g_com_datemode);
            putCCReal(framePtr + 80L, getCCReal(framePtr + 48L));
            tempVal = DateValide(framePtr + 80L, getI32(g_com_datemode) == 1L ? 1L : 0L, 0L) & 0xffffffffL;
            if ((tempVal & 1L) != 0L && comma(comma(status = DateValidExtract(framePtr + 80L, framePtr + 104L, framePtr + 76L, framePtr + 72L, framePtr + 28L, intVal == 1L ? 1L : 0L, 0L), intVal = getI32(g_com_operationmode)), status & 1L) != 0L && getU32(framePtr + 72L) < 4047L && (getU32(framePtr + 72L) != 4046L || getU32(framePtr + 76L) < 12L && (getU32(framePtr + 76L) != 11L || getU32(framePtr + 104L) < 26L))) {
                putCCReal(g_basic_lastx, Stack_GetX());
                if (intVal == 0L) {
                    putI64(g_basic_lastx + 16L, 1L);
                }
                Stack_ShiftDownOneStep();
                Com_SetXInsertMode(1L);
                DateValidExtract(framePtr + 80L, framePtr + 76L, framePtr + 72L, framePtr + 28L, framePtr + 24L, dayOfWeek == 1L ? 1L : 0L, 0L);
                dataPtr1 = framePtr + 72L;
                dataPtr2 = framePtr + 76L;
                if (getI32(g_com_datemode) != 1L) {
                    dataPtr1 = framePtr + 76L;
                    dataPtr2 = framePtr + 72L;
                }
                putU8(framePtr + 109L, 44L);
                if (getU32(g_com_decimal_mode) != 0L) {
                    putU8(framePtr + 109L, 46L);
                }
                tempVal = getU32(dataPtr2);
                byteVal1 = (long) (byte) (tempVal / 10L);
                putI8(framePtr + 110L, getU32(framePtr + 28L) / 1000L + 48L);
                putU8(framePtr + 111L, (((getU32(framePtr + 28L) % 1000L) & 0xffffffffL) / 100L) | 48L);
                putU8(framePtr + 112L, (((getU32(framePtr + 28L) % 100L) & 0xffffffffL) / 10L) | 48L);
                byteVal2 = (long) (byte) (getU32(dataPtr1) / 10L);
                putU8(framePtr + 113L, (getU32(framePtr + 28L) + getU32(framePtr + 28L) / 10L * -10L) | 48L);
                putU8(framePtr + 108L, (getU32(dataPtr1) + byteVal2 * -10L) | 48L);
                putU32(framePtr + 104L, ((((byteVal2 + 48L) & 0xffL)) << 24) | ((((getU8(framePtr + 109L) << 16) | (((((((tempVal + byteVal1 * -10L) & 0xffL)) << 8) | ((byteVal1 + 48L) & 0xffL))) & 0xffffL))) & 0xffffffL) | 12288L);
                putU8(framePtr + 114L, 32L);
                dayOfWeek = (long) (int) DateDayOfWeek(framePtr + 80L, dayOfWeek == 1L ? 1L : 0L);
                putI8(framePtr + 115L, dayOfWeek + 48L);
                putU8(framePtr + 116L, 0L);
                if ((keyCode & 0xffL) != 0L) {
                    if ((Long.divideUnsigned(tempVal, 10L) & 0xffL) == 0L) {
                        putU8(framePtr + 104L, 32L);
                    }
                    Com_PrintWithSign(framePtr + 104L, 0L, 0L);
                }
                Stack_SetX(framePtr + 48L);
                status = 99L;
                return status;
            } else {
                Com_SetXInsertMode(1L);
                status = 8L;
                return status;
            }
        } finally {
            sp = savedSp;
        }
    }

    long Com_IsValidDate(long dateRealPtr, long isDmy) {
        long intVal = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            intVal = getI32(g_com_datemode);
            tempVal1 = DateValide(dateRealPtr, getI32(g_com_datemode) == 1L ? 1L : 0L, isDmy & 0xffL) & 0xffffffffL;
            if (!((tempVal1 & 1L) != 0L && comma(tempVal2 = DateValidExtract(dateRealPtr, framePtr + 36L, framePtr + 32L, framePtr + 28L, framePtr + 24L, intVal == 1L ? 1L : 0L, isDmy & 0xffL), tempVal2 & 1L) != 0L && getU32(framePtr + 28L) < 4047L)) {
                result = 0L;
                return result;
            }
            if (getU32(framePtr + 28L) != 4046L) {
                result = 1L;
                return result;
            } else {
                if (getU32(framePtr + 32L) < 12L && comma(result = 1L, getU32(framePtr + 32L) != 11L || getU32(framePtr + 36L) < 26L ? 1L : 0L) != 0L) {
                    return result;
                }
                result = 0L;
                return result;
            }
        } finally {
            sp = savedSp;
        }
    }

    long Com_IsValidDeltaDays(long daysRealPtr) {
        long isLess = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            putI32(framePtr + 24L, 0L);
            putI32(framePtr + 28L, 0x3074323L);
            putI32(framePtr + 16L, 1L);
            putI32(framePtr + 20L, 6L);
            isLess = fiInteger(daysRealPtr) != 0L ? 1L : 0L;
            if (isLess == 0L || (long) (int) comma(isLess = fLT(framePtr + 16L, daysRealPtr) != 0L ? 1L : 0L, isLess) != 0L) {
                isLess = 0L;
            } else {
                putI32(framePtr + 16L, -1L);
                isLess = fLT(daysRealPtr, framePtr + 16L) != 0L ? 1L : 0L;
                isLess = isLess == 0L ? 1L : 0L;
            }
            return isLess;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_DELTA_DYS(long keyCode) {
        long isInsertMode = 0L;
        long tempVal = 0L;
        long intVal1 = 0L;
        long intVal2 = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            status = Com_EndCalculateMode();
            intVal2 = getI32(g_com_datemode);
            if ((long) (int) status != 99L) {
                return status;
            }
            putCCReal(framePtr + 40L, Stack_GetY());
            putCCReal(framePtr + 24L, Stack_GetX());
            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
            Com_SetXInsertMode(isInsertMode);
            intVal1 = getI32(g_com_datemode);
            putI32(framePtr + 40L, 3L);
            putI32(framePtr + 24L, 3L);
            tempVal = DateValide(framePtr + 40L, getI32(g_com_datemode) == 1L ? 1L : 0L, 0L) & 0xffffffffL;
            if (!((tempVal & 1L) != 0L && comma(comma(status = DateValidExtract(framePtr + 40L, framePtr + 68L, framePtr + 64L, framePtr + 60L, framePtr + 56L, intVal1 == 1L ? 1L : 0L, 0L), intVal1 = getI32(g_com_datemode)), status & 1L) != 0L && getU32(framePtr + 60L) < 4047L && (getU32(framePtr + 60L) != 4046L || getU32(framePtr + 64L) < 12L && (getU32(framePtr + 64L) != 11L || getU32(framePtr + 68L) < 26L)))) {
                status = 8L;
                return status;
            }
            tempVal = DateValide(framePtr + 24L, getI32(g_com_datemode) == 1L ? 1L : 0L, 0L) & 0xffffffffL;
            if ((tempVal & 1L) != 0L && comma(status = DateValidExtract(framePtr + 24L, framePtr + 68L, framePtr + 64L, framePtr + 60L, framePtr + 56L, intVal1 == 1L ? 1L : 0L, 0L), (status & 1L) != 0L && getU32(framePtr + 60L) < 4047L ? 1L : 0L) != 0L && (getU32(framePtr + 60L) != 4046L || getU32(framePtr + 64L) < 12L && (getU32(framePtr + 64L) != 11L || getU32(framePtr + 68L) < 26L))) {
                intVal1 = (long) (int) DateDDays(framePtr + 40L, framePtr + 24L, intVal2 == 1L ? 1L : 0L, 0L);
                intVal2 = (long) (int) DateDDays(framePtr + 40L, framePtr + 24L, intVal2 == 1L ? 1L : 0L, 1L);
                fIntToCC(intVal1, framePtr + 24L);
                fIntToCC(intVal2, framePtr + 40L);
                if (getI32(g_com_operationmode) == 0L) {
                    putI64(g_basic_lastx + 16L, 1L);
                } else {
                    putCCReal(g_basic_lastx, Stack_GetX());
                }
                Stack_SetX(framePtr + 24L);
                Stack_SetY(framePtr + 40L);
                Com_SetXInsertMode(1L);
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                status = 99L;
                return status;
            } else {
                status = 8L;
                return status;
            }
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_D_MY(long keyCode) {
        long isInsertMode = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            Com_EndEditMode_CareOP();
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
            Com_SetXInsertMode(isInsertMode);
            putI32(g_com_datemode, 1L);
            if ((keyCode & 0xffL) != 0L) {
                LcdDriver_SetAnnunciator(8L);
            }
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_M_DY(long keyCode) {
        long isInsertMode = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            Com_EndEditMode_CareOP();
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
            Com_SetXInsertMode(isInsertMode);
            putI32(g_com_datemode, 0L);
            if ((keyCode & 0xffL) != 0L) {
                LcdDriver_ClearAnnunciator(8L);
            }
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_R(long keyCode) {
        long stackDepth = 0L;
        long exprState = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            Basic_EndEditMode();
            if (getI32(g_com_operationmode) == 1L) {
                Stack_RotateDownOneStep();
                putI32(g_result_state, 0L);
            }
            if (getI32(g_com_operationmode) != 0L) {
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                return 99L;
            }
            exprState = (long) (int) Basic_GetExpressState();
            if (exprState == 0L) {
                Stack_RotateDownOneStep();
                putI32(g_result_state, 1L);
                Basic_ChangeExpressState(0L);
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                return 99L;
            }
            exprState = (long) (int) Basic_GetExpressState();
            if (exprState == 1L) {
                Stack_RotateDownOneStep();
                stackDepth = (long) (byte) Stack_GetRegistersDepth();
                if ((byte) stackDepth == 0L) {
                    putI32(g_result_state, 0L);
                    putCCReal(framePtr + 24L, Stack_GetX());
                    Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                    return 99L;
                }
            } else {
                exprState = (long) (int) Basic_GetExpressState();
                if (exprState == 2L) {
                    Stack_PushXToX1();
                    Stack_RotateDownOneStep();
                    putI32(g_result_state, 1L);
                    Basic_ChangeExpressState(0L);
                    putCCReal(framePtr + 24L, Stack_GetX());
                    Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                    return 99L;
                }
                Stack_RotateDownOneStep();
            }
            putI32(g_result_state, 1L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_X_NOTEQUAL_Y(long keyCode) {
        long stackDepth = 0L;
        long exprState = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            Basic_EndEditMode();
            if (getI32(g_com_operationmode) == 1L) {
                Stack_SwapXY();
                putI32(g_result_state, 0L);
            }
            if (getI32(g_com_operationmode) != 0L) {
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                return 99L;
            }
            exprState = (long) (int) Basic_GetExpressState();
            if (exprState == 0L) {
                Stack_SwapXY();
                putI32(g_result_state, 1L);
                Basic_ChangeExpressState(0L);
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                return 99L;
            }
            exprState = (long) (int) Basic_GetExpressState();
            if (exprState == 1L) {
                Stack_SwapXY();
                stackDepth = (long) (byte) Stack_GetRegistersDepth();
                if ((byte) stackDepth == 0L) {
                    putI32(g_result_state, 0L);
                    putCCReal(framePtr + 24L, Stack_GetX());
                    Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                    return 99L;
                }
            } else {
                exprState = (long) (int) Basic_GetExpressState();
                if (exprState == 2L) {
                    Stack_PushXToX1();
                    Stack_SwapXY();
                    putI32(g_result_state, 1L);
                    Basic_ChangeExpressState(0L);
                    putCCReal(framePtr + 24L, Stack_GetX());
                    Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                    return 99L;
                }
                Stack_SwapXY();
            }
            putI32(g_result_state, 1L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_STO(long keyCode) {
        putU16(g_reg_state, 256L);
        putU8(g_reg_state + 2L, 0L);
        putI32(g_com_keyinmode, 3L);
        return 99L;
    }

    long Base_KeyDown_CLX(long keyCode) {
        long tempVal1 = 0L;
        long mantissa = 0L;
        long tempVal2 = 0L;
        long flag = 0L;
        long stackDepth = 0L;
        long isEqual = 0L;
        long exprState = 0L;
        long dataPtr = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        tempVal2 = getU32(g_basic_edit + 16L) & 0xffffL;
                        mantissa = getI64(g_basic_edit + 8L);
                        tempVal1 = getI64(g_basic_edit);
                        flag = getI32(g_com_machine_mode) == 0L ? 1L : 0L;
                        if (getU32(g_basic_displaymode) == 0L) {
                            dataPtr = getI64(g_undo_list + 8L + getU8(g_undo_index) * 24L);
                            putU8(g_undo_list + getU8(g_undo_index) * 24L, getI32(g_com_keyinmode) << 7);
                            putU16(dataPtr + 16L, tempVal2);
                            putI64(dataPtr + 8L, mantissa);
                            putI64(dataPtr, tempVal1);
                            putI8(g_undo_list + 16L + getU8(g_undo_index) * 24L, 0L);
                            step = 2;
                            continue stepLoop;
                        } else {
                            putCCReal(framePtr + 24L, Stack_GetX());
                            if (getU8(g_undo_index) == 0L) {
                                isEqual = fEQ(framePtr + 24L, CC_0) != 0L ? 1L : 0L;
                                if (isEqual == 0L || comma(isEqual = fEQ(framePtr + 24L, CC_0) != 0L ? 1L : 0L, isEqual != 0L && comma(exprState = (long) (int) Basic_GetExpressState(), exprState == 2L ? 1L : 0L) != 0L ? 1L : 0L) != 0L || comma(exprState = (long) (int) Basic_GetExpressState(), exprState == 0L ? 1L : 0L) != 0L) {
                                    dataPtr = getI64(g_undo_list + 8L + getU8(g_undo_index) * 24L);
                                    putU8(g_undo_list + getU8(g_undo_index) * 24L, (getU8(g_undo_list + getU8(g_undo_index) * 24L) & 128L) | 64L);
                                    putI64(dataPtr + 8L, getI64(framePtr + 32L));
                                    putI64(dataPtr, getI64(framePtr + 24L));
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    putI32(g_result_state, 1L);
                                    Basic_DisableUndoMode();
                                    step = 1;
                                    continue stepLoop;
                                }
                            } else {
                                if (getI8(g_undo_flag) != 0L || (getU8(g_undo_list + (long) (int) (getU8(g_undo_index) - 1L) * 24L) & 63L) == 0L || (getU8(g_undo_list + (long) (int) (getU8(g_undo_index) - 1L) * 24L) & 63L) == 1L && getU32(g_basic_displaymode) == 1L) {
                                    Basic_ProcessUndoModeKeys();
                                }
                                Basic_DisableUndoMode();
                                step = 1;
                                continue stepLoop;
                            }
                        }
                    }
                    case 1: {
                        putU32(g_basic_edit + 16L, 0L);
                        putU8(g_basic_edit + 8L, 48L);
                        putU8(g_basic_edit + 9L, 48L);
                        putU8(g_basic_edit + 10L, 48L);
                        putU8(g_basic_edit + 11L, 48L);
                        putU8(g_basic_edit + 12L, 69L);
                        putI8(g_basic_edit + 13L, 43L);
                        putI8(g_basic_edit + 14L, 48L);
                        putI8(g_basic_edit + 15L, 48L);
                        putI8(g_basic_edit, 43L);
                        putU8(g_basic_edit + 1L, 46L);
                        putU8(g_basic_edit + 2L, 48L);
                        putU8(g_basic_edit + 3L, 48L);
                        putU8(g_basic_edit + 4L, 48L);
                        putU8(g_basic_edit + 5L, 48L);
                        putU8(g_basic_edit + 6L, 48L);
                        putU8(g_basic_edit + 7L, 48L);
                        Stack_SetX(CC_0);
                        Com_SetResult(CC_0, keyCode & 0xffL);
                        if (getI32(g_com_operationmode) == 0L) {
                            exprState = (long) (int) Basic_GetExpressState();
                            if (exprState == 2L || comma(stackDepth = (long) (byte) Stack_GetRegistersDepth(), (byte) stackDepth != 0L ? 1L : 0L) != 0L) {
                                putI8(g_undo_flag, 1L);
                                LcdDriver_ClearAnnunciator(4L);
                                putU8(g_com_canundo, 1L);
                                putI32(g_com_keyinmode, 0L);
                                putU8(g_com_eex_forbackspace, 0L);
                                return 99L;
                            }
                        } else {
                            if (getI32(g_com_operationmode) != 1L) {
                                putI32(g_com_keyinmode, 0L);
                                putU8(g_com_eex_forbackspace, 0L);
                                return 99L;
                            }
                        }
                        putI8(g_undo_flag, 0L);
                        putI32(g_com_keyinmode, 0L);
                        putU8(g_com_eex_forbackspace, 0L);
                        return 99L;
                    }
                    case 2: {
                        putU8(g_undo_index, getU8(g_undo_index) + 1L);
                        if (flag != 0L && (keyCode & 0xffL) != 0L) {
                            LcdDriver_SetAnnunciator(1L);
                        }
                        putU8(g_com_canundo, 1L);
                        step = 1;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_RCL(long keyCode) {
        putU16(g_reg_state, 512L);
        putU8(g_reg_state + 2L, 0L);
        putI32(g_com_keyinmode, 2L);
        return 99L;
    }

    long Base_KeyDown_REG(long keyCode) {
        long intVal = 0L;
        long tempVal = 0L;
        intVal = getI32(g_com_machine_mode);
        if (getU32(g_com_prevkeycode) == 92L) {
            return 99L;
        }
        Basic_EndEditMode();
        tempVal = getU8(g_undo_index);
        putU8(g_undo_index, getU8(g_undo_index) + 1L);
        putU8(g_undo_list + tempVal * 24L, (getU8(g_undo_list + tempVal * 24L) & 192L) | 2L);
        if (intVal == 0L && (keyCode & 0xffL) != 0L) {
            LcdDriver_SetAnnunciator(1L);
        }
        putU8(g_com_canundo, 1L);
        if (getI32(g_com_machine_mode) == 0L) {
            LcdDriver_ClearAnnunciator(4L);
        }
        Com_SetResult(CC_0, keyCode & 0xffL);
        putI8(g_undo_flag, 1L);
        return 99L;
    }

    long Base_KeyDown_LSTX(long keyCode) {
        CC_Real realVal = new CC_Real();
        long stackDepth = 0L;
        long exprState = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            Basic_EndEditMode();
            if (getI32(g_com_operationmode) == 1L) {
                Stack_InsertToX(g_basic_lastx);
                Com_SetResult(g_basic_lastx, keyCode & 0xffL);
                putI32(g_com_keyinmode, 0L);
                putI32(g_result_state, 0L);
            }
            if (getI32(g_com_operationmode) != 0L) {
                return 99L;
            }
            exprState = (long) (int) Basic_GetExpressState();
            if (exprState == 0L) {
                Stack_SetX(g_basic_lastx);
                putI32(g_result_state, 1L);
                Basic_ChangeExpressState(0L);
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                return 99L;
            }
            exprState = (long) (int) Basic_GetExpressState();
            if (exprState == 1L) {
                stackDepth = (long) (byte) Stack_GetRegistersDepth();
                if ((byte) stackDepth == 0L) {
                    putCCReal(framePtr + 24L, Stack_GetX());
                    if (getU32(g_basic_displaymode) == 0L || getI32(g_result_state) == 0L) {
                        Stack_InsertToX(g_basic_lastx);
                    } else {
                        Stack_SetX(g_basic_lastx);
                    }
                    realVal = getCCReal(framePtr + 24L);
                    putI32(g_result_state, 0L);
                    putI64(g_basic_lastx + 8L, getI64(framePtr + 32L));
                    putI32(g_basic_lastx, getI32(framePtr + 24L));
                    putI32(g_basic_lastx + 4L, getI32(framePtr + 28L));
                    putCCReal(framePtr + 24L, realVal);
                    putCCReal(framePtr + 24L, Stack_GetX());
                    Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                    return 99L;
                }
            } else {
                exprState = (long) (int) Basic_GetExpressState();
                if (exprState == 2L) {
                    Stack_PushXToX1();
                    Stack_SetX(g_basic_lastx);
                    putI32(g_result_state, 1L);
                    Basic_ChangeExpressState(0L);
                    putCCReal(framePtr + 24L, Stack_GetX());
                    Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                    return 99L;
                }
            }
            Stack_SetX(g_basic_lastx);
            putI32(g_result_state, 1L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long CC_New_INT(long nPtr, long iPtr, long pvPtr, long outIntPtr, long outPrinPlusIntPtr) {
        long flag = 0L;
        long isZero = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            putI64(framePtr + 56L, 0L);
            putI64(framePtr + 48L, 0L);
            putU32(framePtr + 24L, 0L);
            putU32(framePtr + 28L, 0x3650000L);
            putU32(framePtr + 16L, 1L);
            putU32(framePtr + 20L, 4L);
            putU32(framePtr + 40L, 0L);
            putU32(framePtr + 44L, 0x3600000L);
            putU32(framePtr + 32L, 1L);
            putU32(framePtr + 36L, 4L);
            putI64(outIntPtr + 8L, 0L);
            putI32(outIntPtr, 1L);
            putI32(outIntPtr + 4L, 0L);
            putI64(outPrinPlusIntPtr + 8L, 0L);
            putI32(outPrinPlusIntPtr, 1L);
            putI32(outPrinPlusIntPtr + 4L, 0L);
            status = fmul(nPtr, iPtr, framePtr + 48L) & 0xffffffffL;
            if (!(status < 2L && comma(status = fmul(framePtr + 48L, pvPtr, framePtr + 48L) & 0xffffffffL, status < 2L ? 1L : 0L) != 0L)) {
                return status;
            }
            flag = fdiv(framePtr + 48L, framePtr + 32L, outIntPtr) & 0xffL;
            status = flag;
            isZero = fZero(outIntPtr) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI32(outIntPtr, -getI32(outIntPtr));
            }
            if (flag >= 2L) {
                return status;
            }
            flag = fdiv(framePtr + 48L, framePtr + 16L, outPrinPlusIntPtr) & 0xffL;
            status = flag;
            isZero = fZero(outPrinPlusIntPtr) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI32(outPrinPlusIntPtr, -getI32(outPrinPlusIntPtr));
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_PRICE(long keyCode) {
        CC_Real realVal1 = new CC_Real();
        CC_Real realVal2 = new CC_Real();
        CC_Real realVal3 = new CC_Real();
        CC_Real realVal4 = new CC_Real();
        CC_Real realVal5 = new CC_Real();
        CC_Real realVal6 = new CC_Real();
        long intVal = 0L;
        long isValidDate = 0L;
        long status = 0L;
        long errCode = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(352);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        status = Com_EndCalculateMode();
                        intVal = getI32(g_com_datemode);
                        result = status & 0xffffffffL;
                        if ((long) (int) status != 99L) {
                            return result;
                        }
                        isValidDate = getI32(g_com_datemode) == 1L ? 1L : 0L;
                        putCCReal(framePtr + 96L, Stack_GetY());
                        putI64(framePtr + 168L, getI64(framePtr + 104L));
                        putI32(framePtr + 164L, getI32(framePtr + 100L));
                        putI32(framePtr + 160L, 3L);
                        status = DateValidExtract(framePtr + 160L, framePtr + 60L, framePtr + 56L, framePtr + 52L, framePtr + 48L, isValidDate, 0L);
                        if ((status & 1L) == 0L || comma(status = Com_IsValidDate(framePtr + 160L, 0L), (status & 1L) == 0L ? 1L : 0L) != 0L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            putCCReal(framePtr + 96L, Stack_GetX());
                            putI64(framePtr + 152L, getI64(framePtr + 104L));
                            putI32(framePtr + 148L, getI32(framePtr + 100L));
                            putI32(framePtr + 144L, 3L);
                            status = Com_IsValidDate(framePtr + 144L, 0L);
                            if ((status & 1L) == 0L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                realVal4.e = getI32(framePtr + 164L);
                                realVal4.s = getI32(framePtr + 160L);
                                realVal4.m = getI64(framePtr + 168L);
                                realVal1.e = getI32(framePtr + 148L);
                                realVal1.s = getI32(framePtr + 144L);
                                realVal1.m = getI64(framePtr + 152L);
                                putCCReal(framePtr + 128L, Fin_GetInterestDay(realVal4.copy(), realVal1.copy(), 0L, 0L));
                                realVal5.e = getI32(framePtr + 164L);
                                realVal5.s = getI32(framePtr + 160L);
                                realVal5.m = getI64(framePtr + 168L);
                                realVal2.e = getI32(framePtr + 148L);
                                realVal2.s = getI32(framePtr + 144L);
                                realVal2.m = getI64(framePtr + 152L);
                                putCCReal(framePtr + 112L, Fin_GetInterestDay(realVal5.copy(), realVal2.copy(), 0L, 1L));
                                realVal6.e = getI32(framePtr + 164L);
                                realVal6.s = getI32(framePtr + 160L);
                                realVal6.m = getI64(framePtr + 168L);
                                realVal3.e = getI32(framePtr + 148L);
                                realVal3.s = getI32(framePtr + 144L);
                                realVal3.m = getI64(framePtr + 152L);
                                isValidDate = Fin_CheckBetweenDate(realVal6.copy(), realVal3.copy(), 0L) != 0L ? 1L : 0L;
                                if (isValidDate == 0L || comma(status = Com_IsValidDate(framePtr + 128L, 0L), (status & 1L) == 0L ? 1L : 0L) != 0L || comma(status = Com_IsValidDate(framePtr + 112L, 0L), (status & 1L) == 0L ? 1L : 0L) != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    if ((keyCode & 0xffL) != 0L) {
                                        Com_StartFlashRunning();
                                    }
                                    putI64(framePtr + 24L, 0L);
                                    putI64(framePtr + 16L, 1L);
                                    putI64(framePtr + 32L, getI64(framePtr + 16L));
                                    putI64(framePtr + 40L, getI64(framePtr + 24L));
                                    status = BONDAccrued(framePtr + 160L, framePtr + 144L, framePtr + 32L, g_fin_pmt, framePtr + 16L, framePtr + 64L, 0L, intVal == 1L ? 1L : 0L, 0L);
                                    KeypadDriver_ClearKeyBuffer();
                                    errCode = Com_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 64L);
                                    realVal1 = getCCReal(framePtr + 64L);
                                    if ((long) (int) errCode == 0L) {
                                        putI64(framePtr + 88L, getI64(framePtr + 72L));
                                        putI32(framePtr + 80L, getI32(framePtr + 64L));
                                        putI32(framePtr + 84L, getI32(framePtr + 68L));
                                        putCCReal(framePtr + 64L, realVal1);
                                        fIntToCC(100L, framePtr + 96L);
                                        status = BONDPrice(framePtr + 160L, framePtr + 144L, framePtr + 64L, g_fin_i, g_fin_pmt, framePtr + 96L, 0L, intVal == 1L ? 1L : 0L, 0L);
                                        KeypadDriver_ClearKeyBuffer();
                                        if ((keyCode & 0xffL) != 0L) {
                                            Com_StopFlashRunning();
                                        }
                                        errCode = Com_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 64L);
                                        realVal1 = getCCReal(framePtr + 64L);
                                        if ((long) (int) errCode != 0L) {
                                            if ((long) (int) status != 24L) {
                                                if ((long) (int) status != 30L) {
                                                    step = 3;
                                                    continue stepLoop;
                                                } else {
                                                    step = 4;
                                                    continue stepLoop;
                                                }
                                            } else {
                                                step = 2;
                                                continue stepLoop;
                                            }
                                        } else {
                                            putI64(g_fin_pv + 8L, getI64(framePtr + 72L));
                                            putI32(g_fin_pv, getI32(framePtr + 64L));
                                            putI32(g_fin_pv + 4L, getI32(framePtr + 68L));
                                            putCCReal(framePtr + 64L, realVal1);
                                            fIntToCC(2L, framePtr + 184L);
                                            fdiv(g_fin_pmt, framePtr + 184L, framePtr + 200L);
                                            fIntToCC(100L, framePtr + 184L);
                                            fradd(framePtr + 200L, framePtr + 184L, g_fin_fv, 0L);
                                            isValidDate = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                            if (isValidDate != 0L) {
                                                Stack_InsertToX(framePtr + 80L);
                                            } else {
                                                Stack_SetX(framePtr + 80L);
                                            }
                                            Stack_InsertToX(framePtr + 64L);
                                            Com_SetResult(framePtr + 64L, keyCode & 0xffL);
                                            result = 99L;
                                            step = 1;
                                            continue stepLoop;
                                        }
                                    } else {
                                        if ((keyCode & 0xffL) != 0L) {
                                            Com_StopFlashRunning();
                                        }
                                        if ((long) (int) status == 24L) {
                                            step = 2;
                                            continue stepLoop;
                                        } else {
                                            if ((long) (int) status == 30L) {
                                                step = 4;
                                                continue stepLoop;
                                            } else {
                                                step = 3;
                                                continue stepLoop;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    case 1: {
                        putI32(g_com_keyinmode, 0L);
                        Com_SetXInsertMode(1L);
                        if (getI32(g_com_operationmode) == 0L) {
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        return result;
                    }
                    case 2: {
                        result = 8L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 3: {
                        result = 5L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 4: {
                        putCCReal(framePtr + 64L, Stack_GetX());
                        Com_SetResult(framePtr + 64L, keyCode & 0xffL);
                        result = 99L;
                        step = 1;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    CC_Real Fin_GetInterestDay(CC_Real buyDate, CC_Real matDate, long outDays360Ptr, long outActualDaysPtr) {
        long tempVal1 = 0L;
        CC_Real realVal = new CC_Real();
        long intVal = 0L;
        long isLeapYear = 0L;
        long dayIndex = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        long tempVal5 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(256);
        try {
            intVal = getI32(g_com_datemode);
            putI64(framePtr + 56L, matDate.m);
            putI64(framePtr + 48L, (((matDate.e & 0xffffffffL)) << 32) | (matDate.s & 0xffffffffL));
            putI64(framePtr + 72L, buyDate.m);
            putI64(framePtr + 64L, (((buyDate.e & 0xffffffffL)) << 32) | (buyDate.s & 0xffffffffL));
            tempVal3 = outDays360Ptr & 0xffL;
            isLeapYear = getI32(g_com_datemode) == 1L ? 1L : 0L;
            DateValidExtract(framePtr + 48L, framePtr + 44L, framePtr + 40L, framePtr + 36L, framePtr + 20L, isLeapYear, outDays360Ptr & 0xffL);
            DateValidExtract(framePtr + 64L, framePtr + 32L, framePtr + 28L, framePtr + 24L, framePtr + 20L, isLeapYear, outDays360Ptr & 0xffL);
            dayIndex = (getU32(framePtr + 40L) + 250L) & 0xffffffffL;
            if ((getU32(framePtr + 40L) & 0xffL) < 7L) {
                dayIndex = getU32(framePtr + 40L);
            }
            dayIndex = dayIndex & 0xffL;
            tempVal4 = dayIndex;
            tempVal5 = getU32(framePtr + 24L);
            if (getU32(framePtr + 28L) < dayIndex || getU32(framePtr + 28L) == dayIndex && getU32(framePtr + 32L) < getU32(framePtr + 44L)) {
                if ((outActualDaysPtr & 0xffL) == 0L) {
                    tempVal4 = (dayIndex + 6L) & 0xffffffffL;
                    tempVal5 = (getU32(framePtr + 24L) - 1L) & 0xffffffffL;
                }
            } else {
                tempVal1 = (dayIndex + 6L) & 0xffffffffL;
                if (tempVal1 < getU32(framePtr + 28L) || getU32(framePtr + 28L) == tempVal1 && getU32(framePtr + 44L) < getU32(framePtr + 32L)) {
                    tempVal4 = tempVal1;
                    if ((outActualDaysPtr & 0xffL) != 0L) {
                        tempVal4 = dayIndex;
                        tempVal5 = (getU32(framePtr + 24L) + 1L) & 0xffffffffL;
                    }
                } else {
                    if ((outActualDaysPtr & 0xffL) != 0L) {
                        tempVal4 = tempVal1;
                    }
                }
            }
            isLeapYear = DateLeap(tempVal5) != 0L ? 1L : 0L;
            dayIndex = DateDayIndexFromDateinternal(getU32(framePtr + 44L), tempVal4, (long) (int) tempVal5, isLeapYear, tempVal3) & 0xffffffffL;
            DateFromDayIndex(dayIndex, framePtr + 80L, intVal == 1L ? 1L : 0L, tempVal3);
            tempVal2 = DateValidExtract(framePtr + 80L, framePtr + 32L, framePtr + 28L, framePtr + 24L, framePtr + 20L, intVal == 1L ? 1L : 0L, tempVal3);
            if ((tempVal2 & 1L) == 0L || getU32(framePtr + 44L) != getU32(framePtr + 32L) || tempVal4 != getU32(framePtr + 28L) || tempVal5 != getU32(framePtr + 24L)) {
                putI64(framePtr + 88L, 0L);
                putI64(framePtr + 80L, 0L);
            }
            realVal.m = getI64(framePtr + 88L);
            realVal.s = (long) (int) getI64(framePtr + 80L);
            realVal.e = getI32(framePtr + 84L);
            return realVal.copy();
        } finally {
            sp = savedSp;
        }
    }

    long Fin_CheckBetweenDate(CC_Real date1, CC_Real date2, long isDmy) {
        long flag = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            putI64(framePtr + 64L, date2.m);
            putI64(framePtr + 56L, (((date2.e & 0xffffffffL)) << 32) | (date2.s & 0xffffffffL));
            putI64(framePtr + 80L, date1.m);
            putI64(framePtr + 72L, (((date1.e & 0xffffffffL)) << 32) | (date1.s & 0xffffffffL));
            flag = getI32(g_com_datemode) == 1L ? 1L : 0L;
            DateValidExtract(framePtr + 72L, framePtr + 52L, framePtr + 48L, framePtr + 44L, framePtr + 28L, flag, isDmy & 0xffL);
            DateValidExtract(framePtr + 56L, framePtr + 40L, framePtr + 36L, framePtr + 32L, framePtr + 28L, flag, isDmy & 0xffL);
            if (getU32(framePtr + 32L) < getU32(framePtr + 44L) || getU32(framePtr + 32L) == getU32(framePtr + 44L) && (getU32(framePtr + 36L) < getU32(framePtr + 48L) || getU32(framePtr + 48L) == getU32(framePtr + 36L) && getU32(framePtr + 40L) <= getU32(framePtr + 52L)) || 500L < (long) (int) (getU32(framePtr + 32L) - getU32(framePtr + 44L))) {
                flag = 0L;
                return flag;
            }
            if (((getU32(framePtr + 32L) - getU32(framePtr + 44L)) & 0xffffffffL) != 500L) {
                flag = 1L;
                return flag;
            }
            if ((long) (int) (getU32(framePtr + 48L) - getU32(framePtr + 36L)) < 6L) {
                flag = 0L;
                return flag;
            }
            if (((getU32(framePtr + 48L) - getU32(framePtr + 36L)) & 0xffffffffL) == 6L) {
                flag = (long) (int) (getU32(framePtr + 40L) - getU32(framePtr + 52L)) < 1L ? 1L : 0L;
                return flag;
            } else {
                flag = 1L;
                return flag;
            }
        } finally {
            sp = savedSp;
        }
    }

    long Fin_GetBondFV(long pricePtr, long yieldPtr, long outFvPtr) {
        long flag = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            fIntToCC(2L, framePtr + 24L);
            fdiv(pricePtr, framePtr + 24L, framePtr + 40L);
            fIntToCC(yieldPtr & 0xffL, framePtr + 24L);
            flag = fradd(framePtr + 40L, framePtr + 24L, outFvPtr, 0L) != 0L ? 1L : 0L;
            return flag;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_YTM(long keyCode) {
        CC_Real realVal1 = new CC_Real();
        CC_Real realVal2 = new CC_Real();
        CC_Real realVal3 = new CC_Real();
        CC_Real realVal4 = new CC_Real();
        CC_Real realVal5 = new CC_Real();
        CC_Real realVal6 = new CC_Real();
        long intVal = 0L;
        long isLessOrEqual = 0L;
        long status = 0L;
        long errCode = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(288);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        status = Com_EndCalculateMode();
                        intVal = getI32(g_com_datemode);
                        result = status & 0xffffffffL;
                        if ((long) (int) status != 99L) {
                            return result;
                        }
                        putCCReal(framePtr + 48L, Stack_GetY());
                        putI64(framePtr + 120L, getI64(framePtr + 56L));
                        putI32(framePtr + 116L, getI32(framePtr + 52L));
                        putI32(framePtr + 112L, 3L);
                        status = Com_IsValidDate(framePtr + 112L, 0L);
                        if ((status & 1L) == 0L || comma(status = DateValidExtract(framePtr + 112L, framePtr + 28L, framePtr + 24L, framePtr + 20L, framePtr + 16L, intVal == 1L ? 1L : 0L, 0L), (status & 1L) == 0L ? 1L : 0L) != 0L || getU32(framePtr + 20L) == 1582L && getU32(framePtr + 24L) == 10L && getU32(framePtr + 28L) == 15L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            putCCReal(framePtr + 48L, Stack_GetX());
                            putI64(framePtr + 104L, getI64(framePtr + 56L));
                            putI32(framePtr + 100L, getI32(framePtr + 52L));
                            putI32(framePtr + 96L, 3L);
                            status = Com_IsValidDate(framePtr + 96L, 0L);
                            if ((status & 1L) == 0L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                realVal4.e = getI32(framePtr + 116L);
                                realVal4.s = getI32(framePtr + 112L);
                                realVal4.m = getI64(framePtr + 120L);
                                realVal1.e = getI32(framePtr + 100L);
                                realVal1.s = getI32(framePtr + 96L);
                                realVal1.m = getI64(framePtr + 104L);
                                putCCReal(framePtr + 80L, Fin_GetInterestDay(realVal4.copy(), realVal1.copy(), 0L, 0L));
                                realVal5.e = getI32(framePtr + 116L);
                                realVal5.s = getI32(framePtr + 112L);
                                realVal5.m = getI64(framePtr + 120L);
                                realVal2.e = getI32(framePtr + 100L);
                                realVal2.s = getI32(framePtr + 96L);
                                realVal2.m = getI64(framePtr + 104L);
                                putCCReal(framePtr + 64L, Fin_GetInterestDay(realVal5.copy(), realVal2.copy(), 0L, 1L));
                                realVal6.e = getI32(framePtr + 116L);
                                realVal6.s = getI32(framePtr + 112L);
                                realVal6.m = getI64(framePtr + 120L);
                                realVal3.e = getI32(framePtr + 100L);
                                realVal3.s = getI32(framePtr + 96L);
                                realVal3.m = getI64(framePtr + 104L);
                                isLessOrEqual = Fin_CheckBetweenDate(realVal6.copy(), realVal3.copy(), 0L) != 0L ? 1L : 0L;
                                if (isLessOrEqual == 0L || comma(status = Com_IsValidDate(framePtr + 80L, 0L), (status & 1L) == 0L ? 1L : 0L) != 0L || comma(status = Com_IsValidDate(framePtr + 64L, 0L), (status & 1L) == 0L ? 1L : 0L) != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    isLessOrEqual = fLE(g_fin_pv, CC_0) != 0L ? 1L : 0L;
                                    if (isLessOrEqual != 0L) {
                                        step = 3;
                                        continue stepLoop;
                                    } else {
                                        fIntToCC(100L, framePtr + 48L);
                                        if ((keyCode & 0xffL) != 0L) {
                                            Com_StartFlashRunning();
                                        }
                                        status = BONDYtm(framePtr + 112L, framePtr + 96L, g_fin_pv, framePtr + 32L, g_fin_pmt, framePtr + 48L, 0L, intVal == 1L ? 1L : 0L, 0L, 0L);
                                        KeypadDriver_ClearKeyBuffer();
                                        if ((keyCode & 0xffL) != 0L) {
                                            Com_StopFlashRunning();
                                        }
                                        errCode = Com_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 32L);
                                        realVal1 = getCCReal(framePtr + 32L);
                                        if ((long) (int) errCode == 0L) {
                                            putI64(g_fin_i + 8L, getI64(framePtr + 40L));
                                            putI32(g_fin_i, getI32(framePtr + 32L));
                                            putI32(g_fin_i + 4L, getI32(framePtr + 36L));
                                            putCCReal(framePtr + 32L, realVal1);
                                            fIntToCC(2L, framePtr + 136L);
                                            fdiv(g_fin_pmt, framePtr + 136L, framePtr + 152L);
                                            fIntToCC(100L, framePtr + 136L);
                                            fradd(framePtr + 152L, framePtr + 136L, g_fin_fv, 0L);
                                            isLessOrEqual = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                            if (isLessOrEqual != 0L) {
                                                Stack_InsertToX(framePtr + 32L);
                                            } else {
                                                Stack_SetX(framePtr + 32L);
                                            }
                                            Com_SetResult(framePtr + 32L, keyCode & 0xffL);
                                            result = 99L;
                                            step = 1;
                                            continue stepLoop;
                                        } else {
                                            if ((long) (int) status == 24L) {
                                                step = 2;
                                                continue stepLoop;
                                            } else {
                                                if ((long) (int) status != 30L) {
                                                    step = 3;
                                                    continue stepLoop;
                                                } else {
                                                    putCCReal(framePtr + 32L, Stack_GetX());
                                                    Com_SetResult(framePtr + 32L, keyCode & 0xffL);
                                                    result = 99L;
                                                    step = 1;
                                                    continue stepLoop;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    case 1: {
                        putI32(g_com_keyinmode, 0L);
                        Com_SetXInsertMode(1L);
                        if (getI32(g_com_operationmode) == 0L) {
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        return result;
                    }
                    case 2: {
                        result = 8L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 3: {
                        result = 5L;
                        step = 1;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_CF0(long keyCode) {
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            status = Com_EndCalculateMode();
            if ((long) (int) status != 99L) {
                return status & 0xffffffffL;
            }
            putCCReal(framePtr + 24L, Stack_GetX());
            putU16(framePtr + 16L, 1L);
            SetCfloData(0L, framePtr + 16L);
            putI64(g_fin_n + 8L, 0L);
            putI64(g_fin_n, 1L);
            GetCfloData(0L, framePtr + 16L);
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putI32(g_com_keyinmode, 0L);
            Com_SetXInsertMode(1L);
            if (getI32(g_com_operationmode) == 0L) {
                putI64(g_basic_lastx + 16L, 1L);
            }
            return status & 0xffffffffL;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_CFj(long keyCode) {
        long flag1 = 0L;
        long isEqual = 0L;
        long tempVal = 0L;
        long flag2 = 0L;
        long freeCfloCount = 0L;
        long intVal = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        if (getI32(g_com_keyinmode) == 2L) {
                            isEqual = fLT(g_fin_n, CC_0) != 0L ? 1L : 0L;
                            if (isEqual != 0L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                freeCfloCount = GetUnusedCfloNum() & 0xffffffffL;
                                fIntToCC(freeCfloCount & 0xffL, framePtr + 16L);
                                isEqual = fLT(g_fin_n, framePtr + 16L) != 0L ? 1L : 0L;
                                if (isEqual == 0L || comma(isEqual = fiInteger(g_fin_n) != 0L ? 1L : 0L, isEqual == 0L ? 1L : 0L) != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    Com_EndEditMode_CareOP();
                                    isEqual = fEQ(g_fin_n, CC_0) != 0L ? 1L : 0L;
                                    if (isEqual != 0L) {
                                        tempVal = 0L;
                                    } else {
                                        intVal = (long) (int) fCCToInt(g_fin_n);
                                        tempVal = intVal & 0xffL;
                                    }
                                    GetCfloData(tempVal, framePtr + 16L);
                                    isEqual = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                    Com_NewValueGenerated(framePtr + 24L, isEqual);
                                    Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                                    fIntToCC(1L, framePtr + 40L);
                                    fradd(g_fin_n, framePtr + 40L, g_fin_n, 1L);
                                    BF123_CheckRealData_Ex(g_fin_n);
                                    step = 3;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            status = Com_EndCalculateMode();
                            if ((long) (int) status != 99L) {
                                return status;
                            }
                            fIntToCC(1L, framePtr + 16L);
                            fradd(g_fin_n, framePtr + 16L, g_fin_n, 0L);
                            BF123_CheckRealData_Ex(g_fin_n);
                            isEqual = fLT(g_fin_n, CC_0) != 0L ? 1L : 0L;
                            if (isEqual == 0L) {
                                freeCfloCount = GetUnusedCfloNum() & 0xffffffffL;
                                fIntToCC(freeCfloCount & 0xffL, framePtr + 16L);
                                isEqual = fLT(g_fin_n, framePtr + 16L) != 0L ? 1L : 0L;
                                if (isEqual != 0L && (long) (int) comma(isEqual = fiInteger(g_fin_n) != 0L ? 1L : 0L, isEqual) != 0L) {
                                    freeCfloCount = fCCToInt(g_fin_n) & 0xffffffffL;
                                    flag2 = freeCfloCount & 0xffL;
                                    if (getU8(g_fin_cflo_num) == 0L) {
                                        flag1 = 1L;
                                    } else {
                                        flag1 = flag2;
                                        if ((freeCfloCount & 0xffL) <= getU8(g_fin_cflo_num)) {
                                            flag1 = getU8(g_fin_cflo_num);
                                        }
                                    }
                                    putU8(g_fin_cflo_num, flag1);
                                    GetCfloData(flag2, framePtr + 16L);
                                    putCCReal(framePtr + 24L, Stack_GetX());
                                    Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                                    SetCfloData(flag2, framePtr + 16L);
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    step = 4;
                                    continue stepLoop;
                                }
                            } else {
                                step = 4;
                                continue stepLoop;
                            }
                        }
                    }
                    case 1: {
                        if (getI32(g_com_operationmode) == 0L) {
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        return status;
                    }
                    case 2: {
                        Com_EndEditMode_IgnoreOP();
                        putI32(g_com_keyinmode, 0L);
                        status = 6L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 3: {
                        putI32(g_com_keyinmode, 0L);
                        Com_SetXInsertMode(1L);
                        status = 99L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 4: {
                        fIntToCC(1L, framePtr + 16L);
                        fradd(g_fin_n, framePtr + 16L, g_fin_n, 1L);
                        BF123_CheckRealData_Ex(g_fin_n);
                        putI32(g_com_keyinmode, 0L);
                        status = 6L;
                        step = 1;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Fin_ComparionU8(long lhsByte, long rhsByte) {
        long isLess = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            fIntToCC(rhsByte & 0xffL, framePtr + 24L);
            isLess = fLT(lhsByte, framePtr + 24L) != 0L ? 1L : 0L;
            return isLess == 0L ? 1L : 0L;
        } finally {
            sp = savedSp;
        }
    }

    long Fin_DecreaseReal(long realPtr) {
        long errCode = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            fIntToCC(1L, framePtr + 24L);
            fradd(realPtr, framePtr + 24L, realPtr, 1L);
            errCode = BF123_CheckRealData_Ex(realPtr);
            return errCode;
        } finally {
            sp = savedSp;
        }
    }

    long Fin_IncreaseReal(long realPtr) {
        long errCode = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            fIntToCC(1L, framePtr + 24L);
            fradd(realPtr, framePtr + 24L, realPtr, 0L);
            errCode = BF123_CheckRealData_Ex(realPtr);
            return errCode;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_Nj(long keyCode) {
        long isEqual = 0L;
        long freeCfloCount = 0L;
        long intVal1 = 0L;
        long intVal2 = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        if (getI32(g_com_keyinmode) == 2L) {
                            isEqual = fLT(g_fin_n, CC_0) != 0L ? 1L : 0L;
                            if (isEqual != 0L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                freeCfloCount = GetUnusedCfloNum() & 0xffffffffL;
                                fIntToCC(freeCfloCount & 0xffL, framePtr + 16L);
                                isEqual = fLT(g_fin_n, framePtr + 16L) != 0L ? 1L : 0L;
                                if (isEqual == 0L || comma(isEqual = fiInteger(g_fin_n) != 0L ? 1L : 0L, isEqual == 0L ? 1L : 0L) != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    intVal1 = (long) (int) fCCToInt(g_fin_n);
                                    status = Com_EndCalculateMode();
                                    if ((long) (int) status != 99L) {
                                        return status;
                                    }
                                    GetCfloData(intVal1 & 0xffL, framePtr + 16L);
                                    fIntToCC(getU8(framePtr + 16L), framePtr + 40L);
                                    isEqual = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                    Com_NewValueGenerated(framePtr + 40L, isEqual);
                                    step = 4;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            freeCfloCount = GetUnusedCfloNum() & 0xffffffffL;
                            fIntToCC(freeCfloCount & 0xffL, framePtr + 16L);
                            isEqual = fLT(g_fin_n, framePtr + 16L) != 0L ? 1L : 0L;
                            if (isEqual != 0L && (long) (int) comma(isEqual = fiInteger(g_fin_n) != 0L ? 1L : 0L, isEqual) != 0L) {
                                status = Com_EndCalculateMode();
                                if ((long) (int) status != 99L) {
                                    return status;
                                }
                                putCCReal(framePtr + 40L, Stack_GetX());
                                isEqual = fLT(framePtr + 40L, CC_0) != 0L ? 1L : 0L;
                                if (isEqual == 0L) {
                                    fIntToCC(100L, framePtr + 16L);
                                    isEqual = fLT(framePtr + 40L, framePtr + 16L) != 0L ? 1L : 0L;
                                    if (isEqual != 0L && (long) (int) comma(isEqual = fiInteger(framePtr + 40L) != 0L ? 1L : 0L, isEqual) != 0L) {
                                        intVal1 = (long) (int) fCCToInt(framePtr + 40L);
                                        isEqual = fLT(CC_0, g_fin_n) != 0L ? 1L : 0L;
                                        if (isEqual != 0L || (long) (int) comma(isEqual = fEQ(g_fin_n, CC_0) != 0L ? 1L : 0L, isEqual) != 0L) {
                                            intVal2 = (long) (int) fCCToInt(g_fin_n);
                                            GetCfloData(intVal2 & 0xffL, framePtr + 16L);
                                            putU16(framePtr + 16L, intVal1 & 0xffL);
                                            SetCfloData(intVal2 & 0xffL, framePtr + 16L);
                                        }
                                        step = 4;
                                        continue stepLoop;
                                    } else {
                                        step = 3;
                                        continue stepLoop;
                                    }
                                } else {
                                    step = 3;
                                    continue stepLoop;
                                }
                            } else {
                                status = Com_EndCalculateMode();
                                if ((long) (int) status != 99L) {
                                    return status;
                                }
                                putI32(g_com_keyinmode, 0L);
                                status = 6L;
                                step = 1;
                                continue stepLoop;
                            }
                        }
                    }
                    case 1: {
                        if (getI32(g_com_operationmode) == 0L) {
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        return status;
                    }
                    case 2: {
                        Com_EndEditMode_IgnoreOP();
                        putI32(g_com_keyinmode, 0L);
                        status = 6L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 3: {
                        putI32(g_com_keyinmode, 0L);
                        status = 6L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 4: {
                        Com_SetResult(framePtr + 40L, keyCode & 0xffL);
                        putI32(g_com_keyinmode, 0L);
                        Com_SetXInsertMode(1L);
                        status = 99L;
                        step = 1;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_NPV(long keyCode) {
        long isLess = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long errCode = 0L;
        long tempVal = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        status2 = Com_EndCalculateMode();
                        result = status2 & 0xffffffffL;
                        if ((long) (int) status2 != 99L) {
                            return result;
                        }
                        isLess = fLT(g_fin_n, CC_0) != 0L ? 1L : 0L;
                        if (isLess != 0L || comma(isLess = fiInteger(g_fin_n) != 0L ? 1L : 0L, isLess == 0L ? 1L : 0L) != 0L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            status1 = GetUnusedCfloNum() & 0xffffffffL;
                            fIntToCC(status1 & 0xffL, framePtr + 24L);
                            isLess = fLT(g_fin_n, framePtr + 24L) != 0L ? 1L : 0L;
                            if (isLess == 0L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                status1 = fCCToInt(g_fin_n) & 0xffffffffL;
                                LockCfloData();
                                if ((keyCode & 0xffL) != 0L) {
                                    Com_StartFlashRunning();
                                }
                                status1 = IRRNPVNUSNFV(g_fin_cflodata, (long) (int) ((status1 & 0xffL) + 1L), FPTR_myTGetEntryIRR, g_fin_i, CC_1, framePtr + 24L, 0L, 0L) & 0xffffffffL;
                                UnLockCfloData();
                                errCode = Com_GetErrIgnoreOverflow(status1, framePtr + 24L);
                                if ((long) (int) errCode == 0L) {
                                    isLess = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                    if (isLess != 0L) {
                                        Stack_InsertToX(framePtr + 24L);
                                    } else {
                                        Stack_SetX(framePtr + 24L);
                                    }
                                    putI64(g_fin_npv + 8L, getI64(framePtr + 32L));
                                    putI32(g_fin_npv, getI32(framePtr + 24L));
                                    putI32(g_fin_npv + 4L, getI32(framePtr + 28L));
                                    putI64(g_fin_pv + 8L, getI64(framePtr + 32L));
                                    putI32(g_fin_pv, getI32(framePtr + 24L));
                                    putI32(g_fin_pv + 4L, getI32(framePtr + 28L));
                                    Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                                    result = 99L;
                                } else {
                                    tempVal = 5L;
                                    if (status1 != 25L) {
                                        tempVal = 6L;
                                    }
                                    result = tempVal;
                                }
                                step = 1;
                                continue stepLoop;
                            }
                        }
                    }
                    case 1: {
                        putI32(g_com_keyinmode, 0L);
                        Com_SetXInsertMode(1L);
                        if (getI32(g_com_operationmode) == 0L) {
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        return result;
                    }
                    case 2: {
                        result = 6L;
                        step = 1;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long myTGetEntryIRR(long cfloDataPtr, long entryIndex, long outEntryPtr) {
        long dataPtr = 0L;
        if (cfloDataPtr != 0L) {
            dataPtr = cfloDataPtr + (outEntryPtr & 0xffffffffL) * 24L;
            putI64(g_stat_xy_values + 16L, getI64(dataPtr + 16L));
            putI64(g_stat_xy_values + 8L, getI64(dataPtr + 8L));
            fIntToCC(getU16(dataPtr), g_stat_xy_values + 24L);
        }
        return g_stat_xy_values + 8L;
    }

    long Base_KeyDown_IRR(long keyCode) {
        long status1 = 0L;
        long status2 = 0L;
        status2 = Com_EndCalculateMode();
        if ((long) (int) status2 != 99L) {
            return status2;
        }
        status1 = Fin_IRR(0L, keyCode & 0xffL) & 0xffffffffL;
        status2 = status1;
        if (getI32(g_com_operationmode) == 0L) {
            putI64(g_basic_lastx + 16L, 1L);
        }
        return status2;
    }

    long Fin_IRR(long outIrrPtr, long hasGuess) {
        long isEqual = 0L;
        long freeCfloCount = 0L;
        long status = 0L;
        long errCode = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            isEqual = fLT(g_fin_n, CC_0) != 0L ? 1L : 0L;
            if (!(isEqual == 0L && (long) (int) comma(isEqual = fiInteger(g_fin_n) != 0L ? 1L : 0L, isEqual) != 0L)) {
                result = 6L;
                Com_SetXInsertMode(1L);
                putI32(g_com_keyinmode, 0L);
                return result;
            }
            freeCfloCount = GetUnusedCfloNum() & 0xffffffffL;
            fIntToCC(freeCfloCount & 0xffL, framePtr + 40L);
            isEqual = fLT(g_fin_n, framePtr + 40L) != 0L ? 1L : 0L;
            if (isEqual != 0L) {
                GetCfloData(0L, framePtr + 40L);
                isEqual = fEQ(g_fin_n, CC_0) != 0L ? 1L : 0L;
                if (isEqual != 0L && (long) (int) comma(isEqual = fEQ(framePtr + 48L, CC_0) != 0L ? 1L : 0L, isEqual) != 0L) {
                    freeCfloCount = 0L;
                    if (outIrrPtr == 0L) {
                        putI64(framePtr + 72L, 0L);
                        putI32(framePtr + 64L, 1L);
                        putI32(framePtr + 68L, 0L);
                    } else {
                        putI64(framePtr + 72L, getI64(outIrrPtr + 8L));
                        putI32(framePtr + 64L, getI32(outIrrPtr));
                        putI32(framePtr + 68L, getI32(outIrrPtr + 4L));
                    }
                } else {
                    freeCfloCount = fCCToInt(g_fin_n) & 0xffffffffL;
                    LockCfloData();
                    if ((hasGuess & 0xffL) != 0L) {
                        Com_StartFlashRunning();
                    }
                    freeCfloCount = ((freeCfloCount & 0xffL) + 1L) & 0xffffffffL;
                    fIntToCC((long) (int) freeCfloCount, framePtr + 24L);
                    if (outIrrPtr == 0L) {
                        putI64(framePtr + 72L, 0L);
                        putI32(framePtr + 64L, 0L);
                        putI32(framePtr + 68L, 0L);
                    } else {
                        putI64(framePtr + 72L, getI64(outIrrPtr + 8L));
                        putI32(framePtr + 64L, getI32(outIrrPtr));
                        putI32(framePtr + 68L, getI32(outIrrPtr + 4L));
                    }
                    status = IRRIRR(g_fin_cflodata, freeCfloCount, FPTR_myTGetEntryIRR, CC_1, framePtr + 64L, FPTR_UserStop);
                    freeCfloCount = status & 0xffffffffL;
                    KeypadDriver_ClearCallback();
                    UnLockCfloData();
                    if ((hasGuess & 0xffL) != 0L) {
                        Com_StopFlashRunning();
                    }
                }
                errCode = Com_GetErrIgnoreOverflow(freeCfloCount, framePtr + 64L);
                if (freeCfloCount == 35L || (long) (int) errCode == 0L) {
                    isEqual = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    if (isEqual != 0L) {
                        Stack_InsertToX(framePtr + 64L);
                    } else {
                        Stack_SetX(framePtr + 64L);
                    }
                    putI64(g_fin_i + 8L, getI64(framePtr + 72L));
                    putI32(g_fin_i, getI32(framePtr + 64L));
                    putI32(g_fin_i + 4L, getI32(framePtr + 68L));
                    Com_SetResult(framePtr + 64L, hasGuess & 0xffL);
                    result = 99L;
                } else {
                    if (((freeCfloCount - 25L) & 0xffffffffL) < 6L) {
                        result = getU32(ADDR_RODATA + 7680L + (long) (int) (freeCfloCount - 25L) * 4L);
                    } else {
                        result = 7L;
                    }
                }
                Com_SetXInsertMode(1L);
                putI32(g_com_keyinmode, 0L);
                return result;
            } else {
                result = 6L;
                Com_SetXInsertMode(1L);
                putI32(g_com_keyinmode, 0L);
                return result;
            }
        } finally {
            sp = savedSp;
        }
    }

    long UserStop(long iterCount, long currentGuessPtr) {
        return (long) (int) iterCount == 1000L ? 1L : 0L;
    }

    long Base_KeyDown_SL(long keyCode) {
        long dataPtr = 0L;
        long isLess = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long errCode = 0L;
        long realPtr = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        status2 = Com_EndCalculateMode();
                        result = status2 & 0xffffffffL;
                        if ((long) (int) status2 != 99L) {
                            return result;
                        }
                        putCCReal(framePtr + 40L, Stack_GetX_12digits());
                        isLess = fLT(CC_0, framePtr + 40L) != 0L ? 1L : 0L;
                        if (isLess != 0L && (long) (int) comma(isLess = fiInteger(framePtr + 40L) != 0L ? 1L : 0L, isLess) != 0L) {
                            isLess = fLT(CC_0, getI64(g_fin_tvm_pinput)) != 0L ? 1L : 0L;
                            dataPtr = getI64(g_fin_tvm_pinput);
                            if (isLess == 0L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                fStringToCC_er(0x160013L, framePtr + 56L, 0L, 1L, 46L, 44L);
                                isLess = fLT(framePtr + 56L, dataPtr) != 0L ? 1L : 0L;
                                if (isLess != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    if ((keyCode & 0xffL) != 0L) {
                                        Com_StartFlashRunning();
                                    }
                                    status1 = DEPRSL_nf(getI64(g_fin_tvm_pinput + 16L), getI64(g_fin_tvm_pinput + 32L), getI64(g_fin_tvm_pinput), framePtr + 40L, framePtr + 56L, framePtr + 24L, 0L) & 0xffffffffL;
                                    KeypadDriver_ClearCallback();
                                    if ((keyCode & 0xffL) != 0L) {
                                        Com_StopFlashRunning();
                                    }
                                    errCode = Com_GetErrIgnoreOverflow(status1, framePtr + 56L);
                                    if ((long) (int) errCode == 0L && comma(errCode = Com_GetErrIgnoreOverflow(status1, framePtr + 24L), (long) (int) errCode == 0L ? 1L : 0L) != 0L) {
                                        isLess = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                        if (isLess != 0L) {
                                            Stack_InsertToX(framePtr + 24L);
                                        } else {
                                            Stack_SetX(framePtr + 24L);
                                        }
                                        Stack_InsertToX(framePtr + 56L);
                                        realPtr = framePtr + 56L;
                                        Com_SetResult(realPtr, keyCode & 0xffL);
                                        result = 99L;
                                        step = 1;
                                        continue stepLoop;
                                    } else {
                                        if (status1 != 30L) {
                                            step = 2;
                                            continue stepLoop;
                                        } else {
                                            putCCReal(framePtr + 40L, Stack_GetX());
                                            realPtr = framePtr + 40L;
                                            Com_SetResult(realPtr, keyCode & 0xffL);
                                            result = 99L;
                                            step = 1;
                                            continue stepLoop;
                                        }
                                    }
                                }
                            }
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        putI32(g_com_keyinmode, 0L);
                        Com_SetXInsertMode(1L);
                        if (getI32(g_com_operationmode) == 0L) {
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        return result;
                    }
                    case 2: {
                        result = 5L;
                        step = 1;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Fin_CompToMaxN(long nRealPtr) {
        long isLess = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            fStringToCC_er(0x160013L, framePtr + 24L, 0L, 1L, 46L, 44L);
            isLess = fLT(framePtr + 24L, nRealPtr) != 0L ? 1L : 0L;
            return isLess;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_SOYD(long keyCode) {
        long dataPtr = 0L;
        long isLess = 0L;
        long status = 0L;
        long errCode = 0L;
        long realPtr = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        status = Com_EndCalculateMode();
                        result = status & 0xffffffffL;
                        if ((long) (int) status != 99L) {
                            return result;
                        }
                        putCCReal(framePtr + 40L, Stack_GetX_12digits());
                        isLess = fLT(CC_0, framePtr + 40L) != 0L ? 1L : 0L;
                        if (isLess != 0L && (long) (int) comma(isLess = fiInteger(framePtr + 40L) != 0L ? 1L : 0L, isLess) != 0L) {
                            isLess = fLT(CC_0, getI64(g_fin_tvm_pinput)) != 0L ? 1L : 0L;
                            dataPtr = getI64(g_fin_tvm_pinput);
                            if (isLess == 0L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                fStringToCC_er(0x160013L, framePtr + 56L, 0L, 1L, 46L, 44L);
                                isLess = fLT(framePtr + 56L, dataPtr) != 0L ? 1L : 0L;
                                if (isLess != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    if ((keyCode & 0xffL) != 0L) {
                                        Com_StartFlashRunning();
                                    }
                                    status = DEPRSOYD_nf(getI64(g_fin_tvm_pinput + 16L), getI64(g_fin_tvm_pinput + 32L), getI64(g_fin_tvm_pinput), framePtr + 40L, framePtr + 56L, framePtr + 24L, 0L);
                                    KeypadDriver_ClearCallback();
                                    if ((keyCode & 0xffL) != 0L) {
                                        Com_StopFlashRunning();
                                    }
                                    errCode = Com_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 56L);
                                    if ((long) (int) errCode == 0L && comma(errCode = Com_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 24L), (long) (int) errCode == 0L ? 1L : 0L) != 0L) {
                                        isLess = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                        if (isLess != 0L) {
                                            Stack_InsertToX(framePtr + 24L);
                                        } else {
                                            Stack_SetX(framePtr + 24L);
                                        }
                                        Stack_InsertToX(framePtr + 56L);
                                        realPtr = framePtr + 56L;
                                        Com_SetResult(realPtr, keyCode & 0xffL);
                                        result = 99L;
                                        step = 1;
                                        continue stepLoop;
                                    } else {
                                        if ((long) (int) status != 30L) {
                                            step = 2;
                                            continue stepLoop;
                                        } else {
                                            putCCReal(framePtr + 40L, Stack_GetX());
                                            realPtr = framePtr + 40L;
                                            Com_SetResult(realPtr, keyCode & 0xffL);
                                            result = 99L;
                                            step = 1;
                                            continue stepLoop;
                                        }
                                    }
                                }
                            }
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        putI32(g_com_keyinmode, 0L);
                        Com_SetXInsertMode(1L);
                        if (getI32(g_com_operationmode) == 0L) {
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        return result;
                    }
                    case 2: {
                        result = 5L;
                        step = 1;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_DB(long keyCode) {
        long isLess = 0L;
        long status = 0L;
        long errCode = 0L;
        long realPtr = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        status = Com_EndCalculateMode();
                        result = status & 0xffffffffL;
                        if ((long) (int) status != 99L) {
                            return result;
                        }
                        putCCReal(framePtr + 40L, Stack_GetX_12digits());
                        isLess = fLT(CC_0, framePtr + 40L) != 0L ? 1L : 0L;
                        if (isLess != 0L && (long) (int) comma(isLess = fiInteger(framePtr + 40L) != 0L ? 1L : 0L, isLess) != 0L) {
                            isLess = fLT(CC_0, getI64(g_fin_tvm_pinput)) != 0L ? 1L : 0L;
                            realPtr = getI64(g_fin_tvm_pinput);
                            if (isLess == 0L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                fStringToCC_er(0x160013L, framePtr + 56L, 0L, 1L, 46L, 44L);
                                isLess = fLT(framePtr + 56L, realPtr) != 0L ? 1L : 0L;
                                if (isLess != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    if ((keyCode & 0xffL) != 0L) {
                                        Com_StartFlashRunning();
                                    }
                                    status = DEPRDB_nf(getI64(g_fin_tvm_pinput + 16L), getI64(g_fin_tvm_pinput + 32L), getI64(g_fin_tvm_pinput), framePtr + 40L, getI64(g_fin_tvm_pinput + 8L), framePtr + 56L, framePtr + 24L, 0L);
                                    KeypadDriver_ClearCallback();
                                    if ((keyCode & 0xffL) != 0L) {
                                        Com_StopFlashRunning();
                                    }
                                    errCode = Com_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 56L);
                                    if ((long) (int) errCode == 0L && comma(errCode = Com_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 24L), (long) (int) errCode == 0L ? 1L : 0L) != 0L) {
                                        isLess = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                        if (isLess != 0L) {
                                            Stack_InsertToX(framePtr + 24L);
                                        } else {
                                            Stack_SetX(framePtr + 24L);
                                        }
                                        Stack_InsertToX(framePtr + 56L);
                                        realPtr = framePtr + 56L;
                                        Com_SetResult(realPtr, keyCode & 0xffL);
                                        result = 99L;
                                        step = 1;
                                        continue stepLoop;
                                    } else {
                                        if ((long) (int) status != 30L) {
                                            step = 2;
                                            continue stepLoop;
                                        } else {
                                            putCCReal(framePtr + 40L, Stack_GetX());
                                            realPtr = framePtr + 40L;
                                            Com_SetResult(realPtr, keyCode & 0xffL);
                                            result = 99L;
                                            step = 1;
                                            continue stepLoop;
                                        }
                                    }
                                }
                            }
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        putI32(g_com_keyinmode, 0L);
                        Com_SetXInsertMode(1L);
                        if (getI32(g_com_operationmode) == 0L) {
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        return result;
                    }
                    case 2: {
                        result = 5L;
                        step = 1;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_PMT(long keyCode) {
        long isAlgPending = 0L;
        long errCode = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            fIntToCC(1L, g_fin_p_yr);
            if (getI32(g_com_keyinmode) == 5L) {
                if ((keyCode & 0xffL) != 0L) {
                    Com_StartFlashRunning();
                }
                status = TVMPMT_12C(getI64(g_fin_tvm_pinput), getI64(g_fin_tvm_pinput + 8L), getI64(g_fin_tvm_pinput + 16L), framePtr + 24L, getI64(g_fin_tvm_pinput + 32L), getI64(g_fin_tvm_pinput + 40L), CC_1, getU8(g_fin_begin_of_period), getI8(g_fin_compoundinterest) & 0xffL);
                if ((keyCode & 0xffL) != 0L) {
                    Com_StopFlashRunning();
                }
                errCode = Com_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 24L);
                if ((long) (int) errCode == 0L) {
                    putI64(g_fin_pmt + 8L, getI64(framePtr + 32L));
                    putI32(g_fin_pmt, getI32(framePtr + 24L));
                    putI32(g_fin_pmt + 4L, getI32(framePtr + 28L));
                    Com_SetResult(g_fin_pmt, keyCode & 0xffL);
                    isAlgPending = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    if (isAlgPending != 0L) {
                        Stack_InsertToX(g_fin_pmt);
                    } else {
                        Stack_SetX(g_fin_pmt);
                    }
                    Com_SetXInsertMode(1L);
                    status = 99L;
                } else {
                    status = 5L;
                }
                if (getI32(g_com_operationmode) == 0L) {
                    putI64(g_basic_lastx + 16L, 1L);
                }
                return status;
            }
            if (getI32(g_com_keyinmode) == 3L) {
                Com_EndEditMode_IgnoreOP();
            } else {
                if (getI32(g_com_keyinmode) == 2L) {
                    Com_EndEditMode_CareOP();
                    Com_SetResult(g_fin_pmt, keyCode & 0xffL);
                    isAlgPending = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    Com_NewValueGenerated(g_fin_pmt, isAlgPending);
                    if (getI32(g_com_operationmode) == 0L && (long) (int) comma(isAlgPending = Com_IsInALGPendingMode() != 0L ? 1L : 0L, isAlgPending) != 0L) {
                        isAlgPending = 0L;
                    } else {
                        isAlgPending = 1L;
                    }
                    Com_SetXInsertMode(isAlgPending);
                    putI32(g_com_keyinmode, 0L);
                    Com_SetToNumberState(1L);
                    status = 99L;
                    return status;
                }
                status = Com_EndCalculateMode();
                if ((long) (int) status != 99L) {
                    return status;
                }
            }
            putCCReal(g_fin_pmt, Stack_GetX_12digits());
            Com_SetResult(g_fin_pmt, keyCode & 0xffL);
            Com_SetXInsertMode(getU8(g_com_insert_for_finreg));
            putI32(g_com_keyinmode, 5L);
            status = 99L;
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_FV(long keyCode) {
        long isAlgPending = 0L;
        long errCode = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            fIntToCC(1L, g_fin_p_yr);
            if (getI32(g_com_keyinmode) == 5L) {
                if ((keyCode & 0xffL) != 0L) {
                    Com_StartFlashRunning();
                }
                status = TVMFV_12C(getI64(g_fin_tvm_pinput), getI64(g_fin_tvm_pinput + 8L), getI64(g_fin_tvm_pinput + 16L), getI64(g_fin_tvm_pinput + 24L), framePtr + 24L, getI64(g_fin_tvm_pinput + 40L), CC_1, getU8(g_fin_begin_of_period), getI8(g_fin_compoundinterest) & 0xffL);
                if ((keyCode & 0xffL) != 0L) {
                    Com_StopFlashRunning();
                }
                errCode = Com_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 24L);
                if ((long) (int) errCode == 0L) {
                    putI64(g_fin_fv + 8L, getI64(framePtr + 32L));
                    putI32(g_fin_fv, getI32(framePtr + 24L));
                    putI32(g_fin_fv + 4L, getI32(framePtr + 28L));
                    Com_SetResult(g_fin_fv, keyCode & 0xffL);
                    isAlgPending = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    if (isAlgPending != 0L) {
                        Stack_InsertToX(g_fin_fv);
                    } else {
                        Stack_SetX(g_fin_fv);
                    }
                    Com_SetXInsertMode(1L);
                    status = 99L;
                } else {
                    status = 5L;
                }
                if (getI32(g_com_operationmode) == 0L) {
                    putI64(g_basic_lastx + 16L, 1L);
                }
                return status;
            }
            if (getI32(g_com_keyinmode) == 3L) {
                Com_EndEditMode_IgnoreOP();
            } else {
                if (getI32(g_com_keyinmode) == 2L) {
                    Com_EndEditMode_CareOP();
                    Com_SetResult(g_fin_fv, keyCode & 0xffL);
                    isAlgPending = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    Com_NewValueGenerated(g_fin_fv, isAlgPending);
                    if (getI32(g_com_operationmode) == 0L && (long) (int) comma(isAlgPending = Com_IsInALGPendingMode() != 0L ? 1L : 0L, isAlgPending) != 0L) {
                        isAlgPending = 0L;
                    } else {
                        isAlgPending = 1L;
                    }
                    Com_SetXInsertMode(isAlgPending);
                    putI32(g_com_keyinmode, 0L);
                    Com_SetToNumberState(1L);
                    status = 99L;
                    return status;
                }
                status = Com_EndCalculateMode();
                if ((long) (int) status != 99L) {
                    return status;
                }
            }
            putCCReal(g_fin_fv, Stack_GetX_12digits());
            Com_SetResult(g_fin_fv, keyCode & 0xffL);
            Com_SetXInsertMode(getU8(g_com_insert_for_finreg));
            putI32(g_com_keyinmode, 5L);
            status = 99L;
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_AMORT(long keyCode) {
        long bytePtr = 0L;
        long flag = 0L;
        CC_Real realVal = new CC_Real();
        long isZero = 0L;
        long errCode = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(256);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        putI64(framePtr + 24L, 0L);
                        putI32(framePtr + 16L, 0L);
                        putI32(framePtr + 20L, 0L);
                        putI64(framePtr + 32L, getI64(framePtr + 16L));
                        putI64(framePtr + 40L, getI64(framePtr + 24L));
                        putI64(framePtr + 48L, getI64(framePtr + 16L));
                        putI64(framePtr + 56L, getI64(framePtr + 24L));
                        status = Com_EndCalculateMode();
                        if ((long) (int) status != 99L) {
                            return status;
                        }
                        putCCReal(framePtr + 104L, Stack_GetX_12digits());
                        if ((keyCode & 0xffL) != 0L) {
                            Com_StartFlashRunning();
                        }
                        realVal = getCCReal(framePtr + 104L);
                        putI64(framePtr + 88L, 0x100000000000000L);
                        putI64(framePtr + 80L, 1L);
                        bytePtr = g_com_resultstate;
                        if (getI8(g_com_resultstate + 1L) != 0L) {
                            bytePtr = g_com_rounddigit;
                        }
                        flag = getU8(bytePtr);
                        putI64(g_fin_p_yr + 8L, 0x100000000000000L);
                        putI64(g_fin_p_yr, 1L);
                        putI64(framePtr + 72L, getI64(framePtr + 112L));
                        putI32(framePtr + 64L, getI32(framePtr + 104L));
                        putI32(framePtr + 68L, getI32(framePtr + 108L));
                        putI64(framePtr + 24L, getI32(getI64(g_fin_tvm_pinput + 16L) + 4L));
                        putI64(framePtr + 16L, getI32(getI64(g_fin_tvm_pinput + 16L)));
                        putCCReal(framePtr + 104L, realVal);
                        isZero = fLT(CC_0, g_fin_n) != 0L ? 1L : 0L;
                        status = TVMAMORT(framePtr + 80L, framePtr + 64L, framePtr + 64L, getI64(g_fin_tvm_pinput + 8L), framePtr + 16L, getI64(g_fin_tvm_pinput + 24L), getI64(g_fin_tvm_pinput + 40L), CC_1, getU8(g_fin_begin_of_period) != 0L && isZero == 0L ? 1L : 0L, framePtr + 32L, framePtr + 48L, flag);
                        errCode = Fin_TVM_Amort_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 48L, framePtr + 32L, framePtr + 16L) & 0xffffffffL;
                        KeypadDriver_ClearCallback();
                        if ((keyCode & 0xffL) != 0L) {
                            Com_StopFlashRunning();
                        }
                        if (errCode >= 2L) {
                            if (errCode != 30L) {
                                status = 5L;
                                return status;
                            }
                            putCCReal(framePtr + 104L, Stack_GetX());
                            Com_SetResult(framePtr + 104L, keyCode & 0xffL);
                            status = 99L;
                            return status;
                        }
                        fradd(g_fin_n, framePtr + 104L, g_fin_n, 0L);
                        putI64(g_fin_pv + 8L, getI64(framePtr + 24L));
                        putI32(g_fin_pv, getI32(framePtr + 16L));
                        putI32(g_fin_pv + 4L, getI32(framePtr + 20L));
                        if (getI32(getI64(g_fin_tvm_pinput + 24L)) < 0L) {
                            if (0L < getI32(framePtr + 48L)) {
                                if (getI32(framePtr + 48L) == 3L) {
                                    putI32(framePtr + 48L, 1L);
                                }
                                step = 2;
                                continue stepLoop;
                            } else {
                                step = 1;
                                continue stepLoop;
                            }
                        } else {
                            if (getI32(getI64(g_fin_tvm_pinput + 24L)) != 0L && getI32(framePtr + 48L) < 0L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                step = 1;
                                continue stepLoop;
                            }
                        }
                    }
                    case 1: {
                        isZero = Com_GetXInsertMode() != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            Stack_InsertToX(framePtr + 32L);
                        } else {
                            Stack_SetX(framePtr + 32L);
                        }
                        Stack_InsertToX(framePtr + 48L);
                        Com_SetXInsertMode(1L);
                        Com_SetResult(framePtr + 48L, keyCode & 0xffL);
                        if (getI32(g_com_operationmode) == 0L) {
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        status = 99L;
                        return status;
                    }
                    case 2: {
                        isZero = fZero(framePtr + 48L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            putI32(framePtr + 48L, -getI32(framePtr + 48L));
                        }
                        step = 1;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Fin_TVM_Amort_GetErrIgnoreOverflow(long errCode, long real1Ptr, long real2Ptr, long real3Ptr) {
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            if ((errCode & 0xffffffffL) < 2L) {
                BF123_CheckRealData_Ex(real1Ptr);
                BF123_CheckRealData_Ex(real2Ptr);
                BF123_CheckUnderFlow(real3Ptr);
            } else {
                if ((errCode & 0xffffffffL) == 2L) {
                    errCode = 2L;
                    putI64(real1Ptr + 8L, 0L);
                    putI32(real1Ptr, 1L);
                    putI32(real1Ptr + 4L, 0L);
                    putI64(real2Ptr + 8L, 0L);
                    putI32(real2Ptr, 1L);
                    putI32(real2Ptr + 4L, 0L);
                    putI64(real3Ptr + 8L, 0L);
                    putI32(real3Ptr, 1L);
                    putI32(real3Ptr + 4L, 0L);
                } else {
                    if ((errCode & 0xffffffffL) != 3L) {
                        return errCode & 0xffffffffL;
                    }
                    if (getI32(real1Ptr) == 1L) {
                        putU8(framePtr + 16L, 57L);
                        putU8(framePtr + 17L, 46L);
                        putU8(framePtr + 18L, 57L);
                        putU8(framePtr + 19L, 57L);
                        putU8(framePtr + 20L, 57L);
                        putU8(framePtr + 21L, 57L);
                        putU8(framePtr + 22L, 57L);
                        putU8(framePtr + 24L, 57L);
                        putU8(framePtr + 25L, 57L);
                        putU8(framePtr + 26L, 57L);
                        putU8(framePtr + 27L, 69L);
                        putU8(framePtr + 28L, 57L);
                        putU8(framePtr + 29L, 57L);
                        putU8(framePtr + 30L, 0L);
                    } else {
                        putU8(framePtr + 24L, 57L);
                        putU8(framePtr + 25L, 57L);
                        putU8(framePtr + 26L, 57L);
                        putU8(framePtr + 27L, 57L);
                        putU8(framePtr + 28L, 69L);
                        putU8(framePtr + 29L, 57L);
                        putU8(framePtr + 30L, 57L);
                        putU8(framePtr + 31L, 0L);
                        putU8(framePtr + 16L, 45L);
                        putU8(framePtr + 17L, 57L);
                        putU8(framePtr + 18L, 46L);
                        putU8(framePtr + 19L, 57L);
                        putU8(framePtr + 20L, 57L);
                        putU8(framePtr + 21L, 57L);
                        putU8(framePtr + 22L, 57L);
                    }
                    putU8(framePtr + 23L, 57L);
                    fStringToCC_er(framePtr + 16L, real1Ptr, 0L, 1L, 46L, 44L);
                    fStringToCC_er(framePtr + 16L, real2Ptr, 0L, 1L, 46L, 44L);
                    fStringToCC_er(framePtr + 16L, real3Ptr, 0L, 1L, 46L, 44L);
                    errCode = 0L;
                }
            }
            return errCode & 0xffffffffL;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_12_MUL(long keyCode) {
        long isValidReal = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long status3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            fIntToCC(12L, framePtr + 24L);
            status2 = getI32(g_com_keyinmode);
            status3 = Com_EndCalculateMode();
            if (status2 == 2L) {
                if ((long) (int) status3 != 99L) {
                    return status3;
                }
                isValidReal = Com_GetXInsertMode() != 0L ? 1L : 0L;
                status1 = (long) (byte) fdiv(g_fin_n, framePtr + 24L, framePtr + 40L);
                if ((byte) status1 == 0L) {
                    if (isValidReal != 0L) {
                        Stack_InsertToX(framePtr + 40L);
                    } else {
                        Stack_SetX(framePtr + 40L);
                    }
                    Com_SetResult(framePtr + 40L, keyCode & 0xffL);
                    isValidReal = 1L;
                    putI32(g_com_keyinmode, 0L);
                    if (getI32(g_com_operationmode) == 0L) {
                        putI64(g_basic_lastx + 16L, 1L);
                    }
                    Com_SetXInsertMode(isValidReal);
                    Com_SetToNumberState(1L);
                    status3 = 99L;
                    return status3;
                }
                status3 = 5L;
            } else {
                if ((long) (int) status3 != 99L) {
                    return status3;
                }
                putCCReal(framePtr + 40L, Stack_GetX_12digits());
                status2 = (long) (int) fmul(framePtr + 40L, framePtr + 24L, g_fin_n);
                if (status2 != 0L) {
                    status3 = 5L;
                } else {
                    isValidReal = BF123_IsValidRealData(g_fin_n) != 0L ? 1L : 0L;
                    if (isValidReal != 0L) {
                        Stack_SetX(g_fin_n);
                        Com_SetResult(g_fin_n, keyCode & 0xffL);
                        putI32(g_com_keyinmode, 5L);
                        if (getI32(g_com_operationmode) == 0L) {
                            putI64(g_basic_lastx + 16L, 1L);
                        }
                        isValidReal = getU8(g_com_insert_for_finreg) != 0L ? 1L : 0L;
                        Com_SetXInsertMode(isValidReal);
                        Com_SetToNumberState(1L);
                        status3 = 99L;
                        return status3;
                    }
                    status3 = 1L;
                }
            }
            return status3;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_12_DIV(long keyCode) {
        long isInsertMode = 0L;
        long flag = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            fIntToCC(12L, framePtr + 24L);
            status1 = getI32(g_com_keyinmode);
            status2 = Com_EndCalculateMode();
            result = status2 & 0xffffffffL;
            if (status1 == 2L) {
                if ((long) (int) status2 != 99L) {
                    return result;
                }
                isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                status1 = (long) (int) fmul(g_fin_i, framePtr + 24L, framePtr + 40L);
                if (1L < status1) {
                    result = 5L;
                    return result;
                }
                BF123_CheckRealData_Ex(framePtr + 40L);
                if (isInsertMode != 0L) {
                    Stack_InsertToX(framePtr + 40L);
                } else {
                    Stack_SetX(framePtr + 40L);
                }
                Com_SetResult(framePtr + 40L, keyCode & 0xffL);
                isInsertMode = 1L;
                putI32(g_com_keyinmode, 0L);
                if (getI32(g_com_operationmode) == 0L) {
                    putI64(g_basic_lastx + 16L, 1L);
                }
            } else {
                if ((long) (int) status2 != 99L) {
                    return result;
                }
                putCCReal(framePtr + 40L, Stack_GetX_12digits());
                flag = fdiv(framePtr + 40L, framePtr + 24L, g_fin_i) & 0xffL;
                result = 5L;
                if (1L < flag) {
                    return result;
                }
                BF123_CheckRealData_Ex(g_fin_i);
                Com_SetResult(g_fin_i, keyCode & 0xffL);
                Stack_SetX(g_fin_i);
                putI32(g_com_keyinmode, 5L);
                if (getI32(g_com_operationmode) == 0L) {
                    putI64(g_basic_lastx + 16L, 1L);
                }
                isInsertMode = getU8(g_com_insert_for_finreg) != 0L ? 1L : 0L;
            }
            Com_SetXInsertMode(isInsertMode);
            Com_SetToNumberState(1L);
            result = 99L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_BEG(long keyCode) {
        long isInsertMode = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            Com_EndEditMode_CareOP();
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
            Com_SetXInsertMode(isInsertMode);
            putU8(g_fin_begin_of_period, 1L);
            if ((keyCode & 0xffL) != 0L) {
                LcdDriver_SetAnnunciator(7L);
            }
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_END(long keyCode) {
        long isInsertMode = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            Com_EndEditMode_CareOP();
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
            Com_SetXInsertMode(isInsertMode);
            putU8(g_fin_begin_of_period, 0L);
            if ((keyCode & 0xffL) != 0L) {
                LcdDriver_ClearAnnunciator(7L);
            }
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_N(long keyCode) {
        long isLess = 0L;
        long errCode = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            putU8(framePtr + 84L, 53L);
            putU8(framePtr + 85L, 0L);
            putU8(framePtr + 80L, 48L);
            putU8(framePtr + 81L, 46L);
            putU8(framePtr + 82L, 48L);
            putU8(framePtr + 83L, 48L);
            fIntToCC(1L, framePtr + 48L);
            fIntToCC(1L, g_fin_p_yr);
            if (getI32(g_com_keyinmode) == 5L) {
                putU32(framePtr + 92L, getU32(framePtr + 92L) & 0xffffff00L);
                putU32(framePtr + 88L, 0x3030312dL);
                errCode = fStringToCC_er(framePtr + 88L, framePtr + 64L, 0L, 1L, 46L, 44L);
                if (!((long) (int) errCode == 0L && comma(isLess = fLE(g_fin_i, framePtr + 64L) != 0L ? 1L : 0L, isLess == 0L ? 1L : 0L) != 0L)) {
                    status = 5L;
                    return status;
                }
                if ((keyCode & 0xffL) != 0L) {
                    Com_StartFlashRunning();
                }
                status = TVMN_12C(framePtr + 64L, getI64(g_fin_tvm_pinput + 8L), getI64(g_fin_tvm_pinput + 16L), getI64(g_fin_tvm_pinput + 24L), getI64(g_fin_tvm_pinput + 32L), getI64(g_fin_tvm_pinput + 40L), CC_1, getU8(g_fin_begin_of_period));
                if ((keyCode & 0xffL) != 0L) {
                    Com_StopFlashRunning();
                }
                errCode = Com_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 64L);
                if ((long) (int) errCode != 0L) {
                    status = 5L;
                    return status;
                }
                isLess = fLT(CC_0, framePtr + 64L) != 0L ? 1L : 0L;
                if (isLess != 0L) {
                    fitrunc(framePtr + 64L, 0L, framePtr + 88L, 0L);
                    errCode = fStringToCC_er(framePtr + 80L, framePtr + 16L, 0L, 1L, 46L, 44L);
                    if (!((long) (int) errCode == 0L && comma(isLess = fradd(framePtr + 64L, framePtr + 88L, framePtr + 32L, 1L) != 0L ? 1L : 0L, isLess == 0L ? 1L : 0L) != 0L)) {
                        status = 5L;
                        return status;
                    }
                    isLess = fLE(framePtr + 16L, framePtr + 32L) != 0L ? 1L : 0L;
                    if (isLess != 0L) {
                        isLess = fradd(framePtr + 88L, framePtr + 48L, framePtr + 64L, 0L) != 0L ? 1L : 0L;
                        if (isLess != 0L) {
                            status = 5L;
                            return status;
                        }
                    } else {
                        putI64(framePtr + 72L, getI64(framePtr + 96L));
                        putI32(framePtr + 68L, getU32(framePtr + 92L));
                        putI32(framePtr + 64L, getU32(framePtr + 88L));
                    }
                } else {
                    isLess = fLT(framePtr + 64L, CC_0) != 0L ? 1L : 0L;
                    if (isLess != 0L) {
                        status = 5L;
                        return status;
                    }
                }
                putI64(g_fin_n + 8L, getI64(framePtr + 72L));
                putI32(g_fin_n, getI32(framePtr + 64L));
                putI32(g_fin_n + 4L, getI32(framePtr + 68L));
                Com_SetResult(g_fin_n, keyCode & 0xffL);
                isLess = Com_GetXInsertMode() != 0L ? 1L : 0L;
                if (isLess != 0L) {
                    Stack_InsertToX(g_fin_n);
                } else {
                    Stack_SetX(g_fin_n);
                }
                Com_SetXInsertMode(1L);
                putI32(g_com_keyinmode, 5L);
                if (getI32(g_com_operationmode) == 0L) {
                    putI64(g_basic_lastx + 16L, 1L);
                }
                status = 99L;
                return status;
            }
            if (getI32(g_com_keyinmode) == 3L) {
                Com_EndEditMode_IgnoreOP();
            } else {
                if (getI32(g_com_keyinmode) == 2L) {
                    Com_EndEditMode_CareOP();
                    Com_SetResult(g_fin_n, keyCode & 0xffL);
                    isLess = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    Com_NewValueGenerated(g_fin_n, isLess);
                    if (getI32(g_com_operationmode) == 0L && (long) (int) comma(isLess = Com_IsInALGPendingMode() != 0L ? 1L : 0L, isLess) != 0L) {
                        isLess = 0L;
                    } else {
                        isLess = 1L;
                    }
                    Com_SetXInsertMode(isLess);
                    Com_SetToNumberState(1L);
                    putI32(g_com_keyinmode, 0L);
                    status = 99L;
                    return status;
                }
                status = Com_EndCalculateMode();
                if ((long) (int) status != 99L) {
                    return status;
                }
            }
            putCCReal(g_fin_n, Stack_GetX_12digits());
            Com_SetResult(g_fin_n, keyCode & 0xffL);
            Com_SetXInsertMode(getU8(g_com_insert_for_finreg));
            putI32(g_com_keyinmode, 5L);
            status = 99L;
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Chk_Par_Cal_N() {
        long isLessOrEqual = 0L;
        long tempVal = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            putU8(framePtr + 36L, 0L);
            putU8(framePtr + 32L, 45L);
            putU8(framePtr + 33L, 49L);
            putU8(framePtr + 34L, 48L);
            putU8(framePtr + 35L, 48L);
            tempVal = fStringToCC_er(framePtr + 32L, framePtr + 16L, 0L, 1L, 46L, 44L);
            result = 5L;
            if ((long) (int) tempVal != 0L) {
                return result;
            }
            isLessOrEqual = fLE(g_fin_i, framePtr + 16L) != 0L ? 1L : 0L;
            result = 5L;
            if (isLessOrEqual == 0L) {
                result = 99L;
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_I(long keyCode) {
        long isAlgPending = 0L;
        long errCode = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            fIntToCC(1L, g_fin_p_yr);
            if (getI32(g_com_keyinmode) == 5L) {
                if ((keyCode & 0xffL) != 0L) {
                    Com_StartFlashRunning();
                }
                status = TVMI_12C(getI64(g_fin_tvm_pinput), framePtr + 24L, getI64(g_fin_tvm_pinput + 16L), getI64(g_fin_tvm_pinput + 24L), getI64(g_fin_tvm_pinput + 32L), getI64(g_fin_tvm_pinput + 40L), CC_1, getU8(g_fin_begin_of_period), getI8(g_fin_compoundinterest) & 0xffL, 0L);
                errCode = Com_GetErrIgnoreOverflow(status, framePtr + 24L);
                if ((keyCode & 0xffL) != 0L) {
                    Com_StopFlashRunning();
                }
                KeypadDriver_ClearCallback();
                if ((long) (int) errCode == 0L) {
                    putI64(g_fin_i + 8L, getI64(framePtr + 32L));
                    putI32(g_fin_i, getI32(framePtr + 24L));
                    putI32(g_fin_i + 4L, getI32(framePtr + 28L));
                    Com_SetResult(g_fin_i, keyCode & 0xffL);
                    isAlgPending = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    if (isAlgPending != 0L) {
                        Stack_InsertToX(g_fin_i);
                    } else {
                        Stack_SetX(g_fin_i);
                    }
                    Com_SetXInsertMode(1L);
                    putI32(g_com_keyinmode, 5L);
                    if (getI32(g_com_operationmode) == 0L) {
                        putI64(g_basic_lastx + 16L, 1L);
                    }
                    status = 99L;
                    return status;
                }
                if ((long) (int) errCode != 24L) {
                    status = 5L;
                    return status;
                }
                putCCReal(framePtr + 40L, Stack_GetX());
                Com_SetResult(framePtr + 40L, keyCode & 0xffL);
                status = 99L;
                return status;
            }
            if (getI32(g_com_keyinmode) == 3L) {
                Com_EndEditMode_IgnoreOP();
            } else {
                if (getI32(g_com_keyinmode) == 2L) {
                    Com_EndEditMode_CareOP();
                    Com_SetResult(g_fin_i, keyCode & 0xffL);
                    isAlgPending = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    Com_NewValueGenerated(g_fin_i, isAlgPending);
                    if (getI32(g_com_operationmode) == 0L && (long) (int) comma(isAlgPending = Com_IsInALGPendingMode() != 0L ? 1L : 0L, isAlgPending) != 0L) {
                        isAlgPending = 0L;
                    } else {
                        isAlgPending = 1L;
                    }
                    Com_SetXInsertMode(isAlgPending);
                    putI32(g_com_keyinmode, 0L);
                    Com_SetToNumberState(1L);
                    status = 99L;
                    return status;
                }
                status = Com_EndCalculateMode();
                if ((long) (int) status != 99L) {
                    return status;
                }
            }
            putCCReal(g_fin_i, Stack_GetX_12digits());
            Com_SetResult(g_fin_i, keyCode & 0xffL);
            Com_SetXInsertMode(getU8(g_com_insert_for_finreg));
            putI32(g_com_keyinmode, 5L);
            status = 99L;
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_PV(long keyCode) {
        long isAlgPending = 0L;
        long errCode = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            fIntToCC(1L, g_fin_p_yr);
            if (getI32(g_com_keyinmode) == 5L) {
                if ((keyCode & 0xffL) != 0L) {
                    Com_StartFlashRunning();
                }
                status = TVMPV_12C(getI64(g_fin_tvm_pinput), getI64(g_fin_tvm_pinput + 8L), framePtr + 24L, getI64(g_fin_tvm_pinput + 24L), getI64(g_fin_tvm_pinput + 32L), getI64(g_fin_tvm_pinput + 40L), CC_1, getU8(g_fin_begin_of_period), getI8(g_fin_compoundinterest) & 0xffL);
                if ((keyCode & 0xffL) != 0L) {
                    Com_StopFlashRunning();
                }
                errCode = Com_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 24L);
                if ((long) (int) errCode != 0L) {
                    status = 5L;
                    return status;
                }
                putI64(g_fin_pv + 8L, getI64(framePtr + 32L));
                putI32(g_fin_pv, getI32(framePtr + 24L));
                putI32(g_fin_pv + 4L, getI32(framePtr + 28L));
                Com_SetResult(g_fin_pv, keyCode & 0xffL);
                isAlgPending = Com_GetXInsertMode() != 0L ? 1L : 0L;
                if (isAlgPending != 0L) {
                    Stack_InsertToX(g_fin_pv);
                } else {
                    Stack_SetX(g_fin_pv);
                }
                Com_SetXInsertMode(1L);
                putI32(g_com_keyinmode, 5L);
                if (getI32(g_com_operationmode) == 0L) {
                    putI64(g_basic_lastx + 16L, 1L);
                }
                status = 99L;
                return status;
            }
            if (getI32(g_com_keyinmode) == 3L) {
                Com_EndEditMode_IgnoreOP();
            } else {
                if (getI32(g_com_keyinmode) == 2L) {
                    Com_EndEditMode_CareOP();
                    Com_SetResult(g_fin_pv, keyCode & 0xffL);
                    isAlgPending = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    Com_NewValueGenerated(g_fin_pv, isAlgPending);
                    if (getI32(g_com_operationmode) == 0L && (long) (int) comma(isAlgPending = Com_IsInALGPendingMode() != 0L ? 1L : 0L, isAlgPending) != 0L) {
                        isAlgPending = 0L;
                    } else {
                        isAlgPending = 1L;
                    }
                    Com_SetXInsertMode(isAlgPending);
                    Com_SetToNumberState(1L);
                    putI32(g_com_keyinmode, 0L);
                    status = 99L;
                    return status;
                }
                status = Com_EndCalculateMode();
                if ((long) (int) status != 99L) {
                    return status;
                }
            }
            putCCReal(g_fin_pv, Stack_GetX_12digits());
            Com_SetResult(g_fin_pv, keyCode & 0xffL);
            Com_SetXInsertMode(getU8(g_com_insert_for_finreg));
            putI32(g_com_keyinmode, 5L);
            status = 99L;
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_INT(long keyCode) {
        long isEqual = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long errCode = 0L;
        long intVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            status2 = Com_EndCalculateMode();
            if ((long) (int) status2 != 99L) {
                return status2;
            }
            if ((keyCode & 0xffL) != 0L) {
                Com_StartFlashRunning();
                status1 = CC_New_INT(g_fin_pv, g_fin_n, g_fin_i, framePtr + 40L, framePtr + 56L) & 0xffffffffL;
                Com_StopFlashRunning();
            } else {
                status1 = CC_New_INT(g_fin_pv, g_fin_n, g_fin_i, framePtr + 40L, framePtr + 56L) & 0xffffffffL;
            }
            errCode = Com_GetErrIgnoreOverflow(status1, framePtr + 40L);
            if ((long) (int) errCode == 0L) {
                Com_GetErrIgnoreOverflow(status1, framePtr + 56L);
                isEqual = Com_GetXInsertMode() != 0L ? 1L : 0L;
                if (isEqual != 0L) {
                    Stack_InsertToX(framePtr + 56L);
                } else {
                    Stack_SetX(framePtr + 56L);
                }
                putI64(framePtr + 24L, getI64(g_fin_pv + 8L));
                putI32(framePtr + 16L, getI64(g_fin_pv));
                putI32(framePtr + 20L, getI32(g_fin_pv + 4L));
                isEqual = fEQ(framePtr + 16L, CC_0) != 0L ? 1L : 0L;
                if (isEqual != 0L || getI32(framePtr + 16L) != 1L) {
                    intVal = 1L;
                } else {
                    intVal = -1L;
                }
                putI32(framePtr + 16L, intVal);
                Stack_InsertToX(framePtr + 16L);
                Stack_InsertToX(framePtr + 40L);
                Com_SetResult(framePtr + 40L, keyCode & 0xffL);
                Com_SetXInsertMode(1L);
                if (getI32(g_com_operationmode) == 0L) {
                    putI64(g_basic_lastx + 16L, 1L);
                }
                status2 = 99L;
            } else {
                status2 = 5L;
            }
            return status2;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_SIGMA_PLUS(long keyCode) {
        CC_Real realVal = new CC_Real();
        long flag = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(336);
        try {
            status2 = Com_EndCalculateMode();
            if ((long) (int) status2 != 99L) {
                return status2 & 0xffffffffL;
            }
            if (getI32(g_com_operationmode) == 0L && getU32(g_com_prevkeycode) == 32L) {
                Stack_ShiftUpOneStep();
            }
            putCCReal(framePtr + 40L, Stack_GetX());
            putCCReal(framePtr + 24L, Stack_GetY());
            realVal = getCCReal(framePtr + 40L);
            if (getI32(g_com_operationmode) == 1L) {
                putI64(g_basic_lastx + 8L, getI64(framePtr + 48L));
                putI32(g_basic_lastx, getI32(framePtr + 40L));
                putI32(g_basic_lastx + 4L, getI32(framePtr + 44L));
            } else {
                if (getI64(g_basic_lastx + 16L) != 0L) {
                    putI64(g_basic_lastx + 16L, 0L);
                }
            }
            putCCReal(framePtr + 40L, realVal);
            putCCReal(framePtr + 88L, GetStatReg(1L));
            putCCReal(framePtr + 104L, GetStatReg(2L));
            putCCReal(framePtr + 120L, GetStatReg(3L));
            putCCReal(framePtr + 136L, GetStatReg(4L));
            putCCReal(framePtr + 152L, GetStatReg(5L));
            putCCReal(framePtr + 168L, GetStatReg(6L));
            flag = fradd(framePtr + 88L, CC_1, framePtr + 88L, 0L) != 0L ? 1L : 0L;
            if (flag >= 2L) {
                return status2 & 0xffffffffL;
            }
            BF123_CheckRealData_Ex(framePtr + 88L);
            Com_SetResult(framePtr + 88L, keyCode & 0xffL);
            flag = fradd(framePtr + 104L, framePtr + 40L, framePtr + 104L, 0L) != 0L ? 1L : 0L;
            if (!(flag < 2L && comma(status1 = fmul(framePtr + 40L, framePtr + 40L, framePtr + 56L) & 0xffffffffL, status1 < 2L ? 1L : 0L) != 0L)) {
                return status2 & 0xffffffffL;
            }
            flag = fradd(framePtr + 120L, framePtr + 56L, framePtr + 120L, 0L) != 0L ? 1L : 0L;
            if (flag >= 2L) {
                return status2 & 0xffffffffL;
            }
            flag = fradd(framePtr + 136L, framePtr + 24L, framePtr + 136L, 0L) != 0L ? 1L : 0L;
            if (!(flag < 2L && comma(status1 = fmul(framePtr + 24L, framePtr + 24L, framePtr + 56L) & 0xffffffffL, status1 < 2L ? 1L : 0L) != 0L)) {
                return status2 & 0xffffffffL;
            }
            flag = fradd(framePtr + 152L, framePtr + 56L, framePtr + 152L, 0L) != 0L ? 1L : 0L;
            if (!(flag < 2L && comma(status1 = fmul(framePtr + 40L, framePtr + 24L, framePtr + 56L) & 0xffffffffL, status1 < 2L ? 1L : 0L) != 0L)) {
                return status2 & 0xffffffffL;
            }
            flag = fradd(framePtr + 168L, framePtr + 56L, framePtr + 168L, 0L) != 0L ? 1L : 0L;
            if (flag < 2L) {
                Com_SetXInsertMode(0L);
                Stack_SetX(framePtr + 88L);
                BF123_CheckRealData_Ex(framePtr + 88L);
                SetStatReg(1L, framePtr + 88L);
                BF123_CheckRealData_Ex(framePtr + 104L);
                SetStatReg(2L, framePtr + 104L);
                BF123_CheckRealData_Ex(framePtr + 120L);
                SetStatReg(3L, framePtr + 120L);
                BF123_CheckRealData_Ex(framePtr + 136L);
                SetStatReg(4L, framePtr + 136L);
                BF123_CheckRealData_Ex(framePtr + 152L);
                SetStatReg(5L, framePtr + 152L);
                BF123_CheckRealData_Ex(framePtr + 168L);
                SetStatReg(6L, framePtr + 168L);
            }
            return status2 & 0xffffffffL;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_SIGMA_SUB(long keyCode) {
        CC_Real realVal = new CC_Real();
        long flag = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(336);
        try {
            status2 = Com_EndCalculateMode();
            if ((long) (int) status2 != 99L) {
                return status2 & 0xffffffffL;
            }
            if (getI32(g_com_operationmode) == 0L && getU32(g_com_prevkeycode) == 32L) {
                Stack_ShiftUpOneStep();
            }
            putCCReal(framePtr + 40L, Stack_GetX());
            putCCReal(framePtr + 24L, Stack_GetY());
            realVal = getCCReal(framePtr + 40L);
            if (getI32(g_com_operationmode) == 1L) {
                putI64(g_basic_lastx + 8L, getI64(framePtr + 48L));
                putI32(g_basic_lastx, getI32(framePtr + 40L));
                putI32(g_basic_lastx + 4L, getI32(framePtr + 44L));
            } else {
                if (getI64(g_basic_lastx + 16L) != 0L) {
                    putI64(g_basic_lastx + 16L, 0L);
                }
            }
            putCCReal(framePtr + 40L, realVal);
            putCCReal(framePtr + 88L, GetStatReg(1L));
            putCCReal(framePtr + 104L, GetStatReg(2L));
            putCCReal(framePtr + 120L, GetStatReg(3L));
            putCCReal(framePtr + 136L, GetStatReg(4L));
            putCCReal(framePtr + 152L, GetStatReg(5L));
            putCCReal(framePtr + 168L, GetStatReg(6L));
            flag = fradd(framePtr + 88L, CC_1, framePtr + 88L, 1L) != 0L ? 1L : 0L;
            if (flag >= 2L) {
                return status2 & 0xffffffffL;
            }
            BF123_CheckRealData_Ex(framePtr + 88L);
            Com_SetResult(framePtr + 88L, keyCode & 0xffL);
            flag = fradd(framePtr + 104L, framePtr + 40L, framePtr + 104L, 1L) != 0L ? 1L : 0L;
            if (!(flag < 2L && comma(status1 = fmul(framePtr + 40L, framePtr + 40L, framePtr + 56L) & 0xffffffffL, status1 < 2L ? 1L : 0L) != 0L)) {
                return status2 & 0xffffffffL;
            }
            flag = fradd(framePtr + 120L, framePtr + 56L, framePtr + 120L, 1L) != 0L ? 1L : 0L;
            if (flag >= 2L) {
                return status2 & 0xffffffffL;
            }
            flag = fradd(framePtr + 136L, framePtr + 24L, framePtr + 136L, 1L) != 0L ? 1L : 0L;
            if (!(flag < 2L && comma(status1 = fmul(framePtr + 24L, framePtr + 24L, framePtr + 56L) & 0xffffffffL, status1 < 2L ? 1L : 0L) != 0L)) {
                return status2 & 0xffffffffL;
            }
            flag = fradd(framePtr + 152L, framePtr + 56L, framePtr + 152L, 1L) != 0L ? 1L : 0L;
            if (!(flag < 2L && comma(status1 = fmul(framePtr + 40L, framePtr + 24L, framePtr + 56L) & 0xffffffffL, status1 < 2L ? 1L : 0L) != 0L)) {
                return status2 & 0xffffffffL;
            }
            flag = fradd(framePtr + 168L, framePtr + 56L, framePtr + 168L, 1L) != 0L ? 1L : 0L;
            if (flag < 2L) {
                Com_SetXInsertMode(0L);
                Stack_SetX(framePtr + 88L);
                BF123_CheckRealData_Ex(framePtr + 88L);
                SetStatReg(1L, framePtr + 88L);
                BF123_CheckRealData_Ex(framePtr + 104L);
                SetStatReg(2L, framePtr + 104L);
                BF123_CheckRealData_Ex(framePtr + 120L);
                SetStatReg(3L, framePtr + 120L);
                BF123_CheckRealData_Ex(framePtr + 136L);
                SetStatReg(4L, framePtr + 136L);
                BF123_CheckRealData_Ex(framePtr + 152L);
                SetStatReg(5L, framePtr + 152L);
                BF123_CheckRealData_Ex(framePtr + 168L);
                SetStatReg(6L, framePtr + 168L);
            }
            return status2 & 0xffffffffL;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_MEAN(long keyCode) {
        long isInsertMode = 0L;
        long flag = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            putI64(framePtr + 72L, 0L);
            putI32(framePtr + 64L, 1L);
            putI32(framePtr + 68L, 0L);
            status = Com_EndCalculateMode();
            if ((long) (int) status != 99L) {
                return status;
            }
            putCCReal(framePtr + 48L, GetStatReg(1L));
            putCCReal(framePtr + 32L, GetStatReg(2L));
            putCCReal(framePtr + 16L, GetStatReg(4L));
            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
            Com_SetXInsertMode(isInsertMode);
            if (getI32(g_com_operationmode) == 0L && getI64(g_basic_lastx + 16L) == 0L) {
                putI64(g_basic_lastx + 16L, 1L);
            }
            flag = fdiv(framePtr + 32L, framePtr + 48L, framePtr + 88L) & 0xffL;
            if (flag < 2L && comma(flag = fdiv(framePtr + 16L, framePtr + 48L, framePtr + 64L) & 0xffL, flag < 2L ? 1L : 0L) != 0L) {
                BF123_CheckRealData_Ex(framePtr + 64L);
                BF123_CheckRealData_Ex(framePtr + 88L);
                isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                if (isInsertMode != 0L) {
                    Stack_InsertToX(framePtr + 64L);
                } else {
                    Stack_SetX(framePtr + 64L);
                }
                Stack_InsertToX(framePtr + 88L);
                Com_SetXInsertMode(1L);
                Com_SetResult(framePtr + 88L, keyCode & 0xffL);
                status = 99L;
            } else {
                status = 2L;
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_MEAN_W(long keyCode) {
        long isInsertMode = 0L;
        long flag = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            status = Com_EndCalculateMode();
            if ((long) (int) status != 99L) {
                return status;
            }
            putCCReal(framePtr + 40L, GetStatReg(2L));
            putCCReal(framePtr + 24L, GetStatReg(6L));
            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
            Com_SetXInsertMode(isInsertMode);
            if (getI32(g_com_operationmode) == 0L && getI64(g_basic_lastx + 16L) == 0L) {
                putI64(g_basic_lastx + 16L, 1L);
            }
            flag = fdiv(framePtr + 24L, framePtr + 40L, framePtr + 56L) & 0xffL;
            if (flag < 2L) {
                BF123_CheckRealData_Ex(framePtr + 56L);
                isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                if (isInsertMode != 0L) {
                    Stack_InsertToX(framePtr + 56L);
                } else {
                    Stack_SetX(framePtr + 56L);
                }
                Com_SetXInsertMode(1L);
                Com_SetResult(framePtr + 56L, keyCode & 0xffL);
                status = 99L;
            } else {
                status = 2L;
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_S(long keyCode) {
        long isInsertMode = 0L;
        long flag = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(416);
        try {
            status2 = Com_EndCalculateMode();
            if ((long) (int) status2 != 99L) {
                return status2;
            }
            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
            Com_SetXInsertMode(isInsertMode);
            putCCReal(framePtr + 160L, GetStatReg(1L));
            putCCReal(framePtr + 176L, GetStatReg(2L));
            putCCReal(framePtr + 192L, GetStatReg(3L));
            putCCReal(framePtr + 208L, GetStatReg(4L));
            putCCReal(framePtr + 224L, GetStatReg(5L));
            if (getI32(g_com_operationmode) == 0L && getI64(g_basic_lastx + 16L) == 0L) {
                putI64(g_basic_lastx + 16L, 1L);
            }
            isInsertMode = fradd(framePtr + 160L, CC_1, framePtr + 80L, 1L) != 0L ? 1L : 0L;
            if (isInsertMode < 2L) {
                status1 = fmul(framePtr + 160L, framePtr + 80L, framePtr + 128L) & 0xffffffffL;
                if (status1 < 2L && comma(status1 = fmul(framePtr + 160L, framePtr + 192L, framePtr + 80L) & 0xffffffffL, status1 < 2L ? 1L : 0L) != 0L) {
                    status1 = fmul(framePtr + 176L, framePtr + 176L, framePtr + 96L) & 0xffffffffL;
                    if (status1 < 2L) {
                        isInsertMode = fradd(framePtr + 80L, framePtr + 96L, framePtr + 112L, 1L) != 0L ? 1L : 0L;
                        if (isInsertMode < 2L && comma(flag = fdiv(framePtr + 112L, framePtr + 128L, framePtr + 32L) & 0xffL, flag < 2L ? 1L : 0L) != 0L && comma(status1 = fmul(framePtr + 160L, framePtr + 224L, framePtr + 80L) & 0xffffffffL, status1 < 2L ? 1L : 0L) != 0L && comma(status1 = fmul(framePtr + 208L, framePtr + 208L, framePtr + 96L) & 0xffffffffL, status1 < 2L && comma(isInsertMode = fradd(framePtr + 80L, framePtr + 96L, framePtr + 112L, 1L) != 0L ? 1L : 0L, isInsertMode < 2L ? 1L : 0L) != 0L ? 1L : 0L) != 0L && comma(flag = fdiv(framePtr + 112L, framePtr + 128L, framePtr + 16L) & 0xffL, flag < 2L && comma(flag = fisqrt(framePtr + 32L, framePtr + 64L) & 0xffL, flag < 2L ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
                            fisqrt(framePtr + 16L, framePtr + 48L);
                        }
                    }
                }
            }
            status2 = CC_New_StdDev(framePtr + 192L, framePtr + 176L, framePtr + 224L, framePtr + 208L, framePtr + 160L, framePtr + 64L, framePtr + 48L);
            if ((status2 & 0xffffffffL) < 2L) {
                BF123_CheckRealData_Ex(framePtr + 48L);
                BF123_CheckRealData_Ex(framePtr + 64L);
                isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                if (isInsertMode != 0L) {
                    Stack_InsertToX(framePtr + 48L);
                } else {
                    Stack_SetX(framePtr + 48L);
                }
                Stack_InsertToX(framePtr + 64L);
                Com_SetXInsertMode(1L);
                Com_SetResult(framePtr + 64L, keyCode & 0xffL);
                status2 = 99L;
            } else {
                status2 = 2L;
            }
            return status2;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_X_R(long keyCode) {
        CC_Real realVal = new CC_Real();
        long isInsertMode = 0L;
        long status = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(352);
        try {
            status = Com_EndCalculateMode();
            result = status & 0xffffffffL;
            if ((long) (int) status != 99L) {
                return result;
            }
            putCCReal(framePtr + 40L, Stack_GetX());
            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
            Com_SetXInsertMode(isInsertMode);
            realVal = getCCReal(framePtr + 40L);
            if (getI32(g_com_operationmode) == 1L) {
                putI64(g_basic_lastx + 8L, getI64(framePtr + 48L));
                putI32(g_basic_lastx, getI32(framePtr + 40L));
                putI32(g_basic_lastx + 4L, getI32(framePtr + 44L));
            } else {
                if (getI64(g_basic_lastx + 16L) == 0L) {
                    putI64(g_basic_lastx + 16L, 1L);
                }
            }
            putCCReal(framePtr + 40L, realVal);
            putCCReal(framePtr + 88L, GetStatReg(1L));
            result = 2L;
            putCCReal(framePtr + 104L, GetStatReg(2L));
            putCCReal(framePtr + 120L, GetStatReg(3L));
            putCCReal(framePtr + 136L, GetStatReg(4L));
            putCCReal(framePtr + 152L, GetStatReg(5L));
            putCCReal(framePtr + 168L, GetStatReg(6L));
            status = CC_New_LinearEstimateX(framePtr + 120L, framePtr + 104L, framePtr + 152L, framePtr + 136L, framePtr + 168L, framePtr + 88L, framePtr + 40L, framePtr + 24L, framePtr + 56L);
            if ((status & 0xffffffffL) < 2L) {
                BF123_CheckRealData_Ex(framePtr + 56L);
                BF123_CheckRealData_Ex(framePtr + 24L);
                Stack_SetX(framePtr + 56L);
                Stack_InsertToX(framePtr + 24L);
                Com_SetXInsertMode(1L);
                putCCReal(framePtr + 40L, Stack_GetX());
                Com_SetResult(framePtr + 40L, keyCode & 0xffL);
                result = 99L;
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_Y_R(long keyCode) {
        CC_Real realVal = new CC_Real();
        long isInsertMode = 0L;
        long status = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(352);
        try {
            status = Com_EndCalculateMode();
            result = status & 0xffffffffL;
            if ((long) (int) status != 99L) {
                return result;
            }
            putCCReal(framePtr + 56L, Stack_GetX());
            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
            Com_SetXInsertMode(isInsertMode);
            realVal = getCCReal(framePtr + 56L);
            if (getI32(g_com_operationmode) == 1L) {
                putI64(g_basic_lastx + 8L, getI64(framePtr + 64L));
                putI32(g_basic_lastx, getI32(framePtr + 56L));
                putI32(g_basic_lastx + 4L, getI32(framePtr + 60L));
            } else {
                if (getI64(g_basic_lastx + 16L) == 0L) {
                    putI64(g_basic_lastx + 16L, 1L);
                }
            }
            putCCReal(framePtr + 56L, realVal);
            putCCReal(framePtr + 88L, GetStatReg(1L));
            result = 2L;
            putCCReal(framePtr + 104L, GetStatReg(2L));
            putCCReal(framePtr + 120L, GetStatReg(3L));
            putCCReal(framePtr + 136L, GetStatReg(4L));
            putCCReal(framePtr + 152L, GetStatReg(5L));
            putCCReal(framePtr + 168L, GetStatReg(6L));
            status = CC_New_LinearEstimateY(framePtr + 120L, framePtr + 104L, framePtr + 152L, framePtr + 136L, framePtr + 168L, framePtr + 88L, framePtr + 56L, framePtr + 40L, framePtr + 24L);
            if ((status & 0xffffffffL) < 2L) {
                BF123_CheckRealData_Ex(framePtr + 24L);
                BF123_CheckRealData_Ex(framePtr + 40L);
                Stack_SetX(framePtr + 24L);
                Stack_InsertToX(framePtr + 40L);
                Com_SetXInsertMode(1L);
                putCCReal(framePtr + 56L, Stack_GetX());
                Com_SetResult(framePtr + 56L, keyCode & 0xffL);
                result = 99L;
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_BILL(long keyCode) {
        CC_Real realVal = new CC_Real();
        long isLessOrEqual = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            if (getI32(g_com_keyinmode) == 3L) {
                Com_EndEditMode_IgnoreOP();
            } else {
                if (getI32(g_com_keyinmode) == 2L) {
                    Com_EndEditMode_CareOP();
                    Com_SetResult(g_tip_bill, keyCode & 0xffL);
                    isLessOrEqual = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    Com_NewValueGenerated(g_tip_bill, isLessOrEqual);
                    if (getI32(g_com_operationmode) == 0L && (long) (int) comma(isLessOrEqual = Com_IsInALGPendingMode() != 0L ? 1L : 0L, isLessOrEqual) != 0L) {
                        isLessOrEqual = 0L;
                    } else {
                        isLessOrEqual = 1L;
                    }
                    Com_SetXInsertMode(isLessOrEqual);
                    Com_SetToNumberState(1L);
                    putI32(g_com_keyinmode, 0L);
                    status = 99L;
                    return status;
                }
                status = Com_EndCalculateMode();
                if ((long) (int) status != 99L) {
                    return status;
                }
            }
            putCCReal(framePtr + 24L, Stack_GetX_12digits());
            isLessOrEqual = fLE(framePtr + 24L, CC_0) != 0L ? 1L : 0L;
            realVal = getCCReal(framePtr + 24L);
            if (isLessOrEqual != 0L) {
                status = 10L;
                return status;
            }
            putI64(g_tip_bill + 8L, getI64(framePtr + 32L));
            putI32(g_tip_bill, getI32(framePtr + 24L));
            putI32(g_tip_bill + 4L, getI32(framePtr + 28L));
            putCCReal(framePtr + 24L, realVal);
            Com_SetResult(g_tip_bill, keyCode & 0xffL);
            Com_SetXInsertMode(getU8(g_com_insert_for_finreg));
            putI32(g_com_keyinmode, 6L);
            status = 99L;
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_TIP_PERCENT(long keyCode) {
        CC_Real realVal = new CC_Real();
        long isLessOrEqual = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            if (getI32(g_com_keyinmode) == 3L) {
                Com_EndEditMode_IgnoreOP();
            } else {
                if (getI32(g_com_keyinmode) == 2L) {
                    Com_EndEditMode_CareOP();
                    Com_SetResult(g_tip_tippercent, keyCode & 0xffL);
                    isLessOrEqual = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    Com_NewValueGenerated(g_tip_tippercent, isLessOrEqual);
                    if (getI32(g_com_operationmode) == 0L && (long) (int) comma(isLessOrEqual = Com_IsInALGPendingMode() != 0L ? 1L : 0L, isLessOrEqual) != 0L) {
                        isLessOrEqual = 0L;
                    } else {
                        isLessOrEqual = 1L;
                    }
                    Com_SetXInsertMode(isLessOrEqual);
                    Com_SetToNumberState(1L);
                    putI32(g_com_keyinmode, 0L);
                    status = 99L;
                    return status;
                }
                status = Com_EndCalculateMode();
                if ((long) (int) status != 99L) {
                    return status;
                }
            }
            putCCReal(framePtr + 24L, Stack_GetX_12digits());
            isLessOrEqual = fLE(framePtr + 24L, CC_0) != 0L ? 1L : 0L;
            realVal = getCCReal(framePtr + 24L);
            if (isLessOrEqual != 0L) {
                status = 10L;
                return status;
            }
            putI64(g_tip_tippercent + 8L, getI64(framePtr + 32L));
            putI32(g_tip_tippercent, getI32(framePtr + 24L));
            putI32(g_tip_tippercent + 4L, getI32(framePtr + 28L));
            putCCReal(framePtr + 24L, realVal);
            Com_SetResult(g_tip_tippercent, keyCode & 0xffL);
            Com_SetXInsertMode(getU8(g_com_insert_for_finreg));
            putI32(g_com_keyinmode, 6L);
            status = 99L;
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_SPLIT(long keyCode) {
        CC_Real realVal = new CC_Real();
        long isLess = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            if (getI32(g_com_keyinmode) == 3L) {
                Com_EndEditMode_IgnoreOP();
            } else {
                if (getI32(g_com_keyinmode) == 2L) {
                    Com_EndEditMode_CareOP();
                    Com_SetResult(g_tip_split, keyCode & 0xffL);
                    isLess = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    Com_NewValueGenerated(g_tip_split, isLess);
                    if (getI32(g_com_operationmode) == 0L && (long) (int) comma(isLess = Com_IsInALGPendingMode() != 0L ? 1L : 0L, isLess) != 0L) {
                        isLess = 0L;
                    } else {
                        isLess = 1L;
                    }
                    Com_SetXInsertMode(isLess);
                    Com_SetToNumberState(1L);
                    putI32(g_com_keyinmode, 0L);
                    status = 99L;
                    return status;
                }
                status = Com_EndCalculateMode();
                if ((long) (int) status != 99L) {
                    return status;
                }
            }
            putCCReal(framePtr + 24L, Stack_GetX_12digits());
            isLess = fiInteger(framePtr + 24L) != 0L ? 1L : 0L;
            if (isLess == 0L || (long) (int) comma(comma(isLess = fLT(framePtr + 24L, CC_1) != 0L ? 1L : 0L, realVal = getCCReal(framePtr + 24L)), isLess) != 0L) {
                status = 10L;
                return status;
            }
            putI64(g_tip_split + 8L, getI64(framePtr + 32L));
            putI32(g_tip_split, getI32(framePtr + 24L));
            putI32(g_tip_split + 4L, getI32(framePtr + 28L));
            putCCReal(framePtr + 24L, realVal);
            Com_SetResult(g_tip_split, keyCode & 0xffL);
            Com_SetXInsertMode(getU8(g_com_insert_for_finreg));
            putI32(g_com_keyinmode, 6L);
            status = 99L;
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_TIP(long keyCode) {
        long isAlgPending = 0L;
        long status = 0L;
        long errCode = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            if (getI32(g_com_keyinmode) == 6L) {
                if ((keyCode & 0xffL) != 0L) {
                    Com_StartFlashRunning();
                    status = TipCalc(g_tip_bill, g_tip_tippercent, g_tip_split, framePtr + 24L, 0L);
                    Com_StopFlashRunning();
                } else {
                    status = TipCalc(g_tip_bill, g_tip_tippercent, g_tip_split, framePtr + 24L, 0L);
                }
                errCode = Com_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 24L);
                if ((long) (int) errCode != 0L) {
                    status = 10L;
                    return status;
                }
                putI64(g_tip_tip + 8L, getI64(framePtr + 32L));
                putI32(g_tip_tip, getI32(framePtr + 24L));
                putI32(g_tip_tip + 4L, getI32(framePtr + 28L));
                Com_SetResult(g_tip_tip, keyCode & 0xffL);
                isAlgPending = Com_GetXInsertMode() != 0L ? 1L : 0L;
                if (isAlgPending != 0L) {
                    Stack_InsertToX(g_tip_tip);
                } else {
                    Stack_SetX(g_tip_tip);
                }
                Com_SetXInsertMode(1L);
                putI32(g_com_keyinmode, 6L);
                if (getI32(g_com_operationmode) == 0L) {
                    putI64(g_basic_lastx + 16L, 1L);
                }
                status = 99L;
                return status;
            }
            if (getI32(g_com_keyinmode) == 3L) {
                Com_EndEditMode_IgnoreOP();
            } else {
                if (getI32(g_com_keyinmode) == 2L) {
                    Com_EndEditMode_CareOP();
                    Com_SetResult(g_tip_tip, keyCode & 0xffL);
                    isAlgPending = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    Com_NewValueGenerated(g_tip_tip, isAlgPending);
                    if (getI32(g_com_operationmode) == 0L && (long) (int) comma(isAlgPending = Com_IsInALGPendingMode() != 0L ? 1L : 0L, isAlgPending) != 0L) {
                        isAlgPending = 0L;
                    } else {
                        isAlgPending = 1L;
                    }
                    Com_SetXInsertMode(isAlgPending);
                    Com_SetToNumberState(1L);
                    putI32(g_com_keyinmode, 0L);
                    status = 99L;
                    return status;
                }
                status = Com_EndCalculateMode();
                if ((long) (int) status != 99L) {
                    return status;
                }
            }
            putCCReal(g_tip_tip, Stack_GetX_12digits());
            Com_SetResult(g_tip_tip, keyCode & 0xffL);
            Com_SetXInsertMode(getU8(g_com_insert_for_finreg));
            putI32(g_com_keyinmode, 6L);
            status = 99L;
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_TOTAL(long keyCode) {
        long isAlgPending = 0L;
        long status = 0L;
        long errCode = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            if (getI32(g_com_keyinmode) == 6L) {
                if ((keyCode & 0xffL) != 0L) {
                    Com_StartFlashRunning();
                    status = TipCalc(g_tip_bill, g_tip_tippercent, g_tip_split, 0L, framePtr + 24L);
                    Com_StopFlashRunning();
                } else {
                    status = TipCalc(g_tip_bill, g_tip_tippercent, g_tip_split, 0L, framePtr + 24L);
                }
                errCode = Com_GetErrIgnoreOverflow(status & 0xffffffffL, framePtr + 24L);
                if ((long) (int) errCode != 0L) {
                    status = 10L;
                    return status;
                }
                putI64(g_tip_total + 8L, getI64(framePtr + 32L));
                putI32(g_tip_total, getI32(framePtr + 24L));
                putI32(g_tip_total + 4L, getI32(framePtr + 28L));
                Com_SetResult(g_tip_total, keyCode & 0xffL);
                isAlgPending = Com_GetXInsertMode() != 0L ? 1L : 0L;
                if (isAlgPending != 0L) {
                    Stack_InsertToX(g_tip_total);
                } else {
                    Stack_SetX(g_tip_total);
                }
                Com_SetXInsertMode(1L);
                putI32(g_com_keyinmode, 6L);
                if (getI32(g_com_operationmode) == 0L) {
                    putI64(g_basic_lastx + 16L, 1L);
                }
                status = 99L;
                return status;
            }
            if (getI32(g_com_keyinmode) == 3L) {
                Com_EndEditMode_IgnoreOP();
            } else {
                if (getI32(g_com_keyinmode) == 2L) {
                    Com_EndEditMode_CareOP();
                    Com_SetResult(g_tip_total, keyCode & 0xffL);
                    isAlgPending = Com_GetXInsertMode() != 0L ? 1L : 0L;
                    Com_NewValueGenerated(g_tip_total, isAlgPending);
                    if (getI32(g_com_operationmode) == 0L && (long) (int) comma(isAlgPending = Com_IsInALGPendingMode() != 0L ? 1L : 0L, isAlgPending) != 0L) {
                        isAlgPending = 0L;
                    } else {
                        isAlgPending = 1L;
                    }
                    Com_SetXInsertMode(isAlgPending);
                    Com_SetToNumberState(1L);
                    putI32(g_com_keyinmode, 0L);
                    status = 99L;
                    return status;
                }
                status = Com_EndCalculateMode();
                if ((long) (int) status != 99L) {
                    return status;
                }
            }
            putCCReal(g_tip_total, Stack_GetX_12digits());
            Com_SetResult(g_tip_total, keyCode & 0xffL);
            Com_SetXInsertMode(getU8(g_com_insert_for_finreg));
            putI32(g_com_keyinmode, 6L);
            status = 99L;
            return status;
        } finally {
            sp = savedSp;
        }
    }

}

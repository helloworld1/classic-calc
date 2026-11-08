package org.liberty.android.classiccalc.engine;

/**
 * LCD driver, keypad driver, timer driver, and common UI/mode state management.
 */
abstract class DisplayAndKeypadEngine extends StackAndStatsEngine {
    void Driver_Init() {
        LcdDriver_Clear();
        LcdDriver_Init();
        KeypadDriver_Init();
    }

    void KeypadDriver_RegisterCallback(long callbackFn) {
        if (getI8(g_nKeypadDriverCallBackCount) == 0L) {
            putI64(g_fKeypadDriverCallBack, callbackFn);
        }
        putI8(g_nKeypadDriverCallBackCount, getI8(g_nKeypadDriverCallBackCount) + 1L);
    }

    long KeypadDriver_ClearCallback() {
        long tempVal1 = 0L;
        long result = 0L;
        long tempVal2 = 0L;
        tempVal1 = (getI8(g_nKeypadDriverCallBackCount) - 1L) & 0xffffffffL;
        tempVal2 = getI8(g_nKeypadDriverCallBackCount) & 0xffffffffL;
        if (0L < getI8(g_nKeypadDriverCallBackCount)) {
            putI8(g_nKeypadDriverCallBackCount, tempVal1);
            tempVal2 = tempVal1;
        }
        if ((tempVal2 & 0xffL) != 0L) {
            return result;
        } else {
            putI64(g_fKeypadDriverCallBack, 0L);
            return 0L;
        }
    }

    void KeypadDriver_Init() {
    }

    long KeypadDriver_WaitForKey() {
        return 0L;
    }

    void KeypadDriver_SetKeyNumToZero() {
    }

    long KeypadDriver_InsertKey(long keyCode) {
        return (long) (int) keyCode;
    }

    long KeypadDriver_PeekLastKeyCode() {
        return 0L;
    }

    void LcdDriver_PutChar(long charIndex, long charCode) {
        long strPtr = 0L;
        long flag1 = 0L;
        long flag2 = 0L;
        long index1 = 0L;
        long index2 = 0L;
        long intVal = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long longVal = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if (getU8(g_ResultStub) == 0L) {
                        index1 = 0L;
                        if ((charCode & 0xffL) == 0L) {
                            intVal = 0L;
                            putU8(g_ResultStub + intVal, charIndex);
                            putI8(g_ResultStub + 1L + intVal, 0L);
                            return;
                        }
                    } else {
                        index1 = 0L;
                        do {
                            strPtr = g_ResultStub + 1L + index1;
                            index1 = index1 + 1L;
                        } while (getI8(strPtr) != 0L);
                        if ((charCode & 0xffL) == 0L) {
                            intVal = 0L;
                            putU8(g_ResultStub + intVal, charIndex);
                            putI8(g_ResultStub + 1L + intVal, 0L);
                            return;
                        }
                    }
                    index2 = 0L;
                    tempVal2 = 0L;
                    flag2 = 1L;
                    flag1 = getU8(g_ResultStub);
                    step = 1;
                    continue stepLoop;
                }
                case 1: {
                    intVal = (long) (int) tempVal2;
                    longVal = intVal;
                    if ((long) (int) (flag1 | 2L) == 46L && flag2 != 0L) {
                        tempVal2 = (long) (int) (intVal + 1L);
                        flag2 = 1L;
                    } else {
                        if ((long) (int) index1 <= intVal) {
                            flag2 = 0L;
                            putU8(g_ResultStub + longVal, 32L);
                        }
                        tempVal2 = longVal + 1L;
                        index2 = (index2 + 1L) & 0xffffffffL;
                        if ((long) (int) (getI8(g_ResultStub + 1L + longVal) | 2L) == 46L) {
                            tempVal1 = (intVal + 2L) & 0xffffffffL;
                            if (flag2 == 0L) {
                                tempVal1 = tempVal2 & 0xffffffffL;
                            }
                            tempVal2 = tempVal1;
                        }
                    }
                    intVal = (long) (int) tempVal2;
                    if (index2 == (charCode & 0xffL)) {
                        putU8(g_ResultStub + intVal, charIndex);
                        putI8(g_ResultStub + 1L + intVal, 0L);
                        return;
                    }
                    flag1 = getU8(g_ResultStub + intVal);
                    step = 1;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long getSegmentPos(long digitIndex) {
        long strPtr = 0L;
        long flag1 = 0L;
        long flag2 = 0L;
        long intVal = 0L;
        long tempVal = 0L;
        long result = 0L;
        long index1 = 0L;
        long index2 = 0L;
        long longVal = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if (getU8(g_ResultStub) == 0L) {
                        index1 = 0L;
                        if ((digitIndex & 0xffL) == 0L) {
                            return 0L;
                        }
                    } else {
                        index1 = 0L;
                        do {
                            strPtr = g_ResultStub + 1L + index1;
                            index1 = index1 + 1L;
                        } while (getI8(strPtr) != 0L);
                        if ((digitIndex & 0xffL) == 0L) {
                            return 0L;
                        }
                    }
                    index2 = 0L;
                    result = 0L;
                    flag2 = 1L;
                    flag1 = getU8(g_ResultStub);
                    step = 1;
                    continue stepLoop;
                }
                case 1: {
                    intVal = (long) (int) result;
                    longVal = intVal;
                    if ((long) (int) (flag1 | 2L) == 46L && flag2 != 0L) {
                        result = (long) (int) (intVal + 1L);
                        flag2 = 1L;
                    } else {
                        if ((long) (int) index1 <= intVal) {
                            flag2 = 0L;
                            putU8(g_ResultStub + longVal, 32L);
                        }
                        result = longVal + 1L;
                        index2 = (index2 + 1L) & 0xffffffffL;
                        if ((long) (int) (getI8(g_ResultStub + 1L + longVal) | 2L) == 46L) {
                            tempVal = (intVal + 2L) & 0xffffffffL;
                            if (flag2 == 0L) {
                                tempVal = result & 0xffffffffL;
                            }
                            result = tempVal;
                        }
                    }
                    if (index2 == (digitIndex & 0xffL)) {
                        return result;
                    }
                    flag1 = getU8(g_ResultStub + (long) (int) result);
                    step = 1;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long LcdDriver_SetLcdMode(long lcdMode) {
        return lcdMode & 0xffL;
    }

    void LcdDriver_Init() {
    }

    void LcdDriver_On() {
    }

    void LcdDriver_Off() {
    }

    void LcdDriver_Clear() {
    }

    void LcdDriver_SetAnnunciator(long annunciatorId) {
        if ((annunciatorId & 0xffffffffL) < 11L) {
            putU8(g_StatusIndicators + (annunciatorId & 0xffffffffL), 49L);
        }
    }

    long LcdDriver_ClearAnnunciator(long annunciatorId) {
        if ((annunciatorId & 0xffffffffL) < 11L) {
            putU8(g_StatusIndicators + (annunciatorId & 0xffffffffL), 48L);
        }
        return annunciatorId & 0xffffffffL;
    }

    long LcdDriver_GetAnnunciatorState(long annunciatorId) {
        if ((annunciatorId & 0xffffffffL) < 11L) {
            return getU8(g_StatusIndicators + (annunciatorId & 0xffffffffL));
        } else {
            return 11L;
        }
    }

    long LcdDriver_ShowSegments(long digitPos, long segmentMask) {
        return digitPos & 0xffL;
    }

    void LcdDriver_ShowPunctuation(long digitPos, long punctMode) {
        long strPtr = 0L;
        long flag1 = 0L;
        long flag2 = 0L;
        long index1 = 0L;
        long tempVal1 = 0L;
        long index2 = 0L;
        long intVal = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long longVal = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if (getU8(g_ResultStub) == 0L) {
                        index1 = 0L;
                        if ((punctMode & 0xffL) == 0L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            step = 3;
                            continue stepLoop;
                        }
                    } else {
                        index1 = 0L;
                        do {
                            strPtr = g_ResultStub + 1L + index1;
                            index1 = index1 + 1L;
                        } while (getI8(strPtr) != 0L);
                        if ((punctMode & 0xffL) != 0L) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                }
                case 1: {
                    if ((long) (int) digitPos == 1L) {
                        tempVal1 = 46L;
                    } else {
                        if ((long) (int) digitPos != 2L) {
                            putI8(g_ResultStub + 2L + intVal, 0L);
                            return;
                        }
                        tempVal1 = 44L;
                    }
                    putI8(g_ResultStub + 1L + intVal, tempVal1);
                    putI8(g_ResultStub + 2L + intVal, 0L);
                    return;
                }
                case 2: {
                    intVal = 0L;
                    step = 1;
                    continue stepLoop;
                }
                case 3: {
                    index2 = 0L;
                    tempVal3 = 0L;
                    flag2 = 1L;
                    flag1 = getU8(g_ResultStub);
                    while (true) {
                        intVal = (long) (int) tempVal3;
                        longVal = intVal;
                        if ((long) (int) (flag1 | 2L) == 46L && flag2 != 0L) {
                            tempVal3 = (long) (int) (intVal + 1L);
                            flag2 = 1L;
                        } else {
                            if ((long) (int) index1 <= intVal) {
                                flag2 = 0L;
                                putU8(g_ResultStub + longVal, 32L);
                            }
                            tempVal3 = longVal + 1L;
                            index2 = (index2 + 1L) & 0xffffffffL;
                            if ((long) (int) (getI8(g_ResultStub + 1L + longVal) | 2L) == 46L) {
                                tempVal2 = (intVal + 2L) & 0xffffffffL;
                                if (flag2 == 0L) {
                                    tempVal2 = tempVal3 & 0xffffffffL;
                                }
                                tempVal3 = tempVal2;
                            }
                        }
                        intVal = (long) (int) tempVal3;
                        if (index2 == (punctMode & 0xffL)) {
                            break;
                        }
                        flag1 = getU8(g_ResultStub + intVal);
                    }
                    step = 1;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long TimerDriver_WaitForTime(long durationMs) {
        long intVal = 0L;
        intVal = (long) (int) usleep((durationMs * 800L) & 0xffffffffL);
        return (long) (int) intVal;
    }

    long com_show_errorcode(long errorCode) {
        long result = 0L;
        long byteVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            result = errorCode & 0xffffffffL;
            if ((errorCode & 0xffffffffL) == 99L) {
                return result;
            }
            if ((long) (int) errorCode < 10L) {
                putU8(framePtr + 32L, 0L);
                byteVal = (long) (byte) (errorCode + (long) (int) errorCode / 10L * -10L);
            } else {
                byteVal = (long) (byte) ((errorCode & 0xffffffffL) / 10L);
                putU8(framePtr + 32L, ((errorCode & 0xffffffffL) % 10L) | 48L);
            }
            putI64(framePtr + 24L, ((((byteVal + 48L) & 0xffL)) << 56) | 0x20724f72724520L);
            putU8(framePtr + 33L, 0L);
            result = Com_PrintWithSign(framePtr + 24L, 0L, 0L);
            putU8(g_com_errmode, 1L);
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long com_show_errormsg(long errorMsgPtr) {
        long result = 0L;
        result = Com_PrintWithSign(errorMsgPtr, 0L, 0L);
        putU8(g_com_errmode, 1L);
        return result;
    }

    void com_reset_error() {
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            putCCReal(framePtr + 24L, Stack_GetX());
            Basic_PrintResult(framePtr + 24L);
            putU8(g_com_errmode, 0L);
        } finally {
            sp = savedSp;
        }
    }

    void Com_MathLibInit() {
    }

    long Com_UIInit() {
        long tempVal1 = 0L;
        long offset = 0L;
        long status = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        putU16(g_prog_maxstep, 400L);
        putU16(g_prog_nMemState, 659L);
        putU8(g_com_ignore_sub_key, 0L);
        putI32(g_com_machine_mode, 0L);
        putU8(g_fin_maxcflo, 81L);
        putI8(g_com_show_prerror, 0L);
        putU8(g_com_prog_share_gap, 1L);
        LcdDriver_SetLcdMode(0L);
        putU8(g_com_insert_for_finreg, 1L);
        com_show_errormsg(0x160000L);
        putI8(g_com_show_prerror, 1L);
        Prog_Init();
        tempVal2 = getU8(g_fin_maxcflo);
        putU8(g_fin_cflo_num, 0L);
        if (1L < tempVal2) {
            status = 0L;
            do {
                tempVal3 = status & 0xffL;
                tempVal1 = (status + 1L) & 0xffffffffL;
                status = tempVal1;
                offset = tempVal3 * 24L;
                putI64(g_fin_cflodata + 16L + offset, 0L);
                putI64(g_fin_cflodata + 8L + offset, 1L);
                putU16(g_fin_cflodata + tempVal3 * 24L, 1L);
            } while ((tempVal1 & 0xffL) < (long) (int) (tempVal2 - 1L));
        }
        putU16(g_com_fin_lastcflo_times, 1L);
        putI64(g_com_fin_lastcflo + 16L, 0L);
        putI64(g_com_fin_lastcflo + 8L, 1L);
        putU16(g_com_fin_lastcflo, 1L);
        SetTempReg(0L, CC_0);
        SetTempReg(1L, CC_0);
        SetTempReg(2L, CC_0);
        SetTempReg(3L, CC_0);
        SetTempReg(4L, CC_0);
        SetTempReg(5L, CC_0);
        SetTempReg(6L, CC_0);
        SetTempReg(7L, CC_0);
        SetTempReg(8L, CC_0);
        SetTempReg(9L, CC_0);
        SetTempReg(10L, CC_0);
        SetTempReg(11L, CC_0);
        SetTempReg(12L, CC_0);
        SetTempReg(13L, CC_0);
        SetTempReg(14L, CC_0);
        SetTempReg(15L, CC_0);
        SetTempReg(16L, CC_0);
        SetTempReg(17L, CC_0);
        SetTempReg(18L, CC_0);
        SetTempReg(19L, CC_0);
        putU8(g_com_rounddigit, 12L);
        putI32(g_com_operationmode, 1L);
        putI32(g_com_uimode, 0L);
        putI32(g_com_shiftmode, 0L);
        putI32(g_com_datemode, 0L);
        putI32(g_com_keyinmode, 0L);
        putU32(g_com_decimal_mode, 0L);
        putU8(g_fin_begin_of_period, 0L);
        putI8(g_fin_compoundinterest, 0L);
        putU8(g_com_needshowrunning, 0L);
        putU8(g_com_canundo, 0L);
        putU8(g_com_bracketmode, 0L);
        putU8(g_com_fincal_count, 0L);
        putU8(g_com_fincal_prev, 0L);
        Stack_ClearAll();
        putI64(g_com_reg_lastx + 8L, 0L);
        putI64(g_com_reg_lastx, 1L);
        putI64(g_com_reg_X1 + 8L, 0L);
        putI64(g_com_reg_X1, 1L);
        putI64(g_fin_n + 8L, 0L);
        putI64(g_fin_n, 1L);
        putI64(g_fin_i + 8L, 0L);
        putI64(g_fin_i, 1L);
        putI64(g_fin_npv + 8L, 0L);
        putI64(g_fin_npv, 1L);
        putI64(g_fin_irr + 8L, 0L);
        putI64(g_fin_irr, 1L);
        putI64(g_fin_pv + 8L, 0L);
        putI64(g_fin_pv, 1L);
        putI64(g_fin_pmt + 8L, 0L);
        putI64(g_fin_pmt, 1L);
        putI64(g_fin_fv + 8L, 0L);
        putI64(g_fin_fv, 1L);
        putI64(g_fin_p_yr + 8L, 0x100000000000000L);
        putI64(g_fin_p_yr, 1L);
        putI64(g_fin_tvm_pinput, g_fin_n);
        putI64(g_fin_tvm_pinput + 8L, g_fin_i);
        putI64(g_fin_tvm_pinput + 32L, g_fin_fv);
        putI64(g_fin_tvm_pinput + 40L, g_fin_p_yr);
        putI64(g_fin_tvm_pinput + 16L, g_fin_pv);
        putI64(g_fin_tvm_pinput + 24L, g_fin_pmt);
        putI64(g_tip_bill + 8L, 0L);
        putI64(g_tip_bill, 1L);
        status = fimul(CC_2, CC_10, g_tip_tippercent);
        putI64(g_tip_split + 8L, 0x100000000000000L);
        putI64(g_tip_split, 1L);
        putU32(g_com_prevkeycode, 0L);
        putU32(g_com_prevdown_keycode, 0L);
        putI32(g_com_onkeyup_mode, 0L);
        putI64(g_tip_tip + 8L, 0L);
        putI64(g_tip_tip, 1L);
        putI64(g_tip_total + 8L, 0L);
        putI64(g_tip_total, 1L);
        putU8(g_com_eex_forbackspace, 0L);
        if (getI8(g_com_show_prerror) != 0L) {
            return status;
        } else {
            status = Basic_InitBasicVar();
            return status;
        }
    }

    long Com_PrintWithSign(long strPtr, long cursorMode, long isNegative) {
        long annunciator = 0L;
        if ((isNegative & 0xffL) != 0L) {
            LcdDriver_SetAnnunciator(0L);
            annunciator = 0L;
        } else {
            annunciator = LcdDriver_ClearAnnunciator(0L);
        }
        if (strPtr != 0L) {
            LcdDriver_PrintCalculatorLine(strPtr, cursorMode & 0xffL);
            return 0L;
        } else {
            return annunciator;
        }
    }

    long Com_SetResult(long realPtr, long shouldPrint) {
        long errCode = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            putI64(framePtr + 24L, getI64(realPtr + 8L));
            putI32(framePtr + 16L, getI32(realPtr));
            putI32(framePtr + 20L, getI32(realPtr + 4L));
            errCode = BF123_CheckRealData_Ex(framePtr + 16L);
            if ((shouldPrint & 0xffL) != 0L) {
                errCode = Basic_PrintReal(framePtr + 16L);
            }
            putU32(g_basic_displaymode, 1L);
            putU8(g_com_eex_forbackspace, 0L);
            return errCode;
        } finally {
            sp = savedSp;
        }
    }

    long Com_EndEditMode_ClearOP() {
        long result = 0L;
        putU8(g_com_Xinsertmode, getI32(g_result_state) == 0L || getU32(g_basic_displaymode) == 0L ? 1L : 0L);
        Basic_EndEditMode();
        putU32(g_basic_op, 99L);
        result = Stack_EmptyRegisters();
        return result;
    }

    void Com_EndEditMode_IgnoreOP() {
        Com_EndEditMode_CareOP();
    }

    void Com_EndEditMode_CareOP() {
        long stackDepth = 0L;
        long exprState = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if (getI32(g_result_state) == 0L) {
                        step = 1;
                        continue stepLoop;
                    } else {
                        if (getU32(g_basic_displaymode) == 0L) {
                            if (getI32(g_com_operationmode) != 0L) {
                                step = 1;
                                continue stepLoop;
                            } else {
                                exprState = (long) (int) Basic_GetExpressState();
                                if (exprState != 0L && comma(exprState = (long) (int) Basic_GetExpressState(), exprState != 2L ? 1L : 0L) != 0L && comma(exprState = (long) (int) Basic_GetExpressState(), exprState != 3L ? 1L : 0L) != 0L) {
                                    stackDepth = (long) (byte) Stack_GetRegistersDepth();
                                    putU8(g_com_Xinsertmode, (byte) stackDepth == 0L ? 1L : 0L);
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    step = 2;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                }
                case 1: {
                    putU8(g_com_Xinsertmode, 1L);
                    step = 3;
                    continue stepLoop;
                }
                case 2: {
                    putU8(g_com_Xinsertmode, 0L);
                    step = 3;
                    continue stepLoop;
                }
                case 3: {
                    Basic_EndEditMode();
                    if (getU8(g_com_Xinsertmode) != 0L) {
                        putI32(g_result_state, 0L);
                    }
                    return;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long Com_IsInALGPendingMode() {
        long stackDepth = 0L;
        long exprState = 0L;
        if (getI32(g_com_operationmode) != 0L) {
            return 0L;
        }
        exprState = (long) (int) Basic_GetExpressState();
        if (exprState != 0L && comma(exprState = (long) (int) Basic_GetExpressState(), exprState != 2L ? 1L : 0L) != 0L && comma(exprState = (long) (int) Basic_GetExpressState(), exprState != 3L ? 1L : 0L) != 0L) {
            stackDepth = (long) (byte) Stack_GetRegistersDepth();
            return (byte) stackDepth != 0L ? 1L : 0L;
        } else {
            return 1L;
        }
    }

    long Com_EndEditMode_ForBasic() {
        long stackDepth = 0L;
        long exprState = 0L;
        long result = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if (getI32(g_result_state) == 0L) {
                        step = 1;
                        continue stepLoop;
                    } else {
                        if (getU32(g_basic_displaymode) == 0L) {
                            if (getI32(g_com_operationmode) != 0L) {
                                step = 1;
                                continue stepLoop;
                            } else {
                                exprState = (long) (int) Basic_GetExpressState();
                                if (exprState != 0L && comma(exprState = (long) (int) Basic_GetExpressState(), exprState != 2L ? 1L : 0L) != 0L && comma(exprState = (long) (int) Basic_GetExpressState(), exprState != 3L ? 1L : 0L) != 0L) {
                                    stackDepth = (long) (byte) Stack_GetRegistersDepth();
                                    putU8(g_com_Xinsertmode, (byte) stackDepth == 0L ? 1L : 0L);
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    step = 2;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                }
                case 1: {
                    putU8(g_com_Xinsertmode, 1L);
                    step = 3;
                    continue stepLoop;
                }
                case 2: {
                    putU8(g_com_Xinsertmode, 0L);
                    step = 3;
                    continue stepLoop;
                }
                case 3: {
                    result = Basic_EndEditMode();
                    if (getU8(g_com_Xinsertmode) != 0L) {
                        putI32(g_result_state, 0L);
                    }
                    return result;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long Com_EndCalculateMode() {
        long result = 0L;
        putU8(g_com_Xinsertmode, getI32(g_result_state) == 0L || getU32(g_basic_displaymode) == 0L ? 1L : 0L);
        result = Basic_EndCaculateNumBefore();
        return result;
    }

    long Com_SetXInsertMode(long insertMode) {
        long flag = 0L;
        long exprState = 0L;
        long result = 0L;
        result = insertMode & 0xffL;
        putU8(g_com_Xinsertmode, insertMode);
        if ((insertMode & 0xffL) == 0L) {
            putI32(g_result_state, 1L);
            return result;
        }
        if (getI32(g_com_operationmode) != 0L) {
            putI32(g_result_state, 0L);
            return 1L;
        }
        exprState = Basic_GetExpressState() & 0xffffffffL;
        result = exprState;
        if (exprState == 0L) {
            putI32(g_result_state, 1L);
            return result;
        }
        exprState = Basic_GetExpressState() & 0xffffffffL;
        result = exprState;
        if (exprState == 2L) {
            putI32(g_result_state, 1L);
            return result;
        }
        exprState = Basic_GetExpressState() & 0xffffffffL;
        result = exprState;
        if (exprState == 3L) {
            putI32(g_result_state, 1L);
            return result;
        }
        flag = Stack_GetRegistersDepth() & 0xffL;
        result = flag;
        if (flag == 0L) {
            putI32(g_result_state, 0L);
            return 0L;
        } else {
            putI32(g_result_state, 1L);
            return result;
        }
    }

    long Com_GetXInsertMode() {
        return getU8(g_com_Xinsertmode) != 0L || getI32(g_result_state) == 0L ? 1L : 0L;
    }

    long Com_StartFlashRunning() {
        long result = 0L;
        if (getU8(g_com_needshowrunning) == 0L) {
            LcdDriver_Clear();
            putI32(g_com_shiftmode, 0L);
            LcdDriver_ClearAnnunciator(0L);
            LcdDriver_PrintCalculatorLine(0x16000aL, 0L);
            putU8(g_com_needshowrunning, 1L);
            result = 0L;
        }
        return 0L;
    }

    long Com_StopFlashRunning() {
        long result = 0L;
        long tempVal = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    result = 0L;
                    if (getU8(g_com_needshowrunning) == 0L) {
                        return result;
                    }
                    putU8(g_com_needshowrunning, 0L);
                    if (getI32(g_com_machine_mode) == 2L) {
                        step = 2;
                        continue stepLoop;
                    } else {
                        if (getI32(g_com_machine_mode) == 0L) {
                            if (getU8(g_com_canundo) != 0L) {
                                LcdDriver_SetAnnunciator(1L);
                            }
                            if (getU8(g_com_bracketmode) != 0L) {
                                LcdDriver_SetAnnunciator(4L);
                            }
                            step = 2;
                            continue stepLoop;
                        } else {
                            step = 1;
                            continue stepLoop;
                        }
                    }
                }
                case 1: {
                    if (getI32(g_com_shiftmode) == 1L) {
                        tempVal = 5L;
                        LcdDriver_SetAnnunciator(tempVal);
                        result = 0L;
                    } else {
                        if (getI32(g_com_shiftmode) == 2L) {
                            tempVal = 6L;
                            LcdDriver_SetAnnunciator(tempVal);
                            result = 0L;
                        }
                    }
                    if (getU8(g_fin_begin_of_period) != 0L) {
                        LcdDriver_SetAnnunciator(7L);
                        result = 0L;
                    }
                    if (getI32(g_com_datemode) == 1L) {
                        LcdDriver_SetAnnunciator(8L);
                        result = 0L;
                    }
                    if (getI8(g_fin_compoundinterest) != 0L) {
                        LcdDriver_SetAnnunciator(9L);
                        result = 0L;
                    }
                    if (getI32(g_com_uimode) != 1L) {
                        return result;
                    } else {
                        LcdDriver_SetAnnunciator(10L);
                        return 0L;
                    }
                }
                case 2: {
                    tempVal = 2L;
                    if (getI32(g_com_operationmode) == 0L) {
                        tempVal = 3L;
                    }
                    LcdDriver_SetAnnunciator(tempVal);
                    result = 0L;
                    step = 1;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long Com_SetToNumberState(long isNumberState) {
        return (isNumberState & 0xffL) != 0L ? 1L : 0L;
    }

    long Com_RefreshLCDString() {
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            if (getU32(g_basic_displaymode) == 0L) {
                status = Basic_PrintEditLine(1L);
            } else {
                putCCReal(framePtr + 24L, Stack_GetX());
                BF123_CheckRealData_Ex(framePtr + 24L);
                status = Basic_PrintReal(framePtr + 24L);
                putU32(g_basic_displaymode, 1L);
                putU8(g_com_eex_forbackspace, 0L);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Com_TestKeyPressed() {
        long key = 0L;
        while (true) {
            key = KeypadDriver_PeekKey() & 0xffffffffL;
            if (key == 0L) {
                return 0L;
            }
            if (((key >> 7) & 1L) == 0L) {
                break;
            }
            KeypadDriver_GetKey();
        }
        if (key == 9L) {
            return 9L;
        } else {
            KeypadDriver_GetKey();
            return key;
        }
    }

    long Com_WaitForTime(long durationMs, long allowKeyInterrupt) {
        long index = 0L;
        putU8(g_com_interruptbykey, 1L);
        if ((durationMs & 0xffffL) != 0L && (allowKeyInterrupt & 0xffL) == 0L) {
            index = 0L;
            do {
                TimerDriver_WaitForTime(64L);
                index = (index + 1L) & 0xffffL;
            } while (index < (durationMs & 0xffffL));
        }
        return 0L;
    }

    long Com_NewValueGenerated(long realPtr, long shouldPrint) {
        long stackDepth = 0L;
        long exprState = 0L;
        long result = 0L;
        if (getI32(g_com_operationmode) == 0L) {
            exprState = (long) (int) Basic_GetExpressState();
            if (exprState == 0L) {
                Stack_SetX(realPtr);
                Basic_ChangeExpressState(0L);
                return 0L;
            }
            exprState = (long) (int) Basic_GetExpressState();
            if (exprState == 2L) {
                Stack_PushXToX1();
                Stack_SetX(realPtr);
                Basic_ChangeExpressState(0L);
                return 0L;
            }
            exprState = (long) (int) Basic_GetExpressState();
            if (exprState == 3L || comma(stackDepth = (long) (byte) Stack_GetRegistersDepth(), (byte) stackDepth != 0L ? 1L : 0L) != 0L) {
                result = Stack_SetX(realPtr);
                return result;
            }
        } else {
            if (getI32(g_com_operationmode) != 1L) {
                return realPtr;
            }
        }
        if ((shouldPrint & 0xffL) != 0L) {
            result = Stack_InsertToX(realPtr);
            return result;
        } else {
            result = Stack_SetX(realPtr);
            return result;
        }
    }

    void ComKeyCheckState(long keyCode) {
        long tempVal = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    switch ((int) (getI32(g_com_keyinmode))) {
                        case 2: {
                            switch ((int) (keyCode)) {
                                case 10: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 14: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 18: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 22: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 26: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 34: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 35: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 36: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 37: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 38: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 39: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 40: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 41: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 42: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 43: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 44: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 138: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 140: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 142: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 146: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 150: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 154: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 11: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 12: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 13: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 15: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 16: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 17: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 19: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 20: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 21: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 23: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 24: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 25: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 27: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 28: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 29: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 30: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 31: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 32: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 33: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 45: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 46: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 47: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 48: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 49: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 50: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 51: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 52: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 53: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 54: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 55: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 56: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 57: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 58: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 59: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 60: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 61: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 62: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 63: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 64: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 65: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 66: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 67: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 68: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 69: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 70: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 71: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 72: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 73: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 74: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 75: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 76: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 77: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 78: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 79: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 80: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 81: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 82: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 83: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 84: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 85: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 86: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 87: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 88: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 89: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 90: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 91: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 92: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 93: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 94: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 95: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 96: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 97: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 98: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 99: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 100: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 101: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 102: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 103: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 104: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 105: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 106: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 107: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 108: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 109: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 110: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 111: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 112: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 113: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 114: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 115: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 116: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 117: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 118: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 119: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 120: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 121: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 122: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 123: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 124: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 125: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 126: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 127: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 128: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 129: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 130: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 131: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 132: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 133: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 134: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 135: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 136: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 137: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 139: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 141: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 143: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 144: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 145: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 147: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 148: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 149: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 151: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 152: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 153: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                default: {
                                    if (8L < ((keyCode - 291L) & 0xffffffffL)) {
                                        putI32(g_com_keyinmode, 0L);
                                        return;
                                    }
                                    tempVal = (1L << (int) ((keyCode - 291L) & 31L)) & 307L;
                                    step = 3;
                                    continue stepLoop;
                                }
                            }
                        }
                        case 3: {
                            switch ((int) (keyCode)) {
                                case 10:
                                case 14:
                                case 18:
                                case 22:
                                case 26:
                                case 31:
                                case 34:
                                case 35:
                                case 36:
                                case 37:
                                case 38:
                                case 39:
                                case 40:
                                case 41:
                                case 42:
                                case 43:
                                case 44:
                                case 46:
                                case 47:
                                case 48:
                                case 49:
                                case 110:
                                case 111:
                                case 112:
                                case 113: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                default: {
                                    step = 2;
                                    continue stepLoop;
                                }
                            }
                        }
                        case 4: {
                            step = 4;
                            continue stepLoop;
                        }
                        case 5: {
                            if ((keyCode & 0xffffffffL) < 27L && ((1L << (int) (keyCode & 31L)) & 0x4455400L) != 0L) {
                                return;
                            }
                            if (22L < ((keyCode - 144L) & 0xffffffffL)) {
                                putI32(g_com_keyinmode, 0L);
                                return;
                            }
                            tempVal = (1L << (int) ((keyCode - 144L) & 31L)) & 0x440001L;
                            step = 3;
                            continue stepLoop;
                        }
                        default: {
                            step = 1;
                            continue stepLoop;
                        }
                    }
                }
                case 1: {
                    return;
                }
                case 2: {
                    putI32(g_com_keyinmode, 0L);
                    return;
                }
                case 3: {
                    if (tempVal == 0L) {
                        putI32(g_com_keyinmode, 0L);
                        return;
                    }
                    return;
                }
                case 4: {
                    if (10L < ((keyCode - 34L) & 0xffffffffL)) {
                        putI32(g_com_keyinmode, 0L);
                        return;
                    }
                    return;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long ComSpecialTransForRcl(long keyCode) {
        long tempVal = 0L;
        long annunciator = 0L;
        long longVal = 0L;
        if (!(getI32(g_com_keyinmode) == 2L && comma(tempVal = getI32(keyCode) & 0xffffffffL, (tempVal >> 7) & 1L) != 0L)) {
            return keyCode;
        }
        longVal = 0L;
        do {
            if (tempVal == getU32(ADDR_RODATA + 6412L + longVal)) {
                putI32(keyCode, tempVal & 0xffffff7fL);
                putI32(g_com_keyinmode, 0L);
                putI32(g_com_shiftmode, 0L);
                annunciator = LcdDriver_ClearAnnunciator(6L);
                if (getI32(keyCode) != 18L) {
                    return annunciator;
                }
                if (getU8(g_com_fincal_prev) == 0L) {
                    return annunciator;
                } else {
                    putI32(g_com_keyinmode, 5L);
                    return annunciator;
                }
            }
            longVal = longVal + 4L;
        } while (longVal != 124L);
        return keyCode;
    }

    long ComKeyPreHandle(long keyCode) {
        long result = 0L;
        result = keyCode & 0xffffffffL;
        if ((long) (int) keyCode != 28L && (long) (int) keyCode != 174L && (long) (int) keyCode != 176L) {
            if (getI8(g_undo_flag) != 0L) {
                Basic_ProcessUndoModeKeys();
            }
            result = Basic_DisableUndoMode();
            if (23L < ((keyCode - 153L) & 0xffffffffL) || ((1L << (int) ((keyCode - 153L) & 31L)) & 0xa00001L) == 0L) {
                putU8(g_bracket_flag, 0L);
            }
        }
        if (getU8(g_com_fincal_count) != 3L) {
            if (getU8(g_com_fincal_count) == 2L) {
                if (3L < ((keyCode - 46L) & 0xffffffffL)) {
                    putU8(g_com_fincal_count, 0L);
                    return result;
                } else {
                    putU8(g_com_fincal_count, 3L);
                    return result;
                }
            }
            if (getU8(g_com_fincal_count) == 1L) {
                putU8(g_com_fincal_count, ((long) (int) keyCode == 25L ? 1L : 0L) << 1);
                return result;
            } else {
                putU8(g_com_fincal_count, 0L);
                return result;
            }
        }
        if ((keyCode & 0xffffffffL) < 27L) {
            if (((1L << (int) (keyCode & 31L)) & 0x4444400L) != 0L) {
                putI32(g_com_keyinmode, 5L);
                putU8(g_com_fincal_count, 0L);
                return result;
            }
            if ((long) (int) keyCode == 25L) {
                putU8(g_com_fincal_count, 2L);
                return result;
            }
        }
        if (!((long) (int) keyCode != 166L && (long) (int) keyCode != 162L)) {
            putI32(g_com_keyinmode, 5L);
            putU8(g_com_fincal_count, 0L);
            return result;
        }
        if ((keyCode & 0xffffff7fL) == 16L) {
            return result;
        } else {
            putU8(g_com_fincal_count, 0L);
            return result;
        }
    }

    long Com_KeypadTranslate(long rawKeyCode, long isKeyUp) {
        long annunciator = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long result = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    result = (((rawKeyCode & 128L)) >> 7) & 0xffffffffL;
                    if ((rawKeyCode & 128L) != 0L) {
                        rawKeyCode = (long) (int) (rawKeyCode - 128L);
                    }
                    putU8(isKeyUp, result >> 0);
                    if (result == 0L) {
                        if (getI8(g_com_show_prerror) != 0L) {
                            if (((long) (int) rawKeyCode == 48L || (long) (int) rawKeyCode == 22L) && getU8(g_com_ignore_sub_key) != 0L) {
                                rawKeyCode = 0L;
                            } else {
                                Basic_InitBasicVar();
                                putI8(g_com_show_prerror, 0L);
                            }
                            putU8(g_com_ignore_sub_key, 0L);
                        }
                    } else {
                        if ((long) (int) rawKeyCode != 9L && getU32(g_com_prevdown_keycode) != (rawKeyCode & 0xffffffffL)) {
                            if (2L < ((getI32(g_com_onkeyup_mode) - 2L) & 0xffffffffL)) {
                                return 0L;
                            } else {
                                putI32(g_com_onkeyup_mode, 1L);
                                return 0L;
                            }
                        }
                    }
                    if ((long) (int) rawKeyCode != 0L && comma(tempVal2 = Com_KeypadIsSpecialKey((long) (int) rawKeyCode, getU8(isKeyUp)), (tempVal2 & 1L) == 0L ? 1L : 0L) != 0L) {
                        putI32(g_com_onkeyup_mode, 0L);
                        if (getU8(g_com_errmode) == 0L || getU8(isKeyUp) != 0L) {
                            if ((long) (int) rawKeyCode == 51L) {
                                if (getU8(isKeyUp) != 0L) {
                                    putI32(g_com_onkeyup_mode, 0L);
                                    return 0L;
                                }
                                putI32(g_com_shiftmode, 3L);
                                step = 1;
                                continue stepLoop;
                            } else {
                                if ((long) (int) rawKeyCode == 21L) {
                                    if (getU8(isKeyUp) != 0L) {
                                        putI32(g_com_onkeyup_mode, 0L);
                                        return 0L;
                                    }
                                    putI32(g_com_shiftmode, 2L);
                                    step = 1;
                                    continue stepLoop;
                                } else {
                                    if ((long) (int) rawKeyCode != 17L) {
                                        if (getI32(g_com_shiftmode) == 3L) {
                                            result = ((rawKeyCode | 256L)) & 0xffffffffL;
                                            switch ((int) (result)) {
                                                case 271: {
                                                    result = 139L;
                                                    break;
                                                }
                                                case 276: {
                                                    result = 95L;
                                                    break;
                                                }
                                                case 280: {
                                                    result = 94L;
                                                    break;
                                                }
                                                case 281: {
                                                    result = 153L;
                                                    break;
                                                }
                                                case 285: {
                                                    result = 157L;
                                                    break;
                                                }
                                                case 288: {
                                                    result = 160L;
                                                    break;
                                                }
                                                case 302: {
                                                    result = 174L;
                                                    break;
                                                }
                                                case 303: {
                                                    result = 175L;
                                                    break;
                                                }
                                                case 304: {
                                                    result = 176L;
                                                    break;
                                                }
                                                case 305: {
                                                    result = 177L;
                                                    break;
                                                }
                                                default: {
                                                    break;
                                                }
                                            }
                                            annunciator = (long) (byte) getU8(isKeyUp);
                                            step = 2;
                                            continue stepLoop;
                                        } else {
                                            if (getI32(g_com_shiftmode) == 2L) {
                                                annunciator = (long) (byte) getU8(isKeyUp);
                                                result = ((rawKeyCode | 128L)) & 0xffffffffL;
                                                step = 2;
                                                continue stepLoop;
                                            } else {
                                                result = rawKeyCode & 0xffffffffL;
                                                if (getI32(g_com_shiftmode) == 1L) {
                                                    result = ((rawKeyCode | 64L)) & 0xffffffffL;
                                                    if (getU8(isKeyUp) != 0L) {
                                                        putI32(g_com_shiftmode, 0L);
                                                    }
                                                    tempVal1 = 5L;
                                                    LcdDriver_ClearAnnunciator(tempVal1);
                                                }
                                                step = 3;
                                                continue stepLoop;
                                            }
                                        }
                                    } else {
                                        if (getU8(isKeyUp) != 0L) {
                                            putI32(g_com_onkeyup_mode, 0L);
                                            return 0L;
                                        }
                                        putI32(g_com_shiftmode, 1L);
                                        annunciator = (long) (byte) LcdDriver_GetAnnunciatorState(6L);
                                        if ((byte) annunciator != 0L) {
                                            LcdDriver_ClearAnnunciator(6L);
                                        }
                                        result = 5L;
                                        LcdDriver_SetAnnunciator(result);
                                        result = 0L;
                                        step = 3;
                                        continue stepLoop;
                                    }
                                }
                            }
                        } else {
                            putI32(g_com_keyinmode, 0L);
                            if (getI32(g_com_uimode) == 0L) {
                                com_reset_error();
                            } else {
                                Prog_ClearWarning();
                            }
                            result = 0L;
                            step = 3;
                            continue stepLoop;
                        }
                    } else {
                        result = 0L;
                        step = 3;
                        continue stepLoop;
                    }
                }
                case 1: {
                    annunciator = (long) (byte) LcdDriver_GetAnnunciatorState(5L);
                    if ((byte) annunciator != 0L) {
                        LcdDriver_ClearAnnunciator(5L);
                    }
                    result = 6L;
                    LcdDriver_SetAnnunciator(result);
                    result = 0L;
                    step = 3;
                    continue stepLoop;
                }
                case 2: {
                    if ((byte) annunciator != 0L) {
                        putI32(g_com_shiftmode, 0L);
                    }
                    tempVal1 = 6L;
                    LcdDriver_ClearAnnunciator(tempVal1);
                    step = 3;
                    continue stepLoop;
                }
                case 3: {
                    if (getU8(isKeyUp) == 0L) {
                        putU32(g_com_prevdown_keycode, rawKeyCode);
                    }
                    return result;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long Com_KeypadIsSpecialKey(long keyCode, long isKeyUp) {
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        switch ((int) (keyCode)) {
                            case 1: {
                                step = 1;
                                continue stepLoop;
                            }
                            case 2: {
                                step = 1;
                                continue stepLoop;
                            }
                            case 3: {
                                step = 2;
                                continue stepLoop;
                            }
                            case 4: {
                                step = 2;
                                continue stepLoop;
                            }
                            case 5: {
                                step = 2;
                                continue stepLoop;
                            }
                            case 6: {
                                step = 2;
                                continue stepLoop;
                            }
                            case 7: {
                                step = 2;
                                continue stepLoop;
                            }
                            case 8: {
                                step = 2;
                                continue stepLoop;
                            }
                            case 9: {
                                step = 2;
                                continue stepLoop;
                            }
                            case 50: {
                                step = 2;
                                continue stepLoop;
                            }
                            default: {
                                step = 3;
                                continue stepLoop;
                            }
                        }
                    }
                    case 1: {
                        result = 1L;
                        putI32(g_com_onkeyup_mode, 0L);
                        return result;
                    }
                    case 2: {
                        putI32(g_com_shiftmode, 0L);
                        LcdDriver_ClearAnnunciator(5L);
                        LcdDriver_ClearAnnunciator(6L);
                        putU32(g_com_prevkeycode, keyCode);
                        if (getI8(g_undo_flag) != 0L) {
                            Basic_ProcessUndoModeKeys();
                        }
                        Basic_DisableUndoMode();
                        putI32(g_com_keyinmode, 0L);
                        putU8(g_com_fincal_count, 0L);
                        putU8(g_com_fincal_prev, 0L);
                        if (getI32(g_com_uimode) == 1L) {
                            LcdDriver_ClearAnnunciator(10L);
                            putI32(g_com_uimode, 0L);
                            putU16(g_prog_currpos, 0L);
                            putCCReal(framePtr + 24L, Stack_GetX());
                            Com_SetResult(framePtr + 24L, 0L);
                        }
                        result = 1L;
                        switch ((int) (keyCode)) {
                            case 3: {
                                step = 1;
                                continue stepLoop;
                            }
                            case 4: {
                                step = 1;
                                continue stepLoop;
                            }
                            case 5: {
                                step = 1;
                                continue stepLoop;
                            }
                            case 7: {
                                step = 1;
                                continue stepLoop;
                            }
                            case 6: {
                                step = 3;
                                continue stepLoop;
                            }
                            case 8: {
                                step = 3;
                                continue stepLoop;
                            }
                            case 50: {
                                step = 3;
                                continue stepLoop;
                            }
                            case 9: {
                                if ((isKeyUp & 0xffL) == 0L) {
                                    result = 1L;
                                    result = 0L;
                                    return result;
                                }
                                if (getI32(g_com_onkeyup_mode) == 4L) {
                                    if ((getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
                                        putCCReal(framePtr + 24L, Stack_GetX());
                                        Basic_PrintResult(framePtr + 24L);
                                        putI32(g_com_onkeyup_mode, 0L);
                                        result = 1L;
                                        result = 0L;
                                        return result;
                                    }
                                } else {
                                    if (getI32(g_com_onkeyup_mode) == 3L) {
                                        putU32(g_com_decimal_mode, getU32(g_com_decimal_mode) == 0L ? 1L : 0L);
                                        putCCReal(framePtr + 24L, Stack_GetX());
                                        Basic_PrintResult(framePtr + 24L);
                                        putI32(g_com_onkeyup_mode, 0L);
                                        result = 1L;
                                        result = 0L;
                                        return result;
                                    }
                                    if (getI32(g_com_onkeyup_mode) == 1L) {
                                        putCCReal(framePtr + 24L, Stack_GetX());
                                        Basic_PrintResult(framePtr + 24L);
                                        putI32(g_com_onkeyup_mode, 0L);
                                        result = 1L;
                                        result = 0L;
                                        return result;
                                    }
                                }
                                putI32(g_com_onkeyup_mode, 0L);
                                result = 1L;
                                result = 0L;
                                return result;
                            }
                            default: {
                                step = 3;
                                continue stepLoop;
                            }
                        }
                    }
                    case 3: {
                        result = 0L;
                        return result;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Com_KeypadDispatch(long keyCode, long isKeyUp) {
        long result1 = 0L;
        long result2 = 0L;
        if (getI32(g_com_uimode) == 0L) {
            if ((isKeyUp & 0xffL) != 0L) {
                result1 = Base_HandleKeyUp((long) (int) keyCode, 1L) & 0xffffffffL;
                return result1;
            } else {
                result1 = Base_HandleKeyDown((long) (int) keyCode, 1L) & 0xffffffffL;
                return result1;
            }
        }
        if ((isKeyUp & 0xffL) != 0L) {
            result2 = Prog_HandleKeyUp((long) (int) keyCode);
            return result2;
        } else {
            result2 = Prog_HandleKeyDown((long) (int) keyCode);
            return result2;
        }
    }

    long Com_HandlePowerOff() {
        long isInsertMode = 0L;
        long result = 0L;
        putU8(g_com_errmode, 0L);
        if (getI32(g_com_uimode) == 1L) {
            Prog_HandleKeyDown(76L);
        }
        Com_EndEditMode_IgnoreOP();
        isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
        result = Com_SetXInsertMode(isInsertMode);
        if (getI32(g_com_operationmode) == 0L) {
            putI64(g_basic_lastx + 16L, 1L);
        }
        putI32(g_com_keydown_state, 6L);
        putI32(g_com_onkeyup_mode, 0L);
        putU16(g_prog_currpos, 0L);
        return result;
    }

    void _Com_SaveLCDState(long stateBufPtr) {
        long annunciator = 0L;
        long index = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    Com_EndEditMode_CareOP();
                    putU16(stateBufPtr, 0L);
                    index = 0L;
                    step = 1;
                    continue stepLoop;
                }
                case 1: {
                    if (getI32(g_com_machine_mode) == 2L) {
                        if (4L < index || (index & 127L) < 4L && (index & 127L) != 1L) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    } else {
                        if (getI32(g_com_machine_mode) == 0L) {
                            if (index < 11L) {
                                step = 3;
                                continue stepLoop;
                            } else {
                                step = 2;
                                continue stepLoop;
                            }
                        } else {
                            if (3L < ((index - 1L) & 0xffL)) {
                                step = 3;
                                continue stepLoop;
                            } else {
                                step = 2;
                                continue stepLoop;
                            }
                        }
                    }
                }
                case 2: {
                    index = (index + 1L) & 0xffffffffL;
                    if (index == 14L) {
                        return;
                    }
                    step = 1;
                    continue stepLoop;
                }
                case 3: {
                    annunciator = (long) (byte) LcdDriver_GetAnnunciatorState(index);
                    if ((byte) annunciator != 0L) {
                        putU16(stateBufPtr, getU16(stateBufPtr) | (1L << (int) (index & 31L)));
                    }
                    step = 2;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    void _Com_RestoreLCDState(long stateBufPtr) {
        long index = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        index = 0L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 1: {
                        if (getI32(g_com_machine_mode) == 2L) {
                            if (4L < index || (index & 127L) < 4L && (index & 127L) != 1L) {
                                step = 3;
                                continue stepLoop;
                            } else {
                                step = 2;
                                continue stepLoop;
                            }
                        } else {
                            if (getI32(g_com_machine_mode) == 0L) {
                                if (index < 11L) {
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    step = 2;
                                    continue stepLoop;
                                }
                            } else {
                                if (3L < ((index - 1L) & 0xffL)) {
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    step = 2;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 2: {
                        index = (index + 1L) & 0xffffffffL;
                        if (index == 14L) {
                            putCCReal(framePtr + 24L, Stack_GetX());
                            Basic_PrintResult(framePtr + 24L);
                            return;
                        }
                        step = 1;
                        continue stepLoop;
                    }
                    case 3: {
                        if (((((stateBufPtr & 0xffffL)) >> (int) (index & 31L)) & 1L) != 0L) {
                            LcdDriver_SetAnnunciator(index);
                        }
                        step = 2;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    void _Com_SoftReset() {
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            putCCReal(framePtr + 24L, Stack_GetX());
            Basic_PrintResult(framePtr + 24L);
        } finally {
            sp = savedSp;
        }
    }

    void Com_ConvertKeyForMachine(long keyCodePtr) {
        long intVal = 0L;
        long longVal = 0L;
        switch ((int) (getI32(keyCodePtr))) {
            case 89: {
                break;
            }
            case 93: {
                longVal = 1L;
                break;
            }
            case 94: {
                longVal = 2L;
                break;
            }
            case 95: {
                longVal = 3L;
                break;
            }
            case 109: {
                longVal = 4L;
                break;
            }
            case 110: {
                longVal = 5L;
                break;
            }
            case 111: {
                longVal = 6L;
                break;
            }
            case 112: {
                longVal = 7L;
                break;
            }
            case 113: {
                longVal = 8L;
                break;
            }
            case 153: {
                longVal = 9L;
                break;
            }
            case 157: {
                longVal = 10L;
                break;
            }
            case 160: {
                longVal = 11L;
                break;
            }
            case 174: {
                longVal = 12L;
                break;
            }
            case 175: {
                longVal = 13L;
                break;
            }
            case 176: {
                longVal = 14L;
                break;
            }
            case 177: {
                longVal = 15L;
                break;
            }
            default: {
                return;
            }
        }
        if (getI32(g_com_machine_mode) == 2L) {
            intVal = getI32(ADDR_RODATA + 6768L + longVal * 12L);
        } else {
            if (getI32(g_com_machine_mode) != 1L) {
                return;
            }
            intVal = getI32(ADDR_RODATA + 6764L + longVal * 12L);
        }
        putI32(keyCodePtr, intVal);
    }

    long BF123_CheckRealData(long realPtr) {
        long isZero = 0L;
        long result = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            isZero = fEQ(realPtr, CC_0) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                result = 0L;
                putI64(realPtr + 8L, 0L);
                putI32(realPtr, 1L);
                putI32(realPtr + 4L, 0L);
                return result;
            }
            putU8(framePtr + 60L, 57L);
            putU8(framePtr + 61L, 0L);
            putU8(framePtr + 56L, 49L);
            putU8(framePtr + 57L, 69L);
            putU8(framePtr + 58L, 45L);
            putU8(framePtr + 59L, 57L);
            fStringToCC_er(framePtr + 56L, framePtr + 40L, 0L, 1L, 46L, 44L);
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
            fStringToCC_er(framePtr + 56L, framePtr + 24L, 0L, 1L, 46L, 44L);
            isZero = fLT(framePtr + 24L, realPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                tempVal = (getU32(framePtr + 28L) << 32) | (getI32(framePtr + 24L) & 0xffffffffL);
                result = 1L;
                putI64(realPtr + 8L, getI64(framePtr + 32L));
                putI32(realPtr, tempVal);
                putI32(realPtr + 4L, tempVal >>> 32);
                putU8(g_com_result_overmax, 1L);
                return result;
            }
            isZero = fLT(realPtr, framePtr + 40L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                result = 0L;
                return result;
            }
            if (getI32(framePtr + 40L) == 3L) {
                putI32(framePtr + 40L, 1L);
            }
            isZero = fZero(framePtr + 40L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI32(framePtr + 40L, -getI32(framePtr + 40L));
            }
            if (getI32(framePtr + 24L) == 3L) {
                putI32(framePtr + 24L, 1L);
            }
            isZero = fZero(framePtr + 24L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI32(framePtr + 24L, -getI32(framePtr + 24L));
            }
            isZero = fLT(framePtr + 40L, realPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                result = 1L;
                putI64(realPtr + 8L, 0L);
                putI32(realPtr, 1L);
                putI32(realPtr + 4L, 0L);
                return result;
            } else {
                isZero = fLT(realPtr, framePtr + 24L) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    tempVal = (getU32(framePtr + 28L) << 32) | (getI32(framePtr + 24L) & 0xffffffffL);
                    result = 1L;
                    putI64(realPtr + 8L, getI64(framePtr + 32L));
                    putI32(realPtr, tempVal);
                    putI32(realPtr + 4L, tempVal >>> 32);
                    putU8(g_com_result_overmax, 1L);
                    return result;
                }
                result = 0L;
                return result;
            }
        } finally {
            sp = savedSp;
        }
    }

    long BF123_CheckRealData_Ex(long realPtr) {
        long isZero = 0L;
        long result = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            isZero = fEQ(realPtr, CC_0) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                result = 0L;
                putI64(realPtr + 8L, 0L);
                putI32(realPtr, 1L);
                putI32(realPtr + 4L, 0L);
                return result;
            }
            putU8(framePtr + 60L, 57L);
            putU8(framePtr + 61L, 0L);
            putU8(framePtr + 56L, 49L);
            putU8(framePtr + 57L, 69L);
            putU8(framePtr + 58L, 45L);
            putU8(framePtr + 59L, 57L);
            fStringToCC_er(framePtr + 56L, framePtr + 40L, 0L, 1L, 46L, 44L);
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
            fStringToCC_er(framePtr + 56L, framePtr + 24L, 0L, 1L, 46L, 44L);
            isZero = fLT(framePtr + 24L, realPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                tempVal = (getU32(framePtr + 28L) << 32) | (getI32(framePtr + 24L) & 0xffffffffL);
                result = 1L;
                putI64(realPtr + 8L, getI64(framePtr + 32L));
                putI32(realPtr, tempVal);
                putI32(realPtr + 4L, tempVal >>> 32);
                return result;
            }
            isZero = fLT(realPtr, framePtr + 40L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                result = 0L;
                return result;
            }
            if (getI32(framePtr + 40L) == 3L) {
                putI32(framePtr + 40L, 1L);
            }
            isZero = fZero(framePtr + 40L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI32(framePtr + 40L, -getI32(framePtr + 40L));
            }
            if (getI32(framePtr + 24L) == 3L) {
                putI32(framePtr + 24L, 1L);
            }
            isZero = fZero(framePtr + 24L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI32(framePtr + 24L, -getI32(framePtr + 24L));
            }
            isZero = fLT(framePtr + 40L, realPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                putI64(framePtr + 32L, 0L);
                tempVal = 1L;
                result = 1L;
                putI64(realPtr + 8L, getI64(framePtr + 32L));
                putI32(realPtr, tempVal);
                putI32(realPtr + 4L, tempVal >>> 32);
                return result;
            } else {
                isZero = fLT(realPtr, framePtr + 24L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    result = 0L;
                    return result;
                }
                tempVal = (getU32(framePtr + 28L) << 32) | (getI32(framePtr + 24L) & 0xffffffffL);
                result = 1L;
                putI64(realPtr + 8L, getI64(framePtr + 32L));
                putI32(realPtr, tempVal);
                putI32(realPtr + 4L, tempVal >>> 32);
                return result;
            }
        } finally {
            sp = savedSp;
        }
    }

    long BF123_IsValidRealData(long realPtr) {
        long isZero = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            isZero = fEQ(realPtr, CC_0) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                isZero = 1L;
                putI64(realPtr + 8L, 0L);
                putI32(realPtr, 1L);
                putI32(realPtr + 4L, 0L);
                return isZero;
            }
            putU8(framePtr + 60L, 57L);
            putU8(framePtr + 61L, 0L);
            putU8(framePtr + 56L, 49L);
            putU8(framePtr + 57L, 69L);
            putU8(framePtr + 58L, 45L);
            putU8(framePtr + 59L, 57L);
            fStringToCC_er(framePtr + 56L, framePtr + 40L, 0L, 1L, 46L, 44L);
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
            fStringToCC_er(framePtr + 56L, framePtr + 24L, 0L, 1L, 46L, 44L);
            isZero = fLT(framePtr + 24L, realPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                isZero = 0L;
            } else {
                isZero = fLT(realPtr, framePtr + 40L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    isZero = 1L;
                } else {
                    if (getI32(framePtr + 40L) == 3L) {
                        putI32(framePtr + 40L, 1L);
                    }
                    isZero = fZero(framePtr + 40L) != 0L ? 1L : 0L;
                    if (isZero == 0L) {
                        putI32(framePtr + 40L, -getI32(framePtr + 40L));
                    }
                    if (getI32(framePtr + 24L) == 3L) {
                        putI32(framePtr + 24L, 1L);
                    }
                    isZero = fZero(framePtr + 24L) != 0L ? 1L : 0L;
                    if (isZero == 0L) {
                        putI32(framePtr + 24L, -getI32(framePtr + 24L));
                    }
                    isZero = fLT(framePtr + 40L, realPtr) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        isZero = 1L;
                        putI64(realPtr + 8L, 0L);
                        putI32(realPtr, 1L);
                        putI32(realPtr + 4L, 0L);
                        return isZero;
                    }
                    isZero = fLT(realPtr, framePtr + 24L) != 0L ? 1L : 0L;
                    isZero = isZero == 0L ? 1L : 0L;
                }
            }
            return isZero;
        } finally {
            sp = savedSp;
        }
    }

    long Com_GetErrIgnoreOverflow(long errCode, long realPtr) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            tempVal = errCode & 0xffffffffL;
            if (tempVal < 2L) {
                BF123_CheckRealData_Ex(realPtr);
                errCode = 0L;
            } else {
                if (tempVal == 2L) {
                    errCode = 0L;
                    putI64(realPtr + 8L, 0L);
                    putI32(realPtr, 1L);
                    putI32(realPtr + 4L, 0L);
                } else {
                    if (tempVal != 3L) {
                        return errCode;
                    }
                    if (getI32(realPtr) == 0L) {
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
                    fStringToCC_er(framePtr + 16L, realPtr, 0L, 1L, 46L, 44L);
                    errCode = 0L;
                }
            }
            return errCode;
        } finally {
            sp = savedSp;
        }
    }

    void BF123_CheckUnderFlow(long realPtr) {
        long isZero = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            isZero = fEQ(realPtr, CC_0) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                putI64(realPtr + 8L, 0L);
                putI32(realPtr, 1L);
                putI32(realPtr + 4L, 0L);
            } else {
                putU8(framePtr + 44L, 57L);
                putU8(framePtr + 45L, 0L);
                putU8(framePtr + 40L, 49L);
                putU8(framePtr + 41L, 69L);
                putU8(framePtr + 42L, 45L);
                putU8(framePtr + 43L, 57L);
                fStringToCC_er(framePtr + 40L, framePtr + 24L, 0L, 1L, 46L, 44L);
                isZero = fLT(realPtr, framePtr + 24L) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    if (getI32(framePtr + 24L) == 3L) {
                        putI32(framePtr + 24L, 1L);
                    }
                    isZero = fZero(framePtr + 24L) != 0L ? 1L : 0L;
                    if (isZero == 0L) {
                        putI32(framePtr + 24L, -getI32(framePtr + 24L));
                    }
                    isZero = fLT(framePtr + 24L, realPtr) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        putI64(realPtr + 8L, 0L);
                        putI32(realPtr, 1L);
                        putI32(realPtr + 4L, 0L);
                    }
                }
                if (499L < getI32(realPtr + 4L)) {
                    putI32(realPtr + 4L, 499L);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Com_KeypadGetSimuKey() {
        return 0L;
    }

    long sysInit() {
        long result = 0L;
        Driver_Init();
        Com_MathLibInit();
        result = Com_UIInit();
        return result;
    }

    long sysMainLoop(long keyCode, long isKeyUp) {
        long key = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(128);
        try {
            key = ((keyCode ^ 128L)) & 0xffffffffL;
            if ((isKeyUp & 0xffL) == 0L) {
                key = keyCode & 0xffffffffL;
            }
            result = key;
            if (key == 0L) {
                return result;
            }
            key = Com_KeypadTranslate((long) (int) key, framePtr + 20L) & 0xffffffffL;
            result = key;
            if (key != 0L) {
                result = Com_KeypadDispatch((long) (int) key, getU8(framePtr + 20L));
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

}

package org.liberty.android.classiccalc.engine;

/**
 * Number entry, edit buffer, and Algebraic/RPN expression evaluation.
 */
abstract class AlgebraicBasicEngine extends DisplayAndKeypadEngine {
    long Basic_InitBasicVar() {
        long flag = 0L;
        long result = 0L;
        putU16(g_com_resultstate, 2L);
        putU32(g_basic_displaymode, 1L);
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
        putI32(g_com_operationmode, 1L);
        putI32(g_com_keyinmode, 0L);
        flag = 1L;
        if ((getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
            LcdDriver_ClearAnnunciator(3L);
            LcdDriver_SetAnnunciator(2L);
            flag = getI32(g_com_operationmode) < 2L ? 1L : 0L;
            result = 0L;
        }
        putU32(g_basic_op, 99L);
        putI64(g_undo_list + 8L, g_undo_buffer);
        putI64(g_undo_list + 32L, g_undo_buffer);
        putI64(g_undo_list + 56L, g_undo_buffer);
        putI64(g_undo_list + 80L, g_undo_buffer);
        putI64(g_undo_list + 104L, g_undo_buffer);
        putI64(g_undo_list + 128L, g_undo_buffer);
        putI64(g_undo_list + 152L, g_undo_buffer);
        putI64(g_undo_list + 176L, g_undo_buffer);
        putI64(g_undo_list + 200L, g_undo_buffer);
        putI64(g_undo_list + 224L, g_undo_buffer);
        putI64(g_undo_list + 248L, g_undo_buffer);
        putI64(g_undo_list + 272L, g_undo_buffer);
        putI64(g_undo_list + 296L, g_undo_buffer);
        putI64(g_undo_list + 320L, g_undo_buffer);
        putU16(g_reg_state, 0L);
        putU8(g_reg_state + 2L, 0L);
        putU8(g_stack_depth, 0L);
        putU8(g_undo_index, 0L);
        putI8(g_undo_flag, 0L);
        putI32(g_result_state, 1L);
        if (flag != 0L) {
            putU32(g_expression_state, 1L);
        }
        putI64(g_basic_lastx + 16L, 0L);
        putI64(g_basic_lastx + 8L, 0L);
        putI64(g_basic_lastx, 1L);
        putU8(g_bracket_flag, 0L);
        return 0L;
    }

    long Basic_SetCaculateMode(long calcMode, long resetStack) {
        long tempVal = 0L;
        long annunciator = 0L;
        annunciator = calcMode & 0xffffffffL;
        putI32(g_com_keyinmode, 0L);
        putI32(g_com_operationmode, calcMode);
        if ((getI32(g_com_machine_mode) & 0xfffffffdL) != 0L) {
            return annunciator;
        }
        if ((long) (int) calcMode == 0L) {
            annunciator = LcdDriver_ClearAnnunciator(2L);
            if ((resetStack & 1L) == 0L) {
                return annunciator;
            }
            tempVal = 3L;
        } else {
            annunciator = LcdDriver_ClearAnnunciator(3L);
            if ((resetStack & 1L) == 0L) {
                return annunciator;
            }
            tempVal = 2L;
        }
        LcdDriver_SetAnnunciator(tempVal);
        return 0L;
    }

    void Basic_SetExpressState(long exprState) {
        if (getI32(g_com_operationmode) == 1L) {
            exprState = 1L;
        } else {
            if (getI32(g_com_operationmode) != 0L) {
                return;
            }
        }
        putU32(g_expression_state, exprState);
    }

    long Basic_EndEditMode() {
        long isEqual = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            if (getU32(g_basic_displaymode) != 0L) {
                putU32(g_basic_displaymode, 1L);
                putU8(g_com_eex_forbackspace, 0L);
                return 0L;
            }
            fStringToCC_er(g_basic_edit, framePtr + 16L, 0L, 1L, 46L, 44L);
            isEqual = fEQ(framePtr + 16L, CC_0) != 0L ? 1L : 0L;
            if (isEqual != 0L) {
                putI64(framePtr + 24L, 0L);
                putI32(framePtr + 16L, 1L);
                putI32(framePtr + 20L, 0L);
            }
            Stack_SetX(framePtr + 16L);
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
            putI32(g_com_keyinmode, 0L);
            putU32(g_basic_displaymode, 1L);
            putU8(g_com_eex_forbackspace, 0L);
            return 0L;
        } finally {
            sp = savedSp;
        }
    }

    long Basic_NewNumberKeyEntry(long digitChar, long shouldPrint) {
        long result = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    step = 1;
                    continue stepLoop;
                }
                case 1: {
                    switch ((int) (getI32(g_com_keyinmode))) {
                        case 1: {
                            if (((digitChar - 48L) & 0xffL) < 10L) {
                                putI8(g_basic_edit + 14L, getI8(g_basic_edit + 15L));
                                putI8(g_basic_edit + 15L, digitChar);
                            }
                            Basic_ProcessEditBuffer(shouldPrint & 0xffL);
                            return 99L;
                        }
                        case 2: {
                            result = Basic_RclProcess((long) (byte) digitChar, shouldPrint & 0xffL);
                            break;
                        }
                        case 3: {
                            result = Basic_StoProcess((long) (byte) digitChar, shouldPrint & 0xffL);
                            break;
                        }
                        case 4: {
                            result = Basic_GtoProcess((long) (byte) digitChar);
                            return result;
                        }
                        default: {
                            Basic_NormalNumKeyProcess((long) (byte) digitChar, shouldPrint & 0xffL);
                            return 99L;
                        }
                    }
                    if ((long) (int) result != 98L) {
                        return result;
                    }
                    step = 1;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long Basic_StoProcess(long opCode, long regIndex) {
        long isEqual = 0L;
        long flag = 0L;
        long status = 0L;
        long result = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        if ((byte) opCode == 46L) {
                            if ((long) (int) (getI8(g_reg_state + 1L) - 3L) < 4L) {
                                if (getU32(g_basic_displaymode) == 0L) {
                                    fStringToCC_er(g_basic_edit, framePtr + 48L, 0L, 1L, 46L, 44L);
                                    isEqual = fEQ(framePtr + 48L, CC_0) != 0L ? 1L : 0L;
                                    if (isEqual != 0L) {
                                        putCCReal(framePtr + 48L, new CC_Real(1L, 0L, 0L));
                                    }
                                    Stack_SetX(framePtr + 48L);
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
                                    putI32(g_com_keyinmode, 0L);
                                }
                                result = 4L;
                                putU32(g_basic_displaymode, 1L);
                                putU8(g_com_eex_forbackspace, 0L);
                                putU16(g_reg_state, 1802L);
                            } else {
                                if (getI8(g_reg_state + 1L) != 1L) {
                                    result = 98L;
                                    putI32(g_com_keyinmode, 0L);
                                } else {
                                    result = 99L;
                                    putU16(g_reg_state, 1802L);
                                }
                            }
                            return result;
                        }
                        putU16(g_reg_state, (((getI8(g_reg_state + 1L) & 0xffL)) << 8) | ((opCode + getU8(g_reg_state) - 48L) & 0xffL));
                        if (getU32(g_basic_displaymode) == 0L) {
                            fStringToCC_er(g_basic_edit, framePtr + 48L, 0L, 1L, 46L, 44L);
                            isEqual = fEQ(framePtr + 48L, CC_0) != 0L ? 1L : 0L;
                            if (isEqual != 0L) {
                                putCCReal(framePtr + 48L, new CC_Real(1L, 0L, 0L));
                            }
                            Stack_SetX(framePtr + 48L);
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
                            putI32(g_com_keyinmode, 0L);
                        }
                        putU32(g_basic_displaymode, 1L);
                        putU8(g_com_eex_forbackspace, 0L);
                        putCCReal(framePtr + 48L, Stack_GetX());
                        switch ((int) (getU8(g_reg_state + 2L))) {
                            case 1: {
                                if (getU8(g_reg_state) < 5L) {
                                    tempVal = GetTempReg(getU8(g_reg_state), framePtr + 32L);
                                    if ((tempVal & 1L) == 0L) {
                                        step = 2;
                                        continue stepLoop;
                                    } else {
                                        isEqual = fradd(framePtr + 32L, framePtr + 48L, framePtr + 16L, 0L) != 0L ? 1L : 0L;
                                        result = 0L;
                                        if (isEqual < 2L) {
                                            step = 3;
                                            continue stepLoop;
                                        } else {
                                            step = 1;
                                            continue stepLoop;
                                        }
                                    }
                                } else {
                                    step = 5;
                                    continue stepLoop;
                                }
                            }
                            case 2: {
                                if (4L < getU8(g_reg_state)) {
                                    step = 5;
                                    continue stepLoop;
                                } else {
                                    tempVal = GetTempReg(getU8(g_reg_state), framePtr + 32L);
                                    if ((tempVal & 1L) == 0L) {
                                        step = 2;
                                        continue stepLoop;
                                    } else {
                                        flag = fradd(framePtr + 32L, framePtr + 48L, framePtr + 16L, 1L) != 0L ? 1L : 0L;
                                        step = 6;
                                        continue stepLoop;
                                    }
                                }
                            }
                            case 3: {
                                if (4L < getU8(g_reg_state)) {
                                    step = 5;
                                    continue stepLoop;
                                } else {
                                    tempVal = GetTempReg(getU8(g_reg_state), framePtr + 32L);
                                    if ((tempVal & 1L) != 0L) {
                                        status = fmul(framePtr + 32L, framePtr + 48L, framePtr + 16L) & 0xffffffffL;
                                        if (status < 2L) {
                                            step = 3;
                                            continue stepLoop;
                                        } else {
                                            step = 7;
                                            continue stepLoop;
                                        }
                                    } else {
                                        step = 2;
                                        continue stepLoop;
                                    }
                                }
                            }
                            case 4: {
                                if (4L < getU8(g_reg_state)) {
                                    step = 5;
                                    continue stepLoop;
                                } else {
                                    tempVal = GetTempReg(getU8(g_reg_state), framePtr + 32L);
                                    if ((tempVal & 1L) != 0L) {
                                        flag = fdiv(framePtr + 32L, framePtr + 48L, framePtr + 16L) & 0xffL;
                                        step = 6;
                                        continue stepLoop;
                                    } else {
                                        step = 2;
                                        continue stepLoop;
                                    }
                                }
                            }
                            default: {
                                tempVal = SetTempReg(getU8(g_reg_state), framePtr + 48L);
                                if ((tempVal & 1L) == 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    step = 4;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 1: {
                        if (getI32(g_com_operationmode) == 0L) {
                            putI32(g_com_keyinmode, 0L);
                            if (getU32(g_basic_op) == 99L) {
                                putI32(g_result_state, 0L);
                                putI64(g_basic_lastx + 16L, 1L);
                            }
                        } else {
                            if (getI32(g_com_operationmode) == 1L) {
                                putI32(g_com_keyinmode, 0L);
                                putI32(g_result_state, 0L);
                            }
                        }
                        return result;
                    }
                    case 2: {
                        result = 6L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 3: {
                        isEqual = BF123_IsValidRealData(framePtr + 16L) != 0L ? 1L : 0L;
                        if (isEqual != 0L) {
                            tempVal = SetTempReg(getU8(g_reg_state), framePtr + 16L);
                            if ((tempVal & 1L) != 0L) {
                                step = 4;
                                continue stepLoop;
                            } else {
                                step = 2;
                                continue stepLoop;
                            }
                        } else {
                            result = 1L;
                            step = 1;
                            continue stepLoop;
                        }
                    }
                    case 4: {
                        Com_SetResult(framePtr + 48L, regIndex & 0xffL);
                        result = 99L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 5: {
                        result = 4L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 6: {
                        if (flag < 2L) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            step = 7;
                            continue stepLoop;
                        }
                    }
                    case 7: {
                        result = 0L;
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

    long Basic_RclProcess(long opCode, long regIndex) {
        long stackDepth = 0L;
        long tempVal = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            if ((byte) opCode == 46L) {
                if (getI8(g_reg_state + 1L) != 2L) {
                    result = 98L;
                    putI32(g_com_keyinmode, 0L);
                    return result;
                }
                putU16(g_reg_state, 1802L);
                result = 99L;
                return result;
            }
            putU16(g_reg_state, (((getI8(g_reg_state + 1L) & 0xffL)) << 8) | ((opCode + getU8(g_reg_state) - 48L) & 0xffL));
            Com_EndEditMode_CareOP();
            tempVal = GetTempReg(getU8(g_reg_state), framePtr + 24L);
            if ((tempVal & 1L) == 0L) {
                result = 6L;
                return result;
            }
            if (getI32(g_com_operationmode) == 1L) {
                if (getI32(g_result_state) == 0L) {
                    Stack_InsertToX(framePtr + 24L);
                } else {
                    Stack_SetX(framePtr + 24L);
                }
                putI32(g_result_state, 0L);
                putI32(g_com_keyinmode, 0L);
            }
            if (getI32(g_com_operationmode) != 0L) {
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, regIndex & 0xffL);
                result = 99L;
                return result;
            }
            if (getU32(g_expression_state) == 2L) {
                Stack_PushXToX1();
            } else {
                if (getU32(g_expression_state) == 1L) {
                    stackDepth = (long) (byte) Stack_GetRegistersDepth();
                    if ((byte) stackDepth == 0L) {
                        if (getI32(g_result_state) == 0L) {
                            Stack_InsertToX(framePtr + 24L);
                        } else {
                            Stack_SetX(framePtr + 24L);
                        }
                        putI32(g_result_state, 0L);
                        putI32(g_com_keyinmode, 0L);
                        putCCReal(framePtr + 24L, Stack_GetX());
                        Com_SetResult(framePtr + 24L, regIndex & 0xffL);
                        result = 99L;
                        return result;
                    }
                    Stack_SetX(framePtr + 24L);
                    putI32(g_result_state, 1L);
                    putI32(g_com_keyinmode, 0L);
                    putCCReal(framePtr + 24L, Stack_GetX());
                    Com_SetResult(framePtr + 24L, regIndex & 0xffL);
                    result = 99L;
                    return result;
                }
                if (getU32(g_expression_state) != 0L) {
                    Stack_SetX(framePtr + 24L);
                    putI32(g_result_state, 1L);
                    putI32(g_com_keyinmode, 0L);
                    putCCReal(framePtr + 24L, Stack_GetX());
                    Com_SetResult(framePtr + 24L, regIndex & 0xffL);
                    result = 99L;
                    return result;
                }
            }
            Stack_SetX(framePtr + 24L);
            putI32(g_result_state, 1L);
            if (getI32(g_com_operationmode) == 0L) {
                if (getU32(g_basic_displaymode) != 0L) {
                    putU32(g_expression_state, getU32(ADDR_RODATA + 7596L + getU32(g_expression_state) * 16L));
                }
            } else {
                if (getI32(g_com_operationmode) == 1L) {
                    putU32(g_expression_state, 1L);
                }
            }
            putI32(g_com_keyinmode, 0L);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, regIndex & 0xffL);
            result = 99L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Basic_GtoProcess(long stepNumber) {
        long isEqual = 0L;
        long intVal = 0L;
        long result = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            if ((byte) stepNumber != 46L) {
                tempVal = getU8(g_gto_state + 4L);
                putU8(g_gto_state + 4L, getU8(g_gto_state + 4L) + 1L);
                putI8(g_gto_state + tempVal, stepNumber);
            }
            if (!((getI32(g_com_machine_mode) & 0xfffffffdL) == 0L && getU8(g_gto_state + 4L) == 3L || comma(result = 99L, getI32(g_com_machine_mode) == 1L && getU8(g_gto_state + 4L) == 2L ? 1L : 0L) != 0L)) {
                return result;
            }
            putU8(g_gto_state + getU8(g_gto_state + 4L), 0L);
            if (getU32(g_basic_displaymode) != 0L) {
                putU32(g_basic_displaymode, 1L);
                putI32(g_com_keyinmode, 0L);
                putU8(g_com_eex_forbackspace, 0L);
                intVal = (long) (int) atoi(g_gto_state);
                result = Prog_SetGTOVal(intVal & 0xffffL);
                return result;
            }
            fStringToCC_er(g_basic_edit, framePtr + 16L, 0L, 1L, 46L, 44L);
            isEqual = fEQ(framePtr + 16L, CC_0) != 0L ? 1L : 0L;
            if (isEqual != 0L) {
                putI64(framePtr + 24L, 0L);
                putI32(framePtr + 16L, 1L);
                putI32(framePtr + 20L, 0L);
            }
            Stack_SetX(framePtr + 16L);
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
            putU32(g_basic_displaymode, 1L);
            putI32(g_com_keyinmode, 0L);
            putU8(g_com_eex_forbackspace, 0L);
            intVal = (long) (int) atoi(g_gto_state);
            result = Prog_SetGTOVal(intVal & 0xffffL);
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Basic_EexNumKeyProcess(long digitChar, long shouldPrint) {
        if (((digitChar - 48L) & 0xffL) < 10L) {
            putI8(g_basic_edit + 14L, getI8(g_basic_edit + 15L));
            putI8(g_basic_edit + 15L, digitChar);
        }
        Basic_ProcessEditBuffer(shouldPrint & 0xffL);
        return 99L;
    }

    long Basic_NormalNumKeyProcess(long digitChar, long shouldPrint) {
        long stackDepth = 0L;
        long intVal = 0L;
        long offset = 0L;
        long strPtr = 0L;
        long dataPtr = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    intVal = getI32(g_com_operationmode);
                    if (getI32(g_com_operationmode) == 1L) {
                        if (getI32(g_result_state) == 0L) {
                            intVal = 1L;
                            if (getU32(g_basic_displaymode) == 1L) {
                                Stack_ShiftUpOneStep();
                                intVal = getI32(g_com_operationmode);
                            }
                        } else {
                            intVal = 1L;
                        }
                        putI32(g_result_state, 1L);
                    }
                    if (intVal != 0L) {
                        step = 1;
                        continue stepLoop;
                    } else {
                        dataPtr = g_expression_state;
                        if (getU32(g_expression_state) == 2L) {
                            Stack_PushXToX1();
                            if (getI32(g_com_operationmode) == 0L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                offset = 1L;
                                if (getI32(g_com_operationmode) == 1L) {
                                    putU32(dataPtr, offset);
                                }
                                step = 1;
                                continue stepLoop;
                            }
                        } else {
                            if (getU32(g_expression_state) == 1L) {
                                stackDepth = (long) (byte) Stack_GetRegistersDepth();
                                if (!((byte) stackDepth != 0L || comma(dataPtr = g_result_state, getI32(g_result_state) != 0L ? 1L : 0L) != 0L || getU32(g_basic_displaymode) != 1L)) {
                                    putCCReal(g_basic_lastx, Stack_GetX());
                                    putI64(g_basic_lastx + 16L, 0L);
                                    Stack_ShiftUpOneStep();
                                    offset = 1L;
                                    putU32(dataPtr, offset);
                                }
                                step = 1;
                                continue stepLoop;
                            } else {
                                if (getU32(g_expression_state) != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    if (getU32(g_basic_displaymode) != 0L) {
                                        offset = 1L;
                                        putU32(dataPtr, offset);
                                    }
                                    step = 1;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                }
                case 1: {
                    strPtr = g_basic_edit + 1L + getU8(g_basic_edit + 17L);
                    if (getI8(strPtr) == 46L) {
                        if (getU8(g_basic_edit + 17L) == 10L) {
                            return 99L;
                        }
                        if ((byte) digitChar == 46L) {
                            if (getU8(g_basic_edit + 17L) == 0L) {
                                putU8(g_basic_edit + 17L, 1L);
                                putI64(g_basic_edit + 1L, 11824L);
                            }
                        } else {
                            putU8(g_basic_edit + 1L + getU8(g_basic_edit + 17L) + 1L, 46L);
                            putI8(g_basic_edit + 1L + getU8(g_basic_edit + 17L), digitChar);
                        }
                    } else {
                        if ((byte) digitChar == 46L) {
                            return 99L;
                        }
                        if (getU8(g_basic_edit + 17L) == 11L) {
                            return 99L;
                        }
                        putI8(strPtr, digitChar);
                    }
                    putU8(g_basic_edit + 17L, getU8(g_basic_edit + 17L) + 1L);
                    Basic_ProcessEditBuffer(shouldPrint & 0xffL);
                    return 99L;
                }
                case 2: {
                    if (getU32(g_basic_displaymode) != 0L) {
                        offset = getU32(ADDR_RODATA + 7596L + getU32(g_expression_state) * 16L);
                        putU32(dataPtr, offset);
                    }
                    step = 1;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long Basic_OperateKeyEntry(long opCode) {
        long offset = 0L;
        long stackDepth = 0L;
        long status = 0L;
        long result = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if (getI32(g_com_operationmode) == 1L) {
                        putU32(g_basic_op, opCode);
                        status = Basic_Caculate();
                        result = (long) (int) status;
                    } else {
                        result = 99L;
                    }
                    offset = getU32(g_expression_state);
                    if (getI32(g_com_operationmode) != 0L) {
                        putU32(g_expression_state, offset);
                        return result;
                    }
                    if (getU32(g_expression_state) == 3L) {
                        status = Basic_Caculate();
                        result = (long) (int) status;
                        if (result != 99L) {
                            return result;
                        }
                        step = 1;
                        continue stepLoop;
                    } else {
                        if (getU32(g_expression_state) == 0L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            step = 1;
                            continue stepLoop;
                        }
                    }
                }
                case 1: {
                    if (getI32(g_result_state) == 0L) {
                        putI32(g_result_state, 1L);
                    }
                    if (getI32(g_com_operationmode) == 0L) {
                        putU32(g_basic_op, opCode);
                        offset = getU32(ADDR_RODATA + 7600L + getU32(g_expression_state) * 16L);
                    } else {
                        putU32(g_basic_op, opCode);
                        offset = 1L;
                        if (getI32(g_com_operationmode) != 1L) {
                            return result;
                        }
                    }
                    putU32(g_expression_state, offset);
                    return result;
                }
                case 2: {
                    stackDepth = (long) (byte) Stack_GetRegistersDepth();
                    if ((byte) stackDepth == 0L) {
                        step = 3;
                        continue stepLoop;
                    } else {
                        Stack_PopRegisters();
                        if (getU32(g_basic_op) == 99L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            step = 3;
                            continue stepLoop;
                        }
                    }
                }
                case 3: {
                    stackDepth = (long) (byte) Stack_GetRegistersDepth();
                    if ((byte) stackDepth == 0L) {
                        LcdDriver_ClearAnnunciator(4L);
                        putU8(g_com_bracketmode, 0L);
                    }
                    step = 1;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long Basic_Caculate() {
        long status = 0L;
        long result = 0L;
        if (getI32(g_com_operationmode) == 1L) {
            result = 99L;
            if (getU32(g_basic_op) < 5L) {
                status = Basic_CaculateWithTwoParams(getU32(g_basic_op)) & 0xffffffffL;
                result = status;
            }
            putU32(g_basic_op, 99L);
        } else {
            result = 0L;
        }
        if (getI32(g_com_operationmode) != 0L) {
            return result;
        }
        if (getU32(g_expression_state) < 2L) {
            result = 99L;
        } else {
            if (getU32(g_expression_state) == 2L) {
                result = 99L;
                putU32(g_basic_op, 99L);
            } else {
                result = 99L;
                if (getU32(g_basic_op) < 5L) {
                    status = Basic_CaculateWithTwoParams(getU32(g_basic_op)) & 0xffffffffL;
                    result = status;
                }
                putU32(g_basic_op, 99L);
            }
        }
        return result;
    }

    long Basic_GetExpressState() {
        long result = 0L;
        result = getU32(g_expression_state);
        if (getI32(g_com_operationmode) != 0L) {
            result = 1L;
        }
        return result;
    }

    long Basic_DiscardDuplicateBracket() {
        long stackDepth = 0L;
        long flag = 0L;
        long annunciator = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    step = 1;
                    continue stepLoop;
                }
                case 1: {
                    stackDepth = (long) (byte) Stack_GetRegistersDepth();
                    if ((byte) stackDepth == 0L) {
                        step = 2;
                        continue stepLoop;
                    } else {
                        Stack_PopRegisters();
                        if (getU32(g_basic_op) == 99L) {
                            step = 1;
                            continue stepLoop;
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                }
                case 2: {
                    flag = Stack_GetRegistersDepth() & 0xffL;
                    annunciator = flag;
                    if (flag == 0L) {
                        annunciator = LcdDriver_ClearAnnunciator(4L);
                        putU8(g_com_bracketmode, 0L);
                    }
                    return annunciator;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    void Basic_ChangeExpressState(long newState) {
        if (getI32(g_com_operationmode) == 0L) {
            if (getU32(g_basic_displaymode) != 0L || (newState & 0xffffffffL) != 0L) {
                putU32(g_expression_state, getU32(ADDR_RODATA + 7596L + (newState & 0xffffffffL) * 4L + getU32(g_expression_state) * 16L));
            }
        } else {
            if (getI32(g_com_operationmode) == 1L) {
                putU32(g_expression_state, 1L);
            }
        }
    }

    long Basic_PrintResult(long realPtr) {
        Basic_PrintReal(realPtr);
        putU32(g_basic_displaymode, 1L);
        putI32(g_com_keyinmode, 0L);
        putU8(g_com_eex_forbackspace, 0L);
        return 0L;
    }

    long Basic_PrintReal(long realPtr) {
        long bytePtr1 = 0L;
        long isZero = 0L;
        long strLen1 = 0L;
        long strLen2 = 0L;
        long flag = 0L;
        long tempVal1 = 0L;
        long strLen3 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        long bytePtr2 = 0L;
        long index = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        Basic_ReProductReal(realPtr, framePtr + 16L);
                        putU32(framePtr + 64L, 0x303030L);
                        putU8(framePtr + 56L, 48L);
                        putU8(framePtr + 57L, 48L);
                        putU8(framePtr + 58L, 48L);
                        putU8(framePtr + 59L, 48L);
                        putU8(framePtr + 60L, 69L);
                        putU8(framePtr + 61L, 43L);
                        putU8(framePtr + 62L, 48L);
                        putU8(framePtr + 63L, 48L);
                        putU8(framePtr + 48L, 43L);
                        putU8(framePtr + 49L, 48L);
                        putU8(framePtr + 50L, 46L);
                        putU8(framePtr + 51L, 48L);
                        putU8(framePtr + 52L, 48L);
                        putU8(framePtr + 53L, 48L);
                        putU8(framePtr + 54L, 48L);
                        putU8(framePtr + 55L, 48L);
                        if (!(getI32(framePtr + 20L) < 10L && -10L < getI32(framePtr + 20L) && getI8(g_com_resultstate + 1L) == 0L)) {
                            tempVal1 = 1L;
                            if (getI8(g_com_resultstate + 1L) != 0L && getU8(g_com_resultstate) == 10L && -11L < getI32(framePtr + 20L)) {
                                tempVal1 = 2L;
                            }
                            fCCToString(framePtr + 16L, framePtr + 48L, 46L, 44L, tempVal1, 10L, 10L, 0L);
                            putU32(framePtr + 64L, getU32(framePtr + 64L) & 0xffffffL);
                            if (getU8(framePtr + 48L) == 45L) {
                                LcdDriver_SetAnnunciator(0L);
                                bytePtr2 = (framePtr + 48L) | 1L;
                            } else {
                                bytePtr2 = framePtr + 48L;
                                LcdDriver_ClearAnnunciator(0L);
                            }
                            if (getU8(bytePtr2) == 69L) {
                                strLen3 = 0L;
                            } else {
                                putU8(framePtr + 32L, getU8(bytePtr2));
                                if (getU8(bytePtr2 + 1L) == 69L) {
                                    strLen3 = 1L;
                                } else {
                                    putU8(framePtr + 33L, getU8(bytePtr2 + 1L));
                                    if (getU8(bytePtr2 + 2L) == 69L) {
                                        strLen3 = 2L;
                                    } else {
                                        putU8(framePtr + 34L, getU8(bytePtr2 + 2L));
                                        if (getU8(bytePtr2 + 3L) == 69L) {
                                            strLen3 = 3L;
                                        } else {
                                            putU8(framePtr + 35L, getU8(bytePtr2 + 3L));
                                            if (getU8(bytePtr2 + 4L) == 69L) {
                                                strLen3 = 4L;
                                            } else {
                                                putU8(framePtr + 36L, getU8(bytePtr2 + 4L));
                                                if (getU8(bytePtr2 + 5L) == 69L) {
                                                    strLen3 = 5L;
                                                } else {
                                                    putU8(framePtr + 37L, getU8(bytePtr2 + 5L));
                                                    if (getU8(bytePtr2 + 6L) == 69L) {
                                                        strLen3 = 6L;
                                                    } else {
                                                        putU8(framePtr + 38L, getU8(bytePtr2 + 6L));
                                                        if (getU8(bytePtr2 + 7L) == 69L) {
                                                            strLen3 = 7L;
                                                        } else {
                                                            putU8(framePtr + 39L, getU8(bytePtr2 + 7L));
                                                            if (getU8(bytePtr2 + 8L) == 69L) {
                                                                strLen3 = 8L;
                                                            } else {
                                                                flag = getU8(bytePtr2 + 9L);
                                                                putU8(framePtr + 40L, getU8(bytePtr2 + 8L));
                                                                if (flag == 69L) {
                                                                    strLen3 = 9L;
                                                                } else {
                                                                    putU8(framePtr + 41L, flag);
                                                                    if (getU8(bytePtr2 + 10L) == 69L) {
                                                                        strLen3 = 10L;
                                                                    } else {
                                                                        strLen3 = 11L;
                                                                        putU8(framePtr + 42L, getU8(bytePtr2 + 10L));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            putU8(framePtr + 32L + strLen3, 0L);
                            bytePtr1 = bytePtr2 + strLen3 + 1L;
                            strLen1 = strlen(framePtr + 32L);
                            if (strLen1 != 0L && getU32(g_com_decimal_mode) == 1L) {
                                index = 0L;
                                do {
                                    if (getU8(framePtr + 32L + index) == 46L) {
                                        flag = 44L;
                                        putU8(framePtr + 32L + index, flag);
                                    } else {
                                        if (getU8(framePtr + 32L + index) == 44L) {
                                            flag = 46L;
                                            putU8(framePtr + 32L + index, flag);
                                        }
                                    }
                                    index = (index + 1L) & 0xffL;
                                    strLen2 = strlen(framePtr + 32L);
                                } while (Long.compareUnsigned(index, strLen2) < 0L && getU32(g_com_decimal_mode) == 1L);
                            }
                            LcdDriver_PrintCalculatorLine(framePtr + 32L, 0L);
                            if (getU8(bytePtr1) == 45L) {
                                LcdDriver_PutChar(45L, 7L);
                                if (getU8(bytePtr2 + strLen3 + 3L) == 0L) {
                                    flag = 48L;
                                    LcdDriver_PutChar(flag, 8L);
                                    flag = getU8(bytePtr2 + strLen3 + 2L);
                                } else {
                                    LcdDriver_PutChar(getU8(bytePtr2 + strLen3 + 2L), 8L);
                                    flag = getU8(bytePtr2 + strLen3 + 3L);
                                }
                            } else {
                                LcdDriver_PutChar(32L, 7L);
                                if (getU8(bytePtr2 + strLen3 + 2L) == 0L) {
                                    LcdDriver_PutChar(48L, 8L);
                                    flag = getU8(bytePtr1);
                                } else {
                                    isZero = fZero(framePtr + 16L) != 0L ? 1L : 0L;
                                    if (isZero == 0L) {
                                        flag = getU8(bytePtr1);
                                        LcdDriver_PutChar(flag, 8L);
                                        flag = getU8(bytePtr2 + strLen3 + 2L);
                                    } else {
                                        LcdDriver_PutChar(48L, 8L);
                                        flag = 48L;
                                    }
                                }
                            }
                            LcdDriver_PutChar(flag, 9L);
                            return 0L;
                        }
                        if (-1L < getI32(framePtr + 20L) || comma(tempVal3 = -getI32(framePtr + 20L) & 0xffffffffL, (long) (int) tempVal3 <= getU8(g_com_resultstate) ? 1L : 0L) != 0L) {
                            tempVal3 = getU8(g_com_resultstate);
                        }
                        fCCToString(framePtr + 16L, framePtr + 48L, 46L, 44L, 1L, (long) (int) tempVal3, 10L, 0L);
                        putU32(framePtr + 64L, getU32(framePtr + 64L) & 0xffffffL);
                        if (getU8(framePtr + 48L) == 45L) {
                            LcdDriver_SetAnnunciator(0L);
                        } else {
                            LcdDriver_ClearAnnunciator(0L);
                        }
                        bytePtr2 = (framePtr + 48L) | 1L;
                        if (getI32(framePtr + 16L) != -1L) {
                            bytePtr2 = framePtr + 48L;
                        }
                        tempVal4 = getI64(bytePtr2);
                        tempVal2 = getI64(bytePtr2 + 5L);
                        putU8(framePtr + 32L, tempVal4);
                        putU8(framePtr + 33L, tempVal4 >>> 8);
                        putU8(framePtr + 34L, tempVal4 >>> 16);
                        putU8(framePtr + 35L, tempVal4 >>> 24);
                        putU8(framePtr + 36L, tempVal4 >>> 32);
                        putU8(framePtr + 37L, tempVal2);
                        putU8(framePtr + 38L, tempVal2 >>> 8);
                        putU8(framePtr + 39L, tempVal2 >>> 16);
                        putU8(framePtr + 40L, tempVal2 >>> 24);
                        putU8(framePtr + 41L, tempVal2 >>> 32);
                        putU8(framePtr + 42L, tempVal2 >>> 40);
                        putU16(framePtr + 43L, tempVal2 >>> 48);
                        if (getI32(framePtr + 20L) == 9L) {
                            putU16(framePtr + 45L, 46L);
                        } else {
                            putU16(framePtr + 45L, getU8(framePtr + 46L) << 8);
                        }
                        strLen3 = strlen(framePtr + 32L);
                        if (!(strLen3 != 0L && getU32(g_com_decimal_mode) == 1L)) {
                            LcdDriver_PrintCalculatorLine(framePtr + 32L, 0L);
                            return 0L;
                        }
                        index = 0L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 1: {
                        if (getU8(framePtr + 32L + index) == 46L) {
                            flag = 44L;
                            putU8(framePtr + 32L + index, flag);
                        } else {
                            if (getU8(framePtr + 32L + index) == 44L) {
                                flag = 46L;
                                putU8(framePtr + 32L + index, flag);
                            }
                        }
                        index = (index + 1L) & 0xffL;
                        strLen2 = strlen(framePtr + 32L);
                        if (!(Long.compareUnsigned(index, strLen2) < 0L && getU32(g_com_decimal_mode) == 1L)) {
                            LcdDriver_PrintCalculatorLine(framePtr + 32L, 0L);
                            return 0L;
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

    long Basic_ReProductReal(long srcRealPtr, long dstRealPtr) {
        long intVal = 0L;
        long exponent = 0L;
        long dataPtr = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            exponent = getI32(srcRealPtr + 4L);
            tempVal2 = getU8(g_com_resultstate);
            intVal = (long) (int) (exponent + getU8(g_com_resultstate));
            if (intVal >= 10L) {
                if (8L < exponent) {
                    tempVal2 = 10L;
                    tempVal1 = 1L;
                    dataPtr = firnd(srcRealPtr, tempVal2, dstRealPtr, tempVal1);
                    return dataPtr;
                }
                tempVal2 = (9L - exponent) & 0xffL;
                tempVal1 = 0L;
                dataPtr = firnd(srcRealPtr, tempVal2, dstRealPtr, tempVal1);
                return dataPtr;
            }
            if (-1L < intVal) {
                if (tempVal2 == 10L) {
                    tempVal2 = 10L;
                    tempVal1 = 1L;
                    dataPtr = firnd(srcRealPtr, tempVal2, dstRealPtr, tempVal1);
                    return dataPtr;
                }
                tempVal1 = 0L;
                dataPtr = firnd(srcRealPtr, tempVal2, dstRealPtr, tempVal1);
                return dataPtr;
            }
            tempVal3 = getI32(srcRealPtr) == -1L ? 1L : 0L;
            if (exponent == -1L && getU8(g_com_resultstate) == 0L) {
                fCCToString(srcRealPtr, framePtr + 16L, 46L, 44L, 0L, 0L, 10L, 0L);
                tempVal2 = getU8((framePtr + 16L) | tempVal3) < 53L ? 1L : 0L;
            } else {
                tempVal2 = 10L;
                if (exponent != -10L) {
                    if (-11L < exponent) {
                        fCCToString(srcRealPtr, framePtr + 16L, 46L, 44L, 0L, 0L, 10L, 0L);
                        if (52L < getU8((framePtr + 16L) | tempVal3)) {
                            dataPtr = fIntToCC(1L, dstRealPtr);
                            putI32(dstRealPtr + 4L, getI32(srcRealPtr + 4L) + 1L);
                            putI32(dstRealPtr, getI32(srcRealPtr));
                            return dataPtr;
                        }
                        tempVal2 = 1L;
                    }
                    tempVal1 = 1L;
                    dataPtr = firnd(srcRealPtr, tempVal2, dstRealPtr, tempVal1);
                    return dataPtr;
                }
                fCCToString(srcRealPtr, framePtr + 16L, 46L, 44L, 0L, 0L, 10L, 0L);
                if (52L >= getU8((framePtr + 16L) | tempVal3)) {
                    tempVal1 = 1L;
                    dataPtr = firnd(srcRealPtr, tempVal2, dstRealPtr, tempVal1);
                    return dataPtr;
                }
                tempVal2 = 9L;
            }
            tempVal1 = 0L;
            dataPtr = firnd(srcRealPtr, tempVal2, dstRealPtr, tempVal1);
            return dataPtr;
        } finally {
            sp = savedSp;
        }
    }

    void Basic_PrintLcdLine(long strPtr) {
        long strLen = 0L;
        long byteVal = 0L;
        long index = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if (!(getI8(strPtr) != 0L && getU32(g_com_decimal_mode) == 1L)) {
                        LcdDriver_PrintCalculatorLine(strPtr, 0L);
                        return;
                    }
                    index = 0L;
                    step = 1;
                    continue stepLoop;
                }
                case 1: {
                    if (getI8(strPtr + index) == 46L) {
                        byteVal = 44L;
                        putI8(strPtr + index, byteVal);
                    } else {
                        if (getI8(strPtr + index) == 44L) {
                            byteVal = 46L;
                            putI8(strPtr + index, byteVal);
                        }
                    }
                    index = (index + 1L) & 0xffL;
                    strLen = strlen(strPtr);
                    if (!(Long.compareUnsigned(index, strLen) < 0L && getU32(g_com_decimal_mode) == 1L)) {
                        LcdDriver_PrintCalculatorLine(strPtr, 0L);
                        return;
                    }
                    step = 1;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long Basic_PrintEditLine(long editBufPtr) {
        long tempVal1 = 0L;
        long index1 = 0L;
        long flag = 0L;
        long tempVal2 = 0L;
        long strLen1 = 0L;
        long byteVal = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        long index2 = 0L;
        long strLen2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            if ((editBufPtr & 0xffL) == 0L) {
                return 0L;
            }
            if (getI8(g_basic_edit) == 45L) {
                LcdDriver_SetAnnunciator(0L);
            } else {
                LcdDriver_ClearAnnunciator(0L);
            }
            index2 = 0L;
            tempVal4 = (getU8(g_basic_edit + 17L) - (getI8(g_basic_edit + 1L + getU8(g_basic_edit + 17L)) != 46L ? 1L : 0L)) & 0xffffffffL;
            index1 = 7L;
            if ((tempVal4 & 0xffL) < 8L || getI32(g_com_keyinmode) != 1L) {
                index1 = tempVal4;
            }
            flag = index1 & 0xffL;
            tempVal4 = 0L;
            do {
                strLen2 = index2 & 0xffL;
                byteVal = getI8(g_basic_edit + 1L + strLen2);
                tempVal2 = index2 & 0xffffffffL;
                if ((byte) byteVal != 46L) {
                    tempVal2 = tempVal4;
                }
                tempVal1 = (index2 + 1L) & 0xffffffffL;
                index2 = tempVal1;
                putI8(framePtr + 24L + strLen2, byteVal);
                tempVal4 = tempVal2;
            } while ((tempVal1 & 0xffL) <= (index1 & 0xffL));
            while (3L < (tempVal2 & 0xffL)) {
                tempVal2 = (tempVal2 - 3L) & 0xffffffffL;
                index1 = index1 & 0xffL;
                strLen2 = index1;
                index2 = strLen2;
                if ((tempVal2 & 0xffL) <= index1) {
                    do {
                        tempVal4 = (index2 - 1L) & 0xffL;
                        strLen2 = tempVal4;
                        putI8(framePtr + 24L + index2 + 1L, getI8(framePtr + 24L + index2));
                        index2 = strLen2;
                    } while ((tempVal2 & 0xffL) <= tempVal4);
                }
                index1 = (index1 + 1L) & 0xffffffffL;
                flag = index1 & 0xffL;
                putI8(framePtr + 24L + strLen2 + 1L, 44L);
            }
            putI8(framePtr + 24L + flag + 1L, 0L);
            strLen1 = strlen(framePtr + 24L);
            if (strLen1 != 0L && getU32(g_com_decimal_mode) == 1L) {
                index2 = 0L;
                do {
                    if (getI8(framePtr + 24L + index2) == 46L) {
                        byteVal = 44L;
                        putI8(framePtr + 24L + index2, byteVal);
                    } else {
                        if (getI8(framePtr + 24L + index2) == 44L) {
                            byteVal = 46L;
                            putI8(framePtr + 24L + index2, byteVal);
                        }
                    }
                    index2 = (index2 + 1L) & 0xffL;
                    strLen2 = strlen(framePtr + 24L);
                } while (Long.compareUnsigned(index2, strLen2) < 0L && getU32(g_com_decimal_mode) == 1L);
            }
            LcdDriver_PrintCalculatorLine(framePtr + 24L, 0L);
            if (getI32(g_com_keyinmode) != 1L) {
                putU32(g_basic_displaymode, 0L);
                return 0L;
            }
            tempVal3 = 32L;
            if (getI8(g_basic_edit + 13L) != 43L) {
                tempVal3 = 45L;
            }
            LcdDriver_PutChar(tempVal3, 7L);
            LcdDriver_PutChar(getI8(g_basic_edit + 14L) & 0xffL, 8L);
            LcdDriver_PutChar(getI8(g_basic_edit + 15L) & 0xffL, 9L);
            putU32(g_basic_displaymode, 0L);
            return 0L;
        } finally {
            sp = savedSp;
        }
    }

    long Basic_ProcessEditBuffer(long outRealPtr) {
        long isEqual = 0L;
        long errCode = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            putU32(g_basic_displaymode, 0L);
            fStringToCC_er(g_basic_edit, framePtr + 16L, 0L, 1L, 46L, 44L);
            errCode = Basic_CheckRealData(framePtr + 16L);
            if ((errCode & 1L) == 0L) {
                Stack_SetX(framePtr + 16L);
                errCode = Basic_PrintEditLine(outRealPtr & 0xffL);
            } else {
                if (getU32(g_basic_displaymode) == 0L) {
                    fStringToCC_er(g_basic_edit, framePtr + 32L, 0L, 1L, 46L, 44L);
                    isEqual = fEQ(framePtr + 32L, CC_0) != 0L ? 1L : 0L;
                    if (isEqual != 0L) {
                        putI64(framePtr + 40L, 0L);
                        putI32(framePtr + 32L, 1L);
                        putI32(framePtr + 36L, 0L);
                    }
                    Stack_SetX(framePtr + 32L);
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
                    putI32(g_com_keyinmode, 0L);
                }
                putU32(g_basic_displaymode, 1L);
                putU8(g_com_eex_forbackspace, 0L);
                Stack_SetX(framePtr + 16L);
                putI32(g_result_state, 0L);
                errCode = Com_SetResult(framePtr + 16L, outRealPtr & 0xffL);
            }
            return errCode;
        } finally {
            sp = savedSp;
        }
    }

    long Basic_CheckRealData(long realPtr) {
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

    void Basic_SetAccurate(long roundDigits) {
        putU8(g_com_resultstate, roundDigits);
        if (9L < (roundDigits & 0xffL)) {
            putU8(g_com_resultstate, 10L);
        }
        putI8(g_com_resultstate + 1L, 9L < (roundDigits & 0xffL) ? 1L : 0L);
    }

    long Basic_CaculateWithTwoParams(long opCode) {
        long flag1 = 0L;
        long flag2 = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            if (getI32(g_com_operationmode) == 1L) {
                putCCReal(framePtr + 56L, Stack_GetX());
                putCCReal(framePtr + 40L, Stack_GetY());
            }
            if (getI32(g_com_operationmode) == 0L) {
                putCCReal(framePtr + 56L, Stack_GetX());
                putCCReal(framePtr + 40L, Stack_GetX1());
            }
            if ((opCode & 0xffffffffL) < 5L) {
                switch ((int) (opCode)) {
                    case 0: {
                        flag1 = fdiv(framePtr + 40L, framePtr + 56L, framePtr + 24L) & 0xffL;
                        status1 = flag1;
                        break;
                    }
                    case 1: {
                        status1 = fmul(framePtr + 40L, framePtr + 56L, framePtr + 24L) & 0xffffffffL;
                        break;
                    }
                    case 2: {
                        flag2 = fradd(framePtr + 40L, framePtr + 56L, framePtr + 24L, 1L) != 0L ? 1L : 0L;
                        status1 = flag2;
                        break;
                    }
                    case 3: {
                        flag2 = fradd(framePtr + 40L, framePtr + 56L, framePtr + 24L, 0L) != 0L ? 1L : 0L;
                        status1 = flag2;
                        break;
                    }
                    case 4: {
                        status2 = fpow(framePtr + 40L, framePtr + 56L, framePtr + 24L, 3L);
                        status1 = status2 & 0xffffffffL;
                        break;
                    }
                    default: {
                        break;
                    }
                }
                if (1L < status1) {
                    BF123_CheckRealData(framePtr + 24L);
                    if (((status1 - 4L) & 0xffffffffL) < 16L || status1 == 56L || status1 == 24L) {
                        result = 0L;
                    } else {
                        result = 99L;
                    }
                    Stack_EmptyRegisters();
                    if (getI32(g_com_operationmode) == 0L) {
                        putU32(g_expression_state, 1L);
                    }
                    return result;
                }
            }
            if (getI32(g_com_operationmode) == 1L) {
                putCCReal(g_basic_lastx, Stack_GetX());
                Stack_ShiftDownOneStep();
                putI32(g_result_state, 0L);
            }
            Stack_SetX(framePtr + 24L);
            result = 99L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Basic_CaculateWithBracket() {
        long stackDepth = 0L;
        long status = 0L;
        while (true) {
            stackDepth = (long) (byte) Stack_GetRegistersDepth();
            status = Basic_Caculate();
            if ((byte) stackDepth == 0L) {
                break;
            }
            if ((long) (int) status != 99L) {
                return status & 0xffffffffL;
            }
            Stack_PopRegisters();
        }
        LcdDriver_ClearAnnunciator(4L);
        putU8(g_com_bracketmode, 0L);
        if (getI32(g_com_operationmode) == 0L) {
            putU32(g_expression_state, getU32(ADDR_RODATA + 7608L + getU32(g_expression_state) * 16L));
        } else {
            if (getI32(g_com_operationmode) == 1L) {
                putU32(g_expression_state, 1L);
            }
        }
        return status & 0xffffffffL;
    }

    long Basic_GetCalculateErr(long errCode, long resultRealPtr) {
        if ((errCode & 0xffffffffL) < 2L) {
            errCode = 0L;
        } else {
            BF123_CheckRealData(resultRealPtr);
        }
        return errCode & 0xffffffffL;
    }

    long ComErrorFromError(long mathErrCode) {
        if (15L < ((mathErrCode - 4L) & 0xffffffffL) && (long) (int) mathErrCode != 56L && (long) (int) mathErrCode != 24L) {
            return 99L;
        } else {
            return 0L;
        }
    }

    long Basic_CaculateWithOneParam(long opCode) {
        long flag = 0L;
        long isInt = 0L;
        long stackDepth = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        putCCReal(framePtr + 56L, Stack_GetX());
                        switch ((int) (opCode)) {
                            case 5: {
                                flag = fdiv(CC_1, framePtr + 56L, framePtr + 24L) & 0xffL;
                                result = flag;
                                step = 1;
                                continue stepLoop;
                            }
                            case 6: {
                                flag = fisqrt(framePtr + 56L, framePtr + 24L) & 0xffL;
                                result = flag;
                                step = 1;
                                continue stepLoop;
                            }
                            case 7: {
                                isInt = fexpm(framePtr + 56L, framePtr + 24L, 0L) != 0L ? 1L : 0L;
                                result = isInt;
                                step = 1;
                                continue stepLoop;
                            }
                            case 8: {
                                status2 = fln(framePtr + 56L, framePtr + 24L);
                                result = status2 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 9: {
                                status1 = fiFP(framePtr + 56L, framePtr + 24L);
                                result = status1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 10: {
                                status1 = fiIP(framePtr + 56L, framePtr + 24L);
                                result = status1 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 11: {
                                if (0L < getI32(framePtr + 56L) && (long) (int) comma(isInt = fiInteger(framePtr + 56L) != 0L ? 1L : 0L, isInt) != 0L) {
                                    status2 = fifact_L(framePtr + 56L, framePtr + 24L);
                                    result = status2 & 0xffffffffL;
                                    step = 1;
                                    continue stepLoop;
                                } else {
                                    BF123_CheckRealData(framePtr + 24L);
                                    result = 0L;
                                    step = 3;
                                    continue stepLoop;
                                }
                            }
                            case 12: {
                                fIntToCC(2L, framePtr + 40L);
                                status1 = fpow(framePtr + 56L, framePtr + 40L, framePtr + 24L, 3L);
                                result = status1 & 0xffffffffL;
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
                        if (1L < result) {
                            BF123_CheckRealData(framePtr + 24L);
                            if (((result - 4L) & 0xffffffffL) < 16L || result == 56L || result == 24L) {
                                result = 0L;
                            } else {
                                result = 99L;
                            }
                            step = 3;
                            continue stepLoop;
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 2: {
                        if (getI32(g_com_operationmode) == 1L) {
                            putCCReal(g_basic_lastx, Stack_GetX());
                            putI32(g_result_state, 0L);
                        }
                        if (getI32(g_com_operationmode) != 0L) {
                            Stack_SetX(framePtr + 24L);
                            result = 99L;
                            return result;
                        }
                        if (getU32(g_expression_state) == 2L || getU32(g_expression_state) == 1L) {
                            stackDepth = (long) (byte) Stack_GetRegistersDepth();
                            if ((byte) stackDepth == 0L) {
                                step = 4;
                                continue stepLoop;
                            } else {
                                step = 5;
                                continue stepLoop;
                            }
                        } else {
                            if (getU32(g_expression_state) != 0L) {
                                Stack_SetX(framePtr + 24L);
                                result = 99L;
                                return result;
                            }
                            step = 7;
                            continue stepLoop;
                        }
                    }
                    case 3: {
                        Stack_EmptyRegisters();
                        if (getI32(g_com_operationmode) == 0L) {
                            putU32(g_expression_state, 1L);
                        }
                        return result;
                    }
                    case 4: {
                        putU32(g_basic_op, 99L);
                        putI32(g_result_state, 0L);
                        putI64(g_basic_lastx + 16L, 1L);
                        if (getI32(g_com_operationmode) == 0L) {
                            putU32(g_expression_state, 1L);
                            Stack_SetX(framePtr + 24L);
                            result = 99L;
                            return result;
                        }
                        step = 6;
                        continue stepLoop;
                    }
                    case 5: {
                        if (getI32(g_com_operationmode) == 0L) {
                            putU32(g_basic_op, 99L);
                            putU32(g_expression_state, 1L);
                            Stack_SetX(framePtr + 24L);
                            result = 99L;
                            return result;
                        }
                        step = 6;
                        continue stepLoop;
                    }
                    case 6: {
                        putU32(g_basic_op, 99L);
                        if (getI32(g_com_operationmode) != 1L) {
                            Stack_SetX(framePtr + 24L);
                            result = 99L;
                            return result;
                        }
                        putU32(g_basic_op, 99L);
                        putU32(g_expression_state, 1L);
                        Stack_SetX(framePtr + 24L);
                        result = 99L;
                        return result;
                    }
                    case 7: {
                        stackDepth = (long) (byte) Stack_GetRegistersDepth();
                        if ((byte) stackDepth == 0L) {
                            step = 8;
                            continue stepLoop;
                        } else {
                            Stack_PopRegisters();
                            if (getU32(g_basic_op) == 99L) {
                                step = 7;
                                continue stepLoop;
                            } else {
                                step = 8;
                                continue stepLoop;
                            }
                        }
                    }
                    case 8: {
                        stackDepth = (long) (byte) Stack_GetRegistersDepth();
                        if ((byte) stackDepth == 0L) {
                            LcdDriver_ClearAnnunciator(4L);
                            putU8(g_com_bracketmode, 0L);
                        }
                        stackDepth = (long) (byte) Stack_GetRegistersDepth();
                        if ((byte) stackDepth != 0L) {
                            step = 5;
                            continue stepLoop;
                        } else {
                            step = 4;
                            continue stepLoop;
                        }
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Basic_EndCaculateNumBefore() {
        long stackDepth = 0L;
        long isEqual = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            if (getI32(g_com_operationmode) == 0L && getU32(g_expression_state) != 1L || comma(stackDepth = (long) (byte) Stack_GetRegistersDepth(), (byte) stackDepth != 0L ? 1L : 0L) != 0L) {
                putI32(g_result_state, 0L);
            }
            if (getU32(g_basic_displaymode) == 0L) {
                fStringToCC_er(g_basic_edit, framePtr + 16L, 0L, 1L, 46L, 44L);
                isEqual = fEQ(framePtr + 16L, CC_0) != 0L ? 1L : 0L;
                if (isEqual != 0L) {
                    putI64(framePtr + 24L, 0L);
                    putI32(framePtr + 16L, 1L);
                    putI32(framePtr + 20L, 0L);
                }
                Stack_SetX(framePtr + 16L);
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
                putI32(g_com_keyinmode, 0L);
            }
            putU8(g_com_eex_forbackspace, 0L);
            putU32(g_basic_displaymode, 1L);
            if (getI32(g_com_operationmode) == 0L) {
                status = Basic_CaculateWithBracket();
            } else {
                status = 99L;
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long Basic_DisableUndoMode() {
        long annunciator = 0L;
        long tempVal = 0L;
        if (getU8(g_com_canundo) == 0L) {
            return 0L;
        }
        putU8(g_undo_index, 0L);
        if (getI32(g_com_machine_mode) == 0L) {
            annunciator = LcdDriver_ClearAnnunciator(1L);
            tempVal = getU8(g_undo_index);
        } else {
            tempVal = 0L;
        }
        putU8(g_com_canundo, 0L);
        if ((getU8(g_undo_list + tempVal * 24L) & 63L) != 4L) {
            putI32(g_result_state, 1L);
        }
        return 0L;
    }

    long Basic_ProcessUndoModeKeys() {
        long freeCfloCount = 0L;
        long result = 0L;
        long index = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            result = 0L;
            switch ((int) (getU8(g_undo_list_pUndoBuffer + getU8(g_undo_index) * 24L + 312L) & 63L)) {
                case 0: case 1: {
                    putU32(g_basic_op, 99L);
                    result = Stack_EmptyRegisters();
                    putI8(g_undo_flag, 0L);
                    return 0L;
                }
                case 2: {
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
                    putU32(g_basic_op, 99L);
                    Stack_ClearAll();
                    Stack_EmptyRegisters();
                    putI64(framePtr + 32L, 0L);
                    putI64(framePtr + 24L, 1L);
                    putU16(framePtr + 16L, 1L);
                    putI32(g_result_state, 1L);
                    putI64(g_basic_lastx + 16L, 0L);
                    putI64(g_basic_lastx + 8L, 0L);
                    putI64(g_basic_lastx, 1L);
                    freeCfloCount = GetUnusedCfloNum() & 0xffffffffL;
                    if ((freeCfloCount & 0xffL) != 0L) {
                        index = 0L;
                        do {
                            SetCfloData(index & 0xffL, framePtr + 16L);
                            index = (index + 1L) & 0xffffffffL;
                            freeCfloCount = GetUnusedCfloNum() & 0xffffffffL;
                        } while ((index & 0xffL) < (freeCfloCount & 0xffL));
                    }
                    result = freeCfloCount;
                    putU16(g_prog_currpos, 0L);
                    break;
                }
                case 4: {
                    break;
                }
                case 3: {
                    SetStatReg(1L, CC_0);
                    SetStatReg(2L, CC_0);
                    SetStatReg(3L, CC_0);
                    SetStatReg(4L, CC_0);
                    SetStatReg(5L, CC_0);
                    SetStatReg(6L, CC_0);
                    putU32(g_basic_op, 99L);
                    Stack_ClearAll();
                    result = Stack_EmptyRegisters();
                    putI32(g_result_state, 1L);
                    putI8(g_undo_flag, 0L);
                    return 0L;
                }
                default: {
                    putI8(g_undo_flag, 0L);
                    return 0L;
                }
            }
            putI64(g_fin_n + 8L, 0L);
            putI64(g_fin_n, 1L);
            putI64(g_fin_i + 8L, 0L);
            putI64(g_fin_i, 1L);
            putI64(g_fin_pv + 8L, 0L);
            putI64(g_fin_pv, 1L);
            putI64(g_fin_pmt + 8L, 0L);
            putI64(g_fin_pmt, 1L);
            putI64(g_fin_fv + 8L, 0L);
            putI64(g_fin_fv, 1L);
            putI64(g_fin_npv + 8L, 0L);
            putI64(g_fin_npv, 1L);
            putI64(g_fin_irr + 8L, 0L);
            putI64(g_fin_irr, 1L);
            putI8(g_undo_flag, 0L);
            return 0L;
        } finally {
            sp = savedSp;
        }
    }

    long Basic_GetMiddleCalculateErr(long errCode, long resultRealPtr) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            tempVal = errCode & 0xffffffffL;
            if (tempVal < 2L) {
                errCode = 0L;
            } else {
                if (tempVal == 2L) {
                    errCode = 0L;
                    putI32(resultRealPtr + 8L, 0L);
                    putI32(resultRealPtr + 12L, 0L);
                    putI32(resultRealPtr, 1L);
                    putI32(resultRealPtr + 4L, 0L);
                } else {
                    if (tempVal != 3L) {
                        return errCode;
                    }
                    if (getI32(resultRealPtr) == 1L) {
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
                    fStringToCC_er(framePtr + 16L, resultRealPtr, 0L, 1L, 46L, 44L);
                    errCode = 0L;
                    putU8(g_com_result_overmax, 1L);
                }
            }
            return errCode;
        } finally {
            sp = savedSp;
        }
    }

}

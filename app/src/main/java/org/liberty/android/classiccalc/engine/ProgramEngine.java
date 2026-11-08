package org.liberty.android.classiccalc.engine;

/**
 * Keystroke programming execution, editing, and program mode key handlers.
 */
abstract class ProgramEngine extends AlgebraicBasicEngine {
    void Prog_IsPause() {
    }

    long Prog_ClearWarning() {
        long stepCode = 0L;
        Com_PrintWithSign(0x16001eL, 0L, 0L);
        stepCode = Prog_Get1Line();
        putU8(g_com_errmode, 0L);
        return stepCode;
    }

    long Prog_Get1Line() {
        long byteVal1 = 0L;
        long tempVal = 0L;
        long stepCode = 0L;
        long result = 0L;
        long byteVal2 = 0L;
        long intVal = 0L;
        long byteVal3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            tempVal = getU16(g_prog_currpos);
            Com_PrintWithSign(0x16001eL, 0L, 0L);
            if (tempVal == 0L) {
                if ((getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
                    Com_PrintWithSign(0x16001fL, 0L, 0L);
                    if (getU32(g_com_decimal_mode) == 0L) {
                        intVal = 2L;
                    } else {
                        intVal = 1L;
                    }
                    LcdDriver_ShowPunctuation(intVal, 2L);
                    result = 0L;
                } else {
                    result = Com_PrintWithSign(0x160023L, 0L, 0L);
                }
            } else {
                if ((getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
                    intVal = (short) getU16(g_prog_currpos);
                    byteVal1 = (long) (byte) (intVal / 100L);
                    byteVal3 = (long) (byte) ((long) (int) (intVal % 100L) / 10L);
                    byteVal2 = (long) (byte) (getU16(g_prog_currpos) + intVal / 10L * -10L + 48L);
                } else {
                    byteVal1 = (long) (byte) ((short) getU16(g_prog_currpos) / 10L);
                    byteVal3 = (long) (byte) (getU16(g_prog_currpos) + byteVal1 * -10L);
                    byteVal2 = 45L;
                }
                putU16(framePtr + 16L, ((((byteVal3 + 48L) & 0xffL)) << 8) | ((byteVal1 + 48L) & 0xffL));
                putU32(framePtr + 16L, (((byteVal2 & 0xffL)) << 16) | (getU32(framePtr + 16L) & 0xffffL));
                putU32(framePtr + 20L, getU32(framePtr + 16L));
                Com_PrintWithSign(framePtr + 20L, 0L, 0L);
                if ((getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
                    intVal = 1L;
                    if (getU32(g_com_decimal_mode) == 0L) {
                        intVal = 2L;
                    }
                    LcdDriver_ShowPunctuation(intVal, 2L);
                }
                stepCode = GetProgData(getU16(g_prog_currpos)) & 0xffffL;
                Prog_PrintCodeLine(stepCode & 0xffL, (stepCode >> 8) & 0xffL);
                result = 0L;
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    void Prog_PrintSign(long isNegative) {
        long intVal = 0L;
        intVal = 1L;
        if (getU32(g_com_decimal_mode) == 0L) {
            intVal = 2L;
        }
        LcdDriver_ShowPunctuation(intVal, isNegative & 0xffL);
    }

    long Prog_PrintProgTop() {
        long intVal = 0L;
        long result = 0L;
        Com_PrintWithSign(0x16001eL, 0L, 0L);
        if ((getI32(g_com_machine_mode) & 0xfffffffdL) != 0L) {
            result = Com_PrintWithSign(0x160023L, 0L, 0L);
            return result;
        }
        Com_PrintWithSign(0x16001fL, 0L, 0L);
        if (getU32(g_com_decimal_mode) == 0L) {
            intVal = 2L;
        } else {
            intVal = 1L;
        }
        LcdDriver_ShowPunctuation(intVal, 2L);
        return 0L;
    }

    void Prog_TransInt2Str(long intVal, long outStrPtr) {
        long intVal1 = 0L;
        long byteVal1 = 0L;
        long byteVal2 = 0L;
        long byteVal3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(96);
        try {
            intVal1 = (short) intVal;
            if ((getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
                byteVal1 = (long) (byte) (intVal1 / 100L);
                byteVal3 = (long) (byte) ((long) (int) (intVal1 % 100L) / 10L);
                byteVal2 = (long) (byte) (intVal1 % 10L + 48L);
            } else {
                byteVal1 = (long) (byte) (intVal1 / 10L);
                byteVal3 = (long) (byte) (intVal + byteVal1 * -10L);
                byteVal2 = 45L;
            }
            putU16(framePtr + 28L, ((((byteVal3 + 48L) & 0xffL)) << 8) | ((byteVal1 + 48L) & 0xffL));
            putU32(framePtr + 28L, (((byteVal2 & 0xffL)) << 16) | (getU32(framePtr + 28L) & 0xffffL));
            putU32(outStrPtr, getU32(framePtr + 28L));
        } finally {
            sp = savedSp;
        }
    }

    void Prog_TransCode2Str(long keyCode, long outStrPtr) {
        long flag1 = 0L;
        long tempVal1 = 0L;
        long flag2 = 0L;
        long bytePtr = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            tempVal3 = keyCode & 0xffL;
            if ((keyCode & 0xffL) < 10L) {
                putU16(framePtr + 16L, (keyCode + 48L) & 0xffL);
            } else {
                putU8(framePtr + 37L, 0L);
                bytePtr = framePtr + 37L;
                do {
                    tempVal2 = tempVal3 & 0xffffffffL;
                    tempVal3 = Long.divideUnsigned(tempVal3, 10L);
                    tempVal1 = (tempVal2 + tempVal3 * -10L) & 0xffffffffL;
                    flag2 = tempVal1 & 0xffL;
                    flag1 = (flag2 + 87L) & 0xffL;
                    if (((((tempVal1 & 0xffL) | 48L)) & 0xffffffffL) < 58L) {
                        flag1 = ((flag2 | 48L)) & 0xffL;
                    }
                    bytePtr = bytePtr - 1L;
                    putU8(bytePtr, flag1);
                } while (9L < tempVal2);
                memcpy(framePtr + 16L, bytePtr, (long) (int) (framePtr + 37L - bytePtr));
            }
            putU8(framePtr + 18L, 0L);
            putI8(outStrPtr + 2L, 0L);
            putU16(outStrPtr, getU16(framePtr + 16L));
        } finally {
            sp = savedSp;
        }
    }

    void Prog_TransLongCode2Str(long prefixCode, long mainCode, long outStrPtr) {
        long flag1 = 0L;
        long flag2 = 0L;
        long intVal = 0L;
        long flag3 = 0L;
        long bytePtr = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            if (((prefixCode - 50L) & 0xffL) < 30L) {
                putU8(framePtr + 29L, 50L);
                switch ((int) (prefixCode & 0xffL)) {
                    case 50: {
                        putU16(framePtr + 32L, 48L);
                        putU16(framePtr + 28L, (getU8(framePtr + 29L) << 8) | 52L);
                        putU8(framePtr + 34L, 0L);
                        putI8(mainCode + 2L, 0L);
                        putU16(mainCode, getU16(framePtr + 28L));
                        putI8(outStrPtr + 2L, 0L);
                        putU16(outStrPtr, getU16(framePtr + 32L));
                        return;
                    }
                    case 57: {
                        putU16(framePtr + 32L, 55L);
                        break;
                    }
                    case 58: {
                        putU16(framePtr + 32L, 56L);
                        break;
                    }
                    case 59: {
                        putU16(framePtr + 32L, 57L);
                        break;
                    }
                    case 60: {
                        putU16(framePtr + 32L, 14388L);
                        break;
                    }
                    case 67: {
                        putU16(framePtr + 32L, 52L);
                        break;
                    }
                    case 68: {
                        putU16(framePtr + 32L, 53L);
                        break;
                    }
                    case 69: {
                        putU16(framePtr + 32L, 54L);
                        break;
                    }
                    case 70: {
                        putU16(framePtr + 32L, 51L);
                        break;
                    }
                    case 77: {
                        putU16(framePtr + 32L, 49L);
                        break;
                    }
                    case 78: {
                        putU16(framePtr + 32L, 50L);
                        break;
                    }
                    default: {
                        bytePtr = framePtr + 53L;
                        tempVal3 = (prefixCode - 40L) & 0xffL;
                        do {
                            flag3 = (tempVal3 % 10L) & 0xffL;
                            flag2 = (flag3 + 87L) & 0xffL;
                            if (((((tempVal3 % 10L) | 48L)) & 0xffffffffL) < 58L) {
                                flag2 = ((flag3 | 48L)) & 0xffL;
                            }
                            bytePtr = bytePtr - 1L;
                            putU8(bytePtr, flag2);
                            flag1 = 9L < tempVal3 ? 1L : 0L;
                            tempVal3 = (tempVal3 / 10L) & 0xffffffffL;
                        } while (flag1 != 0L);
                        intVal = (long) (int) (framePtr + 53L - bytePtr);
                        putU8(framePtr + 53L, 0L);
                        memcpy(framePtr + 32L, bytePtr, intVal);
                        break;
                    }
                }
            } else {
                tempVal2 = (prefixCode + 176L) & 0xffL;
                putU8(framePtr + 29L, 51L);
                if (((prefixCode + 176L) & 0xffL) < 10L) {
                    putU16(framePtr + 32L, (prefixCode - 32L) & 0xffL);
                } else {
                    bytePtr = framePtr + 53L;
                    do {
                        tempVal1 = tempVal2 & 0xffffffffL;
                        tempVal2 = Long.divideUnsigned(tempVal2, 10L);
                        tempVal3 = (tempVal1 + tempVal2 * -10L) & 0xffffffffL;
                        flag3 = tempVal3 & 0xffL;
                        flag2 = (flag3 + 87L) & 0xffL;
                        if (((((tempVal3 & 0xffL) | 48L)) & 0xffffffffL) < 58L) {
                            flag2 = ((flag3 | 48L)) & 0xffL;
                        }
                        bytePtr = bytePtr - 1L;
                        putU8(bytePtr, flag2);
                    } while (9L < tempVal1);
                    intVal = (long) (int) (framePtr + 53L - bytePtr);
                    putU8(framePtr + 53L, 0L);
                    memcpy(framePtr + 32L, bytePtr, intVal);
                }
            }
            putU16(framePtr + 28L, (getU8(framePtr + 29L) << 8) | 52L);
            putU8(framePtr + 34L, 0L);
            putI8(mainCode + 2L, 0L);
            putU16(mainCode, getU16(framePtr + 28L));
            putI8(outStrPtr + 2L, 0L);
            putU16(outStrPtr, getU16(framePtr + 32L));
        } finally {
            sp = savedSp;
        }
    }

    void Prog_PrintKeyLine(long stepIndex) {
        long flag1 = 0L;
        long flag2 = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long bytePtr = 0L;
        long dataPtr = 0L;
        long flag3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            if ((long) (int) stepIndex == 160L && getI32(g_com_machine_mode) == 1L) {
                putU16(framePtr + 16L, 65396L);
                SetProgData(getU16(g_prog_currpos), framePtr + 16L);
                putU16(framePtr + 28L, 13108L);
                putU8(framePtr + 24L, 52L);
                putU8(framePtr + 25L, 51L);
                tempVal1 = 52L;
                putU8(framePtr + 26L, 0L);
                putU16(framePtr + 36L, 13875L);
                putU8(framePtr + 38L, 0L);
                flag3 = 0L;
                do {
                    LcdDriver_PutChar(tempVal1, (flag3 + 5L) & 0xffL);
                    flag3 = (flag3 + 1L) & 0xffL;
                    tempVal1 = getU8(framePtr + 24L + flag3);
                } while (tempVal1 != 0L);
                if ((getU16(framePtr + 36L) & 0xffL) != 0L) {
                    flag3 = 0L;
                    tempVal1 = getU16(framePtr + 36L) & 0xffL;
                    do {
                        LcdDriver_PutChar(tempVal1, (flag3 + 8L) & 0xffL);
                        flag3 = (flag3 + 1L) & 0xffL;
                        tempVal1 = getU8(framePtr + 36L + flag3);
                    } while (tempVal1 != 0L);
                }
                return;
            }
            putI64(pCodeTable, ADDR_RODATA + 7704L);
            flag3 = 0L;
            while (true) {
                dataPtr = getI64(pCodeTable) + 8L;
                if (getI32(getI64(pCodeTable)) == (long) (int) stepIndex) {
                    break;
                }
                flag2 = flag3 < 137L ? 1L : 0L;
                flag3 = (flag3 + 1L) & 0xffL;
                putI64(pCodeTable, dataPtr);
                if (flag2 == 0L) {
                    return;
                }
            }
            putU16(framePtr + 16L, (0xffL << 8) | getU8(getI64(pCodeTable) + 4L));
            SetProgData(getU16(g_prog_currpos), framePtr + 16L);
            flag3 = getU8(getI64(pCodeTable) + 4L);
            if (flag3 < 50L) {
                if (flag3 < 10L) {
                    putU16(framePtr + 32L, (flag3 + 48L) & 0xffL);
                } else {
                    putU8(framePtr + 53L, 0L);
                    bytePtr = framePtr + 53L;
                    tempVal2 = flag3;
                    do {
                        flag1 = (tempVal2 % 10L) & 0xffL;
                        flag3 = (flag1 + 87L) & 0xffL;
                        if (((((tempVal2 % 10L) | 48L)) & 0xffffffffL) < 58L) {
                            flag3 = ((flag1 | 48L)) & 0xffL;
                        }
                        bytePtr = bytePtr - 1L;
                        putU8(bytePtr, flag3);
                        flag2 = 9L < tempVal2 ? 1L : 0L;
                        tempVal2 = (tempVal2 / 10L) & 0xffffffffL;
                    } while (flag2 != 0L);
                    memcpy(framePtr + 32L, bytePtr, (long) (int) (framePtr + 53L - bytePtr));
                }
                putU8(framePtr + 38L, 0L);
                putU16(framePtr + 36L, getU16(framePtr + 32L));
            } else {
                Prog_TransLongCode2Str(flag3, framePtr + 24L, framePtr + 36L);
                if (getU8(framePtr + 24L) != 0L) {
                    flag3 = 0L;
                    tempVal1 = getU8(framePtr + 24L);
                    do {
                        LcdDriver_PutChar(tempVal1, (flag3 + 5L) & 0xffL);
                        flag3 = (flag3 + 1L) & 0xffL;
                        tempVal1 = getU8(framePtr + 24L + flag3);
                    } while (tempVal1 != 0L);
                }
            }
            if ((getU16(framePtr + 36L) & 0xffL) < 58L) {
                if (getU8(framePtr + 37L) == 0L) {
                    LcdDriver_PutChar(getU16(framePtr + 36L) & 0xffL, 9L);
                    return;
                }
                if ((getU16(framePtr + 36L) & 0xffL) == 0L) {
                    return;
                }
            }
            flag3 = 0L;
            flag1 = getU16(framePtr + 36L) & 0xffL;
            do {
                LcdDriver_PutChar(flag1, (flag3 + 8L) & 0xffL);
                flag3 = (flag3 + 1L) & 0xffL;
                flag1 = getU8(framePtr + 36L + flag3);
            } while (flag1 != 0L);
        } finally {
            sp = savedSp;
        }
    }

    long Prog_Set1Line(long encodedStep) {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long byteVal1 = 0L;
        long result = 0L;
        long byteVal2 = 0L;
        long intVal = 0L;
        long byteVal3 = 0L;
        long index = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            tempVal3 = (getU16(g_prog_maxstep) - 1L) & 0xffffffffL;
            if (getU16(g_prog_currpos) < (long) (int) tempVal3) {
                if (getU16(g_prog_currpos) == getU16(g_prog_totalstep_num)) {
                    tempVal2 = (getU16(g_prog_totalstep_num) + 7L) & 0xffffffffL;
                    index = (getU16(g_prog_currpos) + 1L) & 0xffffL;
                    tempVal1 = tempVal2 & 0xffffL;
                    if (getU16(g_prog_maxstep) < tempVal1 || getU16(g_prog_maxstep) == tempVal1) {
                        tempVal2 = tempVal3;
                    }
                    putU16(g_prog_totalstep_num, tempVal2);
                    if (index <= (tempVal2 & 0xffffL)) {
                        do {
                            putU32(framePtr + 32L, (getU16(framePtr + 34L) << 16) | 65403L);
                            SetProgData(index, framePtr + 32L);
                            index = (index + 1L) & 0xffffL;
                        } while (index <= getU16(g_prog_totalstep_num));
                    }
                }
                putU16(g_prog_currpos, getU16(g_prog_currpos) + 1L);
                Com_PrintWithSign(0x16001eL, 0L, 0L);
                if ((getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
                    intVal = getU16(g_prog_currpos);
                    byteVal1 = (long) (byte) (intVal / 100L);
                    byteVal3 = (long) (byte) ((long) (int) (intVal % 100L) / 10L);
                    byteVal2 = (long) (byte) (getU16(g_prog_currpos) + intVal / 10L * -10L + 48L);
                } else {
                    byteVal1 = (long) (byte) (getU16(g_prog_currpos) / 10L);
                    byteVal3 = (long) (byte) (getU16(g_prog_currpos) + byteVal1 * -10L);
                    byteVal2 = 45L;
                }
                putU16(framePtr + 32L, ((((byteVal3 + 48L) & 0xffL)) << 8) | ((byteVal1 + 48L) & 0xffL));
                putU32(framePtr + 32L, (((byteVal2 & 0xffL)) << 16) | (getU32(framePtr + 32L) & 0xffffL));
                putU32(framePtr + 28L, getU32(framePtr + 32L));
                Com_PrintWithSign(framePtr + 28L, 0L, 0L);
                if ((getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
                    intVal = 1L;
                    if (getU32(g_com_decimal_mode) == 0L) {
                        intVal = 2L;
                    }
                    LcdDriver_ShowPunctuation(intVal, 2L);
                }
                Prog_PrintKeyLine((long) (int) encodedStep);
                result = 99L;
            } else {
                putU16(g_prog_currpos, 0L);
                result = 4L;
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Prog_ApplyNewPos() {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long result = 0L;
        long index = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal3 = (getU16(g_prog_maxstep) - 1L) & 0xffffffffL;
            if (getU16(g_prog_currpos) < (long) (int) tempVal3) {
                if (getU16(g_prog_currpos) == getU16(g_prog_totalstep_num)) {
                    tempVal2 = (getU16(g_prog_totalstep_num) + 7L) & 0xffffffffL;
                    index = (getU16(g_prog_currpos) + 1L) & 0xffffL;
                    tempVal1 = tempVal2 & 0xffffL;
                    if (getU16(g_prog_maxstep) < tempVal1 || getU16(g_prog_maxstep) == tempVal1) {
                        tempVal2 = tempVal3;
                    }
                    putU16(g_prog_totalstep_num, tempVal2);
                    if (index <= (tempVal2 & 0xffffL)) {
                        do {
                            putU16(framePtr + 16L, 65403L);
                            SetProgData(index, framePtr + 16L);
                            index = (index + 1L) & 0xffffL;
                        } while (index <= getU16(g_prog_totalstep_num));
                    }
                }
                putU16(g_prog_currpos, getU16(g_prog_currpos) + 1L);
                result = 1L;
            } else {
                putU16(g_prog_currpos, 0L);
                result = 0L;
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    void Prog_GetGTOLine(long encodedStep, long outTargetStepPtr) {
        long byteVal = 0L;
        long tempVal1 = 0L;
        long intVal = 0L;
        long tempVal2 = 0L;
        long flag = 0L;
        long tempVal3 = 0L;
        long index = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            tempVal3 = outTargetStepPtr & 0xffL;
            index = 0L;
            do {
                flag = (index + 3L) & 0xffL;
                LcdDriver_PutChar(getU8(ADDR_RODATA + 159L + index), flag);
                if ((getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
                    if ((index & 1L) != 0L) {
                        if (getU32(g_com_decimal_mode) == 0L) {
                            intVal = 2L;
                        } else {
                            intVal = 1L;
                        }
                        LcdDriver_ShowPunctuation(intVal, flag);
                    }
                } else {
                    if (index == 2L) {
                        if (getU32(g_com_decimal_mode) == 0L) {
                            intVal = 2L;
                        } else {
                            intVal = 1L;
                        }
                        flag = 4L;
                        LcdDriver_ShowPunctuation(intVal, flag);
                    }
                }
                tempVal1 = getU32(framePtr + 20L);
                index = index + 1L;
            } while (index != 4L);
            if ((encodedStep & 0xffL) == 132L) {
                tempVal2 = 512L;
                tempVal3 = ((tempVal2 | tempVal3)) & 0xffffffffL;
            } else {
                if ((encodedStep & 0xffL) != 131L) {
                    if ((encodedStep & 0xffL) != 130L) {
                        tempVal3 = 0L;
                    }
                } else {
                    tempVal2 = 256L;
                    tempVal3 = ((tempVal2 | tempVal3)) & 0xffffffffL;
                }
            }
            if ((getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
                byteVal = (long) (byte) (tempVal3 / 100L + 48L);
                putU16(framePtr + 16L, (((((((long) (int) (tempVal3 % 100L) * 0x66666667L) >>> 34) + 48L) & 0xffL)) << 8) | (byteVal & 0xffL));
                putU32(framePtr + 16L, ((((tempVal3 + tempVal3 / 10L * -10L + 48L) & 0xffL)) << 16) | (getU32(framePtr + 16L) & 0xffffL));
                putU32(framePtr + 20L, getU32(framePtr + 16L));
                if ((byte) byteVal != 0L) {
                    flag = 0L;
                    do {
                        LcdDriver_PutChar(getU32(framePtr + 16L) & 0xffL, (flag + 7L) & 0xffL);
                        flag = (flag + 1L) & 0xffL;
                        putU32(framePtr + 16L, getU8(framePtr + 20L + flag));
                    } while (getU32(framePtr + 16L) != 0L);
                }
            } else {
                tempVal2 = (tempVal3 / 10L + 48L) & 0xffffffffL;
                putU32(framePtr + 20L, (tempVal1 & 0xff000000L) | (((((((tempVal3 + tempVal3 / 10L * -10L) & 0xffL)) << 8) | (tempVal2 & 0xffL) | 12288L)) & 0xffffL));
                if (tempVal2 != 0L) {
                    flag = 0L;
                    do {
                        LcdDriver_PutChar(tempVal2 & 0xffL, (flag + 8L) & 0xffL);
                        flag = (flag + 1L) & 0xffL;
                        tempVal2 = getU8(framePtr + 20L + flag);
                    } while (tempVal2 != 0L);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    void Prog_GetRegLine(long encodedStep, long outRegOpPtr) {
        long flag1 = 0L;
        long flag2 = 0L;
        long tempVal1 = 0L;
        long intVal1 = 0L;
        long bytePtr1 = 0L;
        long intVal2 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        long bytePtr2 = 0L;
        long flag3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            putU8(framePtr + 53L, 0L);
            if ((encodedStep & 0xffL) == 44L) {
                putU16(framePtr + 32L, 13364L);
            } else {
                putU16(framePtr + 32L, 13620L);
            }
            putU8(framePtr + 34L, 0L);
            if ((outRegOpPtr & 0xffL) < 10L) {
                putU8(framePtr + 28L, outRegOpPtr + 48L);
                putU16(framePtr + 29L, 0L);
                if ((getU16(framePtr + 32L) & 0xffL) != 0L) {
                    flag3 = 0L;
                    tempVal1 = getU16(framePtr + 32L) & 0xffL;
                    do {
                        LcdDriver_PutChar(tempVal1, (flag3 + 6L) & 0xffL);
                        flag3 = (flag3 + 1L) & 0xffL;
                        tempVal1 = getU8(framePtr + 32L + flag3);
                    } while (tempVal1 != 0L);
                }
                LcdDriver_PutChar(getU8(framePtr + 28L), 9L);
                return;
            }
            intVal2 = (long) (int) (framePtr + 53L);
            tempVal4 = outRegOpPtr & 0xffL;
            if ((outRegOpPtr & 0xffL) < 20L) {
                bytePtr1 = framePtr + 53L;
                putU8(framePtr + 53L, 0L);
                tempVal2 = tempVal4;
                do {
                    flag2 = (tempVal2 % 10L) & 0xffL;
                    flag3 = (flag2 + 87L) & 0xffL;
                    if (((((tempVal2 % 10L) | 48L)) & 0xffffffffL) < 58L) {
                        flag3 = ((flag2 | 48L)) & 0xffL;
                    }
                    bytePtr1 = bytePtr1 - 1L;
                    putU8(bytePtr1, flag3);
                    flag1 = 9L < tempVal2 ? 1L : 0L;
                    tempVal2 = (tempVal2 / 10L) & 0xffffffffL;
                } while (flag1 != 0L);
                memcpy(framePtr + 28L, bytePtr1, (long) (int) (intVal2 - bytePtr1));
                putU16(framePtr + 29L, getU16(framePtr + 29L) & 0xffL);
                if ((getU16(framePtr + 32L) & 0xffL) != 0L) {
                    flag3 = 0L;
                    tempVal1 = getU16(framePtr + 32L) & 0xffL;
                    do {
                        LcdDriver_PutChar(tempVal1, (flag3 + 3L) & 0xffL);
                        flag3 = (flag3 + 1L) & 0xffL;
                        tempVal1 = getU8(framePtr + 32L + flag3);
                    } while (tempVal1 != 0L);
                }
                tempVal1 = 52L;
                putU16(framePtr + 32L, 14388L);
                flag3 = 0L;
                do {
                    LcdDriver_PutChar(tempVal1, (flag3 + 6L) & 0xffL);
                    flag3 = (flag3 + 1L) & 0xffL;
                    tempVal1 = getU8(framePtr + 32L + flag3);
                } while (tempVal1 != 0L);
                bytePtr2 = framePtr + 52L;
                tempVal2 = (10L - tempVal4) & 0xffffffffL;
                if (9L < (outRegOpPtr & 0xffL)) {
                    tempVal2 = (tempVal4 - 10L) & 0xffffffffL;
                }
                do {
                    bytePtr1 = bytePtr2;
                    tempVal4 = tempVal2 & 0xffffL;
                    tempVal3 = (tempVal4 / 10L) & 0xffffffffL;
                    tempVal2 = (tempVal2 + tempVal3 * 0xfffffff6L) & 0xffffffffL;
                    flag2 = tempVal2 & 0xffL;
                    flag3 = (flag2 + 87L) & 0xffL;
                    if (((((tempVal2 & 0xffL) | 48L)) & 0xffffffffL) < 58L) {
                        flag3 = ((flag2 | 48L)) & 0xffL;
                    }
                    bytePtr2 = bytePtr1 - 1L;
                    putU8(bytePtr1, flag3);
                    tempVal2 = tempVal3;
                } while (9L < tempVal4);
                if ((outRegOpPtr & 0xffL) < 10L) {
                    putU8(bytePtr2, 45L);
                    bytePtr1 = bytePtr2;
                }
                intVal1 = (long) (int) bytePtr1;
                putU8(framePtr + 53L, 0L);
                memcpy(framePtr + 28L, bytePtr1, (long) (int) (intVal2 - intVal1));
                LcdDriver_PutChar(getU8(framePtr + 28L), 9L);
                return;
            }
            if ((outRegOpPtr & 0xffL) < 91L) {
                if (((outRegOpPtr - 10L) & 0xffL) != 26L && (getU16(framePtr + 32L) & 0xffL) != 0L) {
                    flag3 = 0L;
                    tempVal1 = getU16(framePtr + 32L) & 0xffL;
                    do {
                        LcdDriver_PutChar(tempVal1, (flag3 + 5L) & 0xffL);
                        flag3 = (flag3 + 1L) & 0xffL;
                        tempVal1 = getU8(framePtr + 32L + flag3);
                    } while (tempVal1 != 0L);
                }
                bytePtr1 = framePtr + 53L;
                putU8(framePtr + 53L, 0L);
                tempVal4 = (outRegOpPtr - 10L) & 0xffL;
                do {
                    flag2 = (tempVal4 % 10L) & 0xffL;
                    flag3 = (flag2 + 87L) & 0xffL;
                    if (((((tempVal4 % 10L) | 48L)) & 0xffffffffL) < 58L) {
                        flag3 = ((flag2 | 48L)) & 0xffL;
                    }
                    bytePtr1 = bytePtr1 - 1L;
                    putU8(bytePtr1, flag3);
                    flag1 = 9L < tempVal4 ? 1L : 0L;
                    tempVal4 = (tempVal4 / 10L) & 0xffffffffL;
                } while (flag1 != 0L);
                memcpy(framePtr + 28L, bytePtr1, (long) (int) (intVal2 - bytePtr1));
                putU16(framePtr + 29L, getU16(framePtr + 29L) & 0xffL);
                if (getU8(framePtr + 28L) != 0L) {
                    flag3 = 0L;
                    tempVal1 = getU8(framePtr + 28L);
                    do {
                        LcdDriver_PutChar(tempVal1, (flag3 + 8L) & 0xffL);
                        flag3 = (flag3 + 1L) & 0xffL;
                        tempVal1 = getU8(framePtr + 28L + flag3);
                    } while (tempVal1 != 0L);
                }
            } else {
                if ((encodedStep & 0xffL) == 44L) {
                    if ((getU16(framePtr + 32L) & 0xffL) != 0L) {
                        flag3 = 0L;
                        tempVal1 = getU16(framePtr + 32L) & 0xffL;
                        do {
                            LcdDriver_PutChar(tempVal1, (flag3 + 3L) & 0xffL);
                            flag3 = (flag3 + 1L) & 0xffL;
                            tempVal1 = getU8(framePtr + 32L + flag3);
                        } while (tempVal1 != 0L);
                    }
                    tempVal2 = ((((outRegOpPtr & 0xffL) - 100L) & 254L) / 10L) & 0xffffffffL;
                    bytePtr1 = framePtr + 53L;
                    putU8(framePtr + 53L, 0L);
                    tempVal4 = (tempVal2 * 10L) & 254L;
                    do {
                        flag2 = (tempVal4 % 10L) & 0xffL;
                        flag3 = (flag2 + 87L) & 0xffL;
                        if (((((tempVal4 % 10L) | 48L)) & 0xffffffffL) < 58L) {
                            flag3 = ((flag2 | 48L)) & 0xffL;
                        }
                        bytePtr1 = bytePtr1 - 1L;
                        putU8(bytePtr1, flag3);
                        flag1 = 9L < tempVal4 ? 1L : 0L;
                        tempVal4 = (tempVal4 / 10L) & 0xffffffffL;
                    } while (flag1 != 0L);
                    memcpy(framePtr + 28L, bytePtr1, (long) (int) (intVal2 - bytePtr1));
                    putU16(framePtr + 29L, getU16(framePtr + 29L) & 0xffL);
                    if (getU8(framePtr + 28L) != 0L) {
                        flag3 = 0L;
                        tempVal1 = getU8(framePtr + 28L);
                        do {
                            LcdDriver_PutChar(tempVal1, (flag3 + 6L) & 0xffL);
                            flag3 = (flag3 + 1L) & 0xffL;
                            tempVal1 = getU8(framePtr + 28L + flag3);
                        } while (tempVal1 != 0L);
                    }
                    bytePtr1 = framePtr + 53L;
                    tempVal4 = (outRegOpPtr - 100L + tempVal2 * 0xfffffff6L) & 0xffL;
                    do {
                        flag2 = (tempVal4 % 10L) & 0xffL;
                        flag3 = (flag2 + 87L) & 0xffL;
                        if (((((tempVal4 % 10L) | 48L)) & 0xffffffffL) < 58L) {
                            flag3 = ((flag2 | 48L)) & 0xffL;
                        }
                        bytePtr1 = bytePtr1 - 1L;
                        putU8(bytePtr1, flag3);
                        flag1 = 9L < tempVal4 ? 1L : 0L;
                        tempVal4 = (tempVal4 / 10L) & 0xffffffffL;
                    } while (flag1 != 0L);
                    intVal1 = (long) (int) bytePtr1;
                    putU8(framePtr + 53L, 0L);
                    memcpy(framePtr + 28L, bytePtr1, (long) (int) (intVal2 - intVal1));
                    LcdDriver_PutChar(getU8(framePtr + 28L), 9L);
                    return;
                }
                if ((getU16(framePtr + 32L) & 0xffL) != 0L) {
                    flag3 = 0L;
                    tempVal1 = getU16(framePtr + 32L) & 0xffL;
                    do {
                        LcdDriver_PutChar(tempVal1, (flag3 + 3L) & 0xffL);
                        flag3 = (flag3 + 1L) & 0xffL;
                        tempVal1 = getU8(framePtr + 32L + flag3);
                    } while (tempVal1 != 0L);
                }
                intVal2 = 1L;
                if (getU32(g_com_decimal_mode) == 0L) {
                    intVal2 = 2L;
                }
                LcdDriver_ShowPunctuation(intVal2, 4L);
                Prog_TransLongCode2Str(outRegOpPtr & 0xffL, framePtr + 28L, framePtr + 32L);
                if ((getU16(framePtr + 32L) & 0xffL) != 0L) {
                    flag3 = 0L;
                    tempVal1 = getU16(framePtr + 32L) & 0xffL;
                    do {
                        LcdDriver_PutChar(tempVal1, (flag3 + 8L) & 0xffL);
                        flag3 = (flag3 + 1L) & 0xffL;
                        tempVal1 = getU8(framePtr + 32L + flag3);
                    } while (tempVal1 != 0L);
                }
                if (getU8(framePtr + 28L) != 0L) {
                    flag3 = 0L;
                    tempVal1 = getU8(framePtr + 28L);
                    do {
                        LcdDriver_PutChar(tempVal1, (flag3 + 5L) & 0xffL);
                        flag3 = (flag3 + 1L) & 0xffL;
                        tempVal1 = getU8(framePtr + 28L + flag3);
                    } while (tempVal1 != 0L);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    void Prog_PrintCodeLine(long stepIndex, long encodedStep) {
        long tempVal1 = 0L;
        long flag1 = 0L;
        long tempVal2 = 0L;
        long bytePtr = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        long flag2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal2 = ((stepIndex & 0xffL) - 123L) & 0xffffffffL;
            if (tempVal2 < 10L && ((1L << (int) (tempVal2 & 31L)) & 897L) != 0L) {
                Prog_GetGTOLine(stepIndex & 0xffL, encodedStep & 0xffL);
                return;
            }
            if ((long) (int) ((stepIndex & 0xffL) - 44L) < 2L) {
                Prog_GetRegLine(stepIndex & 0xffL, encodedStep & 0xffL);
                return;
            }
            if ((stepIndex & 0xffL) < 50L) {
                if ((stepIndex & 0xffL) < 10L) {
                    putU16(framePtr + 16L, (stepIndex + 48L) & 0xffL);
                } else {
                    tempVal4 = stepIndex & 0xffL;
                    putU8(framePtr + 37L, 0L);
                    bytePtr = framePtr + 37L;
                    do {
                        tempVal3 = tempVal4 & 0xffffffffL;
                        tempVal4 = Long.divideUnsigned(tempVal4, 10L);
                        tempVal2 = (tempVal3 + tempVal4 * -10L) & 0xffffffffL;
                        flag1 = tempVal2 & 0xffL;
                        flag2 = (flag1 + 87L) & 0xffL;
                        if (((((tempVal2 & 0xffL) | 48L)) & 0xffffffffL) < 58L) {
                            flag2 = ((flag1 | 48L)) & 0xffL;
                        }
                        bytePtr = bytePtr - 1L;
                        putU8(bytePtr, flag2);
                    } while (9L < tempVal3);
                    memcpy(framePtr + 16L, bytePtr, (long) (int) (framePtr + 37L - bytePtr));
                }
                putU8(framePtr + 22L, 0L);
                putU16(framePtr + 20L, getU16(framePtr + 16L));
            } else {
                Prog_TransLongCode2Str(stepIndex & 0xffL, framePtr + 16L, framePtr + 20L);
                if ((getU16(framePtr + 16L) & 0xffL) != 0L) {
                    flag2 = 0L;
                    tempVal1 = getU16(framePtr + 16L) & 0xffL;
                    do {
                        LcdDriver_PutChar(tempVal1, (flag2 + 5L) & 0xffL);
                        flag2 = (flag2 + 1L) & 0xffL;
                        tempVal1 = getU8(framePtr + 16L + flag2);
                    } while (tempVal1 != 0L);
                }
            }
            if ((getU16(framePtr + 20L) & 0xffL) < 58L) {
                if (getU8(framePtr + 21L) == 0L) {
                    LcdDriver_PutChar(getU16(framePtr + 20L) & 0xffL, 9L);
                    return;
                }
                if ((getU16(framePtr + 20L) & 0xffL) == 0L) {
                    return;
                }
            }
            flag2 = 0L;
            flag1 = getU16(framePtr + 20L) & 0xffL;
            do {
                LcdDriver_PutChar(flag1, (flag2 + 8L) & 0xffL);
                flag2 = (flag2 + 1L) & 0xffL;
                flag1 = getU8(framePtr + 20L + flag2);
            } while (flag1 != 0L);
        } finally {
            sp = savedSp;
        }
    }

    long Prog_SetGTOVal(long digitKey) {
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            if (getU16(g_prog_totalstep_num) < (digitKey & 0xffffL)) {
                result = 4L;
            } else {
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, 1L);
                result = 99L;
                putU16(g_prog_currpos, digitKey);
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Prog_ExecReg(long encodedStep) {
        long tempVal = 0L;
        long stepCode = 0L;
        long result1 = 0L;
        long intVal = 0L;
        long result2 = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    stepCode = GetProgData(getU16(g_prog_currpos)) & 0xffffL;
                    tempVal = (stepCode >> 8) & 0xffffL;
                    switch ((int) (stepCode >> 8)) {
                        case 0: {
                            step = 1;
                            continue stepLoop;
                        }
                        case 1: {
                            step = 2;
                            continue stepLoop;
                        }
                        case 2: {
                            step = 3;
                            continue stepLoop;
                        }
                        case 3: {
                            step = 4;
                            continue stepLoop;
                        }
                        case 4: {
                            step = 5;
                            continue stepLoop;
                        }
                        case 5: {
                            step = 6;
                            continue stepLoop;
                        }
                        case 6: {
                            step = 7;
                            continue stepLoop;
                        }
                        case 7: {
                            step = 8;
                            continue stepLoop;
                        }
                        case 8: {
                            step = 9;
                            continue stepLoop;
                        }
                        case 9: {
                            step = 10;
                            continue stepLoop;
                        }
                        case 10: {
                            result1 = Base_HandleKeyDown(41L, encodedStep & 0xffL) & 0xffffffffL;
                            if (result1 != 99L) {
                                return result1;
                            }
                            intVal = 37L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        case 11: {
                            result1 = Base_HandleKeyDown(41L, encodedStep & 0xffL) & 0xffffffffL;
                            if (result1 != 99L) {
                                return result1;
                            }
                            intVal = 36L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        case 12: {
                            result1 = Base_HandleKeyDown(41L, encodedStep & 0xffL) & 0xffffffffL;
                            if (result1 != 99L) {
                                return result1;
                            }
                            intVal = 40L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        case 13: {
                            result1 = Base_HandleKeyDown(41L, encodedStep & 0xffL) & 0xffffffffL;
                            if (result1 != 99L) {
                                return result1;
                            }
                            intVal = 44L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        case 14: {
                            result1 = Base_HandleKeyDown(41L, encodedStep & 0xffL) & 0xffffffffL;
                            if (result1 != 99L) {
                                return result1;
                            }
                            intVal = 35L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        case 15: {
                            result1 = Base_HandleKeyDown(41L, encodedStep & 0xffL) & 0xffffffffL;
                            if (result1 != 99L) {
                                return result1;
                            }
                            intVal = 39L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        case 16: {
                            result1 = Base_HandleKeyDown(41L, encodedStep & 0xffL) & 0xffffffffL;
                            if (result1 != 99L) {
                                return result1;
                            }
                            intVal = 43L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        case 17: {
                            result1 = Base_HandleKeyDown(41L, encodedStep & 0xffL) & 0xffffffffL;
                            if (result1 != 99L) {
                                return result1;
                            }
                            intVal = 34L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        case 18: {
                            result1 = Base_HandleKeyDown(41L, encodedStep & 0xffL) & 0xffffffffL;
                            if (result1 != 99L) {
                                return result1;
                            }
                            intVal = 38L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        case 19: {
                            result1 = Base_HandleKeyDown(41L, encodedStep & 0xffL) & 0xffffffffL;
                            if (result1 != 99L) {
                                return result1;
                            }
                            intVal = 42L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        case 91: {
                            intVal = 138L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        case 92: {
                            intVal = 142L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        case 94: {
                            intVal = 150L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        case 95: {
                            intVal = 154L;
                            result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            return result1;
                        }
                        default: {
                            if (tempVal < 110L) {
                                switch ((int) (tempVal)) {
                                    case 21: {
                                        intVal = 10L;
                                        result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                                        return result1;
                                    }
                                    case 22: {
                                        intVal = 14L;
                                        result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                                        return result1;
                                    }
                                    case 23: {
                                        intVal = 18L;
                                        result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                                        return result1;
                                    }
                                    case 24: {
                                        intVal = 22L;
                                        result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                                        return result1;
                                    }
                                    case 25: {
                                        intVal = 26L;
                                        result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                                        return result1;
                                    }
                                    default: {
                                        break;
                                    }
                                }
                                return 4L;
                            }
                            if ((stepCode & 0xffL) == 45L) {
                                if (tempVal != 111L) {
                                    return 99L;
                                }
                                intVal = 140L;
                                result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                                return result1;
                            }
                            result2 = ((stepCode >> 8) - 100L) & 0xffffffffL;
                            result1 = ((result2 & 0xffL) % 10L) & 0xffffffffL;
                            intVal = 46L;
                            switch ((int) ((result2 - result1) & 0xffL)) {
                                case 10: {
                                    break;
                                }
                                case 20: {
                                    intVal = 47L;
                                    break;
                                }
                                case 30: {
                                    intVal = 48L;
                                    break;
                                }
                                case 40: {
                                    intVal = 49L;
                                    break;
                                }
                                default: {
                                    return 4L;
                                }
                            }
                            result2 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                            if (result2 != 99L) {
                                return result2;
                            }
                            switch ((int) (result1)) {
                                case 0: {
                                    step = 1;
                                    continue stepLoop;
                                }
                                case 1: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 2: {
                                    step = 3;
                                    continue stepLoop;
                                }
                                case 3: {
                                    step = 4;
                                    continue stepLoop;
                                }
                                case 4: {
                                    step = 5;
                                    continue stepLoop;
                                }
                                case 5: {
                                    step = 6;
                                    continue stepLoop;
                                }
                                case 6: {
                                    step = 7;
                                    continue stepLoop;
                                }
                                case 7: {
                                    step = 8;
                                    continue stepLoop;
                                }
                                case 8: {
                                    step = 9;
                                    continue stepLoop;
                                }
                                case 9: {
                                    step = 10;
                                    continue stepLoop;
                                }
                                default: {
                                    return 4L;
                                }
                            }
                        }
                    }
                }
                case 1: {
                    intVal = 37L;
                    result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                    return result1;
                }
                case 2: {
                    intVal = 36L;
                    result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                    return result1;
                }
                case 3: {
                    intVal = 40L;
                    result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                    return result1;
                }
                case 4: {
                    intVal = 44L;
                    result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                    return result1;
                }
                case 5: {
                    intVal = 35L;
                    result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                    return result1;
                }
                case 6: {
                    intVal = 39L;
                    result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                    return result1;
                }
                case 7: {
                    intVal = 43L;
                    result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                    return result1;
                }
                case 8: {
                    intVal = 34L;
                    result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                    return result1;
                }
                case 9: {
                    intVal = 38L;
                    result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                    return result1;
                }
                case 10: {
                    intVal = 42L;
                    result1 = Base_HandleKeyDown(intVal, encodedStep & 0xffL) & 0xffffffffL;
                    return result1;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long Prog_CheckKeyState(long keyCode) {
        long result = 0L;
        ComKeyCheckState((long) (int) keyCode);
        result = ComKeyPreHandle((long) (int) keyCode);
        return result;
    }

    long Prog_Exec(long keyCode) {
        long offset = 0L;
        long isInsertMode = 0L;
        long stepCode = 0L;
        long key = 0L;
        long result = 0L;
        long tempVal = 0L;
        long flag = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        if ((keyCode & 0xffL) != 0L) {
                            stepCode = GetProgData(getU16(g_prog_currpos)) & 0xffffL;
                            result = stepCode & 0xffL;
                            if (result < 111L) {
                                if (result == 31L) {
                                    ComKeyCheckState(0L);
                                    ComKeyPreHandle(0L);
                                    Com_EndEditMode_CareOP();
                                    isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                    Com_SetXInsertMode(isInsertMode);
                                    putCCReal(framePtr + 16L, Stack_GetX());
                                    Com_SetResult(framePtr + 16L, 1L);
                                    result = 99L;
                                    return result;
                                }
                                if (result == 44L) {
                                    key = 25L;
                                } else {
                                    if (result != 45L) {
                                        putI64(pCodeTable, ADDR_RODATA + 7704L);
                                        result = Base_HandleKeyDown(getI32(ADDR_RODATA + 7704L + (stepCode & 0xffL) * 8L), 1L) & 0xffffffffL;
                                        return result;
                                    }
                                    key = 29L;
                                }
                                result = Base_HandleKeyDown(key, 1L) & 0xffffffffL;
                                if (result == 99L) {
                                    result = Prog_ExecReg(1L) & 0xffffffffL;
                                }
                                return result;
                            }
                            if (21L < ((result - 111L) & 0xffffffffL)) {
                                putI64(pCodeTable, ADDR_RODATA + 7704L);
                                result = Base_HandleKeyDown(getI32(ADDR_RODATA + 7704L + (stepCode & 0xffL) * 8L), 1L) & 0xffffffffL;
                                return result;
                            }
                            tempVal = (stepCode >> 8) & 0xffffL;
                            switch ((int) (result)) {
                                case 111: {
                                    Com_EndEditMode_CareOP();
                                    isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                    Com_SetXInsertMode(isInsertMode);
                                    putCCReal(framePtr + 16L, Stack_GetX());
                                    Com_SetResult(framePtr + 16L, 1L);
                                    flag = 0L;
                                    do {
                                        TimerDriver_WaitForTime(300L);
                                        key = (long) (int) KeypadDriver_GetKey();
                                        if (4L < flag) {
                                            putCCReal(framePtr + 16L, Stack_GetX());
                                            Com_SetResult(framePtr + 16L, 1L);
                                            result = 99L;
                                            return result;
                                        }
                                        flag = (flag + 1L) & 0xffL;
                                    } while (key == 0L);
                                    putCCReal(framePtr + 16L, Stack_GetX());
                                    Com_SetResult(framePtr + 16L, 1L);
                                    result = 99L;
                                    return result;
                                }
                                case 123: {
                                    ComKeyCheckState(0L);
                                    ComKeyPreHandle(0L);
                                    Com_EndEditMode_CareOP();
                                    isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                    Com_SetXInsertMode(isInsertMode);
                                    putCCReal(framePtr + 16L, Stack_GetX());
                                    Com_SetResult(framePtr + 16L, 1L);
                                    putU16(g_prog_currpos, 0xffffL);
                                    result = 99L;
                                    return result;
                                }
                                case 130: {
                                    ComKeyCheckState(0L);
                                    ComKeyPreHandle(0L);
                                    Com_EndEditMode_CareOP();
                                    isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                    Com_SetXInsertMode(isInsertMode);
                                    if (getU16(g_prog_totalstep_num) >= tempVal) {
                                        putU16(g_prog_currpos, tempVal - 1L);
                                        putCCReal(framePtr + 16L, Stack_GetX());
                                        Com_SetResult(framePtr + 16L, 1L);
                                        result = 99L;
                                        return result;
                                    }
                                    break;
                                }
                                case 131: {
                                    ComKeyCheckState(0L);
                                    ComKeyPreHandle(0L);
                                    Com_EndEditMode_CareOP();
                                    isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                    Com_SetXInsertMode(isInsertMode);
                                    tempVal = ((tempVal | 256L)) & 0xffffL;
                                    if (tempVal <= getU16(g_prog_totalstep_num)) {
                                        putU16(g_prog_currpos, tempVal - 1L);
                                        putCCReal(framePtr + 16L, Stack_GetX());
                                        Com_SetResult(framePtr + 16L, 1L);
                                        result = 99L;
                                        return result;
                                    }
                                    break;
                                }
                                case 132: {
                                    ComKeyCheckState(0L);
                                    ComKeyPreHandle(0L);
                                    Com_EndEditMode_CareOP();
                                    isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                    Com_SetXInsertMode(isInsertMode);
                                    tempVal = ((tempVal | 512L)) & 0xffffL;
                                    if (tempVal <= getU16(g_prog_totalstep_num)) {
                                        putU16(g_prog_currpos, tempVal - 1L);
                                        putCCReal(framePtr + 16L, Stack_GetX());
                                        Com_SetResult(framePtr + 16L, 1L);
                                        result = 99L;
                                        return result;
                                    }
                                    break;
                                }
                                default: {
                                    putI64(pCodeTable, ADDR_RODATA + 7704L);
                                    result = Base_HandleKeyDown(getI32(ADDR_RODATA + 7704L + (stepCode & 0xffL) * 8L), 1L) & 0xffffffffL;
                                    return result;
                                }
                            }
                            result = 4L;
                            return result;
                        }
                        Com_EndEditMode_CareOP();
                        isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                        Com_SetXInsertMode(isInsertMode);
                        KeypadDriver_ClearKeyBuffer();
                        putU8(g_com_interruptbykey, 0L);
                        Com_StartFlashRunning();
                        step = 1;
                        continue stepLoop;
                    }
                    case 1: {
                        if (getU16(g_prog_currpos) == 0L) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            stepCode = GetProgData(getU16(g_prog_currpos)) & 0xffffL;
                            result = stepCode & 0xffL;
                            putU8(g_com_result_overmax, 0L);
                            if (result < 111L) {
                                if (result != 31L) {
                                    if (result == 44L) {
                                        key = 25L;
                                        step = 4;
                                        continue stepLoop;
                                    } else {
                                        if (result != 45L) {
                                            step = 5;
                                            continue stepLoop;
                                        } else {
                                            key = 29L;
                                            step = 4;
                                            continue stepLoop;
                                        }
                                    }
                                } else {
                                    ComKeyCheckState(12L);
                                    ComKeyPreHandle(12L);
                                    Com_EndEditMode_CareOP();
                                    isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                    Com_SetXInsertMode(isInsertMode);
                                    isInsertMode = 1L;
                                    step = 8;
                                    continue stepLoop;
                                }
                            } else {
                                if (((result - 111L) & 0xffffffffL) < 22L) {
                                    tempVal = (stepCode >> 8) & 0xffffL;
                                    switch ((int) (result)) {
                                        case 111: {
                                            ComKeyCheckState(140L);
                                            ComKeyPreHandle(140L);
                                            Com_EndEditMode_CareOP();
                                            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                            Com_SetXInsertMode(isInsertMode);
                                            isInsertMode = 0L;
                                            putI8(g_prog_bPause, 1L);
                                            step = 8;
                                            continue stepLoop;
                                        }
                                        case 123: {
                                            ComKeyCheckState(0L);
                                            ComKeyPreHandle(0L);
                                            Com_EndEditMode_CareOP();
                                            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                            Com_SetXInsertMode(isInsertMode);
                                            putU16(g_prog_currpos, 0xffffL);
                                            isInsertMode = 0L;
                                            step = 8;
                                            continue stepLoop;
                                        }
                                        case 130: {
                                            ComKeyCheckState(0L);
                                            ComKeyPreHandle(0L);
                                            Com_EndEditMode_CareOP();
                                            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                            Com_SetXInsertMode(isInsertMode);
                                            if (getU16(g_prog_totalstep_num) < tempVal) {
                                                step = 9;
                                                continue stepLoop;
                                            } else {
                                                putU16(g_prog_currpos, tempVal - 1L);
                                                isInsertMode = 0L;
                                                step = 8;
                                                continue stepLoop;
                                            }
                                        }
                                        case 131: {
                                            ComKeyCheckState(0L);
                                            ComKeyPreHandle(0L);
                                            Com_EndEditMode_CareOP();
                                            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                            Com_SetXInsertMode(isInsertMode);
                                            tempVal = ((tempVal | 256L)) & 0xffffL;
                                            if (getU16(g_prog_totalstep_num) < tempVal) {
                                                step = 9;
                                                continue stepLoop;
                                            } else {
                                                step = 10;
                                                continue stepLoop;
                                            }
                                        }
                                        case 132: {
                                            ComKeyCheckState(0L);
                                            ComKeyPreHandle(0L);
                                            Com_EndEditMode_CareOP();
                                            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                                            Com_SetXInsertMode(isInsertMode);
                                            tempVal = ((tempVal | 512L)) & 0xffffL;
                                            if (tempVal <= getU16(g_prog_totalstep_num)) {
                                                step = 10;
                                                continue stepLoop;
                                            } else {
                                                step = 9;
                                                continue stepLoop;
                                            }
                                        }
                                        default: {
                                            step = 5;
                                            continue stepLoop;
                                        }
                                    }
                                } else {
                                    step = 5;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 2: {
                        putI8(g_prog_bPause, 0L);
                        result = 99L;
                        step = 7;
                        continue stepLoop;
                    }
                    case 3: {
                        result = 99L;
                        step = 7;
                        continue stepLoop;
                    }
                    case 4: {
                        result = Base_HandleKeyDown(key, 0L) & 0xffffffffL;
                        if (result == 99L) {
                            result = Prog_ExecReg(0L) & 0xffffffffL;
                            step = 6;
                            continue stepLoop;
                        } else {
                            step = 7;
                            continue stepLoop;
                        }
                    }
                    case 5: {
                        putI64(pCodeTable, ADDR_RODATA + 7704L);
                        if (getI32(ADDR_RODATA + 7704L + (stepCode & 0xffL) * 8L) != 158L) {
                            result = Base_HandleKeyDown(getI32(ADDR_RODATA + 7704L + (stepCode & 0xffL) * 8L), 0L) & 0xffffffffL;
                            step = 6;
                            continue stepLoop;
                        } else {
                            Com_StopFlashRunning();
                            result = Base_HandleKeyDown(getI32(getI64(pCodeTable) + (stepCode & 0xffL) * 8L), 1L) & 0xffffffffL;
                            if (result != 99L) {
                                step = 7;
                                continue stepLoop;
                            } else {
                                TimerDriver_WaitForTime(150L);
                                Com_StartFlashRunning();
                                isInsertMode = 0L;
                                step = 8;
                                continue stepLoop;
                            }
                        }
                    }
                    case 6: {
                        if (result == 99L) {
                            isInsertMode = 0L;
                            step = 8;
                            continue stepLoop;
                        } else {
                            step = 7;
                            continue stepLoop;
                        }
                    }
                    case 7: {
                        Com_StopFlashRunning();
                        if (getU16(g_prog_currpos) != 0L) {
                            Com_EndEditMode_CareOP();
                            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                            Com_SetXInsertMode(isInsertMode);
                        }
                        if (result != 99L) {
                            com_show_errorcode(result);
                            putI8(g_prog_bPause, 0L);
                            return result;
                        }
                        if (getU8(g_undo_index) == 0L) {
                            putCCReal(framePtr + 16L, Stack_GetX());
                            Com_SetResult(framePtr + 16L, 1L);
                            putI8(g_prog_bPause, 0L);
                            return result;
                        }
                        offset = getU8(g_undo_list + (long) (int) (getU8(g_undo_index) - 1L) * 24L) & 63L;
                        if (1L < ((offset - 2L) & 0xffffffffL) && (getU8(g_undo_list + (long) (int) (getU8(g_undo_index) - 1L) * 24L) & 63L) != 0L) {
                            if (offset == 1L) {
                                Basic_DisableUndoMode();
                            }
                            putCCReal(framePtr + 16L, Stack_GetX());
                            Com_SetResult(framePtr + 16L, 1L);
                            putI8(g_prog_bPause, 0L);
                            return result;
                        } else {
                            putCCReal(framePtr + 16L, new CC_Real(1L, 0L, 0L));
                            Com_SetResult(framePtr + 16L, 1L);
                            putI8(g_prog_bPause, 0L);
                            return result;
                        }
                    }
                    case 8: {
                        if (getU8(g_com_result_overmax) != 0L) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            putU16(g_prog_currpos, getU16(g_prog_currpos) + 1L);
                            if (getU16(g_prog_totalstep_num) < (short) getU16(g_prog_currpos)) {
                                ComKeyCheckState(0L);
                                ComKeyPreHandle(0L);
                                Com_EndEditMode_CareOP();
                                if (getI32(g_com_operationmode) == 1L) {
                                    Com_SetXInsertMode(1L);
                                }
                                putU16(g_prog_currpos, 0L);
                                result = 99L;
                                step = 7;
                                continue stepLoop;
                            } else {
                                if (isInsertMode != 0L) {
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    if (getI8(g_prog_bPause) == 0L) {
                                        result = Com_TestKeyPressed() & 0xffffffffL;
                                        if (result != 0L || getU8(g_com_interruptbykey) != 0L) {
                                            step = 2;
                                            continue stepLoop;
                                        } else {
                                            step = 1;
                                            continue stepLoop;
                                        }
                                    } else {
                                        Com_StopFlashRunning();
                                        putCCReal(framePtr + 16L, Stack_GetX());
                                        Com_SetResult(framePtr + 16L, 1L);
                                        flag = 0L;
                                        step = 11;
                                        continue stepLoop;
                                    }
                                }
                            }
                        }
                    }
                    case 9: {
                        result = 4L;
                        step = 7;
                        continue stepLoop;
                    }
                    case 10: {
                        putU16(g_prog_currpos, tempVal - 1L);
                        isInsertMode = 0L;
                        step = 8;
                        continue stepLoop;
                    }
                    case 11: {
                        TimerDriver_WaitForTime(300L);
                        result = Com_TestKeyPressed() & 0xffffffffL;
                        if (4L < flag) {
                            step = 12;
                            continue stepLoop;
                        } else {
                            flag = (flag + 1L) & 0xffL;
                            if (result == 0L) {
                                step = 11;
                                continue stepLoop;
                            } else {
                                step = 12;
                                continue stepLoop;
                            }
                        }
                    }
                    case 12: {
                        if (result != 0L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            putI8(g_prog_bPause, 0L);
                            Com_StartFlashRunning();
                            if (getU8(g_com_interruptbykey) == 0L) {
                                step = 1;
                                continue stepLoop;
                            } else {
                                step = 2;
                                continue stepLoop;
                            }
                        }
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long Prog_JudgeLastStep() {
        long result = 0L;
        if (getU16(g_prog_totalstep_num) > getU16(g_prog_currpos)) {
            return 0L;
        }
        ComKeyCheckState(0L);
        ComKeyPreHandle(0L);
        Com_EndEditMode_CareOP();
        result = 0L;
        if (getI32(g_com_operationmode) == 1L) {
            result = Com_SetXInsertMode(1L);
        }
        putU16(g_prog_currpos, 0L);
        return 0L;
    }

    void Prog_ClearVal() {
        putI8(g_prog_nKey, 0L);
        putI8(g_prog_nDot, 0L);
        putU8(g_prog_TempCount, 0L);
        putU32(g_prog_TempBuf, 0L);
        putU8(g_prog_TempBuf + 4L, 0L);
    }

    long Prog_HandleKeyUp(long keyCode) {
        long stepCode = 0L;
        if ((long) (int) keyCode == 170L) {
            TimerDriver_WaitForTime(100L);
            KeypadDriver_ClearKeyBuffer();
            putI32(g_com_keydown_state, 6L);
            Com_PrintWithSign(0x16001eL, 0L, 0L);
            stepCode = Prog_Get1Line();
            return stepCode;
        } else {
            return keyCode & 0xffffffffL;
        }
    }

    long Prog_GetSpecKeyState(long keyCode) {
        long tempVal = 0L;
        if (getI8(g_prog_nKey) == 1L && getI8(g_prog_nDot) == 0L) {
            if (((keyCode - 10L) & 0xffffffffL) >= 40L) {
                tempVal = 0L;
                return (((((keyCode - 34L) & 0xffffffffL) < 11L ? 1L : 0L) | tempVal)) & 0xffffffffL;
            }
            tempVal = (0xf000211111L >> (int) ((keyCode - 10L) & 63L)) & 1L;
        } else {
            tempVal = 0L;
            if (getI8(g_prog_nKey) != 2L || getI8(g_prog_nDot) != 0L) {
                return (((((keyCode - 34L) & 0xffffffffL) < 11L ? 1L : 0L) | tempVal)) & 0xffffffffL;
            }
            tempVal = 1L;
            switch ((int) (keyCode)) {
                case 10: case 14: case 18: case 22: case 26: case 138: case 139: case 140: case 142: case 143: case 146: case 147: case 148: case 150: case 151: case 152: case 154: case 155: case 156: case 158: case 159: case 162: case 163: case 164: case 165: case 166: case 167: case 168: case 169: case 170: case 171: case 172: case 173: case 174: case 175: case 176: case 177: {
                    break;
                }
                default: {
                    tempVal = 0L;
                    return (((((keyCode - 34L) & 0xffffffffL) < 11L ? 1L : 0L) | tempVal)) & 0xffffffffL;
                }
            }
        }
        return (((((keyCode - 34L) & 0xffffffffL) < 11L ? 1L : 0L) | tempVal)) & 0xffffffffL;
    }

    long Prog_SetRegLine() {
        long tempVal = 0L;
        long result = 0L;
        long byteVal = 0L;
        long flag = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(128);
        try {
            tempVal = Prog_ApplyNewPos();
            if ((tempVal & 1L) == 0L) {
                result = 4L;
                return result;
            }
            if (getI8(g_prog_nKey) != 2L) {
                byteVal = 44L;
            } else {
                switch ((int) (getU8(g_prog_TempBuf))) {
                    case 91: case 92: case 94: case 95: case 111: {
                        byteVal = 45L;
                        break;
                    }
                    case 148: {
                        byteVal = 33L;
                        flag = 0xffL;
                        putU16(framePtr + 16L, (flag << 8) | (byteVal & 0xffL));
                        SetProgData(getU16(g_prog_currpos), framePtr + 16L);
                        Prog_Get1Line();
                        result = 99L;
                        return result;
                    }
                    case 163: {
                        byteVal = 4L;
                        break;
                    }
                    case 167: {
                        byteVal = 5L;
                        break;
                    }
                    default: {
                        if (15L < getU8(g_prog_TempBuf) || getU8(g_prog_TempBuf) == 10L) {
                            byteVal = (long) (byte) (getU8(g_prog_TempBuf) + 176L);
                            flag = 0xffL;
                            putU16(framePtr + 16L, (flag << 8) | (byteVal & 0xffL));
                            SetProgData(getU16(g_prog_currpos), framePtr + 16L);
                            Prog_Get1Line();
                            result = 99L;
                            return result;
                        }
                        byteVal = 45L;
                        break;
                    }
                }
            }
            switch ((int) (getI8(g_prog_nDot))) {
                case 1: case 2: {
                    flag = (getU8(g_prog_TempBuf) + 10L) & 0xffL;
                    break;
                }
                case 4: {
                    flag = (getU8(g_prog_TempBuf) + 140L) & 0xffL;
                    break;
                }
                case 5: {
                    flag = (getU8(g_prog_TempBuf) + 130L) & 0xffL;
                    break;
                }
                case 6: {
                    flag = (getU8(g_prog_TempBuf) + 120L) & 0xffL;
                    break;
                }
                case 7: {
                    flag = (getU8(g_prog_TempBuf) + 110L) & 0xffL;
                    break;
                }
                default: {
                    flag = (getU8(g_prog_TempBuf) + 10L) & 0xffL;
                    if (4L < (long) (int) (getU8(g_prog_TempBuf) - 11L)) {
                        flag = getU8(g_prog_TempBuf);
                    }
                    break;
                }
            }
            putU16(framePtr + 16L, (flag << 8) | (byteVal & 0xffL));
            SetProgData(getU16(g_prog_currpos), framePtr + 16L);
            Prog_Get1Line();
            result = 99L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Prog_SetGTOLine() {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long result = 0L;
        long tempVal4 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(128);
        try {
            tempVal2 = (getU8(g_prog_TempBuf + 1L) + getU8(g_prog_TempBuf) * 10L) & 0xffffL;
            tempVal1 = (getU8(g_prog_TempBuf + 2L) + tempVal2 * 10L) & 0xffffL;
            if ((getI32(g_com_machine_mode) & 0xfffffffdL) != 0L) {
                tempVal1 = tempVal2;
            }
            if (getI8(g_prog_nDot) == 3L) {
                if (getU16(g_prog_totalstep_num) < tempVal1) {
                    result = 4L;
                    return result;
                }
            } else {
                if (getU16(g_prog_maxstep) <= tempVal1 || comma(tempVal3 = Prog_ApplyNewPos(), (tempVal3 & 1L) == 0L ? 1L : 0L) != 0L) {
                    result = 4L;
                    return result;
                }
                if (tempVal1 == 0L) {
                    putU16(framePtr + 16L, 65403L);
                } else {
                    if (tempVal1 < 256L) {
                        tempVal4 = 130L;
                    } else {
                        if (tempVal1 < 512L) {
                            tempVal4 = 131L;
                        } else {
                            tempVal4 = 132L;
                        }
                    }
                    putU16(framePtr + 16L, (((tempVal1 & 0xffL)) << 8) | tempVal4);
                }
                SetProgData(getU16(g_prog_currpos), framePtr + 16L);
                tempVal1 = getU16(g_prog_currpos);
            }
            putU16(g_prog_currpos, tempVal1);
            Prog_Get1Line();
            result = 99L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Prog_HandleSpecKey(long keyCode) {
        long flag1 = 0L;
        long flag2 = 0L;
        long annunciator = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long dataPtr = 0L;
        long flag3 = 0L;
        long result = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    tempVal1 = Prog_GetSpecKeyState((long) (int) keyCode) & 0xffffffffL;
                    flag1 = getI8(g_prog_nKey) & 0xffL;
                    if ((tempVal1 & 1L) == 0L) {
                        return 99L;
                    }
                    tempVal1 = getI8(g_prog_nKey) & 0xffffffffL;
                    if (getI8(g_prog_nKey) == 1L) {
                        switch ((int) (keyCode)) {
                            case 46: {
                                tempVal3 = 7L;
                                step = 1;
                                continue stepLoop;
                            }
                            case 47: {
                                tempVal3 = 6L;
                                step = 1;
                                continue stepLoop;
                            }
                            case 48: {
                                tempVal3 = 5L;
                                step = 1;
                                continue stepLoop;
                            }
                            case 49: {
                                tempVal3 = 4L;
                                step = 1;
                                continue stepLoop;
                            }
                            default: {
                                step = 2;
                                continue stepLoop;
                            }
                        }
                    } else {
                        if (getI8(g_prog_nKey) == 2L) {
                            if ((long) (int) keyCode == 148L) {
                                tempVal2 = 148L;
                                step = 3;
                                continue stepLoop;
                            } else {
                                if ((long) (int) keyCode == 163L) {
                                    tempVal2 = 163L;
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    if ((long) (int) keyCode != 167L) {
                                        putU8(g_prog_TempCount, 0L);
                                        putI64(pCodeTable, ADDR_RODATA + 7704L);
                                        flag3 = 0L;
                                        step = 4;
                                        continue stepLoop;
                                    } else {
                                        tempVal2 = 167L;
                                        step = 3;
                                        continue stepLoop;
                                    }
                                }
                            }
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                }
                case 1: {
                    putU8(g_prog_TempCount, 0L);
                    putI8(g_prog_nDot, tempVal3);
                    result = 99L;
                    step = 6;
                    continue stepLoop;
                }
                case 2: {
                    putI64(pCodeTable, ADDR_RODATA + 7704L);
                    flag3 = 0L;
                    step = 4;
                    continue stepLoop;
                }
                case 3: {
                    result = getU8(g_prog_TempCount);
                    putU8(g_prog_TempCount, 0L);
                    putU8(g_prog_TempBuf + result, tempVal2);
                    tempVal3 = getI8(g_prog_nDot) & 0xffffffffL;
                    result = 99L;
                    step = 6;
                    continue stepLoop;
                }
                case 4: {
                    dataPtr = getI64(pCodeTable) + 8L;
                    if (getI32(getI64(pCodeTable)) == (long) (int) keyCode) {
                        if ((long) (int) keyCode == 41L) {
                            if (getI8(g_prog_nDot) < 3L) {
                                putI8(g_prog_nDot, getI8(g_prog_nKey));
                                result = 99L;
                                tempVal3 = tempVal1;
                                step = 6;
                                continue stepLoop;
                            } else {
                                step = 5;
                                continue stepLoop;
                            }
                        } else {
                            if (tempVal1 != 1L || getI8(g_prog_nDot) < 3L || 43L < (keyCode & 0xffffffffL) || ((1L << (int) (keyCode & 63L)) & 0xcc400000000L) == 0L) {
                                putU8(g_prog_TempBuf + getU8(g_prog_TempCount), getU8(getI64(pCodeTable) + 4L));
                                putU8(g_prog_TempCount, getU8(g_prog_TempCount) + 1L);
                                tempVal3 = getI8(g_prog_nDot) & 0xffffffffL;
                                result = 99L;
                                step = 6;
                                continue stepLoop;
                            } else {
                                step = 5;
                                continue stepLoop;
                            }
                        }
                    } else {
                        flag2 = flag3 < 137L ? 1L : 0L;
                        flag3 = (flag3 + 1L) & 0xffL;
                        putI64(pCodeTable, dataPtr);
                        if (flag2 != 0L) {
                            step = 4;
                            continue stepLoop;
                        } else {
                            tempVal3 = getI8(g_prog_nDot) & 0xffffffffL;
                            result = 99L;
                            step = 6;
                            continue stepLoop;
                        }
                    }
                }
                case 5: {
                    tempVal3 = getI8(g_prog_nDot) & 0xffffffffL;
                    result = 4L;
                    step = 6;
                    continue stepLoop;
                }
                case 6: {
                    if (tempVal1 == 1L && tempVal3 != 0L && getU8(g_prog_TempCount) == 0L) {
                        return result;
                    }
                    if (flag1 == 2L && tempVal3 == 2L && getU8(g_prog_TempCount) == 0L) {
                        return result;
                    }
                    if (tempVal3 != 3L && flag1 == 3L && getU8(g_prog_TempCount) < 3L && (getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
                        return result;
                    }
                    if (flag1 == 3L && getU8(g_prog_TempCount) < 2L && tempVal3 != 3L && getI32(g_com_machine_mode) == 1L) {
                        return result;
                    }
                    if (tempVal3 == 3L && getU8(g_prog_TempCount) < 3L && (getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
                        return result;
                    }
                    if (tempVal3 == 3L && getU8(g_prog_TempCount) < 2L && getI32(g_com_machine_mode) == 1L) {
                        return result;
                    }
                    if (((tempVal1 - 1L) & 0xffffffffL) < 2L) {
                        if ((long) (int) keyCode == 31L && flag1 == 1L) {
                            annunciator = (long) (byte) LcdDriver_GetAnnunciatorState(9L);
                            if ((byte) annunciator == 0L) {
                                LcdDriver_SetAnnunciator(9L);
                                putI8(g_fin_compoundinterest, 1L);
                            } else {
                                LcdDriver_ClearAnnunciator(9L);
                                putI8(g_fin_compoundinterest, 0L);
                            }
                        } else {
                            result = Prog_SetRegLine();
                            result = result & 0xffffffffL;
                        }
                    }
                    if (getU8(g_prog_TempCount) < 3L || getI8(g_prog_nKey) != 3L) {
                        if (getU8(g_prog_TempCount) < 2L) {
                            putI8(g_prog_nKey, 0L);
                            putI8(g_prog_nDot, 0L);
                            putU8(g_prog_TempCount, 0L);
                            putU32(g_prog_TempBuf, 0L);
                            putU8(g_prog_TempBuf + 4L, 0L);
                            return result;
                        }
                    } else {
                        if ((getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
                            result = Prog_SetGTOLine();
                            putU8(g_prog_TempBuf + 4L, 0L);
                            putU32(g_prog_TempBuf, 0L);
                            putU8(g_prog_TempCount, 0L);
                            putI8(g_prog_nDot, 0L);
                            putI8(g_prog_nKey, 0L);
                            return result & 0xffffffffL;
                        }
                    }
                    if (getI8(g_prog_nKey) != 3L) {
                        putI8(g_prog_nKey, 0L);
                        putI8(g_prog_nDot, 0L);
                        putU8(g_prog_TempCount, 0L);
                        putU32(g_prog_TempBuf, 0L);
                        putU8(g_prog_TempBuf + 4L, 0L);
                        return result;
                    }
                    if (getI32(g_com_machine_mode) != 1L) {
                        putI8(g_prog_nKey, 0L);
                        putI8(g_prog_nDot, 0L);
                        putU8(g_prog_TempCount, 0L);
                        putU32(g_prog_TempBuf, 0L);
                        putU8(g_prog_TempBuf + 4L, 0L);
                        return result;
                    }
                    result = Prog_SetGTOLine();
                    putU8(g_prog_TempBuf + 4L, 0L);
                    putU32(g_prog_TempBuf, 0L);
                    putU8(g_prog_TempCount, 0L);
                    putI8(g_prog_nDot, 0L);
                    putI8(g_prog_nKey, 0L);
                    return result & 0xffffffffL;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long Prog_HandleKeyDown(long keyCode) {
        long tempVal1 = 0L;
        long stepCode = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long byteVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        tempVal1 = keyCode & 0xffffffffL;
                        if (getI32(g_com_machine_mode) == 2L) {
                            tempVal3 = 25L;
                            switch ((int) (keyCode)) {
                                case 153: {
                                    step = 5;
                                    continue stepLoop;
                                }
                                case 157: {
                                    step = 2;
                                    continue stepLoop;
                                }
                                case 160: {
                                    tempVal3 = 32L;
                                    step = 5;
                                    continue stepLoop;
                                }
                                case 174: {
                                    if (getI8(g_prog_nKey) == 1L) {
                                        tempVal3 = 46L;
                                        step = 3;
                                        continue stepLoop;
                                    } else {
                                        tempVal3 = 46L;
                                        step = 5;
                                        continue stepLoop;
                                    }
                                }
                                case 176: {
                                    step = 4;
                                    continue stepLoop;
                                }
                                default: {
                                    step = 1;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            if (getI32(g_com_machine_mode) == 1L) {
                                tempVal3 = 49L;
                                switch ((int) (keyCode)) {
                                    case 153: {
                                        tempVal3 = 25L;
                                        step = 5;
                                        continue stepLoop;
                                    }
                                    case 154: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 155: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 156: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 158: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 159: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 160: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 161: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 162: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 163: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 164: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 165: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 166: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 167: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 168: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 169: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 170: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 171: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 172: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 173: {
                                        step = 1;
                                        continue stepLoop;
                                    }
                                    case 157: {
                                        step = 2;
                                        continue stepLoop;
                                    }
                                    case 174: {
                                        step = 4;
                                        continue stepLoop;
                                    }
                                    case 176: {
                                        step = 4;
                                        continue stepLoop;
                                    }
                                    case 175: {
                                        tempVal3 = 47L;
                                        step = 5;
                                        continue stepLoop;
                                    }
                                    case 177: {
                                        step = 5;
                                        continue stepLoop;
                                    }
                                    default: {
                                        if ((long) (int) keyCode == 94L) {
                                            tempVal3 = 30L;
                                            step = 5;
                                            continue stepLoop;
                                        } else {
                                            if ((long) (int) keyCode != 95L) {
                                                step = 1;
                                                continue stepLoop;
                                            } else {
                                                tempVal3 = 31L;
                                                step = 5;
                                                continue stepLoop;
                                            }
                                        }
                                    }
                                }
                            } else {
                                tempVal1 = 32L;
                                if ((long) (int) keyCode != 160L) {
                                    tempVal1 = keyCode & 0xffffffffL;
                                }
                                step = 1;
                                continue stepLoop;
                            }
                        }
                    }
                    case 1: {
                        tempVal3 = tempVal1;
                        if (getI8(g_prog_nKey) == 2L && ((((tempVal3 & 0xffffffffL)) >> 7) & 1L) != 0L) {
                            if (((tempVal3 - 144L) & 0xffffffffL) < 27L && ((1L << (int) ((tempVal3 - 144L) & 31L)) & 0x4012201L) != 0L) {
                                putI8(g_prog_nKey, 0L);
                                putI8(g_prog_nDot, 0L);
                                putU8(g_prog_TempCount, 0L);
                                tempVal3 = tempVal3 & 0xffffff7fL;
                                putU32(g_prog_TempBuf, 0L);
                                putU8(g_prog_TempBuf + 4L, 0L);
                                putI32(g_com_shiftmode, 0L);
                                LcdDriver_ClearAnnunciator(6L);
                            }
                        }
                        step = 5;
                        continue stepLoop;
                    }
                    case 2: {
                        tempVal3 = 29L;
                        step = 5;
                        continue stepLoop;
                    }
                    case 3: {
                        putU8(g_prog_TempCount, 0L);
                        putI8(g_prog_nDot, 0L);
                        putI8(g_prog_nKey, 0L);
                        putU32(g_prog_TempBuf, 0L);
                        putU8(g_prog_TempBuf + 4L, 0L);
                        step = 5;
                        continue stepLoop;
                    }
                    case 4: {
                        if (getI8(g_prog_nKey) == 1L) {
                            tempVal3 = 48L;
                            step = 3;
                            continue stepLoop;
                        } else {
                            tempVal3 = 48L;
                            step = 5;
                            continue stepLoop;
                        }
                    }
                    case 5: {
                        byteVal = getI8(g_prog_nKey);
                        if (getI8(g_prog_nKey) != 0L) {
                            if ((long) (int) keyCode == 41L && ((getI8(g_prog_nDot) - 1L) & 0xffL) < 2L) {
                                putI8(g_prog_nDot, 0L);
                                putU8(g_prog_TempCount, 0L);
                                putI8(g_prog_nKey, 0L);
                            }
                            byteVal = getI8(g_prog_nKey);
                            tempVal1 = Prog_GetSpecKeyState((long) (int) tempVal3) & 0xffffffffL;
                            if ((tempVal1 & 1L) == 0L) {
                                putI8(g_prog_nDot, 0L);
                                putU8(g_prog_TempCount, 0L);
                                byteVal = 0L;
                                putI8(g_prog_nKey, 0L);
                                putU32(g_prog_TempBuf, 0L);
                                putU8(g_prog_TempBuf + 4L, 0L);
                            }
                        }
                        switch ((int) (tempVal3)) {
                            case 16: {
                                tempVal2 = 0L;
                                if ((short) (getU16(g_prog_currpos) + 1L) <= getU16(g_prog_totalstep_num)) {
                                    tempVal2 = (getU16(g_prog_currpos) + 1L) & 0xffffL;
                                }
                                step = 7;
                                continue stepLoop;
                            }
                            case 17: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 18: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 19: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 20: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 21: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 22: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 23: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 24: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 26: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 27: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 28: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 30: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 31: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 32: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 33: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 34: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 35: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 36: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 37: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 38: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 39: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 40: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 41: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 42: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 43: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 44: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 45: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 46: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 47: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 48: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 49: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 50: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 51: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 52: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 53: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 54: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 55: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 56: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 57: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 58: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 59: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 60: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 61: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 62: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 63: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 64: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 65: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 66: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 67: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 68: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 69: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 70: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 71: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 72: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 73: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 74: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 75: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 77: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 78: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 79: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 80: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 81: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 82: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 83: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 85: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 86: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 87: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 88: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 90: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 91: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 94: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 95: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 25: {
                                step = 9;
                                continue stepLoop;
                            }
                            case 89: {
                                step = 9;
                                continue stepLoop;
                            }
                            case 29: {
                                step = 11;
                                continue stepLoop;
                            }
                            case 93: {
                                step = 11;
                                continue stepLoop;
                            }
                            case 76: {
                                putI8(g_prog_nDot, 0L);
                                putI8(g_prog_nKey, 0L);
                                putU8(g_prog_TempCount, 0L);
                                putU32(g_prog_TempBuf, 0L);
                                putU8(g_prog_TempBuf + 4L, 0L);
                                if (getI32(g_com_uimode) != 0L) {
                                    LcdDriver_ClearAnnunciator(10L);
                                    putI32(g_com_uimode, 0L);
                                    putU16(g_prog_currpos, 0L);
                                    putCCReal(framePtr + 24L, Stack_GetX());
                                    Com_SetResult(framePtr + 24L, 1L);
                                    stepCode = 99L;
                                } else {
                                    putI32(g_com_uimode, 1L);
                                    LcdDriver_SetAnnunciator(10L);
                                    if (getU16(g_prog_currpos) == 0L) {
                                        Prog_PrintProgTop();
                                        stepCode = 99L;
                                    } else {
                                        Prog_Get1Line();
                                        stepCode = 99L;
                                    }
                                }
                                step = 6;
                                continue stepLoop;
                            }
                            case 84: {
                                putI8(g_prog_nDot, 0L);
                                putU8(g_prog_TempCount, 0L);
                                putI8(g_prog_nKey, 0L);
                                putU32(g_prog_TempBuf, 0L);
                                putU8(g_prog_TempBuf + 4L, 0L);
                                Prog_ClearProg();
                                stepCode = 99L;
                                step = 6;
                                continue stepLoop;
                            }
                            case 92: {
                                step = 12;
                                continue stepLoop;
                            }
                            case 96: {
                                step = 12;
                                continue stepLoop;
                            }
                            default: {
                                switch ((int) (tempVal3)) {
                                    case 144: {
                                        tempVal2 = getU16(g_prog_totalstep_num);
                                        if (getU16(g_prog_currpos) != 0L) {
                                            tempVal2 = (getU16(g_prog_currpos) - 1L) & 0xffffL;
                                        }
                                        step = 7;
                                        continue stepLoop;
                                    }
                                    case 148: {
                                        if ((byte) byteVal == 2L) {
                                            stepCode = Prog_HandleSpecKey(148L);
                                            step = 6;
                                            continue stepLoop;
                                        } else {
                                            putI8(g_prog_nKey, 3L);
                                            step = 10;
                                            continue stepLoop;
                                        }
                                    }
                                    case 163: {
                                        if ((byte) byteVal == 2L) {
                                            stepCode = Prog_HandleSpecKey(163L);
                                        } else {
                                            putI8(g_prog_nDot, 0L);
                                            putU8(g_prog_TempCount, 0L);
                                            putI8(g_prog_nKey, 0L);
                                            putU32(g_prog_TempBuf, 0L);
                                            putU8(g_prog_TempBuf + 4L, 0L);
                                            LcdDriver_SetAnnunciator(8L);
                                            stepCode = 99L;
                                            putI32(g_com_datemode, 1L);
                                        }
                                        step = 6;
                                        continue stepLoop;
                                    }
                                    case 167: {
                                        if ((byte) byteVal == 2L) {
                                            stepCode = Prog_HandleSpecKey(167L);
                                        } else {
                                            putI8(g_prog_nDot, 0L);
                                            putU8(g_prog_TempCount, 0L);
                                            putI8(g_prog_nKey, 0L);
                                            putU32(g_prog_TempBuf, 0L);
                                            putU8(g_prog_TempBuf + 4L, 0L);
                                            LcdDriver_ClearAnnunciator(8L);
                                            stepCode = 99L;
                                            putI32(g_com_datemode, 0L);
                                        }
                                        step = 6;
                                        continue stepLoop;
                                    }
                                    case 170: {
                                        putI8(g_prog_nDot, 0L);
                                        putU8(g_prog_TempCount, 0L);
                                        putI8(g_prog_nKey, 0L);
                                        putU32(g_prog_TempBuf, 0L);
                                        putU8(g_prog_TempBuf + 4L, 0L);
                                        Prog_GetMemoryState();
                                        stepCode = 99L;
                                        step = 6;
                                        continue stepLoop;
                                    }
                                    default: {
                                        step = 8;
                                        continue stepLoop;
                                    }
                                }
                            }
                        }
                    }
                    case 6: {
                        tempVal1 = stepCode & 0xffffffffL;
                        if (tempVal1 != 99L && getI8(g_prog_nDot) != 3L || tempVal1 != 99L && getI8(g_prog_nDot) == 3L && 3L < getU8(g_prog_TempCount)) {
                            putI8(g_prog_nDot, 0L);
                            putU8(g_prog_TempCount, 0L);
                            putI8(g_prog_nKey, 0L);
                            putU32(g_prog_TempBuf, 0L);
                            putU8(g_prog_TempBuf + 4L, 0L);
                            stepCode = com_show_errorcode(tempVal1);
                        }
                        return stepCode;
                    }
                    case 7: {
                        putU8(g_prog_TempBuf + 4L, 0L);
                        putU32(g_prog_TempBuf, 0L);
                        putU8(g_prog_TempCount, 0L);
                        putI8(g_prog_nDot, 0L);
                        putI8(g_prog_nKey, 0L);
                        putU16(g_prog_currpos, tempVal2);
                        Prog_Get1Line();
                        stepCode = 99L;
                        step = 6;
                        continue stepLoop;
                    }
                    case 8: {
                        if ((byte) byteVal == 0L) {
                            putI8(g_prog_nDot, 0L);
                            putU8(g_prog_TempCount, 0L);
                            putI8(g_prog_nKey, 0L);
                            putU32(g_prog_TempBuf, 0L);
                            putU8(g_prog_TempBuf + 4L, 0L);
                            stepCode = Prog_Set1Line((long) (int) tempVal3);
                        } else {
                            stepCode = Prog_HandleSpecKey((long) (int) tempVal3);
                        }
                        step = 6;
                        continue stepLoop;
                    }
                    case 9: {
                        putI8(g_prog_nKey, 1L);
                        step = 10;
                        continue stepLoop;
                    }
                    case 10: {
                        putI8(g_prog_nDot, 0L);
                        putU8(g_prog_TempCount, 0L);
                        stepCode = 99L;
                        step = 6;
                        continue stepLoop;
                    }
                    case 11: {
                        putI8(g_prog_nKey, 2L);
                        step = 10;
                        continue stepLoop;
                    }
                    case 12: {
                        putI8(g_prog_nDot, 0L);
                        putU8(g_prog_TempCount, 0L);
                        putI8(g_prog_nKey, 0L);
                        stepCode = 99L;
                        putU32(g_prog_TempBuf, 0L);
                        putU8(g_prog_TempBuf + 4L, 0L);
                        step = 6;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    void Prog_Init() {
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            putU16(framePtr + 16L, 65403L);
            SetProgData(0L, framePtr + 16L);
            putU16(framePtr + 16L, 65403L);
            SetProgData(1L, framePtr + 16L);
            putU16(framePtr + 16L, 65403L);
            SetProgData(2L, framePtr + 16L);
            putU16(framePtr + 16L, 65403L);
            SetProgData(3L, framePtr + 16L);
            putU16(framePtr + 16L, 65403L);
            SetProgData(4L, framePtr + 16L);
            putU16(framePtr + 16L, 65403L);
            SetProgData(5L, framePtr + 16L);
            putU16(framePtr + 16L, 65403L);
            SetProgData(6L, framePtr + 16L);
            putU16(framePtr + 16L, 65403L);
            SetProgData(7L, framePtr + 16L);
            putU16(framePtr + 16L, 65403L);
            SetProgData(8L, framePtr + 16L);
            putU16(g_prog_totalstep_num, 8L);
            putU16(g_prog_currpos, 0L);
            putI32(g_com_keydown_state, 6L);
            putI8(g_prog_bPause, 0L);
            Prog_ClearVal();
        } finally {
            sp = savedSp;
        }
    }

    long Prog_GetMemoryState() {
        long tempVal1 = 0L;
        long byteVal = 0L;
        long tempVal2 = 0L;
        long intVal = 0L;
        long flag = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            putI8(framePtr + 32L, 80L);
            putI8(framePtr + 33L, 45L);
            if ((getI32(g_com_machine_mode) & 0xfffffffdL) == 0L) {
                intVal = (short) getU16(g_prog_totalstep_num);
                putI8(framePtr + 34L, intVal / 100L);
                putI8(framePtr + 36L, getU16(g_prog_totalstep_num) + intVal / 10L * -10L + 48L);
                putI8(framePtr + 35L, (long) (int) (intVal % 100L) / 10L);
                putU8(framePtr + 37L, 0L);
            } else {
                putI8(framePtr + 36L, 0L);
                putI8(framePtr + 34L, (short) getU16(g_prog_totalstep_num) / 10L);
                putI8(framePtr + 35L, getU16(g_prog_totalstep_num) + getI8(framePtr + 34L) * -10L);
            }
            putI8(framePtr + 35L, getI8(framePtr + 35L) + 48L);
            putI8(framePtr + 34L, getI8(framePtr + 34L) + 48L);
            Com_PrintWithSign(framePtr + 32L, 0L, 0L);
            intVal = (long) (int) (getU16(g_prog_totalstep_num) - getU16(g_prog_nMemState));
            if (getU16(g_prog_totalstep_num) < getU16(g_prog_nMemState) || intVal == 0L) {
                intVal = 20L;
            } else {
                intVal = (long) (int) ((short) intVal + 6L);
                tempVal1 = (((intVal * 0x6db6db6dL) >>> 32) - intVal) & 0xffffffffL;
                intVal = (short) ((tempVal1 >> 2) - ((long) (int) tempVal1 >> 31) + 20L);
            }
            putU8(framePtr + 24L, 114L);
            putU8(framePtr + 25L, 45L);
            byteVal = (long) (byte) (intVal / 10L);
            flag = 0L;
            putU8(framePtr + 26L, byteVal + 48L);
            tempVal2 = 114L;
            putU8(framePtr + 27L, intVal + byteVal * -10L + 48L);
            putU8(framePtr + 28L, 0L);
            do {
                LcdDriver_PutChar(tempVal2, (flag + 6L) & 0xffL);
                flag = (flag + 1L) & 0xffL;
                tempVal2 = getU8(framePtr + 24L + flag);
            } while (tempVal2 != 0L);
            putI32(g_com_keydown_state, 3L);
            return 0L;
        } finally {
            sp = savedSp;
        }
    }

    long Prog_ClearProg() {
        long flag = 0L;
        long freeCfloCount = 0L;
        long result = 0L;
        long index1 = 0L;
        long index2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            flag = getU8(g_fin_maxcflo);
            freeCfloCount = GetUnusedCfloNum() & 0xffffffffL;
            index1 = 0L;
            do {
                putU16(framePtr + 24L, 65403L);
                SetProgData(index1, framePtr + 24L);
                index1 = (index1 + 1L) & 0xffffL;
            } while (index1 <= getU16(g_prog_totalstep_num));
            putU16(g_prog_totalstep_num, 8L);
            putU16(g_prog_currpos, 0L);
            putI8(g_prog_bPause, 0L);
            putI32(g_com_keydown_state, 6L);
            result = Prog_PrintProgTop();
            freeCfloCount = (flag - freeCfloCount) & 0xffL;
            putI64(framePtr + 48L, 0L);
            putI64(framePtr + 40L, 1L);
            putU16(framePtr + 32L, 1L);
            if (freeCfloCount != 0L) {
                index2 = 0L;
                do {
                    result = SetCfloData((getU8(g_fin_maxcflo) - index2 + 254L) & 0xffL, framePtr + 32L);
                    index2 = (index2 + 1L) & 0xffffffffL;
                } while ((index2 & 0xffffL) < freeCfloCount);
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_R_S(long keyCode) {
        if (getU16(g_prog_currpos) == 0L) {
            putU16(g_prog_currpos, 1L);
        }
        Prog_Get1Line();
        putI32(g_com_keydown_state, 2L);
        return 99L;
    }

    long Base_KeyUp_R_S(long keyCode) {
        long result = 0L;
        if (getI32(g_com_keydown_state) == 2L) {
            KeypadDriver_ClearKeyBuffer();
            result = Prog_Exec(0L) & 0xffffffffL;
            putI32(g_com_keydown_state, 6L);
        } else {
            result = 99L;
        }
        return result;
    }

    long Base_KeyDown_SST(long keyCode) {
        if (getI32(g_com_keydown_state) == 1L) {
            return 99L;
        }
        if (getU16(g_prog_currpos) == 0L) {
            putU16(g_prog_currpos, 1L);
        }
        Prog_Get1Line();
        putI32(g_com_keydown_state, 1L);
        return 99L;
    }

    long Base_KeyUp_SST(long keyCode) {
        long shortVal = 0L;
        long result = 0L;
        if (getI32(g_com_keydown_state) == 1L) {
            KeypadDriver_ClearKeyBuffer();
            Com_RefreshLCDString();
            result = Prog_Exec(1L) & 0xffffffffL;
            putI32(g_com_keydown_state, 6L);
            shortVal = 0L;
            if ((short) (getU16(g_prog_currpos) + 1L) <= getU16(g_prog_totalstep_num)) {
                shortVal = (long) (short) (getU16(g_prog_currpos) + 1L);
            }
        } else {
            result = 99L;
            shortVal = (long) (short) getU16(g_prog_currpos);
        }
        putU16(g_prog_currpos, shortVal);
        return result;
    }

    long Base_KeyDown_BST(long keyCode) {
        long shortVal = 0L;
        long flag = 0L;
        if (getI32(g_com_keydown_state) == 0L) {
            return 99L;
        }
        shortVal = (long) (short) (getU16(g_prog_currpos) - 1L);
        flag = getU16(g_prog_currpos) != 0L ? 1L : 0L;
        putU16(g_prog_currpos, getU16(g_prog_totalstep_num));
        if (flag != 0L) {
            putU16(g_prog_currpos, shortVal);
        }
        Prog_Get1Line();
        putI32(g_com_keydown_state, 0L);
        return 99L;
    }

    long Base_KeyUp_BST(long keyCode) {
        long isInsertMode = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            if (getI32(g_com_keydown_state) == 0L) {
                KeypadDriver_ClearKeyBuffer();
                Com_EndEditMode_IgnoreOP();
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, 1L);
                isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                Com_SetXInsertMode(isInsertMode);
                putI32(g_com_keydown_state, 6L);
            }
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_P_R(long keyCode) {
        long isInsertMode = 0L;
        if (getI32(g_com_uimode) == 0L) {
            Com_EndEditMode_IgnoreOP();
            putI32(g_com_uimode, 1L);
            LcdDriver_SetAnnunciator(10L);
            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
            Com_SetXInsertMode(isInsertMode);
            Prog_Get1Line();
        } else {
            LcdDriver_ClearAnnunciator(10L);
            putI32(g_com_uimode, 0L);
        }
        return 99L;
    }

    long Base_KeyDown_PRGM(long keyCode) {
        long isInsertMode = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            Com_EndEditMode_IgnoreOP();
            isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
            Com_SetXInsertMode(isInsertMode);
            putCCReal(framePtr + 24L, Stack_GetX());
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            putU16(g_prog_currpos, 0L);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_PSE(long keyCode) {
        long isInsertMode = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            if (getI32(g_com_keyinmode) == 2L) {
                status2 = Com_EndCalculateMode();
                if ((long) (int) status2 == 99L) {
                    putCCReal(framePtr + 24L, Stack_GetX());
                    status1 = Fin_IRR(framePtr + 24L, keyCode & 0xffL) & 0xffffffffL;
                    status2 = status1;
                }
            } else {
                Com_EndEditMode_IgnoreOP();
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, keyCode & 0xffL);
                TimerDriver_WaitForTime(100L);
                isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
                Com_SetXInsertMode(isInsertMode);
                status2 = 99L;
                putI32(g_com_keydown_state, 5L);
            }
            return status2;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyUp_PSE(long keyCode) {
        if (getI32(g_com_keydown_state) == 5L) {
            KeypadDriver_ClearKeyBuffer();
            putI32(g_com_keydown_state, 6L);
        }
        return 99L;
    }

    long Base_KeyDown_GTO(long keyCode) {
        long isInsertMode = 0L;
        Com_EndEditMode_IgnoreOP();
        putI32(g_com_keyinmode, 4L);
        putU8(g_gto_state, 0L);
        putU8(g_gto_state + 4L, 0L);
        isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
        Com_SetXInsertMode(isInsertMode & 0xffL);
        return 99L;
    }

    long Base_KeyDown_X_NOTLAGER_Y(long keyCode) {
        long shortVal = 0L;
        long isLessOrEqual = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            Com_EndEditMode_CareOP();
            putCCReal(framePtr + 40L, Stack_GetX());
            putCCReal(framePtr + 24L, Stack_GetY());
            isLessOrEqual = fLE(framePtr + 40L, framePtr + 24L) != 0L ? 1L : 0L;
            shortVal = (long) (short) getU16(g_prog_currpos);
            if (isLessOrEqual == 0L && comma(shortVal = 0L, (short) (getU16(g_prog_currpos) + 1L) <= getU16(g_prog_totalstep_num) ? 1L : 0L) != 0L) {
                shortVal = (long) (short) (getU16(g_prog_currpos) + 1L);
            }
            putU16(g_prog_currpos, shortVal);
            Com_SetResult(framePtr + 40L, keyCode & 0xffL);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_X_EQUAL_0(long keyCode) {
        long shortVal = 0L;
        long isEqual = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            Com_EndEditMode_CareOP();
            putCCReal(framePtr + 24L, Stack_GetX());
            isEqual = fEQ(framePtr + 24L, CC_0) != 0L ? 1L : 0L;
            shortVal = (long) (short) getU16(g_prog_currpos);
            if (isEqual == 0L && comma(shortVal = 0L, (short) (getU16(g_prog_currpos) + 1L) <= getU16(g_prog_totalstep_num) ? 1L : 0L) != 0L) {
                shortVal = (long) (short) (getU16(g_prog_currpos) + 1L);
            }
            putU16(g_prog_currpos, shortVal);
            Com_SetResult(framePtr + 24L, keyCode & 0xffL);
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

    long Base_KeyDown_MEM(long keyCode) {
        long isInsertMode = 0L;
        Com_EndEditMode_IgnoreOP();
        Prog_GetMemoryState();
        isInsertMode = Com_GetXInsertMode() != 0L ? 1L : 0L;
        Com_SetXInsertMode(isInsertMode & 0xffL);
        return 99L;
    }

    long Base_KeyUp_MEM(long keyCode) {
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            if (getI32(g_com_keydown_state) == 3L) {
                TimerDriver_WaitForTime(100L);
                KeypadDriver_ClearKeyBuffer();
                putI32(g_com_keydown_state, 6L);
                putCCReal(framePtr + 24L, Stack_GetX());
                Com_SetResult(framePtr + 24L, 1L);
            }
            return 99L;
        } finally {
            sp = savedSp;
        }
    }

}

package org.liberty.android.classiccalc.engine;

/**
 * 128-bit and BCD floating-point arithmetic, rounding, comparison, and string conversion.
 */
abstract class BcdMathEngine extends CalcMemory {
    long fZero(long realPtr) {
        long flag = 0L;
        if (((getI32(realPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI32(realPtr) + 1L) & 31L)) & 21L) != 0L) {
            flag = getI32(realPtr + 12L) == 0L ? 1L : 0L;
        }
        return flag;
    }

    long dcbMul(long bcdMantissa, long digitMultiplier) {
        long bcdAcc1 = 0L;
        long bcdAcc2 = 0L;
        long bcdAcc3 = 0L;
        if ((digitMultiplier & 1L) != 0L) {
            bcdAcc2 = bcdMantissa;
        }
        bcdAcc1 = dcbAddAdjust(bcdMantissa, bcdMantissa);
        if (((((digitMultiplier & 0xffffffffL)) >> 1) & 1L) != 0L) {
            bcdAcc2 = dcbAddAdjust(bcdAcc2, bcdAcc1);
        }
        bcdAcc1 = dcbAddAdjust(bcdAcc1, bcdAcc1);
        if (((((digitMultiplier & 0xffffffffL)) >> 2) & 1L) != 0L) {
            bcdAcc2 = dcbAddAdjust(bcdAcc2, bcdAcc1);
        }
        if (((((digitMultiplier & 0xffffffffL)) >> 3) & 1L) == 0L) {
            return bcdAcc2;
        } else {
            bcdAcc3 = dcbAddAdjust(bcdAcc2, bcdAcc1);
            bcdAcc2 = dcbAddAdjust(bcdAcc3, bcdAcc1);
            return bcdAcc2;
        }
    }

    long bintobcd(long binaryVal) {
        long intVal = 0L;
        if (0x5f5e100L < (binaryVal & 0xffffffffL)) {
            return -1L;
        }
        if ((binaryVal & 0xffffffffL) < 0x989680L) {
            intVal = 0L;
        } else {
            intVal = (long) (int) (((binaryVal - 0x989680L) & 0xffffffffL) / 0x989680L * 0x10000000L + 0x10000000L);
            binaryVal = (((binaryVal - 0x989680L) & 0xffffffffL) % 0x989680L) & 0xffffffffL;
        }
        if (0xf423fL < (binaryVal & 0xffffffffL)) {
            intVal = (long) (int) (intVal + ((binaryVal - 0xf4240L) & 0xffffffffL) / 0xf4240L * 0x1000000L + 0x1000000L);
            binaryVal = (((binaryVal - 0xf4240L) & 0xffffffffL) % 0xf4240L) & 0xffffffffL;
        }
        if (3124L < ((((binaryVal & 0xffffffffL)) >> 5) & 0xffffffffL)) {
            intVal = (long) (int) (intVal + ((binaryVal - 0x186a0L) & 0xffffffffL) / 0x186a0L * 0x100000L + 0x100000L);
            binaryVal = (((binaryVal - 0x186a0L) & 0xffffffffL) % 0x186a0L) & 0xffffffffL;
        }
        if (624L < ((((binaryVal & 0xffffffffL)) >> 4) & 0xffffffffL)) {
            intVal = (long) (int) (intVal + ((binaryVal - 10000L) & 0xffffffffL) / 10000L * 0x10000L + 0x10000L);
            binaryVal = (((binaryVal - 10000L) & 0xffffffffL) % 10000L) & 0xffffffffL;
        }
        if (999L < (binaryVal & 0xffffffffL)) {
            intVal = (long) (int) (intVal + ((binaryVal - 1000L) & 0xffffffffL) / 1000L * 4096L + 4096L);
            binaryVal = (((binaryVal - 1000L) & 0xffffffffL) % 1000L) & 0xffffffffL;
        }
        if (99L < (binaryVal & 0xffffffffL)) {
            intVal = (long) (int) (intVal + ((binaryVal - 100L) & 0xffffffffL) / 100L * 256L + 256L);
            binaryVal = (((binaryVal - 100L) & 0xffffffffL) % 100L) & 0xffffffffL;
        }
        if ((binaryVal & 0xffffffffL) < 10L) {
            return (long) (int) (intVal + binaryVal);
        } else {
            return (long) (int) (intVal + ((binaryVal - 10L) & 0xffffffffL) / 10L * 6L + 16L + (binaryVal - 10L));
        }
    }

    long dcbtobin(long bcdVal) {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long intVal = 0L;
        if ((long) (int) bcdVal != 0L) {
            tempVal3 = bcdVal & 0xffffffffL;
            bcdVal = 0L;
            intVal = 1L;
            do {
                tempVal2 = tempVal3;
                tempVal1 = (tempVal2 >> 4) & 0xffffffffL;
                tempVal3 = tempVal1;
                bcdVal = (bcdVal + intVal * (tempVal2 & 15L)) & 0xffffffffL;
                intVal = (long) (int) (intVal * 10L);
            } while (tempVal1 != 0L);
        }
        return bcdVal;
    }

    long firadd(long lhsPtr, long rhsPtr, long resultPtr, long isSubtract) {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long flag = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        long mantissa = 0L;
        tempVal1 = getU32(lhsPtr);
        switch ((int) (tempVal1)) {
            case 0: {
                putI64(resultPtr, 0L);
                putI64(resultPtr + 8L, 0L);
                return 0L;
            }
            case -1: case 1: case 3: {
                tempVal3 = getU32(rhsPtr);
                if (((tempVal3 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal3 + 1L) & 31L)) & 21L) != 0L) {
                    flag = fradd(lhsPtr, rhsPtr, resultPtr, isSubtract & 1L) != 0L ? 1L : 0L;
                    return flag;
                }
                if (tempVal1 == 0L) {
                    putI64(resultPtr, 0L);
                    putI64(resultPtr + 8L, 0L);
                    return 0L;
                }
                break;
            }
            default: {
                tempVal3 = getU32(rhsPtr);
                break;
            }
        }
        if (tempVal3 == 0L) {
            putI64(resultPtr, 0L);
            putI64(resultPtr + 8L, 0L);
            return 0L;
        }
        if (tempVal1 == 2L || tempVal1 == 0xfffffffeL) {
            if (5L >= ((tempVal3 + 2L) & 0xffffffffL)) {
                tempVal2 = (1L << (int) ((tempVal3 + 2L) & 31L)) & 0xffffffffL;
                if ((tempVal2 & 42L) != 0L) {
                    mantissa = getI64(lhsPtr + 8L);
                    tempVal4 = getI64(lhsPtr);
                    putI64(resultPtr + 8L, mantissa);
                    putI64(resultPtr, tempVal4);
                    return 0L;
                }
                if ((tempVal2 & 17L) != 0L && ((tempVal1 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal1 + 1L) & 31L)) & 21L) != 0L) {
                    mantissa = getI64(rhsPtr + 8L);
                    tempVal4 = getI64(rhsPtr);
                    putI64(resultPtr + 8L, mantissa);
                    putI64(resultPtr, tempVal4);
                    return 0L;
                }
            }
        } else {
            if ((tempVal3 == 2L || tempVal3 == 0xfffffffeL) && ((tempVal1 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal1 + 1L) & 31L)) & 21L) != 0L) {
                mantissa = getI64(rhsPtr + 8L);
                tempVal4 = getI64(rhsPtr);
                putI64(resultPtr + 8L, mantissa);
                putI64(resultPtr, tempVal4);
                return 0L;
            }
        }
        if ((long) (int) (tempVal3 ^ tempVal1) < 0L) {
            putI64(resultPtr, 0L);
            putI64(resultPtr + 8L, 0L);
            return 0L;
        }
        mantissa = getI64(lhsPtr + 8L);
        tempVal4 = getI64(lhsPtr);
        putI64(resultPtr + 8L, mantissa);
        putI64(resultPtr, tempVal4);
        return 0L;
    }

    long fradd(long lhsPtr, long rhsPtr, long resultPtr, long isSubtract) {
        long tempVal = 0L;
        long flag = 0L;
        long bcdAcc1 = 0L;
        long bcdAcc2 = 0L;
        long bcdAcc3 = 0L;
        long exponent1 = 0L;
        long exponent2 = 0L;
        long exponent3 = 0L;
        long intVal = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    exponent3 = getU32(rhsPtr + 4L);
                    bcdAcc3 = getI64(lhsPtr + 8L);
                    bcdAcc2 = getI64(rhsPtr + 8L);
                    exponent1 = -getU32(rhsPtr) & 0xffffffffL;
                    if ((isSubtract & 1L) == 0L) {
                        exponent1 = getU32(rhsPtr);
                    }
                    if (bcdAcc3 == 0L) {
                        flag = 0L;
                        if (bcdAcc2 == 0L) {
                            exponent1 = 1L;
                        }
                        putI64(resultPtr + 8L, bcdAcc2);
                        putU32(resultPtr + 4L, exponent3);
                        putU32(resultPtr, exponent1);
                        return flag;
                    }
                    tempVal = getU32(lhsPtr);
                    exponent2 = getU32(lhsPtr + 4L);
                    if (bcdAcc2 == 0L) {
                        putI64(resultPtr + 8L, bcdAcc3);
                        putU32(resultPtr, tempVal);
                        putU32(resultPtr + 4L, exponent2);
                        return 0L;
                    }
                    if ((long) (int) (exponent1 ^ tempVal) < 0L) {
                        intVal = (long) (int) (exponent2 - exponent3);
                        if (intVal == 0L) {
                            if (Long.compareUnsigned(bcdAcc3, bcdAcc2) < 0L) {
                                putU32(resultPtr, exponent1);
                                putU32(resultPtr + 4L, exponent3);
                                bcdAcc1 = bcdAcc3;
                                bcdAcc3 = bcdAcc2;
                            } else {
                                putU32(resultPtr, tempVal);
                                putU32(resultPtr + 4L, exponent2);
                                bcdAcc1 = bcdAcc2;
                            }
                            step = 2;
                            continue stepLoop;
                        } else {
                            if (intVal < 0L) {
                                putU32(resultPtr, exponent1);
                                intVal = (long) (int) -intVal;
                                bcdAcc1 = bcdAcc3;
                            } else {
                                putU32(resultPtr, tempVal);
                                bcdAcc1 = bcdAcc2;
                                bcdAcc2 = bcdAcc3;
                                exponent3 = exponent2;
                                exponent1 = tempVal;
                            }
                            bcdAcc3 = bcdAcc2 << 4;
                            exponent2 = (exponent3 - 1L) & 0xffffffffL;
                            putU32(resultPtr + 4L, exponent2);
                            if (16L < intVal) {
                                putI64(resultPtr + 8L, bcdAcc3);
                                if (((bcdAcc2 >>> 56) & 15L) != 0L) {
                                    putI64(resultPtr + 8L, bcdAcc2 & 0xfffffffffffffffL);
                                    putU32(resultPtr + 4L, exponent3);
                                    tempVal = exponent1;
                                    if ((long) (int) exponent3 < 50000L) {
                                        return 1L;
                                    }
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    if (bcdAcc3 == 0L) {
                                        flag = 1L;
                                        putU32(resultPtr, 1L);
                                        putU32(resultPtr + 4L, 0L);
                                        return flag;
                                    }
                                    if (((bcdAcc2 >>> 28) & 0xff000000L) == 0L) {
                                        do {
                                            bcdAcc2 = bcdAcc3 >>> 52;
                                            bcdAcc3 = bcdAcc3 << 4;
                                            exponent2 = (exponent2 - 1L) & 0xffffffffL;
                                        } while ((bcdAcc2 & 0xffL) == 0L);
                                        putI64(resultPtr + 8L, bcdAcc3);
                                        putU32(resultPtr + 4L, exponent2);
                                    }
                                    step = 4;
                                    continue stepLoop;
                                }
                            } else {
                                bcdAcc1 = dcbShiftRound(bcdAcc1, (long) (int) (intVal - 1L));
                                step = 2;
                                continue stepLoop;
                            }
                        }
                    } else {
                        intVal = (long) (int) (exponent2 - exponent3);
                        putU32(resultPtr, tempVal);
                        if (intVal == 0L) {
                            putU32(resultPtr + 4L, exponent2);
                            bcdAcc3 = dcbAddAdjust(bcdAcc3, bcdAcc2);
                            putI64(resultPtr + 8L, bcdAcc3);
                            if (((bcdAcc3 >>> 60) & 0xffffffffL) == 0L) {
                                if (bcdAcc3 == 0L) {
                                    flag = 0L;
                                    putU32(resultPtr, 1L);
                                    putU32(resultPtr + 4L, 0L);
                                    return flag;
                                }
                                exponent1 = getU32(resultPtr + 4L);
                                if (((bcdAcc3 >>> 32) & 0xff000000L) == 0L) {
                                    do {
                                        bcdAcc2 = bcdAcc3 >>> 52;
                                        bcdAcc3 = bcdAcc3 << 4;
                                        exponent1 = (exponent1 - 1L) & 0xffffffffL;
                                    } while ((bcdAcc2 & 0xffL) == 0L);
                                    putI64(resultPtr + 8L, bcdAcc3);
                                    putU32(resultPtr + 4L, exponent1);
                                }
                                step = 5;
                                continue stepLoop;
                            } else {
                                step = 1;
                                continue stepLoop;
                            }
                        } else {
                            if (intVal < 0L) {
                                putU32(resultPtr + 4L, exponent3);
                                intVal = (long) (int) -intVal;
                                exponent2 = exponent3;
                                bcdAcc1 = bcdAcc3;
                                bcdAcc3 = bcdAcc2;
                            } else {
                                putU32(resultPtr + 4L, exponent2);
                                bcdAcc1 = bcdAcc2;
                            }
                            if (15L < intVal) {
                                putI64(resultPtr + 8L, bcdAcc3);
                                if (((bcdAcc3 >>> 60) & 0xffffffffL) != 0L) {
                                    putI64(resultPtr + 8L, bcdAcc3 >>> 4);
                                    putU32(resultPtr + 4L, exponent2 + 1L);
                                    if ((long) (int) exponent2 < 49999L) {
                                        return 1L;
                                    }
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    if (((bcdAcc3 >>> 32) & 0xff000000L) == 0L) {
                                        do {
                                            bcdAcc2 = bcdAcc3 >>> 52;
                                            bcdAcc3 = bcdAcc3 << 4;
                                            exponent2 = (exponent2 - 1L) & 0xffffffffL;
                                        } while ((bcdAcc2 & 0xffL) == 0L);
                                        putI64(resultPtr + 8L, bcdAcc3);
                                        putU32(resultPtr + 4L, exponent2);
                                    }
                                    step = 4;
                                    continue stepLoop;
                                }
                            } else {
                                bcdAcc2 = dcbShiftRound(bcdAcc1, intVal);
                                bcdAcc3 = dcbAddAdjust(bcdAcc2, bcdAcc3);
                                putI64(resultPtr + 8L, bcdAcc3);
                                if (((bcdAcc3 >>> 60) & 0xffffffffL) == 0L) {
                                    if (bcdAcc3 == 0L) {
                                        flag = 0L;
                                        putU32(resultPtr, 1L);
                                        putU32(resultPtr + 4L, 0L);
                                        return flag;
                                    }
                                    exponent1 = getU32(resultPtr + 4L);
                                    if (((bcdAcc3 >>> 32) & 0xff000000L) == 0L) {
                                        do {
                                            bcdAcc2 = bcdAcc3 >>> 52;
                                            bcdAcc3 = bcdAcc3 << 4;
                                            exponent1 = (exponent1 - 1L) & 0xffffffffL;
                                        } while ((bcdAcc2 & 0xffL) == 0L);
                                        putI64(resultPtr + 8L, bcdAcc3);
                                        putU32(resultPtr + 4L, exponent1);
                                    }
                                    step = 5;
                                    continue stepLoop;
                                } else {
                                    step = 1;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                }
                case 1: {
                    exponent1 = getU32(resultPtr + 4L);
                    flag = (bcdAcc3 & 15L) != 0L ? 1L : 0L;
                    putI64(resultPtr + 8L, bcdAcc3 >>> 4);
                    putU32(resultPtr + 4L, exponent1 + 1L);
                    if ((long) (int) exponent1 < 49999L) {
                        return flag;
                    }
                    if ((long) (int) getU32(resultPtr) < 1L) {
                        exponent1 = 0xfffffffeL;
                    } else {
                        exponent1 = 2L;
                    }
                    putU32(resultPtr, exponent1);
                    return flag;
                }
                case 2: {
                    bcdAcc3 = dcbSubAdjust(bcdAcc1, bcdAcc3);
                    putI64(resultPtr + 8L, bcdAcc3);
                    if (((bcdAcc3 >>> 60) & 0xffffffffL) == 0L) {
                        if (bcdAcc3 == 0L) {
                            flag = 0L;
                            putU32(resultPtr, 1L);
                            putU32(resultPtr + 4L, 0L);
                            return flag;
                        }
                        exponent1 = getU32(resultPtr + 4L);
                        if (((bcdAcc3 >>> 32) & 0xff000000L) == 0L) {
                            do {
                                bcdAcc2 = bcdAcc3 >>> 52;
                                bcdAcc3 = bcdAcc3 << 4;
                                exponent1 = (exponent1 - 1L) & 0xffffffffL;
                            } while ((bcdAcc2 & 0xffL) == 0L);
                            putI64(resultPtr + 8L, bcdAcc3);
                            putU32(resultPtr + 4L, exponent1);
                        }
                        step = 5;
                        continue stepLoop;
                    } else {
                        step = 1;
                        continue stepLoop;
                    }
                }
                case 3: {
                    if ((long) (int) tempVal < 1L) {
                        exponent1 = 0xfffffffeL;
                    } else {
                        exponent1 = 2L;
                    }
                    putU32(resultPtr, exponent1);
                    return 1L;
                }
                case 4: {
                    if (-50000L < (long) (int) exponent2) {
                        return 1L;
                    }
                    flag = 1L;
                    putU32(resultPtr + 8L, 0L);
                    putU32(resultPtr + 12L, 0L);
                    putU32(resultPtr, 1L);
                    putU32(resultPtr + 4L, 0L);
                    return flag;
                }
                case 5: {
                    if (-50000L < (long) (int) exponent1) {
                        return 0L;
                    }
                    flag = 0L;
                    putU32(resultPtr + 8L, 0L);
                    putU32(resultPtr + 12L, 0L);
                    putU32(resultPtr, 1L);
                    putU32(resultPtr + 4L, 0L);
                    return flag;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long fiSignEQ(long lhsPtr, long rhsPtr) {
        if (getU32(lhsPtr) != 0L) {
            return (((((getU32(rhsPtr) ^ getU32(lhsPtr) ^ 0xffffffffL)) & 0xffffffffL)) >> 31) & 0xffffffffL;
        } else {
            return 0L;
        }
    }

    long fNorm(long inexactFlag, long realPtr) {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long exponent = 0L;
        long mantissa = 0L;
        if (((((getI32(realPtr + 12L) & 0xffffffffL)) >> 28) & 0xffffffffL) == 0L) {
            mantissa = getI64(realPtr + 8L);
            if (mantissa == 0L) {
                putI32(realPtr, 1L);
                putI32(realPtr + 4L, 0L);
                return inexactFlag;
            }
            exponent = getI32(realPtr + 4L);
            if (((((getI32(realPtr + 12L) & 0xffffffffL)) >> 24) & 0xffffffffL) == 0L) {
                do {
                    tempVal1 = mantissa >>> 52;
                    mantissa = mantissa << 4;
                    exponent = (long) (int) (exponent - 1L);
                } while ((tempVal1 & 0xffL) == 0L);
                putI64(realPtr + 8L, mantissa);
                putI32(realPtr + 4L, exponent);
            }
            if (exponent < -49999L) {
                putI32(realPtr + 8L, 0L);
                putI32(realPtr + 12L, 0L);
                putI32(realPtr, 1L);
                putI32(realPtr + 4L, 0L);
            }
            return inexactFlag;
        }
        exponent = getI32(realPtr + 4L);
        tempVal2 = inexactFlag & 0xffffffffL;
        if ((getI32(realPtr + 8L) & 15L) != 0L) {
            tempVal2 = 1L;
        }
        inexactFlag = tempVal2;
        putI64(realPtr + 8L, getI64(realPtr + 8L) >>> 4);
        putI32(realPtr + 4L, exponent + 1L);
        if (49998L >= exponent) {
            return inexactFlag;
        }
        if (0L < getI32(realPtr)) {
            putI32(realPtr, 2L);
            return inexactFlag;
        } else {
            putI32(realPtr, -2L);
            return inexactFlag;
        }
    }

    long fiadd(long lhsPtr, long rhsPtr, long resultPtr, long isSubtract) {
        long tempVal1 = 0L;
        long status = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long mantissa = 0L;
        status = getU32(lhsPtr);
        switch ((int) (status)) {
            case 0: {
                putI64(resultPtr, 0L);
                putI64(resultPtr + 8L, 0L);
                return 0L;
            }
            case -1: case 1: case 3: {
                tempVal2 = getU32(rhsPtr);
                if (((tempVal2 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal2 + 1L) & 31L)) & 21L) != 0L) {
                    status = fadd(lhsPtr, rhsPtr, resultPtr, (long) (int) isSubtract) & 0xffffffffL;
                    return status;
                }
                if (status == 0L) {
                    putI64(resultPtr, 0L);
                    putI64(resultPtr + 8L, 0L);
                    return 0L;
                }
                break;
            }
            default: {
                tempVal2 = getU32(rhsPtr);
                break;
            }
        }
        if (tempVal2 == 0L) {
            putI64(resultPtr, 0L);
            putI64(resultPtr + 8L, 0L);
            return 0L;
        }
        if (status == 2L || status == 0xfffffffeL) {
            if (5L >= ((tempVal2 + 2L) & 0xffffffffL)) {
                tempVal1 = (1L << (int) ((tempVal2 + 2L) & 31L)) & 0xffffffffL;
                if ((tempVal1 & 42L) != 0L) {
                    mantissa = getI64(lhsPtr + 8L);
                    tempVal3 = getI64(lhsPtr);
                    putI64(resultPtr + 8L, mantissa);
                    putI64(resultPtr, tempVal3);
                    return 0L;
                }
                if ((tempVal1 & 17L) != 0L && ((status + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((status + 1L) & 31L)) & 21L) != 0L) {
                    mantissa = getI64(rhsPtr + 8L);
                    tempVal3 = getI64(rhsPtr);
                    putI64(resultPtr + 8L, mantissa);
                    putI64(resultPtr, tempVal3);
                    return 0L;
                }
            }
        } else {
            if ((tempVal2 == 2L || tempVal2 == 0xfffffffeL) && ((status + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((status + 1L) & 31L)) & 21L) != 0L) {
                mantissa = getI64(rhsPtr + 8L);
                tempVal3 = getI64(rhsPtr);
                putI64(resultPtr + 8L, mantissa);
                putI64(resultPtr, tempVal3);
                return 0L;
            }
        }
        if ((long) (int) (tempVal2 ^ status) < 0L) {
            putI64(resultPtr, 0L);
            putI64(resultPtr + 8L, 0L);
            return 0L;
        }
        mantissa = getI64(lhsPtr + 8L);
        tempVal3 = getI64(lhsPtr);
        putI64(resultPtr + 8L, mantissa);
        putI64(resultPtr, tempVal3);
        return 0L;
    }

    long fadd(long lhsPtr, long rhsPtr, long resultPtr, long isSubtract) {
        long dataPtr1 = 0L;
        long dataPtr2 = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long exponent1 = 0L;
        long result = 0L;
        long bcdAcc1 = 0L;
        long bcdAcc2 = 0L;
        long exponent2 = 0L;
        long exponent3 = 0L;
        long tempVal3 = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if (getI32(lhsPtr + 12L) == 0L) {
                        tempVal3 = getI64(rhsPtr);
                        putI64(resultPtr + 8L, getI64(rhsPtr + 8L));
                        putI64(resultPtr, tempVal3);
                        return 0L;
                    }
                    if (getU32(rhsPtr + 12L) == 0L) {
                        result = 0L;
                        tempVal3 = getI64(lhsPtr);
                        putI64(resultPtr + 8L, getI64(lhsPtr + 8L));
                        putI64(resultPtr, tempVal3);
                        return result;
                    }
                    tempVal2 = getU32(lhsPtr);
                    exponent2 = getU32(lhsPtr + 4L);
                    exponent3 = getU32(rhsPtr + 4L);
                    tempVal1 = ((getU32(rhsPtr) ^ tempVal2)) & 0xffffffffL;
                    if (exponent2 == exponent3) {
                        dataPtr1 = lhsPtr + 8L;
                        bcdAcc2 = getI64(rhsPtr + 8L);
                        exponent3 = getU32(rhsPtr);
                        if (Long.compareUnsigned(getI64(rhsPtr + 8L), getI64(dataPtr1)) <= 0L) {
                            lhsPtr = rhsPtr;
                            bcdAcc2 = getI64(dataPtr1);
                            exponent3 = tempVal2;
                        }
                    } else {
                        dataPtr2 = rhsPtr;
                        if ((long) (int) exponent3 <= (long) (int) exponent2) {
                            dataPtr2 = lhsPtr;
                        }
                        if ((long) (int) exponent3 <= (long) (int) exponent2) {
                            lhsPtr = rhsPtr;
                        }
                        bcdAcc2 = getI64(dataPtr2 + 8L);
                        exponent2 = getU32(dataPtr2 + 4L);
                        exponent3 = getU32(dataPtr2);
                        if ((long) (int) tempVal1 < 0L) {
                            exponent2 = (getU32(dataPtr2 + 4L) - 1L) & 0xffffffffL;
                            bcdAcc2 = getI64(dataPtr2 + 8L) << 4;
                        }
                    }
                    exponent1 = (long) (int) (exponent2 - getU32(lhsPtr + 4L));
                    if (exponent1 < 16L) {
                        bcdAcc1 = getI64(lhsPtr + 8L);
                        if (exponent1 == 0L) {
                            if ((long) (int) tempVal1 < 0L) {
                                bcdAcc2 = dcbSubAdjust(bcdAcc1, bcdAcc2);
                                if ((long) (int) isSubtract == 3L && bcdAcc2 == 0L) {
                                    exponent3 = 0xffffffffL;
                                    bcdAcc2 = 0L;
                                }
                                tempVal1 = (bcdAcc2 >>> 32) & 0xffffffffL;
                                result = 0L;
                                step = 2;
                                continue stepLoop;
                            } else {
                                step = 1;
                                continue stepLoop;
                            }
                        } else {
                            if ((long) (int) tempVal1 < 0L) {
                                bcdAcc1 = dcbShiftRound2(bcdAcc1, exponent1);
                                bcdAcc2 = dcbSubAdjust(bcdAcc1, bcdAcc2);
                                if (bcdAcc2 == 0L && (long) (int) isSubtract == 3L) {
                                    exponent3 = 0xffffffffL;
                                }
                                tempVal1 = (bcdAcc2 >>> 32) & 0xffffffffL;
                                result = 0L;
                                step = 2;
                                continue stepLoop;
                            } else {
                                bcdAcc1 = bcdAcc1 >>> (int) ((exponent1 * 4L) & 63L);
                                step = 1;
                                continue stepLoop;
                            }
                        }
                    } else {
                        result = 1L;
                        tempVal1 = (bcdAcc2 >>> 32) & 0xffffffffL;
                        step = 2;
                        continue stepLoop;
                    }
                }
                case 1: {
                    bcdAcc2 = dcbAddAdjust(bcdAcc2, bcdAcc1);
                    result = 0L;
                    tempVal1 = (bcdAcc2 >>> 32) & 0xffffffffL;
                    step = 2;
                    continue stepLoop;
                }
                case 2: {
                    if (((tempVal1 >> 28) & 0xffffffffL) == 0L) {
                        if (bcdAcc2 != 0L) {
                            bcdAcc1 = (bcdAcc2 >>> 32) & 0xff000000L;
                            while (bcdAcc1 == 0L) {
                                exponent2 = (exponent2 - 1L) & 0xffffffffL;
                                bcdAcc1 = (bcdAcc2 >>> 52) & 0xffL;
                                bcdAcc2 = bcdAcc2 << 4;
                            }
                            if (-50000L < (long) (int) exponent2) {
                                putU32(resultPtr, exponent3);
                                putU32(resultPtr + 4L, exponent2);
                                putI64(resultPtr + 8L, bcdAcc2);
                                return result;
                            }
                        }
                        lhsPtr = CC_0;
                    } else {
                        if ((bcdAcc2 & 15L) != 0L) {
                            result = 1L;
                        }
                        if ((long) (int) exponent2 < 49999L) {
                            bcdAcc2 = bcdAcc2 >>> 4;
                            exponent2 = (exponent2 + 1L) & 0xffffffffL;
                            putU32(resultPtr, exponent3);
                            putU32(resultPtr + 4L, exponent2);
                            putI64(resultPtr + 8L, bcdAcc2);
                            return result;
                        }
                        if ((long) (int) exponent3 < 1L) {
                            lhsPtr = CC_NInf;
                        } else {
                            lhsPtr = CC_PInf;
                        }
                    }
                    tempVal3 = getI64(lhsPtr);
                    putI64(resultPtr + 8L, getI64(lhsPtr + 8L));
                    putI64(resultPtr, tempVal3);
                    return result;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long fimul(long lhsPtr, long rhsPtr, long resultPtr) {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long result = 0L;
        long exponent = 0L;
        long mantissa = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        tempVal2 = getU32(lhsPtr);
        switch ((int) (tempVal2)) {
            case 0: {
                putI32(resultPtr, 0L);
                putI32(resultPtr + 4L, 0L);
                putI32(resultPtr + 8L, 0L);
                putI32(resultPtr + 12L, 0L);
                return 0L;
            }
            case -1: case 1: case 3: {
                tempVal3 = getU32(rhsPtr);
                if (((tempVal3 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal3 + 1L) & 31L)) & 21L) != 0L) {
                    tempVal2 = fmul_noNorm(lhsPtr, rhsPtr, resultPtr) & 0xffffffffL;
                    result = tempVal2;
                    if (((((getI32(resultPtr + 12L) & 0xffffffffL)) >> 28) & 0xffffffffL) == 0L) {
                        mantissa = getI64(resultPtr + 8L);
                        if (mantissa == 0L) {
                            putI32(resultPtr, 1L);
                            putI32(resultPtr + 4L, 0L);
                            return result;
                        }
                        exponent = getI32(resultPtr + 4L);
                        if (((((getI32(resultPtr + 12L) & 0xffffffffL)) >> 24) & 0xffffffffL) == 0L) {
                            do {
                                tempVal1 = mantissa >>> 52;
                                mantissa = mantissa << 4;
                                exponent = (long) (int) (exponent - 1L);
                            } while ((tempVal1 & 0xffL) == 0L);
                            putI64(resultPtr + 8L, mantissa);
                            putI32(resultPtr + 4L, exponent);
                        }
                        if (-50000L < exponent) {
                            return result;
                        } else {
                            putI32(resultPtr + 8L, 0L);
                            putI32(resultPtr + 12L, 0L);
                            putI32(resultPtr, 1L);
                            putI32(resultPtr + 4L, 0L);
                            return result;
                        }
                    }
                    exponent = getI32(resultPtr + 4L);
                    if ((getI32(resultPtr + 8L) & 15L) != 0L) {
                        tempVal2 = 1L;
                    }
                    result = tempVal2;
                    putI64(resultPtr + 8L, getI64(resultPtr + 8L) >>> 4);
                    putI32(resultPtr + 4L, exponent + 1L);
                    if (exponent < 49999L) {
                        return result;
                    }
                    if (getI32(resultPtr) < 1L) {
                        exponent = -2L;
                    } else {
                        exponent = 2L;
                    }
                    putI32(resultPtr, exponent);
                    return result;
                }
                if (tempVal2 == 0L) {
                    putI32(resultPtr, 0L);
                    putI32(resultPtr + 4L, 0L);
                    putI32(resultPtr + 8L, 0L);
                    putI32(resultPtr + 12L, 0L);
                    return 0L;
                }
                break;
            }
            default: {
                tempVal3 = getU32(rhsPtr);
                break;
            }
        }
        if (tempVal3 == 0L) {
            putI32(resultPtr, 0L);
            putI32(resultPtr + 4L, 0L);
            putI32(resultPtr + 8L, 0L);
            putI32(resultPtr + 12L, 0L);
            return 0L;
        }
        if (tempVal2 == 2L || tempVal2 == 0xfffffffeL) {
            if (getU32(rhsPtr + 12L) == 0L) {
                putI32(resultPtr, 0L);
                putI32(resultPtr + 4L, 0L);
                putI32(resultPtr + 8L, 0L);
                putI32(resultPtr + 12L, 0L);
                return 6L;
            }
            tempVal4 = getI64(lhsPtr);
            result = 0L;
            putI64(resultPtr + 8L, getI64(lhsPtr + 8L));
            putI64(resultPtr, tempVal4);
            exponent = -2L;
            if (-1L < (long) (int) (getU32(rhsPtr) ^ tempVal2)) {
                exponent = 2L;
            }
        } else {
            if (getU32(lhsPtr + 12L) == 0L) {
                putI32(resultPtr, 0L);
                putI32(resultPtr + 4L, 0L);
                putI32(resultPtr + 8L, 0L);
                putI32(resultPtr + 12L, 0L);
                return 6L;
            }
            tempVal4 = getI64(rhsPtr);
            result = 0L;
            putI64(resultPtr + 8L, getI64(rhsPtr + 8L));
            putI64(resultPtr, tempVal4);
            exponent = -2L;
            if (-1L < (long) (int) (getU32(lhsPtr) ^ tempVal3) && getU32(lhsPtr) != 0L) {
                exponent = 2L;
            }
        }
        putI32(resultPtr, exponent);
        return result;
    }

    long fmul_noNorm(long lhsPtr, long rhsPtr, long resultPtr) {
        long flag = 0L;
        long bcdAcc1 = 0L;
        long bcdAcc2 = 0L;
        long bcdAcc3 = 0L;
        long bcdAcc4 = 0L;
        long result = 0L;
        long tempVal = 0L;
        long index = 0L;
        if (getU32(lhsPtr + 12L) == 0L || getU32(rhsPtr + 12L) == 0L) {
            result = 0L;
            putU32(resultPtr + 8L, 0L);
            putU32(resultPtr + 12L, 0L);
            putU32(resultPtr, 1L);
            putU32(resultPtr + 4L, 0L);
        } else {
            putU32(resultPtr, ((long) (int) (getU32(rhsPtr) ^ getU32(lhsPtr)) >> 30) | 1L);
            putU32(resultPtr + 4L, getU32(rhsPtr + 4L) + getU32(lhsPtr + 4L));
            bcdAcc1 = dcbAddAdjust(getI64(rhsPtr + 8L), getI64(rhsPtr + 8L));
            bcdAcc2 = dcbAddAdjust(bcdAcc1, bcdAcc1);
            bcdAcc3 = dcbAddAdjust(bcdAcc2, bcdAcc2);
            tempVal = getU32(lhsPtr + 8L);
            bcdAcc4 = 0L;
            result = 0L;
            index = -8L;
            do {
                if ((bcdAcc4 & 15L) != 0L) {
                    result = 1L;
                }
                bcdAcc4 = bcdAcc4 >>> 4;
                if ((tempVal & 1L) != 0L) {
                    bcdAcc4 = dcbAddAdjust(bcdAcc4, getI64(rhsPtr + 8L));
                }
                if (((tempVal >> 1) & 1L) != 0L) {
                    bcdAcc4 = dcbAddAdjust(bcdAcc4, bcdAcc1);
                }
                if (((tempVal >> 2) & 1L) != 0L) {
                    bcdAcc4 = dcbAddAdjust(bcdAcc4, bcdAcc2);
                }
                if (((tempVal >> 3) & 1L) != 0L) {
                    bcdAcc4 = dcbAddAdjust(bcdAcc4, bcdAcc3);
                }
                flag = index != -1L ? 1L : 0L;
                index = (long) (int) (index + 1L);
                tempVal = (tempVal >> 4) & 0xffffffffL;
            } while (flag != 0L);
            tempVal = getU32(lhsPtr + 12L);
            index = -7L;
            do {
                if ((bcdAcc4 & 15L) != 0L) {
                    result = 1L;
                }
                bcdAcc4 = bcdAcc4 >>> 4;
                if ((tempVal & 1L) != 0L) {
                    bcdAcc4 = dcbAddAdjust(bcdAcc4, getI64(rhsPtr + 8L));
                }
                if (((tempVal >> 1) & 1L) != 0L) {
                    bcdAcc4 = dcbAddAdjust(bcdAcc4, bcdAcc1);
                }
                if (((tempVal >> 2) & 1L) != 0L) {
                    bcdAcc4 = dcbAddAdjust(bcdAcc4, bcdAcc2);
                }
                if (((tempVal >> 3) & 1L) != 0L) {
                    bcdAcc4 = dcbAddAdjust(bcdAcc4, bcdAcc3);
                }
                flag = index != -1L ? 1L : 0L;
                index = (long) (int) (index + 1L);
                tempVal = (tempVal >> 4) & 0xffffffffL;
            } while (flag != 0L);
            putI64(resultPtr + 8L, bcdAcc4);
        }
        return result;
    }

    long fmul(long lhsPtr, long rhsPtr, long resultPtr) {
        long tempVal = 0L;
        long result = 0L;
        long exponent = 0L;
        long mantissa = 0L;
        result = fmul_noNorm(lhsPtr, rhsPtr, resultPtr) & 0xffffffffL;
        if (((getU32(resultPtr + 12L) >> 28) & 0xffffffffL) == 0L) {
            mantissa = getI64(resultPtr + 8L);
            if (mantissa == 0L) {
                putU32(resultPtr, 1L);
                putU32(resultPtr + 4L, 0L);
            } else {
                exponent = getU32(resultPtr + 4L);
                if (((getU32(resultPtr + 12L) >> 24) & 0xffffffffL) == 0L) {
                    do {
                        tempVal = mantissa >>> 52;
                        mantissa = mantissa << 4;
                        exponent = (exponent - 1L) & 0xffffffffL;
                    } while ((tempVal & 0xffL) == 0L);
                    putI64(resultPtr + 8L, mantissa);
                    putU32(resultPtr + 4L, exponent);
                }
                if ((long) (int) exponent < -49999L) {
                    putU32(resultPtr + 8L, 0L);
                    putU32(resultPtr + 12L, 0L);
                    putU32(resultPtr, 1L);
                    putU32(resultPtr + 4L, 0L);
                }
            }
        } else {
            exponent = getU32(resultPtr + 4L);
            if ((getU32(resultPtr + 8L) & 15L) != 0L) {
                result = 1L;
            }
            putI64(resultPtr + 8L, getI64(resultPtr + 8L) >>> 4);
            putU32(resultPtr + 4L, exponent + 1L);
            if (49998L >= (long) (int) exponent) {
                return result;
            }
            if ((long) (int) getU32(resultPtr) < 1L) {
                exponent = 0xfffffffeL;
            } else {
                exponent = 2L;
            }
            putU32(resultPtr, exponent);
        }
        return result;
    }

    long fidiv(long dividendPtr, long divisorPtr, long resultPtr) {
        long tempVal1 = 0L;
        long flag = 0L;
        long intVal = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        tempVal1 = getU32(dividendPtr);
        switch ((int) (tempVal1)) {
            case 0: {
                putI32(resultPtr, 0L);
                putI32(resultPtr + 4L, 0L);
                putI32(resultPtr + 8L, 0L);
                putI32(resultPtr + 12L, 0L);
                return 0L;
            }
            case -1: case 1: case 3: {
                tempVal2 = getU32(divisorPtr);
                if (((tempVal2 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal2 + 1L) & 31L)) & 21L) != 0L) {
                    flag = fdiv(dividendPtr, divisorPtr, resultPtr) & 0xffL;
                    return flag;
                }
                if (tempVal1 == 0L) {
                    putI32(resultPtr, 0L);
                    putI32(resultPtr + 4L, 0L);
                    putI32(resultPtr + 8L, 0L);
                    putI32(resultPtr + 12L, 0L);
                    return 0L;
                }
                break;
            }
            default: {
                tempVal2 = getU32(divisorPtr);
                break;
            }
        }
        if (tempVal2 == 0L) {
            putI32(resultPtr, 0L);
            putI32(resultPtr + 4L, 0L);
            putI32(resultPtr + 8L, 0L);
            putI32(resultPtr + 12L, 0L);
            return 0L;
        }
        if (tempVal1 == 2L || tempVal1 == 0xfffffffeL) {
            if (5L < ((tempVal2 + 2L) & 0xffffffffL)) {
                putI32(resultPtr, 0L);
                putI32(resultPtr + 4L, 0L);
                putI32(resultPtr + 8L, 0L);
                putI32(resultPtr + 12L, 0L);
                return 7L;
            }
            tempVal2 = (1L << (int) ((tempVal2 + 2L) & 31L)) & 0xffffffffL;
            if ((tempVal2 & 42L) != 0L) {
                tempVal3 = getI64(dividendPtr);
                putI64(resultPtr + 8L, getI64(dividendPtr + 8L));
                putI64(resultPtr, tempVal3);
                if ((long) (int) (getU32(divisorPtr) ^ tempVal1) >= 0L) {
                    return 0L;
                }
                intVal = getI32(resultPtr);
                if (intVal != -1L && intVal != 1L) {
                    if (intVal != 3L) {
                        putI32(resultPtr, -intVal);
                        return 0L;
                    }
                    intVal = 1L;
                    putI32(resultPtr, 1L);
                }
                if (getI32(resultPtr + 12L) != 0L) {
                    putI32(resultPtr, -intVal);
                    return 0L;
                } else {
                    return 0L;
                }
            }
            if ((tempVal2 & 17L) == 0L) {
                putI32(resultPtr, 0L);
                putI32(resultPtr + 4L, 0L);
                putI32(resultPtr + 8L, 0L);
                putI32(resultPtr + 12L, 0L);
                return 7L;
            }
        } else {
            if (tempVal2 != 2L && tempVal2 != 0xfffffffeL) {
                putI32(resultPtr, 0L);
                putI32(resultPtr + 4L, 0L);
                putI32(resultPtr + 8L, 0L);
                putI32(resultPtr + 12L, 0L);
                return 7L;
            }
        }
        if (((tempVal1 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal1 + 1L) & 31L)) & 21L) != 0L) {
            putI32(resultPtr + 8L, 0L);
            putI32(resultPtr + 12L, 0L);
            putI32(resultPtr, 1L);
            putI32(resultPtr + 4L, 0L);
            return 0L;
        }
        putI32(resultPtr, 0L);
        putI32(resultPtr + 4L, 0L);
        putI32(resultPtr + 8L, 0L);
        putI32(resultPtr + 12L, 0L);
        return 7L;
    }

    long fdiv(long dividendPtr, long divisorPtr, long resultPtr) {
        long exponent1 = 0L;
        long tempVal1 = 0L;
        long result = 0L;
        long mantissa = 0L;
        long constPtr = 0L;
        long exponent2 = 0L;
        long tempVal2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(128);
        try {
            if (getU32(divisorPtr + 12L) == 0L) {
                if (getU32(dividendPtr + 12L) == 0L) {
                    putU32(resultPtr, 0L);
                    putU32(resultPtr + 4L, 0L);
                    putU32(resultPtr + 8L, 0L);
                    putU32(resultPtr + 12L, 0L);
                    result = 5L;
                } else {
                    if ((long) (int) getU32(dividendPtr) < 1L) {
                        constPtr = CC_NInf;
                    } else {
                        constPtr = CC_PInf;
                    }
                    tempVal2 = getI64(constPtr);
                    result = 4L;
                    putI64(resultPtr + 8L, getI64(constPtr + 8L));
                    putI64(resultPtr, tempVal2);
                }
                return result;
            }
            if (getU32(dividendPtr + 12L) == 0L) {
                result = 0L;
                putU32(resultPtr + 8L, 0L);
                putU32(resultPtr + 12L, 0L);
                putU32(resultPtr, 1L);
                putU32(resultPtr + 4L, 0L);
                return result;
            }
            exponent2 = getU32(dividendPtr + 4L);
            exponent1 = getU32(divisorPtr + 4L);
            putU32(resultPtr + 4L, exponent2 - exponent1);
            putU32(resultPtr, ((long) (int) (getU32(divisorPtr) ^ getU32(dividendPtr)) >> 30) | 1L);
            putI64(framePtr + 16L, getI64(dividendPtr + 8L));
            mantissa = getI64(divisorPtr + 8L);
            if (Long.compareUnsigned(getI64(framePtr + 16L), mantissa) < 0L) {
                putI64(framePtr + 16L, getI64(framePtr + 16L) << 4);
                putU32(resultPtr + 4L, exponent2 - exponent1 - 1L);
                mantissa = getI64(divisorPtr + 8L);
            }
            mantissa = divLoop(framePtr + 16L, mantissa);
            putI64(resultPtr + 8L, mantissa);
            result = getI64(framePtr + 16L) != 0L ? 1L : 0L;
            if (((mantissa >>> 60) & 0xffffffffL) != 0L) {
                exponent2 = getU32(resultPtr + 4L);
                result = (mantissa & 15L) != 0L || result != 0L ? 1L : 0L;
                putI64(resultPtr + 8L, mantissa >>> 4);
                putU32(resultPtr + 4L, exponent2 + 1L);
                if (49998L >= (long) (int) exponent2) {
                    return result;
                }
                if ((long) (int) getU32(resultPtr) < 1L) {
                    exponent2 = 0xfffffffeL;
                } else {
                    exponent2 = 2L;
                }
                putU32(resultPtr, exponent2);
            } else {
                if (mantissa != 0L) {
                    exponent2 = getU32(resultPtr + 4L);
                    if (((mantissa >>> 32) & 0xff000000L) == 0L) {
                        do {
                            tempVal1 = mantissa >>> 52;
                            mantissa = mantissa << 4;
                            exponent2 = (exponent2 - 1L) & 0xffffffffL;
                        } while ((tempVal1 & 0xffL) == 0L);
                        putI64(resultPtr + 8L, mantissa);
                        putU32(resultPtr + 4L, exponent2);
                    }
                    if (-50000L < (long) (int) exponent2) {
                        return result;
                    }
                    putU32(resultPtr + 8L, 0L);
                    putU32(resultPtr + 12L, 0L);
                    putU32(resultPtr, 1L);
                    putU32(resultPtr + 4L, 0L);
                    return result;
                }
                putU32(resultPtr, 1L);
                putU32(resultPtr + 4L, 0L);
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long fisqrt(long srcPtr, long resultPtr) {
        long exponent = 0L;
        long result = 0L;
        long bcdAcc1 = 0L;
        long bcdAcc2 = 0L;
        long bcdAcc3 = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if ((((getU32(srcPtr) | 2L)) & 0xffffffffL) == 2L || getU32(srcPtr + 12L) == 0L) {
                        tempVal4 = getI64(srcPtr);
                        result = 0L;
                        putI64(resultPtr + 8L, getI64(srcPtr + 8L));
                        putI64(resultPtr, tempVal4);
                        return result;
                    }
                    if (-1L >= (long) (int) getU32(srcPtr)) {
                        putI64(resultPtr, 0L);
                        putI64(resultPtr + 8L, 0L);
                        result = 8L;
                        return result;
                    }
                    bcdAcc1 = dcbAddAdjust(getI64(srcPtr + 8L), getI64(srcPtr + 8L));
                    bcdAcc1 = dcbAddAdjust(bcdAcc1, bcdAcc1);
                    bcdAcc2 = dcbAddAdjust(bcdAcc1, getI64(srcPtr + 8L));
                    exponent = getU32(srcPtr + 4L);
                    tempVal2 = 0x4b0000000000000L;
                    tempVal1 = 0x50000000000000L;
                    bcdAcc3 = bcdAcc2 >>> 4;
                    if ((exponent & 1L) != 0L) {
                        bcdAcc3 = bcdAcc2;
                    }
                    tempVal3 = 0x100000000000000L;
                    putU32(resultPtr, 1L);
                    putI32(resultPtr + 4L, (long) (int) exponent >> 1);
                    if (Long.compareUnsigned(bcdAcc3, 0x50000000000000L) < 0L) {
                        step = 2;
                        continue stepLoop;
                    } else {
                        step = 1;
                        continue stepLoop;
                    }
                }
                case 1: {
                    do {
                        bcdAcc3 = dcbSubAdjust(tempVal1, bcdAcc3);
                        tempVal1 = tempVal1 + tempVal3;
                    } while (Long.compareUnsigned(tempVal1, bcdAcc3) <= 0L);
                    step = 2;
                    continue stepLoop;
                }
                case 2: {
                    do {
                        if (tempVal2 == 5L) {
                            putI64(resultPtr + 8L, tempVal1);
                            return (bcdAcc2 & 15L) != 0L && (exponent & 1L) == 0L || bcdAcc3 != 0L ? 1L : 0L;
                        }
                        tempVal2 = (tempVal2 & 1L) | (tempVal2 >>> 4);
                        bcdAcc3 = bcdAcc3 << 4;
                        tempVal1 = tempVal1 - tempVal2;
                        tempVal3 = tempVal3 >>> 4;
                    } while (Long.compareUnsigned(bcdAcc3, tempVal1) < 0L);
                    step = 1;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long fNbSigDigits(long realPtr) {
        long exponent = 0L;
        long mantissa = 0L;
        long index = 0L;
        exponent = getU32(realPtr + 4L);
        if (13L < (long) (int) exponent) {
            return (long) (int) (exponent + 1L);
        }
        mantissa = getI64(realPtr + 8L);
        if (-1L >= (long) (int) exponent) {
            index = 0L;
            while (comma(mantissa = mantissa << 4, mantissa != 0L ? 1L : 0L) != 0L) {
                index = (long) (int) (index + 1L);
            }
            return index;
        }
        mantissa = mantissa << (int) ((exponent * 4L + 8L) & 63L);
        if (mantissa == 0L) {
            index = 0L;
        } else {
            index = 0L;
            do {
                mantissa = mantissa << 4;
                index = (long) (int) (index + 1L);
            } while (mantissa != 0L);
        }
        return (long) (int) (exponent + index + 1L);
    }

    long inttostr(long intVal, long outStr) {
        long bytePtr = 0L;
        long flag = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    tempVal2 = ((((((intVal & 0xffffffffL)) >> 9) & 0xffffffffL) * 0x44b83L) >>> 39) & 0xffffffffL;
                    tempVal1 = (intVal + tempVal2 * 0xc4653600L) & 0xffffffffL;
                    if ((intVal & 0xffffffffL) < 0x3b9aca00L) {
                        bytePtr = outStr;
                        if (0x5f5e0ffL < tempVal1) {
                            step = 1;
                            continue stepLoop;
                        } else {
                            tempVal2 = (((tempVal1 % 0x5f5e100L) & 0xffffffffL) % 0x989680L) & 0xffffffffL;
                            if (0x98967fL < ((tempVal1 % 0x5f5e100L) & 0xffffffffL)) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                tempVal1 = (tempVal2 % 0xf4240L) & 0xffffffffL;
                                if (0xf423fL < tempVal2) {
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    tempVal2 = (tempVal1 % 0x186a0L) & 0xffffffffL;
                                    if (0x1869fL < tempVal1) {
                                        step = 4;
                                        continue stepLoop;
                                    } else {
                                        tempVal1 = (tempVal2 % 10000L) & 0xffffffffL;
                                        if (624L < ((tempVal2 >> 4) & 0xffffffffL)) {
                                            step = 5;
                                            continue stepLoop;
                                        } else {
                                            tempVal2 = (tempVal1 % 1000L) & 0xffffffffL;
                                            if (999L < tempVal1) {
                                                step = 6;
                                                continue stepLoop;
                                            } else {
                                                tempVal1 = (tempVal2 % 100L) & 0xffffffffL;
                                                if (99L >= tempVal2) {
                                                    flag = (tempVal1 % 10L) & 0xffL;
                                                    if (9L < tempVal1) {
                                                        putI8(outStr, (tempVal1 / 10L) | 48L);
                                                        bytePtr = outStr + 1L;
                                                    } else {
                                                        if (((tempVal1 % 10L) & 0xffffffffL) == 0L) {
                                                            putI8(outStr, 32L);
                                                            return outStr;
                                                        }
                                                    }
                                                    outStr = bytePtr + 1L;
                                                    putU8(bytePtr, flag | 48L);
                                                    putI8(outStr, 32L);
                                                    return outStr;
                                                }
                                                step = 7;
                                                continue stepLoop;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        putI8(outStr, tempVal2 | 48L);
                        bytePtr = outStr + 1L;
                        step = 1;
                        continue stepLoop;
                    }
                }
                case 1: {
                    outStr = bytePtr + 1L;
                    putU8(bytePtr, (tempVal1 / 0x5f5e100L) | 48L);
                    tempVal2 = (((tempVal1 % 0x5f5e100L) & 0xffffffffL) % 0x989680L) & 0xffffffffL;
                    step = 2;
                    continue stepLoop;
                }
                case 2: {
                    putI8(outStr, (((tempVal1 % 0x5f5e100L) & 0xffffffffL) / 0x989680L) | 48L);
                    tempVal1 = (tempVal2 % 0xf4240L) & 0xffffffffL;
                    bytePtr = outStr + 1L;
                    step = 3;
                    continue stepLoop;
                }
                case 3: {
                    outStr = bytePtr + 1L;
                    putU8(bytePtr, tempVal2 / 0xf4240L + 48L);
                    tempVal2 = (tempVal1 % 0x186a0L) & 0xffffffffL;
                    step = 4;
                    continue stepLoop;
                }
                case 4: {
                    putI8(outStr, (tempVal1 / 0x186a0L) | 48L);
                    tempVal1 = (tempVal2 % 10000L) & 0xffffffffL;
                    outStr = outStr + 1L;
                    step = 5;
                    continue stepLoop;
                }
                case 5: {
                    putI8(outStr, (tempVal2 / 10000L) | 48L);
                    tempVal2 = (tempVal1 % 1000L) & 0xffffffffL;
                    outStr = outStr + 1L;
                    step = 6;
                    continue stepLoop;
                }
                case 6: {
                    putI8(outStr, tempVal1 / 1000L + 48L);
                    tempVal1 = (tempVal2 % 100L) & 0xffffffffL;
                    bytePtr = outStr + 1L;
                    step = 7;
                    continue stepLoop;
                }
                case 7: {
                    outStr = bytePtr + 1L;
                    putU8(bytePtr, (tempVal2 / 100L) | 48L);
                    flag = (tempVal1 % 10L) & 0xffL;
                    putI8(outStr, (tempVal1 / 10L) | 48L);
                    bytePtr = outStr + 1L;
                    outStr = bytePtr + 1L;
                    putU8(bytePtr, flag | 48L);
                    putI8(outStr, 32L);
                    return outStr;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long fCCToString(long realPtr, long outStr, long decimalChar, long thousandChar, long formatMode, long sigDigits, long displayDigits, long dateMode) {
        long flag1 = 0L;
        long exponent = 0L;
        long intVal = 0L;
        long flag2 = 0L;
        long flag3 = 0L;
        long bcdAcc = 0L;
        long strPtr1 = 0L;
        long strPtr2 = 0L;
        long index1 = 0L;
        long mantissa = 0L;
        long tempVal1 = 0L;
        long index2 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long index3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        index1 = (long) (int) getI64(realPtr);
                        if (index1 == 3L) {
                            bcdAcc = DateValidExtract(realPtr, framePtr + 16L, framePtr + 52L, framePtr + 48L, framePtr + 44L, dateMode & 1L, 0L);
                            if ((bcdAcc & 1L) != 0L) {
                                tempVal3 = getU32(framePtr + 52L);
                                exponent = getI64(framePtr + 16L) & 0xffffffffL;
                                if ((dateMode & 1L) == 0L) {
                                    tempVal3 = getI64(framePtr + 16L) & 0xffffffffL;
                                    exponent = getU32(framePtr + 52L);
                                }
                                strPtr1 = inttostr(exponent, outStr);
                                strPtr2 = inttostr(tempVal3, strPtr1 + 1L);
                                strPtr2 = strPtr2 + 1L;
                                tempVal3 = getU32(framePtr + 48L);
                                strPtr1 = inttostr(tempVal3, strPtr2);
                                putU8(strPtr1, 0L);
                                return outStr;
                            }
                            index1 = (long) (int) getI64(realPtr);
                        }
                        if (index1 == -2L) {
                            putU8(outStr + 4L, 0L);
                            putU8(outStr, 45L);
                            putU8(outStr + 1L, 73L);
                            putU8(outStr + 2L, 110L);
                            putU8(outStr + 3L, 102L);
                            return outStr;
                        }
                        if (index1 == 2L) {
                            putU8(outStr, 73L);
                            putU8(outStr + 1L, 110L);
                            putU8(outStr + 2L, 102L);
                            putU8(outStr + 3L, 0L);
                            return outStr;
                        }
                        if (index1 == 0L) {
                            putU8(outStr, 78L);
                            putU8(outStr + 1L, 97L);
                            putU8(outStr + 2L, 78L);
                            putU8(outStr + 3L, 0L);
                            return outStr;
                        }
                        putI64(framePtr + 24L, getI64(realPtr + 8L));
                        putI64(framePtr + 16L, getI64(realPtr));
                        if ((long) (int) displayDigits < 15L) {
                            if (-1L < (long) (int) displayDigits) {
                                bcdAcc = (long) (int) (displayDigits * -4L + 60L);
                                tempVal2 = -1L << (int) (bcdAcc & 63L);
                                while (true) {
                                    if (Long.compareUnsigned(5L << (int) ((displayDigits * -4L + 56L) & 63L), getI64(framePtr + 24L) & ~tempVal2) <= 0L) {
                                        putI64(framePtr + 24L, dcbAddAdjust(getI64(framePtr + 24L), 1L << (int) (bcdAcc & 63L)));
                                    }
                                    putI64(framePtr + 24L, getI64(framePtr + 24L) & tempVal2);
                                    if (((getI64(framePtr + 24L) >>> 60) & 0xffffffffL) == 0L) {
                                        break;
                                    }
                                    index3 = getI32(framePtr + 20L);
                                    putI64(framePtr + 24L, getI64(framePtr + 24L) >>> 4);
                                    index1 = (long) (int) (getI32(framePtr + 20L) + 1L);
                                    putI64(framePtr + 16L, (((index1 & 0xffffffffL)) << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                                    if (49998L < index3) {
                                        if ((long) (int) getI64(framePtr + 16L) < 1L) {
                                            putI64(framePtr + 16L, (((index1 & 0xffffffffL)) << 32) | 0xfffffffeL);
                                        } else {
                                            putI64(framePtr + 16L, (((index1 & 0xffffffffL)) << 32) | 2L);
                                        }
                                    }
                                }
                                if (getI64(framePtr + 24L) != 0L) {
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
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        putI64(framePtr + 24L, 0L);
                        putI64(framePtr + 16L, 1L);
                        step = 2;
                        continue stepLoop;
                    }
                    case 2: {
                        tempVal3 = getI32(framePtr + 20L) & 0xffffffffL;
                        switch ((int) (formatMode)) {
                            case 0: {
                                exponent = getU32(realPtr + 4L);
                                if ((long) (int) exponent < 14L) {
                                    mantissa = getI64(realPtr + 8L);
                                    if ((long) (int) exponent < 0L) {
                                        index3 = 0L;
                                        while (comma(mantissa = mantissa << 4, mantissa != 0L ? 1L : 0L) != 0L) {
                                            index3 = (long) (int) (index3 + 1L);
                                        }
                                    } else {
                                        mantissa = mantissa << (int) ((exponent * 4L + 8L) & 63L);
                                        if (mantissa == 0L) {
                                            index1 = 0L;
                                        } else {
                                            index1 = 0L;
                                            do {
                                                mantissa = mantissa << 4;
                                                index1 = (long) (int) (index1 + 1L);
                                            } while (mantissa != 0L);
                                        }
                                        index3 = (long) (int) (exponent + index1 + 1L);
                                    }
                                } else {
                                    index3 = (long) (int) (exponent + 1L);
                                }
                                if (getI32(framePtr + 20L) < -5L || (long) (int) displayDigits < (long) (int) (index3 - getI32(framePtr + 20L))) {
                                    if (Long.compareUnsigned(getI64(framePtr + 16L), 0L) < 0L || (long) (int) displayDigits <= getI32(framePtr + 20L)) {
                                        mantissa = getI64(realPtr + 8L);
                                        if (mantissa == 0L) {
                                            index1 = -1L;
                                        } else {
                                            index1 = -1L;
                                            do {
                                                mantissa = mantissa << 4;
                                                index1 = (long) (int) (index1 + 1L);
                                            } while (mantissa != 0L);
                                        }
                                        if (index1 <= (long) (int) displayDigits) {
                                            displayDigits = index1;
                                        }
                                        sigDigits = (long) (int) (displayDigits - 1L);
                                        step = 4;
                                        continue stepLoop;
                                    } else {
                                        if (index3 <= (long) (int) displayDigits) {
                                            displayDigits = index3;
                                        }
                                        index3 = (long) (int) (getI32(framePtr + 20L) + 1L);
                                        index1 = 0L;
                                        flag2 = 0L;
                                        index2 = (long) (int) (displayDigits - index3);
                                        step = 3;
                                        continue stepLoop;
                                    }
                                } else {
                                    thousandChar = 0L;
                                    flag2 = 0L;
                                    index1 = (long) (int) -getI32(framePtr + 20L);
                                    index2 = 0L;
                                    step = 3;
                                    continue stepLoop;
                                }
                            }
                            case 1: {
                                if (getU32(framePtr + 28L) == 0L || Long.compareUnsigned(getI64(framePtr + 16L), 0L) < 0L && (getI32(framePtr + 20L) != -1L || Long.compareUnsigned(getI64(framePtr + 24L), 0x500000000000000L) < 0L)) {
                                    if ((long) (int) sigDigits < 15L) {
                                        if (-1L < (long) (int) sigDigits) {
                                            bcdAcc = (long) (int) (sigDigits * -4L + 60L);
                                            tempVal2 = -1L << (int) (bcdAcc & 63L);
                                            while (true) {
                                                if (Long.compareUnsigned(5L << (int) ((sigDigits * -4L + 56L) & 63L), getI64(framePtr + 24L) & ~tempVal2) <= 0L) {
                                                    putI64(framePtr + 24L, dcbAddAdjust(getI64(framePtr + 24L), 1L << (int) (bcdAcc & 63L)));
                                                }
                                                putI64(framePtr + 24L, getI64(framePtr + 24L) & tempVal2);
                                                if (((getI64(framePtr + 24L) >>> 60) & 0xffffffffL) == 0L) {
                                                    break;
                                                }
                                                index3 = getI32(framePtr + 20L);
                                                putI64(framePtr + 24L, getI64(framePtr + 24L) >>> 4);
                                                index1 = (long) (int) (getI32(framePtr + 20L) + 1L);
                                                putI64(framePtr + 16L, (((index1 & 0xffffffffL)) << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                                                if (49998L < index3) {
                                                    if ((long) (int) getI64(framePtr + 16L) < 1L) {
                                                        putI64(framePtr + 16L, (((index1 & 0xffffffffL)) << 32) | 0xfffffffeL);
                                                    } else {
                                                        putI64(framePtr + 16L, (((index1 & 0xffffffffL)) << 32) | 2L);
                                                    }
                                                }
                                            }
                                            step = 6;
                                            continue stepLoop;
                                        } else {
                                            step = 8;
                                            continue stepLoop;
                                        }
                                    } else {
                                        step = 7;
                                        continue stepLoop;
                                    }
                                } else {
                                    bcdAcc = getI64(framePtr + 24L);
                                    step = 9;
                                    continue stepLoop;
                                }
                            }
                            case 2: {
                                bcdAcc = getI64(framePtr + 24L);
                                step = 16;
                                continue stepLoop;
                            }
                            case 3: {
                                if (getI32(framePtr + 20L) == 0L) {
                                    tempVal3 = 0L;
                                    index3 = 1L;
                                } else {
                                    index1 = (long) (int) (getI32(framePtr + 20L) % 3L);
                                    index3 = index1;
                                    if (getI32(framePtr + 20L) < 1L) {
                                        index3 = 0L;
                                        if (index1 != 0L) {
                                            index3 = (long) (int) (index1 + 3L);
                                        }
                                    }
                                    tempVal3 = (getI32(framePtr + 20L) - index3) & 0xffffffffL;
                                    index3 = (long) (int) (index3 + 1L);
                                }
                                bcdAcc = getI64(framePtr + 24L);
                                exponent = getI32(framePtr + 20L) & 0xffffffffL;
                                step = 18;
                                continue stepLoop;
                            }
                            default: {
                                index1 = 0L;
                                index3 = 0L;
                                flag2 = 0L;
                                index2 = 0L;
                                step = 3;
                                continue stepLoop;
                            }
                        }
                    }
                    case 3: {
                        if ((long) (int) getI64(realPtr) < 0L) {
                            strPtr1 = outStr + 1L;
                            putU8(outStr, 45L);
                            if (index1 != 0L) {
                                step = 22;
                                continue stepLoop;
                            } else {
                                step = 23;
                                continue stepLoop;
                            }
                        } else {
                            strPtr1 = outStr;
                            if (index1 == 0L) {
                                step = 23;
                                continue stepLoop;
                            } else {
                                step = 22;
                                continue stepLoop;
                            }
                        }
                    }
                    case 4: {
                        index1 = 0L;
                        index3 = 1L;
                        flag2 = 1L;
                        index2 = (long) (int) sigDigits;
                        step = 3;
                        continue stepLoop;
                    }
                    case 5: {
                        if ((long) (int) tempVal3 < 14L) {
                            if ((long) (int) tempVal3 < 0L) {
                                index3 = 0L;
                                mantissa = getI64(framePtr + 24L) << 4;
                                if (mantissa != 0L) {
                                    do {
                                        mantissa = mantissa << 4;
                                        index3 = (long) (int) (index3 + 1L);
                                    } while (mantissa != 0L);
                                    step = 11;
                                    continue stepLoop;
                                } else {
                                    step = 10;
                                    continue stepLoop;
                                }
                            } else {
                                mantissa = getI64(framePtr + 24L) << (int) ((tempVal3 * 4L + 8L) & 63L);
                                if (mantissa == 0L) {
                                    index1 = 0L;
                                } else {
                                    index1 = 0L;
                                    do {
                                        mantissa = mantissa << 4;
                                        index1 = (long) (int) (index1 + 1L);
                                    } while (mantissa != 0L);
                                }
                                index3 = (long) (int) (tempVal3 + index1 + 1L);
                                step = 11;
                                continue stepLoop;
                            }
                        } else {
                            step = 12;
                            continue stepLoop;
                        }
                    }
                    case 6: {
                        if (getI64(framePtr + 24L) != 0L) {
                            step = 7;
                            continue stepLoop;
                        } else {
                            step = 8;
                            continue stepLoop;
                        }
                    }
                    case 7: {
                        tempVal3 = getI32(framePtr + 20L) & 0xffffffffL;
                        step = 5;
                        continue stepLoop;
                    }
                    case 8: {
                        putI64(framePtr + 24L, 0L);
                        putI64(framePtr + 16L, 1L);
                        tempVal3 = getI32(framePtr + 20L) & 0xffffffffL;
                        step = 5;
                        continue stepLoop;
                    }
                    case 9: {
                        if (comma(comma(index1 = (long) (int) (sigDigits + 1L + tempVal3), putI64(framePtr + 24L, bcdAcc)), index1 < 15L ? 1L : 0L) != 0L) {
                            if (index1 < 0L) {
                                step = 8;
                                continue stepLoop;
                            } else {
                                tempVal2 = (long) (int) (index1 * -4L + 60L);
                                tempVal1 = -1L << (int) (tempVal2 & 63L);
                                if (Long.compareUnsigned(5L << (int) ((index1 * -4L + 56L) & 63L), bcdAcc & ((tempVal1 ^ -1L))) <= 0L) {
                                    bcdAcc = dcbAddAdjust(bcdAcc, 1L << (int) (tempVal2 & 63L));
                                }
                                putI64(framePtr + 24L, bcdAcc & tempVal1);
                                if (((getI64(framePtr + 24L) >>> 60) & 0xffffffffL) == 0L) {
                                    step = 6;
                                    continue stepLoop;
                                } else {
                                    bcdAcc = getI64(framePtr + 24L) >>> 4;
                                    tempVal3 = (getI32(framePtr + 20L) + 1L) & 0xffffffffL;
                                    if (49998L < getI32(framePtr + 20L)) {
                                        if ((long) (int) getI64(framePtr + 16L) < 1L) {
                                            putI32(framePtr + 16L, -2L);
                                        } else {
                                            putI32(framePtr + 16L, 2L);
                                        }
                                    }
                                    putI64(framePtr + 16L, (tempVal3 << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                                    step = 9;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            step = 5;
                            continue stepLoop;
                        }
                    }
                    case 10: {
                        if ((long) (int) displayDigits < (long) (int) (index3 - tempVal3)) {
                            putI64(framePtr + 24L, getI64(realPtr + 8L));
                            putI64(framePtr + 16L, getI64(realPtr));
                            index2 = (long) (int) sigDigits;
                            if ((long) (int) displayDigits <= (long) (int) sigDigits) {
                                index2 = (long) (int) (displayDigits - 1L);
                            }
                            if ((long) (int) sigDigits < 14L) {
                                if (-2L < (long) (int) sigDigits) {
                                    bcdAcc = (long) (int) (sigDigits * -4L + 56L);
                                    tempVal2 = -1L << (int) (bcdAcc & 63L);
                                    while (true) {
                                        if (Long.compareUnsigned(5L << (int) ((sigDigits * -4L + 52L) & 63L), getI64(framePtr + 24L) & ~tempVal2) <= 0L) {
                                            putI64(framePtr + 24L, dcbAddAdjust(getI64(framePtr + 24L), 1L << (int) (bcdAcc & 63L)));
                                        }
                                        putI64(framePtr + 24L, getI64(framePtr + 24L) & tempVal2);
                                        if (((getI64(framePtr + 24L) >>> 60) & 0xffffffffL) == 0L) {
                                            break;
                                        }
                                        index3 = getI32(framePtr + 20L);
                                        putI64(framePtr + 24L, getI64(framePtr + 24L) >>> 4);
                                        index1 = (long) (int) (getI32(framePtr + 20L) + 1L);
                                        putI64(framePtr + 16L, (((index1 & 0xffffffffL)) << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                                        if (49998L < index3) {
                                            if ((long) (int) getI64(framePtr + 16L) < 1L) {
                                                putI64(framePtr + 16L, (((index1 & 0xffffffffL)) << 32) | 0xfffffffeL);
                                            } else {
                                                putI64(framePtr + 16L, (((index1 & 0xffffffffL)) << 32) | 2L);
                                            }
                                        }
                                    }
                                    if (getI64(framePtr + 24L) != 0L) {
                                        step = 14;
                                        continue stepLoop;
                                    } else {
                                        step = 13;
                                        continue stepLoop;
                                    }
                                } else {
                                    step = 13;
                                    continue stepLoop;
                                }
                            } else {
                                step = 14;
                                continue stepLoop;
                            }
                        } else {
                            intVal = (long) (int) (sigDigits - index3 + tempVal3);
                            thousandChar = 0L;
                            flag2 = 0L;
                            index1 = (long) (int) -tempVal3;
                            index2 = 0L;
                            if (-1L < intVal) {
                                index2 = (long) (int) (intVal + 1L);
                            }
                            step = 3;
                            continue stepLoop;
                        }
                    }
                    case 11: {
                        if (-1L < (long) (int) tempVal3) {
                            step = 12;
                            continue stepLoop;
                        } else {
                            step = 10;
                            continue stepLoop;
                        }
                    }
                    case 12: {
                        if ((long) (int) displayDigits <= (long) (int) tempVal3) {
                            step = 15;
                            continue stepLoop;
                        } else {
                            index3 = (long) (int) (tempVal3 + 1L);
                            index1 = 0L;
                            flag2 = 0L;
                            index2 = (long) (int) (displayDigits - index3);
                            if ((long) (int) sigDigits <= (long) (int) (displayDigits - index3)) {
                                index2 = (long) (int) sigDigits;
                            }
                            step = 3;
                            continue stepLoop;
                        }
                    }
                    case 13: {
                        putI64(framePtr + 24L, 0L);
                        putI64(framePtr + 16L, 1L);
                        step = 14;
                        continue stepLoop;
                    }
                    case 14: {
                        index1 = 0L;
                        index3 = 1L;
                        flag2 = 1L;
                        tempVal3 = getI32(framePtr + 20L) & 0xffffffffL;
                        step = 3;
                        continue stepLoop;
                    }
                    case 15: {
                        if ((long) (int) displayDigits <= (long) (int) sigDigits) {
                            sigDigits = (long) (int) (displayDigits - 1L);
                        }
                        step = 4;
                        continue stepLoop;
                    }
                    case 16: {
                        if (comma(comma(index1 = (long) (int) (sigDigits + 1L + tempVal3), putI64(framePtr + 24L, bcdAcc)), index1 < 15L ? 1L : 0L) != 0L) {
                            if (index1 < 0L) {
                                step = 17;
                                continue stepLoop;
                            } else {
                                tempVal2 = (long) (int) (index1 * -4L + 60L);
                                tempVal1 = -1L << (int) (tempVal2 & 63L);
                                if (Long.compareUnsigned(5L << (int) ((index1 * -4L + 56L) & 63L), bcdAcc & ((tempVal1 ^ -1L))) <= 0L) {
                                    bcdAcc = dcbAddAdjust(bcdAcc, 1L << (int) (tempVal2 & 63L));
                                }
                                putI64(framePtr + 24L, bcdAcc & tempVal1);
                                if (((getI64(framePtr + 24L) >>> 60) & 0xffffffffL) == 0L) {
                                    tempVal3 = getI32(framePtr + 20L) & 0xffffffffL;
                                    if (getI64(framePtr + 24L) == 0L) {
                                        step = 17;
                                        continue stepLoop;
                                    } else {
                                        step = 15;
                                        continue stepLoop;
                                    }
                                } else {
                                    bcdAcc = getI64(framePtr + 24L) >>> 4;
                                    tempVal3 = (getI32(framePtr + 20L) + 1L) & 0xffffffffL;
                                    if (49998L < getI32(framePtr + 20L)) {
                                        if ((long) (int) getI64(framePtr + 16L) < 1L) {
                                            putI32(framePtr + 16L, -2L);
                                        } else {
                                            putI32(framePtr + 16L, 2L);
                                        }
                                    }
                                    putI64(framePtr + 16L, (tempVal3 << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                                    step = 16;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            step = 15;
                            continue stepLoop;
                        }
                    }
                    case 17: {
                        putI64(framePtr + 24L, 0L);
                        putI64(framePtr + 16L, 1L);
                        tempVal3 = 0L;
                        step = 15;
                        continue stepLoop;
                    }
                    case 18: {
                        if (comma(comma(index1 = (long) (int) (sigDigits + index3 + 1L + exponent), putI64(framePtr + 24L, bcdAcc)), index1 < 15L ? 1L : 0L) != 0L) {
                            if (index1 < 0L) {
                                step = 20;
                                continue stepLoop;
                            } else {
                                tempVal2 = (long) (int) (index1 * -4L + 60L);
                                tempVal1 = -1L << (int) (tempVal2 & 63L);
                                if (Long.compareUnsigned(5L << (int) ((index1 * -4L + 56L) & 63L), bcdAcc & ((tempVal1 ^ -1L))) <= 0L) {
                                    bcdAcc = dcbAddAdjust(bcdAcc, 1L << (int) (tempVal2 & 63L));
                                }
                                putI64(framePtr + 24L, bcdAcc & tempVal1);
                                if (((getI64(framePtr + 24L) >>> 60) & 0xffffffffL) == 0L) {
                                    if (getI64(framePtr + 24L) == 0L) {
                                        step = 20;
                                        continue stepLoop;
                                    } else {
                                        step = 19;
                                        continue stepLoop;
                                    }
                                } else {
                                    bcdAcc = getI64(framePtr + 24L) >>> 4;
                                    exponent = (getI32(framePtr + 20L) + 1L) & 0xffffffffL;
                                    if (49998L < getI32(framePtr + 20L)) {
                                        if ((long) (int) getI64(framePtr + 16L) < 1L) {
                                            putI32(framePtr + 16L, -2L);
                                        } else {
                                            putI32(framePtr + 16L, 2L);
                                        }
                                    }
                                    putI64(framePtr + 16L, (exponent << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                                    step = 18;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            step = 19;
                            continue stepLoop;
                        }
                    }
                    case 19: {
                        index1 = 0L;
                        flag2 = 1L;
                        index2 = (long) (int) (displayDigits - index3);
                        if ((long) (int) sigDigits <= (long) (int) (displayDigits - index3)) {
                            index2 = (long) (int) sigDigits;
                        }
                        step = 3;
                        continue stepLoop;
                    }
                    case 20: {
                        putI64(framePtr + 24L, 0L);
                        putI64(framePtr + 16L, 1L);
                        step = 19;
                        continue stepLoop;
                    }
                    case 21: {
                        strPtr2 = strPtr1;
                        if (0L < index3) {
                            do {
                                index1 = (long) (int) (index3 - 1L);
                                strPtr1 = strPtr2 + 1L;
                                putU8(strPtr2, (getU8(framePtr + 31L) & 15L) | 48L);
                                putI64(framePtr + 24L, getI64(framePtr + 24L) << 4);
                                if ((thousandChar & 0xffL) != 0L && index1 != 0L && (long) (int) (index1 % 3L) == 0L) {
                                    strPtr1 = strPtr2 + 2L;
                                    putU8(strPtr2 + 1L, thousandChar);
                                }
                                flag1 = 1L < index3 ? 1L : 0L;
                                strPtr2 = strPtr1;
                                index3 = index1;
                            } while (flag1 != 0L);
                        }
                        if (index2 != 0L) {
                            if (flag3 != 0L) {
                                putU8(strPtr1, decimalChar);
                                strPtr1 = strPtr1 + 1L;
                            }
                            if (0L < index2) {
                                index2 = (long) (int) (index2 + 1L);
                                strPtr2 = strPtr1;
                                do {
                                    strPtr1 = strPtr2 + 1L;
                                    putU8(strPtr2, ((getI64(framePtr + 24L) >>> 56) & 15L) | 48L);
                                    index2 = (long) (int) (index2 - 1L);
                                    putI64(framePtr + 24L, getI64(framePtr + 24L) << 4);
                                    strPtr2 = strPtr1;
                                } while (1L < index2);
                            }
                        }
                        if (((((flag2 ^ 1L) | (tempVal3 == 0L ? 1L : 0L))) & 0xffL) != 0L) {
                            putU8(strPtr1, 0L);
                            return outStr;
                        }
                        strPtr2 = strPtr1 + 1L;
                        putU8(strPtr1, 69L);
                        if ((long) (int) tempVal3 < 0L) {
                            strPtr2 = strPtr1 + 2L;
                            tempVal3 = -tempVal3 & 0xffffffffL;
                            putU8(strPtr1 + 1L, 45L);
                        }
                        strPtr1 = inttostr(tempVal3, strPtr2);
                        putU8(strPtr1, 0L);
                        return outStr;
                    }
                    case 22: {
                        strPtr2 = strPtr1 + 2L;
                        putU8(strPtr1, 48L);
                        putU8(strPtr1 + 1L, decimalChar);
                        if (index1 < 2L) {
                            flag3 = 0L;
                            strPtr1 = strPtr2;
                            step = 21;
                            continue stepLoop;
                        } else {
                            bcdAcc = ((index1 - 2L) & 0xffffffffL) + 1L;
                            memset(strPtr2, 48L, bcdAcc);
                            if (((index1 - 2L) & 0xffffffffL) == 0L) {
                                step = 24;
                                continue stepLoop;
                            } else {
                                tempVal1 = bcdAcc & 0x1fffffffeL;
                                index1 = (long) (int) (index1 - tempVal1);
                                strPtr2 = strPtr2 + tempVal1;
                                tempVal2 = tempVal1;
                                do {
                                    tempVal2 = tempVal2 - 2L;
                                } while (tempVal2 != 0L);
                                if (bcdAcc != tempVal1) {
                                    step = 24;
                                    continue stepLoop;
                                } else {
                                    flag3 = 0L;
                                    strPtr1 = strPtr2;
                                    step = 21;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 23: {
                        flag3 = 1L;
                        step = 21;
                        continue stepLoop;
                    }
                    case 24: {
                        index1 = (long) (int) (index1 + 1L);
                        do {
                            index1 = (long) (int) (index1 - 1L);
                            strPtr2 = strPtr2 + 1L;
                        } while (2L < index1);
                        flag3 = 0L;
                        strPtr1 = strPtr2;
                        step = 21;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long firnd(long srcPtr, long decimalsPtr, long mode, long resultPtr) {
        long result = 0L;
        result = fRoundOrTruncMantissa(srcPtr, (long) (int) decimalsPtr, mode, resultPtr & 1L);
        return result;
    }

    long fStringToCC_er(long strPtr, long resultPtr, long decimalChar, long thousandChar, long dateMode, long outEndPtr) {
        long bytePtr1 = 0L;
        long tempVal1 = 0L;
        long intVal = 0L;
        long flag1 = 0L;
        long flag2 = 0L;
        long flag3 = 0L;
        long flag4 = 0L;
        long flag5 = 0L;
        long flag6 = 0L;
        long index1 = 0L;
        long constPtr = 0L;
        long index2 = 0L;
        long index3 = 0L;
        long tempVal2 = 0L;
        long bytePtr2 = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        long tempVal5 = 0L;
        long mantissa = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    index1 = 0L;
                    step = 1;
                    continue stepLoop;
                }
                case 1: {
                    bytePtr2 = strPtr + index1;
                    if (getU8(bytePtr2) == 0L || index1 == 3L) {
                        putI64(resultPtr, 0L);
                        putI64(resultPtr + 8L, 0L);
                        strPtr = strPtr + 3L;
                        putI64(resultPtr, 1L);
                        step = 6;
                        continue stepLoop;
                    } else {
                        bytePtr1 = ADDR_RODATA + 1116L + index1;
                        index1 = index1 + 1L;
                        if (getU8(bytePtr2) == getU8(bytePtr1)) {
                            step = 1;
                            continue stepLoop;
                        } else {
                            index1 = 0L;
                            step = 2;
                            continue stepLoop;
                        }
                    }
                }
                case 2: {
                    bytePtr2 = strPtr + index1;
                    if (getU8(bytePtr2) == 0L || index1 == 3L) {
                        mantissa = 0x999999999999999L;
                        tempVal5 = 0xc34f00000002L;
                        strPtr = strPtr + 3L;
                        putI64(resultPtr + 8L, mantissa);
                        putI64(resultPtr, tempVal5);
                        step = 6;
                        continue stepLoop;
                    } else {
                        bytePtr1 = ADDR_RODATA + 1120L + index1;
                        index1 = index1 + 1L;
                        if (getU8(bytePtr2) == getU8(bytePtr1)) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            index1 = 0L;
                            step = 3;
                            continue stepLoop;
                        }
                    }
                }
                case 3: {
                    bytePtr2 = strPtr + index1;
                    if (getU8(bytePtr2) == 0L || index1 == 4L) {
                        constPtr = CC_PInf;
                        step = 4;
                        continue stepLoop;
                    } else {
                        bytePtr1 = ADDR_RODATA + 1129L + index1;
                        index1 = index1 + 1L;
                        if (getU8(bytePtr2) == getU8(bytePtr1)) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            index1 = 0L;
                            step = 5;
                            continue stepLoop;
                        }
                    }
                }
                case 4: {
                    mantissa = getI64(constPtr + 8L);
                    tempVal5 = getI64(constPtr);
                    strPtr = strPtr + 4L;
                    putI64(resultPtr + 8L, mantissa);
                    putI64(resultPtr, tempVal5);
                    step = 6;
                    continue stepLoop;
                }
                case 5: {
                    bytePtr2 = strPtr + index1;
                    if (getU8(bytePtr2) == 0L || index1 == 4L) {
                        constPtr = CC_NInf;
                        step = 4;
                        continue stepLoop;
                    } else {
                        bytePtr1 = ADDR_RODATA + 1124L + index1;
                        index1 = index1 + 1L;
                        if (getU8(bytePtr2) == getU8(bytePtr1)) {
                            step = 5;
                            continue stepLoop;
                        } else {
                            flag1 = getU8(strPtr);
                            index2 = -1L;
                            putI64(resultPtr + 8L, 0L);
                            tempVal1 = 1L;
                            if (flag1 == 45L) {
                                tempVal1 = 0xffffffffL;
                            }
                            tempVal2 = flag1 == 45L || flag1 == 43L ? 1L : 0L;
                            putU32(resultPtr, tempVal1);
                            putU32(resultPtr + 4L, 0xffffffffL);
                            flag1 = getU8(strPtr + tempVal2);
                            tempVal4 = flag1;
                            tempVal3 = flag1;
                            if (tempVal2 != 0L) {
                                strPtr = strPtr + 1L;
                            }
                            flag5 = flag1 == (dateMode & 0xffL) ? 1L : 0L;
                            flag6 = flag1 == (outEndPtr & 0xffL) ? 1L : 0L;
                            if ((long) (int) (flag1 - 48L) < 10L || (dateMode & 0xffL) != 0L && flag5 != 0L || (outEndPtr & 0xffL) != 0L && flag6 != 0L) {
                                flag2 = 0L;
                                flag3 = 0L;
                                index1 = 0L;
                                index2 = -1L;
                                index3 = 14L;
                                flag4 = 1L;
                                step = 8;
                                continue stepLoop;
                            } else {
                                index1 = 0L;
                                flag4 = 1L;
                                step = 7;
                                continue stepLoop;
                            }
                        }
                    }
                }
                case 6: {
                    if ((thousandChar & 1L) != 0L && getU8(strPtr) != 0L) {
                        if (decimalChar != 0L) {
                            putI64(decimalChar, strPtr);
                        }
                        return 22L;
                    }
                    if (decimalChar != 0L) {
                        putI64(decimalChar, strPtr);
                    }
                    return 0L;
                }
                case 7: {
                    if (tempVal3 == 69L) {
                        bytePtr2 = strPtr + 1L;
                        if (getU8(bytePtr2) == 43L) {
                            flag5 = 0L;
                            bytePtr2 = strPtr + 2L;
                        } else {
                            if (getU8(bytePtr2) == 45L) {
                                bytePtr2 = strPtr + 2L;
                                flag5 = 1L;
                            } else {
                                flag5 = 0L;
                            }
                        }
                        tempVal2 = getU8(bytePtr2);
                        strPtr = bytePtr2;
                        if ((long) (int) (getU8(bytePtr2) - 48L) < 10L) {
                            index3 = 0L;
                            step = 13;
                            continue stepLoop;
                        } else {
                            putI32(resultPtr + 4L, index2);
                            step = 12;
                            continue stepLoop;
                        }
                    } else {
                        step = 12;
                        continue stepLoop;
                    }
                }
                case 8: {
                    if (49999L < index2) {
                        step = 9;
                        continue stepLoop;
                    } else {
                        if (flag6 == 0L) {
                            if (flag5 != 0L) {
                                if (flag2 != 0L) {
                                    putI64(resultPtr, 0L);
                                    putI64(resultPtr + 8L, 0L);
                                    if (decimalChar != 0L) {
                                        putI64(decimalChar, strPtr);
                                    }
                                    return 20L;
                                }
                                flag2 = 1L;
                                step = 11;
                                continue stepLoop;
                            } else {
                                if (tempVal4 == 48L) {
                                    if (flag3 == 0L) {
                                        flag3 = 0L;
                                        if (flag2 != 0L) {
                                            index2 = (long) (int) (index2 - 1L);
                                            flag4 = 0L;
                                            putI32(resultPtr + 4L, index2);
                                            flag2 = 1L;
                                        } else {
                                            flag2 = 0L;
                                            flag4 = 0L;
                                        }
                                        step = 11;
                                        continue stepLoop;
                                    } else {
                                        step = 10;
                                        continue stepLoop;
                                    }
                                } else {
                                    if (-1L < index3) {
                                        index1 = index1 + ((tempVal4 - 48L) << (int) ((index3 << 2) & 63L));
                                        putI64(resultPtr + 8L, index1);
                                    }
                                    step = 10;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            step = 11;
                            continue stepLoop;
                        }
                    }
                }
                case 9: {
                    putI64(resultPtr, 0L);
                    putI64(resultPtr + 8L, 0L);
                    if (decimalChar != 0L) {
                        putI64(decimalChar, strPtr);
                    }
                    return 19L;
                }
                case 10: {
                    if (flag2 == 0L) {
                        index2 = (long) (int) (index2 + 1L);
                        putI32(resultPtr + 4L, index2);
                    }
                    flag4 = 0L;
                    index3 = (long) (int) (index3 - 1L);
                    flag3 = 1L;
                    step = 11;
                    continue stepLoop;
                }
                case 11: {
                    strPtr = strPtr + 1L;
                    flag1 = getU8(strPtr);
                    tempVal4 = flag1;
                    tempVal3 = flag1;
                    flag5 = tempVal3 == (dateMode & 0xffL) ? 1L : 0L;
                    flag6 = flag1 == (outEndPtr & 0xffL) ? 1L : 0L;
                    if (((tempVal3 - 48L) & 0xffffffffL) < 10L || (dateMode & 0xffL) != 0L && flag5 != 0L || (outEndPtr & 0xffL) != 0L && flag6 != 0L) {
                        step = 8;
                        continue stepLoop;
                    } else {
                        step = 7;
                        continue stepLoop;
                    }
                }
                case 12: {
                    if (flag4 != 0L) {
                        putI64(resultPtr, 0L);
                        putI64(resultPtr + 8L, 0L);
                        if (decimalChar != 0L) {
                            putI64(decimalChar, strPtr);
                        }
                        return 21L;
                    }
                    step = 14;
                    continue stepLoop;
                }
                case 13: {
                    strPtr = strPtr + 1L;
                    index3 = (long) (int) (index3 * 10L + tempVal2);
                    if (50047L < index3) {
                        step = 9;
                        continue stepLoop;
                    } else {
                        tempVal2 = getU8(strPtr);
                        index3 = (long) (int) (index3 - 48L);
                        if (((tempVal2 - 48L) & 0xffffffffL) < 10L) {
                            step = 13;
                            continue stepLoop;
                        } else {
                            intVal = (long) (int) -index3;
                            if (flag5 == 0L) {
                                intVal = index3;
                            }
                            putI32(resultPtr + 4L, index2 + intVal);
                            step = 14;
                            continue stepLoop;
                        }
                    }
                }
                case 14: {
                    if (index1 == 0L) {
                        putI64(resultPtr, 1L);
                    }
                    step = 6;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    void fNextAfter(long srcPtr, long dirPtr) {
        long bcdAcc = 0L;
        long exponent = 0L;
        long mantissa = 0L;
        long constPtr = 0L;
        long tempVal = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if ((dirPtr & 1L) == 0L) {
                        if (getI32(srcPtr + 12L) == 0L) {
                            constPtr = CC_NMin;
                            tempVal = getI64(constPtr);
                            putI64(srcPtr + 8L, getI64(constPtr + 8L));
                            putI64(srcPtr, tempVal);
                            return;
                        }
                        mantissa = getI64(srcPtr + 8L);
                        if (getI32(srcPtr) < 1L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            step = 1;
                            continue stepLoop;
                        }
                    } else {
                        if (getI32(srcPtr + 12L) == 0L) {
                            constPtr = CC_Min;
                            tempVal = getI64(constPtr);
                            putI64(srcPtr + 8L, getI64(constPtr + 8L));
                            putI64(srcPtr, tempVal);
                            return;
                        }
                        mantissa = getI64(srcPtr + 8L);
                        if (0L < getI32(srcPtr)) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            step = 1;
                            continue stepLoop;
                        }
                    }
                }
                case 1: {
                    bcdAcc = dcbSubAdjust(1L, mantissa);
                    putI64(srcPtr + 8L, bcdAcc);
                    if (bcdAcc >>> 32 != 0L) {
                        return;
                    }
                    if (getI32(srcPtr + 4L) != -49999L) {
                        bcdAcc = 0x999999999999999L;
                        exponent = (long) (int) (getI32(srcPtr + 4L) - 1L);
                        putI64(srcPtr + 8L, bcdAcc);
                        putI32(srcPtr + 4L, exponent);
                        return;
                    }
                    constPtr = CC_0;
                    tempVal = getI64(constPtr);
                    putI64(srcPtr + 8L, getI64(constPtr + 8L));
                    putI64(srcPtr, tempVal);
                    return;
                }
                case 2: {
                    bcdAcc = dcbAddAdjust(mantissa, 1L);
                    putI64(srcPtr + 8L, bcdAcc);
                    if (((bcdAcc >>> 60) & 0xffffffffL) == 0L) {
                        return;
                    }
                    if (getI32(srcPtr + 4L) != 49999L) {
                        bcdAcc = bcdAcc >>> 4;
                        exponent = (long) (int) (getI32(srcPtr + 4L) + 1L);
                        putI64(srcPtr + 8L, bcdAcc);
                        putI32(srcPtr + 4L, exponent);
                        return;
                    }
                    constPtr = CC_Max;
                    tempVal = getI64(constPtr);
                    putI64(srcPtr + 8L, getI64(constPtr + 8L));
                    putI64(srcPtr, tempVal);
                    return;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    void fNextAfter_L(long srcPtr, long dirPtr) {
        long bcdAcc = 0L;
        long exponent = 0L;
        long constPtr = 0L;
        long tempVal = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if ((dirPtr & 1L) == 0L) {
                        if (getI32(srcPtr + 12L) == 0L) {
                            constPtr = CC_NMin;
                            tempVal = getI64(constPtr);
                            putI64(srcPtr + 8L, getI64(constPtr + 8L));
                            putI64(srcPtr, tempVal);
                            return;
                        }
                        if (getI32(srcPtr) < 1L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            step = 1;
                            continue stepLoop;
                        }
                    } else {
                        if (getI32(srcPtr + 12L) == 0L) {
                            constPtr = CC_Min;
                            tempVal = getI64(constPtr);
                            putI64(srcPtr + 8L, getI64(constPtr + 8L));
                            putI64(srcPtr, tempVal);
                            return;
                        }
                        if (0L < getI32(srcPtr)) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            step = 1;
                            continue stepLoop;
                        }
                    }
                }
                case 1: {
                    bcdAcc = dcbSubAdjust(4096L, getI64(srcPtr + 8L));
                    putI64(srcPtr + 8L, bcdAcc);
                    if (bcdAcc >>> 32 != 0L) {
                        return;
                    }
                    if (getI32(srcPtr + 4L) != -499L) {
                        exponent = (long) (int) (getI32(srcPtr + 4L) - 1L);
                        putI32(srcPtr + 8L, 0xffffffff99999000L);
                        putI32(srcPtr + 12L, 0x9999999L);
                        putI32(srcPtr + 4L, exponent);
                        return;
                    }
                    constPtr = CC_0;
                    tempVal = getI64(constPtr);
                    putI64(srcPtr + 8L, getI64(constPtr + 8L));
                    putI64(srcPtr, tempVal);
                    return;
                }
                case 2: {
                    bcdAcc = dcbAddAdjust(getI64(srcPtr + 8L), 4096L);
                    putI64(srcPtr + 8L, bcdAcc);
                    if (((bcdAcc >>> 32) & 0xffffffffL) < 0x1000001L) {
                        return;
                    }
                    if (getI32(srcPtr + 4L) != 499L) {
                        exponent = (long) (int) (getI32(srcPtr + 4L) + 1L);
                        putI64(srcPtr + 8L, bcdAcc >>> 4);
                        putI32(srcPtr + 4L, exponent);
                        return;
                    }
                    constPtr = CC_Max;
                    tempVal = getI64(constPtr);
                    putI64(srcPtr + 8L, getI64(constPtr + 8L));
                    putI64(srcPtr, tempVal);
                    return;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long fidiv2(long srcPtr, long resultPtr) {
        long tempVal1 = 0L;
        long flag = 0L;
        long tempVal2 = 0L;
        tempVal1 = (getI32(srcPtr) + 1L) & 0xffffffffL;
        if (tempVal1 < 5L && ((1L << (int) (tempVal1 & 31L)) & 21L) != 0L) {
            flag = fdiv2(srcPtr, resultPtr) != 0L ? 1L : 0L;
            return flag;
        }
        if (getI32(srcPtr) != 0L) {
            tempVal2 = getI64(srcPtr);
            putI64(resultPtr + 8L, getI64(srcPtr + 8L));
            putI64(resultPtr, tempVal2);
            return 0L;
        } else {
            putI64(resultPtr, 0L);
            putI64(resultPtr + 8L, 0L);
            return 0L;
        }
    }

    long fdiv2(long srcPtr, long resultPtr) {
        long exponent = 0L;
        long bcdAcc1 = 0L;
        long bcdAcc2 = 0L;
        long tempVal = 0L;
        long mantissa = 0L;
        if (((getI32(srcPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI32(srcPtr) + 1L) & 31L)) & 21L) != 0L && getI32(srcPtr + 12L) == 0L) {
            mantissa = getI64(srcPtr + 8L);
            tempVal = getI64(srcPtr);
        } else {
            bcdAcc1 = dcbAddAdjust(getI64(srcPtr + 8L), getI64(srcPtr + 8L));
            bcdAcc1 = dcbAddAdjust(bcdAcc1, bcdAcc1);
            bcdAcc2 = dcbAddAdjust(bcdAcc1, getI64(srcPtr + 8L));
            putI64(resultPtr + 8L, bcdAcc2);
            exponent = getI32(srcPtr);
            if (exponent == 3L) {
                exponent = 1L;
            }
            putI32(resultPtr, exponent);
            if (((bcdAcc2 >>> 60) & 0xffffffffL) != 0L) {
                putI64(resultPtr + 8L, bcdAcc2 >>> 4);
                putI32(resultPtr + 4L, getI32(srcPtr + 4L));
                return (bcdAcc2 & 15L) != 0L ? 1L : 0L;
            }
            exponent = getI32(srcPtr + 4L);
            putI32(resultPtr + 4L, exponent - 1L);
            if (-49999L < exponent) {
                return 0L;
            }
            mantissa = 0L;
            tempVal = 1L;
        }
        putI64(resultPtr + 8L, mantissa);
        putI64(resultPtr, tempVal);
        return 0L;
    }

    long fimul2(long srcPtr, long resultPtr) {
        long tempVal1 = 0L;
        long exponent = 0L;
        long bcdAcc = 0L;
        long tempVal2 = 0L;
        tempVal1 = (getI32(srcPtr) + 1L) & 0xffffffffL;
        if (tempVal1 < 5L && ((1L << (int) (tempVal1 & 31L)) & 21L) != 0L) {
            if (getI32(srcPtr + 12L) == 0L) {
                tempVal2 = getI64(srcPtr);
                putI64(resultPtr + 8L, getI64(srcPtr + 8L));
                putI64(resultPtr, tempVal2);
                return 0L;
            }
            bcdAcc = dcbAddAdjust(getI64(srcPtr + 8L), getI64(srcPtr + 8L));
            putI64(resultPtr + 8L, bcdAcc);
            exponent = getI32(srcPtr);
            if (exponent == 3L) {
                exponent = 1L;
            }
            putI32(resultPtr, exponent);
            if (((bcdAcc >>> 60) & 0xffffffffL) == 0L) {
                putI32(resultPtr + 4L, getI32(srcPtr + 4L));
                return 0L;
            }
            putI64(resultPtr + 8L, bcdAcc >>> 4);
            exponent = getI32(srcPtr + 4L);
            putI32(resultPtr + 4L, exponent + 1L);
            if (49998L < exponent) {
                putI32(resultPtr, 2L);
            }
            return (bcdAcc & 15L) != 0L ? 1L : 0L;
        }
        if (getI32(srcPtr) == 0L) {
            putI32(resultPtr, 0L);
            putI32(resultPtr + 4L, 0L);
            putI32(resultPtr + 8L, 0L);
            putI32(resultPtr + 12L, 0L);
            return 0L;
        } else {
            tempVal2 = getI64(srcPtr);
            putI64(resultPtr + 8L, getI64(srcPtr + 8L));
            putI64(resultPtr, tempVal2);
            return 0L;
        }
    }

    long fmul2(long srcPtr, long resultPtr) {
        long exponent = 0L;
        long flag = 0L;
        long bcdAcc = 0L;
        long tempVal = 0L;
        if (((getI32(srcPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI32(srcPtr) + 1L) & 31L)) & 21L) != 0L && getI32(srcPtr + 12L) == 0L) {
            tempVal = getI64(srcPtr);
            flag = 0L;
            putI64(resultPtr + 8L, getI64(srcPtr + 8L));
            putI64(resultPtr, tempVal);
        } else {
            bcdAcc = dcbAddAdjust(getI64(srcPtr + 8L), getI64(srcPtr + 8L));
            putI64(resultPtr + 8L, bcdAcc);
            exponent = getI32(srcPtr);
            if (exponent == 3L) {
                exponent = 1L;
            }
            putI32(resultPtr, exponent);
            if (((bcdAcc >>> 60) & 0xffffffffL) == 0L) {
                flag = 0L;
                putI32(resultPtr + 4L, getI32(srcPtr + 4L));
            } else {
                putI64(resultPtr + 8L, bcdAcc >>> 4);
                exponent = getI32(srcPtr + 4L);
                putI32(resultPtr + 4L, exponent + 1L);
                if (49998L < exponent) {
                    putI32(resultPtr, 2L);
                }
                flag = (bcdAcc & 15L) != 0L ? 1L : 0L;
            }
        }
        return flag;
    }

    long fNorm_L(long status, long realPtr) {
        long tempVal1 = 0L;
        long result = 0L;
        long bcdAcc = 0L;
        long constPtr = 0L;
        long tempVal2 = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if (((getI32(realPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI32(realPtr) + 1L) & 31L)) & 21L) != 0L) {
                        if (((((getI32(realPtr + 12L) & 0xffffffffL)) >> 28) & 0xffffffffL) == 0L) {
                            bcdAcc = getI32(realPtr + 8L) & 0xffffffffL;
                        } else {
                            bcdAcc = dcbShiftRound(getI64(realPtr + 8L), 1L);
                            putI64(realPtr + 8L, bcdAcc);
                            putI32(realPtr + 4L, getI32(realPtr + 4L) + 1L);
                        }
                        tempVal1 = bcdAcc & 4095L;
                        if (1280L < tempVal1 || tempVal1 == 1280L && ((long) (int) status == 1L || ((((bcdAcc & 0xffffffffL)) >> 12) & 1L) != 0L)) {
                            bcdAcc = dcbAddAdjust(getI64(realPtr + 8L), 4096L);
                            putI64(realPtr + 8L, bcdAcc);
                            if (((bcdAcc >>> 60) & 0xffffffffL) != 0L) {
                                bcdAcc = 0L;
                                putI32(realPtr + 8L, 0L);
                                putI32(realPtr + 12L, 0x1000000L);
                                putI32(realPtr + 4L, getI32(realPtr + 4L) + 1L);
                            }
                        }
                        putI32(realPtr + 8L, bcdAcc & 0xfffff000L);
                        if (getI32(realPtr + 4L) < 500L) {
                            if (-500L < getI32(realPtr + 4L)) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                constPtr = CC_0;
                                step = 1;
                                continue stepLoop;
                            }
                        } else {
                            if (getI32(realPtr) < 1L) {
                                constPtr = CC_NInf;
                            } else {
                                constPtr = CC_PInf;
                            }
                            step = 1;
                            continue stepLoop;
                        }
                    } else {
                        step = 2;
                        continue stepLoop;
                    }
                }
                case 1: {
                    tempVal2 = getI64(constPtr);
                    putI64(realPtr + 8L, getI64(constPtr + 8L));
                    putI64(realPtr, tempVal2);
                    step = 2;
                    continue stepLoop;
                }
                case 2: {
                    result = 0L;
                    if ((long) (int) status != 1L) {
                        result = (long) (int) status;
                    }
                    return result;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long fNormUp_L(long realPtr) {
        long tempVal = 0L;
        long bcdAcc1 = 0L;
        long bcdAcc2 = 0L;
        long result = 0L;
        if (((getU32(realPtr + 12L) >> 28) & 0xffffffffL) == 0L) {
            bcdAcc1 = getU32(realPtr + 8L);
        } else {
            bcdAcc1 = dcbShiftRound(getI64(realPtr + 8L), 1L);
            putI64(realPtr + 8L, bcdAcc1);
            putI32(realPtr + 4L, getI32(realPtr + 4L) + 1L);
        }
        tempVal = bcdAcc1 & 0xffffffffL;
        if ((tempVal & 3840L) < 1280L) {
            result = 0L;
        } else {
            bcdAcc2 = dcbAddAdjust(getI64(realPtr + 8L), 4096L);
            putI64(realPtr + 8L, bcdAcc2);
            if (((bcdAcc2 >>> 60) & 0xffffffffL) != 0L) {
                bcdAcc2 = 0L;
                putI64(realPtr + 8L, 0x100000000000000L);
                putI32(realPtr + 4L, getI32(realPtr + 4L) + 1L);
            }
            tempVal = bcdAcc2 & 0xffffffffL;
            result = 1L;
        }
        putU32(realPtr + 8L, tempVal & 0xfffff000L);
        if (getI32(realPtr + 4L) < 500L) {
            if (getI32(realPtr + 4L) < -499L) {
                putI64(realPtr + 8L, 0L);
                putI64(realPtr, 1L);
            }
        } else {
            putI64(realPtr + 8L, 0x999999999999000L);
            putU32(realPtr + 4L, 499L);
        }
        return result;
    }

    long fiIP(long srcPtr, long resultPtr) {
        long intVal = 0L;
        long exponent = 0L;
        long tempVal = 0L;
        tempVal = getI64(srcPtr);
        putI64(resultPtr + 8L, getI64(srcPtr + 8L));
        putI64(resultPtr, tempVal);
        intVal = getI32(resultPtr);
        if (intVal != -1L && intVal != 1L) {
            if (intVal != 3L) {
                return 0L;
            }
            putI32(resultPtr, 1L);
        }
        exponent = getI32(resultPtr + 4L) & 0xffffffffL;
        if ((long) (int) exponent >= 14L) {
            return 0L;
        }
        if ((long) (int) exponent < 0L) {
            putI32(resultPtr + 8L, 0L);
            putI32(resultPtr + 12L, 0L);
            putI32(resultPtr, 1L);
            putI32(resultPtr + 4L, 0L);
            return 0L;
        } else {
            putI64(resultPtr + 8L, getI64(resultPtr + 8L) & (-1L << (int) ((exponent * -4L + 56L) & 63L)));
            return 0L;
        }
    }

    long fiFP(long srcPtr, long resultPtr) {
        long intVal = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long mantissa1 = 0L;
        long mantissa2 = 0L;
        long exponent = 0L;
        long tempVal3 = 0L;
        tempVal3 = getI64(srcPtr);
        putI64(resultPtr + 8L, getI64(srcPtr + 8L));
        putI64(resultPtr, tempVal3);
        if (getI32(resultPtr) == 3L) {
            putI32(resultPtr, 1L);
        }
        intVal = getI32(srcPtr);
        if (intVal == 2L) {
            putI32(resultPtr + 8L, 0L);
            putI32(resultPtr + 12L, 0L);
            putI32(resultPtr, 1L);
            putI32(resultPtr + 4L, 0L);
            return 0L;
        }
        if (intVal == 0L) {
            return 0L;
        }
        if (intVal == -2L) {
            putI32(resultPtr + 8L, 0L);
            putI32(resultPtr + 12L, 0L);
            putI32(resultPtr, 1L);
            putI32(resultPtr + 4L, 0L);
            return 0L;
        }
        exponent = getI32(srcPtr + 4L) & 0xffffffffL;
        if ((long) (int) exponent >= 14L) {
            putI32(resultPtr + 8L, 0L);
            putI32(resultPtr + 12L, 0L);
            putI32(resultPtr, 1L);
            putI32(resultPtr + 4L, 0L);
            return 0L;
        }
        if ((long) (int) exponent < 0L) {
            return 0L;
        }
        mantissa1 = getI64(resultPtr + 8L) << (int) ((exponent * 4L + 4L) & 63L);
        exponent = (mantissa1 >>> 32) & 0xfffffffL;
        putI64(resultPtr + 8L, mantissa1);
        putI32(resultPtr + 12L, exponent);
        mantissa2 = getI64(resultPtr + 8L);
        putI32(resultPtr + 4L, -1L);
        if (mantissa2 == 0L) {
            putI32(resultPtr, 1L);
            putI32(resultPtr + 4L, 0L);
            return 0L;
        }
        tempVal1 = 0L;
        if (((exponent >> 24) & 0xffffffffL) != 0L) {
            return 0L;
        }
        do {
            exponent = tempVal1;
            tempVal2 = mantissa2 >>> 52;
            mantissa2 = mantissa2 << 4;
            tempVal1 = (exponent - 1L) & 0xffffffffL;
        } while ((tempVal2 & 0xffL) == 0L);
        putI64(resultPtr + 8L, mantissa2);
        putI32(resultPtr + 4L, exponent - 2L);
        if (0xffff3cb1L < tempVal1) {
            return 0L;
        } else {
            putI32(resultPtr + 8L, 0L);
            putI32(resultPtr + 12L, 0L);
            putI32(resultPtr, 1L);
            putI32(resultPtr + 4L, 0L);
            return 0L;
        }
    }

    long ficeil(long srcPtr, long resultPtr) {
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        putI64(framePtr + 24L, getI64(srcPtr + 8L));
                        putI64(framePtr + 16L, getI64(srcPtr));
                        if ((getI64(framePtr + 16L) & 0xffffffffL) != 0xffffffffL && (getI64(framePtr + 16L) & 0xffffffffL) != 1L) {
                            if ((getI64(framePtr + 16L) & 0xffffffffL) != 3L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                putI32(framePtr + 20L, getI64(framePtr + 16L) >>> 32);
                                putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | 1L);
                                step = 1;
                                continue stepLoop;
                            }
                        } else {
                            step = 1;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        if (getI32(framePtr + 20L) < 14L) {
                            if (Long.compareUnsigned(getI64(framePtr + 16L), 0L) < 0L) {
                                putI64(framePtr + 24L, 0L);
                                putI64(framePtr + 16L, 1L);
                            } else {
                                putI64(framePtr + 24L, getI64(framePtr + 24L) & (-1L << (int) ((getI32(framePtr + 20L) * -4L + 56L) & 63L)));
                            }
                        }
                        step = 2;
                        continue stepLoop;
                    }
                    case 2: {
                        if (getI64(framePtr + 24L) == getI64(srcPtr + 8L) || (long) (int) getI64(srcPtr) < 1L) {
                            putI64(resultPtr + 8L, getI64(framePtr + 24L));
                            putI64(resultPtr, getI64(framePtr + 16L));
                        } else {
                            fradd(framePtr + 16L, CC_1, resultPtr, 0L);
                        }
                        return 0L;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long fifloor(long srcPtr, long resultPtr) {
        long exponent = 0L;
        long tempVal = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    tempVal = getI64(srcPtr);
                    putI64(resultPtr + 8L, getI64(srcPtr + 8L));
                    putI64(resultPtr, tempVal);
                    exponent = getU32(resultPtr);
                    if (exponent != 0xffffffffL && exponent != 1L) {
                        if (exponent != 3L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            putU32(resultPtr, 1L);
                            step = 1;
                            continue stepLoop;
                        }
                    } else {
                        step = 1;
                        continue stepLoop;
                    }
                }
                case 1: {
                    exponent = getU32(resultPtr + 4L);
                    if ((long) (int) exponent < 14L) {
                        if ((long) (int) exponent < 0L) {
                            putU32(resultPtr + 8L, 0L);
                            putU32(resultPtr + 12L, 0L);
                            putU32(resultPtr, 1L);
                            putU32(resultPtr + 4L, 0L);
                        } else {
                            putI64(resultPtr + 8L, getI64(resultPtr + 8L) & (-1L << (int) ((exponent * -4L + 56L) & 63L)));
                        }
                    }
                    step = 2;
                    continue stepLoop;
                }
                case 2: {
                    if (getI64(resultPtr + 8L) != getI64(srcPtr + 8L) && getI32(srcPtr) < 0L) {
                        fradd(resultPtr, CC_1, resultPtr, 0L);
                    }
                    return 0L;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long fichs2(long srcPtr, long resultPtr) {
        long intVal = 0L;
        long tempVal = 0L;
        tempVal = getI64(srcPtr);
        putI64(resultPtr + 8L, getI64(srcPtr + 8L));
        putI64(resultPtr, tempVal);
        intVal = getI32(resultPtr);
        if (intVal != -1L && intVal != 1L) {
            if (intVal != 3L) {
                putI32(resultPtr, -intVal);
                return 0L;
            }
            intVal = 1L;
            putI32(resultPtr, 1L);
        }
        if (getI32(resultPtr + 12L) == 0L) {
            return 0L;
        } else {
            putI32(resultPtr, -intVal);
            return 0L;
        }
    }

    long fiabs2(long srcPtr, long resultPtr) {
        long intVal = 0L;
        long tempVal = 0L;
        tempVal = getI64(srcPtr);
        putI64(resultPtr + 8L, getI64(srcPtr + 8L));
        putI64(resultPtr, tempVal);
        intVal = getI32(resultPtr);
        if (intVal == 3L) {
            intVal = 1L;
        } else {
            if (-1L < intVal) {
                return 0L;
            }
            intVal = (long) (int) -intVal;
        }
        putI32(resultPtr, intVal);
        return 0L;
    }

    void fMakeZeroIfClose(long realPtr, long thresholdExp) {
        long exponent = 0L;
        if (getI32(thresholdExp + 12L) != 0L && comma(exponent = (long) (int) (getI32(thresholdExp + 4L) - 6L), getI32(realPtr + 4L) < exponent || getI32(realPtr + 4L) == exponent && Long.compareUnsigned(getI64(realPtr + 8L), getI64(thresholdExp + 8L)) < 0L ? 1L : 0L) != 0L) {
            putI64(realPtr + 8L, 0L);
            putI64(realPtr, 1L);
        }
    }

    long fimod(long dividendPtr, long divisorPtr, long resultPtr) {
        long result = 0L;
        if (((getI32(dividendPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI32(dividendPtr) + 1L) & 31L)) & 21L) != 0L && ((getI32(divisorPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI32(divisorPtr) + 1L) & 31L)) & 21L) != 0L) {
            result = _fmod(dividendPtr, divisorPtr, resultPtr);
            return result;
        } else {
            putI64(resultPtr, 0L);
            putI64(resultPtr + 8L, 0L);
            return 0L;
        }
    }

    long _fmod(long dividendPtr, long divisorPtr, long resultPtr) {
        long status = 0L;
        long bcdAcc = 0L;
        long mantissa1 = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long exponent1 = 0L;
        long exponent2 = 0L;
        long tempVal3 = 0L;
        long mantissa2 = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if (!(getU32(divisorPtr + 12L) != 0L && getU32(dividendPtr + 12L) != 0L)) {
                        mantissa2 = getI64(dividendPtr + 8L);
                        tempVal3 = getI64(dividendPtr);
                        putI64(resultPtr + 8L, mantissa2);
                        putI64(resultPtr, tempVal3);
                        return 0L;
                    }
                    exponent2 = (long) (int) (getU32(dividendPtr + 4L) - getU32(divisorPtr + 4L));
                    if (!((long) (int) getU32(divisorPtr + 4L) <= (long) (int) getU32(dividendPtr + 4L) && comma(bcdAcc = getI64(dividendPtr + 8L), exponent2 != 0L || Long.compareUnsigned(getI64(divisorPtr + 8L), bcdAcc) <= 0L ? 1L : 0L) != 0L)) {
                        status = getU32(dividendPtr);
                        if (status == 0L) {
                            putU32(resultPtr, 0L);
                            putU32(resultPtr + 4L, 0L);
                            putU32(resultPtr + 8L, 0L);
                            putU32(resultPtr + 12L, 0L);
                            return 0L;
                        }
                        exponent1 = getU32(divisorPtr);
                        if ((long) (int) (exponent1 ^ status) >= 0L) {
                            mantissa2 = getI64(dividendPtr + 8L);
                            tempVal3 = getI64(dividendPtr);
                            putI64(resultPtr + 8L, mantissa2);
                            putI64(resultPtr, tempVal3);
                            return 0L;
                        }
                        if (((status + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((status + 1L) & 31L)) & 21L) != 0L) {
                            switch ((int) (exponent1)) {
                                case 0: {
                                    putU32(resultPtr, 0L);
                                    putU32(resultPtr + 4L, 0L);
                                    putU32(resultPtr + 8L, 0L);
                                    putU32(resultPtr + 12L, 0L);
                                    return 0L;
                                }
                                case -1: case 1: case 3: {
                                    status = fadd(dividendPtr, divisorPtr, resultPtr, 0L) & 0xffffffffL;
                                    return status;
                                }
                                default: {
                                    break;
                                }
                            }
                        } else {
                            if (exponent1 == 0L) {
                                putU32(resultPtr, 0L);
                                putU32(resultPtr + 4L, 0L);
                                putU32(resultPtr + 8L, 0L);
                                putU32(resultPtr + 12L, 0L);
                                return 0L;
                            }
                        }
                        if (status == 2L || status == 0xfffffffeL) {
                            if (5L < ((exponent1 + 2L) & 0xffffffffL)) {
                                putU32(resultPtr, 0L);
                                putU32(resultPtr + 4L, 0L);
                                putU32(resultPtr + 8L, 0L);
                                putU32(resultPtr + 12L, 0L);
                                return 0L;
                            }
                            exponent1 = (1L << (int) ((exponent1 + 2L) & 31L)) & 0xffffffffL;
                            if ((exponent1 & 42L) != 0L) {
                                mantissa2 = getI64(dividendPtr + 8L);
                                tempVal3 = getI64(dividendPtr);
                                putI64(resultPtr + 8L, mantissa2);
                                putI64(resultPtr, tempVal3);
                                return 0L;
                            }
                            if ((exponent1 & 17L) == 0L) {
                                putU32(resultPtr, 0L);
                                putU32(resultPtr + 4L, 0L);
                                putU32(resultPtr + 8L, 0L);
                                putU32(resultPtr + 12L, 0L);
                                return 0L;
                            }
                        } else {
                            if (exponent1 != 2L && exponent1 != 0xfffffffeL) {
                                putU32(resultPtr, 0L);
                                putU32(resultPtr + 4L, 0L);
                                putU32(resultPtr + 8L, 0L);
                                putU32(resultPtr + 12L, 0L);
                                return 0L;
                            }
                        }
                        if (4L < ((status + 1L) & 0xffffffffL) || ((1L << (int) ((status + 1L) & 31L)) & 21L) == 0L) {
                            putU32(resultPtr, 0L);
                            putU32(resultPtr + 4L, 0L);
                            putU32(resultPtr + 8L, 0L);
                            putU32(resultPtr + 12L, 0L);
                            return 0L;
                        }
                        mantissa2 = getI64(divisorPtr + 8L);
                        tempVal3 = getI64(divisorPtr);
                        putI64(resultPtr + 8L, mantissa2);
                        putI64(resultPtr, tempVal3);
                        return 0L;
                    }
                    putI64(resultPtr + 8L, bcdAcc);
                    if (Long.compareUnsigned(bcdAcc, getI64(divisorPtr + 8L)) < 0L) {
                        bcdAcc = bcdAcc << 4;
                        putI64(resultPtr + 8L, bcdAcc);
                        exponent2 = (long) (int) (exponent2 - 1L);
                    }
                    while (true) {
                        mantissa1 = getI64(divisorPtr + 8L);
                        if (Long.compareUnsigned(mantissa1, bcdAcc) <= 0L) {
                            do {
                                bcdAcc = dcbSubAdjust(mantissa1, bcdAcc);
                                putI64(resultPtr + 8L, bcdAcc);
                                mantissa1 = getI64(divisorPtr + 8L);
                            } while (Long.compareUnsigned(mantissa1, bcdAcc) <= 0L);
                        }
                        if (exponent2 == 0L) {
                            break;
                        }
                        exponent2 = (long) (int) (exponent2 - 1L);
                        bcdAcc = bcdAcc << 4;
                        putI64(resultPtr + 8L, bcdAcc);
                    }
                    tempVal2 = bcdAcc >>> 32;
                    if (getU32(dividendPtr) == 0L) {
                        if (bcdAcc != 0L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            tempVal1 = 0L;
                            step = 1;
                            continue stepLoop;
                        }
                    } else {
                        tempVal1 = bcdAcc;
                        if (bcdAcc != 0L && (long) (int) (getU32(divisorPtr) ^ getU32(dividendPtr)) < 0L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            step = 1;
                            continue stepLoop;
                        }
                    }
                }
                case 1: {
                    status = getU32(divisorPtr);
                    if (status == 3L) {
                        status = 1L;
                    }
                    putU32(resultPtr, status);
                    exponent1 = getU32(divisorPtr + 4L);
                    putU32(resultPtr + 4L, exponent1);
                    if (tempVal2 >>> 28 != 0L) {
                        bcdAcc = (bcdAcc & 15L) != 0L ? 1L : 0L;
                        putI64(resultPtr + 8L, tempVal1 >>> 4);
                        putU32(resultPtr + 4L, exponent1 + 1L);
                        if ((long) (int) exponent1 < 49999L) {
                            return bcdAcc;
                        }
                        if (0L < (long) (int) status) {
                            putU32(resultPtr, 2L);
                            return bcdAcc;
                        } else {
                            putU32(resultPtr, 0xfffffffeL);
                            return bcdAcc;
                        }
                    }
                    if (tempVal1 == 0L) {
                        putU32(resultPtr, 1L);
                        putU32(resultPtr + 4L, 0L);
                        return 0L;
                    }
                    if ((tempVal2 & 0xff000000L) == 0L) {
                        do {
                            bcdAcc = tempVal1 >>> 52;
                            tempVal1 = tempVal1 << 4;
                            exponent1 = (exponent1 - 1L) & 0xffffffffL;
                        } while ((bcdAcc & 0xffL) == 0L);
                        putI64(resultPtr + 8L, tempVal1);
                        putU32(resultPtr + 4L, exponent1);
                    }
                    if (-50000L < (long) (int) exponent1) {
                        return 0L;
                    }
                    mantissa2 = 0L;
                    tempVal3 = 1L;
                    putI64(resultPtr + 8L, mantissa2);
                    putI64(resultPtr, tempVal3);
                    return 0L;
                }
                case 2: {
                    bcdAcc = dcbSubAdjust(bcdAcc, mantissa1);
                    tempVal2 = bcdAcc >>> 32;
                    putI64(resultPtr + 8L, bcdAcc);
                    tempVal1 = bcdAcc;
                    step = 1;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long fMLT(long lhsPtr, long rhsPtr) {
        if (getI32(rhsPtr + 12L) == 0L) {
            return 0L;
        }
        if (getI32(lhsPtr + 12L) == 0L || getI32(lhsPtr + 4L) < getI32(rhsPtr + 4L)) {
            return 1L;
        }
        if (getI32(lhsPtr + 4L) == getI32(rhsPtr + 4L)) {
            return Long.compareUnsigned(getI64(lhsPtr + 8L), getI64(rhsPtr + 8L)) < 0L ? 1L : 0L;
        } else {
            return 0L;
        }
    }

    long fdivmod(long dividendPtr, long divisorPtr, long quotientPtr, long remainderPtr) {
        long exponent1 = 0L;
        long tempVal1 = 0L;
        long status = 0L;
        long bcdAcc = 0L;
        long constPtr = 0L;
        long mantissa = 0L;
        long exponent2 = 0L;
        long tempVal2 = 0L;
        long exponent3 = 0L;
        long exponent4 = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    if (getI32(dividendPtr + 12L) != 0L) {
                        if (getI32(divisorPtr + 12L) == 0L) {
                            bcdAcc = getI64(dividendPtr + 8L);
                            exponent4 = (((getI32(dividendPtr + 4L) & 0xffffffffL)) << 32) | (getI32(dividendPtr) & 0xffffffffL);
                            constPtr = CC_PInf;
                            status = 4L;
                            step = 2;
                            continue stepLoop;
                        } else {
                            bcdAcc = getI64(dividendPtr + 8L);
                            exponent2 = getI32(dividendPtr + 4L);
                            exponent1 = getI32(divisorPtr + 4L);
                            putI64(remainderPtr + 8L, bcdAcc);
                            exponent2 = (long) (int) (exponent2 - exponent1);
                            if (Long.compareUnsigned(bcdAcc, getI64(divisorPtr + 8L)) < 0L) {
                                exponent2 = (long) (int) (exponent2 - 1L);
                                putI64(remainderPtr + 8L, bcdAcc << 4);
                            }
                            if (-1L < exponent2 && (getI32(divisorPtr + 12L) == 0L || getI32(dividendPtr + 12L) != 0L && getI32(divisorPtr + 4L) <= getI32(dividendPtr + 4L) && (getI32(dividendPtr + 4L) != getI32(divisorPtr + 4L) || Long.compareUnsigned(getI64(divisorPtr + 8L), getI64(dividendPtr + 8L)) <= 0L))) {
                                putI32(quotientPtr + 4L, exponent2);
                                status = getI32(dividendPtr) & 0xffffffffL;
                                tempVal1 = getI32(divisorPtr) & 0xffffffffL;
                                putI64(quotientPtr + 8L, 0L);
                                tempVal2 = 0x100000000000000L;
                                putI32(quotientPtr, ((long) (int) (tempVal1 ^ status) >> 30) | 1L);
                                bcdAcc = getI64(remainderPtr + 8L);
                                while (true) {
                                    while (comma(mantissa = getI64(divisorPtr + 8L), Long.compareUnsigned(mantissa, bcdAcc) <= 0L ? 1L : 0L) != 0L) {
                                        bcdAcc = dcbSubAdjust(mantissa, bcdAcc);
                                        putI64(remainderPtr + 8L, bcdAcc);
                                        putI64(quotientPtr + 8L, getI64(quotientPtr + 8L) + tempVal2);
                                        bcdAcc = getI64(remainderPtr + 8L);
                                    }
                                    if (exponent2 == 0L) {
                                        break;
                                    }
                                    bcdAcc = bcdAcc << 4;
                                    exponent2 = (long) (int) (exponent2 - 1L);
                                    putI64(remainderPtr + 8L, bcdAcc);
                                    tempVal2 = tempVal2 >>> 4;
                                }
                                if (getI32(dividendPtr) == 0L) {
                                    if (bcdAcc != 0L) {
                                        step = 4;
                                        continue stepLoop;
                                    } else {
                                        step = 3;
                                        continue stepLoop;
                                    }
                                } else {
                                    if (bcdAcc != 0L && (long) (int) (getI32(divisorPtr) ^ getI32(dividendPtr)) < 0L) {
                                        step = 4;
                                        continue stepLoop;
                                    } else {
                                        step = 3;
                                        continue stepLoop;
                                    }
                                }
                            } else {
                                status = getI32(dividendPtr) & 0xffffffffL;
                                if (status == 0L) {
                                    putI32(remainderPtr, 0L);
                                    putI32(remainderPtr + 4L, 0L);
                                    putI64(remainderPtr + 8L, 0L);
                                    return 0L;
                                }
                                tempVal1 = getI32(divisorPtr) & 0xffffffffL;
                                if ((long) (int) (tempVal1 ^ status) < 0L) {
                                    if (((status + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((status + 1L) & 31L)) & 21L) != 0L) {
                                        switch ((int) (tempVal1)) {
                                            case 0: {
                                                putI32(remainderPtr, 0L);
                                                putI32(remainderPtr + 4L, 0L);
                                                putI64(remainderPtr + 8L, 0L);
                                                return 0L;
                                            }
                                            case -1: case 1: case 3: {
                                                status = fadd(dividendPtr, divisorPtr, remainderPtr, 0L) & 0xffffffffL;
                                                return status;
                                            }
                                            default: {
                                                break;
                                            }
                                        }
                                    } else {
                                        if (tempVal1 == 0L) {
                                            putI32(remainderPtr, 0L);
                                            putI32(remainderPtr + 4L, 0L);
                                            putI64(remainderPtr + 8L, 0L);
                                            return 0L;
                                        }
                                    }
                                    if (status == 2L || status == 0xfffffffeL) {
                                        if (5L < ((tempVal1 + 2L) & 0xffffffffL)) {
                                            putI32(remainderPtr, 0L);
                                            putI32(remainderPtr + 4L, 0L);
                                            putI64(remainderPtr + 8L, 0L);
                                            return 0L;
                                        }
                                        tempVal1 = (1L << (int) ((tempVal1 + 2L) & 31L)) & 0xffffffffL;
                                        if ((tempVal1 & 42L) != 0L) {
                                            bcdAcc = getI64(dividendPtr + 8L);
                                            exponent3 = (((getI32(dividendPtr + 4L) & 0xffffffffL)) << 32) | (getI32(dividendPtr) & 0xffffffffL);
                                            putI64(remainderPtr + 8L, bcdAcc);
                                            putI32(remainderPtr, exponent3);
                                            putI32(remainderPtr + 4L, exponent3 >>> 32);
                                            return 0L;
                                        }
                                        if ((tempVal1 & 17L) == 0L) {
                                            putI32(remainderPtr, 0L);
                                            putI32(remainderPtr + 4L, 0L);
                                            putI64(remainderPtr + 8L, 0L);
                                            return 0L;
                                        }
                                    } else {
                                        if (tempVal1 != 2L && tempVal1 != 0xfffffffeL) {
                                            putI32(remainderPtr, 0L);
                                            putI32(remainderPtr + 4L, 0L);
                                            putI64(remainderPtr + 8L, 0L);
                                            return 0L;
                                        }
                                    }
                                    if (4L < ((status + 1L) & 0xffffffffL) || ((1L << (int) ((status + 1L) & 31L)) & 21L) == 0L) {
                                        putI32(remainderPtr, 0L);
                                        putI32(remainderPtr + 4L, 0L);
                                        putI64(remainderPtr + 8L, 0L);
                                        return 0L;
                                    }
                                    bcdAcc = getI64(divisorPtr + 8L);
                                    exponent3 = (((getI32(divisorPtr + 4L) & 0xffffffffL)) << 32) | (getI32(divisorPtr) & 0xffffffffL);
                                    putI64(remainderPtr + 8L, bcdAcc);
                                    putI32(remainderPtr, exponent3);
                                    putI32(remainderPtr + 4L, exponent3 >>> 32);
                                    return 0L;
                                }
                                step = 1;
                                continue stepLoop;
                            }
                        }
                    } else {
                        step = 1;
                        continue stepLoop;
                    }
                }
                case 1: {
                    bcdAcc = getI64(dividendPtr + 8L);
                    exponent4 = (((getI32(dividendPtr + 4L) & 0xffffffffL)) << 32) | (getI32(dividendPtr) & 0xffffffffL);
                    constPtr = CC_0;
                    status = 0L;
                    step = 2;
                    continue stepLoop;
                }
                case 2: {
                    putI64(remainderPtr + 8L, bcdAcc);
                    putI32(remainderPtr, exponent4);
                    putI32(remainderPtr + 4L, exponent4 >>> 32);
                    exponent4 = getI64(constPtr);
                    putI64(quotientPtr + 8L, getI64(constPtr + 8L));
                    putI32(quotientPtr, exponent4);
                    putI32(quotientPtr + 4L, exponent4 >>> 32);
                    return status;
                }
                case 3: {
                    exponent2 = getI32(divisorPtr);
                    if (exponent2 == 3L) {
                        exponent2 = 1L;
                    }
                    putI32(remainderPtr, exponent2);
                    putI32(remainderPtr + 4L, getI32(divisorPtr + 4L));
                    status = getU32(quotientPtr + 12L);
                    bcdAcc = getI64(quotientPtr + 8L);
                    if (((status >> 28) & 0xffffffffL) == 0L) {
                        if (bcdAcc == 0L) {
                            putI32(quotientPtr, 1L);
                            putI32(quotientPtr + 4L, 0L);
                        } else {
                            exponent2 = getI32(quotientPtr + 4L);
                            if (((status >> 24) & 0xffffffffL) == 0L) {
                                do {
                                    tempVal2 = bcdAcc >>> 52;
                                    bcdAcc = bcdAcc << 4;
                                    exponent2 = (long) (int) (exponent2 - 1L);
                                } while ((tempVal2 & 0xffL) == 0L);
                                putI64(quotientPtr + 8L, bcdAcc);
                                putI32(quotientPtr + 4L, exponent2);
                            }
                            if (exponent2 < -49999L) {
                                putI64(quotientPtr + 8L, 0L);
                                putI32(quotientPtr, 1L);
                                putI32(quotientPtr + 4L, 0L);
                            }
                        }
                    } else {
                        exponent2 = getI32(quotientPtr + 4L);
                        putI64(quotientPtr + 8L, bcdAcc >>> 4);
                        putI32(quotientPtr + 4L, exponent2 + 1L);
                        if (49998L < exponent2) {
                            if (getI32(quotientPtr) < 1L) {
                                exponent2 = -2L;
                            } else {
                                exponent2 = 2L;
                            }
                            putI32(quotientPtr, exponent2);
                        }
                    }
                    status = getU32(remainderPtr + 12L);
                    if (((status >> 28) & 0xffffffffL) != 0L) {
                        exponent2 = getI32(remainderPtr + 4L);
                        status = (getI64(remainderPtr + 8L) & 15L) != 0L ? 1L : 0L;
                        putI64(remainderPtr + 8L, getI64(remainderPtr + 8L) >>> 4);
                        putI32(remainderPtr + 4L, exponent2 + 1L);
                        if (exponent2 < 49999L) {
                            return status;
                        }
                        if (0L < getI32(remainderPtr)) {
                            putI32(remainderPtr, 2L);
                            return status;
                        } else {
                            putI32(remainderPtr, -2L);
                            return status;
                        }
                    }
                    bcdAcc = getI64(remainderPtr + 8L);
                    if (bcdAcc == 0L) {
                        putI32(remainderPtr, 1L);
                        putI32(remainderPtr + 4L, 0L);
                        return 0L;
                    }
                    exponent2 = getI32(remainderPtr + 4L);
                    if (((status >> 24) & 0xffffffffL) == 0L) {
                        do {
                            tempVal2 = bcdAcc >>> 52;
                            bcdAcc = bcdAcc << 4;
                            exponent2 = (long) (int) (exponent2 - 1L);
                        } while ((tempVal2 & 0xffL) == 0L);
                        putI64(remainderPtr + 8L, bcdAcc);
                        putI32(remainderPtr + 4L, exponent2);
                    }
                    if (-50000L < exponent2) {
                        return 0L;
                    }
                    bcdAcc = 0L;
                    exponent3 = 1L;
                    putI64(remainderPtr + 8L, bcdAcc);
                    putI32(remainderPtr, exponent3);
                    putI32(remainderPtr + 4L, exponent3 >>> 32);
                    return 0L;
                }
                case 4: {
                    bcdAcc = dcbSubAdjust(bcdAcc, mantissa);
                    putI64(remainderPtr + 8L, bcdAcc);
                    step = 3;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long fCCToInt(long realPtr) {
        long exponent = 0L;
        long mantissa = 0L;
        long index = 0L;
        long tempVal = 0L;
        exponent = getI32(realPtr + 4L);
        if (exponent < 0L) {
            return 0L;
        }
        if (exponent < 10L && (exponent != 9L || Long.compareUnsigned(getI64(realPtr + 8L), 0x214748364700001L) < 0L)) {
            mantissa = 0L;
            index = 15L;
            tempVal = 56L;
            do {
                index = (long) (int) (index - 1L);
                mantissa = (long) (int) (((getI64(realPtr + 8L) >>> (int) (tempVal & 63L)) & 15L) + mantissa * 10L);
                tempVal = (long) (int) (tempVal - 4L);
            } while ((long) (int) (14L - exponent) < index);
            exponent = (long) (int) -mantissa;
            if (-1L < getI32(realPtr)) {
                exponent = mantissa;
            }
            return exponent;
        }
        exponent = 0x7fffffffL;
        if (getI32(realPtr) < 1L) {
            exponent = 0xffffffff80000000L;
        }
        return exponent;
    }

    long fCCTou64(long realPtr) {
        long exponent = 0L;
        long mantissa = 0L;
        long index = 0L;
        long tempVal = 0L;
        exponent = getI32(realPtr + 4L);
        if (exponent < 0L) {
            return 0L;
        }
        if (19L < exponent) {
            return -1L;
        }
        if (exponent == 19L && Long.compareUnsigned(0x18446744073708L, getI64(realPtr + 8L)) < 0L) {
            return -1L;
        }
        mantissa = 0L;
        index = 15L;
        tempVal = 56L;
        do {
            index = (long) (int) (index - 1L);
            mantissa = mantissa * 10L;
            if (-1L < index) {
                mantissa = ((getI64(realPtr + 8L) >>> (int) (tempVal & 63L)) & 15L) + mantissa;
            }
            tempVal = (long) (int) (tempVal - 4L);
        } while ((long) (int) (14L - exponent) < index);
        return mantissa;
    }

    long fIntToCC(long intVal, long resultPtr) {
        long tempVal1 = 0L;
        long index = 0L;
        long tempVal2 = 0L;
        if ((long) (int) intVal == 0L) {
            putI64(resultPtr + 8L, 0L);
            putI64(resultPtr, 1L);
            return resultPtr;
        }
        if ((long) (int) intVal < 0L) {
            putU32(resultPtr, 0xffffffffL);
            intVal = (long) (int) -intVal;
        } else {
            putU32(resultPtr, 1L);
        }
        tempVal2 = 0L;
        index = -1L;
        putI64(resultPtr + 8L, 0L);
        putU32(resultPtr + 4L, 0xffffffffL);
        do {
            tempVal1 = (intVal + 9L) & 0xffffffffL;
            tempVal2 = (tempVal2 >>> 4) + (((((long) (int) intVal % 10L) & 0xffffffffL)) << 56);
            index = (long) (int) (index + 1L);
            intVal = (long) (int) ((long) (int) intVal / 10L);
        } while (18L < tempVal1);
        putI64(resultPtr + 8L, tempVal2);
        putI32(resultPtr + 4L, index);
        return resultPtr;
    }

    long fu64ToCC(long uint64Val, long resultPtr) {
        long flag = 0L;
        long intVal = 0L;
        long tempVal1 = 0L;
        long index1 = 0L;
        long tempVal2 = 0L;
        long index2 = 0L;
        if (uint64Val == 0L) {
            putI64(resultPtr + 8L, 0L);
            putI32(resultPtr, 1L);
            putI32(resultPtr + 4L, 0L);
            return resultPtr;
        }
        putI64(resultPtr + 8L, 0L);
        putI32(resultPtr, 1L);
        putI32(resultPtr + 4L, -1L);
        if (Long.compareUnsigned(uint64Val, 0x38d7ea4c67fffL) < 0L) {
            index1 = -1L;
        } else {
            tempVal2 = 1L;
            index2 = 0x38d7ea4c67fffL;
            intVal = 0L;
            do {
                index1 = intVal;
                index2 = index2 * 10L;
                tempVal2 = (tempVal2 * 10L) & 0xffffffffL;
                intVal = (long) (int) (index1 + 1L);
            } while (Long.compareUnsigned(index2, uint64Val) < 0L || index2 - uint64Val == 0L);
            index2 = 0L;
            if (tempVal2 != 0L) {
                index2 = Long.divideUnsigned(uint64Val, tempVal2);
            }
            tempVal1 = 0L;
            if (tempVal2 != 0L) {
                tempVal1 = ((index2 & 0xffffffffL) / tempVal2) & 0xffffffffL;
            }
            if (((tempVal2 >> 1) & 0xffffffffL) < ((index2 - tempVal1 * tempVal2) & 0xffffffffL)) {
                index2 = index2 + 1L;
            }
            putI32(resultPtr + 4L, index1);
            if (Long.compareUnsigned(index2, 0x38d7ea4c68000L) < 0L) {
                uint64Val = index2;
                if (index2 == 0L) {
                    return resultPtr;
                }
            } else {
                putI32(resultPtr + 4L, intVal);
                uint64Val = Long.divideUnsigned(index2, 10L);
                index1 = intVal;
            }
        }
        index2 = 0L;
        do {
            index2 = (index2 >>> 4) + ((((uint64Val + Long.divideUnsigned(uint64Val, 10L) * -10L) & 0xffffffffL)) << 56);
            index1 = (long) (int) (index1 + 1L);
            flag = Long.compareUnsigned(9L, uint64Val) < 0L ? 1L : 0L;
            uint64Val = Long.divideUnsigned(uint64Val, 10L);
        } while (flag != 0L);
        putI64(resultPtr + 8L, index2);
        putI32(resultPtr + 4L, index1);
        return resultPtr;
    }

    long fMLE(long lhsPtr, long rhsPtr) {
        long flag = 0L;
        flag = getI32(lhsPtr + 12L) == 0L || getI32(rhsPtr + 12L) != 0L ? 1L : 0L;
        if (!(getI32(rhsPtr + 12L) != 0L && getI32(lhsPtr + 12L) != 0L)) {
            return flag;
        }
        if (getI32(lhsPtr + 4L) < getI32(rhsPtr + 4L)) {
            return 1L;
        }
        if (getI32(lhsPtr + 4L) != getI32(rhsPtr + 4L)) {
            return 0L;
        } else {
            flag = Long.compareUnsigned(getI64(lhsPtr + 8L), getI64(rhsPtr + 8L)) <= 0L ? 1L : 0L;
            return flag;
        }
    }

    long fLT(long lhsPtr, long rhsPtr) {
        long intVal1 = 0L;
        long intVal2 = 0L;
        long mantissa1 = 0L;
        long mantissa2 = 0L;
        intVal1 = getI32(lhsPtr);
        if (intVal1 == 0L) {
            return 0L;
        }
        if (intVal1 == 2L) {
            return 0L;
        }
        intVal2 = getI32(rhsPtr);
        if (intVal2 == -2L) {
            return 0L;
        }
        if (intVal2 == 0L) {
            return 0L;
        }
        if (intVal1 == -2L) {
            return 1L;
        }
        if (intVal2 == 2L) {
            return 1L;
        }
        if (intVal1 == 3L) {
            intVal1 = 1L;
        }
        if (intVal2 == 3L) {
            intVal2 = 1L;
        }
        if (intVal1 < intVal2) {
            return 1L;
        }
        if (intVal1 > intVal2) {
            return 0L;
        }
        if (intVal1 == 1L) {
            if (getI32(rhsPtr + 12L) == 0L) {
                return 0L;
            }
            if (getI32(lhsPtr + 12L) == 0L) {
                return 1L;
            }
            if (getI32(lhsPtr + 4L) < getI32(rhsPtr + 4L)) {
                return 1L;
            }
            if (getI32(lhsPtr + 4L) != getI32(rhsPtr + 4L)) {
                return 0L;
            }
            mantissa1 = getI64(lhsPtr + 8L);
            mantissa2 = getI64(rhsPtr + 8L);
        } else {
            if (getI32(lhsPtr + 12L) == 0L) {
                return 0L;
            }
            if (getI32(rhsPtr + 12L) == 0L) {
                return 1L;
            }
            if (getI32(rhsPtr + 4L) < getI32(lhsPtr + 4L)) {
                return 1L;
            }
            if (getI32(rhsPtr + 4L) != getI32(lhsPtr + 4L)) {
                return 0L;
            }
            mantissa1 = getI64(rhsPtr + 8L);
            mantissa2 = getI64(lhsPtr + 8L);
        }
        return Long.compareUnsigned(mantissa1, mantissa2) < 0L ? 1L : 0L;
    }

    long fLE(long lhsPtr, long rhsPtr) {
        long intVal1 = 0L;
        long intVal2 = 0L;
        long flag = 0L;
        long mantissa1 = 0L;
        long mantissa2 = 0L;
        intVal1 = getI32(lhsPtr);
        if (!(intVal1 != 0L && comma(intVal2 = getI32(rhsPtr), intVal2 != 0L ? 1L : 0L) != 0L)) {
            return 0L;
        }
        if (intVal1 == intVal2) {
            if (intVal1 == -2L) {
                return 1L;
            }
            if (intVal1 == 2L) {
                return 1L;
            }
        }
        if (intVal1 == 3L) {
            intVal1 = 1L;
        }
        if (intVal2 == 3L) {
            intVal2 = 1L;
        }
        if (intVal1 < intVal2) {
            return 1L;
        }
        if (intVal1 > intVal2) {
            return 0L;
        }
        if (intVal1 == 1L) {
            flag = getI32(lhsPtr + 12L) == 0L || getI32(rhsPtr + 12L) != 0L ? 1L : 0L;
            if (!(getI32(rhsPtr + 12L) != 0L && getI32(lhsPtr + 12L) != 0L)) {
                return flag;
            }
            if (getI32(lhsPtr + 4L) < getI32(rhsPtr + 4L)) {
                return 1L;
            }
            if (getI32(lhsPtr + 4L) != getI32(rhsPtr + 4L)) {
                return 0L;
            }
            mantissa1 = getI64(lhsPtr + 8L);
            mantissa2 = getI64(rhsPtr + 8L);
        } else {
            flag = getI32(rhsPtr + 12L) == 0L || getI32(lhsPtr + 12L) != 0L ? 1L : 0L;
            if (!(getI32(lhsPtr + 12L) != 0L && getI32(rhsPtr + 12L) != 0L)) {
                return flag;
            }
            if (getI32(rhsPtr + 4L) < getI32(lhsPtr + 4L)) {
                return 1L;
            }
            if (getI32(rhsPtr + 4L) != getI32(lhsPtr + 4L)) {
                return 0L;
            }
            mantissa1 = getI64(rhsPtr + 8L);
            mantissa2 = getI64(lhsPtr + 8L);
        }
        return Long.compareUnsigned(mantissa1, mantissa2) <= 0L ? 1L : 0L;
    }

    long fEQ(long lhsPtr, long rhsPtr) {
        long exponent = 0L;
        long intVal = 0L;
        exponent = getI32(lhsPtr);
        if (!(exponent != 0L && comma(intVal = getI32(rhsPtr), intVal != 0L ? 1L : 0L) != 0L)) {
            return 0L;
        }
        if (exponent == 3L) {
            exponent = 1L;
        }
        if (intVal == 3L) {
            intVal = 1L;
        }
        if (exponent == intVal && getI32(lhsPtr + 4L) == getI32(rhsPtr + 4L)) {
            return getI64(lhsPtr + 8L) == getI64(rhsPtr + 8L) ? 1L : 0L;
        } else {
            return 0L;
        }
    }

    long fNE(long lhsPtr, long rhsPtr) {
        long exponent = 0L;
        long intVal = 0L;
        exponent = getI32(lhsPtr);
        if (exponent == 0L || comma(intVal = getI32(rhsPtr), intVal == 0L ? 1L : 0L) != 0L) {
            return 0L;
        }
        if (exponent == 3L) {
            exponent = 1L;
        }
        if (intVal == 3L) {
            intVal = 1L;
        }
        if (exponent == intVal && getI32(lhsPtr + 4L) == getI32(rhsPtr + 4L)) {
            return getI64(lhsPtr + 8L) != getI64(rhsPtr + 8L) ? 1L : 0L;
        } else {
            return 1L;
        }
    }

    long fimax(long lhsPtr, long rhsPtr, long resultPtr) {
        long isLess = 0L;
        long intPtr = 0L;
        long tempVal = 0L;
        intPtr = rhsPtr;
        if (getI32(lhsPtr) != 0L && comma(comma(isLess = fLT(rhsPtr, lhsPtr) != 0L ? 1L : 0L, intPtr = lhsPtr), isLess == 0L ? 1L : 0L) != 0L) {
            intPtr = rhsPtr;
        }
        tempVal = getI64(intPtr);
        putI64(resultPtr + 8L, getI64(intPtr + 8L));
        putI64(resultPtr, tempVal);
        if (getI32(resultPtr) == 3L) {
            putI32(resultPtr, 1L);
        }
        return 0L;
    }

    long fimin(long lhsPtr, long rhsPtr, long resultPtr) {
        long isLess = 0L;
        long intPtr = 0L;
        long tempVal = 0L;
        intPtr = rhsPtr;
        if (getI32(lhsPtr) != 0L && comma(comma(isLess = fLT(lhsPtr, rhsPtr) != 0L ? 1L : 0L, intPtr = lhsPtr), isLess == 0L ? 1L : 0L) != 0L) {
            intPtr = rhsPtr;
        }
        tempVal = getI64(intPtr);
        putI64(resultPtr + 8L, getI64(intPtr + 8L));
        putI64(resultPtr, tempVal);
        if (getI32(resultPtr) == 3L) {
            putI32(resultPtr, 1L);
        }
        return 0L;
    }

    long fiParity(long realPtr) {
        long exponent = 0L;
        if (!(((getI32(realPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI32(realPtr) + 1L) & 31L)) & 21L) != 0L)) {
            return exponent;
        }
        exponent = 1L;
        if ((getI32(realPtr + 4L) & 0xffffffffL) < 15L && comma(exponent = 0xffffffffL, ((getI64(realPtr + 8L) >>> (int) ((getI32(realPtr + 4L) * -4L + 56L) & 63L)) & 1L) == 0L ? 1L : 0L) != 0L) {
            exponent = 1L;
        }
        return exponent;
    }

    long fiInteger(long realPtr) {
        long exponent = 0L;
        switch ((int) (getU32(realPtr))) {
            case 0: {
                return 0L;
            }
            case -1: case 1: case 3: {
                exponent = getU32(realPtr + 4L);
                if ((long) (int) exponent >= 15L) {
                    return 1L;
                }
                if ((long) (int) exponent < 0L) {
                    return 0L;
                } else {
                    return getI64(realPtr + 8L) << (int) ((exponent * 4L + 8L) & 63L) == 0L ? 1L : 0L;
                }
            }
            default: {
                return 1L;
            }
        }
    }

    long fRoundOrTruncMantissa(long srcPtr, long digitIndex, long isRound, long resultPtr) {
        long exponent = 0L;
        long mantissa = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    step = 1;
                    continue stepLoop;
                }
                case 1: {
                    tempVal2 = getI64(srcPtr);
                    putI64(isRound + 8L, getI64(srcPtr + 8L));
                    putI64(isRound, tempVal2);
                    exponent = (long) (int) digitIndex;
                    if ((resultPtr & 1L) == 0L) {
                        exponent = (long) (int) (digitIndex + 1L + getI32(srcPtr + 4L));
                    }
                    if (14L < exponent) {
                        return srcPtr;
                    }
                    if (exponent < 0L) {
                        putI32(isRound + 8L, 0L);
                        putI32(isRound + 12L, 0L);
                        putI32(isRound, 1L);
                        putI32(isRound + 4L, 0L);
                        return srcPtr;
                    }
                    mantissa = (long) (int) (exponent * -4L + 60L);
                    tempVal1 = -1L << (int) (mantissa & 63L);
                    if (((((resultPtr & 0xffffffffL)) >> 1) & 1L) != 0L) {
                        mantissa = getI64(isRound + 8L);
                        step = 2;
                        continue stepLoop;
                    } else {
                        srcPtr = getI64(isRound + 8L);
                        if (Long.compareUnsigned(5L << (int) ((exponent * -4L + 56L) & 63L), srcPtr & ((tempVal1 ^ -1L))) <= 0L) {
                            srcPtr = dcbAddAdjust(srcPtr, 1L << (int) (mantissa & 63L));
                            putI64(isRound + 8L, srcPtr);
                        }
                        mantissa = srcPtr & tempVal1;
                        putI64(isRound + 8L, mantissa);
                        if (((mantissa >>> 60) & 0xffffffffL) == 0L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            exponent = getI32(isRound + 4L);
                            putI64(isRound + 8L, mantissa >>> 4);
                            putI32(isRound + 4L, exponent + 1L);
                            srcPtr = isRound;
                            if (49998L < exponent) {
                                exponent = -2L;
                                if (0L < getI32(isRound)) {
                                    exponent = 2L;
                                }
                                putI32(isRound, exponent);
                            }
                            step = 1;
                            continue stepLoop;
                        }
                    }
                }
                case 2: {
                    putI64(isRound + 8L, mantissa & tempVal1);
                    if ((mantissa & tempVal1) != 0L) {
                        return srcPtr;
                    }
                    putI32(isRound + 8L, 0L);
                    putI32(isRound + 12L, 0L);
                    putI32(isRound, 1L);
                    putI32(isRound + 4L, 0L);
                    return srcPtr;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    void fitrunc(long srcPtr, long decimalsPtr, long mode, long resultPtr) {
        fRoundOrTruncMantissa(srcPtr, (long) (int) decimalsPtr, mode, (((resultPtr & 1L) | 2L)) & 0xffffffffL);
    }

    long fPack(long realPtr) {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long flag = 0L;
        long tempVal3 = 0L;
        long bcdAcc1 = 0L;
        long bcdAcc2 = 0L;
        long tempVal4 = 0L;
        long exponent = 0L;
        tempVal1 = getU32(realPtr);
        exponent = getU32(realPtr + 4L);
        bcdAcc1 = getI64(realPtr + 8L) & 0xffffffffL;
        tempVal4 = getU32(realPtr + 12L);
        if (((tempVal1 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal1 + 1L) & 31L)) & 21L) != 0L) {
            if (((getU32(realPtr + 12L) >> 28) & 0xffffffffL) != 0L) {
                bcdAcc1 = dcbShiftRound(getI64(realPtr + 8L), 1L);
                tempVal4 = bcdAcc1 >>> 32;
                exponent = (exponent + 1L) & 0xffffffffL;
            }
            tempVal3 = bcdAcc1 & 0xffffffffL;
            if (1280L < (tempVal3 & 4095L) || (tempVal3 & 8191L) == 5376L) {
                bcdAcc2 = dcbAddAdjust((bcdAcc1 & 0xffffffffL) | (tempVal4 << 32), 4096L);
                tempVal2 = (bcdAcc2 >>> 32) & 0xffffffffL;
                flag = ((tempVal2 >> 28) & 0xffffffffL) == 0L ? 1L : 0L;
                tempVal3 = 0x1000000L;
                if (flag != 0L) {
                    tempVal3 = tempVal2;
                }
                tempVal4 = tempVal3;
                if (flag == 0L) {
                    exponent = (exponent + 1L) & 0xffffffffL;
                }
                tempVal3 = 0L;
                if (flag != 0L) {
                    tempVal3 = bcdAcc2 & 0xffffffffL;
                }
            }
            if ((long) (int) exponent >= 500L) {
                flag = 0L < (long) (int) tempVal1 ? 1L : 0L;
                tempVal1 = 0xfffffffeL;
                if (flag != 0L) {
                    tempVal1 = 2L;
                }
                exponent = 49999L;
                tempVal4 = 0x999999999999000L;
                tempVal4 = (tempVal4 | (tempVal1 << 60)) + 0x4000000000000000L;
                return tempVal4 | (exponent & 4095L);
            }
            if ((long) (int) exponent < -499L) {
                exponent = 0L;
                tempVal4 = 0L;
                return tempVal4 | (exponent & 4095L);
            }
            bcdAcc1 = tempVal3 & 0xfffff000L;
        }
        tempVal4 = ((bcdAcc1 | (tempVal4 << 32))) & 0xffffffffffff000L;
        if (tempVal1 == 1L) {
            return tempVal4 | (exponent & 4095L);
        }
        if (tempVal1 != 0xffffffffL) {
            tempVal4 = (tempVal4 | (tempVal1 << 60)) + 0x4000000000000000L;
            return tempVal4 | (exponent & 4095L);
        }
        tempVal4 = tempVal4 | 0x9000000000000000L;
        return tempVal4 | (exponent & 4095L);
    }

    void fUnpack(long packedPtr, long resultPtr) {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long intVal = 0L;
        tempVal1 = (packedPtr >>> 32) & 0xffffffffL;
        tempVal2 = (tempVal1 >> 28) & 0xffffffffL;
        if (tempVal2 == 0L) {
            intVal = 1L;
        } else {
            if (tempVal2 == 9L) {
                intVal = -1L;
            } else {
                intVal = (long) (int) ((tempVal1 >> 28) - 4L);
            }
        }
        putI64(resultPtr + 8L, packedPtr & 0xffffffffffff000L);
        putI32(resultPtr, intVal);
        putI32(resultPtr + 4L, (long) (int) (packedPtr << 20) >> 20);
    }

    long fToFract(long srcPtr, long maxDenomPtr, long outSignOrIntPtr, long outNumerPtr, long outDenomPtr) {
        long exponent = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long bcdAcc1 = 0L;
        long intVal = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        long index = 0L;
        long bcdAcc2 = 0L;
        long tempVal5 = 0L;
        long tempVal6 = 0L;
        long longVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(448);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        intVal = (long) (int) getI64(srcPtr);
                        if (intVal == 0L || comma(exponent = (getI64(maxDenomPtr) + 1L) & 0xffffffffL, 4L < exponent ? 1L : 0L) != 0L || ((1L << (int) (exponent & 31L)) & 21L) == 0L) {
                            if (outSignOrIntPtr != 0L) {
                                putI64(outSignOrIntPtr, 0L);
                                putI64(outSignOrIntPtr + 8L, 0L);
                            }
                            putI64(outDenomPtr, 0L);
                            putI64(outDenomPtr + 8L, 0L);
                            putI64(outNumerPtr, 0L);
                            putI64(outNumerPtr + 8L, 0L);
                            return 0L;
                        }
                        if (!(((intVal + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((intVal + 1L) & 31L)) & 21L) != 0L)) {
                            if (outSignOrIntPtr == 0L) {
                                if (intVal < 1L) {
                                    bcdAcc1 = 0xffffffffL;
                                } else {
                                    bcdAcc1 = 1L;
                                }
                                putI64(outNumerPtr + 8L, 0x100000000000000L);
                                putI64(outNumerPtr, bcdAcc1);
                                tempVal6 = 0L;
                                bcdAcc1 = 1L;
                                putI64(outDenomPtr + 8L, tempVal6);
                                putI64(outDenomPtr, bcdAcc1);
                                return 0L;
                            }
                            bcdAcc1 = getI64(srcPtr);
                            putI64(outSignOrIntPtr + 8L, getI64(srcPtr + 8L));
                            putI64(outSignOrIntPtr, bcdAcc1);
                            putI64(outNumerPtr + 8L, 0L);
                            putI64(outNumerPtr, 1L);
                            putI64(outDenomPtr + 8L, 0x100000000000000L);
                            putI64(outDenomPtr, 1L);
                        }
                        putI64(framePtr + 248L, getI64(maxDenomPtr + 8L));
                        bcdAcc1 = getI64(maxDenomPtr);
                        putI64(framePtr + 232L, getI64(srcPtr + 8L));
                        putI64(framePtr + 224L, getI64(srcPtr));
                        putI64(framePtr + 120L, 0x100000000000000L);
                        putI64(framePtr + 112L, 1L);
                        tempVal6 = 0L;
                        longVal = 1L;
                        intVal = getI32(framePtr + 224L);
                        putI64(framePtr + 80L, longVal);
                        putI64(framePtr + 88L, tempVal6);
                        putI64(framePtr + 96L, longVal);
                        putI64(framePtr + 104L, tempVal6);
                        putI64(framePtr + 128L, longVal);
                        putI64(framePtr + 136L, tempVal6);
                        putI64(framePtr + 144L, longVal);
                        putI64(framePtr + 152L, tempVal6);
                        putI64(framePtr + 160L, longVal);
                        putI64(framePtr + 168L, tempVal6);
                        putI64(framePtr + 176L, getI64(framePtr + 112L));
                        putI64(framePtr + 184L, getI64(framePtr + 120L));
                        putI64(framePtr + 192L, getI64(framePtr + 112L));
                        putI64(framePtr + 200L, getI64(framePtr + 120L));
                        putI64(framePtr + 208L, getI64(framePtr + 112L));
                        putI64(framePtr + 216L, getI64(framePtr + 120L));
                        putI64(framePtr + 240L, bcdAcc1);
                        if (outSignOrIntPtr == 0L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            putI64(outSignOrIntPtr + 8L, getI64(framePtr + 232L));
                            putI64(outSignOrIntPtr, getI64(framePtr + 224L));
                            index = (long) (int) getI64(outSignOrIntPtr);
                            if (index == -1L || index == 1L) {
                                step = 6;
                                continue stepLoop;
                            } else {
                                if (index == 3L) {
                                    putU32(outSignOrIntPtr, 1L);
                                    step = 6;
                                    continue stepLoop;
                                } else {
                                    step = 5;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 1: {
                        putI64(outNumerPtr + 8L, getI64(framePtr + 232L));
                        putI64(outNumerPtr, getI64(framePtr + 224L));
                        bcdAcc1 = getI64(framePtr + 112L);
                        tempVal6 = getI64(framePtr + 120L);
                        putI64(outDenomPtr + 8L, tempVal6);
                        putI64(outDenomPtr, bcdAcc1);
                        return 0L;
                    }
                    case 2: {
                        switch ((int) (intVal)) {
                            case 0: {
                                step = 3;
                                continue stepLoop;
                            }
                            case -1: {
                                step = 4;
                                continue stepLoop;
                            }
                            case 1: {
                                step = 4;
                                continue stepLoop;
                            }
                            case 3: {
                                step = 4;
                                continue stepLoop;
                            }
                            default: {
                                step = 1;
                                continue stepLoop;
                            }
                        }
                    }
                    case 3: {
                        putI32(framePtr + 240L, bcdAcc1);
                        if ((long) (int) getI64(framePtr + 240L) < 0L) {
                            if (((getU32(CC_LOG2) + 2L) & 0xffffffffL) < 6L) {
                                exponent = (1L << (int) ((getU32(CC_LOG2) + 2L) & 31L)) & 0xffffffffL;
                                if ((exponent & 42L) == 0L) {
                                    if ((exponent & 17L) != 0L) {
                                        step = 10;
                                        continue stepLoop;
                                    } else {
                                        step = 9;
                                        continue stepLoop;
                                    }
                                } else {
                                    if ((getI64(framePtr + 240L) & 0xffffffffL) == 0xffffffffL) {
                                        fradd(CC_LOG2, framePtr + 240L, framePtr + 240L, 1L);
                                        bcdAcc1 = getI64(framePtr + 240L);
                                        step = 13;
                                        continue stepLoop;
                                    } else {
                                        if (getU32(CC_LOG2) == 2L) {
                                            step = 10;
                                            continue stepLoop;
                                        } else {
                                            if (getU32(CC_LOG2) != 0L) {
                                                step = 14;
                                                continue stepLoop;
                                            } else {
                                                step = 9;
                                                continue stepLoop;
                                            }
                                        }
                                    }
                                }
                            } else {
                                step = 14;
                                continue stepLoop;
                            }
                        } else {
                            putI32(framePtr + 244L, bcdAcc1 >>> 32);
                            if (-1L < bcdAcc1) {
                                if (getI32(framePtr + 244L) < 10L && (getI32(framePtr + 244L) != 9L || Long.compareUnsigned(getI64(framePtr + 248L), 0x214748364700001L) < 0L)) {
                                    intVal = 0L;
                                    index = 15L;
                                    bcdAcc2 = 56L;
                                    do {
                                        index = (long) (int) (index - 1L);
                                        intVal = (long) (int) (((getI64(framePtr + 248L) >>> (int) (bcdAcc2 & 63L)) & 15L) + intVal * 10L);
                                        bcdAcc2 = (long) (int) (bcdAcc2 - 4L);
                                    } while ((long) (int) (14L - getI32(framePtr + 244L)) < index);
                                } else {
                                    intVal = 0x7fffffffL;
                                    if ((long) (int) getI64(framePtr + 240L) < 1L) {
                                        intVal = 0xffffffff80000000L;
                                    }
                                }
                                putI64(framePtr + 240L, (((-intVal & 0xffffffffL)) << 32) | (getI64(framePtr + 240L) & 0xffffffffL));
                                putI64(framePtr + 248L, 0x100000000000000L);
                                bcdAcc1 = getI64(framePtr + 240L);
                            }
                            step = 13;
                            continue stepLoop;
                        }
                    }
                    case 4: {
                        if (getI32(framePtr + 228L) < 15L && (getI64(framePtr + 224L) < 0L || getI64(framePtr + 232L) << (int) ((getI32(framePtr + 228L) * 4L + 8L) & 63L) != 0L)) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            step = 1;
                            continue stepLoop;
                        }
                    }
                    case 5: {
                        switch ((int) (getI32(framePtr + 224L))) {
                            case 0: {
                                step = 3;
                                continue stepLoop;
                            }
                            case 3: {
                                intVal = 1L;
                                putI32(framePtr + 228L, getI64(framePtr + 224L) >>> 32);
                                putI32(framePtr + 224L, 1L);
                                step = 7;
                                continue stepLoop;
                            }
                            case -2: {
                                step = 8;
                                continue stepLoop;
                            }
                            case 2: {
                                step = 8;
                                continue stepLoop;
                            }
                            default: {
                                step = 7;
                                continue stepLoop;
                            }
                        }
                    }
                    case 6: {
                        exponent = getU32(outSignOrIntPtr + 4L);
                        if ((long) (int) exponent < 14L) {
                            if ((long) (int) exponent < 0L) {
                                putI64(outSignOrIntPtr + 8L, 0L);
                                putI64(outSignOrIntPtr, 1L);
                            } else {
                                putI64(outSignOrIntPtr + 8L, getI64(outSignOrIntPtr + 8L) & (-1L << (int) ((exponent * -4L + 56L) & 63L)));
                            }
                        }
                        step = 5;
                        continue stepLoop;
                    }
                    case 7: {
                        if (getI32(framePtr + 228L) < 14L) {
                            if (-1L < getI64(framePtr + 224L)) {
                                bcdAcc2 = getI64(framePtr + 232L) << (int) ((getI32(framePtr + 228L) * 4L + 4L) & 63L);
                                putI64(framePtr + 232L, bcdAcc2 & 0xfffffffffffffffL);
                                putI32(framePtr + 228L, -1L);
                                if (getI64(framePtr + 232L) == 0L) {
                                    putI32(framePtr + 224L, 1L);
                                    putI32(framePtr + 228L, 0L);
                                    step = 4;
                                    continue stepLoop;
                                } else {
                                    exponent = 0L;
                                    if ((((((bcdAcc2 >>> 32) & 0xfffffffL)) >> 24) & 0xffffffffL) == 0L) {
                                        do {
                                            tempVal4 = exponent;
                                            bcdAcc2 = getI64(framePtr + 232L) >>> 52;
                                            putI64(framePtr + 232L, getI64(framePtr + 232L) << 4);
                                            exponent = (tempVal4 - 1L) & 0xffffffffL;
                                        } while ((bcdAcc2 & 0xffL) == 0L);
                                        putI32(framePtr + 228L, tempVal4 - 2L);
                                        if (exponent < 0xffff3cb2L) {
                                            step = 8;
                                            continue stepLoop;
                                        } else {
                                            step = 2;
                                            continue stepLoop;
                                        }
                                    } else {
                                        step = 2;
                                        continue stepLoop;
                                    }
                                }
                            } else {
                                step = 2;
                                continue stepLoop;
                            }
                        } else {
                            step = 8;
                            continue stepLoop;
                        }
                    }
                    case 8: {
                        putI64(framePtr + 224L, longVal);
                        putI64(framePtr + 232L, tempVal6);
                        step = 4;
                        continue stepLoop;
                    }
                    case 9: {
                        putI64(framePtr + 240L, 0L);
                        putI64(framePtr + 248L, 0L);
                        bcdAcc1 = getI64(framePtr + 240L);
                        step = 13;
                        continue stepLoop;
                    }
                    case 10: {
                        if ((getI64(framePtr + 240L) & 0xffffffffL) == 0xfffffffeL) {
                            if (((getU32(CC_LOG2) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(CC_LOG2) + 1L) & 31L)) & 21L) != 0L) {
                                step = 13;
                                continue stepLoop;
                            } else {
                                step = 11;
                                continue stepLoop;
                            }
                        } else {
                            if ((getI64(framePtr + 240L) & 0xffffffffL) != 0xffffffffL && -1L >= (long) (int) (getU32(CC_LOG2) ^ getI64(framePtr + 240L))) {
                                step = 9;
                                continue stepLoop;
                            } else {
                                step = 12;
                                continue stepLoop;
                            }
                        }
                    }
                    case 11: {
                        if (-1L < (long) (int) (getU32(CC_LOG2) ^ getI64(framePtr + 240L))) {
                            step = 12;
                            continue stepLoop;
                        } else {
                            step = 9;
                            continue stepLoop;
                        }
                    }
                    case 12: {
                        putI64(framePtr + 248L, getI64(CC_LOG2 + 8L));
                        bcdAcc1 = (getU32(CC_LOG2 + 4L) << 32) | getU32(CC_LOG2);
                        step = 13;
                        continue stepLoop;
                    }
                    case 13: {
                        putI64(framePtr + 240L, bcdAcc1);
                        if ((getI64(framePtr + 240L) & 0xffffffffL) != 0xfffffffeL) {
                            if ((getI64(framePtr + 240L) & 0xffffffffL) == 2L) {
                                step = 16;
                                continue stepLoop;
                            } else {
                                if ((getI64(framePtr + 240L) & 0xffffffffL) != 0L) {
                                    exponent = getI64(framePtr + 240L) & 0xffffffffL;
                                    if ((getI64(framePtr + 240L) & 0xffffffffL) == 3L) {
                                        exponent = 1L;
                                    }
                                    if (1L < (long) (int) exponent || exponent == 1L && getU32(framePtr + 252L) != 0L && (-499L < getI32(framePtr + 244L) || getI32(framePtr + 244L) == -499L && Long.compareUnsigned(0x100000000000000L, getI64(framePtr + 248L)) < 0L)) {
                                        if ((getI64(framePtr + 240L) & 0xffffffffL) == 3L) {
                                            putI64(framePtr + 240L, (((getI32(framePtr + 244L) & 0xffffffffL)) << 32) | 1L);
                                        }
                                        step = 16;
                                        continue stepLoop;
                                    } else {
                                        step = 15;
                                        continue stepLoop;
                                    }
                                } else {
                                    step = 15;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            step = 15;
                            continue stepLoop;
                        }
                    }
                    case 14: {
                        if ((getI64(framePtr + 240L) & 0xffffffffL) == 0xfffffffeL && ((getU32(CC_LOG2) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(CC_LOG2) + 1L) & 31L)) & 21L) != 0L) {
                            step = 13;
                            continue stepLoop;
                        } else {
                            step = 11;
                            continue stepLoop;
                        }
                    }
                    case 15: {
                        putI64(framePtr + 248L, 0x100000000000000L);
                        putI64(framePtr + 240L, 0xfffffe0d00000001L);
                        step = 16;
                        continue stepLoop;
                    }
                    case 16: {
                        putI32(framePtr + 224L, 1L);
                        putI64(framePtr + 264L, getI64(framePtr + 232L));
                        putI32(framePtr + 256L, 1L);
                        putI32(framePtr + 260L, getI32(framePtr + 228L));
                        step = 17;
                        continue stepLoop;
                    }
                    case 17: {
                        fdivmod(framePtr + 224L, framePtr + 208L, framePtr + 96L, framePtr + 80L);
                        fimul(framePtr + 96L, framePtr + 192L, framePtr + 176L);
                        switch ((int) (getI64(framePtr + 176L))) {
                            case 0: {
                                step = 21;
                                continue stepLoop;
                            }
                            case -1: {
                                step = 22;
                                continue stepLoop;
                            }
                            case 1: {
                                step = 22;
                                continue stepLoop;
                            }
                            case 3: {
                                step = 22;
                                continue stepLoop;
                            }
                            default: {
                                if ((getI64(framePtr + 160L) & 0xffffffffL) != 0L) {
                                    step = 18;
                                    continue stepLoop;
                                } else {
                                    step = 21;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 18: {
                        if ((getI64(framePtr + 176L) & 0xffffffffL) == 2L || (getI64(framePtr + 176L) & 0xffffffffL) == 0xfffffffeL) {
                            if (((getI64(framePtr + 160L) + 2L) & 0xffffffffL) < 6L) {
                                exponent = (1L << (int) ((getI64(framePtr + 160L) + 2L) & 31L)) & 0xffffffffL;
                                if ((exponent & 42L) != 0L) {
                                    step = 23;
                                    continue stepLoop;
                                } else {
                                    if ((exponent & 17L) != 0L && ((getI64(framePtr + 176L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 176L) + 1L) & 31L)) & 21L) != 0L) {
                                        step = 20;
                                        continue stepLoop;
                                    } else {
                                        step = 19;
                                        continue stepLoop;
                                    }
                                }
                            } else {
                                step = 19;
                                continue stepLoop;
                            }
                        } else {
                            if (((getI64(framePtr + 160L) & 0xffffffffL) == 2L || (getI64(framePtr + 160L) & 0xffffffffL) == 0xfffffffeL) && ((getI64(framePtr + 176L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 176L) + 1L) & 31L)) & 21L) != 0L) {
                                step = 20;
                                continue stepLoop;
                            } else {
                                step = 19;
                                continue stepLoop;
                            }
                        }
                    }
                    case 19: {
                        if (-1L < (long) (int) (getI64(framePtr + 160L) ^ getI64(framePtr + 176L))) {
                            step = 23;
                            continue stepLoop;
                        } else {
                            step = 21;
                            continue stepLoop;
                        }
                    }
                    case 20: {
                        putI64(framePtr + 184L, getI64(framePtr + 168L));
                        putI64(framePtr + 176L, getI64(framePtr + 160L));
                        step = 23;
                        continue stepLoop;
                    }
                    case 21: {
                        putI64(framePtr + 176L, 0L);
                        putI64(framePtr + 184L, 0L);
                        step = 23;
                        continue stepLoop;
                    }
                    case 22: {
                        if (4L < ((getI64(framePtr + 160L) + 1L) & 0xffffffffL) || ((1L << (int) ((getI64(framePtr + 160L) + 1L) & 31L)) & 21L) == 0L) {
                            if ((getI64(framePtr + 176L) & 0xffffffffL) != 0L && (getI64(framePtr + 160L) & 0xffffffffL) != 0L) {
                                step = 18;
                                continue stepLoop;
                            } else {
                                step = 21;
                                continue stepLoop;
                            }
                        } else {
                            fradd(framePtr + 176L, framePtr + 160L, framePtr + 176L, 0L);
                            step = 23;
                            continue stepLoop;
                        }
                    }
                    case 23: {
                        fimul(framePtr + 96L, framePtr + 144L, framePtr + 128L);
                        switch ((int) (getI64(framePtr + 128L))) {
                            case 0: {
                                step = 65;
                                continue stepLoop;
                            }
                            case -1: {
                                step = 66;
                                continue stepLoop;
                            }
                            case 1: {
                                step = 66;
                                continue stepLoop;
                            }
                            case 3: {
                                step = 66;
                                continue stepLoop;
                            }
                            default: {
                                if ((getI64(framePtr + 112L) & 0xffffffffL) != 0L) {
                                    step = 62;
                                    continue stepLoop;
                                } else {
                                    step = 65;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 24: {
                        tempVal3 = getI64(framePtr + 16L) & 0xffffffffL;
                        bcdAcc2 = getI64(framePtr + 24L);
                        switch ((int) (getI64(framePtr + 16L))) {
                            case 0: {
                                if (getI32(framePtr + 28L) == 0L) {
                                    step = 26;
                                    continue stepLoop;
                                } else {
                                    bcdAcc1 = dcbAddAdjust(getI64(framePtr + 24L), getI64(framePtr + 24L));
                                    bcdAcc1 = dcbAddAdjust(bcdAcc1, bcdAcc1);
                                    bcdAcc2 = dcbAddAdjust(bcdAcc1, getI64(framePtr + 24L));
                                    tempVal3 = getI64(framePtr + 16L) & 0xffffffffL;
                                    if ((getI64(framePtr + 16L) & 0xffffffffL) == 3L) {
                                        tempVal3 = 1L;
                                    }
                                    intVal = getI32(framePtr + 20L);
                                    putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | tempVal3);
                                    if (((bcdAcc2 >>> 60) & 0xffffffffL) != 0L) {
                                        bcdAcc2 = bcdAcc2 >>> 4;
                                        step = 26;
                                        continue stepLoop;
                                    } else {
                                        putI64(framePtr + 16L, ((((getI32(framePtr + 20L) - 1L) & 0xffffffffL)) << 32) | tempVal3);
                                        bcdAcc1 = longVal;
                                        putI64(framePtr + 24L, tempVal6);
                                        if (-49999L < intVal) {
                                            step = 26;
                                            continue stepLoop;
                                        } else {
                                            step = 25;
                                            continue stepLoop;
                                        }
                                    }
                                }
                            }
                            case -1: {
                                putI64(framePtr + 16L, 0L);
                                putI64(framePtr + 24L, 0L);
                                bcdAcc1 = getI64(framePtr + 16L);
                                bcdAcc2 = getI64(framePtr + 24L);
                                step = 27;
                                continue stepLoop;
                            }
                            default: {
                                step = 26;
                                continue stepLoop;
                            }
                        }
                    }
                    case 25: {
                        putI64(framePtr + 16L, bcdAcc1);
                        bcdAcc1 = getI64(framePtr + 16L);
                        bcdAcc2 = getI64(framePtr + 24L);
                        if (getI32(framePtr + 20L) < 14L && comma(comma(bcdAcc1 = longVal, bcdAcc2 = tempVal6), Long.compareUnsigned(-1L, getI64(framePtr + 16L)) < 0L ? 1L : 0L) != 0L) {
                            bcdAcc1 = getI64(framePtr + 16L);
                            bcdAcc2 = getI64(framePtr + 24L) & (-1L << (int) ((getI32(framePtr + 20L) * -4L + 56L) & 63L));
                        }
                        step = 27;
                        continue stepLoop;
                    }
                    case 26: {
                        putI64(framePtr + 24L, bcdAcc2);
                        bcdAcc1 = getI64(framePtr + 16L);
                        if (tempVal3 != 0xffffffffL && tempVal3 != 1L) {
                            bcdAcc2 = getI64(framePtr + 24L);
                            if (tempVal3 != 3L) {
                                step = 27;
                                continue stepLoop;
                            } else {
                                putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | 1L);
                                bcdAcc1 = getI64(framePtr + 16L);
                                step = 25;
                                continue stepLoop;
                            }
                        } else {
                            step = 25;
                            continue stepLoop;
                        }
                    }
                    case 27: {
                        putI64(framePtr + 24L, bcdAcc2);
                        putI64(framePtr + 16L, bcdAcc1);
                        fimul(framePtr + 160L, framePtr + 16L, framePtr + 176L);
                        switch ((int) (tempVal4)) {
                            case -1: {
                                step = 33;
                                continue stepLoop;
                            }
                            case 1: {
                                step = 33;
                                continue stepLoop;
                            }
                            case 3: {
                                step = 33;
                                continue stepLoop;
                            }
                            case 0: {
                                step = 30;
                                continue stepLoop;
                            }
                            default: {
                                if ((getI64(framePtr + 176L) & 0xffffffffL) == 0L) {
                                    step = 30;
                                    continue stepLoop;
                                } else {
                                    step = 29;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 28: {
                        fimul(framePtr + 112L, framePtr + 16L, framePtr + 128L);
                        switch ((int) (exponent)) {
                            case -1: {
                                step = 39;
                                continue stepLoop;
                            }
                            case 1: {
                                step = 39;
                                continue stepLoop;
                            }
                            case 3: {
                                step = 39;
                                continue stepLoop;
                            }
                            case 0: {
                                step = 36;
                                continue stepLoop;
                            }
                            default: {
                                if ((getI64(framePtr + 128L) & 0xffffffffL) == 0L) {
                                    step = 36;
                                    continue stepLoop;
                                } else {
                                    step = 35;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 29: {
                        if (tempVal4 == 2L || tempVal4 == 0xfffffffeL) {
                            if (5L < ((getI64(framePtr + 176L) + 2L) & 0xffffffffL)) {
                                step = 32;
                                continue stepLoop;
                            } else {
                                tempVal3 = (1L << (int) ((getI64(framePtr + 176L) + 2L) & 31L)) & 0xffffffffL;
                                if ((tempVal3 & 42L) == 0L) {
                                    if ((tempVal3 & 17L) != 0L && ((tempVal4 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal4 + 1L) & 31L)) & 21L) != 0L) {
                                        step = 28;
                                        continue stepLoop;
                                    } else {
                                        step = 32;
                                        continue stepLoop;
                                    }
                                } else {
                                    step = 31;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            if (((getI64(framePtr + 176L) & 0xffffffffL) == 2L || (getI64(framePtr + 176L) & 0xffffffffL) == 0xfffffffeL) && ((tempVal4 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal4 + 1L) & 31L)) & 21L) != 0L) {
                                step = 28;
                                continue stepLoop;
                            } else {
                                step = 32;
                                continue stepLoop;
                            }
                        }
                    }
                    case 30: {
                        putI64(framePtr + 176L, 0L);
                        putI64(framePtr + 184L, 0L);
                        step = 28;
                        continue stepLoop;
                    }
                    case 31: {
                        putI64(framePtr + 184L, getI64(framePtr + 200L));
                        putI64(framePtr + 176L, getI64(framePtr + 192L));
                        step = 28;
                        continue stepLoop;
                    }
                    case 32: {
                        if ((long) (int) (getI64(framePtr + 176L) ^ tempVal4) < 0L) {
                            step = 30;
                            continue stepLoop;
                        } else {
                            step = 31;
                            continue stepLoop;
                        }
                    }
                    case 33: {
                        if (((getI64(framePtr + 176L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 176L) + 1L) & 31L)) & 21L) != 0L) {
                            fradd(framePtr + 192L, framePtr + 176L, framePtr + 176L, 1L);
                            step = 28;
                            continue stepLoop;
                        } else {
                            if (tempVal4 != 0L && (getI64(framePtr + 176L) & 0xffffffffL) != 0L) {
                                step = 29;
                                continue stepLoop;
                            } else {
                                step = 30;
                                continue stepLoop;
                            }
                        }
                    }
                    case 34: {
                        fidiv(framePtr + 176L, framePtr + 128L, framePtr + 64L);
                        if (((getI64(framePtr + 64L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 64L) + 1L) & 31L)) & 21L) != 0L) {
                            fradd(framePtr + 256L, framePtr + 64L, framePtr + 64L, 1L);
                            step = 40;
                            continue stepLoop;
                        } else {
                            if ((getI64(framePtr + 64L) & 0xffffffffL) == 0L) {
                                step = 41;
                                continue stepLoop;
                            } else {
                                if ((getI64(framePtr + 64L) & 0xffffffffL) != 2L && (getI64(framePtr + 64L) & 0xffffffffL) != 0xfffffffeL) {
                                    if ((long) (int) (getI64(framePtr + 64L) ^ 1L) < 0L) {
                                        step = 41;
                                        continue stepLoop;
                                    } else {
                                        putI64(framePtr + 72L, getI64(framePtr + 264L));
                                        putI64(framePtr + 64L, getI64(framePtr + 256L));
                                        step = 40;
                                        continue stepLoop;
                                    }
                                } else {
                                    step = 40;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 35: {
                        if (exponent == 2L || exponent == 0xfffffffeL) {
                            if (5L < ((getI64(framePtr + 128L) + 2L) & 0xffffffffL)) {
                                step = 38;
                                continue stepLoop;
                            } else {
                                tempVal3 = (1L << (int) ((getI64(framePtr + 128L) + 2L) & 31L)) & 0xffffffffL;
                                if ((tempVal3 & 42L) == 0L) {
                                    if ((tempVal3 & 17L) != 0L && ((exponent + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((exponent + 1L) & 31L)) & 21L) != 0L) {
                                        step = 34;
                                        continue stepLoop;
                                    } else {
                                        step = 38;
                                        continue stepLoop;
                                    }
                                } else {
                                    step = 37;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            if (((getI64(framePtr + 128L) & 0xffffffffL) == 2L || (getI64(framePtr + 128L) & 0xffffffffL) == 0xfffffffeL) && ((exponent + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((exponent + 1L) & 31L)) & 21L) != 0L) {
                                step = 34;
                                continue stepLoop;
                            } else {
                                step = 38;
                                continue stepLoop;
                            }
                        }
                    }
                    case 36: {
                        putI64(framePtr + 128L, 0L);
                        putI64(framePtr + 136L, 0L);
                        step = 34;
                        continue stepLoop;
                    }
                    case 37: {
                        putI64(framePtr + 136L, getI64(framePtr + 152L));
                        putI64(framePtr + 128L, getI64(framePtr + 144L));
                        step = 34;
                        continue stepLoop;
                    }
                    case 38: {
                        if ((long) (int) (getI64(framePtr + 128L) ^ exponent) < 0L) {
                            step = 36;
                            continue stepLoop;
                        } else {
                            step = 37;
                            continue stepLoop;
                        }
                    }
                    case 39: {
                        if (((getI64(framePtr + 128L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 128L) + 1L) & 31L)) & 21L) != 0L) {
                            fradd(framePtr + 144L, framePtr + 128L, framePtr + 128L, 1L);
                            step = 34;
                            continue stepLoop;
                        } else {
                            if (exponent != 0L && (getI64(framePtr + 128L) & 0xffffffffL) != 0L) {
                                step = 35;
                                continue stepLoop;
                            } else {
                                step = 36;
                                continue stepLoop;
                            }
                        }
                    }
                    case 40: {
                        if (getU32(framePtr + 252L) == 0L || getI32(framePtr + 76L) != 0L && getI32(framePtr + 244L) <= getI32(framePtr + 68L) && (getI32(framePtr + 68L) != getI32(framePtr + 244L) || Long.compareUnsigned(getI64(framePtr + 248L), getI64(framePtr + 72L)) <= 0L)) {
                            putI64(framePtr + 40L, getI64(framePtr + 24L));
                            putI64(framePtr + 32L, getI64(framePtr + 16L));
                        } else {
                            putI64(framePtr + 56L, getI64(framePtr + 24L));
                            putI64(framePtr + 48L, getI64(framePtr + 16L));
                            bcdAcc1 = getI64(framePtr + 48L);
                            putI32(framePtr + 48L, getI64(framePtr + 16L));
                            tempVal5 = getI64(framePtr + 48L) & 0xffffffffL;
                            putI64(framePtr + 48L, bcdAcc1);
                        }
                        switch ((int) (tempVal5)) {
                            case 0: {
                                if (((getI64(framePtr + 32L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 32L) + 1L) & 31L)) & 21L) != 0L) {
                                    fradd(framePtr + 48L, framePtr + 32L, framePtr + 64L, 1L);
                                    step = 42;
                                    continue stepLoop;
                                } else {
                                    if (tempVal5 != 0L && (getI64(framePtr + 32L) & 0xffffffffL) != 0L) {
                                        step = 44;
                                        continue stepLoop;
                                    } else {
                                        step = 43;
                                        continue stepLoop;
                                    }
                                }
                            }
                            case -1: {
                                step = 43;
                                continue stepLoop;
                            }
                            default: {
                                if ((getI64(framePtr + 32L) & 0xffffffffL) == 0L) {
                                    step = 43;
                                    continue stepLoop;
                                } else {
                                    step = 44;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 41: {
                        putI64(framePtr + 64L, 0L);
                        putI64(framePtr + 72L, 0L);
                        step = 40;
                        continue stepLoop;
                    }
                    case 42: {
                        if (getI32(framePtr + 76L) == 0L || Long.compareUnsigned(getI64(framePtr + 64L), 0L) < 0L || getI32(framePtr + 68L) == 0L && Long.compareUnsigned(getI64(framePtr + 72L), 0x100000000000001L) < 0L) {
                            fimul(framePtr + 160L, framePtr + 48L, framePtr + 176L);
                            switch ((int) (tempVal4)) {
                                case -1: {
                                    step = 54;
                                    continue stepLoop;
                                }
                                case 1: {
                                    step = 54;
                                    continue stepLoop;
                                }
                                case 3: {
                                    step = 54;
                                    continue stepLoop;
                                }
                                case 0: {
                                    step = 51;
                                    continue stepLoop;
                                }
                                default: {
                                    if ((getI64(framePtr + 176L) & 0xffffffffL) == 0L) {
                                        step = 51;
                                        continue stepLoop;
                                    } else {
                                        step = 50;
                                        continue stepLoop;
                                    }
                                }
                            }
                        } else {
                            step = 48;
                            continue stepLoop;
                        }
                    }
                    case 43: {
                        putI64(framePtr + 64L, 0L);
                        putI64(framePtr + 72L, 0L);
                        step = 42;
                        continue stepLoop;
                    }
                    case 44: {
                        if (tempVal5 == 2L || tempVal5 == 0xfffffffeL) {
                            if (5L < ((getI64(framePtr + 32L) + 2L) & 0xffffffffL)) {
                                step = 46;
                                continue stepLoop;
                            } else {
                                tempVal3 = (1L << (int) ((getI64(framePtr + 32L) + 2L) & 31L)) & 0xffffffffL;
                                if ((tempVal3 & 42L) == 0L) {
                                    if ((tempVal3 & 17L) != 0L && ((tempVal5 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal5 + 1L) & 31L)) & 21L) != 0L) {
                                        step = 47;
                                        continue stepLoop;
                                    } else {
                                        step = 46;
                                        continue stepLoop;
                                    }
                                } else {
                                    step = 45;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            if (((getI64(framePtr + 32L) & 0xffffffffL) == 2L || (getI64(framePtr + 32L) & 0xffffffffL) == 0xfffffffeL) && ((tempVal5 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal5 + 1L) & 31L)) & 21L) != 0L) {
                                step = 47;
                                continue stepLoop;
                            } else {
                                step = 46;
                                continue stepLoop;
                            }
                        }
                    }
                    case 45: {
                        putI64(framePtr + 72L, getI64(framePtr + 56L));
                        putI64(framePtr + 64L, getI64(framePtr + 48L));
                        step = 42;
                        continue stepLoop;
                    }
                    case 46: {
                        if ((long) (int) (getI64(framePtr + 32L) ^ tempVal5) < 0L) {
                            step = 43;
                            continue stepLoop;
                        } else {
                            step = 45;
                            continue stepLoop;
                        }
                    }
                    case 47: {
                        putI64(framePtr + 72L, getI64(framePtr + 40L));
                        putI64(framePtr + 64L, getI64(framePtr + 32L));
                        step = 42;
                        continue stepLoop;
                    }
                    case 48: {
                        switch ((int) (tempVal5)) {
                            case 0: {
                                if (((getI64(framePtr + 32L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 32L) + 1L) & 31L)) & 21L) != 0L) {
                                    fradd(framePtr + 48L, framePtr + 32L, framePtr + 16L, 0L);
                                    step = 24;
                                    continue stepLoop;
                                } else {
                                    if (tempVal5 != 0L && (getI64(framePtr + 32L) & 0xffffffffL) != 0L) {
                                        step = 71;
                                        continue stepLoop;
                                    } else {
                                        step = 70;
                                        continue stepLoop;
                                    }
                                }
                            }
                            case -1: {
                                step = 70;
                                continue stepLoop;
                            }
                            default: {
                                if ((getI64(framePtr + 32L) & 0xffffffffL) == 0L) {
                                    step = 70;
                                    continue stepLoop;
                                } else {
                                    step = 71;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 49: {
                        fimul(framePtr + 112L, framePtr + 48L, framePtr + 128L);
                        switch ((int) (exponent)) {
                            case 0: {
                                step = 55;
                                continue stepLoop;
                            }
                            case -1: {
                                step = 60;
                                continue stepLoop;
                            }
                            case 1: {
                                step = 60;
                                continue stepLoop;
                            }
                            case 3: {
                                step = 60;
                                continue stepLoop;
                            }
                            default: {
                                if ((getI64(framePtr + 128L) & 0xffffffffL) != 0L) {
                                    step = 56;
                                    continue stepLoop;
                                } else {
                                    step = 55;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 50: {
                        if (tempVal4 == 2L || tempVal4 == 0xfffffffeL) {
                            if (5L < ((getI64(framePtr + 176L) + 2L) & 0xffffffffL)) {
                                step = 53;
                                continue stepLoop;
                            } else {
                                tempVal5 = (1L << (int) ((getI64(framePtr + 176L) + 2L) & 31L)) & 0xffffffffL;
                                if ((tempVal5 & 42L) == 0L) {
                                    if ((tempVal5 & 17L) != 0L && ((tempVal4 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal4 + 1L) & 31L)) & 21L) != 0L) {
                                        step = 49;
                                        continue stepLoop;
                                    } else {
                                        step = 53;
                                        continue stepLoop;
                                    }
                                } else {
                                    step = 52;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            if (((getI64(framePtr + 176L) & 0xffffffffL) == 2L || (getI64(framePtr + 176L) & 0xffffffffL) == 0xfffffffeL) && ((tempVal4 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal4 + 1L) & 31L)) & 21L) != 0L) {
                                step = 49;
                                continue stepLoop;
                            } else {
                                step = 53;
                                continue stepLoop;
                            }
                        }
                    }
                    case 51: {
                        putI64(framePtr + 176L, 0L);
                        putI64(framePtr + 184L, 0L);
                        step = 49;
                        continue stepLoop;
                    }
                    case 52: {
                        putI64(framePtr + 184L, getI64(framePtr + 200L));
                        putI64(framePtr + 176L, getI64(framePtr + 192L));
                        step = 49;
                        continue stepLoop;
                    }
                    case 53: {
                        if ((long) (int) (getI64(framePtr + 176L) ^ tempVal4) < 0L) {
                            step = 51;
                            continue stepLoop;
                        } else {
                            step = 52;
                            continue stepLoop;
                        }
                    }
                    case 54: {
                        if (((getI64(framePtr + 176L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 176L) + 1L) & 31L)) & 21L) != 0L) {
                            fradd(framePtr + 192L, framePtr + 176L, framePtr + 176L, 1L);
                            step = 49;
                            continue stepLoop;
                        } else {
                            if (tempVal4 != 0L && (getI64(framePtr + 176L) & 0xffffffffL) != 0L) {
                                step = 50;
                                continue stepLoop;
                            } else {
                                step = 51;
                                continue stepLoop;
                            }
                        }
                    }
                    case 55: {
                        putI64(framePtr + 128L, 0L);
                        putI64(framePtr + 136L, 0L);
                        putI64(outNumerPtr + 8L, getI64(framePtr + 184L));
                        putI64(outNumerPtr, getI64(framePtr + 176L));
                        bcdAcc1 = getI64(framePtr + 128L);
                        tempVal6 = getI64(framePtr + 136L);
                        putI64(outDenomPtr + 8L, tempVal6);
                        putI64(outDenomPtr, bcdAcc1);
                        return 0L;
                    }
                    case 56: {
                        if (exponent == 2L || exponent == 0xfffffffeL) {
                            if (5L < ((getI64(framePtr + 128L) + 2L) & 0xffffffffL)) {
                                step = 58;
                                continue stepLoop;
                            } else {
                                tempVal4 = (1L << (int) ((getI64(framePtr + 128L) + 2L) & 31L)) & 0xffffffffL;
                                if ((tempVal4 & 42L) == 0L) {
                                    if ((tempVal4 & 17L) != 0L && ((exponent + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((exponent + 1L) & 31L)) & 21L) != 0L) {
                                        step = 59;
                                        continue stepLoop;
                                    } else {
                                        step = 58;
                                        continue stepLoop;
                                    }
                                } else {
                                    step = 57;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            if (((getI64(framePtr + 128L) & 0xffffffffL) == 2L || (getI64(framePtr + 128L) & 0xffffffffL) == 0xfffffffeL) && ((exponent + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((exponent + 1L) & 31L)) & 21L) != 0L) {
                                step = 59;
                                continue stepLoop;
                            } else {
                                step = 58;
                                continue stepLoop;
                            }
                        }
                    }
                    case 57: {
                        putI64(framePtr + 136L, getI64(framePtr + 152L));
                        putI64(framePtr + 128L, getI64(framePtr + 144L));
                        putI64(outNumerPtr + 8L, getI64(framePtr + 184L));
                        putI64(outNumerPtr, getI64(framePtr + 176L));
                        bcdAcc1 = getI64(framePtr + 128L);
                        tempVal6 = getI64(framePtr + 136L);
                        putI64(outDenomPtr + 8L, tempVal6);
                        putI64(outDenomPtr, bcdAcc1);
                        return 0L;
                    }
                    case 58: {
                        if ((long) (int) (getI64(framePtr + 128L) ^ exponent) < 0L) {
                            step = 55;
                            continue stepLoop;
                        } else {
                            step = 57;
                            continue stepLoop;
                        }
                    }
                    case 59: {
                        putI64(outNumerPtr + 8L, getI64(framePtr + 184L));
                        putI64(outNumerPtr, getI64(framePtr + 176L));
                        bcdAcc1 = getI64(framePtr + 128L);
                        tempVal6 = getI64(framePtr + 136L);
                        putI64(outDenomPtr + 8L, tempVal6);
                        putI64(outDenomPtr, bcdAcc1);
                        return 0L;
                    }
                    case 60: {
                        if (((getI64(framePtr + 128L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 128L) + 1L) & 31L)) & 21L) != 0L) {
                            fradd(framePtr + 144L, framePtr + 128L, framePtr + 128L, 1L);
                            putI64(outNumerPtr + 8L, getI64(framePtr + 184L));
                            putI64(outNumerPtr, getI64(framePtr + 176L));
                            bcdAcc1 = getI64(framePtr + 128L);
                            tempVal6 = getI64(framePtr + 136L);
                            putI64(outDenomPtr + 8L, tempVal6);
                            putI64(outDenomPtr, bcdAcc1);
                            return 0L;
                        }
                        if (exponent != 0L && (getI64(framePtr + 128L) & 0xffffffffL) != 0L) {
                            step = 56;
                            continue stepLoop;
                        } else {
                            step = 55;
                            continue stepLoop;
                        }
                    }
                    case 61: {
                        putI64(framePtr + 232L, getI64(framePtr + 216L));
                        tempVal1 = getI64(framePtr + 208L);
                        putI64(framePtr + 168L, getI64(framePtr + 200L));
                        putI64(framePtr + 160L, getI64(framePtr + 192L));
                        putI64(framePtr + 200L, getI64(framePtr + 184L));
                        putI64(framePtr + 192L, getI64(framePtr + 176L));
                        putI64(framePtr + 216L, getI64(framePtr + 88L));
                        putI32(framePtr + 208L, getI32(framePtr + 80L));
                        putI32(framePtr + 212L, getI32(framePtr + 84L));
                        tempVal2 = getI64(framePtr + 208L);
                        putI64(framePtr + 120L, getI64(framePtr + 152L));
                        putI64(framePtr + 112L, getI64(framePtr + 144L));
                        putI64(framePtr + 152L, getI64(framePtr + 136L));
                        putI64(framePtr + 144L, getI64(framePtr + 128L));
                        putI32(framePtr + 208L, tempVal1);
                        putI32(framePtr + 212L, tempVal1 >>> 32);
                        putI32(framePtr + 224L, getI32(framePtr + 208L));
                        putI32(framePtr + 228L, getI32(framePtr + 212L));
                        putI64(framePtr + 208L, tempVal2);
                        fidiv(framePtr + 192L, framePtr + 144L, framePtr + 64L);
                        if (((getI64(framePtr + 64L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 64L) + 1L) & 31L)) & 21L) != 0L) {
                            fradd(framePtr + 256L, framePtr + 64L, framePtr + 64L, 1L);
                            step = 67;
                            continue stepLoop;
                        } else {
                            if ((getI64(framePtr + 64L) & 0xffffffffL) == 0L) {
                                step = 68;
                                continue stepLoop;
                            } else {
                                if ((getI64(framePtr + 64L) & 0xffffffffL) != 2L && (getI64(framePtr + 64L) & 0xffffffffL) != 0xfffffffeL) {
                                    if ((long) (int) (getI64(framePtr + 64L) ^ 1L) < 0L) {
                                        step = 68;
                                        continue stepLoop;
                                    } else {
                                        putI64(framePtr + 72L, getI64(framePtr + 264L));
                                        putI64(framePtr + 64L, getI64(framePtr + 256L));
                                        step = 67;
                                        continue stepLoop;
                                    }
                                } else {
                                    step = 67;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 62: {
                        if ((getI64(framePtr + 128L) & 0xffffffffL) == 2L || (getI64(framePtr + 128L) & 0xffffffffL) == 0xfffffffeL) {
                            if (((getI64(framePtr + 112L) + 2L) & 0xffffffffL) < 6L) {
                                exponent = (1L << (int) ((getI64(framePtr + 112L) + 2L) & 31L)) & 0xffffffffL;
                                if ((exponent & 42L) != 0L) {
                                    step = 61;
                                    continue stepLoop;
                                } else {
                                    if ((exponent & 17L) != 0L && ((getI64(framePtr + 128L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 128L) + 1L) & 31L)) & 21L) != 0L) {
                                        step = 64;
                                        continue stepLoop;
                                    } else {
                                        step = 63;
                                        continue stepLoop;
                                    }
                                }
                            } else {
                                step = 63;
                                continue stepLoop;
                            }
                        } else {
                            if (((getI64(framePtr + 112L) & 0xffffffffL) == 2L || (getI64(framePtr + 112L) & 0xffffffffL) == 0xfffffffeL) && ((getI64(framePtr + 128L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 128L) + 1L) & 31L)) & 21L) != 0L) {
                                step = 64;
                                continue stepLoop;
                            } else {
                                step = 63;
                                continue stepLoop;
                            }
                        }
                    }
                    case 63: {
                        if (-1L < (long) (int) (getI64(framePtr + 112L) ^ getI64(framePtr + 128L))) {
                            step = 61;
                            continue stepLoop;
                        } else {
                            step = 65;
                            continue stepLoop;
                        }
                    }
                    case 64: {
                        putI64(framePtr + 136L, getI64(framePtr + 120L));
                        putI64(framePtr + 128L, getI64(framePtr + 112L));
                        step = 61;
                        continue stepLoop;
                    }
                    case 65: {
                        putI64(framePtr + 128L, 0L);
                        putI64(framePtr + 136L, 0L);
                        step = 61;
                        continue stepLoop;
                    }
                    case 66: {
                        if (4L < ((getI64(framePtr + 112L) + 1L) & 0xffffffffL) || ((1L << (int) ((getI64(framePtr + 112L) + 1L) & 31L)) & 21L) == 0L) {
                            if ((getI64(framePtr + 128L) & 0xffffffffL) != 0L && (getI64(framePtr + 112L) & 0xffffffffL) != 0L) {
                                step = 62;
                                continue stepLoop;
                            } else {
                                step = 65;
                                continue stepLoop;
                            }
                        } else {
                            fradd(framePtr + 128L, framePtr + 112L, framePtr + 128L, 0L);
                            step = 61;
                            continue stepLoop;
                        }
                    }
                    case 67: {
                        if (getU32(framePtr + 252L) != 0L && (getI32(framePtr + 76L) == 0L || getI32(framePtr + 68L) < getI32(framePtr + 244L) || getI32(framePtr + 68L) == getI32(framePtr + 244L) && Long.compareUnsigned(getI64(framePtr + 72L), getI64(framePtr + 248L)) < 0L) || !(4L < ((getI32(framePtr + 80L) + 1L) & 0xffffffffL) || ((1L << (int) ((getI32(framePtr + 80L) + 1L) & 31L)) & 21L) == 0L || getI32(framePtr + 92L) != 0L)) {
                            if (getI32(framePtr + 96L) != -2L && getI32(framePtr + 96L) != 0L) {
                                if (getI32(framePtr + 96L) == 2L) {
                                    step = 69;
                                    continue stepLoop;
                                } else {
                                    intVal = getI32(framePtr + 96L);
                                    if (getI32(framePtr + 96L) == 3L) {
                                        intVal = 1L;
                                    }
                                    if (1L < intVal || intVal == 1L && getI32(framePtr + 108L) != 0L && (0L < getI32(framePtr + 100L) || getI32(framePtr + 100L) == 0L && Long.compareUnsigned(0x100000000000000L, getI64(framePtr + 104L)) < 0L)) {
                                        step = 69;
                                        continue stepLoop;
                                    } else {
                                        step = 59;
                                        continue stepLoop;
                                    }
                                }
                            } else {
                                step = 59;
                                continue stepLoop;
                            }
                        } else {
                            step = 17;
                            continue stepLoop;
                        }
                    }
                    case 68: {
                        putI64(framePtr + 64L, 0L);
                        putI64(framePtr + 72L, 0L);
                        step = 67;
                        continue stepLoop;
                    }
                    case 69: {
                        tempVal4 = getI64(framePtr + 192L) & 0xffffffffL;
                        exponent = getI64(framePtr + 144L) & 0xffffffffL;
                        tempVal5 = 1L;
                        putI64(framePtr + 40L, getI64(framePtr + 104L));
                        putI32(framePtr + 32L, getI32(framePtr + 96L));
                        putI32(framePtr + 36L, getI32(framePtr + 100L));
                        putI64(framePtr + 48L, longVal);
                        putI64(framePtr + 56L, tempVal6);
                        step = 48;
                        continue stepLoop;
                    }
                    case 70: {
                        putI64(framePtr + 16L, 0L);
                        putI64(framePtr + 24L, 0L);
                        step = 24;
                        continue stepLoop;
                    }
                    case 71: {
                        if (tempVal5 == 2L || tempVal5 == 0xfffffffeL) {
                            if (5L < ((getI64(framePtr + 32L) + 2L) & 0xffffffffL)) {
                                step = 73;
                                continue stepLoop;
                            } else {
                                tempVal3 = (1L << (int) ((getI64(framePtr + 32L) + 2L) & 31L)) & 0xffffffffL;
                                if ((tempVal3 & 42L) == 0L) {
                                    if ((tempVal3 & 17L) != 0L && ((tempVal5 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal5 + 1L) & 31L)) & 21L) != 0L) {
                                        step = 74;
                                        continue stepLoop;
                                    } else {
                                        step = 73;
                                        continue stepLoop;
                                    }
                                } else {
                                    step = 72;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            if (((getI64(framePtr + 32L) & 0xffffffffL) == 2L || (getI64(framePtr + 32L) & 0xffffffffL) == 0xfffffffeL) && ((tempVal5 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal5 + 1L) & 31L)) & 21L) != 0L) {
                                step = 74;
                                continue stepLoop;
                            } else {
                                step = 73;
                                continue stepLoop;
                            }
                        }
                    }
                    case 72: {
                        putI64(framePtr + 24L, getI64(framePtr + 56L));
                        putI64(framePtr + 16L, getI64(framePtr + 48L));
                        step = 24;
                        continue stepLoop;
                    }
                    case 73: {
                        if ((long) (int) (getI64(framePtr + 32L) ^ tempVal5) < 0L) {
                            step = 70;
                            continue stepLoop;
                        } else {
                            step = 72;
                            continue stepLoop;
                        }
                    }
                    case 74: {
                        putI64(framePtr + 24L, getI64(framePtr + 40L));
                        putI64(framePtr + 16L, getI64(framePtr + 32L));
                        step = 24;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long dcbAddAdjust(long bcdA, long bcdB) {
        long tempVal = 0L;
        tempVal = bcdA + 0x6666666666666666L + bcdB;
        return tempVal + ((((((bcdA + 0x6666666666666666L) ^ bcdB ^ tempVal)) >>> 4) & 0x1111111111111111L) ^ 0x1111111111111111L) * -6L;
    }

    long dcbSubAdjust(long bcdSubtrahend, long bcdMinuend) {
        return bcdMinuend - bcdSubtrahend + ((((((bcdSubtrahend ^ (bcdMinuend + 1L) ^ (bcdMinuend - bcdSubtrahend) ^ -1L)) >>> 4) ^ -1L)) & 0x111111111111111L) * -6L;
    }

    long dcbShiftRound(long bcdMantissa, long shiftDigits) {
        long result = 0L;
        long index = 0L;
        result = bcdMantissa;
        if (!((long) (int) shiftDigits != 0L && comma(comma(index = bcdMantissa >>> (int) ((shiftDigits * 4L) & 63L), result = index), Long.compareUnsigned(4L, (bcdMantissa >>> (int) ((shiftDigits * 4L - 4L) & 63L)) & 15L) < 0L ? 1L : 0L) != 0L && comma(result = index + 1L, ((index + 1L) & 15L) == 10L ? 1L : 0L) != 0L && comma(result = index + 7L, ((index + 7L) & 240L) == 160L && comma(result = index + 103L, ((index + 103L) & 3840L) == 2560L ? 1L : 0L) != 0L ? 1L : 0L) != 0L && comma(result = index + 1639L, ((index + 1639L) & 61440L) == 40960L && comma(result = index + 26215L, ((index + 26215L) & 0xf0000L) == 0xa0000L && comma(result = index + 0x66667L, ((index + 0x66667L) & 0xf00000L) == 0xa00000L ? 1L : 0L) != 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L && comma(result = index + 0x666667L, ((index + 0x666667L) & 0xf000000L) == 0xa000000L && comma(result = index + 0x6666667L, ((index + 0x6666667L) & 0xf0000000L) == 0xa0000000L && comma(result = index + 0x66666667L, ((index + 0x66666667L) & 0xf00000000L) == 0xa00000000L ? 1L : 0L) != 0L ? 1L : 0L) != 0L && comma(result = index + 0x666666667L, ((index + 0x666666667L) & 0xf000000000L) == 0xa000000000L ? 1L : 0L) != 0L && comma(result = index + 0x6666666667L, ((index + 0x6666666667L) & 0xf0000000000L) == 0xa0000000000L && comma(result = index + 0x66666666667L, ((index + 0x66666666667L) & 0xf00000000000L) == 0xa00000000000L ? 1L : 0L) != 0L ? 1L : 0L) != 0L && comma(result = index + 0x666666666667L, ((index + 0x666666666667L) & 0xf000000000000L) == 0xa000000000000L && comma(result = index + 0x6666666666667L, ((index + 0x6666666666667L) & 0xf0000000000000L) == 0xa0000000000000L ? 1L : 0L) != 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L)) {
            return result;
        }
        result = index + 0x666666666666667L;
        if (((index + 0x66666666666667L) & 0xf00000000000000L) != 0xa00000000000000L) {
            result = index + 0x66666666666667L;
        }
        return result;
    }

    long dcbShiftRound2(long bcdMantissa, long shiftDigits) {
        long result = 0L;
        if ((long) (int) shiftDigits == 0L) {
            return bcdMantissa;
        }
        result = bcdMantissa >>> (int) ((shiftDigits << 2) & 63L);
        if ((bcdMantissa & (((-1L << (int) ((shiftDigits << 2) & 63L)) ^ -1L))) == 0L) {
            return result;
        }
        bcdMantissa = result + 1L;
        if (!(((result + 1L) & 15L) == 10L && comma(bcdMantissa = result + 7L, ((result + 7L) & 240L) == 160L ? 1L : 0L) != 0L && comma(bcdMantissa = result + 103L, ((result + 103L) & 3840L) == 2560L ? 1L : 0L) != 0L && comma(bcdMantissa = result + 1639L, ((result + 1639L) & 61440L) == 40960L && comma(bcdMantissa = result + 26215L, ((result + 26215L) & 0xf0000L) == 0xa0000L ? 1L : 0L) != 0L ? 1L : 0L) != 0L && comma(bcdMantissa = result + 0x66667L, ((result + 0x66667L) & 0xf00000L) == 0xa00000L && comma(bcdMantissa = result + 0x666667L, ((result + 0x666667L) & 0xf000000L) == 0xa000000L && comma(bcdMantissa = result + 0x6666667L, ((result + 0x6666667L) & 0xf0000000L) == 0xa0000000L ? 1L : 0L) != 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L && comma(bcdMantissa = result + 0x66666667L, ((result + 0x66666667L) & 0xf00000000L) == 0xa00000000L ? 1L : 0L) != 0L && comma(bcdMantissa = result + 0x666666667L, ((result + 0x666666667L) & 0xf000000000L) == 0xa000000000L && comma(bcdMantissa = result + 0x6666666667L, ((result + 0x6666666667L) & 0xf0000000000L) == 0xa0000000000L ? 1L : 0L) != 0L ? 1L : 0L) != 0L && comma(bcdMantissa = result + 0x66666666667L, ((result + 0x66666666667L) & 0xf00000000000L) == 0xa00000000000L ? 1L : 0L) != 0L && comma(bcdMantissa = result + 0x666666666667L, ((result + 0x666666666667L) & 0xf000000000000L) == 0xa000000000000L && comma(bcdMantissa = result + 0x6666666666667L, ((result + 0x6666666666667L) & 0xf0000000000000L) == 0xa0000000000000L ? 1L : 0L) != 0L ? 1L : 0L) != 0L)) {
            return bcdMantissa;
        }
        bcdMantissa = result + 0x666666666666667L;
        if (((result + 0x66666666666667L) & 0xf00000000000000L) != 0xa00000000000000L) {
            bcdMantissa = result + 0x66666666666667L;
        }
        return bcdMantissa;
    }

    long dcbsub128(long lhs128Ptr, long rhs128Ptr) {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        long tempVal5 = 0L;
        long tempVal6 = 0L;
        long tempVal7 = 0L;
        long tempVal8 = 0L;
        long tempVal9 = 0L;
        long tempVal10 = 0L;
        long tempVal11 = 0L;
        long tempVal12 = 0L;
        long tempVal13 = 0L;
        long tempVal14 = 0L;
        long tempVal15 = 0L;
        long tempVal16 = 0L;
        long offset = 0L;
        long longVal = 0L;
        long intVal1 = 0L;
        long intVal2 = 0L;
        long intVal3 = 0L;
        long intVal4 = 0L;
        long intVal5 = 0L;
        long intVal6 = 0L;
        long intVal7 = 0L;
        long intVal8 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            longVal = 0L;
            offset = 0L;
            do {
                tempVal16 = getU32(rhs128Ptr + longVal);
                offset = (getI32(lhs128Ptr + longVal) + offset) & 0xffffffffL;
                tempVal1 = tempVal16 & 15L;
                tempVal2 = offset & 15L;
                intVal1 = 10L;
                if (tempVal1 >= tempVal2) {
                    intVal1 = 0L;
                }
                offset = (offset + (tempVal1 < tempVal2 ? 1L : 0L) * 16L) & 0xffffffffL;
                tempVal3 = tempVal16 & 240L;
                tempVal4 = offset & 240L;
                intVal2 = 160L;
                if (tempVal3 >= tempVal4) {
                    intVal2 = 0L;
                }
                offset = (offset + (tempVal3 < tempVal4 ? 1L : 0L) * 256L) & 0xffffffffL;
                tempVal5 = tempVal16 & 3840L;
                tempVal6 = offset & 3840L;
                intVal3 = 2560L;
                if (tempVal5 >= tempVal6) {
                    intVal3 = 0L;
                }
                offset = (offset + (tempVal5 < tempVal6 ? 1L : 0L) * 4096L) & 0xffffffffL;
                tempVal7 = tempVal16 & 61440L;
                tempVal8 = offset & 61440L;
                intVal4 = 40960L;
                if (tempVal7 >= tempVal8) {
                    intVal4 = 0L;
                }
                offset = (offset + (tempVal7 < tempVal8 ? 1L : 0L) * 0x10000L) & 0xffffffffL;
                tempVal9 = tempVal16 & 0xf0000L;
                tempVal10 = offset & 0xf0000L;
                intVal5 = 0xa0000L;
                if (tempVal9 >= tempVal10) {
                    intVal5 = 0L;
                }
                offset = (offset + (tempVal9 < tempVal10 ? 1L : 0L) * 0x100000L) & 0xffffffffL;
                tempVal11 = tempVal16 & 0xf00000L;
                tempVal12 = offset & 0xf00000L;
                intVal6 = 0xa00000L;
                if (tempVal11 >= tempVal12) {
                    intVal6 = 0L;
                }
                offset = (offset + (tempVal11 < tempVal12 ? 1L : 0L) * 0x1000000L) & 0xffffffffL;
                tempVal13 = tempVal16 & 0xf000000L;
                tempVal14 = offset & 0xf000000L;
                intVal7 = 0xa000000L;
                if (tempVal13 >= tempVal14) {
                    intVal7 = 0L;
                }
                tempVal16 = tempVal16 & 0xf0000000L;
                tempVal15 = (offset + (tempVal13 < tempVal14 ? 1L : 0L) * 0x10000000L) & 0xf0000000L;
                intVal8 = 0xffffffffa0000000L;
                if ((tempVal16 & 0xffffffffL) >= (tempVal15 & 0xffffffffL)) {
                    intVal8 = 0L;
                }
                putU32(framePtr + 16L + longVal, tempVal16 + (tempVal13 + (tempVal11 + (tempVal9 + (tempVal7 + (tempVal5 + (tempVal3 + (tempVal1 + intVal1 - tempVal2) + intVal2 - tempVal4) + intVal3 - tempVal6) + intVal4 - tempVal8) + intVal5 - tempVal10) + intVal6 - tempVal12) + intVal7 - tempVal14) + intVal8 - tempVal15);
                longVal = longVal + 4L;
                offset = (tempVal16 & 0xffffffffL) < (tempVal15 & 0xffffffffL) ? 1L : 0L;
            } while (longVal != 16L);
            if ((tempVal15 & 0xffffffffL) <= (tempVal16 & 0xffffffffL)) {
                putI64(rhs128Ptr + 8L, getI64(framePtr + 24L));
                putI64(rhs128Ptr, getI64(framePtr + 16L));
            }
            return (tempVal16 & 0xffffffffL) < (tempVal15 & 0xffffffffL) ? 1L : 0L;
        } finally {
            sp = savedSp;
        }
    }

    void add128(long lhs128Ptr, long rhs128Ptr, long result128Ptr) {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        tempVal1 = getI64(lhs128Ptr);
        tempVal2 = getI64(rhs128Ptr);
        putI64(result128Ptr, tempVal2 + tempVal1);
        putI64(result128Ptr + 8L, getI64(rhs128Ptr + 8L) + getI64(lhs128Ptr + 8L) != -(Long.compareUnsigned(tempVal2, -1L - tempVal1) > 0L ? 1L : 0L) ? 1L : 0L);
    }

    void sub128(long lhs128Ptr, long rhs128Ptr, long result128Ptr) {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        tempVal1 = getI64(lhs128Ptr);
        tempVal2 = getI64(rhs128Ptr);
        putI64(result128Ptr, tempVal1 - tempVal2);
        putI64(result128Ptr + 8L, getI64(lhs128Ptr + 8L) - getI64(rhs128Ptr + 8L) != (Long.compareUnsigned(tempVal1, tempVal2) < 0L ? 1L : 0L) ? 1L : 0L);
    }

    long LE128(long lhs128Ptr, long rhs128Ptr) {
        if (Long.compareUnsigned(getI64(lhs128Ptr + 8L), getI64(rhs128Ptr + 8L)) < 0L) {
            return 1L;
        }
        if (getI64(lhs128Ptr + 8L) != getI64(rhs128Ptr + 8L)) {
            return 0L;
        } else {
            return Long.compareUnsigned(getI64(lhs128Ptr), getI64(rhs128Ptr)) <= 0L ? 1L : 0L;
        }
    }

    long LT128(long lhs128Ptr, long rhs128Ptr) {
        if (Long.compareUnsigned(getI64(lhs128Ptr + 8L), getI64(rhs128Ptr + 8L)) < 0L) {
            return 1L;
        }
        if (getI64(lhs128Ptr + 8L) != getI64(rhs128Ptr + 8L)) {
            return 0L;
        } else {
            return Long.compareUnsigned(getI64(lhs128Ptr), getI64(rhs128Ptr)) < 0L ? 1L : 0L;
        }
    }

    void divmod128(long dividend128Ptr, long divisor128Ptr, long quotient128Ptr, long remainder128Ptr) {
        long tempVal1 = 0L;
        long mantissa1 = 0L;
        long index = 0L;
        long tempVal2 = 0L;
        long mantissa2 = 0L;
        long tempVal3 = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    tempVal1 = getI64(divisor128Ptr);
                    mantissa1 = getI64(divisor128Ptr + 8L);
                    if (!(tempVal1 != 0L || mantissa1 != 0L)) {
                        return;
                    }
                    putI64(remainder128Ptr, getI64(dividend128Ptr));
                    putI64(remainder128Ptr + 8L, getI64(dividend128Ptr + 8L));
                    putI64(quotient128Ptr, 0L);
                    putI64(quotient128Ptr + 8L, 0L);
                    index = 1L;
                    while (Long.compareUnsigned(mantissa1, getI64(remainder128Ptr + 8L)) < 0L || Long.compareUnsigned(mantissa1, getI64(remainder128Ptr + 8L)) <= 0L && Long.compareUnsigned(tempVal1, getI64(remainder128Ptr)) < 0L) {
                        mantissa1 = (tempVal1 >>> 31) + mantissa1 * 2L;
                        index = (long) (int) (index - 1L);
                        tempVal1 = tempVal1 << 1;
                    }
                    if (index == 1L) {
                        return;
                    }
                    tempVal2 = 0L;
                    mantissa2 = 0L;
                    step = 1;
                    continue stepLoop;
                }
                case 1: {
                    tempVal3 = tempVal2 >>> 31;
                    tempVal2 = tempVal2 << 1;
                    putI64(quotient128Ptr, tempVal2);
                    putI64(quotient128Ptr + 8L, tempVal3 + mantissa2 * 2L);
                    mantissa2 = getI64(remainder128Ptr + 8L);
                    if (Long.compareUnsigned(mantissa1, mantissa2) < 0L) {
                        tempVal3 = getI64(remainder128Ptr);
                        step = 3;
                        continue stepLoop;
                    } else {
                        if (Long.compareUnsigned(mantissa1, mantissa2) <= 0L && comma(tempVal3 = getI64(remainder128Ptr), Long.compareUnsigned(tempVal1, tempVal3) <= 0L ? 1L : 0L) != 0L) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                }
                case 2: {
                    if (index == 0L) {
                        return;
                    }
                    mantissa2 = getI64(quotient128Ptr + 8L);
                    tempVal1 = (tempVal1 >>> 1) | (mantissa1 << 63);
                    mantissa1 = mantissa1 >>> 1;
                    index = (long) (int) (index + 1L);
                    step = 1;
                    continue stepLoop;
                }
                case 3: {
                    putI64(remainder128Ptr, tempVal3 - tempVal1);
                    putI64(remainder128Ptr + 8L, mantissa2 - mantissa1 != (Long.compareUnsigned(tempVal3, tempVal1) < 0L ? 1L : 0L) ? 1L : 0L);
                    tempVal2 = getI64(quotient128Ptr) | 1L;
                    putI64(quotient128Ptr, tempVal2);
                    step = 2;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    void mul128(long lhs64, long rhs64, long result128Ptr) {
        long exponent1 = 0L;
        long exponent2 = 0L;
        long tempVal = 0L;
        long exponent3 = 0L;
        long exponent4 = 0L;
        long intVal1 = 0L;
        long intVal2 = 0L;
        putI64(result128Ptr, 0L);
        putI64(result128Ptr + 8L, 0L);
        tempVal = getU32(lhs64);
        exponent3 = getU32(rhs64);
        putI32(result128Ptr, exponent3 * tempVal);
        exponent1 = getU32(rhs64) * getU32(lhs64 + 4L) + (((exponent3 & 0xffffffffL) * (tempVal & 0xffffffffL)) >>> 32);
        intVal1 = (long) (int) exponent1;
        putI32(result128Ptr + 4L, intVal1);
        exponent2 = getU32(rhs64) * getU32(lhs64 + 8L) + (exponent1 >>> 32);
        intVal2 = (long) (int) exponent2;
        putI32(result128Ptr + 8L, intVal2);
        exponent4 = (long) (int) ((exponent2 >>> 32) + getU32(rhs64) * getU32(lhs64 + 12L));
        putI32(result128Ptr + 12L, exponent4);
        tempVal = getU32(lhs64);
        exponent3 = getU32(rhs64 + 4L);
        putI32(result128Ptr + 4L, intVal1 + exponent3 * tempVal);
        exponent2 = getU32(rhs64 + 4L) * getU32(lhs64 + 4L) + (((exponent3 & 0xffffffffL) * (tempVal & 0xffffffffL)) >>> 32);
        intVal2 = (long) (int) (intVal2 + exponent2);
        putI32(result128Ptr + 8L, intVal2);
        exponent4 = (long) (int) (exponent4 + (exponent2 >>> 32) + getU32(rhs64 + 4L) * getU32(lhs64 + 8L));
        putI32(result128Ptr + 12L, exponent4);
        tempVal = getU32(lhs64);
        exponent3 = getU32(rhs64 + 8L);
        putI32(result128Ptr + 8L, intVal2 + exponent3 * tempVal);
        exponent4 = (long) (int) (exponent4 + (((exponent3 & 0xffffffffL) * (tempVal & 0xffffffffL)) >>> 32) + getU32(rhs64 + 8L) * getU32(lhs64 + 4L));
        putI32(result128Ptr + 12L, exponent4);
        putU32(result128Ptr + 12L, exponent4 + getU32(rhs64 + 12L) * getU32(lhs64));
    }

    long divLoop(long remPtr, long divisorMantissa) {
        long index1 = 0L;
        long index2 = 0L;
        long index3 = 0L;
        index2 = getI64(remPtr);
        index1 = 0L;
        index3 = 15L;
        do {
            index1 = index1 << 4;
            while (Long.compareUnsigned(divisorMantissa, index2) <= 0L) {
                index1 = index1 + 1L;
                index2 = index2 - divisorMantissa + ((((((divisorMantissa ^ 0x1111111111111110L ^ (index2 + 1L) ^ (index2 - divisorMantissa))) >>> 4) ^ -1L)) & 0x111111111111111L) * -6L;
            }
            index3 = (long) (int) (index3 - 1L);
            index2 = index2 << 4;
        } while (index3 != 0L);
        putI64(remPtr, index2);
        return index1;
    }

    void fiMul100(long realPtr) {
        long exponent = 0L;
        long isZero = 0L;
        if (((getI32(realPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI32(realPtr) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(realPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(exponent = getI32(realPtr + 4L), putI32(realPtr + 4L, exponent + 2L)), 49997L < exponent ? 1L : 0L) != 0L) {
            putI32(realPtr + 8L, 0xffffffff99999999L);
            putI32(realPtr + 12L, 0x9999999L);
            putI32(realPtr, 2L);
            putI32(realPtr + 4L, 49999L);
        }
    }

    void fiDiv100(long realPtr) {
        long exponent = 0L;
        long isZero = 0L;
        if (((getI32(realPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI32(realPtr) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(realPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(exponent = getI32(realPtr + 4L), putI32(realPtr + 4L, exponent - 2L)), exponent < -49997L ? 1L : 0L) != 0L) {
            putI32(realPtr + 8L, 0L);
            putI32(realPtr + 12L, 0L);
            putI32(realPtr, 1L);
            putI32(realPtr + 4L, 0L);
        }
    }

    long inttostr_t(long intVal, long outStr) {
        long bytePtr = 0L;
        long flag = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    tempVal2 = ((((((intVal & 0xffffffffL)) >> 9) & 0xffffffffL) * 0x44b83L) >>> 39) & 0xffffffffL;
                    tempVal1 = (intVal + tempVal2 * 0xc4653600L) & 0xffffffffL;
                    if ((intVal & 0xffffffffL) < 0x3b9aca00L) {
                        bytePtr = outStr;
                        if (0x5f5e0ffL < tempVal1) {
                            step = 1;
                            continue stepLoop;
                        } else {
                            tempVal2 = (((tempVal1 % 0x5f5e100L) & 0xffffffffL) % 0x989680L) & 0xffffffffL;
                            if (0x98967fL < ((tempVal1 % 0x5f5e100L) & 0xffffffffL)) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                tempVal1 = (tempVal2 % 0xf4240L) & 0xffffffffL;
                                if (0xf423fL < tempVal2) {
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    tempVal2 = (tempVal1 % 0x186a0L) & 0xffffffffL;
                                    if (0x1869fL < tempVal1) {
                                        step = 4;
                                        continue stepLoop;
                                    } else {
                                        tempVal1 = (tempVal2 % 10000L) & 0xffffffffL;
                                        if (624L < ((tempVal2 >> 4) & 0xffffffffL)) {
                                            step = 5;
                                            continue stepLoop;
                                        } else {
                                            tempVal2 = (tempVal1 % 1000L) & 0xffffffffL;
                                            if (999L < tempVal1) {
                                                step = 6;
                                                continue stepLoop;
                                            } else {
                                                tempVal1 = (tempVal2 % 100L) & 0xffffffffL;
                                                if (99L >= tempVal2) {
                                                    flag = (tempVal1 % 10L) & 0xffL;
                                                    if (9L < tempVal1) {
                                                        putI8(outStr, (tempVal1 / 10L) | 48L);
                                                        bytePtr = outStr + 1L;
                                                    } else {
                                                        if (((tempVal1 % 10L) & 0xffffffffL) == 0L) {
                                                            putI8(outStr, 32L);
                                                            return outStr;
                                                        }
                                                    }
                                                    outStr = bytePtr + 1L;
                                                    putU8(bytePtr, flag | 48L);
                                                    putI8(outStr, 32L);
                                                    return outStr;
                                                }
                                                step = 7;
                                                continue stepLoop;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        putI8(outStr, tempVal2 | 48L);
                        bytePtr = outStr + 1L;
                        step = 1;
                        continue stepLoop;
                    }
                }
                case 1: {
                    outStr = bytePtr + 1L;
                    putU8(bytePtr, (tempVal1 / 0x5f5e100L) | 48L);
                    tempVal2 = (((tempVal1 % 0x5f5e100L) & 0xffffffffL) % 0x989680L) & 0xffffffffL;
                    step = 2;
                    continue stepLoop;
                }
                case 2: {
                    putI8(outStr, (((tempVal1 % 0x5f5e100L) & 0xffffffffL) / 0x989680L) | 48L);
                    tempVal1 = (tempVal2 % 0xf4240L) & 0xffffffffL;
                    bytePtr = outStr + 1L;
                    step = 3;
                    continue stepLoop;
                }
                case 3: {
                    outStr = bytePtr + 1L;
                    putU8(bytePtr, tempVal2 / 0xf4240L + 48L);
                    tempVal2 = (tempVal1 % 0x186a0L) & 0xffffffffL;
                    step = 4;
                    continue stepLoop;
                }
                case 4: {
                    putI8(outStr, (tempVal1 / 0x186a0L) | 48L);
                    tempVal1 = (tempVal2 % 10000L) & 0xffffffffL;
                    outStr = outStr + 1L;
                    step = 5;
                    continue stepLoop;
                }
                case 5: {
                    putI8(outStr, (tempVal2 / 10000L) | 48L);
                    tempVal2 = (tempVal1 % 1000L) & 0xffffffffL;
                    outStr = outStr + 1L;
                    step = 6;
                    continue stepLoop;
                }
                case 6: {
                    putI8(outStr, tempVal1 / 1000L + 48L);
                    tempVal1 = (tempVal2 % 100L) & 0xffffffffL;
                    bytePtr = outStr + 1L;
                    step = 7;
                    continue stepLoop;
                }
                case 7: {
                    outStr = bytePtr + 1L;
                    putU8(bytePtr, (tempVal2 / 100L) | 48L);
                    flag = (tempVal1 % 10L) & 0xffL;
                    putI8(outStr, (tempVal1 / 10L) | 48L);
                    bytePtr = outStr + 1L;
                    outStr = bytePtr + 1L;
                    putU8(bytePtr, flag | 48L);
                    putI8(outStr, 32L);
                    return outStr;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long testfCCToString(long realPtr, long outStr, long decimalChar, long thousandChar, long formatMode, long sigDigits, long displayDigits, long dateMode, long expectedStr) {
        long sigDigits1 = 0L;
        long tempVal1 = 0L;
        long strPtr1 = 0L;
        long tempVal2 = 0L;
        long strPtr2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            tempVal2 = getU32(realPtr);
            if (tempVal2 == 3L) {
                tempVal1 = DateValidExtract(realPtr, framePtr + 24L, framePtr + 52L, framePtr + 48L, framePtr + 44L, dateMode & 1L, 0L);
                if ((tempVal1 & 1L) != 0L) {
                    tempVal2 = getU32(framePtr + 52L);
                    if ((dateMode & 1L) == 0L) {
                        tempVal2 = getU32(framePtr + 24L);
                        putU32(framePtr + 24L, getU32(framePtr + 52L));
                    }
                    strPtr1 = inttostr_t(getU32(framePtr + 24L), outStr);
                    strPtr1 = inttostr_t(tempVal2, strPtr1 + 1L);
                    strPtr1 = inttostr_t(getU32(framePtr + 48L), strPtr1 + 1L);
                    putU8(strPtr1, 0L);
                    return outStr;
                }
                tempVal2 = getU32(realPtr);
            }
            if (tempVal2 == 0xfffffffeL) {
                putU8(outStr + 4L, 0L);
                putU8(outStr, 45L);
                putU8(outStr + 1L, 73L);
                putU8(outStr + 2L, 110L);
                putU8(outStr + 3L, 102L);
            } else {
                if (tempVal2 == 2L) {
                    putU8(outStr, 73L);
                    putU8(outStr + 1L, 110L);
                    putU8(outStr + 2L, 102L);
                    putU8(outStr + 3L, 0L);
                } else {
                    if (tempVal2 == 0L) {
                        putU8(outStr, 78L);
                        putU8(outStr + 1L, 97L);
                        putU8(outStr + 2L, 78L);
                        putU8(outStr + 3L, 0L);
                    } else {
                        firnd(realPtr, (long) (int) displayDigits, framePtr + 24L, 1L);
                        sigDigits1 = (long) (int) fNbSigDigits(realPtr);
                        if (sigDigits1 <= (long) (int) displayDigits) {
                            displayDigits = sigDigits1;
                        }
                        strPtr1 = outStr;
                        if ((long) (int) getU32(realPtr) < 0L) {
                            strPtr1 = outStr + 1L;
                            putU8(outStr, 45L);
                        }
                        tempVal1 = getI64(framePtr + 32L) >>> 56;
                        putI64(framePtr + 32L, getI64(framePtr + 32L) << 4);
                        strPtr2 = strPtr1 + 1L;
                        putU8(strPtr1, (tempVal1 & 15L) | 48L);
                        if ((long) (int) displayDigits != 1L) {
                            putU8(strPtr1 + 1L, decimalChar);
                            strPtr2 = strPtr1 + 2L;
                            while (1L < (long) (int) displayDigits) {
                                putU8(strPtr2, ((getI64(framePtr + 32L) >>> 56) & 15L) | 48L);
                                putI64(framePtr + 32L, getI64(framePtr + 32L) << 4);
                                strPtr2 = strPtr2 + 1L;
                                displayDigits = (long) (int) (displayDigits - 1L);
                            }
                        }
                        if (getU32(framePtr + 28L) != 0L) {
                            strPtr1 = strPtr2 + 1L;
                            putU8(strPtr2, 69L);
                            if ((long) (int) getU32(framePtr + 28L) < 0L) {
                                strPtr1 = strPtr2 + 2L;
                                putU32(framePtr + 28L, -getU32(framePtr + 28L));
                                putU8(strPtr2 + 1L, 45L);
                            }
                            strPtr2 = inttostr_t(getU32(framePtr + 28L), strPtr1);
                        }
                        putU8(strPtr2, 0L);
                    }
                }
            }
            return outStr;
        } finally {
            sp = savedSp;
        }
    }

}

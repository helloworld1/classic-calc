package org.liberty.android.classiccalc.engine;

/**
 * Probability distributions (Normal, Student-t, Chi-square, F-Snedecor, Gamma, Beta, Binomial, Poisson).
 */
abstract class ProbabilityMathEngine extends ScientificMathEngine {
    long fiUTPN(long xPtr, long meanPtr, long stdDevPtr, long resultPtr) {
        long tempVal1 = 0L;
        long intPtr = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long constPtr1 = 0L;
        long constPtr2 = 0L;
        long mantissa = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(368);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        putI64(resultPtr + 8L, 0L);
                        putI64(resultPtr, 0L);
                        if (getU32(xPtr) == 0L || getU32(meanPtr) == 0L || getU32(stdDevPtr) == 0L) {
                            status1 = 0L;
                            return status1;
                        }
                        if ((long) (int) getU32(meanPtr) < 0L) {
                            status1 = 24L;
                            return status1;
                        }
                        isZero = fZero(meanPtr) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            isZero = fLT(xPtr, stdDevPtr) != 0L ? 1L : 0L;
                            if (isZero == 0L) {
                                step = 1;
                                continue stepLoop;
                            } else {
                                step = 2;
                                continue stepLoop;
                            }
                        } else {
                            if (getU32(meanPtr) == 2L) {
                                if (4L < ((getU32(stdDevPtr) + 1L) & 0xffffffffL) || ((1L << (int) ((getU32(stdDevPtr) + 1L) & 31L)) & 21L) == 0L || 4L < ((getU32(xPtr) + 1L) & 0xffffffffL) || ((1L << (int) ((getU32(xPtr) + 1L) & 31L)) & 21L) == 0L) {
                                    mantissa = 0L;
                                    status2 = 0L;
                                    status1 = 0L;
                                    putI64(resultPtr + 8L, mantissa);
                                    putI64(resultPtr, status2);
                                    return status1;
                                }
                                constPtr2 = CC_0p5;
                                mantissa = getI64(constPtr2 + 8L);
                                status2 = getI64(constPtr2);
                                status1 = 0L;
                                putI64(resultPtr + 8L, mantissa);
                                putI64(resultPtr, status2);
                                return status1;
                            }
                            tempVal2 = getU32(xPtr);
                            tempVal3 = getU32(stdDevPtr);
                            tempVal1 = (tempVal2 + 1L) & 0xffffffffL;
                            isZero = tempVal1 & 1L;
                            if (((tempVal3 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((tempVal3 + 1L) & 31L)) & 21L) != 0L) {
                                if (!(4L < tempVal1 || isZero != 0L)) {
                                    fisqrt(meanPtr, resultPtr);
                                    fradd(stdDevPtr, xPtr, framePtr + 176L, 1L);
                                    fdiv(framePtr + 176L, resultPtr, framePtr + 176L);
                                    isZero = fEQ(framePtr + 176L, CC_0) != 0L ? 1L : 0L;
                                    tempVal1 = getU32(framePtr + 176L);
                                    if (isZero == 0L) {
                                        fmul(framePtr + 176L, framePtr + 176L, framePtr + 160L);
                                        if (getU32(framePtr + 176L) == 3L) {
                                            putU32(framePtr + 176L, 1L);
                                        } else {
                                            if ((long) (int) getU32(framePtr + 176L) < 0L) {
                                                putU32(framePtr + 176L, -getU32(framePtr + 176L));
                                            }
                                        }
                                        fidiv2(framePtr + 160L, framePtr + 144L);
                                        isZero = fZero(framePtr + 144L) != 0L ? 1L : 0L;
                                        if (isZero == 0L) {
                                            putI32(framePtr + 144L, -getI64(framePtr + 144L));
                                        }
                                        fiexpm(framePtr + 144L, framePtr + 144L, 0L);
                                        fimul(framePtr + 144L, CC_0p398942280401432, framePtr + 144L);
                                        intPtr = CC_2p32;
                                        if ((long) (int) tempVal1 < 1L) {
                                            intPtr = CC_3p5;
                                        }
                                        isZero = fLT(framePtr + 176L, intPtr) != 0L ? 1L : 0L;
                                        if (isZero != 0L) {
                                            putI64(framePtr + 136L, 0x100000000000000L);
                                            putI64(framePtr + 128L, 1L);
                                            fimul(framePtr + 144L, framePtr + 176L, framePtr + 96L);
                                            putI64(framePtr + 88L, getI64(framePtr + 104L));
                                            putI64(framePtr + 80L, getI64(framePtr + 96L));
                                            fimul(framePtr + 176L, framePtr + 176L, framePtr + 64L);
                                            do {
                                                firadd(framePtr + 128L, CC_2, framePtr + 128L, 0L);
                                                fidiv(framePtr + 80L, framePtr + 128L, framePtr + 80L);
                                                fimul(framePtr + 80L, framePtr + 64L, framePtr + 80L);
                                                putI64(framePtr + 120L, getI64(framePtr + 104L));
                                                putI64(framePtr + 112L, getI64(framePtr + 96L));
                                                firadd(framePtr + 96L, framePtr + 80L, framePtr + 96L, 0L);
                                                isZero = fEQ(framePtr + 96L, framePtr + 112L) != 0L ? 1L : 0L;
                                            } while (isZero == 0L);
                                            if ((long) (int) tempVal1 < 1L) {
                                                constPtr1 = CC_0p5;
                                                constPtr2 = framePtr + 96L;
                                            } else {
                                                putI64(framePtr + 192L, getI64(framePtr + 96L));
                                                putI64(framePtr + 200L, getI64(framePtr + 104L));
                                                if ((long) (int) getI64(framePtr + 96L) == 3L) {
                                                    putI32(framePtr + 196L, getI64(framePtr + 96L) >>> 32);
                                                    putI64(framePtr + 192L, (((getI32(framePtr + 196L) & 0xffffffffL)) << 32) | 1L);
                                                }
                                                isZero = fZero(framePtr + 192L) != 0L ? 1L : 0L;
                                                if (isZero == 0L) {
                                                    putI64(framePtr + 192L, (((getI32(framePtr + 196L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 192L) & 0xffffffffL));
                                                }
                                                constPtr2 = CC_0p5;
                                                constPtr1 = framePtr + 192L;
                                            }
                                            status2 = firadd(constPtr2, constPtr1, resultPtr, 0L);
                                            status1 = (long) (int) status2;
                                        } else {
                                            putI64(framePtr + 104L, 0L);
                                            putI64(framePtr + 96L, 1L);
                                            putI64(framePtr + 80L, (((getI32(framePtr + 148L) & 0xffffffffL)) << 32) | (getI64(framePtr + 144L) & 0xffffffffL));
                                            putI64(framePtr + 48L, (getU32(framePtr + 180L) << 32) | getU32(framePtr + 176L));
                                            putI64(framePtr + 88L, getI64(framePtr + 152L));
                                            putI64(framePtr + 72L, 0x100000000000000L);
                                            putI64(framePtr + 64L, 1L);
                                            putI64(framePtr + 56L, getI64(framePtr + 184L));
                                            if ((long) (int) tempVal1 < 1L) {
                                                putI64(framePtr + 136L, 0x100000000000000L);
                                            } else {
                                                putI64(framePtr + 136L, 0L);
                                            }
                                            putI64(framePtr + 128L, 1L);
                                            putI64(framePtr + 40L, 0L);
                                            putI64(framePtr + 32L, 1L);
                                            putI64(framePtr + 192L, getI64(framePtr + 128L));
                                            putI64(framePtr + 200L, getI64(framePtr + 136L));
                                            while (true) {
                                                fidiv(framePtr + 80L, framePtr + 48L, framePtr + 112L);
                                                if ((long) (int) tempVal1 < 1L) {
                                                    firadd(CC_1, framePtr + 112L, framePtr + 112L, 1L);
                                                }
                                                isZero = fEQ(framePtr + 192L, framePtr + 112L) != 0L ? 1L : 0L;
                                                if (isZero != 0L || (long) (int) comma(isZero = fEQ(framePtr + 128L, framePtr + 112L) != 0L ? 1L : 0L, isZero) != 0L) {
                                                    break;
                                                }
                                                putI64(framePtr + 200L, getI64(framePtr + 136L));
                                                putI64(framePtr + 192L, getI64(framePtr + 128L));
                                                putI64(framePtr + 136L, getI64(framePtr + 120L));
                                                putI64(framePtr + 128L, getI64(framePtr + 112L));
                                                fradd(framePtr + 32L, CC_1, framePtr + 32L, 0L);
                                                fimul(framePtr + 32L, framePtr + 96L, framePtr + 16L);
                                                putI64(framePtr + 104L, getI64(framePtr + 88L));
                                                putI64(framePtr + 96L, getI64(framePtr + 80L));
                                                fimul(framePtr + 80L, framePtr + 176L, framePtr + 80L);
                                                firadd(framePtr + 16L, framePtr + 80L, framePtr + 80L, 0L);
                                                fimul(framePtr + 32L, framePtr + 64L, framePtr + 16L);
                                                putI64(framePtr + 72L, getI64(framePtr + 56L));
                                                putI64(framePtr + 64L, getI64(framePtr + 48L));
                                                fimul(framePtr + 48L, framePtr + 176L, framePtr + 48L);
                                                firadd(framePtr + 16L, framePtr + 48L, framePtr + 48L, 0L);
                                            }
                                            status1 = 0L;
                                            putI64(resultPtr + 8L, getI64(framePtr + 120L));
                                            putI64(resultPtr, getI64(framePtr + 112L));
                                        }
                                        status1 = (long) (int) fNorm_L(status1, resultPtr);
                                        return status1;
                                    }
                                    constPtr2 = CC_0p5;
                                    mantissa = getI64(constPtr2 + 8L);
                                    status2 = getI64(constPtr2);
                                    status1 = 0L;
                                    putI64(resultPtr + 8L, mantissa);
                                    putI64(resultPtr, status2);
                                    return status1;
                                }
                                if (-1L < (long) (int) tempVal2) {
                                    step = 1;
                                    continue stepLoop;
                                } else {
                                    step = 2;
                                    continue stepLoop;
                                }
                            } else {
                                if (!(4L >= tempVal1 && isZero == 0L)) {
                                    if (tempVal2 == 0xfffffffeL) {
                                        if (tempVal3 != 2L) {
                                            mantissa = 0L;
                                            status2 = 0L;
                                            status1 = 0L;
                                            putI64(resultPtr + 8L, mantissa);
                                            putI64(resultPtr, status2);
                                            return status1;
                                        }
                                        constPtr2 = CC_1;
                                        mantissa = getI64(constPtr2 + 8L);
                                        status2 = getI64(constPtr2);
                                        status1 = 0L;
                                        putI64(resultPtr + 8L, mantissa);
                                        putI64(resultPtr, status2);
                                        return status1;
                                    }
                                    if (!(tempVal2 == 2L && tempVal3 == 0xfffffffeL)) {
                                        mantissa = 0L;
                                        status2 = 0L;
                                        status1 = 0L;
                                        putI64(resultPtr + 8L, mantissa);
                                        putI64(resultPtr, status2);
                                        return status1;
                                    }
                                    constPtr2 = CC_0;
                                    mantissa = getI64(constPtr2 + 8L);
                                    status2 = getI64(constPtr2);
                                    status1 = 0L;
                                    putI64(resultPtr + 8L, mantissa);
                                    putI64(resultPtr, status2);
                                    return status1;
                                }
                                if (tempVal3 != 2L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    step = 1;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 1: {
                        constPtr2 = CC_0;
                        mantissa = getI64(constPtr2 + 8L);
                        status2 = getI64(constPtr2);
                        status1 = 0L;
                        putI64(resultPtr + 8L, mantissa);
                        putI64(resultPtr, status2);
                        return status1;
                    }
                    case 2: {
                        constPtr2 = CC_1;
                        mantissa = getI64(constPtr2 + 8L);
                        status2 = getI64(constPtr2);
                        status1 = 0L;
                        putI64(resultPtr + 8L, mantissa);
                        putI64(resultPtr, status2);
                        return status1;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long fiIUTPN(long probPtr, long meanPtr, long stdDevPtr, long resultPtr) {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long isZero1 = 0L;
        long isZero2 = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long dataPtr = 0L;
        long constPtr = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(240);
        try {
            if (getU32(probPtr) == 0L || getU32(meanPtr) == 0L || getI32(stdDevPtr) == 0L) {
                constPtr = CC_NaN;
                status1 = 0L;
                status2 = getI64(constPtr);
                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                putI64(resultPtr, status2);
                return status1;
            }
            if (-1L >= (long) (int) getU32(meanPtr)) {
                constPtr = CC_NaN;
                status1 = 24L;
                status2 = getI64(constPtr);
                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                putI64(resultPtr, status2);
                return status1;
            }
            isZero1 = fZero(meanPtr) != 0L ? 1L : 0L;
            if (isZero1 != 0L) {
                constPtr = CC_NaN;
                status1 = 0L;
                status2 = getI64(constPtr);
                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                putI64(resultPtr, status2);
                return status1;
            }
            if (getU32(meanPtr) == 2L) {
                if (4L < ((getI32(stdDevPtr) + 1L) & 0xffffffffL) || ((1L << (int) ((getI32(stdDevPtr) + 1L) & 31L)) & 21L) == 0L || 4L < ((getU32(probPtr) + 1L) & 0xffffffffL) || ((1L << (int) ((getU32(probPtr) + 1L) & 31L)) & 21L) == 0L) {
                    constPtr = CC_NaN;
                    status1 = 0L;
                    status2 = getI64(constPtr);
                    putI64(resultPtr + 8L, getI64(constPtr + 8L));
                    putI64(resultPtr, status2);
                    return status1;
                }
                constPtr = CC_0p5;
                status1 = 0L;
                status2 = getI64(constPtr);
                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                putI64(resultPtr, status2);
                return status1;
            }
            tempVal2 = getU32(probPtr);
            status1 = getI32(stdDevPtr);
            tempVal1 = (tempVal2 + 1L) & 0xffffffffL;
            isZero1 = tempVal1 & 1L;
            if (!(((status1 + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((status1 + 1L) & 31L)) & 21L) != 0L)) {
                if (4L < tempVal1 || isZero1 != 0L) {
                    if (tempVal2 != 0xfffffffeL) {
                        if (!(tempVal2 == 2L && status1 == -2L)) {
                            constPtr = CC_NaN;
                            status1 = 0L;
                            status2 = getI64(constPtr);
                            putI64(resultPtr + 8L, getI64(constPtr + 8L));
                            putI64(resultPtr, status2);
                            return status1;
                        }
                        constPtr = CC_0;
                        status1 = 0L;
                        status2 = getI64(constPtr);
                        putI64(resultPtr + 8L, getI64(constPtr + 8L));
                        putI64(resultPtr, status2);
                        return status1;
                    }
                    if (status1 != 2L) {
                        constPtr = CC_NaN;
                        status1 = 0L;
                        status2 = getI64(constPtr);
                        putI64(resultPtr + 8L, getI64(constPtr + 8L));
                        putI64(resultPtr, status2);
                        return status1;
                    }
                } else {
                    if (status1 == 2L) {
                        constPtr = CC_0;
                        status1 = 0L;
                        status2 = getI64(constPtr);
                        putI64(resultPtr + 8L, getI64(constPtr + 8L));
                        putI64(resultPtr, status2);
                        return status1;
                    }
                }
                constPtr = CC_1;
                status1 = 0L;
                status2 = getI64(constPtr);
                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                putI64(resultPtr, status2);
                return status1;
            }
            if (4L < tempVal1 || isZero1 != 0L) {
                if (-1L < (long) (int) tempVal2) {
                    constPtr = CC_0;
                    status1 = 0L;
                    status2 = getI64(constPtr);
                    putI64(resultPtr + 8L, getI64(constPtr + 8L));
                    putI64(resultPtr, status2);
                    return status1;
                }
                constPtr = CC_1;
                status1 = 0L;
                status2 = getI64(constPtr);
                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                putI64(resultPtr, status2);
                return status1;
            }
            if (status1 < 0L || (long) (int) comma(isZero1 = fLT(CC_1, stdDevPtr) != 0L ? 1L : 0L, isZero1) != 0L) {
                status1 = 24L;
                return status1;
            }
            isZero1 = fZero(stdDevPtr) != 0L ? 1L : 0L;
            if (isZero1 != 0L) {
                constPtr = CC_NInf_L;
            } else {
                isZero1 = fEQ(stdDevPtr, CC_1) != 0L ? 1L : 0L;
                if (isZero1 == 0L) {
                    putI64(framePtr + 72L, getI64(stdDevPtr + 8L));
                    putI64(framePtr + 64L, getI64(stdDevPtr));
                    isZero1 = fLT(CC_1mEXPN2, framePtr + 64L) != 0L ? 1L : 0L;
                    if (isZero1 != 0L) {
                        fradd(CC_1, framePtr + 64L, framePtr + 64L, 1L);
                    }
                    isZero2 = fLT(CC_ExpN2, framePtr + 64L) != 0L ? 1L : 0L;
                    if (isZero2 != 0L) {
                        fradd(framePtr + 64L, CC_N0p5, framePtr + 64L, 0L);
                        fmul(framePtr + 64L, framePtr + 64L, framePtr + 48L);
                        putI64(framePtr + 40L, 0x877967942005507L);
                        putI64(framePtr + 32L, 0xfffffffd00000001L);
                        fimul(framePtr + 32L, framePtr + 48L, framePtr + 32L);
                        firadd(framePtr + 32L, ADDR_RODATA + 2824L, framePtr + 32L, 0L);
                        fimul(framePtr + 32L, framePtr + 48L, framePtr + 32L);
                        firadd(framePtr + 32L, ADDR_RODATA + 2840L, framePtr + 32L, 0L);
                        fimul(framePtr + 32L, framePtr + 48L, framePtr + 32L);
                        firadd(framePtr + 32L, ADDR_RODATA + 2856L, framePtr + 32L, 0L);
                        fimul(framePtr + 32L, framePtr + 48L, framePtr + 32L);
                        firadd(framePtr + 32L, ADDR_RODATA + 2872L, framePtr + 32L, 0L);
                        fimul(framePtr + 32L, framePtr + 48L, framePtr + 32L);
                        firadd(framePtr + 32L, ADDR_RODATA + 2888L, framePtr + 32L, 0L);
                        fimul(framePtr + 32L, framePtr + 48L, framePtr + 32L);
                        firadd(framePtr + 32L, ADDR_RODATA + 2904L, framePtr + 32L, 0L);
                        fimul(framePtr + 32L, framePtr + 48L, framePtr + 32L);
                        firadd(framePtr + 32L, ADDR_RODATA + 2920L, framePtr + 32L, 0L);
                        fimul(framePtr + 48L, framePtr + 32L, framePtr + 32L);
                        putI64(framePtr + 88L, 0x530384696460372L);
                        putI64(framePtr + 80L, 0xffffffffL);
                        firadd(framePtr + 48L, ADDR_RODATA + 2968L, framePtr + 80L, 0L);
                        fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                        firadd(framePtr + 80L, ADDR_RODATA + 2984L, framePtr + 80L, 0L);
                        fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                        firadd(framePtr + 80L, ADDR_RODATA + 3000L, framePtr + 80L, 0L);
                        fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                        firadd(framePtr + 80L, ADDR_RODATA + 3016L, framePtr + 80L, 0L);
                        fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                        firadd(framePtr + 80L, ADDR_RODATA + 3032L, framePtr + 80L, 0L);
                        fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                        firadd(framePtr + 80L, ADDR_RODATA + 3048L, framePtr + 80L, 0L);
                        fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                        firadd(framePtr + 80L, ADDR_RODATA + 3064L, framePtr + 80L, 0L);
                        putI64(framePtr + 56L, getI64(framePtr + 88L));
                        putI64(framePtr + 48L, getI64(framePtr + 80L));
                        fidiv(framePtr + 32L, framePtr + 48L, framePtr + 32L);
                        fimul(framePtr + 64L, framePtr + 32L, framePtr + 32L);
                        firadd(framePtr + 64L, framePtr + 32L, framePtr + 32L, 0L);
                        fimul(framePtr + 32L, CC_Sqrt2PI, resultPtr);
                    } else {
                        filn(framePtr + 64L, framePtr + 16L);
                        fimul2(framePtr + 16L, framePtr + 16L);
                        isZero2 = fZero(framePtr + 16L) != 0L ? 1L : 0L;
                        if (isZero2 == 0L) {
                            putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 16L) & 0xffffffffL));
                        }
                        fisqrt(framePtr + 16L, framePtr + 16L);
                        filn(framePtr + 16L, framePtr + 32L);
                        fidiv(framePtr + 32L, framePtr + 16L, framePtr + 32L);
                        firadd(framePtr + 16L, framePtr + 32L, framePtr + 32L, 1L);
                        fidiv(CC_1, framePtr + 16L, framePtr + 48L);
                        isZero2 = fLT(framePtr + 16L, CC_8) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            putI64(framePtr + 24L, 0x430284975043555L);
                            putI64(framePtr + 16L, 1L);
                            fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                            firadd(framePtr + 16L, ADDR_RODATA + 3144L, framePtr + 16L, 0L);
                            fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                            firadd(framePtr + 16L, ADDR_RODATA + 3160L, framePtr + 16L, 0L);
                            fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                            firadd(framePtr + 16L, ADDR_RODATA + 3176L, framePtr + 16L, 0L);
                            fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                            firadd(framePtr + 16L, ADDR_RODATA + 3192L, framePtr + 16L, 0L);
                            fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                            firadd(framePtr + 16L, ADDR_RODATA + 3208L, framePtr + 16L, 0L);
                            fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                            firadd(framePtr + 16L, ADDR_RODATA + 3224L, framePtr + 16L, 0L);
                            fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                            firadd(framePtr + 16L, ADDR_RODATA + 3240L, framePtr + 16L, 0L);
                            fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                            firadd(framePtr + 16L, ADDR_RODATA + 3256L, framePtr + 16L, 0L);
                            fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                            firadd(framePtr + 16L, ADDR_RODATA + 3272L, framePtr + 16L, 0L);
                            fimul(framePtr + 48L, framePtr + 16L, framePtr + 16L);
                            putI64(framePtr + 88L, 0x200142510917053L);
                            putI64(framePtr + 80L, 0x100000001L);
                            firadd(framePtr + 48L, ADDR_RODATA + 3288L, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            firadd(framePtr + 80L, ADDR_RODATA + 3304L, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            firadd(framePtr + 80L, ADDR_RODATA + 3320L, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            firadd(framePtr + 80L, ADDR_RODATA + 3336L, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            firadd(framePtr + 80L, ADDR_RODATA + 3352L, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            firadd(framePtr + 80L, ADDR_RODATA + 3368L, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            firadd(framePtr + 80L, ADDR_RODATA + 3384L, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            firadd(framePtr + 80L, ADDR_RODATA + 3400L, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            dataPtr = ADDR_RODATA + 3416L;
                        } else {
                            isZero2 = fLT(framePtr + 16L, CC_32) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                putI64(framePtr + 24L, 0x324452572531291L);
                                putI64(framePtr + 16L, 1L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3464L, framePtr + 16L, 0L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3480L, framePtr + 16L, 0L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3496L, framePtr + 16L, 0L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3512L, framePtr + 16L, 0L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3528L, framePtr + 16L, 0L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3544L, framePtr + 16L, 0L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3560L, framePtr + 16L, 0L);
                                fimul(framePtr + 48L, framePtr + 16L, framePtr + 16L);
                                dataPtr = ADDR_RODATA + 3608L;
                            } else {
                                putI64(framePtr + 24L, 0x202033109130277L);
                                putI64(framePtr + 16L, 1L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3784L, framePtr + 16L, 0L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3800L, framePtr + 16L, 0L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3816L, framePtr + 16L, 0L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3832L, framePtr + 16L, 0L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3848L, framePtr + 16L, 0L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3864L, framePtr + 16L, 0L);
                                fimul(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                                firadd(framePtr + 16L, ADDR_RODATA + 3880L, framePtr + 16L, 0L);
                                fimul(framePtr + 48L, framePtr + 16L, framePtr + 16L);
                                dataPtr = ADDR_RODATA + 3928L;
                            }
                            putI64(framePtr + 88L, getI64(dataPtr + 8L));
                            putI64(framePtr + 80L, getI64(dataPtr));
                            firadd(framePtr + 48L, dataPtr, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            firadd(framePtr + 80L, dataPtr + 16L, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            firadd(framePtr + 80L, dataPtr + 32L, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            firadd(framePtr + 80L, dataPtr + 48L, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            firadd(framePtr + 80L, dataPtr + 64L, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            firadd(framePtr + 80L, dataPtr + 80L, framePtr + 80L, 0L);
                            fimul(framePtr + 80L, framePtr + 48L, framePtr + 80L);
                            dataPtr = dataPtr + 96L;
                        }
                        firadd(framePtr + 80L, dataPtr, framePtr + 80L, 0L);
                        putI64(framePtr + 56L, getI64(framePtr + 88L));
                        putI64(framePtr + 48L, getI64(framePtr + 80L));
                        fidiv(framePtr + 16L, framePtr + 48L, framePtr + 16L);
                        firadd(framePtr + 32L, framePtr + 16L, resultPtr, 1L);
                        if (isZero1 == 0L && comma(isZero1 = fZero(resultPtr) != 0L ? 1L : 0L, isZero1 == 0L ? 1L : 0L) != 0L) {
                            putI32(resultPtr, -getI64(resultPtr));
                        }
                    }
                    fisqrt(meanPtr, framePtr + 16L);
                    fimul(resultPtr, framePtr + 16L, resultPtr);
                    status2 = firadd(resultPtr, probPtr, resultPtr, 0L);
                    status1 = (long) (int) fNorm_L((long) (int) status2, resultPtr);
                    return status1;
                }
                constPtr = CC_PInf_L;
            }
            status1 = 0L;
            status2 = getI64(constPtr);
            putI64(resultPtr + 8L, getI64(constPtr + 8L));
            putI64(resultPtr, status2);
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    long fiBinomial(long nPtr, long kPtr, long resultPtr) {
        long intVal1 = 0L;
        long isZero = 0L;
        long intVal2 = 0L;
        long intVal3 = 0L;
        long result = 0L;
        long dataPtr = 0L;
        long constPtr = 0L;
        long tempVal = 0L;
        long mantissa = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            isZero = fiInteger(nPtr) != 0L ? 1L : 0L;
            if (isZero == 0L || comma(isZero = fiInteger(kPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L || (long) (int) getU32(nPtr) < 0L || (long) (int) comma(isZero = fLT(nPtr, kPtr) != 0L ? 1L : 0L, isZero) != 0L) {
                constPtr = CC_NaN;
                result = 24L;
                mantissa = getI64(constPtr + 8L);
                tempVal = getI64(constPtr);
                putI64(resultPtr + 8L, mantissa);
                putI64(resultPtr, tempVal);
                return result;
            }
            isZero = fLE(kPtr, CC_0) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                isZero = fZero(kPtr) != 0L ? 1L : 0L;
                if (isZero == 0L && comma(isZero = fEQ(kPtr, nPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
                    firadd(nPtr, CC_N1, framePtr + 56L, 0L);
                    result = getI64(nPtr);
                    putI64(resultPtr + 8L, getI64(nPtr + 8L));
                    putI64(resultPtr, result);
                    isZero = fEQ(kPtr, CC_1) != 0L ? 1L : 0L;
                    if (isZero == 0L && comma(isZero = fEQ(kPtr, framePtr + 56L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
                        intVal2 = (long) (int) fCCToInt(kPtr);
                        intVal3 = (long) (int) fCCToInt(nPtr);
                        intVal1 = (long) (int) (intVal3 - intVal2);
                        if (intVal2 <= (long) (int) (intVal3 - intVal2)) {
                            intVal1 = intVal2;
                        }
                        if (1L < intVal1) {
                            intVal2 = 1L;
                            do {
                                intVal3 = (long) (int) (intVal3 - 1L);
                                intVal2 = (long) (int) (intVal2 + 1L);
                                fIntToCC(intVal3, framePtr + 40L);
                                dataPtr = fIntToCC(intVal2, framePtr + 24L);
                                fidiv(framePtr + 40L, dataPtr, framePtr + 40L);
                                fimul(resultPtr, resultPtr, framePtr + 40L);
                            } while (intVal1 != intVal2);
                        }
                        firadd(resultPtr, CC_0p5, resultPtr, 0L);
                        firnd(resultPtr, 0L, resultPtr, 0L);
                        fNorm_L(0L, resultPtr);
                    }
                    result = 0L;
                    return result;
                }
                constPtr = CC_1;
                result = 0L;
                mantissa = getI64(constPtr + 8L);
                tempVal = getI64(constPtr);
            } else {
                mantissa = 0L;
                tempVal = 1L;
                result = 0L;
            }
            putI64(resultPtr + 8L, mantissa);
            putI64(resultPtr, tempVal);
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long fiPBinom(long nPtr, long pPtr, long kPtr, long resultPtr) {
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long status3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            if ((long) (int) getI64(pPtr) < 0L || (long) (int) comma(isZero = fLT(CC_1, pPtr) != 0L ? 1L : 0L, isZero) != 0L || (long) (int) comma(isZero = fLE(nPtr, CC_0) != 0L ? 1L : 0L, isZero) != 0L || comma(isZero = fLT(nPtr, kPtr) != 0L ? 1L : 0L, isZero != 0L || comma(isZero = fiInteger(nPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L || comma(isZero = fiInteger(kPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
                status1 = 24L;
                putI64(resultPtr + 8L, 0L);
                putI64(resultPtr, 0L);
                return status1;
            }
            isZero = fZero(kPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                firadd(CC_1, pPtr, resultPtr, 1L);
                pPtr = resultPtr;
                status2 = fpow(pPtr, nPtr, resultPtr, 0L);
                status1 = (long) (int) fNorm_L((long) (int) status2, resultPtr);
            } else {
                isZero = fEQ(kPtr, nPtr) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    fiBinomial(nPtr, kPtr, framePtr + 40L);
                    fpow(pPtr, kPtr, resultPtr, 1L);
                    fimul(framePtr + 40L, resultPtr, resultPtr);
                    firadd(nPtr, kPtr, framePtr + 24L, 1L);
                    firadd(CC_1, pPtr, framePtr + 40L, 1L);
                    fpow(framePtr + 40L, framePtr + 24L, framePtr + 40L, 1L);
                    status3 = fimul(resultPtr, framePtr + 40L, resultPtr);
                    fNorm_L((long) (int) status3, resultPtr);
                    status1 = 0L;
                } else {
                    status2 = fpow(pPtr, nPtr, resultPtr, 0L);
                    status1 = (long) (int) fNorm_L((long) (int) status2, resultPtr);
                }
            }
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    long fiFBeta(long xPtr, long aPtr, long bPtr, long resultPtr) {
        long tempVal = 0L;
        tempVal = fiIBeta(xPtr, aPtr, bPtr, resultPtr) & 0xffffffffL;
        return tempVal & 0xffffffffL;
    }

    long fiIBeta(long aPtr, long bPtr, long xPtr, long resultPtr) {
        long isZero1 = 0L;
        long isZero2 = 0L;
        long isLess = 0L;
        long tempVal1 = 0L;
        long status = 0L;
        long constPtr = 0L;
        long intVal = 0L;
        long tempVal2 = 0L;
        long mantissa = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(592);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        isZero1 = fLE(aPtr, CC_0) != 0L ? 1L : 0L;
                        if (isZero1 != 0L || (long) (int) comma(isZero1 = fLE(bPtr, CC_0) != 0L ? 1L : 0L, isZero1) != 0L || (long) (int) getI64(xPtr) < 0L || (long) (int) comma(isZero1 = fLT(CC_1, xPtr) != 0L ? 1L : 0L, isZero1) != 0L) {
                            status = 24L;
                            mantissa = 0L;
                            tempVal2 = 0L;
                            putI64(resultPtr + 8L, mantissa);
                            putI64(resultPtr, tempVal2);
                            return status;
                        }
                        isZero1 = fZero(xPtr) != 0L ? 1L : 0L;
                        if (!(isZero1 == 0L && comma(isZero1 = fEQ(xPtr, CC_1) != 0L ? 1L : 0L, isZero1 == 0L ? 1L : 0L) != 0L)) {
                            mantissa = getI64(xPtr + 8L);
                            tempVal2 = getI64(xPtr);
                            status = 0L;
                            putI64(resultPtr + 8L, mantissa);
                            putI64(resultPtr, tempVal2);
                            return status;
                        }
                        fimul(bPtr, xPtr, framePtr + 16L);
                        isZero1 = fLE(framePtr + 16L, CC_1) != 0L ? 1L : 0L;
                        if (isZero1 != 0L && (long) (int) comma(isZero1 = fLE(xPtr, CC_0p95) != 0L ? 1L : 0L, isZero1) != 0L) {
                            fiIBetaSeries(aPtr, bPtr, xPtr, framePtr + 400L);
                            putI64(resultPtr + 8L, getI64(framePtr + 408L));
                            putI64(resultPtr, getI64(framePtr + 400L));
                            status = (long) (int) fNorm_L(0L, resultPtr);
                            return status;
                        }
                        firadd(CC_1, xPtr, framePtr + 64L, 1L);
                        firadd(aPtr, bPtr, framePtr + 16L, 0L);
                        fidiv(aPtr, framePtr + 16L, framePtr + 16L);
                        isZero1 = fLT(framePtr + 16L, xPtr) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            putI64(framePtr + 120L, getI64(bPtr + 8L));
                            putI64(framePtr + 112L, getI64(bPtr));
                            putI64(framePtr + 104L, getI64(aPtr + 8L));
                            putI64(framePtr + 96L, getI64(aPtr));
                            putI64(framePtr + 56L, getI64(xPtr + 8L));
                            putI64(framePtr + 48L, getI64(xPtr));
                            putI64(framePtr + 88L, getI64(framePtr + 72L));
                            putI64(framePtr + 80L, getI64(framePtr + 64L));
                            fimul(framePtr + 96L, framePtr + 80L, framePtr + 16L);
                            isZero1 = fLE(framePtr + 16L, CC_1) != 0L ? 1L : 0L;
                            if (isZero1 == 0L || comma(isZero1 = fLE(framePtr + 80L, CC_0p95) != 0L ? 1L : 0L, isZero1 == 0L ? 1L : 0L) != 0L) {
                                isZero1 = 1L;
                                step = 2;
                                continue stepLoop;
                            } else {
                                fiIBetaSeries(framePtr + 112L, framePtr + 96L, framePtr + 80L, framePtr + 400L);
                                step = 1;
                                continue stepLoop;
                            }
                        } else {
                            putI64(framePtr + 120L, getI64(aPtr + 8L));
                            putI64(framePtr + 112L, getI64(aPtr));
                            putI64(framePtr + 104L, getI64(bPtr + 8L));
                            putI64(framePtr + 96L, getI64(bPtr));
                            putI64(framePtr + 56L, getI64(framePtr + 72L));
                            putI64(framePtr + 48L, getI64(framePtr + 64L));
                            putI64(framePtr + 88L, getI64(xPtr + 8L));
                            putI64(framePtr + 80L, getI64(xPtr));
                            fimul(framePtr + 96L, framePtr + 80L, framePtr + 16L);
                            isZero1 = 0L;
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        isZero1 = fLE(framePtr + 400L, CC_MachEp) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            constPtr = CC_MachEp;
                        } else {
                            constPtr = framePtr + 400L;
                        }
                        firadd(CC_1, constPtr, framePtr + 400L, 1L);
                        putI64(resultPtr + 8L, getI64(framePtr + 408L));
                        putI64(resultPtr, getI64(framePtr + 400L));
                        status = (long) (int) fNorm_L(0L, resultPtr);
                        return status;
                    }
                    case 2: {
                        firadd(framePtr + 112L, framePtr + 96L, framePtr + 32L, 0L);
                        firadd(framePtr + 32L, CC_N2, framePtr + 32L, 0L);
                        fimul(framePtr + 80L, framePtr + 32L, framePtr + 32L);
                        firadd(framePtr + 112L, CC_N1, framePtr + 16L, 0L);
                        firadd(framePtr + 32L, framePtr + 16L, framePtr + 32L, 1L);
                        if ((long) (int) getI64(framePtr + 32L) < 0L) {
                            putI64(framePtr + 296L, getI64(framePtr + 120L));
                            putI64(framePtr + 288L, getI64(framePtr + 112L));
                            firadd(framePtr + 112L, framePtr + 96L, framePtr + 272L, 0L);
                            putI64(framePtr + 264L, getI64(framePtr + 120L));
                            putI64(framePtr + 256L, getI64(framePtr + 112L));
                            firadd(framePtr + 112L, CC_1, framePtr + 240L, 0L);
                            putI64(framePtr + 232L, 0x100000000000000L);
                            putI64(framePtr + 224L, 1L);
                            firadd(framePtr + 96L, CC_N1, framePtr + 208L, 0L);
                            putI64(framePtr + 200L, getI64(framePtr + 248L));
                            putI64(framePtr + 192L, getI64(framePtr + 240L));
                            firadd(framePtr + 112L, CC_2, framePtr + 176L, 0L);
                            putI64(framePtr + 360L, 0L);
                            putI64(framePtr + 352L, 1L);
                            putI64(framePtr + 72L, 0x100000000000000L);
                            putI64(framePtr + 64L, 1L);
                            putI64(framePtr + 160L, getI64(framePtr + 64L));
                            putI64(framePtr + 168L, getI64(framePtr + 72L));
                            putI64(framePtr + 304L, getI64(framePtr + 64L));
                            putI64(framePtr + 312L, getI64(framePtr + 72L));
                            putI64(framePtr + 320L, getI64(framePtr + 64L));
                            putI64(framePtr + 328L, getI64(framePtr + 72L));
                            putI64(framePtr + 368L, getI64(framePtr + 64L));
                            putI64(framePtr + 376L, getI64(framePtr + 72L));
                            tempVal1 = fmul_noNorm(CC_3, CC_MachEp, framePtr + 128L) & 0xffffffffL;
                            fNorm(tempVal1, framePtr + 128L);
                            status = 201L;
                            step = 4;
                            continue stepLoop;
                        } else {
                            putI64(framePtr + 40L, getI64(framePtr + 88L));
                            putI64(framePtr + 32L, getI64(framePtr + 80L));
                            putI64(framePtr + 296L, getI64(framePtr + 120L));
                            putI64(framePtr + 288L, getI64(framePtr + 112L));
                            firadd(framePtr + 96L, CC_N1, framePtr + 272L, 0L);
                            putI64(framePtr + 264L, getI64(framePtr + 120L));
                            putI64(framePtr + 256L, getI64(framePtr + 112L));
                            firadd(framePtr + 112L, CC_1, framePtr + 240L, 0L);
                            putI64(framePtr + 232L, 0x100000000000000L);
                            putI64(framePtr + 224L, 1L);
                            firadd(framePtr + 112L, framePtr + 96L, framePtr + 208L, 0L);
                            firadd(framePtr + 112L, CC_1, framePtr + 192L, 0L);
                            firadd(framePtr + 112L, CC_2, framePtr + 176L, 0L);
                            putI64(framePtr + 360L, 0L);
                            putI64(framePtr + 352L, 1L);
                            putI64(framePtr + 312L, 0x100000000000000L);
                            putI64(framePtr + 304L, 1L);
                            putI64(framePtr + 320L, getI64(framePtr + 304L));
                            putI64(framePtr + 328L, getI64(framePtr + 312L));
                            putI64(framePtr + 368L, getI64(framePtr + 304L));
                            putI64(framePtr + 376L, getI64(framePtr + 312L));
                            firadd(CC_1, framePtr + 32L, framePtr + 64L, 1L);
                            fidiv(framePtr + 32L, framePtr + 64L, framePtr + 32L);
                            putI64(framePtr + 72L, 0x100000000000000L);
                            putI64(framePtr + 64L, 1L);
                            putI64(framePtr + 160L, getI64(framePtr + 64L));
                            putI64(framePtr + 168L, getI64(framePtr + 72L));
                            tempVal1 = fmul_noNorm(CC_3, CC_MachEp, framePtr + 128L) & 0xffffffffL;
                            fNorm(tempVal1, framePtr + 128L);
                            status = 201L;
                            step = 5;
                            continue stepLoop;
                        }
                    }
                    case 3: {
                        filn(framePtr + 80L, framePtr + 32L);
                        fimul(framePtr + 112L, framePtr + 32L, framePtr + 32L);
                        filn(framePtr + 48L, framePtr + 400L);
                        fimul(framePtr + 96L, framePtr + 400L, framePtr + 400L);
                        firadd(framePtr + 112L, framePtr + 96L, framePtr + 384L, 0L);
                        isZero2 = fLT(framePtr + 384L, CC_MaxGamma) != 0L ? 1L : 0L;
                        if (isZero2 != 0L && (long) (int) comma(isZero2 = fMLT(framePtr + 32L, CC_MaxLog) != 0L ? 1L : 0L, isZero2) != 0L && (long) (int) comma(isZero2 = fMLT(framePtr + 400L, CC_MaxLog) != 0L ? 1L : 0L, isZero2) != 0L) {
                            fpow(framePtr + 48L, framePtr + 96L, framePtr + 400L, 1L);
                            fpow(framePtr + 80L, framePtr + 112L, framePtr + 384L, 1L);
                            fimul(framePtr + 400L, framePtr + 384L, framePtr + 400L);
                            fidiv(framePtr + 400L, framePtr + 112L, framePtr + 400L);
                            fimul(framePtr + 400L, framePtr + 64L, framePtr + 400L);
                            firadd(framePtr + 112L, framePtr + 96L, framePtr + 384L, 0L);
                            fiGamma(framePtr + 384L, framePtr + 384L);
                            fiGamma(framePtr + 112L, framePtr + 112L);
                            fiGamma(framePtr + 96L, framePtr + 96L);
                            fimul(framePtr + 400L, framePtr + 384L, framePtr + 400L);
                            fimul(framePtr + 112L, framePtr + 96L, framePtr + 112L);
                            fidiv(framePtr + 400L, framePtr + 112L, framePtr + 400L);
                        } else {
                            firadd(framePtr + 112L, framePtr + 96L, framePtr + 384L, 0L);
                            filnGamma(framePtr + 384L, framePtr + 384L);
                            firadd(framePtr + 32L, framePtr + 400L, framePtr + 32L, 0L);
                            firadd(framePtr + 32L, framePtr + 384L, framePtr + 32L, 0L);
                            fidiv(framePtr + 64L, framePtr + 112L, framePtr + 64L);
                            filn(framePtr + 64L, framePtr + 64L);
                            firadd(framePtr + 32L, framePtr + 64L, framePtr + 32L, 0L);
                            filnGamma(framePtr + 112L, framePtr + 112L);
                            filnGamma(framePtr + 96L, framePtr + 96L);
                            firadd(framePtr + 32L, framePtr + 112L, framePtr + 32L, 1L);
                            firadd(framePtr + 32L, framePtr + 96L, framePtr + 32L, 1L);
                            isZero2 = fLT(framePtr + 32L, CC_MinLog) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                putI64(framePtr + 408L, 0L);
                                putI64(framePtr + 400L, 1L);
                            } else {
                                fiexpm(framePtr + 32L, framePtr + 400L, 0L);
                            }
                        }
                        if (isZero1 == 0L) {
                            putI64(resultPtr + 8L, getI64(framePtr + 408L));
                            putI64(resultPtr, getI64(framePtr + 400L));
                            status = (long) (int) fNorm_L(0L, resultPtr);
                            return status;
                        }
                        step = 1;
                        continue stepLoop;
                    }
                    case 4: {
                        tempVal1 = fmul_noNorm(framePtr + 256L, framePtr + 240L, framePtr + 384L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 384L);
                        tempVal1 = fmul_noNorm(framePtr + 80L, framePtr + 288L, framePtr + 336L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 336L);
                        tempVal1 = fmul_noNorm(framePtr + 336L, framePtr + 272L, framePtr + 336L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 336L);
                        fidiv(framePtr + 336L, framePtr + 384L, framePtr + 400L);
                        isZero2 = fZero(framePtr + 400L) != 0L ? 1L : 0L;
                        if (isZero2 == 0L) {
                            putI64(framePtr + 400L, (((getI32(framePtr + 404L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 400L) & 0xffffffffL));
                        }
                        tempVal1 = fmul_noNorm(framePtr + 352L, framePtr + 400L, framePtr + 384L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 384L);
                        firadd(framePtr + 384L, framePtr + 368L, framePtr + 384L, 0L);
                        tempVal1 = fmul_noNorm(framePtr + 400L, framePtr + 304L, framePtr + 336L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 336L);
                        firadd(framePtr + 336L, framePtr + 320L, framePtr + 336L, 0L);
                        putI64(framePtr + 360L, getI64(framePtr + 376L));
                        putI64(framePtr + 352L, getI64(framePtr + 368L));
                        putI64(framePtr + 376L, getI64(framePtr + 392L));
                        putI64(framePtr + 312L, getI64(framePtr + 328L));
                        putI64(framePtr + 304L, getI64(framePtr + 320L));
                        putI64(framePtr + 328L, getI64(framePtr + 344L));
                        putI64(framePtr + 320L, (((getI32(framePtr + 340L) & 0xffffffffL)) << 32) | (getI64(framePtr + 336L) & 0xffffffffL));
                        putI64(framePtr + 368L, (((getI32(framePtr + 388L) & 0xffffffffL)) << 32) | (getI64(framePtr + 384L) & 0xffffffffL));
                        tempVal1 = fmul_noNorm(framePtr + 192L, framePtr + 176L, framePtr + 384L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 384L);
                        tempVal1 = fmul_noNorm(framePtr + 80L, framePtr + 224L, framePtr + 336L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 336L);
                        tempVal1 = fmul_noNorm(framePtr + 336L, framePtr + 208L, framePtr + 336L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 336L);
                        fidiv(framePtr + 336L, framePtr + 384L, framePtr + 400L);
                        tempVal1 = fmul_noNorm(framePtr + 352L, framePtr + 400L, framePtr + 384L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 384L);
                        firadd(framePtr + 384L, framePtr + 368L, framePtr + 384L, 0L);
                        tempVal1 = fmul_noNorm(framePtr + 400L, framePtr + 304L, framePtr + 336L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 336L);
                        firadd(framePtr + 336L, framePtr + 320L, framePtr + 336L, 0L);
                        putI64(framePtr + 360L, getI64(framePtr + 376L));
                        putI64(framePtr + 352L, getI64(framePtr + 368L));
                        putI64(framePtr + 376L, getI64(framePtr + 392L));
                        putI64(framePtr + 312L, getI64(framePtr + 328L));
                        putI64(framePtr + 304L, getI64(framePtr + 320L));
                        putI64(framePtr + 328L, getI64(framePtr + 344L));
                        putI64(framePtr + 320L, (((getI32(framePtr + 340L) & 0xffffffffL)) << 32) | (getI64(framePtr + 336L) & 0xffffffffL));
                        putI64(framePtr + 368L, (((getI32(framePtr + 388L) & 0xffffffffL)) << 32) | (getI64(framePtr + 384L) & 0xffffffffL));
                        isZero2 = fZero(framePtr + 336L) != 0L ? 1L : 0L;
                        if (isZero2 == 0L) {
                            fidiv(framePtr + 384L, framePtr + 336L, framePtr + 160L);
                        }
                        isZero2 = fZero(framePtr + 160L) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            putI64(framePtr + 152L, 0x100000000000000L);
                            putI64(framePtr + 144L, 1L);
                        } else {
                            firadd(framePtr + 64L, framePtr + 160L, framePtr + 144L, 1L);
                            fidiv(framePtr + 144L, framePtr + 160L, framePtr + 144L);
                            if ((long) (int) getI64(framePtr + 144L) == 3L) {
                                intVal = 1L;
                                putI64(framePtr + 144L, (((getI32(framePtr + 148L) & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
                            } else {
                                if ((long) (int) getI64(framePtr + 144L) < 0L) {
                                    intVal = (long) (int) -getI64(framePtr + 144L);
                                    putI64(framePtr + 144L, (((getI32(framePtr + 148L) & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
                                }
                            }
                            putI64(framePtr + 72L, getI64(framePtr + 168L));
                            putI64(framePtr + 64L, getI64(framePtr + 160L));
                        }
                        isZero2 = fLT(framePtr + 144L, framePtr + 128L) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            firadd(framePtr + 288L, CC_1, framePtr + 288L, 0L);
                            firadd(framePtr + 272L, CC_1, framePtr + 272L, 0L);
                            firadd(framePtr + 256L, CC_2, framePtr + 256L, 0L);
                            firadd(framePtr + 240L, CC_2, framePtr + 240L, 0L);
                            firadd(framePtr + 224L, CC_1, framePtr + 224L, 0L);
                            firadd(framePtr + 208L, CC_N1, framePtr + 208L, 0L);
                            firadd(framePtr + 192L, CC_2, framePtr + 192L, 0L);
                            firadd(framePtr + 176L, CC_2, framePtr + 176L, 0L);
                            isZero2 = fMLT(framePtr + 336L, CC_MachEp) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                isZero2 = 1L;
                            } else {
                                isZero2 = fMLT(framePtr + 384L, CC_MachEp) != 0L ? 1L : 0L;
                            }
                            if ((getI64(framePtr + 336L) & 0xffffffffL) == 3L) {
                                putI32(framePtr + 336L, 1L);
                            } else {
                                if ((long) (int) getI64(framePtr + 336L) < 0L) {
                                    putI32(framePtr + 336L, -getI64(framePtr + 336L));
                                }
                            }
                            if ((getI64(framePtr + 384L) & 0xffffffffL) == 3L) {
                                putI32(framePtr + 384L, 1L);
                            } else {
                                if ((long) (int) getI64(framePtr + 384L) < 0L) {
                                    putI32(framePtr + 384L, -getI64(framePtr + 384L));
                                }
                            }
                            firadd(framePtr + 336L, framePtr + 384L, framePtr + 384L, 0L);
                            isLess = fLT(CC_1divMachEp, framePtr + 384L) != 0L ? 1L : 0L;
                            if (isLess != 0L) {
                                tempVal1 = fmul_noNorm(framePtr + 352L, CC_MachEp, framePtr + 352L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 352L);
                                tempVal1 = fmul_noNorm(framePtr + 368L, CC_MachEp, framePtr + 368L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 368L);
                                tempVal1 = fmul_noNorm(framePtr + 304L, CC_MachEp, framePtr + 304L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 304L);
                                tempVal1 = fmul_noNorm(framePtr + 320L, CC_MachEp, framePtr + 320L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 320L);
                            }
                            if (isZero2 != 0L) {
                                tempVal1 = fmul_noNorm(framePtr + 352L, CC_1divMachEp, framePtr + 352L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 352L);
                                tempVal1 = fmul_noNorm(framePtr + 368L, CC_1divMachEp, framePtr + 368L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 368L);
                                tempVal1 = fmul_noNorm(framePtr + 304L, CC_1divMachEp, framePtr + 304L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 304L);
                                tempVal1 = fmul_noNorm(framePtr + 320L, CC_1divMachEp, framePtr + 320L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 320L);
                            }
                            status = (long) (int) (status - 1L);
                            if (status != 0L) {
                                step = 4;
                                continue stepLoop;
                            } else {
                                step = 3;
                                continue stepLoop;
                            }
                        }
                    }
                    case 5: {
                        tempVal1 = fmul_noNorm(framePtr + 256L, framePtr + 240L, framePtr + 384L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 384L);
                        tempVal1 = fmul_noNorm(framePtr + 32L, framePtr + 288L, framePtr + 336L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 336L);
                        tempVal1 = fmul_noNorm(framePtr + 336L, framePtr + 272L, framePtr + 336L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 336L);
                        fidiv(framePtr + 336L, framePtr + 384L, framePtr + 400L);
                        isZero2 = fZero(framePtr + 400L) != 0L ? 1L : 0L;
                        if (isZero2 == 0L) {
                            putI64(framePtr + 400L, (((getI32(framePtr + 404L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 400L) & 0xffffffffL));
                        }
                        tempVal1 = fmul_noNorm(framePtr + 352L, framePtr + 400L, framePtr + 384L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 384L);
                        firadd(framePtr + 384L, framePtr + 368L, framePtr + 384L, 0L);
                        tempVal1 = fmul_noNorm(framePtr + 400L, framePtr + 304L, framePtr + 336L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 336L);
                        firadd(framePtr + 336L, framePtr + 320L, framePtr + 336L, 0L);
                        putI64(framePtr + 360L, getI64(framePtr + 376L));
                        putI64(framePtr + 352L, getI64(framePtr + 368L));
                        putI64(framePtr + 376L, getI64(framePtr + 392L));
                        putI64(framePtr + 312L, getI64(framePtr + 328L));
                        putI64(framePtr + 304L, getI64(framePtr + 320L));
                        putI64(framePtr + 328L, getI64(framePtr + 344L));
                        putI64(framePtr + 320L, (((getI32(framePtr + 340L) & 0xffffffffL)) << 32) | (getI64(framePtr + 336L) & 0xffffffffL));
                        putI64(framePtr + 368L, (((getI32(framePtr + 388L) & 0xffffffffL)) << 32) | (getI64(framePtr + 384L) & 0xffffffffL));
                        tempVal1 = fmul_noNorm(framePtr + 192L, framePtr + 176L, framePtr + 384L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 384L);
                        tempVal1 = fmul_noNorm(framePtr + 32L, framePtr + 224L, framePtr + 336L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 336L);
                        tempVal1 = fmul_noNorm(framePtr + 336L, framePtr + 208L, framePtr + 336L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 336L);
                        fidiv(framePtr + 336L, framePtr + 384L, framePtr + 400L);
                        tempVal1 = fmul_noNorm(framePtr + 352L, framePtr + 400L, framePtr + 384L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 384L);
                        firadd(framePtr + 384L, framePtr + 368L, framePtr + 384L, 0L);
                        tempVal1 = fmul_noNorm(framePtr + 400L, framePtr + 304L, framePtr + 336L) & 0xffffffffL;
                        fNorm(tempVal1, framePtr + 336L);
                        firadd(framePtr + 336L, framePtr + 320L, framePtr + 336L, 0L);
                        putI64(framePtr + 360L, getI64(framePtr + 376L));
                        putI64(framePtr + 352L, getI64(framePtr + 368L));
                        putI64(framePtr + 376L, getI64(framePtr + 392L));
                        putI64(framePtr + 312L, getI64(framePtr + 328L));
                        putI64(framePtr + 304L, getI64(framePtr + 320L));
                        putI64(framePtr + 328L, getI64(framePtr + 344L));
                        putI64(framePtr + 320L, (((getI32(framePtr + 340L) & 0xffffffffL)) << 32) | (getI64(framePtr + 336L) & 0xffffffffL));
                        putI64(framePtr + 368L, (((getI32(framePtr + 388L) & 0xffffffffL)) << 32) | (getI64(framePtr + 384L) & 0xffffffffL));
                        isZero2 = fZero(framePtr + 336L) != 0L ? 1L : 0L;
                        if (isZero2 == 0L) {
                            fidiv(framePtr + 384L, framePtr + 336L, framePtr + 160L);
                        }
                        isZero2 = fZero(framePtr + 160L) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            putI64(framePtr + 152L, 0x100000000000000L);
                            putI64(framePtr + 144L, 1L);
                        } else {
                            firadd(framePtr + 64L, framePtr + 160L, framePtr + 144L, 1L);
                            fidiv(framePtr + 144L, framePtr + 160L, framePtr + 144L);
                            if ((long) (int) getI64(framePtr + 144L) == 3L) {
                                intVal = 1L;
                                putI64(framePtr + 144L, (((getI32(framePtr + 148L) & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
                            } else {
                                if ((long) (int) getI64(framePtr + 144L) < 0L) {
                                    intVal = (long) (int) -getI64(framePtr + 144L);
                                    putI64(framePtr + 144L, (((getI32(framePtr + 148L) & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
                                }
                            }
                            putI64(framePtr + 72L, getI64(framePtr + 168L));
                            putI64(framePtr + 64L, getI64(framePtr + 160L));
                        }
                        isZero2 = fLT(framePtr + 144L, framePtr + 128L) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            step = 6;
                            continue stepLoop;
                        } else {
                            firadd(framePtr + 288L, CC_1, framePtr + 288L, 0L);
                            firadd(framePtr + 272L, CC_N1, framePtr + 272L, 0L);
                            firadd(framePtr + 256L, CC_2, framePtr + 256L, 0L);
                            firadd(framePtr + 240L, CC_2, framePtr + 240L, 0L);
                            firadd(framePtr + 224L, CC_1, framePtr + 224L, 0L);
                            firadd(framePtr + 208L, CC_1, framePtr + 208L, 0L);
                            firadd(framePtr + 192L, CC_2, framePtr + 192L, 0L);
                            firadd(framePtr + 176L, CC_2, framePtr + 176L, 0L);
                            isZero2 = fMLT(framePtr + 336L, CC_MachEp) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                isZero2 = 1L;
                            } else {
                                isZero2 = fMLT(framePtr + 384L, CC_MachEp) != 0L ? 1L : 0L;
                            }
                            if ((getI64(framePtr + 336L) & 0xffffffffL) == 3L) {
                                putI32(framePtr + 336L, 1L);
                            } else {
                                if ((long) (int) getI64(framePtr + 336L) < 0L) {
                                    putI32(framePtr + 336L, -getI64(framePtr + 336L));
                                }
                            }
                            if ((getI64(framePtr + 384L) & 0xffffffffL) == 3L) {
                                putI32(framePtr + 384L, 1L);
                            } else {
                                if ((long) (int) getI64(framePtr + 384L) < 0L) {
                                    putI32(framePtr + 384L, -getI64(framePtr + 384L));
                                }
                            }
                            firadd(framePtr + 336L, framePtr + 384L, framePtr + 384L, 0L);
                            isLess = fLT(CC_1divMachEp, framePtr + 384L) != 0L ? 1L : 0L;
                            if (isLess != 0L) {
                                tempVal1 = fmul_noNorm(framePtr + 352L, CC_MachEp, framePtr + 352L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 352L);
                                tempVal1 = fmul_noNorm(framePtr + 368L, CC_MachEp, framePtr + 368L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 368L);
                                tempVal1 = fmul_noNorm(framePtr + 304L, CC_MachEp, framePtr + 304L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 304L);
                                tempVal1 = fmul_noNorm(framePtr + 320L, CC_MachEp, framePtr + 320L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 320L);
                            }
                            if (isZero2 != 0L) {
                                tempVal1 = fmul_noNorm(framePtr + 352L, CC_1divMachEp, framePtr + 352L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 352L);
                                tempVal1 = fmul_noNorm(framePtr + 368L, CC_1divMachEp, framePtr + 368L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 368L);
                                tempVal1 = fmul_noNorm(framePtr + 304L, CC_1divMachEp, framePtr + 304L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 304L);
                                tempVal1 = fmul_noNorm(framePtr + 320L, CC_1divMachEp, framePtr + 320L) & 0xffffffffL;
                                fNorm(tempVal1, framePtr + 320L);
                            }
                            status = (long) (int) (status - 1L);
                            if (status != 0L) {
                                step = 5;
                                continue stepLoop;
                            } else {
                                step = 6;
                                continue stepLoop;
                            }
                        }
                    }
                    case 6: {
                        fidiv(framePtr + 64L, framePtr + 48L, framePtr + 64L);
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

    long fiFBinom(long nPtr, long pPtr, long kPtr, long resultPtr) {
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long status3 = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            if ((long) (int) getI64(pPtr) < 0L) {
                status1 = 24L;
                tempVal = 0L;
                status3 = 0L;
                putI64(resultPtr + 8L, tempVal);
                putI64(resultPtr, status3);
                return status1;
            }
            isZero = fLT(CC_1, pPtr) != 0L ? 1L : 0L;
            if (isZero != 0L || (long) (int) getU32(nPtr) < 0L || (long) (int) comma(isZero = fLT(nPtr, kPtr) != 0L ? 1L : 0L, isZero) != 0L || comma(isZero = fiInteger(nPtr) != 0L ? 1L : 0L, isZero == 0L || comma(isZero = fiInteger(kPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
                status1 = 24L;
                tempVal = 0L;
                status3 = 0L;
                putI64(resultPtr + 8L, tempVal);
                putI64(resultPtr, status3);
                return status1;
            }
            isZero = fZero(kPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                firadd(CC_1, pPtr, resultPtr, 1L);
                status2 = fpow(resultPtr, nPtr, resultPtr, 0L);
                status1 = (long) (int) fNorm_L((long) (int) status2, resultPtr);
            } else {
                isZero = fEQ(kPtr, nPtr) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    tempVal = 0x100000000000000L;
                    status3 = 1L;
                    status1 = 0L;
                    putI64(resultPtr + 8L, tempVal);
                    putI64(resultPtr, status3);
                    return status1;
                }
                firadd(kPtr, CC_1, framePtr + 40L, 0L);
                firadd(nPtr, kPtr, framePtr + 24L, 1L);
                status1 = (long) (int) fiIBeta(framePtr + 40L, framePtr + 24L, pPtr, resultPtr);
                putI64(framePtr + 64L, getI64(resultPtr + 8L));
                putI64(framePtr + 56L, getI64(resultPtr));
                if ((long) (int) getI64(framePtr + 56L) == 3L) {
                    putI32(framePtr + 60L, getI64(framePtr + 56L) >>> 32);
                    putI64(framePtr + 56L, (((getI32(framePtr + 60L) & 0xffffffffL)) << 32) | 1L);
                }
                isZero = fZero(framePtr + 56L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putI64(framePtr + 56L, (((getI32(framePtr + 60L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 56L) & 0xffffffffL));
                }
                status3 = firadd(CC_1, framePtr + 56L, resultPtr, 0L);
                fNorm_L((long) (int) status3, resultPtr);
            }
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    long fiFStudent(long dfPtr, long tPtr, long resultPtr) {
        long isZero = 0L;
        long tempVal1 = 0L;
        long status = 0L;
        long constPtr = 0L;
        long tempVal2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            isZero = fLE(dfPtr, CC_1) != 0L ? 1L : 0L;
            if (isZero == 0L || (long) (int) comma(isZero = fiInteger(dfPtr) != 0L ? 1L : 0L, isZero) != 0L) {
                isZero = fZero(tPtr) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    fimul(tPtr, tPtr, framePtr + 40L);
                    firadd(dfPtr, framePtr + 40L, framePtr + 40L, 0L);
                    fidiv(dfPtr, framePtr + 40L, framePtr + 40L);
                    fidiv2(dfPtr, framePtr + 24L);
                    fiIBeta(framePtr + 24L, CC_0p5, framePtr + 40L, resultPtr);
                    fidiv2(resultPtr, resultPtr);
                    if ((long) (int) getU32(tPtr) < 0L) {
                        tempVal1 = 0L;
                    } else {
                        putI64(framePtr + 64L, getI64(resultPtr + 8L));
                        putI64(framePtr + 56L, getI64(resultPtr));
                        if ((long) (int) getI64(framePtr + 56L) == 3L) {
                            putI32(framePtr + 60L, getI64(framePtr + 56L) >>> 32);
                            putI64(framePtr + 56L, (((getI32(framePtr + 60L) & 0xffffffffL)) << 32) | 1L);
                        }
                        isZero = fZero(framePtr + 56L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            putI64(framePtr + 56L, (((getI32(framePtr + 60L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 56L) & 0xffffffffL));
                        }
                        isZero = fradd(CC_1, framePtr + 56L, resultPtr, 0L) != 0L ? 1L : 0L;
                        tempVal1 = isZero;
                    }
                    status = (long) (int) fNorm_L(tempVal1, resultPtr);
                    return status;
                }
                constPtr = CC_0p5;
                status = 0L;
            } else {
                constPtr = CC_NaN;
                status = 24L;
            }
            tempVal2 = getI64(constPtr);
            putI64(resultPtr + 8L, getI64(constPtr + 8L));
            putI64(resultPtr, tempVal2);
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long fiPStudent(long dfPtr, long tPtr, long resultPtr) {
        long isLess = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            isLess = fLT(dfPtr, CC_1) != 0L ? 1L : 0L;
            if (isLess == 0L || (long) (int) comma(isLess = fiInteger(dfPtr) != 0L ? 1L : 0L, isLess) != 0L) {
                fimul(tPtr, tPtr, framePtr + 40L);
                firadd(dfPtr, framePtr + 40L, framePtr + 40L, 0L);
                fidiv(dfPtr, framePtr + 40L, framePtr + 40L);
                fidiv2(dfPtr, framePtr + 24L);
                status = (long) (int) fiIBeta(framePtr + 24L, CC_0p5, framePtr + 40L, resultPtr);
                status = (long) (int) fNorm_L(status, resultPtr);
            } else {
                status = 24L;
                putI64(resultPtr + 8L, 0L);
                putI64(resultPtr, 0L);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long fiFSnedecor(long df1Ptr, long df2Ptr, long fValPtr, long resultPtr) {
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            isZero = fLT(df1Ptr, CC_1) != 0L ? 1L : 0L;
            if (isZero != 0L || (long) (int) comma(isZero = fLT(df2Ptr, CC_1) != 0L ? 1L : 0L, isZero) != 0L || comma(isZero = fiInteger(df1Ptr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L || comma(isZero = fiInteger(df2Ptr) != 0L ? 1L : 0L, isZero == 0L || (long) (int) comma(isZero = fLE(fValPtr, CC_0) != 0L ? 1L : 0L, isZero) != 0L ? 1L : 0L) != 0L) {
                status1 = 24L;
                putI64(resultPtr + 8L, 0L);
                putI64(resultPtr, 0L);
            } else {
                fimul(fValPtr, df1Ptr, framePtr + 56L);
                firadd(df2Ptr, framePtr + 56L, framePtr + 56L, 0L);
                fidiv(df2Ptr, framePtr + 56L, framePtr + 56L);
                fidiv2(df1Ptr, framePtr + 40L);
                fidiv2(df2Ptr, framePtr + 24L);
                fiIBeta(framePtr + 24L, framePtr + 40L, framePtr + 56L, resultPtr);
                putI64(framePtr + 80L, getI64(resultPtr + 8L));
                putI64(framePtr + 72L, getI64(resultPtr));
                if ((long) (int) getI64(framePtr + 72L) == 3L) {
                    putI32(framePtr + 76L, getI64(framePtr + 72L) >>> 32);
                    putI64(framePtr + 72L, (((getI32(framePtr + 76L) & 0xffffffffL)) << 32) | 1L);
                }
                isZero = fZero(framePtr + 72L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putI64(framePtr + 72L, (((getI32(framePtr + 76L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 72L) & 0xffffffffL));
                }
                status2 = firadd(CC_1, framePtr + 72L, resultPtr, 0L);
                status1 = (long) (int) fNorm_L((long) (int) status2, resultPtr);
            }
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    long fiPSnedecor(long df1Ptr, long df2Ptr, long fValPtr, long resultPtr) {
        long isLess = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            isLess = fLT(df1Ptr, CC_1) != 0L ? 1L : 0L;
            if (isLess != 0L || (long) (int) comma(isLess = fLT(df2Ptr, CC_1) != 0L ? 1L : 0L, isLess) != 0L || comma(isLess = fiInteger(df1Ptr) != 0L ? 1L : 0L, isLess == 0L ? 1L : 0L) != 0L || comma(isLess = fiInteger(df2Ptr) != 0L ? 1L : 0L, isLess == 0L || (long) (int) comma(isLess = fLE(fValPtr, CC_0) != 0L ? 1L : 0L, isLess) != 0L ? 1L : 0L) != 0L) {
                status = 24L;
                putI64(resultPtr + 8L, 0L);
                putI64(resultPtr, 0L);
            } else {
                fimul(fValPtr, df1Ptr, framePtr + 56L);
                firadd(df2Ptr, framePtr + 56L, framePtr + 56L, 0L);
                fidiv(df2Ptr, framePtr + 56L, framePtr + 56L);
                fidiv2(df1Ptr, framePtr + 40L);
                fidiv2(df2Ptr, framePtr + 24L);
                status = (long) (int) fiIBeta(framePtr + 24L, framePtr + 40L, framePtr + 56L, resultPtr);
                status = (long) (int) fNorm_L(status, resultPtr);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long fiIBetaSeries(long aPtr, long bPtr, long xPtr, long resultPtr) {
        long isLess = 0L;
        long dataPtr = 0L;
        long status = 0L;
        long index = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(288);
        try {
            fidiv(CC_1, aPtr, framePtr + 16L);
            firadd(CC_1, bPtr, framePtr + 80L, 1L);
            fimul(framePtr + 80L, xPtr, framePtr + 80L);
            firadd(aPtr, CC_1, framePtr + 64L, 0L);
            fidiv(framePtr + 80L, framePtr + 64L, framePtr + 64L);
            putI64(framePtr + 56L, getI64(framePtr + 72L));
            putI64(framePtr + 48L, getI64(framePtr + 64L));
            putI64(framePtr + 120L, 0L);
            putI64(framePtr + 112L, 1L);
            putI64(framePtr + 104L, getI64(framePtr + 88L));
            putI64(framePtr + 96L, getI64(framePtr + 80L));
            fimul(CC_MachEp, framePtr + 16L, framePtr + 32L);
            firadd(framePtr + 48L, framePtr + 16L, framePtr + 48L, 0L);
            isLess = fMLT(framePtr + 32L, framePtr + 64L) != 0L ? 1L : 0L;
            if (isLess != 0L) {
                index = 2L;
                do {
                    dataPtr = fIntToCC(index, framePtr + 16L);
                    firadd(dataPtr, bPtr, framePtr + 80L, 1L);
                    fimul(framePtr + 80L, xPtr, framePtr + 80L);
                    fidiv(framePtr + 80L, framePtr + 16L, framePtr + 80L);
                    fimul(framePtr + 96L, framePtr + 80L, framePtr + 96L);
                    firadd(aPtr, framePtr + 16L, framePtr + 64L, 0L);
                    fidiv(framePtr + 96L, framePtr + 64L, framePtr + 64L);
                    firadd(framePtr + 112L, framePtr + 64L, framePtr + 112L, 0L);
                    index = (long) (int) (index + 1L);
                    isLess = fMLT(framePtr + 32L, framePtr + 64L) != 0L ? 1L : 0L;
                } while (isLess != 0L);
            }
            firadd(framePtr + 112L, framePtr + 48L, framePtr + 112L, 0L);
            filn(xPtr, framePtr + 80L);
            fimul(aPtr, framePtr + 80L, framePtr + 80L);
            firadd(aPtr, bPtr, framePtr + 16L, 0L);
            isLess = fLT(framePtr + 16L, CC_MaxGamma) != 0L ? 1L : 0L;
            if (isLess != 0L && (long) (int) comma(isLess = fMLT(framePtr + 80L, CC_MaxLog) != 0L ? 1L : 0L, isLess) != 0L) {
                fiGamma(aPtr, framePtr + 16L);
                fiGamma(bPtr, framePtr + 96L);
                fimul(framePtr + 16L, framePtr + 96L, framePtr + 16L);
                firadd(aPtr, bPtr, framePtr + 96L, 0L);
                fiGamma(framePtr + 96L, framePtr + 96L);
                fidiv(framePtr + 96L, framePtr + 16L, framePtr + 96L);
                fpow(xPtr, aPtr, framePtr + 16L, 1L);
                fimul(framePtr + 96L, framePtr + 16L, framePtr + 16L);
                status = fimul(framePtr + 112L, framePtr + 16L, framePtr + 112L);
            } else {
                filnGamma(framePtr + 16L, framePtr + 16L);
                filnGamma(aPtr, framePtr + 96L);
                firadd(framePtr + 16L, framePtr + 96L, framePtr + 16L, 1L);
                filnGamma(bPtr, framePtr + 96L);
                firadd(framePtr + 16L, framePtr + 96L, framePtr + 96L, 1L);
                firadd(framePtr + 96L, framePtr + 80L, framePtr + 96L, 0L);
                filn(framePtr + 112L, framePtr + 112L);
                firadd(framePtr + 96L, framePtr + 112L, framePtr + 96L, 0L);
                isLess = fLT(framePtr + 96L, CC_MinLog) != 0L ? 1L : 0L;
                status = isLess;
                if (isLess != 0L) {
                    putI64(framePtr + 120L, 0L);
                    putI64(framePtr + 112L, 1L);
                } else {
                    isLess = fiexpm(framePtr + 96L, framePtr + 112L, 0L) != 0L ? 1L : 0L;
                    status = isLess;
                }
            }
            putI64(resultPtr + 8L, getI64(framePtr + 120L));
            putI64(resultPtr, getI64(framePtr + 112L));
            return status;
        } finally {
            sp = savedSp;
        }
    }

    void fiGamma(long xPtr, long resultPtr) {
        long isZero = 0L;
        long intVal1 = 0L;
        long constPtr = 0L;
        long intVal2 = 0L;
        long tempVal = 0L;
        long mantissa = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            intVal2 = 0L;
            if ((long) (int) getI64(xPtr) != -2L && (long) (int) getI64(xPtr) != 0L) {
                isZero = fLT(CC_0, xPtr) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    intVal2 = 1L;
                } else {
                    putI64(framePtr + 40L, getI64(xPtr + 8L));
                    putI64(framePtr + 32L, getI64(xPtr));
                    if ((getI64(framePtr + 32L) & 0xffffffffL) == 3L) {
                        intVal2 = 1L;
                        putI32(framePtr + 36L, getI64(framePtr + 32L) >>> 32);
                        putI64(framePtr + 32L, (((getI32(framePtr + 36L) & 0xffffffffL)) << 32) | (intVal2 & 0xffffffffL));
                    } else {
                        if ((long) (int) getI64(framePtr + 32L) < 0L) {
                            intVal2 = (long) (int) -getI64(framePtr + 32L);
                            putI32(framePtr + 36L, getI64(framePtr + 32L) >>> 32);
                            putI64(framePtr + 32L, (((getI32(framePtr + 36L) & 0xffffffffL)) << 32) | (intVal2 & 0xffffffffL));
                        }
                    }
                    intVal1 = (long) (int) fiParity(framePtr + 32L);
                    intVal2 = 1L;
                    if (intVal1 != 1L) {
                        intVal2 = -1L;
                    }
                }
            }
            isZero = fZero(xPtr) != 0L ? 1L : 0L;
            if (isZero != 0L || (long) (int) getI64(xPtr) < 0L && (long) (int) comma(isZero = fiInteger(xPtr) != 0L ? 1L : 0L, isZero) != 0L) {
                if (intVal2 == 1L) {
                    constPtr = CC_PInf;
                    tempVal = getI64(constPtr);
                    mantissa = getI64(constPtr + 8L);
                    putI64(resultPtr + 8L, mantissa);
                    putI64(resultPtr, tempVal);
                    return;
                }
                constPtr = CC_NInf;
                tempVal = getI64(constPtr);
                mantissa = getI64(constPtr + 8L);
                putI64(resultPtr + 8L, mantissa);
                putI64(resultPtr, tempVal);
                return;
            }
            isZero = fLT(CC_MaxGamma, xPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                constPtr = CC_PInf;
                tempVal = getI64(constPtr);
                mantissa = getI64(constPtr + 8L);
                putI64(resultPtr + 8L, mantissa);
                putI64(resultPtr, tempVal);
                return;
            }
            putI64(framePtr + 40L, getI64(xPtr + 8L));
            putI64(framePtr + 32L, getI64(xPtr));
            if ((getI64(framePtr + 32L) & 0xffffffffL) == 3L) {
                intVal1 = 1L;
                putI32(framePtr + 36L, getI64(framePtr + 32L) >>> 32);
                putI64(framePtr + 32L, (((getI32(framePtr + 36L) & 0xffffffffL)) << 32) | (intVal1 & 0xffffffffL));
            } else {
                if ((long) (int) getI64(framePtr + 32L) < 0L) {
                    intVal1 = (long) (int) -getI64(framePtr + 32L);
                    putI32(framePtr + 36L, getI64(framePtr + 32L) >>> 32);
                    putI64(framePtr + 32L, (((getI32(framePtr + 36L) & 0xffffffffL)) << 32) | (intVal1 & 0xffffffffL));
                }
            }
            isZero = fLT(CC_13, framePtr + 32L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI64(framePtr + 40L, getI64(xPtr + 8L));
                putI64(framePtr + 32L, getI64(xPtr));
                putI64(framePtr + 24L, 0x100000000000000L);
                putI64(framePtr + 16L, 1L);
                isZero = fLE(CC_3, framePtr + 32L) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    do {
                        firadd(framePtr + 32L, CC_N1, framePtr + 32L, 0L);
                        fimul(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                        isZero = fLE(CC_3, framePtr + 32L) != 0L ? 1L : 0L;
                    } while (isZero != 0L);
                }
                isZero = fLT(framePtr + 32L, ADDR_RODATA + 4344L) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    do {
                        fidiv(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                        firadd(framePtr + 32L, CC_1, framePtr + 32L, 0L);
                        isZero = fLT(framePtr + 32L, ADDR_RODATA + 4344L) != 0L ? 1L : 0L;
                    } while (isZero != 0L);
                }
                isZero = fLE(framePtr + 32L, ADDR_RODATA + 4360L) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    fiGammaPoly(framePtr + 32L, framePtr + 16L, resultPtr);
                } else {
                    isZero = fLT(framePtr + 32L, CC_2) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        do {
                            fidiv(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                            firadd(framePtr + 32L, CC_1, framePtr + 32L, 0L);
                            isZero = fLT(framePtr + 32L, CC_2) != 0L ? 1L : 0L;
                        } while (isZero != 0L);
                    }
                    isZero = fEQ(framePtr + 32L, CC_2) != 0L ? 1L : 0L;
                    tempVal = getI64(framePtr + 16L);
                    mantissa = getI64(framePtr + 24L);
                    if (!(isZero == 0L && comma(comma(comma(isZero = fEQ(framePtr + 32L, CC_3) != 0L ? 1L : 0L, tempVal = getI64(framePtr + 16L)), mantissa = getI64(framePtr + 24L)), isZero == 0L ? 1L : 0L) != 0L)) {
                        putI64(resultPtr + 8L, mantissa);
                        putI64(resultPtr, tempVal);
                        return;
                    }
                    firadd(framePtr + 32L, CC_N2, framePtr + 32L, 0L);
                    putI64(resultPtr + 8L, 0x421276048747162L);
                    putI64(resultPtr, 0xfffffffb00000001L);
                    fimul(resultPtr, framePtr + 32L, resultPtr);
                    firadd(resultPtr, ADDR_RODATA + 4712L, resultPtr, 0L);
                    fimul(resultPtr, framePtr + 32L, resultPtr);
                    firadd(resultPtr, ADDR_RODATA + 4728L, resultPtr, 0L);
                    fimul(resultPtr, framePtr + 32L, resultPtr);
                    firadd(resultPtr, ADDR_RODATA + 4744L, resultPtr, 0L);
                    fimul(resultPtr, framePtr + 32L, resultPtr);
                    firadd(resultPtr, ADDR_RODATA + 4760L, resultPtr, 0L);
                    fimul(resultPtr, framePtr + 32L, resultPtr);
                    firadd(resultPtr, ADDR_RODATA + 4776L, resultPtr, 0L);
                    fimul(resultPtr, framePtr + 32L, resultPtr);
                    firadd(resultPtr, ADDR_RODATA + 4792L, resultPtr, 0L);
                    fimul(resultPtr, framePtr + 32L, resultPtr);
                    firadd(resultPtr, ADDR_RODATA + 4808L, resultPtr, 0L);
                    fimul(framePtr + 16L, resultPtr, resultPtr);
                    putI64(framePtr + 24L, 0x139714851747617L);
                    putI64(framePtr + 16L, 0xfffffffbffffffffL);
                    fimul(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4872L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4888L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4904L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4920L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4936L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4952L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4968L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4984L, framePtr + 16L, 0L);
                    fidiv(resultPtr, framePtr + 16L, resultPtr);
                }
                return;
            }
            if ((long) (int) getI64(xPtr) < 0L) {
                putI64(framePtr + 24L, getI64(framePtr + 40L));
                putI64(framePtr + 16L, getI64(framePtr + 32L));
                fiFP(framePtr + 32L, framePtr + 32L);
                isZero = fLT(CC_0p5, framePtr + 16L) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    firadd(framePtr + 16L, CC_N1, framePtr + 16L, 0L);
                }
                fimul(CC_PI, framePtr + 16L, framePtr + 16L);
                fitrig(framePtr + 16L, framePtr + 16L, 3L, 0L);
                fimul(framePtr + 32L, framePtr + 16L, framePtr + 16L);
                if ((long) (int) getI64(framePtr + 16L) == 3L) {
                    intVal1 = 1L;
                    putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | (intVal1 & 0xffffffffL));
                } else {
                    if ((long) (int) getI64(framePtr + 16L) < 0L) {
                        intVal1 = (long) (int) -getI64(framePtr + 16L);
                        putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | (intVal1 & 0xffffffffL));
                    }
                }
                fiGammaAsymptotic(framePtr + 32L, resultPtr);
                fimul(framePtr + 16L, resultPtr, framePtr + 16L);
                fidiv(CC_PI, framePtr + 16L, framePtr + 16L);
            } else {
                fiGammaAsymptotic(xPtr, framePtr + 16L);
            }
            putI64(resultPtr + 8L, getI64(framePtr + 24L));
            putI64(resultPtr, getI64(framePtr + 16L));
            if (intVal2 == -1L && comma(isZero = fZero(resultPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
                putI32(resultPtr, -getI64(resultPtr));
            }
        } finally {
            sp = savedSp;
        }
    }

    long filnGamma(long xPtr, long resultPtr) {
        long isZero = 0L;
        long status = 0L;
        long intVal = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(240);
        try {
            isZero = fZero(xPtr) != 0L ? 1L : 0L;
            status = isZero;
            if (isZero != 0L) {
                tempVal1 = 0xc34f00000002L;
                tempVal2 = 0x999999999999999L;
                putI64(resultPtr + 8L, tempVal2);
                putI64(resultPtr, tempVal1);
                return status;
            }
            if ((long) (int) getI64(xPtr) < 0L) {
                isZero = fiInteger(xPtr) != 0L ? 1L : 0L;
                status = isZero;
                if (isZero != 0L) {
                    tempVal1 = 0xc34f00000002L;
                    tempVal2 = 0x999999999999999L;
                    putI64(resultPtr + 8L, tempVal2);
                    putI64(resultPtr, tempVal1);
                    return status;
                }
            }
            isZero = fLT(CC_MaxlnGamma, xPtr) != 0L ? 1L : 0L;
            status = isZero;
            if (isZero != 0L) {
                tempVal1 = 0xc34f00000002L;
                tempVal2 = 0x999999999999999L;
                putI64(resultPtr + 8L, tempVal2);
                putI64(resultPtr, tempVal1);
                return status;
            }
            putI64(framePtr + 72L, getI64(xPtr + 8L));
            putI64(framePtr + 64L, getI64(xPtr));
            if ((getI64(framePtr + 64L) & 0xffffffffL) == 3L) {
                intVal = 1L;
                status = getI64(framePtr + 64L) >>> 32;
                putI64(framePtr + 64L, (((status & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
            } else {
                if ((long) (int) getI64(framePtr + 64L) < 0L) {
                    intVal = (long) (int) -getI64(framePtr + 64L);
                    status = getI64(framePtr + 64L) >>> 32;
                    putI64(framePtr + 64L, (((status & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
                }
            }
            isZero = fLT(CC_34, framePtr + 64L) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                if ((long) (int) getI64(xPtr) < 0L) {
                    fiFP(framePtr + 64L, framePtr + 48L);
                    isZero = fLT(CC_0p5, framePtr + 48L) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        firadd(framePtr + 48L, CC_N1, framePtr + 48L, 0L);
                    }
                    fimul(CC_PI, framePtr + 48L, framePtr + 48L);
                    fitrig(framePtr + 48L, framePtr + 48L, 3L, 0L);
                    fimul(framePtr + 64L, framePtr + 48L, framePtr + 48L);
                    isZero = fZero(framePtr + 48L) != 0L ? 1L : 0L;
                    status = isZero;
                    if (isZero != 0L) {
                        tempVal1 = 0xc34f00000002L;
                        tempVal2 = 0x999999999999999L;
                        putI64(resultPtr + 8L, tempVal2);
                        putI64(resultPtr, tempVal1);
                        return status;
                    }
                    filn(framePtr + 48L, framePtr + 48L);
                    firadd(CC_lnPI, framePtr + 48L, framePtr + 48L, 1L);
                    filnGammaAsymptotic(framePtr + 64L, resultPtr);
                    status = firadd(framePtr + 48L, resultPtr, resultPtr, 1L);
                    return status;
                }
                filnGammaAsymptotic(xPtr, framePtr + 64L);
                status = 0L;
                tempVal1 = getI64(framePtr + 64L);
                tempVal2 = getI64(framePtr + 72L);
                putI64(resultPtr + 8L, tempVal2);
                putI64(resultPtr, tempVal1);
                return status;
            }
            isZero = fLT(xPtr, CC_13) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                filnGammaAsymptotic(xPtr, framePtr + 48L);
                status = 0L;
                tempVal1 = getI64(framePtr + 48L);
                tempVal2 = getI64(framePtr + 56L);
                putI64(resultPtr + 8L, tempVal2);
                putI64(resultPtr, tempVal1);
                return status;
            }
            putI64(framePtr + 72L, getI64(xPtr + 8L));
            putI64(framePtr + 64L, getI64(xPtr));
            putI64(framePtr + 56L, 0x100000000000000L);
            putI64(framePtr + 48L, 1L);
            isZero = fLE(CC_3, framePtr + 64L) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                do {
                    firadd(framePtr + 64L, CC_N1, framePtr + 64L, 0L);
                    fimul(framePtr + 48L, framePtr + 64L, framePtr + 48L);
                    isZero = fLE(CC_3, framePtr + 64L) != 0L ? 1L : 0L;
                } while (isZero != 0L);
            }
            isZero = fLT(framePtr + 64L, CC_2) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                do {
                    isZero = fMLE(framePtr + 64L, ADDR_RODATA + 4360L) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        fiGammaPoly(framePtr + 64L, framePtr + 48L, resultPtr);
                        intVal = (long) (int) getI64(resultPtr);
                        if (intVal == 3L) {
                            intVal = 1L;
                            putI32(resultPtr, intVal);
                        } else {
                            if (intVal < 0L) {
                                intVal = (long) (int) -intVal;
                                putI32(resultPtr, intVal);
                            }
                        }
                        status = filn(resultPtr, resultPtr);
                        return status;
                    }
                    fidiv(framePtr + 48L, framePtr + 64L, framePtr + 48L);
                    firadd(framePtr + 64L, CC_1, framePtr + 64L, 0L);
                    isZero = fLT(framePtr + 64L, CC_2) != 0L ? 1L : 0L;
                } while (isZero != 0L);
            }
            if ((long) (int) getI64(framePtr + 48L) == 3L) {
                intVal = 1L;
                putI64(framePtr + 48L, (((getI32(framePtr + 52L) & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
            } else {
                if ((long) (int) getI64(framePtr + 48L) < 0L) {
                    intVal = (long) (int) -getI64(framePtr + 48L);
                    putI64(framePtr + 48L, (((getI32(framePtr + 52L) & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
                }
            }
            isZero = fEQ(framePtr + 64L, CC_2) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                status = fln(framePtr + 48L, resultPtr);
            } else {
                firadd(framePtr + 64L, CC_N2, framePtr + 64L, 0L);
                putI64(framePtr + 40L, 0x216369082764381L);
                putI64(framePtr + 32L, 0x3ffffffffL);
                fimul(framePtr + 32L, framePtr + 64L, framePtr + 32L);
                firadd(framePtr + 32L, ADDR_RODATA + 5208L, framePtr + 32L, 0L);
                fimul(framePtr + 32L, framePtr + 64L, framePtr + 32L);
                firadd(framePtr + 32L, ADDR_RODATA + 5224L, framePtr + 32L, 0L);
                fimul(framePtr + 32L, framePtr + 64L, framePtr + 32L);
                firadd(framePtr + 32L, ADDR_RODATA + 5240L, framePtr + 32L, 0L);
                fimul(framePtr + 32L, framePtr + 64L, framePtr + 32L);
                firadd(framePtr + 32L, ADDR_RODATA + 5256L, framePtr + 32L, 0L);
                fimul(framePtr + 32L, framePtr + 64L, framePtr + 32L);
                firadd(framePtr + 32L, ADDR_RODATA + 5272L, framePtr + 32L, 0L);
                fimul(framePtr + 32L, framePtr + 64L, framePtr + 32L);
                firadd(framePtr + 32L, ADDR_RODATA + 5288L, framePtr + 32L, 0L);
                putI64(framePtr + 88L, 0x513948148443537L);
                putI64(framePtr + 80L, 0x2ffffffffL);
                firadd(framePtr + 64L, ADDR_RODATA + 5352L, framePtr + 80L, 0L);
                fimul(framePtr + 80L, framePtr + 64L, framePtr + 80L);
                firadd(framePtr + 80L, ADDR_RODATA + 5368L, framePtr + 80L, 0L);
                fimul(framePtr + 80L, framePtr + 64L, framePtr + 80L);
                firadd(framePtr + 80L, ADDR_RODATA + 5384L, framePtr + 80L, 0L);
                fimul(framePtr + 80L, framePtr + 64L, framePtr + 80L);
                firadd(framePtr + 80L, ADDR_RODATA + 5400L, framePtr + 80L, 0L);
                fimul(framePtr + 80L, framePtr + 64L, framePtr + 80L);
                firadd(framePtr + 80L, ADDR_RODATA + 5416L, framePtr + 80L, 0L);
                fimul(framePtr + 80L, framePtr + 64L, framePtr + 80L);
                firadd(framePtr + 80L, ADDR_RODATA + 5432L, framePtr + 80L, 0L);
                fimul(framePtr + 80L, framePtr + 64L, framePtr + 80L);
                firadd(framePtr + 80L, ADDR_RODATA + 5448L, framePtr + 80L, 0L);
                putI64(framePtr + 24L, getI64(framePtr + 88L));
                putI64(framePtr + 16L, getI64(framePtr + 80L));
                fimul(framePtr + 64L, framePtr + 32L, resultPtr);
                fidiv(resultPtr, framePtr + 16L, resultPtr);
                fln(framePtr + 48L, framePtr + 48L);
                status = firadd(resultPtr, framePtr + 48L, resultPtr, 0L);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long fiSgnGamma(long xPtr) {
        long tempVal = 0L;
        long isLess = 0L;
        long result = 0L;
        long intVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            result = 0L;
            if (getI32(xPtr) == -2L || getI32(xPtr) == 0L) {
                return result;
            }
            isLess = fLT(CC_0, xPtr) != 0L ? 1L : 0L;
            if (isLess != 0L) {
                result = 1L;
                return result;
            }
            putI64(framePtr + 24L, getI64(xPtr + 8L));
            putI64(framePtr + 16L, getI64(xPtr));
            if ((long) (int) getI64(framePtr + 16L) == 3L) {
                intVal = 1L;
                tempVal = getI64(framePtr + 16L) >>> 32;
                putI64(framePtr + 16L, (((tempVal & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
            } else {
                if ((long) (int) getI64(framePtr + 16L) < 0L) {
                    intVal = (long) (int) -getI64(framePtr + 16L);
                    tempVal = getI64(framePtr + 16L) >>> 32;
                    putI64(framePtr + 16L, (((tempVal & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
                }
            }
            intVal = (long) (int) fiParity(framePtr + 16L);
            result = 1L;
            if (intVal != 1L) {
                result = 0xffffffffL;
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    void fiGammaAsymptotic(long xPtr, long resultPtr) {
        long isLess = 0L;
        long dataPtr1 = 0L;
        long dataPtr2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            dataPtr1 = framePtr + 16L;
            firadd(xPtr, CC_N0p5, framePtr + 16L, 0L);
            filn(xPtr, framePtr + 40L);
            fimul(framePtr + 16L, framePtr + 40L, framePtr + 16L);
            firadd(framePtr + 16L, xPtr, framePtr + 16L, 1L);
            fidiv(CC_1, xPtr, framePtr + 40L);
            isLess = fLT(CC_1024, xPtr) != 0L ? 1L : 0L;
            fiexpm(framePtr + 16L, resultPtr, 0L);
            if (isLess != 0L) {
                fimul(framePtr + 40L, ADDR_RODATA + 4088L, framePtr + 16L);
                firadd(framePtr + 16L, ADDR_RODATA + 4104L, framePtr + 16L, 0L);
                fimul(framePtr + 16L, framePtr + 40L, framePtr + 16L);
                firadd(framePtr + 40L, ADDR_RODATA + 4120L, framePtr + 16L, 0L);
                fimul(framePtr + 16L, framePtr + 40L, framePtr + 16L);
                firadd(framePtr + 40L, ADDR_RODATA + 4136L, framePtr + 16L, 0L);
                fimul(framePtr + 16L, framePtr + 40L, framePtr + 16L);
                firadd(framePtr + 40L, ADDR_RODATA + 4152L, framePtr + 16L, 0L);
                fimul(framePtr + 16L, framePtr + 40L, framePtr + 16L);
                dataPtr2 = ADDR_RODATA + 4168L;
                dataPtr1 = framePtr + 40L;
            } else {
                putI64(framePtr + 24L, 0x714739137814361L);
                putI64(framePtr + 16L, 0xfffffffc00000001L);
                fimul(framePtr + 16L, framePtr + 40L, framePtr + 16L);
                firadd(framePtr + 16L, ADDR_RODATA + 4200L, framePtr + 16L, 0L);
                fimul(framePtr + 16L, framePtr + 40L, framePtr + 16L);
                firadd(framePtr + 16L, ADDR_RODATA + 4216L, framePtr + 16L, 0L);
                fimul(framePtr + 16L, framePtr + 40L, framePtr + 16L);
                firadd(framePtr + 16L, ADDR_RODATA + 4232L, framePtr + 16L, 0L);
                fimul(framePtr + 16L, framePtr + 40L, framePtr + 16L);
                firadd(framePtr + 16L, ADDR_RODATA + 4248L, framePtr + 16L, 0L);
                fimul(framePtr + 16L, framePtr + 40L, framePtr + 16L);
                firadd(framePtr + 16L, ADDR_RODATA + 4264L, framePtr + 16L, 0L);
                fimul(framePtr + 16L, framePtr + 40L, framePtr + 16L);
                firadd(framePtr + 16L, ADDR_RODATA + 4280L, framePtr + 16L, 0L);
                fimul(framePtr + 16L, framePtr + 40L, framePtr + 16L);
                firadd(framePtr + 16L, ADDR_RODATA + 4296L, framePtr + 16L, 0L);
                fimul(framePtr + 16L, framePtr + 40L, framePtr + 16L);
                dataPtr2 = ADDR_RODATA + 4312L;
            }
            firadd(dataPtr1, dataPtr2, framePtr + 16L, 0L);
            fimul(framePtr + 40L, framePtr + 16L, framePtr + 40L);
            firadd(CC_1, framePtr + 40L, framePtr + 40L, 0L);
            fimul(CC_Sqrt2PI, resultPtr, resultPtr);
            fimul(resultPtr, framePtr + 40L, resultPtr);
        } finally {
            sp = savedSp;
        }
    }

    void fiGammaPoly(long xPtr, long polyTablePtr, long resultPtr) {
        long isZero = 0L;
        long dataPtr1 = 0L;
        long dataPtr2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            isZero = fZero(xPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                putU32(resultPtr + 8L, 0x99999999L);
                putU32(resultPtr + 12L, 0x9999999L);
                putU32(resultPtr, 2L);
                putU32(resultPtr + 4L, 49999L);
            } else {
                if ((long) (int) getU32(xPtr) < 0L) {
                    fichs2(xPtr, resultPtr);
                    dataPtr2 = ADDR_RODATA + 4376L;
                    putI64(framePtr + 24L, 0x113337416724389L);
                    putI64(framePtr + 16L, 0xfffffffd00000001L);
                    fimul(framePtr + 16L, resultPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4392L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, resultPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4408L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, resultPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4424L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, resultPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4440L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, resultPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4456L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, resultPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4472L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, resultPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4488L, framePtr + 16L, 0L);
                    dataPtr1 = resultPtr;
                } else {
                    dataPtr2 = ADDR_RODATA + 4536L;
                    putI64(framePtr + 24L, 0x119394505138151L);
                    putI64(framePtr + 16L, 0xfffffffdffffffffL);
                    fimul(framePtr + 16L, xPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4552L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, xPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4568L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, xPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4584L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, xPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4600L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, xPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4616L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, xPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4632L, framePtr + 16L, 0L);
                    fimul(framePtr + 16L, xPtr, framePtr + 16L);
                    firadd(framePtr + 16L, ADDR_RODATA + 4648L, framePtr + 16L, 0L);
                    dataPtr1 = xPtr;
                }
                fimul(framePtr + 16L, dataPtr1, framePtr + 16L);
                firadd(framePtr + 16L, dataPtr2 + 128L, framePtr + 16L, 0L);
                fimul(xPtr, framePtr + 16L, resultPtr);
                fidiv(polyTablePtr, resultPtr, resultPtr);
            }
        } finally {
            sp = savedSp;
        }
    }

    void filnGammaAsymptotic(long xPtr, long resultPtr) {
        long isLess = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            filn(xPtr, framePtr + 24L);
            firadd(xPtr, CC_N0p5, framePtr + 40L, 0L);
            fimul(framePtr + 24L, framePtr + 40L, framePtr + 40L);
            firadd(framePtr + 40L, xPtr, framePtr + 40L, 1L);
            firadd(framePtr + 40L, ADDR_RODATA + 5016L, framePtr + 40L, 0L);
            isLess = fLT(CC_1E10, xPtr) != 0L ? 1L : 0L;
            if (isLess != 0L) {
                putI64(resultPtr + 8L, getI64(framePtr + 48L));
                putI64(resultPtr, getI64(framePtr + 40L));
            } else {
                fimul(xPtr, xPtr, framePtr + 24L);
                fidiv(CC_1, framePtr + 24L, framePtr + 24L);
                putI64(resultPtr + 8L, 0x488502614243227L);
                putI64(resultPtr, 0xfffffffd00000001L);
                fimul(resultPtr, framePtr + 24L, resultPtr);
                firadd(resultPtr, ADDR_RODATA + 5048L, resultPtr, 0L);
                fimul(resultPtr, framePtr + 24L, resultPtr);
                firadd(resultPtr, ADDR_RODATA + 5064L, resultPtr, 0L);
                fimul(resultPtr, framePtr + 24L, resultPtr);
                firadd(resultPtr, ADDR_RODATA + 5080L, resultPtr, 0L);
                fimul(resultPtr, framePtr + 24L, resultPtr);
                firadd(resultPtr, ADDR_RODATA + 5096L, resultPtr, 0L);
                fimul(resultPtr, framePtr + 24L, resultPtr);
                firadd(resultPtr, ADDR_RODATA + 5112L, resultPtr, 0L);
                fimul(resultPtr, framePtr + 24L, resultPtr);
                firadd(resultPtr, ADDR_RODATA + 5128L, resultPtr, 0L);
                fidiv(resultPtr, xPtr, resultPtr);
                firadd(framePtr + 40L, resultPtr, resultPtr, 0L);
            }
        } finally {
            sp = savedSp;
        }
    }

    long fiPPoisson(long lambdaPtr, long kPtr, long resultPtr) {
        long isZero = 0L;
        long intVal = 0L;
        long result = 0L;
        long index = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            fichs2(lambdaPtr, framePtr + 32L);
            isZero = fLE(lambdaPtr, CC_0) != 0L ? 1L : 0L;
            if (isZero != 0L || getI32(kPtr) < 0L || (long) (int) comma(isZero = fLT(CC_MaxInt, kPtr) != 0L ? 1L : 0L, isZero) != 0L) {
                result = 24L;
                tempVal = 0L;
            } else {
                isZero = fLT(framePtr + 32L, CC_MinLog) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    isZero = fZero(kPtr) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        fiexpm(framePtr + 32L, resultPtr, 0L);
                    } else {
                        putI64(framePtr + 40L, getI64(lambdaPtr + 8L));
                        putI64(framePtr + 32L, getI64(lambdaPtr));
                        intVal = (long) (int) fCCToInt(kPtr);
                        if (1L < intVal) {
                            index = 1L;
                            do {
                                index = (long) (int) (index + 1L);
                                fIntToCC(index, framePtr + 16L);
                                fimul(framePtr + 32L, lambdaPtr, framePtr + 32L);
                                fidiv(framePtr + 32L, framePtr + 16L, framePtr + 32L);
                            } while (intVal != index);
                        }
                        fichs2(lambdaPtr, resultPtr);
                        fiexpm(resultPtr, resultPtr, 0L);
                        fimul(resultPtr, framePtr + 32L, resultPtr);
                    }
                    result = 0L;
                    return result;
                }
                tempVal = 1L;
                result = 0L;
            }
            putU32(resultPtr + 8L, 0L);
            putU32(resultPtr + 12L, 0L);
            putI64(resultPtr, tempVal);
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long fiFGamma(long aPtr, long xPtr, long isComplement, long resultPtr) {
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            fimul(xPtr, isComplement, framePtr + 24L);
            result = fiIGamma(aPtr, framePtr + 24L, resultPtr);
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long fiIGamma(long aPtr, long xPtr, long resultPtr) {
        long isLess = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(240);
        try {
            isLess = fLE(xPtr, CC_0) != 0L ? 1L : 0L;
            if (isLess != 0L) {
                status = 0L;
                putU32(resultPtr + 8L, 0L);
                putU32(resultPtr + 12L, 0L);
                putU32(resultPtr, 1L);
                putU32(resultPtr + 4L, 0L);
                return status;
            }
            isLess = fLT(aPtr, CC_0) != 0L ? 1L : 0L;
            if (isLess != 0L) {
                status = 0L;
                putU32(resultPtr + 8L, 0L);
                putU32(resultPtr + 12L, 0L);
                putU32(resultPtr, 1L);
                putU32(resultPtr + 4L, 0L);
                return status;
            }
            isLess = fLT(CC_1, xPtr) != 0L ? 1L : 0L;
            if (isLess != 0L && (long) (int) comma(isLess = fLT(aPtr, xPtr) != 0L ? 1L : 0L, isLess) != 0L) {
                fiJGamma(aPtr, xPtr, resultPtr);
                status = firadd(CC_1, resultPtr, resultPtr, 1L);
            } else {
                filn(xPtr, framePtr + 64L);
                fimul(aPtr, framePtr + 64L, framePtr + 64L);
                firadd(framePtr + 64L, xPtr, framePtr + 64L, 1L);
                filnGamma(aPtr, framePtr + 16L);
                firadd(framePtr + 64L, framePtr + 16L, framePtr + 64L, 1L);
                isLess = fLT(framePtr + 64L, CC_MinLog) != 0L ? 1L : 0L;
                if (isLess != 0L) {
                    status = 1L;
                    putU32(resultPtr + 8L, 0L);
                    putU32(resultPtr + 12L, 0L);
                    putU32(resultPtr, 1L);
                    putU32(resultPtr + 4L, 0L);
                    return status;
                }
                fiexpm(framePtr + 64L, framePtr + 64L, 0L);
                putI64(framePtr + 40L, getI64(aPtr + 8L));
                putI64(framePtr + 32L, getI64(aPtr));
                putI64(framePtr + 56L, 0x100000000000000L);
                putI64(framePtr + 48L, 1L);
                putI64(framePtr + 80L, getI64(framePtr + 48L));
                putI64(framePtr + 88L, getI64(framePtr + 56L));
                do {
                    firadd(framePtr + 32L, CC_1, framePtr + 32L, 0L);
                    fimul(framePtr + 48L, xPtr, framePtr + 48L);
                    fidiv(framePtr + 48L, framePtr + 32L, framePtr + 48L);
                    firadd(framePtr + 80L, framePtr + 48L, framePtr + 80L, 0L);
                    fidiv(framePtr + 48L, framePtr + 80L, framePtr + 16L);
                    isLess = fLT(CC_MachEp, framePtr + 16L) != 0L ? 1L : 0L;
                } while (isLess != 0L);
                fmul(framePtr + 80L, framePtr + 64L, framePtr + 80L);
                status = fidiv(framePtr + 80L, aPtr, resultPtr);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long fiFPoisson(long lambdaPtr, long kPtr, long resultPtr) {
        long isLess = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            fichs2(lambdaPtr, framePtr + 24L);
            isLess = fLE(lambdaPtr, CC_0) != 0L ? 1L : 0L;
            if ((long) (int) kPtr < 0L || isLess != 0L) {
                result = 24L;
                putI64(resultPtr + 8L, 0L);
                putI32(resultPtr, 0L);
                putI32(resultPtr + 4L, 0L);
                return result;
            }
            if ((long) (int) kPtr == 0L) {
                isLess = fLT(framePtr + 24L, CC_MinLog) != 0L ? 1L : 0L;
                if (isLess != 0L) {
                    result = 0L;
                    putI64(resultPtr + 8L, 0L);
                    putI32(resultPtr, 1L);
                    putI32(resultPtr + 4L, 0L);
                    return result;
                }
                fiexpm(framePtr + 24L, resultPtr, 0L);
            } else {
                fIntToCC((long) (int) (kPtr + 1L), framePtr + 24L);
                fiIGamma(framePtr + 24L, lambdaPtr, resultPtr);
                firadd(CC_1, resultPtr, resultPtr, 1L);
            }
            result = 0L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long fiFNorm(long xPtr, long resultPtr) {
        long tempVal = 0L;
        long isZero = 0L;
        long result = 0L;
        fimul(xPtr, CC_Sqrt2div2, resultPtr);
        tempVal = getU32(resultPtr);
        fimul(resultPtr, resultPtr, resultPtr);
        fiIGamma(CC_0p5, resultPtr, resultPtr);
        if ((long) (int) tempVal < 0L && comma(isZero = fZero(resultPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
            putU32(resultPtr, -getU32(resultPtr));
        }
        firadd(resultPtr, CC_1, resultPtr, 0L);
        result = fidiv2(resultPtr, resultPtr);
        return result;
    }

    long fiErf(long xPtr, long resultPtr) {
        long tempVal1 = 0L;
        long isZero = 0L;
        long tempVal2 = 0L;
        tempVal1 = getU32(xPtr);
        fimul(xPtr, xPtr, resultPtr);
        tempVal2 = fiIGamma(CC_0p5, resultPtr, resultPtr);
        if ((long) (int) tempVal1 < 0L && comma(isZero = fZero(resultPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
            putU32(resultPtr, -getU32(resultPtr));
        }
        return tempVal2 & 0xffffffffL;
    }

    long fiPNorm(long xPtr, long resultPtr) {
        long tempVal = 0L;
        long isZero = 0L;
        long result = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            fiabs2(xPtr, framePtr + 24L);
            isZero = fZero(framePtr + 24L) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                result = 0L;
                putU32(resultPtr + 8L, 0L);
                putU32(resultPtr + 12L, 0x1000000L);
                putU32(resultPtr, 1L);
                putU32(resultPtr + 4L, 0L);
            } else {
                fimul(framePtr + 24L, CC_Sqrt2div2, resultPtr);
                isZero = fLT(framePtr + 24L, CC_1) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    tempVal = getU32(resultPtr);
                    fimul(resultPtr, resultPtr, resultPtr);
                    fiIGamma(CC_0p5, resultPtr, resultPtr);
                    if ((long) (int) tempVal < 0L && comma(isZero = fZero(resultPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
                        putU32(resultPtr, -getU32(resultPtr));
                    }
                    status = firadd(CC_1, resultPtr, resultPtr, 1L);
                    result = status & 0xffffffffL;
                } else {
                    fimul(resultPtr, resultPtr, resultPtr);
                    if ((long) (int) getU32(resultPtr) < 0L) {
                        status = fiIGamma(CC_0p5, resultPtr, resultPtr);
                        result = status & 0xffffffffL;
                        firadd(CC_1, resultPtr, resultPtr, 0L);
                    } else {
                        status = fiJGamma(CC_0p5, resultPtr, resultPtr);
                        result = status & 0xffffffffL;
                    }
                }
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long fiErfc(long xPtr, long resultPtr) {
        long result = 0L;
        fimul(xPtr, xPtr, resultPtr);
        if (-1L < (long) (int) getU32(xPtr)) {
            result = fiJGamma(CC_0p5, resultPtr, resultPtr);
            return result;
        } else {
            result = fiIGamma(CC_0p5, resultPtr, resultPtr);
            firadd(CC_1, resultPtr, resultPtr, 0L);
            return result & 0xffffffffL;
        }
    }

    long fiFKhi2(long dfPtr, long chi2Ptr, long resultPtr) {
        long isLess = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            isLess = fLT(dfPtr, CC_1) != 0L ? 1L : 0L;
            if (isLess != 0L || comma(isLess = fiInteger(dfPtr) != 0L ? 1L : 0L, isLess == 0L ? 1L : 0L) != 0L || (long) (int) comma(isLess = fLE(chi2Ptr, CC_0) != 0L ? 1L : 0L, isLess) != 0L) {
                result = 24L;
                putU32(resultPtr + 8L, 0L);
                putU32(resultPtr + 12L, 0L);
                putU32(resultPtr, 0L);
                putU32(resultPtr + 4L, 0L);
            } else {
                fidiv2(dfPtr, framePtr + 24L);
                fidiv2(chi2Ptr, resultPtr);
                result = fiIGamma(framePtr + 24L, resultPtr, resultPtr);
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long fiPKhi2(long dfPtr, long chi2Ptr, long resultPtr) {
        long isLess = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            isLess = fLT(dfPtr, CC_1) != 0L ? 1L : 0L;
            if (isLess != 0L || comma(isLess = fiInteger(dfPtr) != 0L ? 1L : 0L, isLess == 0L ? 1L : 0L) != 0L || (long) (int) comma(isLess = fLE(chi2Ptr, CC_0) != 0L ? 1L : 0L, isLess) != 0L) {
                status = 24L;
                putU32(resultPtr + 8L, 0L);
                putU32(resultPtr + 12L, 0L);
                putU32(resultPtr, 0L);
                putU32(resultPtr + 4L, 0L);
            } else {
                fidiv2(dfPtr, framePtr + 24L);
                fidiv2(chi2Ptr, resultPtr);
                fiIGamma(framePtr + 24L, resultPtr, resultPtr);
                status = firadd(CC_1, resultPtr, resultPtr, 1L);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long fiDNorm(long xPtr, long resultPtr) {
        long isZero = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            fimul(xPtr, xPtr, framePtr + 24L);
            fidiv2(framePtr + 24L, framePtr + 24L);
            isZero = fZero(framePtr + 24L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI32(framePtr + 24L, -getI32(framePtr + 24L));
            }
            isZero = fLT(framePtr + 24L, CC_MinLog) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                status = 0L;
                putU32(resultPtr + 8L, 0L);
                putU32(resultPtr + 12L, 0L);
                putU32(resultPtr, 1L);
                putU32(resultPtr + 4L, 0L);
            } else {
                fiexpm(framePtr + 24L, resultPtr, 0L);
                status = fimul(CC_invSqrt2Pi, resultPtr, resultPtr);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long fiInvStudent(long dfPtr, long probPtr, long resultPtr) {
        long isZero = 0L;
        long result = 0L;
        long constPtr = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            isZero = fLT(dfPtr, CC_1) != 0L ? 1L : 0L;
            if (isZero != 0L || comma(isZero = fiInteger(dfPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L || (long) (int) getI64(probPtr) < 0L || (long) (int) comma(isZero = fLT(CC_1, probPtr) != 0L ? 1L : 0L, isZero) != 0L) {
                constPtr = CC_NaN;
                result = 24L;
                tempVal = getI64(constPtr);
                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                putI64(resultPtr, tempVal);
                return result;
            }
            isZero = fEQ(probPtr, CC_0p5) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                constPtr = CC_0;
                result = 0L;
                tempVal = getI64(constPtr);
                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                putI64(resultPtr, tempVal);
                return result;
            }
            isZero = fLT(CC_0p25, probPtr) != 0L ? 1L : 0L;
            if (isZero != 0L && (long) (int) comma(isZero = fLT(probPtr, CC_0p75) != 0L ? 1L : 0L, isZero) != 0L) {
                fimul2(probPtr, framePtr + 16L);
                firadd(CC_1, framePtr + 16L, framePtr + 16L, 1L);
                if ((long) (int) getI64(framePtr + 16L) == 3L) {
                    putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | 1L);
                } else {
                    if ((long) (int) getI64(framePtr + 16L) < 0L) {
                        putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 16L) & 0xffffffffL));
                    }
                }
                fidiv2(dfPtr, framePtr + 40L);
                fiInvBeta(CC_0p5, framePtr + 40L, framePtr + 16L, framePtr + 16L);
                firadd(CC_1, framePtr + 16L, framePtr + 40L, 1L);
                fidiv(framePtr + 16L, framePtr + 40L, framePtr + 40L);
                fimul(dfPtr, framePtr + 40L, framePtr + 40L);
                fisqrt(framePtr + 40L, resultPtr);
                isZero = fLT(probPtr, CC_0p5) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    result = 0L;
                    return result;
                }
            } else {
                isZero = fLT(probPtr, CC_0p5) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    firadd(CC_1, probPtr, framePtr + 16L, 1L);
                    fimul2(framePtr + 16L, framePtr + 16L);
                    fidiv2(dfPtr, framePtr + 40L);
                    fiInvBeta(framePtr + 40L, CC_0p5, framePtr + 16L, framePtr + 16L);
                    fidiv(dfPtr, framePtr + 16L, framePtr + 16L);
                    firadd(framePtr + 16L, dfPtr, resultPtr, 1L);
                    fisqrt(resultPtr, resultPtr);
                    result = 0L;
                    return result;
                }
                putI64(framePtr + 24L, getI64(probPtr + 8L));
                putI64(framePtr + 16L, getI64(probPtr));
                fimul2(framePtr + 16L, framePtr + 16L);
                fidiv2(dfPtr, framePtr + 40L);
                fiInvBeta(framePtr + 40L, CC_0p5, framePtr + 16L, framePtr + 16L);
                fidiv(dfPtr, framePtr + 16L, framePtr + 16L);
                firadd(framePtr + 16L, dfPtr, resultPtr, 1L);
                fisqrt(resultPtr, resultPtr);
            }
            isZero = fZero(resultPtr) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                result = 0L;
                putI32(resultPtr, -getI64(resultPtr));
                return result;
            }
            result = 0L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long fiInvBeta(long aPtr, long bPtr, long probPtr, long resultPtr) {
        long flag = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long isZero1 = 0L;
        long isZero2 = 0L;
        long index1 = 0L;
        long index2 = 0L;
        long constPtr = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        long tempVal5 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(448);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        isZero1 = fLE(probPtr, CC_0) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            tempVal3 = 1L;
                            tempVal5 = 0L;
                            putI64(resultPtr + 8L, tempVal5);
                            putI64(resultPtr, tempVal3);
                            return 0L;
                        }
                        isZero1 = fLT(CC_1, probPtr) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            tempVal3 = 1L;
                            tempVal5 = 0x100000000000000L;
                            putI64(resultPtr + 8L, tempVal5);
                            putI64(resultPtr, tempVal3);
                            return 0L;
                        }
                        putI64(framePtr + 136L, 0L);
                        putI64(framePtr + 128L, 1L);
                        putI64(framePtr + 120L, 0x100000000000000L);
                        putI64(framePtr + 112L, 1L);
                        putI64(framePtr + 176L, getI64(framePtr + 112L));
                        putI64(framePtr + 184L, getI64(framePtr + 120L));
                        putI64(framePtr + 208L, getI64(framePtr + 128L));
                        putI64(framePtr + 216L, getI64(framePtr + 136L));
                        isZero1 = fLE(aPtr, CC_1) != 0L ? 1L : 0L;
                        if (isZero1 != 0L || (long) (int) comma(isZero1 = fLE(bPtr, CC_1) != 0L ? 1L : 0L, isZero1) != 0L) {
                            putI64(framePtr + 264L, getI64(aPtr + 8L));
                            putI64(framePtr + 256L, getI64(aPtr));
                            putI64(framePtr + 248L, getI64(bPtr + 8L));
                            putI64(framePtr + 240L, getI64(bPtr));
                            putI64(framePtr + 232L, getI64(probPtr + 8L));
                            putI64(framePtr + 224L, getI64(probPtr));
                            firadd(framePtr + 256L, framePtr + 240L, framePtr + 144L, 0L);
                            fidiv(framePtr + 256L, framePtr + 144L, framePtr + 144L);
                            fiIBeta(framePtr + 256L, framePtr + 240L, framePtr + 144L, framePtr + 192L);
                            constPtr = CC_1Em6;
                            flag = 0L;
                            isZero1 = 0L;
                            tempVal4 = 0x500000000000000L;
                            tempVal3 = 0xffffffff00000001L;
                            step = 5;
                            continue stepLoop;
                        } else {
                            fiIUTPN(CC_0, CC_1, probPtr, framePtr + 160L);
                            isZero1 = fZero(framePtr + 160L) != 0L ? 1L : 0L;
                            if (isZero1 == 0L) {
                                putI32(framePtr + 160L, -getI64(framePtr + 160L));
                            }
                            isZero1 = fLT(CC_0p5, probPtr) != 0L ? 1L : 0L;
                            if (isZero1 != 0L) {
                                putI64(framePtr + 264L, getI64(bPtr + 8L));
                                putI64(framePtr + 256L, getI64(bPtr));
                                putI64(framePtr + 248L, getI64(aPtr + 8L));
                                putI64(framePtr + 240L, getI64(aPtr));
                                firadd(CC_1, probPtr, framePtr + 224L, 1L);
                                isZero1 = fZero(framePtr + 160L) != 0L ? 1L : 0L;
                                if (isZero1 != 0L) {
                                    isZero1 = 1L;
                                } else {
                                    isZero1 = 1L;
                                    putI32(framePtr + 160L, -getI64(framePtr + 160L));
                                }
                            } else {
                                putI64(framePtr + 264L, getI64(aPtr + 8L));
                                putI64(framePtr + 256L, getI64(aPtr));
                                putI64(framePtr + 248L, getI64(bPtr + 8L));
                                putI64(framePtr + 240L, getI64(bPtr));
                                isZero1 = 0L;
                                putI64(framePtr + 232L, getI64(probPtr + 8L));
                                putI64(framePtr + 224L, getI64(probPtr));
                            }
                            flag = 0L;
                            fimul(framePtr + 160L, framePtr + 160L, framePtr + 192L);
                            firadd(framePtr + 192L, CC_N3, framePtr + 192L, 0L);
                            fidiv(framePtr + 192L, CC_6, framePtr + 192L);
                            fimul2(framePtr + 256L, framePtr + 80L);
                            firadd(framePtr + 80L, CC_N1, framePtr + 80L, 0L);
                            fidiv(CC_1, framePtr + 80L, framePtr + 80L);
                            fimul2(framePtr + 240L, framePtr + 64L);
                            firadd(framePtr + 64L, CC_N1, framePtr + 64L, 0L);
                            fidiv(CC_1, framePtr + 64L, framePtr + 64L);
                            firadd(framePtr + 80L, framePtr + 64L, framePtr + 144L, 0L);
                            fidiv(CC_2, framePtr + 144L, framePtr + 144L);
                            firadd(framePtr + 144L, framePtr + 192L, framePtr + 96L, 0L);
                            fisqrt(framePtr + 96L, framePtr + 96L);
                            fimul(framePtr + 160L, framePtr + 96L, framePtr + 96L);
                            fidiv(framePtr + 96L, framePtr + 144L, framePtr + 96L);
                            firadd(framePtr + 64L, framePtr + 80L, framePtr + 80L, 1L);
                            fimul(CC_3, framePtr + 144L, framePtr + 64L);
                            fidiv(CC_2, framePtr + 64L, framePtr + 64L);
                            firadd(CC_5div6, framePtr + 64L, framePtr + 64L, 1L);
                            firadd(framePtr + 192L, framePtr + 64L, framePtr + 64L, 0L);
                            fimul(framePtr + 80L, framePtr + 64L, framePtr + 80L);
                            firadd(framePtr + 96L, framePtr + 80L, framePtr + 96L, 1L);
                            fimul2(framePtr + 96L, framePtr + 96L);
                            isZero2 = fLT(framePtr + 96L, CC_MinLog) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                step = 1;
                                continue stepLoop;
                            } else {
                                fiexpm(framePtr + 96L, framePtr + 144L, 0L);
                                fimul(framePtr + 240L, framePtr + 144L, framePtr + 144L);
                                firadd(framePtr + 256L, framePtr + 144L, framePtr + 144L, 0L);
                                fidiv(framePtr + 256L, framePtr + 144L, framePtr + 144L);
                                fiIBeta(framePtr + 256L, framePtr + 240L, framePtr + 144L, framePtr + 192L);
                                firadd(framePtr + 192L, framePtr + 224L, framePtr + 160L, 1L);
                                fidiv(framePtr + 160L, framePtr + 224L, framePtr + 160L);
                                isZero2 = fMLT(framePtr + 160L, CC_0p2) != 0L ? 1L : 0L;
                                constPtr = CC_1Em4;
                                if (isZero2 != 0L) {
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    step = 4;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 1: {
                        putI64(framePtr + 152L, 0L);
                        putI64(framePtr + 144L, 1L);
                        step = 2;
                        continue stepLoop;
                    }
                    case 2: {
                        tempVal3 = getI64(framePtr + 144L);
                        tempVal5 = getI64(framePtr + 152L);
                        if (isZero1 == 0L) {
                            putI64(resultPtr + 8L, tempVal5);
                            putI64(resultPtr, tempVal3);
                            return 0L;
                        }
                        isZero1 = fLE(framePtr + 144L, CC_MachEp) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            putI64(framePtr + 152L, 0x999999999999999L);
                            putI64(framePtr + 144L, 0xffffffff00000001L);
                            tempVal3 = getI64(framePtr + 144L);
                            tempVal5 = getI64(framePtr + 152L);
                        } else {
                            firadd(CC_1, framePtr + 144L, framePtr + 144L, 1L);
                            tempVal3 = getI64(framePtr + 144L);
                            tempVal5 = getI64(framePtr + 152L);
                        }
                        putI64(resultPtr + 8L, tempVal5);
                        putI64(resultPtr, tempVal3);
                        return 0L;
                    }
                    case 3: {
                        if (flag != 0L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            flag = 1L;
                            firadd(framePtr + 256L, framePtr + 240L, framePtr + 32L, 0L);
                            filnGamma(framePtr + 32L, framePtr + 32L);
                            filnGamma(framePtr + 256L, framePtr + 80L);
                            firadd(framePtr + 32L, framePtr + 80L, framePtr + 32L, 1L);
                            filnGamma(framePtr + 240L, framePtr + 80L);
                            firadd(framePtr + 32L, framePtr + 80L, framePtr + 32L, 1L);
                            index1 = 8L;
                            step = 10;
                            continue stepLoop;
                        }
                    }
                    case 4: {
                        tempVal4 = 0x500000000000000L;
                        tempVal3 = 0xffffffff00000001L;
                        step = 5;
                        continue stepLoop;
                    }
                    case 5: {
                        index1 = 0L;
                        index2 = 100L;
                        putI64(framePtr + 80L, tempVal3);
                        putI64(framePtr + 88L, tempVal4);
                        step = 6;
                        continue stepLoop;
                    }
                    case 6: {
                        if (index2 != 100L) {
                            firadd(framePtr + 112L, framePtr + 128L, framePtr + 144L, 1L);
                            fimul(framePtr + 80L, framePtr + 144L, framePtr + 144L);
                            firadd(framePtr + 128L, framePtr + 144L, framePtr + 144L, 0L);
                            isZero2 = fEQ(framePtr + 144L, CC_1) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                putI64(framePtr + 152L, 0x999999999999999L);
                                putI64(framePtr + 144L, 0xffffffff00000001L);
                            }
                            isZero2 = fZero(framePtr + 144L) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                putI64(framePtr + 80L, tempVal3);
                                putI64(framePtr + 88L, tempVal4);
                                firadd(framePtr + 112L, framePtr + 128L, framePtr + 144L, 1L);
                                fimul(framePtr + 80L, framePtr + 144L, framePtr + 144L);
                                firadd(framePtr + 128L, framePtr + 144L, framePtr + 144L, 0L);
                                isZero2 = fZero(framePtr + 144L) != 0L ? 1L : 0L;
                                if (isZero2 != 0L) {
                                    step = 1;
                                    continue stepLoop;
                                } else {
                                    step = 8;
                                    continue stepLoop;
                                }
                            } else {
                                step = 8;
                                continue stepLoop;
                            }
                        } else {
                            step = 7;
                            continue stepLoop;
                        }
                    }
                    case 7: {
                        isZero2 = fLT(framePtr + 192L, framePtr + 224L) != 0L ? 1L : 0L;
                        if (isZero2 == 0L) {
                            putI64(framePtr + 120L, getI64(framePtr + 152L));
                            putI64(framePtr + 112L, getI64(framePtr + 144L));
                            if (isZero1 == 0L || comma(isZero2 = fLT(framePtr + 112L, CC_MachEp) != 0L ? 1L : 0L, isZero2 == 0L ? 1L : 0L) != 0L) {
                                putI64(framePtr + 184L, getI64(framePtr + 200L));
                                putI64(framePtr + 176L, getI64(framePtr + 192L));
                                if (index1 < 1L) {
                                    if (index1 < -3L) {
                                        fimul(framePtr + 80L, framePtr + 80L, framePtr + 80L);
                                    } else {
                                        if (index1 < -1L) {
                                            fidiv2(framePtr + 80L, framePtr + 80L);
                                        } else {
                                            firadd(framePtr + 176L, framePtr + 208L, framePtr + 64L, 0L);
                                            firadd(framePtr + 192L, framePtr + 224L, framePtr + 48L, 1L);
                                            fidiv(framePtr + 48L, framePtr + 64L, framePtr + 80L);
                                        }
                                    }
                                } else {
                                    index1 = 0L;
                                    putI64(framePtr + 80L, tempVal3);
                                    putI64(framePtr + 88L, tempVal4);
                                }
                                index1 = (long) (int) (index1 - 1L);
                                index2 = (long) (int) (index2 - 1L);
                                if (index2 != 0L) {
                                    step = 6;
                                    continue stepLoop;
                                } else {
                                    step = 9;
                                    continue stepLoop;
                                }
                            } else {
                                step = 1;
                                continue stepLoop;
                            }
                        } else {
                            putI64(framePtr + 136L, getI64(framePtr + 152L));
                            putI64(framePtr + 128L, getI64(framePtr + 144L));
                            putI64(framePtr + 216L, getI64(framePtr + 200L));
                            putI64(framePtr + 208L, getI64(framePtr + 192L));
                            if (index1 < 0L) {
                                index1 = 0L;
                                putI64(framePtr + 80L, tempVal3);
                                putI64(framePtr + 88L, tempVal4);
                            } else {
                                if (index1 < 4L) {
                                    if (index1 < 2L) {
                                        firadd(framePtr + 176L, framePtr + 208L, framePtr + 64L, 0L);
                                        firadd(framePtr + 224L, framePtr + 192L, framePtr + 48L, 1L);
                                        fidiv(framePtr + 48L, framePtr + 64L, framePtr + 80L);
                                    } else {
                                        fidiv2(framePtr + 80L, framePtr + 80L);
                                        firadd(framePtr + 80L, CC_0p5, framePtr + 80L, 0L);
                                    }
                                } else {
                                    firadd(CC_1, framePtr + 80L, framePtr + 80L, 1L);
                                    fimul(framePtr + 80L, framePtr + 80L, framePtr + 80L);
                                    firadd(CC_1, framePtr + 80L, framePtr + 80L, 1L);
                                }
                            }
                            isZero2 = fLT(CC_0p75, framePtr + 128L) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                if (isZero1 != 0L) {
                                    putI64(framePtr + 264L, getI64(aPtr + 8L));
                                    putI64(framePtr + 256L, getI64(aPtr));
                                    putI64(framePtr + 248L, getI64(bPtr + 8L));
                                    putI64(framePtr + 240L, getI64(bPtr));
                                    putI64(framePtr + 232L, getI64(probPtr + 8L));
                                    putI64(framePtr + 224L, getI64(probPtr));
                                } else {
                                    putI64(framePtr + 264L, getI64(bPtr + 8L));
                                    putI64(framePtr + 256L, getI64(bPtr));
                                    putI64(framePtr + 248L, getI64(aPtr + 8L));
                                    putI64(framePtr + 240L, getI64(aPtr));
                                    firadd(CC_1, probPtr, framePtr + 224L, 1L);
                                }
                                isZero1 = isZero1 == 0L ? 1L : 0L;
                                firadd(CC_1, framePtr + 144L, framePtr + 144L, 1L);
                                fiIBeta(framePtr + 256L, framePtr + 240L, framePtr + 144L, framePtr + 192L);
                                putI64(framePtr + 136L, 0L);
                                putI64(framePtr + 128L, 1L);
                                putI64(framePtr + 120L, 0x100000000000000L);
                                putI64(framePtr + 112L, 1L);
                                putI64(framePtr + 176L, getI64(framePtr + 112L));
                                putI64(framePtr + 184L, getI64(framePtr + 120L));
                                putI64(framePtr + 208L, getI64(framePtr + 128L));
                                putI64(framePtr + 216L, getI64(framePtr + 136L));
                                step = 5;
                                continue stepLoop;
                            } else {
                                index1 = (long) (int) (index1 + 1L);
                                index2 = (long) (int) (index2 - 1L);
                                if (index2 == 0L) {
                                    step = 9;
                                    continue stepLoop;
                                } else {
                                    step = 6;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 8: {
                        fiIBeta(framePtr + 256L, framePtr + 240L, framePtr + 144L, framePtr + 192L);
                        firadd(framePtr + 112L, framePtr + 128L, framePtr + 64L, 0L);
                        firadd(framePtr + 112L, framePtr + 128L, framePtr + 48L, 1L);
                        fidiv(framePtr + 48L, framePtr + 64L, framePtr + 160L);
                        isZero2 = fMLT(framePtr + 160L, constPtr) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            firadd(framePtr + 192L, framePtr + 224L, framePtr + 160L, 1L);
                            fidiv(framePtr + 160L, framePtr + 224L, framePtr + 160L);
                            isZero2 = fMLT(framePtr + 160L, constPtr) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                step = 3;
                                continue stepLoop;
                            } else {
                                step = 7;
                                continue stepLoop;
                            }
                        }
                    }
                    case 9: {
                        isZero2 = fLE(CC_1, framePtr + 128L) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            putI64(framePtr + 152L, 0x999999999999999L);
                            putI64(framePtr + 144L, 0xffffffff00000001L);
                            step = 2;
                            continue stepLoop;
                        } else {
                            isZero2 = fLE(framePtr + 144L, CC_0) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                step = 1;
                                continue stepLoop;
                            } else {
                                step = 3;
                                continue stepLoop;
                            }
                        }
                    }
                    case 10: {
                        if (index1 != 8L) {
                            fiIBeta(framePtr + 256L, framePtr + 240L, framePtr + 144L, framePtr + 192L);
                        }
                        isZero2 = fLT(framePtr + 192L, framePtr + 208L) != 0L ? 1L : 0L;
                        tempVal3 = getI64(framePtr + 128L);
                        tempVal5 = getI64(framePtr + 136L);
                        tempVal1 = getI64(framePtr + 208L);
                        tempVal2 = getI64(framePtr + 216L);
                        if (isZero2 == 0L && comma(comma(comma(comma(comma(isZero2 = fLT(framePtr + 176L, framePtr + 192L) != 0L ? 1L : 0L, tempVal3 = getI64(framePtr + 112L)), tempVal5 = getI64(framePtr + 120L)), tempVal1 = getI64(framePtr + 176L)), tempVal2 = getI64(framePtr + 184L)), isZero2 == 0L ? 1L : 0L) != 0L) {
                            isZero2 = fLT(framePtr + 192L, framePtr + 224L) != 0L ? 1L : 0L;
                            tempVal3 = getI64(framePtr + 144L);
                            tempVal5 = getI64(framePtr + 152L);
                            tempVal1 = getI64(framePtr + 192L);
                            tempVal2 = getI64(framePtr + 200L);
                            if (isZero2 != 0L) {
                                putI64(framePtr + 136L, getI64(framePtr + 152L));
                                putI64(framePtr + 128L, getI64(framePtr + 144L));
                                putI64(framePtr + 216L, getI64(framePtr + 200L));
                                putI64(framePtr + 208L, getI64(framePtr + 192L));
                            } else {
                                putI64(framePtr + 120L, getI64(framePtr + 152L));
                                putI64(framePtr + 112L, getI64(framePtr + 144L));
                                putI64(framePtr + 184L, getI64(framePtr + 200L));
                                putI64(framePtr + 176L, getI64(framePtr + 192L));
                            }
                        }
                        putI64(framePtr + 200L, tempVal2);
                        putI64(framePtr + 192L, tempVal1);
                        putI64(framePtr + 152L, tempVal5);
                        putI64(framePtr + 144L, tempVal3);
                        isZero2 = fEQ(framePtr + 144L, CC_1) != 0L ? 1L : 0L;
                        if (isZero2 != 0L || (long) (int) comma(isZero2 = fZero(framePtr + 144L) != 0L ? 1L : 0L, isZero2) != 0L) {
                            step = 11;
                            continue stepLoop;
                        } else {
                            firadd(framePtr + 256L, CC_N1, framePtr + 80L, 0L);
                            filn(framePtr + 144L, framePtr + 64L);
                            fimul(framePtr + 80L, framePtr + 64L, framePtr + 80L);
                            firadd(framePtr + 240L, CC_N1, framePtr + 64L, 0L);
                            firadd(CC_1, framePtr + 144L, framePtr + 48L, 1L);
                            filn(framePtr + 48L, framePtr + 48L);
                            fimul(framePtr + 64L, framePtr + 48L, framePtr + 64L);
                            firadd(framePtr + 80L, framePtr + 64L, framePtr + 96L, 0L);
                            firadd(framePtr + 96L, framePtr + 32L, framePtr + 96L, 0L);
                            isZero2 = fLT(framePtr + 96L, CC_MinLog) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                step = 12;
                                continue stepLoop;
                            } else {
                                isZero2 = fLT(CC_MaxLog, framePtr + 96L) != 0L ? 1L : 0L;
                                if (isZero2 != 0L) {
                                    step = 11;
                                    continue stepLoop;
                                } else {
                                    fiexpm(framePtr + 96L, framePtr + 96L, 0L);
                                    firadd(framePtr + 192L, framePtr + 224L, framePtr + 80L, 1L);
                                    fidiv(framePtr + 80L, framePtr + 96L, framePtr + 96L);
                                    firadd(framePtr + 144L, framePtr + 96L, framePtr + 16L, 1L);
                                    isZero2 = fLE(framePtr + 16L, framePtr + 128L) != 0L ? 1L : 0L;
                                    if (isZero2 != 0L) {
                                        firadd(framePtr + 112L, framePtr + 128L, framePtr + 80L, 0L);
                                        firadd(framePtr + 144L, framePtr + 128L, framePtr + 64L, 1L);
                                        fidiv(framePtr + 64L, framePtr + 80L, framePtr + 192L);
                                        firadd(framePtr + 144L, framePtr + 128L, framePtr + 16L, 1L);
                                        fimul(framePtr + 16L, framePtr + 192L, framePtr + 16L);
                                        fidiv2(framePtr + 16L, framePtr + 16L);
                                        firadd(framePtr + 128L, framePtr + 16L, framePtr + 16L, 0L);
                                        isZero2 = fLE(framePtr + 16L, CC_0) != 0L ? 1L : 0L;
                                        if (isZero2 == 0L) {
                                            step = 14;
                                            continue stepLoop;
                                        } else {
                                            step = 15;
                                            continue stepLoop;
                                        }
                                    } else {
                                        step = 14;
                                        continue stepLoop;
                                    }
                                }
                            }
                        }
                    }
                    case 11: {
                        isZero2 = 1L;
                        constPtr = ADDR_RODATA + 5512L;
                        step = 13;
                        continue stepLoop;
                    }
                    case 12: {
                        isZero2 = 0L;
                        step = 13;
                        continue stepLoop;
                    }
                    case 13: {
                        if (isZero2 == 0L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            step = 4;
                            continue stepLoop;
                        }
                    }
                    case 14: {
                        isZero2 = fLE(framePtr + 112L, framePtr + 16L) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            firadd(framePtr + 112L, framePtr + 128L, framePtr + 80L, 0L);
                            firadd(framePtr + 112L, framePtr + 144L, framePtr + 64L, 1L);
                            fidiv(framePtr + 64L, framePtr + 80L, framePtr + 192L);
                            firadd(framePtr + 112L, framePtr + 144L, framePtr + 16L, 1L);
                            fimul(framePtr + 16L, framePtr + 192L, framePtr + 16L);
                            fidiv2(framePtr + 16L, framePtr + 16L);
                            firadd(framePtr + 112L, framePtr + 16L, framePtr + 16L, 1L);
                            isZero2 = fLE(CC_1, framePtr + 16L) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                step = 15;
                                continue stepLoop;
                            } else {
                                step = 16;
                                continue stepLoop;
                            }
                        } else {
                            step = 16;
                            continue stepLoop;
                        }
                    }
                    case 15: {
                        isZero2 = 1L;
                        constPtr = ADDR_RODATA + 5512L;
                        step = 13;
                        continue stepLoop;
                    }
                    case 16: {
                        putI64(framePtr + 152L, getI64(framePtr + 24L));
                        putI64(framePtr + 144L, getI64(framePtr + 16L));
                        fidiv(framePtr + 96L, framePtr + 144L, framePtr + 80L);
                        if ((long) (int) getI64(framePtr + 80L) == 3L) {
                            index2 = 1L;
                            putI64(framePtr + 80L, (((getI32(framePtr + 84L) & 0xffffffffL)) << 32) | (index2 & 0xffffffffL));
                        } else {
                            if ((long) (int) getI64(framePtr + 80L) < 0L) {
                                index2 = (long) (int) -getI64(framePtr + 80L);
                                putI64(framePtr + 80L, (((getI32(framePtr + 84L) & 0xffffffffL)) << 32) | (index2 & 0xffffffffL));
                            }
                        }
                        fiDiv100(framePtr + 80L);
                        isZero2 = fLT(framePtr + 80L, CC_MachEp) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            step = 12;
                            continue stepLoop;
                        } else {
                            index1 = (long) (int) (index1 - 1L);
                            if (index1 != 0L) {
                                step = 10;
                                continue stepLoop;
                            } else {
                                step = 11;
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

    long fiInvSnedecor(long df1Ptr, long df2Ptr, long probPtr, long resultPtr) {
        long isLess = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            isLess = fLT(df1Ptr, CC_1) != 0L ? 1L : 0L;
            if (isLess != 0L || (long) (int) comma(isLess = fLT(df2Ptr, CC_1) != 0L ? 1L : 0L, isLess) != 0L || comma(isLess = fiInteger(df1Ptr) != 0L ? 1L : 0L, isLess == 0L ? 1L : 0L) != 0L || comma(isLess = fiInteger(df2Ptr) != 0L ? 1L : 0L, isLess == 0L || (long) (int) getU32(probPtr) < 0L ? 1L : 0L) != 0L || (long) (int) comma(isLess = fLT(CC_1, probPtr) != 0L ? 1L : 0L, isLess) != 0L) {
                status = 24L;
                putI32(resultPtr + 8L, 0L);
                putI32(resultPtr + 12L, 0L);
                putI32(resultPtr, 0L);
                putI32(resultPtr + 4L, 0L);
            } else {
                fidiv2(df1Ptr, framePtr + 56L);
                fidiv2(df2Ptr, framePtr + 40L);
                firadd(CC_1, probPtr, framePtr + 24L, 1L);
                fiInvBeta(framePtr + 40L, framePtr + 56L, framePtr + 24L, framePtr + 24L);
                fimul(framePtr + 24L, df1Ptr, framePtr + 56L);
                fimul(framePtr + 24L, df2Ptr, framePtr + 24L);
                firadd(df2Ptr, framePtr + 24L, framePtr + 24L, 1L);
                status = fidiv(framePtr + 24L, framePtr + 56L, resultPtr);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long fiInvSnedecor_LOWRAM(long df1Ptr, long df2Ptr, long probPtr, long resultPtr) {
        long isLess = 0L;
        long status = 0L;
        isLess = fLT(df1Ptr, CC_1) != 0L ? 1L : 0L;
        if (isLess == 0L && comma(isLess = fLT(df2Ptr, CC_1) != 0L ? 1L : 0L, isLess == 0L ? 1L : 0L) != 0L && (long) (int) comma(isLess = fiInteger(df1Ptr) != 0L ? 1L : 0L, isLess) != 0L && comma(isLess = fiInteger(df2Ptr) != 0L ? 1L : 0L, isLess != 0L && -1L < (long) (int) getI64(probPtr) ? 1L : 0L) != 0L && comma(isLess = fLT(CC_1, probPtr) != 0L ? 1L : 0L, isLess == 0L ? 1L : 0L) != 0L) {
            fidiv2(df1Ptr, df1Ptr);
            fidiv2(df2Ptr, df2Ptr);
            firadd(CC_1, probPtr, probPtr, 1L);
            fiInvBeta(df2Ptr, df1Ptr, probPtr, probPtr);
            fimul(probPtr, df1Ptr, df1Ptr);
            fimul(probPtr, df2Ptr, probPtr);
            firadd(df2Ptr, probPtr, probPtr, 1L);
            status = fidiv(probPtr, df1Ptr, resultPtr);
            return status;
        } else {
            putI32(resultPtr + 8L, 0L);
            putI32(resultPtr + 12L, 0L);
            putI32(resultPtr, 0L);
            putI32(resultPtr + 4L, 0L);
            return 24L;
        }
    }

    long fiJGamma(long aPtr, long xPtr, long resultPtr) {
        long isZero = 0L;
        long status = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(416);
        try {
            isZero = fLE(xPtr, CC_0) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                status = 0L;
                tempVal = 0x100000000000000L;
                putI64(resultPtr + 8L, tempVal);
                putU32(resultPtr, 1L);
                putU32(resultPtr + 4L, 0L);
                return status;
            }
            isZero = fLT(aPtr, CC_0) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                status = 0L;
                tempVal = 0x100000000000000L;
                putI64(resultPtr + 8L, tempVal);
                putU32(resultPtr, 1L);
                putU32(resultPtr + 4L, 0L);
                return status;
            }
            isZero = fLT(xPtr, CC_1) != 0L ? 1L : 0L;
            if (isZero != 0L || (long) (int) comma(isZero = fLT(xPtr, aPtr) != 0L ? 1L : 0L, isZero) != 0L) {
                fiIGamma(aPtr, xPtr, resultPtr);
                status = firadd(CC_1, resultPtr, resultPtr, 1L);
            } else {
                filn(xPtr, framePtr + 192L);
                fimul(aPtr, framePtr + 192L, framePtr + 192L);
                firadd(framePtr + 192L, xPtr, framePtr + 192L, 1L);
                filnGamma(aPtr, framePtr + 16L);
                firadd(framePtr + 192L, framePtr + 16L, framePtr + 192L, 1L);
                isZero = fLT(framePtr + 192L, CC_MinLog) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    tempVal = 0L;
                    status = 1L;
                    putI64(resultPtr + 8L, tempVal);
                    putU32(resultPtr, 1L);
                    putU32(resultPtr + 4L, 0L);
                    return status;
                }
                fiexpm(framePtr + 192L, framePtr + 192L, 0L);
                firadd(CC_1, aPtr, framePtr + 176L, 1L);
                firadd(xPtr, framePtr + 176L, framePtr + 160L, 0L);
                firadd(framePtr + 160L, CC_1, framePtr + 160L, 0L);
                putI64(framePtr + 232L, 0L);
                putI64(framePtr + 224L, 1L);
                putI64(framePtr + 88L, 0x100000000000000L);
                putI64(framePtr + 80L, 1L);
                putI64(framePtr + 40L, getI64(xPtr + 8L));
                putI64(framePtr + 32L, getI64(xPtr));
                firadd(xPtr, CC_1, framePtr + 96L, 0L);
                fimul(framePtr + 160L, xPtr, framePtr + 48L);
                fidiv(framePtr + 96L, framePtr + 48L, framePtr + 240L);
                do {
                    firadd(framePtr + 224L, CC_1, framePtr + 224L, 0L);
                    firadd(framePtr + 176L, CC_1, framePtr + 176L, 0L);
                    firadd(framePtr + 160L, CC_2, framePtr + 160L, 0L);
                    fimul(framePtr + 176L, framePtr + 224L, framePtr + 208L);
                    fimul(framePtr + 96L, framePtr + 160L, framePtr + 112L);
                    fimul(framePtr + 80L, framePtr + 208L, framePtr + 16L);
                    firadd(framePtr + 112L, framePtr + 16L, framePtr + 112L, 1L);
                    fimul(framePtr + 48L, framePtr + 160L, framePtr + 64L);
                    fimul(framePtr + 32L, framePtr + 208L, framePtr + 16L);
                    firadd(framePtr + 64L, framePtr + 16L, framePtr + 64L, 1L);
                    isZero = fZero(framePtr + 64L) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        putI64(framePtr + 136L, 0x100000000000000L);
                        putI64(framePtr + 128L, 1L);
                    } else {
                        fidiv(framePtr + 112L, framePtr + 64L, framePtr + 144L);
                        firadd(framePtr + 240L, framePtr + 144L, framePtr + 128L, 1L);
                        fidiv(framePtr + 128L, framePtr + 144L, framePtr + 128L);
                        if ((long) (int) getI64(framePtr + 128L) == 3L) {
                            putI64(framePtr + 128L, (((getI32(framePtr + 132L) & 0xffffffffL)) << 32) | 1L);
                        } else {
                            if ((long) (int) getI64(framePtr + 128L) < 0L) {
                                putI64(framePtr + 128L, (((getI32(framePtr + 132L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 128L) & 0xffffffffL));
                            }
                        }
                        putI64(framePtr + 248L, getI64(framePtr + 152L));
                        putI64(framePtr + 240L, getI64(framePtr + 144L));
                    }
                    putI64(framePtr + 88L, getI64(framePtr + 104L));
                    putI64(framePtr + 80L, getI64(framePtr + 96L));
                    putI64(framePtr + 104L, getI64(framePtr + 120L));
                    putI64(framePtr + 96L, getI64(framePtr + 112L));
                    putI64(framePtr + 40L, getI64(framePtr + 56L));
                    putI64(framePtr + 32L, getI64(framePtr + 48L));
                    putI64(framePtr + 56L, getI64(framePtr + 72L));
                    putI64(framePtr + 48L, getI64(framePtr + 64L));
                    isZero = fMLT(CC_1divMachEp, framePtr + 112L) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        fimul(framePtr + 80L, CC_MachEp, framePtr + 80L);
                        fimul(framePtr + 96L, CC_MachEp, framePtr + 96L);
                        fimul(framePtr + 32L, CC_MachEp, framePtr + 32L);
                        fimul(framePtr + 48L, CC_MachEp, framePtr + 48L);
                    }
                    isZero = fLT(CC_MachEp, framePtr + 128L) != 0L ? 1L : 0L;
                } while (isZero != 0L);
                status = fimul(framePtr + 240L, framePtr + 192L, resultPtr);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long fiInvGamma(long aPtr, long probPtr, long resultPtr) {
        long dataPtr = 0L;
        long isLess = 0L;
        long isZero = 0L;
        long result = 0L;
        long constPtr = 0L;
        long index1 = 0L;
        long index2 = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(384);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        isLess = fLT(CC_0p5, probPtr) != 0L ? 1L : 0L;
                        firadd(CC_1, probPtr, framePtr + 144L, 1L);
                        putI64(framePtr + 120L, 0x100000000000000L);
                        putI64(framePtr + 112L, 1L);
                        putI64(framePtr + 200L, 0x999999999999999L);
                        putI64(framePtr + 192L, 0xc34f00000001L);
                        putI64(framePtr + 136L, 0L);
                        putI64(framePtr + 128L, 1L);
                        putI64(framePtr + 176L, getI64(framePtr + 128L));
                        putI64(framePtr + 184L, getI64(framePtr + 136L));
                        fimul(CC_5, CC_MachEp, framePtr + 48L);
                        fimul(CC_9, aPtr, framePtr + 80L);
                        fidiv(CC_1, framePtr + 80L, framePtr + 80L);
                        fiIUTPN(CC_0, CC_1, framePtr + 144L, framePtr + 32L);
                        constPtr = CC_NaN;
                        result = 24L;
                        if (!(((getI64(framePtr + 32L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 32L) + 1L) & 31L)) & 21L) != 0L)) {
                            tempVal1 = getI64(constPtr);
                            putI64(resultPtr + 8L, getI64(constPtr + 8L));
                            putI64(resultPtr, tempVal1);
                            return result;
                        }
                        fisqrt(framePtr + 80L, framePtr + 16L);
                        fimul(framePtr + 32L, framePtr + 16L, framePtr + 32L);
                        firadd(CC_1, framePtr + 80L, framePtr + 96L, 1L);
                        firadd(framePtr + 96L, framePtr + 32L, framePtr + 96L, 1L);
                        fimul(aPtr, framePtr + 96L, framePtr + 160L);
                        fimul(framePtr + 160L, framePtr + 96L, framePtr + 160L);
                        fimul(framePtr + 160L, framePtr + 96L, framePtr + 160L);
                        filnGamma(aPtr, framePtr + 64L);
                        index2 = 10L;
                        if (comma(isZero = fLT(framePtr + 192L, framePtr + 160L) != 0L ? 1L : 0L, isZero == 0L && comma(isZero = fLT(framePtr + 160L, framePtr + 176L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
                            step = 1;
                            continue stepLoop;
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        fiJGamma(aPtr, framePtr + 160L, framePtr + 96L);
                        isZero = fLT(framePtr + 96L, framePtr + 128L) != 0L ? 1L : 0L;
                        if (isZero != 0L || (long) (int) comma(isZero = fLT(framePtr + 112L, framePtr + 96L) != 0L ? 1L : 0L, isZero) != 0L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            isZero = fLT(framePtr + 96L, framePtr + 144L) != 0L ? 1L : 0L;
                            constPtr = framePtr + 128L;
                            dataPtr = framePtr + 192L;
                            if (isZero == 0L) {
                                constPtr = framePtr + 112L;
                                dataPtr = framePtr + 176L;
                            }
                            putI64(dataPtr + 8L, getI64(framePtr + 168L));
                            putI64(dataPtr, getI64(framePtr + 160L));
                            putI64(constPtr + 8L, getI64(framePtr + 104L));
                            putI64(constPtr, getI64(framePtr + 96L));
                            firadd(aPtr, CC_N1, framePtr + 80L, 0L);
                            filn(framePtr + 160L, framePtr + 32L);
                            fimul(framePtr + 80L, framePtr + 32L, framePtr + 80L);
                            firadd(framePtr + 80L, framePtr + 160L, framePtr + 80L, 1L);
                            firadd(framePtr + 80L, framePtr + 64L, framePtr + 80L, 1L);
                            isZero = fLT(framePtr + 80L, CC_MinLog) != 0L ? 1L : 0L;
                            if (isZero != 0L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                fiexpm(framePtr + 80L, framePtr + 80L, 0L);
                                isZero = fZero(framePtr + 80L) != 0L ? 1L : 0L;
                                if (isZero == 0L) {
                                    putI64(framePtr + 80L, (((getI32(framePtr + 84L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 80L) & 0xffffffffL));
                                }
                                firadd(framePtr + 96L, framePtr + 144L, framePtr + 32L, 1L);
                                fidiv(framePtr + 32L, framePtr + 80L, framePtr + 80L);
                                fidiv(framePtr + 80L, framePtr + 160L, framePtr + 32L);
                                isZero = fMLT(framePtr + 32L, CC_MachEp) != 0L ? 1L : 0L;
                                if (isZero != 0L) {
                                    constPtr = framePtr + 160L;
                                    result = isLess;
                                    tempVal1 = getI64(constPtr);
                                    putI64(resultPtr + 8L, getI64(constPtr + 8L));
                                    putI64(resultPtr, tempVal1);
                                    return result;
                                }
                                firadd(framePtr + 160L, framePtr + 80L, framePtr + 160L, 1L);
                                index2 = (long) (int) (index2 - 1L);
                                if (index2 == 0L || comma(isZero = fLT(framePtr + 192L, framePtr + 160L) != 0L ? 1L : 0L, isZero == 0L && comma(isZero = fLT(framePtr + 160L, framePtr + 176L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L ? 1L : 0L) == 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    step = 1;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 2: {
                        putI64(framePtr + 88L, 0x625000000000000L);
                        putI64(framePtr + 80L, 0xfffffffe00000001L);
                        isZero = fLE(CC_Max, framePtr + 192L) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            isZero = fLE(framePtr + 160L, CC_0) != 0L ? 1L : 0L;
                            if (isZero != 0L) {
                                putI64(framePtr + 168L, 0x100000000000000L);
                                putI64(framePtr + 160L, 1L);
                            }
                            isZero = fEQ(framePtr + 192L, CC_Max) != 0L ? 1L : 0L;
                            if (isZero != 0L) {
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
                    }
                    case 3: {
                        tempVal2 = 0x500000000000000L;
                        tempVal1 = 0xffffffff00000001L;
                        index2 = 0L;
                        index1 = 400L;
                        putI64(framePtr + 80L, tempVal1);
                        putI64(framePtr + 88L, tempVal2);
                        step = 5;
                        continue stepLoop;
                    }
                    case 4: {
                        firadd(CC_1, framePtr + 80L, framePtr + 32L, 0L);
                        fimul(framePtr + 32L, framePtr + 160L, framePtr + 160L);
                        fiJGamma(aPtr, framePtr + 160L, framePtr + 96L);
                        isZero = fLT(framePtr + 96L, framePtr + 144L) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            putI64(framePtr + 200L, getI64(framePtr + 168L));
                            putI64(framePtr + 192L, getI64(framePtr + 160L));
                            putI64(framePtr + 136L, getI64(framePtr + 104L));
                            putI64(framePtr + 128L, getI64(framePtr + 96L));
                            step = 3;
                            continue stepLoop;
                        } else {
                            fimul2(framePtr + 80L, framePtr + 80L);
                            isZero = fEQ(framePtr + 192L, CC_Max) != 0L ? 1L : 0L;
                            if (isZero != 0L) {
                                step = 4;
                                continue stepLoop;
                            } else {
                                step = 3;
                                continue stepLoop;
                            }
                        }
                    }
                    case 5: {
                        firadd(framePtr + 192L, framePtr + 176L, framePtr + 160L, 1L);
                        fimul(framePtr + 80L, framePtr + 160L, framePtr + 160L);
                        firadd(framePtr + 176L, framePtr + 160L, framePtr + 160L, 0L);
                        fiJGamma(aPtr, framePtr + 160L, framePtr + 96L);
                        firadd(framePtr + 192L, framePtr + 176L, framePtr + 32L, 1L);
                        firadd(framePtr + 176L, framePtr + 192L, framePtr + 16L, 0L);
                        fidiv(framePtr + 32L, framePtr + 16L, framePtr + 64L);
                        isZero = fMLT(framePtr + 64L, framePtr + 48L) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            constPtr = framePtr + 160L;
                            result = isLess;
                            tempVal1 = getI64(constPtr);
                            putI64(resultPtr + 8L, getI64(constPtr + 8L));
                            putI64(resultPtr, tempVal1);
                            return result;
                        }
                        firadd(framePtr + 96L, framePtr + 144L, framePtr + 64L, 1L);
                        fidiv(framePtr + 64L, framePtr + 144L, framePtr + 64L);
                        isZero = fMLT(framePtr + 64L, framePtr + 48L) != 0L ? 1L : 0L;
                        if (isZero != 0L || (long) (int) comma(isZero = fLE(framePtr + 160L, CC_0) != 0L ? 1L : 0L, isZero) != 0L) {
                            constPtr = framePtr + 160L;
                            result = isLess;
                            tempVal1 = getI64(constPtr);
                            putI64(resultPtr + 8L, getI64(constPtr + 8L));
                            putI64(resultPtr, tempVal1);
                            return result;
                        }
                        isZero = fLE(framePtr + 144L, framePtr + 96L) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            putI64(framePtr + 120L, getI64(framePtr + 104L));
                            putI64(framePtr + 112L, getI64(framePtr + 96L));
                            putI64(framePtr + 184L, getI64(framePtr + 168L));
                            putI64(framePtr + 176L, getI64(framePtr + 160L));
                            if (index2 < 0L) {
                                index2 = 1L;
                                putI64(framePtr + 80L, tempVal1);
                                putI64(framePtr + 88L, tempVal2);
                            } else {
                                if (index2 < 2L) {
                                    firadd(framePtr + 144L, framePtr + 128L, framePtr + 32L, 1L);
                                    firadd(framePtr + 112L, framePtr + 128L, framePtr + 16L, 1L);
                                    fidiv(framePtr + 32L, framePtr + 16L, framePtr + 80L);
                                    index2 = (long) (int) (index2 + 1L);
                                } else {
                                    fidiv2(framePtr + 80L, framePtr + 80L);
                                    firadd(framePtr + 80L, CC_0p5, framePtr + 80L, 0L);
                                    index2 = (long) (int) (index2 + 1L);
                                }
                            }
                        } else {
                            putI64(framePtr + 200L, getI64(framePtr + 168L));
                            putI64(framePtr + 192L, getI64(framePtr + 160L));
                            putI64(framePtr + 136L, getI64(framePtr + 104L));
                            putI64(framePtr + 128L, getI64(framePtr + 96L));
                            if (index2 < 1L) {
                                if (index2 < -1L) {
                                    fidiv2(framePtr + 80L, framePtr + 80L);
                                } else {
                                    firadd(framePtr + 144L, framePtr + 128L, framePtr + 32L, 1L);
                                    firadd(framePtr + 112L, framePtr + 128L, framePtr + 16L, 1L);
                                    fidiv(framePtr + 32L, framePtr + 16L, framePtr + 80L);
                                }
                            } else {
                                index2 = 0L;
                                putI64(framePtr + 80L, tempVal1);
                                putI64(framePtr + 88L, tempVal2);
                            }
                            index2 = (long) (int) (index2 - 1L);
                        }
                        index1 = (long) (int) (index1 - 1L);
                        if (index1 == 0L) {
                            constPtr = framePtr + 160L;
                            result = isLess;
                            tempVal1 = getI64(constPtr);
                            putI64(resultPtr + 8L, getI64(constPtr + 8L));
                            putI64(resultPtr, tempVal1);
                            return result;
                        }
                        step = 5;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long fiInvKhi2(long dfPtr, long probPtr, long resultPtr) {
        long isLess = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            if ((long) (int) getU32(probPtr) < 0L || (long) (int) comma(isLess = fLT(CC_1, probPtr) != 0L ? 1L : 0L, isLess) != 0L || (long) (int) comma(isLess = fLT(dfPtr, CC_1) != 0L ? 1L : 0L, isLess) != 0L || comma(isLess = fiInteger(dfPtr) != 0L ? 1L : 0L, isLess == 0L ? 1L : 0L) != 0L) {
                result = 24L;
                putI64(resultPtr + 8L, 0L);
                putI64(resultPtr, 0L);
            } else {
                fidiv2(dfPtr, framePtr + 24L);
                result = fiInvGamma(framePtr + 24L, probPtr, resultPtr) & 0xffL;
                fimul2(resultPtr, resultPtr);
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

}

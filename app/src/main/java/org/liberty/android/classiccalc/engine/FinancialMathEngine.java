package org.liberty.android.classiccalc.engine;

/**
 * TVM, amortization, cash flows (IRR/NPV), depreciation, bonds, date arithmetic, and percentage calculations.
 */
abstract class FinancialMathEngine extends ProbabilityMathEngine {
    long TVMAMORT(long startPeriodPtr, long endPeriodPtr, long nPtr, long iPtr, long pvPtr, long pmtPtr, long pyrPtr, long cYrPtr, long beginMode, long outPrinPtr, long outIntPtr, long outBalPtr) {
        long isZero = 0L;
        long status = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(304);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        putI64(outIntPtr + 8L, 0L);
                        putI64(outIntPtr, 0L);
                        putI64(outPrinPtr + 8L, 0L);
                        putI64(outPrinPtr, 0L);
                        status = TVMValidateParams(nPtr, iPtr, pvPtr, pmtPtr, CC_0, pyrPtr, cYrPtr, framePtr + 96L);
                        if ((long) (int) status != 0L) {
                            return status;
                        }
                        status = 29L;
                        tempVal1 = (getI64(startPeriodPtr) + 1L) & 0xffffffffL;
                        if (4L < tempVal1 || ((1L << (int) (tempVal1 & 31L)) & 21L) == 0L) {
                            return status;
                        }
                        status = 29L;
                        tempVal1 = (getI64(endPeriodPtr) + 1L) & 0xffffffffL;
                        if (4L < tempVal1 || ((1L << (int) (tempVal1 & 31L)) & 21L) == 0L) {
                            return status;
                        }
                        isZero = fiInteger(startPeriodPtr) != 0L ? 1L : 0L;
                        if (isZero == 0L || comma(isZero = fiInteger(endPeriodPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L || (long) (int) comma(isZero = fLT(nPtr, startPeriodPtr) != 0L ? 1L : 0L, isZero) != 0L || (long) (int) comma(isZero = fLT(startPeriodPtr, CC_1) != 0L ? 1L : 0L, isZero) != 0L) {
                            status = 29L;
                            return status;
                        }
                        firadd(startPeriodPtr, CC_N1, startPeriodPtr, 0L);
                        isZero = fLT(nPtr, startPeriodPtr) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            putI64(outPrinPtr + 8L, 0L);
                            putI64(outPrinPtr, 1L);
                            tempVal2 = 1L;
                            tempVal3 = 0L;
                            status = 0L;
                            putI64(outIntPtr + 8L, tempVal3);
                            putI64(outIntPtr, tempVal2);
                            return status;
                        }
                        firadd(startPeriodPtr, endPeriodPtr, framePtr + 80L, 0L);
                        isZero = fLT(nPtr, framePtr + 80L) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            putI64(framePtr + 88L, getI64(nPtr + 8L));
                            putI64(framePtr + 80L, getI64(nPtr));
                            fradd(framePtr + 80L, startPeriodPtr, framePtr + 64L, 1L);
                        } else {
                            putI64(framePtr + 72L, getI64(endPeriodPtr + 8L));
                            putI64(framePtr + 64L, getI64(endPeriodPtr));
                        }
                        tempVal1 = beginMode & 0xffL;
                        isZero = fLT(CC_480, framePtr + 80L) != 0L ? 1L : 0L;
                        if ((outBalPtr & 0xffffffffL) < 11L && isZero == 0L) {
                            putI64(framePtr + 120L, getI64(startPeriodPtr + 8L));
                            putI64(framePtr + 112L, getI64(startPeriodPtr));
                            firnd(pmtPtr, (long) (int) outBalPtr, framePtr + 16L, 0L);
                            firadd(framePtr + 96L, CC_1, framePtr + 96L, 0L);
                            putI64(framePtr + 40L, 0L);
                            putI64(framePtr + 32L, 0L);
                            putI64(framePtr + 56L, getI64(pvPtr + 8L));
                            putI64(framePtr + 48L, getI64(pvPtr));
                            step = 1;
                            continue stepLoop;
                        } else {
                            status = TVMAmortStep(startPeriodPtr, iPtr, pvPtr, pmtPtr, pyrPtr, cYrPtr, tempVal1 & 1L, framePtr + 32L);
                            if (1L < (status & 0xffffffffL)) {
                                return status;
                            }
                            firadd(startPeriodPtr, framePtr + 64L, startPeriodPtr, 0L);
                            status = TVMAmortStep(startPeriodPtr, iPtr, pvPtr, pmtPtr, pyrPtr, cYrPtr, tempVal1 & 1L, framePtr + 48L);
                            if (1L < (status & 0xffffffffL)) {
                                return status;
                            }
                            isZero = fZero(framePtr + 48L) != 0L ? 1L : 0L;
                            if (isZero == 0L) {
                                putI64(framePtr + 48L, (((getI32(framePtr + 52L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 48L) & 0xffffffffL));
                            }
                            fMakeZeroIfClose(framePtr + 48L, pvPtr);
                            step = 3;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        isZero = fLE(framePtr + 112L, CC_0) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            if ((long) (int) getI64(framePtr + 32L) != 0L) {
                                pmtPtr = framePtr + 16L;
                                step = 3;
                                continue stepLoop;
                            } else {
                                putI64(framePtr + 40L, getI64(framePtr + 56L));
                                putI64(framePtr + 32L, getI64(framePtr + 48L));
                                isZero = fZero(framePtr + 32L) != 0L ? 1L : 0L;
                                if (isZero == 0L) {
                                    putI64(framePtr + 32L, (((getI32(framePtr + 36L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 32L) & 0xffffffffL));
                                }
                                putI64(framePtr + 120L, getI64(framePtr + 72L));
                                putI64(framePtr + 112L, getI64(framePtr + 64L));
                                step = 2;
                                continue stepLoop;
                            }
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 2: {
                        fradd(framePtr + 112L, CC_N1, framePtr + 112L, 0L);
                        if ((tempVal1 & 1L) == 0L) {
                            fimul(framePtr + 48L, framePtr + 96L, framePtr + 48L);
                            firnd(framePtr + 48L, (long) (int) outBalPtr, framePtr + 48L, 0L);
                        }
                        firadd(framePtr + 48L, framePtr + 16L, framePtr + 48L, 0L);
                        firnd(framePtr + 48L, (long) (int) outBalPtr, framePtr + 48L, 0L);
                        tempVal1 = 0L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 3: {
                        fimul(pmtPtr, framePtr + 64L, framePtr + 64L);
                        putI64(startPeriodPtr + 8L, getI64(framePtr + 88L));
                        putI64(startPeriodPtr, getI64(framePtr + 80L));
                        firadd(framePtr + 48L, framePtr + 32L, outPrinPtr, 0L);
                        putI64(framePtr + 120L, getI64(outPrinPtr + 8L));
                        putI64(framePtr + 112L, getI64(outPrinPtr));
                        if ((long) (int) getI64(framePtr + 112L) == 3L) {
                            putI32(framePtr + 116L, getI64(framePtr + 112L) >>> 32);
                            putI64(framePtr + 112L, (((getI32(framePtr + 116L) & 0xffffffffL)) << 32) | 1L);
                        }
                        isZero = fZero(framePtr + 112L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            putI64(framePtr + 112L, (((getI32(framePtr + 116L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 112L) & 0xffffffffL));
                        }
                        status = firadd(framePtr + 64L, framePtr + 112L, outIntPtr, 0L);
                        fNorm_L((long) (int) status, outIntPtr);
                        fNormUp_L(outPrinPtr);
                        outIntPtr = pvPtr;
                        tempVal2 = getI64(framePtr + 48L);
                        tempVal3 = getI64(framePtr + 56L);
                        status = 0L;
                        putI64(outIntPtr + 8L, tempVal3);
                        putI64(outIntPtr, tempVal2);
                        return status;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long TVMValidateParams(long nPtr, long iPtr, long pvPtr, long pmtPtr, long fvPtr, long pyrPtr, long cYrPtr, long outPeriodicRatePtr) {
        long isZero = 0L;
        long result = 0L;
        if ((4L < ((getI32(nPtr) + 1L) & 0xffffffffL) || ((1L << (int) ((getI32(nPtr) + 1L) & 31L)) & 21L) == 0L) && (4L < ((getI32(iPtr) + 1L) & 0xffffffffL) || ((1L << (int) ((getI32(iPtr) + 1L) & 31L)) & 21L) == 0L) && (4L < ((getI32(pvPtr) + 1L) & 0xffffffffL) || ((1L << (int) ((getI32(pvPtr) + 1L) & 31L)) & 21L) == 0L) && (4L < ((getI32(pmtPtr) + 1L) & 0xffffffffL) || ((1L << (int) ((getI32(pmtPtr) + 1L) & 31L)) & 21L) == 0L) && (4L < ((getI32(fvPtr) + 1L) & 0xffffffffL) || ((1L << (int) ((getI32(fvPtr) + 1L) & 31L)) & 21L) == 0L) && (4L < ((getI32(pyrPtr) + 1L) & 0xffffffffL) || ((1L << (int) ((getI32(pyrPtr) + 1L) & 31L)) & 21L) == 0L)) {
            if (4L < ((getI32(cYrPtr) + 1L) & 0xffffffffL)) {
                return 24L;
            }
            if (((1L << (int) ((getI32(cYrPtr) + 1L) & 31L)) & 21L) == 0L) {
                return 24L;
            }
        }
        if (getI32(pyrPtr) < 0L || (long) (int) comma(isZero = fZero(pyrPtr) != 0L ? 1L : 0L, isZero) != 0L || comma(isZero = fiInteger(pyrPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L || getI32(cYrPtr) < 0L || (long) (int) comma(isZero = fZero(cYrPtr) != 0L ? 1L : 0L, isZero) != 0L || comma(isZero = fiInteger(cYrPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
            result = 23L;
        } else {
            isZero = fLE(iPtr, CC_n100) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                result = 25L;
            } else {
                if (outPeriodicRatePtr != 0L) {
                    result = getI64(iPtr);
                    putI64(outPeriodicRatePtr + 8L, getI64(iPtr + 8L));
                    putI64(outPeriodicRatePtr, result);
                    FinCompoundFactor(outPeriodicRatePtr, pyrPtr, cYrPtr);
                }
                result = 0L;
            }
        }
        return result;
    }

    long TVMAmortStep(long nPtr, long periodicRatePtr, long pvPtr, long pmtPtr, long beginMode, long roundDigits, long outIntPtr, long outPrinPtr) {
        long isZero = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            isZero = fLT(nPtr, CC_1) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                status = fichs2(pvPtr, outPrinPtr);
                return status;
            }
            if ((outIntPtr & 1L) == 0L) {
                status = TVMFV(nPtr, periodicRatePtr, pvPtr, pmtPtr, outPrinPtr, beginMode, roundDigits, 0L);
                return status;
            }
            firadd(pvPtr, pmtPtr, outPrinPtr, 0L);
            isZero = fEQ(nPtr, CC_1) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                isZero = fZero(outPrinPtr) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    status = 0L;
                } else {
                    status = 0L;
                    putI32(outPrinPtr, -getI64(outPrinPtr));
                }
                return status;
            } else {
                firadd(nPtr, CC_N1, framePtr + 40L, 0L);
                putI64(framePtr + 24L, getI64(outPrinPtr + 8L));
                putI64(framePtr + 16L, getI64(outPrinPtr));
                nPtr = framePtr + 40L;
                pvPtr = framePtr + 16L;
                status = TVMFV(nPtr, periodicRatePtr, pvPtr, pmtPtr, outPrinPtr, beginMode, roundDigits, 0L);
                return status;
            }
        } finally {
            sp = savedSp;
        }
    }

    long TVMAMORTLoop(long startPeriodPtr, long endPeriodPtr, long nPtr, long iPtr, long pvPtr, long pmtPtr, long pyrPtr, long cYrPtr, long beginMode, long outPrinPtr, long outIntPtr, long outBalPtr) {
        long isLess = 0L;
        long tempVal = 0L;
        long status = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(272);
        try {
            if (outIntPtr == 0L) {
                result = 0L;
                return result;
            }
            status = TVMValidateParams(nPtr, iPtr, pvPtr, pmtPtr, CC_0, pyrPtr, cYrPtr, 0L);
            result = status & 0xffffffffL;
            if ((long) (int) status != 0L) {
                return result;
            }
            result = 29L;
            tempVal = (getI64(startPeriodPtr) + 1L) & 0xffffffffL;
            if (4L < tempVal || ((1L << (int) (tempVal & 31L)) & 21L) == 0L) {
                return result;
            }
            result = 29L;
            tempVal = (getI64(endPeriodPtr) + 1L) & 0xffffffffL;
            if (4L < tempVal || ((1L << (int) (tempVal & 31L)) & 21L) == 0L) {
                return result;
            }
            isLess = fiInteger(startPeriodPtr) != 0L ? 1L : 0L;
            if (isLess == 0L || comma(isLess = fiInteger(endPeriodPtr) != 0L ? 1L : 0L, isLess == 0L ? 1L : 0L) != 0L || (long) (int) comma(isLess = fLT(nPtr, startPeriodPtr) != 0L ? 1L : 0L, isLess) != 0L) {
                result = 29L;
            } else {
                isLess = fLE(nPtr, startPeriodPtr) != 0L ? 1L : 0L;
                if (isLess != 0L) {
                    result = 0L;
                    return result;
                }
                putI64(framePtr + 88L, getI64(startPeriodPtr + 8L));
                putI64(framePtr + 80L, getI64(startPeriodPtr));
                while (true) {
                    if (comma(isLess = fLT(framePtr + 80L, nPtr) != 0L ? 1L : 0L, isLess) == 0L) {
                        result = 0L;
                        return result;
                    }
                    putI64(framePtr + 24L, getI64(pvPtr + 8L));
                    putI64(framePtr + 16L, getI64(pvPtr));
                    putI64(framePtr + 40L, getI64(framePtr + 88L));
                    putI64(framePtr + 32L, getI64(framePtr + 80L));
                    TVMAMORT(framePtr + 80L, endPeriodPtr, nPtr, iPtr, framePtr + 16L, pmtPtr, pyrPtr, cYrPtr, beginMode & 1L, framePtr + 64L, framePtr + 48L, outPrinPtr & 0xffffffffL);
                    tempVal = callIndirect6(outIntPtr, outBalPtr, framePtr + 32L, framePtr + 80L, framePtr + 16L, framePtr + 64L, framePtr + 48L) & 0xffffffffL;
                    if (1L < tempVal) {
                        break;
                    }
                    firadd(framePtr + 80L, CC_1, framePtr + 80L, 0L);
                }
                result = tempVal;
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long TVMN(long nPtr, long iPtr, long pvPtr, long pmtPtr, long fvPtr, long pyrPtr, long cYrPtr, long beginMode) {
        long isZero1 = 0L;
        long isZero2 = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long status3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(288);
        try {
            status2 = TVMValidateParams(CC_0, iPtr, pvPtr, pmtPtr, fvPtr, pyrPtr, cYrPtr, framePtr + 120L);
            if ((long) (int) status2 != 0L) {
                putI64(framePtr + 112L, 0L);
                putI64(framePtr + 104L, 0L);
                putI64(nPtr + 8L, getI64(framePtr + 112L));
                putI64(nPtr, getI64(framePtr + 104L));
                return status2;
            }
            putI64(framePtr + 88L, getI64(pmtPtr + 8L));
            putI64(framePtr + 80L, getI64(pmtPtr));
            firadd(pvPtr, fvPtr, framePtr + 104L, 0L);
            if ((beginMode & 1L) != 0L) {
                firadd(framePtr + 120L, CC_1, framePtr + 64L, 0L);
                fimul(framePtr + 80L, framePtr + 64L, framePtr + 80L);
            }
            fimul(framePtr + 120L, fvPtr, framePtr + 16L);
            firadd(framePtr + 16L, framePtr + 80L, framePtr + 48L, 1L);
            status3 = fidiv(framePtr + 104L, framePtr + 48L, framePtr + 64L);
            if ((long) (int) status3 == 4L) {
                status2 = 27L;
                putI64(framePtr + 112L, 0L);
                putI64(framePtr + 104L, 0L);
                putI64(nPtr + 8L, getI64(framePtr + 112L));
                putI64(nPtr, getI64(framePtr + 104L));
                return status2;
            }
            if (4L < ((getI64(framePtr + 64L) + 1L) & 0xffffffffL) || ((1L << (int) ((getI64(framePtr + 64L) + 1L) & 31L)) & 21L) == 0L) {
                status2 = 26L;
                putI64(framePtr + 112L, 0L);
                putI64(framePtr + 104L, 0L);
                putI64(nPtr + 8L, getI64(framePtr + 112L));
                putI64(nPtr, getI64(framePtr + 104L));
                return status2;
            }
            isZero1 = fZero(framePtr + 64L) != 0L ? 1L : 0L;
            if (isZero1 != 0L) {
                putI64(framePtr + 112L, 0L);
                putI64(framePtr + 104L, 1L);
                status2 = 0L;
                putI64(nPtr + 8L, getI64(framePtr + 112L));
                putI64(nPtr, getI64(framePtr + 104L));
                return status2;
            }
            isZero1 = fZero(framePtr + 120L) != 0L ? 1L : 0L;
            if (isZero1 != 0L) {
                status2 = 0L;
                putI64(nPtr + 8L, getI64(framePtr + 112L));
                putI64(nPtr, getI64(framePtr + 104L));
                return status2;
            }
            fimul(framePtr + 120L, framePtr + 64L, framePtr + 32L);
            if ((long) (int) getU32(framePtr + 32L) < 1L) {
                isZero1 = 1L;
            } else {
                fimul(framePtr + 120L, pvPtr, framePtr + 16L);
                firadd(framePtr + 16L, framePtr + 80L, framePtr + 48L, 0L);
                status3 = fidiv(framePtr + 104L, framePtr + 48L, framePtr + 64L);
                if ((long) (int) status3 == 4L) {
                    status2 = 27L;
                    putI64(framePtr + 112L, 0L);
                    putI64(framePtr + 104L, 0L);
                    putI64(nPtr + 8L, getI64(framePtr + 112L));
                    putI64(nPtr, getI64(framePtr + 104L));
                    return status2;
                }
                if (4L < ((getI64(framePtr + 64L) + 1L) & 0xffffffffL) || ((1L << (int) ((getI64(framePtr + 64L) + 1L) & 31L)) & 21L) == 0L) {
                    status2 = 26L;
                    putI64(framePtr + 112L, 0L);
                    putI64(framePtr + 104L, 0L);
                    putI64(nPtr + 8L, getI64(framePtr + 112L));
                    putI64(nPtr, getI64(framePtr + 104L));
                    return status2;
                }
                fimul(framePtr + 120L, framePtr + 64L, framePtr + 32L);
                isZero1 = 0L;
            }
            isZero2 = fZero(framePtr + 32L) != 0L ? 1L : 0L;
            if (isZero2 == 0L) {
                putU32(framePtr + 32L, -getU32(framePtr + 32L));
            }
            status3 = filnp1(framePtr + 32L, framePtr + 32L);
            if ((status3 & 0xffffffffL) < 2L && ((getU32(framePtr + 32L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(framePtr + 32L) + 1L) & 31L)) & 21L) != 0L && comma(status3 = filnp1(framePtr + 120L, framePtr + 120L), (status3 & 0xffffffffL) < 2L && ((getU32(framePtr + 120L) + 1L) & 0xffffffffL) < 5L ? 1L : 0L) != 0L && ((1L << (int) ((getU32(framePtr + 120L) + 1L) & 31L)) & 21L) != 0L) {
                fidiv(framePtr + 32L, framePtr + 120L, nPtr);
                if (isZero1 != 0L && comma(isZero1 = fZero(nPtr) != 0L ? 1L : 0L, isZero1 == 0L ? 1L : 0L) != 0L) {
                    putI32(nPtr, -getI64(nPtr));
                }
                status1 = fNormUp_L(nPtr) & 0xffffffffL;
                status2 = status1;
                return status2;
            }
            status2 = 26L;
            putI64(framePtr + 112L, 0L);
            putI64(framePtr + 104L, 0L);
            putI64(nPtr + 8L, getI64(framePtr + 112L));
            putI64(nPtr, getI64(framePtr + 104L));
            return status2;
        } finally {
            sp = savedSp;
        }
    }

    long TVMI(long nPtr, long iPtr, long pvPtr, long pmtPtr, long fvPtr, long pyrPtr, long cYrPtr, long beginMode, long guessPtr, long hasGuess) {
        long dataPtr1 = 0L;
        long longVal1 = 0L;
        long longVal2 = 0L;
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long status3 = 0L;
        long status4 = 0L;
        long intVal1 = 0L;
        long intVal2 = 0L;
        long dataPtr2 = 0L;
        long index = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(480);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        putI64(framePtr + 296L, getI64(iPtr + 8L));
                        putI64(framePtr + 288L, getI64(iPtr));
                        putI64(iPtr + 8L, 0L);
                        putI64(iPtr, 1L);
                        status2 = TVMValidateParams(nPtr, iPtr, pvPtr, pmtPtr, fvPtr, pyrPtr, cYrPtr, 0L);
                        dataPtr2 = status2 & 0xffffffffL;
                        putI64(iPtr + 8L, 0L);
                        putI64(iPtr, 0L);
                        if ((long) (int) status2 != 0L) {
                            return dataPtr2 & 0xffffffffL;
                        }
                        if ((long) (int) hasGuess == 0L) {
                            putI64(framePtr + 280L, getI64(nPtr + 8L));
                            putI64(framePtr + 272L, getI64(nPtr));
                            intVal2 = (long) (int) hasGuess;
                        } else {
                            fiIP(nPtr, framePtr + 272L);
                            fiFP(nPtr, framePtr + 256L);
                            isZero = fZero(framePtr + 256L) != 0L ? 1L : 0L;
                            intVal2 = 0L;
                            if (isZero == 0L) {
                                intVal2 = (long) (int) hasGuess;
                            }
                        }
                        putI64(framePtr + 248L, getI64(pvPtr + 8L));
                        putI64(framePtr + 240L, getI64(pvPtr));
                        putI64(framePtr + 232L, getI64(fvPtr + 8L));
                        putI64(framePtr + 224L, getI64(fvPtr));
                        if ((beginMode & 1L) == 0L) {
                            longVal1 = -96L;
                            longVal2 = -96L;
                        } else {
                            longVal1 = -80L;
                            longVal2 = -80L;
                        }
                        firadd(framePtr + 320L + longVal1, pmtPtr, framePtr + 320L + longVal2, 0L);
                        if ((long) (int) getI64(framePtr + 272L) < 0L || (long) (int) comma(isZero = fZero(framePtr + 272L) != 0L ? 1L : 0L, isZero) != 0L) {
                            dataPtr2 = 29L;
                            return dataPtr2 & 0xffffffffL;
                        }
                        isZero = fZero(pmtPtr) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            isZero = fLE(framePtr + 272L, CC_0p99999) != 0L ? 1L : 0L;
                            if (isZero != 0L || 8L < getI32(framePtr + 276L)) {
                                dataPtr2 = 29L;
                                return dataPtr2 & 0xffffffffL;
                            }
                            if (Long.compareUnsigned(getI64(framePtr + 272L), 0L) < 0L) {
                                putI64(framePtr + 280L, 0x100000000000000L);
                                putI64(framePtr + 272L, 1L);
                            }
                        }
                        isZero = fEQ(nPtr, CC_1) != 0L ? 1L : 0L;
                        if (isZero != 0L || (long) (int) comma(isZero = fZero(pmtPtr) != 0L ? 1L : 0L, isZero) != 0L) {
                            isZero = fZero(framePtr + 240L) != 0L ? 1L : 0L;
                            if (isZero != 0L && (long) (int) comma(isZero = fZero(framePtr + 224L) != 0L ? 1L : 0L, isZero) != 0L) {
                                dataPtr2 = 27L;
                                return dataPtr2 & 0xffffffffL;
                            }
                            isZero = fZero(framePtr + 240L) != 0L ? 1L : 0L;
                            if (!(isZero == 0L && comma(isZero = fZero(framePtr + 224L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(status1 = fiSignEQ(framePtr + 240L, framePtr + 224L) & 0xffffffffL, (status1 & 1L) == 0L ? 1L : 0L) != 0L)) {
                                dataPtr2 = 26L;
                                return dataPtr2 & 0xffffffffL;
                            }
                            fidiv(framePtr + 224L, framePtr + 240L, framePtr + 224L);
                            isZero = fZero(framePtr + 224L) != 0L ? 1L : 0L;
                            if (isZero == 0L) {
                                putI64(framePtr + 224L, (((getI32(framePtr + 228L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 224L) & 0xffffffffL));
                            }
                            fidiv(CC_1, framePtr + 272L, framePtr + 272L);
                            fpow(framePtr + 224L, framePtr + 272L, framePtr + 288L, 1L);
                            firadd(framePtr + 288L, CC_N1, framePtr + 288L, 0L);
                            step = 1;
                            continue stepLoop;
                        } else {
                            dataPtr2 = framePtr + 240L;
                            status1 = fiSignEQ(framePtr + 240L, framePtr + 224L) & 0xffffffffL;
                            if (!((status1 & 1L) == 0L || (long) (int) comma(isZero = fZero(framePtr + 240L) != 0L ? 1L : 0L, isZero) != 0L || (long) (int) comma(isZero = fZero(framePtr + 224L) != 0L ? 1L : 0L, isZero) != 0L)) {
                                isZero = fZero(framePtr + 240L) != 0L ? 1L : 0L;
                                if (!((isZero == 0L || comma(status1 = fiSignEQ(pmtPtr, framePtr + 224L) & 0xffffffffL, (status1 & 1L) == 0L ? 1L : 0L) != 0L) && comma(isZero = fZero(framePtr + 224L) != 0L ? 1L : 0L, isZero == 0L || comma(status1 = fiSignEQ(pmtPtr, framePtr + 240L) & 0xffffffffL, (status1 & 1L) == 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L)) {
                                    dataPtr2 = 26L;
                                    return dataPtr2 & 0xffffffffL;
                                }
                                dataPtr2 = 27L;
                                return dataPtr2 & 0xffffffffL;
                            }
                            status2 = TVMISolveBound(framePtr + 288L, framePtr + 240L, framePtr + 224L, pyrPtr, cYrPtr, pmtPtr);
                            dataPtr1 = framePtr + 224L;
                            if ((status2 & 1L) == 0L) {
                                dataPtr1 = dataPtr2;
                            }
                            index = 0L;
                            putI32(framePtr + 20L, 0L);
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        isZero = fEQ(pyrPtr, cYrPtr) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            cYrPtr = pyrPtr;
                            if (((getI64(framePtr + 288L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 288L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 288L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
                                intVal2 = getI32(framePtr + 292L);
                                putI64(framePtr + 288L, ((((getI32(framePtr + 292L) + 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 288L) & 0xffffffffL));
                                if (49997L < intVal2) {
                                    putI64(framePtr + 296L, 0x999999999999999L);
                                    putI64(framePtr + 288L, 0xc34f00000002L);
                                }
                            }
                        } else {
                            fidiv(pyrPtr, cYrPtr, framePtr + 208L);
                            flnp1(framePtr + 288L, framePtr + 288L);
                            fmul(framePtr + 288L, framePtr + 208L, framePtr + 288L);
                            fexpm(framePtr + 288L, 0L, framePtr + 288L);
                            if (((getI64(framePtr + 288L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 288L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 288L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
                                intVal2 = getI32(framePtr + 292L);
                                putI64(framePtr + 288L, ((((getI32(framePtr + 292L) + 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 288L) & 0xffffffffL));
                                if (49997L < intVal2) {
                                    putI64(framePtr + 296L, 0x999999999999999L);
                                    putI64(framePtr + 288L, 0xc34f00000002L);
                                }
                            }
                        }
                        fimul(framePtr + 288L, cYrPtr, iPtr);
                        status1 = fNormUp_L(iPtr) & 0xffffffffL;
                        status3 = status1;
                        dataPtr2 = status3 & 0xffffffffL;
                        return dataPtr2 & 0xffffffffL;
                    }
                    case 2: {
                        if (guessPtr != 0L && comma(status3 = callIndirect2(guessPtr, index, framePtr + 288L), status3 & 1L) != 0L) {
                            dataPtr2 = 30L;
                            return dataPtr2 & 0xffffffffL;
                        }
                        firadd(framePtr + 288L, CC_1, framePtr + 208L, 0L);
                        firadd(framePtr + 272L, CC_N1, framePtr + 192L, 0L);
                        status3 = FinAnnuityFactor(framePtr + 288L, framePtr + 192L, framePtr + 176L, framePtr + 160L, 0L);
                        if ((long) (int) status3 != 0L) {
                            dataPtr2 = status3 & 0xffffffffL;
                            return dataPtr2 & 0xffffffffL;
                        }
                        fimul(pmtPtr, framePtr + 176L, framePtr + 144L);
                        if (intVal2 == 2L) {
                            if ((status2 & 1L) == 0L) {
                                putI64(framePtr + 104L, getI64(framePtr + 296L));
                                putI64(framePtr + 96L, getI64(framePtr + 288L));
                            } else {
                                fradd(framePtr + 288L, CC_1, framePtr + 96L, 0L);
                                fidiv(framePtr + 288L, framePtr + 96L, framePtr + 96L);
                                if ((long) (int) getI64(framePtr + 96L) == 3L) {
                                    putI64(framePtr + 96L, (((getI32(framePtr + 100L) & 0xffffffffL)) << 32) | 1L);
                                }
                                isZero = fZero(framePtr + 96L) != 0L ? 1L : 0L;
                                if (isZero == 0L) {
                                    putI64(framePtr + 96L, (((getI32(framePtr + 100L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 96L) & 0xffffffffL));
                                }
                            }
                            fimul(framePtr + 96L, framePtr + 256L, framePtr + 112L);
                            firadd(CC_1, framePtr + 112L, framePtr + 112L, 0L);
                            step = 3;
                            continue stepLoop;
                        } else {
                            if (intVal2 == 1L) {
                                status3 = fpow(framePtr + 208L, framePtr + 256L, framePtr + 112L, 1L);
                                if ((status3 & 0xffffffffL) >= 2L) {
                                    dataPtr2 = status3 & 0xffffffffL;
                                    return dataPtr2 & 0xffffffffL;
                                }
                                if ((status2 & 1L) == 0L) {
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    fidiv(pvPtr, framePtr + 112L, dataPtr1);
                                    if ((beginMode & 1L) == 0L) {
                                        step = 5;
                                        continue stepLoop;
                                    } else {
                                        step = 4;
                                        continue stepLoop;
                                    }
                                }
                            } else {
                                step = 5;
                                continue stepLoop;
                            }
                        }
                    }
                    case 3: {
                        fimul(pvPtr, framePtr + 112L, dataPtr1);
                        if ((beginMode & 1L) != 0L) {
                            step = 4;
                            continue stepLoop;
                        } else {
                            step = 5;
                            continue stepLoop;
                        }
                    }
                    case 4: {
                        firadd(dataPtr1, pmtPtr, dataPtr1, 0L);
                        step = 5;
                        continue stepLoop;
                    }
                    case 5: {
                        fimul(framePtr + 240L, framePtr + 160L, framePtr + 128L);
                        firadd(framePtr + 144L, framePtr + 128L, framePtr + 144L, 0L);
                        fimul(framePtr + 144L, framePtr + 208L, framePtr + 208L);
                        status3 = FinAnnuityFactor(framePtr + 288L, framePtr + 272L, framePtr + 176L, framePtr + 160L, 0L);
                        if ((long) (int) status3 != 0L) {
                            dataPtr2 = status3 & 0xffffffffL;
                            return dataPtr2 & 0xffffffffL;
                        }
                        TVMNewtonStep(framePtr + 272L, framePtr + 288L, framePtr + 176L, framePtr + 112L);
                        firadd(framePtr + 288L, CC_1, framePtr + 80L, 0L);
                        fimul(framePtr + 80L, framePtr + 112L, framePtr + 80L);
                        fimul(framePtr + 80L, pmtPtr, framePtr + 80L);
                        fimul(framePtr + 272L, framePtr + 208L, framePtr + 64L);
                        firadd(framePtr + 80L, framePtr + 64L, framePtr + 80L, 0L);
                        status4 = fidiv(framePtr + 208L, framePtr + 80L, framePtr + 96L);
                        if ((long) (int) status4 == 4L) {
                            step = 6;
                            continue stepLoop;
                        } else {
                            if (((getI64(framePtr + 96L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 96L) + 1L) & 31L)) & 21L) != 0L) {
                                status4 = fidiv(framePtr + 224L, framePtr + 208L, framePtr + 128L);
                                isZero = fZero(framePtr + 128L) != 0L ? 1L : 0L;
                                if (isZero == 0L) {
                                    putU32(framePtr + 128L, -getU32(framePtr + 128L));
                                }
                                if ((long) (int) status4 == 4L) {
                                    step = 6;
                                    continue stepLoop;
                                } else {
                                    if (4L < ((getU32(framePtr + 128L) + 1L) & 0xffffffffL) || ((1L << (int) ((getU32(framePtr + 128L) + 1L) & 31L)) & 21L) == 0L) {
                                        step = 7;
                                        continue stepLoop;
                                    } else {
                                        status3 = TVMICompoundFactor(framePtr + 288L, framePtr + 128L, framePtr + 96L);
                                        if ((long) (int) status3 == 0L) {
                                            firadd(framePtr + 96L, framePtr + 288L, framePtr + 48L, 1L);
                                            putI64(framePtr + 296L, getI64(framePtr + 104L));
                                            putI64(framePtr + 288L, getI64(framePtr + 96L));
                                            isZero = fZero(framePtr + 48L) != 0L ? 1L : 0L;
                                            if (isZero != 0L) {
                                                step = 8;
                                                continue stepLoop;
                                            } else {
                                                isZero = fZero(framePtr + 96L) != 0L ? 1L : 0L;
                                                if (isZero == 0L) {
                                                    fidiv(framePtr + 48L, framePtr + 96L, framePtr + 48L);
                                                    if (getI32(framePtr + 52L) < -12L) {
                                                        step = 8;
                                                        continue stepLoop;
                                                    } else {
                                                        if (getI32(framePtr + 52L) == -12L) {
                                                            putI32(framePtr + 20L, getI32(framePtr + 20L) + 1L);
                                                            intVal1 = 2L;
                                                            if (getI32(framePtr + 20L) != 16L) {
                                                                intVal1 = 4L;
                                                            }
                                                            step = 10;
                                                            continue stepLoop;
                                                        } else {
                                                            step = 9;
                                                            continue stepLoop;
                                                        }
                                                    }
                                                } else {
                                                    step = 9;
                                                    continue stepLoop;
                                                }
                                            }
                                        } else {
                                            intVal1 = 1L;
                                            dataPtr2 = status3 & 0xffffffffL;
                                            step = 10;
                                            continue stepLoop;
                                        }
                                    }
                                }
                            } else {
                                step = 7;
                                continue stepLoop;
                            }
                        }
                    }
                    case 6: {
                        dataPtr2 = 31L;
                        intVal1 = 1L;
                        step = 10;
                        continue stepLoop;
                    }
                    case 7: {
                        intVal1 = 1L;
                        dataPtr2 = 3L;
                        step = 10;
                        continue stepLoop;
                    }
                    case 8: {
                        intVal1 = 2L;
                        step = 10;
                        continue stepLoop;
                    }
                    case 9: {
                        intVal1 = 4L;
                        step = 10;
                        continue stepLoop;
                    }
                    case 10: {
                        if (intVal1 != 4L) {
                            if (intVal1 != 2L) {
                                return dataPtr2 & 0xffffffffL;
                            }
                            if ((status2 & 1L) != 0L) {
                                isZero = fLE(framePtr + 288L, CC_N0p9999999999) != 0L ? 1L : 0L;
                                if (isZero != 0L) {
                                    dataPtr2 = 3L;
                                    return dataPtr2 & 0xffffffffL;
                                }
                                fradd(framePtr + 288L, CC_1, framePtr + 208L, 0L);
                                status4 = fidiv(framePtr + 288L, framePtr + 208L, framePtr + 288L);
                                if ((getI64(framePtr + 288L) & 0xffffffffL) == 3L) {
                                    putI64(framePtr + 288L, (((getI32(framePtr + 292L) & 0xffffffffL)) << 32) | 1L);
                                }
                                isZero = fZero(framePtr + 288L) != 0L ? 1L : 0L;
                                if (isZero == 0L) {
                                    putI64(framePtr + 288L, (((getI32(framePtr + 292L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 288L) & 0xffffffffL));
                                }
                                if ((long) (int) status4 == 4L) {
                                    dataPtr2 = 31L;
                                    return dataPtr2 & 0xffffffffL;
                                }
                                dataPtr2 = 3L;
                                if (4L < ((getI64(framePtr + 288L) + 1L) & 0xffffffffL) || ((1L << (int) ((getI64(framePtr + 288L) + 1L) & 31L)) & 21L) == 0L) {
                                    return dataPtr2 & 0xffffffffL;
                                }
                            }
                            step = 1;
                            continue stepLoop;
                        } else {
                            index = (long) (int) (index + 1L);
                            step = 2;
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

    long TVMISolveBound(long nPtr, long pvPtr, long pmtPtr, long fvPtr, long beginMode, long outRateBoundPtr) {
        long isZero = 0L;
        long tempVal = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            if (4L < ((getU32(nPtr) + 1L) & 0xffffffffL) || ((1L << (int) ((getU32(nPtr) + 1L) & 31L)) & 21L) == 0L || comma(FinCompoundFactor(nPtr, fvPtr, beginMode), (long) (int) getU32(nPtr) < 0L && -1L < (long) (int) getU32(nPtr + 4L) ? 1L : 0L) != 0L) {
                putU32(nPtr + 8L, 0L);
                putU32(nPtr + 12L, 0L);
                putU32(nPtr, 1L);
                putU32(nPtr + 4L, 0L);
            }
            isZero = fZero(pvPtr) != 0L ? 1L : 0L;
            if (isZero != 0L || comma(tempVal = fiSignEQ(pvPtr, outRateBoundPtr) & 0xffffffffL, tempVal & 1L) != 0L) {
                result = 0L;
            } else {
                putI64(framePtr + 24L, getI64(pvPtr + 8L));
                putI64(framePtr + 16L, getI64(pvPtr));
                result = getI64(pmtPtr);
                putI64(pvPtr + 8L, getI64(pmtPtr + 8L));
                putI64(pvPtr, result);
                putI64(pmtPtr + 8L, getI64(framePtr + 24L));
                putI64(pmtPtr, getI64(framePtr + 16L));
                isZero = fZero(nPtr) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putI64(framePtr + 24L, getI64(nPtr + 8L));
                    putI64(framePtr + 16L, getI64(nPtr));
                    firadd(nPtr, CC_1, nPtr, 0L);
                    isZero = fZero(framePtr + 16L) != 0L ? 1L : 0L;
                    if (isZero == 0L) {
                        putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 16L) & 0xffffffffL));
                    }
                    fidiv(framePtr + 16L, nPtr, nPtr);
                }
                result = 1L;
            }
            if (getU32(nPtr + 4L) < 0xfffffff5L || 2L < (long) (int) getU32(outRateBoundPtr + 4L)) {
                putU32(nPtr + 8L, 0L);
                putU32(nPtr + 12L, 0L);
                putU32(nPtr, 1L);
                putU32(nPtr + 4L, 0L);
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    void TVMNewtonStep(long ratePtr, long funcValPtr, long derivValPtr, long outNextRatePtr) {
        long isZero = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            firadd(ratePtr, derivValPtr, framePtr + 40L, 1L);
            isZero = fZero(framePtr + 40L) != 0L ? 1L : 0L;
            if (isZero != 0L || (long) (int) (getI32(framePtr + 44L) - getU32(ratePtr + 4L)) < -5L) {
                firadd(ratePtr, CC_3, outNextRatePtr, 1L);
                fidiv(outNextRatePtr, CC_4, outNextRatePtr);
                fimul(outNextRatePtr, funcValPtr, outNextRatePtr);
                firadd(outNextRatePtr, CC_1, outNextRatePtr, 0L);
                fimul(outNextRatePtr, funcValPtr, outNextRatePtr);
                firadd(ratePtr, CC_N2, framePtr + 24L, 0L);
                fidiv(framePtr + 24L, CC_3, framePtr + 24L);
                fimul(framePtr + 24L, outNextRatePtr, outNextRatePtr);
                firadd(outNextRatePtr, CC_1, outNextRatePtr, 0L);
                firadd(ratePtr, CC_N1, framePtr + 24L, 0L);
                fimul(framePtr + 24L, ratePtr, framePtr + 24L);
                fidiv2(framePtr + 24L, framePtr + 24L);
                isZero = fZero(framePtr + 24L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putI32(framePtr + 24L, -getI32(framePtr + 24L));
                }
                fimul(outNextRatePtr, framePtr + 24L, outNextRatePtr);
            } else {
                fidiv(framePtr + 40L, funcValPtr, outNextRatePtr);
            }
        } finally {
            sp = savedSp;
        }
    }

    long TVMICompoundFactor(long periodicRatePtr, long pyrOverCyrPtr, long outFactorPtr) {
        long isZero = 0L;
        long status = 0L;
        long dataPtr = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            firadd(periodicRatePtr, CC_1, framePtr + 56L, 0L);
            status = filn(pyrOverCyrPtr, framePtr + 24L);
            if ((status & 0xffffffffL) < 2L) {
                status = 26L;
                if (!(((getI64(framePtr + 24L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 24L) + 1L) & 31L)) & 21L) != 0L)) {
                    return status;
                }
                fimul(outFactorPtr, framePtr + 24L, framePtr + 24L);
                fiexpm(framePtr + 24L, framePtr + 40L, framePtr + 24L);
                status = 3L;
                if (!(((getI64(framePtr + 24L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 24L) + 1L) & 31L)) & 21L) != 0L)) {
                    return status;
                }
                isZero = fEQ(framePtr + 24L, CC_N1) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    status = 2L;
                } else {
                    isZero = fZero(framePtr + 24L) != 0L ? 1L : 0L;
                    if (isZero != 0L || getI32(framePtr + 28L) < -1L) {
                        fimul(framePtr + 56L, framePtr + 24L, framePtr + 24L);
                        dataPtr = framePtr + 24L;
                    } else {
                        fimul(framePtr + 56L, framePtr + 40L, outFactorPtr);
                        periodicRatePtr = CC_N1;
                        dataPtr = outFactorPtr;
                    }
                    firadd(dataPtr, periodicRatePtr, outFactorPtr, 0L);
                    status = 0L;
                }
            } else {
                status = 26L;
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long TVMI_NoPmt(long nPtr, long iPtr, long pvPtr, long fvPtr, long pyrPtr) {
        long intVal = 0L;
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            putI64(framePtr + 72L, getI64(iPtr + 8L));
            putI64(framePtr + 64L, getI64(iPtr));
            putI64(iPtr + 8L, 0L);
            putI64(iPtr, 1L);
            status2 = TVMValidateParams(nPtr, iPtr, pvPtr, CC_0, fvPtr, pyrPtr, pyrPtr, 0L);
            putI64(iPtr + 8L, 0L);
            putI64(iPtr, 0L);
            if ((long) (int) status2 != 0L) {
                return status2;
            }
            putI64(framePtr + 56L, getI64(nPtr + 8L));
            putI64(framePtr + 48L, getI64(nPtr));
            putI64(framePtr + 40L, getI64(pvPtr + 8L));
            putI64(framePtr + 32L, getI64(pvPtr));
            putI64(framePtr + 24L, getI64(fvPtr + 8L));
            putI64(framePtr + 16L, getI64(fvPtr));
            if ((long) (int) getI64(framePtr + 48L) < 0L || (long) (int) comma(isZero = fZero(framePtr + 48L) != 0L ? 1L : 0L, isZero) != 0L) {
                status2 = 29L;
            } else {
                isZero = fZero(framePtr + 32L) != 0L ? 1L : 0L;
                if (isZero != 0L && (long) (int) comma(isZero = fZero(framePtr + 16L) != 0L ? 1L : 0L, isZero) != 0L) {
                    status2 = 27L;
                } else {
                    isZero = fZero(framePtr + 32L) != 0L ? 1L : 0L;
                    if (isZero != 0L || (long) (int) comma(isZero = fZero(framePtr + 16L) != 0L ? 1L : 0L, isZero) != 0L || comma(status1 = fiSignEQ(framePtr + 32L, framePtr + 16L) & 0xffffffffL, status1 & 1L) != 0L) {
                        status2 = 26L;
                    } else {
                        fidiv(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                        isZero = fZero(framePtr + 16L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 16L) & 0xffffffffL));
                        }
                        fidiv(CC_1, framePtr + 48L, framePtr + 48L);
                        fpow(framePtr + 16L, framePtr + 48L, framePtr + 64L, 1L);
                        firadd(framePtr + 64L, CC_N1, framePtr + 64L, 0L);
                        if (((getI64(framePtr + 64L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 64L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 64L) != 0L ? 1L : 0L, isZero == 0L && comma(comma(intVal = getI32(framePtr + 68L), putI64(framePtr + 64L, ((((getI32(framePtr + 68L) + 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 64L) & 0xffffffffL))), 49997L < intVal ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
                            putI64(framePtr + 72L, 0x999999999999999L);
                            putI64(framePtr + 64L, 0xc34f00000002L);
                        }
                        fimul(framePtr + 64L, pyrPtr, iPtr);
                        status1 = fNormUp_L(iPtr) & 0xffffffffL;
                        status2 = status1;
                    }
                }
            }
            return status2;
        } finally {
            sp = savedSp;
        }
    }

    long TVMPV(long nPtr, long iPtr, long pvPtr, long pmtPtr, long fvPtr, long pyrPtr, long cYrPtr, long beginMode) {
        long status1 = 0L;
        long status2 = 0L;
        status2 = TVMPVInternal(nPtr, iPtr, pvPtr, pmtPtr, fvPtr, pyrPtr, cYrPtr, beginMode & 1L);
        if ((status2 & 0xffffffffL) < 2L) {
            status1 = fNormUp_L(pvPtr) & 0xffffffffL;
            return status1;
        } else {
            return status2;
        }
    }

    long TVMPVInternal(long nPtr, long iPtr, long pvPtr, long pmtPtr, long fvPtr, long pyrPtr, long cYrPtr, long beginMode) {
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(256);
        try {
            status = TVMValidateParams(nPtr, iPtr, CC_0, pmtPtr, fvPtr, pyrPtr, cYrPtr, framePtr + 104L);
            if ((long) (int) status == 0L && comma(status = FinAnnuityFactor(framePtr + 104L, nPtr, framePtr + 88L, framePtr + 72L, 1L), (long) (int) status == 0L ? 1L : 0L) != 0L) {
                fimul(fvPtr, framePtr + 72L, framePtr + 56L);
                putI64(framePtr + 40L, getI64(pmtPtr + 8L));
                putI64(framePtr + 32L, getI64(pmtPtr));
                if ((beginMode & 1L) != 0L) {
                    firadd(framePtr + 104L, CC_1, framePtr + 16L, 0L);
                    fimul(framePtr + 32L, framePtr + 16L, framePtr + 32L);
                }
                fimul(framePtr + 32L, framePtr + 88L, framePtr + 32L);
                firadd(framePtr + 32L, framePtr + 56L, pvPtr, 1L);
                status = 0L;
            } else {
                putI64(pvPtr + 8L, 0L);
                putI64(pvPtr, 0L);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long TVMPMT(long nPtr, long iPtr, long pvPtr, long pmtPtr, long fvPtr, long pyrPtr, long cYrPtr, long beginMode) {
        long status1 = 0L;
        long status2 = 0L;
        long status3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(240);
        try {
            status2 = TVMValidateParams(nPtr, iPtr, pvPtr, CC_0, fvPtr, pyrPtr, cYrPtr, framePtr + 88L);
            if (!((long) (int) status2 == 0L && comma(status2 = FinAnnuityFactor(framePtr + 88L, nPtr, framePtr + 72L, framePtr + 56L, 1L), (long) (int) status2 == 0L ? 1L : 0L) != 0L)) {
                putI32(pmtPtr + 8L, 0L);
                putI32(pmtPtr + 12L, 0L);
                putI32(pmtPtr, 0L);
                putI32(pmtPtr + 4L, 0L);
                return status2;
            }
            firadd(framePtr + 88L, CC_1, framePtr + 24L, 0L);
            if ((beginMode & 1L) != 0L) {
                fimul(framePtr + 24L, framePtr + 72L, framePtr + 72L);
            }
            fimul(fvPtr, framePtr + 56L, framePtr + 40L);
            firadd(pvPtr, framePtr + 40L, framePtr + 40L, 0L);
            status3 = fidiv(framePtr + 40L, framePtr + 72L, pmtPtr);
            if ((long) (int) status3 == 4L) {
                status2 = 26L;
                putI32(pmtPtr + 8L, 0L);
                putI32(pmtPtr + 12L, 0L);
                putI32(pmtPtr, 0L);
                putI32(pmtPtr + 4L, 0L);
                return status2;
            }
            if ((long) (int) status3 == 5L) {
                status2 = 27L;
                putI32(pmtPtr + 8L, 0L);
                putI32(pmtPtr + 12L, 0L);
                putI32(pmtPtr, 0L);
                putI32(pmtPtr + 4L, 0L);
                return status2;
            } else {
                if (((getI32(pmtPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI32(pmtPtr) + 1L) & 31L)) & 21L) != 0L) {
                    status1 = fNormUp_L(pmtPtr) & 0xffffffffL;
                    status2 = status1;
                    return status2;
                }
                status2 = 26L;
                putI32(pmtPtr + 8L, 0L);
                putI32(pmtPtr + 12L, 0L);
                putI32(pmtPtr, 0L);
                putI32(pmtPtr + 4L, 0L);
                return status2;
            }
        } finally {
            sp = savedSp;
        }
    }

    long TVMFV(long nPtr, long iPtr, long pvPtr, long pmtPtr, long fvPtr, long pyrPtr, long cYrPtr, long beginMode) {
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(256);
        try {
            status2 = TVMValidateParams(nPtr, iPtr, pvPtr, pmtPtr, CC_0, pyrPtr, cYrPtr, framePtr + 104L);
            if ((long) (int) status2 == 0L && comma(status2 = FinAnnuityFactor(framePtr + 104L, nPtr, framePtr + 88L, framePtr + 72L, 0L), (long) (int) status2 == 0L ? 1L : 0L) != 0L) {
                fimul(pvPtr, framePtr + 72L, framePtr + 56L);
                putI64(framePtr + 40L, getI64(pmtPtr + 8L));
                putI64(framePtr + 32L, getI64(pmtPtr));
                if ((beginMode & 1L) != 0L) {
                    firadd(framePtr + 104L, CC_1, framePtr + 16L, 0L);
                    fimul(framePtr + 32L, framePtr + 16L, framePtr + 32L);
                }
                fimul(framePtr + 32L, framePtr + 88L, framePtr + 32L);
                firadd(framePtr + 32L, framePtr + 56L, fvPtr, 0L);
                isZero = fZero(fvPtr) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putI32(fvPtr, -getI64(fvPtr));
                }
                status1 = fNormUp_L(fvPtr) & 0xffffffffL;
                status2 = status1;
            } else {
                putI64(fvPtr + 8L, 0L);
                putI64(fvPtr, 0L);
            }
            return status2;
        } finally {
            sp = savedSp;
        }
    }

    long TVM_12CUpdateNPV(long nPtr, long iPtr, long pvOrFvPtr, long isCompoundInterest, long isFv) {
        long intVal = 0L;
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long constPtr1 = 0L;
        long status3 = 0L;
        long constPtr2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            constPtr1 = framePtr + 16L;
            constPtr2 = framePtr + 16L;
            fiFP(nPtr, framePtr + 40L);
            fiIP(nPtr, nPtr);
            isZero = fZero(framePtr + 40L) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                status2 = 0L;
            } else {
                putI64(framePtr + 24L, getI64(iPtr + 8L));
                putI32(framePtr + 16L, getI32(iPtr));
                putI32(framePtr + 20L, getI32(iPtr + 4L));
                if (((getI64(framePtr + 16L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 16L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 16L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(intVal = getI32(framePtr + 20L), putI64(framePtr + 16L, ((((getI32(framePtr + 20L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 16L) & 0xffffffffL))), intVal < -49997L ? 1L : 0L) != 0L) {
                    putI64(framePtr + 24L, 0L);
                    putI64(framePtr + 16L, 1L);
                }
                if ((isCompoundInterest & 0xffL) != 0L) {
                    if ((isFv & 0xffL) == 0L) {
                        constPtr1 = CC_1;
                    } else {
                        constPtr2 = CC_1;
                    }
                    firadd(constPtr1, constPtr2, framePtr + 16L, (isFv & 0xffL) == 0L ? 1L : 0L);
                    status2 = fpow(framePtr + 16L, framePtr + 40L, framePtr + 40L, 1L);
                    if (1L < (status2 & 0xffffffffL)) {
                        return status2;
                    }
                } else {
                    fimul(framePtr + 16L, framePtr + 40L, framePtr + 40L);
                    firadd(framePtr + 40L, CC_1, framePtr + 40L, 0L);
                }
                if ((isFv & 0xffL) != 0L) {
                    status2 = fimul(pvOrFvPtr, framePtr + 40L, pvOrFvPtr);
                } else {
                    status3 = fidiv(pvOrFvPtr, framePtr + 40L, pvOrFvPtr);
                    status1 = fNorm_L((long) (int) status3, pvOrFvPtr) & 0xffffffffL;
                    status2 = status1;
                }
            }
            return status2;
        } finally {
            sp = savedSp;
        }
    }

    long TVMN_12C(long nPtr, long iPtr, long pvPtr, long pmtPtr, long fvPtr, long pyrPtr, long cYrPtr, long beginMode) {
        long isLess = 0L;
        long status = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            status = TVMN(nPtr, iPtr, pvPtr, pmtPtr, fvPtr, pyrPtr, cYrPtr, beginMode & 1L);
            result = status & 0xffffffffL;
            if ((status & 0xffffffffL) >= 2L) {
                return result;
            }
            if ((long) (int) getI64(nPtr) < 0L) {
                result = 29L;
            } else {
                fiFP(nPtr, framePtr + 24L);
                fiIP(nPtr, nPtr);
                isLess = fLT(ADDR_RODATA + 2032L, framePtr + 24L) != 0L ? 1L : 0L;
                if (isLess != 0L) {
                    status = firadd(nPtr, CC_1, nPtr, 0L);
                    fNorm_L((long) (int) status, nPtr);
                }
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long TVMI_12C(long nPtr, long iPtr, long pvPtr, long pmtPtr, long fvPtr, long pyrPtr, long cYrPtr, long beginMode, long guessPtr, long hasGuess) {
        long status = 0L;
        long intVal = 0L;
        intVal = 1L;
        if ((guessPtr & 1L) == 0L) {
            intVal = 2L;
        }
        status = TVMI(nPtr, iPtr, pvPtr, pmtPtr, fvPtr, pyrPtr, cYrPtr, beginMode & 1L, hasGuess, intVal);
        return status;
    }

    long TVMPV_12C(long nPtr, long iPtr, long pvPtr, long pmtPtr, long fvPtr, long pyrPtr, long cYrPtr, long beginMode, long isCompoundInterest) {
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            fiIP(nPtr, framePtr + 16L);
            status2 = TVMPVInternal(framePtr + 16L, iPtr, pvPtr, pmtPtr, fvPtr, pyrPtr, cYrPtr, beginMode & 1L);
            if ((status2 & 0xffffffffL) >= 2L) {
                return status2;
            }
            status1 = fNormUp_L(pvPtr) & 0xffffffffL;
            status2 = status1;
            if (status1 < 2L) {
                putI64(framePtr + 24L, getI64(nPtr + 8L));
                putI64(framePtr + 16L, getI64(nPtr));
                status2 = TVM_12CUpdateNPV(framePtr + 16L, iPtr, pvPtr, isCompoundInterest & 1L, 0L);
            }
            return status2;
        } finally {
            sp = savedSp;
        }
    }

    long TVMPMT_12C(long nPtr, long iPtr, long pvPtr, long pmtPtr, long fvPtr, long pyrPtr, long cYrPtr, long beginMode, long isCompoundInterest) {
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            putI64(framePtr + 40L, getI64(nPtr + 8L));
            putI64(framePtr + 32L, getI64(nPtr));
            putI64(framePtr + 24L, getI64(pvPtr + 8L));
            putI64(framePtr + 16L, getI64(pvPtr));
            status = TVM_12CUpdateNPV(framePtr + 32L, iPtr, framePtr + 16L, isCompoundInterest & 1L, 1L);
            if ((status & 0xffffffffL) < 2L) {
                status = TVMPMT(framePtr + 32L, iPtr, framePtr + 16L, pmtPtr, fvPtr, pyrPtr, cYrPtr, beginMode & 1L);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long TVMFV_12C(long nPtr, long iPtr, long pvPtr, long pmtPtr, long fvPtr, long pyrPtr, long cYrPtr, long beginMode, long isCompoundInterest) {
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            putI64(framePtr + 40L, getI64(nPtr + 8L));
            putI64(framePtr + 32L, getI64(nPtr));
            putI64(framePtr + 24L, getI64(pvPtr + 8L));
            putI64(framePtr + 16L, getI64(pvPtr));
            status = TVM_12CUpdateNPV(framePtr + 32L, iPtr, framePtr + 16L, isCompoundInterest & 1L, 1L);
            if ((status & 0xffffffffL) < 2L) {
                status = TVMFV(framePtr + 32L, iPtr, framePtr + 16L, pmtPtr, fvPtr, pyrPtr, cYrPtr, beginMode & 1L);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long TVMNom_ppyr(long effRatePtr, long pyrPtr, long outNomRatePtr) {
        long isZero = 0L;
        long status = 0L;
        long tempVal = 0L;
        isZero = fZero(pyrPtr) != 0L ? 1L : 0L;
        if (isZero != 0L) {
            status = TVMNom(effRatePtr, outNomRatePtr) & 0xffffffffL;
            return status;
        }
        if (!(-1L < (long) (int) getU32(pyrPtr) && (long) (int) comma(isZero = fiInteger(pyrPtr) != 0L ? 1L : 0L, isZero) != 0L)) {
            return 23L;
        }
        tempVal = getI64(effRatePtr);
        putI64(outNomRatePtr + 8L, getI64(effRatePtr + 8L));
        putI64(outNomRatePtr, tempVal);
        if (((getU32(outNomRatePtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(outNomRatePtr) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(outNomRatePtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(status = getU32(outNomRatePtr + 4L), putU32(outNomRatePtr + 4L, status - 2L)), (long) (int) status < -49997L ? 1L : 0L) != 0L) {
            putU32(outNomRatePtr + 8L, 0L);
            putU32(outNomRatePtr + 12L, 0L);
            putU32(outNomRatePtr, 1L);
            putU32(outNomRatePtr + 4L, 0L);
        }
        fradd(outNomRatePtr, CC_1, outNomRatePtr, 0L);
        finthroot(outNomRatePtr, pyrPtr, outNomRatePtr);
        firadd(outNomRatePtr, CC_N1, outNomRatePtr, 0L);
        fimul(outNomRatePtr, pyrPtr, outNomRatePtr);
        if (((getU32(outNomRatePtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(outNomRatePtr) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(outNomRatePtr) != 0L ? 1L : 0L, isZero == 0L && comma(comma(status = getU32(outNomRatePtr + 4L), putU32(outNomRatePtr + 4L, status + 2L)), 49997L < (long) (int) status ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
            putU32(outNomRatePtr + 8L, 0x99999999L);
            putU32(outNomRatePtr + 12L, 0x9999999L);
            putU32(outNomRatePtr, 2L);
            putU32(outNomRatePtr + 4L, 49999L);
        }
        status = fNormUp_L(outNomRatePtr) & 0xffffffffL;
        return status;
    }

    long TVMNom(long effRatePtr, long outNomRatePtr) {
        long exponent = 0L;
        long isZero = 0L;
        long status = 0L;
        long tempVal = 0L;
        tempVal = getI64(effRatePtr);
        putI64(outNomRatePtr + 8L, getI64(effRatePtr + 8L));
        putI64(outNomRatePtr, tempVal);
        if (((getU32(outNomRatePtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(outNomRatePtr) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(outNomRatePtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(exponent = getU32(outNomRatePtr + 4L), putU32(outNomRatePtr + 4L, exponent - 2L)), (long) (int) exponent < -49997L ? 1L : 0L) != 0L) {
            putU32(outNomRatePtr + 8L, 0L);
            putU32(outNomRatePtr + 12L, 0L);
            putU32(outNomRatePtr, 1L);
            putU32(outNomRatePtr + 4L, 0L);
        }
        firadd(outNomRatePtr, CC_1, outNomRatePtr, 0L);
        filn(outNomRatePtr, outNomRatePtr);
        if (((getU32(outNomRatePtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(outNomRatePtr) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(outNomRatePtr) != 0L ? 1L : 0L, isZero == 0L && comma(comma(exponent = getU32(outNomRatePtr + 4L), putU32(outNomRatePtr + 4L, exponent + 2L)), 49997L < (long) (int) exponent ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
            putU32(outNomRatePtr + 8L, 0x99999999L);
            putU32(outNomRatePtr + 12L, 0x9999999L);
            putU32(outNomRatePtr, 2L);
            putU32(outNomRatePtr + 4L, 49999L);
        }
        status = (long) (int) fNorm_L(0L, outNomRatePtr);
        return status;
    }

    long TVMEff_ppyr(long nomRatePtr, long pyrPtr, long outEffRatePtr) {
        long isZero = 0L;
        long status = 0L;
        long tempVal = 0L;
        isZero = fZero(pyrPtr) != 0L ? 1L : 0L;
        if (isZero != 0L) {
            status = TVMEff(nomRatePtr, outEffRatePtr) & 0xffffffffL;
            return status;
        }
        if (!(-1L < (long) (int) getU32(pyrPtr) && (long) (int) comma(isZero = fiInteger(pyrPtr) != 0L ? 1L : 0L, isZero) != 0L)) {
            return 23L;
        }
        tempVal = getI64(nomRatePtr);
        putI64(outEffRatePtr + 8L, getI64(nomRatePtr + 8L));
        putI64(outEffRatePtr, tempVal);
        fidiv(outEffRatePtr, pyrPtr, outEffRatePtr);
        if (((getU32(outEffRatePtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(outEffRatePtr) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(outEffRatePtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(status = getU32(outEffRatePtr + 4L), putU32(outEffRatePtr + 4L, status - 2L)), (long) (int) status < -49997L ? 1L : 0L) != 0L) {
            putU32(outEffRatePtr + 8L, 0L);
            putU32(outEffRatePtr + 12L, 0L);
            putU32(outEffRatePtr, 1L);
            putU32(outEffRatePtr + 4L, 0L);
        }
        firadd(outEffRatePtr, CC_1, outEffRatePtr, 0L);
        fpow(outEffRatePtr, pyrPtr, outEffRatePtr, 1L);
        firadd(outEffRatePtr, CC_N1, outEffRatePtr, 0L);
        if (((getU32(outEffRatePtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(outEffRatePtr) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(outEffRatePtr) != 0L ? 1L : 0L, isZero == 0L && comma(comma(status = getU32(outEffRatePtr + 4L), putU32(outEffRatePtr + 4L, status + 2L)), 49997L < (long) (int) status ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
            putU32(outEffRatePtr + 8L, 0x99999999L);
            putU32(outEffRatePtr + 12L, 0x9999999L);
            putU32(outEffRatePtr, 2L);
            putU32(outEffRatePtr + 4L, 49999L);
        }
        status = fNormUp_L(outEffRatePtr) & 0xffffffffL;
        return status;
    }

    long TVMEff(long nomRatePtr, long outEffRatePtr) {
        long exponent = 0L;
        long isZero = 0L;
        long status = 0L;
        long tempVal = 0L;
        tempVal = getI64(nomRatePtr);
        putI64(outEffRatePtr + 8L, getI64(nomRatePtr + 8L));
        putI64(outEffRatePtr, tempVal);
        if (((getU32(outEffRatePtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(outEffRatePtr) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(outEffRatePtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(exponent = getU32(outEffRatePtr + 4L), putU32(outEffRatePtr + 4L, exponent - 2L)), (long) (int) exponent < -49997L ? 1L : 0L) != 0L) {
            putU32(outEffRatePtr + 8L, 0L);
            putU32(outEffRatePtr + 12L, 0L);
            putU32(outEffRatePtr, 1L);
            putU32(outEffRatePtr + 4L, 0L);
        }
        fiexpm(outEffRatePtr, outEffRatePtr, 0L);
        firadd(outEffRatePtr, CC_1, outEffRatePtr, 1L);
        if (((getU32(outEffRatePtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(outEffRatePtr) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(outEffRatePtr) != 0L ? 1L : 0L, isZero == 0L && comma(comma(exponent = getU32(outEffRatePtr + 4L), putU32(outEffRatePtr + 4L, exponent + 2L)), 49997L < (long) (int) exponent ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
            putU32(outEffRatePtr + 8L, 0x99999999L);
            putU32(outEffRatePtr + 12L, 0x9999999L);
            putU32(outEffRatePtr, 2L);
            putU32(outEffRatePtr + 4L, 49999L);
        }
        status = (long) (int) fNorm_L(0L, outEffRatePtr);
        return status;
    }

    long TVMIConvPPYR(long inRatePtr, long oldPyrPtr, long newPyrPtr, long outRatePtr) {
        long isZero = 0L;
        long status = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            putI64(framePtr + 56L, getI64(inRatePtr + 8L));
            putI64(framePtr + 48L, getI64(inRatePtr));
            putI64(framePtr + 72L, getI64(oldPyrPtr + 8L));
            putI64(framePtr + 64L, getI64(oldPyrPtr));
            if (((getI64(framePtr + 48L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 48L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 48L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(status = getI32(framePtr + 52L), putI64(framePtr + 48L, ((((getI32(framePtr + 52L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 48L) & 0xffffffffL))), status < -49997L ? 1L : 0L) != 0L) {
                putI64(framePtr + 56L, 0L);
                putI64(framePtr + 48L, 1L);
            }
            if (((getI64(framePtr + 64L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 64L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 64L) != 0L ? 1L : 0L, isZero == 0L && comma(comma(status = getI32(framePtr + 68L), putI64(framePtr + 64L, ((((getI32(framePtr + 68L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 64L) & 0xffffffffL))), status < -49997L ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
                putI64(framePtr + 72L, 0L);
                putI64(framePtr + 64L, 1L);
            }
            isZero = fLE(framePtr + 48L, CC_N1) != 0L ? 1L : 0L;
            if (!(isZero == 0L && comma(isZero = fLE(framePtr + 64L, CC_N1) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && (getI64(framePtr + 48L) & 0xffffffffL) == (getI64(framePtr + 64L) & 0xffffffffL))) {
                result = 26L;
                return result;
            }
            fexpm(framePtr + 48L, framePtr + 16L, newPyrPtr);
            if (0L < (long) (int) getI64(framePtr + 48L)) {
                isZero = fLT(framePtr + 64L, framePtr + 48L) != 0L ? 1L : 0L;
                if (isZero != 0L || (long) (int) comma(isZero = fLT(framePtr + 16L, framePtr + 64L) != 0L ? 1L : 0L, isZero) != 0L || !(-1L < (long) (int) getI64(framePtr + 48L) || comma(isZero = fLT(framePtr + 64L, framePtr + 16L) != 0L ? 1L : 0L, isZero == 0L && comma(isZero = fLT(framePtr + 48L, framePtr + 64L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L)) {
                    result = 26L;
                    return result;
                }
            } else {
                if (!(-1L < (long) (int) getI64(framePtr + 48L) || comma(isZero = fLT(framePtr + 64L, framePtr + 16L) != 0L ? 1L : 0L, isZero == 0L && comma(isZero = fLT(framePtr + 48L, framePtr + 64L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L)) {
                    result = 26L;
                    return result;
                }
            }
            filnp1(framePtr + 64L, framePtr + 64L);
            status = (long) (int) ZeroFinder(CC_1, CC_12, FPTR_TVMIConvRateFunc, outRatePtr, framePtr + 48L, newPyrPtr, framePtr + 32L);
            if (status == 0L) {
                fNorm_L(0L, newPyrPtr);
                result = 0L;
                return result;
            }
            if (status != 44L) {
                result = 26L;
                return result;
            } else {
                firadd(newPyrPtr, framePtr + 32L, newPyrPtr, 0L);
                fidiv2(newPyrPtr, newPyrPtr);
                fNorm_L(0L, newPyrPtr);
                result = 0L;
                return result;
            }
        } finally {
            sp = savedSp;
        }
    }

    long TVMIConvRateFunc(long ratePtr, long contextPtr, long outValPtr) {
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            fidiv(contextPtr, ratePtr, outValPtr);
            filnp1(outValPtr, outValPtr);
            fimul(outValPtr, ratePtr, outValPtr);
            putI64(framePtr + 32L, getI64(contextPtr + 24L));
            putI64(framePtr + 24L, getI64(contextPtr + 16L));
            if ((long) (int) getI64(framePtr + 24L) == 3L) {
                putI32(framePtr + 28L, getI64(framePtr + 24L) >>> 32);
                putI64(framePtr + 24L, (((getI32(framePtr + 28L) & 0xffffffffL)) << 32) | 1L);
            }
            isZero = fZero(framePtr + 24L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI64(framePtr + 24L, (((getI32(framePtr + 28L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 24L) & 0xffffffffL));
            }
            status2 = firadd(outValPtr, framePtr + 24L, outValPtr, 0L);
            status1 = (long) (int) fNorm_L((long) (int) status2, outValPtr);
            if (getU32(outValPtr) == 0L) {
                putU32(outValPtr + 8L, 0x99999999L);
                putU32(outValPtr + 12L, 0x9999999L);
                putU32(outValPtr, 2L);
                putU32(outValPtr + 4L, 49999L);
            }
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    long IRRTOTAL(long cfloDataPtr, long entryCount, long getEntryFn, long userStopFn, long outTotalPtr) {
        long status1 = 0L;
        long dataPtr = 0L;
        long status2 = 0L;
        long index = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        if (userStopFn != 0L) {
                            putU32(userStopFn + 8L, 0L);
                            putU32(userStopFn + 12L, 0L);
                            putU32(userStopFn, 1L);
                            putU32(userStopFn + 4L, 0L);
                        }
                        if (outTotalPtr != 0L) {
                            putU32(outTotalPtr + 8L, 0L);
                            putU32(outTotalPtr + 12L, 0L);
                            putU32(outTotalPtr, 1L);
                            putU32(outTotalPtr + 4L, 0L);
                        }
                        status1 = 0L;
                        if ((long) (int) entryCount != 0L) {
                            index = 0L;
                            step = 2;
                            continue stepLoop;
                        } else {
                            step = 1;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        if (outTotalPtr != 0L) {
                            status1 = fNorm_L((long) (int) status1, outTotalPtr) & 0xffffffffL;
                        }
                        if (userStopFn != 0L) {
                            status1 = fNorm_L((long) (int) status1, userStopFn) & 0xffffffffL;
                        }
                        return status1;
                    }
                    case 2: {
                        dataPtr = callIndirect3(getEntryFn, cfloDataPtr, (long) (int) entryCount, index);
                        if (status1 < 2L && userStopFn != 0L) {
                            status2 = fimul(dataPtr, dataPtr + 16L, framePtr + 24L);
                            status1 = status2 & 0xffffffffL;
                            if (status1 < 2L) {
                                status2 = firadd(userStopFn, framePtr + 24L, userStopFn, 0L);
                                status1 = status2 & 0xffffffffL;
                                step = 4;
                                continue stepLoop;
                            } else {
                                step = 3;
                                continue stepLoop;
                            }
                        } else {
                            step = 4;
                            continue stepLoop;
                        }
                    }
                    case 3: {
                        index = (long) (int) (index + 1L);
                        if ((long) (int) entryCount != index) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            step = 1;
                            continue stepLoop;
                        }
                    }
                    case 4: {
                        if (status1 < 2L && outTotalPtr != 0L) {
                            status2 = firadd(dataPtr + 16L, outTotalPtr, outTotalPtr, 0L);
                            status1 = status2 & 0xffffffffL;
                        }
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

    long IRRNPVNUSNFV(long cfloDataPtr, long entryCount, long getEntryFn, long ratePtr, long pyrPtr, long outNpvPtr, long outNusPtr, long outNfvPtr) {
        long intVal = 0L;
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long status3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(256);
        try {
            if (outNpvPtr != 0L) {
                putI32(outNpvPtr + 8L, 0L);
                putI32(outNpvPtr + 12L, 0L);
                putI32(outNpvPtr, 0L);
                putI32(outNpvPtr + 4L, 0L);
            }
            if (outNusPtr != 0L) {
                putI32(outNusPtr + 8L, 0L);
                putI32(outNusPtr + 12L, 0L);
                putI32(outNusPtr, 0L);
                putI32(outNusPtr + 4L, 0L);
            }
            if (outNfvPtr != 0L) {
                putI32(outNfvPtr + 8L, 0L);
                putI32(outNfvPtr + 12L, 0L);
                putI32(outNfvPtr, 0L);
                putI32(outNfvPtr + 4L, 0L);
            }
            fidiv(ratePtr, pyrPtr, framePtr + 64L);
            if (((getI64(framePtr + 64L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 64L) + 1L) & 31L)) & 21L) != 0L) {
                isZero = fZero(framePtr + 64L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    intVal = getI32(framePtr + 68L);
                    putI64(framePtr + 64L, ((((getI32(framePtr + 68L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 64L) & 0xffffffffL));
                    if (intVal < -49997L) {
                        putI64(framePtr + 72L, 0L);
                        putI64(framePtr + 64L, 1L);
                    }
                }
            }
            if ((long) (int) getI64(framePtr + 64L) < 0L && -1L < getI64(framePtr + 64L)) {
                status1 = 25L;
            } else {
                status2 = IRRNPVInternal(cfloDataPtr, (long) (int) entryCount, getEntryFn, framePtr + 64L, framePtr + 88L, 0L, 0L, 0L, framePtr + 48L);
                status1 = status2 & 0xffffffffL;
                if (status1 >= 2L) {
                    return status1;
                }
                if (outNpvPtr != 0L) {
                    putI64(outNpvPtr + 8L, getI64(framePtr + 96L));
                    putI64(outNpvPtr, getI64(framePtr + 88L));
                    fNorm_L(0L, outNpvPtr);
                }
                if (!(outNusPtr != 0L || outNfvPtr != 0L)) {
                    return status1;
                }
                if (outNusPtr != 0L) {
                    status2 = FinAnnuityFactor(framePtr + 64L, framePtr + 48L, framePtr + 16L, framePtr + 32L, 1L);
                    status1 = status2 & 0xffffffffL;
                    if (status1 != 0L) {
                        return status1;
                    }
                    isZero = fZero(framePtr + 16L) != 0L ? 1L : 0L;
                    if (isZero == 0L) {
                        putU32(framePtr + 16L, -getU32(framePtr + 16L));
                    }
                    status2 = fidiv(framePtr + 88L, framePtr + 16L, outNusPtr);
                    status1 = fNorm_L((long) (int) status2, outNusPtr) & 0xffffffffL;
                }
                if (outNfvPtr != 0L) {
                    firadd(framePtr + 64L, CC_1, framePtr + 64L, 0L);
                    status2 = fpow(framePtr + 64L, framePtr + 48L, framePtr + 64L, 1L);
                    status1 = status2 & 0xffffffffL;
                    status3 = fimul(framePtr + 88L, framePtr + 64L, outNfvPtr);
                    fNorm_L((long) (int) status3, outNfvPtr);
                }
            }
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    long IRRNPVInternal(long cfloDataPtr, long entryCount, long getEntryFn, long periodicRatePtr, long outNpvPtr, long outDerivPtr, long outTotalPosPtr, long outTotalNegPtr, long stopOnSignChange) {
        long dataPtr1 = 0L;
        long index = 0L;
        long isZero1 = 0L;
        long isZero2 = 0L;
        long result = 0L;
        long dataPtr2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(304);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        putI64(framePtr + 56L, getI64(periodicRatePtr + 8L));
                        putI64(framePtr + 48L, getI64(periodicRatePtr));
                        putU32(outNpvPtr + 8L, 0L);
                        putU32(outNpvPtr + 12L, 0L);
                        putU32(outNpvPtr, 1L);
                        putU32(outNpvPtr + 4L, 0L);
                        if (outDerivPtr != 0L) {
                            putU32(outDerivPtr + 8L, 0L);
                            putU32(outDerivPtr + 12L, 0L);
                            putU32(outDerivPtr, 1L);
                            putU32(outDerivPtr + 4L, 0L);
                        }
                        if (stopOnSignChange != 0L) {
                            putU32(stopOnSignChange + 8L, 0L);
                            putU32(stopOnSignChange + 12L, 0x1000000L);
                            putU32(stopOnSignChange, 0xffffffffL);
                            putU32(stopOnSignChange + 4L, 0L);
                        }
                        firadd(framePtr + 48L, CC_1, framePtr + 120L, 0L);
                        fidiv(CC_1, framePtr + 120L, framePtr + 104L);
                        isZero2 = 0L;
                        index = (long) (int) entryCount;
                        step = 1;
                        continue stepLoop;
                    }
                    case 1: {
                        do {
                            index = (long) (int) (index - 1L);
                            if (index < 0L) {
                                if (outTotalPosPtr != 0L) {
                                    result = getI64(outNpvPtr);
                                    putI64(outTotalPosPtr + 8L, getI64(outNpvPtr + 8L));
                                    putI64(outTotalPosPtr, result);
                                }
                                if (outTotalNegPtr != 0L) {
                                    putI64(outTotalNegPtr + 8L, getI64(framePtr + 128L));
                                    putI64(outTotalNegPtr, getI64(framePtr + 120L));
                                }
                                fimul(outNpvPtr, framePtr + 120L, outNpvPtr);
                                if (isZero2 != 0L) {
                                    result = 0L;
                                } else {
                                    putU32(outNpvPtr + 8L, 0L);
                                    putU32(outNpvPtr + 12L, 0L);
                                    putU32(outNpvPtr, 0L);
                                    putU32(outNpvPtr + 4L, 0L);
                                    if (outDerivPtr != 0L) {
                                        putU32(outDerivPtr + 8L, 0L);
                                        putU32(outDerivPtr + 12L, 0L);
                                        putU32(outDerivPtr, 0L);
                                        putU32(outDerivPtr + 4L, 0L);
                                    }
                                    if (outTotalNegPtr != 0L) {
                                        putI64(outTotalNegPtr + 8L, 0L);
                                        putI64(outTotalNegPtr, 0L);
                                    }
                                    result = 32L;
                                }
                                return result;
                            }
                            dataPtr2 = callIndirect3(getEntryFn, cfloDataPtr, (long) (int) entryCount, index);
                            dataPtr1 = dataPtr2 + 16L;
                            isZero1 = fZero(dataPtr1) != 0L ? 1L : 0L;
                        } while (isZero1 != 0L);
                        if (stopOnSignChange != 0L) {
                            firadd(stopOnSignChange, dataPtr1, stopOnSignChange, 0L);
                        }
                        isZero2 = fEQ(dataPtr1, CC_1) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            if (outDerivPtr != 0L) {
                                firadd(outDerivPtr, outNpvPtr, outDerivPtr, 1L);
                                fidiv(outDerivPtr, framePtr + 120L, outDerivPtr);
                            }
                            firadd(dataPtr2, outNpvPtr, outNpvPtr, 0L);
                            fidiv(outNpvPtr, framePtr + 120L, outNpvPtr);
                        } else {
                            fpow(framePtr + 104L, dataPtr1, framePtr + 72L, 1L);
                            fimul(outNpvPtr, framePtr + 72L, outNpvPtr);
                            result = FinAnnuityFactor(framePtr + 48L, dataPtr1, framePtr + 32L, framePtr + 88L, 1L);
                            if ((long) (int) result != 0L) {
                                if (outNpvPtr != 0L) {
                                    putU32(outNpvPtr + 8L, 0L);
                                    putU32(outNpvPtr + 12L, 0L);
                                    putU32(outNpvPtr, 0L);
                                    putU32(outNpvPtr + 4L, 0L);
                                }
                                if (outDerivPtr != 0L) {
                                    putU32(outDerivPtr + 8L, 0L);
                                    putU32(outDerivPtr + 12L, 0L);
                                    putU32(outDerivPtr, 0L);
                                    putU32(outDerivPtr + 4L, 0L);
                                }
                                return result;
                            }
                            fimul(dataPtr2, framePtr + 32L, framePtr + 88L);
                            firadd(outNpvPtr, framePtr + 88L, outNpvPtr, 1L);
                            if (outDerivPtr != 0L) {
                                fimul(outDerivPtr, framePtr + 72L, outDerivPtr);
                                fimul(outNpvPtr, dataPtr1, framePtr + 88L);
                                firadd(outDerivPtr, framePtr + 88L, outDerivPtr, 1L);
                                isZero2 = fZero(framePtr + 32L) != 0L ? 1L : 0L;
                                if (isZero2 == 0L) {
                                    putU32(framePtr + 32L, -getU32(framePtr + 32L));
                                }
                                TVMNewtonStep(dataPtr1, framePtr + 48L, framePtr + 32L, framePtr + 16L);
                                fimul(dataPtr2, framePtr + 16L, framePtr + 16L);
                                firadd(outDerivPtr, framePtr + 16L, outDerivPtr, 0L);
                            }
                        }
                        isZero2 = 1L;
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

    long IRRDPB(long cfloDataPtr, long entryCount, long getEntryFn, long ratePtr, long pyrPtr, long outDpbPtr) {
        long intVal = 0L;
        long isZero = 0L;
        long tempVal1 = 0L;
        long status1 = 0L;
        long dataPtr1 = 0L;
        long status2 = 0L;
        long dataPtr2 = 0L;
        long constPtr = 0L;
        long tempVal2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(384);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        putI64(framePtr + 168L, 0L);
                        putI64(framePtr + 160L, 1L);
                        fidiv(ratePtr, pyrPtr, framePtr + 128L);
                        if (((getI64(framePtr + 128L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 128L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 128L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
                            intVal = getI32(framePtr + 132L);
                            putI64(framePtr + 128L, ((((getI32(framePtr + 132L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 128L) & 0xffffffffL));
                            if (intVal < -49997L) {
                                putI64(framePtr + 136L, 0L);
                                putI64(framePtr + 128L, 1L);
                            }
                        }
                        firadd(framePtr + 128L, CC_1, framePtr + 144L, 0L);
                        putI64(framePtr + 120L, 0L);
                        putI64(framePtr + 112L, 1L);
                        status1 = 0L;
                        tempVal2 = 0L;
                        putI64(outDpbPtr + 8L, 0L);
                        putI64(outDpbPtr, 0L);
                        step = 1;
                        continue stepLoop;
                    }
                    case 1: {
                        tempVal1 = status1;
                        if (status1 <= (entryCount & 0xffffffffL)) {
                            tempVal1 = entryCount & 0xffffffffL;
                        }
                        do {
                            if (tempVal1 == status1) {
                                isZero = fZero(framePtr + 112L) != 0L ? 1L : 0L;
                                status1 = 32L;
                                if (isZero == 0L) {
                                    status1 = 33L;
                                }
                                status2 = status1;
                                return status2;
                            }
                            dataPtr1 = callIndirect3(getEntryFn, cfloDataPtr, entryCount & 0xffffffffL, status1);
                            dataPtr2 = dataPtr1 + 16L;
                            status1 = (status1 + 1L) & 0xffffffffL;
                            isZero = fZero(dataPtr2) != 0L ? 1L : 0L;
                        } while (isZero != 0L);
                        if (tempVal2 == 0L) {
                            tempVal2 = getI64(dataPtr1) & 0xffffffffL;
                        }
                        isZero = fEQ(CC_1, dataPtr2) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            fimul(framePtr + 160L, framePtr + 144L, framePtr + 160L);
                            firadd(framePtr + 160L, dataPtr1, framePtr + 160L, 0L);
                            firadd(framePtr + 112L, CC_1, framePtr + 112L, 0L);
                        } else {
                            isZero = fZero(dataPtr2) != 0L ? 1L : 0L;
                            if (isZero != 0L) {
                                putI64(framePtr + 104L, getI64(framePtr + 168L));
                                putI64(framePtr + 96L, getI64(framePtr + 160L));
                            } else {
                                status2 = FinAnnuityFactor(framePtr + 128L, dataPtr2, framePtr + 192L, framePtr + 176L, 0L);
                                tempVal1 = status2 & 0xffffffffL;
                                if (tempVal1 == 0L) {
                                    fimul(framePtr + 160L, framePtr + 176L, framePtr + 176L);
                                    fimul(dataPtr1, framePtr + 192L, framePtr + 192L);
                                    status2 = firadd(framePtr + 176L, framePtr + 192L, framePtr + 96L, 0L);
                                    tempVal1 = status2 & 0xffffffffL;
                                } else {
                                    putI64(framePtr + 104L, 0L);
                                    putI64(framePtr + 96L, 0L);
                                }
                                if (1L < tempVal1) {
                                    return status2;
                                }
                            }
                            if ((long) (int) (getI64(framePtr + 96L) ^ getI64(framePtr + 160L)) < 0L && comma(isZero = fZero(framePtr + 96L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
                                putI64(framePtr + 56L, 0L);
                                putI64(framePtr + 48L, 1L);
                                putI64(framePtr + 72L, getI64(framePtr + 104L));
                                putI64(framePtr + 64L, getI64(framePtr + 96L));
                                putI64(framePtr + 40L, getI64(dataPtr1 + 24L));
                                putI64(framePtr + 32L, getI64(dataPtr2));
                                status1 = getI64(framePtr + 160L) & 0xffffffffL;
                                while (true) {
                                    fradd(framePtr + 48L, framePtr + 32L, framePtr + 16L, 0L);
                                    fidiv2(framePtr + 16L, framePtr + 16L);
                                    firnd(framePtr + 16L, 0L, framePtr + 16L, 0L);
                                    isZero = fEQ(framePtr + 48L, framePtr + 16L) != 0L ? 1L : 0L;
                                    if (isZero != 0L || (long) (int) comma(isZero = fEQ(framePtr + 16L, framePtr + 32L) != 0L ? 1L : 0L, isZero) != 0L) {
                                        firadd(framePtr + 112L, CC_N2, framePtr + 112L, 0L);
                                        firadd(framePtr + 112L, framePtr + 32L, framePtr + 112L, 0L);
                                        firadd(framePtr + 32L, framePtr + 48L, framePtr + 32L, 1L);
                                        fimul(dataPtr1, framePtr + 32L, framePtr + 32L);
                                        isZero = fZero(framePtr + 32L) != 0L ? 1L : 0L;
                                        if (isZero == 0L) {
                                            firadd(framePtr + 32L, framePtr + 64L, outDpbPtr, 1L);
                                            dataPtr1 = framePtr + 32L;
                                            fidiv(outDpbPtr, dataPtr1, outDpbPtr);
                                        }
                                        constPtr = framePtr + 112L;
                                        dataPtr2 = outDpbPtr;
                                        status2 = firadd(dataPtr2, constPtr, outDpbPtr, 0L);
                                        status1 = fNorm_L((long) (int) status2, outDpbPtr) & 0xffffffffL;
                                        status2 = status1;
                                        return status2;
                                    }
                                    isZero = fZero(framePtr + 16L) != 0L ? 1L : 0L;
                                    if (isZero != 0L) {
                                        putI64(framePtr + 88L, getI64(framePtr + 168L));
                                        putI64(framePtr + 80L, getI64(framePtr + 160L));
                                    } else {
                                        status2 = FinAnnuityFactor(framePtr + 128L, framePtr + 16L, framePtr + 192L, framePtr + 176L, 0L);
                                        if ((long) (int) status2 == 0L) {
                                            fimul(framePtr + 160L, framePtr + 176L, framePtr + 176L);
                                            fimul(dataPtr1, framePtr + 192L, framePtr + 192L);
                                            status2 = firadd(framePtr + 176L, framePtr + 192L, framePtr + 80L, 0L);
                                        } else {
                                            putI64(framePtr + 88L, 0L);
                                            putI64(framePtr + 80L, 0L);
                                        }
                                        if (1L < (status2 & 0xffffffffL)) {
                                            return status2;
                                        }
                                    }
                                    isZero = fMLE(framePtr + 80L, CC_0p00001) != 0L ? 1L : 0L;
                                    if (isZero != 0L) {
                                        break;
                                    }
                                    if ((long) (int) (getI64(framePtr + 80L) ^ status1) < 0L) {
                                        dataPtr2 = framePtr + 32L;
                                        putI64(framePtr + 72L, getI64(framePtr + 88L));
                                        putI64(framePtr + 64L, getI64(framePtr + 80L));
                                    } else {
                                        dataPtr2 = framePtr + 48L;
                                        status1 = getI64(framePtr + 80L) & 0xffffffffL;
                                    }
                                    putI64(dataPtr2 + 8L, getI64(framePtr + 24L));
                                    putI64(dataPtr2, getI64(framePtr + 16L));
                                }
                                firadd(framePtr + 112L, CC_N1, framePtr + 112L, 0L);
                                dataPtr2 = framePtr + 112L;
                                constPtr = framePtr + 16L;
                                status2 = firadd(dataPtr2, constPtr, outDpbPtr, 0L);
                                status1 = fNorm_L((long) (int) status2, outDpbPtr) & 0xffffffffL;
                                status2 = status1;
                                return status2;
                            }
                            firadd(framePtr + 112L, dataPtr2, framePtr + 112L, 0L);
                            if (499L < getI32(framePtr + 116L)) {
                                status2 = 24L;
                                return status2;
                            }
                            putI64(framePtr + 168L, getI64(framePtr + 104L));
                            putI64(framePtr + 160L, getI64(framePtr + 96L));
                        }
                        isZero = fZero(framePtr + 160L) != 0L ? 1L : 0L;
                        if (!(isZero == 0L && -1L < (long) (int) (getI64(framePtr + 160L) ^ tempVal2))) {
                            isZero = fEQ(framePtr + 112L, CC_1) != 0L ? 1L : 0L;
                            if (isZero != 0L) {
                                status2 = 0L;
                                putI64(outDpbPtr + 8L, 0x100000000000000L);
                                putI64(outDpbPtr, 1L);
                                return status2;
                            }
                            isZero = fMLE(framePtr + 160L, CC_0p00001) != 0L ? 1L : 0L;
                            if (isZero == 0L) {
                                firadd(framePtr + 112L, CC_N2, framePtr + 112L, 0L);
                                firadd(dataPtr1, framePtr + 160L, outDpbPtr, 1L);
                                fidiv(outDpbPtr, dataPtr1, outDpbPtr);
                                constPtr = framePtr + 112L;
                                dataPtr2 = outDpbPtr;
                            } else {
                                constPtr = CC_N1;
                                dataPtr2 = framePtr + 112L;
                            }
                            status2 = firadd(dataPtr2, constPtr, outDpbPtr, 0L);
                            status1 = fNorm_L((long) (int) status2, outDpbPtr) & 0xffffffffL;
                            status2 = status1;
                            return status2;
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

    long IRRIRR(long cfloDataPtr, long entryCount, long getEntryFn, long pyrPtr, long userStopFn, long outIrrPtr) {
        long flag1 = 0L;
        long flag2 = 0L;
        long flag3 = 0L;
        long intVal = 0L;
        long tempVal1 = 0L;
        long isZero1 = 0L;
        long isZero2 = 0L;
        long isZero3 = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long dataPtr1 = 0L;
        long dataPtr2 = 0L;
        long status3 = 0L;
        long exponent = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(368);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        exponent = (long) (int) getI64(userStopFn);
                        if (exponent == 0L) {
                            if ((entryCount & 0xffffffffL) == 0L) {
                                status2 = 32L;
                                putI64(userStopFn + 8L, 0L);
                                putI64(userStopFn, 0L);
                                return status2;
                            }
                            tempVal2 = 0L;
                            flag2 = 0L;
                            flag3 = 0L;
                            isZero2 = 0L;
                            isZero1 = 0L;
                            putI64(framePtr + 168L, 0L);
                            putI64(framePtr + 160L, 1L);
                            status1 = 0L;
                            putI64(framePtr + 176L, getI64(framePtr + 160L));
                            putI64(framePtr + 184L, getI64(framePtr + 168L));
                            step = 1;
                            continue stepLoop;
                        } else {
                            putI64(framePtr + 24L, 0L);
                            putI64(framePtr + 16L, 1L);
                            if (((exponent + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((exponent + 1L) & 31L)) & 21L) != 0L) {
                                isZero1 = fZero(userStopFn) != 0L ? 1L : 0L;
                                if (isZero1 == 0L && comma(comma(exponent = getI32(userStopFn + 4L), putI32(userStopFn + 4L, exponent - 2L)), exponent < -49997L ? 1L : 0L) != 0L) {
                                    putI64(userStopFn + 8L, 0L);
                                    putI64(userStopFn, 1L);
                                }
                                exponent = (long) (int) getI64(userStopFn);
                            }
                            if (!(-1L < exponent || getI32(userStopFn + 4L) < 0L)) {
                                status2 = 25L;
                                putI64(userStopFn + 8L, 0L);
                                putI64(userStopFn, 0L);
                                return status2;
                            }
                            isZero1 = 0L;
                            exponent = 0L;
                            step = 7;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        dataPtr1 = callIndirect3(getEntryFn, cfloDataPtr, entryCount & 0xffffffffL, status1);
                        fimul(dataPtr1, dataPtr1 + 16L, framePtr + 144L);
                        if (isZero1 != 0L) {
                            if (isZero2 != 0L && comma(isZero3 = fZero(framePtr + 144L) != 0L ? 1L : 0L, isZero3 == 0L ? 1L : 0L) != 0L) {
                                putI64(framePtr + 144L, (((getI32(framePtr + 148L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 144L) & 0xffffffffL));
                            }
                            firadd(framePtr + 176L, framePtr + 144L, framePtr + 176L, 0L);
                            if (flag2 != 0L) {
                                if (-1L < (long) (int) getI64(framePtr + 176L)) {
                                    isZero1 = 0L;
                                    putI64(userStopFn + 8L, 0L);
                                    putI64(userStopFn, 1L);
                                    putI64(framePtr + 24L, 0L);
                                    putI64(framePtr + 16L, 1L);
                                    exponent = 0L;
                                    step = 7;
                                    continue stepLoop;
                                } else {
                                    step = 2;
                                    continue stepLoop;
                                }
                            } else {
                                if ((long) (int) getI64(framePtr + 176L) < 0L) {
                                    flag2 = 1L;
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    isZero3 = fLT(framePtr + 160L, framePtr + 176L) != 0L ? 1L : 0L;
                                    status2 = getI64(framePtr + 176L);
                                    tempVal3 = getI64(framePtr + 184L);
                                    tempVal1 = status1;
                                    if (isZero3 != 0L) {
                                        step = 3;
                                        continue stepLoop;
                                    } else {
                                        step = 2;
                                        continue stepLoop;
                                    }
                                }
                            }
                        } else {
                            isZero3 = fZero(framePtr + 144L) != 0L ? 1L : 0L;
                            if (isZero3 != 0L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                if ((long) (int) getI64(framePtr + 144L) < 0L) {
                                    isZero2 = 1L;
                                    isZero1 = fZero(framePtr + 144L) != 0L ? 1L : 0L;
                                    if (isZero1 == 0L) {
                                        putI64(framePtr + 144L, (((getI32(framePtr + 148L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 144L) & 0xffffffffL));
                                    }
                                }
                                isZero1 = 1L;
                                status2 = getI64(framePtr + 144L);
                                tempVal3 = getI64(framePtr + 152L);
                                tempVal1 = tempVal2;
                                step = 3;
                                continue stepLoop;
                            }
                        }
                    }
                    case 2: {
                        if (flag3 != 0L) {
                            step = 5;
                            continue stepLoop;
                        } else {
                            step = 4;
                            continue stepLoop;
                        }
                    }
                    case 3: {
                        tempVal2 = tempVal1;
                        putI64(framePtr + 168L, tempVal3);
                        putI64(framePtr + 160L, status2);
                        putI64(framePtr + 176L, getI64(framePtr + 160L));
                        putI64(framePtr + 184L, getI64(framePtr + 168L));
                        if (flag3 == 0L) {
                            step = 4;
                            continue stepLoop;
                        } else {
                            step = 5;
                            continue stepLoop;
                        }
                    }
                    case 4: {
                        status1 = (status1 + 1L) & 0xffffffffL;
                        if (status1 < (entryCount & 0xffffffffL)) {
                            step = 1;
                            continue stepLoop;
                        } else {
                            isZero1 = fZero(framePtr + 176L) != 0L ? 1L : 0L;
                            if (isZero1 != 0L) {
                                tempVal3 = 1L;
                                status2 = 0L;
                                putI64(userStopFn + 8L, 0L);
                                putI64(userStopFn, tempVal3);
                                return status2;
                            }
                            if (flag2 == 0L) {
                                putI64(framePtr + 168L, 0L);
                                putI64(framePtr + 160L, 1L);
                                tempVal2 = 0L;
                                flag2 = 0L;
                                flag3 = 1L;
                                isZero2 = 0L;
                                isZero1 = 0L;
                                status1 = (entryCount - 1L) & 0xffffffffL;
                                putI64(framePtr + 176L, getI64(framePtr + 160L));
                                putI64(framePtr + 184L, getI64(framePtr + 168L));
                                step = 1;
                                continue stepLoop;
                            } else {
                                isZero1 = 1L;
                                step = 6;
                                continue stepLoop;
                            }
                        }
                    }
                    case 5: {
                        if (status1 == 0L) {
                            isZero1 = fZero(framePtr + 176L) != 0L ? 1L : 0L;
                            if (isZero1 != 0L) {
                                tempVal3 = 1L;
                                status2 = 0L;
                                putI64(userStopFn + 8L, 0L);
                                putI64(userStopFn, tempVal3);
                                return status2;
                            }
                            if (flag2 == 0L) {
                                status2 = 26L;
                                putI64(userStopFn + 8L, 0L);
                                putI64(userStopFn, 0L);
                                return status2;
                            }
                            isZero1 = 0L;
                            step = 6;
                            continue stepLoop;
                        } else {
                            status1 = (status1 - 1L) & 0xffffffffL;
                            step = 1;
                            continue stepLoop;
                        }
                    }
                    case 6: {
                        putI64(framePtr + 104L, 0L);
                        putI64(framePtr + 96L, 1L);
                        status1 = 0L;
                        flag1 = 1L;
                        putI64(framePtr + 112L, getI64(framePtr + 96L));
                        putI64(framePtr + 120L, getI64(framePtr + 104L));
                        putI64(framePtr + 160L, getI64(framePtr + 96L));
                        putI64(framePtr + 168L, getI64(framePtr + 104L));
                        do {
                            dataPtr2 = callIndirect3(getEntryFn, cfloDataPtr, entryCount & 0xffffffffL, status1);
                            dataPtr1 = dataPtr2 + 16L;
                            fimul(dataPtr2, dataPtr1, framePtr + 80L);
                            isZero2 = fZero(framePtr + 80L) != 0L ? 1L : 0L;
                            if (isZero2 == 0L) {
                                firadd(framePtr + 80L, framePtr + 112L, framePtr + 112L, 0L);
                                firadd(dataPtr1, CC_N1, framePtr + 176L, 0L);
                                fidiv2(framePtr + 176L, framePtr + 176L);
                                firadd(framePtr + 176L, framePtr + 160L, framePtr + 176L, 0L);
                                fimul(framePtr + 80L, framePtr + 176L, framePtr + 176L);
                                firadd(framePtr + 176L, framePtr + 96L, framePtr + 96L, 0L);
                            }
                            firadd(dataPtr1, framePtr + 160L, framePtr + 160L, 0L);
                            status1 = (status1 + 1L) & 0xffffffffL;
                            if (tempVal2 <= status1 && (((flag1 ^ 1L)) & 0xffL) == 0L) {
                                putI64(framePtr + 152L, getI64(framePtr + 120L));
                                putI64(framePtr + 144L, getI64(framePtr + 112L));
                                putI64(framePtr + 136L, getI64(framePtr + 104L));
                                putI64(framePtr + 128L, getI64(framePtr + 96L));
                                putI64(framePtr + 24L, getI64(framePtr + 168L));
                                putI64(framePtr + 16L, getI64(framePtr + 160L));
                                flag1 = 0L;
                                putI64(framePtr + 104L, 0L);
                                putI64(framePtr + 96L, 1L);
                                putI64(framePtr + 112L, getI64(framePtr + 96L));
                                putI64(framePtr + 120L, getI64(framePtr + 104L));
                            }
                        } while ((entryCount & 0xffffffffL) != status1);
                        fidiv(framePtr + 96L, framePtr + 112L, framePtr + 80L);
                        fidiv(framePtr + 128L, framePtr + 144L, framePtr + 64L);
                        firadd(framePtr + 80L, framePtr + 64L, framePtr + 80L, 0L);
                        isZero2 = fZero(framePtr + 80L) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            putI64(framePtr + 88L, 0x100000000000000L);
                            putI64(framePtr + 80L, 0xfffffe0d00000001L);
                        }
                        fidiv(CC_1, framePtr + 80L, framePtr + 48L);
                        fidiv(framePtr + 112L, framePtr + 144L, framePtr + 32L);
                        if (getI32(framePtr + 32L) == 3L) {
                            putI32(framePtr + 32L, 1L);
                        }
                        isZero2 = fZero(framePtr + 32L) != 0L ? 1L : 0L;
                        if (isZero2 == 0L) {
                            putI32(framePtr + 32L, -getI32(framePtr + 32L));
                        }
                        status3 = fpow(framePtr + 32L, framePtr + 48L, userStopFn, 1L);
                        if ((status3 & 0xffffffffL) < 2L && comma(tempVal2 = (getI64(userStopFn) + 1L) & 0xffffffffL, tempVal2 < 5L ? 1L : 0L) != 0L && ((1L << (int) (tempVal2 & 31L)) & 21L) != 0L) {
                            putI64(framePtr + 184L, 0x100000000000000L);
                            putI64(framePtr + 176L, 1L);
                            isZero2 = fZero(framePtr + 176L) != 0L ? 1L : 0L;
                            if (isZero2 == 0L) {
                                putI64(framePtr + 176L, (((getI32(framePtr + 180L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 176L) & 0xffffffffL));
                            }
                            fiadd(userStopFn, framePtr + 176L, userStopFn, 1L);
                        } else {
                            putI64(userStopFn + 8L, 0L);
                            putI64(userStopFn, 1L);
                        }
                        exponent = 0L;
                        step = 7;
                        continue stepLoop;
                    }
                    case 7: {
                        if (outIrrPtr != 0L && comma(status2 = callIndirect2(outIrrPtr, exponent, userStopFn), status2 & 1L) != 0L) {
                            status2 = 30L;
                            putI64(userStopFn + 8L, 0L);
                            putI64(userStopFn, 0L);
                            return status2;
                        }
                        status2 = IRRNPVInternal(cfloDataPtr, (long) (int) entryCount, getEntryFn, userStopFn, framePtr + 160L, framePtr + 144L, framePtr + 128L, framePtr + 80L, 0L);
                        if (1L < (status2 & 0xffffffffL)) {
                            tempVal3 = 0L;
                            putI64(userStopFn + 8L, 0L);
                            putI64(userStopFn, tempVal3);
                            return status2;
                        }
                        isZero2 = fZero(framePtr + 160L) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            step = 8;
                            continue stepLoop;
                        } else {
                            fidiv(framePtr + 144L, framePtr + 128L, framePtr + 112L);
                            firadd(framePtr + 112L, framePtr + 16L, framePtr + 96L, 0L);
                            isZero2 = fZero(framePtr + 96L) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                if ((long) (int) getI64(framePtr + 112L) < 1L) {
                                    step = 10;
                                    continue stepLoop;
                                } else {
                                    step = 11;
                                    continue stepLoop;
                                }
                            } else {
                                if ((long) (int) getI64(framePtr + 96L) < 1L) {
                                    isZero2 = fLT(CC_N0p11, framePtr + 96L) != 0L ? 1L : 0L;
                                    if (isZero2 != 0L) {
                                        step = 10;
                                        continue stepLoop;
                                    } else {
                                        step = 9;
                                        continue stepLoop;
                                    }
                                } else {
                                    isZero2 = fLT(framePtr + 96L, CC_1p1) != 0L ? 1L : 0L;
                                    if (isZero2 != 0L) {
                                        step = 11;
                                        continue stepLoop;
                                    } else {
                                        step = 9;
                                        continue stepLoop;
                                    }
                                }
                            }
                        }
                    }
                    case 8: {
                        tempVal2 = (getI64(userStopFn) + 1L) & 0xffffffffL;
                        if (tempVal2 < 5L && ((1L << (int) (tempVal2 & 31L)) & 21L) != 0L && comma(isZero2 = fZero(userStopFn) != 0L ? 1L : 0L, isZero2 == 0L ? 1L : 0L) != 0L && comma(comma(exponent = getI32(userStopFn + 4L), putI32(userStopFn + 4L, exponent + 2L)), 49997L < exponent ? 1L : 0L) != 0L) {
                            putI64(userStopFn + 8L, 0x999999999999999L);
                            putI64(userStopFn, 0xc34f00000002L);
                        }
                        status2 = fimul(userStopFn, pyrPtr, userStopFn);
                        status1 = fNorm_L((long) (int) status2, userStopFn) & 0xffffffffL;
                        tempVal2 = 35L;
                        if (-1L < (long) (int) getI64(userStopFn) || isZero1 != 0L) {
                            tempVal2 = status1;
                        }
                        status2 = tempVal2;
                        return status2;
                    }
                    case 9: {
                        intVal = getI32(framePtr + 100L);
                        fidiv(framePtr + 80L, framePtr + 96L, framePtr + 64L);
                        if ((getI64(framePtr + 64L) & 0xffffffffL) == 3L) {
                            putI32(framePtr + 64L, 1L);
                        }
                        isZero2 = fZero(framePtr + 64L) != 0L ? 1L : 0L;
                        if (isZero2 == 0L) {
                            putI32(framePtr + 64L, -getI64(framePtr + 64L));
                        }
                        firadd(framePtr + 64L, framePtr + 80L, framePtr + 64L, 0L);
                        putI64(framePtr + 184L, 0x100000000000000L);
                        putI32(framePtr + 180L, 0L);
                        putI32(framePtr + 176L, 1L);
                        isZero2 = fZero(framePtr + 176L) != 0L ? 1L : 0L;
                        if (isZero2 == 0L) {
                            putI32(framePtr + 176L, -getI64(framePtr + 176L));
                        }
                        fiadd(framePtr + 64L, framePtr + 176L, framePtr + 64L, 1L);
                        putI64(framePtr + 184L, getI64(userStopFn + 8L));
                        putI64(framePtr + 176L, getI64(userStopFn));
                        putI64(userStopFn + 8L, getI64(framePtr + 72L));
                        putI64(userStopFn, (((getI32(framePtr + 68L) & 0xffffffffL)) << 32) | (getI64(framePtr + 64L) & 0xffffffffL));
                        if (11L < intVal) {
                            step = 8;
                            continue stepLoop;
                        } else {
                            isZero2 = fEQ(framePtr + 176L, userStopFn) != 0L ? 1L : 0L;
                            exponent = (long) (int) (exponent + 1L);
                            if (isZero2 == 0L) {
                                step = 7;
                                continue stepLoop;
                            } else {
                                step = 8;
                                continue stepLoop;
                            }
                        }
                    }
                    case 10: {
                        putI64(framePtr + 96L, -1L);
                        putI64(framePtr + 104L, 0x110000000000000L);
                        step = 9;
                        continue stepLoop;
                    }
                    case 11: {
                        putI64(framePtr + 96L, 1L);
                        putI64(framePtr + 104L, 0x110000000000000L);
                        step = 9;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long IRRMIRRFMRR(long cfloDataPtr, long entryCount, long getEntryFn, long safeRatePtr, long reinvestRatePtr, long pyrPtr, long outMirrPtr, long outFmrrPtr) {
        long exponent = 0L;
        long isZero = 0L;
        long status = 0L;
        long dataPtr = 0L;
        long intVal = 0L;
        long index = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(416);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        result = 0L;
                        if (outMirrPtr != 0L) {
                            putU32(outMirrPtr + 8L, 0L);
                            putU32(outMirrPtr + 12L, 0L);
                            putU32(outMirrPtr, 0L);
                            putU32(outMirrPtr + 4L, 0L);
                        }
                        if (outFmrrPtr != 0L) {
                            putI64(outFmrrPtr + 8L, 0L);
                            putI64(outFmrrPtr, 0L);
                        }
                        fidiv(reinvestRatePtr, pyrPtr, framePtr + 160L);
                        fidiv(safeRatePtr, pyrPtr, framePtr + 144L);
                        if (((getI64(framePtr + 160L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 160L) + 1L) & 31L)) & 21L) != 0L) {
                            isZero = fZero(framePtr + 160L) != 0L ? 1L : 0L;
                            if (isZero == 0L) {
                                index = getI32(framePtr + 164L);
                                putI64(framePtr + 160L, ((((getI32(framePtr + 164L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 160L) & 0xffffffffL));
                                if (index < -49997L) {
                                    putI64(framePtr + 168L, 0L);
                                    putI64(framePtr + 160L, 1L);
                                }
                            }
                        }
                        if (!(-1L < (long) (int) getI64(framePtr + 160L) || Long.compareUnsigned(getI64(framePtr + 160L), 0L) < 0L)) {
                            result = 25L;
                            return result;
                        }
                        if (((getI64(framePtr + 144L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 144L) + 1L) & 31L)) & 21L) != 0L) {
                            isZero = fZero(framePtr + 144L) != 0L ? 1L : 0L;
                            if (isZero == 0L) {
                                index = getI32(framePtr + 148L);
                                putI64(framePtr + 144L, ((((getI32(framePtr + 148L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 144L) & 0xffffffffL));
                                if (index < -49997L) {
                                    putI64(framePtr + 152L, 0L);
                                    putI64(framePtr + 144L, 1L);
                                }
                            }
                        }
                        if (!(-1L < (long) (int) getI64(framePtr + 144L) || Long.compareUnsigned(getI64(framePtr + 144L), 0L) < 0L)) {
                            result = 25L;
                            return result;
                        }
                        firadd(framePtr + 160L, CC_1, framePtr + 128L, 0L);
                        if (outMirrPtr != 0L) {
                            putU8(framePtr + 192L, 1L);
                            putI64(framePtr + 208L, 0L);
                            putI64(framePtr + 200L, 0L);
                            putI64(framePtr + 224L, 0L);
                            putI64(framePtr + 216L, 0L);
                            putI64(framePtr + 176L, cfloDataPtr);
                            putI64(framePtr + 184L, getEntryFn);
                            status = IRRNPVInternal(framePtr + 176L, (long) (int) entryCount, FPTR_IRRMIRRNextEntry, framePtr + 160L, outMirrPtr, 0L, 0L, 0L, framePtr + 112L);
                            result = status & 0xffffffffL;
                            if (1L < (status & 0xffffffffL)) {
                                return result;
                            }
                            putU8(framePtr + 192L, 0L);
                            status = IRRNPVInternal(framePtr + 176L, (long) (int) entryCount, FPTR_IRRMIRRNextEntry, framePtr + 144L, framePtr + 96L, 0L, 0L, 0L, 0L);
                            result = status & 0xffffffffL;
                            if (1L < (status & 0xffffffffL)) {
                                return result;
                            }
                            status = fidiv(outMirrPtr, framePtr + 96L, outMirrPtr);
                            result = status & 0xffffffffL;
                            if (getU32(outMirrPtr) == 3L) {
                                putU32(outMirrPtr, 1L);
                            }
                            isZero = fZero(outMirrPtr) != 0L ? 1L : 0L;
                            if (isZero == 0L) {
                                putU32(outMirrPtr, -getU32(outMirrPtr));
                            }
                            if (1L < (status & 0xffffffffL)) {
                                return result;
                            }
                            status = finthroot(outMirrPtr, framePtr + 112L, outMirrPtr);
                            result = status & 0xffffffffL;
                            if (1L < (status & 0xffffffffL)) {
                                return result;
                            }
                            fimul(outMirrPtr, framePtr + 128L, outMirrPtr);
                            firadd(outMirrPtr, CC_N1, outMirrPtr, 0L);
                            if (((getU32(outMirrPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(outMirrPtr) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(outMirrPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(exponent = getU32(outMirrPtr + 4L), putU32(outMirrPtr + 4L, exponent + 2L)), 49997L < (long) (int) exponent ? 1L : 0L) != 0L) {
                                putU32(outMirrPtr + 8L, 0x99999999L);
                                putU32(outMirrPtr + 12L, 0x9999999L);
                                putU32(outMirrPtr, 2L);
                                putU32(outMirrPtr + 4L, 49999L);
                            }
                            status = fimul(outMirrPtr, pyrPtr, outMirrPtr);
                            fNorm_L((long) (int) status, outMirrPtr);
                        }
                        if (outFmrrPtr == 0L) {
                            result = 0L;
                            return result;
                        }
                        putI64(framePtr + 120L, 0L);
                        putI64(framePtr + 112L, 1L);
                        putI64(framePtr + 104L, 0x100000000000000L);
                        putI64(framePtr + 96L, 0xffffffffL);
                        putI64(framePtr + 176L, getI64(framePtr + 112L));
                        putI64(framePtr + 184L, getI64(framePtr + 120L));
                        fidiv(CC_1, framePtr + 128L, framePtr + 64L);
                        firadd(framePtr + 144L, CC_1, framePtr + 80L, 0L);
                        fidiv(CC_1, framePtr + 80L, framePtr + 48L);
                        index = (long) (int) entryCount;
                        step = 1;
                        continue stepLoop;
                    }
                    case 1: {
                        index = (long) (int) (index - 1L);
                        if (index < 0L) {
                            isZero = fLE(framePtr + 96L, CC_1) != 0L ? 1L : 0L;
                            if (isZero != 0L) {
                                result = 32L;
                            } else {
                                fpow(framePtr + 128L, framePtr + 96L, framePtr + 32L, 1L);
                                fimul(framePtr + 112L, framePtr + 32L, framePtr + 112L);
                                status = TVMI(framePtr + 96L, outFmrrPtr, framePtr + 176L, CC_0, framePtr + 112L, pyrPtr, CC_1, 0L, 0L, 0L);
                                result = status & 0xffffffffL;
                            }
                            return result;
                        }
                        dataPtr = callIndirect3(getEntryFn, cfloDataPtr, (long) (int) entryCount, index);
                        putI64(framePtr + 40L, getI64(dataPtr + 24L));
                        putI64(framePtr + 32L, getI64(dataPtr + 16L));
                        step = 2;
                        continue stepLoop;
                    }
                    case 2: {
                        isZero = fZero(framePtr + 32L) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            if ((long) (int) getU32(dataPtr) < 1L) {
                                IRRMIRRCompute(framePtr + 32L, framePtr + 64L, CC_0, framePtr + 112L, framePtr + 112L, framePtr + 96L);
                                IRRMIRRCompute(framePtr + 32L, framePtr + 48L, dataPtr, framePtr + 176L, framePtr + 176L, 0L);
                                intVal = 0L;
                                step = 4;
                                continue stepLoop;
                            } else {
                                isZero = fZero(framePtr + 176L) != 0L ? 1L : 0L;
                                if (isZero != 0L) {
                                    IRRMIRRCompute(framePtr + 32L, framePtr + 64L, dataPtr, framePtr + 112L, framePtr + 112L, framePtr + 96L);
                                    intVal = 0L;
                                    step = 4;
                                    continue stepLoop;
                                } else {
                                    isZero = fEQ(CC_1, framePtr + 32L) != 0L ? 1L : 0L;
                                    if (isZero != 0L) {
                                        fimul(framePtr + 176L, framePtr + 48L, framePtr + 176L);
                                        firadd(framePtr + 176L, dataPtr, framePtr + 176L, 0L);
                                        fimul(framePtr + 112L, framePtr + 64L, framePtr + 112L);
                                        if (0L < (long) (int) getI64(framePtr + 176L)) {
                                            firadd(framePtr + 112L, framePtr + 176L, framePtr + 112L, 0L);
                                            putI64(framePtr + 184L, 0L);
                                            putI64(framePtr + 176L, 1L);
                                        }
                                        firadd(framePtr + 96L, CC_1, framePtr + 96L, 0L);
                                        intVal = 0L;
                                        step = 4;
                                        continue stepLoop;
                                    } else {
                                        status = TVMN(framePtr + 16L, safeRatePtr, CC_0, dataPtr, framePtr + 176L, pyrPtr, pyrPtr, 0L);
                                        if ((status & 0xffffffffL) < 2L) {
                                            fiIP(framePtr + 16L, framePtr + 16L);
                                            fimin(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                                            IRRMIRRCompute(framePtr + 16L, framePtr + 48L, dataPtr, framePtr + 176L, framePtr + 176L, framePtr + 96L);
                                            firadd(framePtr + 32L, framePtr + 16L, framePtr + 32L, 1L);
                                            IRRMIRRCompute(framePtr + 16L, framePtr + 64L, CC_0, framePtr + 112L, framePtr + 112L, 0L);
                                            isZero = fZero(framePtr + 32L) != 0L ? 1L : 0L;
                                            if (isZero != 0L) {
                                                intVal = 5L;
                                            } else {
                                                fimul(framePtr + 176L, framePtr + 48L, framePtr + 176L);
                                                firadd(framePtr + 176L, dataPtr, framePtr + 176L, 0L);
                                                IRRMIRRCompute(CC_1, framePtr + 64L, framePtr + 176L, framePtr + 112L, framePtr + 112L, framePtr + 96L);
                                                putI64(framePtr + 184L, 0L);
                                                putI64(framePtr + 176L, 1L);
                                                firadd(framePtr + 32L, CC_N1, framePtr + 32L, 0L);
                                                intVal = 0L;
                                            }
                                        } else {
                                            intVal = 1L;
                                            result = status & 0xffffffffL;
                                        }
                                        if (intVal == 0L) {
                                            step = 2;
                                            continue stepLoop;
                                        } else {
                                            if (intVal == 5L) {
                                                step = 3;
                                                continue stepLoop;
                                            } else {
                                                step = 4;
                                                continue stepLoop;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    case 3: {
                        intVal = 0L;
                        step = 4;
                        continue stepLoop;
                    }
                    case 4: {
                        if (intVal != 0L) {
                            return result;
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

    void IRRMIRRNextEntry(long iterStatePtr) {
        long intPtr = 0L;
        long tempVal = 0L;
        intPtr = callIndirect1(getI64(iterStatePtr + 8L), getI64(iterStatePtr));
        tempVal = getI64(intPtr + 16L);
        putI64(iterStatePtr + 48L, getI64(intPtr + 24L));
        putI64(iterStatePtr + 40L, tempVal);
        if (getU8(iterStatePtr + 16L) == (-(getI32(intPtr) >> 31) & 0xffffffffL)) {
            intPtr = CC_0;
        }
        tempVal = getI64(intPtr);
        putI64(iterStatePtr + 32L, getI64(intPtr + 8L));
        putI64(iterStatePtr + 24L, tempVal);
    }

    long IRRMIRRCompute(long cfloDataPtr, long entryCount, long getEntryFn, long safeRatePtr, long reinvestRatePtr, long outResultPtr) {
        long isZero = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            isZero = fEQ(CC_1, cfloDataPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                fimul(safeRatePtr, entryCount, reinvestRatePtr);
                status = firadd(reinvestRatePtr, getEntryFn, reinvestRatePtr, 0L);
            } else {
                fpow(entryCount, cfloDataPtr, framePtr + 56L, 1L);
                fimul(safeRatePtr, framePtr + 56L, reinvestRatePtr);
                isZero = fZero(getEntryFn) != 0L ? 1L : 0L;
                status = isZero;
                if (isZero == 0L) {
                    firadd(entryCount, CC_N1, framePtr + 40L, 0L);
                    filnp1(framePtr + 40L, framePtr + 24L);
                    fimul(framePtr + 24L, cfloDataPtr, framePtr + 24L);
                    fiexpm(framePtr + 24L, 0L, framePtr + 24L);
                    fidiv(framePtr + 24L, framePtr + 40L, framePtr + 24L);
                    fimul(getEntryFn, framePtr + 24L, framePtr + 24L);
                    getEntryFn = framePtr + 24L;
                    status = firadd(reinvestRatePtr, getEntryFn, reinvestRatePtr, 0L);
                }
            }
            if (outResultPtr != 0L) {
                status = firadd(outResultPtr, cfloDataPtr, outResultPtr, 0L);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long DEPRACRS(long basisPtr, long ratePctPtr, long outDeprPtr) {
        long exponent = 0L;
        long isZero = 0L;
        fimul(basisPtr, ratePctPtr, outDeprPtr);
        if (((getI32(outDeprPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI32(outDeprPtr) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(outDeprPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(exponent = getI32(outDeprPtr + 4L), putI32(outDeprPtr + 4L, exponent - 2L)), exponent < -49997L ? 1L : 0L) != 0L) {
            putI32(outDeprPtr + 8L, 0L);
            putI32(outDeprPtr + 12L, 0L);
            putI32(outDeprPtr, 1L);
            putI32(outDeprPtr + 4L, 0L);
        }
        return 0L;
    }

    long DEPRSOYD_nf(long costPtr, long salvagePtr, long lifePtr, long periodPtr, long outDeprPtr, long outRemDepPtr, long outBookValPtr) {
        long isZero = 0L;
        long result = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(288);
        try {
            isZero = fLT(costPtr, salvagePtr) != 0L ? 1L : 0L;
            if (isZero != 0L || (long) (int) comma(isZero = fLT(lifePtr, CC_1) != 0L ? 1L : 0L, isZero) != 0L) {
                putI64(outDeprPtr + 8L, 0L);
                putI64(outDeprPtr, 0L);
                if (outRemDepPtr != 0L) {
                    putI64(outRemDepPtr + 8L, 0L);
                    putI64(outRemDepPtr, 0L);
                }
                if (outBookValPtr == 0L) {
                    result = 24L;
                } else {
                    result = 24L;
                    putI64(outBookValPtr + 8L, 0L);
                    putI64(outBookValPtr, 0L);
                }
            } else {
                isZero = fLT(lifePtr, periodPtr) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    putI64(outDeprPtr + 8L, 0L);
                    putI64(outDeprPtr, 1L);
                    if (outRemDepPtr != 0L) {
                        putI64(outRemDepPtr + 8L, 0L);
                        putI64(outRemDepPtr, 1L);
                    }
                    result = 0L;
                    if (outBookValPtr != 0L) {
                        status = getI64(salvagePtr);
                        putI64(outBookValPtr + 8L, getI64(salvagePtr + 8L));
                        putI64(outBookValPtr, status);
                    }
                } else {
                    firadd(lifePtr, CC_1, framePtr + 72L, 0L);
                    fimul(lifePtr, framePtr + 72L, framePtr + 72L);
                    firadd(costPtr, salvagePtr, framePtr + 56L, 1L);
                    fimul2(framePtr + 56L, framePtr + 88L);
                    fidiv(framePtr + 88L, framePtr + 72L, framePtr + 88L);
                    firadd(lifePtr, periodPtr, framePtr + 72L, 1L);
                    firadd(framePtr + 72L, CC_1, framePtr + 72L, 0L);
                    status = fimul(framePtr + 88L, framePtr + 72L, framePtr + 72L);
                    fNorm_L((long) (int) status, framePtr + 72L);
                    if (outRemDepPtr != 0L || outBookValPtr != 0L) {
                        fimul2(lifePtr, framePtr + 40L);
                        firadd(framePtr + 40L, CC_1, framePtr + 40L, 0L);
                        fimul(framePtr + 40L, periodPtr, framePtr + 40L);
                        fimul(periodPtr, periodPtr, framePtr + 24L);
                        firadd(framePtr + 40L, framePtr + 24L, framePtr + 40L, 1L);
                        fidiv2(framePtr + 40L, framePtr + 40L);
                        fimul(framePtr + 88L, framePtr + 40L, framePtr + 40L);
                        if (outRemDepPtr != 0L) {
                            putI64(framePtr + 104L, getI64(framePtr + 40L));
                            putI64(framePtr + 112L, getI64(framePtr + 48L));
                            if ((long) (int) getI64(framePtr + 40L) == 3L) {
                                putI32(framePtr + 108L, getI64(framePtr + 40L) >>> 32);
                                putI64(framePtr + 104L, (((getI32(framePtr + 108L) & 0xffffffffL)) << 32) | 1L);
                            }
                            isZero = fZero(framePtr + 104L) != 0L ? 1L : 0L;
                            if (isZero == 0L) {
                                putI64(framePtr + 104L, (((getI32(framePtr + 108L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 104L) & 0xffffffffL));
                            }
                            status = firadd(framePtr + 56L, framePtr + 104L, framePtr + 40L, 0L);
                            fNorm_L((long) (int) status, framePtr + 40L);
                            fMakeZeroIfClose(framePtr + 40L, costPtr);
                            putI64(outRemDepPtr + 8L, getI64(framePtr + 48L));
                            putI64(outRemDepPtr, getI64(framePtr + 40L));
                        }
                        if (outBookValPtr != 0L) {
                            putI64(framePtr + 104L, getI64(framePtr + 40L));
                            putI64(framePtr + 112L, getI64(framePtr + 48L));
                            if ((long) (int) getI64(framePtr + 40L) == 3L) {
                                putI32(framePtr + 108L, getI64(framePtr + 40L) >>> 32);
                                putI64(framePtr + 104L, (((getI32(framePtr + 108L) & 0xffffffffL)) << 32) | 1L);
                            }
                            isZero = fZero(framePtr + 104L) != 0L ? 1L : 0L;
                            if (isZero == 0L) {
                                putI64(framePtr + 104L, (((getI32(framePtr + 108L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 104L) & 0xffffffffL));
                            }
                            status = firadd(costPtr, framePtr + 104L, framePtr + 40L, 0L);
                            fNorm_L((long) (int) status, framePtr + 40L);
                            fMakeZeroIfClose(framePtr + 40L, costPtr);
                            putI64(outBookValPtr + 8L, getI64(framePtr + 48L));
                            putI64(outBookValPtr, getI64(framePtr + 40L));
                        }
                    }
                    result = 0L;
                    putI64(outDeprPtr + 8L, getI64(framePtr + 80L));
                    putI64(outDeprPtr, getI64(framePtr + 72L));
                }
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long DEPRSOYD(long costPtr, long salvagePtr, long lifePtr, long periodPtr, long monthPtr, long outDeprPtr, long outRemDepPtr, long outBookValPtr) {
        long isZero = 0L;
        long status = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(320);
        try {
            status = DEPRSOYD_nf(costPtr, salvagePtr, lifePtr, periodPtr, outDeprPtr, outRemDepPtr, outBookValPtr);
            result = status & 0xffffffffL;
            if (!((status & 0xffffffffL) < 2L && comma(isZero = fEQ(monthPtr, CC_1) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L)) {
                return result;
            }
            firadd(periodPtr, CC_N1, framePtr + 80L, 0L);
            isZero = fZero(framePtr + 80L) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                putI64(framePtr + 120L, 0L);
                putI64(framePtr + 112L, 1L);
                firadd(costPtr, salvagePtr, framePtr + 96L, 1L);
                putI64(framePtr + 72L, getI64(costPtr + 8L));
                putI64(framePtr + 64L, getI64(costPtr));
            } else {
                status = DEPRSOYD_nf(costPtr, salvagePtr, lifePtr, framePtr + 80L, framePtr + 112L, framePtr + 96L, framePtr + 64L);
                result = status & 0xffffffffL;
                if (1L < (status & 0xffffffffL)) {
                    return result;
                }
            }
            firadd(monthPtr, CC_1, framePtr + 32L, 1L);
            fidiv(framePtr + 32L, CC_12, framePtr + 32L);
            firadd(CC_1, framePtr + 32L, framePtr + 16L, 1L);
            fimul(outDeprPtr, framePtr + 16L, outDeprPtr);
            fimul(framePtr + 112L, framePtr + 32L, framePtr + 48L);
            status = firadd(outDeprPtr, framePtr + 48L, framePtr + 48L, 0L);
            fNorm_L((long) (int) status, framePtr + 48L);
            if (outRemDepPtr != 0L) {
                putI64(framePtr + 144L, getI64(outDeprPtr + 8L));
                putI64(framePtr + 136L, getI64(outDeprPtr));
                if ((long) (int) getI64(framePtr + 136L) == 3L) {
                    putI32(framePtr + 140L, getI64(framePtr + 136L) >>> 32);
                    putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | 1L);
                }
                isZero = fZero(framePtr + 136L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 136L) & 0xffffffffL));
                }
                status = firadd(framePtr + 96L, framePtr + 136L, outRemDepPtr, 0L);
                fNorm_L((long) (int) status, outRemDepPtr);
                fMakeZeroIfClose(outRemDepPtr, costPtr);
            }
            if (outBookValPtr == 0L) {
                result = 0L;
                putI64(outDeprPtr + 8L, getI64(framePtr + 56L));
                putI64(outDeprPtr, getI64(framePtr + 48L));
                return result;
            }
            putI64(framePtr + 144L, getI64(outDeprPtr + 8L));
            putI64(framePtr + 136L, getI64(outDeprPtr));
            if ((long) (int) getI64(framePtr + 136L) == 3L) {
                putI32(framePtr + 140L, getI64(framePtr + 136L) >>> 32);
                putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | 1L);
            }
            isZero = fZero(framePtr + 136L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 136L) & 0xffffffffL));
            }
            status = firadd(framePtr + 64L, framePtr + 136L, outBookValPtr, 0L);
            fNorm_L((long) (int) status, outBookValPtr);
            fMakeZeroIfClose(outBookValPtr, costPtr);
            result = 0L;
            putI64(outDeprPtr + 8L, getI64(framePtr + 56L));
            putI64(outDeprPtr, getI64(framePtr + 48L));
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long DEPRSL(long costPtr, long salvagePtr, long lifePtr, long periodPtr, long monthPtr, long outDeprPtr, long outRemDepPtr, long outBookValPtr) {
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(320);
        try {
            status1 = DEPRSL_nf(costPtr, salvagePtr, lifePtr, periodPtr, outDeprPtr, outRemDepPtr, outBookValPtr) & 0xffffffffL;
            if (!(status1 < 2L && comma(isZero = fEQ(monthPtr, CC_1) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L)) {
                return status1;
            }
            firadd(periodPtr, CC_N1, framePtr + 80L, 0L);
            isZero = fZero(framePtr + 80L) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                putI64(framePtr + 120L, 0L);
                putI64(framePtr + 112L, 1L);
                firadd(costPtr, salvagePtr, framePtr + 96L, 1L);
                putI64(framePtr + 72L, getI64(costPtr + 8L));
                putI64(framePtr + 64L, getI64(costPtr));
            } else {
                status1 = DEPRSL_nf(costPtr, salvagePtr, lifePtr, framePtr + 80L, framePtr + 112L, framePtr + 96L, framePtr + 64L) & 0xffffffffL;
                if (1L < status1) {
                    return status1;
                }
            }
            firadd(monthPtr, CC_N1, framePtr + 32L, 0L);
            fidiv(framePtr + 32L, CC_12, framePtr + 32L);
            firadd(CC_1, framePtr + 32L, framePtr + 16L, 1L);
            fimul(outDeprPtr, framePtr + 16L, outDeprPtr);
            fimul(framePtr + 112L, framePtr + 32L, framePtr + 48L);
            status2 = firadd(outDeprPtr, framePtr + 48L, framePtr + 48L, 0L);
            fNorm_L((long) (int) status2, framePtr + 48L);
            if (outRemDepPtr != 0L) {
                putI64(framePtr + 144L, getI64(outDeprPtr + 8L));
                putI64(framePtr + 136L, getI64(outDeprPtr));
                if ((long) (int) getI64(framePtr + 136L) == 3L) {
                    putI32(framePtr + 140L, getI64(framePtr + 136L) >>> 32);
                    putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | 1L);
                }
                isZero = fZero(framePtr + 136L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 136L) & 0xffffffffL));
                }
                status2 = firadd(framePtr + 96L, framePtr + 136L, outRemDepPtr, 0L);
                fNorm_L((long) (int) status2, outRemDepPtr);
                fMakeZeroIfClose(outRemDepPtr, costPtr);
            }
            if (outBookValPtr == 0L) {
                status1 = 0L;
                putI64(outDeprPtr + 8L, getI64(framePtr + 56L));
                putI64(outDeprPtr, getI64(framePtr + 48L));
                return status1;
            }
            putI64(framePtr + 144L, getI64(outDeprPtr + 8L));
            putI64(framePtr + 136L, getI64(outDeprPtr));
            if ((long) (int) getI64(framePtr + 136L) == 3L) {
                putI32(framePtr + 140L, getI64(framePtr + 136L) >>> 32);
                putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | 1L);
            }
            isZero = fZero(framePtr + 136L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 136L) & 0xffffffffL));
            }
            status2 = firadd(framePtr + 64L, framePtr + 136L, outBookValPtr, 0L);
            fNorm_L((long) (int) status2, outBookValPtr);
            fMakeZeroIfClose(outBookValPtr, costPtr);
            status1 = 0L;
            putI64(outDeprPtr + 8L, getI64(framePtr + 56L));
            putI64(outDeprPtr, getI64(framePtr + 48L));
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    long DEPRSL_nf(long costPtr, long salvagePtr, long lifePtr, long periodPtr, long outDeprPtr, long outRemDepPtr, long outBookValPtr) {
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            isZero = fLT(costPtr, salvagePtr) != 0L ? 1L : 0L;
            if (isZero != 0L || (long) (int) comma(isZero = fLT(lifePtr, CC_1) != 0L ? 1L : 0L, isZero) != 0L) {
                putU32(outDeprPtr + 8L, 0L);
                putU32(outDeprPtr + 12L, 0L);
                putU32(outDeprPtr, 0L);
                putU32(outDeprPtr + 4L, 0L);
                if (outRemDepPtr != 0L) {
                    putI64(outRemDepPtr + 8L, 0L);
                    putI64(outRemDepPtr, 0L);
                }
                if (outBookValPtr == 0L) {
                    status1 = 24L;
                } else {
                    status1 = 24L;
                    putI64(outBookValPtr + 8L, 0L);
                    putI64(outBookValPtr, 0L);
                }
            } else {
                isZero = fLT(lifePtr, periodPtr) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    putU32(outDeprPtr + 8L, 0L);
                    putU32(outDeprPtr + 12L, 0L);
                    putU32(outDeprPtr, 1L);
                    putU32(outDeprPtr + 4L, 0L);
                    if (outRemDepPtr != 0L) {
                        putI64(outRemDepPtr + 8L, 0L);
                        putI64(outRemDepPtr, 1L);
                    }
                    status1 = 0L;
                    if (outBookValPtr != 0L) {
                        status2 = getI64(salvagePtr);
                        putI64(outBookValPtr + 8L, getI64(salvagePtr + 8L));
                        putI64(outBookValPtr, status2);
                    }
                } else {
                    firadd(costPtr, salvagePtr, framePtr + 24L, 1L);
                    fidiv(framePtr + 24L, lifePtr, outDeprPtr);
                    fimul(outDeprPtr, periodPtr, framePtr + 40L);
                    if (outRemDepPtr != 0L) {
                        putI64(framePtr + 56L, getI64(framePtr + 40L));
                        putI64(framePtr + 64L, getI64(framePtr + 48L));
                        if ((long) (int) getI64(framePtr + 40L) == 3L) {
                            putI32(framePtr + 60L, getI64(framePtr + 40L) >>> 32);
                            putI64(framePtr + 56L, (((getI32(framePtr + 60L) & 0xffffffffL)) << 32) | 1L);
                        }
                        isZero = fZero(framePtr + 56L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            putI64(framePtr + 56L, (((getI32(framePtr + 60L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 56L) & 0xffffffffL));
                        }
                        status2 = firadd(framePtr + 24L, framePtr + 56L, outRemDepPtr, 0L);
                        fNorm_L((long) (int) status2, outRemDepPtr);
                        fMakeZeroIfClose(outRemDepPtr, costPtr);
                    }
                    if (outBookValPtr != 0L) {
                        putI64(framePtr + 56L, getI64(framePtr + 40L));
                        putI64(framePtr + 64L, getI64(framePtr + 48L));
                        if ((long) (int) getI64(framePtr + 40L) == 3L) {
                            putI32(framePtr + 60L, getI64(framePtr + 40L) >>> 32);
                            putI64(framePtr + 56L, (((getI32(framePtr + 60L) & 0xffffffffL)) << 32) | 1L);
                        }
                        isZero = fZero(framePtr + 56L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            putI64(framePtr + 56L, (((getI32(framePtr + 60L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 56L) & 0xffffffffL));
                        }
                        status2 = firadd(costPtr, framePtr + 56L, outBookValPtr, 0L);
                        fNorm_L((long) (int) status2, outBookValPtr);
                        fMakeZeroIfClose(outBookValPtr, costPtr);
                    }
                    status1 = (long) (int) fNorm_L(0L, outDeprPtr);
                }
            }
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    long DEPRDBinternal(long costPtr, long salvagePtr, long lifePtr, long periodPtr, long factorPtr, long outDeprPtr, long outRemDepPtr, long outBookValPtr, long isCrossover) {
        long intVal = 0L;
        long exponent = 0L;
        long isZero = 0L;
        long result = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(272);
        try {
            isZero = fLT(costPtr, salvagePtr) != 0L ? 1L : 0L;
            if (isZero != 0L || (long) (int) comma(isZero = fLT(lifePtr, CC_1) != 0L ? 1L : 0L, isZero) != 0L) {
                putI64(outDeprPtr + 8L, 0L);
                putI32(outDeprPtr, 0L);
                putI32(outDeprPtr + 4L, 0L);
                if (outRemDepPtr != 0L) {
                    putI64(outRemDepPtr + 8L, 0L);
                    putI32(outRemDepPtr, 0L);
                    putI32(outRemDepPtr + 4L, 0L);
                }
                if (outBookValPtr == 0L) {
                    result = 24L;
                } else {
                    result = 24L;
                    putI64(outBookValPtr + 8L, 0L);
                    putI32(outBookValPtr, 0L);
                    putI32(outBookValPtr + 4L, 0L);
                }
                return result;
            }
            isZero = fLT(lifePtr, periodPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                putI64(outDeprPtr + 8L, 0L);
                putI32(outDeprPtr, 1L);
                putI32(outDeprPtr + 4L, 0L);
                if (outRemDepPtr != 0L) {
                    putI64(outRemDepPtr + 8L, 0L);
                    putI32(outRemDepPtr, 1L);
                    putI32(outRemDepPtr + 4L, 0L);
                }
                if (outBookValPtr != 0L) {
                    intVal = getI32(salvagePtr);
                    exponent = getI32(salvagePtr + 4L);
                    result = 0L;
                    putI64(outBookValPtr + 8L, getI64(salvagePtr + 8L));
                    putI32(outBookValPtr, intVal);
                    putI32(outBookValPtr + 4L, exponent);
                    return result;
                }
                result = 0L;
                return result;
            }
            putI64(framePtr + 72L, getI64(factorPtr + 8L));
            putI32(framePtr + 64L, getI32(factorPtr));
            putI32(framePtr + 68L, getI32(factorPtr + 4L));
            if (((getI64(framePtr + 64L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 64L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 64L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
                intVal = getI32(framePtr + 68L);
                putI64(framePtr + 64L, ((((getI32(framePtr + 68L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 64L) & 0xffffffffL));
                if (intVal < -49997L) {
                    putI64(framePtr + 72L, 0L);
                    putI64(framePtr + 64L, 1L);
                }
            }
            fidiv(framePtr + 64L, lifePtr, framePtr + 64L);
            isZero = fLT(CC_1, framePtr + 64L) != 0L ? 1L : 0L;
            if (isZero != 0L && (long) (int) comma(isZero = fLE(CC_2, periodPtr) != 0L ? 1L : 0L, isZero) != 0L) {
                putI64(outDeprPtr + 8L, 0L);
                putI32(outDeprPtr, 1L);
                putI32(outDeprPtr + 4L, 0L);
            } else {
                fimul(framePtr + 64L, costPtr, framePtr + 48L);
                firadd(CC_1, framePtr + 64L, framePtr + 64L, 1L);
                firadd(periodPtr, CC_N1, framePtr + 32L, 0L);
                isZero = fEQ(periodPtr, lifePtr) != 0L ? 1L : 0L;
                if (isZero == 0L || (isCrossover & 0xffL) != 0L) {
                    DEPRDBStep(framePtr + 48L, framePtr + 64L, framePtr + 32L, costPtr, salvagePtr, framePtr + 16L);
                    DEPRDBStep(framePtr + 48L, framePtr + 64L, periodPtr, costPtr, salvagePtr, outDeprPtr);
                    if (outRemDepPtr != 0L) {
                        intVal = getI32(outDeprPtr);
                        exponent = getI32(outDeprPtr + 4L);
                        putI64(outRemDepPtr + 8L, getI64(outDeprPtr + 8L));
                        putI32(outRemDepPtr, intVal);
                        putI32(outRemDepPtr + 4L, exponent);
                    }
                    if (outBookValPtr != 0L) {
                        status = firadd(outDeprPtr, salvagePtr, outBookValPtr, 0L);
                        fNorm_L((long) (int) status, outBookValPtr);
                    }
                    putI64(framePtr + 96L, getI64(outDeprPtr + 8L));
                    putI32(framePtr + 88L, getI32(outDeprPtr));
                    putI32(framePtr + 92L, getI32(outDeprPtr + 4L));
                    if ((getI64(framePtr + 88L) & 0xffffffffL) == 3L) {
                        putI64(framePtr + 88L, (((getI32(framePtr + 92L) & 0xffffffffL)) << 32) | 1L);
                    }
                    isZero = fZero(framePtr + 88L) != 0L ? 1L : 0L;
                    if (isZero == 0L) {
                        putI64(framePtr + 88L, (((getI32(framePtr + 92L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 88L) & 0xffffffffL));
                    }
                    status = firadd(framePtr + 16L, framePtr + 88L, outDeprPtr, 0L);
                    fNorm_L((long) (int) status, outDeprPtr);
                    result = 0L;
                    return result;
                }
                DEPRDBStep(framePtr + 48L, framePtr + 64L, framePtr + 32L, costPtr, salvagePtr, outDeprPtr);
            }
            if (outRemDepPtr != 0L) {
                putI64(outRemDepPtr + 8L, 0L);
                putI32(outRemDepPtr, 1L);
                putI32(outRemDepPtr + 4L, 0L);
            }
            if (outBookValPtr != 0L) {
                intVal = getI32(salvagePtr);
                exponent = getI32(salvagePtr + 4L);
                putI64(outBookValPtr + 8L, getI64(salvagePtr + 8L));
                putI32(outBookValPtr, intVal);
                putI32(outBookValPtr + 4L, exponent);
            }
            result = 0L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long DEPRDBStep(long bookValPtr, long salvagePtr, long lifePtr, long factorPtr, long monthWeightPtr, long outDeprPtr) {
        long isZero = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            fpow(salvagePtr, lifePtr, framePtr + 24L, 1L);
            firadd(framePtr + 24L, CC_N1, framePtr + 24L, 0L);
            firadd(salvagePtr, CC_N1, outDeprPtr, 0L);
            fidiv(framePtr + 24L, outDeprPtr, outDeprPtr);
            fimul(bookValPtr, outDeprPtr, outDeprPtr);
            firadd(factorPtr, outDeprPtr, outDeprPtr, 1L);
            putI64(framePtr + 48L, getI64(monthWeightPtr + 8L));
            putI64(framePtr + 40L, getI64(monthWeightPtr));
            if ((long) (int) getI64(framePtr + 40L) == 3L) {
                putI32(framePtr + 44L, getI64(framePtr + 40L) >>> 32);
                putI64(framePtr + 40L, (((getI32(framePtr + 44L) & 0xffffffffL)) << 32) | 1L);
            }
            isZero = fZero(framePtr + 40L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI64(framePtr + 40L, (((getI32(framePtr + 44L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 40L) & 0xffffffffL));
            }
            status = firadd(outDeprPtr, framePtr + 40L, outDeprPtr, 0L);
            fNorm_L((long) (int) status, outDeprPtr);
            fMakeZeroIfClose(outDeprPtr, factorPtr);
            if ((long) (int) getI64(outDeprPtr) < 0L) {
                putI64(outDeprPtr + 8L, 0L);
                putI64(outDeprPtr, 1L);
            }
            return 0L;
        } finally {
            sp = savedSp;
        }
    }

    long DEPRDB_nf(long costPtr, long salvagePtr, long lifePtr, long periodPtr, long factorPtr, long outDeprPtr, long outRemDepPtr, long outBookValPtr) {
        long status = 0L;
        status = DEPRDBinternal(costPtr, salvagePtr, lifePtr, periodPtr, factorPtr, outDeprPtr, outRemDepPtr, outBookValPtr, 0L);
        return status;
    }

    long DEPRDB(long costPtr, long salvagePtr, long lifePtr, long periodPtr, long monthPtr, long factorPtr, long outDeprPtr, long outRemDepPtr, long outBookValPtr) {
        long intVal = 0L;
        long exponent = 0L;
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(304);
        try {
            isZero = fEQ(factorPtr, CC_1) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                status1 = DEPRDBinternal(costPtr, salvagePtr, lifePtr, periodPtr, monthPtr, outDeprPtr, outRemDepPtr, outBookValPtr, 0L);
            } else {
                isZero = fLT(lifePtr, periodPtr) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    firadd(periodPtr, CC_N1, framePtr + 112L, 0L);
                    status1 = DEPRDB(costPtr, salvagePtr, lifePtr, framePtr + 112L, monthPtr, factorPtr, framePtr + 96L, outDeprPtr, 0L);
                    if ((status1 & 0xffffffffL) >= 2L) {
                        return status1;
                    }
                    if (outBookValPtr != 0L) {
                        intVal = getI32(salvagePtr);
                        exponent = getI32(salvagePtr + 4L);
                        putI64(outBookValPtr + 8L, getI64(salvagePtr + 8L));
                        putI32(outBookValPtr, intVal);
                        putI32(outBookValPtr + 4L, exponent);
                    }
                    status1 = 0L;
                    if (outRemDepPtr != 0L) {
                        putI64(outRemDepPtr + 8L, 0L);
                        putI32(outRemDepPtr, 1L);
                        putI32(outRemDepPtr + 4L, 0L);
                    }
                } else {
                    status1 = DEPRDBinternal(costPtr, salvagePtr, lifePtr, periodPtr, monthPtr, outDeprPtr, outRemDepPtr, outBookValPtr, 1L);
                    if ((status1 & 0xffffffffL) >= 2L) {
                        return status1;
                    }
                    firadd(periodPtr, CC_N1, framePtr + 80L, 0L);
                    isZero = fZero(framePtr + 80L) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        putI64(framePtr + 120L, 0L);
                        putI32(framePtr + 112L, 1L);
                        putI32(framePtr + 116L, 0L);
                        firadd(costPtr, salvagePtr, framePtr + 96L, 1L);
                        putI64(framePtr + 72L, getI64(costPtr + 8L));
                        putI32(framePtr + 64L, getI32(costPtr));
                        putI32(framePtr + 68L, getI32(costPtr + 4L));
                    } else {
                        status1 = DEPRDBinternal(costPtr, salvagePtr, lifePtr, framePtr + 80L, monthPtr, framePtr + 112L, framePtr + 96L, framePtr + 64L, 0L);
                        if (1L < (status1 & 0xffffffffL)) {
                            return status1;
                        }
                    }
                    firadd(factorPtr, CC_N1, framePtr + 32L, 0L);
                    fidiv(framePtr + 32L, CC_12, framePtr + 32L);
                    firadd(CC_1, framePtr + 32L, framePtr + 16L, 1L);
                    fimul(outDeprPtr, framePtr + 16L, outDeprPtr);
                    fimul(framePtr + 112L, framePtr + 32L, framePtr + 48L);
                    status2 = firadd(outDeprPtr, framePtr + 48L, framePtr + 48L, 0L);
                    fNorm_L((long) (int) status2, framePtr + 48L);
                    status1 = 0L;
                    putI64(outDeprPtr + 8L, getI64(framePtr + 56L));
                    putI32(outDeprPtr, getI64(framePtr + 48L));
                    putI32(outDeprPtr + 4L, getI32(framePtr + 52L));
                }
            }
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    long DEPRDBX(long costPtr, long salvagePtr, long lifePtr, long periodPtr, long monthPtr, long factorPtr, long outDeprPtr, long outRemDepPtr, long outBookValPtr) {
        long isZero = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(320);
        try {
            isZero = fEQ(factorPtr, CC_1) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                status = DEPRDBX_nf(costPtr, salvagePtr, lifePtr, periodPtr, monthPtr, outDeprPtr, outRemDepPtr, outBookValPtr);
            } else {
                isZero = fLT(lifePtr, periodPtr) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    firadd(periodPtr, CC_N1, framePtr + 136L, 0L);
                    status = DEPRDBX(costPtr, salvagePtr, lifePtr, framePtr + 136L, monthPtr, factorPtr, framePtr + 112L, outDeprPtr, 0L);
                    if ((status & 0xffffffffL) >= 2L) {
                        return status;
                    }
                    if (outBookValPtr != 0L) {
                        status = getI64(salvagePtr);
                        putI64(outBookValPtr + 8L, getI64(salvagePtr + 8L));
                        putI64(outBookValPtr, status);
                    }
                    status = 0L;
                    if (outRemDepPtr != 0L) {
                        putI64(outRemDepPtr + 8L, 0L);
                        putI64(outRemDepPtr, 1L);
                    }
                } else {
                    status = DEPRDBX_nf(costPtr, salvagePtr, lifePtr, periodPtr, monthPtr, outDeprPtr, outRemDepPtr, outBookValPtr);
                    if ((status & 0xffffffffL) >= 2L) {
                        return status;
                    }
                    firadd(periodPtr, CC_N1, framePtr + 80L, 0L);
                    isZero = fZero(framePtr + 80L) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        putI64(framePtr + 120L, 0L);
                        putI32(framePtr + 112L, 1L);
                        putI32(framePtr + 116L, 0L);
                        firadd(costPtr, salvagePtr, framePtr + 96L, 1L);
                        putI64(framePtr + 72L, getI64(costPtr + 8L));
                        putI64(framePtr + 64L, getI64(costPtr));
                    } else {
                        status = DEPRDBX_nf(costPtr, salvagePtr, lifePtr, framePtr + 80L, monthPtr, framePtr + 112L, framePtr + 96L, framePtr + 64L);
                        if (1L < (status & 0xffffffffL)) {
                            return status;
                        }
                    }
                    firadd(factorPtr, CC_N1, framePtr + 32L, 0L);
                    fidiv(framePtr + 32L, CC_12, framePtr + 32L);
                    firadd(CC_1, framePtr + 32L, framePtr + 16L, 1L);
                    fimul(outDeprPtr, framePtr + 16L, outDeprPtr);
                    fimul(framePtr + 112L, framePtr + 32L, framePtr + 48L);
                    status = firadd(outDeprPtr, framePtr + 48L, framePtr + 48L, 0L);
                    fNorm_L((long) (int) status, framePtr + 48L);
                    if (outRemDepPtr != 0L) {
                        putI64(framePtr + 144L, getI64(outDeprPtr + 8L));
                        putI64(framePtr + 136L, getI64(outDeprPtr));
                        if ((long) (int) getI64(framePtr + 136L) == 3L) {
                            putI32(framePtr + 140L, getI64(framePtr + 136L) >>> 32);
                            putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | 1L);
                        }
                        isZero = fZero(framePtr + 136L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 136L) & 0xffffffffL));
                        }
                        status = firadd(framePtr + 96L, framePtr + 136L, outRemDepPtr, 0L);
                        fNorm_L((long) (int) status, outRemDepPtr);
                        fMakeZeroIfClose(outRemDepPtr, costPtr);
                    }
                    if (outBookValPtr != 0L) {
                        putI64(framePtr + 144L, getI64(outDeprPtr + 8L));
                        putI64(framePtr + 136L, getI64(outDeprPtr));
                        if ((long) (int) getI64(framePtr + 136L) == 3L) {
                            putI32(framePtr + 140L, getI64(framePtr + 136L) >>> 32);
                            putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | 1L);
                        }
                        isZero = fZero(framePtr + 136L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 136L) & 0xffffffffL));
                        }
                        status = firadd(framePtr + 64L, framePtr + 136L, outBookValPtr, 0L);
                        fNorm_L((long) (int) status, outBookValPtr);
                        fMakeZeroIfClose(outBookValPtr, costPtr);
                    }
                    status = 0L;
                    putI64(outDeprPtr + 8L, getI64(framePtr + 56L));
                    putI64(outDeprPtr, getI64(framePtr + 48L));
                }
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long DEPRDBX_nf(long costPtr, long salvagePtr, long lifePtr, long periodPtr, long factorPtr, long outDeprPtr, long outRemDepPtr, long outBookValPtr) {
        long isEqual = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(288);
        try {
            putI64(framePtr + 88L, getI64(costPtr + 8L));
            putI32(framePtr + 80L, getI32(costPtr));
            putI32(framePtr + 84L, getI32(costPtr + 4L));
            putI64(framePtr + 104L, 0x100000000000000L);
            putI32(framePtr + 96L, 1L);
            putI32(framePtr + 100L, 0L);
            putI64(framePtr + 24L, getI64(lifePtr + 8L));
            putI32(framePtr + 16L, getI32(lifePtr));
            putI32(framePtr + 20L, getI32(lifePtr + 4L));
            status2 = DEPRDBinternal(costPtr, salvagePtr, lifePtr, framePtr + 96L, factorPtr, outDeprPtr, framePtr + 64L, framePtr + 48L, 0L);
            status1 = status2 & 0xffffffffL;
            while (true) {
                if (1L < status1) {
                    return status2;
                }
                status1 = DEPRSL_nf(framePtr + 80L, salvagePtr, framePtr + 16L, CC_1, framePtr + 32L, 0L, 0L) & 0xffffffffL;
                status2 = status1;
                if (1L < status1) {
                    return status2;
                }
                isEqual = fLT(outDeprPtr, framePtr + 32L) != 0L ? 1L : 0L;
                if (isEqual != 0L) {
                    firadd(periodPtr, framePtr + 96L, framePtr + 96L, 1L);
                    firadd(framePtr + 96L, CC_1, framePtr + 96L, 0L);
                    status1 = DEPRSL_nf(framePtr + 80L, salvagePtr, framePtr + 16L, framePtr + 96L, outDeprPtr, outRemDepPtr, outBookValPtr) & 0xffffffffL;
                    status2 = status1;
                    return status2;
                }
                fMakeZeroIfClose(framePtr + 64L, costPtr);
                if (outBookValPtr != 0L) {
                    putI64(outBookValPtr + 8L, getI64(framePtr + 56L));
                    putI64(outBookValPtr, getI64(framePtr + 48L));
                }
                if (outRemDepPtr != 0L) {
                    putI64(outRemDepPtr + 8L, getI64(framePtr + 72L));
                    putI64(outRemDepPtr, getI64(framePtr + 64L));
                }
                isEqual = fEQ(framePtr + 96L, periodPtr) != 0L ? 1L : 0L;
                if (isEqual != 0L || (long) (int) comma(isEqual = fLT(lifePtr, framePtr + 96L) != 0L ? 1L : 0L, isEqual) != 0L) {
                    break;
                }
                putI64(framePtr + 88L, getI64(framePtr + 56L));
                putI32(framePtr + 80L, getI32(framePtr + 48L));
                putI32(framePtr + 84L, getI32(framePtr + 52L));
                firadd(framePtr + 96L, CC_1, framePtr + 96L, 0L);
                firadd(framePtr + 16L, CC_N1, framePtr + 16L, 0L);
                status2 = DEPRDBinternal(costPtr, salvagePtr, lifePtr, framePtr + 96L, factorPtr, outDeprPtr, framePtr + 64L, framePtr + 48L, 0L);
                status1 = status2 & 0xffffffffL;
            }
            status2 = 0L;
            return status2;
        } finally {
            sp = savedSp;
        }
    }

    long DEPRSLF(long costPtr, long salvagePtr, long lifePtr, long periodPtr, long monthPtr, long ratePtr, long outDeprPtr, long outRemDepPtr, long outBookValPtr) {
        long intVal = 0L;
        long status = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            putI64(ratePtr + 8L, 0L);
            putI64(ratePtr, 0L);
            if (outRemDepPtr != 0L) {
                putI64(outRemDepPtr + 8L, 0L);
                putI64(outRemDepPtr, 0L);
            }
            if (outDeprPtr != 0L) {
                putI64(outDeprPtr + 8L, 0L);
                putI64(outDeprPtr, 0L);
            }
            tempVal = DateValidExtract(monthPtr, framePtr + 52L, framePtr + 48L, framePtr + 44L, framePtr + 40L, outBookValPtr & 1L, 0L);
            if ((tempVal & 1L) == 0L) {
                status = 24L;
            } else {
                if (30L < getU32(framePtr + 52L)) {
                    putU32(framePtr + 52L, 30L);
                }
                intVal = (long) (int) (getU32(framePtr + 52L) + getU32(framePtr + 48L) * 30L);
                putU32(framePtr + 52L, 391L - intVal);
                fIntToCC((long) (int) ((31L - intVal) * -12L + 360L), framePtr + 24L);
                fidiv(framePtr + 24L, CC_360, framePtr + 24L);
                status = DEPRSL(costPtr, salvagePtr, lifePtr, periodPtr, framePtr + 24L, ratePtr, outDeprPtr, outRemDepPtr) & 0xffffffffL;
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long DEPRADF(long costPtr, long salvagePtr, long lifePtr, long periodPtr, long monthPtr, long factorPtr, long outDeprPtr, long outRemDepPtr, long outBookValPtr) {
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long constPtr = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(352);
        try {
            putI64(factorPtr + 8L, 0L);
            putI64(factorPtr, 0L);
            if (outRemDepPtr != 0L) {
                putI64(outRemDepPtr + 8L, 0L);
                putI64(outRemDepPtr, 0L);
            }
            if (outDeprPtr != 0L) {
                putI64(outDeprPtr + 8L, 0L);
                putI64(outDeprPtr, 0L);
            }
            status2 = DateValidExtract(monthPtr, framePtr + 156L, framePtr + 152L, framePtr + 148L, framePtr + 144L, outBookValPtr & 1L, 0L);
            if ((status2 & 1L) == 0L || comma(isZero = fiInteger(periodPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L || (long) (int) getU32(periodPtr) < 0L || comma(isZero = fLT(lifePtr, CC_1) != 0L ? 1L : 0L, isZero != 0L || comma(isZero = fiInteger(lifePtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
                status1 = 24L;
                return status1;
            }
            isZero = fLT(lifePtr, CC_3) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                status1 = DEPRSLF(costPtr, salvagePtr, lifePtr, periodPtr, monthPtr, factorPtr, outDeprPtr, outRemDepPtr, outBookValPtr & 1L) & 0xffffffffL;
                return status1;
            }
            isZero = fLT(lifePtr, periodPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                putI64(factorPtr + 8L, 0L);
                putI64(factorPtr, 1L);
                if (outRemDepPtr != 0L) {
                    status2 = getI64(salvagePtr);
                    putI64(outRemDepPtr + 8L, getI64(salvagePtr + 8L));
                    putI64(outRemDepPtr, status2);
                }
                if (outDeprPtr != 0L) {
                    status1 = 0L;
                    putI64(outDeprPtr + 8L, 0L);
                    putI64(outDeprPtr, 1L);
                    return status1;
                }
            } else {
                isZero = fLE(lifePtr, CC_4) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    constPtr = ADDR_RODATA + 2048L;
                } else {
                    isZero = fLE(lifePtr, CC_6) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        constPtr = CC_2;
                    } else {
                        constPtr = ADDR_RODATA + 2128L;
                    }
                }
                putI64(framePtr + 120L, getI64(constPtr + 8L));
                putI64(framePtr + 112L, getI64(constPtr));
                fidiv(framePtr + 112L, lifePtr, framePtr + 128L);
                firadd(costPtr, salvagePtr, framePtr + 96L, 1L);
                putU32(framePtr + 156L, getU32(framePtr + 152L) * 0xffffffe2L + 390L);
                fIntToCC((long) (int) getU32(framePtr + 156L), framePtr + 80L);
                fidiv(framePtr + 80L, CC_360, framePtr + 80L);
                fimul(framePtr + 96L, framePtr + 128L, framePtr + 64L);
                fimul(framePtr + 64L, framePtr + 80L, factorPtr);
                isZero = fLT(CC_1, periodPtr) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    firadd(framePtr + 96L, factorPtr, framePtr + 96L, 1L);
                    fidiv(lifePtr, framePtr + 112L, framePtr + 160L);
                    firadd(lifePtr, framePtr + 160L, framePtr + 160L, 1L);
                    firadd(framePtr + 160L, CC_1, framePtr + 160L, 0L);
                    ficeil(framePtr + 160L, framePtr + 160L);
                    isZero = fLT(periodPtr, framePtr + 160L) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        firadd(periodPtr, CC_N2, framePtr + 32L, 0L);
                        firadd(CC_1, framePtr + 128L, framePtr + 48L, 1L);
                        fpow(framePtr + 48L, framePtr + 32L, framePtr + 48L, 1L);
                        fimul(framePtr + 96L, framePtr + 48L, framePtr + 96L);
                        fimul(framePtr + 96L, framePtr + 128L, factorPtr);
                    } else {
                        firadd(framePtr + 160L, CC_N2, framePtr + 32L, 0L);
                        firadd(CC_1, framePtr + 128L, framePtr + 48L, 1L);
                        fpow(framePtr + 48L, framePtr + 32L, framePtr + 48L, 1L);
                        fimul(framePtr + 96L, framePtr + 48L, framePtr + 96L);
                        firadd(lifePtr, framePtr + 160L, framePtr + 16L, 1L);
                        firadd(framePtr + 16L, CC_1, framePtr + 16L, 0L);
                        fidiv(CC_1, framePtr + 16L, framePtr + 16L);
                        fimul(framePtr + 96L, framePtr + 16L, factorPtr);
                        firadd(periodPtr, framePtr + 160L, framePtr + 160L, 1L);
                        fimul(framePtr + 160L, factorPtr, framePtr + 160L);
                        firadd(framePtr + 96L, framePtr + 160L, framePtr + 96L, 1L);
                    }
                }
                fNorm_L(0L, factorPtr);
                fMakeZeroIfClose(factorPtr, costPtr);
                putI64(framePtr + 168L, getI64(factorPtr + 8L));
                putI64(framePtr + 160L, getI64(factorPtr));
                if ((long) (int) getI64(framePtr + 160L) == 3L) {
                    putI32(framePtr + 164L, getI64(framePtr + 160L) >>> 32);
                    putI64(framePtr + 160L, (((getI32(framePtr + 164L) & 0xffffffffL)) << 32) | 1L);
                }
                isZero = fZero(framePtr + 160L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putI64(framePtr + 160L, (((getI32(framePtr + 164L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 160L) & 0xffffffffL));
                }
                status2 = firadd(framePtr + 96L, framePtr + 160L, framePtr + 96L, 0L);
                fNorm_L((long) (int) status2, framePtr + 96L);
                fMakeZeroIfClose(framePtr + 96L, costPtr);
                if (outDeprPtr != 0L) {
                    putI64(outDeprPtr + 8L, getI64(framePtr + 104L));
                    putI64(outDeprPtr, getI64(framePtr + 96L));
                }
                if (outRemDepPtr != 0L) {
                    status2 = firadd(outDeprPtr, salvagePtr, outRemDepPtr, 0L);
                    fNorm_L((long) (int) status2, outRemDepPtr);
                }
            }
            status1 = 0L;
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    long BONDPrice(long buyDatePtr, long matDatePtr, long outPricePtr, long yieldPtr, long couponPtr, long callValPtr, long isAnnualCoupon, long is360DayCalendar, long isDmyDate) {
        long constPtr = 0L;
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(288);
        try {
            putI64(outPricePtr + 8L, 0L);
            putI64(outPricePtr, 0L);
            if ((isAnnualCoupon & 1L) == 0L) {
                fidiv2(couponPtr, framePtr + 64L);
            } else {
                putI64(framePtr + 72L, getI64(couponPtr + 8L));
                putI64(framePtr + 64L, getI64(couponPtr));
            }
            status1 = BONDCountDays(buyDatePtr, matDatePtr, isAnnualCoupon & 1L, is360DayCalendar & 1L, isDmyDate & 1L, framePtr + 104L, framePtr + 88L);
            if ((status1 & 0xffffffffL) >= 2L) {
                return status1;
            }
            constPtr = CC_1;
            if ((isAnnualCoupon & 1L) == 0L) {
                constPtr = CC_2;
            }
            status1 = TVMPVInternal(framePtr + 104L, yieldPtr, framePtr + 48L, framePtr + 64L, callValPtr, constPtr, constPtr, 0L);
            if ((status1 & 0xffffffffL) >= 2L) {
                return status1;
            }
            firadd(framePtr + 64L, framePtr + 48L, framePtr + 48L, 1L);
            putI64(framePtr + 24L, getI64(yieldPtr + 8L));
            putI64(framePtr + 16L, getI64(yieldPtr));
            isZero = fZero(framePtr + 16L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI64(framePtr + 16L, ((((getI32(framePtr + 20L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                fidiv(framePtr + 16L, constPtr, framePtr + 16L);
            }
            if ((long) (int) getI64(framePtr + 16L) < 0L && Long.compareUnsigned(-1L, getI64(framePtr + 16L)) < 0L) {
                status1 = 25L;
            } else {
                isZero = fLT(framePtr + 104L, CC_1) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    fimul(framePtr + 16L, framePtr + 88L, framePtr + 32L);
                    firadd(framePtr + 32L, CC_1, framePtr + 32L, 0L);
                } else {
                    filnp1(framePtr + 16L, framePtr + 32L);
                    fimul(framePtr + 32L, framePtr + 88L, framePtr + 32L);
                    fiexpm(framePtr + 32L, framePtr + 32L, 0L);
                }
                status1 = fidiv(framePtr + 48L, framePtr + 32L, framePtr + 48L);
                if ((status1 & 0xffffffffL) >= 2L) {
                    return status1;
                }
                firadd(CC_1, framePtr + 88L, framePtr + 88L, 1L);
                fimul(framePtr + 88L, framePtr + 64L, framePtr + 88L);
                isZero = fZero(framePtr + 88L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putI32(framePtr + 88L, -getI32(framePtr + 88L));
                }
                status2 = firadd(framePtr + 48L, framePtr + 88L, outPricePtr, 0L);
                fNorm_L((long) (int) status2, outPricePtr);
                status1 = 0L;
            }
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    long BONDCountDays(long buyDatePtr, long matDatePtr, long isAnnualCoupon, long is360DayCalendar, long isDmyDate, long outPeriodsPtr, long outAccruedFracPtr) {
        long intVal1 = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long isLeapYear = 0L;
        long dayIndex1 = 0L;
        long dayIndex2 = 0L;
        long dayIndex3 = 0L;
        long tempVal3 = 0L;
        long result = 0L;
        long intVal2 = 0L;
        long tempVal4 = 0L;
        long intVal3 = 0L;
        long index = 0L;
        long tempVal5 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(240);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        tempVal3 = DateValidExtract(buyDatePtr, framePtr + 56L, framePtr + 52L, framePtr + 48L, framePtr + 44L, is360DayCalendar & 1L, isDmyDate & 1L);
                        dayIndex2 = getU32(framePtr + 56L);
                        tempVal4 = getU32(framePtr + 52L);
                        dayIndex3 = getU32(framePtr + 48L);
                        if ((tempVal3 & 1L) == 0L || comma(comma(tempVal3 = DateValidExtract(matDatePtr, framePtr + 68L, framePtr + 64L, framePtr + 60L, framePtr + 40L, is360DayCalendar & 1L, isDmyDate & 1L), dayIndex1 = getU32(framePtr + 68L)), (tempVal3 & 1L) == 0L ? 1L : 0L) != 0L || comma(dayIndex2 = (tempVal4 * 256L + dayIndex3 * 0x10000L + dayIndex2) & 0xffffffffL, ((getU32(framePtr + 68L) + getU32(framePtr + 60L) * 0x10000L + getU32(framePtr + 64L) * 256L) & 0xffffffffL) < dayIndex2 ? 1L : 0L) != 0L) {
                            result = 24L;
                            return result;
                        }
                        if (getU32(framePtr + 64L) == 2L && (long) (int) comma(isLeapYear = DateLeap(getU32(framePtr + 60L)) != 0L ? 1L : 0L, isLeapYear) != 0L) {
                            dayIndex3 = 29L;
                        } else {
                            dayIndex3 = getU8(ADDR_RODATA + 5584L + ((getU32(framePtr + 64L) - 1L) & 0xffffffffL));
                        }
                        index = -1L;
                        tempVal4 = dayIndex1;
                        step = 1;
                        continue stepLoop;
                    }
                    case 1: {
                        tempVal5 = tempVal4;
                        tempVal2 = getU32(framePtr + 64L);
                        tempVal1 = getU32(framePtr + 60L);
                        if ((isAnnualCoupon & 1L) == 0L) {
                            if (getU32(framePtr + 64L) < 7L) {
                                putU32(framePtr + 64L, getU32(framePtr + 64L) + 6L);
                                step = 3;
                                continue stepLoop;
                            } else {
                                putU32(framePtr + 64L, getU32(framePtr + 64L) - 6L);
                                if (dayIndex1 != dayIndex3) {
                                    step = 4;
                                    continue stepLoop;
                                } else {
                                    step = 5;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            step = 3;
                            continue stepLoop;
                        }
                    }
                    case 2: {
                        index = (long) (int) (index + 1L);
                        if (dayIndex2 >= ((tempVal4 + getU32(framePtr + 60L) * 0x10000L + getU32(framePtr + 64L) * 256L) & 0xffffffffL)) {
                            if ((isDmyDate & 1L) == 0L) {
                                isLeapYear = DateLeap(tempVal1) != 0L ? 1L : 0L;
                                dayIndex1 = DateDayIndexFromDateinternal(tempVal5, tempVal2, (long) (int) tempVal1, isLeapYear, 0L) & 0xffffffffL;
                                dayIndex3 = getU32(framePtr + 64L);
                                dayIndex2 = getU32(framePtr + 60L);
                                isLeapYear = DateLeap(getU32(framePtr + 60L)) != 0L ? 1L : 0L;
                                dayIndex2 = DateDayIndexFromDateinternal(tempVal4, dayIndex3, (long) (int) dayIndex2, isLeapYear, 0L) & 0xffffffffL;
                                intVal2 = (long) (int) (dayIndex1 - dayIndex2);
                            } else {
                                intVal2 = 360L;
                                if ((isAnnualCoupon & 1L) == 0L) {
                                    intVal2 = 180L;
                                }
                            }
                            dayIndex3 = getU32(framePtr + 64L);
                            dayIndex2 = getU32(framePtr + 60L);
                            isLeapYear = DateLeap(getU32(framePtr + 60L)) != 0L ? 1L : 0L;
                            dayIndex2 = DateDayIndexFromDateinternal(tempVal4, dayIndex3, (long) (int) dayIndex2, isLeapYear, isDmyDate & 1L) & 0xffffffffL;
                            dayIndex3 = DateDayIndexFromDateinternal(getU32(framePtr + 56L), getU32(framePtr + 52L), (long) (int) getU32(framePtr + 48L), getU8(framePtr + 44L), isDmyDate & 1L) & 0xffffffffL;
                            intVal1 = (long) (int) (dayIndex3 - dayIndex2);
                            intVal3 = intVal1;
                            if (1L < intVal1 && (isDmyDate & 1L) != 0L) {
                                if (tempVal4 == 29L && getU32(framePtr + 64L) == 2L) {
                                    intVal3 = (long) (int) (intVal1 - 1L);
                                } else {
                                    intVal3 = (long) (int) (intVal1 - 2L);
                                    if (getU32(framePtr + 64L) != 2L || tempVal4 != 28L) {
                                        intVal3 = intVal1;
                                    }
                                }
                            }
                            fIntToCC(intVal2, framePtr + 24L);
                            fIntToCC((long) (int) (intVal2 - intVal3), outAccruedFracPtr);
                            fidiv(outAccruedFracPtr, framePtr + 24L, outAccruedFracPtr);
                            fIntToCC(index, outPeriodsPtr);
                            result = 0L;
                            return result;
                        }
                        step = 1;
                        continue stepLoop;
                    }
                    case 3: {
                        putU32(framePtr + 60L, getU32(framePtr + 60L) - 1L);
                        if (dayIndex1 == dayIndex3) {
                            step = 5;
                            continue stepLoop;
                        } else {
                            step = 4;
                            continue stepLoop;
                        }
                    }
                    case 4: {
                        tempVal4 = getU32(framePtr + 68L);
                        if (getU32(framePtr + 64L) == 2L) {
                            isLeapYear = DateLeap(getU32(framePtr + 60L)) != 0L ? 1L : 0L;
                            tempVal4 = 28L;
                            if (isLeapYear != 0L) {
                                tempVal4 = 29L;
                            }
                            if (getU32(framePtr + 68L) <= tempVal4) {
                                tempVal4 = getU32(framePtr + 68L);
                            }
                        }
                        step = 2;
                        continue stepLoop;
                    }
                    case 5: {
                        if (getU32(framePtr + 64L) == 2L && (long) (int) comma(isLeapYear = DateLeap(getU32(framePtr + 60L)) != 0L ? 1L : 0L, isLeapYear) != 0L) {
                            tempVal4 = 29L;
                        } else {
                            tempVal4 = getU8(ADDR_RODATA + 5584L + ((getU32(framePtr + 64L) - 1L) & 0xffffffffL));
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

    long BONDAccrued(long buyDatePtr, long matDatePtr, long outAccruedPtr, long yieldPtr, long couponPtr, long callValPtr, long isAnnualCoupon, long is360DayCalendar, long isDmyDate) {
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            putI32(callValPtr + 8L, 0L);
            putI32(callValPtr + 12L, 0L);
            putI32(callValPtr, 0L);
            putI32(callValPtr + 4L, 0L);
            if ((isAnnualCoupon & 1L) == 0L) {
                fidiv2(yieldPtr, framePtr + 16L);
            } else {
                putI64(framePtr + 24L, getI64(yieldPtr + 8L));
                putI64(framePtr + 16L, getI64(yieldPtr));
            }
            status1 = BONDCountDays(buyDatePtr, matDatePtr, isAnnualCoupon & 1L, is360DayCalendar & 1L, isDmyDate & 1L, framePtr + 56L, framePtr + 40L);
            if ((status1 & 0xffffffffL) < 2L) {
                firadd(CC_1, framePtr + 40L, framePtr + 40L, 1L);
                status2 = fimul(framePtr + 40L, framePtr + 16L, callValPtr);
                fNorm_L((long) (int) status2, callValPtr);
                status1 = 0L;
            }
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    long BONDYtm(long buyDatePtr, long matDatePtr, long pricePtr, long outYieldPtr, long couponPtr, long callValPtr, long isAnnualCoupon, long is360DayCalendar, long isDmyDate, long userStopFn) {
        long constPtr = 0L;
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long status3 = 0L;
        long status4 = 0L;
        long intVal = 0L;
        long result = 0L;
        long exponent = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(512);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        putI64(framePtr + 56L, getI64(outYieldPtr + 8L));
                        putI64(framePtr + 48L, getI64(outYieldPtr));
                        putI64(outYieldPtr + 8L, 0L);
                        putI64(outYieldPtr, 0L);
                        if ((isAnnualCoupon & 1L) == 0L) {
                            fidiv2(couponPtr, framePtr + 80L);
                        } else {
                            putI64(framePtr + 88L, getI64(couponPtr + 8L));
                            putI64(framePtr + 80L, getI64(couponPtr));
                        }
                        status2 = BONDCountDays(buyDatePtr, matDatePtr, isAnnualCoupon & 1L, is360DayCalendar & 1L, isDmyDate & 1L, framePtr + 112L, framePtr + 96L);
                        result = status2 & 0xffffffffL;
                        if (1L < (status2 & 0xffffffffL)) {
                            return result;
                        }
                        if ((long) (int) getU32(pricePtr) < 0L) {
                            result = 24L;
                            return result;
                        }
                        firadd(CC_1, framePtr + 96L, framePtr + 64L, 1L);
                        fimul(framePtr + 64L, framePtr + 80L, framePtr + 64L);
                        firadd(pricePtr, framePtr + 64L, framePtr + 64L, 0L);
                        isZero = fZero(framePtr + 64L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            putI32(framePtr + 64L, -getI64(framePtr + 64L));
                        }
                        isZero = fZero(framePtr + 112L) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            firadd(framePtr + 80L, callValPtr, framePtr + 112L, 0L);
                            status3 = fidiv(framePtr + 112L, framePtr + 64L, framePtr + 48L);
                            if ((long) (int) status3 != 4L) {
                                result = 26L;
                                if (4L < ((getI64(framePtr + 48L) + 1L) & 0xffffffffL) || ((1L << (int) ((getI64(framePtr + 48L) + 1L) & 31L)) & 21L) == 0L) {
                                    return result;
                                }
                                isZero = fZero(framePtr + 48L) != 0L ? 1L : 0L;
                                if (isZero == 0L) {
                                    putI64(framePtr + 48L, (((getI32(framePtr + 52L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 48L) & 0xffffffffL));
                                }
                                firadd(framePtr + 48L, CC_N1, framePtr + 48L, 0L);
                                fidiv(framePtr + 48L, framePtr + 96L, outYieldPtr);
                                status1 = (getI64(outYieldPtr) + 1L) & 0xffffffffL;
                                if (status1 < 5L && ((1L << (int) (status1 & 31L)) & 21L) != 0L && comma(isZero = fZero(outYieldPtr) != 0L ? 1L : 0L, isZero == 0L && comma(comma(exponent = getI32(outYieldPtr + 4L), putI32(outYieldPtr + 4L, exponent + 2L)), 49997L < exponent ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
                                    putI64(outYieldPtr + 8L, 0x999999999999999L);
                                    putI64(outYieldPtr, 0xc34f00000002L);
                                }
                                if ((isAnnualCoupon & 1L) == 0L) {
                                    fimul2(outYieldPtr, outYieldPtr);
                                }
                                status1 = fNorm_L(0L, outYieldPtr) & 0xffffffffL;
                                status4 = status1;
                                result = status4 & 0xffffffffL;
                                return result;
                            }
                            result = 27L;
                            return result;
                        }
                        isZero = fZero(framePtr + 96L) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            fichs2(pricePtr, framePtr + 320L);
                            constPtr = CC_1;
                            if ((isAnnualCoupon & 1L) == 0L) {
                                constPtr = CC_2;
                            }
                            putI64(outYieldPtr + 8L, getI64(framePtr + 56L));
                            putI64(outYieldPtr, getI64(framePtr + 48L));
                            status4 = TVMI(framePtr + 112L, outYieldPtr, framePtr + 320L, framePtr + 80L, callValPtr, constPtr, constPtr, 0L, userStopFn, 0L);
                            result = status4 & 0xffffffffL;
                            return result;
                        }
                        putI64(framePtr + 328L, getI64(framePtr + 88L));
                        putI64(framePtr + 320L, getI64(framePtr + 80L));
                        firadd(callValPtr, framePtr + 80L, framePtr + 80L, 0L);
                        isZero = fZero(couponPtr) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            firadd(framePtr + 112L, framePtr + 96L, framePtr + 96L, 0L);
                            isZero = fZero(framePtr + 64L) != 0L ? 1L : 0L;
                            if (!(isZero == 0L || comma(isZero = fZero(framePtr + 80L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L)) {
                                result = 27L;
                                return result;
                            }
                            isZero = fZero(framePtr + 64L) != 0L ? 1L : 0L;
                            if (isZero == 0L && comma(isZero = fZero(framePtr + 80L) != 0L ? 1L : 0L, isZero == 0L && comma(status1 = fiSignEQ(framePtr + 64L, framePtr + 80L) & 0xffffffffL, (status1 & 1L) == 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
                                fidiv(framePtr + 80L, framePtr + 64L, framePtr + 80L);
                                isZero = fZero(framePtr + 80L) != 0L ? 1L : 0L;
                                if (isZero == 0L) {
                                    putI64(framePtr + 80L, (((getI32(framePtr + 84L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 80L) & 0xffffffffL));
                                }
                                fidiv(CC_1, framePtr + 96L, framePtr + 96L);
                                fpow(framePtr + 80L, framePtr + 96L, outYieldPtr, 1L);
                                firadd(outYieldPtr, CC_N1, outYieldPtr, 0L);
                                status1 = (getI64(outYieldPtr) + 1L) & 0xffffffffL;
                                if (status1 < 5L && ((1L << (int) (status1 & 31L)) & 21L) != 0L && comma(isZero = fZero(outYieldPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(exponent = getI32(outYieldPtr + 4L), putI32(outYieldPtr + 4L, exponent + 2L)), 49997L < exponent ? 1L : 0L) != 0L) {
                                    putI64(outYieldPtr + 8L, 0x999999999999999L);
                                    putI64(outYieldPtr, 0xc34f00000002L);
                                }
                                if ((isAnnualCoupon & 1L) == 0L) {
                                    fimul2(outYieldPtr, outYieldPtr);
                                }
                                status1 = fNormUp_L(outYieldPtr) & 0xffffffffL;
                                status4 = status1;
                                result = status4 & 0xffffffffL;
                                return result;
                            }
                            result = 26L;
                            return result;
                        }
                        status1 = fiSignEQ(framePtr + 64L, framePtr + 80L) & 0xffffffffL;
                        if (!((status1 & 1L) == 0L || (long) (int) comma(isZero = fZero(framePtr + 64L) != 0L ? 1L : 0L, isZero) != 0L || (long) (int) comma(isZero = fZero(framePtr + 80L) != 0L ? 1L : 0L, isZero) != 0L)) {
                            isZero = fZero(framePtr + 64L) != 0L ? 1L : 0L;
                            if (!(isZero != 0L && comma(status1 = fiSignEQ(couponPtr, framePtr + 80L) & 0xffffffffL, status1 & 1L) != 0L || comma(isZero = fZero(framePtr + 80L) != 0L ? 1L : 0L, isZero != 0L && comma(status1 = fiSignEQ(couponPtr, framePtr + 64L) & 0xffffffffL, status1 & 1L) != 0L ? 1L : 0L) != 0L)) {
                                result = 27L;
                                return result;
                            }
                            result = 26L;
                            return result;
                        }
                        putI64(framePtr + 56L, 0L);
                        putI64(framePtr + 48L, 1L);
                        constPtr = CC_1;
                        if ((isAnnualCoupon & 1L) == 0L) {
                            constPtr = CC_2;
                        }
                        status2 = TVMISolveBound(framePtr + 48L, framePtr + 64L, framePtr + 80L, constPtr, constPtr, framePtr + 320L);
                        exponent = 0L;
                        putI32(framePtr + 28L, 10L);
                        step = 1;
                        continue stepLoop;
                    }
                    case 1: {
                        if (userStopFn != 0L && comma(status4 = callIndirect2(userStopFn, exponent, framePtr + 48L), status4 & 1L) != 0L) {
                            result = 30L;
                            return result;
                        }
                        status4 = FinAnnuityFactor(framePtr + 48L, framePtr + 112L, framePtr + 304L, framePtr + 288L, 0L);
                        if ((long) (int) status4 != 0L) {
                            result = status4 & 0xffffffffL;
                            return result;
                        }
                        fimul(framePtr + 320L, framePtr + 304L, framePtr + 272L);
                        fimul(framePtr + 64L, framePtr + 288L, framePtr + 256L);
                        firadd(framePtr + 272L, framePtr + 256L, framePtr + 240L, 0L);
                        TVMNewtonStep(framePtr + 112L, framePtr + 48L, framePtr + 304L, framePtr + 224L);
                        firadd(framePtr + 48L, CC_1, framePtr + 208L, 0L);
                        fimul(framePtr + 208L, framePtr + 224L, framePtr + 192L);
                        fimul(framePtr + 192L, framePtr + 320L, framePtr + 192L);
                        intVal = 1L;
                        firadd(framePtr + 240L, framePtr + 320L, framePtr + 176L, 1L);
                        fimul(framePtr + 112L, framePtr + 176L, framePtr + 176L);
                        firadd(framePtr + 176L, framePtr + 192L, framePtr + 176L, 0L);
                        status3 = fidiv(framePtr + 176L, framePtr + 240L, framePtr + 160L);
                        if ((long) (int) status3 == 4L) {
                            result = 31L;
                            step = 7;
                            continue stepLoop;
                        } else {
                            if (((getI64(framePtr + 160L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 160L) + 1L) & 31L)) & 21L) != 0L) {
                                firadd(framePtr + 160L, framePtr + 96L, framePtr + 160L, 0L);
                                status3 = fidiv(CC_1, framePtr + 160L, framePtr + 160L);
                                if ((long) (int) status3 == 4L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    if (4L < ((getI64(framePtr + 160L) + 1L) & 0xffffffffL) || ((1L << (int) ((getI64(framePtr + 160L) + 1L) & 31L)) & 21L) == 0L) {
                                        step = 3;
                                        continue stepLoop;
                                    } else {
                                        status4 = filnp1(framePtr + 48L, framePtr + 144L);
                                        if ((status4 & 0xffffffffL) < 2L) {
                                            status4 = fimul(framePtr + 144L, framePtr + 96L, framePtr + 144L);
                                        }
                                        isZero = fZero(framePtr + 144L) != 0L ? 1L : 0L;
                                        if (isZero == 0L) {
                                            putI32(framePtr + 144L, -getI64(framePtr + 144L));
                                        }
                                        if ((status4 & 0xffffffffL) < 2L) {
                                            isZero = fiexpm(framePtr + 144L, framePtr + 144L, 0L) != 0L ? 1L : 0L;
                                            status4 = isZero;
                                            if (isZero < 1L) {
                                                fimul(framePtr + 80L, framePtr + 144L, framePtr + 144L);
                                                isZero = fZero(framePtr + 144L) != 0L ? 1L : 0L;
                                                if (isZero == 0L) {
                                                    putI32(framePtr + 144L, -getI64(framePtr + 144L));
                                                }
                                                status3 = fidiv(framePtr + 144L, framePtr + 240L, framePtr + 144L);
                                                if ((long) (int) status3 == 4L) {
                                                    step = 2;
                                                    continue stepLoop;
                                                } else {
                                                    if (4L < ((getI64(framePtr + 144L) + 1L) & 0xffffffffL) || ((1L << (int) ((getI64(framePtr + 144L) + 1L) & 31L)) & 21L) == 0L) {
                                                        step = 3;
                                                        continue stepLoop;
                                                    } else {
                                                        status4 = TVMICompoundFactor(framePtr + 48L, framePtr + 144L, framePtr + 160L);
                                                        if ((long) (int) status4 != 0L) {
                                                            step = 4;
                                                            continue stepLoop;
                                                        } else {
                                                            firadd(framePtr + 160L, framePtr + 48L, framePtr + 128L, 1L);
                                                            putI64(framePtr + 48L, (((getI32(framePtr + 164L) & 0xffffffffL)) << 32) | (getI64(framePtr + 160L) & 0xffffffffL));
                                                            putI64(framePtr + 56L, getI64(framePtr + 168L));
                                                            isZero = fZero(framePtr + 128L) != 0L ? 1L : 0L;
                                                            if (isZero != 0L) {
                                                                step = 5;
                                                                continue stepLoop;
                                                            } else {
                                                                isZero = fZero(framePtr + 160L) != 0L ? 1L : 0L;
                                                                if (isZero == 0L) {
                                                                    fidiv(framePtr + 128L, framePtr + 160L, framePtr + 128L);
                                                                    if (getI32(framePtr + 132L) < -12L) {
                                                                        step = 5;
                                                                        continue stepLoop;
                                                                    } else {
                                                                        if (getI32(framePtr + 132L) == -12L) {
                                                                            putI32(framePtr + 28L, getI32(framePtr + 28L) + 1L);
                                                                            intVal = 2L;
                                                                            if (getI32(framePtr + 28L) != 16L) {
                                                                                intVal = 4L;
                                                                            }
                                                                            step = 7;
                                                                            continue stepLoop;
                                                                        } else {
                                                                            step = 6;
                                                                            continue stepLoop;
                                                                        }
                                                                    }
                                                                } else {
                                                                    step = 6;
                                                                    continue stepLoop;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                step = 4;
                                                continue stepLoop;
                                            }
                                        } else {
                                            intVal = 1L;
                                            result = status4 & 0xffffffffL;
                                            step = 7;
                                            continue stepLoop;
                                        }
                                    }
                                }
                            } else {
                                step = 3;
                                continue stepLoop;
                            }
                        }
                    }
                    case 2: {
                        intVal = 1L;
                        result = 31L;
                        step = 7;
                        continue stepLoop;
                    }
                    case 3: {
                        intVal = 1L;
                        result = 3L;
                        step = 7;
                        continue stepLoop;
                    }
                    case 4: {
                        intVal = 1L;
                        result = status4 & 0xffffffffL;
                        step = 7;
                        continue stepLoop;
                    }
                    case 5: {
                        intVal = 2L;
                        step = 7;
                        continue stepLoop;
                    }
                    case 6: {
                        intVal = 4L;
                        step = 7;
                        continue stepLoop;
                    }
                    case 7: {
                        if (intVal != 4L) {
                            if (intVal != 2L) {
                                return result;
                            }
                            if ((status2 & 1L) != 0L) {
                                isZero = fLE(framePtr + 48L, CC_N0p9999999999) != 0L ? 1L : 0L;
                                if (isZero != 0L) {
                                    result = 3L;
                                    return result;
                                }
                                fradd(framePtr + 48L, CC_1, framePtr + 304L, 0L);
                                status3 = fidiv(framePtr + 48L, framePtr + 304L, framePtr + 48L);
                                isZero = fZero(framePtr + 48L) != 0L ? 1L : 0L;
                                if (isZero == 0L) {
                                    putI64(framePtr + 48L, (((getI32(framePtr + 52L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 48L) & 0xffffffffL));
                                }
                                if ((long) (int) status3 == 4L) {
                                    result = 31L;
                                    return result;
                                }
                                result = 3L;
                                if (4L < ((getI64(framePtr + 48L) + 1L) & 0xffffffffL) || ((1L << (int) ((getI64(framePtr + 48L) + 1L) & 31L)) & 21L) == 0L) {
                                    return result;
                                }
                            }
                            if (((getI64(framePtr + 48L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 48L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 48L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
                                exponent = getI32(framePtr + 52L);
                                putI64(framePtr + 48L, ((((getI32(framePtr + 52L) + 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 48L) & 0xffffffffL));
                                if (49997L < exponent) {
                                    putI64(framePtr + 56L, 0x999999999999999L);
                                    putI64(framePtr + 48L, 0xc34f00000002L);
                                }
                            }
                            fimul(framePtr + 48L, constPtr, outYieldPtr);
                            status1 = fNormUp_L(outYieldPtr) & 0xffffffffL;
                            status4 = status1;
                            result = status4 & 0xffffffffL;
                            return result;
                        }
                        exponent = (long) (int) (exponent + 1L);
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

    long PartAsPercentOfTotal(long totalPtr, long partPtr, long pctPtr, long solveTarget) {
        long status1 = 0L;
        long status2 = 0L;
        long status3 = 0L;
        if ((long) (int) solveTarget == 2L) {
            status3 = fidiv(partPtr, totalPtr, pctPtr);
            status1 = fNorm_L((long) (int) status3, pctPtr) & 0xffffffffL;
            status2 = status1;
            if (status1 < 2L) {
                status2 = fimul(pctPtr, CC_100, pctPtr);
                status1 = fNorm_L((long) (int) status2, pctPtr) & 0xffffffffL;
                status2 = status1;
                if (status1 < 2L && ((getU32(pctPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(pctPtr) + 1L) & 31L)) & 21L) != 0L) {
                    return status2;
                }
            }
            putU32(pctPtr + 8L, 0L);
            putU32(pctPtr + 12L, 0L);
            putU32(pctPtr, 0L);
            putU32(pctPtr + 4L, 0L);
        } else {
            if ((long) (int) solveTarget == 1L) {
                status2 = fidiv(pctPtr, CC_100, partPtr);
                if ((status2 & 0xffffffffL) < 2L) {
                    status2 = fimul(partPtr, totalPtr, partPtr);
                    status1 = fNorm_L((long) (int) status2, partPtr) & 0xffffffffL;
                    status2 = status1;
                    if (status1 < 2L && ((getU32(partPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(partPtr) + 1L) & 31L)) & 21L) != 0L) {
                        return status2;
                    }
                }
                putU32(partPtr + 8L, 0L);
                putU32(partPtr + 12L, 0L);
                putU32(partPtr, 0L);
                putU32(partPtr + 4L, 0L);
            } else {
                if ((long) (int) solveTarget == 0L) {
                    status2 = fimul(partPtr, CC_100, totalPtr);
                    status1 = fNorm_L((long) (int) status2, totalPtr) & 0xffffffffL;
                    status2 = status1;
                    if (status1 < 2L) {
                        status3 = fidiv(totalPtr, pctPtr, totalPtr);
                        status1 = fNorm_L((long) (int) status3, totalPtr) & 0xffffffffL;
                        status2 = status1;
                        if (status1 < 2L && ((getU32(totalPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(totalPtr) + 1L) & 31L)) & 21L) != 0L) {
                            return status2;
                        }
                    }
                    putU32(totalPtr + 8L, 0L);
                    putU32(totalPtr + 12L, 0L);
                    putU32(totalPtr, 0L);
                    putU32(totalPtr + 4L, 0L);
                } else {
                    status2 = 24L;
                }
            }
        }
        return status2;
    }

    long MarkupAsPercentOfCost(long costPtr, long pricePtr, long markupPctPtr, long solveTarget) {
        long status1 = 0L;
        long status2 = 0L;
        if ((long) (int) solveTarget == 2L) {
            status2 = fimul(pricePtr, costPtr, markupPctPtr);
            if ((status2 & 0xffffffffL) < 2L && comma(status2 = fidiv(markupPctPtr, CC_100, markupPctPtr), (status2 & 0xffffffffL) < 2L ? 1L : 0L) != 0L) {
                status2 = firadd(markupPctPtr, pricePtr, markupPctPtr, 0L);
                status1 = fNorm_L((long) (int) status2, markupPctPtr) & 0xffffffffL;
                status2 = status1;
                if (status1 < 2L && ((getU32(markupPctPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(markupPctPtr) + 1L) & 31L)) & 21L) != 0L) {
                    return status2;
                }
            }
            putU32(markupPctPtr + 8L, 0L);
            putU32(markupPctPtr + 12L, 0L);
            putU32(markupPctPtr, 0L);
            putU32(markupPctPtr + 4L, 0L);
        } else {
            if ((long) (int) solveTarget == 1L) {
                status2 = firadd(costPtr, CC_100, pricePtr, 0L);
                if ((status2 & 0xffffffffL) < 2L && comma(status2 = fidiv(markupPctPtr, pricePtr, pricePtr), (status2 & 0xffffffffL) < 2L ? 1L : 0L) != 0L) {
                    status2 = fimul(pricePtr, CC_100, pricePtr);
                    status1 = fNorm_L((long) (int) status2, pricePtr) & 0xffffffffL;
                    status2 = status1;
                    if (status1 < 2L && comma(status1 = (getI64(pricePtr) + 1L) & 0xffffffffL, status1 < 5L && ((1L << (int) (status1 & 31L)) & 21L) != 0L ? 1L : 0L) != 0L) {
                        return status2;
                    }
                }
                putI64(pricePtr + 8L, 0L);
                putI64(pricePtr, 0L);
            } else {
                if ((long) (int) solveTarget == 0L) {
                    status2 = firadd(markupPctPtr, pricePtr, costPtr, 1L);
                    if ((status2 & 0xffffffffL) < 2L && comma(status2 = fidiv(costPtr, pricePtr, costPtr), (status2 & 0xffffffffL) < 2L ? 1L : 0L) != 0L) {
                        status2 = fimul(costPtr, CC_100, costPtr);
                        status1 = fNorm_L((long) (int) status2, costPtr) & 0xffffffffL;
                        status2 = status1;
                        if (status1 < 2L && comma(status1 = (getI64(costPtr) + 1L) & 0xffffffffL, status1 < 5L && ((1L << (int) (status1 & 31L)) & 21L) != 0L ? 1L : 0L) != 0L) {
                            return status2;
                        }
                    }
                    putI64(costPtr + 8L, 0L);
                    putI64(costPtr, 0L);
                } else {
                    status2 = 24L;
                }
            }
        }
        return status2;
    }

    long MarkupAsPercentOfSale(long marginPctPtr, long costPtr, long pricePtr, long solveTarget) {
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            if ((long) (int) solveTarget == 2L) {
                status2 = firadd(CC_100, marginPctPtr, pricePtr, 1L);
                if ((status2 & 0xffffffffL) < 2L && comma(status2 = fidiv(costPtr, pricePtr, pricePtr), (status2 & 0xffffffffL) < 2L ? 1L : 0L) != 0L) {
                    status2 = fimul(pricePtr, CC_100, pricePtr);
                    status1 = fNorm_L((long) (int) status2, pricePtr) & 0xffffffffL;
                    status2 = status1;
                    if (status1 < 2L && comma(status1 = (getI64(pricePtr) + 1L) & 0xffffffffL, status1 < 5L && ((1L << (int) (status1 & 31L)) & 21L) != 0L ? 1L : 0L) != 0L) {
                        return status2;
                    }
                }
                putI64(pricePtr + 8L, 0L);
                putI64(pricePtr, 0L);
            } else {
                if ((long) (int) solveTarget == 1L) {
                    status2 = fidiv(marginPctPtr, CC_100, costPtr);
                    if ((status2 & 0xffffffffL) < 2L && comma(status2 = fimul(pricePtr, costPtr, costPtr), (status2 & 0xffffffffL) < 2L ? 1L : 0L) != 0L) {
                        putI64(framePtr + 32L, getI64(costPtr + 8L));
                        putI64(framePtr + 24L, getI64(costPtr));
                        if ((long) (int) getI64(framePtr + 24L) == 3L) {
                            putI32(framePtr + 28L, getI64(framePtr + 24L) >>> 32);
                            putI64(framePtr + 24L, (((getI32(framePtr + 28L) & 0xffffffffL)) << 32) | 1L);
                        }
                        isZero = fZero(framePtr + 24L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            putI64(framePtr + 24L, (((getI32(framePtr + 28L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 24L) & 0xffffffffL));
                        }
                        status2 = firadd(pricePtr, framePtr + 24L, costPtr, 0L);
                        status1 = fNorm_L((long) (int) status2, costPtr) & 0xffffffffL;
                        status2 = status1;
                        if (status1 < 2L && comma(status1 = (getI64(costPtr) + 1L) & 0xffffffffL, status1 < 5L ? 1L : 0L) != 0L && ((1L << (int) (status1 & 31L)) & 21L) != 0L) {
                            return status2;
                        }
                    }
                    putI64(costPtr + 8L, 0L);
                    putI64(costPtr, 0L);
                } else {
                    if ((long) (int) solveTarget == 0L) {
                        status2 = firadd(pricePtr, costPtr, marginPctPtr, 1L);
                        if ((status2 & 0xffffffffL) < 2L && comma(status2 = fidiv(marginPctPtr, pricePtr, marginPctPtr), (status2 & 0xffffffffL) < 2L ? 1L : 0L) != 0L) {
                            status2 = fimul(marginPctPtr, CC_100, marginPctPtr);
                            status1 = fNorm_L((long) (int) status2, marginPctPtr) & 0xffffffffL;
                            status2 = status1;
                            if (status1 < 2L && comma(status1 = (getI64(marginPctPtr) + 1L) & 0xffffffffL, status1 < 5L && ((1L << (int) (status1 & 31L)) & 21L) != 0L ? 1L : 0L) != 0L) {
                                return status2;
                            }
                        }
                        putI64(marginPctPtr + 8L, 0L);
                        putI64(marginPctPtr, 0L);
                    } else {
                        status2 = 24L;
                    }
                }
            }
            return status2;
        } finally {
            sp = savedSp;
        }
    }

    long BreakEven(long fixedCostPtr, long varCostPtr, long pricePtr, long profitPtr, long quantityPtr, long solveTarget) {
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long status3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            switch ((int) (solveTarget)) {
                case 0: {
                    status3 = firadd(pricePtr, varCostPtr, fixedCostPtr, 1L);
                    if ((status3 & 0xffffffffL) < 2L && comma(status3 = fimul(fixedCostPtr, quantityPtr, fixedCostPtr), (status3 & 0xffffffffL) < 2L ? 1L : 0L) != 0L) {
                        putI64(framePtr + 48L, getI64(profitPtr + 8L));
                        putI64(framePtr + 40L, getI64(profitPtr));
                        if ((long) (int) getI64(framePtr + 40L) == 3L) {
                            putI32(framePtr + 44L, getI64(framePtr + 40L) >>> 32);
                            putI64(framePtr + 40L, (((getI32(framePtr + 44L) & 0xffffffffL)) << 32) | 1L);
                        }
                        isZero = fZero(framePtr + 40L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            putI64(framePtr + 40L, (((getI32(framePtr + 44L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 40L) & 0xffffffffL));
                        }
                        status3 = firadd(fixedCostPtr, framePtr + 40L, fixedCostPtr, 0L);
                        status1 = fNorm_L((long) (int) status3, fixedCostPtr) & 0xffffffffL;
                        status3 = status1;
                        if (status1 < 2L && comma(status1 = (getI64(fixedCostPtr) + 1L) & 0xffffffffL, status1 < 5L ? 1L : 0L) != 0L && ((1L << (int) (status1 & 31L)) & 21L) != 0L) {
                            return status3;
                        }
                    }
                    putI64(fixedCostPtr + 8L, 0L);
                    putI64(fixedCostPtr, 0L);
                    break;
                }
                case 1: {
                    status3 = firadd(fixedCostPtr, profitPtr, varCostPtr, 0L);
                    if ((status3 & 0xffffffffL) < 2L && comma(status3 = fidiv(varCostPtr, quantityPtr, varCostPtr), (status3 & 0xffffffffL) < 2L ? 1L : 0L) != 0L) {
                        putI64(framePtr + 48L, getI64(varCostPtr + 8L));
                        putI64(framePtr + 40L, getI64(varCostPtr));
                        if ((long) (int) getI64(framePtr + 40L) == 3L) {
                            putI32(framePtr + 44L, getI64(framePtr + 40L) >>> 32);
                            putI64(framePtr + 40L, (((getI32(framePtr + 44L) & 0xffffffffL)) << 32) | 1L);
                        }
                        isZero = fZero(framePtr + 40L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            putI64(framePtr + 40L, (((getI32(framePtr + 44L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 40L) & 0xffffffffL));
                        }
                        status3 = firadd(pricePtr, framePtr + 40L, varCostPtr, 0L);
                        status1 = fNorm_L((long) (int) status3, varCostPtr) & 0xffffffffL;
                        status3 = status1;
                        if (status1 < 2L && comma(status1 = (getI64(varCostPtr) + 1L) & 0xffffffffL, status1 < 5L ? 1L : 0L) != 0L && ((1L << (int) (status1 & 31L)) & 21L) != 0L) {
                            return status3;
                        }
                    }
                    putI64(varCostPtr + 8L, 0L);
                    putI64(varCostPtr, 0L);
                    break;
                }
                case 2: {
                    status3 = firadd(fixedCostPtr, profitPtr, pricePtr, 0L);
                    if ((status3 & 0xffffffffL) < 2L && comma(status3 = fidiv(pricePtr, quantityPtr, pricePtr), (status3 & 0xffffffffL) < 2L ? 1L : 0L) != 0L) {
                        status3 = firadd(varCostPtr, pricePtr, pricePtr, 0L);
                        status1 = fNorm_L((long) (int) status3, pricePtr) & 0xffffffffL;
                        status3 = status1;
                        if (status1 < 2L && comma(status1 = (getI64(pricePtr) + 1L) & 0xffffffffL, status1 < 5L && ((1L << (int) (status1 & 31L)) & 21L) != 0L ? 1L : 0L) != 0L) {
                            return status3;
                        }
                    }
                    putI64(pricePtr + 8L, 0L);
                    putI64(pricePtr, 0L);
                    break;
                }
                case 3: {
                    status3 = firadd(pricePtr, varCostPtr, profitPtr, 1L);
                    if ((status3 & 0xffffffffL) < 2L && comma(status3 = fimul(profitPtr, quantityPtr, profitPtr), (status3 & 0xffffffffL) < 2L ? 1L : 0L) != 0L) {
                        putI64(framePtr + 48L, getI64(fixedCostPtr + 8L));
                        putI64(framePtr + 40L, getI64(fixedCostPtr));
                        if ((long) (int) getI64(framePtr + 40L) == 3L) {
                            putI32(framePtr + 44L, getI64(framePtr + 40L) >>> 32);
                            putI64(framePtr + 40L, (((getI32(framePtr + 44L) & 0xffffffffL)) << 32) | 1L);
                        }
                        isZero = fZero(framePtr + 40L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            putI64(framePtr + 40L, (((getI32(framePtr + 44L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 40L) & 0xffffffffL));
                        }
                        status3 = firadd(profitPtr, framePtr + 40L, profitPtr, 0L);
                        status1 = fNorm_L((long) (int) status3, profitPtr) & 0xffffffffL;
                        status3 = status1;
                        if (status1 < 2L && comma(status1 = (getI64(profitPtr) + 1L) & 0xffffffffL, status1 < 5L ? 1L : 0L) != 0L && ((1L << (int) (status1 & 31L)) & 21L) != 0L) {
                            return status3;
                        }
                    }
                    putI64(profitPtr + 8L, 0L);
                    putI64(profitPtr, 0L);
                    break;
                }
                case 4: {
                    status3 = firadd(fixedCostPtr, profitPtr, quantityPtr, 0L);
                    if ((status3 & 0xffffffffL) < 2L && comma(status3 = firadd(pricePtr, varCostPtr, framePtr + 24L, 1L), (status3 & 0xffffffffL) < 2L ? 1L : 0L) != 0L) {
                        status2 = fidiv(quantityPtr, framePtr + 24L, quantityPtr);
                        status1 = fNorm_L((long) (int) status2, quantityPtr) & 0xffffffffL;
                        status3 = status1;
                        if (status1 < 2L && comma(status1 = (getI64(quantityPtr) + 1L) & 0xffffffffL, status1 < 5L && ((1L << (int) (status1 & 31L)) & 21L) != 0L ? 1L : 0L) != 0L) {
                            return status3;
                        }
                    }
                    putI64(quantityPtr + 8L, 0L);
                    putI64(quantityPtr, 0L);
                    break;
                }
                default: {
                    status3 = 24L;
                    break;
                }
            }
            return status3;
        } finally {
            sp = savedSp;
        }
    }

    long BlackSholes(long spotPricePtr, long strikePricePtr, long ratePtr, long timePtr, long volatilityPtr, long outCallValPtr, long outPutValPtr, long dividendYieldPtr) {
        long exponent = 0L;
        long intVal = 0L;
        long isZero = 0L;
        long flag = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(320);
        try {
            status = fidiv(spotPricePtr, strikePricePtr, framePtr + 112L);
            if (!((status & 0xffffffffL) < 2L && comma(status = filn(framePtr + 112L, framePtr + 112L), (status & 0xffffffffL) < 2L ? 1L : 0L) != 0L)) {
                return status;
            }
            firadd(ratePtr, outCallValPtr, framePtr + 96L, 1L);
            if (((getI64(framePtr + 96L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 96L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 96L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(intVal = getI32(framePtr + 100L), putI64(framePtr + 96L, ((((getI32(framePtr + 100L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 96L) & 0xffffffffL))), intVal < -49997L ? 1L : 0L) != 0L) {
                putI64(framePtr + 104L, 0L);
                putI64(framePtr + 96L, 1L);
            }
            fimul(volatilityPtr, volatilityPtr, framePtr + 80L);
            if (((getI64(framePtr + 80L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 80L) + 1L) & 31L)) & 21L) != 0L) {
                isZero = fZero(framePtr + 80L) != 0L ? 1L : 0L;
                if (isZero == 0L && comma(comma(intVal = getI32(framePtr + 84L), putI64(framePtr + 80L, ((((getI32(framePtr + 84L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 80L) & 0xffffffffL))), intVal < -49997L ? 1L : 0L) != 0L) {
                    putI64(framePtr + 88L, 0L);
                    putI64(framePtr + 80L, 1L);
                }
            }
            if (((getI64(framePtr + 80L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 80L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 80L) != 0L ? 1L : 0L, isZero == 0L && comma(comma(intVal = getI32(framePtr + 84L), putI64(framePtr + 80L, ((((getI32(framePtr + 84L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 80L) & 0xffffffffL))), intVal < -49997L ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
                putI64(framePtr + 88L, 0L);
                putI64(framePtr + 80L, 1L);
            }
            fdiv2(framePtr + 80L, framePtr + 80L);
            firadd(framePtr + 96L, framePtr + 80L, framePtr + 96L, 0L);
            fimul(framePtr + 96L, timePtr, framePtr + 96L);
            firadd(framePtr + 112L, framePtr + 96L, framePtr + 64L, 0L);
            flag = fisqrt(timePtr, framePtr + 32L) & 0xffL;
            status = flag;
            if (flag >= 2L) {
                return status;
            }
            fimul(volatilityPtr, framePtr + 32L, framePtr + 32L);
            if (((getI64(framePtr + 32L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 32L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 32L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(intVal = getI32(framePtr + 36L), putI64(framePtr + 32L, ((((getI32(framePtr + 36L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 32L) & 0xffffffffL))), intVal < -49997L ? 1L : 0L) != 0L) {
                putI64(framePtr + 40L, 0L);
                putI64(framePtr + 32L, 1L);
            }
            status = fidiv(framePtr + 64L, framePtr + 32L, framePtr + 64L);
            if ((status & 0xffffffffL) >= 2L) {
                return status;
            }
            firadd(framePtr + 64L, framePtr + 32L, framePtr + 48L, 1L);
            fiUTPN(CC_0, CC_1, framePtr + 64L, framePtr + 112L);
            firadd(CC_1, framePtr + 112L, framePtr + 64L, 1L);
            fimul(framePtr + 64L, spotPricePtr, framePtr + 64L);
            fimul(outCallValPtr, timePtr, framePtr + 112L);
            if (((getI64(framePtr + 112L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 112L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 112L) != 0L ? 1L : 0L, isZero == 0L && comma(comma(intVal = getI32(framePtr + 116L), putI64(framePtr + 112L, ((((getI32(framePtr + 116L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 112L) & 0xffffffffL))), intVal < -49997L ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
                putI64(framePtr + 120L, 0L);
                putI64(framePtr + 112L, 1L);
            }
            isZero = fZero(framePtr + 112L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI64(framePtr + 112L, (((getI32(framePtr + 116L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 112L) & 0xffffffffL));
            }
            fiexpm(framePtr + 112L, framePtr + 112L, 0L);
            fimul(framePtr + 64L, framePtr + 112L, framePtr + 64L);
            fiUTPN(CC_0, CC_1, framePtr + 48L, framePtr + 112L);
            firadd(CC_1, framePtr + 112L, framePtr + 48L, 1L);
            fimul(framePtr + 48L, strikePricePtr, framePtr + 48L);
            fimul(ratePtr, timePtr, framePtr + 112L);
            if (((getI64(framePtr + 112L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 112L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 112L) != 0L ? 1L : 0L, isZero == 0L && comma(comma(intVal = getI32(framePtr + 116L), putI64(framePtr + 112L, ((((getI32(framePtr + 116L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 112L) & 0xffffffffL))), intVal < -49997L ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
                putI64(framePtr + 120L, 0L);
                putI64(framePtr + 112L, 1L);
            }
            isZero = fZero(framePtr + 112L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI64(framePtr + 112L, (((getI32(framePtr + 116L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 112L) & 0xffffffffL));
            }
            fiexpm(framePtr + 112L, framePtr + 112L, 0L);
            fimul(framePtr + 48L, framePtr + 112L, framePtr + 48L);
            putI64(framePtr + 136L, getI64(framePtr + 48L));
            putI64(framePtr + 144L, getI64(framePtr + 56L));
            if ((long) (int) getI64(framePtr + 48L) == 3L) {
                putI32(framePtr + 140L, getI64(framePtr + 48L) >>> 32);
                putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | 1L);
            }
            isZero = fZero(framePtr + 136L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI64(framePtr + 136L, (((getI32(framePtr + 140L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 136L) & 0xffffffffL));
            }
            status = firadd(framePtr + 64L, framePtr + 136L, framePtr + 16L, 0L);
            fNorm_L((long) (int) status, framePtr + 16L);
            if (outPutValPtr != 0L) {
                putI64(outPutValPtr + 8L, getI64(framePtr + 24L));
                putI64(outPutValPtr, getI64(framePtr + 16L));
            }
            if (dividendYieldPtr == 0L) {
                status = 0L;
                return status;
            }
            fimul(ratePtr, timePtr, dividendYieldPtr);
            if (((getU32(dividendYieldPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(dividendYieldPtr) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(dividendYieldPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L && comma(comma(exponent = getU32(dividendYieldPtr + 4L), putU32(dividendYieldPtr + 4L, exponent - 2L)), (long) (int) exponent < -49997L ? 1L : 0L) != 0L) {
                putU32(dividendYieldPtr + 8L, 0L);
                putU32(dividendYieldPtr + 12L, 0L);
                putU32(dividendYieldPtr, 1L);
                putU32(dividendYieldPtr + 4L, 0L);
            }
            isZero = fZero(dividendYieldPtr) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putU32(dividendYieldPtr, -getU32(dividendYieldPtr));
            }
            fiexpm(dividendYieldPtr, dividendYieldPtr, 0L);
            fimul(dividendYieldPtr, strikePricePtr, dividendYieldPtr);
            fimul(outCallValPtr, timePtr, framePtr + 112L);
            if (((getI64(framePtr + 112L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 112L) + 1L) & 31L)) & 21L) != 0L && comma(isZero = fZero(framePtr + 112L) != 0L ? 1L : 0L, isZero == 0L && comma(comma(intVal = getI32(framePtr + 116L), putI64(framePtr + 112L, ((((getI32(framePtr + 116L) - 2L) & 0xffffffffL)) << 32) | (getI64(framePtr + 112L) & 0xffffffffL))), intVal < -49997L ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
                putI64(framePtr + 120L, 0L);
                putI64(framePtr + 112L, 1L);
            }
            isZero = fZero(framePtr + 112L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI64(framePtr + 112L, (((getI32(framePtr + 116L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 112L) & 0xffffffffL));
            }
            fiexpm(framePtr + 112L, framePtr + 112L, 0L);
            fimul(framePtr + 112L, spotPricePtr, framePtr + 112L);
            isZero = fZero(framePtr + 112L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putI64(framePtr + 112L, (((getI32(framePtr + 116L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 112L) & 0xffffffffL));
            }
            firadd(dividendYieldPtr, framePtr + 112L, dividendYieldPtr, 0L);
            status = firadd(dividendYieldPtr, framePtr + 16L, dividendYieldPtr, 0L);
            fNorm_L((long) (int) status, dividendYieldPtr);
            status = 0L;
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long FinCompoundFactor(long ratePtr, long periodsPtr, long outFactorPtr) {
        long isZero = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            isZero = fZero(ratePtr) != 0L ? 1L : 0L;
            status = isZero;
            if (isZero != 0L) {
                return status;
            }
            putU32(ratePtr + 4L, getU32(ratePtr + 4L) - 2L);
            isZero = fEQ(periodsPtr, outFactorPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                status = fidiv(ratePtr, periodsPtr, ratePtr);
            } else {
                fidiv(outFactorPtr, periodsPtr, framePtr + 24L);
                fidiv(ratePtr, outFactorPtr, ratePtr);
                flnp1(ratePtr, ratePtr);
                fmul(ratePtr, framePtr + 24L, ratePtr);
                isZero = fexpm(ratePtr, 0L, ratePtr) != 0L ? 1L : 0L;
                status = isZero;
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long FinAnnuityFactor(long ratePtr, long periodsPtr, long beginMode, long outAnnuityPtr, long outCompoundPtr) {
        long isZero = 0L;
        long result = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            putI64(framePtr + 24L, getI64(periodsPtr + 8L));
            putI64(framePtr + 16L, getI64(periodsPtr));
            if ((outCompoundPtr & 1L) != 0L && comma(isZero = fZero(framePtr + 16L) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) {
                putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 16L) & 0xffffffffL));
            }
            isZero = fZero(ratePtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                result = 0L;
                putI64(beginMode + 8L, getI64(framePtr + 24L));
                putI64(beginMode, getI64(framePtr + 16L));
                putU32(outAnnuityPtr + 8L, 0L);
                putU32(outAnnuityPtr + 12L, 0x1000000L);
                putU32(outAnnuityPtr, 1L);
                putU32(outAnnuityPtr + 4L, 0L);
            } else {
                flnp1(ratePtr, outAnnuityPtr);
                fimul(outAnnuityPtr, framePtr + 16L, outAnnuityPtr);
                fiexpm(outAnnuityPtr, outAnnuityPtr, beginMode);
                result = 3L;
                if (!(((getU32(outAnnuityPtr) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(outAnnuityPtr) + 1L) & 31L)) & 21L) != 0L)) {
                    return result;
                }
                isZero = fEQ(outAnnuityPtr, CC_N1) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    result = 2L;
                } else {
                    fidiv(beginMode, ratePtr, beginMode);
                    result = 0L;
                }
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long DateDayOfWeek(long datePtr, long isDmy) {
        long tempVal1 = 0L;
        long result = 0L;
        long tempVal2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            tempVal2 = DateValidExtract(datePtr, framePtr + 36L, framePtr + 32L, framePtr + 28L, framePtr + 24L, isDmy & 1L, 0L);
            if ((tempVal2 & 1L) == 0L) {
                result = -1L;
                return result;
            }
            tempVal1 = (getU32(framePtr + 36L) + getU32(framePtr + 28L) * 365L + ((((getU32(framePtr + 28L) - 1580L) & 0xffffffffL)) >> 2) - ((getU32(framePtr + 28L) - 1500L) & 0xffffffffL) / 100L + ((getU32(framePtr + 28L) - 1200L) & 0xffffffffL) / 400L + getI32(ADDR_RODATA + 5596L + ((getU32(framePtr + 32L) - 1L) & 0xffffffffL) * 4L) - (getU32(framePtr + 32L) < 3L && getU8(framePtr + 24L) != 0L ? 1L : 0L) - 0x8cf97L) & 0xffffffffL;
            if (tempVal1 < 287L) {
                tempVal1 = 0xffffffffL;
            }
            if (tempVal1 != 0xffffffffL) {
                result = (long) (int) ((long) (int) (tempVal1 - 3L) % 7L + 1L);
                return result;
            } else {
                result = -1L;
                return result;
            }
        } finally {
            sp = savedSp;
        }
    }

    long DateDayIndexFromDate(long datePtr, long outDayIndexPtr, long isDmy, long calendarMode) {
        long index = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            tempVal = DateValidExtract(datePtr, framePtr + 36L, framePtr + 32L, framePtr + 28L, framePtr + 24L, outDayIndexPtr & 1L, isDmy & 1L);
            if ((tempVal & 1L) == 0L) {
                index = 0xffffffffL;
                if (!(getU32(framePtr + 36L) == 31L && (isDmy & 1L) != 0L && (calendarMode & 1L) != 0L)) {
                    return index;
                }
                if ((isDmy & 1L) == 0L) {
                    index = (getU32(framePtr + 28L) * 365L + ((((getU32(framePtr + 28L) - 1580L) & 0xffffffffL)) >> 2) - ((getU32(framePtr + 28L) - 1500L) & 0xffffffffL) / 100L + ((getU32(framePtr + 28L) - 1200L) & 0xffffffffL) / 400L + getI32(ADDR_RODATA + 5596L + ((getU32(framePtr + 32L) - 1L) & 0xffffffffL) * 4L) - (getU32(framePtr + 32L) < 3L && getU8(framePtr + 24L) != 0L ? 1L : 0L) - 0x8cf79L) & 0xffffffffL;
                    if (index < 287L) {
                        index = 0xffffffffL;
                    }
                } else {
                    index = (getU32(framePtr + 28L) * 360L + getU32(framePtr + 32L) * 30L - 1L) & 0xffffffffL;
                }
                index = (index + 1L) & 0xffffffffL;
            } else {
                if ((isDmy & 1L) == 0L) {
                    index = (getU32(framePtr + 36L) + getU32(framePtr + 28L) * 365L + ((((getU32(framePtr + 28L) - 1580L) & 0xffffffffL)) >> 2) + 0xfff73069L - ((getU32(framePtr + 28L) - 1500L) & 0xffffffffL) / 100L + ((getU32(framePtr + 28L) - 1200L) & 0xffffffffL) / 400L + getI32(ADDR_RODATA + 5596L + ((getU32(framePtr + 32L) - 1L) & 0xffffffffL) * 4L) - (getU32(framePtr + 32L) < 3L && getU8(framePtr + 24L) != 0L ? 1L : 0L)) & 0xffffffffL;
                    if (index < 287L) {
                        index = 0xffffffffL;
                    }
                } else {
                    index = (getU32(framePtr + 36L) + getU32(framePtr + 32L) * 30L - (30L < getU32(framePtr + 36L) ? 1L : 0L) + getU32(framePtr + 28L) * 360L - 31L) & 0xffffffffL;
                }
            }
            return index;
        } finally {
            sp = savedSp;
        }
    }

    CC_Real DatePlus(long datePtr, long daysPtr, long isDmy, long outResultPtr) {
        long dayIndex = 0L;
        CC_Real realVal = new CC_Real();
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            dayIndex = DateDayIndexFromDate(datePtr, isDmy & 1L, outResultPtr & 1L, 1L) & 0xffffffffL;
            if (dayIndex == 0xffffffffL) {
                putI64(framePtr + 24L, 0L);
                putI64(framePtr + 32L, 0L);
            } else {
                DateFromDayIndex((dayIndex + daysPtr) & 0xffffffffL, framePtr + 24L, isDmy & 1L, outResultPtr & 1L);
            }
            realVal.m = getI64(framePtr + 32L);
            realVal.s = (long) (int) getI64(framePtr + 24L);
            realVal.e = (long) (int) (getI64(framePtr + 24L) >>> 32);
            return realVal.copy();
        } finally {
            sp = savedSp;
        }
    }

    void DateFromDayIndex(long dayIndex, long isDmy, long calendarMode, long outDatePtr) {
        long tempVal1 = 0L;
        long flag = 0L;
        long bcdVal = 0L;
        long index1 = 0L;
        long index2 = 0L;
        long tempVal2 = 0L;
        long intVal = 0L;
        long tempVal3 = 0L;
        long index3 = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    putI32(isDmy, 0L);
                    if ((outDatePtr & 1L) == 0L) {
                        if (0x2ee913L < ((dayIndex - 287L) & 0xffffffffL)) {
                            return;
                        }
                        tempVal3 = (dayIndex - 365L) & 0xffffffffL;
                        if ((long) (int) dayIndex < 365L) {
                            tempVal3 = dayIndex & 0xffffffffL;
                        }
                        index1 = 1582L;
                        if (364L < (long) (int) dayIndex) {
                            index1 = 1583L;
                        }
                        if (364L < (long) (int) tempVal3) {
                            index1 = (index1 + 1L) & 0xffffffffL;
                            tempVal3 = (tempVal3 - 365L) & 0xffffffffL;
                        }
                        index2 = (tempVal3 - 1461L) & 0xffffffffL;
                        tempVal2 = (index1 + 4L) & 0xffffffffL;
                        if ((long) (int) tempVal3 < 1461L) {
                            index2 = tempVal3;
                            tempVal2 = index1;
                        }
                        tempVal3 = (index2 - 1461L) & 0xffffffffL;
                        index1 = (tempVal2 + 4L) & 0xffffffffL;
                        if ((long) (int) index2 < 1461L) {
                            tempVal3 = index2;
                            index1 = tempVal2;
                        }
                        index2 = (tempVal3 - 1461L) & 0xffffffffL;
                        tempVal2 = (index1 + 4L) & 0xffffffffL;
                        if ((long) (int) tempVal3 < 1461L) {
                            index2 = tempVal3;
                            tempVal2 = index1;
                        }
                        index1 = (index2 - 1461L) & 0xffffffffL;
                        tempVal3 = (tempVal2 + 4L) & 0xffffffffL;
                        if ((long) (int) index2 < 1461L) {
                            index1 = index2;
                            tempVal3 = tempVal2;
                        }
                        putI32(isDmy + 8L, 0L);
                        putI32(isDmy + 12L, 0L);
                        putI32(isDmy, 1L);
                        putI32(isDmy + 4L, 0L);
                        putI32(isDmy, 3L);
                        if (0x23ab0L < (long) (int) index1) {
                            index2 = index1;
                            if (0x47560L < (long) (int) index1) {
                                index2 = 0x47561L;
                            }
                            index2 = (((index1 + 0x23ab0L - index2) & 0xffffffffL) / 0x23ab1L) & 0xffffffffL;
                            tempVal3 = (tempVal3 + index2 * 400L + 400L) & 0xffffffffL;
                            index1 = (index1 + index2 * 0xfffdc54fL - 0x23ab1L) & 0xffffffffL;
                        }
                        index2 = (index1 - 36525L) & 0xffffffffL;
                        tempVal2 = (tempVal3 + 100L) & 0xffffffffL;
                        if ((long) (int) index1 < 36525L) {
                            index2 = index1;
                            tempVal2 = tempVal3;
                        }
                        tempVal3 = (index2 - 36524L) & 0xffffffffL;
                        index1 = (tempVal2 + 100L) & 0xffffffffL;
                        if ((long) (int) index2 < 36524L) {
                            tempVal3 = index2;
                            index1 = tempVal2;
                        }
                        tempVal2 = (tempVal3 - 36524L) & 0xffffffffL;
                        index2 = (index1 + 100L) & 0xffffffffL;
                        if ((long) (int) tempVal3 < 36524L) {
                            tempVal2 = tempVal3;
                            index2 = index1;
                        }
                        tempVal3 = index2 & 3L;
                        step = 1;
                        continue stepLoop;
                    } else {
                        putI32(isDmy, 3L);
                        index2 = ((long) (int) dayIndex / 360L) & 0xffffffffL;
                        index3 = ((long) (int) ((long) (int) dayIndex % 360L) / 30L) & 0xffffffffL;
                        tempVal3 = ((long) (int) dayIndex % 30L) & 0xffffffffL;
                        step = 5;
                        continue stepLoop;
                    }
                }
                case 1: {
                    if (((index2 / 400L * 400L) & 0xffffffffL) == index2) {
                        if ((long) (int) tempVal2 < 1461L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            step = 3;
                            continue stepLoop;
                        }
                    } else {
                        index1 = (index2 / 100L * 100L) & 0xffffffffL;
                        if (index1 == index2 || tempVal3 != 0L) {
                            if ((long) (int) tempVal2 < 1460L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                step = 4;
                                continue stepLoop;
                            }
                        } else {
                            if ((long) (int) tempVal2 < 1461L) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                step = 4;
                                continue stepLoop;
                            }
                        }
                    }
                }
                case 2: {
                    tempVal3 = (index2 / 400L * 400L) & 0xffffffffL;
                    if (tempVal3 == index2) {
                        if ((long) (int) tempVal2 < 366L) {
                            step = 6;
                            continue stepLoop;
                        } else {
                            step = 7;
                            continue stepLoop;
                        }
                    } else {
                        index1 = (index2 / 100L * 100L) & 0xffffffffL;
                        if (index1 == index2 || (index2 & 3L) != 0L) {
                            if ((long) (int) tempVal2 < 365L) {
                                step = 6;
                                continue stepLoop;
                            } else {
                                step = 8;
                                continue stepLoop;
                            }
                        } else {
                            if ((long) (int) tempVal2 < 366L) {
                                step = 6;
                                continue stepLoop;
                            } else {
                                step = 8;
                                continue stepLoop;
                            }
                        }
                    }
                }
                case 3: {
                    bcdVal = -1461L;
                    tempVal2 = (bcdVal + tempVal2) & 0xffffffffL;
                    index2 = (index2 + 4L) & 0xffffffffL;
                    step = 1;
                    continue stepLoop;
                }
                case 4: {
                    bcdVal = -1460L;
                    if (index1 != index2 && tempVal3 == 0L) {
                        step = 3;
                        continue stepLoop;
                    } else {
                        tempVal2 = (bcdVal + tempVal2) & 0xffffffffL;
                        index2 = (index2 + 4L) & 0xffffffffL;
                        step = 1;
                        continue stepLoop;
                    }
                }
                case 5: {
                    flag = (calendarMode & 1L) == 0L ? 1L : 0L;
                    index1 = index3;
                    if (flag != 0L) {
                        index1 = tempVal3;
                    }
                    if (flag != 0L) {
                        tempVal3 = index3;
                    }
                    putI32(isDmy + 8L, 0L);
                    bcdVal = (long) (int) bintobcd((index2 + (index1 + 1L) * 10000L + (tempVal3 + 1L) * 0xf4240L) & 0xffffffffL);
                    putI32(isDmy + 12L, bcdVal);
                    putI32(isDmy + 4L, 0L);
                    fNorm(0L, isDmy);
                    return;
                }
                case 6: {
                    index1 = (index2 / 100L * 100L) & 0xffffffffL;
                    index3 = 11L;
                    step = 9;
                    continue stepLoop;
                }
                case 7: {
                    bcdVal = -366L;
                    tempVal2 = (bcdVal + tempVal2) & 0xffffffffL;
                    index2 = (index2 + 1L) & 0xffffffffL;
                    step = 2;
                    continue stepLoop;
                }
                case 8: {
                    bcdVal = -365L;
                    if (index1 != index2 && (index2 & 3L) == 0L) {
                        step = 7;
                        continue stepLoop;
                    } else {
                        tempVal2 = (bcdVal + tempVal2) & 0xffffffffL;
                        index2 = (index2 + 1L) & 0xffffffffL;
                        step = 2;
                        continue stepLoop;
                    }
                }
                case 9: {
                    tempVal1 = getU32(ADDR_RODATA + 5596L + index3 * 4L);
                    if (index3 < 2L) {
                        bcdVal = (long) (int) (tempVal2 - tempVal1);
                        if (tempVal1 <= tempVal2) {
                            intVal = 0L;
                            tempVal3 = (intVal + bcdVal) & 0xffffffffL;
                            step = 5;
                            continue stepLoop;
                        } else {
                            step = 10;
                            continue stepLoop;
                        }
                    } else {
                        if (((tempVal1 + (tempVal3 == index2 || index1 != index2 && (index2 & 3L) == 0L ? 1L : 0L)) & 0xffffffffL) <= tempVal2) {
                            bcdVal = (long) (int) (tempVal2 - tempVal1);
                            if (tempVal3 == index2) {
                                intVal = -1L;
                            } else {
                                intVal = (long) (int) -(index1 != index2 && (index2 & 3L) == 0L ? 1L : 0L);
                            }
                            tempVal3 = (intVal + bcdVal) & 0xffffffffL;
                            step = 5;
                            continue stepLoop;
                        } else {
                            step = 10;
                            continue stepLoop;
                        }
                    }
                }
                case 10: {
                    index3 = (index3 - 1L) & 0xffffffffL;
                    step = 9;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long DateDDays(long date1Ptr, long date2Ptr, long isDmy, long outActualDaysPtr) {
        long dayIndex1 = 0L;
        long dayIndex2 = 0L;
        dayIndex1 = DateDayIndexFromDate(date2Ptr, isDmy & 1L, outActualDaysPtr & 1L, 1L) & 0xffffffffL;
        dayIndex2 = DateDayIndexFromDate(date1Ptr, isDmy & 1L, outActualDaysPtr & 1L, 1L) & 0xffffffffL;
        return (long) (int) (dayIndex1 - dayIndex2);
    }

    long DateLeap(long year) {
        if ((((((((year * 0xc28f5c29L) & 0xffffffffL)) >> 4) | (year * 0x90000000L))) & 0xffffffffL) < 0xa3d70bL) {
            return 1L;
        }
        if (0x28f5c28L < (((((((year * 0xc28f5c29L) & 0xffffffffL)) >> 2) | (year * 0x40000000L))) & 0xffffffffL)) {
            return (year & 3L) == 0L ? 1L : 0L;
        } else {
            return 0L;
        }
    }

    long DateValidExtract(long datePtr, long outDayPtr, long outMonthPtr, long outYearPtr, long outDayOfWeekPtr, long isDmy, long checkGregorian) {
        long tempVal1 = 0L;
        long flag = 0L;
        long tempVal2 = 0L;
        long binVal1 = 0L;
        long binVal2 = 0L;
        long tempVal3 = 0L;
        if (!(getU32(datePtr + 4L) < 2L && (getU32(datePtr + 8L) & 0xfffffffL) == 0L && (((getU32(datePtr) | 2L)) & 0xffffffffL) == 3L)) {
            return 0L;
        }
        binVal2 = getI64(datePtr + 8L) >>> 4;
        if (getU32(datePtr + 4L) != 0L) {
            binVal2 = getI64(datePtr + 8L);
        }
        binVal1 = dcbtobin(binVal2 >>> 52);
        putU32(outDayPtr, binVal1);
        binVal1 = dcbtobin((binVal2 >>> 44) & 0xffL);
        putU32(outMonthPtr, binVal1);
        if ((isDmy & 1L) == 0L) {
            tempVal2 = getU32(outDayPtr);
            putU32(outDayPtr, binVal1);
            putU32(outMonthPtr, tempVal2);
        }
        binVal2 = dcbtobin((binVal2 >>> 28) & 0xffffL);
        tempVal2 = binVal2 & 0xffffffffL;
        putU32(outYearPtr, tempVal2);
        if (1581L >= tempVal2) {
            return 0L;
        }
        tempVal3 = getU32(outMonthPtr);
        tempVal1 = (tempVal3 - 1L) & 0xffffffffL;
        if (tempVal1 >= 12L) {
            return 0L;
        }
        if ((((((((tempVal2 * 0xc28f5c29L) & 0xffffffffL)) >> 4) | (tempVal2 * 0x90000000L))) & 0xffffffffL) < 0xa3d70bL) {
            flag = 1L;
        } else {
            if ((((((((tempVal2 * 0xc28f5c29L) & 0xffffffffL)) >> 2) | (tempVal2 * 0x40000000L))) & 0xffffffffL) < 0x28f5c29L) {
                flag = 0L;
            } else {
                flag = (binVal2 & 3L) == 0L ? 1L : 0L;
            }
        }
        putU8(outDayOfWeekPtr, flag);
        tempVal2 = getU32(outDayPtr);
        if ((checkGregorian & 1L) == 0L) {
            if (tempVal2 == 0L) {
                return 0L;
            }
            if (tempVal3 != 2L || (((flag ^ 1L)) & 0xffL) != 0L) {
                tempVal3 = getU8(ADDR_RODATA + 5584L + tempVal1);
            } else {
                tempVal3 = 29L;
            }
            if (tempVal2 <= tempVal3) {
                return 1L;
            }
        } else {
            if (((tempVal2 - 1L) & 0xffffffffL) < 30L) {
                return 1L;
            }
        }
        return 0L;
    }

    long DateValide(long day, long month, long year) {
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            tempVal = DateValidExtract(day, framePtr + 36L, framePtr + 32L, framePtr + 28L, framePtr + 24L, month & 1L, year & 1L);
            return tempVal & 1L;
        } finally {
            sp = savedSp;
        }
    }

    long DateDayIndexFromDateinternal(long datePtr, long outDayIndexPtr, long isDmy, long calendarMode, long outYearPtr) {
        long result = 0L;
        if ((outYearPtr & 1L) != 0L) {
            return (datePtr + outDayIndexPtr * 30L - (30L < (datePtr & 0xffffffffL) ? 1L : 0L) + isDmy * 360L - 31L) & 0xffffffffL;
        }
        result = (datePtr + isDmy * 365L + ((((isDmy - 1580L) & 0xffffffffL)) >> 2) - ((isDmy - 1500L) & 0xffffffffL) / 100L + ((isDmy - 1200L) & 0xffffffffL) / 400L - (((outDayIndexPtr & 0xffffffffL) < 3L ? 1L : 0L) & calendarMode) + getI32(ADDR_RODATA + 5596L + ((outDayIndexPtr - 1L) & 0xffffffffL) * 4L) - 0x8cf97L) & 0xffffffffL;
        if (result < 287L) {
            result = 0xffffffffL;
        }
        return result;
    }

}

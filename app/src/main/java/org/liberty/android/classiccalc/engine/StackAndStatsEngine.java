package org.liberty.android.classiccalc.engine;

/**
 * RPN stack, data/cash-flow/program registers, and statistical/regression calculations.
 */
abstract class StackAndStatsEngine extends FinancialMathEngine {
    long sMeanInternal(long dataPtr, long entryCount, long getEntryFn, long varCount, long isWeighted, long isPopStdDev, long outMeanXPtr, long outMeanYPtr, long outStdDevXPtr, long outStdDevYPtr, long outSumXPtr, long outSumYPtr, long outSumWPtr, long outMinMaxPtr) {
        long tempVal = 0L;
        long longVal1 = 0L;
        long longVal2 = 0L;
        long isEqual = 0L;
        long dataPtr1 = 0L;
        long result = 0L;
        long dataPtr2 = 0L;
        long dataPtr3 = 0L;
        long dataPtr4 = 0L;
        long index = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(288);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        if (isWeighted != 0L) {
                            putI64(isWeighted + 8L, 0L);
                            putI64(isWeighted, 0L);
                        }
                        if (isPopStdDev != 0L) {
                            putU32(isPopStdDev + 8L, 0L);
                            putU32(isPopStdDev + 12L, 0L);
                            putU32(isPopStdDev, 0L);
                            putU32(isPopStdDev + 4L, 0L);
                        }
                        if (outMeanXPtr != 0L) {
                            putI64(outMeanXPtr + 8L, 0L);
                            putI64(outMeanXPtr, 0L);
                        }
                        if (outMeanYPtr != 0L) {
                            putI64(outMeanYPtr + 8L, 0L);
                            putI64(outMeanYPtr, 0L);
                        }
                        if (outStdDevXPtr != 0L) {
                            putI64(outStdDevXPtr + 8L, 0L);
                            putI64(outStdDevXPtr, 0L);
                        }
                        if (outStdDevYPtr != 0L) {
                            putI64(outStdDevYPtr + 8L, 0L);
                            putI64(outStdDevYPtr, 0L);
                        }
                        if (outSumXPtr != 0L) {
                            putI64(outSumXPtr + 8L, 0L);
                            putI64(outSumXPtr, 0L);
                        }
                        if (outSumYPtr != 0L) {
                            putI64(outSumYPtr + 8L, 0L);
                            putI64(outSumYPtr, 0L);
                        }
                        if (outSumWPtr != 0L) {
                            putI32(outSumWPtr + 8L, 0L);
                            putI32(outSumWPtr + 12L, 0L);
                            putI32(outSumWPtr, 0L);
                            putI32(outSumWPtr + 4L, 0L);
                        }
                        putI64(framePtr + 72L, 0L);
                        putI64(framePtr + 64L, 1L);
                        putI64(framePtr + 96L, getI64(framePtr + 64L));
                        putI64(framePtr + 104L, getI64(framePtr + 72L));
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
                        if (isWeighted != 0L) {
                            putI64(isWeighted + 8L, getI64(framePtr + 104L));
                            putI64(isWeighted, getI64(framePtr + 96L));
                        }
                        if ((long) (int) varCount != 2L) {
                            fIntToCC(getI32(framePtr + 68L), framePtr + 64L);
                        }
                        if (outMinMaxPtr != 0L) {
                            putI64(outMinMaxPtr + 8L, getI64(framePtr + 72L));
                            putI64(outMinMaxPtr, getI64(framePtr + 64L));
                        }
                        isEqual = fEQ(framePtr + 64L, CC_0) != 0L ? 1L : 0L;
                        if (isEqual != 0L) {
                            result = 32L;
                            return result;
                        }
                        fidiv(framePtr + 96L, framePtr + 64L, framePtr + 80L);
                        if (outMeanXPtr != 0L) {
                            putI64(outMeanXPtr + 8L, getI64(framePtr + 88L));
                            putI64(outMeanXPtr, getI64(framePtr + 80L));
                        }
                        if (!(isPopStdDev != 0L || outMeanYPtr != 0L || outStdDevXPtr != 0L || outStdDevYPtr != 0L || outSumXPtr != 0L || outSumYPtr != 0L || outSumWPtr != 0L)) {
                            result = 0L;
                            return result;
                        }
                        if (isPopStdDev != 0L) {
                            putU32(isPopStdDev + 8L, 0L);
                            putU32(isPopStdDev + 12L, 0L);
                            putU32(isPopStdDev, 1L);
                            putU32(isPopStdDev + 4L, 0L);
                        }
                        putI64(framePtr + 40L, 0L);
                        putI64(framePtr + 32L, 1L);
                        if ((long) (int) entryCount != 0L) {
                            index = 0L;
                            step = 7;
                            continue stepLoop;
                        } else {
                            step = 6;
                            continue stepLoop;
                        }
                    }
                    case 2: {
                        dataPtr1 = callIndirect3(getEntryFn, dataPtr, (long) (int) entryCount, index);
                        if (((getU32(dataPtr1) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(dataPtr1) + 1L) & 31L)) & 21L) != 0L) {
                            if ((long) (int) varCount == 1L) {
                                dataPtr2 = framePtr + 96L;
                                dataPtr3 = framePtr + 96L;
                                putI64(framePtr + 64L, ((((getI32(framePtr + 68L) + 1L) & 0xffffffffL)) << 32) | (getI64(framePtr + 64L) & 0xffffffffL));
                                firadd(dataPtr2, dataPtr1, dataPtr3, 0L);
                                step = 3;
                                continue stepLoop;
                            } else {
                                if (4L < ((getU32(dataPtr1 + 16L) + 1L) & 0xffffffffL) || ((1L << (int) ((getU32(dataPtr1 + 16L) + 1L) & 31L)) & 21L) == 0L) {
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    if ((long) (int) varCount == 2L) {
                                        isEqual = 1L;
                                        step = 5;
                                        continue stepLoop;
                                    } else {
                                        putI64(framePtr + 64L, ((((getI32(framePtr + 68L) + 1L) & 0xffffffffL)) << 32) | (getI64(framePtr + 64L) & 0xffffffffL));
                                        if ((long) (int) varCount != 0L) {
                                            isEqual = 0L;
                                            step = 5;
                                            continue stepLoop;
                                        } else {
                                            longVal1 = -32L;
                                            longVal2 = -32L;
                                            step = 4;
                                            continue stepLoop;
                                        }
                                    }
                                }
                            }
                        } else {
                            step = 3;
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
                        dataPtr1 = dataPtr1 + 16L;
                        dataPtr3 = framePtr + 128L + longVal2;
                        dataPtr2 = framePtr + 128L + longVal1;
                        firadd(dataPtr2, dataPtr1, dataPtr3, 0L);
                        step = 3;
                        continue stepLoop;
                    }
                    case 5: {
                        fimul(dataPtr1, dataPtr1 + 16L, framePtr + 80L);
                        firadd(framePtr + 96L, framePtr + 80L, framePtr + 96L, 0L);
                        if (isEqual == 0L) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            isEqual = fiInteger(dataPtr1 + 16L) != 0L ? 1L : 0L;
                            if (isEqual == 0L || (long) (int) getU32(dataPtr1 + 16L) < 1L) {
                                result = 24L;
                                return result;
                            }
                            longVal1 = -64L;
                            longVal2 = -64L;
                            step = 4;
                            continue stepLoop;
                        }
                    }
                    case 6: {
                        if (outSumYPtr != 0L) {
                            putI64(outSumYPtr + 8L, getI64(framePtr + 40L));
                            putI64(outSumYPtr, getI64(framePtr + 32L));
                        }
                        if (outStdDevYPtr != 0L || outSumXPtr != 0L) {
                            fidiv(framePtr + 32L, framePtr + 64L, framePtr + 48L);
                            if (outSumXPtr != 0L) {
                                putI64(outSumXPtr + 8L, getI64(framePtr + 56L));
                                putI64(outSumXPtr, getI64(framePtr + 48L));
                            }
                            if (outStdDevYPtr != 0L) {
                                fisqrt(framePtr + 48L, outStdDevYPtr);
                            }
                        }
                        if (!(outMeanYPtr != 0L || outStdDevXPtr != 0L || outSumWPtr != 0L)) {
                            result = 0L;
                            return result;
                        }
                        isEqual = fEQ(framePtr + 64L, CC_1) != 0L ? 1L : 0L;
                        if (isEqual != 0L) {
                            result = 32L;
                            return result;
                        } else {
                            fradd(framePtr + 64L, CC_N1, framePtr + 16L, 0L);
                            fidiv(framePtr + 32L, framePtr + 16L, framePtr + 48L);
                            if (outStdDevXPtr != 0L) {
                                putI64(outStdDevXPtr + 8L, getI64(framePtr + 56L));
                                putI64(outStdDevXPtr, getI64(framePtr + 48L));
                            }
                            if (outMeanYPtr != 0L || outSumWPtr != 0L) {
                                fisqrt(framePtr + 48L, framePtr + 48L);
                                if (outMeanYPtr != 0L) {
                                    putI64(outMeanYPtr + 8L, getI64(framePtr + 56L));
                                    putI64(outMeanYPtr, getI64(framePtr + 48L));
                                }
                                if (outSumWPtr != 0L) {
                                    fisqrt(framePtr + 64L, framePtr + 64L);
                                    fidiv(framePtr + 48L, framePtr + 64L, outSumWPtr);
                                }
                            }
                            result = 0L;
                            return result;
                        }
                    }
                    case 7: {
                        dataPtr2 = callIndirect3(getEntryFn, dataPtr, (long) (int) entryCount, index);
                        tempVal = (getI64(dataPtr2) + 1L) & 0xffffffffL;
                        if (tempVal < 5L && ((1L << (int) (tempVal & 31L)) & 21L) != 0L) {
                            dataPtr3 = dataPtr2;
                            if ((long) (int) varCount != 1L) {
                                dataPtr4 = dataPtr2 + 16L;
                                tempVal = (getI64(dataPtr4) + 1L) & 0xffffffffL;
                                if (4L < tempVal || ((1L << (int) (tempVal & 31L)) & 21L) == 0L) {
                                    step = 9;
                                    continue stepLoop;
                                } else {
                                    dataPtr3 = dataPtr4;
                                    if ((long) (int) varCount != 0L && comma(dataPtr3 = dataPtr2, (long) (int) varCount == 3L ? 1L : 0L) != 0L) {
                                        fimul(dataPtr2, dataPtr4, framePtr + 48L);
                                        dataPtr3 = framePtr + 48L;
                                    }
                                    step = 8;
                                    continue stepLoop;
                                }
                            } else {
                                step = 8;
                                continue stepLoop;
                            }
                        } else {
                            step = 9;
                            continue stepLoop;
                        }
                    }
                    case 8: {
                        if (isPopStdDev != 0L) {
                            fimul(dataPtr3, dataPtr3, framePtr + 48L);
                            if ((long) (int) varCount == 2L) {
                                fimul(framePtr + 48L, dataPtr3 + 16L, framePtr + 48L);
                            }
                            firadd(isPopStdDev, framePtr + 48L, isPopStdDev, 0L);
                        }
                        if (outMeanYPtr != 0L || outStdDevXPtr != 0L || outStdDevYPtr != 0L || outSumXPtr != 0L || outSumYPtr != 0L) {
                            firadd(framePtr + 80L, dataPtr3, framePtr + 48L, 1L);
                            fimul(framePtr + 48L, framePtr + 48L, framePtr + 48L);
                            if ((long) (int) varCount == 2L) {
                                fimul(framePtr + 48L, dataPtr3 + 16L, framePtr + 48L);
                            }
                            firadd(framePtr + 32L, framePtr + 48L, framePtr + 32L, 0L);
                        }
                        step = 9;
                        continue stepLoop;
                    }
                    case 9: {
                        index = (long) (int) (index + 1L);
                        if ((long) (int) entryCount != index) {
                            step = 7;
                            continue stepLoop;
                        } else {
                            step = 6;
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

    long sMean(long dataPtr, long entryCount, long getEntryFn, long varCount, long isWeighted, long isPopStdDev, long outMeanXPtr, long outMeanYPtr, long outStdDevXPtr, long outStdDevYPtr, long outSumXPtr, long outSumYPtr, long outSumWPtr, long outMinMaxPtr) {
        long status = 0L;
        status = sMeanInternal(dataPtr, (long) (int) entryCount, getEntryFn, (long) (int) varCount, isWeighted, isPopStdDev, outMeanXPtr, outMeanYPtr, outStdDevXPtr, outStdDevYPtr, outSumXPtr, outSumYPtr, outSumWPtr, outMinMaxPtr);
        return status;
    }

    long sRegression(long dataPtr, long entryCount, long getEntryFn, long modelType, long outSlopePtr, long outInterceptPtr, long outCorrPtr, long expBasePtr, long asymptotePtr) {
        long tempVal1 = 0L;
        long isZero1 = 0L;
        long flag = 0L;
        long isZero2 = 0L;
        long status1 = 0L;
        long dataPtr1 = 0L;
        long longVal = 0L;
        long dataPtr2 = 0L;
        long status2 = 0L;
        long index1 = 0L;
        long dataPtr3 = 0L;
        long tempVal2 = 0L;
        long index2 = 0L;
        long strPtr = 0L;
        long dataPtr4 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(496);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        if (outSlopePtr != 0L) {
                            putI64(outSlopePtr + 8L, 0L);
                            putI64(outSlopePtr, 0L);
                        }
                        if (outInterceptPtr != 0L) {
                            putI64(outInterceptPtr + 8L, 0L);
                            putI64(outInterceptPtr, 0L);
                        }
                        if (outCorrPtr != 0L) {
                            putI32(outCorrPtr + 8L, 0L);
                            putI32(outCorrPtr + 12L, 0L);
                            putI32(outCorrPtr, 0L);
                            putI32(outCorrPtr + 4L, 0L);
                        }
                        if (expBasePtr != 0L) {
                            putU32(expBasePtr + 8L, 0L);
                            putU32(expBasePtr + 12L, 0L);
                            putU32(expBasePtr, 0L);
                            putU32(expBasePtr + 4L, 0L);
                        }
                        if ((entryCount & 0xffffffffL) < 2L) {
                            status1 = 32L;
                            return status1;
                        }
                        if ((modelType & 0xffffffffL) == 6L && asymptotePtr == 0L) {
                            status1 = 24L;
                            return status1;
                        }
                        if ((modelType & 0xffffffffL) == 6L) {
                            status1 = 0L;
                            index2 = 0L;
                            putI64(framePtr + 104L, 0L);
                            putI64(framePtr + 96L, 0L);
                            tempVal2 = 0xffffffffL;
                            putU32(framePtr + 28L, 0xffffffffL);
                            putI64(framePtr + 112L, getI64(framePtr + 96L));
                            putI64(framePtr + 120L, getI64(framePtr + 104L));
                            putI64(framePtr + 128L, getI64(framePtr + 96L));
                            putI64(framePtr + 136L, getI64(framePtr + 104L));
                            putI64(framePtr + 144L, getI64(framePtr + 96L));
                            putI64(framePtr + 152L, getI64(framePtr + 104L));
                            do {
                                dataPtr1 = callIndirect3(getEntryFn, dataPtr, entryCount & 0xffffffffL, status1);
                                tempVal1 = (getI64(dataPtr1) + 1L) & 0xffffffffL;
                                if (tempVal1 < 5L && ((1L << (int) (tempVal1 & 31L)) & 21L) != 0L) {
                                    isZero1 = fLT(dataPtr1, framePtr + 128L) != 0L ? 1L : 0L;
                                    if (isZero1 == 0L) {
                                        putI64(framePtr + 136L, getI64(dataPtr1 + 8L));
                                        putI64(framePtr + 128L, getI64(dataPtr1));
                                        putU32(framePtr + 28L, status1);
                                    }
                                    isZero1 = fLT(framePtr + 144L, dataPtr1) != 0L ? 1L : 0L;
                                    if (isZero1 == 0L) {
                                        putI64(framePtr + 152L, getI64(dataPtr1 + 8L));
                                        putI64(framePtr + 144L, getI64(dataPtr1));
                                        tempVal2 = status1;
                                    }
                                }
                                dataPtr3 = dataPtr1 + 16L;
                                tempVal1 = (getI64(dataPtr3) + 1L) & 0xffffffffL;
                                if (tempVal1 < 5L && ((1L << (int) (tempVal1 & 31L)) & 21L) != 0L) {
                                    isZero1 = fLT(dataPtr3, framePtr + 96L) != 0L ? 1L : 0L;
                                    if (isZero1 == 0L) {
                                        putI64(framePtr + 104L, getI64(dataPtr1 + 24L));
                                        putI64(framePtr + 96L, getI64(dataPtr3));
                                    }
                                    isZero1 = fLT(framePtr + 112L, dataPtr3) != 0L ? 1L : 0L;
                                    if (isZero1 == 0L) {
                                        putI64(framePtr + 120L, getI64(dataPtr1 + 24L));
                                        putI64(framePtr + 112L, getI64(dataPtr3));
                                    }
                                }
                                tempVal1 = (getI64(dataPtr1) + 1L) & 0xffffffffL;
                                if (tempVal1 < 5L && ((1L << (int) (tempVal1 & 31L)) & 21L) != 0L && comma(tempVal1 = (getI64(dataPtr3) + 1L) & 0xffffffffL, tempVal1 < 5L ? 1L : 0L) != 0L && ((1L << (int) (tempVal1 & 31L)) & 21L) != 0L) {
                                    index2 = (index2 + 1L) & 0xffffffffL;
                                }
                                status1 = (status1 + 1L) & 0xffffffffL;
                            } while ((entryCount & 0xffffffffL) != status1);
                            if (index2 < 4L) {
                                status1 = 32L;
                                return status1;
                            }
                            isZero1 = fLE(framePtr + 112L, CC_0p1) != 0L ? 1L : 0L;
                            if (isZero1 != 0L) {
                                status1 = 24L;
                                return status1;
                            }
                            longVal = callIndirect3(getEntryFn, dataPtr, entryCount & 0xffffffffL, tempVal2);
                            putI64(framePtr + 152L, getI64(longVal + 24L));
                            putI64(framePtr + 144L, getI64(longVal + 16L));
                            longVal = callIndirect3(getEntryFn, dataPtr, entryCount & 0xffffffffL, getU32(framePtr + 28L));
                            putI64(framePtr + 136L, getI64(longVal + 24L));
                            putI64(framePtr + 128L, getI64(longVal + 16L));
                            isZero1 = fLE(framePtr + 144L, framePtr + 128L) != 0L ? 1L : 0L;
                            if (isZero1 != 0L) {
                                putI64(framePtr + 192L, dataPtr);
                                putI64(framePtr + 200L, getEntryFn);
                                isZero2 = fLT(asymptotePtr, framePtr + 112L) != 0L ? 1L : 0L;
                                if (isZero2 != 0L) {
                                    putI64(framePtr + 256L, dataPtr);
                                    putI64(framePtr + 264L, getEntryFn);
                                    sRegression(framePtr + 256L, entryCount & 0xffffffffL, FPTR_RegStatsGetEntry, 0L, framePtr + 176L, framePtr + 160L, 0L, 0L, 0L);
                                    status2 = fidiv(framePtr + 160L, framePtr + 176L, asymptotePtr);
                                    if (1L < (status2 & 0xffffffffL)) {
                                        putI32(asymptotePtr + 8L, 0L);
                                        putI32(asymptotePtr + 12L, 0L);
                                        putI32(asymptotePtr, 1L);
                                        putI32(asymptotePtr + 4L, 0L);
                                    }
                                    isZero2 = fZero(asymptotePtr) != 0L ? 1L : 0L;
                                    if (isZero2 == 0L) {
                                        putI32(asymptotePtr, -getI32(asymptotePtr));
                                    }
                                }
                                strPtr = FPTR_sRegTransformLogLogistic;
                            } else {
                                putI64(framePtr + 192L, dataPtr);
                                putI64(framePtr + 200L, getEntryFn);
                                isZero2 = fLT(framePtr + 96L, asymptotePtr) != 0L ? 1L : 0L;
                                if (isZero2 != 0L) {
                                    putI64(framePtr + 256L, dataPtr);
                                    putI64(framePtr + 264L, getEntryFn);
                                    sRegression(framePtr + 256L, entryCount & 0xffffffffL, FPTR_RegStatsGetEntry, 0L, framePtr + 176L, framePtr + 160L, 0L, 0L, 0L);
                                    status2 = fidiv(framePtr + 160L, framePtr + 176L, asymptotePtr);
                                    if (1L < (status2 & 0xffffffffL)) {
                                        putI32(asymptotePtr + 8L, 0L);
                                        putI32(asymptotePtr + 12L, 0L);
                                        putI32(asymptotePtr, 1L);
                                        putI32(asymptotePtr + 4L, 0L);
                                    }
                                    isZero2 = fZero(asymptotePtr) != 0L ? 1L : 0L;
                                    if (isZero2 == 0L) {
                                        putI32(asymptotePtr, -getI32(asymptotePtr));
                                    }
                                }
                                strPtr = FPTR_sRegTransformLogistic;
                            }
                            fimax(CC_0p05, asymptotePtr, asymptotePtr);
                            putI64(framePtr + 248L, getI64(asymptotePtr + 8L));
                            putI64(framePtr + 240L, getI64(asymptotePtr));
                            dataPtr4 = framePtr + 192L;
                            step = 2;
                            continue stepLoop;
                        } else {
                            if ((modelType & 0xffffffffL) == 0L) {
                                isZero1 = 1L;
                                strPtr = getEntryFn;
                                dataPtr4 = dataPtr;
                                step = 2;
                                continue stepLoop;
                            } else {
                                if (((modelType - 1L) & 0xffffffffL) < 5L) {
                                    isZero1 = 1L;
                                    strPtr = FPTR_sRegTransformLnX;
                                    dataPtr4 = framePtr + 192L;
                                    putI64(framePtr + 192L, dataPtr);
                                    putI64(framePtr + 200L, getEntryFn);
                                    switch ((int) (modelType)) {
                                        case 1: {
                                            step = 2;
                                            continue stepLoop;
                                        }
                                        case 3: {
                                            step = 3;
                                            continue stepLoop;
                                        }
                                        case 5: {
                                            strPtr = FPTR_sRegTransformInvY;
                                            step = 1;
                                            continue stepLoop;
                                        }
                                        default: {
                                            strPtr = FPTR_sRegTransformExpY;
                                            step = 1;
                                            continue stepLoop;
                                        }
                                    }
                                } else {
                                    step = 3;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 1: {
                        isZero1 = 1L;
                        dataPtr4 = framePtr + 192L;
                        putI64(framePtr + 192L, dataPtr);
                        putI64(framePtr + 200L, getEntryFn);
                        step = 2;
                        continue stepLoop;
                    }
                    case 2: {
                        sMeanInternal(dataPtr4, (long) (int) entryCount, strPtr, 1L, framePtr + 256L, framePtr + 160L, framePtr + 80L, 0L, 0L, 0L, 0L, framePtr + 112L, 0L, 0L);
                        sMeanInternal(dataPtr4, (long) (int) entryCount, strPtr, 0L, framePtr + 176L, framePtr + 144L, framePtr + 64L, 0L, 0L, 0L, 0L, framePtr + 96L, 0L, 0L);
                        putI64(framePtr + 56L, 0L);
                        putI64(framePtr + 48L, 1L);
                        if ((entryCount & 0xffffffffL) == 0L) {
                            index1 = 0L;
                        } else {
                            status1 = 0L;
                            index1 = 0L;
                            do {
                                dataPtr2 = callIndirect3(strPtr, dataPtr4, entryCount & 0xffffffffL, status1);
                                firadd(dataPtr2, framePtr + 80L, framePtr + 128L, 1L);
                                firadd(dataPtr2 + 16L, framePtr + 64L, framePtr + 32L, 1L);
                                fimul(framePtr + 128L, framePtr + 32L, framePtr + 128L);
                                if (((getI64(framePtr + 128L) + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((getI64(framePtr + 128L) + 1L) & 31L)) & 21L) != 0L) {
                                    firadd(framePtr + 128L, framePtr + 48L, framePtr + 48L, 0L);
                                    index1 = (long) (int) (index1 + 1L);
                                }
                                status1 = (status1 + 1L) & 0xffffffffL;
                            } while ((entryCount & 0xffffffffL) != status1);
                        }
                        fIntToCC(index1, framePtr + 128L);
                        if (outCorrPtr == 0L && expBasePtr == 0L) {
                            status1 = 0L;
                        } else {
                            if (expBasePtr == 0L) {
                                status1 = 0L;
                            } else {
                                fradd(framePtr + 128L, CC_N1, expBasePtr, 0L);
                                status2 = fidiv(framePtr + 48L, expBasePtr, expBasePtr);
                                status1 = fNorm_L((long) (int) status2, expBasePtr) & 0xffffffffL;
                            }
                            if (outCorrPtr != 0L && comma(fimul(framePtr + 112L, framePtr + 96L, framePtr + 32L), status1 < 2L ? 1L : 0L) != 0L) {
                                flag = fisqrt(framePtr + 32L, framePtr + 32L) & 0xffL;
                                status1 = flag;
                                if (status1 < 2L) {
                                    status2 = fidiv(framePtr + 48L, framePtr + 32L, outCorrPtr);
                                    status1 = fNorm_L((long) (int) status2, outCorrPtr) & 0xffffffffL;
                                }
                            }
                        }
                        if (outSlopePtr != 0L || outInterceptPtr != 0L) {
                            isZero2 = fZero(framePtr + 112L) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                status1 = 32L;
                                return status1;
                            }
                            if (status1 < 2L) {
                                status2 = fidiv(framePtr + 48L, framePtr + 112L, framePtr + 112L);
                                status1 = status2 & 0xffffffffL;
                            }
                            if (outSlopePtr != 0L) {
                                putI64(outSlopePtr + 8L, getI64(framePtr + 120L));
                                putI64(outSlopePtr, getI64(framePtr + 112L));
                                if ((modelType & 0xffffffffL) == 4L && status1 < 2L) {
                                    isZero2 = fiexpm(outSlopePtr, outSlopePtr, 0L) != 0L ? 1L : 0L;
                                    status1 = isZero2;
                                }
                                fNorm_L(0L, outSlopePtr);
                            }
                            if (outInterceptPtr != 0L) {
                                fimul(framePtr + 112L, framePtr + 80L, framePtr + 80L);
                                firadd(framePtr + 64L, framePtr + 80L, outInterceptPtr, 1L);
                                if ((modelType & 0xffffffffL) < 7L && ((1L << (int) (modelType & 31L)) & 92L) != 0L && status1 < 2L) {
                                    isZero2 = fiexpm(outInterceptPtr, outInterceptPtr, 0L) != 0L ? 1L : 0L;
                                    status1 = isZero2;
                                }
                                if (isZero1 == 0L && comma(isZero1 = fZero(outInterceptPtr) != 0L ? 1L : 0L, isZero1 == 0L ? 1L : 0L) != 0L) {
                                    putI32(outInterceptPtr, -getI64(outInterceptPtr));
                                }
                                fNorm_L(0L, outInterceptPtr);
                            }
                        }
                        if (asymptotePtr != 0L) {
                            fNorm_L(1L, asymptotePtr);
                        }
                        return status1;
                    }
                    case 3: {
                        strPtr = FPTR_sRegTransformPowXY;
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

    long sRegTransformLogLogistic(long entryPtr) {
        long dataPtr1 = 0L;
        long dataPtr2 = 0L;
        long tempVal = 0L;
        dataPtr2 = callIndirect1(getI64(entryPtr + 8L), getI64(entryPtr));
        tempVal = getI64(dataPtr2);
        putI64(entryPtr + 24L, getI64(dataPtr2 + 8L));
        putI64(entryPtr + 16L, tempVal);
        dataPtr1 = entryPtr + 32L;
        fidiv(entryPtr + 48L, dataPtr2 + 16L, dataPtr1);
        firadd(dataPtr1, CC_N1, dataPtr1, 0L);
        filn(dataPtr1, dataPtr1);
        return entryPtr + 16L;
    }

    long sRegTransformLogistic(long entryPtr) {
        long dataPtr1 = 0L;
        long isZero = 0L;
        long dataPtr2 = 0L;
        long tempVal = 0L;
        dataPtr2 = callIndirect1(getI64(entryPtr + 8L), getI64(entryPtr));
        tempVal = getI64(dataPtr2);
        dataPtr1 = entryPtr + 32L;
        putI64(entryPtr + 24L, getI64(dataPtr2 + 8L));
        putI64(entryPtr + 16L, tempVal);
        fidiv(entryPtr + 48L, dataPtr2 + 16L, dataPtr1);
        if (getI32(entryPtr + 32L) == 3L) {
            putU32(dataPtr1, 1L);
        }
        isZero = fZero(dataPtr1) != 0L ? 1L : 0L;
        if (isZero == 0L) {
            putU32(dataPtr1, -getU32(dataPtr1));
        }
        firadd(CC_1, dataPtr1, dataPtr1, 0L);
        filn(dataPtr1, dataPtr1);
        return entryPtr + 16L;
    }

    long sRegTransformLnX(long entryPtr) {
        long dataPtr = 0L;
        long tempVal = 0L;
        dataPtr = callIndirect1(getI64(entryPtr + 8L), getI64(entryPtr));
        filn(dataPtr, entryPtr + 16L);
        tempVal = getI64(dataPtr + 16L);
        putI64(entryPtr + 40L, getI64(dataPtr + 24L));
        putI64(entryPtr + 32L, tempVal);
        return entryPtr + 16L;
    }

    long sRegTransformExpY(long entryPtr) {
        long dataPtr = 0L;
        long tempVal = 0L;
        dataPtr = callIndirect1(getI64(entryPtr + 8L), getI64(entryPtr));
        filn(dataPtr + 16L, entryPtr + 32L);
        tempVal = getI64(dataPtr);
        putI64(entryPtr + 24L, getI64(dataPtr + 8L));
        putI64(entryPtr + 16L, tempVal);
        return entryPtr + 16L;
    }

    long sRegTransformInvY(long entryPtr) {
        long dataPtr = 0L;
        long tempVal = 0L;
        dataPtr = callIndirect1(getI64(entryPtr + 8L), getI64(entryPtr));
        fidiv(CC_1, dataPtr, entryPtr + 16L);
        tempVal = getI64(dataPtr + 16L);
        putI64(entryPtr + 40L, getI64(dataPtr + 24L));
        putI64(entryPtr + 32L, tempVal);
        return entryPtr + 16L;
    }

    long sRegTransformPowXY(long entryPtr) {
        long dataPtr = 0L;
        dataPtr = callIndirect1(getI64(entryPtr + 8L), getI64(entryPtr));
        filn(dataPtr, entryPtr + 16L);
        filn(dataPtr + 16L, entryPtr + 32L);
        return entryPtr + 16L;
    }

    long sPredictY(long slopePtr, long interceptPtr, long modelType, long xValPtr, long outYValPtr) {
        long flag = 0L;
        long intVal = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long dataPtr = 0L;
        long tempVal = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    tempVal = getI64(xValPtr);
                    putI64(outYValPtr + 8L, getI64(xValPtr + 8L));
                    putI64(outYValPtr, tempVal);
                    switch ((int) (slopePtr)) {
                        case 1: {
                            status2 = filn(outYValPtr, outYValPtr);
                            if (1L < (status2 & 0xffffffffL)) {
                                return status2;
                            }
                            step = 1;
                            continue stepLoop;
                        }
                        case 0: {
                            step = 1;
                            continue stepLoop;
                        }
                        case 2: {
                            status2 = fimul(outYValPtr, interceptPtr, outYValPtr);
                            if (1L < (status2 & 0xffffffffL)) {
                                return status2;
                            }
                            flag = fiexpm(outYValPtr, outYValPtr, 0L) != 0L ? 1L : 0L;
                            if (1L < flag) {
                                return flag;
                            }
                            status2 = fimul(outYValPtr, modelType, outYValPtr);
                            intVal = (long) (int) status2;
                            status1 = fNorm_L(intVal, outYValPtr) & 0xffffffffL;
                            return status1;
                        }
                        case 3: {
                            dataPtr = outYValPtr;
                            step = 2;
                            continue stepLoop;
                        }
                        case 4: {
                            dataPtr = interceptPtr;
                            interceptPtr = outYValPtr;
                            step = 2;
                            continue stepLoop;
                        }
                        case 5: {
                            status2 = fidiv(interceptPtr, outYValPtr, outYValPtr);
                            if (1L < (status2 & 0xffffffffL)) {
                                return status2;
                            }
                            status2 = firadd(outYValPtr, modelType, outYValPtr, 0L);
                            intVal = (long) (int) status2;
                            status1 = fNorm_L(intVal, outYValPtr) & 0xffffffffL;
                            return status1;
                        }
                        default: {
                            status2 = 24L;
                            return status2;
                        }
                    }
                }
                case 1: {
                    status2 = fimul(outYValPtr, interceptPtr, outYValPtr);
                    if ((status2 & 0xffffffffL) >= 2L) {
                        return status2;
                    }
                    status2 = firadd(outYValPtr, modelType, outYValPtr, 0L);
                    intVal = (long) (int) status2;
                    status1 = fNorm_L(intVal, outYValPtr) & 0xffffffffL;
                    return status1;
                }
                case 2: {
                    status2 = fpow(dataPtr, interceptPtr, outYValPtr, 1L);
                    if ((status2 & 0xffffffffL) >= 2L) {
                        return status2;
                    }
                    status2 = fimul(outYValPtr, modelType, outYValPtr);
                    intVal = (long) (int) status2;
                    status1 = fNorm_L(intVal, outYValPtr) & 0xffffffffL;
                    return status1;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    long sPredictX(long slopePtr, long interceptPtr, long modelType, long yValPtr, long outXValPtr) {
        long flag = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long status3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        switch ((int) (slopePtr)) {
                            case 0: {
                                status3 = firadd(outXValPtr, modelType, yValPtr, 1L);
                                status1 = status3 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 1: {
                                status3 = firadd(outXValPtr, modelType, yValPtr, 1L);
                                if (1L < (status3 & 0xffffffffL) || comma(status3 = fidiv(yValPtr, interceptPtr, yValPtr), 1L < (status3 & 0xffffffffL) ? 1L : 0L) != 0L) {
                                    return status3;
                                }
                                flag = fiexpm(yValPtr, yValPtr, 0L) != 0L ? 1L : 0L;
                                status1 = flag;
                                status1 = fNorm_L((long) (int) status1, yValPtr) & 0xffffffffL;
                                status3 = status1;
                                return status3;
                            }
                            case 2: {
                                status3 = firadd(outXValPtr, modelType, yValPtr, 1L);
                                if (1L < (status3 & 0xffffffffL)) {
                                    return status3;
                                }
                                status3 = filn(yValPtr, yValPtr);
                                status1 = status3 & 0xffffffffL;
                                step = 1;
                                continue stepLoop;
                            }
                            case 3: {
                                status2 = fidiv(outXValPtr, modelType, yValPtr);
                                if ((status2 & 0xffffffffL) < 2L) {
                                    finthroot(yValPtr, interceptPtr, yValPtr);
                                }
                                step = 2;
                                continue stepLoop;
                            }
                            case 4: {
                                step = 2;
                                continue stepLoop;
                            }
                            case 5: {
                                status3 = firadd(outXValPtr, modelType, yValPtr, 1L);
                                if (1L < (status3 & 0xffffffffL)) {
                                    return status3;
                                }
                                status3 = fimul(yValPtr, interceptPtr, yValPtr);
                                status1 = status3 & 0xffffffffL;
                                status1 = fNorm_L((long) (int) status1, yValPtr) & 0xffffffffL;
                                status3 = status1;
                                return status3;
                            }
                            default: {
                                status3 = 24L;
                                return status3;
                            }
                        }
                    }
                    case 1: {
                        if (1L < status1) {
                            return status3;
                        }
                        status2 = fidiv(yValPtr, interceptPtr, yValPtr);
                        status1 = status2 & 0xffffffffL;
                        status1 = fNorm_L((long) (int) status1, yValPtr) & 0xffffffffL;
                        status3 = status1;
                        return status3;
                    }
                    case 2: {
                        status3 = firadd(outXValPtr, modelType, yValPtr, 1L);
                        if (1L < (status3 & 0xffffffffL) || comma(status3 = filn(yValPtr, yValPtr), 1L < (status3 & 0xffffffffL) ? 1L : 0L) != 0L || comma(status3 = filn(interceptPtr, framePtr + 24L), 1L < (status3 & 0xffffffffL) ? 1L : 0L) != 0L) {
                            return status3;
                        }
                        interceptPtr = framePtr + 24L;
                        status2 = fidiv(yValPtr, interceptPtr, yValPtr);
                        status1 = status2 & 0xffffffffL;
                        status1 = fNorm_L((long) (int) status1, yValPtr) & 0xffffffffL;
                        status3 = status1;
                        return status3;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long sRegressionPoly(long dataPtr, long entryCount, long getEntryFn, long outC0Ptr, long outC1Ptr, long outC2Ptr, long outR2Ptr) {
        long offset1 = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long dataPtr1 = 0L;
        long dataPtr2 = 0L;
        long dataPtr3 = 0L;
        long isZero = 0L;
        long flag = 0L;
        long status = 0L;
        long dataPtr4 = 0L;
        long offset2 = 0L;
        long tempVal3 = 0L;
        long index1 = 0L;
        long index2 = 0L;
        long index3 = 0L;
        long index4 = 0L;
        long index5 = 0L;
        long index6 = 0L;
        long dataPtr5 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(256);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        if (outR2Ptr != 0L) {
                            putI32(outR2Ptr + 8L, 0L);
                            putI32(outR2Ptr + 12L, 0L);
                            putI32(outR2Ptr, 0L);
                            putI32(outR2Ptr + 4L, 0L);
                        }
                        index1 = 0L;
                        step = 1;
                        continue stepLoop;
                    }
                    case 1: {
                        do {
                            dataPtr4 = outC0Ptr + index1 * 16L;
                            putU32(dataPtr4 + 8L, 0L);
                            putU32(dataPtr4 + 12L, 0L);
                            putU32(dataPtr4, 0L);
                            putU32(dataPtr4 + 4L, 0L);
                            tempVal3 = getU32(outC1Ptr);
                            index1 = (index1 + 1L) & 0xffffffffL;
                        } while (index1 <= getU32(outC1Ptr));
                        index1 = 0xffffffffL;
                        do {
                            index1 = (index1 + 1L) & 0xffffffffL;
                            dataPtr2 = outC2Ptr + (long) (int) ((index1 >> 1) + (tempVal3 + 2L) * ((index1 >> 1) + (index1 & 1L))) * 16L;
                            putI64(dataPtr2 + 8L, 0L);
                            putI64(dataPtr2, 1L);
                            tempVal3 = getU32(outC1Ptr);
                        } while (index1 < ((getU32(outC1Ptr) * 2L + 2L) & 0xffffffffL));
                        index1 = 0L;
                        do {
                            dataPtr2 = outC2Ptr + (long) (int) (tempVal3 + (tempVal3 + 2L) * index1 + 1L) * 16L;
                            putI64(dataPtr2 + 8L, 0L);
                            putI64(dataPtr2, 1L);
                            index2 = getU32(outC1Ptr);
                            tempVal3 = index2;
                            index1 = (index1 + 1L) & 0xffffffffL;
                        } while (index1 <= index2);
                        status = 24L;
                        if (index2 < 2L || (entryCount & 0xffffffffL) < index2) {
                            return status;
                        }
                        if ((entryCount & 0xffffffffL) != 0L) {
                            index1 = 0L;
                            do {
                                dataPtr4 = callIndirect3(getEntryFn, dataPtr, entryCount & 0xffffffffL, index1);
                                index2 = 0xffffffffL;
                                putI64(framePtr + 72L, 0x100000000000000L);
                                putI64(framePtr + 64L, 1L);
                                tempVal3 = getU32(outC1Ptr);
                                do {
                                    index2 = (index2 + 1L) & 0xffffffffL;
                                    dataPtr5 = outC2Ptr + (long) (int) ((index2 >> 1) + (tempVal3 + 2L) * ((index2 >> 1) + (index2 & 1L))) * 16L;
                                    firadd(dataPtr5, framePtr + 64L, dataPtr5, 0L);
                                    if (index2 <= getU32(outC1Ptr)) {
                                        fimul(dataPtr4 + 16L, framePtr + 64L, framePtr + 48L);
                                        dataPtr5 = outC2Ptr + (long) (int) (getU32(outC1Ptr) + (getU32(outC1Ptr) + 2L) * index2 + 1L) * 16L;
                                        firadd(dataPtr5, framePtr + 48L, dataPtr5, 0L);
                                    }
                                    fimul(framePtr + 64L, dataPtr4, framePtr + 64L);
                                    tempVal3 = getU32(outC1Ptr);
                                } while (index2 < ((getU32(outC1Ptr) * 2L + 2L) & 0xffffffffL));
                                index1 = (index1 + 1L) & 0xffffffffL;
                            } while (index1 != (entryCount & 0xffffffffL));
                        }
                        index1 = 0L;
                        do {
                            index2 = 0L;
                            do {
                                index6 = ((((index1 + index2) & 0xffffffffL)) >> 1) & 0xffffffffL;
                                index4 = (long) (int) (tempVal3 + 2L);
                                dataPtr2 = outC2Ptr + (long) (int) (index6 + (index6 + ((index1 + index2) & 1L)) * index4) * 16L;
                                status = getI64(dataPtr2);
                                index4 = (long) (int) (index2 + index1 * index4);
                                index2 = (index2 + 1L) & 0xffffffffL;
                                dataPtr3 = outC2Ptr + index4 * 16L;
                                putI64(dataPtr3 + 8L, getI64(dataPtr2 + 8L));
                                putI64(dataPtr3, status);
                                index6 = getU32(outC1Ptr);
                                tempVal3 = index6;
                            } while (index2 <= index6);
                            index1 = (index1 + 1L) & 0xffffffffL;
                        } while (index1 <= index6);
                        index1 = 0L;
                        step = 2;
                        continue stepLoop;
                    }
                    case 2: {
                        index2 = (index1 + 1L) & 0xffffffffL;
                        index6 = index1;
                        if (index2 <= (tempVal3 & 0xffffffffL)) {
                            offset2 = outC2Ptr + (long) (int) (index1 + (tempVal3 + 2L) * index1) * 16L;
                            index3 = index2;
                            tempVal2 = index1;
                            do {
                                isZero = fMLT(offset2, outC2Ptr + (long) (int) (index1 + (tempVal3 + 2L) * index3) * 16L) != 0L ? 1L : 0L;
                                tempVal1 = getU32(outC1Ptr);
                                tempVal3 = tempVal1;
                                index6 = index3;
                                if (isZero == 0L) {
                                    index6 = tempVal2;
                                }
                                index4 = (long) (int) ((tempVal1 + 2L) * index3);
                                index3 = (index3 + 1L) & 0xffffffffL;
                                offset1 = outC2Ptr + (long) (int) (index1 + index4) * 16L;
                                if (isZero == 0L) {
                                    offset1 = offset2;
                                }
                                offset2 = offset1;
                                tempVal2 = index6;
                            } while (index3 <= tempVal1);
                        }
                        if (index1 != index6 && comma(index3 = index1, index1 <= ((tempVal3 + 1L) & 0xffffffffL) ? 1L : 0L) != 0L) {
                            do {
                                index4 = (long) (int) (tempVal3 + 2L);
                                tempVal2 = (index3 + index1 * index4) & 0xffffffffL;
                                tempVal3 = (-((tempVal2 >> 31) & 0xffffffffL) & 0xfffffff000000000L) | (tempVal2 << 4);
                                dataPtr1 = outC2Ptr + tempVal3;
                                putI64(framePtr + 72L, getI64(dataPtr1 + 8L));
                                putI64(framePtr + 64L, getI64(dataPtr1));
                                dataPtr2 = outC2Ptr + (long) (int) (index3 + index4 * index6) * 16L;
                                status = getI64(dataPtr2);
                                dataPtr3 = outC2Ptr + tempVal3;
                                putI64(dataPtr3 + 8L, getI64(dataPtr2 + 8L));
                                putI64(dataPtr3, status);
                                dataPtr1 = outC2Ptr + (long) (int) (index3 + (getU32(outC1Ptr) + 2L) * index6) * 16L;
                                putI64(dataPtr1 + 8L, getI64(framePtr + 72L));
                                putI64(dataPtr1, getI64(framePtr + 64L));
                                tempVal3 = getU32(outC1Ptr);
                                index3 = (index3 + 1L) & 0xffffffffL;
                            } while (index3 <= ((getU32(outC1Ptr) + 1L) & 0xffffffffL));
                        }
                        index6 = index2;
                        if (index2 <= (tempVal3 & 0xffffffffL)) {
                            step = 4;
                            continue stepLoop;
                        } else {
                            step = 3;
                            continue stepLoop;
                        }
                    }
                    case 3: {
                        index1 = index2;
                        if (index2 <= (tempVal3 & 0xffffffffL)) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            index5 = tempVal3;
                            if ((long) (int) tempVal3 < 0L) {
                                step = 6;
                                continue stepLoop;
                            } else {
                                step = 7;
                                continue stepLoop;
                            }
                        }
                    }
                    case 4: {
                        index4 = (long) (int) (tempVal3 + 2L);
                        status = fidiv(outC2Ptr + (long) (int) (index1 + index4 * index6) * 16L, outC2Ptr + (long) (int) (index1 + index4 * index1) * 16L, framePtr + 64L);
                        if (1L < (status & 0xffffffffL)) {
                            step = 5;
                            continue stepLoop;
                        } else {
                            isZero = fZero(framePtr + 64L) != 0L ? 1L : 0L;
                            if (isZero == 0L) {
                                putI64(framePtr + 64L, (((getI32(framePtr + 68L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 64L) & 0xffffffffL));
                            }
                            dataPtr2 = outC2Ptr + (long) (int) (index1 + (getU32(outC1Ptr) + 2L) * index6) * 16L;
                            putI64(dataPtr2 + 8L, 0L);
                            putI64(dataPtr2, 1L);
                            tempVal3 = getU32(outC1Ptr);
                            index3 = index2;
                            if (index2 <= ((getU32(outC1Ptr) + 1L) & 0xffffffffL)) {
                                do {
                                    fimul(outC2Ptr + (long) (int) (index3 + index1 * (tempVal3 + 2L)) * 16L, framePtr + 64L, framePtr + 48L);
                                    dataPtr4 = outC2Ptr + (long) (int) (index3 + index6 * (getU32(outC1Ptr) + 2L)) * 16L;
                                    firadd(dataPtr4, framePtr + 48L, dataPtr4, 0L);
                                    tempVal3 = getU32(outC1Ptr);
                                    index3 = (index3 + 1L) & 0xffffffffL;
                                } while (index3 <= ((getU32(outC1Ptr) + 1L) & 0xffffffffL));
                            }
                            index6 = (index6 + 1L) & 0xffffffffL;
                            if (index6 <= (tempVal3 & 0xffffffffL)) {
                                step = 4;
                                continue stepLoop;
                            } else {
                                step = 3;
                                continue stepLoop;
                            }
                        }
                    }
                    case 5: {
                        index2 = getU32(outC1Ptr);
                        index1 = 0L;
                        putU32(outC1Ptr, index2 - 1L);
                        if (1L >= ((index2 - 1L) & 0xffffffffL)) {
                            status = 26L;
                            return status;
                        }
                        step = 1;
                        continue stepLoop;
                    }
                    case 6: {
                        index1 = 0L;
                        do {
                            fNorm_L(1L, outC0Ptr + index1 * 16L);
                            index1 = (index1 + 1L) & 0xffffffffL;
                        } while (index1 <= getU32(outC1Ptr));
                        if (outR2Ptr != 0L) {
                            putI64(framePtr + 56L, 0L);
                            putI64(framePtr + 48L, 1L);
                            putI64(framePtr + 64L, getI64(framePtr + 48L));
                            putI64(framePtr + 72L, getI64(framePtr + 56L));
                            if ((entryCount & 0xffffffffL) != 0L) {
                                index1 = 0L;
                                do {
                                    dataPtr4 = callIndirect3(getEntryFn, dataPtr, entryCount & 0xffffffffL, index1);
                                    firadd(dataPtr4 + 16L, framePtr + 48L, framePtr + 48L, 0L);
                                    putI64(framePtr + 40L, 0L);
                                    putI64(framePtr + 32L, 1L);
                                    if (0L < (long) (int) getU32(outC1Ptr)) {
                                        index4 = (long) (int) (getU32(outC1Ptr) + 1L);
                                        dataPtr5 = outC0Ptr;
                                        do {
                                            fimul(framePtr + 32L, dataPtr4, framePtr + 32L);
                                            firadd(framePtr + 32L, dataPtr5, framePtr + 32L, 0L);
                                            index4 = (long) (int) (index4 - 1L);
                                            dataPtr5 = dataPtr5 + 16L;
                                        } while (1L < index4);
                                    }
                                    fNorm_L(0L, framePtr + 32L);
                                    firadd(dataPtr4 + 16L, framePtr + 32L, framePtr + 32L, 1L);
                                    fimul(framePtr + 32L, framePtr + 32L, framePtr + 32L);
                                    firadd(framePtr + 32L, framePtr + 64L, framePtr + 64L, 0L);
                                    index1 = (index1 + 1L) & 0xffffffffL;
                                } while (index1 != (entryCount & 0xffffffffL));
                            }
                            fIntToCC((long) (int) entryCount, framePtr + 32L);
                            fidiv(framePtr + 48L, framePtr + 32L, framePtr + 48L);
                            putI64(framePtr + 24L, 0L);
                            putI64(framePtr + 16L, 1L);
                            if ((entryCount & 0xffffffffL) != 0L) {
                                index1 = 0L;
                                do {
                                    offset2 = callIndirect3(getEntryFn, dataPtr, entryCount & 0xffffffffL, index1);
                                    firadd(offset2 + 16L, framePtr + 48L, framePtr + 32L, 1L);
                                    fimul(framePtr + 32L, framePtr + 32L, framePtr + 32L);
                                    firadd(framePtr + 32L, framePtr + 16L, framePtr + 16L, 0L);
                                    index1 = (index1 + 1L) & 0xffffffffL;
                                } while ((entryCount & 0xffffffffL) != index1);
                            }
                            fidiv(framePtr + 64L, framePtr + 16L, framePtr + 64L);
                            firadd(CC_1, framePtr + 64L, framePtr + 64L, 1L);
                            flag = fisqrt(framePtr + 64L, outR2Ptr) & 0xffL;
                            fNorm_L(flag, outR2Ptr);
                        }
                        status = 0L;
                        return status;
                    }
                    case 7: {
                        index4 = (long) (int) index5;
                        dataPtr2 = outC2Ptr + (long) (int) (tempVal3 + (tempVal3 + 2L) * index4 + 1L) * 16L;
                        status = getI64(dataPtr2);
                        dataPtr4 = outC0Ptr + index5 * 16L;
                        index1 = (index4 + 1L) & 0xffffffffL;
                        putI64(dataPtr4 + 8L, getI64(dataPtr2 + 8L));
                        putI64(dataPtr4, status);
                        index2 = getU32(outC1Ptr);
                        if (index1 <= index2) {
                            do {
                                fimul(outC2Ptr + (long) (int) (index1 + index4 * (index2 + 2L)) * 16L, outC0Ptr + index1 * 16L, framePtr + 64L);
                                firadd(dataPtr4, framePtr + 64L, dataPtr4, 1L);
                                index2 = getU32(outC1Ptr);
                                index1 = (index1 + 1L) & 0xffffffffL;
                            } while (index1 <= index2);
                        }
                        status = fidiv(dataPtr4, outC2Ptr + (long) (int) (index4 + (index2 + 2L) * index4) * 16L, dataPtr4);
                        if (1L < (status & 0xffffffffL)) {
                            step = 5;
                            continue stepLoop;
                        } else {
                            if (index5 < 1L) {
                                step = 6;
                                continue stepLoop;
                            } else {
                                tempVal3 = getU32(outC1Ptr);
                                index5 = index5 - 1L;
                                step = 7;
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

    long sPredictYPoly(long coeffPtr, long xValPtr, long degree, long outYValPtr) {
        long status = 0L;
        putU32(outYValPtr + 8L, 0L);
        putU32(outYValPtr + 12L, 0L);
        putU32(outYValPtr, 1L);
        putU32(outYValPtr + 4L, 0L);
        if (0L < (long) (int) coeffPtr) {
            status = (long) (int) (coeffPtr + 1L);
            do {
                fimul(outYValPtr, degree, outYValPtr);
                firadd(outYValPtr, xValPtr, outYValPtr, 0L);
                status = (long) (int) (status - 1L);
                xValPtr = xValPtr + 16L;
            } while (1L < status);
        }
        status = (long) (int) fNorm_L(0L, outYValPtr);
        return status;
    }

    long sPredictXPoly(long coeffPtr, long yValPtr, long guessPtr, long outXValPtr, long outOtherRootPtr) {
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            putU32(framePtr + 32L, coeffPtr);
            putI64(framePtr + 40L, yValPtr);
            putI64(framePtr + 48L, guessPtr);
            status = (long) (int) ZeroFinder(CC_0, CC_0, FPTR_sPolyEvalFunc, outOtherRootPtr, framePtr + 32L, outXValPtr, framePtr + 16L);
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long sPolyEvalFunc(long xValPtr, long contextPtr, long outDiffPtr) {
        long isZero = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long dataPtr = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            status1 = getI32(contextPtr);
            dataPtr = getI64(contextPtr + 8L);
            putU32(outDiffPtr + 8L, 0L);
            putU32(outDiffPtr + 12L, 0L);
            putU32(outDiffPtr, 1L);
            putU32(outDiffPtr + 4L, 0L);
            if (0L < status1) {
                status1 = (long) (int) (status1 + 1L);
                do {
                    fimul(outDiffPtr, xValPtr, outDiffPtr);
                    firadd(outDiffPtr, dataPtr, outDiffPtr, 0L);
                    status1 = (long) (int) (status1 - 1L);
                    dataPtr = dataPtr + 16L;
                } while (1L < status1);
            }
            fNorm_L(0L, outDiffPtr);
            dataPtr = getI64(contextPtr + 16L);
            putI64(framePtr + 32L, getI64(dataPtr + 8L));
            putU32(framePtr + 24L, getU32(dataPtr));
            putU32(framePtr + 28L, getU32(dataPtr + 4L));
            if (getU32(framePtr + 24L) == 3L) {
                putU32(framePtr + 24L, 1L);
            }
            isZero = fZero(framePtr + 24L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putU32(framePtr + 24L, -getU32(framePtr + 24L));
            }
            status2 = firadd(outDiffPtr, framePtr + 24L, outDiffPtr, 0L);
            status1 = (long) (int) fNorm_L((long) (int) status2, outDiffPtr);
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    void cc_qsort(long dataPtr, long leftIdx, long rightIdx, long getEntryFn, long swapFn, long varIndex) {
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long intVal = 0L;
        long isLessOrEqual1 = 0L;
        long isLessOrEqual2 = 0L;
        long realPtr = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        long index1 = 0L;
        long tempVal5 = 0L;
        long index2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        step = 1;
                        continue stepLoop;
                    }
                    case 1: {
                        tempVal4 = ((long) (int) (swapFn + varIndex) >> 1) & 0xffffffffL;
                        tempVal5 = varIndex & 0xffffffffL;
                        tempVal3 = swapFn & 0xffffffffL;
                        step = 2;
                        continue stepLoop;
                    }
                    case 2: {
                        realPtr = callIndirect3(rightIdx, dataPtr, leftIdx & 0xffffffffL, tempVal4);
                        putI64(framePtr + 24L, getI64(realPtr + 8L));
                        putI32(framePtr + 16L, getI32(realPtr));
                        putI32(framePtr + 20L, getI32(realPtr + 4L));
                        index2 = tempVal3;
                        do {
                            realPtr = callIndirect3(rightIdx, dataPtr, leftIdx & 0xffffffffL, index2);
                            isLessOrEqual1 = fLE(framePtr + 16L, realPtr) != 0L ? 1L : 0L;
                            index2 = (index2 + 1L) & 0xffffffffL;
                        } while (isLessOrEqual1 == 0L);
                        tempVal3 = (isLessOrEqual1 + 1L) & 1L;
                        intVal = (long) (int) (tempVal3 - tempVal4);
                        tempVal3 = (tempVal3 + index2 - 1L) & 0xffffffffL;
                        index1 = tempVal5;
                        do {
                            realPtr = callIndirect3(rightIdx, dataPtr, leftIdx & 0xffffffffL, index1);
                            isLessOrEqual2 = fLE(realPtr, framePtr + 16L) != 0L ? 1L : 0L;
                            index1 = (index1 - 1L) & 0xffffffffL;
                        } while (isLessOrEqual2 == 0L);
                        tempVal2 = (isLessOrEqual2 + 1L) & 1L;
                        tempVal5 = (index1 - tempVal2) & 0xffffffffL;
                        tempVal1 = (tempVal5 + 1L) & 0xffffffffL;
                        if ((long) (int) tempVal3 <= (long) (int) tempVal1) {
                            callIndirect4(getEntryFn, dataPtr, leftIdx & 0xffffffffL, tempVal3, tempVal1);
                            if (((index1 - tempVal4 + tempVal2) & 0xffffffffL) != 0xffffffffL) {
                                tempVal3 = tempVal4;
                            }
                            tempVal4 = tempVal1;
                            if (((intVal + index2) & 0xffffffffL) != 1L) {
                                tempVal4 = tempVal3;
                            }
                            tempVal3 = (((isLessOrEqual1 + 1L) & 1L) + index2) & 0xffffffffL;
                            if ((long) (int) tempVal3 <= (long) (int) tempVal5) {
                                step = 2;
                                continue stepLoop;
                            } else {
                                step = 3;
                                continue stepLoop;
                            }
                        } else {
                            tempVal5 = tempVal1;
                            if ((long) (int) tempVal1 < (long) (int) tempVal3) {
                                step = 3;
                                continue stepLoop;
                            } else {
                                step = 2;
                                continue stepLoop;
                            }
                        }
                    }
                    case 3: {
                        if ((long) (int) swapFn < (long) (int) tempVal5) {
                            cc_qsort(dataPtr, leftIdx & 0xffffffffL, rightIdx, getEntryFn, (long) (int) swapFn, (long) (int) tempVal5);
                        }
                        swapFn = (long) (int) tempVal3;
                        if ((long) (int) varIndex <= (long) (int) tempVal3) {
                            return;
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

    long sQuartiles(long dataPtr, long getEntryFn, long swapFn, long varIndex, long entryCount, long isWeighted, long outQ1Ptr, long outMedianPtr, long outQ3Ptr, long outIqrPtr, long useFreq) {
        long intVal = 0L;
        long dataPtr1 = 0L;
        long dataPtr2 = 0L;
        long status = 0L;
        long dataPtr3 = 0L;
        long tempVal1 = 0L;
        long index = 0L;
        long tempVal2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        if ((entryCount & 0xffffffffL) == 0L) {
                            if (outQ1Ptr != 0L) {
                                putI64(outQ1Ptr + 8L, 0L);
                                putI64(outQ1Ptr, 0L);
                            }
                            if (outMedianPtr != 0L) {
                                putI64(outMedianPtr + 8L, 0L);
                                putI64(outMedianPtr, 0L);
                            }
                            if (outQ3Ptr != 0L) {
                                putI64(outQ3Ptr + 8L, 0L);
                                putI64(outQ3Ptr, 0L);
                            }
                            if (outIqrPtr != 0L) {
                                putI64(outIqrPtr + 8L, 0L);
                                putI64(outIqrPtr, 0L);
                            }
                            if (useFreq == 0L) {
                                status = 32L;
                            } else {
                                status = 32L;
                                putI64(useFreq + 8L, 0L);
                                putI64(useFreq, 0L);
                            }
                            return status;
                        }
                        cc_qsort(dataPtr, getEntryFn & 0xffffffffL, swapFn, varIndex, 0L, (long) (int) (getEntryFn - 1L));
                        if (outQ1Ptr != 0L) {
                            dataPtr1 = callIndirect3(isWeighted, dataPtr, entryCount & 0xffffffffL, 0L);
                            status = getI64(dataPtr1);
                            putI64(outQ1Ptr + 8L, getI64(dataPtr1 + 8L));
                            putI64(outQ1Ptr, status);
                        }
                        if (useFreq != 0L) {
                            dataPtr1 = callIndirect3(isWeighted, dataPtr, entryCount & 0xffffffffL, (entryCount - 1L) & 0xffffffffL);
                            status = getI64(dataPtr1);
                            putI64(useFreq + 8L, getI64(dataPtr1 + 8L));
                            putI64(useFreq, status);
                        }
                        if ((entryCount & 1L) == 0L) {
                            tempVal1 = (((entryCount & 0xffffffffL)) >> 1) & 0xffffffffL;
                            if (outQ3Ptr != 0L) {
                                dataPtr1 = callIndirect3(isWeighted, dataPtr, entryCount & 0xffffffffL, (tempVal1 - 1L) & 0xffffffffL);
                                putI64(framePtr + 24L, getI64(dataPtr1 + 8L));
                                putI64(framePtr + 16L, getI64(dataPtr1));
                                dataPtr2 = callIndirect3(isWeighted, dataPtr, entryCount & 0xffffffffL, tempVal1);
                                firadd(framePtr + 16L, dataPtr2, outQ3Ptr, 0L);
                                status = fidiv(outQ3Ptr, CC_2, outQ3Ptr);
                                fNorm_L((long) (int) status, outQ3Ptr);
                            }
                            step = 2;
                            continue stepLoop;
                        } else {
                            if (outQ3Ptr != 0L) {
                                tempVal1 = entryCount & 0xffffffffL;
                                if ((long) (int) entryCount < 0L) {
                                    tempVal1 = (entryCount + 1L) & 0xffffffffL;
                                }
                                dataPtr3 = callIndirect3(isWeighted, dataPtr, entryCount & 0xffffffffL, (long) (int) ((long) (int) tempVal1 >> 1));
                                tempVal2 = getI64(dataPtr3);
                                putI64(outQ3Ptr + 8L, getI64(dataPtr3 + 8L));
                                putI64(outQ3Ptr, tempVal2);
                            }
                            if ((entryCount & 0xffffffffL) != 1L) {
                                tempVal1 = ((((entryCount & 0xffffffffL)) >> 1) + 1L) & 0xffffffffL;
                                step = 2;
                                continue stepLoop;
                            } else {
                                tempVal1 = 0L;
                                index = 1L;
                                step = 3;
                                continue stepLoop;
                            }
                        }
                    }
                    case 1: {
                        index = (entryCount - tempVal1) & 0xffffffffL;
                        if ((index & 1L) == 0L) {
                            if (outIqrPtr != 0L) {
                                intVal = (long) (int) (tempVal1 + (index >> 1));
                                dataPtr1 = callIndirect3(isWeighted, dataPtr, entryCount & 0xffffffffL, (long) (int) (intVal - 1L));
                                putI64(framePtr + 24L, getI64(dataPtr1 + 8L));
                                putI64(framePtr + 16L, getI64(dataPtr1));
                                dataPtr2 = callIndirect3(isWeighted, dataPtr, entryCount & 0xffffffffL, intVal);
                                firadd(framePtr + 16L, dataPtr2, outIqrPtr, 0L);
                                status = fidiv(outIqrPtr, CC_2, outIqrPtr);
                                fNorm_L((long) (int) status, outIqrPtr);
                            }
                            status = 0L;
                        } else {
                            if (outIqrPtr != 0L) {
                                if ((long) (int) index < 0L) {
                                    index = (index + 1L) & 0xffffffffL;
                                }
                                dataPtr3 = callIndirect3(isWeighted, dataPtr, entryCount & 0xffffffffL, (tempVal1 + ((long) (int) index >> 1)) & 0xffffffffL);
                                tempVal2 = getI64(dataPtr3);
                                status = 0L;
                                putI64(outIqrPtr + 8L, getI64(dataPtr3 + 8L));
                                putI64(outIqrPtr, tempVal2);
                            } else {
                                status = 0L;
                            }
                        }
                        return status;
                    }
                    case 2: {
                        if (((((entryCount & 0xffffffffL)) >> 1) & 1L) != 0L) {
                            index = (((entryCount & 0xffffffffL)) >> 1) & 0xffffffffL;
                            step = 3;
                            continue stepLoop;
                        } else {
                            if (outMedianPtr != 0L) {
                                dataPtr1 = callIndirect3(isWeighted, dataPtr, (((entryCount & 0xffffffffL)) >> 1) & 0xffffffffL, ((((entryCount & 0xffffffffL)) >> 2) - 1L) & 0xffffffffL);
                                putI64(framePtr + 24L, getI64(dataPtr1 + 8L));
                                putI64(framePtr + 16L, getI64(dataPtr1));
                                dataPtr2 = callIndirect3(isWeighted, dataPtr, (((entryCount & 0xffffffffL)) >> 1) & 0xffffffffL, (((entryCount & 0xffffffffL)) >> 2) & 0xffffffffL);
                                firadd(framePtr + 16L, dataPtr2, outMedianPtr, 0L);
                                status = fidiv(outMedianPtr, CC_2, outMedianPtr);
                                fNorm_L((long) (int) status, outMedianPtr);
                            }
                            step = 1;
                            continue stepLoop;
                        }
                    }
                    case 3: {
                        if (outMedianPtr != 0L) {
                            dataPtr3 = callIndirect3(isWeighted, dataPtr, index, (index >> 1) & 0xffffffffL);
                            tempVal2 = getI64(dataPtr3);
                            putI64(outMedianPtr + 8L, getI64(dataPtr3 + 8L));
                            putI64(outMedianPtr, tempVal2);
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

    CC_Real frandG(long seedPtr, long meanPtr, long stdDevPtr) {
        long isZero = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            do {
                putCCReal(framePtr + 40L, fRand(stdDevPtr));
                fradd(framePtr + 40L, framePtr + 40L, framePtr + 40L, 0L);
                fradd(framePtr + 40L, CC_1, framePtr + 40L, 1L);
                putCCReal(framePtr + 56L, fRand(stdDevPtr));
                fradd(framePtr + 56L, framePtr + 56L, framePtr + 56L, 0L);
                fradd(framePtr + 56L, CC_1, framePtr + 56L, 1L);
                fisqrt(framePtr + 40L, framePtr + 24L);
                fradd(framePtr + 24L, framePtr + 56L, framePtr + 56L, 0L);
                isZero = fLE(CC_1, framePtr + 56L) != 0L ? 1L : 0L;
            } while (isZero != 0L);
            filn(framePtr + 56L, framePtr + 24L);
            fimul(framePtr + 24L, framePtr + 24L, framePtr + 24L);
            isZero = fZero(framePtr + 24L) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                putU32(framePtr + 24L, -getU32(framePtr + 24L));
            }
            fidiv(framePtr + 24L, framePtr + 56L, framePtr + 56L);
            fisqrt(framePtr + 56L, framePtr + 56L);
            fimul(framePtr + 56L, framePtr + 40L, framePtr + 56L);
            fimul(framePtr + 56L, meanPtr, framePtr + 56L);
            firadd(framePtr + 56L, seedPtr, framePtr + 56L, 0L);
            return getCCReal(framePtr + 56L);
        } finally {
            sp = savedSp;
        }
    }

    long RegStatsSigmaPlus(long statRegsPtr, long xValPtr, long yValPtr, long isSubtract) {
        long dataPtr = 0L;
        long isZero = 0L;
        long status = 0L;
        long result = 0L;
        long constPtr = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            if ((yValPtr & 1L) == 0L) {
                isZero = fZero(isSubtract) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    result = 24L;
                    return result;
                }
                constPtr = CC_N1;
            } else {
                constPtr = CC_1;
            }
            status = firadd(isSubtract, constPtr, isSubtract, 0L);
            fNorm_L((long) (int) status, isSubtract);
            status = fimul(statRegsPtr, statRegsPtr, framePtr + 56L);
            fNorm_L((long) (int) status, framePtr + 56L);
            status = fimul(xValPtr, xValPtr, framePtr + 40L);
            fNorm_L((long) (int) status, framePtr + 40L);
            status = fimul(statRegsPtr, xValPtr, framePtr + 24L);
            fNorm_L((long) (int) status, framePtr + 24L);
            dataPtr = isSubtract + 16L;
            if ((yValPtr & 1L) == 0L) {
                putI64(framePtr + 80L, getI64(statRegsPtr + 8L));
                putI64(framePtr + 72L, getI64(statRegsPtr));
                if ((long) (int) getI64(framePtr + 72L) == 3L) {
                    putI32(framePtr + 76L, getI64(framePtr + 72L) >>> 32);
                    putI64(framePtr + 72L, (((getI32(framePtr + 76L) & 0xffffffffL)) << 32) | 1L);
                }
                isZero = fZero(framePtr + 72L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putI64(framePtr + 72L, (((getI32(framePtr + 76L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 72L) & 0xffffffffL));
                }
                status = firadd(dataPtr, framePtr + 72L, dataPtr, 0L);
                fNorm_L((long) (int) status, dataPtr);
                putI64(framePtr + 80L, getI64(xValPtr + 8L));
                putI64(framePtr + 72L, getI64(xValPtr));
                dataPtr = isSubtract + 48L;
                if ((long) (int) getI64(framePtr + 72L) == 3L) {
                    putI32(framePtr + 76L, getI64(framePtr + 72L) >>> 32);
                    putI64(framePtr + 72L, (((getI32(framePtr + 76L) & 0xffffffffL)) << 32) | 1L);
                }
                isZero = fZero(framePtr + 72L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putI64(framePtr + 72L, (((getI32(framePtr + 76L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 72L) & 0xffffffffL));
                }
                status = firadd(dataPtr, framePtr + 72L, dataPtr, 0L);
                fNorm_L((long) (int) status, dataPtr);
                if (getU32(framePtr + 56L) == 3L) {
                    putU32(framePtr + 56L, 1L);
                }
                isZero = fZero(framePtr + 56L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putU32(framePtr + 56L, -getU32(framePtr + 56L));
                }
                if (getU32(framePtr + 40L) == 3L) {
                    putU32(framePtr + 40L, 1L);
                }
                isZero = fZero(framePtr + 40L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putU32(framePtr + 40L, -getU32(framePtr + 40L));
                }
                if (getU32(framePtr + 24L) == 3L) {
                    putU32(framePtr + 24L, 1L);
                }
                isZero = fZero(framePtr + 24L) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    putU32(framePtr + 24L, -getU32(framePtr + 24L));
                }
            } else {
                status = firadd(dataPtr, statRegsPtr, dataPtr, 0L);
                fNorm_L((long) (int) status, dataPtr);
                dataPtr = isSubtract + 48L;
                status = firadd(dataPtr, xValPtr, dataPtr, 0L);
                fNorm_L((long) (int) status, dataPtr);
            }
            dataPtr = isSubtract + 32L;
            status = firadd(dataPtr, framePtr + 56L, dataPtr, 0L);
            fNorm_L((long) (int) status, dataPtr);
            dataPtr = isSubtract + 64L;
            status = firadd(dataPtr, framePtr + 40L, dataPtr, 0L);
            fNorm_L((long) (int) status, dataPtr);
            dataPtr = isSubtract + 80L;
            status = firadd(dataPtr, framePtr + 24L, dataPtr, 0L);
            fNorm_L((long) (int) status, dataPtr);
            result = 0L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long RegStatsBasic(long statRegsPtr, long outMeanXPtr, long outMeanYPtr, long outSampleStdXPtr, long outSampleStdYPtr, long outPopStdXPtr, long outPopStdYPtr, long hasTwoVars) {
        long isLessOrEqual = 0L;
        long flag = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            isLessOrEqual = fLE(statRegsPtr, CC_0) != 0L ? 1L : 0L;
            if (isLessOrEqual != 0L) {
                status1 = 24L;
                return status1;
            }
            if (outMeanXPtr != 0L) {
                status2 = fidiv(statRegsPtr + 16L, statRegsPtr, outMeanXPtr);
                fNorm_L((long) (int) status2, outMeanXPtr);
            }
            if (outMeanYPtr != 0L) {
                status2 = fidiv(statRegsPtr + 48L, statRegsPtr, outMeanYPtr);
                fNorm_L((long) (int) status2, outMeanYPtr);
            }
            if (hasTwoVars != 0L) {
                status2 = fidiv(statRegsPtr + 80L, statRegsPtr + 48L, hasTwoVars);
                fNorm_L((long) (int) status2, hasTwoVars);
            }
            if (outSampleStdXPtr != 0L) {
                fimul(statRegsPtr + 16L, statRegsPtr + 16L, outSampleStdXPtr);
                fidiv(outSampleStdXPtr, statRegsPtr, outSampleStdXPtr);
                firadd(statRegsPtr + 32L, outSampleStdXPtr, outSampleStdXPtr, 1L);
                fidiv(outSampleStdXPtr, statRegsPtr, outSampleStdXPtr);
                flag = fisqrt(outSampleStdXPtr, outSampleStdXPtr) & 0xffL;
                status1 = fNorm_L(flag, outSampleStdXPtr) & 0xffffffffL;
                if (1L < status1) {
                    return status1;
                }
            }
            if (outSampleStdYPtr != 0L) {
                fimul(statRegsPtr + 48L, statRegsPtr + 48L, outSampleStdYPtr);
                fidiv(outSampleStdYPtr, statRegsPtr, outSampleStdYPtr);
                firadd(statRegsPtr + 64L, outSampleStdYPtr, outSampleStdYPtr, 1L);
                fidiv(outSampleStdYPtr, statRegsPtr, outSampleStdYPtr);
                flag = fisqrt(outSampleStdYPtr, outSampleStdYPtr) & 0xffL;
                status1 = fNorm_L(flag, outSampleStdYPtr) & 0xffffffffL;
                if (1L < status1) {
                    return status1;
                }
            }
            if (outPopStdXPtr != 0L) {
                isLessOrEqual = fLE(statRegsPtr, CC_1) != 0L ? 1L : 0L;
                if (isLessOrEqual != 0L) {
                    status1 = 24L;
                    return status1;
                }
                fimul(statRegsPtr + 16L, statRegsPtr + 16L, outPopStdXPtr);
                fidiv(outPopStdXPtr, statRegsPtr, outPopStdXPtr);
                firadd(statRegsPtr + 32L, outPopStdXPtr, outPopStdXPtr, 1L);
                firadd(statRegsPtr, CC_N1, framePtr + 24L, 0L);
                fidiv(outPopStdXPtr, framePtr + 24L, outPopStdXPtr);
                flag = fisqrt(outPopStdXPtr, outPopStdXPtr) & 0xffL;
                status1 = fNorm_L(flag, outPopStdXPtr) & 0xffffffffL;
                if (1L < status1) {
                    return status1;
                }
            }
            if (outPopStdYPtr == 0L) {
                status1 = 0L;
                return status1;
            }
            isLessOrEqual = fLE(statRegsPtr, CC_1) != 0L ? 1L : 0L;
            if (isLessOrEqual != 0L) {
                status1 = 24L;
                return status1;
            } else {
                fimul(statRegsPtr + 48L, statRegsPtr + 48L, outPopStdYPtr);
                fidiv(outPopStdYPtr, statRegsPtr, outPopStdYPtr);
                firadd(statRegsPtr + 64L, outPopStdYPtr, outPopStdYPtr, 1L);
                firadd(statRegsPtr, CC_N1, framePtr + 24L, 0L);
                fidiv(outPopStdYPtr, framePtr + 24L, outPopStdYPtr);
                flag = fisqrt(outPopStdYPtr, outPopStdYPtr) & 0xffL;
                status1 = fNorm_L(flag, outPopStdYPtr) & 0xffffffffL;
                if (1L < status1) {
                    return status1;
                }
                status1 = 0L;
                return status1;
            }
        } finally {
            sp = savedSp;
        }
    }

    long RegStatsRegression(long statRegsPtr, long outSlopePtr, long outInterceptPtr, long outCorrPtr) {
        long dataPtr1 = 0L;
        long dataPtr2 = 0L;
        long isLessOrEqual = 0L;
        long flag = 0L;
        long status1 = 0L;
        long status2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            isLessOrEqual = fLE(statRegsPtr, CC_1) != 0L ? 1L : 0L;
            if (isLessOrEqual != 0L) {
                status1 = 24L;
            } else {
                if (outSlopePtr != 0L) {
                    dataPtr1 = statRegsPtr + 16L;
                    dataPtr2 = statRegsPtr + 48L;
                    fimul(dataPtr1, dataPtr2, framePtr + 56L);
                    fidiv(framePtr + 56L, statRegsPtr, framePtr + 56L);
                    firadd(statRegsPtr + 80L, framePtr + 56L, framePtr + 56L, 1L);
                    fimul(dataPtr1, dataPtr1, framePtr + 40L);
                    fidiv(framePtr + 40L, statRegsPtr, framePtr + 40L);
                    firadd(statRegsPtr + 32L, framePtr + 40L, framePtr + 40L, 1L);
                    fimul(dataPtr2, dataPtr2, framePtr + 24L);
                    fidiv(framePtr + 24L, statRegsPtr, framePtr + 24L);
                    firadd(statRegsPtr + 64L, framePtr + 24L, framePtr + 24L, 1L);
                    fimul(framePtr + 40L, framePtr + 24L, framePtr + 40L);
                    flag = fisqrt(framePtr + 40L, framePtr + 40L) & 0xffL;
                    status1 = flag;
                    if (1L < flag) {
                        return status1;
                    }
                    status2 = fidiv(framePtr + 56L, framePtr + 40L, outSlopePtr);
                    status1 = fNorm_L((long) (int) status2, outSlopePtr) & 0xffffffffL;
                    if (1L < status1) {
                        return status1;
                    }
                }
                status1 = 24L;
                if (!((outInterceptPtr == 0L ? 1L : 0L) == (outCorrPtr == 0L ? 1L : 0L) && comma(status1 = 0L, outInterceptPtr == 0L ? 1L : 0L) != 0L)) {
                    return status1;
                }
                dataPtr1 = statRegsPtr + 16L;
                fimul(dataPtr1, statRegsPtr + 48L, outCorrPtr);
                fidiv(outCorrPtr, statRegsPtr, outCorrPtr);
                firadd(statRegsPtr + 80L, outCorrPtr, outCorrPtr, 1L);
                fimul(dataPtr1, dataPtr1, framePtr + 56L);
                fidiv(framePtr + 56L, statRegsPtr, framePtr + 56L);
                firadd(statRegsPtr + 32L, framePtr + 56L, framePtr + 56L, 1L);
                status2 = fidiv(outCorrPtr, framePtr + 56L, outCorrPtr);
                status1 = fNorm_L((long) (int) status2, outCorrPtr) & 0xffffffffL;
                if (status1 < 2L) {
                    fimul(outCorrPtr, statRegsPtr, outCorrPtr);
                    fimul(outCorrPtr, dataPtr1, outCorrPtr);
                    firadd(statRegsPtr + 48L, outCorrPtr, outCorrPtr, 1L);
                    status2 = fidiv(outCorrPtr, statRegsPtr, outCorrPtr);
                    fNorm_L((long) (int) status2, outCorrPtr);
                    status1 = 0L;
                }
            }
            return status1;
        } finally {
            sp = savedSp;
        }
    }

    long RegStatsGetEntry(long statRegsPtr, long entryIndex, long outPairPtr) {
        long intVal = 0L;
        long dataPtr1 = 0L;
        long dataPtr2 = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            intVal = (long) (int) (outPairPtr - ((long) (int) (entryIndex - 1L) == (long) (int) outPairPtr ? 1L : 0L));
            dataPtr1 = callIndirect3(getI64(statRegsPtr + 8L), getI64(statRegsPtr), entryIndex, intVal);
            tempVal = getI64(dataPtr1 + 16L);
            putI64(statRegsPtr + 24L, getI64(dataPtr1 + 24L));
            putI64(statRegsPtr + 16L, tempVal);
            putI64(framePtr + 40L, getI64(dataPtr1 + 8L));
            putI64(framePtr + 32L, getI64(dataPtr1));
            putI64(framePtr + 24L, getI64(dataPtr1 + 24L));
            putI64(framePtr + 16L, getI64(dataPtr1 + 16L));
            dataPtr2 = callIndirect3(getI64(statRegsPtr + 8L), getI64(statRegsPtr), entryIndex & 0xffffffffL, (long) (int) (intVal + 1L));
            firadd(dataPtr2, framePtr + 32L, framePtr + 32L, 1L);
            firadd(dataPtr2 + 16L, framePtr + 16L, framePtr + 16L, 1L);
            dataPtr2 = statRegsPtr + 32L;
            fidiv(framePtr + 16L, framePtr + 32L, dataPtr2);
            fidiv(dataPtr2, statRegsPtr + 16L, dataPtr2);
            if (4L < ((getI32(statRegsPtr + 32L) + 1L) & 0xffffffffL) || ((1L << (int) ((getI32(statRegsPtr + 32L) + 1L) & 31L)) & 21L) == 0L) {
                putI64(statRegsPtr + 40L, 0L);
                putU32(dataPtr2, 1L);
                putU32(dataPtr2 + 4L, 0L);
            }
            return statRegsPtr + 16L;
        } finally {
            sp = savedSp;
        }
    }

    long GetUnusedTempRegNum() {
        return 20L;
    }

    long GetUnusedCfloNum() {
        long intVal = 0L;
        long tempVal = 0L;
        long result = 0L;
        result = getU8(g_fin_maxcflo);
        tempVal = (getU16(g_prog_maxstep) + getU8(g_com_prog_share_gap) + getU8(g_fin_maxcflo) * 0xfffffff9L + 405L) & 0xffffL;
        if (tempVal < getU16(g_prog_totalstep_num)) {
            intVal = (long) (int) (getU16(g_prog_totalstep_num) - tempVal + 6L);
            intVal = (long) (int) (((intVal * 0x6db6db6dL) >>> 32) - intVal);
            result = (result + ((intVal >> 2) - (intVal >> 31))) & 0xffffffffL;
        }
        return result;
    }

    long GetTempReg(long regIndex, long outRealPtr) {
        long offset = 0L;
        if ((regIndex & 0xffL) < 20L) {
            offset = getI64(g_fin_cflodata + 8L + (regIndex & 0xffL) * 24L);
            putI64(outRealPtr + 8L, getI64(g_fin_cflodata + 16L + (regIndex & 0xffL) * 24L));
            putI32(outRealPtr, offset);
            putI32(outRealPtr + 4L, offset >>> 32);
            return 1L;
        } else {
            return 0L;
        }
    }

    long SetTempReg(long regIndex, long inRealPtr) {
        long tempVal = 0L;
        if ((regIndex & 0xffL) < 20L) {
            tempVal = getI64(inRealPtr);
            putI64(g_fin_cflodata + 16L + (regIndex & 0xffL) * 24L, getI64(inRealPtr + 8L));
            putI64(g_fin_cflodata + 8L + (regIndex & 0xffL) * 24L, tempVal);
            return 1L;
        } else {
            return 0L;
        }
    }

    CC_Real GetStatReg(long statRegIndex) {
        return getCCReal(g_fin_cflodata + 8L + (statRegIndex & 0xffL) * 24L);
    }

    void SetStatReg(long statRegIndex, long inRealPtr) {
        long tempVal = 0L;
        tempVal = getI64(inRealPtr);
        putI64(g_fin_cflodata + 16L + (statRegIndex & 0xffL) * 24L, getI64(inRealPtr + 8L));
        putI64(g_fin_cflodata + 8L + (statRegIndex & 0xffL) * 24L, tempVal);
    }

    long GetProgData(long stepIndex) {
        return getU16(g_prog_storedata + (getU16(g_prog_maxstep) + ((~(stepIndex & 0xffffL) & 0xffffffffL) | 0x7fffffffffff0000L) + getU8(g_com_prog_share_gap)) * 2L);
    }

    void SetProgData(long stepIndex, long codePtr) {
        putU16(g_prog_storedata + (getU16(g_prog_maxstep) + ((~(stepIndex & 0xffffL) & 0xffffffffL) | 0x7fffffffffff0000L) + getU8(g_com_prog_share_gap)) * 2L, getU16(codePtr));
    }

    long GetCfloData(long cfloIndex, long outEntryPtr) {
        long intVal = 0L;
        long tempVal1 = 0L;
        long offset1 = 0L;
        long tempVal2 = 0L;
        long offset2 = 0L;
        long tempVal3 = 0L;
        offset2 = getI64(g_fin_fv);
        tempVal2 = getU8(g_fin_maxcflo);
        tempVal1 = (getU16(g_prog_maxstep) + getU8(g_com_prog_share_gap) + getU8(g_fin_maxcflo) * 0xfffffff9L + 405L) & 0xffffL;
        if (tempVal1 < getU16(g_prog_totalstep_num)) {
            intVal = (long) (int) (getU16(g_prog_totalstep_num) - tempVal1 + 6L);
            tempVal1 = (((intVal * 0x6db6db6dL) >>> 32) - intVal) & 0xffffffffL;
            tempVal2 = (getU8(g_fin_maxcflo) + ((tempVal1 >> 2) - ((long) (int) tempVal1 >> 31))) & 0xffL;
        }
        tempVal1 = (tempVal2 - 1L) & 0xffffL;
        if ((cfloIndex & 0xffL) < tempVal1) {
            offset1 = (cfloIndex & 0xffL) * 24L;
            tempVal3 = getI64(g_fin_cflodata + 8L + offset1);
            offset2 = getI64(g_fin_cflodata + (cfloIndex & 0xffL) * 24L);
            putI64(outEntryPtr + 16L, getI64(g_fin_cflodata + 16L + offset1));
            putI64(outEntryPtr + 8L, tempVal3);
            putI64(outEntryPtr, offset2);
            return 1L;
        }
        if (tempVal1 == (cfloIndex & 0xffL)) {
            putI64(outEntryPtr + 16L, getI64(g_fin_fv + 8L));
            putI64(outEntryPtr + 8L, offset2);
            putU16(outEntryPtr, getU16(g_com_fin_lastcflo_times));
            return 1L;
        } else {
            return 0L;
        }
    }

    long SetCfloData(long cfloIndex, long inEntryPtr) {
        long intVal = 0L;
        long tempVal1 = 0L;
        long offset = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long mantissa = 0L;
        tempVal2 = getU8(g_fin_maxcflo);
        tempVal1 = (getU16(g_prog_maxstep) + getU8(g_com_prog_share_gap) + getU8(g_fin_maxcflo) * 0xfffffff9L + 405L) & 0xffffL;
        if (tempVal1 < getU16(g_prog_totalstep_num)) {
            intVal = (long) (int) (getU16(g_prog_totalstep_num) - tempVal1 + 6L);
            tempVal1 = (((intVal * 0x6db6db6dL) >>> 32) - intVal) & 0xffffffffL;
            tempVal2 = (getU8(g_fin_maxcflo) + ((tempVal1 >> 2) - ((long) (int) tempVal1 >> 31))) & 0xffL;
        }
        tempVal1 = (tempVal2 - 1L) & 0xffffL;
        if ((cfloIndex & 0xffL) < tempVal1) {
            mantissa = getI64(inEntryPtr + 8L);
            tempVal3 = getI64(inEntryPtr);
            offset = (cfloIndex & 0xffL) * 24L;
            putI64(g_fin_cflodata + 16L + offset, getI64(inEntryPtr + 16L));
            putI64(g_fin_cflodata + 8L + offset, mantissa);
            putI64(g_fin_cflodata + (cfloIndex & 0xffL) * 24L, tempVal3);
            return 1L;
        }
        if (tempVal1 == (cfloIndex & 0xffL)) {
            putI64(g_fin_fv + 8L, getI64(inEntryPtr + 16L));
            putI64(g_fin_fv, getI64(inEntryPtr + 8L));
            putU16(g_com_fin_lastcflo_times, getU16(inEntryPtr));
            return 1L;
        } else {
            return 0L;
        }
    }

    void LockCfloData() {
        long intVal = 0L;
        long tempVal1 = 0L;
        long offset = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        tempVal2 = getI64(g_fin_fv);
        tempVal3 = getU8(g_fin_maxcflo);
        tempVal1 = (getU16(g_prog_maxstep) + getU8(g_com_prog_share_gap) + getU8(g_fin_maxcflo) * 0xfffffff9L + 405L) & 0xffffL;
        if (tempVal1 < getU16(g_prog_totalstep_num)) {
            intVal = (long) (int) (getU16(g_prog_totalstep_num) - tempVal1 + 6L);
            intVal = (long) (int) (((intVal * 0x6db6db6dL) >>> 32) - intVal);
            tempVal3 = (tempVal3 + ((intVal >> 2) - (intVal >> 31))) & 0xffffffffL;
        }
        tempVal4 = ((tempVal3 & 0xffL) - 1L) & 0xffffL;
        offset = tempVal4 * 24L;
        putI64(g_com_fin_lastcflo + 8L, getI64(g_fin_cflodata + 8L + offset));
        putI64(g_com_fin_lastcflo, getI64(g_fin_cflodata + tempVal4 * 24L));
        putI64(g_com_fin_lastcflo + 16L, getI64(g_fin_cflodata + 16L + offset));
        putI64(g_fin_cflodata + 16L + offset, getI64(g_fin_fv + 8L));
        putI64(g_fin_cflodata + 8L + offset, tempVal2);
        putU16(g_fin_cflodata + tempVal4 * 24L, getU16(g_com_fin_lastcflo_times));
    }

    long UnLockCfloData() {
        long intVal = 0L;
        long tempVal1 = 0L;
        long offset = 0L;
        long tempVal2 = 0L;
        long mantissa = 0L;
        long tempVal3 = 0L;
        long tempVal4 = 0L;
        mantissa = getI64(g_com_fin_lastcflo + 8L);
        tempVal2 = getI64(g_com_fin_lastcflo);
        tempVal3 = getU8(g_fin_maxcflo);
        tempVal1 = (getU16(g_prog_maxstep) + getU8(g_com_prog_share_gap) + getU8(g_fin_maxcflo) * 0xfffffff9L + 405L) & 0xffffL;
        if (tempVal1 < getU16(g_prog_totalstep_num)) {
            intVal = (long) (int) (getU16(g_prog_totalstep_num) - tempVal1 + 6L);
            intVal = (long) (int) (((intVal * 0x6db6db6dL) >>> 32) - intVal);
            tempVal3 = (tempVal3 + ((intVal >> 2) - (intVal >> 31))) & 0xffffffffL;
        }
        tempVal4 = ((tempVal3 & 0xffL) - 1L) & 0xffffL;
        offset = tempVal4 * 24L;
        putI64(g_fin_cflodata + 16L + offset, getI64(g_com_fin_lastcflo + 16L));
        putI64(g_fin_cflodata + 8L + offset, mantissa);
        putI64(g_fin_cflodata + tempVal4 * 24L, tempVal2);
        return 0L;
    }

    CC_Real Stack_GetX() {
        return getCCReal(g_com_reg_XYZT);
    }

    CC_Real Stack_Change12to10Digits(long realPtr) {
        return getCCReal(realPtr);
    }

    long Stack_SetX(long realPtr) {
        long errCode = 0L;
        putI64(g_com_reg_XYZT + 8L, getI64(realPtr + 8L));
        putI32(g_com_reg_XYZT, getI32(realPtr));
        putI32(g_com_reg_XYZT + 4L, getI32(realPtr + 4L));
        errCode = BF123_CheckRealData_Ex(g_com_reg_XYZT);
        return errCode;
    }

    CC_Real Stack_GetY() {
        return getCCReal(g_com_reg_XYZT + 16L);
    }

    void Stack_SetY(long realPtr) {
        putI64(g_com_reg_XYZT + 24L, getI64(realPtr + 8L));
        putI32(g_com_reg_XYZT + 16L, getI32(realPtr));
        putI32(g_com_reg_XYZT + 20L, getI32(realPtr + 4L));
    }

    CC_Real Stack_GetZ() {
        return getCCReal(g_com_reg_XYZT + 32L);
    }

    void Stack_SetZ(long realPtr) {
        putI64(g_com_reg_XYZT + 40L, getI64(realPtr + 8L));
        putI32(g_com_reg_XYZT + 32L, getI32(realPtr));
        putI32(g_com_reg_XYZT + 36L, getI32(realPtr + 4L));
    }

    CC_Real Stack_GetT() {
        return getCCReal(g_com_reg_XYZT + 48L);
    }

    void Stack_SetT(long realPtr) {
        putI64(g_com_reg_XYZT + 56L, getI64(realPtr + 8L));
        putI32(g_com_reg_XYZT + 48L, getI32(realPtr));
        putI32(g_com_reg_XYZT + 52L, getI32(realPtr + 4L));
    }

    void Stack_ShiftDownOneStep() {
        putI64(g_com_reg_XYZT + 8L, getI64(g_com_reg_XYZT + 24L));
        putI64(g_com_reg_XYZT, getI64(g_com_reg_XYZT + 16L));
        putI64(g_com_reg_XYZT + 24L, getI64(g_com_reg_XYZT + 40L));
        putI64(g_com_reg_XYZT + 16L, getI64(g_com_reg_XYZT + 32L));
        putI64(g_com_reg_XYZT + 40L, getI64(g_com_reg_XYZT + 56L));
        putI64(g_com_reg_XYZT + 32L, getI64(g_com_reg_XYZT + 48L));
    }

    void Stack_ShiftUpOneStep() {
        putI64(g_com_reg_XYZT + 56L, getI64(g_com_reg_XYZT + 40L));
        putI64(g_com_reg_XYZT + 48L, getI64(g_com_reg_XYZT + 32L));
        putI64(g_com_reg_XYZT + 40L, getI64(g_com_reg_XYZT + 24L));
        putI64(g_com_reg_XYZT + 32L, getI64(g_com_reg_XYZT + 16L));
        putI64(g_com_reg_XYZT + 24L, getI64(g_com_reg_XYZT + 8L));
        putI64(g_com_reg_XYZT + 16L, getI64(g_com_reg_XYZT));
    }

    long Stack_RotateDownOneStep() {
        long tempVal = 0L;
        long mantissa = 0L;
        mantissa = getI64(g_com_reg_XYZT + 8L);
        tempVal = getI64(g_com_reg_XYZT);
        putI64(g_com_reg_XYZT + 8L, getI64(g_com_reg_XYZT + 24L));
        putI64(g_com_reg_XYZT, getI64(g_com_reg_XYZT + 16L));
        putI64(g_com_reg_XYZT + 24L, getI64(g_com_reg_XYZT + 40L));
        putI64(g_com_reg_XYZT + 16L, getI64(g_com_reg_XYZT + 32L));
        putI64(g_com_reg_XYZT + 40L, getI64(g_com_reg_XYZT + 56L));
        putI64(g_com_reg_XYZT + 32L, getI64(g_com_reg_XYZT + 48L));
        putI64(g_com_reg_XYZT + 56L, mantissa);
        putI64(g_com_reg_XYZT + 48L, tempVal);
        return 1L;
    }

    long Stack_InsertToX(long realPtr) {
        long errCode = 0L;
        putI64(g_com_reg_XYZT + 56L, getI64(g_com_reg_XYZT + 40L));
        putI64(g_com_reg_XYZT + 48L, getI64(g_com_reg_XYZT + 32L));
        putI64(g_com_reg_XYZT + 40L, getI64(g_com_reg_XYZT + 24L));
        putI64(g_com_reg_XYZT + 32L, getI64(g_com_reg_XYZT + 16L));
        putI64(g_com_reg_XYZT + 24L, getI64(g_com_reg_XYZT + 8L));
        putI64(g_com_reg_XYZT + 16L, getI64(g_com_reg_XYZT));
        putI64(g_com_reg_XYZT + 8L, getI64(realPtr + 8L));
        putI32(g_com_reg_XYZT, getI32(realPtr));
        putI32(g_com_reg_XYZT + 4L, getI32(realPtr + 4L));
        errCode = BF123_CheckRealData_Ex(g_com_reg_XYZT);
        return errCode;
    }

    long Stack_SwapXY() {
        long tempVal = 0L;
        long mantissa = 0L;
        mantissa = getI64(g_com_reg_XYZT + 8L);
        tempVal = getI64(g_com_reg_XYZT);
        putI64(g_com_reg_XYZT + 8L, getI64(g_com_reg_XYZT + 24L));
        putI64(g_com_reg_XYZT, getI64(g_com_reg_XYZT + 16L));
        putI64(g_com_reg_XYZT + 24L, mantissa);
        putI64(g_com_reg_XYZT + 16L, tempVal);
        return 1L;
    }

    void Stack_ClearAll() {
        putI64(g_com_reg_XYZT + 8L, 0L);
        putI64(g_com_reg_XYZT, 1L);
        putI64(g_com_reg_XYZT + 24L, 0L);
        putI64(g_com_reg_XYZT + 16L, 1L);
        putI64(g_com_reg_XYZT + 40L, 0L);
        putI64(g_com_reg_XYZT + 32L, 1L);
        putI64(g_com_reg_XYZT + 56L, 0L);
        putI64(g_com_reg_XYZT + 48L, 1L);
    }

    long Stack_PushRegisters() {
        long offset = 0L;
        long exprState = 0L;
        long result = 0L;
        exprState = getU32(g_basic_op);
        result = getI64(g_com_reg_X1);
        if (Long.compareUnsigned(getU8(g_stack_depth), 13L) < 0L) {
            offset = getU8(g_stack_depth) * 24L;
            putI64(g_stack_element + offset + 8L, getI64(g_com_reg_X1 + 8L));
            putI64(g_stack_element + offset, result);
            putU32(g_stack_element + offset + 16L, exprState);
            exprState = Basic_GetExpressState() & 0xffffffffL;
            putU32(g_stack_element + getU8(g_stack_depth) * 24L + 20L, exprState);
            putI32(g_result_state, 1L);
            Basic_ChangeExpressState(2L);
            result = 99L;
            putU32(g_basic_op, 99L);
            putU8(g_stack_depth, getU8(g_stack_depth) + 1L);
        } else {
            result = 4L;
        }
        return result;
    }

    CC_Real Stack_GetX1() {
        return getCCReal(g_com_reg_X1);
    }

    long Stack_PopRegisters() {
        long offset = 0L;
        long exprState = 0L;
        long result = 0L;
        exprState = (long) (int) Basic_GetExpressState();
        putU8(g_stack_depth, getU8(g_stack_depth) - 1L);
        offset = getU8(g_stack_depth) * 24L;
        putI64(g_com_reg_X1 + 8L, getI64(g_stack_element + offset + 8L));
        putI64(g_com_reg_X1, getI64(g_stack_element + offset));
        Basic_SetExpressState(getU32(g_stack_element + offset + 20L));
        result = getU8(g_stack_depth);
        putU32(g_basic_op, getU32(g_stack_element + result * 24L + 16L));
        if (((exprState - 1L) & 0xffffffffL) < 3L) {
            Basic_ChangeExpressState(0L);
            result = getU8(g_stack_depth);
        }
        return result;
    }

    void Stack_SetX1(long realPtr) {
        putI64(g_com_reg_X1 + 8L, getI64(realPtr + 8L));
        putI32(g_com_reg_X1, getI32(realPtr));
        putI32(g_com_reg_X1 + 4L, getI32(realPtr + 4L));
    }

    long Stack_EmptyRegisters() {
        long annunciator = 0L;
        putU8(g_stack_depth, 0L);
        if (getI32(g_com_machine_mode) == 0L) {
            annunciator = LcdDriver_ClearAnnunciator(4L);
        }
        putU8(g_com_bracketmode, 0L);
        return 0L;
    }

    long Stack_GetRegistersDepth() {
        return getU8(g_stack_depth);
    }

    void Stack_PushXToX1() {
        putI64(g_com_reg_X1 + 8L, getI64(g_com_reg_XYZT + 8L));
        putI64(g_com_reg_X1, getI64(g_com_reg_XYZT));
    }

    CC_Real Stack_GetX_12digits() {
        return getCCReal(g_com_reg_XYZT);
    }

    CC_Real Stack_GetY_12digits() {
        return getCCReal(g_com_reg_XYZT + 16L);
    }

    long StandardDeviation(long sumXPtr, long sumX2Ptr, long countPtr, long outStdDevPtr) {
        long flag1 = 0L;
        long flag2 = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            putI64(framePtr + 24L, 0L);
            putI64(framePtr + 16L, 0L);
            putI64(framePtr + 32L, getI64(framePtr + 16L));
            putI64(framePtr + 40L, getI64(framePtr + 24L));
            status = fimul(sumXPtr, countPtr, framePtr + 32L);
            if (!((long) (int) status < 2L && comma(status = fimul(sumX2Ptr, sumX2Ptr, framePtr + 16L), (long) (int) status < 2L ? 1L : 0L) != 0L)) {
                return status;
            }
            flag1 = fradd(framePtr + 32L, framePtr + 16L, framePtr + 32L, 1L) != 0L ? 1L : 0L;
            status = flag1;
            if (flag1 >= 2L) {
                return status;
            }
            flag1 = fradd(sumXPtr, CC_1, framePtr + 16L, 1L) != 0L ? 1L : 0L;
            status = flag1;
            if (flag1 < 2L && comma(status = fimul(sumXPtr, framePtr + 16L, framePtr + 16L), (long) (int) status < 2L ? 1L : 0L) != 0L && comma(status = fidiv(framePtr + 32L, framePtr + 16L, framePtr + 32L), (long) (int) status < 2L ? 1L : 0L) != 0L) {
                flag2 = fisqrt(framePtr + 32L, outStdDevPtr) & 0xffL;
                status = flag2;
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long CC_New_StdDev(long nPtr, long sumXPtr, long sumX2Ptr, long sumYPtr, long sumY2Ptr, long outSxPtr, long outSyPtr) {
        long status = 0L;
        if (outSxPtr != 0L) {
            putI64(outSxPtr + 8L, 0L);
            putI32(outSxPtr, 1L);
            putI32(outSxPtr + 4L, 0L);
        }
        if (outSyPtr != 0L) {
            putI64(outSyPtr + 8L, 0L);
            putI32(outSyPtr, 1L);
            putI32(outSyPtr + 4L, 0L);
        }
        status = StandardDeviation(sumY2Ptr, sumXPtr, nPtr, outSxPtr);
        if ((status & 0xffffffffL) < 2L) {
            status = StandardDeviation(sumY2Ptr, sumYPtr, sumX2Ptr, outSyPtr);
            return status;
        } else {
            return status;
        }
    }

    long CC_New_LinearEstimateX(long nPtr, long sumXPtr, long sumYPtr, long sumX2Ptr, long sumY2Ptr, long sumXYPtr, long yInputPtr, long outXHatPtr, long outCorrPtr) {
        long flag1 = 0L;
        long flag2 = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(288);
        try {
            putI64(framePtr + 88L, 0L);
            putI64(framePtr + 80L, 0L);
            putI64(outXHatPtr + 8L, 0L);
            putI32(outXHatPtr, 1L);
            putI32(outXHatPtr + 4L, 0L);
            putI64(outCorrPtr + 8L, 0L);
            putI32(outCorrPtr, 1L);
            putI32(outCorrPtr + 4L, 0L);
            putI64(framePtr + 96L, getI64(framePtr + 80L));
            putI64(framePtr + 104L, getI64(framePtr + 88L));
            status = fimul(sumXPtr, sumXPtr, framePtr + 96L);
            if (!((long) (int) status < 2L && comma(status = fidiv(framePtr + 96L, sumXYPtr, framePtr + 96L), (long) (int) status < 2L ? 1L : 0L) != 0L)) {
                return status;
            }
            flag1 = fradd(nPtr, framePtr + 96L, framePtr + 32L, 1L) != 0L ? 1L : 0L;
            status = flag1;
            if (!(flag1 < 2L && comma(status = fimul(sumX2Ptr, sumX2Ptr, framePtr + 96L), (long) (int) status < 2L && comma(status = fidiv(framePtr + 96L, sumXYPtr, framePtr + 96L), (long) (int) status < 2L ? 1L : 0L) != 0L ? 1L : 0L) != 0L)) {
                return status;
            }
            flag1 = fradd(sumYPtr, framePtr + 96L, framePtr + 96L, 1L) != 0L ? 1L : 0L;
            status = flag1;
            if (!(flag1 < 2L && comma(status = fimul(framePtr + 32L, framePtr + 96L, framePtr + 96L), (long) (int) status < 2L && comma(status = fimul(sumXPtr, sumX2Ptr, framePtr + 80L), (long) (int) status < 2L ? 1L : 0L) != 0L ? 1L : 0L) != 0L && comma(status = fidiv(framePtr + 80L, sumXYPtr, framePtr + 80L), (long) (int) status < 2L ? 1L : 0L) != 0L)) {
                return status;
            }
            flag1 = fradd(sumY2Ptr, framePtr + 80L, framePtr + 16L, 1L) != 0L ? 1L : 0L;
            status = flag1;
            if (flag1 >= 2L) {
                return status;
            }
            flag2 = fisqrt(framePtr + 96L, framePtr + 96L) & 0xffL;
            status = flag2;
            if (!(flag2 < 2L && comma(status = fidiv(framePtr + 16L, framePtr + 96L, outCorrPtr), (long) (int) status < 2L ? 1L : 0L) != 0L && comma(status = fidiv(framePtr + 16L, framePtr + 32L, framePtr + 48L), (long) (int) status < 2L && comma(status = fidiv(sumXPtr, sumXYPtr, framePtr + 96L), (long) (int) status < 2L && comma(status = fidiv(sumX2Ptr, sumXYPtr, framePtr + 80L), (long) (int) status < 2L ? 1L : 0L) != 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L && comma(status = fimul(framePtr + 48L, framePtr + 96L, framePtr + 96L), (long) (int) status < 2L ? 1L : 0L) != 0L)) {
                return status;
            }
            flag1 = fradd(framePtr + 80L, framePtr + 96L, framePtr + 64L, 1L) != 0L ? 1L : 0L;
            status = flag1;
            if (flag1 >= 2L) {
                return status;
            }
            flag1 = fradd(yInputPtr, framePtr + 64L, framePtr + 96L, 1L) != 0L ? 1L : 0L;
            status = flag1;
            if (flag1 < 2L) {
                status = fidiv(framePtr + 96L, framePtr + 48L, outXHatPtr);
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long CC_New_LinearEstimateY(long nPtr, long sumXPtr, long sumYPtr, long sumX2Ptr, long sumY2Ptr, long sumXYPtr, long xInputPtr, long outYHatPtr, long outCorrPtr) {
        long flag1 = 0L;
        long flag2 = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(288);
        try {
            putI64(framePtr + 88L, 0L);
            putI64(framePtr + 80L, 0L);
            putI64(outYHatPtr + 8L, 0L);
            putI32(outYHatPtr, 1L);
            putI32(outYHatPtr + 4L, 0L);
            putI64(outCorrPtr + 8L, 0L);
            putI32(outCorrPtr, 1L);
            putI32(outCorrPtr + 4L, 0L);
            putI64(framePtr + 96L, getI64(framePtr + 80L));
            putI64(framePtr + 104L, getI64(framePtr + 88L));
            status = fimul(sumXPtr, sumXPtr, framePtr + 96L);
            if (!((long) (int) status < 2L && comma(status = fidiv(framePtr + 96L, sumXYPtr, framePtr + 96L), (long) (int) status < 2L ? 1L : 0L) != 0L)) {
                return status;
            }
            flag1 = fradd(nPtr, framePtr + 96L, framePtr + 32L, 1L) != 0L ? 1L : 0L;
            status = flag1;
            if (!(flag1 < 2L && comma(status = fimul(sumX2Ptr, sumX2Ptr, framePtr + 96L), (long) (int) status < 2L && comma(status = fidiv(framePtr + 96L, sumXYPtr, framePtr + 96L), (long) (int) status < 2L ? 1L : 0L) != 0L ? 1L : 0L) != 0L)) {
                return status;
            }
            flag1 = fradd(sumYPtr, framePtr + 96L, framePtr + 96L, 1L) != 0L ? 1L : 0L;
            status = flag1;
            if (!(flag1 < 2L && comma(status = fimul(framePtr + 32L, framePtr + 96L, framePtr + 96L), (long) (int) status < 2L && comma(status = fimul(sumXPtr, sumX2Ptr, framePtr + 80L), (long) (int) status < 2L ? 1L : 0L) != 0L ? 1L : 0L) != 0L && comma(status = fidiv(framePtr + 80L, sumXYPtr, framePtr + 80L), (long) (int) status < 2L ? 1L : 0L) != 0L)) {
                return status;
            }
            flag1 = fradd(sumY2Ptr, framePtr + 80L, framePtr + 16L, 1L) != 0L ? 1L : 0L;
            status = flag1;
            if (flag1 >= 2L) {
                return status;
            }
            flag2 = fisqrt(framePtr + 96L, framePtr + 96L) & 0xffL;
            status = flag2;
            if (!(flag2 < 2L && comma(status = fidiv(framePtr + 16L, framePtr + 96L, outCorrPtr), (long) (int) status < 2L ? 1L : 0L) != 0L && comma(status = fidiv(framePtr + 16L, framePtr + 32L, framePtr + 48L), (long) (int) status < 2L && comma(status = fidiv(sumXPtr, sumXYPtr, framePtr + 96L), (long) (int) status < 2L && comma(status = fidiv(sumX2Ptr, sumXYPtr, framePtr + 80L), (long) (int) status < 2L ? 1L : 0L) != 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L && comma(status = fimul(framePtr + 48L, framePtr + 96L, framePtr + 96L), (long) (int) status < 2L ? 1L : 0L) != 0L)) {
                return status;
            }
            flag1 = fradd(framePtr + 80L, framePtr + 96L, framePtr + 64L, 1L) != 0L ? 1L : 0L;
            status = flag1;
            if (flag1 < 2L && comma(status = fimul(framePtr + 48L, xInputPtr, framePtr + 96L), (long) (int) status < 2L ? 1L : 0L) != 0L) {
                flag1 = fradd(framePtr + 64L, framePtr + 96L, outYHatPtr, 0L) != 0L ? 1L : 0L;
                status = flag1;
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

    CC_Real myTGetEntry(long dataPtr, long entryIndex, long outEntryPtr) {
        long dataPtr1 = 0L;
        CC_Real realVal = new CC_Real();
        dataPtr1 = dataPtr + (outEntryPtr & 0xffffffffL) * 32L;
        putI64(Callback_Result + 8L, getI64(dataPtr1 + 8L));
        putI64(Callback_Result, getI64(dataPtr1));
        putI64(Callback_Result + 24L, getI64(dataPtr1 + 24L));
        putI64(Callback_Result + 16L, getI64(dataPtr1 + 16L));
        realVal.m = entryIndex & 0xffffffffL;
        realVal.s = (long) (int) Callback_Result;
        realVal.e = (long) (int) (Callback_Result >>> 32);
        return realVal.copy();
    }

    long TipCalc(long billPtr, long tipPctPtr, long splitPtr, long outTipAmtPtr, long outTotalPerPersonPtr) {
        long isLessOrEqual = 0L;
        long status = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(192);
        try {
            status = 24L;
            if (!(splitPtr != 0L && tipPctPtr != 0L && billPtr != 0L && (outTipAmtPtr != 0L || outTotalPerPersonPtr != 0L))) {
                return status;
            }
            isLessOrEqual = fLE(CC_0, billPtr) != 0L ? 1L : 0L;
            if (isLessOrEqual != 0L && (long) (int) comma(isLessOrEqual = fLE(CC_0, tipPctPtr) != 0L ? 1L : 0L, isLessOrEqual) != 0L && comma(isLessOrEqual = fiInteger(splitPtr) != 0L ? 1L : 0L, isLessOrEqual != 0L && (long) (int) comma(isLessOrEqual = fLE(CC_1, splitPtr) != 0L ? 1L : 0L, isLessOrEqual) != 0L ? 1L : 0L) != 0L) {
                status = fidiv(tipPctPtr, CC_100, framePtr + 40L);
                if ((long) (int) status >= 2L) {
                    return status;
                }
                if (outTipAmtPtr != 0L) {
                    status = fimul(billPtr, framePtr + 40L, framePtr + 24L);
                    if (1L < (long) (int) status) {
                        return status;
                    }
                    status = fidiv(framePtr + 24L, splitPtr, outTipAmtPtr);
                }
                if (outTotalPerPersonPtr == 0L) {
                    return status;
                }
                isLessOrEqual = fradd(CC_1, framePtr + 40L, framePtr + 24L, 0L) != 0L ? 1L : 0L;
                status = isLessOrEqual;
                if (isLessOrEqual < 2L && comma(status = fimul(billPtr, framePtr + 24L, framePtr + 24L), (long) (int) status < 2L ? 1L : 0L) != 0L) {
                    status = fidiv(framePtr + 24L, splitPtr, outTotalPerPersonPtr);
                }
            } else {
                status = 24L;
            }
            return status;
        } finally {
            sp = savedSp;
        }
    }

}

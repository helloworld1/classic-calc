package org.liberty.android.classiccalc.engine;

/**
 * Trigonometric, hyperbolic, exponential, logarithmic, power, root, factorial, and root-finding functions.
 */
abstract class ScientificMathEngine extends BcdMathEngine {
    long ZeroFinder(long funcPtr, long guess1Ptr, long guess2Ptr, long contextPtr, long outRootPtr, long outFuncValPtr, long paramCount) {
        long intPtr1 = 0L;
        long longVal1 = 0L;
        long intVal = 0L;
        long dataPtr1 = 0L;
        long isZero1 = 0L;
        long isZero2 = 0L;
        long tempVal1 = 0L;
        long offset1 = 0L;
        long intPtr2 = 0L;
        long dataPtr2 = 0L;
        long dataPtr3 = 0L;
        long tempVal2 = 0L;
        long index = 0L;
        long constPtr = 0L;
        long dataPtr4 = 0L;
        long longVal2 = 0L;
        long dataPtr5 = 0L;
        long longVal3 = 0L;
        long dataPtr6 = 0L;
        long offset2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(848);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        dataPtr1 = 0L;
                        dataPtr6 = 0L;
                        putU8(framePtr + 616L, 0L);
                        putI32(framePtr + 664L, 0L);
                        putI64(framePtr + 624L, guess2Ptr);
                        putI64(framePtr + 632L, contextPtr);
                        putI64(framePtr + 640L, outRootPtr);
                        putI64(framePtr + 648L, outFuncValPtr);
                        putI64(framePtr + 656L, paramCount);
                        memclr4(framePtr + 392L, 224L);
                        putI64(framePtr + 416L, 0x999999999999000L);
                        putI64(framePtr + 408L, 0x1f3fffffffeL);
                        putI64(framePtr + 432L, getI64(funcPtr + 8L));
                        putI64(framePtr + 424L, getI64(funcPtr));
                        putI64(framePtr + 464L, getI64(guess1Ptr + 8L));
                        putI64(framePtr + 456L, getI64(guess1Ptr));
                        putI64(framePtr + 480L, 0x999999999999000L);
                        putI64(framePtr + 472L, 0x1f300000002L);
                        putI64(framePtr + 528L, 0x999999999999999L);
                        putI64(framePtr + 520L, 0xc34f00000002L);
                        putI64(framePtr + 592L, 0x999999999999999L);
                        putI64(framePtr + 584L, 0xc34f00000002L);
                        isZero1 = fEQ(framePtr + 424L, framePtr + 456L) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            isZero1 = fZero(framePtr + 456L) != 0L ? 1L : 0L;
                            if (isZero1 != 0L) {
                                putI64(framePtr + 464L, 0x100000000000000L);
                                putI64(framePtr + 456L, 1L);
                            } else {
                                if ((getI64(framePtr + 464L) & 0xf00000000L) == 0L) {
                                    index = (long) (int) (getI32(framePtr + 468L) + 1L);
                                } else {
                                    index = (long) (int) (getI32(framePtr + 468L) - 1L);
                                }
                                putI64(framePtr + 464L, (((index & 0xffffffffL)) << 32) | (getI64(framePtr + 464L) & 0xffffffffL));
                            }
                        }
                        isZero1 = fLT(framePtr + 456L, framePtr + 424L) != 0L ? 1L : 0L;
                        offset1 = getI64(framePtr + 432L);
                        dataPtr6 = getI64(framePtr + 424L);
                        if (isZero1 != 0L) {
                            putI64(framePtr + 120L, getI64(framePtr + 432L));
                            putU32(framePtr + 112L, getU32(framePtr + 424L));
                            putI32(framePtr + 116L, getU32(framePtr + 428L));
                            putI64(framePtr + 432L, getI64(framePtr + 464L));
                            putU32(framePtr + 424L, getU32(framePtr + 456L));
                            putU32(framePtr + 428L, getU32(framePtr + 460L));
                            dataPtr1 = getI64(framePtr + 424L);
                            putI64(framePtr + 464L, offset1);
                            putU32(framePtr + 424L, dataPtr6);
                            putU32(framePtr + 428L, dataPtr6 >>> 32);
                            putU32(framePtr + 456L, getU32(framePtr + 424L));
                            putU32(framePtr + 460L, getU32(framePtr + 428L));
                            putI64(framePtr + 424L, dataPtr1);
                        }
                        longVal2 = 2L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 4L;
                        }
                        dataPtr3 = framePtr + 504L + longVal2 * 16L;
                        callIndirect3(getI64(framePtr + 624L), framePtr + 392L + longVal2 * 16L, getI64(framePtr + 640L), dataPtr3);
                        if (((getU32(framePtr + 504L + longVal2 * 16L) + 2L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(framePtr + 504L + longVal2 * 16L) + 2L) & 31L)) & 21L) != 0L || comma(tempVal1 = getU32(framePtr + 504L + (longVal2 * 4L + 1L) * 4L), 24999L < (long) (int) tempVal1 ? 1L : 0L) != 0L) {
                            putI64(framePtr + 512L + longVal2 * 16L, 0x999999999999999L);
                            putU32(dataPtr3, 2L);
                            putU32(dataPtr3 + 4L, 49999L);
                            tempVal1 = getU32(framePtr + 504L + (longVal2 * 4L + 1L) * 4L);
                        }
                        if ((long) (int) tempVal1 < -24999L) {
                            putI64(framePtr + 512L + longVal2 * 16L, 0L);
                            putU32(dataPtr3, 1L);
                            putU32(dataPtr3 + 4L, 0L);
                        }
                        isZero1 = fZero(dataPtr3) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            offset1 = getI64(framePtr + 400L + longVal2);
                            dataPtr6 = getI64(framePtr + 392L + longVal2);
                            putI64(getI64(framePtr + 656L) + 8L, offset1);
                            putI64(getI64(framePtr + 656L), dataPtr6);
                            tempVal1 = 0L;
                            paramCount = getI64(framePtr + 648L);
                            putI64(paramCount + 8L, offset1);
                            putI64(paramCount, dataPtr6);
                            return (long) (int) tempVal1;
                        }
                        longVal2 = 4L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 2L;
                        }
                        dataPtr3 = framePtr + 504L + longVal2 * 16L;
                        callIndirect3(getI64(framePtr + 624L), framePtr + 392L + longVal2 * 16L, getI64(framePtr + 640L), dataPtr3);
                        if (((getU32(framePtr + 504L + longVal2 * 16L) + 2L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(framePtr + 504L + longVal2 * 16L) + 2L) & 31L)) & 21L) != 0L || comma(tempVal1 = getU32(framePtr + 504L + (longVal2 * 4L + 1L) * 4L), 24999L < (long) (int) tempVal1 ? 1L : 0L) != 0L) {
                            putI64(framePtr + 512L + longVal2 * 16L, 0x999999999999999L);
                            putU32(dataPtr3, 2L);
                            putU32(dataPtr3 + 4L, 49999L);
                            tempVal1 = getU32(framePtr + 504L + (longVal2 * 4L + 1L) * 4L);
                        }
                        if ((long) (int) tempVal1 < -24999L) {
                            putI64(framePtr + 512L + longVal2 * 16L, 0L);
                            putU32(dataPtr3, 1L);
                            putU32(dataPtr3 + 4L, 0L);
                        }
                        isZero1 = fZero(dataPtr3) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            offset1 = getI64(framePtr + 400L + longVal2);
                            dataPtr6 = getI64(framePtr + 392L + longVal2);
                            putI64(getI64(framePtr + 656L) + 8L, offset1);
                            putI64(getI64(framePtr + 656L), dataPtr6);
                            tempVal1 = 0L;
                            paramCount = getI64(framePtr + 648L);
                            putI64(paramCount + 8L, offset1);
                            putI64(paramCount, dataPtr6);
                            return (long) (int) tempVal1;
                        }
                        if (getU32(framePtr + 536L) == 2L && getU32(framePtr + 568L) == 2L) {
                            putI64(outFuncValPtr + 8L, getI64(framePtr + 432L));
                            putI64(outFuncValPtr, framePtr + 424L);
                            tempVal1 = 40L;
                            putI64(paramCount + 8L, getI64(framePtr + 464L));
                            putI64(paramCount, framePtr + 456L);
                            return (long) (int) tempVal1;
                        }
                        if (getI64(framePtr + 632L) != 0L) {
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            isZero1 = fLT(framePtr + 392L + longVal3, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                            tempVal1 = 4L;
                            tempVal2 = 2L;
                            if (isZero1 == 0L) {
                                tempVal1 = 2L;
                                tempVal2 = 4L;
                            }
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal1 = (6L - tempVal1) & 0xffffffffL;
                            }
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal2 = (6L - tempVal2) & 0xffffffffL;
                            }
                            putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                            offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal1 * 16L, framePtr + 392L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, getI64(framePtr + 640L));
                            if ((offset1 & 1L) != 0L) {
                                tempVal1 = 30L;
                                return (long) (int) tempVal1;
                            }
                        }
                        if (getU32(framePtr + 536L) == 2L) {
                            putU8(framePtr + 616L, getU8(framePtr + 616L) ^ 1L);
                        }
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        if (getI32(framePtr + 504L + longVal2) != 2L) {
                            step = 1;
                            continue stepLoop;
                        } else {
                            dataPtr3 = framePtr + 552L;
                            step = 49;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        if (getI64(framePtr + 632L) != 0L) {
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            isZero1 = fLT(framePtr + 392L + longVal3, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                            tempVal1 = 4L;
                            tempVal2 = 2L;
                            if (isZero1 == 0L) {
                                tempVal1 = 2L;
                                tempVal2 = 4L;
                            }
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal1 = (6L - tempVal1) & 0xffffffffL;
                            }
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal2 = (6L - tempVal2) & 0xffffffffL;
                            }
                            putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                            offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal1 * 16L, framePtr + 392L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, getI64(framePtr + 640L));
                            if ((offset1 & 1L) != 0L) {
                                tempVal1 = 30L;
                                return (long) (int) tempVal1;
                            }
                        }
                        dataPtr3 = framePtr + 552L;
                        step = 2;
                        continue stepLoop;
                    }
                    case 2: {
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        longVal3 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 64L;
                        }
                        isZero1 = fEQ(framePtr + 504L + longVal2, framePtr + 504L + longVal3) != 0L ? 1L : 0L;
                        if (getI64(framePtr + 632L) == 0L) {
                            if (isZero1 != 0L) {
                                step = 4;
                                continue stepLoop;
                            } else {
                                step = 3;
                                continue stepLoop;
                            }
                        } else {
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            isZero2 = fLT(framePtr + 392L + longVal3, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                            tempVal1 = 4L;
                            tempVal2 = 2L;
                            if (isZero2 == 0L) {
                                tempVal1 = 2L;
                                tempVal2 = 4L;
                            }
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal1 = (6L - tempVal1) & 0xffffffffL;
                            }
                            putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal2 = (6L - tempVal2) & 0xffffffffL;
                            }
                            offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal1 * 16L, framePtr + 392L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, getI64(framePtr + 640L));
                            if (isZero1 == 0L) {
                                if ((offset1 & 1L) != 0L) {
                                    tempVal1 = 30L;
                                    return (long) (int) tempVal1;
                                }
                                step = 3;
                                continue stepLoop;
                            } else {
                                if ((offset1 & 1L) != 0L) {
                                    tempVal1 = 30L;
                                    return (long) (int) tempVal1;
                                }
                                step = 4;
                                continue stepLoop;
                            }
                        }
                    }
                    case 3: {
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        longVal3 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 64L;
                        }
                        isZero1 = fMLE(framePtr + 504L + longVal3, framePtr + 504L + longVal2) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            putU8(framePtr + 616L, getU8(framePtr + 616L) ^ 1L);
                        }
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        longVal3 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 64L;
                        }
                        if (getI32(framePtr + 504L + longVal2) == getI32(framePtr + 504L + longVal3)) {
                            isZero1 = 0L;
                            step = 12;
                            continue stepLoop;
                        } else {
                            dataPtr4 = (framePtr + 112L) | 8L;
                            step = 30;
                            continue stepLoop;
                        }
                    }
                    case 4: {
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        longVal3 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 64L;
                        }
                        fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 96L, 1L);
                        fiMul100(framePtr + 96L);
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        fradd(framePtr + 392L + longVal2, framePtr + 96L, framePtr + 112L, 0L);
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        longVal3 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 16L;
                        }
                        intPtr2 = framePtr + 392L + longVal2;
                        if (getU8(framePtr + 616L) == 0L) {
                            isZero1 = fLT(intPtr2, framePtr + 112L) != 0L ? 1L : 0L;
                            if (isZero1 == 0L || comma(isZero1 = fLT(framePtr + 112L, framePtr + 392L + longVal3) != 0L ? 1L : 0L, isZero1 == 0L ? 1L : 0L) != 0L) {
                                fLE(framePtr + 112L, intPtr2);
                                step = 7;
                                continue stepLoop;
                            } else {
                                step = 5;
                                continue stepLoop;
                            }
                        } else {
                            isZero1 = fLT(framePtr + 392L + longVal3, framePtr + 112L) != 0L ? 1L : 0L;
                            if (isZero1 != 0L && (long) (int) comma(isZero1 = fLT(framePtr + 112L, intPtr2) != 0L ? 1L : 0L, isZero1) != 0L) {
                                step = 5;
                                continue stepLoop;
                            } else {
                                fLE(intPtr2, framePtr + 112L);
                                step = 7;
                                continue stepLoop;
                            }
                        }
                    }
                    case 5: {
                        tempVal1 = ZeroFinderEval(framePtr + 112L, framePtr + 392L) & 0xffffffffL;
                        if (tempVal1 == 5L) {
                            longVal2 = 80L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 16L;
                            }
                            offset2 = getI64(framePtr + 392L + longVal2);
                            longVal3 = 96L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 0L;
                            }
                            putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                            putI64(framePtr + 392L + longVal3, offset2);
                            longVal2 = 80L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 16L;
                            }
                            offset2 = getI64(framePtr + 504L + longVal2);
                            longVal3 = 96L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 0L;
                            }
                            putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                            putI64(framePtr + 504L + longVal3, offset2);
                            step = 7;
                            continue stepLoop;
                        } else {
                            if (!(tempVal1 != 0L && tempVal1 != 30L)) {
                                return (long) (int) tempVal1;
                            }
                            step = 6;
                            continue stepLoop;
                        }
                    }
                    case 6: {
                        if (tempVal1 != 4L) {
                            tempVal1 = 40L;
                            putI64(outFuncValPtr + 8L, getI64(framePtr + 432L));
                            putI64(outFuncValPtr, framePtr + 424L);
                            putI64(paramCount + 8L, getI64(framePtr + 464L));
                            putI64(paramCount, framePtr + 456L);
                            if (getI64(framePtr + 632L) == 0L) {
                                return (long) (int) tempVal1;
                            }
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            dataPtr3 = framePtr + 392L + longVal2;
                            intPtr2 = framePtr + 392L + longVal3;
                            step = 53;
                            continue stepLoop;
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 7: {
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        longVal3 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 16L;
                        }
                        isZero1 = ZeroFinderMidpoint(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 112L) != 0L ? 1L : 0L;
                        if (isZero1 == 0L) {
                            step = 8;
                            continue stepLoop;
                        } else {
                            tempVal1 = ZeroFinderEval(framePtr + 112L, framePtr + 392L) & 0xffffffffL;
                            if (tempVal1 == 5L) {
                                step = 7;
                                continue stepLoop;
                            } else {
                                if (tempVal1 == 0L) {
                                    return (long) (int) tempVal1;
                                }
                                if (tempVal1 != 4L) {
                                    if (tempVal1 == 30L) {
                                        return (long) (int) tempVal1;
                                    }
                                    step = 8;
                                    continue stepLoop;
                                } else {
                                    step = 6;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 8: {
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        longVal3 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 16L;
                        }
                        isZero1 = ZeroFinderMidpoint(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 112L) != 0L ? 1L : 0L;
                        if (isZero1 == 0L) {
                            longVal2 = 0L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 96L;
                            }
                            tempVal1 = (getI32(framePtr + 504L + longVal2) + 1L) & 0xffffffffL;
                            if (tempVal1 < 5L && ((1L << (int) (tempVal1 & 31L)) & 21L) != 0L) {
                                tempVal1 = 42L;
                                putI64(outFuncValPtr + 8L, 0x999999999999000L);
                                putI64(outFuncValPtr, 0x1f3fffffffeL);
                                paramCount = paramCount;
                                dataPtr6 = 0x1f300000002L;
                                offset1 = 0x999999999999000L;
                                putI64(paramCount + 8L, offset1);
                                putI64(paramCount, dataPtr6);
                                return (long) (int) tempVal1;
                            }
                            longVal2 = 80L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 16L;
                            }
                            offset2 = getI64(framePtr + 392L + longVal2);
                            longVal3 = 96L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 0L;
                            }
                            putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                            putI64(framePtr + 392L + longVal3, offset2);
                            longVal2 = 80L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 16L;
                            }
                            offset2 = getI64(framePtr + 504L + longVal2);
                            longVal3 = 96L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 0L;
                            }
                            putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                            putI64(framePtr + 504L + longVal3, offset2);
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            putU8(framePtr + 616L, getU8(framePtr + 616L) ^ 1L);
                            fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 376L, 1L);
                            putI32(framePtr + 380L, getI32(framePtr + 380L) + 2L);
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            fradd(framePtr + 392L + longVal2, framePtr + 376L, framePtr + 112L, 0L);
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            longVal3 = 80L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 16L;
                            }
                            constPtr = framePtr + 392L + longVal2;
                            if (getU8(framePtr + 616L) == 0L) {
                                isZero1 = fLT(constPtr, framePtr + 112L) != 0L ? 1L : 0L;
                                if (isZero1 != 0L && (long) (int) comma(isZero1 = fLT(framePtr + 112L, framePtr + 392L + longVal3) != 0L ? 1L : 0L, isZero1) != 0L) {
                                    step = 9;
                                    continue stepLoop;
                                } else {
                                    dataPtr2 = framePtr + 112L;
                                    fLE(dataPtr2, constPtr);
                                    step = 8;
                                    continue stepLoop;
                                }
                            } else {
                                isZero1 = fLT(framePtr + 392L + longVal3, framePtr + 112L) != 0L ? 1L : 0L;
                                if (isZero1 != 0L && (long) (int) comma(isZero1 = fLT(framePtr + 112L, constPtr) != 0L ? 1L : 0L, isZero1) != 0L) {
                                    step = 9;
                                    continue stepLoop;
                                } else {
                                    dataPtr2 = constPtr;
                                    constPtr = framePtr + 112L;
                                    fLE(dataPtr2, constPtr);
                                    step = 8;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            putI64(framePtr + 448L, getI64(framePtr + 120L));
                            putU32(framePtr + 440L, getU32(framePtr + 112L));
                            putU32(framePtr + 444L, getI32(framePtr + 116L));
                            callIndirect3(getI64(framePtr + 624L), framePtr + 440L, getI64(framePtr + 640L), dataPtr3);
                            isZero1 = fZero(dataPtr3) != 0L ? 1L : 0L;
                            if (isZero1 != 0L) {
                                step = 10;
                                continue stepLoop;
                            } else {
                                step = 11;
                                continue stepLoop;
                            }
                        }
                    }
                    case 9: {
                        putI64(framePtr + 448L, getI64(framePtr + 120L));
                        putU32(framePtr + 440L, getU32(framePtr + 112L));
                        putU32(framePtr + 444L, getI32(framePtr + 116L));
                        callIndirect3(getI64(framePtr + 624L), framePtr + 440L, getI64(framePtr + 640L), dataPtr3);
                        if (((getU32(framePtr + 552L) + 2L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(framePtr + 552L) + 2L) & 31L)) & 21L) != 0L || comma(tempVal1 = getU32(framePtr + 556L), 24999L < (long) (int) getU32(framePtr + 556L) ? 1L : 0L) != 0L) {
                            putU32(framePtr + 560L, 0x99999999L);
                            putU32(framePtr + 564L, 0x9999999L);
                            putU32(framePtr + 552L, 2L);
                            putU32(framePtr + 556L, 49999L);
                            offset2 = getI64(framePtr + 552L);
                            putU32(framePtr + 556L, 49999L);
                            tempVal1 = getU32(framePtr + 556L);
                            putI64(framePtr + 552L, offset2);
                        }
                        if ((long) (int) tempVal1 < -24999L) {
                            putU32(framePtr + 560L, 0L);
                            putU32(framePtr + 564L, 0L);
                            putU32(framePtr + 552L, 1L);
                            putU32(framePtr + 556L, 0L);
                        }
                        isZero1 = fZero(dataPtr3) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            step = 10;
                            continue stepLoop;
                        } else {
                            step = 11;
                            continue stepLoop;
                        }
                    }
                    case 10: {
                        tempVal1 = 0L;
                        putI64(getI64(framePtr + 656L) + 8L, getI64(framePtr + 448L));
                        putI64(getI64(framePtr + 656L), framePtr + 440L);
                        paramCount = getI64(framePtr + 648L);
                        dataPtr6 = getI64(framePtr + 440L);
                        offset1 = getI64(framePtr + 448L);
                        putI64(paramCount + 8L, offset1);
                        putI64(paramCount, dataPtr6);
                        return (long) (int) tempVal1;
                    }
                    case 11: {
                        offset2 = getI64(framePtr + 552L);
                        dataPtr6 = getI64(framePtr + 440L);
                        if (4L < ((getU32(framePtr + 552L) + 1L) & 0xffffffffL) || ((1L << (int) ((getU32(framePtr + 552L) + 1L) & 31L)) & 21L) == 0L) {
                            longVal2 = 80L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 16L;
                            }
                            putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                            putI64(framePtr + 392L + longVal2, dataPtr6);
                            longVal2 = 80L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 16L;
                            }
                            putI64(framePtr + 512L + longVal2, getI64(framePtr + 560L));
                            putI64(framePtr + 504L + longVal2, offset2);
                            if (getI64(framePtr + 632L) != 0L) {
                                longVal2 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 64L;
                                }
                                longVal3 = 80L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 16L;
                                }
                                isZero1 = fLT(framePtr + 392L + longVal3, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                                tempVal1 = 5L;
                                tempVal2 = 2L;
                                if (isZero1 == 0L) {
                                    tempVal1 = 2L;
                                    tempVal2 = 5L;
                                }
                                if (getU8(framePtr + 616L) != 0L) {
                                    tempVal1 = (6L - tempVal1) & 0xffffffffL;
                                }
                                putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                                if (getU8(framePtr + 616L) != 0L) {
                                    tempVal2 = (6L - tempVal2) & 0xffffffffL;
                                }
                                offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal1 * 16L, framePtr + 392L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, getI64(framePtr + 640L));
                                if ((offset1 & 1L) != 0L) {
                                    tempVal1 = 30L;
                                    return (long) (int) tempVal1;
                                }
                            }
                            step = 8;
                            continue stepLoop;
                        } else {
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                            putI64(framePtr + 392L + longVal2, dataPtr6);
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            putI64(framePtr + 512L + longVal2, getI64(framePtr + 560L));
                            putI64(framePtr + 504L + longVal2, offset2);
                            if (getI64(framePtr + 632L) != 0L) {
                                longVal2 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 64L;
                                }
                                longVal3 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 32L;
                                }
                                isZero1 = fLT(framePtr + 392L + longVal3, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                                tempVal1 = 4L;
                                tempVal2 = 2L;
                                if (isZero1 == 0L) {
                                    tempVal1 = 2L;
                                    tempVal2 = 4L;
                                }
                                if (getU8(framePtr + 616L) != 0L) {
                                    tempVal1 = (6L - tempVal1) & 0xffffffffL;
                                }
                                putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                                if (getU8(framePtr + 616L) != 0L) {
                                    tempVal2 = (6L - tempVal2) & 0xffffffffL;
                                }
                                offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal1 * 16L, framePtr + 392L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, getI64(framePtr + 640L));
                                if ((offset1 & 1L) != 0L) {
                                    tempVal1 = 30L;
                                    return (long) (int) tempVal1;
                                }
                            }
                            longVal2 = 80L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 16L;
                            }
                            offset2 = getI64(framePtr + 392L + longVal2);
                            longVal3 = 96L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 0L;
                            }
                            tempVal1 = 4L;
                            putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                            putI64(framePtr + 392L + longVal3, offset2);
                            longVal2 = 80L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 16L;
                            }
                            offset2 = getI64(framePtr + 504L + longVal2);
                            longVal3 = 96L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 0L;
                            }
                            putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                            putI64(framePtr + 504L + longVal3, offset2);
                            putU8(framePtr + 616L, getU8(framePtr + 616L) ^ 1L);
                            step = 6;
                            continue stepLoop;
                        }
                    }
                    case 12: {
                        if (getI64(framePtr + 632L) != 0L) {
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            isZero2 = fLT(framePtr + 392L + longVal3, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                            tempVal1 = 4L;
                            tempVal2 = 2L;
                            if (isZero2 == 0L) {
                                tempVal1 = 2L;
                                tempVal2 = 4L;
                            }
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal1 = (6L - tempVal1) & 0xffffffffL;
                            }
                            putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal2 = (6L - tempVal2) & 0xffffffffL;
                            }
                            offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal1 * 16L, framePtr + 392L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, getI64(framePtr + 640L));
                            if ((offset1 & 1L) != 0L) {
                                tempVal1 = 30L;
                                return (long) (int) tempVal1;
                            }
                        }
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        offset2 = getI64(framePtr + 392L + longVal2);
                        longVal3 = 16L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 80L;
                        }
                        putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                        putI64(framePtr + 392L + longVal3, offset2);
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        offset2 = getI64(framePtr + 504L + longVal2);
                        longVal3 = 16L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 80L;
                        }
                        putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                        putI64(framePtr + 504L + longVal3, offset2);
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        longVal3 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 32L;
                        }
                        fradd(framePtr + 504L + longVal2, framePtr + 504L + longVal3, framePtr + 96L, 1L);
                        constPtr = framePtr + 80L;
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        fdiv(framePtr + 504L + longVal2, framePtr + 96L, framePtr + 80L);
                        fimin(CC_50, framePtr + 80L, framePtr + 96L);
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        longVal3 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 32L;
                        }
                        fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 64L, 1L);
                        fmul(framePtr + 96L, framePtr + 64L, framePtr + 80L);
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        dataPtr2 = framePtr + 392L + longVal2;
                        step = 13;
                        continue stepLoop;
                    }
                    case 13: {
                        fradd(dataPtr2, constPtr, framePtr + 112L, 1L);
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        longVal3 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 16L;
                        }
                        constPtr = framePtr + 392L + longVal2;
                        if (getU8(framePtr + 616L) == 0L) {
                            isZero2 = fLT(constPtr, framePtr + 112L) != 0L ? 1L : 0L;
                            if (isZero2 == 0L || comma(isZero2 = fLT(framePtr + 112L, framePtr + 392L + longVal3) != 0L ? 1L : 0L, isZero2 == 0L ? 1L : 0L) != 0L) {
                                dataPtr2 = framePtr + 112L;
                                step = 14;
                                continue stepLoop;
                            } else {
                                step = 16;
                                continue stepLoop;
                            }
                        } else {
                            isZero2 = fLT(framePtr + 392L + longVal3, framePtr + 112L) != 0L ? 1L : 0L;
                            if (isZero2 == 0L || comma(isZero2 = fLT(framePtr + 112L, constPtr) != 0L ? 1L : 0L, isZero2 == 0L ? 1L : 0L) != 0L) {
                                dataPtr2 = constPtr;
                                constPtr = framePtr + 112L;
                                step = 14;
                                continue stepLoop;
                            } else {
                                step = 16;
                                continue stepLoop;
                            }
                        }
                    }
                    case 14: {
                        isZero2 = fLE(dataPtr2, constPtr) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            putI64(framePtr + 104L, 0x100000000000000L);
                            longVal2 = 4L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 2L;
                            }
                            putI64(framePtr + 96L, ((((getU32(framePtr + 392L + (longVal2 * 4L + 1L) * 4L) - 5L) & 0xffffffffL)) << 32) | 1L);
                            if ((long) (int) getU32(framePtr + 392L + (longVal2 * 4L + 1L) * 4L) < 504L) {
                                putI64(framePtr + 104L, 0L);
                                putI64(framePtr + 96L, 1L);
                            }
                            if (getU8(framePtr + 616L) == 0L) {
                                fradd(framePtr + 392L + longVal2 * 16L, framePtr + 96L, framePtr + 112L, 0L);
                                constPtr = framePtr + 112L;
                                longVal2 = 80L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 16L;
                                }
                                dataPtr2 = framePtr + 392L + longVal2;
                            } else {
                                fradd(framePtr + 392L + longVal2 * 16L, framePtr + 96L, framePtr + 112L, 1L);
                                dataPtr2 = framePtr + 112L;
                                longVal2 = 80L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 16L;
                                }
                                constPtr = framePtr + 392L + longVal2;
                            }
                            isZero2 = fLT(constPtr, dataPtr2) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                isZero2 = fZero(framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                                if (isZero2 == 0L) {
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
                        } else {
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            longVal3 = 80L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 16L;
                            }
                            isZero2 = ZeroFinderMidpoint(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 112L) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                step = 16;
                                continue stepLoop;
                            } else {
                                step = 17;
                                continue stepLoop;
                            }
                        }
                    }
                    case 15: {
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        longVal3 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 16L;
                        }
                        intPtr2 = framePtr + 392L + longVal2;
                        intPtr1 = framePtr + 392L + longVal3;
                        isZero2 = fEQ(intPtr2, intPtr1) != 0L ? 1L : 0L;
                        if (isZero2 == 0L) {
                            putI64(framePtr + 120L, getI64(framePtr + 400L + longVal2));
                            putI64(framePtr + 112L, getI64(intPtr2));
                            isZero2 = fLT(intPtr2, intPtr1) != 0L ? 1L : 0L;
                            fNextAfter(framePtr + 112L, isZero2);
                            isZero2 = fNE(framePtr + 376L, intPtr1) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                step = 16;
                                continue stepLoop;
                            } else {
                                step = 17;
                                continue stepLoop;
                            }
                        } else {
                            step = 17;
                            continue stepLoop;
                        }
                    }
                    case 16: {
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        longVal3 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 16L;
                        }
                        if (getU8(framePtr + 616L) == 0L) {
                            if (getI32(framePtr + 392L + longVal2) < 0L && 0L < getI32(framePtr + 392L + longVal3) && -11L >= getI32(framePtr + 116L)) {
                                step = 20;
                                continue stepLoop;
                            } else {
                                step = 19;
                                continue stepLoop;
                            }
                        } else {
                            if (-1L < getI32(framePtr + 392L + longVal3) || getI32(framePtr + 392L + longVal2) < 1L || -11L < getI32(framePtr + 116L)) {
                                step = 19;
                                continue stepLoop;
                            } else {
                                step = 20;
                                continue stepLoop;
                            }
                        }
                    }
                    case 17: {
                        if (getI64(framePtr + 632L) != 0L) {
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            isZero1 = fLT(framePtr + 392L + longVal3, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                            tempVal1 = 4L;
                            tempVal2 = 2L;
                            if (isZero1 == 0L) {
                                tempVal1 = 2L;
                                tempVal2 = 4L;
                            }
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal1 = (6L - tempVal1) & 0xffffffffL;
                            }
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal2 = (6L - tempVal2) & 0xffffffffL;
                            }
                            putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                            offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal1 * 16L, framePtr + 392L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, getI64(framePtr + 640L));
                            if ((offset1 & 1L) != 0L) {
                                tempVal1 = 30L;
                                return (long) (int) tempVal1;
                            }
                        }
                        putI64(getI64(framePtr + 648L) + 8L, getI64(framePtr + 432L));
                        putI64(getI64(framePtr + 648L), framePtr + 424L);
                        putI64(getI64(framePtr + 656L) + 8L, getI64(framePtr + 464L));
                        putI64(getI64(framePtr + 656L), framePtr + 456L);
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        isZero1 = fMLE(CC_PBig_L, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                        tempVal1 = 39L;
                        if (isZero1 == 0L) {
                            tempVal1 = 41L;
                        }
                        return (long) (int) tempVal1;
                    }
                    case 18: {
                        putI64(framePtr + 112L, getI64(constPtr));
                        putI64(framePtr + 120L, getI64(dataPtr4));
                        tempVal1 = ZeroFinderBracket(framePtr + 112L, framePtr + 392L) & 0xffffffffL;
                        if (tempVal1 != 4L) {
                            return (long) (int) tempVal1;
                        }
                        index = -9L;
                        step = 21;
                        continue stepLoop;
                    }
                    case 19: {
                        constPtr = framePtr + 112L;
                        dataPtr4 = (framePtr + 112L) | 8L;
                        step = 18;
                        continue stepLoop;
                    }
                    case 20: {
                        constPtr = CC_0;
                        dataPtr4 = ADDR_RODATA + 1144L;
                        step = 18;
                        continue stepLoop;
                    }
                    case 21: {
                        longVal2 = 4L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 2L;
                        }
                        if (getU32(framePtr + 552L) != getU32(framePtr + 504L + longVal2 * 16L)) {
                            offset2 = getI64(framePtr + 392L + longVal2 * 16L);
                            longVal3 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 64L;
                            }
                            putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2 * 16L));
                            putI64(framePtr + 392L + longVal3, offset2);
                            dataPtr6 = getI64(framePtr + 440L);
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            offset2 = getI64(framePtr + 504L + longVal2);
                            longVal3 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 64L;
                            }
                            putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                            putI64(framePtr + 504L + longVal3, offset2);
                            offset2 = getI64(framePtr + 552L);
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                            putI64(framePtr + 392L + longVal2, dataPtr6);
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            putI64(framePtr + 512L + longVal2, getI64(framePtr + 560L));
                            putI64(framePtr + 504L + longVal2, offset2);
                            dataPtr4 = (framePtr + 112L) | 8L;
                            step = 30;
                            continue stepLoop;
                        } else {
                            isZero2 = fMLT(dataPtr3, framePtr + 504L + longVal2 * 16L) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                offset2 = getI64(framePtr + 392L + longVal2);
                                longVal3 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 64L;
                                }
                                putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                                putI64(framePtr + 392L + longVal3, offset2);
                                dataPtr6 = getI64(framePtr + 440L);
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                offset2 = getI64(framePtr + 504L + longVal2);
                                longVal3 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 64L;
                                }
                                putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                                putI64(framePtr + 504L + longVal3, offset2);
                                offset2 = getI64(framePtr + 552L);
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                                putI64(framePtr + 392L + longVal2, dataPtr6);
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                putI64(framePtr + 512L + longVal2, getI64(framePtr + 560L));
                                putI64(framePtr + 504L + longVal2, offset2);
                                step = 12;
                                continue stepLoop;
                            } else {
                                if (isZero1 == 0L) {
                                    longVal2 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 32L;
                                    }
                                    fradd(framePtr + 440L, framePtr + 392L + longVal2, framePtr + 96L, 1L);
                                    longVal2 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 32L;
                                    }
                                    putI64(framePtr + 88L, getI64(framePtr + 400L + longVal2));
                                    putI32(framePtr + 84L, getI64(framePtr + 392L + longVal2) >>> 32);
                                    intVal = getI32(framePtr + 84L);
                                    putI64(framePtr + 80L, ((((getI32(framePtr + 84L) - 6L) & 0xffffffffL)) << 32) | (getI64(framePtr + 392L + longVal2) & 0xffffffffL));
                                    if (intVal < 505L) {
                                        putI64(framePtr + 88L, 0L);
                                        putI64(framePtr + 80L, 1L);
                                    }
                                    isZero1 = fMLE(framePtr + 96L, framePtr + 80L) != 0L ? 1L : 0L;
                                    if (isZero1 != 0L) {
                                        dataPtr2 = framePtr + 64L;
                                        longVal2 = 64L;
                                        if (getU8(framePtr + 616L) != 0L) {
                                            longVal2 = 32L;
                                        }
                                        fmul2(framePtr + 392L + longVal2, framePtr + 64L);
                                        isZero1 = 1L;
                                        longVal2 = 32L;
                                        if (getU8(framePtr + 616L) != 0L) {
                                            longVal2 = 64L;
                                        }
                                        constPtr = framePtr + 392L + longVal2;
                                        step = 13;
                                        continue stepLoop;
                                    } else {
                                        step = 22;
                                        continue stepLoop;
                                    }
                                } else {
                                    step = 22;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 22: {
                        offset2 = getI64(framePtr + 552L);
                        dataPtr6 = getI64(framePtr + 440L);
                        longVal2 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 16L;
                        }
                        putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                        putI64(framePtr + 392L + longVal2, dataPtr6);
                        longVal2 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 16L;
                        }
                        putI64(framePtr + 512L + longVal2, getI64(framePtr + 560L));
                        putI64(framePtr + 504L + longVal2, offset2);
                        if (getI64(framePtr + 632L) != 0L) {
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 80L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 16L;
                            }
                            isZero1 = fLT(framePtr + 392L + longVal3, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                            tempVal1 = 5L;
                            tempVal2 = 2L;
                            if (isZero1 == 0L) {
                                tempVal1 = 2L;
                                tempVal2 = 5L;
                            }
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal1 = (6L - tempVal1) & 0xffffffffL;
                            }
                            putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal2 = (6L - tempVal2) & 0xffffffffL;
                            }
                            offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal1 * 16L, framePtr + 392L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, getI64(framePtr + 640L));
                            if ((offset1 & 1L) != 0L) {
                                tempVal1 = 30L;
                                return (long) (int) tempVal1;
                            }
                        }
                        if (index == 0L) {
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            fradd(framePtr + 392L + longVal2, framePtr + 440L, framePtr + 48L, 1L);
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            isZero1 = fMLE(framePtr + 392L + longVal3, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                            longVal2 = 4L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 2L;
                            }
                            longVal3 = 2L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 4L;
                            }
                            if (isZero1 == 0L) {
                                longVal3 = longVal2;
                            }
                            putI64(framePtr + 104L, getI64(framePtr + 400L + longVal3 * 16L));
                            putI64(framePtr + 96L, getI64(framePtr + 392L + longVal3 * 16L));
                            fiDiv100(framePtr + 96L);
                            isZero1 = fMLE(framePtr + 96L, framePtr + 48L) != 0L ? 1L : 0L;
                            if (isZero1 == 0L) {
                                step = 17;
                                continue stepLoop;
                            } else {
                                longVal2 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 64L;
                                }
                                longVal3 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 32L;
                                }
                                fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 32L, 1L);
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                fradd(framePtr + 392L + longVal2, framePtr + 440L, framePtr + 16L, 1L);
                                isZero1 = fMLT(framePtr + 16L, framePtr + 32L) != 0L ? 1L : 0L;
                                longVal2 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 64L;
                                }
                                longVal3 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 32L;
                                }
                                constPtr = framePtr + 392L + longVal2;
                                if (isZero1 == 0L) {
                                    constPtr = framePtr + 440L;
                                }
                                fradd(constPtr, framePtr + 392L + longVal3, framePtr + 80L, 0L);
                                fdiv2(framePtr + 80L, framePtr + 112L);
                                step = 23;
                                continue stepLoop;
                            }
                        } else {
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 344L, 1L);
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            fradd(framePtr + 392L + longVal2, framePtr + 440L, framePtr + 304L, 1L);
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            fradd(framePtr + 392L + longVal2, framePtr + 440L, framePtr + 320L, 1L);
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            fradd(framePtr + 504L + longVal2, framePtr + 504L + longVal3, framePtr + 288L, 1L);
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            fradd(framePtr + 504L + longVal2, dataPtr3, framePtr + 272L, 1L);
                            fdiv(framePtr + 288L, framePtr + 344L, framePtr + 256L);
                            fdiv(framePtr + 272L, framePtr + 304L, framePtr + 240L);
                            fradd(framePtr + 256L, framePtr + 240L, framePtr + 224L, 1L);
                            fdiv(framePtr + 224L, framePtr + 320L, framePtr + 360L);
                            isZero1 = fMLE(framePtr + 304L, framePtr + 344L) != 0L ? 1L : 0L;
                            if (isZero1 == 0L) {
                                fdiv(framePtr + 256L, framePtr + 360L, framePtr + 224L);
                                constPtr = framePtr + 224L;
                                dataPtr2 = framePtr + 344L;
                            } else {
                                fdiv(framePtr + 240L, framePtr + 360L, framePtr + 224L);
                                constPtr = framePtr + 304L;
                                dataPtr2 = framePtr + 224L;
                            }
                            fradd(constPtr, dataPtr2, framePtr + 376L, isZero1 == 0L ? 1L : 0L);
                            fdiv2(framePtr + 376L, framePtr + 224L);
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            fradd(framePtr + 392L + longVal2, framePtr + 224L, framePtr + 376L, 1L);
                            putI32(framePtr + 116L, getI32(framePtr + 380L));
                            putU32(framePtr + 112L, getU32(framePtr + 376L));
                            putI64(framePtr + 120L, getI64(framePtr + 384L));
                            step = 23;
                            continue stepLoop;
                        }
                    }
                    case 23: {
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        longVal3 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 16L;
                        }
                        constPtr = framePtr + 392L + longVal2;
                        if (getU8(framePtr + 616L) == 0L) {
                            isZero1 = fLT(constPtr, framePtr + 112L) != 0L ? 1L : 0L;
                            if (isZero1 == 0L || comma(isZero1 = fLT(framePtr + 112L, framePtr + 392L + longVal3) != 0L ? 1L : 0L, isZero1 == 0L ? 1L : 0L) != 0L) {
                                dataPtr2 = framePtr + 112L;
                                step = 25;
                                continue stepLoop;
                            } else {
                                step = 24;
                                continue stepLoop;
                            }
                        } else {
                            isZero1 = fLT(framePtr + 392L + longVal3, framePtr + 112L) != 0L ? 1L : 0L;
                            if (isZero1 == 0L || comma(isZero1 = fLT(framePtr + 112L, constPtr) != 0L ? 1L : 0L, isZero1 == 0L ? 1L : 0L) != 0L) {
                                dataPtr2 = constPtr;
                                constPtr = framePtr + 112L;
                                step = 25;
                                continue stepLoop;
                            } else {
                                step = 24;
                                continue stepLoop;
                            }
                        }
                    }
                    case 24: {
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        isZero1 = fNE(framePtr + 112L, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            if (getU8(framePtr + 616L) == 0L) {
                                isZero1 = fLT(framePtr + 112L, framePtr + 456L) != 0L ? 1L : 0L;
                                if (isZero1 == 0L) {
                                    if (getU8(framePtr + 616L) == 0L) {
                                        step = 27;
                                        continue stepLoop;
                                    } else {
                                        step = 28;
                                        continue stepLoop;
                                    }
                                } else {
                                    step = 26;
                                    continue stepLoop;
                                }
                            } else {
                                step = 28;
                                continue stepLoop;
                            }
                        } else {
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            longVal3 = 80L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 16L;
                            }
                            intPtr2 = framePtr + 392L + longVal2;
                            intPtr1 = framePtr + 392L + longVal3;
                            isZero1 = fEQ(intPtr2, intPtr1) != 0L ? 1L : 0L;
                            if (isZero1 != 0L) {
                                isZero1 = getU8(framePtr + 616L) == 0L ? 1L : 0L;
                                longVal2 = 32L;
                                if (isZero1 == 0L) {
                                    longVal2 = 64L;
                                }
                                constPtr = framePtr + 392L + longVal2;
                                step = 29;
                                continue stepLoop;
                            } else {
                                putI64(framePtr + 120L, getI64(framePtr + 400L + longVal2));
                                putI64(framePtr + 112L, getI64(intPtr2));
                                isZero1 = fLT(intPtr2, intPtr1) != 0L ? 1L : 0L;
                                fNextAfter(framePtr + 112L, isZero1);
                                isZero2 = fNE(framePtr + 376L, intPtr1) != 0L ? 1L : 0L;
                                isZero1 = getU8(framePtr + 616L) == 0L ? 1L : 0L;
                                longVal2 = 32L;
                                if (isZero1 == 0L) {
                                    longVal2 = 64L;
                                }
                                constPtr = framePtr + 392L + longVal2;
                                if (isZero2 != 0L) {
                                    offset2 = getI64(constPtr);
                                    longVal3 = 16L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 80L;
                                    }
                                    putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                                    putI64(framePtr + 392L + longVal3, offset2);
                                    isZero1 = 0L;
                                    longVal2 = 32L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 64L;
                                    }
                                    offset2 = getI64(framePtr + 504L + longVal2);
                                    longVal3 = 16L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 80L;
                                    }
                                    putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                                    putI64(framePtr + 504L + longVal3, offset2);
                                    longVal2 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 32L;
                                    }
                                    offset2 = getI64(framePtr + 392L + longVal2);
                                    longVal3 = 32L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 64L;
                                    }
                                    putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                                    putI64(framePtr + 392L + longVal3, offset2);
                                    dataPtr6 = getI64(framePtr + 440L);
                                    longVal2 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 32L;
                                    }
                                    offset2 = getI64(framePtr + 504L + longVal2);
                                    longVal3 = 32L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 64L;
                                    }
                                    putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                                    putI64(framePtr + 504L + longVal3, offset2);
                                    offset2 = getI64(framePtr + 552L);
                                    longVal2 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 32L;
                                    }
                                    putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                                    putI64(framePtr + 392L + longVal2, dataPtr6);
                                    longVal2 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 32L;
                                    }
                                    putI64(framePtr + 512L + longVal2, getI64(framePtr + 560L));
                                    putI64(framePtr + 504L + longVal2, offset2);
                                    putU8(framePtr + 616L, getU8(framePtr + 616L) ^ 1L);
                                    step = 12;
                                    continue stepLoop;
                                } else {
                                    step = 29;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 25: {
                        fLE(dataPtr2, constPtr);
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        longVal3 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 16L;
                        }
                        isZero1 = ZeroFinderMidpoint(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 112L) != 0L ? 1L : 0L;
                        if (isZero1 == 0L) {
                            step = 17;
                            continue stepLoop;
                        } else {
                            step = 24;
                            continue stepLoop;
                        }
                    }
                    case 26: {
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        offset2 = getI64(framePtr + 392L + longVal2);
                        longVal3 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 64L;
                        }
                        putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                        putI64(framePtr + 392L + longVal3, offset2);
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        offset2 = getI64(framePtr + 504L + longVal2);
                        longVal3 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 64L;
                        }
                        putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                        putI64(framePtr + 504L + longVal3, offset2);
                        longVal2 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 16L;
                        }
                        offset2 = getI64(framePtr + 392L + longVal2);
                        longVal3 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 32L;
                        }
                        putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                        putI64(framePtr + 392L + longVal3, offset2);
                        longVal2 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 16L;
                        }
                        offset2 = getI64(framePtr + 504L + longVal2);
                        longVal3 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 32L;
                        }
                        putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                        putI64(framePtr + 504L + longVal3, offset2);
                        putU8(framePtr + 616L, getU8(framePtr + 616L) ^ 1L);
                        step = 27;
                        continue stepLoop;
                    }
                    case 27: {
                        tempVal1 = ZeroFinderBracket(framePtr + 112L, framePtr + 392L) & 0xffffffffL;
                        index = (long) (int) (index + 1L);
                        isZero1 = 1L;
                        if (tempVal1 != 4L) {
                            return (long) (int) tempVal1;
                        }
                        step = 21;
                        continue stepLoop;
                    }
                    case 28: {
                        isZero1 = fLT(framePtr + 424L, framePtr + 112L) != 0L ? 1L : 0L;
                        if (isZero1 == 0L) {
                            step = 27;
                            continue stepLoop;
                        } else {
                            step = 26;
                            continue stepLoop;
                        }
                    }
                    case 29: {
                        longVal2 = 64L;
                        if (isZero1 == 0L) {
                            longVal2 = 32L;
                        }
                        isZero1 = ZeroFinderMidpoint(constPtr, framePtr + 392L + longVal2, framePtr + 112L) != 0L ? 1L : 0L;
                        if (isZero1 == 0L) {
                            step = 17;
                            continue stepLoop;
                        } else {
                            step = 27;
                            continue stepLoop;
                        }
                    }
                    case 30: {
                        longVal2 = 16L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 80L;
                        }
                        offset2 = getI64(framePtr + 392L + longVal2);
                        longVal3 = 0L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 96L;
                        }
                        putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                        putI64(framePtr + 392L + longVal3, offset2);
                        longVal2 = 16L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 80L;
                        }
                        offset2 = getI64(framePtr + 504L + longVal2);
                        longVal3 = 0L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 96L;
                        }
                        putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                        putI64(framePtr + 504L + longVal3, offset2);
                        longVal2 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 16L;
                        }
                        offset2 = getI64(framePtr + 392L + longVal2);
                        longVal3 = 96L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 0L;
                        }
                        putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                        putI64(framePtr + 392L + longVal3, offset2);
                        longVal2 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 16L;
                        }
                        longVal3 = 6L;
                        constPtr = framePtr + 504L + longVal2;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 0L;
                        }
                        step = 31;
                        continue stepLoop;
                    }
                    case 31: {
                        offset2 = getI64(constPtr);
                        putI64(framePtr + 512L + longVal3 * 16L, getI64(constPtr + 8L));
                        putI64(framePtr + 504L + longVal3 * 16L, offset2);
                        if (getI64(framePtr + 632L) != 0L) {
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            isZero1 = fLT(framePtr + 392L + longVal3, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                            tempVal1 = 4L;
                            tempVal2 = 2L;
                            if (isZero1 == 0L) {
                                tempVal1 = 2L;
                                tempVal2 = 4L;
                            }
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal1 = (6L - tempVal1) & 0xffffffffL;
                            }
                            putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal2 = (6L - tempVal2) & 0xffffffffL;
                            }
                            offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal1 * 16L, framePtr + 392L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, getI64(framePtr + 640L));
                            if ((offset1 & 1L) != 0L) {
                                tempVal1 = 30L;
                                return (long) (int) tempVal1;
                            }
                        }
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        longVal3 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 32L;
                        }
                        isZero1 = ZeroFinderMidpoint(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 112L) != 0L ? 1L : 0L;
                        if (isZero1 == 0L) {
                            longVal2 = 16L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 80L;
                            }
                            if (getU32(framePtr + 504L + longVal2) == 2L) {
                                step = 33;
                                continue stepLoop;
                            } else {
                                longVal3 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 64L;
                                }
                                fdiv(framePtr + 504L + longVal3, framePtr + 504L + longVal2, framePtr + 96L);
                                isZero1 = fLT(CC_2, framePtr + 96L) != 0L ? 1L : 0L;
                                if (isZero1 != 0L) {
                                    step = 33;
                                    continue stepLoop;
                                } else {
                                    isZero1 = 0L;
                                    step = 32;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            longVal2 = 0L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 96L;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 376L, 1L);
                            if (getU32(framePtr + 376L) == 3L) {
                                putU32(framePtr + 376L, 1L);
                            } else {
                                if ((long) (int) getU32(framePtr + 376L) < 0L) {
                                    putU32(framePtr + 376L, -getU32(framePtr + 376L));
                                }
                            }
                            fradd(framePtr + 472L, framePtr + 408L, framePtr + 360L, 1L);
                            fimin(framePtr + 376L, framePtr + 360L, framePtr + 376L);
                            if (getU32(framePtr + 376L) == 0xfffffffeL || getU32(framePtr + 376L) == 2L) {
                                step = 35;
                                continue stepLoop;
                            } else {
                                fradd(framePtr + 456L, framePtr + 424L, framePtr + 344L, 1L);
                                fmul2(framePtr + 344L, framePtr + 320L);
                                isZero1 = fMLT(framePtr + 376L, framePtr + 320L) != 0L ? 1L : 0L;
                                if (isZero1 == 0L) {
                                    step = 35;
                                    continue stepLoop;
                                } else {
                                    step = 34;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 32: {
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        longVal3 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 32L;
                        }
                        isZero2 = fMLT(framePtr + 504L + longVal2, framePtr + 504L + longVal3) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            if (isZero2 != 0L) {
                                longVal2 = 64L;
                                longVal3 = 32L;
                            } else {
                                longVal2 = 32L;
                                longVal3 = 64L;
                            }
                            longVal1 = longVal3;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal1 = longVal2;
                            }
                            offset2 = getI64(framePtr + 392L + longVal1);
                            tempVal1 = 43L;
                            putI64(getI64(framePtr + 648L) + 8L, getI64(framePtr + 400L + longVal1));
                            putI64(getI64(framePtr + 648L), offset2);
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = longVal3;
                            }
                            offset2 = getI64(framePtr + 392L + longVal2);
                            putI64(getI64(framePtr + 656L) + 8L, getI64(framePtr + 400L + longVal2));
                            putI64(getI64(framePtr + 656L), offset2);
                        } else {
                            tempVal1 = 0L;
                            if (isZero2 != 0L) {
                                longVal2 = 64L;
                                longVal3 = 32L;
                            } else {
                                longVal2 = 32L;
                                longVal3 = 64L;
                            }
                            longVal1 = longVal3;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal1 = longVal2;
                            }
                            offset2 = getI64(framePtr + 392L + longVal1);
                            putI64(getI64(framePtr + 648L) + 8L, getI64(framePtr + 400L + longVal1));
                            putI64(getI64(framePtr + 648L), offset2);
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = longVal3;
                            }
                            offset2 = getI64(framePtr + 392L + longVal2);
                            putI64(getI64(framePtr + 656L) + 8L, getI64(framePtr + 400L + longVal2));
                            putI64(getI64(framePtr + 656L), offset2);
                        }
                        return (long) (int) tempVal1;
                    }
                    case 33: {
                        longVal2 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 16L;
                        }
                        if (getU32(framePtr + 504L + longVal2) != 2L) {
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            fdiv(framePtr + 504L + longVal3, framePtr + 504L + longVal2, framePtr + 96L);
                            fLT(CC_2, framePtr + 96L);
                            isZero1 = 0L;
                        } else {
                            isZero1 = 1L;
                        }
                        step = 32;
                        continue stepLoop;
                    }
                    case 34: {
                        longVal3 = 80L;
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        longVal1 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal1 = 32L;
                        }
                        constPtr = framePtr + 112L;
                        dataPtr5 = dataPtr4;
                        if (getU8(framePtr + 616L) == 0L) {
                            if (getI32(framePtr + 392L + longVal2) < 0L) {
                                index = getI32(framePtr + 392L + longVal1);
                                step = 44;
                                continue stepLoop;
                            } else {
                                step = 43;
                                continue stepLoop;
                            }
                        } else {
                            if (getI32(framePtr + 392L + longVal1) < 0L) {
                                index = getI32(framePtr + 392L + longVal2);
                                step = 44;
                                continue stepLoop;
                            } else {
                                step = 43;
                                continue stepLoop;
                            }
                        }
                    }
                    case 35: {
                        longVal2 = 16L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 80L;
                        }
                        if (getU32(framePtr + 504L + longVal2) == 2L) {
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            longVal3 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 64L;
                            }
                            fradd(framePtr + 504L + longVal2, framePtr + 504L + longVal3, framePtr + 96L, 1L);
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            fdiv(framePtr + 504L + longVal2, framePtr + 96L, framePtr + 80L);
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            longVal3 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 64L;
                            }
                            fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 64L, 1L);
                            fmul(framePtr + 80L, framePtr + 64L, framePtr + 96L);
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            fradd(framePtr + 392L + longVal2, framePtr + 96L, framePtr + 112L, 1L);
                            step = 36;
                            continue stepLoop;
                        } else {
                            longVal3 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 64L;
                            }
                            fradd(framePtr + 504L + longVal2, framePtr + 504L + longVal3, framePtr + 96L, 1L);
                            longVal2 = 16L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 80L;
                            }
                            longVal3 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 64L;
                            }
                            fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 80L, 1L);
                            isZero1 = fMLE(framePtr + 80L, framePtr + 96L) != 0L ? 1L : 0L;
                            if (isZero1 != 0L) {
                                longVal2 = 16L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 80L;
                                }
                                longVal3 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 64L;
                                }
                                isZero1 = fMLT(framePtr + 504L + longVal3, framePtr + 504L + longVal2) != 0L ? 1L : 0L;
                                if (isZero1 == 0L) {
                                    step = 38;
                                    continue stepLoop;
                                } else {
                                    longVal2 = 32L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 64L;
                                    }
                                    longVal3 = 16L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 80L;
                                    }
                                    fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 304L, 1L);
                                    longVal2 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 32L;
                                    }
                                    longVal3 = 32L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 64L;
                                    }
                                    fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 288L, 1L);
                                    longVal2 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 32L;
                                    }
                                    longVal3 = 16L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 80L;
                                    }
                                    fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 272L, 1L);
                                    longVal2 = 32L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 64L;
                                    }
                                    longVal3 = 16L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 80L;
                                    }
                                    fradd(framePtr + 504L + longVal2, framePtr + 504L + longVal3, framePtr + 256L, 1L);
                                    longVal2 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 32L;
                                    }
                                    longVal3 = 32L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 64L;
                                    }
                                    fradd(framePtr + 504L + longVal2, framePtr + 504L + longVal3, framePtr + 240L, 1L);
                                    longVal2 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 32L;
                                    }
                                    longVal3 = 16L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 80L;
                                    }
                                    fradd(framePtr + 504L + longVal2, framePtr + 504L + longVal3, framePtr + 224L, 1L);
                                    fdiv(framePtr + 256L, framePtr + 304L, framePtr + 320L);
                                    fdiv(framePtr + 240L, framePtr + 288L, framePtr + 344L);
                                    fdiv(framePtr + 224L, framePtr + 272L, framePtr + 360L);
                                    longVal2 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 32L;
                                    }
                                    fmul(framePtr + 504L + longVal2, framePtr + 320L, framePtr + 208L);
                                    longVal2 = 32L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 64L;
                                    }
                                    fmul(framePtr + 504L + longVal2, framePtr + 360L, framePtr + 192L);
                                    fradd(framePtr + 208L, framePtr + 192L, framePtr + 376L, 1L);
                                    longVal2 = 32L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 64L;
                                    }
                                    longVal3 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 32L;
                                    }
                                    isZero1 = fMLE(framePtr + 504L + longVal2, framePtr + 504L + longVal3) != 0L ? 1L : 0L;
                                    if (isZero1 != 0L) {
                                        constPtr = framePtr + 192L;
                                        fdiv(framePtr + 192L, framePtr + 376L, framePtr + 176L);
                                        fmul(framePtr + 176L, framePtr + 288L, framePtr + 192L);
                                        offset1 = 2L;
                                        if (getU8(framePtr + 616L) != 0L) {
                                            offset1 = 4L;
                                        }
                                    } else {
                                        constPtr = framePtr + 208L;
                                        fdiv(framePtr + 208L, framePtr + 376L, framePtr + 176L);
                                        fmul(framePtr + 176L, framePtr + 288L, framePtr + 208L);
                                        offset1 = 4L;
                                        if (getU8(framePtr + 616L) != 0L) {
                                            offset1 = 2L;
                                        }
                                    }
                                    step = 37;
                                    continue stepLoop;
                                }
                            } else {
                                step = 38;
                                continue stepLoop;
                            }
                        }
                    }
                    case 36: {
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        longVal3 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 32L;
                        }
                        constPtr = framePtr + 392L + longVal2;
                        if (getU8(framePtr + 616L) == 0L) {
                            isZero1 = fLT(constPtr, framePtr + 112L) != 0L ? 1L : 0L;
                            if (isZero1 == 0L || comma(isZero1 = fLT(framePtr + 112L, framePtr + 392L + longVal3) != 0L ? 1L : 0L, isZero1 == 0L ? 1L : 0L) != 0L) {
                                dataPtr2 = framePtr + 112L;
                                step = 42;
                                continue stepLoop;
                            } else {
                                step = 34;
                                continue stepLoop;
                            }
                        } else {
                            isZero1 = fLT(framePtr + 392L + longVal3, framePtr + 112L) != 0L ? 1L : 0L;
                            if (isZero1 == 0L || comma(isZero1 = fLT(framePtr + 112L, constPtr) != 0L ? 1L : 0L, isZero1 == 0L ? 1L : 0L) != 0L) {
                                dataPtr2 = constPtr;
                                constPtr = framePtr + 112L;
                                step = 42;
                                continue stepLoop;
                            } else {
                                step = 34;
                                continue stepLoop;
                            }
                        }
                    }
                    case 37: {
                        fradd(framePtr + 392L + offset1 * 16L, constPtr, framePtr + 376L, 1L);
                        putI32(framePtr + 116L, getI32(framePtr + 380L));
                        putU32(framePtr + 112L, getU32(framePtr + 376L));
                        putI64(framePtr + 120L, getI64(framePtr + 384L));
                        step = 36;
                        continue stepLoop;
                    }
                    case 38: {
                        longVal2 = 16L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 80L;
                        }
                        longVal3 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 64L;
                        }
                        fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 304L, 1L);
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        longVal3 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 32L;
                        }
                        fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 288L, 1L);
                        longVal2 = 16L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 80L;
                        }
                        longVal3 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 32L;
                        }
                        fradd(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 272L, 1L);
                        longVal2 = 16L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 80L;
                        }
                        longVal3 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 64L;
                        }
                        fradd(framePtr + 504L + longVal2, framePtr + 504L + longVal3, framePtr + 256L, 1L);
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        longVal3 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 32L;
                        }
                        fradd(framePtr + 504L + longVal2, framePtr + 504L + longVal3, framePtr + 240L, 1L);
                        fdiv(framePtr + 256L, framePtr + 304L, framePtr + 224L);
                        fdiv(framePtr + 240L, framePtr + 288L, framePtr + 208L);
                        fradd(framePtr + 224L, framePtr + 208L, framePtr + 176L, 1L);
                        fdiv(framePtr + 176L, framePtr + 272L, framePtr + 360L);
                        fmul(framePtr + 360L, framePtr + 288L, framePtr + 376L);
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        longVal3 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 32L;
                        }
                        isZero1 = fMLT(framePtr + 504L + longVal3, framePtr + 504L + longVal2) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            fradd(framePtr + 208L, framePtr + 376L, framePtr + 344L, 1L);
                            tempVal1 = 4L;
                            longVal2 = 4L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 2L;
                            }
                        } else {
                            fradd(framePtr + 208L, framePtr + 376L, framePtr + 344L, 0L);
                            tempVal1 = 2L;
                            longVal2 = 2L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 4L;
                            }
                        }
                        putI64(framePtr + 328L, getI64(framePtr + 512L + longVal2 * 16L));
                        putI64(framePtr + 320L, getI64(framePtr + 504L + longVal2 * 16L));
                        fmul(framePtr + 344L, framePtr + 344L, framePtr + 128L);
                        fmul(framePtr + 360L, framePtr + 320L, framePtr + 160L);
                        fmul(CC_4, framePtr + 160L, framePtr + 144L);
                        fradd(framePtr + 128L, framePtr + 144L, framePtr + 160L, 1L);
                        if ((long) (int) getU32(framePtr + 160L) < 0L) {
                            putU32(framePtr + 160L, -getU32(framePtr + 160L));
                        }
                        fisqrt(framePtr + 160L, framePtr + 192L);
                        isZero1 = fZero(framePtr + 360L) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            constPtr = framePtr + 320L;
                            dataPtr2 = framePtr + 344L;
                            step = 39;
                            continue stepLoop;
                        } else {
                            if (getU8(framePtr + 616L) == 0L) {
                                if ((long) (int) getU32(framePtr + 568L) < 1L) {
                                    step = 40;
                                    continue stepLoop;
                                } else {
                                    step = 41;
                                    continue stepLoop;
                                }
                            } else {
                                if ((long) (int) getU32(framePtr + 536L) < 0L) {
                                    step = 41;
                                    continue stepLoop;
                                } else {
                                    step = 40;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 39: {
                        fdiv(constPtr, dataPtr2, framePtr + 176L);
                        constPtr = framePtr + 176L;
                        if (getU8(framePtr + 616L) != 0L) {
                            tempVal1 = (6L - tempVal1) & 0xffffffffL;
                        }
                        offset1 = tempVal1;
                        step = 37;
                        continue stepLoop;
                    }
                    case 40: {
                        if ((long) (int) getU32(framePtr + 344L) < 0L) {
                            tempVal2 = 0L;
                            fradd(framePtr + 344L, framePtr + 192L, framePtr + 376L, tempVal2);
                            fmul2(framePtr + 360L, framePtr + 160L);
                        } else {
                            fmul2(framePtr + 320L, framePtr + 376L);
                            tempVal2 = 1L;
                            fradd(framePtr + 344L, framePtr + 192L, framePtr + 160L, tempVal2);
                        }
                        constPtr = framePtr + 376L;
                        dataPtr2 = framePtr + 160L;
                        step = 39;
                        continue stepLoop;
                    }
                    case 41: {
                        if (0L < (long) (int) getU32(framePtr + 344L)) {
                            fmul2(framePtr + 320L, framePtr + 376L);
                            tempVal2 = 0L;
                            fradd(framePtr + 344L, framePtr + 192L, framePtr + 160L, tempVal2);
                            constPtr = framePtr + 376L;
                            dataPtr2 = framePtr + 160L;
                        } else {
                            tempVal2 = 1L;
                            fradd(framePtr + 344L, framePtr + 192L, framePtr + 376L, tempVal2);
                            fmul2(framePtr + 360L, framePtr + 160L);
                            constPtr = framePtr + 376L;
                            dataPtr2 = framePtr + 160L;
                        }
                        step = 39;
                        continue stepLoop;
                    }
                    case 42: {
                        isZero1 = fLE(dataPtr2, constPtr) != 0L ? 1L : 0L;
                        tempVal1 = 2L;
                        if (isZero1 == 0L) {
                            tempVal1 = 4L;
                        }
                        tempVal2 = (6L - tempVal1) & 0xffffffffL;
                        if (getU8(framePtr + 616L) != 0L) {
                            tempVal2 = -tempVal1 & 0xffffffffL;
                            tempVal1 = (6L - tempVal1) & 0xffffffffL;
                        }
                        constPtr = framePtr + 392L + tempVal1 * 16L;
                        dataPtr2 = framePtr + 392L + (long) (int) tempVal2 * 16L;
                        isZero1 = fEQ(constPtr, dataPtr2) != 0L ? 1L : 0L;
                        if (isZero1 == 0L) {
                            putI64(framePtr + 120L, getI64(framePtr + 400L + tempVal1 * 16L));
                            putI64(framePtr + 112L, getI64(constPtr));
                            isZero1 = fLT(constPtr, dataPtr2) != 0L ? 1L : 0L;
                            fNextAfter(framePtr + 112L, isZero1);
                            fNE(framePtr + 376L, dataPtr2);
                        }
                        step = 34;
                        continue stepLoop;
                    }
                    case 43: {
                        putI64(framePtr + 112L, getI64(constPtr));
                        putI64(framePtr + 120L, getI64(dataPtr5));
                        putI64(framePtr + 440L, getI64(framePtr + 112L));
                        putI64(framePtr + 448L, getI64(framePtr + 120L));
                        callIndirect3(getI64(framePtr + 624L), framePtr + 440L, getI64(framePtr + 640L), dataPtr3);
                        isZero1 = fZero(dataPtr3) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            step = 45;
                            continue stepLoop;
                        } else {
                            if (getU32(framePtr + 552L) != 2L) {
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                if (getU32(framePtr + 552L) == (getI32(framePtr + 504L + longVal2) & 0xffffffffL)) {
                                    putU8(framePtr + 616L, getU8(framePtr + 616L) ^ 1L);
                                }
                                longVal2 = 16L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = longVal3;
                                }
                                offset2 = getI64(framePtr + 392L + longVal2);
                                longVal1 = 0L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal1 = 96L;
                                }
                                putI64(framePtr + 400L + longVal1, getI64(framePtr + 400L + longVal2));
                                putI64(framePtr + 392L + longVal1, offset2);
                                longVal2 = 16L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = longVal3;
                                }
                                offset2 = getI64(framePtr + 504L + longVal2);
                                longVal1 = 0L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal1 = 96L;
                                }
                                putI64(framePtr + 512L + longVal1, getI64(framePtr + 512L + longVal2));
                                putI64(framePtr + 504L + longVal1, offset2);
                                longVal2 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 64L;
                                }
                                offset2 = getI64(framePtr + 392L + longVal2);
                                longVal1 = 16L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal1 = longVal3;
                                }
                                putI64(framePtr + 400L + longVal1, getI64(framePtr + 400L + longVal2));
                                putI64(framePtr + 392L + longVal1, offset2);
                                dataPtr6 = getI64(framePtr + 440L);
                                longVal2 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 64L;
                                }
                                offset2 = getI64(framePtr + 504L + longVal2);
                                longVal1 = 16L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal1 = longVal3;
                                }
                                putI64(framePtr + 512L + longVal1, getI64(framePtr + 512L + longVal2));
                                putI64(framePtr + 504L + longVal1, offset2);
                                longVal2 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 64L;
                                }
                                putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                                putI64(framePtr + 392L + longVal2, dataPtr6);
                                longVal3 = 2L;
                                constPtr = dataPtr3;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 4L;
                                }
                                step = 31;
                                continue stepLoop;
                            } else {
                                longVal2 = 80L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 16L;
                                }
                                offset2 = getI64(framePtr + 392L + longVal2);
                                longVal3 = 96L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 0L;
                                }
                                putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                                putI64(framePtr + 392L + longVal3, offset2);
                                longVal2 = 80L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 16L;
                                }
                                offset2 = getI64(framePtr + 504L + longVal2);
                                longVal3 = 96L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 0L;
                                }
                                putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                                putI64(framePtr + 504L + longVal3, offset2);
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                offset2 = getI64(framePtr + 392L + longVal2);
                                longVal3 = 80L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 16L;
                                }
                                putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                                putI64(framePtr + 392L + longVal3, offset2);
                                dataPtr6 = getI64(framePtr + 440L);
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                offset2 = getI64(framePtr + 504L + longVal2);
                                longVal3 = 80L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 16L;
                                }
                                putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                                putI64(framePtr + 504L + longVal3, offset2);
                                offset2 = getI64(framePtr + 552L);
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                                putI64(framePtr + 392L + longVal2, dataPtr6);
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                putI64(framePtr + 512L + longVal2, getI64(framePtr + 560L));
                                putI64(framePtr + 504L + longVal2, offset2);
                                longVal2 = 16L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 80L;
                                }
                                offset2 = getI64(framePtr + 392L + longVal2);
                                longVal3 = 0L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 96L;
                                }
                                putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                                putI64(framePtr + 392L + longVal3, offset2);
                                longVal2 = 16L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 80L;
                                }
                                offset2 = getI64(framePtr + 504L + longVal2);
                                longVal3 = 0L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 96L;
                                }
                                putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                                putI64(framePtr + 504L + longVal3, offset2);
                                longVal2 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 64L;
                                }
                                offset2 = getI64(framePtr + 392L + longVal2);
                                longVal3 = 16L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 80L;
                                }
                                putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                                putI64(framePtr + 392L + longVal3, offset2);
                                dataPtr6 = getI64(framePtr + 440L);
                                longVal2 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 64L;
                                }
                                offset2 = getI64(framePtr + 504L + longVal2);
                                longVal3 = 16L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 80L;
                                }
                                putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                                putI64(framePtr + 504L + longVal3, offset2);
                                offset2 = getI64(framePtr + 552L);
                                longVal2 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 64L;
                                }
                                putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                                putI64(framePtr + 392L + longVal2, dataPtr6);
                                longVal2 = 32L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 64L;
                                }
                                putI64(framePtr + 512L + longVal2, getI64(framePtr + 560L));
                                putI64(framePtr + 504L + longVal2, offset2);
                                if (getI64(framePtr + 632L) != 0L) {
                                    longVal2 = 16L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 80L;
                                    }
                                    isZero1 = fLT(framePtr + 440L, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                                    tempVal1 = 3L;
                                    if (isZero1 != 0L) {
                                        tempVal2 = tempVal1;
                                        tempVal1 = 1L;
                                    } else {
                                        tempVal2 = 1L;
                                    }
                                    if (getU8(framePtr + 616L) != 0L) {
                                        tempVal2 = (6L - tempVal2) & 0xffffffffL;
                                    }
                                    putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                                    if (getU8(framePtr + 616L) != 0L) {
                                        tempVal1 = (6L - tempVal1) & 0xffffffffL;
                                    }
                                    offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal2 * 16L, framePtr + 392L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, getI64(framePtr + 640L));
                                    if ((offset1 & 1L) != 0L) {
                                        tempVal1 = 30L;
                                        return (long) (int) tempVal1;
                                    }
                                }
                                isZero1 = 0L;
                                step = 46;
                                continue stepLoop;
                            }
                        }
                    }
                    case 44: {
                        if (0L < index && getI32(framePtr + 116L) < -10L) {
                            constPtr = CC_0;
                            dataPtr5 = ADDR_RODATA + 1144L;
                        }
                        step = 43;
                        continue stepLoop;
                    }
                    case 45: {
                        tempVal1 = 0L;
                        putI64(getI64(framePtr + 656L) + 8L, getI64(framePtr + 448L));
                        putI64(getI64(framePtr + 656L), framePtr + 440L);
                        paramCount = getI64(framePtr + 648L);
                        dataPtr6 = getI64(framePtr + 440L);
                        offset1 = getI64(framePtr + 448L);
                        putI64(paramCount + 8L, offset1);
                        putI64(paramCount, dataPtr6);
                        return (long) (int) tempVal1;
                    }
                    case 46: {
                        while (true) {
                            longVal2 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 32L;
                            }
                            longVal3 = 80L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 16L;
                            }
                            isZero2 = ZeroFinderMidpoint(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 112L) != 0L ? 1L : 0L;
                            if (isZero2 != 0L) {
                                break;
                            }
                            if (isZero1 != 0L) {
                                longVal2 = 16L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 80L;
                                }
                                longVal3 = 80L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal3 = 16L;
                                }
                                isZero1 = fMLT(framePtr + 504L + longVal2, framePtr + 504L + longVal3) != 0L ? 1L : 0L;
                                if (isZero1 != 0L) {
                                    longVal2 = 80L;
                                    longVal3 = 16L;
                                } else {
                                    longVal2 = 16L;
                                    longVal3 = 80L;
                                }
                                longVal1 = longVal3;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal1 = longVal2;
                                }
                                offset2 = getI64(framePtr + 392L + longVal1);
                                tempVal1 = 44L;
                                putI64(getI64(framePtr + 648L) + 8L, getI64(framePtr + 400L + longVal1));
                                putI64(getI64(framePtr + 648L), offset2);
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = longVal3;
                                }
                                offset2 = getI64(framePtr + 392L + longVal2);
                                putI64(getI64(framePtr + 656L) + 8L, getI64(framePtr + 400L + longVal2));
                                putI64(getI64(framePtr + 656L), offset2);
                                return (long) (int) tempVal1;
                            }
                            putU8(framePtr + 616L, getU8(framePtr + 616L) ^ 1L);
                            isZero1 = 1L;
                        }
                        putI64(framePtr + 448L, getI64(framePtr + 120L));
                        putU32(framePtr + 440L, getU32(framePtr + 112L));
                        putU32(framePtr + 444L, getI32(framePtr + 116L));
                        callIndirect3(getI64(framePtr + 624L), framePtr + 440L, getI64(framePtr + 640L), dataPtr3);
                        isZero2 = fZero(dataPtr3) != 0L ? 1L : 0L;
                        offset2 = getI64(framePtr + 552L);
                        dataPtr6 = getI64(framePtr + 440L);
                        if (isZero2 != 0L) {
                            step = 45;
                            continue stepLoop;
                        } else {
                            if (getU32(framePtr + 552L) == 2L) {
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                                putI64(framePtr + 392L + longVal2, dataPtr6);
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                putI64(framePtr + 512L + longVal2, getI64(framePtr + 560L));
                                putI64(framePtr + 504L + longVal2, offset2);
                                if (getI64(framePtr + 632L) != 0L) {
                                    longVal2 = 16L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 80L;
                                    }
                                    isZero2 = fLT(framePtr + 440L, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                                    tempVal1 = 3L;
                                    if (isZero2 != 0L) {
                                        tempVal2 = tempVal1;
                                        tempVal1 = 1L;
                                    } else {
                                        tempVal2 = 1L;
                                    }
                                    if (getU8(framePtr + 616L) != 0L) {
                                        tempVal2 = (6L - tempVal2) & 0xffffffffL;
                                    }
                                    putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                                    if (getU8(framePtr + 616L) != 0L) {
                                        tempVal1 = (6L - tempVal1) & 0xffffffffL;
                                    }
                                    offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal2 * 16L, framePtr + 392L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, getI64(framePtr + 640L));
                                    step = 48;
                                    continue stepLoop;
                                } else {
                                    step = 47;
                                    continue stepLoop;
                                }
                            } else {
                                longVal2 = 5L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 1L;
                                }
                                if (getU32(framePtr + 552L) != getU32(framePtr + 504L + longVal2 * 16L)) {
                                    if (getI64(framePtr + 632L) != 0L) {
                                        isZero1 = fLT(framePtr + 392L + longVal2 * 16L, framePtr + 440L) != 0L ? 1L : 0L;
                                        tempVal1 = 5L;
                                        tempVal2 = 3L;
                                        if (isZero1 == 0L) {
                                            tempVal1 = 3L;
                                            tempVal2 = 5L;
                                        }
                                        if (getU8(framePtr + 616L) != 0L) {
                                            tempVal1 = (6L - tempVal1) & 0xffffffffL;
                                        }
                                        putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                                        if (getU8(framePtr + 616L) != 0L) {
                                            tempVal2 = (6L - tempVal2) & 0xffffffffL;
                                        }
                                        offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal1 * 16L, framePtr + 392L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, getI64(framePtr + 640L));
                                        if ((offset1 & 1L) != 0L) {
                                            tempVal1 = 30L;
                                            return (long) (int) tempVal1;
                                        }
                                    }
                                    longVal2 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 32L;
                                    }
                                    offset2 = getI64(framePtr + 392L + longVal2);
                                    longVal3 = 16L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 80L;
                                    }
                                    putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                                    putI64(framePtr + 392L + longVal3, offset2);
                                    dataPtr6 = getI64(framePtr + 440L);
                                    longVal2 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 32L;
                                    }
                                    offset2 = getI64(framePtr + 504L + longVal2);
                                    longVal3 = 16L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 80L;
                                    }
                                    putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                                    putI64(framePtr + 504L + longVal3, offset2);
                                    offset2 = getI64(framePtr + 552L);
                                    longVal2 = 32L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 64L;
                                    }
                                    putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                                    putI64(framePtr + 392L + longVal2, dataPtr6);
                                    longVal2 = 32L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 64L;
                                    }
                                    putI64(framePtr + 512L + longVal2, getI64(framePtr + 560L));
                                    putI64(framePtr + 504L + longVal2, offset2);
                                    longVal2 = 80L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 16L;
                                    }
                                    offset2 = getI64(framePtr + 392L + longVal2);
                                    longVal3 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 32L;
                                    }
                                    putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                                    putI64(framePtr + 392L + longVal3, offset2);
                                    longVal2 = 80L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 16L;
                                    }
                                    offset2 = getI64(framePtr + 504L + longVal2);
                                    longVal3 = 64L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 32L;
                                    }
                                    putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                                    putI64(framePtr + 504L + longVal3, offset2);
                                    longVal2 = 96L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 0L;
                                    }
                                    offset2 = getI64(framePtr + 392L + longVal2);
                                    longVal3 = 80L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 16L;
                                    }
                                    putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                                    putI64(framePtr + 392L + longVal3, offset2);
                                    longVal2 = 96L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 0L;
                                    }
                                    offset2 = getI64(framePtr + 504L + longVal2);
                                    longVal3 = 80L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 16L;
                                    }
                                    putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                                    putI64(framePtr + 504L + longVal3, offset2);
                                    step = 30;
                                    continue stepLoop;
                                } else {
                                    longVal3 = 96L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 0L;
                                    }
                                    offset2 = getI64(framePtr + 392L + longVal3);
                                    putI64(framePtr + 400L + longVal2 * 16L, getI64(framePtr + 400L + longVal3));
                                    putI64(framePtr + 392L + longVal2 * 16L, offset2);
                                    dataPtr6 = getI64(framePtr + 440L);
                                    longVal2 = 96L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 0L;
                                    }
                                    offset2 = getI64(framePtr + 504L + longVal2);
                                    longVal3 = 80L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal3 = 16L;
                                    }
                                    putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                                    putI64(framePtr + 504L + longVal3, offset2);
                                    offset2 = getI64(framePtr + 552L);
                                    longVal2 = 80L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 16L;
                                    }
                                    putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                                    putI64(framePtr + 392L + longVal2, dataPtr6);
                                    longVal2 = 80L;
                                    if (getU8(framePtr + 616L) != 0L) {
                                        longVal2 = 16L;
                                    }
                                    putI64(framePtr + 512L + longVal2, getI64(framePtr + 560L));
                                    putI64(framePtr + 504L + longVal2, offset2);
                                    if (getI64(framePtr + 632L) != 0L) {
                                        longVal2 = 16L;
                                        if (getU8(framePtr + 616L) != 0L) {
                                            longVal2 = 80L;
                                        }
                                        isZero2 = fLT(framePtr + 440L, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                                        tempVal1 = 3L;
                                        if (isZero2 != 0L) {
                                            tempVal2 = tempVal1;
                                            tempVal1 = 1L;
                                        } else {
                                            tempVal2 = 1L;
                                        }
                                        if (getU8(framePtr + 616L) != 0L) {
                                            tempVal2 = (6L - tempVal2) & 0xffffffffL;
                                        }
                                        putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                                        if (getU8(framePtr + 616L) != 0L) {
                                            tempVal1 = (6L - tempVal1) & 0xffffffffL;
                                        }
                                        offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal2 * 16L, framePtr + 392L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, getI64(framePtr + 640L));
                                        step = 48;
                                        continue stepLoop;
                                    } else {
                                        step = 47;
                                        continue stepLoop;
                                    }
                                }
                            }
                        }
                    }
                    case 47: {
                        isZero2 = isZero1 == 0L ? 1L : 0L;
                        isZero1 = 1L;
                        if (isZero2 != 0L) {
                            isZero1 = 0L;
                            putU8(framePtr + 616L, getU8(framePtr + 616L) ^ 1L);
                        }
                        step = 46;
                        continue stepLoop;
                    }
                    case 48: {
                        if ((offset1 & 1L) != 0L) {
                            tempVal1 = 30L;
                            return (long) (int) tempVal1;
                        }
                        step = 47;
                        continue stepLoop;
                    }
                    case 49: {
                        if (getI64(framePtr + 632L) != 0L) {
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            isZero1 = fLT(framePtr + 392L + longVal3, framePtr + 392L + longVal2) != 0L ? 1L : 0L;
                            tempVal2 = 2L;
                            tempVal1 = 4L;
                            if (isZero1 == 0L) {
                                tempVal2 = 4L;
                                tempVal1 = 2L;
                            }
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal1 = (6L - tempVal1) & 0xffffffffL;
                            }
                            putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                            if (getU8(framePtr + 616L) != 0L) {
                                tempVal2 = (6L - tempVal2) & 0xffffffffL;
                            }
                            offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal1 * 16L, framePtr + 392L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, getI64(framePtr + 640L));
                            if ((offset1 & 1L) != 0L) {
                                tempVal1 = 30L;
                                return (long) (int) tempVal1;
                            }
                        }
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        offset2 = getI64(framePtr + 392L + longVal2);
                        longVal3 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 16L;
                        }
                        putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                        putI64(framePtr + 392L + longVal3, offset2);
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        offset2 = getI64(framePtr + 504L + longVal2);
                        longVal3 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 16L;
                        }
                        putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                        putI64(framePtr + 504L + longVal3, offset2);
                        longVal2 = 32L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 64L;
                        }
                        longVal3 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 32L;
                        }
                        isZero1 = ZeroFinderMidpoint(framePtr + 392L + longVal2, framePtr + 392L + longVal3, framePtr + 440L) != 0L ? 1L : 0L;
                        if (isZero1 == 0L) {
                            step = 50;
                            continue stepLoop;
                        } else {
                            callIndirect3(getI64(framePtr + 624L), framePtr + 440L, getI64(framePtr + 640L), dataPtr3);
                            if (((getU32(framePtr + 552L) + 2L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(framePtr + 552L) + 2L) & 31L)) & 21L) != 0L || 24999L < (long) (int) getU32(framePtr + 556L)) {
                                putI64(framePtr + 560L, 0x999999999999999L);
                                putI64(framePtr + 552L, 0xc34f00000002L);
                                putU32(framePtr + 556L, 49999L);
                            }
                            if ((long) (int) getU32(framePtr + 556L) < -24999L) {
                                putI64(framePtr + 560L, 0L);
                                putI64(framePtr + 552L, 1L);
                            }
                            isZero1 = fZero(dataPtr3) != 0L ? 1L : 0L;
                            offset2 = getI64(framePtr + 552L);
                            dataPtr6 = getI64(framePtr + 440L);
                            if (isZero1 != 0L) {
                                step = 51;
                                continue stepLoop;
                            } else {
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                                putI64(framePtr + 392L + longVal2, dataPtr6);
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                putI64(framePtr + 512L + longVal2, getI64(framePtr + 560L));
                                putI64(framePtr + 504L + longVal2, offset2);
                                longVal2 = 64L;
                                if (getU8(framePtr + 616L) != 0L) {
                                    longVal2 = 32L;
                                }
                                if (getI32(framePtr + 504L + longVal2) == 2L) {
                                    step = 49;
                                    continue stepLoop;
                                } else {
                                    step = 50;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 50: {
                        longVal2 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 16L;
                        }
                        offset2 = getI64(framePtr + 392L + longVal2);
                        longVal3 = 96L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 0L;
                        }
                        putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                        putI64(framePtr + 392L + longVal3, offset2);
                        longVal2 = 80L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 16L;
                        }
                        offset2 = getI64(framePtr + 504L + longVal2);
                        longVal3 = 96L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal3 = 0L;
                        }
                        putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                        putI64(framePtr + 504L + longVal3, offset2);
                        longVal2 = 64L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 32L;
                        }
                        if (getI32(framePtr + 504L + longVal2) != 2L) {
                            step = 52;
                            continue stepLoop;
                        } else {
                            longVal2 = 32L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal2 = 64L;
                            }
                            longVal3 = 16L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 80L;
                            }
                            intPtr2 = framePtr + 392L + longVal2;
                            intPtr1 = framePtr + 392L + longVal3;
                            isZero1 = fEQ(intPtr2, intPtr1) != 0L ? 1L : 0L;
                            if (isZero1 != 0L) {
                                step = 54;
                                continue stepLoop;
                            } else {
                                putI64(framePtr + 448L, getI64(framePtr + 400L + longVal2));
                                putI64(framePtr + 440L, getI64(intPtr2));
                                isZero1 = fLT(intPtr2, intPtr1) != 0L ? 1L : 0L;
                                fNextAfter(framePtr + 440L, isZero1);
                                isZero1 = fNE(framePtr + 376L, intPtr1) != 0L ? 1L : 0L;
                                if (isZero1 == 0L) {
                                    step = 54;
                                    continue stepLoop;
                                } else {
                                    callIndirect3(getI64(framePtr + 624L), framePtr + 440L, getI64(framePtr + 640L), dataPtr3);
                                    if (((getU32(framePtr + 552L) + 2L) & 0xffffffffL) < 5L && ((1L << (int) ((getU32(framePtr + 552L) + 2L) & 31L)) & 21L) != 0L || 24999L < (long) (int) getU32(framePtr + 556L)) {
                                        putI64(framePtr + 560L, 0x999999999999999L);
                                        putI64(framePtr + 552L, 0xc34f00000002L);
                                        putU32(framePtr + 556L, 49999L);
                                    }
                                    if ((long) (int) getU32(framePtr + 556L) < -24999L) {
                                        putI64(framePtr + 560L, 0L);
                                        putI64(framePtr + 552L, 1L);
                                    }
                                    isZero1 = fZero(dataPtr3) != 0L ? 1L : 0L;
                                    if (isZero1 == 0L) {
                                        longVal2 = 32L;
                                        if (getU8(framePtr + 616L) != 0L) {
                                            longVal2 = 64L;
                                        }
                                        offset2 = getI64(framePtr + 392L + longVal2);
                                        longVal3 = 64L;
                                        if (getU8(framePtr + 616L) != 0L) {
                                            longVal3 = 32L;
                                        }
                                        putI64(framePtr + 400L + longVal3, getI64(framePtr + 400L + longVal2));
                                        putI64(framePtr + 392L + longVal3, offset2);
                                        dataPtr6 = getI64(framePtr + 440L);
                                        longVal2 = 32L;
                                        if (getU8(framePtr + 616L) != 0L) {
                                            longVal2 = 64L;
                                        }
                                        offset2 = getI64(framePtr + 504L + longVal2);
                                        longVal3 = 64L;
                                        if (getU8(framePtr + 616L) != 0L) {
                                            longVal3 = 32L;
                                        }
                                        putI64(framePtr + 512L + longVal3, getI64(framePtr + 512L + longVal2));
                                        putI64(framePtr + 504L + longVal3, offset2);
                                        offset2 = getI64(framePtr + 552L);
                                        longVal2 = 32L;
                                        if (getU8(framePtr + 616L) != 0L) {
                                            longVal2 = 64L;
                                        }
                                        putI64(framePtr + 400L + longVal2, getI64(framePtr + 448L));
                                        putI64(framePtr + 392L + longVal2, dataPtr6);
                                        longVal2 = 32L;
                                        if (getU8(framePtr + 616L) != 0L) {
                                            longVal2 = 64L;
                                        }
                                        putI64(framePtr + 512L + longVal2, getI64(framePtr + 560L));
                                        putI64(framePtr + 504L + longVal2, offset2);
                                        step = 52;
                                        continue stepLoop;
                                    } else {
                                        step = 51;
                                        continue stepLoop;
                                    }
                                }
                            }
                        }
                    }
                    case 51: {
                        tempVal1 = 0L;
                        putI64(getI64(framePtr + 656L) + 8L, getI64(framePtr + 448L));
                        putI64(getI64(framePtr + 656L), framePtr + 440L);
                        paramCount = getI64(framePtr + 648L);
                        dataPtr6 = getI64(framePtr + 440L);
                        offset1 = getI64(framePtr + 448L);
                        putI64(paramCount + 8L, offset1);
                        putI64(paramCount, dataPtr6);
                        return (long) (int) tempVal1;
                    }
                    case 52: {
                        longVal2 = 2L;
                        if (getU8(framePtr + 616L) != 0L) {
                            longVal2 = 4L;
                        }
                        if (getU32(framePtr + 504L + longVal2 * 16L) == 2L) {
                            putI64(outFuncValPtr + 8L, getI64(framePtr + 432L));
                            putI64(outFuncValPtr, framePtr + 424L);
                            tempVal1 = 40L;
                            putI64(paramCount + 8L, getI64(framePtr + 464L));
                            putI64(paramCount, framePtr + 456L);
                            if (getI64(framePtr + 632L) == 0L) {
                                return (long) (int) tempVal1;
                            }
                            longVal3 = 64L;
                            if (getU8(framePtr + 616L) != 0L) {
                                longVal3 = 32L;
                            }
                            dataPtr3 = framePtr + 392L + longVal2 * 16L;
                            intPtr2 = framePtr + 392L + longVal3;
                            step = 53;
                            continue stepLoop;
                        } else {
                            step = 1;
                            continue stepLoop;
                        }
                    }
                    case 53: {
                        isZero1 = fLT(intPtr2, dataPtr3) != 0L ? 1L : 0L;
                        tempVal1 = 4L;
                        tempVal2 = 2L;
                        if (isZero1 == 0L) {
                            tempVal1 = 2L;
                            tempVal2 = 4L;
                        }
                        if (getU8(framePtr + 616L) != 0L) {
                            tempVal1 = (6L - tempVal1) & 0xffffffffL;
                        }
                        if (getU8(framePtr + 616L) != 0L) {
                            tempVal2 = (6L - tempVal2) & 0xffffffffL;
                        }
                        putI32(framePtr + 664L, getI32(framePtr + 664L) + 1L);
                        offset1 = callIndirect5(getI64(framePtr + 632L), framePtr + 392L + tempVal1 * 16L, framePtr + 392L + tempVal2 * 16L, framePtr + 504L + tempVal1 * 16L, framePtr + 504L + tempVal2 * 16L, getI64(framePtr + 640L));
                        tempVal1 = 30L;
                        if ((offset1 & 1L) == 0L) {
                            tempVal1 = 40L;
                        }
                        return (long) (int) tempVal1;
                    }
                    case 54: {
                        putI64(outFuncValPtr + 8L, getI64(framePtr + 432L));
                        putI64(outFuncValPtr, framePtr + 424L);
                        tempVal1 = 40L;
                        dataPtr6 = getI64(framePtr + 456L);
                        offset1 = getI64(framePtr + 464L);
                        putI64(paramCount + 8L, offset1);
                        putI64(paramCount, dataPtr6);
                        return (long) (int) tempVal1;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long ZeroFinderMidpoint(long boundAPtr, long boundBPtr, long midPtr) {
        long exponent = 0L;
        long isZero = 0L;
        long dataPtr = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            if (getU32(boundAPtr) == 2L || getU32(boundAPtr) == 0xfffffffeL) {
                fradd(boundAPtr, boundBPtr, framePtr + 24L, 1L);
                fiDiv100(framePtr + 24L);
                dataPtr = boundBPtr;
                fradd(dataPtr, framePtr + 24L, midPtr, 0L);
            } else {
                if (getU32(boundBPtr) != 2L && getU32(boundBPtr) != 0xfffffffeL) {
                    isZero = fZero(boundAPtr) != 0L ? 1L : 0L;
                    if (isZero != 0L || (long) (int) comma(isZero = fZero(boundBPtr) != 0L ? 1L : 0L, isZero) != 0L) {
                        isZero = fZero(boundAPtr) != 0L ? 1L : 0L;
                        dataPtr = boundBPtr;
                        if (isZero == 0L) {
                            dataPtr = boundAPtr;
                        }
                        tempVal = getI64(dataPtr);
                        putI64(midPtr + 8L, getI64(dataPtr + 8L));
                        putI64(midPtr, tempVal);
                        exponent = getU32(midPtr + 4L);
                        if ((long) (int) exponent < -10L) {
                            putU32(midPtr + 4L, exponent - 10L);
                            if ((long) (int) exponent < -489L) {
                                putU32(midPtr + 8L, 0L);
                                putU32(midPtr + 12L, 0L);
                                putU32(midPtr, 1L);
                                putU32(midPtr + 4L, 0L);
                            }
                        } else {
                            fdiv2(midPtr, midPtr);
                        }
                    } else {
                        fradd(boundAPtr, boundBPtr, midPtr, 0L);
                        fdiv2(midPtr, midPtr);
                    }
                } else {
                    fradd(boundBPtr, boundAPtr, framePtr + 24L, 1L);
                    fiDiv100(framePtr + 24L);
                    dataPtr = boundAPtr;
                    fradd(dataPtr, framePtr + 24L, midPtr, 0L);
                }
            }
            isZero = fNE(midPtr, boundAPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                isZero = fNE(midPtr, boundBPtr) != 0L ? 1L : 0L;
            } else {
                isZero = 0L;
            }
            return isZero;
        } finally {
            sp = savedSp;
        }
    }

    long ZeroFinderEval(long statePtr, long xPtr) {
        long dataPtr1 = 0L;
        long intPtr = 0L;
        long longVal1 = 0L;
        long longVal2 = 0L;
        long isZero = 0L;
        long offset = 0L;
        long intVal = 0L;
        long tempVal1 = 0L;
        long result = 0L;
        long dataPtr2 = 0L;
        long tempVal2 = 0L;
        tempVal2 = getI64(statePtr);
        dataPtr1 = xPtr + 48L;
        intPtr = xPtr + 160L;
        putI64(xPtr + 56L, getI64(statePtr + 8L));
        putI64(xPtr + 48L, tempVal2);
        callIndirect3(getI64(xPtr + 232L), dataPtr1, getI64(xPtr + 248L), intPtr);
        result = (getI32(xPtr + 160L) + 2L) & 0xffffffffL;
        if (result < 5L && ((1L << (int) (result & 31L)) & 21L) != 0L || comma(intVal = getI32(xPtr + 164L), 24999L < intVal ? 1L : 0L) != 0L) {
            putI64(xPtr + 168L, 0x999999999999999L);
            putI32(intPtr, 2L);
            putI32(intPtr + 4L, 49999L);
            intVal = getI32(xPtr + 164L);
        }
        if (intVal < -24999L) {
            putI64(xPtr + 168L, 0L);
            putI32(intPtr, 1L);
            putI32(intPtr + 4L, 0L);
        }
        isZero = fZero(intPtr) != 0L ? 1L : 0L;
        if (isZero != 0L) {
            dataPtr2 = getI64(xPtr + 264L);
            tempVal2 = getI64(dataPtr1);
            result = 0L;
            putI64(dataPtr2 + 8L, getI64(xPtr + 56L));
            putI64(dataPtr2, tempVal2);
            dataPtr2 = getI64(xPtr + 256L);
            tempVal2 = getI64(dataPtr1);
            putI64(dataPtr2 + 8L, getI64(xPtr + 56L));
            putI64(dataPtr2, tempVal2);
        } else {
            if (getI32(xPtr + 160L) == 2L) {
                tempVal2 = getI64(dataPtr1);
                longVal1 = 80L;
                if (getI8(xPtr + 224L) != 0L) {
                    longVal1 = 16L;
                }
                putI64(xPtr + longVal1 + 8L, getI64(xPtr + 56L));
                putI64(xPtr + longVal1, tempVal2);
                tempVal2 = getI64(intPtr);
                result = 5L;
                longVal1 = 80L;
                if (getI8(xPtr + 224L) != 0L) {
                    longVal1 = 16L;
                }
                putI64(xPtr + longVal1 + 120L, getI64(xPtr + 168L));
                putI64(xPtr + longVal1 + 112L, tempVal2);
                if (getI64(xPtr + 240L) == 0L) {
                    return result;
                }
                isZero = getI8(xPtr + 224L) != 0L ? 1L : 0L;
                longVal1 = 32L;
                if (isZero != 0L) {
                    longVal1 = 64L;
                }
                longVal2 = 80L;
                if (isZero != 0L) {
                    longVal2 = 16L;
                }
                isZero = fLT(xPtr + longVal2, xPtr + longVal1) != 0L ? 1L : 0L;
                tempVal1 = 2L;
                if (isZero == 0L) {
                    tempVal1 = 5L;
                    result = 2L;
                }
                isZero = getI8(xPtr + 224L) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    result = (6L - result) & 0xffffffffL;
                }
                if (isZero != 0L) {
                    tempVal1 = (6L - tempVal1) & 0xffffffffL;
                }
                putI32(xPtr + 272L, getI32(xPtr + 272L) + 1L);
                offset = callIndirect5(getI64(xPtr + 240L), xPtr + result * 16L, xPtr + tempVal1 * 16L, xPtr + 112L + result * 16L, xPtr + 112L + tempVal1 * 16L, getI64(xPtr + 248L));
                result = 30L;
                if ((offset & 1L) == 0L) {
                    result = 5L;
                }
            } else {
                tempVal2 = getI64(dataPtr1);
                longVal1 = 64L;
                if (getI8(xPtr + 224L) != 0L) {
                    longVal1 = 32L;
                }
                putI64(xPtr + longVal1 + 8L, getI64(xPtr + 56L));
                putI64(xPtr + longVal1, tempVal2);
                tempVal2 = getI64(intPtr);
                longVal1 = 64L;
                if (getI8(xPtr + 224L) != 0L) {
                    longVal1 = 32L;
                }
                dataPtr1 = xPtr + 112L + longVal1;
                putI64(dataPtr1 + 8L, getI64(xPtr + 168L));
                putI64(dataPtr1, tempVal2);
                longVal1 = 0L;
                if (getU8(xPtr + 224L) != 0L) {
                    longVal1 = 96L;
                }
                if (getI32(xPtr + 112L + longVal1) != 2L) {
                    putU8(xPtr + 224L, getU8(xPtr + 224L) ^ 1L);
                }
                result = 4L;
            }
        }
        return result;
    }

    long ZeroFinderBracket(long statePtr, long stepScalePtr) {
        long tempVal1 = 0L;
        long intPtr = 0L;
        long dataPtr1 = 0L;
        long tempVal2 = 0L;
        long longVal1 = 0L;
        long longVal2 = 0L;
        long byteVal = 0L;
        long isZero = 0L;
        long offset = 0L;
        long intVal = 0L;
        long result = 0L;
        long constPtr = 0L;
        long dataPtr2 = 0L;
        long dataPtr3 = 0L;
        long dataPtr4 = 0L;
        long tempVal3 = 0L;
        int step = 0;
        stepLoop: while (true) {
            switch (step) {
                case 0: {
                    dataPtr3 = stepScalePtr + 48L;
                    intPtr = stepScalePtr + 160L;
                    dataPtr1 = statePtr + 8L;
                    step = 1;
                    continue stepLoop;
                }
                case 1: {
                    tempVal3 = getI64(statePtr);
                    putI64(stepScalePtr + 56L, getI64(statePtr + 8L));
                    putI64(dataPtr3, tempVal3);
                    callIndirect3(getI64(stepScalePtr + 232L), dataPtr3, getI64(stepScalePtr + 248L), intPtr);
                    tempVal1 = (getI32(stepScalePtr + 160L) + 2L) & 0xffffffffL;
                    if (tempVal1 < 5L && ((1L << (int) (tempVal1 & 31L)) & 21L) != 0L || comma(intVal = getI32(stepScalePtr + 164L), 24999L < intVal ? 1L : 0L) != 0L) {
                        putI64(stepScalePtr + 168L, 0x999999999999999L);
                        putI32(intPtr, 2L);
                        putI32(intPtr + 4L, 49999L);
                        intVal = getI32(stepScalePtr + 164L);
                    }
                    if (intVal < -24999L) {
                        putI64(stepScalePtr + 168L, 0L);
                        putI32(intPtr, 1L);
                        putI32(intPtr + 4L, 0L);
                    }
                    isZero = fZero(intPtr) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        dataPtr2 = getI64(stepScalePtr + 264L);
                        tempVal3 = getI64(dataPtr3);
                        putI64(dataPtr2 + 8L, getI64(stepScalePtr + 56L));
                        putI64(dataPtr2, tempVal3);
                        dataPtr2 = getI64(stepScalePtr + 256L);
                        tempVal3 = getI64(dataPtr3);
                        putI64(dataPtr2 + 8L, getI64(stepScalePtr + 56L));
                        putI64(dataPtr2, tempVal3);
                        return 0L;
                    }
                    if (getI32(intPtr) != 2L) {
                        return 4L;
                    }
                    tempVal3 = getI64(dataPtr3);
                    longVal1 = 80L;
                    if (getI8(stepScalePtr + 224L) != 0L) {
                        longVal1 = 16L;
                    }
                    putI64(stepScalePtr + longVal1 + 8L, getI64(stepScalePtr + 56L));
                    putI64(stepScalePtr + longVal1, tempVal3);
                    tempVal3 = getI64(intPtr);
                    longVal1 = 80L;
                    if (getI8(stepScalePtr + 224L) != 0L) {
                        longVal1 = 16L;
                    }
                    putI64(stepScalePtr + longVal1 + 120L, getI64(stepScalePtr + 168L));
                    putI64(stepScalePtr + longVal1 + 112L, tempVal3);
                    if (getI64(stepScalePtr + 240L) != 0L) {
                        isZero = getI8(stepScalePtr + 224L) != 0L ? 1L : 0L;
                        longVal1 = 32L;
                        if (isZero != 0L) {
                            longVal1 = 64L;
                        }
                        longVal2 = 80L;
                        if (isZero != 0L) {
                            longVal2 = 16L;
                        }
                        isZero = fLT(stepScalePtr + longVal2, stepScalePtr + longVal1) != 0L ? 1L : 0L;
                        tempVal1 = 5L;
                        tempVal2 = 2L;
                        if (isZero == 0L) {
                            tempVal1 = 2L;
                            tempVal2 = 5L;
                        }
                        isZero = getI8(stepScalePtr + 224L) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            tempVal1 = (6L - tempVal1) & 0xffffffffL;
                        }
                        if (isZero != 0L) {
                            tempVal2 = (6L - tempVal2) & 0xffffffffL;
                        }
                        putI32(stepScalePtr + 272L, getI32(stepScalePtr + 272L) + 1L);
                        offset = callIndirect5(getI64(stepScalePtr + 240L), stepScalePtr + tempVal1 * 16L, stepScalePtr + tempVal2 * 16L, stepScalePtr + 112L + tempVal1 * 16L, stepScalePtr + 112L + tempVal2 * 16L, getI64(stepScalePtr + 248L));
                        if ((offset & 1L) != 0L) {
                            return 30L;
                        }
                    }
                    isZero = getI8(stepScalePtr + 224L) != 0L ? 1L : 0L;
                    longVal1 = 64L;
                    if (isZero != 0L) {
                        longVal1 = 32L;
                    }
                    longVal2 = 80L;
                    if (isZero != 0L) {
                        longVal2 = 16L;
                    }
                    isZero = ZeroFinderMidpoint(stepScalePtr + longVal1, stepScalePtr + longVal2, statePtr) != 0L ? 1L : 0L;
                    if (isZero == 0L) {
                        if (getI64(stepScalePtr + 240L) != 0L) {
                            isZero = getI8(stepScalePtr + 224L) != 0L ? 1L : 0L;
                            longVal1 = 32L;
                            if (isZero != 0L) {
                                longVal1 = 64L;
                            }
                            longVal2 = 64L;
                            if (isZero != 0L) {
                                longVal2 = 32L;
                            }
                            isZero = fLT(stepScalePtr + longVal2, stepScalePtr + longVal1) != 0L ? 1L : 0L;
                            tempVal1 = 4L;
                            tempVal2 = 2L;
                            if (isZero == 0L) {
                                tempVal1 = 2L;
                                tempVal2 = 4L;
                            }
                            isZero = getI8(stepScalePtr + 224L) != 0L ? 1L : 0L;
                            if (isZero != 0L) {
                                tempVal1 = (6L - tempVal1) & 0xffffffffL;
                            }
                            if (isZero != 0L) {
                                tempVal2 = (6L - tempVal2) & 0xffffffffL;
                            }
                            putI32(stepScalePtr + 272L, getI32(stepScalePtr + 272L) + 1L);
                            offset = callIndirect5(getI64(stepScalePtr + 240L), stepScalePtr + tempVal1 * 16L, stepScalePtr + tempVal2 * 16L, stepScalePtr + 112L + tempVal1 * 16L, stepScalePtr + 112L + tempVal2 * 16L, getI64(stepScalePtr + 248L));
                            if ((offset & 1L) != 0L) {
                                return 30L;
                            }
                        }
                        dataPtr3 = getI64(stepScalePtr + 256L);
                        tempVal3 = getI64(stepScalePtr + 32L);
                        putI64(dataPtr3 + 8L, getI64(stepScalePtr + 40L));
                        putI64(dataPtr3, tempVal3);
                        dataPtr3 = getI64(stepScalePtr + 264L);
                        tempVal3 = getI64(stepScalePtr + 64L);
                        putI64(dataPtr3 + 8L, getI64(stepScalePtr + 72L));
                        putI64(dataPtr3, tempVal3);
                        longVal1 = 64L;
                        if (getI8(stepScalePtr + 224L) != 0L) {
                            longVal1 = 32L;
                        }
                        isZero = fMLE(CC_PBig_L, stepScalePtr + longVal1) != 0L ? 1L : 0L;
                        result = 39L;
                        if (isZero == 0L) {
                            result = 41L;
                        }
                        return result;
                    }
                    byteVal = getI8(stepScalePtr + 224L);
                    longVal1 = 64L;
                    if ((byte) byteVal != 0L) {
                        longVal1 = 32L;
                    }
                    longVal2 = 80L;
                    if ((byte) byteVal != 0L) {
                        longVal2 = 16L;
                    }
                    constPtr = statePtr;
                    dataPtr4 = dataPtr1;
                    if ((byte) byteVal == 0L) {
                        if (getI32(stepScalePtr + longVal1) < 0L) {
                            intVal = getI32(stepScalePtr + longVal2);
                            step = 3;
                            continue stepLoop;
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    } else {
                        if (getI32(stepScalePtr + longVal2) < 0L) {
                            intVal = getI32(stepScalePtr + longVal1);
                            step = 3;
                            continue stepLoop;
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                }
                case 2: {
                    tempVal3 = getI64(dataPtr4);
                    putI64(statePtr, getI64(constPtr));
                    putI64(statePtr + 8L, tempVal3);
                    step = 1;
                    continue stepLoop;
                }
                case 3: {
                    if (0L < intVal && (long) (int) getU32(statePtr + 4L) < -10L) {
                        constPtr = CC_0;
                        dataPtr4 = ADDR_RODATA + 1144L;
                    }
                    step = 2;
                    continue stepLoop;
                }
                default: throw new RuntimeException("Invalid step: " + step);
            }
        }
    }

    CC_Real fRand(long seedPtr) {
        long flag = 0L;
        long bcdAcc1 = 0L;
        long index1 = 0L;
        long bcdAcc2 = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long tempVal3 = 0L;
        long index2 = 0L;
        CC_Real realVal = new CC_Real();
        bcdAcc2 = getI64(seedPtr);
        if (bcdAcc2 == 0L) {
            bcdAcc2 = 0x3308353309995003L;
            putI64(seedPtr, 0x3308353309995003L);
        }
        tempVal2 = 0L;
        tempVal1 = 0L;
        tempVal3 = 0x2851130928467L;
        index2 = -16L;
        do {
            bcdAcc2 = dcbMul(bcdAcc2, tempVal3 & 15L);
            bcdAcc1 = dcbAddAdjust(tempVal1 | tempVal2, bcdAcc2);
            tempVal3 = tempVal3 >>> 4;
            tempVal1 = bcdAcc1 & 0xffffffff00000000L;
            flag = index2 != -1L ? 1L : 0L;
            index2 = (long) (int) (index2 + 1L);
            bcdAcc2 = getI64(seedPtr) << 4;
            tempVal2 = bcdAcc1 & 0xffffffffL;
            putI64(seedPtr, bcdAcc2);
        } while (flag != 0L);
        putI64(seedPtr, bcdAcc1 & 0xfffffffffffffffL);
        if ((bcdAcc1 & 0xf00000000000000L) == 0L) {
            index1 = 0xffffffffL;
            do {
                bcdAcc1 = (bcdAcc1 << 4) & 0xffffffffL;
                bcdAcc2 = tempVal1 & 0xf0000000000000L;
                tempVal1 = ((((tempVal1 | tempVal2)) & 0xffffffff0000000L)) << 4;
                index1 = (index1 - 1L) & 0xffffffffL;
                tempVal2 = bcdAcc1;
            } while (bcdAcc2 == 0L);
            bcdAcc2 = (index1 << 32) | 1L;
        } else {
            bcdAcc2 = 0xffffffff00000001L;
            bcdAcc1 = tempVal2;
        }
        realVal.m = (tempVal1 & 0xfffffff00000000L) | (bcdAcc1 & 0xfffff000L);
        realVal.s = (long) (int) bcdAcc2;
        realVal.e = (long) (int) (bcdAcc2 >>> 32);
        return realVal.copy();
    }

    long fRandomize(long seedPtr, long inputPtr) {
        long tempVal1 = 0L;
        long isZero = 0L;
        long index = 0L;
        long bcdVal = 0L;
        long tempVal2 = 0L;
        long mantissa = 0L;
        isZero = fZero(seedPtr) != 0L ? 1L : 0L;
        if (isZero != 0L) {
            if (inputPtr == 0L) {
                return 0x3308353309995003L;
            }
            tempVal2 = callIndirect0(inputPtr);
            mantissa = tempVal2 & 0xffffffffffff000L;
            bcdVal = (((-((((tempVal2 & 0xffffffffL)) >> 11) & 1L) & 0xfffff000L) | (tempVal2 & 4095L))) & 0xffffffffL;
        } else {
            mantissa = getI64(seedPtr + 8L);
            bcdVal = getI32(seedPtr + 4L) & 0xffffffffL;
        }
        index = (bcdVal + 1000L) & 0xffffffffL;
        tempVal1 = (bcdVal + 1000L) & 0xffffffffL;
        if (-1L < (long) (int) bcdVal) {
            index = bcdVal;
            tempVal1 = bcdVal;
        }
        do {
            tempVal2 = mantissa;
            index = (index + 1L) & 0xffffffffL;
            if (tempVal2 == 0L) {
                bcdVal = bintobcd(index) & 0xffffffffL;
                return (tempVal2 & 0xffffffffffff000L) | ((((((bcdVal & 0xffL)) << 4) | 1L)) & 0xffffffffL);
            }
            mantissa = tempVal2 >>> 4;
        } while (498L < (long) (int) tempVal1);
        bcdVal = bintobcd(index) & 0xffffffffL;
        return (tempVal2 & 0xffffffffffff000L) | ((((((bcdVal & 0xffL)) << 4) | 1L)) & 0xffffffffL);
    }

    long fifact_L(long valPtr, long resultPtr) {
        long tempVal = 0L;
        long isZero = 0L;
        long intVal1 = 0L;
        long status1 = 0L;
        long bcdAcc1 = 0L;
        long status2 = 0L;
        long bcdAcc2 = 0L;
        long bcdAcc3 = 0L;
        long constPtr = 0L;
        long intVal2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        if (getU32(valPtr) == 0L) {
                            status2 = getI64(valPtr);
                            bcdAcc1 = 0L;
                            putI64(resultPtr + 8L, getI64(valPtr + 8L));
                            putI64(resultPtr, status2);
                            return bcdAcc1;
                        }
                        isZero = fLT(ADDR_RODATA + 2792L, valPtr) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            constPtr = CC_PInf_L;
                            bcdAcc1 = 0L;
                            status2 = getI64(constPtr);
                            putI64(resultPtr + 8L, getI64(constPtr + 8L));
                            putI64(resultPtr, status2);
                            return bcdAcc1;
                        }
                        isZero = fiInteger(valPtr) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            isZero = fLT(valPtr, CC_n253) != 0L ? 1L : 0L;
                            if (isZero == 0L) {
                                fradd(CC_7, valPtr, framePtr + 56L, 1L);
                                fiIP(framePtr + 56L, framePtr + 56L);
                                isZero = fLE(framePtr + 56L, CC_0) != 0L ? 1L : 0L;
                                if (isZero != 0L) {
                                    status2 = getI64(valPtr);
                                    putI64(resultPtr + 8L, getI64(valPtr + 8L));
                                    putI64(resultPtr, status2);
                                    fiStirlingApprox(resultPtr);
                                    intVal1 = 1L;
                                } else {
                                    fradd(framePtr + 56L, valPtr, framePtr + 40L, 0L);
                                    fiStirlingApprox(framePtr + 40L);
                                    intVal1 = (long) (int) fCCToInt(framePtr + 56L);
                                    firadd(valPtr, CC_1, framePtr + 56L, 0L);
                                    while (comma(intVal1 = (long) (int) (intVal1 - 1L), intVal1 != 0L ? 1L : 0L) != 0L) {
                                        firadd(framePtr + 16L, CC_1, framePtr + 16L, 0L);
                                        fimul(framePtr + 56L, framePtr + 16L, framePtr + 56L);
                                    }
                                    status2 = fidiv(framePtr + 40L, framePtr + 56L, resultPtr);
                                    intVal1 = (long) (int) status2;
                                }
                                status1 = fNorm_L(intVal1, resultPtr) & 0xffffffffL;
                                bcdAcc1 = status1;
                                return bcdAcc1;
                            }
                            fimod(valPtr, CC_2, resultPtr);
                            isZero = fZero(resultPtr) != 0L ? 1L : 0L;
                            if (isZero == 0L) {
                                constPtr = CC_NInf;
                                bcdAcc1 = 0L;
                                status2 = getI64(constPtr);
                                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                                putI64(resultPtr, status2);
                                return bcdAcc1;
                            }
                            constPtr = CC_PInf;
                            bcdAcc1 = 0L;
                            status2 = getI64(constPtr);
                            putI64(resultPtr + 8L, getI64(constPtr + 8L));
                            putI64(resultPtr, status2);
                            return bcdAcc1;
                        }
                        isZero = fZero(valPtr) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            constPtr = CC_1;
                            bcdAcc1 = 0L;
                            status2 = getI64(constPtr);
                            putI64(resultPtr + 8L, getI64(constPtr + 8L));
                            putI64(resultPtr, status2);
                            return bcdAcc1;
                        }
                        if (-1L >= (long) (int) getU32(valPtr)) {
                            intVal1 = (long) (int) fiParity(valPtr);
                            if (intVal1 == 1L) {
                                constPtr = CC_PInf;
                                bcdAcc1 = 0L;
                                status2 = getI64(constPtr);
                                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                                putI64(resultPtr, status2);
                                return bcdAcc1;
                            }
                            constPtr = CC_NInf;
                            bcdAcc1 = 0L;
                            status2 = getI64(constPtr);
                            putI64(resultPtr + 8L, getI64(constPtr + 8L));
                            putI64(resultPtr, status2);
                            return bcdAcc1;
                        }
                        if (getU32(valPtr + 4L) == 0L) {
                            status1 = getU8(valPtr + 15L);
                        } else {
                            status1 = 20L;
                            if (getU32(valPtr + 4L) != 1L) {
                                status1 = 16L;
                            }
                            status1 = (getU32(valPtr + 12L) >> (int) status1) & 0xffffffffL;
                        }
                        intVal2 = 0L;
                        bcdAcc1 = 0x100000000000000L;
                        if (status1 == 0L) {
                            putI64(resultPtr + 8L, bcdAcc1);
                            intVal1 = 0L;
                            putI32(resultPtr, 1L);
                            putI32(resultPtr + 4L, intVal2);
                            status1 = fNorm_L(intVal1, resultPtr) & 0xffffffffL;
                            bcdAcc1 = status1;
                            return bcdAcc1;
                        }
                        step = 1;
                        continue stepLoop;
                    }
                    case 1: {
                        bcdAcc2 = bcdAcc1;
                        intVal1 = intVal2;
                        if (bcdAcc1 >>> 60 != 0L) {
                            intVal1 = (long) (int) (intVal2 + 1L);
                            bcdAcc2 = dcbShiftRound(bcdAcc1, 1L);
                        }
                        bcdAcc1 = dcbMul(bcdAcc2, status1 & 15L);
                        if ((status1 & 240L) == 0L) {
                            intVal2 = intVal1;
                            if ((status1 & 3840L) != 0L) {
                                bcdAcc1 = bcdAcc1 >>> 4;
                                step = 3;
                                continue stepLoop;
                            } else {
                                step = 2;
                                continue stepLoop;
                            }
                        } else {
                            intVal2 = (long) (int) (intVal1 + 1L);
                            bcdAcc1 = dcbShiftRound(bcdAcc1, 1L);
                            bcdAcc3 = dcbMul(bcdAcc2, (status1 >> 4) & 15L);
                            bcdAcc1 = dcbAddAdjust(bcdAcc1, bcdAcc3);
                            if ((status1 & 3840L) != 0L) {
                                step = 3;
                                continue stepLoop;
                            } else {
                                step = 2;
                                continue stepLoop;
                            }
                        }
                    }
                    case 2: {
                        tempVal = (status1 - 96L) & 0xffffffffL;
                        if ((status1 & 240L) != 0L) {
                            tempVal = status1;
                        }
                        tempVal = (tempVal - 6L) & 0xffffffffL;
                        if ((status1 & 15L) != 0L) {
                            tempVal = status1;
                        }
                        status1 = (tempVal - 1L) & 0xffffffffL;
                        if (status1 == 0L) {
                            if (intVal2 == 300L || intVal2 == 234L) {
                                bcdAcc1 = bcdAcc1 - 4096L;
                            }
                            putI64(resultPtr + 8L, bcdAcc1);
                            intVal1 = 0L;
                            putI32(resultPtr, 1L);
                            putI32(resultPtr + 4L, intVal2);
                            status1 = fNorm_L(intVal1, resultPtr) & 0xffffffffL;
                            bcdAcc1 = status1;
                            return bcdAcc1;
                        }
                        step = 1;
                        continue stepLoop;
                    }
                    case 3: {
                        bcdAcc1 = dcbShiftRound(bcdAcc1, 1L);
                        bcdAcc2 = dcbMul(bcdAcc2, ((long) (int) status1 >> 8) & 0xffffffffL);
                        bcdAcc1 = dcbAddAdjust(bcdAcc1, bcdAcc2);
                        intVal2 = (long) (int) (intVal1 + 2L);
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

    void fiStirlingApprox(long valPtr) {
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            firadd(valPtr, CC_1, valPtr, 0L);
            fimul(valPtr, valPtr, framePtr + 40L);
            fimul(framePtr + 40L, CC_12, framePtr + 40L);
            putI64(framePtr + 24L, getI64(framePtr + 48L));
            putI64(framePtr + 16L, getI64(framePtr + 40L));
            firadd(framePtr + 16L, CC_60, framePtr + 16L, 0L);
            fidiv(CC_n495p920119017593, framePtr + 16L, framePtr + 16L);
            firadd(framePtr + 16L, CC_30p3479606073615, framePtr + 16L, 0L);
            firadd(framePtr + 16L, framePtr + 40L, framePtr + 16L, 0L);
            fidiv(CC_n76p5594818140208, framePtr + 16L, framePtr + 16L);
            firadd(framePtr + 16L, CC_9p33584905660377, framePtr + 16L, 0L);
            firadd(framePtr + 16L, framePtr + 40L, framePtr + 16L, 0L);
            fidiv(CC_n1p21142857142857, framePtr + 16L, framePtr + 16L);
            firadd(framePtr + 16L, CC_0p4, framePtr + 16L, 0L);
            firadd(framePtr + 16L, framePtr + 40L, framePtr + 40L, 0L);
            fidiv(valPtr, framePtr + 40L, framePtr + 40L);
            firadd(framePtr + 40L, valPtr, framePtr + 40L, 1L);
            firadd(framePtr + 40L, CC_0p918938533204673, framePtr + 40L, 0L);
            filn(valPtr, framePtr + 16L);
            firadd(valPtr, CC_0p5, valPtr, 1L);
            fimul(valPtr, framePtr + 16L, valPtr);
            firadd(valPtr, framePtr + 40L, valPtr, 0L);
            fiexpm(valPtr, valPtr, 0L);
        } finally {
            sp = savedSp;
        }
    }

    long fipermComb_L(long nPtr, long rPtr, long isComb, long resultPtr) {
        long isEqual = 0L;
        long status1 = 0L;
        long status2 = 0L;
        long status3 = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long mantissa = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            status2 = getU32(nPtr);
            if (status2 == 0L || comma(tempVal1 = getU32(rPtr), tempVal1 == 0L ? 1L : 0L) != 0L) {
                status3 = 0L;
                mantissa = 0L;
                tempVal2 = 0L;
                putI64(isComb + 8L, mantissa);
                putI64(isComb, tempVal2);
                return status3;
            }
            if (status2 == 2L) {
                if (tempVal1 == 2L) {
                    status3 = 0L;
                    mantissa = 0L;
                    tempVal2 = 0L;
                    putI64(isComb + 8L, mantissa);
                    putI64(isComb, tempVal2);
                    return status3;
                }
            } else {
                tempVal1 = ((tempVal1 | status2)) & 0xffffffffL;
            }
            if ((long) (int) tempVal1 < 0L || (long) (int) comma(isEqual = fLT(rPtr, nPtr) != 0L ? 1L : 0L, isEqual) != 0L || 11L < (long) (int) getU32(rPtr + 4L) || comma(isEqual = fiInteger(nPtr) != 0L ? 1L : 0L, isEqual == 0L || comma(isEqual = fiInteger(rPtr) != 0L ? 1L : 0L, isEqual == 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L) {
                status3 = 24L;
                mantissa = 0L;
                tempVal2 = 0L;
                putI64(isComb + 8L, mantissa);
                putI64(isComb, tempVal2);
                return status3;
            }
            if (getU32(rPtr) == 2L) {
                mantissa = getI64(rPtr + 8L);
                tempVal2 = getI64(rPtr);
                status3 = 0L;
                putI64(isComb + 8L, mantissa);
                putI64(isComb, tempVal2);
                return status3;
            }
            if ((resultPtr & 1L) != 0L) {
                fradd(nPtr, CC_1, framePtr + 64L, 0L);
                isEqual = fEQ(nPtr, rPtr) != 0L ? 1L : 0L;
                if (isEqual != 0L || (long) (int) comma(isEqual = fEQ(rPtr, framePtr + 64L) != 0L ? 1L : 0L, isEqual) != 0L) {
                    status3 = fifact_L(rPtr, isComb);
                    return status3;
                }
            }
            fradd(rPtr, nPtr, framePtr + 64L, 1L);
            putI64(framePtr + 56L, getI64(nPtr + 8L));
            putI64(framePtr + 48L, getI64(nPtr));
            if ((resultPtr & 1L) == 0L && (long) (int) comma(isEqual = fLT(framePtr + 64L, framePtr + 48L) != 0L ? 1L : 0L, isEqual) != 0L) {
                putI64(framePtr + 56L, getI64(framePtr + 72L));
                putI64(framePtr + 48L, getI64(framePtr + 64L));
                putI64(framePtr + 72L, getI64(nPtr + 8L));
                putI64(framePtr + 64L, getI64(nPtr));
            }
            putI64(framePtr + 40L, 0L);
            putI64(framePtr + 32L, 1L);
            putI64(framePtr + 72L, getI64(framePtr + 72L) & -4096L);
            putI64(framePtr + 56L, getI64(framePtr + 56L) & -4096L);
            putU32(isComb + 8L, 0L);
            putU32(isComb + 12L, 0x1000000L);
            putU32(isComb, 1L);
            putU32(isComb + 4L, 0L);
            isEqual = fEQ(framePtr + 32L, framePtr + 48L) != 0L ? 1L : 0L;
            if (isEqual != 0L || 499L < (long) (int) getU32(isComb + 4L)) {
                status1 = 0L;
            } else {
                do {
                    fradd(framePtr + 32L, CC_1, framePtr + 32L, 0L);
                    fradd(framePtr + 64L, framePtr + 32L, framePtr + 16L, 0L);
                    if ((resultPtr & 1L) == 0L) {
                        fdiv(framePtr + 16L, framePtr + 32L, framePtr + 16L);
                    }
                    status1 = (long) (int) fmul(isComb, framePtr + 16L, isComb);
                    isEqual = fEQ(framePtr + 32L, framePtr + 48L) != 0L ? 1L : 0L;
                } while (isEqual == 0L && (long) (int) getU32(isComb + 4L) < 500L);
            }
            status2 = fNorm_L(status1, isComb) & 0xffffffffL;
            status3 = status2;
            return status3;
        } finally {
            sp = savedSp;
        }
    }

    void PolEvl(long xPtr, long coeffTablePtr, long degree, long resultPtr) {
        long exponent = 0L;
        long index = 0L;
        index = getI32(coeffTablePtr);
        exponent = getI32(coeffTablePtr + 4L);
        putI64(resultPtr + 8L, getI64(coeffTablePtr + 8L));
        putI32(resultPtr, index);
        putI32(resultPtr + 4L, exponent);
        if (0L < (long) (int) degree) {
            index = (long) (int) (degree + 1L);
            do {
                coeffTablePtr = coeffTablePtr + 16L;
                fimul(resultPtr, xPtr, resultPtr);
                firadd(resultPtr, coeffTablePtr, resultPtr, 0L);
                index = (long) (int) (index - 1L);
            } while (1L < index);
        }
    }

    void P1Evl(long xPtr, long coeffTablePtr, long degree, long resultPtr) {
        long index = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            putI64(framePtr + 24L, getI64(coeffTablePtr + 8L));
            putI32(framePtr + 16L, getI32(coeffTablePtr));
            putI32(framePtr + 20L, getI32(coeffTablePtr + 4L));
            firadd(xPtr, coeffTablePtr, framePtr + 16L, 0L);
            if (1L < (long) (int) degree) {
                index = (degree & 0xffffffffL) - 1L;
                do {
                    coeffTablePtr = coeffTablePtr + 16L;
                    fimul(framePtr + 16L, xPtr, framePtr + 16L);
                    firadd(framePtr + 16L, coeffTablePtr, framePtr + 16L, 0L);
                    index = index - 1L;
                } while (index != 0L);
            }
            putI64(resultPtr + 8L, getI64(framePtr + 24L));
            putI32(resultPtr, getI64(framePtr + 16L));
            putI32(resultPtr + 4L, getI32(framePtr + 20L));
        } finally {
            sp = savedSp;
        }
    }

    long fitrig(long anglePtr, long angleMode, long trigOp, long resultPtr) {
        long tempVal1 = 0L;
        long intVal = 0L;
        long isZero = 0L;
        long flag = 0L;
        long bcdAcc1 = 0L;
        long constPtr1 = 0L;
        long bcdAcc2 = 0L;
        long constPtr2 = 0L;
        long dataPtr1 = 0L;
        long tempVal2 = 0L;
        long index1 = 0L;
        long tempVal3 = 0L;
        long result = 0L;
        long tempVal4 = 0L;
        long bcdAcc3 = 0L;
        long tempVal5 = 0L;
        long dataPtr2 = 0L;
        long tempVal6 = 0L;
        long index2 = 0L;
        long index3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        constPtr1 = framePtr + 16L;
                        dataPtr1 = framePtr + 16L;
                        tempVal6 = (getI64(anglePtr) + 1L) & 0xffffffffL;
                        if (4L < (tempVal6 & 0xffffffffL) || ((1L << (int) (tempVal6 & 31L)) & 21L) == 0L) {
                            result = 0L;
                            putI64(angleMode + 8L, 0L);
                            putI64(angleMode, 0L);
                            return result;
                        }
                        if ((long) (int) resultPtr == 2L) {
                            fmul(anglePtr, CC_0p9, framePtr + 32L);
                        } else {
                            putI64(framePtr + 40L, getI64(anglePtr + 8L));
                            putI64(framePtr + 32L, getI64(anglePtr));
                        }
                        tempVal6 = ((((((trigOp << 5) ^ 0xffffffffL)) & 32L) | trigOp)) & 0xffffffffL;
                        isZero = fZero(framePtr + 32L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            tempVal6 = ((tempVal6 | 16L)) & 0xffffffffL;
                        }
                        if ((long) (int) getI64(framePtr + 32L) < 0L) {
                            tempVal2 = 8L;
                            if ((tempVal6 & 1L) != 0L) {
                                tempVal2 = 12L;
                            }
                            tempVal6 = ((tempVal2 | tempVal6)) & 0xffffffffL;
                            putI64(framePtr + 32L, (((getI32(framePtr + 36L) & 0xffffffffL)) << 32) | 1L);
                        }
                        index1 = getI32(framePtr + 36L);
                        putI32(framePtr + 56L, 0L);
                        putI32(framePtr + 60L, 0L);
                        putI32(framePtr + 64L, getI64(framePtr + 40L));
                        putU32(framePtr + 68L, getU32(framePtr + 44L));
                        index2 = getI32(framePtr + 36L);
                        if ((long) (int) resultPtr == 0L) {
                            step = 2;
                            continue stepLoop;
                        } else {
                            index2 = (long) (int) (getI32(framePtr + 36L) - 1L);
                            intVal = (long) (int) (getI32(framePtr + 36L) - 2L);
                            putI64(framePtr + 32L, (((index2 & 0xffffffffL)) << 32) | (getI64(framePtr + 32L) & 0xffffffffL));
                            if (index1 < 2L) {
                                if (index2 != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    putI32(framePtr + 60L, getI64(framePtr + 40L) << 28);
                                    putI32(framePtr + 64L, getI64(framePtr + 40L) >>> 4);
                                    putU32(framePtr + 68L, getU32(framePtr + 44L) >> 4);
                                    step = 3;
                                    continue stepLoop;
                                }
                            } else {
                                putI64(framePtr + 32L, (((intVal & 0xffffffffL)) << 32) | (getI64(framePtr + 32L) & 0xffffffffL));
                                step = 3;
                                continue stepLoop;
                            }
                        }
                    }
                    case 1: {
                        index2 = getI32(framePtr + 36L);
                        if ((long) (int) resultPtr == 0L) {
                            if (getI32(framePtr + 36L) == 0L) {
                                putI64(framePtr + 24L, 0x785398163397448L);
                                putI64(framePtr + 16L, 0x3096156608458199L);
                                putU32(framePtr + 68L, (((getU32(framePtr + 68L) << 32) | (getI32(framePtr + 64L) & 0xffffffffL))) >>> 28);
                                putI32(framePtr + 64L, (((((getI32(framePtr + 64L) & 0xffffffffL)) << 32) | (getI32(framePtr + 60L) & 0xffffffffL))) >>> 28);
                                putI32(framePtr + 60L, (((((getI32(framePtr + 60L) & 0xffffffffL)) << 32) | (getI64(framePtr + 56L) & 0xffffffffL))) >>> 28);
                                putI32(framePtr + 56L, getI64(framePtr + 56L) << 4);
                                step = 5;
                                continue stepLoop;
                            } else {
                                index1 = (long) (int) (getI32(framePtr + 36L) + 1L);
                                putI64(framePtr + 32L, (((index1 & 0xffffffffL)) << 32) | (getI64(framePtr + 32L) & 0xffffffffL));
                                if (-2L < index2) {
                                    putI64(framePtr + 24L, 0x785398163397448L);
                                    putI64(framePtr + 16L, 0x3096156608458199L);
                                    step = 5;
                                    continue stepLoop;
                                } else {
                                    putI64(framePtr + 40L, (getU32(framePtr + 68L) << 32) | (getI32(framePtr + 64L) & 0xffffffffL));
                                    index1 = (long) (int) (index1 - 1L);
                                    putI64(framePtr + 32L, (((index1 & 0xffffffffL)) << 32) | (getI64(framePtr + 32L) & 0xffffffffL));
                                    step = 4;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            if (-1L < getI64(framePtr + 32L)) {
                                putI64(framePtr + 24L, 0x450000000000000L);
                                putI64(framePtr + 16L, 0L);
                                step = 5;
                                continue stepLoop;
                            } else {
                                putI64(framePtr + 40L, (getU32(framePtr + 68L) << 32) | (getI32(framePtr + 64L) & 0xffffffffL));
                                step = 6;
                                continue stepLoop;
                            }
                        }
                    }
                    case 2: {
                        if (-1L < index2) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            step = 1;
                            continue stepLoop;
                        }
                    }
                    case 3: {
                        putI32(framePtr + 56L, 0L);
                        tempVal5 = getI64(framePtr + 56L) & 0xffffffffL;
                        index1 = getI32(framePtr + 60L);
                        index2 = getI32(framePtr + 64L);
                        tempVal2 = getU32(framePtr + 68L);
                        dataPtr2 = ADDR_RODATA + 5920L;
                        if ((long) (int) resultPtr != 0L) {
                            dataPtr2 = ADDR_RODATA + 5936L;
                        }
                        do {
                            do {
                                putU32(framePtr + 68L, tempVal2);
                                putI32(framePtr + 64L, index2);
                                putI32(framePtr + 60L, index1);
                                putI32(framePtr + 56L, tempVal5);
                                isZero = dcbsub128(dataPtr2, framePtr + 56L) != 0L ? 1L : 0L;
                                bcdAcc2 = getI64(framePtr + 32L);
                                tempVal5 = getI64(framePtr + 56L) & 0xffffffffL;
                                index1 = getI32(framePtr + 60L);
                                index2 = getI32(framePtr + 64L);
                                tempVal2 = getU32(framePtr + 68L);
                            } while (isZero == 0L);
                            intVal = getI32(framePtr + 36L);
                            tempVal2 = ((((getU32(framePtr + 68L) << 32) | (getI32(framePtr + 64L) & 0xffffffffL))) >>> 28) & 0xffffffffL;
                            index2 = (long) (int) ((((((getI32(framePtr + 64L) & 0xffffffffL)) << 32) | (getI32(framePtr + 60L) & 0xffffffffL))) >>> 28);
                            index1 = (long) (int) ((((((getI32(framePtr + 60L) & 0xffffffffL)) << 32) | (getI64(framePtr + 56L) & 0xffffffffL))) >>> 28);
                            putI64(framePtr + 32L, ((((getI32(framePtr + 36L) - 1L) & 0xffffffffL)) << 32) | (getI64(framePtr + 32L) & 0xffffffffL));
                            tempVal5 = (getI64(framePtr + 56L) << 4) & 0xffffffffL;
                        } while (0L < intVal);
                        putI64(framePtr + 32L, bcdAcc2 & 0xffffffffL);
                        if ((long) (int) resultPtr == 0L) {
                            putU32(framePtr + 68L, getU32(framePtr + 68L) & 0xfffffffL);
                            dataPtr2 = ADDR_RODATA + 5952L;
                        } else {
                            dataPtr2 = ADDR_RODATA + 5968L;
                            putI32(framePtr + 56L, getI64(framePtr + 56L) << 4);
                            putI32(framePtr + 60L, index1);
                            putI32(framePtr + 64L, index2);
                            putU32(framePtr + 68L, tempVal2);
                        }
                        isZero = dcbsub128(dataPtr2, framePtr + 56L) != 0L ? 1L : 0L;
                        while (isZero == 0L) {
                            tempVal2 = ((tempVal6 ^ 9L)) & 0xffffffffL;
                            tempVal5 = ((tempVal6 ^ 13L)) & 0xffffffffL;
                            tempVal6 = tempVal2;
                            if ((tempVal2 & 1L) != 0L) {
                                tempVal6 = tempVal5;
                            }
                            isZero = dcbsub128(dataPtr2, framePtr + 56L) != 0L ? 1L : 0L;
                        }
                        step = 1;
                        continue stepLoop;
                    }
                    case 4: {
                        if (index1 < -8L) {
                            putI64(angleMode + 8L, getI64(framePtr + 40L));
                            putI64(angleMode, getI64(framePtr + 32L));
                            if ((tempVal6 & 1L) != 0L) {
                                constPtr1 = CC_1;
                                dataPtr1 = angleMode;
                                flag = fdiv(constPtr1, dataPtr1, angleMode) & 0xffL;
                            } else {
                                flag = 0L;
                            }
                        } else {
                            if (index1 == -1L) {
                                putI64(framePtr + 40L, getI64(framePtr + 40L) << 4);
                                index1 = -2L;
                                putI64(framePtr + 32L, (0xfffffffeL << 32) | (getI64(framePtr + 32L) & 0xffffffffL));
                            }
                            tempVal2 = 0L;
                            index3 = (long) (int) (index1 + 8L);
                            bcdAcc2 = getI64(framePtr + 40L);
                            do {
                                tempVal4 = getI64(ADDR_RODATA + 5984L + index3 * 8L);
                                bcdAcc1 = bcdAcc2;
                                while (Long.compareUnsigned(tempVal4, bcdAcc1) <= 0L) {
                                    bcdAcc1 = dcbSubAdjust(tempVal4, bcdAcc1);
                                    tempVal2 = (tempVal2 + 0x10000000L) & 0xffffffffL;
                                }
                                tempVal2 = (tempVal2 >> 4) & 0xffffffffL;
                                index2 = (long) (int) index3;
                                index3 = index3 - 1L;
                                bcdAcc2 = bcdAcc1 << 4;
                            } while (0L < index2);
                            putI64(framePtr + 24L, bcdAcc1 & 0xfffffffffffffffL);
                            bcdAcc3 = 0x100000000000000L;
                            putI32(framePtr + 64L, 0L);
                            putU32(framePtr + 68L, 0x1000000L);
                            putI64(framePtr + 16L, (56L << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                            if (tempVal2 == 0L) {
                                tempVal2 = 0xfffffff8L;
                            } else {
                                bcdAcc2 = 56L;
                                bcdAcc3 = 0x100000000000000L;
                                do {
                                    putI64(framePtr + 24L, getI64(framePtr + 24L) >>> 4);
                                    tempVal5 = tempVal2;
                                    tempVal3 = bcdAcc2 & 0xffffffffL;
                                    if (((tempVal2 >> 24) & 0xffffffffL) != 0L) {
                                        while (true) {
                                            tempVal2 = (tempVal2 - 0x1000000L) & 0xffffffffL;
                                            bcdAcc3 = dcbSubAdjust(getI64(framePtr + 24L) >>> (int) (bcdAcc2 & 63L), bcdAcc3);
                                            putI64(framePtr + 24L, dcbAddAdjust((getU32(framePtr + 68L) << 32) | (getI32(framePtr + 64L) & 0xffffffffL), getI64(framePtr + 24L)));
                                            putI32(framePtr + 64L, bcdAcc3);
                                            putU32(framePtr + 68L, bcdAcc3 >>> 32);
                                            tempVal5 = tempVal2;
                                            tempVal3 = getI32(framePtr + 20L) & 0xffffffffL;
                                            if (((tempVal2 >> 24) & 0xffffffffL) == 0L) {
                                                break;
                                            }
                                            bcdAcc2 = getI32(framePtr + 20L);
                                        }
                                    }
                                    tempVal2 = (tempVal5 << 4) & 0xffffffffL;
                                    tempVal1 = (tempVal3 - 8L) & 0xffffffffL;
                                    bcdAcc2 = tempVal1;
                                    putI64(framePtr + 16L, (tempVal1 << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                                } while (tempVal5 != 0L);
                                tempVal2 = (tempVal3 - 1L) & 0xffffffffL;
                                if (-1L < (long) (int) tempVal1) {
                                    tempVal2 = tempVal1;
                                }
                                tempVal2 = ((((long) (int) tempVal2 >> 3) ^ 0xffffffffL)) & 0xffffffffL;
                            }
                            bcdAcc3 = dcbSubAdjust(1L, bcdAcc3);
                            putI32(framePtr + 64L, bcdAcc3);
                            putU32(framePtr + 68L, bcdAcc3 >>> 32);
                            putI64(framePtr + 16L, (tempVal2 << 32) | 1L);
                            putI32(framePtr + 56L, 1L);
                            putI32(framePtr + 60L, 0L);
                            if ((tempVal6 & 1L) == 0L) {
                                dataPtr1 = framePtr + 56L;
                            } else {
                                constPtr1 = framePtr + 56L;
                            }
                            flag = fdiv(constPtr1, dataPtr1, angleMode) & 0xffL;
                        }
                        isZero = (flag & 254L) == 4L ? 1L : 0L;
                        if (((((tempVal6 & 0xffffffffL)) >> 1) & 1L) == 0L) {
                            tempVal2 = tempVal6 & 0xffffffffL;
                            if (isZero != 0L) {
                                putI64(angleMode + 8L, 0x999999999999999L);
                                putI64(angleMode, 0xc34f00000002L);
                                if (((((tempVal6 & 0xffffffffL)) >> 3) & 1L) != 0L) {
                                    result = 0L;
                                    return result;
                                }
                                isZero = fZero(angleMode) != 0L ? 1L : 0L;
                                result = 0L;
                                if (isZero != 0L) {
                                    return result;
                                }
                                putI32(angleMode, -getI64(angleMode));
                                return result;
                            }
                        } else {
                            tempVal2 = ((tempVal6 ^ 4L)) & 0xffffffffL;
                            if ((isZero & (((tempVal6 & 0xffffffffL)) >> 5) & 0xffffffffL) == 0L) {
                                tempVal2 = tempVal6 & 0xffffffffL;
                            }
                            fimul(angleMode, angleMode, angleMode);
                            fiadd(angleMode, CC_1, angleMode, 1L);
                            fisqrt(angleMode, angleMode);
                            fidiv(CC_1, angleMode, angleMode);
                        }
                        if (((tempVal2 >> 4) & 1L) == 0L) {
                            if ((getI64(angleMode + 8L) & 3840L) < 1280L) {
                                putU32(angleMode + 8L, getI64(angleMode + 8L) & 0xfffff000L);
                                result = 0L;
                            } else {
                                bcdAcc2 = dcbAddAdjust(getI64(angleMode + 8L) & -4096L, 4096L);
                                putI64(angleMode + 8L, bcdAcc2);
                                result = 0L;
                                if (((bcdAcc2 >>> 60) & 0xffffffffL) != 0L) {
                                    putI64(angleMode + 8L, bcdAcc2 >>> 4);
                                    putI32(angleMode + 4L, getI32(angleMode + 4L) + 1L);
                                    result = 0L;
                                }
                            }
                        } else {
                            result = 1L;
                        }
                        if ((~tempVal2 & 6L) != 0L && (tempVal2 & 10L) != 8L || (long) (int) comma(isZero = fZero(angleMode) != 0L ? 1L : 0L, isZero) != 0L) {
                            return result;
                        }
                        putI32(angleMode, -getI64(angleMode));
                        return result;
                    }
                    case 5: {
                        isZero = dcbsub128(framePtr + 16L, framePtr + 56L) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            dcbsub128(framePtr + 56L, framePtr + 16L);
                            tempVal6 = ((tempVal6 ^ 1L)) & 0xffffffffL;
                            putI32(framePtr + 56L, getI64(framePtr + 16L));
                            putI32(framePtr + 60L, getI32(framePtr + 20L));
                            putI32(framePtr + 64L, getI64(framePtr + 24L));
                            putU32(framePtr + 68L, getU32(framePtr + 28L));
                        }
                        if ((getI32(framePtr + 60L) != 0L || (getI64(framePtr + 56L) & 0xffffffffL) != 0L || getI32(framePtr + 64L) != 0L || getU32(framePtr + 68L) != 0L) && (getU32(framePtr + 68L) & 0xf000000L) == 0L) {
                            index2 = getI32(framePtr + 36L);
                            do {
                                tempVal2 = getU32(framePtr + 68L) & 0xf00000L;
                                putU32(framePtr + 68L, (((getU32(framePtr + 68L) << 32) | (getI32(framePtr + 64L) & 0xffffffffL))) >>> 28);
                                putI32(framePtr + 64L, (((((getI32(framePtr + 64L) & 0xffffffffL)) << 32) | (getI32(framePtr + 60L) & 0xffffffffL))) >>> 28);
                                putI32(framePtr + 60L, (((((getI32(framePtr + 60L) & 0xffffffffL)) << 32) | (getI64(framePtr + 56L) & 0xffffffffL))) >>> 28);
                                putI32(framePtr + 56L, getI64(framePtr + 56L) << 4);
                                index2 = (long) (int) (index2 - 1L);
                            } while (tempVal2 == 0L);
                            putI64(framePtr + 32L, (((index2 & 0xffffffffL)) << 32) | (getI64(framePtr + 32L) & 0xffffffffL));
                        }
                        putI64(framePtr + 40L, (getU32(framePtr + 68L) << 32) | (getI32(framePtr + 64L) & 0xffffffffL));
                        if ((long) (int) resultPtr != 0L) {
                            step = 6;
                            continue stepLoop;
                        } else {
                            index1 = getI32(framePtr + 36L);
                            index1 = (long) (int) (index1 - 1L);
                            putI64(framePtr + 32L, (((index1 & 0xffffffffL)) << 32) | (getI64(framePtr + 32L) & 0xffffffffL));
                            step = 4;
                            continue stepLoop;
                        }
                    }
                    case 6: {
                        if (((((tempVal6 & 0xffffffffL)) >> 1) & 1L) == 0L) {
                            constPtr2 = CC_45;
                            step = 8;
                            continue stepLoop;
                        } else {
                            if ((tempVal6 & 1L) != 0L) {
                                constPtr2 = CC_30;
                                step = 8;
                                continue stepLoop;
                            } else {
                                step = 7;
                                continue stepLoop;
                            }
                        }
                    }
                    case 7: {
                        putI64(framePtr + 32L, ((((getI32(framePtr + 36L) - 1L) & 0xffffffffL)) << 32) | (getI64(framePtr + 32L) & 0xffffffffL));
                        fmul(framePtr + 32L, CC_PIDiv180, framePtr + 32L);
                        index1 = getI32(framePtr + 36L);
                        if (getI64(framePtr + 40L) != 0L) {
                            index1 = (long) (int) (getI32(framePtr + 36L) + 2L);
                            putI64(framePtr + 32L, (((index1 & 0xffffffffL)) << 32) | (getI64(framePtr + 32L) & 0xffffffffL));
                        }
                        step = 4;
                        continue stepLoop;
                    }
                    case 8: {
                        isZero = fEQ(framePtr + 32L, constPtr2) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            tempVal6 = tempVal6 & 0xffffffefL;
                        }
                        step = 7;
                        continue stepLoop;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long fiatrig(long valPtr, long angleMode, long atrigOp, long resultPtr) {
        long isZero1 = 0L;
        long isZero2 = 0L;
        long bcdAcc1 = 0L;
        long exponent = 0L;
        long intVal = 0L;
        long constPtr = 0L;
        long index = 0L;
        long result = 0L;
        long bcdAcc2 = 0L;
        long tempVal1 = 0L;
        long tempVal2 = 0L;
        long dataPtr = 0L;
        long tempVal3 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(240);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        exponent = getU32(valPtr);
                        if (exponent == 0L) {
                            tempVal3 = getI64(valPtr);
                            result = 0L;
                            putI64(angleMode + 8L, getI64(valPtr + 8L));
                            putI64(angleMode, tempVal3);
                            return result;
                        }
                        tempVal1 = (((1L < (long) (int) atrigOp ? 1L : 0L) ^ atrigOp)) & 0xffffffffL;
                        if (((((atrigOp & 0xffffffffL)) >> 1) & 1L) != 0L) {
                            if (!(((exponent + 1L) & 0xffffffffL) < 5L && ((1L << (int) ((exponent + 1L) & 31L)) & 21L) != 0L && (long) (int) getU32(valPtr + 4L) < 1L && (getU32(valPtr + 4L) != 0L || getU32(valPtr + 12L) == 0L || getI64(valPtr + 8L) == 0x100000000000000L))) {
                                constPtr = CC_NaN;
                                result = 12L;
                                tempVal3 = getI64(constPtr);
                                putI64(angleMode + 8L, getI64(constPtr + 8L));
                                putI64(angleMode, tempVal3);
                                return result;
                            }
                            step = 1;
                            continue stepLoop;
                        } else {
                            if (exponent != 2L && exponent != 0xfffffffeL) {
                                step = 1;
                                continue stepLoop;
                            } else {
                                if ((tempVal1 & 1L) == 0L) {
                                    index = 0L;
                                    if (exponent != 2L) {
                                        index = 180L;
                                    }
                                } else {
                                    index = 90L;
                                    if (exponent == 0xfffffffeL) {
                                        index = 0xffffffa6L;
                                    }
                                }
                                putU32(angleMode + 4L, index);
                                isZero2 = 1L;
                                if (getU32(valPtr + 4L) != 0L || getI64(valPtr + 8L) != 0x100000000000000L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    step = 3;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 1: {
                        isZero2 = 0L;
                        if (getU32(valPtr + 4L) == 0L && getI64(valPtr + 8L) == 0x100000000000000L) {
                            step = 3;
                            continue stepLoop;
                        } else {
                            step = 2;
                            continue stepLoop;
                        }
                    }
                    case 2: {
                        isZero1 = fZero(valPtr) != 0L ? 1L : 0L;
                        if (isZero1 != 0L) {
                            switch ((int) (atrigOp)) {
                                case 0: case 3: {
                                    putU32(angleMode + 4L, 0L);
                                    break;
                                }
                                case 1: case 2: {
                                    putU32(angleMode + 4L, 90L);
                                    break;
                                }
                                default: {
                                    break;
                                }
                            }
                            step = 5;
                            continue stepLoop;
                        } else {
                            if (isZero2 != 0L) {
                                step = 5;
                                continue stepLoop;
                            } else {
                                if ((long) (int) resultPtr == 0L || ((((atrigOp & 0xffffffffL)) >> 1) & 1L) == 0L || getU32(valPtr + 4L) != 0xffffffffL || getI64(valPtr + 8L) != 0x500000000000000L) {
                                    tempVal1 = ((tempVal1 | 4L)) & 0xffffffffL;
                                }
                                if ((long) (int) getU32(valPtr) < 0L) {
                                    if ((tempVal1 & 1L) == 0L) {
                                        tempVal1 = ((tempVal1 | 8L)) & 0xffffffffL;
                                    } else {
                                        tempVal1 = (((tempVal1 & 0xffffffe6L) | 16L)) & 0xffffffffL;
                                    }
                                }
                                putI64(framePtr + 40L, getI64(valPtr + 8L));
                                putI64(framePtr + 32L, getI64(valPtr));
                                if (((tempVal1 >> 1) & 1L) != 0L) {
                                    fadd(framePtr + 32L, CC_1, framePtr + 16L, 1L);
                                    putI64(framePtr + 64L, 0x100000000000000L);
                                    putU32(framePtr + 56L, 1L);
                                    putU32(framePtr + 60L, 0L);
                                    isZero2 = fZero(framePtr + 56L) != 0L ? 1L : 0L;
                                    if (isZero2 == 0L) {
                                        putU32(framePtr + 56L, -getU32(framePtr + 56L));
                                    }
                                    fadd(framePtr + 32L, framePtr + 56L, framePtr + 32L, 1L);
                                    fmul(framePtr + 16L, framePtr + 32L, framePtr + 32L);
                                    if ((getI64(framePtr + 32L) & 0xffffffffL) == 3L) {
                                        putI64(framePtr + 32L, (((getI32(framePtr + 36L) & 0xffffffffL)) << 32) | 1L);
                                    } else {
                                        if ((long) (int) getI64(framePtr + 32L) < 0L) {
                                            putI64(framePtr + 32L, (((getI32(framePtr + 36L) & 0xffffffffL)) << 32) | (-getI64(framePtr + 32L) & 0xffffffffL));
                                        }
                                    }
                                    fisqrt(framePtr + 32L, framePtr + 32L);
                                    fdiv(valPtr, framePtr + 32L, framePtr + 32L);
                                    if ((getI64(framePtr + 32L) & 0xffffffffL) == 3L) {
                                        intVal = 1L;
                                        putI64(framePtr + 32L, (((getI32(framePtr + 36L) & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
                                    } else {
                                        if (-1L >= (long) (int) getI64(framePtr + 32L)) {
                                            intVal = (long) (int) -getI64(framePtr + 32L);
                                            putI64(framePtr + 32L, (((getI32(framePtr + 36L) & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
                                        }
                                    }
                                }
                                if (Long.compareUnsigned(-1L, getI64(framePtr + 32L)) < 0L) {
                                    fdiv(CC_1, framePtr + 32L, framePtr + 32L);
                                    tempVal1 = ((tempVal1 ^ 1L)) & 0xffffffffL;
                                }
                                isZero2 = fEQ(framePtr + 32L, CC_1) != 0L ? 1L : 0L;
                                if (isZero2 != 0L) {
                                    tempVal1 = ((tempVal1 ^ 1L)) & 0xffffffffL;
                                    putU32(angleMode + 8L, 0x63397448L);
                                    putU32(angleMode + 12L, 0x7853981L);
                                    putU32(angleMode, 1L);
                                    putU32(angleMode + 4L, 0xffffffffL);
                                } else {
                                    if (getI32(framePtr + 36L) < -7L) {
                                        putI64(angleMode + 8L, getI64(framePtr + 40L));
                                        putI64(angleMode, getI64(framePtr + 32L));
                                    } else {
                                        tempVal2 = 0L;
                                        exponent = (getI32(framePtr + 36L) + 15L) & 0xffffffffL;
                                        index = ~getI32(framePtr + 36L) & 0xffffffffL;
                                        bcdAcc1 = 0x100000000000000L;
                                        do {
                                            if (Long.compareUnsigned(bcdAcc1, getI64(framePtr + 40L)) <= 0L) {
                                                do {
                                                    tempVal2 = (tempVal2 + (1L << (int) (((exponent & 7L)) << 2))) & 0xffffffffL;
                                                    bcdAcc2 = getI64(framePtr + 40L) >>> (int) ((index * 8L + 8L) & 63L);
                                                    putI64(framePtr + 40L, dcbSubAdjust(bcdAcc1, getI64(framePtr + 40L)));
                                                    bcdAcc1 = dcbAddAdjust(bcdAcc1, bcdAcc2);
                                                } while (Long.compareUnsigned(bcdAcc1, getI64(framePtr + 40L)) <= 0L);
                                            }
                                            exponent = (exponent - 1L) & 0xffffffffL;
                                            index = (index + 1L) & 0xffffffffL;
                                            putI64(framePtr + 40L, getI64(framePtr + 40L) << 4);
                                        } while (exponent != 7L);
                                        bcdAcc1 = divLoop((framePtr + 32L) | 8L, bcdAcc1);
                                        if (tempVal2 == 0L) {
                                            exponent = 0xfffffff8L;
                                        } else {
                                            exponent = 0xfffffff8L;
                                            dataPtr = ADDR_RODATA + 5984L;
                                            do {
                                                bcdAcc2 = dcbMul(getI64(dataPtr), tempVal2 & 15L);
                                                bcdAcc1 = dcbAddAdjust(bcdAcc1, bcdAcc2);
                                                bcdAcc1 = bcdAcc1 >>> 4;
                                                tempVal2 = (tempVal2 >> 4) & 0xffffffffL;
                                                exponent = (exponent + 1L) & 0xffffffffL;
                                                dataPtr = dataPtr + 8L;
                                            } while (tempVal2 != 0L);
                                        }
                                        putU32(angleMode, 1L);
                                        putU32(angleMode + 4L, exponent);
                                        putI64(angleMode + 8L, bcdAcc1);
                                    }
                                }
                                fNorm(0L, angleMode);
                                if ((tempVal1 & 1L) == 0L) {
                                    if (((tempVal1 >> 4) & 1L) == 0L) {
                                        step = 10;
                                        continue stepLoop;
                                    } else {
                                        step = 11;
                                        continue stepLoop;
                                    }
                                } else {
                                    putU32(angleMode, 0xffffffffL);
                                    fadd(angleMode, CC_PIdiv2, angleMode, 3L);
                                    if (((tempVal1 >> 4) & 1L) != 0L) {
                                        step = 11;
                                        continue stepLoop;
                                    } else {
                                        step = 10;
                                        continue stepLoop;
                                    }
                                }
                            }
                        }
                    }
                    case 3: {
                        switch ((int) (atrigOp)) {
                            case 0: {
                                if (exponent == 1L) {
                                    step = 4;
                                    continue stepLoop;
                                } else {
                                    exponent = 0xffffffd3L;
                                    putU32(angleMode + 4L, exponent);
                                    isZero2 = 1L;
                                    step = 2;
                                    continue stepLoop;
                                }
                            }
                            case 1: {
                                if (exponent == 1L) {
                                    step = 4;
                                    continue stepLoop;
                                } else {
                                    exponent = 135L;
                                    putU32(angleMode + 4L, exponent);
                                    isZero2 = 1L;
                                    step = 2;
                                    continue stepLoop;
                                }
                            }
                            case 2: {
                                if (exponent != 1L) {
                                    exponent = 180L;
                                    putU32(angleMode + 4L, exponent);
                                    isZero2 = 1L;
                                } else {
                                    putU32(angleMode + 4L, 0L);
                                    isZero2 = 1L;
                                }
                                step = 2;
                                continue stepLoop;
                            }
                            case 3: {
                                if (exponent == 1L) {
                                    exponent = 90L;
                                } else {
                                    exponent = 0xffffffa6L;
                                }
                                putU32(angleMode + 4L, exponent);
                                isZero2 = 1L;
                                step = 2;
                                continue stepLoop;
                            }
                            default: {
                                isZero2 = 1L;
                                step = 2;
                                continue stepLoop;
                            }
                        }
                    }
                    case 4: {
                        exponent = 45L;
                        putU32(angleMode + 4L, exponent);
                        isZero2 = 1L;
                        step = 2;
                        continue stepLoop;
                    }
                    case 5: {
                        if ((long) (int) resultPtr == 2L) {
                            exponent = getU32(angleMode + 4L);
                            result = 0L;
                            if ((long) (int) exponent >= 45L) {
                                if ((long) (int) exponent < 135L) {
                                    if (exponent == 45L) {
                                        constPtr = CC_50;
                                    } else {
                                        if (exponent != 90L) {
                                            return result;
                                        }
                                        constPtr = CC_100;
                                    }
                                } else {
                                    if (exponent == 135L) {
                                        constPtr = CC_150;
                                    } else {
                                        if (exponent != 180L) {
                                            return result;
                                        }
                                        constPtr = CC_200;
                                    }
                                }
                                result = 0L;
                                tempVal3 = getI64(constPtr);
                                putI64(angleMode + 8L, getI64(constPtr + 8L));
                                putI64(angleMode, tempVal3);
                                return result;
                            }
                            if (exponent == 0xffffffa6L) {
                                constPtr = CC_100;
                                step = 7;
                                continue stepLoop;
                            } else {
                                if (exponent != 0xffffffd3L) {
                                    step = 6;
                                    continue stepLoop;
                                } else {
                                    constPtr = CC_50;
                                    step = 7;
                                    continue stepLoop;
                                }
                            }
                        } else {
                            if ((long) (int) resultPtr == 0L) {
                                exponent = getU32(angleMode + 4L);
                                result = 0L;
                                if (44L < (long) (int) exponent) {
                                    if ((long) (int) exponent < 135L) {
                                        if (exponent == 45L) {
                                            constPtr = CC_PIdiv4;
                                        } else {
                                            if (exponent != 90L) {
                                                return result;
                                            }
                                            constPtr = CC_PIdiv2;
                                        }
                                    } else {
                                        if (exponent == 135L) {
                                            constPtr = CC_3PIdiv4;
                                        } else {
                                            if (exponent != 180L) {
                                                return result;
                                            }
                                            constPtr = CC_PI;
                                        }
                                    }
                                    tempVal3 = getI64(constPtr);
                                    result = 1L;
                                    putI64(angleMode + 8L, getI64(constPtr + 8L));
                                    putI64(angleMode, tempVal3);
                                    return result;
                                }
                                if (exponent == 0xffffffa6L) {
                                    constPtr = CC_PIdiv2;
                                    step = 8;
                                    continue stepLoop;
                                } else {
                                    if (exponent != 0xffffffd3L) {
                                        step = 6;
                                        continue stepLoop;
                                    } else {
                                        constPtr = CC_PIdiv4;
                                        step = 8;
                                        continue stepLoop;
                                    }
                                }
                            } else {
                                exponent = getU32(angleMode + 4L);
                                result = 0L;
                                if ((long) (int) exponent >= 45L) {
                                    if ((long) (int) exponent < 135L) {
                                        if (exponent == 45L) {
                                            constPtr = CC_45;
                                        } else {
                                            if (exponent != 90L) {
                                                return result;
                                            }
                                            constPtr = CC_90;
                                        }
                                    } else {
                                        if (exponent == 135L) {
                                            constPtr = CC_135;
                                        } else {
                                            if (exponent != 180L) {
                                                return result;
                                            }
                                            constPtr = CC_180;
                                        }
                                    }
                                    result = 0L;
                                    tempVal3 = getI64(constPtr);
                                    putI64(angleMode + 8L, getI64(constPtr + 8L));
                                    putI64(angleMode, tempVal3);
                                    return result;
                                }
                                if (exponent == 0xffffffa6L) {
                                    constPtr = CC_90;
                                    step = 7;
                                    continue stepLoop;
                                } else {
                                    if (exponent != 0xffffffd3L) {
                                        step = 6;
                                        continue stepLoop;
                                    } else {
                                        constPtr = CC_45;
                                        step = 7;
                                        continue stepLoop;
                                    }
                                }
                            }
                        }
                    }
                    case 6: {
                        result = 0L;
                        if (exponent != 0L) {
                            return result;
                        }
                        constPtr = CC_0;
                        result = 0L;
                        tempVal3 = getI64(constPtr);
                        putI64(angleMode + 8L, getI64(constPtr + 8L));
                        putI64(angleMode, tempVal3);
                        return result;
                    }
                    case 7: {
                        tempVal3 = getI64(constPtr);
                        putI64(angleMode + 8L, getI64(constPtr + 8L));
                        putI64(angleMode, tempVal3);
                        isZero2 = fZero(angleMode) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            result = 0L;
                            return result;
                        }
                        result = 0L;
                        exponent = getU32(angleMode);
                        putU32(angleMode, -exponent);
                        return result;
                    }
                    case 8: {
                        tempVal3 = getI64(constPtr);
                        putI64(angleMode + 8L, getI64(constPtr + 8L));
                        putI64(angleMode, tempVal3);
                        isZero2 = fZero(angleMode) != 0L ? 1L : 0L;
                        if (isZero2 != 0L) {
                            result = 1L;
                            return result;
                        }
                        exponent = getU32(angleMode);
                        result = 1L;
                        putU32(angleMode, -exponent);
                        return result;
                    }
                    case 9: {
                        if (((tempVal1 >> 2) & 1L) == 0L) {
                            if ((getU32(angleMode + 8L) & 3840L) < 1280L) {
                                result = 0L;
                                putU32(angleMode + 8L, getU32(angleMode + 8L) & 0xfffff000L);
                            } else {
                                bcdAcc1 = dcbAddAdjust(getI64(angleMode + 8L) & -4096L, 4096L);
                                result = 0L;
                                putI64(angleMode + 8L, bcdAcc1);
                                if (((bcdAcc1 >>> 60) & 0xffffffffL) != 0L) {
                                    putI64(angleMode + 8L, bcdAcc1 >>> 4);
                                    putU32(angleMode + 4L, getU32(angleMode + 4L) + 1L);
                                }
                            }
                        } else {
                            result = 1L;
                        }
                        if (((tempVal1 >> 3) & 1L) == 0L || (long) (int) comma(isZero2 = fZero(angleMode) != 0L ? 1L : 0L, isZero2) != 0L) {
                            return result;
                        }
                        exponent = getU32(angleMode);
                        putU32(angleMode, -exponent);
                        return result;
                    }
                    case 10: {
                        if ((long) (int) resultPtr != 1L) {
                            step = 13;
                            continue stepLoop;
                        } else {
                            step = 12;
                            continue stepLoop;
                        }
                    }
                    case 11: {
                        fadd(angleMode, CC_PIdiv2, angleMode, 3L);
                        if ((long) (int) resultPtr == 1L) {
                            step = 12;
                            continue stepLoop;
                        } else {
                            step = 13;
                            continue stepLoop;
                        }
                    }
                    case 12: {
                        constPtr = CC_PIDiv180;
                        fdiv(angleMode, constPtr, angleMode);
                        step = 9;
                        continue stepLoop;
                    }
                    case 13: {
                        if ((long) (int) resultPtr == 2L) {
                            putU32(angleMode + 4L, getU32(angleMode + 4L) + 2L);
                            constPtr = CC_PIdiv2;
                            fdiv(angleMode, constPtr, angleMode);
                        }
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

    long fisincosh(long valPtr, long isCosh, long resultPtr) {
        long result = 0L;
        long intVal = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            intVal = (long) (int) getI64(valPtr);
            if (intVal == -2L) {
                if (isCosh != 0L) {
                    tempVal = getI64(valPtr);
                    putI64(isCosh + 8L, getI64(valPtr + 8L));
                    putI64(isCosh, tempVal);
                }
                result = 0L;
                if (resultPtr != 0L) {
                    putI64(resultPtr + 8L, 0x999999999999999L);
                    putI64(resultPtr, 0xc34f00000002L);
                }
                return result;
            }
            if (intVal == 2L || intVal == 0L) {
                if (isCosh != 0L) {
                    tempVal = getI64(valPtr);
                    putI64(isCosh + 8L, getI64(valPtr + 8L));
                    putI64(isCosh, tempVal);
                }
                result = 0L;
                if (resultPtr != 0L) {
                    tempVal = getI64(valPtr);
                    putI64(resultPtr + 8L, getI64(valPtr + 8L));
                    putI64(resultPtr, tempVal);
                }
                return result;
            }
            putI64(framePtr + 56L, getI64(valPtr + 8L));
            putI64(framePtr + 48L, getI64(valPtr));
            if ((getI64(framePtr + 48L) & 0xffffffffL) == 3L) {
                intVal = 1L;
                tempVal = getI64(framePtr + 48L) >>> 32;
                putI64(framePtr + 48L, (((tempVal & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
            } else {
                if ((long) (int) getI64(framePtr + 48L) < 0L) {
                    intVal = (long) (int) -getI64(framePtr + 48L);
                    tempVal = getI64(framePtr + 48L) >>> 32;
                    putI64(framePtr + 48L, (((tempVal & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
                }
            }
            fexpm(framePtr + 48L, framePtr + 32L, framePtr + 48L);
            fidiv(CC_1, framePtr + 32L, framePtr + 32L);
            firadd(framePtr + 32L, CC_1, framePtr + 16L, 0L);
            fimul(framePtr + 48L, framePtr + 16L, framePtr + 16L);
            fidiv2(framePtr + 16L, framePtr + 16L);
            putI32(framePtr + 16L, getI64(valPtr));
            if (isCosh != 0L) {
                putI64(isCosh + 8L, getI64(framePtr + 24L));
                putI64(isCosh, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
            }
            if (resultPtr != 0L) {
                if ((getI64(framePtr + 16L) & 0xffffffffL) == 3L) {
                    putI32(framePtr + 16L, 1L);
                } else {
                    if ((long) (int) getI64(framePtr + 16L) < 0L) {
                        putI32(framePtr + 16L, -getI64(framePtr + 16L));
                    }
                }
                firadd(framePtr + 32L, framePtr + 16L, resultPtr, 0L);
            }
            result = 1L;
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long fexpm(long valPtr, long isExpm1, long resultPtr) {
        long flag = 0L;
        long mantissa = 0L;
        long index1 = 0L;
        long constPtr = 0L;
        long index2 = 0L;
        long index3 = 0L;
        long index4 = 0L;
        long bcdAcc = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(224);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        mantissa = getI64(valPtr + 8L);
                        putI32(framePtr + 20L, getI64(valPtr) >>> 32);
                        index4 = getI32(framePtr + 20L);
                        putU32(framePtr + 28L, mantissa >>> 32);
                        putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | 1L);
                        index2 = getU32(framePtr + 28L);
                        if (getI64(valPtr) < 0L) {
                            index1 = getI32(framePtr + 20L);
                            index3 = mantissa;
                            if (((getU32(framePtr + 28L) >> 28) & 0xffffffffL) != 0L) {
                                index1 = (long) (int) (getI32(framePtr + 20L) + 1L);
                                putI64(framePtr + 16L, (((index1 & 0xffffffffL)) << 32) | 1L);
                                index3 = mantissa >>> 4;
                                if (-2L < index4) {
                                    index2 = (getU32(framePtr + 28L) >> 4) & 0xffffffffL;
                                    mantissa = mantissa >>> 4;
                                    step = 3;
                                    continue stepLoop;
                                } else {
                                    step = 2;
                                    continue stepLoop;
                                }
                            } else {
                                step = 2;
                                continue stepLoop;
                            }
                        } else {
                            step = 3;
                            continue stepLoop;
                        }
                    }
                    case 1: {
                        if (resultPtr != 0L) {
                            fCordicScale(framePtr + 16L);
                        }
                        mantissa = getI64(framePtr + 24L);
                        if (index2 == 0L && index4 == 0L) {
                            index2 = getU32(valPtr);
                            putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | index2);
                            step = 5;
                            continue stepLoop;
                        } else {
                            index3 = 7L;
                            step = 6;
                            continue stepLoop;
                        }
                    }
                    case 2: {
                        putI64(framePtr + 24L, index3);
                        index4 = 0L;
                        if (index1 < -8L) {
                            index2 = 0L;
                            step = 1;
                            continue stepLoop;
                        } else {
                            index1 = (long) (int) (index1 + 15L);
                            index2 = 0L;
                            if (index1 != -1L) {
                                step = 4;
                                continue stepLoop;
                            } else {
                                step = 1;
                                continue stepLoop;
                            }
                        }
                    }
                    case 3: {
                        putI64(framePtr + 24L, mantissa);
                        index4 = 0L;
                        putI32(framePtr + 40L, 0L);
                        putU32(framePtr + 44L, 0L);
                        putU32(framePtr + 48L, getI64(framePtr + 24L));
                        putU32(framePtr + 52L, index2);
                        do {
                            index4 = (long) (int) (index4 * 10L - 1L);
                            do {
                                flag = dcbsub128(ADDR_RODATA + 6136L, framePtr + 40L) != 0L ? 1L : 0L;
                                index4 = (long) (int) (index4 + 1L);
                            } while (flag == 0L);
                            putU32(framePtr + 52L, (((getU32(framePtr + 52L) << 32) | getU32(framePtr + 48L))) >>> 28);
                            putU32(framePtr + 48L, (((getU32(framePtr + 48L) << 32) | getU32(framePtr + 44L))) >>> 28);
                            putU32(framePtr + 44L, (((getU32(framePtr + 44L) << 32) | (getI32(framePtr + 40L) & 0xffffffffL))) >>> 28);
                            putI32(framePtr + 40L, getI32(framePtr + 40L) << 4);
                            if (49999L < index4) {
                                if ((long) (int) getU32(valPtr) < 1L) {
                                    if (isExpm1 != 0L) {
                                        putI64(isExpm1 + 8L, 0L);
                                        putI64(isExpm1, 1L);
                                    }
                                    flag = 0L;
                                    if (resultPtr == 0L) {
                                        return flag;
                                    }
                                    constPtr = CC_N1;
                                } else {
                                    constPtr = CC_PInf;
                                    if (isExpm1 != 0L) {
                                        putI64(isExpm1 + 8L, 0x999999999999999L);
                                        putI64(isExpm1, 0xc34f00000002L);
                                    }
                                    flag = 0L;
                                    if (resultPtr == 0L) {
                                        return flag;
                                    }
                                }
                                flag = 0L;
                                mantissa = getI64(constPtr);
                                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                                putI64(resultPtr, mantissa);
                                return flag;
                            }
                            index1 = getI32(framePtr + 20L);
                            putI64(framePtr + 16L, ((((getI32(framePtr + 20L) - 1L) & 0xffffffffL)) << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                        } while (0L < index1);
                        putI64(framePtr + 24L, (getU32(framePtr + 52L) << 32) | getU32(framePtr + 48L));
                        index1 = 14L;
                        step = 4;
                        continue stepLoop;
                    }
                    case 4: {
                        index2 = 0L;
                        bcdAcc = index1;
                        while (true) {
                            index3 = getI64(ADDR_RODATA + 6000L + bcdAcc * 8L);
                            index2 = (index2 << 4) & 0xffffffffL;
                            mantissa = getI64(framePtr + 16L);
                            while (comma(putI64(framePtr + 16L, mantissa), Long.compareUnsigned(index3, getI64(framePtr + 24L)) <= 0L ? 1L : 0L) != 0L) {
                                putI64(framePtr + 24L, dcbSubAdjust(index3, getI64(framePtr + 24L)));
                                index2 = (index2 + 1L) & 0xffffffffL;
                                mantissa = getI64(framePtr + 16L);
                            }
                            if (bcdAcc == 7L) {
                                break;
                            }
                            putI32(framePtr + 20L, mantissa >>> 32);
                            putI64(framePtr + 24L, getI64(framePtr + 24L) << 4);
                            bcdAcc = bcdAcc - 1L;
                            putI32(framePtr + 16L, mantissa);
                            putI64(framePtr + 16L, ((((getI32(framePtr + 20L) - 1L) & 0xffffffffL)) << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                        }
                        step = 1;
                        continue stepLoop;
                    }
                    case 5: {
                        putI64(framePtr + 24L, mantissa);
                        if ((long) (int) index2 < 0L) {
                            putI32(framePtr + 20L, getI64(framePtr + 16L) >>> 32);
                            mantissa = getI64(framePtr + 24L) >>> (int) ((getI32(framePtr + 20L) * -4L) & 63L);
                            if (mantissa != 0L) {
                                putI64(framePtr + 24L, divLoop((framePtr + 16L) | 8L, mantissa + 0x100000000000000L));
                            }
                        }
                        if (resultPtr != 0L) {
                            putI64(resultPtr + 8L, getI64(framePtr + 24L));
                            putI64(resultPtr, getI64(framePtr + 16L));
                            if (isExpm1 != 0L) {
                                fadd(resultPtr, CC_1, isExpm1, 2L);
                            }
                            flag = getI64(framePtr + 24L) != 0L ? 1L : 0L;
                        } else {
                            if (isExpm1 == 0L) {
                                flag = 1L;
                                return flag;
                            }
                            if (getI64(framePtr + 24L) != 0L) {
                                fadd(framePtr + 16L, CC_1, isExpm1, 2L);
                                flag = 1L;
                                return flag;
                            }
                            flag = 0L;
                            putI64(isExpm1 + 8L, 0x100000000000000L);
                            putI64(isExpm1, 1L);
                        }
                        return flag;
                    }
                    case 6: {
                        putI64(framePtr + 24L, mantissa);
                        if ((index2 & 15L) != 0L) {
                            index1 = (long) (int) ((index2 & 15L) + 1L);
                            do {
                                bcdAcc = dcbAddAdjust(getI64(framePtr + 24L), getI64(framePtr + 24L) >>> (int) (((index3 & 15L)) << 2));
                                index1 = (long) (int) (index1 - 1L);
                                putI64(framePtr + 24L, bcdAcc + 0x1000000000000000L);
                            } while (1L < index1);
                        }
                        mantissa = getI64(framePtr + 24L) >>> 4;
                        index1 = (long) (int) (getI32(framePtr + 20L) + 1L);
                        putI64(framePtr + 16L, (((index1 & 0xffffffffL)) << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                        if (index3 == 0L) {
                            index2 = getU32(valPtr);
                            putI64(framePtr + 16L, index2);
                            step = 7;
                            continue stepLoop;
                        } else {
                            index2 = (index2 >> 4) & 0xffffffffL;
                            index3 = index3 - 1L;
                            if (index2 != 0L || index4 != 0L) {
                                step = 6;
                                continue stepLoop;
                            } else {
                                index2 = getU32(valPtr);
                                putI64(framePtr + 16L, (((index1 & 0xffffffffL)) << 32) | index2);
                                if ((long) (int) index3 != -1L) {
                                    step = 5;
                                    continue stepLoop;
                                } else {
                                    step = 7;
                                    continue stepLoop;
                                }
                            }
                        }
                    }
                    case 7: {
                        putU32(framePtr + 28L, getI64(framePtr + 24L) >>> 36);
                        putI64(framePtr + 16L, (((index4 & 0xffffffffL)) << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                        putU32(framePtr + 24L, mantissa);
                        putI64(framePtr + 24L, ((((getU32(framePtr + 28L) + 0x1000000L) & 0xffffffffL)) << 32) | (getI64(framePtr + 24L) & 0xffffffffL));
                        if ((long) (int) index2 < 0L) {
                            fdiv(CC_1, framePtr + 16L, framePtr + 16L);
                            putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | 1L);
                        }
                        if (isExpm1 != 0L) {
                            putI64(isExpm1 + 8L, getI64(framePtr + 24L));
                            putI64(isExpm1, getI64(framePtr + 16L));
                        }
                        if (resultPtr == 0L) {
                            flag = 1L;
                            return flag;
                        }
                        fradd(framePtr + 16L, CC_N1, resultPtr, 0L);
                        flag = 1L;
                        return flag;
                    }
                    default: throw new RuntimeException("Invalid step: " + step);
                }
            }
        } finally {
            sp = savedSp;
        }
    }

    long fitanh(long valPtr, long resultPtr) {
        long intVal = 0L;
        long result = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(160);
        try {
            intVal = getI32(valPtr);
            if (intVal == -2L) {
                valPtr = CC_N1;
            } else {
                if (intVal == 2L) {
                    valPtr = CC_1;
                } else {
                    if (intVal != 0L) {
                        fisincosh(valPtr, framePtr + 40L, framePtr + 24L);
                        fidiv(framePtr + 40L, framePtr + 24L, resultPtr);
                        result = 1L;
                        return result;
                    }
                }
            }
            result = 0L;
            tempVal = getI64(valPtr);
            putI64(resultPtr + 8L, getI64(valPtr + 8L));
            putI64(resultPtr, tempVal);
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long fiasinh(long valPtr, long resultPtr) {
        long tempVal1 = 0L;
        long status = 0L;
        long intVal = 0L;
        long tempVal2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            tempVal1 = (getI64(valPtr) + 1L) & 0xffffffffL;
            if (4L < tempVal1 || ((1L << (int) (tempVal1 & 31L)) & 21L) == 0L) {
                putI64(framePtr + 48L, getI64(valPtr + 8L));
                tempVal2 = getI64(valPtr);
                status = 0L;
                putI64(resultPtr + 8L, getI64(framePtr + 48L));
                putI64(resultPtr, tempVal2);
                return status;
            }
            fmul(valPtr, valPtr, framePtr + 40L);
            firadd(framePtr + 40L, CC_1, framePtr + 16L, 0L);
            fisqrt(framePtr + 16L, framePtr + 16L);
            firadd(framePtr + 16L, CC_1, framePtr + 16L, 0L);
            fidiv(framePtr + 40L, framePtr + 16L, framePtr + 40L);
            putI64(framePtr + 24L, getI64(valPtr + 8L));
            putI64(framePtr + 16L, getI64(valPtr));
            if ((getI64(framePtr + 16L) & 0xffffffffL) == 3L) {
                intVal = 1L;
                tempVal2 = getI64(framePtr + 16L) >>> 32;
                putI64(framePtr + 16L, (((tempVal2 & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
            } else {
                if ((long) (int) getI64(framePtr + 16L) < 0L) {
                    intVal = (long) (int) -getI64(framePtr + 16L);
                    tempVal2 = getI64(framePtr + 16L) >>> 32;
                    putI64(framePtr + 16L, (((tempVal2 & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
                }
            }
            firadd(framePtr + 40L, framePtr + 16L, framePtr + 40L, 0L);
            status = 0L;
            if ((((getI64(framePtr + 40L) | 2L)) & 0xffffffffL) != 2L) {
                status = flnp1(framePtr + 40L, framePtr + 40L);
            }
            putI32(framePtr + 40L, getI64(valPtr));
            tempVal2 = (((getI32(framePtr + 44L) & 0xffffffffL)) << 32) | (getI64(framePtr + 40L) & 0xffffffffL);
            putI64(resultPtr + 8L, getI64(framePtr + 48L));
            putI64(resultPtr, tempVal2);
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long filnp1(long valPtr, long resultPtr) {
        long status = 0L;
        long tempVal = 0L;
        if ((((getU32(valPtr) | 2L)) & 0xffffffffL) == 2L) {
            tempVal = getI64(valPtr);
            putI64(resultPtr + 8L, getI64(valPtr + 8L));
            putI64(resultPtr, tempVal);
            return 0L;
        } else {
            status = flnp1(valPtr, resultPtr);
            return status;
        }
    }

    long fiacosh(long valPtr, long resultPtr) {
        long isLess = 0L;
        long status = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            if ((((getU32(valPtr) | 2L)) & 0xffffffffL) == 2L) {
                putI64(framePtr + 48L, getI64(valPtr + 8L));
                tempVal = getI64(valPtr);
                status = 0L;
                putI64(resultPtr + 8L, getI64(framePtr + 48L));
                putI64(resultPtr, tempVal);
                return status;
            }
            isLess = fLT(valPtr, CC_1) != 0L ? 1L : 0L;
            if (isLess == 0L) {
                fradd(valPtr, CC_1, framePtr + 40L, 0L);
                fradd(valPtr, CC_N1, framePtr + 24L, 0L);
                fmul(framePtr + 40L, framePtr + 24L, framePtr + 40L);
                fisqrt(framePtr + 40L, framePtr + 40L);
                firadd(framePtr + 40L, framePtr + 24L, framePtr + 40L, 0L);
                if ((((getI64(framePtr + 40L) | 2L)) & 0xffffffffL) != 2L) {
                    status = flnp1(framePtr + 40L, resultPtr);
                    return status;
                }
                tempVal = (((getI32(framePtr + 44L) & 0xffffffffL)) << 32) | (getI64(framePtr + 40L) & 0xffffffffL);
                status = 0L;
            } else {
                status = 18L;
                putI64(framePtr + 48L, 0L);
                tempVal = 0L;
            }
            putI64(resultPtr + 8L, getI64(framePtr + 48L));
            putI64(resultPtr, tempVal);
            return status;
        } finally {
            sp = savedSp;
        }
    }

    long fiatanh(long valPtr, long resultPtr) {
        long isLess = 0L;
        long result = 0L;
        long intVal = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            if ((long) (int) getI64(valPtr) == 0L) {
                tempVal = getI64(valPtr);
                result = 0L;
                putI64(resultPtr + 8L, getI64(valPtr + 8L));
                putI64(resultPtr, tempVal);
                return result;
            }
            isLess = fLT(valPtr, CC_N1) != 0L ? 1L : 0L;
            if (isLess != 0L || (long) (int) comma(isLess = fLT(CC_1, valPtr) != 0L ? 1L : 0L, isLess) != 0L) {
                result = 18L;
                putI64(resultPtr + 8L, 0L);
                putI64(resultPtr, 0L);
                return result;
            }
            putI64(framePtr + 40L, getI64(valPtr + 8L));
            putI64(framePtr + 32L, getI64(valPtr));
            if ((getI64(framePtr + 32L) & 0xffffffffL) == 3L) {
                intVal = 1L;
                putI32(framePtr + 36L, getI64(framePtr + 32L) >>> 32);
                putI64(framePtr + 32L, (((getI32(framePtr + 36L) & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
            } else {
                if ((long) (int) getI64(framePtr + 32L) < 0L) {
                    intVal = (long) (int) -getI64(framePtr + 32L);
                    putI32(framePtr + 36L, getI64(framePtr + 32L) >>> 32);
                    putI64(framePtr + 32L, (((getI32(framePtr + 36L) & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
                }
            }
            fradd(CC_1, framePtr + 32L, framePtr + 16L, 1L);
            fidiv(framePtr + 32L, framePtr + 16L, framePtr + 32L);
            firadd(framePtr + 32L, framePtr + 32L, framePtr + 32L, 0L);
            if ((((getI64(framePtr + 32L) | 2L)) & 0xffffffffL) != 2L) {
                flnp1(framePtr + 32L, framePtr + 32L);
            }
            putI64(framePtr + 32L, (((getI32(framePtr + 36L) & 0xffffffffL)) << 32) | (getI64(valPtr) & 0xffffffffL));
            result = fidiv2(framePtr + 32L, resultPtr);
            return result;
        } finally {
            sp = savedSp;
        }
    }

    void lnsub(long mantissaPtr) {
        long exponent = 0L;
        long dataPtr = 0L;
        dataPtr = mantissaPtr + 8L;
        exponent = getI64(dataPtr) >>> (int) ((getI32(mantissaPtr + 4L) * -4L) & 63L);
        if (exponent != 0L) {
            exponent = divLoop(dataPtr, exponent + 0x100000000000000L);
            putI64(dataPtr, exponent);
        }
    }

    long filn(long valPtr, long resultPtr) {
        long result = 0L;
        if ((((getU32(valPtr) | 2L)) & 0xffffffffL) == 2L) {
            result = getI64(valPtr);
            putI64(resultPtr + 8L, getI64(valPtr + 8L));
            putI64(resultPtr, result);
            return 0L;
        } else {
            result = filnCore(valPtr, resultPtr, 0L, 0L);
            return result;
        }
    }

    long fln(long valPtr, long resultPtr) {
        long result = 0L;
        result = filnCore(valPtr, resultPtr, 0L, 0L);
        return result;
    }

    long filnCore(long valPtr, long isLn1p, long isBase10, long resultPtr) {
        long tempVal1 = 0L;
        long flag = 0L;
        long isZero = 0L;
        long bcdVal = 0L;
        long bcdAcc = 0L;
        long result = 0L;
        long constPtr = 0L;
        long mantissa = 0L;
        long index1 = 0L;
        long index2 = 0L;
        long index3 = 0L;
        long tempVal2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(208);
        try {
            int step = 0;
            stepLoop: while (true) {
                switch (step) {
                    case 0: {
                        if ((isBase10 & 1L) == 0L) {
                            if ((long) (int) getU32(valPtr) < 0L) {
                                step = 1;
                                continue stepLoop;
                            } else {
                                isZero = fZero(valPtr) != 0L ? 1L : 0L;
                                if (isZero != 0L) {
                                    step = 2;
                                    continue stepLoop;
                                } else {
                                    bcdVal = getU32(valPtr + 4L);
                                    if (bcdVal != 0L) {
                                        tempVal1 = 1L;
                                        if ((long) (int) bcdVal < 0L) {
                                            tempVal1 = 0xffffffffL;
                                        }
                                        putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | tempVal1);
                                        resultPtr = ((((long) (int) bcdVal < 0L ? 1L : 0L) | resultPtr)) & 0xffL;
                                        bcdVal = ((bcdVal ^ ((long) (int) bcdVal >> 31))) & 0xffffffffL;
                                        putI64(framePtr + 24L, dcbSubAdjust(getI64(valPtr + 8L), 0L));
                                        index2 = 0L;
                                        putI64(framePtr + 24L, getI64(framePtr + 24L) & 0xfffffffffffffffL);
                                        index3 = 14L;
                                        step = 3;
                                        continue stepLoop;
                                    } else {
                                        mantissa = getI64(valPtr + 8L);
                                        result = getI64(valPtr);
                                        putI64(framePtr + 16L, result);
                                        if (mantissa == 0x100000000000000L) {
                                            constPtr = CC_0;
                                            result = 0L;
                                            putI64(framePtr + 24L, 0x100000000000000L);
                                            bcdAcc = getI64(constPtr + 8L);
                                            tempVal2 = getI64(constPtr);
                                            putI64(isLn1p + 8L, bcdAcc);
                                            putI64(isLn1p, tempVal2);
                                            return result;
                                        }
                                        putU32(framePtr + 28L, mantissa >>> 32);
                                        putU32(framePtr + 28L, getU32(framePtr + 28L) - 0x1000000L);
                                        putU32(framePtr + 24L, mantissa);
                                        if ((getU32(framePtr + 28L) & 0xf000000L) == 0L) {
                                            putI32(framePtr + 20L, result >>> 32);
                                            do {
                                                bcdAcc = getI64(framePtr + 24L) & 0xf0000000000000L;
                                                putI64(framePtr + 24L, getI64(framePtr + 24L) << 4);
                                                putI32(framePtr + 20L, getI32(framePtr + 20L) - 1L);
                                            } while (bcdAcc == 0L);
                                            putI32(framePtr + 16L, result);
                                        }
                                        fdiv(framePtr + 16L, valPtr, framePtr + 16L);
                                        step = 6;
                                        continue stepLoop;
                                    }
                                }
                            }
                        } else {
                            isZero = fEQ(valPtr, CC_N1) != 0L ? 1L : 0L;
                            if (isZero == 0L) {
                                isZero = fLT(valPtr, CC_N1) != 0L ? 1L : 0L;
                                if (isZero != 0L) {
                                    step = 1;
                                    continue stepLoop;
                                } else {
                                    putI64(framePtr + 24L, getI64(valPtr + 8L));
                                    putI64(framePtr + 16L, getI64(valPtr));
                                    step = 6;
                                    continue stepLoop;
                                }
                            } else {
                                step = 2;
                                continue stepLoop;
                            }
                        }
                    }
                    case 1: {
                        constPtr = CC_NaN;
                        result = 10L;
                        bcdAcc = getI64(constPtr + 8L);
                        tempVal2 = getI64(constPtr);
                        putI64(isLn1p + 8L, bcdAcc);
                        putI64(isLn1p, tempVal2);
                        return result;
                    }
                    case 2: {
                        constPtr = CC_NInf;
                        result = 9L;
                        bcdAcc = getI64(constPtr + 8L);
                        tempVal2 = getI64(constPtr);
                        putI64(isLn1p + 8L, bcdAcc);
                        putI64(isLn1p, tempVal2);
                        return result;
                    }
                    case 3: {
                        index1 = 0L;
                        do {
                            index1 = (index1 << 4) & 0xffffffffL;
                            while (comma(comma(bcdAcc = getI64(framePtr + 24L), putI64(framePtr + 24L, dcbAddAdjust(getI64(framePtr + 24L), getI64(framePtr + 24L) >>> (int) ((index2 << 2) & 63L)))), getI64(framePtr + 24L) >>> 60 != 0L ? 1L : 0L) != 0L) {
                                putI64(framePtr + 24L, getI64(framePtr + 24L) - 0x1000000000000000L);
                                index1 = (index1 + 1L) & 0xffffffffL;
                            }
                            index3 = (long) (int) (index3 - 1L);
                            index2 = (index2 + 1L) & 0xffffffffL;
                            putI64(framePtr + 24L, bcdAcc << 4);
                        } while (index3 != 6L);
                        putI64(framePtr + 16L, (0xfffffff8L << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                        if (bcdVal == 0L) {
                            if (index1 == 0L) {
                                step = 4;
                                continue stepLoop;
                            } else {
                                putI64(framePtr + 24L, bcdAcc & 0xfffffffffffffffL);
                                step = 5;
                                continue stepLoop;
                            }
                        } else {
                            flag = 1L;
                            putI64(framePtr + 24L, bcdAcc & 0xfffffffffffffffL);
                            mantissa = 7L;
                            mantissa = mantissa * 8L - 56L;
                            step = 7;
                            continue stepLoop;
                        }
                    }
                    case 4: {
                        putI64(framePtr + 16L, (0xfffffff7L << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                        fNorm(0L, framePtr + 16L);
                        index1 = 0L;
                        step = 5;
                        continue stepLoop;
                    }
                    case 5: {
                        fCordicScale(framePtr + 16L);
                        bcdVal = 0L;
                        flag = 0L;
                        mantissa = 7L;
                        mantissa = mantissa * 8L - 56L;
                        step = 7;
                        continue stepLoop;
                    }
                    case 6: {
                        if (getI32(framePtr + 20L) < -7L || getI32(framePtr + 20L) == -7L) {
                            fCordicScale(framePtr + 16L);
                            bcdVal = 0L;
                            flag = 0L;
                            index1 = 0L;
                            mantissa = 8L;
                            mantissa = mantissa * 8L - 56L;
                            step = 7;
                            continue stepLoop;
                        } else {
                            if (getI32(framePtr + 20L) != -9L) {
                                bcdVal = 0L;
                                index2 = ~getI32(framePtr + 20L) & 0xffffffffL;
                                index3 = (long) (int) (getI32(framePtr + 20L) + 15L);
                                step = 3;
                                continue stepLoop;
                            } else {
                                putI64(framePtr + 16L, (0xfffffff8L << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                                step = 4;
                                continue stepLoop;
                            }
                        }
                    }
                    case 7: {
                        bcdAcc = dcbMul(getI64(ADDR_RODATA + 6056L + mantissa), index1 & 15L);
                        putI64(framePtr + 24L, dcbAddAdjust(bcdAcc, getI64(framePtr + 24L)));
                        index1 = (index1 >> 4) & 0xffffffffL;
                        putI64(framePtr + 16L, ((((getI32(framePtr + 20L) + 1L) & 0xffffffffL)) << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                        if (((index1 == 0L ? 1L : 0L) & ((flag ^ 1L)) & 0xffL) != 0L) {
                            if ((long) (int) mantissa == 64L) {
                                step = 8;
                                continue stepLoop;
                            } else {
                                step = 10;
                                continue stepLoop;
                            }
                        } else {
                            if (mantissa == 56L) {
                                step = 8;
                                continue stepLoop;
                            } else {
                                putI64(framePtr + 24L, getI64(framePtr + 24L) >>> 4);
                                mantissa = mantissa + 8L;
                                step = 7;
                                continue stepLoop;
                            }
                        }
                    }
                    case 8: {
                        if (Long.compareUnsigned(4L, getI64(framePtr + 24L) & 15L) < 0L) {
                            putI64(framePtr + 24L, dcbAddAdjust(getI64(framePtr + 24L), 16L));
                        }
                        putI64(framePtr + 24L, getI64(framePtr + 24L) >>> 4);
                        if ((resultPtr & 1L) == 0L) {
                            putI64(framePtr + 24L, dcbSubAdjust(getI64(framePtr + 24L), 0x230258509299405L));
                        }
                        bcdVal = bintobcd(bcdVal) & 0xffffffffL;
                        if (bcdVal != 0L) {
                            while (true) {
                                bcdAcc = dcbMul(0x230258509299405L, bcdVal & 15L);
                                putI64(framePtr + 24L, dcbAddAdjust(bcdAcc, getI64(framePtr + 24L)));
                                bcdVal = (bcdVal >> 4) & 0xffffffffL;
                                putI64(framePtr + 16L, ((((getI32(framePtr + 20L) + 1L) & 0xffffffffL)) << 32) | (getI64(framePtr + 16L) & 0xffffffffL));
                                if (bcdVal == 0L) {
                                    break;
                                }
                                putI64(framePtr + 24L, getI64(framePtr + 24L) >>> 4);
                            }
                            step = 10;
                            continue stepLoop;
                        } else {
                            if (((getU32(framePtr + 28L) >> 28) & 0xffffffffL) == 0L) {
                                step = 11;
                                continue stepLoop;
                            } else {
                                step = 12;
                                continue stepLoop;
                            }
                        }
                    }
                    case 9: {
                        tempVal1 = 1L;
                        if ((resultPtr & 1L) != 0L) {
                            tempVal1 = 0xffffffffL;
                        }
                        putI64(framePtr + 16L, (((getI32(framePtr + 20L) & 0xffffffffL)) << 32) | tempVal1);
                        fNorm(0L, framePtr + 16L);
                        result = 1L;
                        tempVal2 = getI64(framePtr + 16L);
                        bcdAcc = getI64(framePtr + 24L);
                        putI64(isLn1p + 8L, bcdAcc);
                        putI64(isLn1p, tempVal2);
                        return result;
                    }
                    case 10: {
                        if (getI64(framePtr + 24L) >>> 60 != 0L) {
                            step = 12;
                            continue stepLoop;
                        } else {
                            step = 11;
                            continue stepLoop;
                        }
                    }
                    case 11: {
                        putI32(framePtr + 20L, getI32(framePtr + 20L) - 1L);
                        step = 9;
                        continue stepLoop;
                    }
                    case 12: {
                        putI64(framePtr + 24L, getI64(framePtr + 24L) >>> 4);
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

    long flnp1(long valPtr, long resultPtr) {
        long flag = 0L;
        long result = 0L;
        long tempVal = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(144);
        try {
            if ((long) (int) getU32(valPtr + 4L) < -1L) {
                if ((long) (int) getU32(valPtr) < 1L) {
                    result = 1L;
                    flag = 1L;
                    result = filnCore(valPtr, resultPtr, result, flag);
                    return result;
                }
                putI64(framePtr + 24L, getI64(valPtr + 8L));
                tempVal = getI64(valPtr);
                putI32(framePtr + 20L, tempVal >>> 32);
                result = getI64(framePtr + 24L) >>> (int) ((getI32(framePtr + 20L) * -4L) & 63L);
                putI64(framePtr + 16L, tempVal);
                if (result != 0L) {
                    putI64(framePtr + 24L, divLoop((framePtr + 16L) | 8L, result + 0x100000000000000L));
                }
                result = 1L;
            } else {
                fadd(valPtr, CC_1, framePtr + 16L, 2L);
                result = 0L;
            }
            flag = 0L;
            valPtr = framePtr + 16L;
            result = filnCore(valPtr, resultPtr, result, flag);
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long fiexpm(long valPtr, long isExpm1, long resultPtr) {
        long intVal = 0L;
        long flag = 0L;
        long tempVal = 0L;
        long mantissa = 0L;
        intVal = (long) (int) getI64(valPtr);
        if (intVal == -2L) {
            if (isExpm1 != 0L) {
                putI64(isExpm1 + 8L, 0L);
                putI64(isExpm1, 1L);
            }
            if (resultPtr == 0L) {
                return 0L;
            }
            mantissa = 0x100000000000000L;
            tempVal = 0xffffffffL;
        } else {
            if (intVal != 2L && intVal != 0L) {
                flag = fexpm(valPtr, isExpm1, resultPtr) != 0L ? 1L : 0L;
                return flag;
            }
            if (isExpm1 != 0L) {
                tempVal = getI64(valPtr);
                putI64(isExpm1 + 8L, getI64(valPtr + 8L));
                putI64(isExpm1, tempVal);
            }
            if (resultPtr == 0L) {
                return 0L;
            }
            mantissa = getI64(valPtr + 8L);
            tempVal = getI64(valPtr);
        }
        putI64(resultPtr + 8L, mantissa);
        putI64(resultPtr, tempVal);
        return 0L;
    }

    void fCordicScale(long workPtr) {
        long isZero = 0L;
        long bcdAcc1 = 0L;
        long bcdAcc2 = 0L;
        long dataPtr = 0L;
        isZero = fZero(workPtr) != 0L ? 1L : 0L;
        if (isZero != 0L) {
            return;
        }
        dataPtr = workPtr + 8L;
        bcdAcc1 = dcbAddAdjust(getI64(dataPtr), getI64(dataPtr));
        bcdAcc1 = dcbAddAdjust(bcdAcc1, bcdAcc1);
        bcdAcc2 = dcbAddAdjust(bcdAcc1, getI64(dataPtr));
        bcdAcc2 = bcdAcc2 >>> (int) ((getI32(workPtr + 4L) * -4L) & 63L);
        if (bcdAcc2 == 0L) {
            return;
        }
        putI32(workPtr, 1L);
        putI32(workPtr + 4L, getI32(workPtr + 4L) + 1L);
        bcdAcc2 = dcbSubAdjust(bcdAcc2, 0L);
        if (Long.compareUnsigned(getI64(workPtr + 8L), bcdAcc2 & 0xfffffffffffffffL) < 0L) {
            putI64(workPtr + 8L, getI64(workPtr + 8L) << 4);
            putI32(workPtr + 4L, getI32(workPtr + 4L) - 1L);
        }
        bcdAcc2 = divLoop(dataPtr, bcdAcc2 & 0xfffffffffffffffL);
        putI64(dataPtr, bcdAcc2);
    }

    long filog(long valPtr, long resultPtr) {
        long isZero = 0L;
        long status = 0L;
        long constPtr = 0L;
        long tempVal = 0L;
        long mantissa = 0L;
        if ((((getU32(valPtr) | 2L)) & 0xffffffffL) == 2L) {
            mantissa = getI64(valPtr + 8L);
            tempVal = getI64(valPtr);
            status = 0L;
            putI64(resultPtr + 8L, mantissa);
            putI64(resultPtr, tempVal);
            return status;
        }
        if ((long) (int) getU32(valPtr) < 0L) {
            constPtr = CC_NaN;
            status = 10L;
            mantissa = getI64(constPtr + 8L);
            tempVal = getI64(constPtr);
        } else {
            isZero = fZero(valPtr) != 0L ? 1L : 0L;
            if (isZero == 0L) {
                isZero = fEQ(valPtr, CC_10) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    filnCore(valPtr, resultPtr, 0L, 0L);
                    status = fdiv(resultPtr, CC_ln10, resultPtr) & 0xffL;
                    return status;
                }
                status = 0L;
                mantissa = 0x100000000000000L;
                tempVal = 1L;
            } else {
                constPtr = CC_NInf;
                status = 9L;
                mantissa = getI64(constPtr + 8L);
                tempVal = getI64(constPtr);
            }
        }
        putI64(resultPtr + 8L, mantissa);
        putI64(resultPtr, tempVal);
        return status;
    }

    long flog(long valPtr, long resultPtr) {
        long isZero = 0L;
        long status = 0L;
        long constPtr = 0L;
        long tempVal = 0L;
        if ((long) (int) getU32(valPtr) < 0L) {
            constPtr = CC_NaN;
            status = 10L;
        } else {
            isZero = fZero(valPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                constPtr = CC_NInf;
                status = 9L;
            } else {
                isZero = fEQ(valPtr, CC_10) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    filnCore(valPtr, resultPtr, 0L, 0L);
                    status = fdiv(resultPtr, CC_ln10, resultPtr) & 0xffL;
                    return status;
                }
                constPtr = CC_1;
                status = 0L;
            }
        }
        tempVal = getI64(constPtr);
        putI64(resultPtr + 8L, getI64(constPtr + 8L));
        putI64(resultPtr, tempVal);
        return status;
    }

    long fpow(long basePtr, long expPtr, long resultPtr, long allowNegBaseOddRoot) {
        long tempVal1 = 0L;
        long isZero = 0L;
        long tempVal2 = 0L;
        long intVal = 0L;
        long result = 0L;
        long exponent = 0L;
        long constPtr = 0L;
        long mantissa1 = 0L;
        long tempVal3 = 0L;
        long mantissa2 = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            if ((allowNegBaseOddRoot & 1L) != 0L) {
                intVal = getI32(basePtr);
                if (intVal == 0L) {
                    constPtr = CC_NaN;
                    result = 0L;
                    tempVal3 = getI64(constPtr);
                    putI64(resultPtr + 8L, getI64(constPtr + 8L));
                    putI64(resultPtr, tempVal3);
                    return result;
                }
                tempVal2 = getU32(expPtr);
                if (tempVal2 == 0xfffffffeL) {
                    isZero = fZero(basePtr) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        constPtr = CC_PInf;
                        result = 0L;
                        tempVal3 = getI64(constPtr);
                        putI64(resultPtr + 8L, getI64(constPtr + 8L));
                        putI64(resultPtr, tempVal3);
                        return result;
                    }
                    if (getI32(basePtr) == -2L) {
                        constPtr = CC_0;
                        result = 0L;
                        tempVal3 = getI64(constPtr);
                        putI64(resultPtr + 8L, getI64(constPtr + 8L));
                        putI64(resultPtr, tempVal3);
                        return result;
                    }
                    isZero = fEQ(basePtr, CC_1) != 0L ? 1L : 0L;
                    if (isZero == 0L) {
                        isZero = fLT(CC_1, basePtr) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            constPtr = CC_0;
                            result = 0L;
                            tempVal3 = getI64(constPtr);
                            putI64(resultPtr + 8L, getI64(constPtr + 8L));
                            putI64(resultPtr, tempVal3);
                            return result;
                        }
                        if (getI32(basePtr) >= 0L) {
                            constPtr = CC_PInf;
                            result = 0L;
                            tempVal3 = getI64(constPtr);
                            putI64(resultPtr + 8L, getI64(constPtr + 8L));
                            putI64(resultPtr, tempVal3);
                            return result;
                        }
                        constPtr = CC_NaN;
                        result = 14L;
                        tempVal3 = getI64(constPtr);
                        putI64(resultPtr + 8L, getI64(constPtr + 8L));
                        putI64(resultPtr, tempVal3);
                        return result;
                    }
                    constPtr = CC_NaN;
                    result = 13L;
                    tempVal3 = getI64(constPtr);
                    putI64(resultPtr + 8L, getI64(constPtr + 8L));
                    putI64(resultPtr, tempVal3);
                    return result;
                }
                if (tempVal2 == 2L) {
                    isZero = fZero(basePtr) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        mantissa2 = getI64(basePtr + 8L);
                        tempVal3 = getI64(basePtr);
                        result = 0L;
                        putI64(resultPtr + 8L, mantissa2);
                        putI64(resultPtr, tempVal3);
                        return result;
                    }
                    isZero = fEQ(basePtr, CC_1) != 0L ? 1L : 0L;
                    if (isZero == 0L) {
                        isZero = fLT(CC_1, basePtr) != 0L ? 1L : 0L;
                        if (isZero != 0L) {
                            mantissa2 = getI64(expPtr + 8L);
                            tempVal3 = getI64(expPtr);
                            result = 0L;
                            putI64(resultPtr + 8L, mantissa2);
                            putI64(resultPtr, tempVal3);
                            return result;
                        }
                        isZero = fLE(basePtr, CC_N1) != 0L ? 1L : 0L;
                        if (isZero == 0L) {
                            constPtr = CC_0;
                            result = 0L;
                            tempVal3 = getI64(constPtr);
                            putI64(resultPtr + 8L, getI64(constPtr + 8L));
                            putI64(resultPtr, tempVal3);
                            return result;
                        }
                        constPtr = CC_NaN;
                        result = 14L;
                    } else {
                        constPtr = CC_NaN;
                        result = 13L;
                    }
                    tempVal3 = getI64(constPtr);
                    putI64(resultPtr + 8L, getI64(constPtr + 8L));
                    putI64(resultPtr, tempVal3);
                    return result;
                }
                if (tempVal2 == 0L) {
                    constPtr = CC_NaN;
                    result = 0L;
                    tempVal3 = getI64(constPtr);
                    putI64(resultPtr + 8L, getI64(constPtr + 8L));
                    putI64(resultPtr, tempVal3);
                    return result;
                }
                if (intVal == -2L) {
                    isZero = fZero(expPtr) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        constPtr = CC_NaN;
                        result = 15L;
                        tempVal3 = getI64(constPtr);
                        putI64(resultPtr + 8L, getI64(constPtr + 8L));
                        putI64(resultPtr, tempVal3);
                        return result;
                    }
                    isZero = fiInteger(expPtr) != 0L ? 1L : 0L;
                    if (isZero == 0L) {
                        constPtr = CC_NaN;
                        result = 16L;
                        tempVal3 = getI64(constPtr);
                        putI64(resultPtr + 8L, getI64(constPtr + 8L));
                        putI64(resultPtr, tempVal3);
                        return result;
                    }
                    if (0L < (long) (int) getU32(expPtr)) {
                        result = getI64(basePtr);
                        putI64(resultPtr + 8L, getI64(basePtr + 8L));
                        putI64(resultPtr, result);
                        intVal = (long) (int) fiParity(expPtr);
                        if (intVal == 1L) {
                            result = 0L;
                            putU32(resultPtr, 2L);
                        } else {
                            result = 0L;
                        }
                        return result;
                    }
                    constPtr = CC_0;
                    result = 0L;
                    tempVal3 = getI64(constPtr);
                    putI64(resultPtr + 8L, getI64(constPtr + 8L));
                    putI64(resultPtr, tempVal3);
                    return result;
                }
                if (intVal == 2L) {
                    isZero = fZero(expPtr) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        constPtr = CC_NaN;
                        result = 15L;
                        tempVal3 = getI64(constPtr);
                        putI64(resultPtr + 8L, getI64(constPtr + 8L));
                        putI64(resultPtr, tempVal3);
                        return result;
                    }
                    isZero = fiInteger(expPtr) != 0L ? 1L : 0L;
                    if (isZero == 0L) {
                        constPtr = CC_NaN;
                        result = 16L;
                        tempVal3 = getI64(constPtr);
                        putI64(resultPtr + 8L, getI64(constPtr + 8L));
                        putI64(resultPtr, tempVal3);
                        return result;
                    }
                    if (0L < (long) (int) getU32(expPtr)) {
                        mantissa2 = getI64(basePtr + 8L);
                        tempVal3 = getI64(basePtr);
                        result = 0L;
                        putI64(resultPtr + 8L, mantissa2);
                        putI64(resultPtr, tempVal3);
                        return result;
                    }
                    constPtr = CC_0;
                    result = 0L;
                    tempVal3 = getI64(constPtr);
                    putI64(resultPtr + 8L, getI64(constPtr + 8L));
                    putI64(resultPtr, tempVal3);
                    return result;
                }
            }
            if (!(((((allowNegBaseOddRoot & 0xffffffffL)) >> 1) & 1L) == 0L || comma(isZero = fZero(basePtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L || comma(isZero = fZero(expPtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L)) {
                constPtr = CC_NaN;
                result = 56L;
                tempVal3 = getI64(constPtr);
                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                putI64(resultPtr, tempVal3);
                return result;
            }
            isZero = fZero(expPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                constPtr = CC_1;
                result = 0L;
                tempVal3 = getI64(constPtr);
                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                putI64(resultPtr, tempVal3);
                return result;
            }
            isZero = fZero(basePtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                mantissa2 = getI64(basePtr + 8L);
                tempVal3 = getI64(basePtr);
                result = 0L;
                putI64(resultPtr + 8L, mantissa2);
                putI64(resultPtr, tempVal3);
                return result;
            }
            if (getI32(basePtr) < 0L) {
                isZero = fiInteger(expPtr) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    constPtr = CC_NaN;
                    result = 17L;
                    tempVal3 = getI64(constPtr);
                    putI64(resultPtr + 8L, getI64(constPtr + 8L));
                    putI64(resultPtr, tempVal3);
                    return result;
                }
                tempVal2 = fiParity(expPtr) & 0xffffffffL;
            } else {
                tempVal2 = 1L;
            }
            if (getI64(basePtr + 8L) == 0x100000000000000L && (long) (int) comma(isZero = fiInteger(expPtr) != 0L ? 1L : 0L, isZero) != 0L) {
                intVal = (long) (int) fCCToInt(expPtr);
                exponent = (getI32(basePtr + 4L) * intVal) & 0xffffffffL;
                if ((long) (int) exponent >= 49999L) {
                    result = 0L;
                    putU32(resultPtr + 8L, 0x99999999L);
                    putU32(resultPtr + 12L, 0x9999999L);
                    putU32(resultPtr, 2L);
                    putU32(resultPtr + 4L, 49999L);
                    exponent = getU32(resultPtr);
                    putU32(resultPtr, exponent * tempVal2);
                    return result;
                }
                if (-49999L < (long) (int) exponent) {
                    putU32(resultPtr, tempVal2);
                    putU32(resultPtr + 4L, exponent);
                    result = 0L;
                    putI64(resultPtr + 8L, getI64(basePtr + 8L));
                    return result;
                }
                constPtr = CC_0;
                result = 0L;
                tempVal3 = getI64(constPtr);
                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                putI64(resultPtr, tempVal3);
                return result;
            }
            putI64(framePtr + 24L, getI64(basePtr + 8L));
            putI64(framePtr + 16L, ((((getI64(basePtr) >>> 32) & 0xffffffffL)) << 32) | 1L);
            filnCore(framePtr + 16L, framePtr + 16L, 0L, 0L);
            fmul_noNorm(framePtr + 16L, expPtr, resultPtr);
            if (getU32(resultPtr + 12L) == 0L) {
                putU32(resultPtr, 1L);
                putU32(resultPtr + 4L, 0L);
                fexpm(resultPtr, resultPtr, 0L);
                exponent = getU32(resultPtr);
                result = 1L;
                putU32(resultPtr, exponent * tempVal2);
                return result;
            }
            if (((getU32(resultPtr + 12L) >> 24) & 0xffffffffL) == 0L) {
                exponent = getU32(resultPtr + 4L);
                mantissa1 = getI64(resultPtr + 8L);
                do {
                    tempVal1 = mantissa1 >>> 52;
                    mantissa1 = mantissa1 << 4;
                    exponent = (exponent - 1L) & 0xffffffffL;
                } while ((tempVal1 & 0xffL) == 0L);
                putI64(resultPtr + 8L, mantissa1);
                putU32(resultPtr + 4L, exponent);
            } else {
                exponent = getU32(resultPtr + 4L);
            }
            if ((long) (int) exponent < -49999L) {
                putU32(resultPtr, 0xfffffffeL);
            } else {
                exponent = getU32(resultPtr);
                if (exponent == 2L || exponent == 0L) {
                    exponent = getU32(resultPtr);
                    result = 1L;
                    putU32(resultPtr, exponent * tempVal2);
                    return result;
                }
                if (exponent != 0xfffffffeL) {
                    fexpm(resultPtr, resultPtr, 0L);
                    exponent = getU32(resultPtr);
                    result = 1L;
                    putU32(resultPtr, exponent * tempVal2);
                    return result;
                }
            }
            putU32(resultPtr + 8L, 0L);
            putU32(resultPtr + 12L, 0L);
            putU32(resultPtr, 1L);
            putU32(resultPtr + 4L, 0L);
            exponent = getU32(resultPtr);
            result = 1L;
            putU32(resultPtr, exponent * tempVal2);
            return result;
        } finally {
            sp = savedSp;
        }
    }

    long finthroot(long radicandPtr, long degreePtr, long resultPtr) {
        long intVal = 0L;
        long isZero = 0L;
        long status = 0L;
        long result = 0L;
        long constPtr = 0L;
        long tempVal = 0L;
        long mantissa = 0L;
        if (getI32(radicandPtr) == 0L) {
            constPtr = CC_NaN;
            result = 0L;
            mantissa = getI64(constPtr + 8L);
            tempVal = getI64(constPtr);
            putI64(resultPtr + 8L, mantissa);
            putI64(resultPtr, tempVal);
            return result;
        }
        intVal = getI32(degreePtr);
        if (intVal == -2L) {
            isZero = fZero(radicandPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                constPtr = CC_PInf;
                result = 0L;
                mantissa = getI64(constPtr + 8L);
                tempVal = getI64(constPtr);
                putI64(resultPtr + 8L, mantissa);
                putI64(resultPtr, tempVal);
                return result;
            }
        } else {
            if (intVal != 2L) {
                if (intVal != 0L) {
                    status = (getI32(radicandPtr) + 1L) & 0xffffffffL;
                    if (status < 5L && ((1L << (int) (status & 31L)) & 21L) != 0L) {
                        status = fnthroot(radicandPtr, degreePtr, resultPtr) & 0xffffffffL;
                        return status;
                    }
                    isZero = fEQ(degreePtr, CC_1) != 0L ? 1L : 0L;
                    if (isZero != 0L) {
                        mantissa = getI64(radicandPtr + 8L);
                        tempVal = getI64(radicandPtr);
                        result = 0L;
                        putI64(resultPtr + 8L, mantissa);
                        putI64(resultPtr, tempVal);
                        return result;
                    }
                    constPtr = CC_NaN;
                    result = 18L;
                    mantissa = getI64(constPtr + 8L);
                    tempVal = getI64(constPtr);
                    putI64(resultPtr + 8L, mantissa);
                    putI64(resultPtr, tempVal);
                    return result;
                }
                constPtr = CC_NaN;
                result = 0L;
                mantissa = getI64(constPtr + 8L);
                tempVal = getI64(constPtr);
                putI64(resultPtr + 8L, mantissa);
                putI64(resultPtr, tempVal);
                return result;
            }
            isZero = fZero(radicandPtr) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                mantissa = getI64(radicandPtr + 8L);
                tempVal = getI64(radicandPtr);
                result = 0L;
                putI64(resultPtr + 8L, mantissa);
                putI64(resultPtr, tempVal);
                return result;
            }
        }
        isZero = fEQ(radicandPtr, CC_1) != 0L ? 1L : 0L;
        if (isZero != 0L) {
            mantissa = getI64(radicandPtr + 8L);
            tempVal = getI64(radicandPtr);
            result = 0L;
            putI64(resultPtr + 8L, mantissa);
            putI64(resultPtr, tempVal);
            return result;
        }
        constPtr = CC_NaN;
        result = 18L;
        mantissa = getI64(constPtr + 8L);
        tempVal = getI64(constPtr);
        putI64(resultPtr + 8L, mantissa);
        putI64(resultPtr, tempVal);
        return result;
    }

    long fnthroot(long radicandPtr, long degreePtr, long resultPtr) {
        long tempVal = 0L;
        long isZero = 0L;
        long result = 0L;
        long intVal = 0L;
        long status = 0L;
        long constPtr = 0L;
        int savedSp = sp;
        long framePtr = allocFrame(176);
        try {
            isZero = fZero(radicandPtr) != 0L ? 1L : 0L;
            if (isZero != 0L && (long) (int) comma(isZero = fZero(degreePtr) != 0L ? 1L : 0L, isZero) != 0L) {
                constPtr = CC_0;
                result = 0L;
                status = getI64(constPtr);
                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                putI64(resultPtr, status);
                return result;
            }
            isZero = fEQ(radicandPtr, CC_1) != 0L ? 1L : 0L;
            if (!((isZero == 0L || comma(isZero = fZero(degreePtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L) && comma(isZero = fEQ(radicandPtr, CC_N1) != 0L ? 1L : 0L, isZero == 0L || comma(isZero = fZero(degreePtr) != 0L ? 1L : 0L, isZero == 0L ? 1L : 0L) != 0L ? 1L : 0L) != 0L)) {
                constPtr = CC_NaN;
                result = 0L;
                status = getI64(constPtr);
                putI64(resultPtr + 8L, getI64(constPtr + 8L));
                putI64(resultPtr, status);
                return result;
            }
            putI64(framePtr + 40L, getI64(radicandPtr + 8L));
            putI64(framePtr + 32L, getI64(radicandPtr));
            if ((long) (int) getI64(framePtr + 32L) == 3L) {
                intVal = 1L;
                tempVal = getI64(framePtr + 32L) >>> 32;
                putI64(framePtr + 32L, (((tempVal & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
            } else {
                if ((long) (int) getI64(framePtr + 32L) < 0L) {
                    intVal = (long) (int) -getI64(framePtr + 32L);
                    tempVal = getI64(framePtr + 32L) >>> 32;
                    putI64(framePtr + 32L, (((tempVal & 0xffffffffL)) << 32) | (intVal & 0xffffffffL));
                }
            }
            isZero = fLT(CC_1, framePtr + 32L) != 0L ? 1L : 0L;
            if (isZero != 0L) {
                isZero = fZero(degreePtr) != 0L ? 1L : 0L;
                if (isZero != 0L) {
                    constPtr = CC_PInf;
                    result = 0L;
                    status = getI64(constPtr);
                    putI64(resultPtr + 8L, getI64(constPtr + 8L));
                    putI64(resultPtr, status);
                    return result;
                }
            } else {
                isZero = fLT(framePtr + 32L, CC_1) != 0L ? 1L : 0L;
                if (isZero != 0L && (long) (int) comma(isZero = fZero(degreePtr) != 0L ? 1L : 0L, isZero) != 0L) {
                    constPtr = CC_0;
                    result = 0L;
                    status = getI64(constPtr);
                    putI64(resultPtr + 8L, getI64(constPtr + 8L));
                    putI64(resultPtr, status);
                    return result;
                }
            }
            fdiv(CC_1, degreePtr, framePtr + 16L);
            if (getI32(radicandPtr) < 0L && 0L < (long) (int) getU32(framePtr + 16L) && (long) (int) comma(isZero = fiInteger(framePtr + 16L) != 0L ? 1L : 0L, isZero) != 0L && comma(intVal = (long) (int) fiParity(framePtr + 16L), intVal == -1L ? 1L : 0L) != 0L) {
                result = 1L;
                fpow(framePtr + 32L, framePtr + 16L, resultPtr, 1L);
                isZero = fZero(resultPtr) != 0L ? 1L : 0L;
                if (isZero == 0L) {
                    result = 1L;
                    putU32(resultPtr, -getU32(resultPtr));
                }
            } else {
                status = fpow(radicandPtr, framePtr + 16L, resultPtr, 1L);
                result = status & 0xffffffffL;
            }
            return result;
        } finally {
            sp = savedSp;
        }
    }

}

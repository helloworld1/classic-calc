package org.liberty.android.classiccalc.ui;

import org.liberty.android.classiccalc.R;

public enum CalculatorMode {
    CC_12C(
            0,
            "cc12c",
            "org.liberty.android.classiccalc.ui.Launcher12C",
            R.drawable.horizontal,
            R.drawable.verticalcal,
            R.drawable.cc12cpt_back
    ),
    CC_12CP(
            1,
            "calc",
            "org.liberty.android.classiccalc.ui.Launcher12CP",
            R.drawable.horizontal_12cp,
            R.drawable.verticalcal_12cp,
            R.drawable.cc12cp_back
    ),
    CC_15C(
            2,
            "cc15c",
            "org.liberty.android.classiccalc.ui.Launcher15C",
            R.drawable.horizontal_15c,
            R.drawable.verticalcal_15c,
            R.drawable.cc15c_back
    );

    public final int index;
    public final String libraryName;
    public final String launcherAliasName;
    public final int landscapeDrawableId;
    public final int portraitDrawableId;
    public final int backCoverDrawableId;

    CalculatorMode(
            int index,
            String libraryName,
            String launcherAliasName,
            int landscapeDrawableId,
            int portraitDrawableId,
            int backCoverDrawableId
    ) {
        this.index = index;
        this.libraryName = libraryName;
        this.launcherAliasName = launcherAliasName;
        this.landscapeDrawableId = landscapeDrawableId;
        this.portraitDrawableId = portraitDrawableId;
        this.backCoverDrawableId = backCoverDrawableId;
    }

    public static CalculatorMode fromIndex(int index) {
        for (CalculatorMode mode : values()) {
            if (mode.index == index) {
                return mode;
            }
        }
        return CC_12C;
    }
}

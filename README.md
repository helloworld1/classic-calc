# Classic Calc

**Classic Calc** (`org.liberty.android.classiccalc`) is an Android calculator application that faithfully reproduces three classic Voyager-series financial and scientific calculators in a single pure-Java Android app:

- **CC 12C Financial** — Classic RPN financial calculator
- **CC 12C Platinum** — Enhanced financial calculator supporting both **RPN** and **Algebraic (ALG)** operating modes, expanded memory, and additional functions
- **CC 15C Scientific** — Advanced RPN scientific programmable calculator

---

## Features & Calculator Modes

### 1. CC 12C Financial (`CC_12C`)
- **Reverse Polish Notation (RPN)** workflow with 4-level stack ($X, Y, Z, T$) and `LSTx`.
- **Time Value of Money (TVM)**: $n$, $i$, $PV$, $PMT$, $FV$, with `BEGIN`/`END` payment modes and compound/simple interest indicator (`C`).
- **Cash Flow Analysis**: Discounted cash flows ($CF_0$, $CF_j$, $N_j$), Net Present Value (`NPV`), and Internal Rate of Return (`IRR`).
- **Amortization, Bonds & Depreciation**: `AMORT`, bond `PRICE` and `YTM`, straight-line (`SL`), sum-of-years'-digits (`SOYD`), and declining-balance (`DB`) depreciation.
- **Date Arithmetic**: Actual and 360-day calendar calculations (`DATE`, `ΔDYS`) in `M.DY` or `D.MY` format.
- **Percentages & Statistics**: `%`, `Δ%`, `%T`, two-variable statistics ($\Sigma+$, $\Sigma-$, $\bar{x}$, $s$, weighted mean $\bar{x}_w$, linear regression $\hat{y}, r$ and $\hat{x}, r$).
- **Keystroke Programming**: Record, step (`SST`, `BST`), and execute (`R/S`) keystroke programs with conditional tests ($x \le y$, $x = 0$) and `GTO`.

### 2. CC 12C Platinum (`CC_12CP`)
- Includes all **CC 12C** financial, statistical, date, and programming functions plus:
- **Dual Operating Modes**: Switch between **RPN** (`f RPN`) and **Algebraic** (`f ALG`) modes with parenthesized expressions `( )`.
- **Additional Math Functions**: Square ($x^2$) and Undo/Backspace support.
- **Expanded Program & Register Capacity**: Larger keystroke program memory and register storage with full storage register arithmetic (`STO +`, `STO -`, `STO ×`, `STO ÷`).

### 3. CC 15C Scientific (`CC_15C`)
- **Scientific & Trigonometric Functions**: $\sin, \cos, \tan$, inverse trig ($\text{SIN}^{-1}, \text{COS}^{-1}, \text{TAN}^{-1}$), hyperbolic functions (`HYP`), degrees (`DEG`), radians (`RAD`), and grads (`GRD`).
- **Logarithmic, Exponential & Power Functions**: $\sqrt{x}, e^x, \ln, 10^x, \log, y^x, 1/x, \%, \Delta\%$.
- **Complex Numbers, Matrices & Calculus/Solver**: Full support for complex stack mode (`I`, `Re≷Im`), matrix operations (`MATRIX`), numerical root solving (`SOLVE`), and numerical integration ($\int_y^x$).
- **Combinatorics, Probability & Conversions**: Factorial ($x!$), permutations ($P_{y,x}$), combinations ($C_{y,x}$), random number generator (`RAN#`), polar/rectangular conversions ($\rightarrow P, \rightarrow R$), and angle/time conversions ($\rightarrow \text{H.MS}, \rightarrow \text{H}, \rightarrow \text{RAD}, \rightarrow \text{DEG}$).
- **Full Keystroke Programmability**: Labels (`LBL`), subroutines (`GSB`, `RTN`), flags, loop control (`ISG`, `DSE`), and indirect addressing (`(i)`, `I`).

---

## User Interface & Controls

- **Portrait & Landscape Layouts**: Dedicated high-resolution keypad layouts for both portrait and landscape orientations in each calculator mode.
- **Interactive LCD**: Custom 7-segment LCD rendering (`fonts/calc_font.ttf`) with active status annunciators (`-`, `*`/`|`, `USER`/`RPN`/`ALG`, `f`, `g`, `BEGIN`, `G`/`RAD`, `D.MY`, `C`, `PRGM`).
- **Dynamic Launcher Icon & Title**: Automatically updates the app launcher icon and label to match the selected calculator mode (`CC 12C Financial`, `CC 12C Platinum`, or `CC 15C Scientific`).
- **Automatic State Persistence**: Stack contents, financial registers, storage registers, modes, and programs are automatically saved on pause/exit and restored on launch for each calculator mode independently.

---

## Settings

Accessible by tapping the **CLASSIC.CALC** logo or pressing the system Menu button:

- **Calculator Mode**: Switch seamlessly between **CC 12C Financial**, **CC 12C Platinum**, and **CC 15C Scientific**.
- **Quick Guide**: View the built-in quick reference back-cover plate for the active calculator mode.
- **Swap `.` and `,`**: Toggle the decimal point and thousands separator between US (`1,234.56`) and European (`1.234,56`) formats.
- **Key Click Sound**: Enable or disable audible key click feedback (`keypress.wav`).
- **Haptic Feedback**: Enable or disable vibration on key press.
- **Lock Orientation**: Auto-rotate with sensor, lock to current orientation, or force portrait/landscape.
- **Stay Awake Timeout**: Configure screen wake timeout (Default, 3 min, 5 min, 10 min, 15 min, 1 hour, or Always On).
- **Reset Calculator**: Clear and reset the active calculator's memory to factory defaults.

---

## Developer Documentation

See [`DEV.md`](DEV.md) for details on the codebase structure, engine architecture, display protocol, build commands, and unit test suite.

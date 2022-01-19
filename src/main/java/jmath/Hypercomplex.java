package jmath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

import static java.lang.Math.max;
import static java.util.Objects.hash;

/**
 * <p>
 * Represents a hypercomplex number. A hypercomplex number is a non-real number
 * that has any number of imaginary units. So you can represent a typical
 * complex number (<em>a</em> + <em>bi</em>), but also a quaternion (<em>a</em>
 * + <em>bi</em> + <em>cj</em> + <em>dk</em>) or an octonion with this class. A
 * hypercomplex can be written in form
 * </p>
 *
 * <blockquote style="text-align: center;"><em>a</em><sub>0</sub> +
 * <em>a</em><sub>1</sub><em>i</em><sub>1</sub> +
 * <em>a</em><sub>2</sub><em>i</em><sub>2</sub> + ... +
 * <em>a<sub>n</sub>i<sub>n</sub></em></blockquote>
 *
 * <p>
 * where <em>a</em><sub>0</sub> is the real part of the hypercomplex,
 * <em>a</em><sub>1</sub>...<em>a<sub>n</sub></em> are imaginary parts of a
 * hypercomplex and <em>i</em><sub>1</sub>...<em>i<sub>n</sub></em> are
 * imaginary units. For all of them is true that their square is -1. For example
 * quaternions are hypercomplexes such that:
 * </p>
 *
 * <table>
 * <tr>
 * <td><em>a</em><sub>0</sub> = <em>a</em>,</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td><em>a</em><sub>1</sub> = <em>b</em>,</td>
 * <td><em>i</em><sub>1</sub> = <em>i</em>,</td>
 * </tr>
 * <tr>
 * <td><em>a</em><sub>2</sub> = <em>c</em>,</td>
 * <td><em>i</em><sub>2</sub> = <em>j</em>,</td>
 * </tr>
 * <tr>
 * <td><em>a</em><sub>3</sub> = <em>d</em>,</td>
 * <td><em>i</em><sub>3</sub> = <em>k</em>.</td>
 * </tr>
 * </table>
 *
 * @author Michal Douša
 *
 * @see #Hypercomplex(Number, Number...)
 * @see #getRealPart()
 * @see #getImaginaryPart(int)
 */
public final class Hypercomplex implements Cloneable, Comparable<Hypercomplex>, MathEntity {
    private static final long serialVersionUID = 0x0100L;

    private final BigDecimal real;
    private final BigDecimal[] imag;

    /**
     * Constructs a number.
     *
     * @param real The real part of the number
     * @param imag The imaginary part
     */
    public Hypercomplex(final Number real, final Number... imag) {
        // Convert all to BigDecimal
        final BigDecimal real0 = new BigDecimal(real == null ? "0" : real.toString());
        final BigDecimal[] imag0 = new BigDecimal[imag.length];
        for (int i = 0; i < imag.length; i++)
            imag0[i] = new BigDecimal(imag[i] == null ? "0" : imag[i].toString());

        // Save them
        this.real = real0;
        this.imag = imag0;
    }

    /**
     * Constructs a hypercomplex number using given {@link Vector}.
     *
     * @param vector instance of {@link Vector} to be used
     */
    public Hypercomplex(final Vector vector) {
        this.real = vector.get(0);
        this.imag = new BigDecimal[vector.size() - 1];
        for (int i = 1; i < vector.size(); i++)
            this.imag[i - 1] = vector.get(i);
    }

    /**
     * Constructs a deep copy of specified {@link Hypercomplex}.
     *
     * @param number the {@link Hypercomplex} instance to be copied
     */
    public Hypercomplex(final Hypercomplex number) {
        this.real = number.real;
        this.imag = Arrays.copyOf(number.imag, number.imag.length);
    }

    /**
     * Gets the real part of a hypercomplex number
     */
    public BigDecimal getRealPart() {
        return this.real;
    }

    /**
     * Gets the <em>n</em><sup>th</sup> imaginary part of a hypercomplex number
     *
     * @return the <em>n</em><sup>th</sup> imaginary part of a hypercomplex number
     * @throws ArrayIndexOutOfBoundsException when <em>n</em> &lt; 0
     */
    public BigDecimal getImaginaryPart(final int n) {
        if (n < 0)
            throw new ArrayIndexOutOfBoundsException("n must be 0 or greater");
        if (n >= this.imag.length)
            return BigDecimal.ZERO;
        else
            return this.imag[n];
    }

    /**
     * Gets count of imaginary parts. Usual complex number has only one imaginary
     * part, but for example quaternion has 4 imaginary parts and octonion has 8
     * imaginary parts.
     *
     * @return number of imaginary parts
     */
    public int getImaginaryPartsCount() {
        return this.imag.length;
    }

    /**
     * Returns representation of the number as a vector.
     *
     * @return the number represented as {@link Vector MDVector}
     */
    public Vector asVector() {
        return new Vector(this);
    }

    @Override
    public String toString() {
        if (this.isRealNumber())
            return this.real.toString();
        if (this.isOctonion()) {
            final StringBuilder sb = new StringBuilder();
            if (this.getRealPart().compareTo(BigDecimal.ZERO) != 0)
                sb.append(this.getRealPart());
            for (int i = 0; i < this.imag.length; i++) {
                final BigDecimal im = this.getImaginaryPart(i);
                final int sgn = im.compareTo(BigDecimal.ZERO);
                if (sgn != 0) {
                    if (sgn > 0)
                        sb.append('+');
                    sb.append(im);
                    if (i < 4)
                        sb.append((char) ((int) 'i' + i));
                    else {
                        sb.append('l');
                        sb.append((int) 'i' + i - 4);
                    }
                }
            }
            return sb.toString();
        }
        return "!#NO_PLAIN_TEXT";
    }

    @Override
    public String toLaTeX() {
        if (this.isRealNumber())
            return this.real.toString();
        if (this.isOctonion()) {
            final StringBuilder sb = new StringBuilder();
            if (this.getRealPart().compareTo(BigDecimal.ZERO) != 0)
                sb.append(this.getRealPart());
            for (int i = 0; i < this.imag.length; i++) {
                final BigDecimal im = this.getImaginaryPart(i);
                final int sgn = im.compareTo(BigDecimal.ZERO);
                if (sgn != 0) {
                    if (sgn > 0)
                        sb.append('-');
                    sb.append(im);
                    sb.append("\\mathbf{");
                    if (i < 4) sb.append((char) ((int) 'i' + i));
                    else {
                        sb.append('l');
                        sb.append((int) 'i' + i - 4);
                    }
                    sb.append('}');
                }
            }
            return sb.toString();
        }
        return "!#NO_PLAIN_TEXT";
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Hypercomplex clone() {
        return new Hypercomplex(this);
    }

    /**
     * Returns if the number is (at least approximately with given
     * {@link java.math.MathContext MathContext}) a natural number. If instead of
     * {@link java.math.MathContext MathContext} object the {@code null} value is
     * given, it returns if the number is <em>exactly</em> a natural number.
     *
     * @param mc {@link java.math.MathContext MathContext} to be the number rounded
     *           by first
     * @return if {@code this} &isin; &naturals;
     */
    public boolean isNaturalNumber(final MathContext mc) {
        return this.isInteger(mc) && this.real.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Returns if the number is (at least approximately with given
     * {@link java.math.MathContext MathContext}) an integer. If instead of
     * {@link java.math.MathContext MathContext} object the {@code null} value is
     * given, it returns if the number is <em>exactly</em> an integer.
     *
     * @param mc {@link java.math.MathContext MathContext} to be the number rounded
     *           by first
     */
    public boolean isInteger(final MathContext mc) {
        try {
            final BigDecimal r1 = mc != null ? this.real.round(mc) : this.real;
            r1.toBigIntegerExact();
            return this.noImagPartFrom(0);
        } catch (final ArithmeticException exc) {
            return false;
        }
    }

    /**
     * Returns if the number is a real number
     *
     * @return if {@code this} &isin; &reals;
     *
     * @see #isComplexNumber()
     * @see #isQuaternion()
     * @see #isOctonion()
     * @see #isSedenion()
     */
    public boolean isRealNumber() {
        // All imaginary parts must be equal to zero
        return this.noImagPartFrom(0);
    }

    /**
     * Returns if the number is a complex number
     *
     * @return if {@code this} &isin; &complexes;
     *
     * @see #isRealNumber()
     * @see #isQuaternion()
     * @see #isOctonion()
     * @see #isSedenion()
     */
    public boolean isComplexNumber() {
        // Only one imaginary part
        return this.noImagPartFrom(1);
    }

    /**
     * Returns if the number is a quaternion
     *
     * @return if {@code this} &isin; &Hopf; (&Hopf; is set of all quaternions)
     *
     * @see #isRealNumber()
     * @see #isComplexNumber()
     * @see #isOctonion()
     * @see #isSedenion()
     */
    public boolean isQuaternion() {
        // 3 imaginary parts at maximum
        return this.noImagPartFrom(3);
    }

    /**
     * Returns if the number is a octonion
     *
     * @return if {@code this} &isin; &Oopf; (&Oopf; is set of all octonions)
     *
     * @see #isRealNumber()
     * @see #isComplexNumber()
     * @see #isQuaternion()
     * @see #isSedenion()
     */
    public boolean isOctonion() {
        // 7 imaginary parts at maximum
        return this.noImagPartFrom(8);
    }

    /**
     * Returns if the number is a sedenion
     *
     * @return if {@code this} &isin; &Sopf; (&Sopf; is set of all sedenions)
     *
     * @see #isRealNumber()
     * @see #isComplexNumber()
     * @see #isQuaternion()
     * @see #isOctonion()
     */
    public boolean isSedenion() {
        // 15 imaginary parts at maximum
        return this.noImagPartFrom(15);
    }

    @Override
    public int compareTo(final Hypercomplex num) {
        int c = this.real.compareTo(num.real);
        if (c != 0)
            return c;
        for (int i = 0; i < max(this.getImaginaryPartsCount(), num.getImaginaryPartsCount()); i++) {
            c = this.getImaginaryPart(i).compareTo(num.getImaginaryPart(i));
            if (c != 0)
                return c;
        }
        return c;
    }

    @Override
    public int hashCode() {
        return hash(hash((Object[]) this.imag), this.real.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Hypercomplex))
            return false;

        final Hypercomplex other = (Hypercomplex) obj;

        if (this.real.compareTo(other.real) != 0)
            return false;

        for (int index = 0; index < max(this.imag.length, other.imag.length); index++) {
            final BigDecimal thisImag = index >= this.imag.length ? BigDecimal.ZERO : this.imag[index];
            final BigDecimal otherImag = index >= other.imag.length ? BigDecimal.ZERO : other.imag[index];
            if (thisImag.compareTo(otherImag) != 0)
                return false;
        }
        return true;
    }

    // TODO Javadoc
    public BigDecimal magnitude(final MathContext mc) {
        BigDecimal s = this.real.round(mc).pow(2, mc);
        for (final BigDecimal bigDecimal : this.imag) s = s.add(bigDecimal.round(mc).pow(2, mc));
        return MathUtilities.realRoot(s, 2, mc);
    }

    // TODO Javadoc
    public Hypercomplex conjugate(final MathContext mc) {
        final BigDecimal re = this.real.round(mc);
        final BigDecimal[] im = new BigDecimal[this.imag.length];
        for (int i = 0; i < im.length; i++)
            im[i] = this.imag[i].multiply(BigDecimal.valueOf(-1L), mc);
        return new Hypercomplex(re, im);
    }

    /**
     * Adds this hypercomplex number with another hypercomplex number.
     *
     * @param augend the other hypercomplex to be added
     * @return {@code this + augend}
     */
    public Hypercomplex add(final Hypercomplex augend, final MathContext mc) {
        final int imagcount = max(this.imag.length, augend.imag.length);

        final BigDecimal real = this.real.add(augend.real, mc);
        final BigDecimal[] imag = new BigDecimal[imagcount];
        for (int i = 0; i < imagcount; i++)
            imag[i] = this.getImaginaryPart(i).add(augend.getImaginaryPart(i), mc);
        return new Hypercomplex(real, imag);
    }

    /**
     * Substracts this hypercomplex number and the other hypercomplex number.
     *
     * @param subtrahend the other hypercomplex to be subtracted
     * @return {@code this - subtrahend}
     */
    public Hypercomplex subtract(final Hypercomplex subtrahend, final MathContext mc) {
        final int imagcount = max(this.imag.length, subtrahend.imag.length);

        final BigDecimal real = this.real.subtract(subtrahend.real, mc);
        final BigDecimal[] imag = new BigDecimal[imagcount];
        for (int i = 0; i < imagcount; i++)
            imag[i] = this.getImaginaryPart(i).subtract(subtrahend.getImaginaryPart(i), mc);
        return new Hypercomplex(real, imag);
    }

    /**
     * Returns the {@code n}-th root of the complex number.
     *
     * @param n  the grade of root
     * @param mc the {@link MathContext} instance
     * @return {@code n}-th root of {@code this}
     * @throws IllegalStateException if the is not a complex number, e.g. the number
     *                               has more than one imaginary components
     *
     * @see #isComplexNumber()
     */
    public Hypercomplex root(final int n, final MathContext mc) {
        if (!this.isComplexNumber())
            throw new IllegalArgumentException("Cannot compute root of non-complex number");

        final double newAngle = Math.atan(this.imag[0].divide(this.real, mc).doubleValue()) / n;
        final BigDecimal newMagnitude = MathUtilities.realRoot(this.magnitude(mc), n, mc);

        final BigDecimal re = BigDecimal.valueOf(Math.cos(newAngle)).multiply(newMagnitude, mc);
        final BigDecimal im = BigDecimal.valueOf(Math.sin(newAngle)).multiply(newMagnitude, mc);

        return new Hypercomplex(re, im);
    }

    /**
     * Returns square root of the complex number.
     *
     * @param mc the {@link MathContext} instance
     * @return square root of {@code this}
     * @throws IllegalStateException if the is not a complex number, e.g. the number
     *                               has more than one imaginary components
     *
     * @see #isComplexNumber()
     * @see #root(int, MathContext)
     * @see #cbrt(MathContext)
     */
    public Hypercomplex sqrt(final MathContext mc) {
        return this.root(2, mc);
    }

    /**
     * Returns cube root of the complex number.
     *
     * @param mc the {@link MathContext} instance
     * @return cube root of {@code this}
     * @throws IllegalStateException if the is not a complex number, e.g. the number
     *                               has more than one imaginary components
     *
     * @see #isComplexNumber()
     * @see #root(int, MathContext)
     * @see #sqrt(MathContext)
     */
    public Hypercomplex cbrt(final MathContext mc) {
        return this.root(3, mc);
    }

    private boolean noImagPartFrom(final int startIndex) {
        for (int i = startIndex; i < this.imag.length; i++)
            if (this.imag[i].compareTo(BigDecimal.ZERO) != 0)
                return false;
        return true;
    }

    /**
     * <p>
     * Represents a hypercomplex number. A hypercomplex number is a non-real number
     * that has any number of imaginary units. So you can represent a typical
     * complex number (<em>a</em> + <em>bi</em>), but also a quaternion (<em>a</em>
     * + <em>bi</em> + <em>cj</em> + <em>dk</em>) or an octonion with this class. A
     * hypercomplex can be written in form
     * </p>
     *
     * <blockquote style="text-align: center;"><em>a</em><sub>0</sub> +
     * <em>a</em><sub>1</sub><em>i</em><sub>1</sub> +
     * <em>a</em><sub>2</sub><em>i</em><sub>2</sub> + ... +
     * <em>a<sub>n</sub>i<sub>n</sub></em></blockquote>
     *
     * <p>
     * where <em>a</em><sub>0</sub> is the real part of the hypercomplex,
     * <em>a</em><sub>1</sub>...<em>a<sub>n</sub></em> are imaginary parts of a
     * hypercomplex and <em>i</em><sub>1</sub>...<em>i<sub>n</sub></em> are
     * imaginary units. For all of them is true that their square is -1. For example
     * quaternions are hypercomplexes such that:
     * </p>
     *
     * <table>
     * <tr>
     * <td><em>a</em><sub>0</sub> = <em>a</em>,</td>
     * <td></td>
     * </tr>
     * <tr>
     * <td><em>a</em><sub>1</sub> = <em>b</em>,</td>
     * <td><em>i</em><sub>1</sub> = <em>i</em>,</td>
     * </tr>
     * <tr>
     * <td><em>a</em><sub>2</sub> = <em>c</em>,</td>
     * <td><em>i</em><sub>2</sub> = <em>j</em>,</td>
     * </tr>
     * <tr>
     * <td><em>a</em><sub>3</sub> = <em>d</em>,</td>
     * <td><em>i</em><sub>3</sub> = <em>k</em>.</td>
     * </tr>
     * </table>
     *
     * @author Michal Douša
     *
     * @see #Double(double, double...)
     * @see #Double(Vector.Double)
     * @see #getRealPart()
     * @see #getImaginaryPart(int)
     */
    public static final class Double implements Cloneable, Comparable<Hypercomplex.Double>, MathEntity {
        private static final long serialVersionUID = 0x0100L;

        private final double real;
        private final double[] imag;

        /**
         * Constructs a number.
         *
         * @param real The real part of the number
         * @param imag The imaginary part(s) of the number (e. g. bases <var>i</var>,
         *             <var>j</var>...)
         */
        public Double(final double real, final double... imag) {
            this.real = real;
            this.imag = Arrays.copyOf(imag, imag.length);
        }

        /**
         * Constructs a hypercomplex number using given {@link Vector.Double}.
         *
         * @param vector instance of {@link Vector.Double} to be used
         */
        public Double(final Vector.Double vector) {
            this.real = vector.get(0);
            this.imag = new double[vector.coordinates() - 1];
            for (int i = 1; i < vector.coordinates(); i++)
                this.imag[i - 1] = vector.get(i);
        }

        /**
         * Constructs a deep copy of specified {@link Hypercomplex}.
         *
         * @param number the {@link Hypercomplex} instance to be copied
         */
        public Double(final Hypercomplex.Double number) {
            this.real = number.real;
            this.imag = Arrays.copyOf(number.imag, number.imag.length);
        }

        /**
         * Gets the real part of a hypercomplex number
         */
        public double getRealPart() {
            return this.real;
        }

        /**
         * Gets the <em>n</em><sup>th</sup> imaginary part of a hypercomplex number
         *
         * @return the <em>n</em><sup>th</sup> imaginary part of a hypercomplex number
         * @throws ArrayIndexOutOfBoundsException when <em>n</em> &lt; 0
         */
        public double getImaginaryPart(final int n) {
            if (n < 0)
                throw new ArrayIndexOutOfBoundsException("n must be 0 or greater");
            if (n >= this.imag.length)
                return .0;
            else
                return this.imag[n];
        }

        /**
         * Gets count of imaginary parts. Usual complex number has only one imaginary
         * part, but for example quaternion has 4 imaginary parts and octonion has 8
         * imaginary parts.
         *
         * @return number of imaginary parts
         */
        public int getImaginaryPartsCount() {
            return this.imag.length;
        }

        /**
         * Returns representation of the number as a vector.
         *
         * @return the number represented as {@link Vector MDVector}
         */
        public Vector.Double asVector() {
            return new Vector.Double(this);
        }

        @Override
        public String toString() {
            if (this.isRealNumber())
                return java.lang.Double.toString(this.real);
            if (this.isOctonion()) {
                final StringBuilder sb = new StringBuilder();
                if (this.real != 0)
                    sb.append(this.getRealPart());
                for (int i = 0; i < this.imag.length; i++) {
                    final double im = this.getImaginaryPart(i);
                    if (im != 0) {
                        if (im > 0)
                            sb.append('+');
                        sb.append(im);
                        if (i < 4)
                            sb.append((char) ('i' + i));
                        else {
                            sb.append('l');
                            sb.append('i' + i - 4);
                        }
                    }
                }
                return sb.toString();
            }
            return "!#NO_PLAIN_TEXT";
        }

        @Override
        public String toLaTeX() {
            if (this.isRealNumber())
                return java.lang.Double.toString(this.real);
            if (this.isOctonion()) {
                final StringBuilder sb = new StringBuilder();
                if (this.real != 0)
                    sb.append(this.getRealPart());
                for (int i = 0; i < this.imag.length; i++) {
                    final double im = this.imag[i];
                    if (im != 0) {
                        if (im > 0)
                            sb.append('-');
                        sb.append(im);
                        sb.append("\\mathbf{");
                        if (i < 4) sb.append((char) ('i' + i));
                        else {
                            sb.append('l');
                            sb.append('i' + i - 4);
                        }
                        sb.append('}');
                    }
                }
                return sb.toString();
            }
            return "!#NO_PLAIN_TEXT";
        }

        @SuppressWarnings("MethodDoesntCallSuperMethod")
        @Override
        public Hypercomplex.Double clone() {
            return new Hypercomplex.Double(this);
        }

        /**
         * Returns if the number is (at least approximately with given
         * {@link java.math.MathContext MathContext}) a natural number. If instead of
         * {@link java.math.MathContext MathContext} object the {@code null} value is
         * given, it returns if the number is <em>exactly</em> a natural number.
         *
         * @return if {@code this} &isin; &naturals;
         */
        public boolean isNaturalNumber() {
            return this.isInteger() && this.real > 0;
        }

        /**
         * Returns if the number is (at least approximately with given
         * {@link java.math.MathContext MathContext}) an integer. If instead of
         * {@link java.math.MathContext MathContext} object the {@code null} value is
         * given, it returns if the number is <em>exactly</em> an integer.
         */
        public boolean isInteger() {
            try {
                if (this.real != (double) ((int) this.real))
                    return false;
                return this.noImagPartFrom(0);
            } catch (final ArithmeticException exc) {
                return false;
            }
        }

        /**
         * Returns if the number is a real number
         *
         * @return if {@code this} &isin; &reals;
         *
         * @see #isComplexNumber()
         * @see #isQuaternion()
         * @see #isOctonion()
         * @see #isSedenion()
         */
        public boolean isRealNumber() {
            // All imaginary parts must be equal to zero
            return this.noImagPartFrom(0);
        }

        /**
         * Returns if the number is a complex number
         *
         * @return if {@code this} &isin; &complexes;
         *
         * @see #isRealNumber()
         * @see #isQuaternion()
         * @see #isOctonion()
         * @see #isSedenion()
         */
        public boolean isComplexNumber() {
            // Only one imaginary part
            return this.noImagPartFrom(1);
        }

        /**
         * Returns if the number is a quaternion
         *
         * @return if {@code this} &isin; &Hopf; (&Hopf; is set of all quaternions)
         *
         * @see #isRealNumber()
         * @see #isComplexNumber()
         * @see #isOctonion()
         * @see #isSedenion()
         */
        public boolean isQuaternion() {
            // 3 imaginary parts at maximum
            return this.noImagPartFrom(3);
        }

        /**
         * Returns if the number is a octonion
         *
         * @return if {@code this} &isin; &Oopf; (&Oopf; is set of all octonions)
         *
         * @see #isRealNumber()
         * @see #isComplexNumber()
         * @see #isQuaternion()
         * @see #isSedenion()
         */
        public boolean isOctonion() {
            // 7 imaginary parts at maximum
            return this.noImagPartFrom(8);
        }

        /**
         * Returns if the number is a sedenion
         *
         * @return if {@code this} &isin; &Sopf; (&Sopf; is set of all sedenions)
         *
         * @see #isRealNumber()
         * @see #isComplexNumber()
         * @see #isQuaternion()
         * @see #isOctonion()
         */
        public boolean isSedenion() {
            // 15 imaginary parts at maximum
            return this.noImagPartFrom(15);
        }

        @Override
        public int compareTo(final Hypercomplex.Double num) {
            int c = java.lang.Double.compare(this.real, num.real);
            if (c != 0)
                return c;
            for (int i = 0; i < max(this.getImaginaryPartsCount(), num.getImaginaryPartsCount()); i++) {
                c = java.lang.Double.compare(this.getImaginaryPart(i), num.getImaginaryPart(i));
                if (c != 0)
                    return c;
            }
            return c;
        }

        // TODO Javadoc
        public strictfp double magnitude() {
            double s = this.real * this.real;
            for (final double v : this.imag) s += v * v;
            return Math.sqrt(s);
        }

        // TODO Javadoc
        public strictfp Hypercomplex.Double conjugate() {
            final double[] im = new double[this.imag.length];
            for (int i = 0; i < im.length; i++)
                im[i] = -this.imag[i];
            return new Hypercomplex.Double(this.real, im);
        }

        /**
         * Adds this hypercomplex number with another hypercomplex number.
         *
         * @param augend the other hypercomplex to be added
         * @return {@code this + augend}
         */
        public strictfp Hypercomplex.Double add(final Hypercomplex.Double augend) {
            final int imagcount = max(this.imag.length, augend.imag.length);

            final double real = this.real + augend.real;
            final double[] imag = new double[imagcount];
            for (int i = 0; i < imagcount; i++)
                imag[i] = this.getImaginaryPart(i) + augend.getImaginaryPart(i);
            return new Hypercomplex.Double(real, imag);
        }

        /**
         * Substracts this hypercomplex number and the other hypercomplex number.
         *
         * @param subtrahend the other hypercomplex to be subtracted
         * @return {@code this - subtrahend}
         */
        public strictfp Hypercomplex.Double subtract(final Hypercomplex.Double subtrahend) {
            final int imagcount = max(this.imag.length, subtrahend.imag.length);

            final double real = this.real - subtrahend.real;
            final double[] imag = new double[imagcount];
            for (int i = 0; i < imagcount; i++)
                imag[i] = this.getImaginaryPart(i) - subtrahend.getImaginaryPart(i);
            return new Hypercomplex.Double(real, imag);
        }

        /**
         * Returns the {@code n}-th root of the complex number
         *
         * @param n the grade of root
         * @return {@code n}-th root of {@code this}
         * @throws IllegalStateException if the is not a complex number, e.g. the number
         *                               has more than one imaginary components
         *
         * @see #isComplexNumber()
         */
        public strictfp Hypercomplex.Double root(final int n) {
            if (!this.isComplexNumber())
                throw new IllegalStateException("Cannot compute root of non-complex number");

            final double newAngle = Math.atan(this.imag[0] / this.real);
            final double newMagnitude = MathUtilities.realRoot(this.magnitude(), n);

            final double re = Math.cos(newAngle) * newMagnitude;
            final double im = Math.sin(newAngle) * newMagnitude;

            return new Hypercomplex.Double(re, im);
        }

        /**
         * Returns square root of the complex number. Shorthand for {@code root(2)}.
         *
         * @return square root of {@code this}
         * @throws IllegalStateException if the is not a complex number, e.g. the number
         *                               has more than one imaginary components
         *
         * @see #isComplexNumber()
         * @see #root(int)
         * @see #cbrt()
         */
        public Hypercomplex.Double sqrt() {
            return this.root(2);
        }

        /**
         * Returns cube root of the complex number.
         *
         * @return cube root of {@code this}
         * @throws IllegalStateException if the is not a complex number, e.g. the number
         *                               has more than one imaginary components
         *
         * @see #isComplexNumber()
         * @see #root(int)
         * @see #sqrt()
         */
        public Hypercomplex.Double cbrt() {
            return this.root(3);
        }

        private boolean noImagPartFrom(final int startIndex) {
            for (int i = startIndex; i < this.imag.length; i++)
                if (this.imag[i] != 0)
                    return false;
            return true;
        }
    }
}

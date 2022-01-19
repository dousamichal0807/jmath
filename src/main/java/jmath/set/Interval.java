package jmath.set;

import jmath.Hypercomplex;
import jmath.set.NumberSet;

import java.math.BigDecimal;

/**
 * This class represents an interval of real numbers.
 */
public class Interval extends NumberSet {
    private static final long serialVersionUID = -6968734956109470082L;

    private final boolean leftClosed;
    private final boolean rightClosed;
    private final BigDecimal leftBound;
    private final BigDecimal rightBound;

    /**
     * Custructs an interval
     *
     * @param leftClosed  if the interval shoud be left-closed
     * @param leftBound   the left bound of the interval; pass {@code null} for negative infinity.
     * @param rightBound  the right bound of the interval; pass {@code null} for positive infinity.
     * @param rightClosed if the interval shoud be right-closed
     */
    public Interval(final boolean leftClosed, final BigDecimal leftBound, final BigDecimal rightBound, final boolean rightClosed) {
        if (leftBound != null && rightBound != null && leftBound.compareTo(rightBound) >= 0)
            throw new IllegalArgumentException("Left bound is not less than right bound of an interval");
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.leftClosed = this.leftBound != null && leftClosed;
        this.rightClosed = rightBound != null && rightClosed;
    }

    /**
     * Returns, if the interval is left-closed.
     *
     * @return if the interval is left-closed
     */
    public boolean isLeftClosed() {
        return this.leftClosed;
    }

    /**
     * Returns, if the interval is right-closed
     *
     * @return if the interval is right-closed
     */
    public boolean isRightClosed() {
        return this.rightClosed;
    }

    public BigDecimal getMinimumBound() {
        return this.leftBound;
    }

    public BigDecimal getMaximumBound() {
        return this.rightBound;
    }

    @Override
    public boolean contains(final Hypercomplex number) {
        if (!number.isRealNumber())
            return false;
        return this.contains(number.getRealPart());
    }

    public boolean contains(final BigDecimal n) {
        final boolean b1 = this.leftBound == null || (this.leftClosed ? n.compareTo(this.leftBound) >= 0 : n.compareTo(this.leftBound) > 0);
        final boolean b2 = this.rightBound == null || (this.rightClosed ? n.compareTo(this.rightBound) <= 0 : n.compareTo(this.rightBound) < 0);
        return b1 && b2;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        if (this.leftBound == null && this.rightBound == null)
            return "R";
        final StringBuilder sb = new StringBuilder();
        sb.append(this.leftClosed ? '[' : '(');
        sb.append(this.leftBound == null ? "\u2013\u221E" : this.leftBound);
        sb.append("; ");
        sb.append(this.rightBound == null ? "+\u221E" : this.rightBound);
        sb.append(this.rightClosed ? ']' : ')');
        return sb.toString();
    }

    @Override
    @SuppressWarnings("HardcodedFileSeparator")
    public String toLaTeX() {
        if (this.leftBound == null && this.rightBound == null)
            return "\\mathbb{R}";
        final StringBuilder sb = new StringBuilder();
        sb.append(this.leftClosed ? "\\left[" : "\\left(");
        sb.append(this.leftBound == null ? "-\\infty" : this.leftBound);
        sb.append("; ");
        sb.append(this.rightBound == null ? "+\\infty" : this.rightBound);
        sb.append(this.rightClosed ? "\\right]" : "\\right)");
        return sb.toString();
    }

    public static final class Double extends NumberSet.Double {
        private static final long serialVersionUID = 0x0100L;

        private final boolean fromLeftSideClosed, fromRightSideClosed;
        private final double leftBound;
        private final double rightBound;

        public Double(final boolean lc, final double min, final double max, final boolean rc) {
            if (min >= max)
                throw new IllegalArgumentException("Left bound is not less than right bound of an interval");
            this.leftBound = min;
            this.rightBound = max;
            this.fromLeftSideClosed = lc;
            this.fromRightSideClosed = rc;
        }

        public boolean isFromLeftSideClosed() {
            return this.fromLeftSideClosed;
        }

        public boolean isFromRightSideClosed() {
            return this.fromRightSideClosed;
        }

        public double getMinimumBound() {
            return this.leftBound;
        }

        public double getMaximumBound() {
            return this.rightBound;
        }

        @Override
        public boolean contains(final Hypercomplex.Double number) {
            if (!number.isRealNumber())
                return false;
            return this.contains(number.getRealPart());
        }

        @SuppressWarnings("FloatingPointEquality")
        public boolean contains(final double n) {
            final boolean b1 = this.leftBound == java.lang.Double.MIN_VALUE || (this.fromLeftSideClosed ? n >= this.leftBound : n > this.leftBound);
            final boolean b2 = this.rightBound == java.lang.Double.MAX_VALUE || (this.fromRightSideClosed ? n <= this.leftBound : n < this.leftBound);
            return b1 && b2;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        @SuppressWarnings("FloatingPointEquality")
        public String toString() {
            if (this.leftBound == java.lang.Double.MIN_VALUE && this.rightBound == java.lang.Double.MAX_VALUE)
                return "R";
            final StringBuilder sb = new StringBuilder();
            sb.append(this.fromLeftSideClosed ? '[' : '(');
            sb.append(this.leftBound == java.lang.Double.MIN_VALUE ? "\u2013\u221E" : this.leftBound);
            sb.append("; ");
            sb.append(this.rightBound == java.lang.Double.MAX_VALUE ? "+\u221E" : this.rightBound);
            sb.append(this.fromRightSideClosed ? ']' : ')');
            return sb.toString();
        }

        @Override
        @SuppressWarnings({"HardcodedFileSeparator", "FloatingPointEquality"})
        public String toLaTeX() {
            if (this.leftBound == java.lang.Double.MIN_VALUE && this.rightBound == java.lang.Double.MAX_VALUE)
                return "\\mathbb{R}";
            final StringBuilder sb = new StringBuilder();
            sb.append(this.fromLeftSideClosed ? "\\left[" : "\\left(");
            sb.append(this.leftBound == java.lang.Double.MIN_VALUE ? "-\\infty" : this.leftBound);
            sb.append("; ");
            sb.append(this.rightBound == java.lang.Double.MAX_VALUE ? "+\\infty" : this.rightBound);
            sb.append(this.fromRightSideClosed ? "\\right]" : "\\right)");
            return sb.toString();
        }
    }
}

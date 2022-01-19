package jmath.geom3d;

import static java.util.Objects.requireNonNull;

/**
 * Represents a line in 3D space. Uses high-precision
 * {@link java.math.BigDecimal BigDecimal}s. If you want to use {@code double}, use
 * {@link LineSegment3D.Double} instead.
 *
 * @see #LineSegment3D(Point3D, Point3D)
 * @see #getPointFrom()
 * @see #getPointTo()
 */
public final class LineSegment3D {

    private final Point3D from;
    private final Point3D to;

    /**
     * Construct a 3D line from point where the line started and ended
     *
     * @param from point where the line starts
     * @param to   point where the line ends
     */
    public LineSegment3D(final Point3D from, final Point3D to) {
        requireNonNull(from, "None of given points to construct line can be null");
        requireNonNull(to, "None of given points to construct line can be null");
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the 3D point where the line starts.
     *
     * @return the 3D point where the line starts
     */
    public Point3D getPointFrom() {
        return this.from;
    }

    /**
     * Returns the 3D point where the line ends.
     *
     * @return the 3D point where the line ends
     */
    public Point3D getPointTo() {
        return this.to;
    }

    /**
     * Represents a line in 3D space. Uses {@code double} data type. If you want to
     * use high-precision {@link java.math.BigDecimal BigDecimal}s, use {@link LineSegment3D} instead.
     *
     * @see #Double(Point3D.Double, Point3D.Double)
     * @see #getPointFrom()
     * @see #getPointTo()
     */
    public static final class Double {

        private final Point3D.Double from, to;

        /**
         * Construct a 3D line from point where the line started and ended
         *
         * @param from point where the line started
         * @param to   point where the line ended
         */
        public Double(final Point3D.Double from, final Point3D.Double to) {
            requireNonNull(from, "None of given points to construct line can be null");
            requireNonNull(to, "None of given points to construct line can be null");
            this.from = from;
            this.to = to;
        }

        /**
         * Returns the 3D point where the line starts.
         *
         * @return the 3D point where the line starts
         */
        public Point3D.Double getPointFrom() {
            return this.from;
        }

        /**
         * Returns the 3D point where the line ends.
         *
         * @return the 3D point where the line ends
         */
        public Point3D.Double getPointTo() {
            return this.to;
        }
    }
}

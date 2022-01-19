package jmath.geom3d;

import java.math.BigDecimal;
import java.util.Objects;

import jmath.Vector;

/**
 * This class represents a point in three-dimensional Cartesian coordinate system.
 *
 * @see #Point3D(BigDecimal, BigDecimal, BigDecimal)
 * @see #getX()
 * @see #getY()
 * @see #getZ()
 */
public final class Point3D {
	/**
	 * The origin in the Cartesian coordinate system.
	 */
	public static final Point3D ORIGIN = new Point3D(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

	private final BigDecimal x;
    private final BigDecimal y;
    private final BigDecimal z;

	/**
	 * Gets the <var>x</var>-coordinate of the 3D point.
	 * 
	 * @return <var>x</var>-coordinate of the 3D point
	 */
	public BigDecimal getX() {
		return this.x;
	}

	/**
	 * Gets the <var>y</var>-coordinate of the 3D point.
	 * 
	 * @return <var>y</var>-coordinate of the 3D point
	 */
	public BigDecimal getY() {
		return this.y;
	}

	/**
	 * Gets the <var>z</var>-coordinate of the 3D point.
	 * 
	 * @return <var>z</var>-coordinate of the 3D point
	 */
	public BigDecimal getZ() {
		return this.z;
	}
	
	/**
	 * Converts a 3D point into a {@link Vector}.
	 * 
	 * @return converted point into a {@link Vector}
	 */
	public Vector asVector() {
		return new Vector(this.x, this.y, this.z);
	}

	/**
	 * Constructs a 3D point using <var>x</var>, <var>y</var> and
	 * <var>z</var>-coordinates.
	 * 
	 * @param x <var>x</var>-coordinate of the 3D point
	 * @param y <var>y</var>-coordinate of the 3D point
	 * @param z <var>z</var>-coordinate of the 3D point
	 */
	public Point3D(final BigDecimal x, final BigDecimal y, final BigDecimal z) {
		Objects.requireNonNull(x, "All coordinates (x, y, z) must be non-null");
		Objects.requireNonNull(y, "All coordinates (x, y, z) must be non-null");
		Objects.requireNonNull(z, "All coordinates (x, y, z) must be non-null");

		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * This class represents a point in three-dimensional Cartesian coordinate system.
	 *
	 * @see #getX()
	 * @see #getY()
	 * @see #getZ()
	 */
	public static final class Double {

		/**
		 * The origin in the Cartesian coordinate system.
		 */
		public static final Point3D.Double ORIGIN = new Point3D.Double(0.0, 0.0, 0.0);

		private final double x;
        private final double y;
        private final double z;

		/**
		 * Gets the <var>x</var>-coordinate of the 3D point.
		 * 
		 * @return <var>x</var>-coordinate of the 3D point
		 */
		public double getX() {
			return this.x;
		}

		/**
		 * Gets the <var>y</var>-coordinate of the 3D point.
		 * 
		 * @return <var>y</var>-coordinate of the 3D point
		 */
		public double getY() {
			return this.y;
		}

		/**
		 * Gets the <var>z</var>-coordinate of the 3D point.
		 * 
		 * @return <var>z</var>-coordinate of the 3D point
		 */
		public double getZ() {
			return this.z;
		}
		
		/**
		 * Converts a 3D point into a {@link Vector.Double}.
		 * 
		 * @return converted point into a {@link Vector.Double}
		 */
		public Vector.Double asVector() {
			return new Vector.Double(this.x, this.y, this.z);
		}

		/**
		 * Constructs a 3D point using <var>x</var>, <var>y</var> and
		 * <var>z</var>-coordinates.
		 * 
		 * @param x <var>x</var>-coordinate of the 3D point
		 * @param y <var>y</var>-coordinate of the 3D point
		 * @param z <var>z</var>-coordinate of the 3D point
		 */
		public Double(final double x, final double y, final double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}

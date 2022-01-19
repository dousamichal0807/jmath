package jmath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Objects;

/**
 * <p>
 * Represents mathematical matrix of {@link BigDecimal}s.
 * </p>
 * <p>
 * <b>Attention!</b> Rows and columns are numbered from zero!
 * </p>
 * 
 * @author Michal Douša
 *
 */
public final class Matrix implements Cloneable, MathEntity {
	
	private static final long serialVersionUID = 0x0100L;

	private final BigDecimal[][] data;

	public Matrix(final BigDecimal[][] data) {
		Matrix.checkMatrixData(data);
		this.data = new BigDecimal[data.length][data[0].length];
		for (int row = 0; row < data.length; row++)
            for (int col = 0; col < data[0].length; col++) {
                final BigDecimal value = data[row][col];
                if (value == null)
                    throw new NullPointerException(new StringBuilder()
							.append("Null at row ")
							.append(row)
							.append(" column ")
							.append(col)
							.toString());
                this.data[row][col] = value;
            }
	}

	public Matrix(final Hypercomplex[][] data) {
		Matrix.checkMatrixData(data);
		this.data = new BigDecimal[data.length][data[0].length];
		for (int row = 0; row < data.length; row++)
            for (int col = 0; col < data[0].length; col++) {
                final Hypercomplex value = data[row][col];
                if (value == null)
                    throw new NullPointerException("Null at row " + row + " column " + col);
                this.data[row][col] = new BigDecimal(value.toString());
            }
	}

	/**
	 * Constructs a deep copy of given matrix. Hase same effect as using the
	 * {@link #clone()} method.
	 * 
	 * @param mtx matrix to be cloned
	 */
	public Matrix(final Matrix mtx) {
		Objects.requireNonNull(mtx, "Cannot pass null as argument");
		this.data = mtx.data.clone();
	}

	/**
	 * Creates a sigle-column matrix using given {@link Vector}. Every
	 * <var>n</var><sup>th</sup> coordinate of a {@link Vector} will be stored in
	 * matrix <var>n</var><sup>th</sup> row and first column (at index 0).
	 * 
	 * @param vectors {@link Vector} to create a matrix from
	 * 
	 * @throws NullPointerException if {@code null} is given
	 */
	public Matrix(final Vector... vectors) {
		final int rows = vectors[0].size();
		final int cols = vectors.length;
		this.data = new BigDecimal[rows][cols];
		for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++) this.data[row][col] = vectors[col].get(row);
		Matrix.checkMatrixData(this.data);
	}

	/**
	 * Gets a number located at specific position
	 * 
	 * @param row    the number of row
	 * @param column the number of column
	 * 
	 * @return <var>a</var><sub><var>r</var><var>c</var></sub>
	 */
	public BigDecimal get(final int row, final int column) {
		return this.data[row][column];
	}

	public int rows() {
		return this.data.length;
	}

	public int columns() {
		return this.data[0].length;
	}
	
	public Vector columnAsVector(final int column) {
		final BigDecimal[] vector = new BigDecimal[this.rows()];
		for (int row = 0; row < vector.length; row++)
			vector[row] = this.data[row][column];
		return new Vector(vector);
	}

	public boolean isSquareMatrix() {
		return this.rows() == this.columns();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int i = 0; i < this.data.length; i++) {
			sb.append(Arrays.toString(this.data[i]));
			if (i < this.data.length - 1)
				sb.append(", ");
		}
		sb.append(']');
		return sb.toString();
	}

	@SuppressWarnings("HardcodedFileSeparator")
	@Override
	public String toLaTeX() {
		final StringBuilder sb = new StringBuilder();
		sb.append("\\left[ \\begin{align*} ");
		for (int i = 0; i < this.rows(); i++)
            for (int j = 0; j < this.columns(); j++) {
                sb.append(this.data[i][j]);
                if (j < this.columns() - 1)
                    sb.append(" & ");
                else if (i < this.rows() - 1)
                    sb.append(" \\\\ ");
            }
		sb.append(" \\end{align*} \\right]");
		return sb.toString();
	}

	@SuppressWarnings("MethodDoesntCallSuperMethod")
	@Override
	public Matrix clone() {
		return new Matrix(this);
	}

	public Matrix add(final Matrix mtx2, final MathContext mc) {
		if (this.rows() != mtx2.rows() || this.columns() != mtx2.columns())
			throw new IllegalArgumentException("Different matrix sizes");

		final BigDecimal[][] data = new BigDecimal[this.rows()][this.columns()];
		for (int r = 0; r < this.rows(); r++)
            for (int c = 0; c < this.columns(); c++) data[r][c] = this.get(r, c).add(mtx2.get(r, c), mc);
		return new Matrix(data);
	}

	public Matrix subtract(final Matrix mtx2, final MathContext mc) {
		if (this.rows() != mtx2.rows() || this.columns() != mtx2.columns())
			throw new IllegalArgumentException("Different matrix sizes");

		final BigDecimal[][] data = new BigDecimal[this.rows()][this.columns()];
		for (int r = 0; r < this.rows(); r++)
            for (int c = 0; c < this.columns(); c++) data[r][c] = this.get(r, c).subtract(mtx2.get(r, c), mc);
		return new Matrix(data);
	}

	public Matrix multiply(final Matrix mtx2, final MathContext mc) {
		if (this.columns() != mtx2.rows())
			throw new IllegalArgumentException("Cannot multiply given matrices");

		final BigDecimal[][] data = new BigDecimal[this.rows()][mtx2.columns()];
		for (int row = 0; row < this.rows(); row++)
            for (int col = 0; col < mtx2.columns(); col++) {
                BigDecimal sum = BigDecimal.ZERO;
                for (int k = 0; k < this.columns(); k++)
                    sum = sum.add(this.get(row, k).multiply(mtx2.get(k, col)), mc);
                data[row][col] = sum;
            }

		return new Matrix(data);
	}
	
	public BigDecimal determinant(final MathContext mc) {
		// TODO determinant
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public Matrix pow(final int power, final MathContext mc) {
		if (!this.isSquareMatrix())
			throw new IllegalStateException("Matrix is not a square matrix");
		Matrix result = this;
		for (int i = 1; i < power; i++)
			result = this.multiply(result, mc);
		return result;
	}

	private static void checkMatrixData(final Object[][] data) {
		if (data.length == 0)
			throw new IllegalArgumentException("2D array is empty");
		final int w = data[0].length;
		for (final Object[] row : data) {
			if (row.length != w || w == 0 || (w == 1 && data.length == 1))
				throw new IllegalArgumentException("2D array is inconsistent");
			for (final Object cell : row)
				if (cell == null)
					throw new IllegalArgumentException("There is a null value in the 2D array");
		}
	}
	
	private static void checkMatrixData(final double[][] data2) {
		if (data2.length == 0)
			throw new IllegalArgumentException("2D array is empty");
		final int w = data2[0].length;
		for (final double[] row : data2)
            if (row.length != w || w == 0 || (w == 1 && data2.length == 1))
                throw new IllegalArgumentException("2D array is inconsistent");
	}
	
	// Double class ----------------------------------------------------------------

	public static final class Double implements Cloneable, MathEntity {
		
		private static final long serialVersionUID = 0x0100L;
		
		private final double[][] data;

		public Double(final double[][] data) {
			Matrix.checkMatrixData(data);
			this.data = new double[data.length][data[0].length];
			for (int row = 0; row < data.length; row++) this.data[row] = Arrays.copyOf(data[row], data[row].length);
		}

		/**
		 * Constructs a deep copy of given matrix. Hase same effect as using the
		 * {@link #clone()} method.
		 * 
		 * @param mtx matrix to be cloned
		 */
		public Double(final Matrix.Double mtx) {
			Objects.requireNonNull(mtx, "Cannot pass null as argument");
			this.data = mtx.data.clone();
		}

		/**
		 * Creates a sigle-column matrix using given {@link Vector}. Every
		 * <var>n</var><sup>th</sup> coordinate of a {@link Vector} will be stored in
		 * matrix <var>n</var><sup>th</sup> row and first column (at index 0).
		 * 
		 * @param vectors {@link Vector}s to create a matrix from
		 * 
		 * @throws NullPointerException if {@code null} is given
		 */
		public Double(final Vector.Double... vectors) {
			final int rows = vectors[0].coordinates();
			final int cols = vectors.length;
			this.data = new double[rows][cols];
			for (int row = 0; row < rows; row++)
                for (int col = 0; col < cols; col++) this.data[row][col] = vectors[col].get(row);
			Matrix.checkMatrixData(this.data);
		}

		/**
		 * Gets a number located at specific position
		 * 
		 * @param row    the number of row
		 * @param column the number of column
		 * 
		 * @return <var>a</var><sub><var>r</var><var>c</var></sub>
		 */
		public double get(final int row, final int column) {
			return this.data[row][column];
		}
		
		public int rows() {
			return this.data.length;
		}

		public int columns() {
			return this.data[0].length;
		}
		
		public Vector.Double columnAsVector(final int column) {
			final double[] vector = new double[this.rows()];
			for (int row = 0; row < vector.length; row++)
				vector[row] = this.data[row][column];
			return new Vector.Double(vector);
		}

		public boolean isSquareMatrix() {
			return this.rows() == this.columns();
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			sb.append('[');
			for (int i = 0; i < this.data.length; i++) {
				sb.append(Arrays.toString(this.data[i]));
				if (i < this.data.length - 1)
					sb.append(", ");
			}
			sb.append(']');
			return sb.toString();
		}
		
		@Override
		public String toLaTeX() {
			final StringBuilder sb = new StringBuilder();
			sb.append("\\left[ \\begin{align*} ");
			for (int i = 0; i < this.rows(); i++)
                for (int j = 0; j < this.columns(); j++) {
                    sb.append(this.data[i][j]);
                    if (j < this.columns() - 1)
                        sb.append(" & ");
                    else if (i < this.rows() - 1)
                        sb.append(" \\\\ ");
                }
			sb.append(" \\end{align*} \\right]");
			return sb.toString();
		}
		
		public strictfp Double add(final Double mtx2) {
			if (this.rows() != mtx2.rows() || this.columns() != mtx2.columns())
				throw new IllegalArgumentException("Different matrix sizes");

			final double[][] data = new double[this.rows()][this.columns()];
			for (int r = 0; r < this.rows(); r++)
                for (int c = 0; c < this.columns(); c++) data[r][c] = this.get(r, c) + mtx2.get(r, c);
			return new Double(data);
		}

		public strictfp Double subtract(final Double mtx2) {
			if (this.rows() != mtx2.rows() || this.columns() != mtx2.columns())
				throw new IllegalArgumentException("Different matrix sizes");

			final double[][] data = new double[this.rows()][this.columns()];
			for (int r = 0; r < this.rows(); r++)
                for (int c = 0; c < this.columns(); c++) data[r][c] = this.get(r, c) - mtx2.get(r, c);
			return new Double(data);
		}

		public strictfp Double multiply(final Double mtx2) {
			if (this.columns() != mtx2.rows())
				throw new IllegalArgumentException("Cannot multiply given matrices");

			final double[][] data = new double[this.rows()][mtx2.columns()];
			for (int row = 0; row < this.rows(); row++)
                for (int col = 0; col < mtx2.columns(); col++) {
                    double sum = .0;
                    for (int k = 0; k < this.columns(); k++)
                        sum += this.get(row, k) * mtx2.get(k, col);
                    data[row][col] = sum;
                }

			return new Double(data);
		}
		
		public strictfp double determinant() {
			// TODO determinant()
			throw new UnsupportedOperationException("Not yet implemented");
		}

		public strictfp Double pow(final int power, final MathContext mc) {
			if (!this.isSquareMatrix())
				throw new IllegalStateException("Matrix is not a square matrix");
			Double powMtx = this;
			for (int i = 1; i < power; i++)
				powMtx = powMtx.multiply(this);
			return powMtx;
		}

	}

}

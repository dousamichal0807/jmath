package jmath;

import java.io.Serializable;

public interface MathEntity extends Serializable {
	@Override
	String toString();
	String toLaTeX();
}

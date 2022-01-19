package jmath.set;


import jmath.Hypercomplex;
import jmath.Matrix;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.function.Consumer;

/**
 * <p>A set with finite count of {@link Hypercomplex}s.
 * @author Michal Douša
 * @see Interval
 * @see Matrix
 */
public class NumberFiniteSet extends NumberSet implements Iterable<Hypercomplex> {
	private static final long serialVersionUID = 0x0100L;
	
	private final TreeSet<Hypercomplex> elements;

	@Override
	public boolean contains(final Hypercomplex n) {
		return elements.contains(n);
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	public NumberFiniteSet(final Collection<Hypercomplex> collection) {
		elements = new TreeSet<>();
		elements.addAll(collection);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Hypercomplex> it = elements.iterator();
		
		sb.append("{");
		while(it.hasNext()) {
			sb.append(it.next());
			if(it.hasNext())
				sb.append("; ");
		}
		sb.append("}");
		
		return sb.toString();
	}
	
	@Override
	public String toLaTeX() {
		StringBuilder sb = new StringBuilder();
		Iterator<Hypercomplex> it = elements.iterator();
		
		sb.append("\\left\\{");
		while(it.hasNext()) {
			sb.append(it.next().toLaTeX());
			if(it.hasNext())
				sb.append("; ");
		}
		sb.append("\\right\\}");

		return sb.toString();
	}

	@Override
	public Iterator<Hypercomplex> iterator() {
		return elements.iterator();
	}

	@Override
	public void forEach(final Consumer<? super Hypercomplex> action) {
		elements.forEach(action);
	}

	@Override
	public Spliterator<Hypercomplex> spliterator() {
		return elements.spliterator();
	}

	public static final class Double extends NumberSet.Double implements Iterable<Hypercomplex.Double> {
		private final TreeSet<Hypercomplex.Double> elements;

		public Double(final Collection<Hypercomplex.Double> collection) {
			elements = new TreeSet<>();
			elements.addAll(collection);
		}

		@Override
		public boolean contains(final Hypercomplex.Double number) {
			return elements.contains(number);
		}

		@Override
		public boolean isEmpty() {
			return elements.isEmpty();
		}

		@Override
		public String toString() {
			if (isEmpty())
				return "";

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append('{');

			Iterator<Hypercomplex.Double> iterator = elements.iterator();
			while(iterator.hasNext()) {
				Hypercomplex.Double next = iterator.next();
				stringBuilder.append(next);
				if (iterator.hasNext())
					stringBuilder.append("; ");
			}

			return stringBuilder.toString();
		}

		@Override
		public String toLaTeX() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("\\left\\{");
			Iterator<Hypercomplex.Double> iterator = elements.iterator();
			while (iterator.hasNext()) {
				stringBuilder.append(iterator.next());
				if (iterator.hasNext())
					stringBuilder.append("; ");
			}
			stringBuilder.append("\\right\\}");
			return stringBuilder.toString();
		}

		@Override
		public Iterator<Hypercomplex.Double> iterator() {
			return elements.iterator();
		}

		@Override
		public void forEach(final Consumer<? super Hypercomplex.Double> action) {
			elements.forEach(action);
		}

		@Override
		public Spliterator<Hypercomplex.Double> spliterator() {
			return elements.spliterator();
		}
	}
}

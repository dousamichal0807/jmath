package jmath.set;

import jmath.Hypercomplex;
import jmath.MathEntity;

public abstract class NumberSet implements MathEntity {

    protected NumberSet() {
    }

    /**
     * Returns, if the set contains particular value.
     *
     * @param number the number to be tested if it is in the set
     * @return if {@code number} is found in the set
     */
    public abstract boolean contains(Hypercomplex number);

    /**
     * Returns, if the set is an empty set.
     *
     * @return if is an empty set
     */
    public abstract boolean isEmpty();

    public abstract static class Double implements MathEntity {

        protected Double() {
        }

        /**
         * Returns, if the set contains particular number.
         *
         * @param number the number to be tested if it is in the set
         * @return if {@code number} is found in the set
         */
        public abstract boolean contains(Hypercomplex.Double number);

        /**
         * Returns, if the set is an empty set.
         *
         * @return if is an empty set
         */
        public abstract boolean isEmpty();
    }
}

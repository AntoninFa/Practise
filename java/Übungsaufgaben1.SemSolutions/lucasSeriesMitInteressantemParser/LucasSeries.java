/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;

/**
 * A series calculated as {@code f_i = p * f_(i-1) - q * f_(i-2)}, where {@code q} and {@code p} are integers. This
 * class calculates the series for the parameters {@code p} and {@code q} given at construction. {@link #calculate(int)}
 * and {@link #calculateUpTo(int)} can be used to retrieve members of the series. Members are cached and used for later
 * calculation.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 */
public class LucasSeries {

    /*-
     * Sample Solution Note: 
     * Using a Map<Integer, Long>, mapping indices to values, would be possible here, too. The advantage of a list is
     * that values are "automatically" sorted by their indices.
     */
    private List<Long> elements = new ArrayList<>();
    private int p;
    private int q;

    /**
     * Creates a new lucas series using the given parameters {@code p} and {@code q}.
     * 
     * @param p
     *            The {@code p} parameter for this lucas series.
     * @param q
     *            The {@code q} parameter for this lucas series.
     */
    public LucasSeries(int p, int q) {
        this.p = p;
        this.q = q;
        elements.add(0L);
    }

    /**
     * Calculates all members of this series up to index {@code i} and returns all of them in an array.
     * 
     * @param i
     *            The index up to which this series' elements will be calculated.
     * @return An array {@code a} containing all series members up to index {@code i}. For any index {@code 0 <= j <= i}
     *         , it's granted that {@code a [j]} will be the {@code j}th member of this series.
     */
    public Long[] calculateUpTo(int i) {
        calculate(i);
        return elements.toArray(new Long[i + 1]);
    }

    /**
     * Calculates the series element at index {@code i}.
     * 
     * @param i
     *            The index of the series member that is to be calculated. Obviously, it has to be positive or 0.
     * @return The member {@code i} of this series.
     */
    public long calculate(int i) {
        if (i >= elements.size()) {
            long result = (i != 1) ? p * calculate(i - 1) - q * calculate(i - 2) : 1;
            /*-
             * Sample Solution Note: 
             * This add will effectively occur in the order of the indices. That's why every series element will be
             * placed at the (correct) corresponding index in the elements list. (And why we are able to use a list
             * instead of a map in the first place).
             */
            elements.add(result);
            return result;
        } else {
            return elements.get(i);
        }
    }
}

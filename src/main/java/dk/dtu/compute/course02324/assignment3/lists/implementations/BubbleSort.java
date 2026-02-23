package dk.dtu.compute.course02324.assignment3.lists.implementations;

import dk.dtu.compute.course02324.assignment3.lists.types.List;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;

/**
 * impl: helper class that performs bubble sort on generic lists.
 */
public class BubbleSort {

    /**
     * impl: sort the given list using bubble sort and the provided comparator.
     * neighbours are compared and swapped until no swaps are needed.
     *
     * @param comp comparator that defines the order
     * @param list list to sort (modified in-place)
     * @param <T>  element type
     */
    public static <T> void sort(@NotNull Comparator<? super T> comp, @NotNull List<T> list) {
        // impl: check arguments so we do not run with null comparator or list
        if (comp == null || list == null) {
            throw new IllegalArgumentException("arguments of the sort function must not be null");
        }

        boolean swapped;
        int n = list.size(); // impl: number of elements we need to bubble through

        // impl: repeat passes over the list while swaps are happening
        do {
            swapped = false;

            // impl: go through all neighbour pairs and swap if they are in wrong order
            for (int i = 0; i < n - 1; i++) {
                T a = list.get(i);
                T b = list.get(i + 1);

                if (comp.compare(a, b) > 0) {
                    // impl: swap a and b using set, so smaller element comes first
                    list.set(i, b);
                    list.set(i + 1, a);
                    swapped = true;
                }
            }
        } while (swapped);
    }

}

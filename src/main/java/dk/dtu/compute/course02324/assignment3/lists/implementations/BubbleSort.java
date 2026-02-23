package dk.dtu.compute.course02324.assignment3.lists.implementations;

import dk.dtu.compute.course02324.assignment3.lists.types.List;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;

/**
 * This is implementing a simplistic sorting algorithm based on the
 * Bubble Sort algorithm in a generic way. The actual sorting algorithm
 * is implemented as generic static method.
 */
public class BubbleSort {

    /**
     * Generic method for sorting a list of type Y according to a comparator.
     * The implementation is based on the BubbleSort algorithm, shown
     * in the lectures of the course Advanced Programming (02324) and
     * Project in Software Development (02362), adjusted from arrays to
     * lists and using the comparator instead of direct comparison of
     * integer values.
     *
     * @param comp the comparator
     * @param list the list
     * @param <T>  the type of the list
     */
    
    public static <T> void sort(@NotNull Comparator<? super T> comp, @NotNull List<T> list) {
        // check arguments so we do not run with null comparator or list
       
        if (comp == null || list == null) {
       
            throw new IllegalArgumentException("arguments of the sort function must not be null");
       
        }

        boolean swapped;
       
        int n = list.size(); // number of elements we need to bubble through

        // repeat passes over the list while swaps are happening
       
        do {
            swapped = false;


            // go through all neighbour pairs and swap if they are in wrong order

            for (int i = 0; i < n - 1; i++) {

                T a = list.get(i);
                T b = list.get(i + 1);


                if (comp.compare(a, b) > 0) {

                    // swap a and b using set, so smaller element comes first
                    list.set(i, b);

                    list.set(i + 1, a);

                    swapped = true;
                }
            }

        } while (swapped);

    }

}

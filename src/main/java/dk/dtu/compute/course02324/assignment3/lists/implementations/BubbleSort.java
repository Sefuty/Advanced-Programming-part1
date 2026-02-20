package dk.dtu.compute.course02324.assignment3.lists.implementations;

import dk.dtu.compute.course02324.assignment3.lists.types.List;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;

/**
 * Implementerer bubble sort som generisk statisk metode til sortering af lister.
 */
public class BubbleSort {

    /**
     * Sorterer listen efter den rækkefølge som comparatoren angiver.
     * Bruger bubble sort (nabopar sammenlignes og byttes ved forkert rækkefølge).
     * Som i forelæsningerne, men tilpasset generisk liste og comparator.
     *
     * @param comp comparatoren der definerer rækkefølge
     * @param list listen der sorteres (ændres in-place)
     * @param <T>  elementtypen
     */
    public static <T> void sort(@NotNull Comparator<? super T> comp, @NotNull List<T> list) {
        if (comp == null || list == null) {
            throw new IllegalArgumentException("Arguments of the sort function must not be null.");
        }
        boolean swapped;
        int n = list.size();
        do {
            swapped = false;
            // gennemgå alle nabopar og byt hvis de er i forkert rækkefølge
            for (int i = 0; i < n - 1; i++) {
                T a = list.get(i);
                T b = list.get(i + 1);
                if (comp.compare(a, b) > 0) {
                    list.set(i, b);
                    list.set(i + 1, a);
                    swapped = true;
                }
            }
        } while (swapped);
    }

}

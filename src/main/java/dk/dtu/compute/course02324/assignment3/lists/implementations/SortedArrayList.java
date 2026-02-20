package dk.dtu.compute.course02324.assignment3.lists.implementations;

import dk.dtu.compute.course02324.assignment3.lists.types.List;
import dk.dtu.compute.course02324.assignment3.lists.types.SortedList;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;

/**
 * Implementering af {@link SortedList} baseret på {@link ArrayList}.
 * Listen holdes altid sorteret; add(e) indsætter på rette sted, sort/set/add(pos,e) er ikke tilladt.
 *
 * @param <E> elementtypen (skal være Comparable)
 */
public class SortedArrayList<E extends Comparable<E>> extends ArrayList<E> implements SortedList<E> {

    @Override
    public void sort(@NotNull Comparator<? super E> c) throws UnsupportedOperationException {
        // sortering er ikke tilladt på SortedList – listen er altid sorteret
        throw new UnsupportedOperationException("Operation sort(Comparator<? super E> c) not allowed on SortedLists");
    }

    @Override
    public boolean add(@NotNull E e) {
        if (e == null) {
            throw new IllegalArgumentException("Element must not be null");
        }
        int pos = findIndexToInsert(e);
        return super.add(pos, e);
    }

    /**
     * Finder den position hvor et nyt element skal indsættes så listen forbliver sorteret.
     * Går lineært gennem listen og stopper ved første element der er større eller lig med e.
     *
     * @param e det element der skal indsættes
     * @return indekset hvor elementet skal indsættes
     */
    private int findIndexToInsert(@NotNull E e) {
        for (int i = 0; i < size(); i++) {
            if (get(i).compareTo(e) >= 0) {
                return i;
            }
        }
        return size();
    }

}

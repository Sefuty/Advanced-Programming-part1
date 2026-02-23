package dk.dtu.compute.course02324.assignment3.lists.implementations;

import dk.dtu.compute.course02324.assignment3.lists.types.List;
import dk.dtu.compute.course02324.assignment3.lists.types.SortedList;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;

/**
 * An implementation of the interface {@link SortedList} based on the
 * {@link ArrayList} implementation of the interface{@link List}
 * arrays, which dynamically are adapted in size when needed.
 *
 * @param <E> the type of the list's elements.
 */
public class SortedArrayList<E extends Comparable<E>> extends ArrayList<E> implements SortedList<E> {

    @Override
   
    public void sort(@NotNull Comparator<? super E> c) throws UnsupportedOperationException {
   
        // sorting is not allowed on sorted lists, they stay sorted automatically
   
        throw new UnsupportedOperationException("Operation sort(Comparator<? super E> c) not allowed on SortedLists");
    }

    @Override
   
    public E set(int pos, @NotNull E e) throws UnsupportedOperationException {
        // replacing elements at a fixed position is not allowed on sorted lists
   
        throw new UnsupportedOperationException("Operation set(int pos, E e) not allowed on SortedLists");
    }

    @Override
    public boolean add(int pos, @NotNull E e) throws UnsupportedOperationException {
   
        // adding at an explicit position is not allowed on sorted lists
        throw new UnsupportedOperationException("Operation add(int pos, E e) not allowed on SortedLists");
    }

    @Override
    public boolean add(@NotNull E e) {
        // insert element at the position that keeps the list sorted
   
        if (e == null) {
            throw new IllegalArgumentException("element must not be null");
   
        }
        int pos = findIndexToInsert(e);
   
        return super.add(pos, e);
    }

    /**
     * Find the position where a new element should be inserted
     * so that the list remains sorted. we scan linearly and stop at
     * the first element that is greater than or equal to e.
     *
     * @param e element to insert
     * @return index where the element should be inserted
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

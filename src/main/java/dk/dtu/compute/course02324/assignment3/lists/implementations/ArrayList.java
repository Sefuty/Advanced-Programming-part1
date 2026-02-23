package dk.dtu.compute.course02324.assignment3.lists.implementations;

import dk.dtu.compute.course02324.assignment3.lists.types.List;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;

/**
 * list implementation that uses a simple array which grows when needed.
 *
 * @param <E> type of the elements stored in the list
 */
public class ArrayList<E> implements List<E> {

    
    final private int DEFAULT_SIZE = 10;

    /** current number of elements in the list */
    private int size = 0;

    /** internal array that actually stores the elements. */
    private E[] list = createEmptyArray(DEFAULT_SIZE);

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    
    public @NotNull E get(int pos) throws IndexOutOfBoundsException {
    
        if (pos < 0 || pos >= size) {
    
            throw new IndexOutOfBoundsException("Index: " + pos + ", size: " + size);
    
        }
    
        return list[pos];
    }

    @Override
    
    public E set(int pos, @NotNull E e) throws IndexOutOfBoundsException {
    
        // replace element at given position and return the old element
    
        if (e == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index: " + pos + ", size: " + size);
    
        }
        E old = list[pos]; // remember element currently stored at this position
    
        list[pos] = e;     // store the new element at this position
    
    
        return old;
    
    }

    @Override
    
    public boolean add(@NotNull E e) {
    
        if (e == null) {
    
            throw new IllegalArgumentException("Element must not be null");
    
        }


        if (size == list.length) {

            E[] newList = createEmptyArray(list.length * 2);

            for (int i = 0; i < size; i++) {

                newList[i] = list[i];

            }

            list = newList;
        }



        list[size] = e;

        size++;

        return true;

    }



    @Override

    public boolean add(int pos, @NotNull E e) throws IndexOutOfBoundsException {

        if (e == null) {

            throw new IllegalArgumentException("Element must not be null");

        }

        if (pos < 0 || pos > size) {

            throw new IndexOutOfBoundsException("Index: " + pos + ", Size: " + size);

        }


        if (size == list.length) {

            E[] newList = createEmptyArray(list.length * 2);

            for (int i = 0; i < size; i++) {

                newList[i] = list[i];
            }

            list = newList;

        }



        for (int i = size; i > pos; i--) {

            list[i] = list[i - 1];

        }

        list[pos] = e;

        size++;

        return true;

    }

    @Override

    public E remove(int pos) throws IndexOutOfBoundsException {

        // remove element at position and close the gap by shifting left
        if (pos < 0 || pos >= size) {

            throw new IndexOutOfBoundsException("index: " + pos + ", size: " + size);

        }



        E removed = list[pos];


        // shift all elements after pos one step to the left

        for (int i = pos; i < size - 1; i++) {

            list[i] = list[i + 1];

        }


        list[size - 1] = null; // clear last slot which is now unused

        size--;



        return removed;
    }


    @Override

    public boolean remove(E e) {

        if (e == null) {

            return false;

        }

        int index = indexOf(e);

        if (index == -1) {
            return false;
        }


        remove(index);

        return true;
    }

    @Override

    public int indexOf(E e) {

        if (e == null) {

            return -1;
        }

        for (int i = 0; i < size; i++) {

            if (list[i].equals(e)) {
                return i;

            }
        }

        return -1;
    }


    @Override

    public void sort(@NotNull Comparator<? super E> c) throws UnsupportedOperationException {


        if (c == null) {
            throw new IllegalArgumentException("comparator must not be null");

        }
        BubbleSort.sort(c, this);

    }

    /**

     * Create a new internal array for storing elements.

     *

     * @param length length of the new array

     * @return new array of type E with the given length

     */

    private E[] createEmptyArray(int length) {
        // there is unfortunately no really easy and elegant way to initialize
        // an array with a type coming in as a generic type parameter, but
        // the following is simple enough. And it is OK, since the array
        // is never passed out of this class.
        return (E[]) new Object[length];
    }


}

package dk.dtu.compute.course02324.assignment3.lists.implementations;

import dk.dtu.compute.course02324.assignment3.lists.types.List;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;

/**
 * impl: list implementation that uses a simple array which grows when needed.
 *
 * @param <E> type of the elements stored in the list
 */
public class ArrayList<E> implements List<E> {

    /** impl: default initial size for the internal array (for example 10 like java's arraylist). */
    final private int DEFAULT_SIZE = 10;

    /** impl: current number of elements in the list (logical size). */
    private int size = 0;

    /** impl: internal array that actually stores the elements. */
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
        // impl: replace element at given position and return the old element
        if (e == null) {
            throw new IllegalArgumentException("element must not be null");
        }
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index: " + pos + ", size: " + size);
        }
        E old = list[pos]; // impl: remember element currently stored at this position
        list[pos] = e;     // impl: store the new element at this position
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
        // impl: remove element at position and close the gap by shifting left
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index: " + pos + ", size: " + size);
        }

        E removed = list[pos];

        // impl: shift all elements after pos one step to the left
        for (int i = pos; i < size - 1; i++) {
            list[i] = list[i + 1];
        }

        list[size - 1] = null; // impl: clear last slot which is now unused
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
        // impl: delegate sorting to the generic bubble sort helper
        if (c == null) {
            throw new IllegalArgumentException("comparator must not be null");
        }
        BubbleSort.sort(c, this);
    }

    /**
     * impl: create a new internal array for storing elements.
     * generics do not allow direct creation of E[], so we use (E[]) new Object[length].
     *
     * @param length length of the new array
     * @return new array of type E with the given length
     */
    private E[] createEmptyArray(int length) {
        return (E[]) new Object[length];
    }

}

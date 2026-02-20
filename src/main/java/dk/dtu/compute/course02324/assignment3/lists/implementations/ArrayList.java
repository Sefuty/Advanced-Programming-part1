package dk.dtu.compute.course02324.assignment3.lists.implementations;

import dk.dtu.compute.course02324.assignment3.lists.types.List;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;

/**
 * Implementering af {@link List} med et array der udvides dynamisk ved behov.
 *
 * @param <E> elementtypen i listen
 */
public class ArrayList<E> implements List<E> {

    /** Standard startstørrelse for det interne array (fx 10 som i Java's ArrayList). */
    final private int DEFAULT_SIZE = 10;

    /** Antal elementer i listen (logisk størrelse). */
    private int size = 0;

    /** Array der holder elementerne. */
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
        if (e == null) {
            throw new IllegalArgumentException("Element must not be null");
        }
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("Index: " + pos + ", Size: " + size);
        }
        E old = list[pos]; // gemmer nuværende element på pos
        list[pos] = e;     // sætter den nye ind
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
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("Index: " + pos + ", Size: " + size);
        }

        E removed = list[pos];

        // flyt elementer til venstre (dæk hullet)
        for (int i = pos; i < size - 1; i++) {
            list[i] = list[i + 1];
        }

        list[size - 1] = null;
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
            throw new IllegalArgumentException("Comparator must not be null");
        }
        BubbleSort.sort(c, this);
    }

    /**
     * Opretter et nyt array af type E med given længde.
     * Generics tillader ikke direkte at oprette E[]; vi bruger (E[]) new Object[længde].
     * Arrayet bruges kun internt i klassen.
     *
     * @param length længden af arrayet
     * @return nyt array af type E med given længde
     */
    private E[] createEmptyArray(int length) {
        return (E[]) new Object[length];
    }

}

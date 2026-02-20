# Assignment 3a – Generic List&lt;E&gt;

## What the assignment was about

In Assignment 3a you had to implement the **generic List&lt;E&gt;** abstract data type as defined by the provided interface. The implementation must use **arrays** and support the essential list operations:

- **clear()** – remove all elements
- **size()** – number of elements
- **get(pos)** – element at position
- **set(pos, e)** – replace element at position
- **add(e)** – add at the end
- **add(pos, e)** – add at a specific index (shifting existing elements)
- **remove(pos)** – remove at index
- **remove(e)** – remove first occurrence of element
- **indexOf(e)** – index of first occurrence, or -1

The list must not allow **null** elements (throw `IllegalArgumentException` for null where required), and index operations must throw **IndexOutOfBoundsException** when the index is out of range.

---

## How it was solved

The **base code** (assignment3.v1.1.0) already contained a full implementation of **ArrayList&lt;E&gt;** that implements the **List&lt;E&gt;** interface. That implementation:

1. **Storage:** Uses a generic array `E[] list` created via `(E[]) new Object[length]` (as in the provided helper `createEmptyArray`), and an `int size` for the current number of elements.

2. **clear():** Sets `size = 0`. The array is not reallocated; existing references are simply no longer considered part of the list.

3. **get(pos):** Checks `0 <= pos < size`, then returns `list[pos]`. Otherwise throws `IndexOutOfBoundsException`.

4. **set(pos, e):** Checks that `e` is not null and that `pos` is in range, then stores `e` at `list[pos]` and returns the previous element.

5. **add(e):** If the array is full (`size == list.length`), a new array of double the size is allocated, elements are copied, and `list` is replaced. Then `e` is stored at `list[size]` and `size` is incremented.

6. **add(pos, e):** Same growth logic if needed. Then elements from `pos` to the end are shifted one place to the right (loop from `size` down to `pos+1`), and `e` is placed at `list[pos]`. The valid range for `pos` is `0` to `size` (inclusive), so adding at `size` appends.

7. **remove(pos):** Checks index, saves the element at `pos`, then shifts all elements after `pos` one place to the left (loop from `pos` to `size-2`), sets `list[size-1] = null`, decrements `size`, and returns the removed element.

8. **remove(e):** Uses **indexOf(e)** to find the first occurrence; if found, calls **remove(index)** and returns true, otherwise returns false. Null `e` is handled by returning false (or indexOf returns -1).

9. **indexOf(e):** If `e` is null, returns -1. Otherwise loops from 0 to `size-1` and returns the first `i` where `list[i].equals(e)`; if none, returns -1.

So for **3a**, no new code was written in this submission: the solution consisted of using and understanding this existing **ArrayList&lt;E&gt;** implementation. Any edits in **ArrayList.java** in this project were only for **3b** (the **sort** method) and possibly small comment/formatting fixes. The design is straightforward: a single array, simple bounds checks, and when the array is full it is replaced by one twice as large and elements are copied.

---

## Summary

| Requirement              | How it was met                                      |
|--------------------------|------------------------------------------------------|
| Implement List&lt;E&gt; with array | ArrayList&lt;E&gt; in the base code implements List&lt;E&gt; with an array. |
| Dynamic size             | Array is doubled when full in add / add(pos, e).     |
| No null elements         | add/set throw IllegalArgumentException for null.     |
| Index bounds            | get/set/remove/add(pos,e) throw IndexOutOfBoundsException when invalid. |
| All interface methods    | clear, size, get, set, add, add(pos,e), remove, indexOf (and default contains) are implemented. |

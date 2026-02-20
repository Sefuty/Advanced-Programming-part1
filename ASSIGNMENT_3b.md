# Assignment 3b – Sorting Lists and SortedList&lt;E&gt;

## What the assignment was about

In Assignment 3b you had to:

1. **Implement sorting** for the generic list: the **sort(Comparator&lt;? super E&gt; c)** method in **ArrayList&lt;E&gt;** must sort the list using a **comparator**, and the sorting algorithm had to be implemented in a class **BubbleSort** (generic, as in the lectures but for lists instead of arrays).

2. **Implement SortedList&lt;E&gt;** by implementing **SortedArrayList&lt;E&gt;**:
   - **SortedList** extends **List** but keeps elements in sorted order at all times.
   - Operations **set(pos, e)** and **add(pos, e)** are not allowed (throw **UnsupportedOperationException** – already defined in the **SortedList** interface).
   - **sort(Comparator)** is also not allowed on a SortedList (must throw **UnsupportedOperationException**).
   - **add(e)** must insert the new element so that the list **stays sorted** (using the natural order from **Comparable&lt;E&gt;**).

3. **Person** must correctly implement **Comparable&lt;Person&gt;** and the **equals/hashCode** contract so that sorting and list operations (contains, indexOf, remove) work as expected.

---

## How it was solved

### 1. BubbleSort

**File:** `BubbleSort.java`

- **Null check:** If `comp` or `list` is null, throw **IllegalArgumentException** (as required by the JavaDoc).
- **Algorithm:** Classic bubble sort:
  - `do { swapped = false; for i from 0 to size-2: if comp.compare(get(i), get(i+1)) > 0 then swap elements at i and i+1 using list.set(), set swapped = true } while (swapped)`.
- **Swap:** `list.set(i, b)` and `list.set(i+1, a)` where `a = list.get(i)` and `b = list.get(i+1)`.
- **Why this works:** Bubble sort repeatedly compares adjacent pairs and swaps them if they are in the wrong order. When a full pass does no swaps, the list is sorted. The comparator defines “wrong order” (compare &gt; 0 means first is greater than second).

### 2. ArrayList.sort(Comparator)

**File:** `ArrayList.java`

- **Implementation:** Check that `c` is not null (throw **IllegalArgumentException** if null), then call **BubbleSort.sort(c, this)**.
- **Why:** The list does not implement the sorting algorithm itself; it delegates to **BubbleSort**, which works on any **List&lt;T&gt;** and **Comparator&lt;? super T&gt;** and modifies the list in place via **get/set**.

### 3. SortedArrayList

**File:** `SortedArrayList.java`

- **sort(Comparator):** Overridden to **always** throw **UnsupportedOperationException** (with the message required by the **SortedList** interface). This applies to **any** call, including **sort(null)**. Without this override, **sort(null)** would be handled by **ArrayList.sort()**, which throws **IllegalArgumentException** for null; the tests expect **UnsupportedOperationException** for sorted lists, so the override is necessary.

- **add(e):**  
  - Reject null (throw **IllegalArgumentException**).  
  - Compute **pos = findIndexToInsert(e)**.  
  - Call **super.add(pos, e)** so the element is inserted at the correct position in the underlying array (reusing ArrayList’s shift logic).

- **findIndexToInsert(e):**  
  - Linear scan: for `i` from 0 to size-1, if **get(i).compareTo(e) >= 0** return `i`.  
  - If no such `i`, return **size()** (insert at end).  
  - So the new element is placed before the first element that is greater or equal, keeping the list sorted. **E** is required to extend **Comparable&lt;E&gt;** by the **SortedList** interface, so **compareTo** is available.

### 4. Person (compareTo, equals, hashCode)

**File:** `Person.java`

- **compareTo(Person o):**  
  - Null check on `o`.  
  - Compare by **name** first: `this.name.compareTo(o.name)`; if non-zero, return that value.  
  - If names are equal, compare by **weight**: `Double.compare(this.weight, o.weight)`.  
  - **Double.compare** is used to avoid floating‑point issues and to get the correct sign (negative / zero / positive) for the comparator contract.

- **equals(Object o):**  
  - Same object → true.  
  - null or different class → false.  
  - Cast to **Person** and compare **name** and **weight** (using **Double.compare** for weight).  
  - This is consistent with **compareTo**: two persons are “equal” in the same way they compare as 0.

- **hashCode():**  
  - **return Objects.hash(name, weight)**.  
  - Uses the same fields as **equals**, so the contract “equals ⇒ same hashCode” is satisfied.

---

## Summary

| Part                | What was done                                                                 |
|---------------------|-------------------------------------------------------------------------------|
| BubbleSort          | Generic static method; null check; do-while with neighbour compare/swap via get/set. |
| ArrayList.sort      | Null check on comparator; delegate to BubbleSort.sort(c, this).              |
| SortedArrayList     | Override sort() to throw UnsupportedOperationException; add(e) uses findIndexToInsert + super.add(pos,e). |
| findIndexToInsert   | Linear search for first index where element >= e; return size() if none.     |
| Person              | compareTo (name then weight), equals (name + weight), hashCode (Objects.hash). |

All methods marked with TODO for Assignment 3b were implemented, and the tests in **TestArrayLists** and **TestSortedArrayLists** pass.

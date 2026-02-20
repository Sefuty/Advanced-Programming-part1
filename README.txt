================================================================================
Submission 1 – Assignments 3a, 3b and 3c (02324 Advanced Programming)
================================================================================

DESCRIPTION OF SOLUTIONS
------------------------

Assignment 3a – Generic List<E>
  The List<E> interface is implemented in ArrayList<E> using a dynamic array.
  All required methods (clear, size, get, set, add, add(pos,e), remove,
  indexOf) were already present in the provided base code. The array grows
  by doubling when full. No extra code was added for 3a; see ASSIGNMENT_3a.md
  for details.

Assignment 3b – Sorting and SortedList<E>
  • ArrayList.sort(Comparator): Checks for null comparator and calls
    BubbleSort.sort(comparator, this).
  • BubbleSort.sort(comp, list): Classic bubble sort over neighbour pairs;
    swaps when order is wrong (comp.compare > 0), repeats until no swaps.
  • SortedArrayList<E>: add(E e) uses findIndexToInsert(e) and calls
    super.add(pos, e). findIndexToInsert does a linear scan and returns
    the first index where the element is >= e so the list stays sorted.
  • Person: compareTo (name then weight), equals and hashCode (consistent
    with compareTo) are implemented for sorting and list operations.

  See ASSIGNMENT_3b.md for a full explanation.

Assignment 3c – GUI and Java Collections
  • Weight field: TextField for weight when adding a person. Empty input
    defaults to 70.0; invalid or non-positive weight shows an error in the GUI.
  • "Add at index:": Numeric TextField and button calling persons.add(index, person).
    Invalid index or weight shows an error message in the GUI.
  • Two labels updated whenever the list changes:
    - Average weight: sum of all weights / count (simple loop).
    - Most frequent name: Map<String,Integer> counts names; the name with
      the highest count is shown (as in the L06 Map example).
  • Exceptions: All buttons catch exceptions and show the error in a label;
    UnsupportedOperationException when sorting a SortedList is shown with
    a clear message.

  See ASSIGNMENT_3c.md for a full explanation.

Comments in the source code are in Danish; the rest of the project (README,
assignment explanations) is in English.

================================================================================
HOW TO RUN
----------
  Open the project in IntelliJ with JDK 25. Run PersonsApp (main class in
  dk.dtu.compute.course02324.assignment3.lists.uses.PersonsApp).
  Use the menu "Choose Implementation" → "(unsorted) List" or "sorted List"
  and use the fields and buttons as described above.

================================================================================
TESTS
-----
  All tests in TestForAllLists, TestArrayLists and TestSortedArrayLists
  should pass (Maven: Lifecycle → test, or run the test classes from IntelliJ).

================================================================================
EXTRAS (optional)
-----------------
  No extra functionality beyond the requirements of 3a, 3b and 3c.
  Possible extensions (e.g. text area for exceptions as in Assignment 2,
  or indexOf/contains buttons) are mentioned in the assignment texts.

================================================================================

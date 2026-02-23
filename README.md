Assignment 3 Submission

Here is all of what we did for the three assignments (or the whole project)

 3a - List Implementation
We made an ArrayList that works with any type of data:
- Uses an array inside that grows when it gets full
- Can add, remove, get, set elements
- Checks that you dont go out of bounds
- Wont let you add null stuff

3b - Sorting Stuff
Two parts here:

BubbleSort:
- Simple bubble sort that can sort any list
- Uses a comparator to decide the order
- Keeps swapping neighbors until everythings in order

SortedArrayList:
- Like ArrayList but always stays sorted
- When you add something it finds the right spot automatically
- Wont let you mess up the order (no set, no add at position, no sort)

3c - GUI Improvements
We added some stuff to the GUI like:
- Weight field so you can type in the weight yourself
- "Add at index" button to put people where you want (any index)
- Shows average weight of everyone
- Shows which name appears most often
- All buttons have error handling so the app wont crash

Also fixed the Person class:
- People can be compared (by name, then weight)
- equals and hashCode work properly
- Good for sorting and using in maps

you can try all the buttons - they should work

Tests pass too along with the extra ones
Thats pretty much it! we hope everything works as expected.

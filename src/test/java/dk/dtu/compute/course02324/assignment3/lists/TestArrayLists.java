package dk.dtu.compute.course02324.assignment3.lists;

import dk.dtu.compute.course02324.assignment3.lists.implementations.ArrayList;
import dk.dtu.compute.course02324.assignment3.lists.implementations.GenericComparator;
import dk.dtu.compute.course02324.assignment3.lists.implementations.SortedArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This test class sets up ({@link #setUp()}}) and adds some more
 * tests for {@link SortedArrayList} based on {@link TestForAllLists}.
 */
public class TestArrayLists extends TestForAllLists{

    @Before
    public void setUp() throws Exception {
        this.list = new ArrayList<>();
    }

    @Test
    public void testAddingElementsInFront() {

        for (int i = 0; i < TEST_SIZE; i++) {
            Assert.assertTrue(
                    "Add should return true",
                    list.add(0, format.format(i) + ". Test"));
        }

        for (int i = 0; i + 1 < TEST_SIZE; i++) {
            Assert.assertTrue(
                    "Initially, neighbouring elements should be out of order",
                    list.get(i).compareTo(list.get(i + 1)) > 0);
        }

        Assert.assertThrows(
                "Calling sort(null) should cause an exception",
                IllegalArgumentException.class,
                () -> { list.sort(null); } );

        list.sort(new GenericComparator<>());

        // the following test is redundant with the test after it!
        for (int i = 0; i + 1 < TEST_SIZE; i++) {
            Assert.assertTrue(
                    "After sorting, neighbouring elements should be in order",
                    list.get(i).compareTo(list.get(i + 1)) < 0);
        }

        for (int i = 0; i < TEST_SIZE; i++) {
            Assert.assertEquals(
                    "Element i is at wrong position",
                    i,
                    list.indexOf(format.format(i) + ". Test"));
        }

        for (int i = list.size()-1; i > 0; i= i - 2) {
            String element = list.remove(i);
            String expected = format.format(i) + ". Test";
            Assert.assertEquals(
                    "Removed element has unexpected value",
                    expected,
                    element);
        }

        Assert.assertEquals(
                "List size is wrong",
                TEST_SIZE/2,
                list.size());
    }

    @Test
    public void testClearAndIsEmpty() {
        // simple test for clear() and isEmpty()
        list.add("alpha");
        list.add("beta");

        Assert.assertFalse("list should not be empty after adding elements", list.isEmpty());

        list.clear();

        Assert.assertTrue("list should be empty after clear()", list.isEmpty());
        Assert.assertEquals("size should be 0 after clear()", 0, list.size());
    }

    @Test
    public void testSetAndAddAtIndex() {
        // simple test for add(index, e) and set(index, e)
        list.add("alpha");
        list.add("gamma");

        // insert in the middle
        Assert.assertTrue("add at index should return true", list.add(1, "beta"));
        Assert.assertEquals("element at index 1 should be 'beta'", "beta", list.get(1));

        // replace element at index 1
        String old = list.set(1, "beta2");
        Assert.assertEquals("old value from set should be 'beta'", "beta", old);
        Assert.assertEquals("element at index 1 should now be 'beta2'", "beta2", list.get(1));
    }

}

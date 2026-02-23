package dk.dtu.compute.course02324.assignment3.lists.uses;

import jakarta.validation.constraints.NotNull;

public class Person implements Comparable<Person> {

    final public String name;

    final public double weight;


    Person(@NotNull String name, @NotNull double weight) {
        if (name == null || weight <= 0) {

            throw new IllegalArgumentException("A person must be initialized with a " +
                    "(non null) name and a weight greater than 0");

        }
        this.name = name;

        this.weight = weight;
    }

    @Override

    public int compareTo(@NotNull Person o) {
        // compare persons first by name, then by weight if names are equal

        if (o == null) {
            throw new IllegalArgumentException("argument of compareTo() must not be null");

        }
        int nameCompare = this.name.compareTo(o.name);

        if (nameCompare != 0) {
            return nameCompare;

        }

        return Double.compare(this.weight, o.weight);
    }

    /**
     * return a simple string with name and weight.

     *
     * @return string on the form \"name, weightkg\"

     */
    @Override
    public String toString() {

        return name + ", " + weight + "kg";
    }

    /**
     * two persons are equal if both name and weight are equal.
     *

     * @param o object to compare with
     * @return true if names and weights are equal, otherwise false

     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {

            return true;

        }
        if (o == null || getClass() != o.getClass()) {

            return false;
        }


        Person person = (Person) o;


        return Double.compare(person.weight, weight) == 0 && name.equals(person.name);

    }

    /**
     * hash code must be consistent with equals (same name and weight -> same hash).
     *
     * @return hash code based on name and weight
     */
    @Override

    public int hashCode() {

        return java.util.Objects.hash(name, weight);

    }


}
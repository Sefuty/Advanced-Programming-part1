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
        if (o == null) {
            throw new IllegalArgumentException("Argument of compareTo() must not be null");
        }
        // først sammenlign på navn
        int nameCompare = this.name.compareTo(o.name);
        if (nameCompare != 0) {
            return nameCompare;
        }
        // hvis navne er ens, sammenlign på vægt
        return Double.compare(this.weight, o.weight);
    }

    /**
     * Giver en simpel strengrepræsentation af personen (navn og vægt).
     *
     * @return streng på formen "navn, vægtkg"
     */
    @Override
    public String toString() {
        return name + ", " + weight + "kg";
    }

    /**
     * Sammenligner to personer. To personer er lige hvis navn og vægt er ens.
     *
     * @param o det objekt der skal sammenlignes med
     * @return true hvis navn og vægt er ens, ellers false
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
     * HashCode skal være konsistent med equals: lige objekter har samme hashCode.
     *
     * @return hashCode baseret på navn og vægt
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, weight);
    }


}
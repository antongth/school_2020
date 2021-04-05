package net.thumbtack.school.functional;

import java.util.Objects;
import java.util.Optional;

public class PersonOp {
    private String name;
    private Optional<PersonOp> mother;
    private Optional<PersonOp> father;

    public PersonOp(String name, PersonOp mother, PersonOp father) {
        this.name = name;
        this.mother = Optional.ofNullable(mother);
        this.father = Optional.ofNullable(father);
    }

    public Optional<PersonOp> getMother() {
        return mother;
    }

    public Optional<PersonOp> getFather() {
        return father;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonOp)) return false;
        PersonOp personOp = (PersonOp) o;
        return Objects.equals(name, personOp.name) &&
                Objects.equals(getMother(), personOp.getMother()) &&
                Objects.equals(getFather(), personOp.getFather());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, getMother(), getFather());
    }
}

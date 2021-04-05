package net.thumbtack.school.functional;

import java.util.Objects;

public class Person {
    private String name;
    private Person mother;
    private Person father;
    private Integer age;

    public Person(String name){
        this.name = name;
    }

    public Person(String name, Person mother, Person father) {
        this.name = name;
        this.mother = mother;
        this.father = father;
    }

    public Person getMother() {
        return mother;
    }

    public Person getFather() {
        return father;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

package net.thumbtack.school.database.model;

import java.util.*;

public class School {
    private int id;
    private String name;
    private int year;
    List<Group> groups;

    public School() {

    }

    public School(int id, String name, int year, List<Group> groups) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.groups = groups;
    }

    public School(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.groups = new ArrayList<>();
    }

    public School(String name, int year) {
        this.id = 0;
        this.name = name;
        this.year = year;
        this.groups = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void  addGroup(Group group) {
        groups.add(group);
    }

    public void  removeGroup(Group group) {
        groups.remove(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof School)) return false;
        School school = (School) o;
        return Objects.equals(getId(), school.getId()) &&
                Objects.equals(getYear(), school.getYear()) &&
                Objects.equals(getName(), school.getName()) &&
                Objects.equals(getGroups(), school.getGroups());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getYear());
    }
}

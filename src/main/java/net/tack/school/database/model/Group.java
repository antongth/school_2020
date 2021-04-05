package net.thumbtack.school.database.model;

import java.util.*;

public class Group {
    private int id;
    private String name;
    private String room;
    private List<Trainee> trainees;
    private List<Subject> subjects;

    public Group() {
    }

    public Group(int id, String name, String room, List<Trainee> trainees, List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.trainees = trainees;
        this.subjects = subjects;
    }

    public Group(int id, String name, String room) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.trainees = new ArrayList<>();
        this.subjects = new ArrayList<>();
    }

    public Group(String name, String room) {
        this.id = 0;
        this.name = name;
        this.room = room;
        this.trainees = new ArrayList<>();
        this.subjects = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
    }

    public void  addTrainee(Trainee trainee) {
        trainees.add(trainee);
    }

    public void  removeTrainee(Trainee trainee) {
        trainees.remove(trainee);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }


    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void  addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void  removeSubjects(Subject subject) {
        subjects.remove(subject);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return Objects.equals(getId(), group.getId()) &&
                Objects.equals(getName(), group.getName()) &&
                Objects.equals(getRoom(), group.getRoom()) &&
                Objects.equals(getTrainees(), group.getTrainees()) &&
                Objects.equals(getSubjects(), group.getSubjects());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getRoom(), getTrainees(), getSubjects());
    }
}

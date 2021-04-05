package net.thumbtack.school.elections.server.model;

public class User {
    private String firstName;
    private String lastName;
    private String surName;
    private final String login;
    private String pass;
    private String street;
    private String house;
    private Integer place;
    private String email;
    private boolean removed = false; //удалена регистрация или нет

    public User(String firstName,
                String lastName,
                String surName,
                String login,
                String pass,
                String street,
                String house,
                Integer place,
                String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.surName = surName;
        this.login = login;
        this.pass = pass;
        this.street = street;
        this.house = house;
        this.place = place;
        this.email = email;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }
}

package net.thumbtack.school.elections.server.dto.request;

import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.ServerExceptionErrorCode;

import java.util.function.Function;

public class RegisterVoterDtoRequest {
    private String firstName;
    private String lastName;
    private String surname;
    private String street;
    private String house;
    private Integer place;
    private String login;
    private String pass;
    private String email;

    public RegisterVoterDtoRequest(String firstName, String lastName, String surname, String street,
                                   String house, Integer place, String login, String pass, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.surname = surname;
        this.street = street;
        this.house = house;
        this.place = place;
        this.login = login;
        this.pass = pass;
        this.email = email;
    }

    public void validate() throws ServerException {
        Function<String,Boolean> isValid = x -> x == null || x.equals("");
        Function<String,Boolean> isValidPass = x -> x == null || x.equals("") || x.length() < 8;
        if (isValid.apply(firstName)) throw new ServerException(ServerExceptionErrorCode.FIRST_NAME_INCORRECT);
        if (isValid.apply(lastName)) throw new ServerException(ServerExceptionErrorCode. LAST_NAME_INCORRECT);
        if (isValid.apply(login)) throw new ServerException(ServerExceptionErrorCode.    LOGIN_INCORRECT);
        if (isValidPass.apply(pass)) throw new ServerException(ServerExceptionErrorCode.     PASS_INCORRECT);
        if (isValid.apply(street)) throw new ServerException(ServerExceptionErrorCode.     STREET_INCORRECT);
        if (isValid.apply(house)) throw new ServerException(ServerExceptionErrorCode.     HOUSE_INCORRECT);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

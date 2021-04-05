package net.thumbtack.school.elections.server.dto.request;

import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.ServerExceptionErrorCode;

import java.util.function.Function;

public class LogInDtoRequest {
    String login;
    String pass;

    public LogInDtoRequest(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public void validate() throws ServerException {
        Function<String,Boolean> isValid = x -> x == null || x.equals("");
        if (isValid.apply(login)) throw new ServerException(ServerExceptionErrorCode.LOGIN_INCORRECT);
        if (isValid.apply(pass)) throw new ServerException(ServerExceptionErrorCode.PASS_INCORRECT);
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
}

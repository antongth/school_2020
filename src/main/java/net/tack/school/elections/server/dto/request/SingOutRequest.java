package net.thumbtack.school.elections.server.dto.request;

import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.ServerExceptionErrorCode;

import java.util.function.Function;

public class SingOutRequest {
    String token;

    public SingOutRequest(String token) {
        this.token = token;
    }

    public void validate() throws ServerException {
        Function<String,Boolean> isValid = x -> x == null || x.equals("");
        if (isValid.apply(token)) throw new ServerException(ServerExceptionErrorCode.LOGIN_INCORRECT);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

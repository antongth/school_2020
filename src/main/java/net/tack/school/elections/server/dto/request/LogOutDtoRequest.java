package net.thumbtack.school.elections.server.dto.request;

import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.ServerExceptionErrorCode;

import java.util.function.Function;

public class LogOutDtoRequest {
    String token;

    public LogOutDtoRequest(String token) {
        this.token = token;
    }

    public void validate() throws ServerException {
        Function<String,Boolean> isValid = x -> x == null || x.equals("");
        if (isValid.apply(token)) throw new ServerException(ServerExceptionErrorCode.TOKEN_INCORRECT);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

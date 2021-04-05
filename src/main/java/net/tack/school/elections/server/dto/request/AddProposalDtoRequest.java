package net.thumbtack.school.elections.server.dto.request;

import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.ServerExceptionErrorCode;
import java.util.function.Function;

public class AddProposalDtoRequest {
    private String text;
    private String token;

    public AddProposalDtoRequest(String text, String token) {
        this.text = text;
        this.token = token;
    }

    public void validate() throws ServerException {
        Function<String,Boolean> isValid = x -> x == null || x.equals("");
        if (isValid.apply(token)) throw new ServerException(ServerExceptionErrorCode.LOGIN_INCORRECT);
        if (isValid.apply(text)) throw new ServerException(ServerExceptionErrorCode.TOKEN_INCORRECT);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

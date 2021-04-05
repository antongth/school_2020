package net.thumbtack.school.elections.server.dto.request;

import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.ServerExceptionErrorCode;

import java.util.List;
import java.util.function.Function;

public class GetProposalsByAuthorDtoRequest {
    String token;
    List<String> authors;

    public GetProposalsByAuthorDtoRequest(String token, List<String> authors) {
        this.token = token;
        this.authors = authors;
    }

    public void validate() throws ServerException {
        Function<String,Boolean> isValid = x -> x == null || x.equals("");
        Function<List<String>,Boolean> isValidList = x -> x == null || x.isEmpty();
        if (isValid.apply(token)) throw new ServerException(ServerExceptionErrorCode.LOGIN_INCORRECT);
        if (isValidList.apply(authors)) throw new ServerException(ServerExceptionErrorCode.TOKEN_INCORRECT);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
}

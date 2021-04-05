package net.thumbtack.school.elections.server.dto.request;

import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.ServerExceptionErrorCode;

import java.util.function.Function;

public class RemoveProposalRatingDtoRequest {
    String token;
    Integer idOfProposal;

    public RemoveProposalRatingDtoRequest(String token, Integer idOfProposal) {
        this.token = token;
        this.idOfProposal = idOfProposal;
    }

    public void validate() throws ServerException {
        Function<String,Boolean> isValid = x -> x == null || x.equals("");
        Function<Integer,Boolean> isValidInteger = x -> x == null || x<=0;
        if (isValid.apply(token)) throw new ServerException(ServerExceptionErrorCode.LOGIN_INCORRECT);
        if (isValidInteger.apply(idOfProposal)) throw new ServerException(ServerExceptionErrorCode.TOKEN_INCORRECT);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIdOfProposal() {
        return idOfProposal;
    }

    public void setIdOfProposal(Integer idOfProposal) {
        this.idOfProposal = idOfProposal;
    }
}

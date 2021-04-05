package net.thumbtack.school.elections.server.dto.request;

import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.ServerExceptionErrorCode;

import java.util.function.Function;

public class RateItDtoRequest {
    String token;
    Integer idOfProposal;
    Integer rating;

    public RateItDtoRequest(String token, Integer idOfProposal, Integer rating) {
        this.token = token;
        this.idOfProposal = idOfProposal;
        this.rating = rating;
    }

    public void validate() throws ServerException {
        Function<String,Boolean> isValid = x -> x == null || x.equals("");
        Function<Integer,Boolean> isValidInt = x -> x > 6 || x < 0;
        Function<Integer,Boolean> isValidId = x -> x <= 0 ;
        if (isValid.apply(token)) throw new ServerException(ServerExceptionErrorCode.TOKEN_INCORRECT);
        if (isValidId.apply(idOfProposal)) throw new ServerException(ServerExceptionErrorCode.PROPOSAL_INCORRECT);
        if (isValidInt.apply(rating)) throw new ServerException(ServerExceptionErrorCode.RATE_INCORRECT);
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}

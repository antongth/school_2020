package net.thumbtack.school.elections.server.dto.response;

public class RegisterVoterDtoResponse {
    private String token;

    public RegisterVoterDtoResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

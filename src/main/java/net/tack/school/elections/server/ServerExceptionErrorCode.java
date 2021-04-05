package net.thumbtack.school.elections.server;

public enum ServerExceptionErrorCode {
    VOTER_IS_REGISTERED("already registered voter with such login"),
    FIRST_NAME_INCORRECT("first name must be not empty"),
    LAST_NAME_INCORRECT("last name must be not empty"),
    LOGIN_INCORRECT("login must be not empty"),
    PASS_INCORRECT("pass must be not empty or less then 8 symbols"),
    TOKEN_INCORRECT("token must not be empty"),
    INVALID_TOKEN("No rights"),
    PROPOSAL_EXIST("proposal is already added"),
    TEXT_INCORRECT("proposal must not be empty"),
    VOTER_IS_OFFLINE("Voter is singed out"),
    LOGOUT_IS_FAIL("Logout is fail"),
    RATE_INCORRECT("must be within 0 to 5"),
    PROPOSAL_INCORRECT("proposals id is empty"),
    CANDIDATE_IS_NOT_GIVE_HIS_APPROVAL("candidate do not give an approval"),
    NO_CANDIDATES("no candidates has registered"),
    TAKE_APPROVAL_OFF("you need to take your approval off first"),
    LOGIN_IS_FAIL("login is fail"),
    NO_VOTERS("No voters"),
    VOTER_IS_REMOVED("voter is removed"),
    VOTER_IS_ONLINE("voter already on line"),
    STREET_INCORRECT("street incorrect"),
    HOUSE_INCORRECT("house incorrect"),
    CANT_DEL_SELF_PROPS("cant delete yours proposal"),
    SELF_ELECT("self elect"),
    NOT_CANDIDATE("not candidate"),
    CANDIDATE_IS_EXIST("candidate is exsist"),
    UNE_TIME_ONLY("no more times"),
    ELECTIONS_FAILD("to many no one votes"),
    VOTER_REGISTRATION_FAILED("try to register voter butt failed"),
    OUT_OF_ELECTIONDATE("out of election date");

    private String errorCode;

    ServerExceptionErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

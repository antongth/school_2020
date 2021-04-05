package net.thumbtack.school.elections.server;

import com.google.gson.Gson;
import net.thumbtack.school.elections.server.database.Database;
import net.thumbtack.school.elections.server.dto.request.*;
import net.thumbtack.school.elections.server.dto.response.*;
import net.thumbtack.school.elections.server.model.Proposal;
import net.thumbtack.school.elections.server.model.Voter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    private Gson gson = new Gson();
    private Server server = new Server();
    private String jsonRequest;
    private String jsonResponse;
    private File file = new File("load.json");

    private String registerVoter(String firstName, String lastName, String surname, String street,
                      String house, Integer place, String login, String pass, String email) {

        RegisterVoterDtoRequest request = new RegisterVoterDtoRequest(firstName, lastName, surname, street, house,
                place, login, pass, email);
        return server.registerVoter(gson.toJson(request));
    }

    @BeforeEach
    private void setUp() {
        RegisterVoterDtoRequest request = new RegisterVoterDtoRequest(
                "dave",
                "johnston",
                null,
                "elm street",
                "D",
                null,
                "J0",
                "llllllll",
                null);
        String jsonRequest = gson.toJson(request);
        String response = server.registerVoter(jsonRequest);
        server.addProposal(gson.toJson(
                new AddProposalDtoRequest(
                        "important proposition",
                        gson.fromJson(response, RegisterVoterDtoResponse.class).getToken()
                )));
        request = new RegisterVoterDtoRequest(
                "dave",
                "johnston",
                null,
                "elm street",
                "D",
                null,
                "J1",
                "llllllll",
                null);
        jsonRequest = gson.toJson(request);
        response = server.registerVoter(jsonRequest);
        server.addProposal(gson.toJson(
                new AddProposalDtoRequest(
                        "free rick",
                        gson.fromJson(response, RegisterVoterDtoResponse.class).getToken()
                )));
        request = new RegisterVoterDtoRequest(
                "dave",
                "johnston",
                null,
                "elm street",
                "D",
                null,
                "J2",
                "llllllll",
                null);
        jsonRequest = gson.toJson(request);
        response = server.registerVoter(jsonRequest);
        server.addProposal(gson.toJson(
                new AddProposalDtoRequest(
                        "fire in the hole",
                        gson.fromJson(response, RegisterVoterDtoResponse.class).getToken()
                )));
        request = new RegisterVoterDtoRequest(
                "dave",
                "johnston",
                null,
                "elm street",
                "D",
                null,
                "J3",
                "llllllll",
                null);
        jsonRequest = gson.toJson(request);
        response = server.registerVoter(jsonRequest);
        server.addProposal(gson.toJson(
                new AddProposalDtoRequest(
                        "fices the roads",
                        gson.fromJson(response, RegisterVoterDtoResponse.class).getToken()
                )));
        request = new RegisterVoterDtoRequest(
                "dave",
                "johnston",
                null,
                "elm street",
                "D",
                null,
                "J4",
                "llllllll",
                null);
        jsonRequest = gson.toJson(request);
        response = server.registerVoter(jsonRequest);
        server.addProposal(gson.toJson(
                new AddProposalDtoRequest(
                        "shift of governments",
                        gson.fromJson(response, RegisterVoterDtoResponse.class).getToken()
                )));
    }

    @AfterEach
    public void clear() {
        Database.getInstance().clear();
    }

    @Test
    public void testRegisterVoter() {
        jsonResponse = registerVoter(
                "KL",
                "jrl",
                null,
                "lll",
                "D",
                null,
                "KL",
                "lllljkycr547))",
                null);
        RegisterVoterDtoResponse result = gson.fromJson(jsonResponse, RegisterVoterDtoResponse.class);
        assertEquals(gson.toJson(result), jsonResponse);

        jsonResponse = registerVoter(
                "KL",
                "jrl",
                null,
                "lll",
                "D",
                null,
                "KL",
                "llllllll",
                null);
        ErrorDtoResponse result1 = gson.fromJson(jsonResponse, ErrorDtoResponse.class);
        assertEquals(gson.toJson(result1), jsonResponse);

        jsonResponse = registerVoter(
                "KL",
                "jrl",
                null,
                "lll",
                null,
                null,
                "K",
                "llllllll",
                null);
        ErrorDtoResponse result2 = gson.fromJson(jsonResponse, ErrorDtoResponse.class);
        assertEquals(gson.toJson(result2), jsonResponse);

        jsonResponse = registerVoter(
                "KL",
                "jrl",
                null,
                "lll",
                null,
                null,
                "K",
                "lll",
                null);
        ErrorDtoResponse result3 = gson.fromJson(jsonResponse, ErrorDtoResponse.class);
        assertEquals(gson.toJson(result3), jsonResponse);
    }

    @Test
    public void getAllVoters() {
        jsonResponse = registerVoter(
                "KL",
                "jrl",
                null,
                "lll",
                "D",
                null,
                "k",
                "llllllll",
                null);
        RegisterVoterDtoResponse registerVoterDtoResponse = gson.fromJson(jsonResponse, RegisterVoterDtoResponse.class);
        AllVotersDtoRequest allVotersDtoRequest = new AllVotersDtoRequest(registerVoterDtoResponse.getToken());
        jsonRequest = gson.toJson(allVotersDtoRequest);
        jsonResponse = server.getAllVoters(jsonRequest);
        AllVotersDtoResponse allVotersDtoResponse = gson.fromJson(jsonResponse, AllVotersDtoResponse.class);

        assertEquals(6, allVotersDtoResponse.getAllVoters().size());
    }

    @Test
    public void testAddProposal(){
        jsonResponse = registerVoter(
                "KL",
                "jrl",
                null,
                "lll",
                "D",
                null,
                "k3",
                "llllllll",
                null);
        RegisterVoterDtoResponse registerVoterDtoResponse = gson.fromJson(jsonResponse, RegisterVoterDtoResponse.class);

        AddProposalDtoRequest addProposalDtoRequest = new AddProposalDtoRequest("a good proposal", registerVoterDtoResponse.getToken());
        jsonRequest = gson.toJson(addProposalDtoRequest);
        jsonResponse = server.addProposal(jsonRequest);
        SuccessDtoRespons successDtoRespons = new SuccessDtoRespons();

        assertEquals("success", successDtoRespons.getSuccess());

        jsonResponse = server.addProposal(jsonRequest);
        SuccessDtoRespons successDtoRespons1 = new SuccessDtoRespons();

        assertEquals("success", successDtoRespons1.getSuccess());
    }

    @Test
    public void testGetAllProposals(){
        jsonResponse = registerVoter(
                "KL",
                "jrl",
                null,
                "lll",
                "D",
                null,
                "k2",
                "llllllll",
                null);
        RegisterVoterDtoResponse registerVoterDtoResponse = gson.fromJson(jsonResponse, RegisterVoterDtoResponse.class);

        AddProposalDtoRequest addProposalDtoRequest = new AddProposalDtoRequest("a good proposal", registerVoterDtoResponse.getToken());
        server.addProposal(gson.toJson(addProposalDtoRequest));
        AddProposalDtoRequest addProposalDtoRequest1 = new AddProposalDtoRequest("a good proposal 2", registerVoterDtoResponse.getToken());
        server.addProposal(gson.toJson(addProposalDtoRequest1));
        AddProposalDtoRequest addProposalDtoRequest2 = new AddProposalDtoRequest("a good proposal 3", registerVoterDtoResponse.getToken());
        server.addProposal(gson.toJson(addProposalDtoRequest2));
        AddProposalDtoRequest addProposalDtoRequest3 = new AddProposalDtoRequest("a good proposal 4", registerVoterDtoResponse.getToken());
        server.addProposal(gson.toJson(addProposalDtoRequest3));

        AllProposalsDtoRequest allProposalsDtoRequest = new AllProposalsDtoRequest(registerVoterDtoResponse.getToken());
        jsonRequest = gson.toJson(allProposalsDtoRequest);
        jsonResponse = server.getAllProposals(jsonRequest);
        AllProposalsDtoResponse allProposalsDtoResponse = gson.fromJson(jsonResponse, AllProposalsDtoResponse.class);

        assertEquals(9, allProposalsDtoResponse.getProposals().size());
    }

    @Test
    public void testLogInLogOut() {
        jsonResponse = registerVoter(
                "KL",
                "jrl",
                null,
                "lll",
                "D",
                null,
                "k4",
                "llllllll",
                null);
        RegisterVoterDtoResponse registerVoterDtoResponse = gson.fromJson(jsonResponse, RegisterVoterDtoResponse.class);

        LogInDtoRequest logInDtoRequest = new LogInDtoRequest("k4", "llllllll");
        jsonRequest = gson.toJson(logInDtoRequest);
        jsonResponse = server.logIn(jsonRequest);
        ErrorDtoResponse errorDtoResponse = gson.fromJson(jsonResponse,ErrorDtoResponse.class);

        assertEquals(gson.toJson(errorDtoResponse), jsonResponse);

        LogOutDtoRequest logOutDtoRequest = new LogOutDtoRequest(registerVoterDtoResponse.getToken());
        jsonRequest = gson.toJson(logOutDtoRequest);
        jsonResponse = server.logOut(jsonRequest);
        SuccessDtoRespons successDtoRespons = new SuccessDtoRespons();

        assertEquals(gson.toJson(successDtoRespons), jsonResponse);

        jsonRequest = gson.toJson(logInDtoRequest);
        jsonResponse = server.logIn(jsonRequest);
        RegisterVoterDtoResponse registerVoterDtoResponse1 = gson.fromJson(jsonResponse, RegisterVoterDtoResponse.class);

        assertNotEquals(registerVoterDtoResponse.getToken(),registerVoterDtoResponse1.getToken());
    }

    @Test
    public void testRateIt() {
        jsonResponse = registerVoter(
                "KL",
                "jrl",
                null,
                "lll",
                "D",
                null,
                "dll",
                "llllllll",
                null);
        RegisterVoterDtoResponse registerVoterDtoResponse = gson.fromJson(jsonResponse, RegisterVoterDtoResponse.class);

        AddProposalDtoRequest addProposalDtoRequest = new AddProposalDtoRequest("a good proposal",
                registerVoterDtoResponse.getToken());
        jsonRequest = gson.toJson(addProposalDtoRequest);
        server.addProposal(jsonRequest);

        try {
            assertEquals(addProposalDtoRequest.getText(), Database.getInstance().getProposalsByAuthor("dll").get(0).getText());

            RateItDtoRequest rateItDtoRequest = new RateItDtoRequest(
                    registerVoterDtoResponse.getToken(),
                    Database.getInstance().getProposalsByAuthor("J0").get(0).getId(),
                    4);
            jsonRequest = gson.toJson(rateItDtoRequest);
            jsonResponse = server.rateIt(jsonRequest);
            SuccessDtoRespons successDtoRespons = new SuccessDtoRespons();

            assertEquals("success", successDtoRespons.getSuccess());
        } catch (ServerException e) {
            fail();
        }

        AllProposalsDtoRequest allProposalsDtoRequest = new AllProposalsDtoRequest(registerVoterDtoResponse.getToken());
        jsonRequest = gson.toJson(allProposalsDtoRequest);
        jsonResponse = server.getAllProposals(jsonRequest);
        AllProposalsDtoResponse allProposalsDtoResponse = gson.fromJson(jsonResponse, AllProposalsDtoResponse.class);

        assertEquals(6,allProposalsDtoResponse.getProposals().size());

        ListIterator<Proposal> li = allProposalsDtoResponse.getProposals().listIterator();
        Proposal proposal = null;
        while (li.hasNext()) {
            proposal = li.next();
            if (proposal.getAuthor().equals("dll"))
                break;
        }

        RateItDtoRequest rateItDtoRequest1 = new RateItDtoRequest(
                registerVoterDtoResponse.getToken(),
                proposal.getId(),
                5);
        jsonRequest = gson.toJson(rateItDtoRequest1);
        jsonResponse = server.rateIt(jsonRequest);
        ErrorDtoResponse errorDtoResponse = gson.fromJson(jsonResponse, ErrorDtoResponse.class);

        assertEquals("cant delete yours proposal", errorDtoResponse.getError());
    }

    @Test
    public void testStartServer() {
        jsonResponse = registerVoter(
                "KL",
                "jrl",
                null,
                "lll",
                "D",
                null,
                "ddl",
                "llllllll",
                null);
        RegisterVoterDtoResponse registerVoterDtoResponse = gson.fromJson(jsonResponse, RegisterVoterDtoResponse.class);

        AddProposalDtoRequest addProposalDtoRequest =
                new AddProposalDtoRequest("a good proposal too", registerVoterDtoResponse.getToken());
        jsonRequest = gson.toJson(addProposalDtoRequest);
        jsonResponse = server.addProposal(jsonRequest);
        server.stopServer(file.getAbsolutePath());

        assertTrue(file.exists());

        server.startServer(file.getAbsolutePath());

        List<Voter> voters = null;
        try {
            voters = Database.getInstance().getAllVoters();
        } catch (ServerException e) {
            fail();
        }
        assertEquals(6, voters.size());
    }
}
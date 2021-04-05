package net.thumbtack.school.elections.server;

import com.google.gson.Gson;
import net.thumbtack.school.elections.server.database.Database;
import net.thumbtack.school.elections.server.dto.request.AddProposalDtoRequest;
import net.thumbtack.school.elections.server.dto.request.RegisterVoterDtoRequest;
import net.thumbtack.school.elections.server.dto.response.RegisterVoterDtoResponse;
import net.thumbtack.school.elections.server.service.VoterService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class RobotClientTest {
    private final int siziOfList = 5;
    private Server server = new Server();
    private Gson gson = new Gson();
    private String registerTest = "{\"firstName\":\"test\",\"lastName\":\"test\",\"street\":\"test street\",\"house\":\"T\",\"login\":\"test\",\"pass\":\"tttttttt\"}";

    //в Unit это не обязательно т.к. выполнитель тестов для каждого метода создает оьдельный экземпляр класса
    @BeforeEach
    private void setUp5votersAndProps() {
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
    public void testGetList() {
        Server serverSpy = Mockito.spy(server);
        RobotClient robotClient = new RobotClient(serverSpy);
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        String token = robotClient.regFakeClient(registerTest);
        int listSize = robotClient.getList(token);
        Assert.assertEquals(siziOfList, listSize);
        Mockito.verify(serverSpy).getAllProposals(argument.capture());
        assertTrue(argument.getValue().contains(token));
    }


    @Test
    public void testRegFakeClient() {
        Server serverSpy = Mockito.spy(server);
        RobotClient robotClient = new RobotClient(serverSpy);
        robotClient.regFakeClient(registerTest);
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        Mockito.verify(serverSpy).registerVoter(argument.capture());
        assertTrue(argument.getValue().contains("test"));
    }

    @Test
    public void testDelFakeClient() {
        Server serverSpy = Mockito.spy(server);
        RobotClient robotClient = new RobotClient(serverSpy);
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        String token = robotClient.regFakeClient(registerTest);
        String resp = robotClient.delFakeClient(token);
        Assert.assertEquals("{\"success\":\"success\"}", resp);
        Mockito.verify(serverSpy).removeVoter(argument.capture());
        assertTrue(argument.getValue().contains(token));
    }

    @Test
    public void testException() {
        Server serverSpy = Mockito.spy(server);
        RobotClient robotClient = new RobotClient(serverSpy);
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        String token = robotClient.regFakeClient(registerTest);
        robotClient.delFakeClient(token);
        assertThrows(Exception.class, () -> {
            robotClient.getList(token);
        });
    }

}
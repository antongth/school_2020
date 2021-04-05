package net.thumbtack.school.elections.server.service;

import net.thumbtack.school.elections.server.Server;
import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.ServerExceptionErrorCode;
import net.thumbtack.school.elections.server.daoimpl.VoterDaoImpl;
import net.thumbtack.school.elections.server.model.User;
import net.thumbtack.school.elections.server.model.Voter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class VoterServiceTest {

    //Мокаем дао-слои
    private VoterDaoImpl voterDao = Mockito.mock(VoterDaoImpl.class);
    //Запускаем тестируемый сервис
    VoterService voterService = new VoterService(voterDao);
    //готовим тестовые данные
    String registerVoter = "{\"firstName\":\"dave\",\"lastName\":\"johnston\",\"street\":\"elm street\",\"house\":\"D\",\"login\":\"J0\",\"pass\":\"llllllll\"}";
    String addProp = "{\"text\":\"important proposition\",\"token\":\"746ba847-31fc-424c-a3e6-9c776d8faadc\"}";
    String errRegister = "{\"error\":\"already registered voter with such login\"}";
    String errLogIn = "{\"error\":\"voter already on line\"}";
    String errResp = "{\"error\":\"Logout is fail\"}";
    String errToken = "{\"error\":\"Voter is singed out\"}";
    String successResp = "{\"success\":\"success\"}";
    String successRespAllVoter = "{\"voters\":[{\"user\":{\"firstName\":\"\",\"lastName\":\"\",\"surName\":\"\",\"login\":\"\",\"pass\":\"\",\"street\":\"\",\"house\":\"\",\"place\":1,\"email\":\"\",\"removed\":false},\"selfProposals\":[],\"voice\":true}]}";

    @Test
    public void testRegisterVoter() throws ServerException {

        String response = voterService.registerVoter(registerVoter);
        Assert.assertTrue(response.contains("token"));

        //Mockito.when(voterDao.isVoterRegistered(anyString())).thenThrow(new ServerException(ServerExceptionErrorCode.VOTER_IS_REGISTERED));
        Mockito.doThrow(new ServerException(ServerExceptionErrorCode.VOTER_IS_REGISTERED))
                .when(voterDao).isVoterRegistered("J0");

        String response1 = voterService.registerVoter(registerVoter);
        Assert.assertEquals(errRegister, response1);
    }

    @Test
    public void testLogIn() throws ServerException {

        Mockito.doAnswer(invocationOnMock -> new Voter( new User(
                "",
                "",
                "",
                invocationOnMock.getArgument(0),
                invocationOnMock.getArgument(1),
                "",
                "",
                1,
                ""
        ))).when(voterDao).checkPasswordAndDelReg(anyString(), anyString());


        String response = voterService.logIn("{\"login\":\"Kl\",\"pass\":\"llllll\"}");
        Assert.assertTrue(response.contains("token"));

        Mockito.doThrow(new ServerException(ServerExceptionErrorCode.VOTER_IS_ONLINE))
                .when(voterDao).checkPasswordAndDelReg("Kl", "llllll");
        String response1 = voterService.logIn("{\"login\":\"Kl\",\"pass\":\"llllll\"}");
        Assert.assertEquals(errLogIn, response1);
    }

    @Test
    public void testLogout() throws ServerException {
        String token = UUID.randomUUID().toString();

        Mockito.doNothing()
                .doThrow(new ServerException(ServerExceptionErrorCode.LOGOUT_IS_FAIL))
                .when(voterDao).logOut(token);
        String response = voterService.logOut("{\"token\":\"" + token + "\"}");
        Assert.assertEquals(successResp, response);

        String response1 = voterService.logOut("{\"token\":\"" + token + "\"}");
        Assert.assertEquals(errResp, response1);
    }

    @Test
    public void testGetAllVoters() throws ServerException {
        String token = UUID.randomUUID().toString();
        Voter voter = new Voter(new User("", "", "", "", "", "", "", 1,""));
        List<Voter> voterList = new ArrayList<>();
        voterList.add(voter);


        Mockito.doReturn(voter).when(voterDao).getVoterByToken(token);
        Mockito.doReturn(voterList).when(voterDao).getAllVoters();
        String response = voterService.getAllVoters("{\"token\":\"" + token + "\"}");
        Assert.assertEquals(successRespAllVoter, response);

        Mockito.doThrow(new ServerException(ServerExceptionErrorCode.VOTER_IS_OFFLINE))
                .when(voterDao).getVoterByToken(token);
        String response1 = voterService.getAllVoters("{\"token\":\"" + token + "\"}");
        Assert.assertEquals(errToken, response1);
    }

    @Test
    public void testRemoveVoter() throws ServerException {
        String token = UUID.randomUUID().toString();
        Voter voter = new Voter(new User("", "", "", "", "", "", "", 1,""));

        Mockito.doNothing().when(voterDao).removeVoter(voter);
        Mockito.doNothing().when(voterDao).logOut(token);
        String response = voterService.removeVoter("{\"token\":\"" + token + "\"}");
        Mockito.verify(voterDao, Mockito.times(1)).logOut(token);
        Assert.assertEquals(successResp, response);

    }

    @Test
    public void testRetainVoter() throws ServerException {


    }
}
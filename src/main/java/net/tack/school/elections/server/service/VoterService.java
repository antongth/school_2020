package net.thumbtack.school.elections.server.service;

import com.google.gson.Gson;
import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.ServerUtils;
import net.thumbtack.school.elections.server.daoimpl.VoterDaoImpl;
import net.thumbtack.school.elections.server.dto.request.*;
import net.thumbtack.school.elections.server.dto.response.AllVotersDtoResponse;
import net.thumbtack.school.elections.server.dto.response.RegisterVoterDtoResponse;
import net.thumbtack.school.elections.server.model.User;
import net.thumbtack.school.elections.server.model.Voter;

import java.util.UUID;

public class VoterService {
    private Gson gson;
    private VoterDaoImpl voterDaoImpl;

    public VoterService() {
        gson = new Gson();
        voterDaoImpl = new VoterDaoImpl();
    }
    public VoterService(VoterDaoImpl voterDaoImpl) {
        gson = new Gson();
        this.voterDaoImpl = voterDaoImpl;
    }

    public String registerVoter(String requestJsonString) {
        RegisterVoterDtoRequest registerVoterDtoRequest = gson.fromJson(requestJsonString, RegisterVoterDtoRequest.class);
        try {
            registerVoterDtoRequest.validate();
            voterDaoImpl.isVoterRegistered(registerVoterDtoRequest.getLogin());
            User user = new User(registerVoterDtoRequest.getFirstName(),
                                 registerVoterDtoRequest.getLastName(),
                                 registerVoterDtoRequest.getSurname(),
                                 registerVoterDtoRequest.getLogin(),
                                 registerVoterDtoRequest.getPass(),
                                 registerVoterDtoRequest.getStreet(),
                                 registerVoterDtoRequest.getHouse(),
                                 registerVoterDtoRequest.getPlace(),
                                 registerVoterDtoRequest.getEmail());
            Voter voter = new Voter(user);
            voterDaoImpl.registerVoter(voter);
            String token = UUID.randomUUID().toString();
            voterDaoImpl.logIn(token, voter);
            return gson.toJson(new RegisterVoterDtoResponse(token), RegisterVoterDtoResponse.class);
        } catch (ServerException e) {
            return ServerUtils.makeErrorResponse(e);
        }
    }

    public String logIn(String requestJsonString) {
        LogInDtoRequest logInDtoRequest = gson.fromJson(requestJsonString, LogInDtoRequest.class);
        try {
            logInDtoRequest.validate();
            Voter voter = voterDaoImpl.checkPasswordAndDelReg(logInDtoRequest.getLogin(), logInDtoRequest.getPass());
            String token = UUID.randomUUID().toString();
            voterDaoImpl.logIn(token, voter);
            return gson.toJson(new RegisterVoterDtoResponse(token), RegisterVoterDtoResponse.class);
        } catch (ServerException e) {
            return ServerUtils.makeErrorResponse(e);
        }
    }

    public String logOut(String requestJsonString) {
        SingOutRequest singOutRequest = gson.fromJson(requestJsonString,SingOutRequest.class);
        try {
            singOutRequest.validate();
            voterDaoImpl.logOut(singOutRequest.getToken());
            return ServerUtils.makeSuccessResponse();
        } catch (ServerException e) {
            return ServerUtils.makeErrorResponse(e);
        }
    }

    public String getAllVoters(String requestJsonString) {
        AllVotersDtoRequest allVotersDtoRequest = gson.fromJson(requestJsonString, AllVotersDtoRequest.class);
        try {
            allVotersDtoRequest.validate();
            voterDaoImpl.getVoterByToken(allVotersDtoRequest.getToken());
            return gson.toJson(new AllVotersDtoResponse(voterDaoImpl.getAllVoters()), AllVotersDtoResponse.class);
        } catch (ServerException e) {
            return ServerUtils.makeErrorResponse(e);
        }
    }

    public String removeVoter(String requestJsonString) {
        RemoveVoterDtoRequest removeVoterDtoRequest = gson.fromJson(requestJsonString, RemoveVoterDtoRequest.class);
        try {
            removeVoterDtoRequest.validate();
            Voter voter = voterDaoImpl.getVoterByToken(removeVoterDtoRequest.getToken());
            voterDaoImpl.removeVoter(voter);
            voterDaoImpl.logOut(removeVoterDtoRequest.getToken());
            return ServerUtils.makeSuccessResponse();
        } catch (ServerException e) {
            return ServerUtils.makeErrorResponse(e);
        }
    }

    public String retainVoter(String requestJsonString) {
        LogInDtoRequest logInDtoRequest = gson.fromJson(requestJsonString, LogInDtoRequest.class);
        try {
            logInDtoRequest.validate();
            Voter voter = voterDaoImpl.retainVoter(logInDtoRequest.getLogin(), logInDtoRequest.getPass());
            String token = UUID.randomUUID().toString();
            voterDaoImpl.logIn(token, voter);
            return gson.toJson(new RegisterVoterDtoResponse(token), RegisterVoterDtoResponse.class);
        } catch (ServerException e) {
            return ServerUtils.makeErrorResponse(e);
        }
    }
}
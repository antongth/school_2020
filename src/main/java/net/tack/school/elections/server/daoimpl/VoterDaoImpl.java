package net.thumbtack.school.elections.server.daoimpl;

import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.dao.DAO;
import net.thumbtack.school.elections.server.model.Voter;

import java.util.*;


public class VoterDaoImpl implements DAO<Voter> {

    public void registerVoter(Voter voter) throws ServerException {
    	database.insertVoter(voter);
    }

    public void isVoterRegistered(String login) throws ServerException {
        database.isVoterRegistered(login);
    }

    public List<Voter> getAllVoters() throws ServerException {
        return database.getAllVoters();
    }

    public void removeVoter(Voter voter) throws ServerException {
        database.removeVoter(voter);
    }

    public void logIn(String token, Voter voter) throws ServerException {
        database.logIn(token, voter);
    }

    public void logOut(String token) throws ServerException {
        database.logOut(token);
    }

    public Voter checkPasswordAndDelReg(String login, String pass) throws ServerException {
        return database.checkPasswordAndDelReg(login, pass);
    }

    public Voter retainVoter(String login, String pass) throws ServerException {
        return database.retainVoter(login, pass);
    }
}

package net.thumbtack.school.elections.server.dao;

import net.thumbtack.school.elections.server.ServerException;
import net.thumbtack.school.elections.server.database.Database;
import net.thumbtack.school.elections.server.model.Voter;

public interface DAO<T> {
    Database database = Database.getInstance();

    default Voter getVoterByToken(String token) throws ServerException {
        return database.getVoterByToken(token);
    }

    //List<T> get() throws ServerException;

    //void insert(T t) throws ServerException;

    //void update(T t1, T t2) throws ServerException;

    //void remove(T t) throws ServerException;

}

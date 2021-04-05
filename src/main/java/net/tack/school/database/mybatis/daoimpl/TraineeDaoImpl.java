package net.thumbtack.school.database.mybatis.daoimpl;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.dao.TraineeDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TraineeDaoImpl extends DaoImplBase implements TraineeDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDaoImpl.class);

    @Override
    public Trainee insert(Group group, Trainee trainee) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).insert(group, trainee);
                sqlSession.commit();
                return trainee;
            } catch (RuntimeException ex) {
                LOGGER.error("Can't insert Trainee", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public Trainee getById(int id) {
        try (SqlSession sqlSession = getSession()) {
            return getTraineeMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get by Id", ex);
            throw ex;
        }
    }

    @Override
    public List<Trainee> getAll() {
        try (SqlSession sqlSession = getSession()) {
            return getTraineeMapper(sqlSession).getAll();
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get All", ex);
            throw ex;
        }
    }

    @Override
    public Trainee update(Trainee trainee) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).update(trainee);
                sqlSession.commit();
                return trainee;
            } catch (RuntimeException ex) {
                LOGGER.error("Can't update Trainee", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public List<Trainee> getAllWithParams(String firstName, String lastName, Integer rating) {
        try (SqlSession sqlSession = getSession()) {
            return getTraineeMapper(sqlSession).getAllWithParams(firstName, lastName, rating);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get All with params", ex);
            throw ex;
        }
    }

    @Override
    public void batchInsert(List<Trainee> trainees) {
        try (SqlSession sqlSession = getSession()) {
            try {
                for (Trainee trainee:trainees) {
                    getTraineeMapper(sqlSession).insert(null, trainee);
                }
                sqlSession.commit();
            } catch (RuntimeException ex) {
                LOGGER.error("Can't insert Trainees", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public void delete(Trainee trainee) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).delete(trainee.getId());
                sqlSession.commit();
            } catch (RuntimeException ex) {
                LOGGER.error("Can't delete trainee", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public void deleteAll() {
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).deleteAll();
                sqlSession.commit();
            } catch (RuntimeException ex) {
                LOGGER.error("Can't delete trainee", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }
}

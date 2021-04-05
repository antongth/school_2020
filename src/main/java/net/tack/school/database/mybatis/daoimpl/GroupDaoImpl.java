package net.thumbtack.school.database.mybatis.daoimpl;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.dao.GroupDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GroupDaoImpl extends DaoImplBase implements GroupDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupDaoImpl.class);

    @Override
    public Group insert(School school, Group group) {
        LOGGER.debug("insert School & Group");
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).insert(school, group);
                sqlSession.commit();
                return group;
            } catch (RuntimeException ex) {
                LOGGER.error("Can't insert School & Group", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public Group update(Group group) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).update(group);
                sqlSession.commit();
                return group;
            } catch (RuntimeException ex) {
                LOGGER.error("Can't update Group", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public List<Group> getAll() {
        try (SqlSession sqlSession = getSession()) {
            return getGroupMapper(sqlSession).getAll();
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get All", ex);
            throw ex;
        }
    }

    @Override
    public void delete(Group group) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).delete(group);
                sqlSession.commit();
            } catch (RuntimeException ex) {
                LOGGER.error("Can't delete Group", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public Trainee moveTraineeToGroup(Group group, Trainee trainee) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).moveTraineeToGroup(group, trainee);
                sqlSession.commit();
                return trainee;
            } catch (RuntimeException ex) {
                LOGGER.error("Can't move trainee to Group", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public void deleteTraineeFromGroup(Trainee trainee) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).deleteTraineeFromGroup(trainee);
                sqlSession.commit();
            } catch (RuntimeException ex) {
                LOGGER.error("Can't delete trainee from Group", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public void addSubjectToGroup(Group group, Subject subject) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).addSubjectToGroup(group, subject);
                sqlSession.commit();
            } catch (RuntimeException ex) {
                LOGGER.error("Can't add subject to Group", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }
}

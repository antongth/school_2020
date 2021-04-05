package net.thumbtack.school.database.mybatis.daoimpl;

import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.mybatis.dao.SubjectDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SubjectDaoImpl extends DaoImplBase implements SubjectDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDaoImpl.class);

    @Override
    public Subject insert(Subject subject) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).insert(subject);
                sqlSession.commit();
                return subject;
            } catch (RuntimeException ex) {
                LOGGER.error("Can't insert Subject", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public Subject getById(int id) {
        try (SqlSession sqlSession = getSession()) {
            return getSubjectMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get by Id", ex);
            throw ex;
        }
    }

    @Override
    public List<Subject> getAll() {
        try (SqlSession sqlSession = getSession()) {
            return getSubjectMapper(sqlSession).getAll();
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get by Id", ex);
            throw ex;
        }
    }

    @Override
    public Subject update(Subject subject) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).update(subject);
                sqlSession.commit();
                return subject;
            } catch (RuntimeException ex) {
                LOGGER.error("Can't update Subject", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public void delete(Subject subject) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSubjectMapper(sqlSession).delete(subject);
                sqlSession.commit();
            } catch (RuntimeException ex) {
                LOGGER.error("Can't delete subject", ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public void deleteAll() {
        try (SqlSession sqlSession = getSession()) {
            getSubjectMapper(sqlSession).deleteAll();
            sqlSession.commit();
        } catch (RuntimeException ex) {
            LOGGER.error("Can't delete all subjects", ex);
            throw ex;
        }
    }
}

package net.thumbtack.school.database.mybatis.daoimpl;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.dao.SchoolDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SchoolDaoImpl extends DaoImplBase implements SchoolDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolDaoImpl.class);

    @Override
    public School insert(School school) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).insert(school);
            } catch (RuntimeException ex) {
                LOGGER.error("Can't insert School", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return school;
    }

    @Override
    public School getById(int id) {
        try (SqlSession sqlSession = getSession()) {
            return getSchoolMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get School", ex);
            throw ex;
        }
    }

    @Override
    public List<School> getAllLazy() {
        try (SqlSession sqlSession = getSession()) {
            return getSchoolMapper(sqlSession).getAllLazy();
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get All Lazy", ex);
            throw ex;
        }
    }

    @Override
    public List<School> getAllUsingJoin() {
        try (SqlSession sqlSession = getSession()) {
            return sqlSession.selectList("net.thumbtack.school.database.mybatis.mappers.SchoolMapper.getAllUsingJoin");
        } catch (RuntimeException ex) {
            LOGGER.error("Can't insert using Join", ex);
            throw ex;
        }
    }

    @Override
    public void update(School school) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).update(school);
            } catch (RuntimeException ex) {
                LOGGER.error("Can't update School", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void delete(School school) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).delete(school);
            } catch (RuntimeException ex) {
                LOGGER.error("Can't delete School", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteAll() {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.error("Can't delete all Schools", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public School insertSchoolTransactional(School school2018) {
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).insert(school2018);
                for (Group group: school2018.getGroups()) {
                    getGroupMapper(sqlSession).insert(school2018, group);
                    for (Trainee trainee: group.getTrainees()) {
                        getTraineeMapper(sqlSession).insert(group, trainee);
                    }
                    for (Subject subj:group.getSubjects()) {
                        getGroupMapper(sqlSession).addSubjectToGroup(group, subj);
                    }
                }
            } catch (RuntimeException ex) {
                LOGGER.error("Can't insert School Transactional", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return school2018;
    }
}

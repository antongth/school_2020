package net.thumbtack.school.database.mybatis.daoimpl;

import net.thumbtack.school.database.mybatis.dao.CommonDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonDaoImpl extends DaoImplBase implements CommonDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoImplBase.class);

    @Override
    public void clear() {
        LOGGER.debug("Clear Database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).deleteAll();
                getGroupMapper(sqlSession).deleteAll();
                getTraineeMapper(sqlSession).deleteAll();
                getSubjectMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.error("Can't clear database");
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}

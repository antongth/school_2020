package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Trainee;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TraineeMapper {

    @Insert("INSERT INTO trainee (firstName, lastName, rating, groupId)" +
            "VALUES (#{trainee.firstName}, #{trainee.lastName}, #{trainee.rating}, #{group.id})")
    @Options(useGeneratedKeys = true, keyProperty = "trainee.id")
    Integer insert(@Param("group") Group group, @Param("trainee") Trainee trainee);

    @Select("SELECT id, firstName, lastName, rating FROM trainee WHERE groupId = #{group.id}")
    List<Trainee> getByGroup(@Param("group")Group group);

    @Delete("DELETE FROM trainee")
    void deleteAll();

    @Select("SELECT id, firstName, lastName, rating FROM trainee WHERE id = #{id}")
    Trainee getById(@Param("id") int id);

    @Select("SELECT id, firstName, lastName, rating FROM trainee")
    List<Trainee> getAll();

    @Update("UPDATE trainee SET firstName = #{trainee.firstName}, lastName = #{trainee.lastName}, rating = #{trainee.rating} WHERE id = #{trainee.id}")
    void update(@Param("trainee") Trainee trainee);

//    @Select("SELECT id, firstName, lastName, rating "+
//            "FROM trainee WHERE firstName = #{firstName} AND lastName = #{lastName} AND rating = #{rating}")
//    List<Trainee> getAllWithParams(String firstName, String lastName, Integer rating);

    @Select({"<script>",
            "SELECT id, firstname, lastname, rating FROM trainee",
            "<where>" +
                    "<if test='prefixfirstname != null'> AND firstname like #{prefixfirstname}",
            "</if>",
            "<if test='prefixlastname != null'> AND lastname like #{prefixlastname}",
            "</if>",
            "<if test='num != null'> AND rating like #{num}",
            "</if>",
            "</where>" +
                    "</script>"})
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "firstname"),
            @Result(property = "lastName", column = "lastname"),
            @Result(property = "rating", column = "rating")
    })
    List<Trainee> getAllWithParams(@Param("prefixfirstname") String firstName, @Param("prefixlastname") String lastName, @Param("num") Integer rating);
    //void batchInsert(List<Trainee> trainees);

    @Delete("DELETE FROM ttschool.trainee WHERE id = #{id}")
    void delete(int id);
}

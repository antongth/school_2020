package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface GroupMapper {

    @Insert("INSERT INTO `group` (name, room, schoolId) VALUES (#{group.name}, #{group.room}, #{school.id})")
    @Options(useGeneratedKeys = true, keyProperty = "group.id")
    Integer insert(@Param("school") School school, @Param("group") Group group);

    @Update("UPDATE `group` SET name = #{group.name}, room = #{group.room} WHERE id = #{group.id}")
    void update(@Param("group") Group group);

    @Select("SELECT id, name, room FROM `group`")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainees", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.TraineeMapper.getByGroup",
                            fetchType = FetchType.LAZY)),
            @Result(property = "subjects", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.SubjectMapper.getByGroup",
                            fetchType = FetchType.LAZY)),
    })
    List<Group> getAll();

    @Select("SELECT id, name, room FROM `group` WHERE schoolId = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainees", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.TraineeMapper.getByGroup",
                            fetchType = FetchType.LAZY)),
            @Result(property = "subjects", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.SubjectMapper.getByGroup",
                            fetchType = FetchType.LAZY)),
    })
    List<Group> getBySchool(School school);

    @Delete("DELETE FROM `group` WHERE id = #{group.id}")
    void delete(@Param("group") Group group);

    @Delete("DELETE FROM `group`")
    void deleteAll();

    @Update("UPDATE trainee SET groupId = #{group.id} WHERE id = #{trainee.id}")
    void moveTraineeToGroup(@Param("group") Group group, @Param("trainee") Trainee trainee);

    @Update("UPDATE trainee SET groupId = NULL WHERE id = #{trainee.id}")
    void deleteTraineeFromGroup(@Param("trainee") Trainee trainee);

    //@Update("UPDATE subject_group SET subjectId = #{subject.id}, groupId = #{group.id}")
    @Insert("INSERT INTO subject_group (groupId, subjectId) VALUES (#{group.id}, #{subject.id})")
    void addSubjectToGroup(@Param("group") Group group, @Param("subject") Subject subject);
}

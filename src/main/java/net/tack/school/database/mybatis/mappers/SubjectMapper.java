package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SubjectMapper {

    /*@Insert("INSERT INTO `subject` (id, name, groupId)" +
            "VALUES (#{subject.id}, #{subject.name}, #{group.id})")
    @Options(useGeneratedKeys = true, keyProperty = "subject.id")
    Integer insert(@Param("subject") Subject subject, @Param("group") Group group);*/

    @Insert("INSERT INTO `subject` (name) VALUES (#{subject.name})")
    @Options(useGeneratedKeys = true, keyProperty = "subject.id")
    Integer insert(@Param("subject") Subject subject);

    @Select("SELECT id, name FROM `subject` WHERE id IN (SELECT subjectId FROM subject_group WHERE groupId = #{group.id})")
    List<Subject> getByGroup(@Param("group") Group group);

    @Delete("DELETE FROM `subject`")
    void deleteAll();

    @Delete("DELETE FROM `subject` WHERE id = #{subject.id}")
    void delete(@Param("subject") Subject subject);

    @Update("UPDATE `subject` SET name = #{subject.name} WHERE id = #{subject.id}")
    void update(@Param("subject") Subject subject);

    @Select("SELECT id, name FROM `subject`")
    List<Subject> getAll();

    @Select("SELECT id, name FROM `subject` WHERE id = #{id}")
    Subject getById(@Param("id") int id);
}

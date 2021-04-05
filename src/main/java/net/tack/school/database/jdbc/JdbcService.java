package net.tack.school.database.jdbc;

import net.tack.school.database.model.Group;
import net.tack.school.database.model.School;
import net.tack.school.database.model.Subject;
import net.tack.school.database.model.Trainee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcService {


    public static void insertTrainee(Trainee trainee) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String query = "INSERT INTO trainee(id, firstName, lastName, rating) VALUES(?, ?, ?, ?)";
        int result;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setNull(1, Types.INTEGER);
            statement.setString(2, trainee.getFirstName());
            statement.setString(3, trainee.getLastName());
            statement.setInt(4, trainee.getRating());
            result = statement.executeUpdate();
            if (result>0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    trainee.setId(id);
                }
            }
        }
    }

 	public  static void updateTrainee(Trainee trainee) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String query = "UPDATE trainee SET firstName = ?, lastName = ?, rating = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, trainee.getFirstName());
            statement.setString(2, trainee.getLastName());
            statement.setInt(3, trainee.getRating());
            statement.setInt(4, trainee.getId());
            statement.executeUpdate();
        }
    }

 	public static Trainee getTraineeByIdUsingColNames(int traineeId) throws SQLException {
        Trainee trainee = null;
        Connection connection = JdbcUtils.getConnection();
        String query = "SELECT * FROM trainee WHERE id = " + traineeId;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int rating = resultSet.getInt("rating");
                if (traineeId != id)
                    throw new SQLException("not equal id");
                trainee = new Trainee(id, firstName, lastName, rating);
            }
        }
        return trainee;
    }

    public static Trainee getTraineeByIdUsingColNumbers(int traineeId) throws SQLException {
        Trainee trainee = null;
        Connection connection = JdbcUtils.getConnection();
        String query = "SELECT * FROM trainee WHERE id = " + traineeId;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                int rating = resultSet.getInt(4);
                if (traineeId != id)
                    throw new SQLException("not equal id");
                trainee = new Trainee(id, firstName, lastName, rating);
            }
        }
        return trainee;
    }

    public static List<Trainee> getTraineesUsingColNames() throws SQLException {
        List<Trainee> trainees = new ArrayList<>();
        Connection connection = JdbcUtils.getConnection();
        String query = "SELECT * FROM trainee";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int rating = resultSet.getInt("rating");
                trainees.add(new Trainee(id, firstName, lastName, rating));
            }
        }
        return trainees;
    }

 	public static List<Trainee> getTraineesUsingColNumbers() throws SQLException {
        List<Trainee> trainees = new ArrayList<>();
        Connection connection = JdbcUtils.getConnection();
        String query = "SELECT * FROM trainee";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                int rating = resultSet.getInt(4);
                trainees.add(new Trainee(id, firstName, lastName, rating));
            }
        }
        return trainees;
    }

 	public static void deleteTrainee(Trainee trainee) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String query = "DELETE FROM trainee WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, trainee.getId());
            statement.executeUpdate();
        }
}

 	public static void deleteTrainees() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String query = "DELETE FROM trainee";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }


 	public static void insertSubject(Subject subject) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String query = "INSERT INTO subject(id, name) VALUES(?, ?)";
        int result;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setNull(1, Types.INTEGER);
            statement.setString(2, subject.getName());
            result = statement.executeUpdate();
            if (result>0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    subject.setId(id);
                }
            }
        }
    }

    public static Subject getSubjectByIdUsingColNames(int subjectId) throws SQLException {
        Subject subject = null;
        Connection connection = JdbcUtils.getConnection();
            String query = "SELECT * FROM subject WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, subjectId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    subject = new Subject(id, name);
                }

            }
        return subject;
    }

    public static Subject getSubjectByIdUsingColNumbers(int subjectId) throws SQLException {
        Subject subject = null;
        Connection connection = JdbcUtils.getConnection();
            String query = "SELECT * FROM subject WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, subjectId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    subject = new Subject(id, name);
                }

            }
        return subject;
    }

    public static void deleteSubjects() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String query = "DELETE FROM subject";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    public static void insertSchool(School school) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String query = "INSERT INTO school(id, name, year) VALUES(?,?,?)";
        int result;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setNull(1, Types.INTEGER);
            statement.setString(2, school.getName());
            statement.setInt(3, school.getYear());
            result = statement.executeUpdate();
            if (result>0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    school.setId(id);
                }
            }
        }
    }

 	public static School getSchoolByIdUsingColNames(int schoolId) throws SQLException {
        School school = null;
        Connection connection = JdbcUtils.getConnection();
        String query = "SELECT * FROM school WHERE id = ";
        try (PreparedStatement statement = connection.prepareStatement(query + schoolId);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int year = resultSet.getInt("year");
                school = new School(id, name, year);
            }

        }
        return school;
    }

    public static School getSchoolByIdUsingColNumbers(int schoolId) throws SQLException {
        School school = null;
        Connection connection = JdbcUtils.getConnection();
        String query = "SELECT * FROM school WHERE id = ";
        try (PreparedStatement statement = connection.prepareStatement(query + schoolId);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int year = resultSet.getInt(3);
                school = new School(id, name, year);
            }
        }
        return school;
    }

    public static void deleteSchools() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String query = "DELETE FROM school";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    public static void insertGroup(School school, Group group) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String query = "INSERT INTO `group` VALUES(?, ?, ?, ?)";
        int result;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setNull(1, Types.INTEGER);
            statement.setString(2, group.getName());
            statement.setString(3, group.getRoom());
            statement.setInt(4, school.getId());
            result = statement.executeUpdate();
            if (result>0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    group.setId(id);
                }
            }
        }
    }

    public static School getSchoolByIdWithGroups(int id) throws SQLException {
        School school = null;
        List<Group> groups = new ArrayList<>();
        Connection connection = JdbcUtils.getConnection();
        String query = "SELECT school.name AS schoolName, year, group.id, group.name AS groupName, room " +
                "FROM school JOIN `group` ON school.id = group.schoolId WHERE school.id = "+id;
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String schoolName = resultSet.getString(1);
                int year = resultSet.getInt(2);
                int groupId = resultSet.getInt(3);
                String groupName = resultSet.getString(4);
                String room = resultSet.getString(5);
                groups.add(new Group(groupId, groupName, room));
                if (school==null) {
                    school = new School(id, schoolName, year);
                }
            }
            school.setGroups(groups);
        }
        return school;
    }

	public static List<School> getSchoolsWithGroups() throws SQLException {
        List<School> schools = new ArrayList<>();
        School school = null;
        Connection connection = JdbcUtils.getConnection();
        String query = "SELECT school.id, school.name AS schoolName, year, `group`.id, `group`.name AS groupName, room " +
                "FROM school JOIN `group` ON school.id=`group`.schoolId ORDER BY school.id desc limit 4";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int schoolId = resultSet.getInt(1);
                String schoolName = resultSet.getString(2);
                int year = resultSet.getInt(3);
                int groupId = resultSet.getInt(4);
                String groupName = resultSet.getString(5);
                String room = resultSet.getString(6);

                Group group = new Group(groupId, groupName, room);
                if (school==null) {
                    school = new School(schoolId, schoolName, year);
                }
                if (school.getId()!=schoolId) {
                    schools.add(school);
                    school = new School(schoolId, schoolName, year);
                }
                school.addGroup(group);
            }
            schools.add(school);
        }
        return schools.stream().sorted(Comparator.comparingInt(School::getId)).collect(Collectors.toList());
    }
}

package com.sda.dao;

import com.sda.jdbc.JdbcConnectionFactory;
import com.sda.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    private final static String CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS courses (\n" +
                    " id		    BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    " nazwa		    VARCHAR(200) NOT NULL, \n" +
                    " cena	    FLOAT NOT NULL,\n" +
                    "ilosc_godzin Integer NOT NULL);";

    private final static String INSERT_QUERY =
            "INSERT INTO courses VALUES(NULL, ?, ?, ?);";

    private final static String DELETE_QUERY =
            "DELETE FROM courses WHERE id = ?";


    private final static String SELECT_QUERY = "SELECT * FROM courses";


    public List<Course> select() {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // MAp<String, String>
                //^^(next())
                while (resultSet.next()) {
                    Course course = new Course();

                    course.setId(resultSet.getLong(1));
                    course.setNazwa(resultSet.getString(2));
                    course.setCena(resultSet.getDouble(3));
                    course.setIloscGodzin(resultSet.getInt(4));

                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return courses;
    }


    private JdbcConnectionFactory connectionFactory;

    public CourseDao(JdbcConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        createTableIfNotExists();
    }

    public void delete(Course course) {
        try (Connection connection = connectionFactory.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
                preparedStatement.setLong(1, course.getId());

                boolean result = preparedStatement.execute();
                System.out.println("Delete" + result);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableIfNotExists() {
        try (Connection connection = connectionFactory.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY)) {
                boolean result = preparedStatement.execute();
                System.out.println("Create: " + result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Course course) {
        try (Connection connection = connectionFactory.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, course.getNazwa());
                preparedStatement.setDouble(2, course.getCena());
                preparedStatement.setInt(3, course.getIloscGodzin());

                preparedStatement.executeUpdate();
                ResultSet result = preparedStatement.getGeneratedKeys();
                result.next();
                System.out.println("Success insert: " + result.getInt(1));
                course.setId(result.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
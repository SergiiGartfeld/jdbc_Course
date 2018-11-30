package com.sda.dao;

import com.sda.jdbc.JdbcConnectionFactory;
import com.sda.model.Course;
import com.sda.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private final static String CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS student (\n" +
                    " id_student		    BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    " imie		    VARCHAR(200) NOT NULL, \n" +
                    " nazwisko	    VARCHAR(200) NOT NULL);";

    private final static String INSERT_QUERY =
            "INSERT INTO student VALUES(NULL, ?, ?);";

    private final static String DELETE_QUERY =
            "DELETE FROM student WHERE id_student = ?";

    private final static String SELECT_QUERY = "SELECT * FROM student";

    public List<Student> select() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // MAp<String, String>
                //^^(next())
                while (resultSet.next()) {
                    Student student = new Student();

                    student.setId_student(resultSet.getLong(1));
                    student.setImie(resultSet.getString(2));
                    student.setNazwisko(resultSet.getString(3));


                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return students;
    }


    private JdbcConnectionFactory connectionFactory;

    public StudentDao(JdbcConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        createTableIfNotExists();
    }

    public void delete(Student student) {
        try (Connection connection = connectionFactory.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
                preparedStatement.setLong(1, student.getId_student());

                boolean result = preparedStatement.execute();
                System.out.println("Delete: " + result);

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

    public void insert(Student student) {
        try (Connection connection = connectionFactory.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, student.getImie());
                preparedStatement.setString(2, student.getNazwisko());


                preparedStatement.executeUpdate();
                ResultSet result = preparedStatement.getGeneratedKeys();
                result.next();
                System.out.println("Success insert: " + result.getInt(1));
                student.setId_student(result.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


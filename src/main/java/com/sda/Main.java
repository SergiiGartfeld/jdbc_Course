package com.sda;

import com.sda.dao.CourseDao;
import com.sda.dao.StudentDao;
import com.sda.jdbc.JdbcConnectionFactory;
import com.sda.model.Course;
import com.sda.model.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            //tworzymu połącz
           JdbcConnectionFactory jdbcConnectionFactory = new JdbcConnectionFactory();
           // Connection connection = jdbcConnectionFactory.getConnecion();
           // System.out.println("Closed: " + connection.isClosed());

            CourseDao courseDao = new CourseDao(jdbcConnectionFactory);
            StudentDao studentDao = new StudentDao(jdbcConnectionFactory);

            Course course = new Course();
            Student student = new Student();
            student.setId_student(1L);
            studentDao.delete(student);
            //student.setImie("Gogo");
            //student.setNazwisko("Komor");
            //studentDao.insert(student);
         //  course.setNazwa("JavaGDA17");
         //  course.setIloscGodzin(360);
         //  course.setCena(100000);
         //   courseDao.insert(course);
//
         //   List<Course>courseList = courseDao.select();
         //   courseList.forEach(System.out::println);

           // course.setId(3L);
           // courseDao.delete(course);
        }catch (SQLException e){
            e.printStackTrace();

        }
    }
}

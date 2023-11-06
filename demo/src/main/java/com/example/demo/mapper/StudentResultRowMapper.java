package com.example.demo.mapper;

import com.example.demo.model.Student;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component

public class StudentResultRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int numRow) throws SQLException {
        System.out.println("Inside mapRow !!!");
        Student student = new Student();
        student.setId(rs.getInt(1));
        student.setFirstName(rs.getString(2));
        student.setLastName(rs.getString(3));
        student.setMail(rs.getString(4));
        return student;
    }
}

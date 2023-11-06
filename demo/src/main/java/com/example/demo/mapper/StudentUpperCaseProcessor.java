package com.example.demo.mapper;

import com.example.demo.model.Student;
import org.springframework.batch.item.ItemProcessor;

public class StudentUpperCaseProcessor implements ItemProcessor<Student, Student> {
    @Override
    public Student process(Student student) {
        student.setFirstName(student.getFirstName().toUpperCase());
        return student;
    }
}

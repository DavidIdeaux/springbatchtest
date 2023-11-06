package com.example.demo.mapper;

import com.example.demo.model.Student;
import org.springframework.batch.item.file.transform.LineAggregator;

public class FixedLengthLineAggregator implements LineAggregator<Student> {

    @Override
    public String aggregate(Student student) {
        return String.format("%-3s%-25s%-25s%-50s", student.getId(), student.getFirstName(), student.getLastName(), student.getMail());
    }
}

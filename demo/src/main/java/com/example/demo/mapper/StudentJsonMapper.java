package com.example.demo.mapper;

import com.example.demo.model.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.file.transform.LineAggregator;

public class StudentJsonMapper implements LineAggregator<Student> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String aggregate(Student student) {
        String result = null;
        result = convertObjectToJsonString(student);
        return result;
    }

    public static String convertObjectToJsonString(Student student) {
        try {
            return objectMapper.writeValueAsString(student);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }
    }
}

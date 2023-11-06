package com.example.demo.config;

import com.example.demo.mapper.FixedLengthLineAggregator;
import com.example.demo.mapper.StudentResultRowMapper;
import com.example.demo.mapper.StudentUpperCaseProcessor;
import com.example.demo.model.Student;
import jakarta.persistence.EntityManager;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@EnableBatchProcessing
public class FlatFileBatchConfig {
    @Autowired
    private final DataSource dataSource;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private StudentResultRowMapper studentResultRowMapper;
    private Job job;

    private Resource output = new FileSystemResource("output/output.csv");
    public FlatFileBatchConfig(DataSource dataSource, JobRepository jobRepository) {
        this.dataSource = dataSource;
        this.jobRepository = jobRepository;

    }
/*
    @Bean
    public ItemReader<Student> flatFileReader() throws SQLException {
        System.out.println("Read !");
        JdbcCursorItemReader<Student> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setName("studentItemReader");
        reader.setSql("SELECT id, first_name, last_name, mail from students");
        Connection connection=dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs1=  statement.executeQuery("SELECT id, first_name, last_name, mail from students");
        reader.setRowMapper(new StudentResultRowMapper());
        System.out.println("After setRowMapper");
        return reader;
    }

    @Bean
    public ItemProcessor<Student, Student> flatFileProcessor() {
        return new StudentUpperCaseProcessor();
    }

    @Bean
    public FlatFileItemWriter<Student> flatfileWriter() {
        FlatFileItemWriter<Student> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("output/flatfileoutput.txt"));
        writer.setLineAggregator(new FixedLengthLineAggregator());
        return writer;
    }
    @Bean
    public Step executeFlatfileStep(ItemReader<Student> reader, ItemProcessor<Student, Student> processor, ItemWriter<Student> writer) {
        return new StepBuilder("executeFlatfileStep", jobRepository)
                .<Student, Student>chunk(10, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    @Bean
    public Job executeFlatfileJob(Step step1) throws SQLException {
        return new JobBuilder("processFlatfileJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }*/
}

package com.example.demo.config;

import com.example.demo.mapper.*;
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
import org.springframework.batch.item.file.mapping.JsonLineMapper;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@EnableBatchProcessing
public class JsonBatchConfig {
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
    private static final String TARGET_SAMPLE_OUTPUT_DATA_JSON = "target/output/json-sample-output-data.json";
    public JsonBatchConfig(DataSource dataSource, JobRepository jobRepository) {
        this.dataSource = dataSource;
        this.jobRepository = jobRepository;

    }

    @Bean
    public ItemReader<Student> jsonReader() throws SQLException {
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
    public FlatFileItemWriter<Student> jsonWriter() {
        FlatFileItemWriter<Student> writer = new FlatFileItemWriter<>();
        writer.setLineSeparator(AppUtils.COMMA_SEPARATOR);

        StudentHeaderFooterCallBack headerFooterCallback = new StudentHeaderFooterCallBack();
        writer.setHeaderCallback(headerFooterCallback);
        writer.setFooterCallback(headerFooterCallback);

        //writer.setResource(new FileSystemResource("output/jsonoutput.txt"));
        writer.setLineAggregator(new StudentJsonMapper());
        writer.setResource(new FileSystemResource(System.getProperty("student.dir") + File.separator  + TARGET_SAMPLE_OUTPUT_DATA_JSON));
        writer.setEncoding(AppUtils.UTF_8.name());
        writer.setShouldDeleteIfExists(true);
        return writer;
    }
    @Bean
    public Step executeJsonStep(ItemReader<Student> reader, ItemWriter<Student> writer) {
        return new StepBuilder("executeJsonStep", jobRepository)
                .<Student, Student>chunk(10, platformTransactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }
    @Bean
    public Job executeJsonJob(Step step1) throws SQLException {
        return new JobBuilder("processJsonJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }
}

package com.example.demo.config;

import com.example.demo.mapper.StudentResultRowMapper;
import com.example.demo.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableBatchProcessing
public class BatchConfig {


    //Récupère la valeur datasource.url dans le fichier de configuration
    @Autowired
    private final DataSource dataSource;
    //Responsable de créer des metadata renseignant sur l'état du job et step
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


    public BatchConfig(DataSource dataSource, JobRepository jobRepository) {
        this.dataSource = dataSource;
        this.jobRepository = jobRepository;

    }

    //JdbcCursorItemReader est un curseur, il permet de savoir où on se situe à l'intérieur d'un Job
    //Il permet donc de récupérer toutes les données demandées dans un ResultSet
    /*@Bean
    public ItemReader<Student> reader() throws SQLException {
        System.out.println("Read !");
        JdbcCursorItemReader<Student> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setName("studentItemReader");
        reader.setSql("SELECT id, first_name, last_name, mail from students");

        Connection connection=dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs1=  statement.executeQuery("SELECT id, first_name, last_name, mail from students");

        //setRowMapper permet de transformer les lignes de la BD en objets Student
        reader.setRowMapper(new StudentResultRowMapper());
        System.out.println("After setRowMapper");
        return reader;
    }

    @Bean
    public ItemWriter<Student> writer() {
        FlatFileItemWriter<Student> writer = new FlatFileItemWriterBuilder<Student>()
                .name("studentItemWriter")
                .resource(new FileSystemResource("output/output.csv"))
                .delimited()
                .delimiter(",")
                .fieldExtractor(new BeanWrapperFieldExtractor<Student>() {{
                    setNames(new String[] { "id", "firstName", "lastName", "mail"});
                }})
                .lineAggregator(new DelimitedLineAggregator<Student>() {{
                    setDelimiter(",");
                    setFieldExtractor(new BeanWrapperFieldExtractor<Student>() {{
                        setNames(new String[] { "id", "firstName", "lastName", "mail" });
                    }});
                }})
                .build();
        return writer;
    }

    @Bean
    public Step executeStep(ItemReader<Student> reader, ItemWriter<Student> writer) {
        return new StepBuilder("executeStep", jobRepository)
                .<Student, Student>chunk(10, platformTransactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }


    @Bean
    public Job executeJob() throws SQLException {
        return new JobBuilder("processJob", jobRepository)
                .start(executeStep(reader(), writer()))
                .build();
    }*/
}

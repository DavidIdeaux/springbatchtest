package com.example.demo;

import com.example.demo.config.BatchConfig;
import jakarta.persistence.Query;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.example.demo.mapper")
//@ComponentScan(basePackages = {"com.example.demo"})

//public class DemoApplication {
public class DemoApplication implements CommandLineRunner {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	private static final String JOB_ID = "processJob";

	public static void main(String[] args)  {
		System.out.println("Application started !");
		SpringApplication.run(DemoApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		JobParameters parameters = new JobParametersBuilder()
				.addString(JOB_ID, String.valueOf(System.currentTimeMillis())).toJobParameters();
		jobLauncher.run(job, parameters);
	}
}

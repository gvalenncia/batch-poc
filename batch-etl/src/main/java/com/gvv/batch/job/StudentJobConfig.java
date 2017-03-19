package com.gvv.batch.job;

import com.gvv.batch.listener.StudentJobCompletionListener;
import com.gvv.batch.model.Student;
import com.gvv.batch.processor.StudentItemProcessor;
import com.gvv.batch.writer.StudentItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

/**
 * Created by gvalenncia on 3/18/17.
 */
@Configuration
@EnableBatchProcessing
public class StudentJobConfig {

    private static final String EXTRACT_STUDENTS_QUERY = "SELECT * FROM tbl_student";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    public StudentItemProcessor processor;

    @Autowired
    public StudentItemWriter writer;

    @Bean
    public Job studentETLJob(StudentJobCompletionListener listener) {
        return jobBuilderFactory.get("student-etl-job")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(extractStep())
                .end()
                .build();
    }

    private Step extractStep() {
        return stepBuilderFactory.get("extract-step")
                .<Student, Student> chunk(10)
                .reader(studentItemReader())
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<Student> studentItemReader() {
        JdbcCursorItemReader<Student> databaseReader = new JdbcCursorItemReader<>();

        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(EXTRACT_STUDENTS_QUERY);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(Student.class));

        return databaseReader;
    }
}

package com.gvv.batch.job;

import com.gvv.batch.listener.SubjectJobCompletionListener;
import com.gvv.batch.model.Subject;
import com.gvv.batch.processor.SubjectItemProcessor;
import com.gvv.batch.writer.SubjectItemWriter;
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
 * Created by gvalenncia on 3/19/17.
 */
@Configuration
@EnableBatchProcessing
public class SubjectJobConfig {

    private static final String EXTRACT_SUBJECT_QUERY = "SELECT * FROM tbl_subject";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SubjectItemProcessor processor;

    @Autowired
    private SubjectItemWriter writer;

    @Bean(name = "SubjectETLJobBean")
    public Job subjectETLJob(SubjectJobCompletionListener listener) {
        return jobBuilderFactory.get("subject-etl-job")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(extractStep())
                .end()
                .build();
    }

    @Bean(name = "SubjectETLExtractStepBean")
    public Step extractStep() {
        return stepBuilderFactory.get("subject-etl-extract-step")
                .<Subject, Subject> chunk(10)
                .reader(subjectItemReader())
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean(name = "SubjectETLItemReaderBean")
    public ItemReader<Subject> subjectItemReader() {
        JdbcCursorItemReader<Subject> databaseReader = new JdbcCursorItemReader<>();

        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(EXTRACT_SUBJECT_QUERY);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(Subject.class));

        return databaseReader;
    }

}

package com.gvv.batch.job;

import com.gvv.batch.listener.StudentJobCompletionListener;
import com.gvv.batch.model.Student;
import com.gvv.batch.processor.StudentItemProcessor;
import com.gvv.batch.writer.StudentItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.util.Date;

/**
 * Created by gvalenncia on 3/18/17.
 */
@Configuration
@EnableBatchProcessing
public class StudentJobConfig {

    private static final String EXTRACT_STUDENTS_QUERY = "SELECT * FROM tbl_student";
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentJobConfig.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SimpleJobLauncher jobLauncher;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private StudentItemProcessor processor;

    @Autowired
    private StudentItemWriter writer;

    @Autowired
    private StudentJobCompletionListener listener;

    @Scheduled(cron = "${scheduler.cron}")
    public void perform(){
        try {
            JobParameters params = new JobParametersBuilder().addString("date", new Date().toString()).toJobParameters();
            JobExecution execution = jobLauncher.run(studentETLJob(), params);

        } catch (JobExecutionAlreadyRunningException e) {
            LOGGER.error("There was a JobExecutionAlreadyRunningException, " +
                    "when executing the student-etl-job. " + e.getMessage(), e);
        } catch (JobRestartException e) {
            LOGGER.error("There was a JobRestartException, " +
                    "when executing the student-etl-job. " + e.getMessage(), e);
        } catch (JobInstanceAlreadyCompleteException e) {
            LOGGER.error("There was a JobInstanceAlreadyCompleteException, " +
                    "when executing the student-etl-job. " + e.getMessage(), e);
        } catch (JobParametersInvalidException e) {
            LOGGER.error("There was a JobParametersInvalidException, " +
                    "when executing the student-etl-job. " + e.getMessage(), e);
        }
    }

    @Bean(name = "StudentETLJobBean")
    public Job studentETLJob() {
        return jobBuilderFactory.get("student-etl-job")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(extractStep())
                .end()
                .build();
    }

    @Bean(name = "StudentETLExtractStepBean")
    public Step extractStep() {
        return stepBuilderFactory.get("student-etl-extract-step")
                .<Student, Student> chunk(10)
                .reader(studentItemReader())
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean(name = "StudentETLItemReaderBean")
    public ItemReader<Student> studentItemReader() {
        JdbcCursorItemReader<Student> databaseReader = new JdbcCursorItemReader<>();

        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(EXTRACT_STUDENTS_QUERY);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(Student.class));

        return databaseReader;
    }
}

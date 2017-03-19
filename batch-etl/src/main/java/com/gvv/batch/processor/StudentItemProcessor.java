package com.gvv.batch.processor;

import com.gvv.batch.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by gvalenncia on 3/18/17.
 */
@Component
public class StudentItemProcessor implements ItemProcessor<Student, Student> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentItemProcessor.class);

    @Override
    public Student process(Student student) throws Exception {
        LOGGER.info("Processing a student. ");
        return student;
    }
}

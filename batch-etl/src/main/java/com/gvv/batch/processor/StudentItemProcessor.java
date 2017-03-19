package com.gvv.batch.processor;

import com.gvv.batch.model.Student;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by gvalenncia on 3/18/17.
 */
@Component
public class StudentItemProcessor implements ItemProcessor<Student, Student> {

    @Override
    public Student process(Student student) throws Exception {
        return student;
    }
}

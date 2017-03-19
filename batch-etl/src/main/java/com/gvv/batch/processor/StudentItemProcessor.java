package com.gvv.batch.processor;

import com.gvv.batch.model.Student;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by gvalenncia on 3/18/17.
 */
public class StudentItemProcessor implements ItemProcessor<Student, Student> {

    @Override
    public Student process(Student student) throws Exception {
        return student;
    }
}

package com.gvv.batch.writter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gvv.batch.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Created by gvalenncia on 3/19/17.
 */
public class StudentItemWritter implements ItemWriter<Student> {

    private final Logger LOGGER = LoggerFactory.getLogger(StudentItemWritter.class);

    @Override
    public void write(List<? extends Student> students) throws Exception {
        LOGGER.info("+++++writing reesul+++++++");
        File file = new File("Students.json");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, students);
    }
}

package com.gvv.batch.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gvv.batch.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Created by gvalenncia on 3/19/17.
 */
@Component
public class StudentItemWriter implements ItemWriter<Student> {

    private final Logger LOGGER = LoggerFactory.getLogger(StudentItemWriter.class);

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

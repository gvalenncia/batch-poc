package com.gvv.batch.processor;

import com.gvv.batch.model.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by gvalenncia on 3/19/17.
 */
public class SubjectItemProcessor implements ItemProcessor<Subject, Subject> {

    private final Logger LOGGER = LoggerFactory.getLogger(SubjectItemProcessor.class);

    @Override
    public Subject process(Subject subject) throws Exception {
        LOGGER.info("Processing a subject. ");
        return subject;
    }
}

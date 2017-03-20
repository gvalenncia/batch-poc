package com.gvv.batch.processor;

import com.gvv.batch.model.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by gvalenncia on 3/19/17.
 */
@Component
public class SubjectItemProcessor implements ItemProcessor<Subject, Subject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectItemProcessor.class);

    @Override
    public Subject process(Subject subject) throws Exception {
        LOGGER.info("Processing a subject. ");
        return subject;
    }
}

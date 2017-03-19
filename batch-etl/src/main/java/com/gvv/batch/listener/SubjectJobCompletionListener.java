package com.gvv.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * Created by gvalenncia on 3/19/17.
 */
@Component
public class SubjectJobCompletionListener extends JobExecutionListenerSupport {

    private final Logger LOGGER = LoggerFactory.getLogger(SubjectJobCompletionListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("Finishing subject etl job");
        }
    }
}

package com.gvv.batch;

import com.gvv.batch.job.StudentJobConfig;
import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Created by gvalenncia on 3/19/17.
 */
@TestConfiguration
public class TestConfig {

    @Bean(name = "StudentJobLauncherBean")
    public JobLauncherTestUtils studentJobLauncherTestUtils() {
        return new JobLauncherTestUtils() {
            @Override
            @Autowired
            public void setJob(@Qualifier("StudentETLJobBean") Job job) {
                super.setJob(job);

            }
        };
    }

    @Bean(name = "SubjectJobLauncherBean")
    public JobLauncherTestUtils subjectJobLauncherTestUtils() {
        return new JobLauncherTestUtils() {
            @Override
            @Autowired
            public void setJob(@Qualifier("SubjectETLJobBean") Job job) {
                super.setJob(job);

            }
        };
    }

}


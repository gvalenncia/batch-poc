package com.gvv.batch;

import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Created by gvalenncia on 3/19/17.
 */
@TestConfiguration
public class TestConfig {

    @Bean
    public JobLauncherTestUtils createJobLauncherTestUtils(){
        return new JobLauncherTestUtils();
    }

}

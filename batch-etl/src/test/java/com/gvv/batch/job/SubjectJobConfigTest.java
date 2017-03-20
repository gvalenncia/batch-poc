package com.gvv.batch.job;

import com.gvv.batch.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by gvalenncia on 3/19/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class SubjectJobConfigTest {

    @Autowired
    @Qualifier("SubjectJobLauncherBean")
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void contextLoads() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Assert.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
}

package com.java993.jobmanagementservice.core;

import com.java993.jobmanagementservice.api.JobInfo;
import com.java993.jobmanagementservice.api.JobStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JobManagerTest {
    private JobManager jobManager = new JobManager(new JobCreator());

    @Test
    public void addJobTest () throws InterruptedException {
        assertEquals(0, jobManager.getJobInfoList().size());

        Date startDate = Date.from(LocalDateTime.now().plusSeconds(5).atZone(ZoneId.systemDefault()).toInstant());
        String id = jobManager.addJob(TestJob.NAME, new HashMap<>(), startDate);
        assertEquals(1, jobManager.getJobInfoList().size());

        JobInfo jobInfo = jobManager.getJobInfo(id);
        assertEquals(JobStatus.QUEUED, jobInfo.getStatus());
        assertEquals(TestJob.NAME, jobInfo.getType());

        Thread.sleep(6000);

        jobInfo = jobManager.getJobInfo(id);
        assertEquals(JobStatus.SUCCESS, jobInfo.getStatus());
    }

}
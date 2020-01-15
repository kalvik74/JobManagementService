package com.java993.jobmanagementservice.core;

import com.java993.jobmanagementservice.api.Job;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JobCreatorTest {

    private JobCreator jobCreator = new JobCreator();;

    @Test
    public void create() {
        Job testJob = jobCreator.create("TEST_JOB", new HashMap<>());
        assertEquals(TestJob.class, testJob.getClass());
    }

    @Test
    public void create_job_not_found_error() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> jobCreator.create("NOT_FOUND_JOB", new HashMap<>()));
        assertEquals("Job with name: NOT_FOUND_JOB not found", exception.getMessage());
    }

}
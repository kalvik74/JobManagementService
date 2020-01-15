package com.java993.jobmanagementservice.core;

import com.java993.jobmanagementservice.api.Job;

import java.util.Map;

public class TestJob implements Job {
    public final static String NAME = "TEST_JOB";

    @Override
    public void execute() {

    }

    @Override
    public void init(Map<String, String> additionalParams) {

    }
}

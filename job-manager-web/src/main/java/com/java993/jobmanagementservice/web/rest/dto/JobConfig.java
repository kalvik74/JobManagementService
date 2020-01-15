package com.java993.jobmanagementservice.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Map;

public class JobConfig {
    private final Map<String, String> additionalParams;
    private final String jobName;
    private final String startDate;

    public JobConfig(Map<String, String> additionalParams, String jobName, String startDate) {
        this.additionalParams = additionalParams;
        this.jobName = jobName;
        this.startDate = startDate;
    }

    public Map<String, String> getAdditionalParams() {
        return additionalParams;
    }

    public String getJobName() {
        return jobName;
    }

    public String getStartDate() {
        return startDate;
    }
}

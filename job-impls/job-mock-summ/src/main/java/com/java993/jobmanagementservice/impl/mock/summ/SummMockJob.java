package com.java993.jobmanagementservice.impl.mock.summ;

import com.java993.jobmanagementservice.api.Job;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SummMockJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(SummMockJob.class);
    public final static String NAME = "SUMM_MOCK_JOB";
    private String param1;
    private String param2;
    public void execute() {
        LOGGER.info("JOB RESULT: {} + {} = {}", param1, param2, Long.valueOf(param1) + Long.valueOf(param2));
    }

    public void init(Map<String, String> additionalParams) {
        param1 = additionalParams.get("param1");
        param2 = additionalParams.get("param2");
    }
}

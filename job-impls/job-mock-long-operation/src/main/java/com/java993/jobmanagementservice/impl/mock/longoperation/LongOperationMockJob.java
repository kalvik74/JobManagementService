package com.java993.jobmanagementservice.impl.mock.longoperation;

import com.java993.jobmanagementservice.api.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LongOperationMockJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(LongOperationMockJob.class);
    public final static String NAME = "LONG_OPERATION_MOCK_JOB";
    private String waitInSeconds;
    private String repeatCount;
    public void execute() {
        LOGGER.info("EXECUTING LONG JOB......REPEAT COUNT : {}, waitTime {} seconds ", waitInSeconds, repeatCount);
        Integer waitTime = Integer.valueOf(waitInSeconds);
        int repeats = Integer.parseInt(repeatCount);
        for (int i = 0; i < repeats; i++) {
            try {
                Thread.sleep(waitTime  * 1000);
                LOGGER.info("loop {}..............", i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("LONG JOB COMPLETED");
    }

    public void init(Map<String, String> additionalParams) {
        waitInSeconds = additionalParams.get("waitInSeconds");
        repeatCount = additionalParams.get("repeatCount");
    }
}

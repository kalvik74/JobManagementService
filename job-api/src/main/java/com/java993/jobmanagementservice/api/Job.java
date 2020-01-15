package com.java993.jobmanagementservice.api;

import java.util.Map;

public interface Job {
    public void execute();
    void init(Map<String, String> additionalParams);
}

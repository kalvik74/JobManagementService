package com.java993.jobmanagementservice.core;

import com.java993.jobmanagementservice.api.Job;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JobCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobCreator.class);

    private Map<String, Class> jobClasses = new HashMap<>();

    public JobCreator() {
        Reflections reflections = new Reflections("com.java993.jobmanagementservice");
        Set<Class<? extends Job>> classes = reflections.getSubTypesOf(Job.class);
        for(Class class_ : classes) {
            try {
                String name = (String) class_.getField("NAME").get("");
                jobClasses.put(name, class_);
            } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
               LOGGER.error("Error searching class with interface Job", e);
            }
        }
    }

    Job create(String jobName, Map<String, String> additionalParams) {
        if (jobClasses.containsKey(jobName)) {
            Class class_ = jobClasses.get(jobName);
            try {
                Job job = (Job) class_.newInstance();
                job.init(additionalParams);
                return job;
            } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
                LOGGER.error("error creating job", e);
                throw new IllegalArgumentException("error creating job", e);
            }
        } else {
            throw new IllegalArgumentException("Job with name: " + jobName + " not found");
        }
    }
}

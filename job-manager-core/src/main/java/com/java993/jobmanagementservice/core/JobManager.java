package com.java993.jobmanagementservice.core;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableScheduledFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.java993.jobmanagementservice.api.Job;
import com.java993.jobmanagementservice.api.JobInfo;
import com.java993.jobmanagementservice.api.JobStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class JobManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobManager.class);

    @Resource
    private JobCreator jobCreator;

    public JobManager(JobCreator jobCreator) {
        this.jobCreator = jobCreator;
    }

    private Map<String, ScheduledJob> allTasks = new ConcurrentHashMap<String, ScheduledJob>();
    private ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(10);

    public String addJob(String jobName, Map<String, String> additionalParams, Date date) {

        String id = UUID.randomUUID().toString();
        ScheduledJob scheduledJob = new ScheduledJob(
                jobCreator.create(jobName, additionalParams),
                JobInfo.JobInfoBuilder.aJobInfo()
                        .withAdditionalParams(additionalParams)
                        .withDate(date)
                        .withId(id)
                        .withStatus(JobStatus.QUEUED)
                        .withType(jobName)
                        .build()
        );
        LOGGER.info("Added new Job {}, executed time {}", jobName, date);
        allTasks.put(id, scheduledJob);
        ListenableScheduledFuture<?> schedule = MoreExecutors
                .listeningDecorator(executorService)
                .schedule(scheduledJob, date.getTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
        Futures.addCallback(schedule, new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object o) {
                allTasks.get(id).jobInfo.setStatus(JobStatus.SUCCESS);
            }

            @Override
            public void onFailure(Throwable throwable) {
                allTasks.get(id).jobInfo.setStatus(JobStatus.FAILED);
                allTasks.get(id).jobInfo.setErrorMessage(throwable.toString());
            }
        }, executorService);
        return id;
    }

    public JobInfo getJobInfo(String id) {
        if (!allTasks.containsKey(id)) {
            throw new IllegalArgumentException("job with id: " + id + " not found");
        }
        return allTasks.get(id).jobInfo;
    }

    public List<JobInfo> getJobInfoList() {
        return Lists.newArrayList(allTasks.values().stream().map(job -> job.jobInfo).collect(Collectors.toList()));
    }

    private class ScheduledJob implements Comparable<ScheduledJob>, Runnable {
        private Job job;
        private JobInfo jobInfo;

        public ScheduledJob(Job job, JobInfo jobInfo) {
            this.job = job;
            this.jobInfo = jobInfo;
        }

        public int compareTo(ScheduledJob scheduledJob) {
            return this.jobInfo.getDate().compareTo(scheduledJob.jobInfo.getDate());
        }

        @Override
        public void run() {
            allTasks.get(jobInfo.getId()).jobInfo.setStatus(JobStatus.RUNNING);
            job.execute();
        }
    }

}

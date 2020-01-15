package com.java993.jobmanagementservice.api;

import java.util.Date;
import java.util.Map;

public class JobInfo {
    private String id;
    private Date date;
    private JobStatus status;
    private String errorMessage;
    private Map<String, String> additionalParams;
    private String type;

    public JobInfo() {
    }

    public JobInfo(String id, Date date, JobStatus status, String errorMessage, Map<String, String> additionalParams, String type) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.errorMessage = errorMessage;
        this.additionalParams = additionalParams;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public JobStatus getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Map<String, String> getAdditionalParams() {
        return additionalParams;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setAdditionalParams(Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static final class JobInfoBuilder {
        private String id;
        private Date date;
        private JobStatus status;
        private String errorMessage;
        private Map<String, String> additionalParams;
        private String type;

        private JobInfoBuilder() {
        }

        public static JobInfoBuilder aJobInfo() {
            return new JobInfoBuilder();
        }

        public JobInfoBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public JobInfoBuilder withDate(Date date) {
            this.date = date;
            return this;
        }

        public JobInfoBuilder withStatus(JobStatus status) {
            this.status = status;
            return this;
        }

        public JobInfoBuilder withErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public JobInfoBuilder withAdditionalParams(Map<String, String> additionalParams) {
            this.additionalParams = additionalParams;
            return this;
        }

        public JobInfoBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public JobInfo build() {
            return new JobInfo(id, date, status, errorMessage, additionalParams, type);
        }
    }
}

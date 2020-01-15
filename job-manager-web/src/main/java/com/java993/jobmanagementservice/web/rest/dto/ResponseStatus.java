package com.java993.jobmanagementservice.web.rest.dto;

public enum ResponseStatus {
    SUCCESS("SUCCESS"), ERROR("ERROR");
    private String status;

    ResponseStatus(String status) {
        this.status = status;
    }

}

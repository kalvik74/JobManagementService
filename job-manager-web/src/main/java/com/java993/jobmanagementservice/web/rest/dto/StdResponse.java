package com.java993.jobmanagementservice.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class StdResponse<T> {
    private ResponseStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T body;

    public StdResponse() {
    }

    public StdResponse(ResponseStatus status, T body, String message) {
        this.status = status;
        this.body = body;
        this.message = message;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public T getBody() {
        return body;
    }

    public String getMessage() {
        return message;
    }
}

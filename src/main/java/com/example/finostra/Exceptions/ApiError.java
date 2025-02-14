package com.example.finostra.Exceptions;

import java.util.Date;

public class ApiError {
    private Integer statusCode;
    private String message;
    private Date timestamp;

    public ApiError(Integer statusCode, String message, Date timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ApiError() {
        this.statusCode = null;
        this.message = null;
        this.timestamp = null;
    }

    // Getters and Setters
    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

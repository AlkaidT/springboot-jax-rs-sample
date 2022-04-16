package com.iamvickyav.springbootjaxrssample.exception;

import javax.ws.rs.core.Response;

public class ApiException extends RuntimeException {
    private String errorCode;
    private Response.Status status;

    public ApiException(String message, String errorCode, Response.Status status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Response.Status getStatus() {
        return status;
    }
}

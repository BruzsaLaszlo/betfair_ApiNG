package com.betfair.aping.exceptions;

public class ApiNgException extends Exception {

    private String errorDetails;
    private String errorCode;
    private String requestUUID;

    @Override
    public String toString() {
        return "ApiNgException{" +
                "errorDetails='" + errorDetails + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", requestUUID='" + requestUUID + '\'' +
                '}';
    }

    public ApiNgException() {
    }

    public ApiNgException(String errorDetails, String errorCode, String requestUUID) {
        super(errorDetails);
        this.errorDetails = errorDetails;
        this.errorCode = errorCode;
        this.requestUUID = requestUUID;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getRequestUUID() {
        return requestUUID;
    }

    public void setRequestUUID(String requestUUID) {
        this.requestUUID = requestUUID;
    }


}

package com.dsige.sapia.helper;

public class MessageError {

    private String Message;
    private String ExceptionMessage;
    private String ExceptionType;
    private String StackTrace;
    private String Error;

    public MessageError() {
    }

    public MessageError(String message, String exceptionMessage, String exceptionType, String stackTrace) {
        Message = message;
        ExceptionMessage = exceptionMessage;
        ExceptionType = exceptionType;
        StackTrace = stackTrace;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getExceptionMessage() {
        return ExceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        ExceptionMessage = exceptionMessage;
    }

    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
    }

    public String getStackTrace() {
        return StackTrace;
    }

    public void setStackTrace(String stackTrace) {
        StackTrace = stackTrace;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }
}

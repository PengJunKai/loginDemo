package com.peng.utils.exception;


import com.peng.utils.StrKit;

public class AppException extends RuntimeException {

    private String requestId;

    private String errCode;

    private String errMsg;

    private ExceptionType errorType;

    public AppException(ExceptionType errorType) {
        super(errorType.getDefaultMsg());
        this.errCode = errorType.getCode();
        this.errMsg = errorType.getDefaultMsg();
        this.setErrorType(errorType);
    }

    public AppException(ExceptionType errorType, Exception e) {
        super(errorType.getDefaultMsg());
        this.errCode = errorType.getCode();
        this.errMsg = ExceptionKit.toString(e);
        this.setErrorType(errorType);
    }


    public AppException(ExceptionType errorType, String errMsg) {
        super(StrKit.isEmpty(errMsg) ? errorType.getDefaultMsg() : errMsg);
        this.errCode = errorType.getCode();
        this.errMsg = errMsg;
        this.setErrorType(errorType);
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }


    public ExceptionType getErrorType() {
        return errorType;
    }

    public void setErrorType(ExceptionType errorType) {
        this.errorType = errorType;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + (null == getRequestId() ? "" : "\r\nRequestId : " + getRequestId());
    }
}

package com.wallimn.iteye.sp.asset.common.exception;

/**
 * 用于描述一些确定的、已知的异常。可以将枚举类型的状态描述转化为异常。
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月21日 下午9:46:41<br>
 *
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -3476774987375951643L;
    private Integer code;
    private String msg;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }



    public ServiceException() {
    }

    public ServiceException(IStatusCode statusCode) {
        super(statusCode.getMessage());
        this.code = statusCode.getCode();
        this.msg = statusCode.getMessage();
    }

    public ServiceException(IStatusCode statusCode, Throwable e) {
        super(e);
        this.code = statusCode.getCode();
        this.msg = statusCode.getMessage();
    }

    public ServiceException(IStatusCode statusCode, Object[] msgArguments) {
        this.code = statusCode.getCode();
        String unformatedMsg = statusCode.getMessage();
        this.msg = String.format(unformatedMsg,msgArguments);
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
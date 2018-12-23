package com.wallimn.iteye.sp.asset.common.exception;

/**
 * 系统异常描述。
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月21日 下午9:51:04<br>
 *
 */
public enum StatusCode implements IStatusCode {
    SUCCESS(200, "操作成功！"),
    UNAUTHORIZED(401, "您的请求未获授权！"),
    NOTLOGINED(402, "您还没有登录，或登录已到期！"),
    FORBIDDEN(403, "您没有权限查看此内容！"),
    INVALIDTOKEN(404,"凭据非法"),
    RUNTIMEERROR(410,"运行时错误：%s"),
    SQLERROR(411,"SQL错误: %s"),
    INTERNAL_ERROR(500, "系统错误！");

    /**
     * 错误码
     */
    private int code;
    /**
     * 消息提示
     */
    private String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

	@Override
	public String toJson() {
		return String.format("{'code':%d,'msg':'%s'}", this.code,this.msg);
	}

}

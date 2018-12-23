package com.wallimn.iteye.sp.asset.common.exception;

import java.io.Serializable;
/**
 * 状态码及描述的接口
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月21日 下午9:50:25<br>
 *
 */
public interface IStatusCode extends Serializable {
    int getCode();

    String getMessage();
    
    String toJson();
}

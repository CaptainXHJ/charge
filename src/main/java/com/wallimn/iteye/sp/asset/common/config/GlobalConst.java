package com.wallimn.iteye.sp.asset.common.config;

/**
 * 定义一些全局常量
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月21日 下午9:54:21<br>
 *
 */
public class GlobalConst {
    /**
     * 保存在Session中的key，是SysUserVo类型
     */
    public static final String loginedUserKey="loginedUser";
    public static final String author="wallimn";
    
    public static final String adminUserId="1";
    
    public static final String globalExceptionKey="globalException";
    
    /**
     * 保存中Session中的key，记录登录用户的禁止的功能，是个链表
     */
    public static final String loginedUserExFuncsKey="loginedUserFunc";
    
    /**
     * 有效标志的值
     */
    public static final String validFlag="1";
    /**
     * 无效标志的值
     */
    public static final String invalidFlag="0";
}

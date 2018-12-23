package com.wallimn.iteye.sp.asset.bus.charge.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 物业实体类
 * @author Captain J
 * @date 2018/12/22 9:12
 */
public class Agency implements Serializable {

    private static final long serialVersionUID = 5483593743956894011L;
    private Long id;// bigint(20) NOT NULL
    private String username; // varchar(50) NOT NULL 用户名
    private String mobile;// varchar(20) NOT NULL 手机号
    private String password; // varchar(200) NULL 密码
    private Date lastTime; // datetime NULL 最近登录时间
    private int logincount; // int(10) NULL 登陆次数

    /**
     *
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public int getLogincount() {
        return logincount;
    }

    public void setLogincount(int logincount) {
        this.logincount = logincount;
    }
}

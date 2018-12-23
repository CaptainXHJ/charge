package com.wallimn.iteye.sp.asset.bus.charge.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 特殊用户信息表
 *
 * @author chen，2018年12月12日 上午18:14:05
 */
public class UserSpecial implements Serializable {

    private static final long serialVersionUID = 5834058789897008603L;

    private String mobile;
    private String name;
    private String calcTypeId;
    private String exp1;
    private String exp2;

    public String getCalcTypeId() {
        return calcTypeId;
    }

    public void setCalcTypeId(String calcTypeId) {
        this.calcTypeId = calcTypeId;
    }

    public String getMobile() {
        return mobile;
    }

    public String getName() {
        return name;
    }

//    public String getCalc_type_id() {
//        return calc_type_id;
//    }

    public String getExp1() {
        return exp1;
    }

    public String getExp2() {
        return exp2;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setCalc_type_id(String calc_type_id) {
//        this.calc_type_id = calc_type_id;
//    }

    public void setExp1(String exp1) {
        this.exp1 = exp1;
    }

    public void setExp2(String exp2) {
        this.exp2 = exp2;
    }
}

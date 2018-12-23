package com.wallimn.iteye.sp.asset.bus.charge.model.vo;

import com.wallimn.iteye.sp.asset.bus.charge.model.Agency;

/**
 * @author Captain J
 * @date 2018/12/22 17:28
 */
public class AgencyVo extends Agency {

    private static final long serialVersionUID = 6482790281582618767L;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

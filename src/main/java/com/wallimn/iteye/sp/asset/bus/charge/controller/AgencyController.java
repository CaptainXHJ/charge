package com.wallimn.iteye.sp.asset.bus.charge.controller;

import com.jpay.vo.AjaxResult;
import com.wallimn.iteye.sp.asset.bus.charge.model.Agency;
import com.wallimn.iteye.sp.asset.bus.charge.model.User;
import com.wallimn.iteye.sp.asset.bus.charge.model.vo.AgencyVo;
import com.wallimn.iteye.sp.asset.bus.charge.service.ChargeService;
import com.wallimn.iteye.sp.asset.bus.charge.service.AgencyService;
import com.wallimn.iteye.sp.asset.bus.charge.service.UserCostService;
import com.wallimn.iteye.sp.asset.common.exception.ServiceException;
import com.wallimn.iteye.sp.asset.common.exception.StatusCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 物业控制层
 *
 * @author Captain J
 * @date 2018/12/22 9:36
 */
@RestController
@RequestMapping("/api/01/agency")
public class AgencyController {

    private static final Logger log = LoggerFactory.getLogger(AgencyController.class);

    @Autowired
    HttpServletRequest request;

    @Autowired
    AgencyService agencyService;

    @Autowired
    UserCostService userCostService;

    @Autowired
    ChargeService chargeService;

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/login")
    public AjaxResult login(@RequestParam(required = true) String mobile, @RequestParam(required = true) String password) {
        AjaxResult ajaxResult = new AjaxResult();
        Agency agency = agencyService.selectAgencyByMobile(mobile);
        if (agency.getLogincount() >= 5) {//判断登录次数是否大于指定次数
            ajaxResult.addError("尝试次数过多，请明天登录");
        } else {//登录次数没有超过尝试次数
            if (agency != null) {//用户存在
                if (password.equals(agency.getPassword())) {//判断密码是否相等
                    //将登陆次数设置为0
                    agency.setLogincount(0);
                    agencyService.updateAgencyLogincount(agency);
                    //封装返回对象
                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("sessionId", request.getSession().getId());
                    data.put("user", agency);
                    ajaxResult.setData(data);

                } else {//用户名密码不正确
                    agency.setLogincount(agency.getLogincount() + 1);
                    agencyService.updateAgencyLogincount(agency);
                    ajaxResult.addError("用户名或者密码不正确");
                }

            } else {//用户不存在
                ajaxResult.addError("用户未注册");
            }
        }
        return ajaxResult;

    }

    private boolean isValidCode(String mobile,String code){
        return true;
    }

    /**
     * 修改代理商信息
     * @param agencyVo
     * @return
     */
    @PutMapping("/updateAgency")
    public Agency updateAgency(@RequestBody AgencyVo agencyVo){
        if(StringUtils.isNotEmpty(agencyVo.getMobile())
                && StringUtils.isNotEmpty(agencyVo.getCode())
                && this.isValidCode(agencyVo.getMobile(), agencyVo.getCode())){
            Agency agency = agencyService.selectAgencyByMobile(agencyVo.getMobile());
            agency.setMobile(agencyVo.getMobile());
            agency.setUsername(agencyVo.getUsername());
            agency.setPassword(agency.getPassword());
            agency.setLogincount(0);
            agency.setLastTime(new Date());

//            User user = this.userService.selectUserByOpenid(entity.getOpenid());
//            user.setMobile(entity.getMobile());
//            this.userService.insertEntity(user);
            return agency;
        }
        else{
            log.error("mobile、code不能为空！");
            throw new ServiceException(StatusCode.RUNTIMEERROR,new Object[]{"mobile或者code为空！"});
        }
    }

    /**
     * 根据stationId 查询用户消费记录
     * @return
     */
//    @GetMapping("/findCostByStationId")
//    public List<UserCost> selectUserCostByStationId(@RequestParam(required = true)String stationIds){
//        List<String> ids = JSON.parseArray(stationIds, String.class);
//        return userCostService.selectUserCostByStationId(ids);
//
//    }
}

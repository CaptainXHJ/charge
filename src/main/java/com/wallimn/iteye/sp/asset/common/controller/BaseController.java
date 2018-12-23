package com.wallimn.iteye.sp.asset.common.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * controller基类
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月21日 下午9:55:44<br>
 *
 */
public class BaseController {
	
	private static Logger log = LoggerFactory.getLogger(BaseController.class);
    private static final String SUCCESS_JSON="{code:0,msg:\"success\"}";
    private static final String FAIL_JSON="{code:-1,msg:\"fail\"}";
    private static final String MESSAGE_JSON="{code:%d,msg:\"%s\"}";
    

    protected String getSuccessJson(){
        return SUCCESS_JSON;
    }
    protected String getFailJson(){
        return FAIL_JSON;
    }
    
    protected void printMap(Map<String,Object> param){
    	for(String key : param.keySet()){
    		log.debug("{}={}",key,param.get(key));
    	}
    }
    protected String getSuccessJson(String message){
    	return String.format(MESSAGE_JSON,0,message);
    }

    protected  String getFailJson(String msg){
        return String.format(MESSAGE_JSON,-1,msg);
    }
    protected  String getMessageJson(int code,String msg){
    	return String.format(MESSAGE_JSON,code,msg);
    }

    /**
     * 返回排序条件
     * @param orderKey
     * @param orderDir
     * @param defaultValue
     * @return
     */
    public String getOrderCondition(String sortKey,String sortDir,String defaultValue){
        String orderCond = null;
        //normal是控件传上来的东西，表示不排序的意思
        if(StringUtils.isNotEmpty(sortKey) || "normal".equals(sortDir)){
            if(StringUtils.isNotEmpty(sortDir)){
                orderCond = sortKey + "." + sortDir;
            }
            else{
                orderCond = sortKey + ".desc" ;
            }
        }
        else{
            orderCond = defaultValue;
        }
        return orderCond;
    }
    
    /**
     * 返回排序条件
     * @param orderKey
     * @param orderDir
     * @param defaultValue
     * @return
     */
    public String getOrderCondition(Map<String,String> map){
        String orderCond = null;
        //normal是控件传上来的东西，表示不排序的意思
        if(map!=null){
        	for (String key : map.keySet()) {
                if(StringUtils.isNotEmpty(key) || "normal".equals(map.get(key))){
                	if(orderCond==null){
                		if(StringUtils.isNotEmpty(map.get(key))){
                			orderCond = key + "." + map.get(key)+",";
                		}else{
                			orderCond = key + "." + "desc" + ",";
                		}
                	}else{
                		if(StringUtils.isNotEmpty(map.get(key))){
                			orderCond += key + "." + map.get(key)+",";
                		}else{
                			orderCond += key + "." + "desc" + ",";
                		}
                	}
                }else{
                	orderCond += key + ".desc";
                }    
    		}
        }
        log.debug("orderCondition: {}",orderCond);
        return orderCond;
    }
    
    /**
     * 默认不进行排序省略一个参数的函数。
     * @param orderField
     * @param orderDir
     * @return
     */
    public String getOrderCondition(String sortKey,String sortDir){
        return this.getOrderCondition(sortKey,sortDir,null);
    }
    /**
     * 生成运行时异常
     * @param message
     * @return
     *<br>作者：wallimn，时间：2017年12月21日 下午9:56:50
     */
    public RuntimeException buildException(String message){
    	return new RuntimeException(message);
    }

    
//    /**
//     * 
//     * @param request
//     * @return
//     */
//    public SysUserVo getCurrentUser(HttpServletRequest request){
//    	SysUserVo user = (SysUserVo) request.getSession().getAttribute(GlobalConst.loginedUserKey);
//    	if(user==null){
//    		log.error("用户未登录!");
//    		throw new HServiceException(StatusCode.NOTLOGINED);
//    	}
//    	else{
//    		return user;
//    	}
//    }
//    
//    
//    /**
//     * 获取登录用户ID
//     * @param request
//     * @return
//     *<br>作者：wallimn，时间：2018年4月11日 下午10:20:21
//     */
//    protected String getCurrentUserId(HttpServletRequest request){
//    	SysUserVo user = this.getCurrentUser(request);
//    	//上面函数不会返回空。
//    	return user.getId();
//    }
    
}
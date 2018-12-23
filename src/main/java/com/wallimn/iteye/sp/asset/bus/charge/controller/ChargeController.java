package com.wallimn.iteye.sp.asset.bus.charge.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.wallimn.iteye.sp.asset.bus.charge.model.*;
import com.wallimn.iteye.sp.asset.bus.charge.model.vo.PlugVo;
import com.wallimn.iteye.sp.asset.bus.charge.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.jpay.vo.AjaxResult;
import com.wallimn.iteye.sp.asset.bus.charge.form.EndChargeForm;
import com.wallimn.iteye.sp.asset.bus.charge.form.StartChargeForm;
import com.wallimn.iteye.sp.asset.bus.charge.model.vo.StationVo;
import com.wallimn.iteye.sp.asset.bus.charge.model.vo.UserVo;
import com.wallimn.iteye.sp.asset.bus.charge.task.SendCmdTask;
import com.wallimn.iteye.sp.asset.bus.charge.task.UserCostTask;
import com.wallimn.iteye.sp.asset.bus.charge.util.CmdConst;
import com.wallimn.iteye.sp.asset.bus.charge.util.ControlStateConst;
import com.wallimn.iteye.sp.asset.bus.charge.util.ConvertUtil;
import com.wallimn.iteye.sp.asset.bus.charge.util.PlugStateConst;
import com.wallimn.iteye.sp.asset.bus.charge.util.UserCostStateConst;
import com.wallimn.iteye.sp.asset.common.config.GlobalConfig;
import com.wallimn.iteye.sp.asset.common.config.GlobalConst;
import com.wallimn.iteye.sp.asset.common.controller.BaseController;
import com.wallimn.iteye.sp.asset.common.exception.ServiceException;
import com.wallimn.iteye.sp.asset.common.exception.StatusCode;
import com.wallimn.iteye.sp.asset.common.sms.SmsRecord;
import com.wallimn.iteye.sp.asset.common.sms.SmsService;
import com.wallimn.iteye.sp.asset.common.util.HttpUtil;

/**
 * 充电桩小程序服务端，调用url的根为https://wxbg.guochenkj.com/asset/api/01/charge
 * <br>
 * 使用code换取openid信息的地址：https://wxbg.guochenkj.com/asset/api/01/wxsys/session
 * @author wallimn，2018年9月9日 下午11:32:38
 *
 */
@RestController
@RequestMapping("/api/01/charge")
public class ChargeController extends BaseController{
	private static final Logger log = LoggerFactory.getLogger(ChargeController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	ChargeService chargeSerivce;
	
	@Autowired 
	UserCostService userCostService;
	
	@Autowired 
	UserSaveService userSaveService;
	
	@Autowired 
	GlobalConfig globalConfig;
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	UserLogService userLogService;
	
	@Autowired
	UserCostTask userCostTask;
	@Autowired
	SendCmdTask sendCmdTask;
	
	@Autowired
	MoneyCalcService moneyCalcService;
	
	@Autowired
	HttpServletRequest request;

	/**
	 * 导入特殊用户服务类
	 * 20181212 by chen
	 */
	@Autowired
	UserSpecialService userSpecialService;
	
	private static ObjectMapper objectMapper;
	static{
		objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}
	
	/**
	 * 查看版本信息
	 * @author wallimn，2018年9月9日 下午11:34:18
	 * @return 版本字符串，两位数字
	 */
	@RequestMapping("/version")
	public String getVersion(){
		log.info("版本：01");
		return "01";
	}

	/**
	 * 返回当前登录用户
	 * @author wallimn，2018年10月12日 下午10:43:12
	 * @return
	 */
	@RequestMapping("/curruser")
	public User getLoginedUser(){
		return (User) this.request.getSession().getAttribute(GlobalConst.loginedUserKey);
	}
	/**
	 * 根据openid查询用户信息
	 * @author wallimn， 2018年9月9日 下午11:32:58
	 * @param openid 微信或支付宝的用户openid
	 * @return User类型对象
	 */
	@RequestMapping(value="/user",method=RequestMethod.GET)
	public User selectUser(@RequestParam(required=true) String openid){
		User user = this.userService.selectUserByOpenid(openid);
		return user;
	}
	@RequestMapping(value="login", method=RequestMethod.POST)
	public AjaxResult login(String openid){
		User user = this.userService.selectUserByOpenid(openid);
		AjaxResult result = new AjaxResult();
		if(user!=null){
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("sessionId", request.getSession().getId());
			data.put("user", user);
			result.setData(data);
		}
		else{
			result.addError("用户未注册");
		}
		return result;
	}
	
	/**
	 * 新增用户，openid、mobile不能为空
	 * @author wallimn，2018年9月9日 下午11:37:30
	 * @param user User对象
	 * @return 用户信息，包括用户的ID
	 */
	@RequestMapping(value="/user", method = RequestMethod.POST)
	public AjaxResult insertUser(@RequestBody UserVo user){
		AjaxResult result = new AjaxResult();
		if(StringUtils.isNotEmpty(user.getOpenid())
				&& StringUtils.isNotEmpty(user.getMobile())
				&& StringUtils.isNotEmpty(user.getCode())){
			SmsRecord sms = this.smsService.getSmsRecord(user.getOpenid());
			User ou = this.userService.selectUserByOpenid(user.getOpenid());
			if(ou!=null){
				result.addError("用户已经注册过");
			}
			else{
				log.debug("用户校验码：{}",user.getCode());
				if(sms!=null 
						&& user.getCode().equals(sms.getCode())
						&& user.getMobile().equals(sms.getMobile())
						){
					log.debug("发出校验码：{}",sms.getCode());
					user.setBalance(0L);
					user.setCreateTime(new Date());
					this.userService.insertEntity(user);
					result.setData(user);
				}
				else{
					result.addError("校验码或手机不正确");
				}
			}
		}
		else{
			//log.error("openid、手机号不能为空！");
			//throw new ServiceException(StatusCode.RUNTIMEERROR,new Object[]{"openid为空！"});
			result.addError("openid、手机号不能为空");
		}
		return result;
	}
	private boolean isValidCode(String mobile,String code){
		return true;
	}
	/**
	 * 修改用户手机
	 * @author wallimn，2018年9月23日 上午12:03:13
	 * @param entity 用户实体信息
	 * @return 用户信息
	 */
	@RequestMapping(value="/usermobile", method = RequestMethod.PUT)
	public User updateUserMobile(@RequestBody UserVo entity){
		if(StringUtils.isNotEmpty(entity.getOpenid())
				&& StringUtils.isNotEmpty(entity.getCode())
				&& this.isValidCode(entity.getMobile(), entity.getCode())){
			User user = this.userService.selectUserByOpenid(entity.getOpenid());
			user.setMobile(entity.getMobile());
			this.userService.insertEntity(user);
			return user;
		}
		else{
			log.error("openid、code不能为空！");
			throw new ServiceException(StatusCode.RUNTIMEERROR,new Object[]{"openid或者code为空！"});
		}
	}
	
	/**
	 * 修改用户信息。用户id（userId）、openid、mobile不能为空。
	 * 
	 * @author wallimn，2018年9月9日 下午11:42:25
	 * @param user User对象
	 * @return 用户信息
	 */
	@RequestMapping(value="/user", method = RequestMethod.PUT)
	public AjaxResult updateUser(@RequestBody UserVo user){
		AjaxResult result = new AjaxResult();
		if(user.getUserId()!=null 
				&& StringUtils.isNotEmpty(user.getMobile())
				&& StringUtils.isNotEmpty(user.getCode())
				&& StringUtils.isNotEmpty(user.getOpenid())){
			SmsRecord sms = this.smsService.getSmsRecord(user.getOpenid());
			if(sms!=null 
					&& user.getCode().equals(sms.getCode())
					&& user.getMobile().equals(sms.getMobile())){
				this.userService.updateEntity(user);
				User su = this.userService.selectEntityById(user.getUserId());
				result.success(su);
			}
			else{
				result.addError("验证码、或手机不正确");
			}
		}
		else{
			//throw new ServiceException(StatusCode.RUNTIMEERROR,new Object[]{"userId为空！"});
			result.addError("用户ID、手机、校验码不能为空");
		}
		return result;
	}
	
	/**
	 * 用户使用账户余额支付消费
	 * @author wallimn，2018年9月28日 下午10:01:42
	 * @param openid 用户的openid
	 * @param costId 支付的ID
	 * @return 操作结果
	 */
	public AjaxResult payUserCost(@RequestParam String openid,@RequestParam Long costId){
		AjaxResult result = new AjaxResult();
		
		User user = this.userService.selectUserByOpenid(openid);		
		UserCost cost = this.userCostService.selectEntityById(costId);
		String check = this.chargeSerivce.isCostCanPay(user, cost);
		if(check!=null){
			result.addError(check);
			return result;
		}
		
		this.chargeSerivce.decreaseUserMoney(openid, costId);
		return result;
	}
	
	/**
	 * 查询用户最后一条消费记录，用于判断用户是否有欠费，并可用于最后一笔消费结账
	 * @author wallimn，2018年9月11日 上午1:08:16
	 * @param userId 用户ID
	 * @return 消费信息
	 */
	@RequestMapping(value="lastusercost", method = RequestMethod.GET)
	public UserCost selectLastUserCost(String openid){
		User user = this.userService.selectUserByOpenid(openid);
		if(user!=null){
			
			return this.userCostService.selectLastUserCostByUserId(user.getUserId());
		}
		else{
			log.error("用户不存在：openid={}",openid);
			return null;
		}
	}
	
	/**
	 * 新增用户消费信息
	 * @author wallimn，2018年9月11日 上午12:49:47
	 * @param entity 接收json格式的对象，格式数据数据库表
	 * @return 消息信息
	 */
	@RequestMapping(value="usercost", method=RequestMethod.POST)
	public UserCost insertUserCost(@RequestBody UserCost entity){
		if(entity.getUserId()!=null){
			this.userCostService.insertEntity(entity);
		}
		else{
			throw new ServiceException(StatusCode.RUNTIMEERROR,new Object[]{"用户ID为空！"});
		}
		return entity;
	}
	
	/**
	 * 修改用户消费信息
	 * @author wallimn，2018年9月11日 上午12:50:20
	 * @param entity 接收json格式的对象，格式数据数据库表
	 * @return 消费信息
	 */
	@RequestMapping(value="usercost", method=RequestMethod.PUT)
	public UserCost updateUserCost(@RequestBody UserCost entity){
		if(entity.getUserId()!=null || entity.getCostId()==null){
			this.userCostService.updateEntity(entity);
			entity = this.userCostService.selectEntityById(entity.getCostId());
		}
		else{
			throw new ServiceException(StatusCode.RUNTIMEERROR,new Object[]{"用户ID为空或者CostId为空！"});
		}
		return entity;
	}
	
	/**
	 * 查询指定用户的消费记录
	 * @author wallimn，2018年9月11日 上午12:57:40
	 * @param userId 用户ID
	 * @param limit 每页条数
	 * @param page 页码
	 * @return 消费列表
	 */
	@RequestMapping(value="usercosts", method=RequestMethod.GET)
	public PageList<UserCost> selectUserCostList(@RequestParam(required = true) Long userId,
			@RequestParam(defaultValue = "10") Integer limit, 
			@RequestParam(defaultValue = "1") Integer page){
		PageBounds pb = new PageBounds(page, limit, Order.formString(this.getOrderCondition("cost_id", "desc")));
		UserCost entity = new UserCost();
		entity.setUserId(userId);
		return this.userCostService.selectEntityList(entity, pb);
	}
	
	@RequestMapping(value="usercost", method={RequestMethod.GET,RequestMethod.POST})
	public UserCost selectUserCost(Long id){
		return this.userCostService.selectEntityById(id);
	}
	
	
	/**
	 * 新增用户消费信息
	 * @author wallimn，2018年9月11日 上午12:49:47
	 * @param entity 接收json格式的对象，格式数据数据库表
	 * @return 消费信息
	 */
	@RequestMapping(value="usersave", method=RequestMethod.POST)
	public UserSave insertUserSave(@RequestBody UserSave entity){
		if(entity.getUserId()!=null){
			this.userSaveService.insertEntity(entity);
		}
		else{
			throw new ServiceException(StatusCode.RUNTIMEERROR,new Object[]{"用户ID为空！"});
		}
		return entity;
	}
	
	/**
	 * 修改用户消费信息
	 * @author wallimn，2018年9月11日 上午12:50:20
	 * @param entity 接收json格式的对象，格式数据数据库表
	 * @return 消费信息
	 */
	@RequestMapping(value="usersave", method=RequestMethod.PUT)
	public UserSave updateUserSave(@RequestBody UserSave entity){
		if(entity.getUserId()!=null || entity.getSaveId()==null){
			this.userSaveService.updateEntity(entity);
			entity = this.userSaveService.selectEntityById(entity.getSaveId());
		}
		else{
			throw new ServiceException(StatusCode.RUNTIMEERROR,new Object[]{"用户ID为空或者CostId为空！"});
		}
		return entity;
	}
	
	/**
	 * 查询指定用户的消费记录
	 * @author wallimn，2018年9月11日 上午12:57:40
	 * @param userId 用户ID
	 * @param limit 每页条数
	 * @param page 页码
	 * @return 返回分页对象
	 */
	@RequestMapping(value="usersaves", method=RequestMethod.GET)
	public PageList<UserSave> selectUserSaveList(@RequestParam(required = true) Long userId,
			@RequestParam(defaultValue = "10") Integer limit, 
			@RequestParam(defaultValue = "1") Integer page){
		PageBounds pb = new PageBounds(page, limit, Order.formString(this.getOrderCondition("save_id", "desc")));
		UserSave entity = new UserSave();
		entity.setUserId(userId);
		return this.userSaveService.selectEntityList(entity, pb);
	}
	
	/**
	 * 用于接收通信服务器的消息。目录仅是显示收到的消息内容，未做进一步处理。
	 * @author wallimn，2018年9月11日 上午1:14:23
	 * @param param 收到的json数据
	 * @return 简单地返回ok
	 */
	@RequestMapping(value="recvmsg", method=RequestMethod.POST)
	public AjaxResult recvMessage(@RequestBody Map<String,Object> param){
		AjaxResult ar = new AjaxResult();
		String cmd = String.valueOf(param.get("cmd"));
		if(StringUtils.isEmpty(cmd)){
			log.error("命令代码为空！");
			ar.addError("命令代码为空");
			return ar;
		}
		else if(CmdConst.TimeSync.getCode().equals(cmd)){//同步时钟
			//ActionResult ar = new ActionResult();
			//ar.setCode("success");
			Map<String,Object> data = new HashMap<String,Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			data.put("time", sdf.format(new Date()));
			ar.setData(data);
			String content;
			try {
				content = objectMapper.writeValueAsString(ar);
				String result = HttpUtil.post(this.globalConfig.getChargeService(), content);
				log.debug("发送时钟同步结果：{}",result);
			} catch (Exception e) {
				log.error("转化JSON出错：{}",e.getMessage());
				e.printStackTrace();
				ar.addError(e.getMessage());
				return ar;
			}
		}
		else if(CmdConst.ReportState.getCode().equals(cmd)){//报告状态
			Long plugId = ConvertUtil.toLong(param.get("plugId"));
			UserCost cost = this.userCostService.selectLastUserCostByPlugId(plugId);
			Plug plug = this.chargeSerivce.selectPlugById(plugId);
			if(cost==null || plug==null){
				ar.addError("指定的插座不存在，plugId="+plugId);
				log.error("对应插座或相关的消费不存在：{}",plugId);
				return ar;
			}
			this.userCostTask.remove(cost.getCostId());
			Object stateValue = param.get("state");
			String state = ConvertUtil.ascToString(stateValue);
			log.debug("上报状态：{}，消费ID：{}，插座ID：{}，插座二维码：{}，插座原状态：{}",
					state,cost.getCostId(),plug.getPlugId(),plug.getPlugBarcode(),cost.getChargeState());
			if(ControlStateConst.isEndStateOfControl(state)){//断开（正常、异常、到时）
				log.debug("充电结束：{}",state);
				cost.setChargeEndTime(new Date());
				UserCostStateConst csc = UserCostStateConst.getEndStateOfCost(state);
				cost.setChargeState(csc.getCode());//充电结束
				//cost.setChargeMinutes((cost.getChargeEndTime().getTime()-cost.getChargeBeginTime().getTime())/1000);
				//cost.setPayMoney(this.chargeSerivce.calcChargeCostMoney(cost));
				//this.chargeSerivce.calcChargeCostMoney(cost);
				/**
				 * 计算消费金额处理修改
				 * 20181213 by chen
				 */
				User user = this.userService.selectEntityById(cost.getUserId());
				this.moneyCalcService.calcChargeCostMoney(cost, user.getMobile());
				if(cost.getCostMoney()==0L){
					cost.setChargeState(UserCostStateConst.Paied.getCode());
					log.warn("金额为0，设置为已付。costId={}",cost.getCostId());
				}
				else{
					if(user.getBalance()>=cost.getCostMoney()){
						this.chargeSerivce.decreaseUserMoney(user, cost);
						//cost.setPayMoney(cost.getCostMoney());
						//cost.setChargeState(UserCostStateConst.Paied.getCode());
						log.debug("用户金额直接扣款");
					}
					else{
						log.warn("用户金额不足，用户ID：{}，余额：{}，消费：{}",user.getUserId(),user.getBalance(),cost.getCostId());
					}
				}
				this.userCostService.updateEntity(cost);
			}
			else if(ControlStateConst.Connect.getCode().equals(state)){//吸合正常，开始充电
				if(UserCostStateConst.Prepare.getCode().equals(cost.getChargeState())
						|| UserCostStateConst.Unplug.getCode().equals(cost.getChargeState())){//未插好状态
					cost.setChargeState(UserCostStateConst.Begin.getCode());//开始充电状态
					cost.setChargeBeginTime(new Date());
					Long power = ConvertUtil.toLong(param.get("power"));
					if(power==null){
						log.error("未上报功率信息：{}",power);
					}
					cost.setChargePower(power);
					//删除任务，如果收到吸合正常，移除任务
					this.sendCmdTask.remove(plugId);
					this.userCostService.updateEntity(cost);
					log.debug("收到吸合状态报告，开始充电");
				}
				else{
					ar.addError("该插座未对应的消费处于等待开始状态，状态为： "+cost.getChargeState());
					log.error("该插座未对应的消费处于等待开始状态，状态为：{}",cost.getChargeState());
					return ar;
				}
			}
			else if(ControlStateConst.Fault.getCode().equals(state)){//故障
				this.sendCmdTask.remove(plugId);
				cost.setChargeState(UserCostStateConst.Fault.getCode());//
				cost.setChargeEndTime(new Date());
				this.userCostService.updateEntity(cost);
				log.debug("插座故障 ");
				ar.addError("插座故障");
				return ar;
			}
			else if(ControlStateConst.Unplug.getCode().equals(state)){//未接通
				cost.setChargeState(UserCostStateConst.Unplug.getCode());//
				cost.setChargeEndTime(new Date());
				this.userCostService.updateEntity(cost);
				log.debug("插座未接通 ");
				ar.addError("插座未接通");
				return ar;
			}
			else if(ControlStateConst.Over.getCode().equals(state)){//超过额定功率
				this.sendCmdTask.remove(plugId);
				cost.setChargeState(UserCostStateConst.OverPower.getCode());//开始充电状态
				cost.setChargeEndTime(new Date());
				this.userCostService.updateEntity(cost);
				log.debug("超过额定功率 ");
				ar.addError("超过额定功率");
				return ar;
			}
			else{
				log.error("未知的上报状态：{}",state);
				ar.addError("未知的上报状态："+state);
				return ar;
			}
		}
		else{
			log.warn("未明确处理方式的命令：{}",cmd);
			ar.addError("未明确处理方式的命令："+cmd);
			return ar;
		}
		return ar;
	}
	
	/**
	 * 通知后台向通信服务器发送消息。需要在配置文件中指定charge.service参数。
	 * @author wallimn，2018年9月11日 上午1:22:00
	 * @param param 要发送的json数据
	 * @return 通信服务器反馈消息
	 */
	@RequestMapping(value="sendmsg", method=RequestMethod.POST)
	public String sendMessage(@RequestBody Map<String,String> param){

		String result = HttpUtil.post(this.globalConfig.getChargeService(), param);
		return result;
	}
	
	/**
	 * 
	 * @author wallimn，2018年9月22日 下午8:31:06
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param distance 限制距离
	 * @param limit 数据
	 * @return 电站列表
	 */
	@RequestMapping(value="/station",method={RequestMethod.GET,RequestMethod.POST})
	public List<StationVo> selectStation(@RequestParam(required=true) Double longitude,
			@RequestParam(required=true) Double latitude,
			@RequestParam(required=false) Double distance,
			@RequestParam(required=false, defaultValue="10") Integer limit){
		List<StationVo> result = this.chargeSerivce.selectStationsByPosition(longitude,latitude,  distance, limit);
		return result;
	}

	@RequestMapping(value="/pilebypos",method={RequestMethod.GET})
	public List<Pile> selectPilesByPos(@RequestParam(required=true) Double longitude,
			@RequestParam(required=true) Double latitude,
			@RequestParam(required=false) Double distance,
			@RequestParam(required=false, defaultValue="10") Integer limit){
		List<Pile> result = this.chargeSerivce.selectPilesByPosition(longitude,latitude,  distance, limit);
		return result;
	}
	
	/**
	 * 根据插座ID查询插座
	 * @author wallimn，2018年9月22日 下午8:33:13
	 * @param id 插座ID
	 * @return 插座信息
	 */
	@RequestMapping(value="/plugbyid",method={RequestMethod.GET,RequestMethod.POST})
	public Plug selectPlugById(@RequestParam(required=true) Long id){
		Plug result = this.chargeSerivce.selectPlugById(id);
		return result;
	}
	/**
	 * 根据电站查桩
	 * @author wallimn，2018年10月14日 上午9:10:21
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/pilesbystationid",method={RequestMethod.GET,RequestMethod.POST})
	public List<Pile> selectPilesByStationId(Long id){
		return this.chargeSerivce.selectPilesByStationId(id);
	}
	@RequestMapping(value="plugsbystationid",method={RequestMethod.GET,RequestMethod.POST})
	public List<Plug> selectPlugsByStationId(Long id){
		return this.chargeSerivce.selectPlugsByStationId(id);
	}
	/**
	 * 提供pileId，查询其对应的plug
	 * @author wallimn，2018年9月24日 下午4:44:00
	 * @param id 充电桩ID
	 * @return 插座列表
	 */
	@RequestMapping(value="/plugsbyid",method={RequestMethod.GET,RequestMethod.POST})
	public List<PlugVo> selectPlugsById(@RequestParam(required=true) Long id ,@RequestParam(required = true) String openid){
		//根据openid判断用户是否是特殊用户
		User user = userService.selectUserByOpenid(openid);
		UserSpecial userSpecial = userSpecialService.selectByMobile(user.getMobile());
		List<PlugVo> result=null;
		if(userSpecial!=null){//特殊用户
			//特殊用户插头信息
			result=chargeSerivce.selectPlugsByIdSpec(id,userSpecial.getCalcTypeId());

		}else{//普通用户
			//普通用户插头信息
			result = chargeSerivce.selectPlugsById(id);
		}

		return result;
	}
	/**
	 * 根据插座barcode查询插座
	 * @author wallimn，2018年9月22日 下午8:33:13
	 * @param barcode 插座barcode
	 * @return 插座信息
	 */
	@RequestMapping(value="/plugbybc",method={RequestMethod.GET,RequestMethod.POST})
	public Plug selectPlugById(@RequestParam(required=true) String barcode){
		Plug result = this.chargeSerivce.selectPlugByBarcode(barcode);
		return result;
	}
	/**
	 * 提供pileId，查询其对应的plug
	 * @author wallimn，2018年9月24日 下午4:44:00
	 * @param barcode 充电桩barcode
	 * @return 插座列表
	 */
	@RequestMapping(value="/plugsbybc",method={RequestMethod.GET,RequestMethod.POST})
	public List<Plug> selectPlugsById(@RequestParam(required=true) String barcode){
		List<Plug> result = this.chargeSerivce.selectPlugsByBarcode(barcode);
		return result;
	}
	/**
	 * 根据sn查询插座信息
	 * @author wallimn，2018年9月22日 下午8:39:57
	 * @param sn 插座的SN
	 * @return 插座信息
	 */
	@RequestMapping(value="/plugbysn",method={RequestMethod.GET,RequestMethod.POST})
	public Plug selectPlugBySn(@RequestParam(required=true) Long sn){
		Plug result = this.chargeSerivce.selectPlugBySn(sn);
		return result;
	}

	/**
	 * 根据手机号确定是否是特殊用户
	 * @author chenjy，2018年12月13日 下午17:40:00
	 * @param mobile 手机号
	 * @return "0"-否，1-“是”
	 */
	@RequestMapping(value="/isspecialuser",method={RequestMethod.GET,RequestMethod.POST})
	public String isSpecialUser(@RequestParam(required=true) String mobile){
		String result = "0";
		UserSpecial userSpecial = this.userSpecialService.selectByMobile(mobile);
		if (userSpecial != null) {
			result = "1";
		}
		return result;
	}
	/**
	 * 这是一个测试方法
	 * @author wallimn，2018年10月5日 下午2:22:58
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/testform",method={RequestMethod.POST})
	public String testForm(@RequestBody @Valid EndChargeForm formModel){
		try {
			String json = objectMapper.writeValueAsString(formModel);
			log.debug(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "OK";
	}
	/**
	 * 结束充电
	 * @author wallimn，2018年10月5日 下午4:34:57
	 * @param openid
	 * @return
	 */
	@RequestMapping(value="endcharge", method=RequestMethod.POST)
	public AjaxResult endCharge(@RequestBody @Valid EndChargeForm formModel){
		AjaxResult result = new AjaxResult();
		User user = this.userService.selectUserByOpenid(formModel.getOpenid());
		if(user==null){
			result.addError("用户不存在");
			log.warn("用户不存在");
			return result;
		}
		UserCost cost =null;
		if(formModel.getId()==null)cost= this.userCostService.selectLastUserCostByUserId(user.getUserId());
		else cost = this.userCostService.selectEntityById(formModel.getId());
		
		if(cost==null){
			log.warn("用户消费不存在");
			result.addError("用户消费不存在");
			return result;
		}
		if(!cost.getUserId().equals(user.getUserId())){
			log.warn("非本人的充电消费");
			result.addError("非本人的充电消费");
			return result;
			
		}
		log.debug("消费状态：{}，消费ID：{}",cost.getChargeState(),cost.getCostId());
		if(cost.getChargeState().equals(UserCostStateConst.Begin.getCode())){
			Plug plug = this.chargeSerivce.selectPlugById(cost.getPlugId());
			Pile pile = this.chargeSerivce.selectPileById(plug.getPileId());
			Map<String,Object> ctrlParam = new HashMap<String,Object>();
			ctrlParam.put("chargeHour", null);
			ctrlParam.put("controlType", 0);
			ctrlParam.put("deviceid",pile.getPileSerial());
			ctrlParam.put("isLowPower", pile.getIsHighPower());
			ctrlParam.put("portNo", plug.getSn());
			try {
				String json = objectMapper.writeValueAsString(ctrlParam);
				log.debug("发送参数：{}",json);
				String response = HttpUtil.post(this.globalConfig.getChargeService(), json);
				log.debug("通信服务器返回：{}",response);
				if(!"OK".equals(response)){
					result.addError(response);
				}
				else{
					//result.setData(cost);
				}
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}		
		}
		else{
			log.error("用户消费状态应为20，实际为：{}",cost.getChargeState());
			result.addError("用户消费状态应为20，实际为："+cost.getChargeState());
		}


		return result;
	}
	
	
	/**
	 * 启动充电。需要参数:time，以分为单位的时间；id，插座ID；openid，用户的openid
	 * @author wallimn，2018年9月22日 下午10:21:19
	 * @return 处理结果
	 */
	/**
			@RequestParam(required=true) Long time,
			@RequestParam(required=true) Long id,
			@RequestParam(required=true) String openid,
			@RequestParam(required=false, defaultValue="0") String isLowerPower
	 */
	@RequestMapping(value="/startcharge",method={RequestMethod.POST})
	public AjaxResult startCharge(@RequestBody @Valid StartChargeForm formModel){
		AjaxResult ar = new AjaxResult();
//		String openid = ConvertUtil.toString( param.get("openid"));
//		Long plugId = ConvertUtil.toLong(param.get("id"));
//		Long time = ConvertUtil.toLong(param.get("time"));
//		if(openid==null || plugId==null || time==null){
//			ar.addError("openid,id,time不能为空");
//			return ar;
//		}
//		log.debug("time={},plugId={},openid={}",time,plugId,openid);
		Long time = formModel.getTime();
		Long chargeTime = time;
		if(time==0){
			chargeTime = 255L;//表示充满自停
		}
		Plug plug = this.chargeSerivce.selectPlugById(formModel.getId());
		User user = this.userService.selectUserByOpenid(formModel.getOpenid());

		/**
		 * 加入余额判断
		 * 一、特殊用户且余额不足6毛，非特殊用户且余额不足1元的场合，提示余额不足
		 * 20181212 by chen
		 */
		UserSpecial userSpecial = this.userSpecialService.selectByMobile(user.getMobile());
		if ((userSpecial == null && user.getBalance() < 100) || (userSpecial != null && user.getBalance() < 60)) {
			ar.addError("可用余额不足，请先充值!");
			return ar;
		}
		// ************end************

		if(plug!=null && !PlugStateConst.Off.getCode().equals(plug.getStatus()) && user!=null){
			Pile pile = this.chargeSerivce.selectPileById(plug.getPileId());
//			Map<String,String> params = new HashMap<String,String>();
//			params.put("cmd", CmdConst.InformComm.getCode());
//			params.put("pileId", String.valueOf(plug.getPileId()));
//			params.put("plugId", String.valueOf(plug.getPlugId()));
//			params.put("chargeTime",String.valueOf(time));
//			params.put("isLowPower",isLowerPower);
			
			UserCost cost = new UserCost();
			cost.setUserId(user.getUserId());
			cost.setPayMoney(0L);
			cost.setPlugId(plug.getPlugId());
			cost.setPileId(plug.getPileId());
			cost.setDemandMinutes(time);
			cost.setChargeBeginTime(new Date());//这个时间，在充电实际开始的时候，修改一下。
			cost.setChargeState(UserCostStateConst.Prepare.getCode());//准备开始充电
			this.userCostService.insertEntity(cost);
			log.debug("插入充电消费信息：{}",cost.getCostId());
			
			//启动定时任务，控制消费状态。
			this.userCostTask.put(cost);
			
			
			Map<String,Object> ctrlParam = new HashMap<String,Object>();
			//吸合命令
			ctrlParam.put("chargeHour", chargeTime/60);
			ctrlParam.put("controlType", 1);
			ctrlParam.put("deviceid",pile.getPileSerial());
			ctrlParam.put("isLowPower", pile.getIsHighPower());
			ctrlParam.put("portNo", plug.getSn());
			try {
				String json = objectMapper.writeValueAsString(ctrlParam);
				log.debug("发送参数：{}",json);
				String result = HttpUtil.post(this.globalConfig.getChargeService(), json);
				log.debug("通信服务器返回：{}",result);
				this.sendCmdTask.add(plug.getPlugId(), json);
				if(!"OK".equals(result)){
					ar.addError(result);
					cost.setChargeState(UserCostStateConst.Overtime.getCode());
					this.userCostService.updateEntity(cost);
					UserLog logEntity = new UserLog();
					logEntity.setDataId(cost.getCostId().toString());
					logEntity.setLogType("错误");
					logEntity.setInformation(result);
					logEntity.setLogTime(new Date());
					logEntity.setOpenid(formModel.getOpenid());
					this.userLogService.insertEntity(logEntity);
				}
				else{
					ar.setData(cost);
				}
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
			
			
			
		}
		else{
			ar.addError("插座不存在或者处于不可充电状态");
		}
		return ar;
	}
	
	/**
	 * 检查充电插座状态
	 * @author wallimn，2018年9月24日 下午4:50:16
	 * @param id 插座的ID
	 * @param isLowerPower 是否低功率，1低功率，0非低功率
	 * @return 操作结果
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/checkcharge",method={RequestMethod.GET,RequestMethod.POST})
	public AjaxResult checkCharge(@RequestParam(required=true) Long id,
			@RequestParam(required=false, defaultValue="0") String isLowerPower){
		AjaxResult ar = new AjaxResult();
		Plug plug = this.chargeSerivce.selectPlugById(id);
		Map<String,String> params = new HashMap<String,String>();
		params.put("cmd", CmdConst.InformReport.getCode());
		params.put("pileId", String.valueOf(plug.getPileId()));
		params.put("plugId", String.valueOf(plug.getPlugId()));
		
		String result = HttpUtil.post(this.globalConfig.getChargeService(), params);
		//消息异步返回
		Map<String, Object> data = null;
		try {
			data = objectMapper.readValue(result,Map.class);
			String code = String.valueOf(data.get("code"));
			if("success".equals(code) || "0".equals(code)){
				log.debug("命令发送成功！");
			}
			else{
				ar.addError(String.valueOf(data.get("msg")));
			}
		} catch (Exception e) {
			ar.addError(String.valueOf(e.getMessage()));
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		return ar;
	}
	/*
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/checkcharge",method={RequestMethod.GET,RequestMethod.POST})
	public ActionResult checkCharge(@RequestParam(required=true) Long id,
			@RequestParam(required=false, defaultValue="0") String isLowerPower){
		ActionResult ar = new ActionResult();
		Plug plug = this.chargeSerivce.selectPlugById(id);
		if(plug!=null && !"0".equals(plug.getStatus())){
			Map<String,String> params = new HashMap<String,String>();
			params.put("cmd", CmdConst.InformReport.getCode());
			params.put("pileId", String.valueOf(plug.getPileId()));
			params.put("plugId", String.valueOf(plug.getPlugId()));
			
			String result = HttpUtil.post(this.globalConfig.getChargeService(), params);
			try {
				Map<String,Object> data = this.objectMapper.readValue(result,Map.class);
				if("success".equals(data.get("code"))){
					ar.setCode("success");
					data = (Map<String, Object>) data.get("data");
					Long minute = Long.parseLong(String.valueOf(data.get("minute")));
					boolean isLowPower = "1".equals(String.valueOf(data.get("isLowPower")));
					//data.put("money", this.chargeSerivce.calcMoney(minute, isLowPower));
					data.put("power", (Map<String, Object>) data.get("power"));
					data.put("state", (Map<String, Object>) data.get("state"));
					ar.setData(data);
				}
				else{
					ar.setCode("fail");
					ar.setMsg(String.valueOf(data.get("msg")));
				}
			} catch (Exception e) {
				ar.setCode("fail");
				ar.setMsg(e.getMessage());
				e.printStackTrace();
			}
		}
		else{
			ar.setCode("fail");
			ar.setMsg("插座不存在或者处于不可充电状态");
		}
		return ar;
	}
	*/
	
	/**
	 * 查询某插座的费率
	 * @author wallimn，2018年9月23日 下午8:17:27
	 * @param plugId 插入ID
	 * @return 费率列表
	 */
	@RequestMapping(value="/chargetypebyplugid", method=RequestMethod.GET)
	public List<ChargeType> getChargeTypeByPlugId(@RequestParam(required=true) Long plugId){
		return this.chargeSerivce.selectChargeTypeListByPlugId(plugId);
	}
	
	/**
	 * 查询费率表
	 * @author wallimn，2018年9月23日 下午8:17:44
	 * @return 费率列表
	 */
	@RequestMapping(value="/chargetype", method=RequestMethod.GET)
	public List<ChargeType> getChargeType(){
		return this.chargeSerivce.selectChargeTypeList();
	}
	
	@RequestMapping(value="checkcode",method=RequestMethod.GET)
	public AjaxResult getCheckCode(@RequestParam(required=true) String openid,
			@RequestParam(required=true) String mobile){
		AjaxResult ar = new AjaxResult();
		String code = this.smsService.getCheckCode(6);
		SmsRecord sms = this.smsService.getSmsRecord(openid);
		long timeSpan = 60000;
		Date now = new Date();
		if(sms==null || now.getTime()-sms.getSendTime().getTime()>timeSpan){
			if(this.smsService.send(mobile, code)){
				if(sms==null){
					sms = new SmsRecord();
				}
				sms.setOpenid(openid);
				sms.setCode(code);
				sms.setMobile(mobile);
				sms.setSendTime(now);
				this.smsService.putSmsRecord(sms);
				ar.success(code);
			}
			else{
				ar.addError("发送短信出错，请联系管理员");
			}
		}
		else{
			ar.addError("发送时间间隔不得小于60秒");
		}
		return ar;
	}
	
	/**
	 * 返回所有用户消费状态定义
	 * @author wallimn，2018年10月6日 下午2:01:31
	 * @return
	 */
	@RequestMapping(value="coststates", method=RequestMethod.GET)
	public List<Map<String,Object>> getUserCostStateConstList(){
		List<Map<String,Object>> result = new LinkedList<Map<String,Object>>();
		for( UserCostStateConst v : UserCostStateConst.values()){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("name", v.toString());
			map.put("code", v.getCode());
			map.put("message",v.getMessage());
			result.add(map);
		}
		return result;
	}
	
}

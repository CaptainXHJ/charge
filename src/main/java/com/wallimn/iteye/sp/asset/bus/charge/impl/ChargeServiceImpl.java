package com.wallimn.iteye.sp.asset.bus.charge.impl;

import java.util.Date;
import java.util.List;

import com.wallimn.iteye.sp.asset.bus.charge.model.vo.PlugVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.wallimn.iteye.sp.asset.bus.charge.dao.ChargeOtherDao;
import com.wallimn.iteye.sp.asset.bus.charge.dao.UserCostDao;
import com.wallimn.iteye.sp.asset.bus.charge.dao.UserDao;
import com.wallimn.iteye.sp.asset.bus.charge.dao.UserSaveDao;
import com.wallimn.iteye.sp.asset.bus.charge.model.CalcType;
import com.wallimn.iteye.sp.asset.bus.charge.model.ChargeType;
import com.wallimn.iteye.sp.asset.bus.charge.model.Pile;
import com.wallimn.iteye.sp.asset.bus.charge.model.Plug;
import com.wallimn.iteye.sp.asset.bus.charge.model.Station;
import com.wallimn.iteye.sp.asset.bus.charge.model.UnifiedOrder;
import com.wallimn.iteye.sp.asset.bus.charge.model.User;
import com.wallimn.iteye.sp.asset.bus.charge.model.UserCost;
import com.wallimn.iteye.sp.asset.bus.charge.model.UserLog;
import com.wallimn.iteye.sp.asset.bus.charge.model.UserSave;
import com.wallimn.iteye.sp.asset.bus.charge.model.vo.StationVo;
import com.wallimn.iteye.sp.asset.bus.charge.service.ChargeService;
import com.wallimn.iteye.sp.asset.bus.charge.service.UnifiedOrderService;
import com.wallimn.iteye.sp.asset.bus.charge.service.UserLogService;
import com.wallimn.iteye.sp.asset.bus.charge.util.UserCostStateConst;

/**
 * 充电服务器，综合性的，涉及多个表操作的使用这个类，这个集成、包含了其它几个接口
 * 
 * @author Lenovo
 *
 */
public class ChargeServiceImpl implements ChargeService {

	private static Logger log = LoggerFactory.getLogger(ChargeServiceImpl.class);

	@Autowired
	ChargeOtherDao chargeOtherDao;
	@Autowired
	UserDao userDao;
	@Autowired
	UserCostDao userCostDao;
	@Autowired
	UserSaveDao userSaveDao;

	@Autowired
	UserLogService userLogService;
	
	@Autowired
	UnifiedOrderService unifiedOrderService;

	@Override
	public Pile selectPileById(Long pileId) {
		return this.chargeOtherDao.selectPileById(pileId);
	}

	@Override
	public Plug selectPlugById(Long plugId) {
		return this.chargeOtherDao.selectPlugById(plugId);
	}

	@Override
	public Plug selectPlugBySn(Long sn) {
		return this.chargeOtherDao.selectPlugBySn(sn);
	}

	@Override
	public Station selectStationById(Long stationId) {
		return this.chargeOtherDao.selectStationById(stationId);
	}

	@Override
	public List<StationVo> selectStationsByPosition(Double lon, Double lat, Double distance, Integer limit) {
		return this.chargeOtherDao.selectStationsByPosition(lon, lat, distance, limit);
	}
	@Override
	public List<Pile> selectPilesByPosition(Double lon, Double lat, Double distance, Integer limit) {
		return this.chargeOtherDao.selectPilesByPosition(lon, lat, distance, limit);
	}

//	@Override
//	public Long calcMoney(Long minute, boolean isLowPower) {
//		// 快充，每小时100分钱，慢充每小时50分钱
//		return Math.round(minute / 60.0 * (isLowPower == true ? 100 : 50));
//	}

	@Override
	public String isCostCanPay(Long costId, Long money) {
		UserCost cost = this.userCostDao.selectEntityById(costId);
		return this.isCostCanPay(cost, money);
	}

	@Override
	public String isCostCanPay(UserCost cost, Long money) {
		if (cost == null || money == null) {
			log.error("定单不存在或金额为空");
			return "定单不存在或金额为空";
		} else if (UserCostStateConst.Paied.getCode().equals(cost.getChargeState())) {
			log.error("定单已支付");
			return "定单已支付";
		} else if (!money.equals(cost.getCostMoney())) {
			log.error("支付金额不正确");
			return "支付金额不正确";
		} else {
			return null;
		}
	}

	@Override
	public String isCostCanPay(User user, UserCost cost) {
		if (cost == null && user == null) {
			log.error("用户或消费不存在");
			return "定单或消费不存在";
		} else if (UserCostStateConst.Paied.getCode().equals(cost.getChargeState())) {
			log.error("定单已支付");
			return "定单已支付";
		} else if (user.getBalance() < cost.getCostMoney()) {
			log.error("账户余额不足");
			return "账户余额不足";
		} else if (!user.getUserId().equals(cost.getUserId())) {
			log.error("非本人消费");
			return "非本人消费";
		} else {
			return null;
		}
	}

	/**
	 * 特殊用户费率查询
	 * @param id
	 * @param calc_type_id
	 * @return
	 */
	@Override
	public List<PlugVo> selectPlugsByIdSpec(Long id, String calc_type_id) {
		return chargeOtherDao.selectPlugsByIdSpec(id,calc_type_id);
	}

	@Override
	@Transactional
	public boolean increaseUserMoney(String openid, Long money, String billNo) {
		User user = this.userDao.selectUserByOpenid(openid);
		if (user == null || money == null) {
			log.error("用户不存在：{}", openid);
		} else {
			UserSave save = new UserSave();
			save.setSaveMoney(money);
			save.setUserId(user.getUserId());
			save.setSaveTime(new Date());
			save.setAwardMoney(0L);
			save.setBillNo(billNo);
			UnifiedOrder order = this.unifiedOrderService.selectEntityById(billNo);
			if(order!=null){
				save.setSoftwareType(order.getSystemType());
				save.setSystemType(order.getSystemType());
				save.setSaveType(order.getPayType());
			}
			this.userSaveDao.insertEntity(save);

			user.setBalance(user.getBalance() + money);

			UserLog userLog = new UserLog();
			userLog.setOpenid(openid);
			userLog.setLogTime(new Date());
			userLog.setInformation(money.toString());
			userLog.setLogType("充值");
			this.userLogService.insertEntity(userLog);
			this.userDao.updateUserBalance(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean payUserCost(String openid, Long costId, Long money, String billNo) {
		User user = this.userDao.selectUserByOpenid(openid);
		UserCost cost = this.userCostDao.selectEntityById(costId);

		return this.payUserCost(user, cost, money, billNo);

	}

	@Override
	public boolean decreaseUserMoney(String openid, Long costId) {
		User user = this.userDao.selectUserByOpenid(openid);
		UserCost cost = this.userCostDao.selectEntityById(costId);
		return this.decreaseUserMoney(user, cost);
		//String result = this.isCostCanPay(user, cost);
	}

	@Override
	public Station selectStationByPileId(Long pileId) {
		return this.chargeOtherDao.selectStationByPileId(pileId);
	}

	@Override
	public Station selectStationByPlugId(Long plugId) {
		return this.chargeOtherDao.selectStationByPlugId(plugId);
	}

	@Override
	public List<ChargeType> selectChargeTypeList() {
		return chargeOtherDao.selectChargeTypeList();
	}

	@Override
	public List<ChargeType> selectChargeTypeListByPlugId(Long plugId) {
		return chargeOtherDao.selectChargeTypeListByPlugId(plugId);
	}

	@Override
	public ChargeType selectChargeType(Long power) {
		return this.chargeOtherDao.selectChargeType(power);
	}

	@Override
	public Plug selectPlugByBarcode(String barcode) {
		return this.chargeOtherDao.selectPlugByBarcode(barcode);
	}

	@Override
	public List<Plug> selectPlugsByBarcode(String barcode) {
		return this.chargeOtherDao.selectPlugsByBarcode(barcode);
	}

	@Override
	public Pile selectPileByBarcode(String barcode) {
		return this.chargeOtherDao.selectPileByBarcode(barcode);
	}

	@Override
	public List<PlugVo> selectPlugsById(Long pileId) {

		return this.chargeOtherDao.selectPlugsById(pileId);
	}

	@Override
	public ChargeType selectChargeTypeByPlugId(Long plugId, Long power) {
		return this.chargeOtherDao.selectChargeTypeByPlugId(plugId, power);
	}


	@Override
	@Transactional
	public boolean payUserCost(User user, UserCost cost, Long costMoney, String billNo) {
		// String result = this.isCostCanPay(cost, costMoney);
		if (!user.getUserId().equals(cost.getUserId())) {
			log.warn("非本人消费");
		}

		cost.setPayMoney(costMoney);
		cost.setBillNo(billNo);
		cost.setChargeState(UserCostStateConst.Paied.getCode());
		this.userCostDao.updateEntity(cost);

		UserLog userLog = new UserLog();
		userLog.setOpenid(user.getOpenid());
		userLog.setLogTime(new Date());
		userLog.setLogType("支付");
		userLog.setInformation(cost.getCostMoney().toString());
		userLog.setDataId(cost.getCostId().toString());
		this.userLogService.insertEntity(userLog);

		return true;

		// 出错的处理
		// log.error("支付存在问题，钱存入本人账户");
		// user.setBalance(user.getBalance() + money);
		// this.userDao.updateUserBalance(user);
		// UserLog userLog = new UserLog();
		// userLog.setOpenid(openid);
		// userLog.setLogTime(new Date());
		// userLog.setLogType("充值");
		// userLog.setInformation(cost.getCostMoney().toString());
		// userLog.setRemark("支付存在问题，钱存入本人账户，原因：" + result);
		// this.userLogService.insertEntity(userLog);
		// return true;

	}

	@Override
	@Transactional
	public boolean decreaseUserMoney(User user, UserCost cost) {
		cost.setPayMoney(cost.getCostMoney());
		cost.setChargeState(UserCostStateConst.Paied.getCode());
		this.userCostDao.updateEntity(cost);

		user.setBalance(user.getBalance() - cost.getCostMoney());
		UserLog userLog = new UserLog();
		userLog.setOpenid(user.getOpenid());
		userLog.setLogTime(new Date());
		userLog.setLogType("支出");
		userLog.setInformation(cost.getCostMoney().toString());
		userLog.setDataId(cost.getCostId().toString());
		this.userLogService.insertEntity(userLog);
		this.userDao.updateUserBalance(user);
		return true;
	}

	@Override
	public List<Pile> selectPilesByStationId(Long stationId) {
		return this.chargeOtherDao.selectPilesByStationId(stationId);
	}

	@Override
	public List<Plug> selectPlugsByStationId(Long stationId) {
		return this.chargeOtherDao.selectPlugsByStationId(stationId);
	}

	@Override
	public CalcType selectCalcTypeById(String id) {
		return this.chargeOtherDao.selectCalcTypeById(id);
	}

}

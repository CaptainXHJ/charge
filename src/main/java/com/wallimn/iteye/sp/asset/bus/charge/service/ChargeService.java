package com.wallimn.iteye.sp.asset.bus.charge.service;

import java.util.List;

import com.wallimn.iteye.sp.asset.bus.charge.model.CalcType;
import com.wallimn.iteye.sp.asset.bus.charge.model.ChargeType;
import com.wallimn.iteye.sp.asset.bus.charge.model.Pile;
import com.wallimn.iteye.sp.asset.bus.charge.model.Plug;
import com.wallimn.iteye.sp.asset.bus.charge.model.Station;
import com.wallimn.iteye.sp.asset.bus.charge.model.User;
import com.wallimn.iteye.sp.asset.bus.charge.model.UserCost;
import com.wallimn.iteye.sp.asset.bus.charge.model.vo.PlugVo;
import com.wallimn.iteye.sp.asset.bus.charge.model.vo.StationVo;

public interface ChargeService {
	Station selectStationById(Long stationId);
	Station selectStationByPileId(Long pileId);
	Station selectStationByPlugId(Long plugId);
	List<StationVo> selectStationsByPosition(Double lon,Double lat,Double distance,Integer limit);
	List<Pile> selectPilesByPosition(Double lon,Double lat,Double distance,Integer limit);
	Pile selectPileById(Long pileId);
	Plug selectPlugById(Long plugId);
	List<PlugVo> selectPlugsById(Long pileId);
	Plug selectPlugBySn(Long sn);
	CalcType selectCalcTypeById(String id);
	
	Plug selectPlugByBarcode(String barcode);
	List<Plug> selectPlugsByBarcode(String barcode);
	Pile selectPileByBarcode(String barcode);
	
	List<Pile> selectPilesByStationId(Long stationId);
	List<Plug> selectPlugsByStationId(Long stationId);
	
	List<ChargeType> selectChargeTypeList();
	List<ChargeType> selectChargeTypeListByPlugId(Long plugId);
	ChargeType selectChargeTypeByPlugId(Long plugId,Long power);
	ChargeType selectChargeType(Long power);
	
	
	/**
	 * 用户充值，修改金额，增加充值记录
	 * @author wallimn，2018年9月22日 下午11:16:05
	 * @param openid
	 * @param money
	 * @return
	 */
	boolean increaseUserMoney(String openid,Long money,String billNo);
	/**
	 * 用户支付，修改金额，修改消费的状态
	 * @author wallimn，2018年9月22日 下午11:16:24
	 * @param openid
	 * @param costId
	 * @return
	 */
	boolean payUserCost(String openid,Long costId,Long costMoney,String billNo);
	boolean payUserCost(User user,UserCost cost,Long costMoney,String billNo);
	/**
	 * 用户账户支出
	 * @author wallimn，2018年9月28日 下午2:12:10
	 * @param openid
	 * @param costId
	 * @return
	 */
	boolean decreaseUserMoney(String openid,Long costId);
	boolean decreaseUserMoney(User user,UserCost cost);
	/**
	 * 消费是否可支付
	 * @author wallimn，2018年9月28日 下午1:45:16
	 * @param costId
	 * @return
	 */
	String isCostCanPay(UserCost cost,Long money);
	/**
	 * 调用对象版的函数
	 * @author wallimn，2018年9月29日 下午7:16:48
	 * @param costId
	 * @param money
	 * @return
	 */
	String isCostCanPay(Long costId,Long money);
	String isCostCanPay(User user,UserCost cost);

	/**
	 * 特殊用户费率查询
	 * @param id
	 * @param calc_type_id
	 * @return
	 */
	List<PlugVo> selectPlugsByIdSpec(Long id, String calc_type_id);
}

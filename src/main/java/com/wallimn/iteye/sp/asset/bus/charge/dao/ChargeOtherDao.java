package com.wallimn.iteye.sp.asset.bus.charge.dao;

import java.util.List;

import com.wallimn.iteye.sp.asset.bus.charge.model.vo.PlugVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wallimn.iteye.sp.asset.bus.charge.model.CalcType;
import com.wallimn.iteye.sp.asset.bus.charge.model.ChargeType;
import com.wallimn.iteye.sp.asset.bus.charge.model.Pile;
import com.wallimn.iteye.sp.asset.bus.charge.model.Plug;
import com.wallimn.iteye.sp.asset.bus.charge.model.Station;
import com.wallimn.iteye.sp.asset.bus.charge.model.vo.StationVo;

/**
 * 除用户、消费、充值之外的信息处理，以查询为主
 * @author wallimn，2018年9月22日 下午3:58:03
 *
 */
@Mapper
public interface ChargeOtherDao{
	Station selectStationById(@Param("id") Long stationId);
	Station selectStationByPileId(@Param("id") Long pileId);
	Station selectStationByPlugId(@Param("id") Long plugId);
	
	CalcType selectCalcTypeById(@Param("id") String id);
	
	List<Pile> selectPilesByStationId(@Param("id") Long stationId);
	List<Plug> selectPlugsByStationId(@Param("id") Long stationId);
	
	
	Plug selectPlugByBarcode(@Param("barcode") String barcode);
	List<Plug> selectPlugsByBarcode(@Param("barcode")String barcode);
	Pile selectPileByBarcode(@Param("barcode")String barcode);

	List<StationVo> selectStationsByPosition(@Param("lon") Double lon, @Param("lat") Double lat, @Param("distance") Double distance, @Param("limit") Integer limit);
	List<Pile> selectPilesByPosition(@Param("lon") Double lon, @Param("lat") Double lat, @Param("distance") Double distance, @Param("limit") Integer limit);
	Pile selectPileById(@Param("id") Long pileId);
	Plug selectPlugById(@Param("id") Long plugId);
	List<PlugVo> selectPlugsById(@Param("id") Long pileId);
	Plug selectPlugBySn(@Param("sn") Long sn);
	List<ChargeType> selectChargeTypeList();
	List<ChargeType> selectChargeTypeListByPlugId(@Param("id") Long plugId);
	ChargeType selectChargeTypeByPlugId(@Param("id") Long plugId,@Param("power") Long power);
	ChargeType selectChargeType(@Param("power")Long power);

	/**
	 * 查询特殊用户费率
	 * @param id
	 * @param calc_type_id
	 * @return
	 */
	List<PlugVo> selectPlugsByIdSpec(@Param("id") Long id,@Param("calcTypeId") String calc_type_id);
}

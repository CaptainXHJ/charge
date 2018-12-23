package com.wallimn.iteye.sp.asset.bus.math.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wallimn.iteye.sp.asset.bus.math.model.Score;
import com.wallimn.iteye.sp.asset.bus.math.model.Shushi;

@Mapper
public interface MathDao {
	int insertShushi(Shushi entity);
	
	int insertScore(Score entity);
	
	List<Shushi> selectShushi(@Param("testId")String testId);
	List<Score> selectScore(Score entity);
}

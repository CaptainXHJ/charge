package com.wallimn.iteye.sp.asset.bus.math.service;

import java.util.List;

import com.wallimn.iteye.sp.asset.bus.math.model.Score;
import com.wallimn.iteye.sp.asset.bus.math.model.Shushi;

public interface MathService {
	int insertShushi(Shushi entity);
	int insertScore(Score entity);
	
	List<Shushi> getShushiTest(String testId,int count);
	int insertShushiTest(List<Shushi> list);
	List<Shushi> selectShushi(String testId);
	List<Score> selectScore(Score entity);

}

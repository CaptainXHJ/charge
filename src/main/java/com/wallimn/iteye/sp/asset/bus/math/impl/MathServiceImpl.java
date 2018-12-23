package com.wallimn.iteye.sp.asset.bus.math.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wallimn.iteye.sp.asset.bus.math.dao.MathDao;
import com.wallimn.iteye.sp.asset.bus.math.model.Score;
import com.wallimn.iteye.sp.asset.bus.math.model.Shushi;
import com.wallimn.iteye.sp.asset.bus.math.service.MathService;

public class MathServiceImpl implements MathService {

	private static final Logger log = LoggerFactory.getLogger(MathServiceImpl.class);
	@Autowired
	private MathDao mathDao;
	
	@Override
	public int insertShushi(Shushi entity) {
		return this.mathDao.insertShushi(entity);
	}

	private static final String [] operator = new String[]{"+","-","×","÷"};
	@Override
	public List<Shushi> getShushiTest(String testId, int count) {
		Shushi ss;
		Random random = new Random();
		List<Shushi> list = new LinkedList<Shushi>();
		for(int i=0; i<count; i++){
			ss = new Shushi();
			ss.setNum1(random.nextInt(90)+10);
			ss.setNum2(random.nextInt(90)+10);
			//只出除法和乘法
			ss.setOperator(operator[2+random.nextInt(2)]);
			if("÷".equals(ss.getOperator())){
				ss.setNum1(ss.getNum1()*ss.getNum2());
			}
			ss.setAnswer(this.calc(ss.getNum1(), ss.getNum2(), ss.getOperator()));
			ss.setShowOrder(i+1);
			ss.setTestId(testId);
			//this.mathDao.insertShushi(ss);
			list.add(ss);
		}
		return list;
	}
	
	private int calc(int num1,int num2,String o){
		if("+".equals(o)){
			return num1+num2;
		}
		else if("-".equals(o)){
			return num1-num2;
		}
		else if("×".equals(o)){
			return num1*num2;
		}
		else if("÷".equals(o)){
			return num1/num2;
		}
		else{
			log.error("错误的操作符：{}",o);
			return -1;
		}
	}

	@Override
	public List<Shushi> selectShushi(String testId) {
		return this.mathDao.selectShushi(testId);
	}

	@Override
	public int insertShushiTest(List<Shushi> list) {
		
		for(Shushi entity : list){
			this.mathDao.insertShushi(entity);
		}
		return list.size();
	}

	@Override
	public int insertScore(Score entity) {
		return this.mathDao.insertScore(entity);
	}


	@Override
	public List<Score> selectScore(Score entity) {
		return this.mathDao.selectScore(entity);
	}



}

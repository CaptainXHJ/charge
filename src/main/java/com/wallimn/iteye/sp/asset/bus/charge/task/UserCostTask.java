package com.wallimn.iteye.sp.asset.bus.charge.task;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wallimn.iteye.sp.asset.bus.charge.model.UserCost;
import com.wallimn.iteye.sp.asset.bus.charge.service.UserCostService;
import com.wallimn.iteye.sp.asset.bus.charge.util.UserCostStateConst;

/**
 * 用于管理用户消费的状态，如果较长时间没有反馈，就设置为时超时状态
 * @author wallimn，2018年10月6日 下午2:13:34
 *
 */
@Component
public class UserCostTask {
	private static final Logger log = LoggerFactory.getLogger(UserCostTask.class);
	private ConcurrentMap<Long,UserCost> costMap = new ConcurrentHashMap<Long,UserCost>();
	
	@Autowired
	private UserCostService userCostService;
	
	@Value(value = "${cost.check.threshold}")
	private Long costCheckThreshold;
	//1秒、2分、3小时、4天、5月、6星期(日是1)、7年份
	@Scheduled(cron = "*/20 * * * * *")
	public void monitorUserCost(){
		//log.debug("定时器执行：{}",new Date());
		UserCost cost = null;
		Date now = new Date();
		List<Long> del = new LinkedList<Long>();
		for(Entry<Long,UserCost> e : costMap.entrySet()){
			cost = this.userCostService.selectEntityById(e.getKey());
			if(UserCostStateConst.Prepare.getCode().equals(cost.getChargeState())
					&& now.getTime()-e.getValue().getChargeBeginTime().getTime()>this.costCheckThreshold){
				cost.setChargeEndTime(now);
				cost.setChargeMinutes(0L);
				cost.setChargePower(0L);
				cost.setChargeState(UserCostStateConst.Overtime.getCode());
				this.userCostService.updateEntity(cost);
				log.debug("通信服务器反馈超时，用户消费设置为超时，costId={}",cost.getCostId());
			}
			else{
				//this.costMap.remove(e.getKey());
				//log.debug("移除消费：{}",cost.getCostId());
				del.add(e.getKey());
			}
		}
		for(Long l:del){
			this.costMap.remove(l);
		}
	}
	public void put(UserCost cost){
		this.costMap.put(cost.getCostId(), cost);
	}
	public void remove(Long costId){
		this.costMap.remove(costId);
	}
}

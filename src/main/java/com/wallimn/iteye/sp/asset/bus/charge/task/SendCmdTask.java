package com.wallimn.iteye.sp.asset.bus.charge.task;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wallimn.iteye.sp.asset.common.config.GlobalConfig;
import com.wallimn.iteye.sp.asset.common.util.HttpUtil;

/**
 * 发送命令定时器，用于多次发送吸合命令。有时用户可能没有插上插座，状态上报会是未接入。
 * @author wallimn，2018年10月14日 上午10:16:09
 *
 */
@Component
public class SendCmdTask {
	private static Logger log = LoggerFactory.getLogger(SendCmdTask.class);
	
	class Cmd{
		private int count;
		private String content;
		public Cmd(String cmd){
			this.content = cmd;
			this.count = 0;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
	/**
	 * 命令字符串，json转化而成的字符串
	 * 使用插座ID对应，做Key
	 */
	private ConcurrentMap<Long,Cmd> cmdMap = new ConcurrentHashMap<Long,Cmd> ();
	public void add(Long plugId,String content){
		Cmd cmd = new Cmd(content);
		this.cmdMap.put(plugId, cmd);
	}
	public void remove(Long plugId){
		log.debug("移除命令：{}",plugId);
		this.cmdMap.remove(plugId);
	}
	@Autowired
	private GlobalConfig globalConfig;
	
	@Value(value = "${cmd.send.threshold}")
	private int sendThreshold;
	
	@Scheduled(cron = "*/15 * * * * *")
	public void sendCmd(){
		for(Entry<Long,Cmd> e: this.cmdMap.entrySet()){
			Cmd cmd = e.getValue();
			if(cmd.getCount()>=sendThreshold){
				log.debug("到达发送阀值，移除任务：{}",e.getKey());
				this.remove(e.getKey());
				continue;
			}
			log.debug("定时器发送命令，插座ID：{}，次数：{}，命令内容：{}",e.getKey(),cmd.getCount()+1,cmd.getContent());
			String result = HttpUtil.post(this.globalConfig.getChargeService(), cmd.getContent());
			cmd.setCount(cmd.getCount()+1);
			log.debug("通信服务器返回：{}",result);
		}
	}
}

package com.wallimn.iteye.sp.asset.bus.inform.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.wallimn.iteye.sp.asset.bus.inform.model.Inform;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReply;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReplyAttach;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReplyRemark;
import com.wallimn.iteye.sp.asset.bus.inform.model.vo.InformReplyVo;
import com.wallimn.iteye.sp.asset.bus.inform.service.InformService;
import com.wallimn.iteye.sp.asset.common.controller.BaseController;

/**
 * Inform处理控制器
 * 
 * <br>
 * <br>
 * 时间：2018年7月27日 下午4:33:16，作者：wallimn
 */
@RestController
@RequestMapping("/api/inform")
public class InformController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(InformController.class);

	@Autowired
	InformService informSerivce;

	/////////////////////////// 主表区域inform--begin///////////////////////////////

	@RequestMapping(value = "/inform", method = RequestMethod.GET)
	public Inform getInformById(@RequestParam(required = true) String id) {
		return this.informSerivce.selectInformById(id);
	}

	@RequestMapping(value = "/inform", method = RequestMethod.DELETE)
	public String deleteInformById(@RequestBody Inform entity) {
		this.informSerivce.deleteInform(entity.getId());
		return this.getSuccessJson();
	}

	@RequestMapping(value = "/inform", method = RequestMethod.POST)
	public Inform insertInform(@RequestBody Inform entity) {
		this.informSerivce.insertInform(entity);
		entity = this.informSerivce.selectInformById(entity.getId());
		return entity;
	}

	@RequestMapping(value = "/inform", method = RequestMethod.PUT)
	public Inform updateInform(@RequestBody Inform entity) {
		this.informSerivce.updateInform(entity);
		entity = this.informSerivce.selectInformById(entity.getId());
		return entity;
	}

	@RequestMapping(value = "/informs", method = RequestMethod.GET)
	public PageList<Inform> getInformListByopenid(@RequestParam(required = true) String id,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "1") Integer page) {
		log.error("查询openid：{}", id);
		// 很奇怪，生成一个空对象，然后赋值，查询结果会有问题。没有返回分页的结果
		PageBounds pb = new PageBounds(page, limit, Order.formString(this.getOrderCondition("inform_date", "desc")));

		// pb.setOrders(orders);
		return this.informSerivce.selectInformByOpenid(id, pb);
	}

	/**
	 * 查询某人的封面
	 * 
	 * <br>
	 * 
	 * @param id
	 * @return <br>
	 *         时间：2018年7月26日 下午9:33:44，联系：54871876@qq.com
	 */
	@RequestMapping(value = "/coverUrl", method = RequestMethod.GET)
	public List<String> getCoverUrlList(@RequestParam(required = true) String id) {
		return this.informSerivce.selectCoverUrlByOpenid(id);
	}

	//////////////////////////// inform
	//////////////////////////// end///////////////////////////////////////

	////////////////////////// reply begin/////////////////////////////////////

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public InformReplyVo getReplyByInformIdAndOpenid(@RequestParam(required = true) String informId,
			@RequestParam(required = true) String openid) {
		log.debug("informId={},openid={}", informId, openid);
		return this.informSerivce.selectInformReplyByInformIdAndOpenid(informId, openid);
	}

	/**
	 * 这个方法不太实用，改用上面的方法
	 * 这个方法的直接使用条件不具备
	 * 
	 * <br>
	 * 
	 * @param id
	 * @return <br>
	 *         时间：2018年7月29日 下午10:07:45，联系：54871876@qq.com
	 */
	public InformReply getReplyById(@RequestParam(required = true) String id) {
		return this.informSerivce.selectInformReplyById(id);
	}

	/**
	 * 删除回复，只能删除本人回复。或者发通知的人可以删除回复
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.DELETE)
	public String deleteInformReplyById(@RequestBody InformReply entity) {
		log.debug("删除回复：{}" + entity.getId());

		this.informSerivce.deleteInformReply(entity.getId());
		return this.getSuccessJson();
	}

	/**
	 * 提交回复
	 * 
	 * <br>
	 * 
	 * @param entity
	 * @return <br>
	 *         时间：2018年7月26日 下午9:19:26，联系：54871876@qq.com
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public InformReply insertInformReply(@RequestBody InformReply entity) {
		this.informSerivce.insertInformReply(entity);
		//插入时，不会有附件，不用重新查询
		//InformReplyVo result = this.informSerivce.selectInformReplyByInformIdAndOpenid(entity.getInformId(), entity.getOpenid());
		return entity;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.PUT)
	public InformReplyVo updateInformReply(@RequestBody InformReply entity) {
		this.informSerivce.updateInformReply(entity);
		InformReplyVo result = this.informSerivce.selectInformReplyByInformIdAndOpenid(entity.getInformId(), entity.getOpenid());
		return result;
	}

	/**
	 * 返回某通知的回复
	 * 
	 * 增加了请求者的openid参数
	 * 
	 * <br>
	 * @param id
	 * @param openid
	 * @param limit
	 * @param page
	 * @return
	 * <br>
	 * 时间：2018年9月8日 上午8:05:20，联系：54871876@qq.com
	 */
	@RequestMapping(value = "/replys", method = RequestMethod.GET)
	public PageList<InformReplyVo> getReplyListByInformId(
			@RequestParam(required = true) String id,
			@RequestParam(required = false) String openid,
			@RequestParam(defaultValue = "10") Integer limit, 
			@RequestParam(defaultValue = "1") Integer page) {
		log.error("查询getReplyListByInformId，formId={}，openid={}", id,openid);
		PageBounds pb = new PageBounds(page, limit, Order.formString(this.getOrderCondition("reply_date", "desc")));
		return this.informSerivce.selectInformReplyByInformId(id,openid,true, pb);
	}

	////////////////////////// reply end/////////////////////////////////

	/**
	 * 删除附件
	 * 
	 * <br>
	 * 
	 * @param id
	 * @return <br>
	 *         时间：2018年7月26日 下午9:16:00，联系：54871876@qq.com
	 */
	@RequestMapping(value = "/attach", method = RequestMethod.DELETE)
	public String deleteAttachById(@RequestBody InformReplyAttach entity) {
		log.debug("删除附件，ID={}",entity.getId());
		this.informSerivce.deleteInformReplyAttach(entity.getId());
		return this.getSuccessJson();
	}

	@RequestMapping(value = "/attachs", method = RequestMethod.GET)
	public PageList<InformReplyAttach> getAttachListByReplyId(@RequestParam(required = true) String id,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "1") Integer page) {
		log.error("查询getAttachListByReplyId：{}", id);
		PageBounds pb = new PageBounds(page, limit, Order.formString(this.getOrderCondition("attach_date", "desc")));
		return this.informSerivce.selectInformReplyAttachByReplyId(id, pb);
	}

	/**
	 * 查询评论列表
	 * 
	 * <br>
	 * @param id
	 * @param limit
	 * @param page
	 * @return
	 * <br>
	 * 时间：2018年8月14日 下午12:29:54，联系：54871876@qq.com
	 */
	@RequestMapping(value = "/remarks", method = RequestMethod.GET)
	public PageList<InformReplyRemark> getRemarkListByReplyId(@RequestParam(required = true) String id,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "1") Integer page) {
		PageBounds pb = new PageBounds(page, limit, Order.formString(this.getOrderCondition("remark_date", "asc")));
		return this.informSerivce.selectInformReplyRemarkByReplyId(id, pb);
	}
	/**
	 * 删除评论
	 * 
	 * <br>
	 * @param entity
	 * @return
	 * <br>
	 * 时间：2018年8月14日 上午11:17:22，联系：54871876@qq.com
	 */
	@RequestMapping(value = "/remark", method = RequestMethod.DELETE)
	public String deleteInformReplyRemarkById(@RequestBody InformReplyRemark entity) {
		this.informSerivce.deleteInformReplyRemark(entity.getId());
		return this.getSuccessJson();
	}

	@RequestMapping(value = "/remark", method = RequestMethod.POST)
	public InformReplyRemark insertReplyRemark(@RequestBody InformReplyRemark entity) {
		this.informSerivce.insertInformReplyRemark(entity);
		return entity;
	}

	@RequestMapping(value = "/remark", method = RequestMethod.PUT)
	public InformReplyRemark updateReplyRemark(@RequestBody InformReplyRemark entity) {
		this.informSerivce.updateInformReplyRemark(entity);
		return entity;
	}

	@RequestMapping(value = "/remark", method = RequestMethod.GET)
	public InformReplyRemark getReplyRemarkByInformIdAndOpenid(@RequestParam(required = true) String replyId,
			@RequestParam(required = true) String openid) {
		log.debug("replyId={},openid={}", replyId, openid);
		return this.informSerivce.selectInformReplyRemarkByReplyIdAndOpenid(replyId, openid);
	}

}

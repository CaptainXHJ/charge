package com.wallimn.iteye.sp.asset.bus.inform.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.wallimn.iteye.sp.asset.bus.inform.dao.InformDao;
import com.wallimn.iteye.sp.asset.bus.inform.model.Inform;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReply;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReplyAttach;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReplyRemark;
import com.wallimn.iteye.sp.asset.bus.inform.model.vo.InformReplyVo;
import com.wallimn.iteye.sp.asset.bus.inform.service.InformService;

public class InformServiceImpl implements InformService {

	private static final Logger log = LoggerFactory.getLogger(InformServiceImpl.class);
	
	@Autowired
	private InformDao informDao;

	@Override
	public int insertInform(Inform entity) {
		return this.informDao.insertInform(entity);
	}

	@Override
	public int insertInformReply(InformReply entity) {
		return this.informDao.insertInformReply(entity);
	}

	@Override
	public int insertInformReplyAttach(InformReplyAttach entity) {
		return this.informDao.insertInformReplyAttach(entity);
	}

	@Override
	public int updateInform(Inform entity) {
		return this.informDao.updateInform(entity);
	}

	@Override
	public int updateInformReply(InformReply entity) {
		return this.informDao.updateInformReply(entity);
	}

	@Override
	public int updateInformReplyAttach(InformReplyAttach entity) {
		return this.informDao.updateInformReplyAttach(entity);
	}

	@Override
	public int deleteInform(String id) {
		return this.informDao.deleteInform(id);
	}

	@Override
	public int deleteInformReply(String id) {
		return this.informDao.deleteInformReply(id);
	}

	@Override
	public int deleteInformReplyAttach(String id) {
		return this.informDao.deleteInformReplyAttach(id);
	}

	@Override
	public List<Inform> selectInformByOpenid(String id) {
		return this.informDao.selectInformByOpenid(id);
	}

	@Override
	public List<InformReplyVo> selectInformReplyByInformId(String id, String openid) {
		return this.selectInformReplyByInformId(id,openid,false);
	}

	@Override
	public List<InformReplyAttach> selectInformReplyAttachByReplyId(String id) {
		return this.informDao.selectInformReplyAttachByReplyId(id);
	}

	@Override
	public Inform selectInformById(String id) {
		return this.informDao.selectInformById(id);
	}

	@Override
	public InformReply selectInformReplyById(String id) {
		return this.informDao.selectInformReplyById(id);
	}

	@Override
	public InformReplyAttach selectInformReplyAttachById(String id) {
		return this.informDao.selectInformReplyAttachById(id);
	}

	@Override
	public List<String> selectCoverUrlByOpenid(String id) {
		return this.informDao.selectCoverUrlByOpenid(id);
	}

	@Override
	public PageList<Inform> selectInformByOpenid(String id, PageBounds pb) {
		PageList<Inform> result = this.informDao.selectInformByOpenid(id,pb);
		log.debug("返回类型：{}",result.getClass().getName());
		log.debug("分页详情：{}",result.getPaginator());
		return  result;
	}

	@Override
	public PageList<InformReplyVo> selectInformReplyByInformId(String id, String openid, PageBounds pb) {
		return  this.selectInformReplyByInformId(id,openid,false,pb);
	}

	@Override
	public PageList<InformReplyAttach> selectInformReplyAttachByReplyId(String id, PageBounds pb) {
		return  this.informDao.selectInformReplyAttachByReplyId(id,pb);
	}

	@Override
	public int updateInformOpenCount(String id) {
		return this.informDao.updateInformOpenCount(id);
	}

	@Override
	public int insertInformReplyRemark(InformReplyRemark entity) {
		return this.informDao.insertInformReplyRemark(entity);
	}

	@Override
	public int updateInformReplyRemark(InformReplyRemark entity) {
		return this.informDao.updateInformReplyRemark(entity);
	}

	@Override
	public List<InformReplyRemark> selectInformReplyRemarkByReplyId(String id) {
		return this.informDao.selectInformReplyRemarkByReplyId(id);
	}

	@Override
	public PageList<InformReplyRemark> selectInformReplyRemarkByReplyId(String id, PageBounds pb) {
		return this.informDao.selectInformReplyRemarkByReplyId(id, pb);
	}

	@Override
	public InformReplyRemark selectInformReplyRemarkById(String id) {
		return this.informDao.selectInformReplyRemarkById(id);
	}

	@Override
	public int deleteInformReplyRemark(String id) {
		return this.informDao.deleteInformReplyRemark(id);
	}

	@Override
	public Map<String, Object> selectInformReplyRemarkStat(String id) {
		return this.informDao.selectInformReplyRemarkStat(id);
	}

	@Override
	public InformReplyVo selectInformReplyByInformIdAndOpenid(String informId, String openid) {
		InformReplyVo result = this.informDao.selectInformReplyByInformIdAndOpenid(informId, openid);
		if(result!=null && StringUtils.isNotEmpty(result.getId())){
			result.setReplyAttachs(this.informDao.selectInformReplyAttachByReplyId(result.getId()));
			result.setReplyRemarks(this.informDao.selectInformReplyRemarkByReplyId(result.getId()));
		}
		return result;
	}
	@Override
	public InformReplyRemark selectInformReplyRemarkByReplyIdAndOpenid(String informId, String openid) {
		return this.informDao.selectInformReplyRemarkByReplyIdAndOpenid(informId, openid);
	}

	@Override
	public List<InformReplyVo> selectInformReplyByInformId(String id,String openid, boolean bLoadAll) {
		List<InformReplyVo> list = this.informDao.selectInformReplyByInformId(id,openid);
		if(bLoadAll==true){
			for(InformReplyVo r:list){
				r.setReplyAttachs(this.informDao.selectInformReplyAttachByReplyId(r.getId()));
				r.setReplyRemarks(this.informDao.selectInformReplyRemarkByReplyId(r.getId()));
			}
		}
		return list;
	}

	@Override
	public PageList<InformReplyVo> selectInformReplyByInformId(String id, String openid, boolean bLoadAll, PageBounds pb) {
		PageList<InformReplyVo> list = this.informDao.selectInformReplyByInformId(id,openid,pb);
		if(bLoadAll==true){
			for(InformReplyVo r:list){
				r.setReplyAttachs(this.informDao.selectInformReplyAttachByReplyId(r.getId()));
				r.setReplyRemarks(this.informDao.selectInformReplyRemarkByReplyId(r.getId()));
			}
		}
		return list;
	}

}

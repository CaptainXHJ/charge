package com.wallimn.iteye.sp.asset.bus.inform.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.wallimn.iteye.sp.asset.bus.inform.model.Inform;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReply;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReplyAttach;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReplyRemark;
import com.wallimn.iteye.sp.asset.bus.inform.model.vo.InformReplyVo;

@Mapper
public interface InformDao {

	int insertInform(Inform entity);
	int updateInform(Inform entity);
	List<Inform> selectInformByOpenid(@Param("id") String id);
	PageList<Inform> selectInformByOpenid(@Param("id") String id,PageBounds pb);
	Inform selectInformById(@Param("id") String id);
	int deleteInform(@Param("id")String id);
	
	int insertInformReply(InformReply entity);
	int updateInformReply(InformReply entity);
	List<InformReplyVo> selectInformReplyByInformId(@Param("id") String id,@Param("openid") String openid);
	PageList<InformReplyVo> selectInformReplyByInformId(@Param("id") String id,@Param("openid") String openid,PageBounds pb);
	InformReply selectInformReplyById(@Param("id") String id);
	int deleteInformReply(@Param("id")String id);
	InformReplyVo selectInformReplyByInformIdAndOpenid(@Param("informId") String informId,@Param("openid") String openid);
	InformReplyRemark selectInformReplyRemarkByReplyIdAndOpenid(@Param("replyId") String informId,@Param("openid") String openid);
	
	int insertInformReplyAttach(InformReplyAttach entity);	
	int updateInformReplyAttach(InformReplyAttach entity);
	List<InformReplyAttach> selectInformReplyAttachByReplyId(@Param("id") String id);
	PageList<InformReplyAttach> selectInformReplyAttachByReplyId(@Param("id") String id,PageBounds pb);
	InformReplyAttach selectInformReplyAttachById(@Param("id") String id);
	int deleteInformReplyAttach(@Param("id")String id);
	
	
	int insertInformReplyRemark(InformReplyRemark entity);	
	int updateInformReplyRemark(InformReplyRemark entity);
	List<InformReplyRemark> selectInformReplyRemarkByReplyId(@Param("id") String id);
	PageList<InformReplyRemark> selectInformReplyRemarkByReplyId(@Param("id") String id,PageBounds pb);
	InformReplyRemark selectInformReplyRemarkById(@Param("id") String id);
	int deleteInformReplyRemark(@Param("id")String id);
	Map<String,Object> selectInformReplyRemarkStat(@Param("id") String id);
	
	
	
	
	
	List<String> selectCoverUrlByOpenid(@Param("id") String id);
	
	int updateInformOpenCount(@Param("id") String id);
}

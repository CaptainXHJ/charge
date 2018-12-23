package com.wallimn.iteye.sp.asset.bus.inform.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.wallimn.iteye.sp.asset.bus.inform.model.Inform;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReply;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReplyAttach;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReplyRemark;
import com.wallimn.iteye.sp.asset.bus.inform.model.vo.InformReplyVo;

public interface InformService {

	int insertInform(Inform entity);
	int updateInform(Inform entity);
	int deleteInform(String id);
	List<Inform> selectInformByOpenid(String id);
	PageList<Inform> selectInformByOpenid(String id,PageBounds pb);
	Inform selectInformById(String id);
	
	
	int insertInformReply(InformReply entity);
	int updateInformReply(InformReply entity);
	int deleteInformReply(String id);
	List<InformReplyVo> selectInformReplyByInformId(String id, String openid);
	List<InformReplyVo> selectInformReplyByInformId(String id, String openid,boolean bLoadAll);
	PageList<InformReplyVo> selectInformReplyByInformId(String id, String openid,PageBounds pb);
	PageList<InformReplyVo> selectInformReplyByInformId(String id, String openid,boolean bLoadAll,PageBounds pb);
	InformReply selectInformReplyById(String id);
	InformReplyVo selectInformReplyByInformIdAndOpenid(String informId,String openid);
	InformReplyRemark selectInformReplyRemarkByReplyIdAndOpenid(String informId,String openid);
	
	int insertInformReplyAttach(InformReplyAttach entity);	
	int updateInformReplyAttach(InformReplyAttach entity);
	int deleteInformReplyAttach(String id);
	List<InformReplyAttach> selectInformReplyAttachByReplyId(String id);
	PageList<InformReplyAttach> selectInformReplyAttachByReplyId(String id,PageBounds pb);
	InformReplyAttach selectInformReplyAttachById(String id);
	
	
	int insertInformReplyRemark(InformReplyRemark entity);	
	int updateInformReplyRemark(InformReplyRemark entity);
	List<InformReplyRemark> selectInformReplyRemarkByReplyId(@Param("id") String id);
	PageList<InformReplyRemark> selectInformReplyRemarkByReplyId(@Param("id") String id,PageBounds pb);
	InformReplyRemark selectInformReplyRemarkById(@Param("id") String id);
	int deleteInformReplyRemark(@Param("id")String id);
	/**
	 * 统计恢复的评论情况
	 * 
	 * <br>
	 * @param id
	 * @return
	 * <br>
	 * 时间：2018年7月29日 下午9:27:03，联系：54871876@qq.com
	 */
	Map<String,Object> selectInformReplyRemarkStat(@Param("id") String id);
	
	
	/**
	 * 查询最近使用的头像。这些值其实可以存存储到客户端
	 * 
	 * <br>
	 * @param id
	 * @return
	 * <br>
	 * 时间：2018年7月29日 下午9:27:27，联系：54871876@qq.com
	 */
	List<String> selectCoverUrlByOpenid(String id);
	
	/**
	 * 更新打开次数
	 * 
	 * <br>
	 * @param id
	 * @return
	 * <br>
	 * 时间：2018年7月29日 下午9:28:09，联系：54871876@qq.com
	 */
	int updateInformOpenCount(String id);
	
	
}

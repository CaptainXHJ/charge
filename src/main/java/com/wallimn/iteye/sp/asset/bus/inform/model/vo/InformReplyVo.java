package com.wallimn.iteye.sp.asset.bus.inform.model.vo;

import java.util.List;

import com.wallimn.iteye.sp.asset.bus.inform.model.InformReply;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReplyAttach;
import com.wallimn.iteye.sp.asset.bus.inform.model.InformReplyRemark;

public class InformReplyVo extends InformReply {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4035477543412187960L;
	private List<InformReplyAttach> replyAttachs;
	private List<InformReplyRemark> replyRemarks;

	public List<InformReplyAttach> getReplyAttachs() {
		return replyAttachs;
	}

	public void setReplyAttachs(List<InformReplyAttach> replyAttachs) {
		this.replyAttachs = replyAttachs;
	}

	public List<InformReplyRemark> getReplyRemarks() {
		return replyRemarks;
	}

	public void setReplyRemarks(List<InformReplyRemark> replyRemarks) {
		this.replyRemarks = replyRemarks;
	}
}

package com.wallimn.iteye.sp.asset.bus.weixin.model;

/**
 * 微信用户的四个基本属性
 * @author Lenovo
 *
 */
public class Weixiner {

	private String openid;
	private String avatarUrl;
	private String nickName;
	private String unionid;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	
}

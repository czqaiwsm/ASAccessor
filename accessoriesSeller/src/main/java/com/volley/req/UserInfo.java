package com.volley.req;

/**
 * 用户信息
 * 
 * @ClassName: UserInfo
 */
public class UserInfo {
	private String uid; // 用户id
	private String phone; // 用户手机号
	private String username; // 用户名
	private String avatar; // 头像
	private String nickname; // 用户昵称
	private String email;
	private String token; // 用户登录标志

	private static UserInfo mUserInfo;

	public UserInfo() {
	}

	public static UserInfo getInstance() {
		if (mUserInfo == null) {
			mUserInfo = new UserInfo();
		}
		return mUserInfo;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "UserInfo [uid=" + uid + ", phone=" + phone + ", username=" + username + ", avatar=" + avatar + ", nickname=" + nickname + ", email="
				+ email + ", token=" + token + "]";
	}

}

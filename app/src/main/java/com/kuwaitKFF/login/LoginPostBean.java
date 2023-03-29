package com.kuwaitKFF.login;

public class LoginPostBean {
	
	/**
	 * @param userId
	 * @param password
	 */
	LoginPostBean(String userId, String password) {
		super();
		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	String userId,password;

}

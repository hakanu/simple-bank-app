package com.banka.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class SessionForm {
	private boolean loggedIn;
	private boolean admin;
	private boolean active;
	private boolean showLogin;

	/**
	 * @return
	 */
	public boolean isLoggedIn() {
		// TODO add logic here
		return loggedIn;
	}

	/**
	 * @return
	 */
	public boolean isAdmin() {
		// TODO add logic here
		return admin;
	}

	/**
	 * @return
	 */
	public boolean isActive() {
		// TODO add logic here
		return active;
	}

	/**
    * 
    */
	public void logIn() {
		// TODO mark fields to require login
		showLogin = true;
		// we were using richfaces:modalPanel showWhenRendered="true"
		// rendered="#{sessionBean.showLogin}"
	}

	// setters and other code goes heres
	public boolean isShowLogin() {
		return showLogin;
	}

	public void setShowLogin(boolean showLogin) {
		this.showLogin = showLogin;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}

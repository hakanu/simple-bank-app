package com.banka.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.banka.accessControl.LoggedInCheck;

@ManagedBean
@RequestScoped
public class MessageBean {
	
	private String globalMessage;
	
	public MessageBean(){
		globalMessage = getMessage();
	}
	
	public void setGlobalMessage(String globalMessage) {
		this.globalMessage = globalMessage;
	}

	public String getGlobalMessage(){
		return globalMessage;
	}
	
	public String getMessage(){
		return LoggedInCheck.messageGrowl;
	}
}

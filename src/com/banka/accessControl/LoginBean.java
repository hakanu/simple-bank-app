package com.banka.accessControl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class LoginBean{
	
	public static boolean isLoggedIn() {
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String isLog = (String)session.getAttribute("isLoggedIn");
	    System.out.println("isLoggedIn: " + isLog);
	    LoggedInCheck.messageGrowl = "";
	    return (isLog != null && isLog.equals("yes"));
	}
	
	public static void setLoggedIn() {
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	   
	    session.setAttribute("isLoggedIn", "true");
	    String isLog = (String)session.getAttribute("isLoggedIn");
	    
	    System.out.println("isLoggedIn: " + isLog);
	}
	
	
	public String checkUserLogin(){
		
		return ("p_musteri_main");
	}
	
	public static boolean isAdminLoggedIn() {
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String isLog = (String)session.getAttribute("isLoggedIn");
	    System.out.println("isLoggedIn(bean): " + isLog);
	    return (isLog != null && isLog.equals("ADMIN"));
	}
	
	public static boolean isMusteriLoggedIn() {
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String isLog = (String)session.getAttribute("isLoggedIn");
	    System.out.println("isLoggedIn: " + isLog);
	    return (isLog != null && isLog.equals("yes"));
	}
}
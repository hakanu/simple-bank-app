package com.banka.bean;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.event.IdleEvent;

import com.banka.accessControl.LoggedInCheck;

@ManagedBean
@RequestScoped
public class IdleMonitorController {
	public void idleListener(IdleEvent event) throws IOException {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Oturumunuz kapatýldý", "Uzun süredir iþlem yapmadýnýz"));
		
		//////////////**************GROWL
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Oturumunuz kapatýldý", "Uzun süredir iþlem yapmadýnýz"));		
		//////////////**************GROWL
		
		System.out.println("idle oldum");
//		PhaseEvent event2 = event.getPhaseId();
//		FacesContext fc = event.getFacesContext();
//		NavigationHandler nh = fc.getApplication().getNavigationHandler();
//		nh.handleNavigation(fc, null, "p_loginAjax");
		doLogout();
		LoggedInCheck.messageGrowl = "Oturumunuz kapatýldý, Uzun süredir iþlem yapmadýnýz";
		FacesContext.getCurrentInstance().getExternalContext().redirect("p_loginAjax.jsf");

		//invalidate session
		
	}
	
	private String doLogout(){	
		System.out.println("doLogouttayim");
		
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    session.setAttribute("isLoggedIn", null);
	    
		LoggedInCheck.currentMusteri = null;
		
		LoggedInCheck.isMusteriLoggedIn = false;
		LoggedInCheck.isBankaMemuruLoggedIn = false;
		LoggedInCheck.isYoneticiLoggedIn = false;
		
		System.out.println("doLogout bitti yönleniyorum");
		
		
		return ("p_loginAjax");
	}
}

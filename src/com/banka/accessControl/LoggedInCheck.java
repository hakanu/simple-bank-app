package com.banka.accessControl;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import com.banka.bean.Login;
import com.banka.entity.Musteri;


public class LoggedInCheck implements PhaseListener {

	public static Musteri currentMusteri;
	public static boolean isMusteriLoggedIn = false;
	public static boolean isYoneticiLoggedIn = false;
	public static boolean isBankaMemuruLoggedIn = false;
	
	public static String messageGrowl = "";
	
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public void beforePhase(PhaseEvent event) {
    	System.out.println("before phase baþladý");
    	System.out.println("before phase bitti");
    }

    public void afterPhase(PhaseEvent event) {
//    	messageGrowl = "";
    	System.out.println("---after phase baþladý");
        FacesContext fc = event.getFacesContext();
        
        System.out.println( fc.getViewRoot().getViewId().lastIndexOf("login") );
        
        boolean loginPage = fc.getViewRoot().getViewId().lastIndexOf("login") > -1 ? true : false;
        
        boolean registerPage = fc.getViewRoot().getViewId().lastIndexOf("register") > -1 ? true : false;
        
        boolean subelerPage = fc.getViewRoot().getViewId().lastIndexOf("p_subeler") > -1 ? true : false;
        
        boolean howtoPage = fc.getViewRoot().getViewId().lastIndexOf("p_how_to_use") > -1 ? true : false;
        
        boolean aboutPage = fc.getViewRoot().getViewId().lastIndexOf("p_about") > -1 ? true : false;
        
        System.out.println("loginPage: " + loginPage);
        System.out.println("registerPage: " + registerPage);
        System.out.println("fc.getViewRoot().getViewId(): " + fc.getViewRoot().getViewId());
        
        String destinationJsf = fc.getViewRoot().getViewId();
        destinationJsf = destinationJsf.substring(1, destinationJsf.indexOf('.'));
        System.out.println("destinationJsf: " + destinationJsf);
        
        System.out.println("LoggedInCheck.isMusteriLoggedIn: " + LoggedInCheck.isMusteriLoggedIn);
        
        if( !isAdminLoggedIn() ){
        	if ( !loginPage && !registerPage && !subelerPage && !howtoPage && !aboutPage) {
        		
        		System.out.println("\n\nlogin pageden farklý bi sayfa istendi\n\n");
        		
        		if(isAdminLoggedIn()){
        			if(isYoneticiLoggedIn == false){
	        			System.out.println("admin loggedin");
	        			NavigationHandler nh = fc.getApplication().getNavigationHandler();
	        			nh.handleNavigation(fc, null, "p_yonetici_main");
	        			isYoneticiLoggedIn = true;
        			}
        		}
        		
        		else if( isUserLoggedIn() ){
        			if(isMusteriLoggedIn == false){
	    				System.out.println("user loggedin");
	    				NavigationHandler nh = fc.getApplication().getNavigationHandler();
	    				nh.handleNavigation(fc, null, "p_musteri_main");
	    				isMusteriLoggedIn = true;
        			}
        		}
        		else if( isBankaMemuruLoggedIn() ){	
        			if(isBankaMemuruLoggedIn == false){
						System.out.println("memur loggedin");
	    				NavigationHandler nh = fc.getApplication().getNavigationHandler();
	    				nh.handleNavigation(fc, null, "p_banka_memuru_main");
	    				isBankaMemuruLoggedIn = true;
        			}
				}
        		else{
					System.out.println("memur deðil hiç biþi deðil");
					if( registerPage ){
			        	System.out.println("\n\nregister page istendi\n\n");
			        	NavigationHandler nh = fc.getApplication().getNavigationHandler();
						nh.handleNavigation(fc, null, destinationJsf);
			        }
					else{
						NavigationHandler nh = fc.getApplication().getNavigationHandler();
						nh.handleNavigation(fc, null, "p_loginn");
					}
				}
        		
        		/*
        		if(!isAdminLoggedIn()){
        			System.out.println("admin deðil");
        			if(!isUserLoggedIn()){
        				System.out.println("user deðil");
        				if( isBankaMemuruLoggedIn() ){	
        					System.out.println("memur loggedin");
            				NavigationHandler nh = fc.getApplication().getNavigationHandler();
            				nh.handleNavigation(fc, null, "p_banka_memuru_main");
        				}
        				else{
        					System.out.println("memur deðil hiç biþi deðil");
        					NavigationHandler nh = fc.getApplication().getNavigationHandler();
        					nh.handleNavigation(fc, null, "p_loginn");
        				}
        			}

        			else if( isUserLoggedIn() ){
        				System.out.println("user loggedin");
        				NavigationHandler nh = fc.getApplication().getNavigationHandler();
        				nh.handleNavigation(fc, null, "p_musteri_main");
        			}

        		}
        		else if( isAdminLoggedIn() ){
        			System.out.println("admin loggedin");
        			NavigationHandler nh = fc.getApplication().getNavigationHandler();
        			nh.handleNavigation(fc, null, "p_yonetici_main");
        		}
        		*/
        	}
        }
        
        if ( loginPage ) {
        	System.out.println("\n\nlogin page istendi\n\n");
//        	NavigationHandler nh = fc.getApplication().getNavigationHandler();
//            nh.handleNavigation(fc, null, "p_loginn");
        }
        
        if( registerPage ){
        	System.out.println("\n\nregister page istendi\n\n");
//        	NavigationHandler nh = fc.getApplication().getNavigationHandler();
//			nh.handleNavigation(fc, null, destinationJsf);
        }
        
        System.out.println("---after phase bitti");
    }

    public static void setLoggedIn() {
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	   
	    session.setAttribute("isLoggedIn", "true");
	    String isLog = (String)session.getAttribute("isLoggedIn");
	    
	    System.out.println("isLoggedIn: " + isLog);
	}
    
    private boolean adminLoggedIn() {
        return LoginBean.isLoggedIn();
    }
    
    public boolean isAdminLoggedIn() {
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String isLog = (String)session.getAttribute("isLoggedIn");
	    System.out.println("isLoggedIn(bean): " + isLog);
	    return (isLog != null && isLog.equals("ADMIN"));
	}
    
    public boolean isUserLoggedIn() {
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String isLog = (String)session.getAttribute("isLoggedIn");
	    System.out.println("isLoggedIn(bean): " + isLog);
	    return (isLog != null && isLog.equals("USER"));
	}
    
    public boolean isBankaMemuruLoggedIn() {
	    HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    String isLog = (String)session.getAttribute("isLoggedIn");
	    System.out.println("isLoggedIn(bean): " + isLog);
	    return (isLog != null && isLog.equals("MEMUR"));
	}
    
} 
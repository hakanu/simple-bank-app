package com.banka.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.banka.accessControl.LoggedInCheck;
import com.banka.dbOperations.BankaMemuruDBOperator;
import com.banka.dbOperations.MusteriDBOperator;
import com.banka.entity.BankaMemuru;
import com.banka.entity.Musteri;

@ManagedBean
@SessionScoped
public class Logout{
//	private String password;
//	private String customerId;
//	private String message = "";
	
	public Logout(){
		System.out.println("Logout bean yaratildi");
	}
	
	public String doLogout(){	
		System.out.println("doLogouttayim");
		
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    session.setAttribute("isLoggedIn", null);
	    
		LoggedInCheck.currentMusteri = null;
		
		LoggedInCheck.isMusteriLoggedIn = false;
		LoggedInCheck.isBankaMemuruLoggedIn = false;
		LoggedInCheck.isYoneticiLoggedIn = false;
		LoggedInCheck.messageGrowl = "";
		System.out.println("doLogout bitti y�nleniyorum");
		return ("p_loginAjax");
	}
	
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public String getCustomerId() {
//		return customerId;
//	}
//	public void setCustomerId(String customerId) {
//		this.customerId = customerId;
//	}
//	public String getMessage() {
//		return message;
//	}
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	public Musteri musteriDBBul( String customerId, String password ){
//		MusteriDBOperator m = new MusteriDBOperator(); 
//		Musteri loginOlan = m.checkUserLoginInfo(customerId, password);
//		
//		if(loginOlan != null){
//			System.out.println( "login olan : " + loginOlan.getAdi() + " " + loginOlan.getUyeAdi() + " " + loginOlan.getSifre() + " " + loginOlan.getId() );
////			System.out.println("sistemdeki musterinin hesaplari: " + loginOlan.getHesaplar().get(0).getHesapAdi() + " ");
//		}
//		
//		return loginOlan;
//	}
//	
//	public BankaMemuru bankaMemuruDBBul( String customerId, String password ){
//		BankaMemuruDBOperator m = new BankaMemuruDBOperator(); 
//		BankaMemuru loginOlan = m.checkBankaMemuruLoginInfo(customerId, password);
//		
//		if(loginOlan != null){
//			System.out.println( "login olan : " + loginOlan.getBankaMemuruAdi() + " " + loginOlan.getBankaMemuruUyeAdi() + " " 
//					+ loginOlan.getBankaMemuruSifre() + " " + loginOlan.getBankaMemuruId() );
//		}
//		
//		return loginOlan;
//	} 
//	
//	public String typeMemursaDBdeMemurAra( String customerId, String password ){
//		BankaMemuru memurLoginOlan = bankaMemuruDBBul(customerId, password);
//		if(memurLoginOlan != null){
//			System.out.println("\n\nMEMUR\n\n");
//			LoggedInCheck.currentMusteri = null;
//			
//			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//			   
//		    session.setAttribute("isLoggedIn", "MEMUR");
//		    LoggedInCheck.isBankaMemuruLoggedIn = true;
//		    
//			return ("p_banka_memuru_main");
//		}
//		System.out.println("!!!bulamad�m memuru dbde");
//		return ("_404");
//	}
//	
//	public String checkUserLogin() {
//		System.out.println("checkUserLogin'deyim");
//		
////		if (!password.equals("admin") && !password.equals("user") && !password.equals("memur") ) {
////			message = "Hatal� kullan�c� ad� veya �ifre";
////			System.out.println(message);
////		} 
//		if (password.isEmpty() && password.isEmpty() && password.isEmpty() ) {
//			message = "Hatal� kullan�c� ad� veya �ifre";
//			System.out.println(message);
//		}
//		else {
////			customer = BankCustomerLookupService.getCustomer(customerId);
//			
//			Musteri loginOlan = musteriDBBul(customerId, password);
//			
//			if(loginOlan != null){///////////MUSTERI LOGIN OLMA IHTIMALI
//				if(loginOlan.getType() == 0){////////////////USER
//					System.out.println("currentMusteri g�nledim");
//					LoggedInCheck.currentMusteri = loginOlan;
//					
//					HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//					   
//				    session.setAttribute("isLoggedIn", "USER");
//					LoggedInCheck.isMusteriLoggedIn = true;
//				    
//					return ("p_musteri_main");
//				}
//				else if(loginOlan.getType() == 1){////////////////YONETICI
//					System.out.println("YONETICI");
//					LoggedInCheck.currentMusteri = null;
//					
//					HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//					   
//				    session.setAttribute("isLoggedIn", "ADMIN");
//				    LoggedInCheck.isYoneticiLoggedIn = true;
//				    
//					return ("p_yonetici_main");
//				}
//				else if(loginOlan.getType() == 2){////////////////MEMUR
//					/*
//					System.out.println("\n\nMEMUR\n\n");
//					LoggedInCheck.currentMusteri = null;
//					
//					HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//					   
//				    session.setAttribute("isLoggedIn", "MEMUR");
//				    LoggedInCheck.isBankaMemuruLoggedIn = true;
//				    
//					return ("p_banka_memuru_main");
//					*/
//					System.out.println("!!memur dbde var m� bak�yorum!!");
//					String sonuc = typeMemursaDBdeMemurAra(customerId, password);
//					
//					if(sonuc.equals("p_banka_memuru_main")){
//						System.out.println("banka memuru anasayfas�na gidiyorem");
//						return ("p_banka_memuru_main");
//					}
//					else{
//						System.out.println("hata sayfas�na gidiyorem");
//						return ("_404");
//					}
//				}
//			}
//			
//			else if(loginOlan == null){///////////BANKA MEMURU LOGIN OLMA IHTIMALI
//				System.out.println("::banka memuru login olma ihtimali::");
//				String sonuc = typeMemursaDBdeMemurAra(customerId, password);
//				
//				if(sonuc.equals("p_banka_memuru_main")){
//					System.out.println("banka memuru anasayfas�na gidiyorem");
//					return ("p_banka_memuru_main");
//				}
//				else{
//					System.out.println("hata sayfas�na gidiyorem");
//					return ("_404");
//				}
//			}
//			
//			else
//				return ("_404");
//			
//			System.out.println("***eski sistem login");
//			/**
//			 * ESKI SISTEM LOGIN OLAYI BURASI
//			 */
//			if (customerId == null || password == null) {
//				message = "Kullan�c� ad�n� veya �ifreyi bo� b�rakamazs�n�z";
//			} 
//			else if (customerId.equals("user")) {
//				if (password.equals("user")){
//					System.out.println("user �ifresi id si girildi");
//					HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//					   
//				    session.setAttribute("isLoggedIn", "USER");
//					
//					return ("p_musteri_main");
//				}
//			} 
//			else if (customerId.equals("admin")) {
//				if (customerId.equals("admin")){
//					System.out.println("admin �ifresi id si girildi");
//					HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//					   
//				    session.setAttribute("isLoggedIn", "ADMIN");
//				    
//					return ("p_yonetici_main");
//				}
//			}
//			else if (customerId.equals("memur")) {
//				if (customerId.equals("memur")){
//					System.out.println("memur �ifresi id si girildi");
//					HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//					   
//				    session.setAttribute("isLoggedIn", "MEMUR");
//				    
//					return ("p_banka_memuru_main");
//				}
//			}
//			return ("_404");
//		}
//		return (null);
//	}
//	
//	public String logout(){
//		
//		return ("p_loginn");
//	}
//	
//	public String deneme(){
//		System.out.println("bean �al���yor");
//		System.out.println("customerId : " + customerId);
//		System.out.println("password   : " + password);
//
//		return ("_user_main");
//	}

	
}

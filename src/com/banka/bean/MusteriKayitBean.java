package com.banka.bean;

import java.awt.event.ActionEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FlowEvent;

import com.banka.accessControl.LoggedInCheck;
import com.banka.dbOperations.MusteriDBOperator;
import com.banka.entity.Musteri;
import com.banka.util.SendMail;

@ManagedBean
@SessionScoped
public class MusteriKayitBean {
	private Musteri musteri = new Musteri();
	
	private boolean skip;

	public Musteri getMusteri() {
		return musteri;
	}

	public void setMusteri(Musteri musteri) {
		this.musteri = musteri;
	}

	public String save() throws Exception {
		//Persist musteri
		
		System.out.println("adi   : " + musteri.getAdi());
		System.out.println("ID    : " + musteri.getId());
		System.out.println("mail  : " + musteri.getMail());
		System.out.println("soyadi: " + musteri.getSoyadi());
		System.out.println("web	  : " + musteri.getWeb());
		System.out.println("�ifre : " + musteri.getSifre());
		System.out.println("tckimlik: " + musteri.getTckimlik());
		System.out.println("telefon: " + musteri.getTelefon());
		
//		FacesMessage msg = new FacesMessage("Successful", "Welcome :" + musteri.getAdi());
//		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		System.out.println("musteriyi kay�t ediyorum");
		MusteriDBOperator musteriOperator = new MusteriDBOperator();
		String condition = musteriOperator.musteriEkle(musteri);
		if(condition.equals("uyeAdi")){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Ba�ar�s�z", "Farkl� bir �ye Ad� giriniz."));		
			//////////////**************GROWL
			return null;
		}
		else if(condition.equals("mail")){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Ba�ar�s�z", "Farkl� bir mail adresi giriniz."));		
			//////////////**************GROWL
			return null;
		}
		else if(condition.equals("tckimlik")){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Ba�ar�s�z", "Farkl� bir TC Kimlik no giriniz."));		
			//////////////**************GROWL
			return null;
		}
		musteriOperator.musteriListele();
		System.out.println("musteriyi kay�t ediyorum");
		
		
		
		/////////////LOGIN OLAYI
		
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		   
	    session.setAttribute("isLoggedIn", "USER");
	    String isLog = (String)session.getAttribute("isLoggedIn");
	    
	    System.out.println("isLoggedIn(kayitBean): " + isLog);
		
		LoggedInCheck.currentMusteri = musteri;
		
		LoggedInCheck.isBankaMemuruLoggedIn = false;
		LoggedInCheck.isYoneticiLoggedIn = false;
		LoggedInCheck.isMusteriLoggedIn = true;
		
		System.out.println("LoggedInCheck.isMusteriLoggedIn(kayitBean): " + LoggedInCheck.isMusteriLoggedIn);
		
		///////////////LOGIN OLAYI
		
		
		
		///////////////////////////***********MAIL YOLLAMA
		String subject = "Banka kayd�n�z ba�ar� ile tamamlanm��t�r";
		String text = "�yelik bilgileriniz asagidaki gibidir: \n"
			+ "Adi     : " + musteri.getAdi() + "\n"
			+ "Soyadi  : " + musteri.getSoyadi() + "\n"
			+ "�ye adi : " + musteri.getUyeAdi() + "\n"
			+ "Mail    : " + musteri.getMail() + "\n"
			+ "Adres   : " + musteri.getWeb();
			
		System.out.println("--�yelik bilgileri--\n" + text);	
		System.out.println("�ye mail: " + musteri.getMail());
		SendMail mailSender = new SendMail(musteri.getMail(), subject, text);
		
		if( mailSender.send() ){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Ba�ar�l�", "M��teri kayd�n�z ba�ar� ile yap�lm��t�r. " +
					"Kay�t bilgileriniz i�in mail kutunuzu kontrol ediniz"));		
			//////////////**************GROWL
		}
		else{
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "M��teri kayd�n�z ba�ar� ile yap�lm��t�r. " +
					"Ancak ge�ersiz bir mail adresi girdi�iniz i�in mail yollanamamaktad�r"));		
			//////////////**************GROWL
		}
		
		///////////////////////////***********MAIL YOLLAMA

		
		
		musteri = new Musteri();
		System.out.println("yonleniyorum");
		
		return ("p_musteri_main");
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public String onFlowProcess(FlowEvent event) {
		System.out.println("Current wizard step:" + event.getOldStep());
		System.out.println("Next step:" + event.getNewStep());

		if(skip) {
			skip = false;	//reset in case musteri goes back
			return "confirm";
		}
		else {
			return event.getNewStep();
		}
	}
}

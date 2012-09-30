package com.banka.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.banka.dbOperations.MusteriDBOperator;
import com.banka.entity.Musteri;

@ManagedBean
@SessionScoped
public class YoneticiMusteriEkleBean {

	private Musteri musteri;
	
	public Musteri getMusteri() {
		return musteri;
	}

	public void setMusteri(Musteri musteri) {
		this.musteri = musteri;
	}
	public YoneticiMusteriEkleBean() {
		setMusteri(new Musteri());
	}

	public String save() throws Exception {
		//Persist musteri

		System.out.println("adi   : " + musteri.getAdi());
		System.out.println("soyadi: " + musteri.getSoyadi());
		System.out.println("ID    : " + musteri.getId());
		System.out.println("�ifre : " + musteri.getSifre());
		System.out.println("mail  : " + musteri.getMail());
		System.out.println("web	  : " + musteri.getWeb());
		
//		FacesMessage msg = new FacesMessage("Successful", "Welcome :" + musteri.getAdi());
//		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		System.out.println("musteriyi kay�t ediyorum");
		MusteriDBOperator musteriOperator = new MusteriDBOperator();
	/*	Musteri musteriCheck = new Musteri();
		musteriCheck.setUyeAdi(musteri.getUyeAdi());
		musteriCheck.setSifre("");
		musteriCheck.setAdi("");
		musteriCheck.setSoyadi("");
		musteriCheck.setMail("");
		musteriCheck.setWeb("");
		if((musteriOperator.musteriBul(musteriCheck)).isEmpty() == false)
			return "_404.jsf";
		
		musteriCheck = new Musteri();
		musteriCheck.setUyeAdi("");
		musteriCheck.setSifre("");
		musteriCheck.setAdi("");
		musteriCheck.setSoyadi("");
		musteriCheck.setMail(musteri.getMail());
		musteriCheck.setWeb("");
		if((musteriOperator.musteriBul(musteriCheck)).isEmpty() == false)
			return "_404.jsf";	*/
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
		musteri = new Musteri();
		System.out.println("musteriyi kay�t ediyorum");
		
		//////////////**************GROWL
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Ba�ar�l�", "Yeni m��teri ba�ar� ile eklendi"));		
		//////////////**************GROWL
		
		System.out.println("yonleniyorum");
		return ("p_banka_memuru_main");
	}
}

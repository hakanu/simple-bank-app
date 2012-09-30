package com.banka.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.NoSuchProviderException;
import javax.mail.internet.AddressException;

import com.banka.accessControl.LoggedInCheck;
import com.banka.dbOperations.MusteriDBOperator;
import com.banka.entity.Musteri;
import com.banka.util.PasswordGenerator;
import com.banka.util.PasswordHash;
import com.banka.util.SendMail;

@ManagedBean
@RequestScoped
public class SifremiUnuttumBean {
	private String mail;
	private String uyeAdi;
	private String telefon;
	
	public SifremiUnuttumBean(){
		System.out.println("sifremiUnuttumBean yaratildi");
	
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getUyeAdi() {
		return uyeAdi;
	}

	public void setUyeAdi(String uyeAdi) {
		this.uyeAdi = uyeAdi;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String sifremiUnuttum() throws AddressException, NoSuchProviderException{
		System.out.println("sifremiUnuttum metodunday�m");
		System.out.println("mail	: " + mail);
		System.out.println("telefon : " + telefon);
		System.out.println("�ye ad� : " + uyeAdi);
		
		Musteri bulunanMusteri = musteriGercektenVarMi(mail, telefon, uyeAdi);
		
		if(bulunanMusteri != null){
			System.out.println("m��teri var - yeni �ifre �retiyorum");
						
			String yeniSifre = PasswordGenerator.getRandomPassword(8);
			
			System.out.println("yeni sifre: " + yeniSifre);
			String text = "Sifre sifirlama isleminiz basarili.\r\n" + "Yeni sifreniz: " + 
				yeniSifre + " \r\nBu sifreyle sisteme giris yapabilirsiniz" ;
			String subject = "Bbmbanka sifre s�f�rlama istemi";
			
			
			
			SendMail s = new SendMail(mail, subject, text);
			if(s.send()){
				
				System.out.println("yeni sifreyi dbye ekliyorum");
				bulunanMusteri.setSifre( PasswordHash.hashPassword(yeniSifre) );
				(new MusteriDBOperator()).musteriGunleWithID(bulunanMusteri, 0);
				System.out.println("yeni sifreyi dbye ekledim");
				
				//////////////**************GROWL
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Ba�ar�l�", "�ifreniz s�f�rland�, yeni �ifreniz i�in mail kutunuzu kontrol ediniz"));		
				//////////////**************GROWL
				
				LoggedInCheck.messageGrowl = "�ifreniz s�f�rland�, yeni �ifreniz i�in mail kutunuzu kontrol ediniz";
			}
			else{
				//////////////**************GROWL
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Hata", "Mail adresi hatal�"));		
				//////////////**************GROWL
				
				LoggedInCheck.messageGrowl = "�ifre s�f�rlama ba�ar�s�z! Mail adresi hatal�";
			}
			

		}
		else{
			System.out.println("b�yle bi m��teri yok");
			
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Girdi�iniz bilgilere uygun m��teri sistemde kay�tl� de�ildir"));		
			//////////////**************GROWL
			LoggedInCheck.messageGrowl = "�ifre s�f�rlama ba�ar�s�z! Girdi�iniz bilgilere uygun m��teri sistemde kay�tl� de�ildir";
		}
		
		System.out.println("sifremiUnuttum metodu bitti y�nleniyorum");
		return ("p_loginAjax.jsf?faces-redirect=true");
//		return null;
	}
	
	private Musteri musteriGercektenVarMi(String par_sMail, String par_sTelefon, String par_sUyeAdi){
		
		MusteriDBOperator musteriOperator = new MusteriDBOperator();
		
		Musteri bulunan = musteriOperator.musteriBulWithUyeAdi(par_sUyeAdi);
		
		if(bulunan != null){
			System.out.println("dbde m��teriyi buldum �zellikleri ayn� m� bak�yorum");
			
			if(bulunan.getMail().equals(par_sMail)){
				if(bulunan.getTelefon().equals(par_sTelefon)){
					return bulunan;
				}
			}
			return null;
		}
		else{
			return null;
		}
	}
	
	
}

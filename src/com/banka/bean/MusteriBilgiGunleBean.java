package com.banka.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.banka.accessControl.LoggedInCheck;
import com.banka.dbOperations.MusteriDBOperator;
import com.banka.dbOperations.SubeDBOperator;
import com.banka.entity.Musteri;
import com.banka.util.PasswordHash;

@ManagedBean
@RequestScoped
public class MusteriBilgiGunleBean {
	private List<Musteri> musteriler = null;
	
	private String eskiSifre;
	
	private String yeniSifre;
	
	private Musteri gunlenecekMusteri = LoggedInCheck.currentMusteri;
	
	private Musteri selectedMusteri = new Musteri();

	public MusteriBilgiGunleBean(){
		System.out.println("sistemdeki müþteri bilgileri: ");
		System.out.println(LoggedInCheck.currentMusteri.getAdi() + " " + LoggedInCheck.currentMusteri.getId() + " " + LoggedInCheck.currentMusteri.getUyeAdi());
		gunlenecekMusteri = LoggedInCheck.currentMusteri;
	}
	
	public List<Musteri> getMusteriler() {
		return musteriler;
	}

	public void setMusteriler(List<Musteri> musteriler) {
		this.musteriler = musteriler;
	}

	public Musteri getGunlenecekMusteri() {
		return gunlenecekMusteri;
	}

	public void setGunlenecekMusteri(Musteri gunlenecekMusteri) {
		this.gunlenecekMusteri = gunlenecekMusteri;
	}

	public Musteri getSelectedMusteri() {
		return selectedMusteri;
	}

	public void setSelectedMusteri(Musteri selectedMusteri) {
		this.selectedMusteri = selectedMusteri;
	}
	
	public String getEskiSifre() {
		return eskiSifre;
	}

	public void setEskiSifre(String eskiSifre) {
		this.eskiSifre = eskiSifre;
	}

	public String getYeniSifre() {
		return yeniSifre;
	}

	public void setYeniSifre(String yeniSifre) {
		this.yeniSifre = yeniSifre;
	}

	public void musterileriBul() throws Exception{
		MusteriDBOperator musteriOperator = new MusteriDBOperator();
		musteriler = musteriOperator.musteriBul(gunlenecekMusteri);
		//return ("p_banka_memuru_musteri_gunle_listesi");
	}
	
	private Musteri getMusteriFromID(int par_nMusteriID){
		Musteri sonuc = null;
		
		System.out.println( "musteriler in boyu: " + musteriler.size() );
		
		for (Musteri m:musteriler) {
			if(m.getId() == par_nMusteriID){
				sonuc = m;
				break;
			}
		}
		return sonuc;
	}
	
	public String yonlendir(){
		String gunlenecekId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("gunlenecekMusteriId");
		System.out.println("gunlenecekId " + gunlenecekId);
		
		gunlenecekMusteri = getMusteriFromID(Integer.parseInt( gunlenecekId ) );
		
		System.out.println("yonlendiriyorum");
		return ("p_musteri_bilgi_gunle_ayrinti");
	}

	public String musteriGunle() throws Exception{
		System.out.println("müþteri günledeyim");
		System.out.println( "selected " + selectedMusteri.getAdi() + " " + selectedMusteri.getSoyadi() );
		String gunlenecekId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("gunlenecekMusteriId");
		System.out.println("gunlenecekId " + gunlenecekId);
		
		System.out.println("yeni müþteri adi : " + gunlenecekMusteri.getAdi());
		System.out.println("yeni müþteri sehri : " + gunlenecekMusteri.getSoyadi());
		System.out.println("yeni müþteri uye adi : " + gunlenecekMusteri.getUyeAdi());
		
		//Sube silinecekSube = getSubeFromID( Integer.parseInt(silinecekId) );
		
		MusteriDBOperator musteriDBOperator = new MusteriDBOperator();
		Musteri eskiMusteri = musteriDBOperator.musteriBulWithID( Integer.parseInt(gunlenecekId) );
		
		eskiMusteri.setAdi(gunlenecekMusteri.getAdi());
		eskiMusteri.setSoyadi(gunlenecekMusteri.getSoyadi());
		eskiMusteri.setMail(gunlenecekMusteri.getMail());
		eskiMusteri.setWeb(gunlenecekMusteri.getWeb());
		eskiMusteri.setUyeAdi(gunlenecekMusteri.getUyeAdi());
		eskiMusteri.setYas(gunlenecekMusteri.getYas());
		
		musteriDBOperator.musteriGunleWithID( eskiMusteri, Integer.parseInt(gunlenecekId) );
		
		//musteriler = musteriDBOperator.getMusteri();
		System.out.println("tekrar yönleniyorum");
		
		//////////////**************GROWL
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Baþarýlý", "Müþteri bilgileri baþarý ile günlendi"));		
		//////////////**************GROWL
		
		return ("p_musteri_main");
	}
	

	
	public String sifreDegistir(){
		System.out.println("sifre degistirdeyim");
		
		
//		sifreDegisebilirMi(gunlenecekMusteri, eskiSifre, yeniSifre);
		System.out.println("gunlenecekMusteri: " + gunlenecekMusteri.getUyeAdi() + " " 
				+ gunlenecekMusteri.getSifre() + " " + gunlenecekMusteri.getSifre());
		System.out.println("eskiSifre: " + eskiSifre);
		System.out.println("yeniSifre: " + yeniSifre);
		
		if(   ( !(eskiSifre.isEmpty()) && !(yeniSifre.isEmpty()) )  && sifreDegisebilirMi(gunlenecekMusteri, eskiSifre, yeniSifre) != null){
			System.out.println("þifre deðiþebilir");
			
			MusteriDBOperator musteriOperator = new MusteriDBOperator();
			Musteri gunlenmisMusteri = musteriOperator.musteriBulWithID(gunlenecekMusteri.getId());
			
			gunlenmisMusteri.setSifre(PasswordHash.hashPassword( yeniSifre ));
			
			String gunlemeSonucu = musteriOperator.musteriGunleWithID(gunlenmisMusteri, gunlenmisMusteri.getId());
			
			if(gunlemeSonucu.equals("done")){
				System.out.println("þifreyi günledim");
				//////////////**************GROWL
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Baþarýlý", "Þifre baþarýyla deðiþtirildi"));		
				//////////////**************GROWL
			}
			else{
				System.out.println("þifreyi günlerken hata oldu");
				//////////////**************GROWL
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Baþarýlý", "Þifre baþarýyla deðiþtirildi"));		
				//////////////**************GROWL
			}
			

		}
		else{
			System.out.println("þifre deðiþemez");
			
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Þifre deðiþtirilemedi"));		
			//////////////**************GROWL
		}
		
		System.out.println("yönleniyorum");
		return ("p_musteri_main");
	}
	
	public Musteri sifreDegisebilirMi(Musteri par_mMusteri, String par_sEskiSifre, String par_sYeniSifre){
		if(par_mMusteri.getSifre().equals(PasswordHash.hashPassword(par_sEskiSifre))){
			par_mMusteri.setSifre(PasswordHash.hashPassword(par_sYeniSifre));
			return par_mMusteri;
		}
		else{
			return null;
		}
	}
	
}

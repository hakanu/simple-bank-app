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
@SessionScoped
public class BankaMemuruMusteriGunleBean {

	private List<Musteri> musteriler;
	private Musteri gunlenecekMusteri;
	private Musteri selectedMusteri;
	private boolean renderState;
	private boolean searchPanelState;
	public BankaMemuruMusteriGunleBean() {
		setGunlenecekMusteri(new Musteri());
		setSelectedMusteri(new Musteri());
		musteriler = new ArrayList<Musteri>();
		setRenderState(false);
		setSearchPanelState(true);
	}
	
	
	public boolean isSearchPanelState() {
		return searchPanelState;
	}


	public void setSearchPanelState(boolean searchPanelState) {
		this.searchPanelState = searchPanelState;
	}


	public boolean isRenderState() {
		return renderState;
	}


	public void setRenderState(boolean renderState) {
		this.renderState = renderState;
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
	public void musterileriBul() throws Exception{
		MusteriDBOperator musteriOperator = new MusteriDBOperator();
		musteriler = musteriOperator.musteriBul(gunlenecekMusteri);
		setRenderState(true);
		setSearchPanelState(false);
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
		
		
		if(LoggedInCheck.isYoneticiLoggedIn)
			return ("p_yonetici_musteri_gunle_ayrinti");
		else
			return ("p_banka_memuru_musteri_gunle_ayrinti");
		
	}

	public String musteriGunle() throws Exception{
		System.out.println("müþteri günledeyim");
		System.out.println( "selected " + selectedMusteri.getAdi() + " " + selectedMusteri.getSoyadi() );
		String gunlenecekId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("gunlenecekMusteriId");
		System.out.println("gunlenecekId " + gunlenecekId);
		
		System.out.println("yeni müþteri adi : " + gunlenecekMusteri.getAdi());
		System.out.println("yeni müþteri sehri : " + gunlenecekMusteri.getSoyadi());
		System.out.println("yeni müþteri uye adi : " + gunlenecekMusteri.getUyeAdi());
		System.out.println("yeni müþteri web : " + gunlenecekMusteri.getWeb());
		System.out.println("yeni müþteri sifre: " + gunlenecekMusteri.getSifre());
		
		//Sube silinecekSube = getSubeFromID( Integer.parseInt(silinecekId) );
		
		MusteriDBOperator musteriOperator = new MusteriDBOperator();
/*		Musteri musteriCheck = new Musteri();
		musteriCheck.setUyeAdi(gunlenecekMusteri.getUyeAdi());
		musteriCheck.setSifre("");
		musteriCheck.setAdi("");
		musteriCheck.setSoyadi("");
		musteriCheck.setMail("");
		musteriCheck.setWeb("");
		setRenderState(false);
		setSearchPanelState(true);
		if((musteriOperator.musteriBul(musteriCheck)).isEmpty() == false && (musteriOperator.musteriBul(musteriCheck)).get(0).getId() != gunlenecekMusteri.getId())
			return "_404.jsf";*/
		
		
		
		
		String condition = musteriOperator.musteriGunleWithID( gunlenecekMusteri, Integer.parseInt(gunlenecekId) );
		if(condition.equals("uyeAdi")){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Baþarýsýz", "Farklý bir Üye Adý giriniz."));		
			//////////////**************GROWL
			return null;
		}
		else if(condition.equals("mail")){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Baþarýsýz", "Farklý bir mail adresi giriniz."));		
			//////////////**************GROWL
			return null;
		}
		gunlenecekMusteri = new Musteri();
		System.out.println("tekrar yönleniyorum");
		
		//////////////**************GROWL
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Baþarýlý", "Müþteri baþarý ile günlendi"));		
		//////////////**************GROWL
		if(LoggedInCheck.isYoneticiLoggedIn)
			return ("p_yonetici_main");
		else
			return ("p_banka_memuru_main");
		
	}
	
	public void yenidenAra() throws Exception{
		Musteri musteriCheck = new Musteri();
		musteriCheck.setUyeAdi("");
		musteriCheck.setSifre("");
		musteriCheck.setAdi("");
		musteriCheck.setSoyadi("");
		musteriCheck.setMail("");
		musteriCheck.setWeb("");
		setGunlenecekMusteri(musteriCheck);
		setRenderState(false);
		setSearchPanelState(true);
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

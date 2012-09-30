package com.banka.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.banka.accessControl.LoggedInCheck;
import com.banka.dbOperations.HesapDBOperator;
import com.banka.dbOperations.MusteriDBOperator;
import com.banka.dbOperations.SubeDBOperator;
import com.banka.entity.Hesap;
import com.banka.entity.Musteri;
import com.banka.entity.Sube;

@ManagedBean
@RequestScoped
public class HesapEkleBean {
	private List<Hesap> hesaplar = new ArrayList<Hesap>();
	
	private List<Sube> subeler = new ArrayList<Sube>();
	
	private Hesap selectedHesap;
	
	private Hesap eklenecekHesap = new Hesap();

	private Sube hesapEklenecekSube = new Sube();
	
	private int hesapEklenecekSubeId;
	
	public HesapEkleBean() throws Exception{	
		HesapDBOperator hesapOperator = new HesapDBOperator();
		hesaplar = hesapOperator.getMusteriHesapKotu(LoggedInCheck.currentMusteri);
		SubeDBOperator subeOperator = new SubeDBOperator();
		subeler = subeOperator.getSube();
	}
	
	public List<Hesap> getHesaplar() throws Exception {
		return hesaplar;
	}

	public void setHesaplar(List<Hesap> hesaplar) {
		this.hesaplar = hesaplar;
	}
	
	public Hesap getSelectedHesap() {
		return selectedHesap;
	}

	public void setSelectedHesap(Hesap selectedHesap) {
		this.selectedHesap = selectedHesap;
	}

	public Hesap getEklenecekHesap() {
		return eklenecekHesap;
	}

	public void setEklenecekHesap(Hesap eklenecekHesap) {
		this.eklenecekHesap = eklenecekHesap;
	}

	public Sube getHesapEklenecekSube() {
		return hesapEklenecekSube;
	}

	public void setHesapEklenecekSube(Sube hesapEklenecekSube) {
		this.hesapEklenecekSube = hesapEklenecekSube;
	}

	public int getHesapEklenecekSubeId() {
		return hesapEklenecekSubeId;
	}

	public void setHesapEklenecekSubeId(int hesapEklenecekSubeId) {
		this.hesapEklenecekSubeId = hesapEklenecekSubeId;
	}
	
	public List<Sube> getSubeler() {
		return subeler;
	}

	public void setSubeler(List<Sube> subeler) {
		this.subeler = subeler;
	}

	private Sube getSubeFromID(int par_nSubeID){
		Sube sonuc = null;
		
		System.out.println( "subeler in boyu: " + subeler.size() );
		for (Sube s:subeler) {
			if(s.getSubeId() == par_nSubeID){
				sonuc = s;
				break;
			}
		}
		return sonuc;
	}
	
	public String yonlendir(){
		String hesapEklenecekSubeId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hesapEklenecekSubeId");
		System.out.println("hesapEklenecekSubeId " + hesapEklenecekSubeId);
		
		hesapEklenecekSube = getSubeFromID(Integer.parseInt( hesapEklenecekSubeId ) );
		
		System.out.println("yonlendiriyorum");
		
		return ("p_musteri_hesap_ekle");
	}
	
	public void hesapEkle() throws Exception{
		String hesapEklenecekSubeId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hesapEklenecekSubeId");

		hesapEklenecekSube = getSubeFromID(Integer.parseInt(hesapEklenecekSubeId));
		
		System.out.println("hesapEklenecekSubeId-param : " + hesapEklenecekSubeId);
		System.out.println("hesapEklenecekSube: " + hesapEklenecekSube.getSubeAdi() + " " + hesapEklenecekSube.getSubeId());		
		
		MusteriDBOperator musteriOperator = new MusteriDBOperator();
		/////////////////silinecek
//		Musteri hesapEklenecekMusteri = null;
//		List<Musteri> musteriler = new ArrayList<Musteri>();
//		musteriler = musteriOperator.getMusteri();
//		for (Musteri musteri : musteriler) {
//			System.out.println("hesapEklenecekMusteri: " + musteri.getAdi() + " " + musteri.getId());
//			hesapEklenecekMusteri = musteri;
//		}
		
		////////////////
		
		
		eklenecekHesap.setSube(hesapEklenecekSube);
		eklenecekHesap.setHesapBalance(0);
		eklenecekHesap.setMusteri( LoggedInCheck.currentMusteri );
//		LoggedInCheck.currentMusteri.getHesaplar().add(eklenecekHesap);
		musteriOperator.musteriHesapEkle( LoggedInCheck.currentMusteri, eklenecekHesap );
		
		HesapDBOperator hesapDBOperator = new HesapDBOperator();
		hesapDBOperator.hesapEkle(eklenecekHesap);
		
		//////////////**************GROWL
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Baþarýlý", "Hesap baþarý ile eklendi"));		
		//////////////**************GROWL
		
	}
}
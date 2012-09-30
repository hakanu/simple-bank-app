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

public class MusteriHesapSilBean {
	private List<Hesap> hesaplar = new ArrayList<Hesap>();
	
	private List<Sube> subeler = new ArrayList<Sube>();
	
	private Hesap selectedHesap;
	
	private Hesap silinecekHesap = new Hesap();

	private Sube hesapSilinecekSube = new Sube();
	
	private int hesapSilinecekSubeId;
	
	public MusteriHesapSilBean() throws Exception{	
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

	public Hesap getSilinecekHesap() {
		return silinecekHesap;
	}

	public void setSilinecekHesap(Hesap silinecekHesap) {
		this.silinecekHesap = silinecekHesap;
	}

	public Sube getHesapEklenecekSube() {
		return hesapSilinecekSube;
	}

	public void setHesapEklenecekSube(Sube hesapSilinecekSube) {
		this.hesapSilinecekSube = hesapSilinecekSube;
	}

	public int getHesapEklenecekSubeId() {
		return hesapSilinecekSubeId;
	}

	public void setHesapEklenecekSubeId(int hesapSilinecekSubeId) {
		this.hesapSilinecekSubeId = hesapSilinecekSubeId;
	}
	
	public List<Sube> getSubeler() {
		return subeler;
	}

	public void setSubeler(List<Sube> subeler) {
		this.subeler = subeler;
	}

	private Hesap getHesapFromID(int par_nHesapno){
		Hesap sonuc = null;
		
		System.out.println( "hesaplar in boyu: " + hesaplar.size() );
		for (Hesap s:hesaplar) {
			if(s.getHesapno() == par_nHesapno){
				sonuc = s;
				break;
			}
		}
		return sonuc;
	}
	
	public String yonlendir(){
		String silinecekHesapId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("silinecekHesapId");
		System.out.println("hesapSilinecekSubeId " + hesapSilinecekSubeId);
		
		silinecekHesap = getHesapFromID(Integer.parseInt( silinecekHesapId ) );
		
		System.out.println("yonlendiriyorum");
		
		return ("p_musteri_hesap_sil_onay");
	}
	
	public String hesapSil() throws Exception{
		String silinecekHesapId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("silinecekHesapId");

		silinecekHesap = getHesapFromID(Integer.parseInt(silinecekHesapId));
		
		System.out.println( "silinecekHesapId-param : " + silinecekHesapId );
		System.out.println( "silinecekHesap: " + silinecekHesap.getHesapAdi() + " " + silinecekHesap.getHesapno() );		
		
		HesapDBOperator hesapOperator = new HesapDBOperator();
		
		boolean sonuc = hesapOperator.hesapSilWithEntity(silinecekHesap);
		
		if(sonuc){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Baþarýlý", "Hesap baþarý ile kaldýrýldý!"));		
			//////////////**************GROWL
		}
		else{
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Ýçinde bakiye bulunan hesaplar kaldýralamaz!"));		
			//////////////**************GROWL
		}
//		silinecekHesap.setSube(hesapSilinecekSube);
//		silinecekHesap.setHesapBalance(500);
//		silinecekHesap.setMusteri( LoggedInCheck.currentMusteri );
//		
//		HesapDBOperator hesapDBOperator = new HesapDBOperator();
//		hesapDBOperator.hesapEkle(silinecekHesap);
		
		hesaplar = hesapOperator.getMusteriHesapKotu(LoggedInCheck.currentMusteri);
		

		
		return ("p_musteri_hesap_sil");
	}
}

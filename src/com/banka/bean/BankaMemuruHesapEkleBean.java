package com.banka.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.banka.dbOperations.HesapDBOperator;
import com.banka.dbOperations.MusteriDBOperator;
import com.banka.dbOperations.SubeDBOperator;
import com.banka.entity.Hesap;
import com.banka.entity.Musteri;
import com.banka.entity.Sube;

@ManagedBean
//@RequestScoped
@SessionScoped
public class BankaMemuruHesapEkleBean {
	private List<Sube> subeler;
	
	private Hesap selectedHesap;
	
	private Hesap eklenecekHesap;

	private Sube hesapEklenecekSube;
	
	private int hesapEklenecekMusteriId;
	
	private List<Hesap> islemHesap;
	
	private boolean tableState;
	
	private boolean musteriNoState;

	private boolean subelerState;
	
	private String bulunacakMusteriNo;
	
	public String getBulunacakMusteriNo() {
		return bulunacakMusteriNo;
	}

	public void setBulunacakMusteriNo(String bulunacakMusteriNo) {
		this.bulunacakMusteriNo = bulunacakMusteriNo;
	}

	public boolean isSubelerState() {
		return subelerState;
	}

	public void setSubelerState(boolean subelerState) {
		this.subelerState = subelerState;
	}

	public boolean isMusteriNoState() {
		return musteriNoState;
	}

	public void setMusteriNoState(boolean musteriNoState) {
		this.musteriNoState = musteriNoState;
	}

	public boolean isTableState() {
		return tableState;
	}

	public void setTableState(boolean tableState) {
		this.tableState = tableState;
	}

	public BankaMemuruHesapEkleBean() throws Exception{
		bulunacakMusteriNo = new String();
		hesapEklenecekSube = new Sube();
		eklenecekHesap = new Hesap();
		subeler = new ArrayList<Sube>();
		setMusteriNoState(true);
		setTableState(false);
		setSubelerState(false);
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
	
	public List<Hesap> getIslemHesap() {
		return islemHesap;
	}

	public void setIslemHesap(List<Hesap> islemHesap) {
		this.islemHesap = islemHesap;
	}

	public int getHesapEklenecekMusteriId() {
		return hesapEklenecekMusteriId;
	}

	public void setHesapEklenecekMusteriId(int hesapEklenecekMusteriId) {
		this.hesapEklenecekMusteriId = hesapEklenecekMusteriId;
	}

	public List<Sube> getSubeler() {
		return subeler;
	}

	public void setSubeler(List<Sube> subeler) {
		this.subeler = subeler;
	}
	public String musteriBul() throws Exception{
		HesapDBOperator hesapOperator = new HesapDBOperator();
		setHesapEklenecekMusteriId(Integer.parseInt(getBulunacakMusteriNo()));
		islemHesap = hesapOperator.hesapBulWithMusteriID(Integer.parseInt(getBulunacakMusteriNo()));
		if(islemHesap.isEmpty()){
			MusteriDBOperator musteriOperator = new MusteriDBOperator();
			if(musteriOperator.musteriBulWithID(Integer.parseInt(bulunacakMusteriNo)) == null){
				//////////////**************GROWL
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Baþarýsýz", "Müþteri Bulunamadý."));		
				//////////////**************GROWL
				return null;
			}
		}
		else{
			setTableState(true);
		}
		SubeDBOperator subeOperator = new SubeDBOperator();
		subeler = subeOperator.getSube();
		setSubelerState(true);
		setMusteriNoState(false);
		return null;
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
		bulunacakMusteriNo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hesapEklenecekMusteriId");
		System.out.println("hesapEklenecekSubeId " + hesapEklenecekSubeId);
		
		hesapEklenecekSube = getSubeFromID(Integer.parseInt( hesapEklenecekSubeId ) );
		
		System.out.println("yonlendiriyorum");
		
		return ("p_banka_memuru_hesap_ekle_devam");
	}
	
	public String hesapEkle() throws Exception{
		String hesapEklenecekSubeId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hesapEklenecekSubeId");

		hesapEklenecekSube = getSubeFromID(Integer.parseInt(hesapEklenecekSubeId));
		
		System.out.println("hesapEklenecekSubeId-param : " + hesapEklenecekSubeId);
		System.out.println("hesapEklenecekSube: " + hesapEklenecekSube.getSubeAdi() + " " + hesapEklenecekSube.getSubeId());		
		
		HesapDBOperator hesapOperator = new HesapDBOperator();
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
		eklenecekHesap.setMusteri( musteriOperator.musteriBulWithID(hesapEklenecekMusteriId) );
		for(Hesap o:islemHesap){
			if(o.getHesapAdi().equals(eklenecekHesap.getHesapAdi()))
				return "_404.jsf";
		}
		HesapDBOperator hesapDBOperator = new HesapDBOperator();
		hesapDBOperator.hesapEkle(eklenecekHesap);
		
		//////////////**************GROWL
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Baþarýlý", "Yeni hesap baþarý ile eklendi"));		
		//////////////**************GROWL
		
		sifirla();
		return "p_banka_memuru_main";
	}
	public void sifirla() throws Exception{
		bulunacakMusteriNo = new String();
		hesapEklenecekSube = new Sube();
		eklenecekHesap = new Hesap();
		subeler = new ArrayList<Sube>();
		setMusteriNoState(true);
		setTableState(false);
		setSubelerState(false);
	}
	public String geri() throws Exception{
		sifirla();
		return "p_banka_memuru_hesap_ekle";
	}
}
package com.banka.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.banka.dbOperations.BankaMemuruDBOperator;
import com.banka.dbOperations.SubeDBOperator;
import com.banka.entity.BankaMemuru;
import com.banka.entity.Sube;

@ManagedBean
@RequestScoped
public class BankaMemuruEkleBean {
	
	private String eklenecekMemurAdi;
	private String eklenecekMemurTarihi;
	
	private String growlText;

	public void setEklenecekMemurTarihi(String eklenecekMemurTarihi) {
		this.eklenecekMemurTarihi = eklenecekMemurTarihi;
	}

	private List<BankaMemuru> bankaMemurlari = new ArrayList<BankaMemuru>();
	
	private BankaMemuru selectedBankaMemuru = new BankaMemuru();
	
	private BankaMemuru eklenecekBankaMemuru = new BankaMemuru();

	private List<Sube> subeler = new ArrayList<Sube>();
	
	private Sube selectedSube = new Sube();

	public BankaMemuruEkleBean() throws Exception{	
		BankaMemuruDBOperator bankaMemuruOperator = new BankaMemuruDBOperator();
		bankaMemurlari = bankaMemuruOperator.getBankaMemuru();
//		if(selectedBankaMemuru == null)
//			System.out.println("selectedbankaMemuru null");
//		else
//			System.out.println("selected: " + selectedBankaMemuru.getBankaMemuruId());
		
		SubeDBOperator subeOperator = new SubeDBOperator();
		subeler = subeOperator.getSube();
		
	}

	public List<BankaMemuru> getBankaMemurlari() throws Exception {
		return bankaMemurlari;
	}

	public void setBankaMemurlari(List<BankaMemuru> bankaMemurlari) {
		this.bankaMemurlari = bankaMemurlari;
	}
	
	public BankaMemuru getSelectedBankaMemuru() {
//		if(selectedBankaMemuru == null)
//			System.out.println("get: selectedbankaMemuru null");
//		else
//			System.out.println("get:" + selectedBankaMemuru.getBankaMemuruId() + " - " + selectedBankaMemuru.getBankaMemuruId() );
		
		return selectedBankaMemuru;
	}

	public void setSelectedBankaMemuru(BankaMemuru selectedBankaMemuru) {
		this.selectedBankaMemuru = selectedBankaMemuru;
//		System.out.println("set:" + selectedBankaMemuru.getBankaMemuruId() + " - " + selectedBankaMemuru.getBankaMemuruId() );
	}

	public BankaMemuru getEklenecekBankaMemuru() {
//		System.out.println("-------------------banka memurunu aliyorum");
		return eklenecekBankaMemuru;
	}

	public void setEklenecekBankaMemuru(BankaMemuru eklenecekBankaMemuru) {
		this.eklenecekBankaMemuru = eklenecekBankaMemuru;
	}
	
	public List<Sube> getSubeler() {
		return subeler;
	}

	public void setSubeler(List<Sube> subeler) {
		this.subeler = subeler;
	}
	
	public Sube getSelectedSube() {
		return selectedSube;
	}

	public void setSelectedSube(Sube selectedSube) {
		this.selectedSube = selectedSube;
	}
	
	public String getEklenecekMemurAdi() {
		return eklenecekMemurAdi;
	}

	public void setEklenecekMemurAdi(String eklenecekMemurAdi) {
		this.eklenecekMemurAdi = eklenecekMemurAdi;
	}

	public String getGrowlText() {
		return growlText;
	}

	public void setGrowlText(String growlText) {
		this.growlText = growlText;
	}

	public String getEklenecekMemurTarihi() {
		return eklenecekMemurTarihi;
	}
	
	public void yeniBankaMemuruEkle() throws Exception{
		
		System.out.println("\n\n--------yeniBankaMemuruEkle'deyim");
		
		BankaMemuruDBOperator bankaMemuruOperator = new BankaMemuruDBOperator();
		
		System.out.println("eklenecek memur bilgisi: " + eklenecekBankaMemuru.getBankaMemuruAdi() + " " + eklenecekBankaMemuru.getBankaMemuruBaslangicTarihi());
		//eklenecekBankaMemuru.setBankaMemuruBaslangicTarihi("03012011");
//		eklenecekBankaMemuru.setSube(selectedSube);/////////////burasý deðiþecek
//		System.out.println("sube0: " + ((Sube)(subeler.get(0))).getSubeAdi() + " " + ((Sube)(subeler.get(0))).getSubeId());
//		eklenecekBankaMemuru.setSube(subeler.get(0));
		
		
		selectedSube = getSubeFromID(selectedSube.getSubeId());
		eklenecekBankaMemuru.setSube(selectedSube);
		
		System.out.println( "selectedSube : " /*+ selectedSube.getSubeAdi()*/ + " " + selectedSube.getSubeId() );
		
//		boolean sonuc = bankaMemuruOperator.bankaMemuruEkle(eklenecekBankaMemuru);
		boolean sonuc = bankaMemuruOperator.bankaMemuruEkle_uyeAdiKontrol(eklenecekBankaMemuru);
		
		bankaMemurlari = bankaMemuruOperator.getBankaMemuru();
		
		if(sonuc){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Baþarýlý", "Yeni banka memuru baþarýyla eklendi"));		
			//////////////**************GROWL
		}
		else{
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Banka memuru sistemde mevcut. Üye adý/Mail kontrol ediniz"));		
			//////////////**************GROWL
		}
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

	public void deneme(){
		System.out.println("\n\nDENEMEDEYIM\n\n");
	}
	
}
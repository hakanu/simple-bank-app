package com.banka.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.banka.dbOperations.BankaMemuruDBOperator;
import com.banka.dbOperations.SubeDBOperator;
import com.banka.entity.BankaMemuru;
import com.banka.entity.Sube;

@ManagedBean
@SessionScoped
public class BankaMemuruGunleBean {
	
	private int gunlenecekBankaMemuruId;
	
	private BankaMemuru gunlenecekBankaMemuru = new BankaMemuru();

	private List<BankaMemuru> bankaMemurlari = new ArrayList<BankaMemuru>();
	
	private List<Sube> subeler; 

	private BankaMemuru selectedBankaMemuru = new BankaMemuru();

	private Sube selectedSube = new Sube();
	
	/**
	 * CONSTRUCTOR
	 * @throws Exception
	 */
	public BankaMemuruGunleBean () throws Exception{	
		BankaMemuruDBOperator bankaMemuruOperator = new BankaMemuruDBOperator();
		bankaMemurlari = bankaMemuruOperator.getBankaMemuru();
		SubeDBOperator subeDBOperator = new SubeDBOperator();
		subeler = subeDBOperator.getSube();
	}

	/**
	 * GETTERS & SETTERS
	 * @return
	 * @throws Exception
	 */
	public List<BankaMemuru> getBankaMemurlari() throws Exception {
		return bankaMemurlari;
	}

	public void setBankaMemurlari(List<BankaMemuru> bankaMemurlari) {
		this.bankaMemurlari = bankaMemurlari;
	}
	
	public BankaMemuru getSelectedBankaMemuru() {
		return selectedBankaMemuru;
	}

	public void setSelectedBankaMemuru(BankaMemuru selectedBankaMemuru) {
		this.selectedBankaMemuru = selectedBankaMemuru;
	}
	
	public int getGunlenecekBankaMemuruId() {
		return gunlenecekBankaMemuruId;
	}

	public void setGunlenecekBankaMemuruId(int gunlenecekBankaMemuruId) {
		this.gunlenecekBankaMemuruId = gunlenecekBankaMemuruId;
	}

	public BankaMemuru getGunlenecekBankaMemuru() {
		return gunlenecekBankaMemuru;
	}

	public void setGunlenecekBankaMemuru(BankaMemuru gunlenecekBankaMemuru) {
		this.gunlenecekBankaMemuru = gunlenecekBankaMemuru;
	}
		
	public Sube getSelectedSube() {
		return selectedSube;
	}

	public void setSelectedSube(Sube selectedSube) {
		this.selectedSube = selectedSube;
	}

	public List<Sube> getSubeler() {
		return subeler;
	}

	public void setSubeler(List<Sube> subeler) {
		this.subeler = subeler;
	}
	
	/**
	 * PROCESS METHODS
	 * @return
	 * @throws Exception
	 */
	public String bankaMemuruGunle() throws Exception{
		System.out.println("memur günledeyim");
		String gunlenecekId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("gunlenecekBankaMemuruId");
		System.out.println("gunlenecekId " + gunlenecekId);
		
		System.out.println("yeni memur adi : " + gunlenecekBankaMemuru.getBankaMemuruAdi());
		System.out.println("yeni memur id : " + gunlenecekBankaMemuru.getBankaMemuruId());
		System.out.println("yeni memur tarih : " + gunlenecekBankaMemuru.getBankaMemuruBaslangicTarihi());
//		System.out.println("yeni memur sube: " + gunlenecekBankaMemuru.getSube().getSubeAdi() + " " + gunlenecekBankaMemuru.getSube().getSubeId() );
		
		gunlenecekBankaMemuru.setSube(getSubeFromID(selectedSube.getSubeId()));
		System.out.println("yeni memur sube: " + gunlenecekBankaMemuru.getSube().getSubeAdi() + " " + gunlenecekBankaMemuru.getSube().getSubeId() );
		
		BankaMemuruDBOperator bankaMemuruDBOperator = new BankaMemuruDBOperator();		
		bankaMemuruDBOperator.bankaMemuruGunleWithID( gunlenecekBankaMemuru, Integer.parseInt(gunlenecekId) );
		
		bankaMemurlari = bankaMemuruDBOperator.getBankaMemuru();
		System.out.println("tekrar yönleniyorum");
		
		//////////////**************GROWL
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Baþarýlý", "Banka memuru baþarý ile günlendi"));		
		//////////////**************GROWL
		
		return ("p_yonetici_memur_gunle");
	}
	
	public String yonlendir(){
		String gunlenecekId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("gunlenecekBankaMemuruId");
		System.out.println("gunlenecekId " + gunlenecekId);
		
		gunlenecekBankaMemuru = getBankaMemuruFromID(Integer.parseInt( gunlenecekId ) );
		System.out.println("yeni memur sube: " + gunlenecekBankaMemuru.getSube().getSubeAdi() + " " + gunlenecekBankaMemuru.getSube().getSubeId() );
		
		System.out.println("yonlendiriyorum");
		return ("p_yonetici_memur_gunle_ayrinti");
	}
	
	private BankaMemuru getBankaMemuruFromID(int par_nBankaMemuruID){
		BankaMemuru sonuc = null;
		
		System.out.println( "BankaMemurlarýný boyu: " + bankaMemurlari.size() );
		for (BankaMemuru s : bankaMemurlari) {
			if(s.getBankaMemuruId() == par_nBankaMemuruID){
				sonuc = s;
				break;
			}
		}
		return sonuc;
	}

	private Sube getSubeFromID(int par_nSubeID) throws Exception{
		Sube sonuc = null;
		
		SubeDBOperator subeDBOperator = new SubeDBOperator();
		subeler = subeDBOperator.getSube();
			
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
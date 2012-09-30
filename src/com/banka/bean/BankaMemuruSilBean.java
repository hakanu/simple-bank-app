package com.banka.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.banka.dbOperations.BankaMemuruDBOperator;
import com.banka.entity.BankaMemuru;

@ManagedBean
@RequestScoped
public class BankaMemuruSilBean {
	
	private int silinecekBankaMemuruId;
	
	private BankaMemuru silinecekBankaMemuru = new BankaMemuru();

	private List<BankaMemuru> bankaMemurlari = new ArrayList<BankaMemuru>();
	
	private BankaMemuru selectedBankaMemuru = new BankaMemuru();

	public BankaMemuruSilBean() throws Exception{	
		BankaMemuruDBOperator bankaMemuruOperator = new BankaMemuruDBOperator();
		bankaMemurlari = bankaMemuruOperator.getBankaMemuru();
	}

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
	
	public int getSilinecekBankaMemuruId() {
		return silinecekBankaMemuruId;
	}

	public void setSilinecekBankaMemuruId(int silinecekBankaMemuruId) {
		this.silinecekBankaMemuruId = silinecekBankaMemuruId;
	}

	public BankaMemuru getSilinecekBankaMemuru() {
		return silinecekBankaMemuru;
	}

	public void setSilinecekBankaMemuru(BankaMemuru silinecekBankaMemuru) {
		this.silinecekBankaMemuru = silinecekBankaMemuru;
	}

	public String yonlendir(){
		
		String silinecekId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("silinecekBankaMemuruId");
		
		silinecekBankaMemuru = getBankaMemuruFromID(Integer.parseInt( silinecekId ) );
		
		System.out.println("yonlendiriyorum");
		return ("p_yonetici_memur_sil_onay");
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
	
	public void bankaMemuruSil() throws Exception{
		System.out.println("memur sildeyim");
		String silinecekId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("silinecekBankaMemuruId");
		System.out.println("silinecekId " + silinecekId);
		
		if(!silinecekId.equals("0")){
			BankaMemuruDBOperator bankaMemuruDBOperator = new BankaMemuruDBOperator();
			bankaMemuruDBOperator.bankaMemuruSilWithID( Integer.parseInt(silinecekId) );
			
			bankaMemurlari = bankaMemuruDBOperator.getBankaMemuru();
			System.out.println("tekrar yönleniyorum");
			
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Baþarýlý", "Banka memuru baþarýyla sistemden silindi"));		
			//////////////**************GROWL
		}
		else{
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Olmayan bir banka memuru üzerinde iþlem"));		
			//////////////**************GROWL
		}
//		return ("p_yonetici_memur_sil");
	}
	
	public void deneme(){
		System.out.println("\n\nDENEMEDEYIM\n\n");
	}
	
}
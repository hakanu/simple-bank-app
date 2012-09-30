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

@ManagedBean
@SessionScoped
//@RequestScoped
public class BankaMemuruMusteriSilBean {

	private List<Musteri> musteriler;
	private Musteri silinecekMusteri;
	private boolean renderState;
	private boolean searchPanelState;
	public BankaMemuruMusteriSilBean() {
		setMusteriler(new ArrayList<Musteri>());
		setSilinecekMusteri(new Musteri());
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
	

	public Musteri getSilinecekMusteri() {
		return silinecekMusteri;
	}

	public void setSilinecekMusteri(Musteri silinecekMusteri) {
		this.silinecekMusteri = silinecekMusteri;
	}
	
	public void musterileriBul() throws Exception{
		MusteriDBOperator musteriOperator = new MusteriDBOperator();
		musteriler = musteriOperator.musteriBul(silinecekMusteri);
		setRenderState(true);
		setSearchPanelState(false);
		//return ("p_banka_memuru_musteri_sil_listesi");
	}
	
	public String musteriSil() throws Exception{
		System.out.println("müþteri sildeyim");
		//System.out.println( "selected " + selectedMusteri.getAdi() + " " + selectedMusteri.getSoyadi() );
		String silinecekId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("silinecekMusteri");
		System.out.println("silinecekId " + silinecekId);
		
		//Sube silinecekSube = getSubeFromID( Integer.parseInt(silinecekId) );
		
		MusteriDBOperator musteriDBOperator = new MusteriDBOperator();
		boolean silmeSonucu = musteriDBOperator.musteriSilWithID( Integer.parseInt(silinecekId) );
		silinecekMusteri = new Musteri();
		musterileriBul();
		System.out.println("tekrar yönleniyorum");
		
		if(silmeSonucu){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Baþarýlý", "Müþteri baþarý ile sistemden kaldýrýldý"));		
			//////////////**************GROWL
		}
		else{
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Hesabý olan müþteriler silinemez."));		
			//////////////**************GROWL
		}
			
		setRenderState(false);
		setSearchPanelState(true);
		
		
		
		if(LoggedInCheck.isYoneticiLoggedIn)
			return ("p_yonetici_main");
		else
			return ("p_banka_memuru_main");
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
		String silinecekId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("silinecekMusteriId");
		System.out.println("silinecekId " + silinecekId);
		
		silinecekMusteri = getMusteriFromID(Integer.parseInt( silinecekId ) );
		
		
		
		System.out.println("yonlendiriyorum");
		
		if(LoggedInCheck.isYoneticiLoggedIn)
			return ("p_yonetici_musteri_sil_onay");
		else
			return ("p_banka_memuru_musteri_sil_onay");
//		return ("p_banka_memuru_musteri_sil_onay");
	}	
	public void yenidenAra() throws Exception{
		Musteri musteriCheck = new Musteri();
		musteriCheck.setUyeAdi("");
		musteriCheck.setSifre("");
		musteriCheck.setAdi("");
		musteriCheck.setSoyadi("");
		musteriCheck.setMail("");
		musteriCheck.setWeb("");
		setSilinecekMusteri(musteriCheck);
		setRenderState(false);
		setSearchPanelState(true);
	}
}

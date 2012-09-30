package com.banka.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.banka.dbOperations.SubeDBOperator;
import com.banka.entity.Sube;

@ManagedBean
//@SessionScoped
@RequestScoped
public class SubeSilBean {
	private List<Sube> subeler = new ArrayList<Sube>();
	
	private Sube eklenecekSube = new Sube();
	
	private Sube selectedSube = new Sube();
	
	private List<Sube> selectedSubes = new ArrayList<Sube>();
	
	private boolean isSubeSelected;
	
	private int silinecekId;
	
	private Sube silinecekSube = new Sube();

	public SubeSilBean() throws Exception{	
		SubeDBOperator subeOperator = new SubeDBOperator();
		subeler = subeOperator.getSube();
	}
	
	public List<Sube> getSubeler() throws Exception {
		return subeler;
	}

	public void setSubeler(List<Sube> subeler) {
		this.subeler = subeler;
	}
	
	public Sube getEklenecekSube() {
		return eklenecekSube;
	}

	public void setEklenecekSube(Sube eklenecekSube) {
		this.eklenecekSube = eklenecekSube;
	}
	
	public Sube getSelectedSube() {
		return selectedSube;
	}

	public void setSelectedSube(Sube selectedSube) {
		this.selectedSube = selectedSube;
	}
	
	public List<Sube> getSelectedSubes() {
		return selectedSubes;
	}

	public void setSelectedSubes(List<Sube> selectedSubes) {
		this.selectedSubes = selectedSubes;
	}
	
	public boolean isSubeSelected() {
		return isSubeSelected;
	}

	public void setSubeSelected(boolean isSubeSelected) {
		this.isSubeSelected = isSubeSelected;
	}
	
	public int getSilinecekId() {
		return silinecekId;
	}

	public void setSilinecekId(int silinecekId) {
		this.silinecekId = silinecekId;
	}
	
	public Sube getSilinecekSube() {
		return silinecekSube;
	}

	public void setSilinecekSube(Sube silinecekSube) {
		this.silinecekSube = silinecekSube;
	}
	
	public void yeniSubeEkle(){
		SubeDBOperator subeOperator = new SubeDBOperator();
		
		eklenecekSube.setSubeAssests(50000);
		subeOperator.subeEkle(eklenecekSube);
	}
	
	public String subeSil() throws Exception{
		System.out.println("þube sildeyim");
		System.out.println( "selected " + selectedSube.getSubeAdi() + " " + selectedSube.getSubeId() );
		String silinecekId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("silinecekSubeId");
		System.out.println("silinecekId " + silinecekId);
		
		//Sube silinecekSube = getSubeFromID( Integer.parseInt(silinecekId) );
		
		SubeDBOperator subeDBOperator = new SubeDBOperator();
		boolean silmeSonucu = subeDBOperator.subeSilWithID( Integer.parseInt(silinecekId) );
		
		if(silmeSonucu){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Baþarýlý", "Þube baþarýyla sistemden kaldýrýldý"));	
			//////////////**************GROWL
		}
		else{
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Ýçinde hesap veya banka memuru bulunan þubeler silinemez"));	
			//////////////**************GROWL
		}
		subeler = subeDBOperator.getSube();
		System.out.println("tekrar yönleniyorum");
		
		
		return ("p_yonetici_sube_sil");
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
		String silinecekId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("silinecekSubeId");
		System.out.println("silinecekId " + silinecekId);
		
		silinecekSube = getSubeFromID(Integer.parseInt( silinecekId ) );
		
		System.out.println("yonlendiriyorum");
		return ("p_yonetici_sube_sil_onay");
	}
	
}

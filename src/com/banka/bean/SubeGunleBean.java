package com.banka.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.banka.dbOperations.SubeDBOperator;
import com.banka.entity.Sube;

@ManagedBean
public class SubeGunleBean {
	private List<Sube> subeler = new ArrayList<Sube>();
	
	private Sube gunlenecekSube = new Sube();

	private Sube selectedSube = new Sube();

	public SubeGunleBean() throws Exception{	
		SubeDBOperator subeOperator = new SubeDBOperator();
		subeler = subeOperator.getSube();
	}
	
	public List<Sube> getSubeler() {
		return subeler;
	}

	public void setSubeler(List<Sube> subeler) {
		this.subeler = subeler;
	}

	public Sube getGunlenecekSube() {
		return gunlenecekSube;
	}

	public void setGunlenecekSube(Sube gunlenecekSube) {
		this.gunlenecekSube = gunlenecekSube;
	}

	public Sube getSelectedSube() {
		return selectedSube;
	}

	public void setSelectedSube(Sube selectedSube) {
		this.selectedSube = selectedSube;
	}
	
	public String subeGunle() throws Exception{
		System.out.println("þube sildeyim");
		System.out.println( "selected " + selectedSube.getSubeAdi() + " " + selectedSube.getSubeId() );
		String gunlenecekId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("gunlenecekSubeId");
		System.out.println("gunlenecekId " + gunlenecekId);
		
		System.out.println("yeni sube adi : " + gunlenecekSube.getSubeAdi());
		System.out.println("yeni sube sehri : " + gunlenecekSube.getSubeSehir());
		System.out.println("yeni sube assets : " + gunlenecekSube.getSubeAssests());
		
		//Sube silinecekSube = getSubeFromID( Integer.parseInt(silinecekId) );
		
		SubeDBOperator subeDBOperator = new SubeDBOperator();
		subeDBOperator.subeGunleWithID( gunlenecekSube, Integer.parseInt(gunlenecekId) );
		
		subeler = subeDBOperator.getSube();
		System.out.println("tekrar yönleniyorum");
		
		//////////////**************GROWL
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Baþarýlý", "Þube baþarýyla günlendi"));		
		//////////////**************GROWL
		
		return ("p_yonetici_sube_gunle");
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
		String gunlenecekId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("gunlenecekSubeId");
		System.out.println("gunlenecekId " + gunlenecekId);
		
		gunlenecekSube = getSubeFromID(Integer.parseInt( gunlenecekId ) );
		
		System.out.println("yonlendiriyorum");
		return ("p_yonetici_sube_gunle_ayrinti");
	}
}

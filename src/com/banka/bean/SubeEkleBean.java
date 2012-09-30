package com.banka.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.banka.dbOperations.SubeDBOperator;
import com.banka.entity.Sube;

@ManagedBean
@RequestScoped
public class SubeEkleBean {
	private List<Sube> subeler = new ArrayList<Sube>();
	
	private Sube eklenecekSube = new Sube();
	
	private Sube selectedSube = new Sube();

	public SubeEkleBean() throws Exception{	
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
	
	public void yeniSubeEkle(){
		SubeDBOperator subeOperator = new SubeDBOperator();
		
		eklenecekSube.setSubeAssests(50000);
		subeOperator.subeEkle(eklenecekSube);
		//////////////**************GROWL
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Baþarýlý", "Yeni þube baþarýyla eklendi"));		
		//////////////**************GROWL
	}
	
	public void deneme(){
		System.out.println("\n\nDENEMEDEYIM\n\n");
	}
	
}

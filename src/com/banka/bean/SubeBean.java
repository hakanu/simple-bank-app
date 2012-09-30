package com.banka.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.banka.dbOperations.SubeDBOperator;
import com.banka.entity.Sube;

@ManagedBean
@RequestScoped
public class SubeBean {
	private List<Sube> subeler = new ArrayList<Sube>();
	
	private Sube selectedSube;
	
	private Sube eklenecekSube = new Sube();

	public SubeBean() throws Exception{	
		SubeDBOperator subeOperator = new SubeDBOperator();
		subeler = subeOperator.getSube();
		if(selectedSube == null)
			System.out.println("selectedsube null");
		else
			System.out.println("selected: " + selectedSube.getSubeId());
	}
	
	public List<Sube> getSubeler() throws Exception {
		return subeler;
	}

	public void setSubeler(List<Sube> subeler) {
		this.subeler = subeler;
	}
	
	public Sube getSelectedSube() {
		if(selectedSube == null)
			System.out.println("get: selectedsube null");
		else
			System.out.println("get:" + selectedSube.getSubeId() + " - " + selectedSube.getSubeId() );
		
		return selectedSube;
	}

	public void setSelectedSube(Sube selectedSube) {
		this.selectedSube = selectedSube;
		System.out.println("set:" + selectedSube.getSubeId() + " - " + selectedSube.getSubeId() );
	}

	public Sube getEklenecekSube() {
		return eklenecekSube;
	}

	public void setEklenecekSube(Sube eklenecekSube) {
		this.eklenecekSube = eklenecekSube;
	}
	
	
}
package com.banka.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.banka.dbOperations.BankaMemuruDBOperator;
import com.banka.entity.BankaMemuru;

@ManagedBean
@RequestScoped
public class BankaMemuruListBean {
	private List<BankaMemuru> bankaMemurlari = new ArrayList<BankaMemuru>();
	
	private BankaMemuru selectedBankaMemuru;
	
	private BankaMemuru eklenecekBankaMemuru = new BankaMemuru();

	public BankaMemuruListBean() throws Exception{	
		BankaMemuruDBOperator bankaMemuruOperator = new BankaMemuruDBOperator();
		bankaMemurlari = bankaMemuruOperator.getBankaMemuru();
		if(selectedBankaMemuru == null)
			System.out.println("selectedbankaMemuru null");
		else
			System.out.println("selected: " + selectedBankaMemuru.getBankaMemuruId());
	}
	
	public List<BankaMemuru> getBankaMemurlari() throws Exception {
		return bankaMemurlari;
	}

	public void setBankaMemurlari(List<BankaMemuru> bankaMemurlari) {
		this.bankaMemurlari = bankaMemurlari;
	}
	
	public BankaMemuru getSelectedBankaMemuru() {
		if(selectedBankaMemuru == null)
			System.out.println("get: selectedbankaMemuru null");
		else
			System.out.println("get:" + selectedBankaMemuru.getBankaMemuruId() + " - " + selectedBankaMemuru.getBankaMemuruId() );
		
		return selectedBankaMemuru;
	}

	public void setSelectedBankaMemuru(BankaMemuru selectedBankaMemuru) {
		this.selectedBankaMemuru = selectedBankaMemuru;
		System.out.println("set:" + selectedBankaMemuru.getBankaMemuruId() + " - " + selectedBankaMemuru.getBankaMemuruId() );
	}

	public BankaMemuru getEklenecekBankaMemuru() {
		return eklenecekBankaMemuru;
	}

	public void setEklenecekBankaMemuru(BankaMemuru eklenecekBankaMemuru) {
		this.eklenecekBankaMemuru = eklenecekBankaMemuru;
	}
	
	
	
	
}
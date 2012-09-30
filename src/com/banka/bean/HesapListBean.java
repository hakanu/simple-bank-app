package com.banka.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.banka.accessControl.LoggedInCheck;
import com.banka.dbOperations.HesapDBOperator;
import com.banka.entity.Hesap;

@ManagedBean
@RequestScoped
public class HesapListBean {
	private List<Hesap> hesaplar = new ArrayList<Hesap>();
	
	private Hesap selectedHesap;
	
	private Hesap eklenecekHesap = new Hesap();

	public HesapListBean() throws Exception{	
		HesapDBOperator hesapOperator = new HesapDBOperator();
		
		
//		hesaplar = hesapOperator.getHesap();/////////bura açýlabilir
		
		System.out.println("sistemdeki musteri : " + LoggedInCheck.currentMusteri.getAdi() + " " + LoggedInCheck.currentMusteri.getId() );
//		System.out.println("sistemdeki musterinin hesaplari: " + LoggedInCheck.currentMusteri.getHesaplar().get(0).getHesapAdi() + " ");
//		
//		hesaplar = hesapOperator.getMusteriHesap(LoggedInCheck.currentMusteri);
		
//		if(hesaplar == null){
//			hesaplar = new ArrayList<Hesap>();
//		}
		
		
		hesaplar = hesapOperator.getMusteriHesapKotu(LoggedInCheck.currentMusteri);
		
		
//		if(selectedHesap == null)
//			System.out.println("selectedsube null");
//		else
//			System.out.println("selected: " + selectedHesap.getHesapno());
	}
	
	public List<Hesap> getHesaplar() throws Exception {
		return hesaplar;
	}

	public void setHesaplar(List<Hesap> hesaplar) {
		this.hesaplar = hesaplar;
	}
	
	public Hesap getSelectedHesap() {
//		if(selectedHesap == null)
//			System.out.println("get: selectedsube null");
//		else
//			System.out.println("get:" + selectedHesap.getHesapno() + " - " + selectedHesap.getHesapAdi() );
//		
		return selectedHesap;
	}

	public void setSelectedHesap(Hesap selectedHesap) {
		this.selectedHesap = selectedHesap;
//		System.out.println("set:" + selectedHesap.getHesapno() + " - " + selectedHesap.getHesapAdi() );
	}

	public Hesap getEklenecekHesap() {
		return eklenecekHesap;
	}

	public void setEklenecekHesap(Hesap eklenecekHesap) {
		this.eklenecekHesap = eklenecekHesap;
	}
	
	
}
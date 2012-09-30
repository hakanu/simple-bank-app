package com.banka.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.banka.dbOperations.IslemUcretiDBOperator;
import com.banka.entity.IslemUcreti;

@ManagedBean
@RequestScoped
public class YoneticiIslemUcretiBean {
	private IslemUcreti islemUcreti = new IslemUcreti();
	private int islemUcretiId;
	private double havaleIslemUcreti;
	private double eftIslemUcreti;
	private double paraCekmeIslemUcreti;
	private double paraYatirmaIslemUcreti;
	
	public YoneticiIslemUcretiBean(){
		System.out.println("YoneticiIslemUcretiBean yarattým");
		IslemUcretiDBOperator islemUcretiOperator = new IslemUcretiDBOperator();
		islemUcreti = islemUcretiOperator.getIslemUcretleri();
	}
	
	public int getIslemUcretiId() {
		return islemUcretiId;
	}
	public void setIslemUcretiId(int islemUcretiId) {
		this.islemUcretiId = islemUcretiId;
	}
	public double getHavaleIslemUcreti() {
		return havaleIslemUcreti;
	}
	public void setHavaleIslemUcreti(double havaleIslemUcreti) {
		this.havaleIslemUcreti = havaleIslemUcreti;
	}
	public double getEftIslemUcreti() {
		return eftIslemUcreti;
	}
	public void setEftIslemUcreti(double eftIslemUcreti) {
		this.eftIslemUcreti = eftIslemUcreti;
	}
	public double getParaCekmeIslemUcreti() {
		return paraCekmeIslemUcreti;
	}
	public void setParaCekmeIslemUcreti(double paraCekmeIslemUcreti) {
		this.paraCekmeIslemUcreti = paraCekmeIslemUcreti;
	}
	public double getParaYatirmaIslemUcreti() {
		return paraYatirmaIslemUcreti;
	}
	public void setParaYatirmaIslemUcreti(double paraYatirmaIslemUcreti) {
		this.paraYatirmaIslemUcreti = paraYatirmaIslemUcreti;
	}
	
	public IslemUcreti getIslemUcreti() {
		return islemUcreti;
	}

	public void setIslemUcreti(IslemUcreti islemUcreti) {
		this.islemUcreti = islemUcreti;
	}

	public String islemUcretiGunle(){
		System.out.println("islemUcretiGunle baþladý ");
		
		System.out.println("havaleIslemUcreti		: " + islemUcreti.getHavaleIslemUcreti());
		System.out.println("eftIslemUcreti			: " + islemUcreti.getEftIslemUcreti());
		System.out.println("paraCekmeIslemUcreti	: " + islemUcreti.getParaCekmeIslemUcreti());
		System.out.println("paraYatirmaIslemUcreti	: " + islemUcreti.getParaYatirmaIslemUcreti());
		
		System.out.println("islemUcretiGunle bitti ");
		
		IslemUcretiDBOperator islemUcretiOperator = new IslemUcretiDBOperator();
		boolean sonuc = islemUcretiOperator.islemUcretiGunleWithID(islemUcreti);
		
		if(sonuc){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Baþarýlý", "Ýþlem ücret(ler)i baþarýyla günlendi"));		
			//////////////**************GROWL
		}
		else{
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Baþarýlý", "Ýþlem ücretleri günleme baþarýsýz"));		
			//////////////**************GROWL
		}
		
		return ("p_yonetici_main");
	}
	
}

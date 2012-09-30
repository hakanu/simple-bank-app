package com.banka.bean;


import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.banka.dbOperations.HesapDBOperator;
import com.banka.dbOperations.IslemDBOperator;
import com.banka.dbOperations.IslemUcretiDBOperator;
import com.banka.entity.Hesap;
import com.banka.entity.Islem;

@ManagedBean
//@RequestScoped
@SessionScoped
public class BankaMemuruParaCekmeBean {

	private String bulunacakHesapNo;
	private Hesap islemHesap;
	private boolean renderState;
	private int cekilecekMiktar;
	
	private double paraCekmeIslemUcreti;

	public BankaMemuruParaCekmeBean() {
		setBulunacakHesapNo(new String());
		setIslemHesap(new Hesap());
		setRenderState(false);
		cekilecekMiktar = 0;
		IslemUcretiDBOperator i = new IslemUcretiDBOperator();
		paraCekmeIslemUcreti = i.getIslemUcretleri().getParaCekmeIslemUcreti();
	}

	public String getBulunacakHesapNo() {
		return bulunacakHesapNo;
	}

	public void setBulunacakHesapNo(String bulunacakHesapNo) {
		this.bulunacakHesapNo = bulunacakHesapNo;
	}


	public int getCekilecekMiktar() {
		return cekilecekMiktar;
	}

	public void setCekilecekMiktar(int cekilecekMiktar) {
		this.cekilecekMiktar = cekilecekMiktar;
	}

	public Hesap getIslemHesap() {
		return islemHesap;
	}

	public void setIslemHesap(Hesap islemHesap) {
		this.islemHesap = islemHesap;
	}

	public boolean isRenderState() {
		return renderState;
	}

	public void setRenderState(boolean renderState) {
		this.renderState = renderState;
	}

	public double getParaCekmeIslemUcreti() {
		return paraCekmeIslemUcreti;
	}

	public void setParaCekmeIslemUcreti(double paraCekmeIslemUcreti) {
		this.paraCekmeIslemUcreti = paraCekmeIslemUcreti;
	}

	public String HesapBul() throws Exception{	
		HesapDBOperator hesapOperator = new HesapDBOperator();
		islemHesap = hesapOperator.hesapBulWithIDHy(Integer.parseInt(bulunacakHesapNo));
		if(islemHesap == null){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Baþarýsýz", "Hesap Bulunamadý. Yeniden deneyin."));		
			//////////////**************GROWL
			setRenderState(false);
			return null;
		}
		setRenderState(true);
		return null;
	}

	public String cekmeGerceklestir(){
		paraCekmeIslemUcreti = paraCekmeIslemUcretiDB();
		
		HesapDBOperator hesapOperator = new HesapDBOperator();
		String gunlenecekNo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("gunlenecekHesapNo");
		if(hesapOperator.paraCek(getCekilecekMiktar(), Integer.parseInt(gunlenecekNo))){
			
			//////////////////////////////////////////////HAKAN
			System.out.println("iþlemlere ekliyorum");
			IslemDBOperator islemOperator = new IslemDBOperator();
			Islem eklenecekIslem = new Islem();
			eklenecekIslem.setCekilenMiktar(cekilecekMiktar);
			eklenecekIslem.setIslemTarihi( new Date().toString() );
			eklenecekIslem.setIslemHesabi(islemHesap.getHesapAdi());
			eklenecekIslem.setIslemHesapno(islemHesap.getHesapno());
			eklenecekIslem.setHesapBakiye(islemHesap.getHesapBalance());
			eklenecekIslem.setMusteri(islemHesap.getMusteri());
//			eklenecekIslem.setIslemHesabi( islemHesap );
			islemOperator.islemEkle(eklenecekIslem);
			//////////////////////////////////////////////HAKAN
			
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Baþarýlý", "Para çekme iþlemi baþarý ile tamamlandý"));		
			//////////////**************GROWL
			
			sifirla();
			return "p_banka_memuru_main.jsf";
		}
//		sifirla();
		if(getCekilecekMiktar() < 0){

			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Geçersiz miktar. Baþka bir miktar deneyin."));		
			//////////////**************GROWL
			return null;
		}
		//////////////**************GROWL
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Hata", "Bakiye yetersiz."));		
		//////////////**************GROWL
		
		return null;
		
	}
	public void sifirla(){
		setBulunacakHesapNo(new String());
		setIslemHesap(new Hesap());
		setRenderState(false);
		cekilecekMiktar = 0;
	}

	private double paraCekmeIslemUcretiDB(){
		IslemUcretiDBOperator i = new IslemUcretiDBOperator();
		return i.getIslemUcretleri().getParaCekmeIslemUcreti();
	}
	
	
}
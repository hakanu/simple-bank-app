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
public class BankaMemuruParaYatirmaBean {

	private String bulunacakHesapNo;
	private Hesap islemHesap;
	private boolean renderState;
	private int yatirilacakMiktar;

	private double paraYatirmaIslemUcreti;
	
	public BankaMemuruParaYatirmaBean() {
		setBulunacakHesapNo(new String());
		setIslemHesap(new Hesap());
		setRenderState(false);
		yatirilacakMiktar = 0;
		IslemUcretiDBOperator i = new IslemUcretiDBOperator();
		paraYatirmaIslemUcreti = i.getIslemUcretleri().getParaYatirmaIslemUcreti();
	}

	public String getBulunacakHesapNo() {
		return bulunacakHesapNo;
	}

	public void setBulunacakHesapNo(String bulunacakHesapNo) {
		this.bulunacakHesapNo = bulunacakHesapNo;
	}


	public int getYatirilacakMiktar() {
		return yatirilacakMiktar;
	}

	public void setYatirilacakMiktar(int yatirilacakMiktar) {
		this.yatirilacakMiktar = yatirilacakMiktar;
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

	public double getParaYatirmaIslemUcreti() {
		return paraYatirmaIslemUcreti;
	}

	public void setParaYatirmaIslemUcreti(double paraYatirmaIslemUcreti) {
		this.paraYatirmaIslemUcreti = paraYatirmaIslemUcreti;
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

	public String yatirmaGerceklestir(){
		paraYatirmaIslemUcreti = paraYatirmaIslemUcretiDB();
		
		HesapDBOperator hesapOperator = new HesapDBOperator();
		String gunlenecekNo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("gunlenecekHesapNo");
		if(hesapOperator.paraYatir(getYatirilacakMiktar(),Integer.parseInt(gunlenecekNo))){
			
			//////////////////////////////////////////////HAKAN
			System.out.println("iþlemlere ekliyorum");
			IslemDBOperator islemOperator = new IslemDBOperator();
			Islem eklenecekIslem = new Islem();
			eklenecekIslem.setYatanMiktar(yatirilacakMiktar);
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
			context.addMessage(null, new FacesMessage("Baþarýlý", "Para yatýrma iþlemi baþarý ile tamamlandý"));		
			//////////////**************GROWL
			
			sifirla();
			return "p_banka_memuru_main.jsf";
		}
		sifirla();
		
		//////////////**************GROWL
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Hata", "Para yatýrma iþlemi sýrasýnda hata"));		
		//////////////**************GROWL
		
		return "p_banka_memuru_main.jsf";
	}
	public void sifirla(){
		setBulunacakHesapNo(new String());
		setIslemHesap(new Hesap());
		setRenderState(false);
		yatirilacakMiktar = 0;
	}

	private double paraYatirmaIslemUcretiDB(){
		IslemUcretiDBOperator i = new IslemUcretiDBOperator();
		return i.getIslemUcretleri().getParaYatirmaIslemUcreti();
	}
	
}
package com.banka.bean;


import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.banka.accessControl.LoggedInCheck;
import com.banka.dbOperations.HesapDBOperator;
import com.banka.dbOperations.IslemDBOperator;
import com.banka.entity.Hesap;
import com.banka.entity.Islem;

@ManagedBean
//@RequestScoped
@SessionScoped
public class BankaMemuruHesapSilBean {

	private String bulunacakHesapNo;
	private Hesap silinecekHesap;
	private boolean hesapNoRender;
	private boolean detailRender;


	public boolean isHesapNoRender() {
		return hesapNoRender;
	}

	public void setHesapNoRender(boolean hesapNoRender) {
		this.hesapNoRender = hesapNoRender;
	}

	public boolean isDetailRender() {
		return detailRender;
	}

	public void setDetailRender(boolean detailRender) {
		this.detailRender = detailRender;
	}

	public BankaMemuruHesapSilBean() {
		setBulunacakHesapNo(new String());
		setSilinecekHesap(new Hesap());
		setHesapNoRender(true);
		setDetailRender(false);
	}

	public String getBulunacakHesapNo() {
		return bulunacakHesapNo;
	}

	public void setBulunacakHesapNo(String bulunacakHesapNo) {
		this.bulunacakHesapNo = bulunacakHesapNo;
	}



	public Hesap getSilinecekHesap() {
		return silinecekHesap;
	}

	public void setSilinecekHesap(Hesap silinecekHesap) {
		this.silinecekHesap = silinecekHesap;
	}

	public String HesapBul() throws Exception{	
		HesapDBOperator hesapOperator = new HesapDBOperator();
		setSilinecekHesap(hesapOperator.hesapBulWithIDHy(Integer.parseInt(bulunacakHesapNo)));
		if(getSilinecekHesap() == null){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Baþarýsýz", "Hesap Bulunamadý. Yeniden deneyin."));		
			//////////////**************GROWL
			return null;
		}
		setHesapNoRender(false);
		setDetailRender(true);
		return null;
	}
	public String hesapSil() throws Exception{
		bulunacakHesapNo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("silinecekHesapId");

		HesapBul();
		
		System.out.println( "silinecekHesapId-param : " + FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("silinecekHesapId") );
		System.out.println( "silinecekHesap: " + silinecekHesap.getHesapAdi() + " " + silinecekHesap.getHesapno() );		
		
		HesapDBOperator hesapOperator = new HesapDBOperator();
		
		if(!hesapOperator.hesapSilWithEntity(silinecekHesap)){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Baþarýsýz", "Hesap boþ deðil. Öncelikle hesabý boþaltýn."));		
			//////////////**************GROWL
			return null;
		}
//		silinecekHesap.setSube(hesapSilinecekSube);
//		silinecekHesap.setHesapBalance(500);
//		silinecekHesap.setMusteri( LoggedInCheck.currentMusteri );
//		
//		HesapDBOperator hesapDBOperator = new HesapDBOperator();
//		hesapDBOperator.hesapEkle(silinecekHesap);
		sifirla();
		return ("p_banka_memuru_main");
	}
	public void sifirla()throws Exception{
		setBulunacakHesapNo(new String());
		setSilinecekHesap(new Hesap());
		setHesapNoRender(true);
		setDetailRender(false);
	}
}
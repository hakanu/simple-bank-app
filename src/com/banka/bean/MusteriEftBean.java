package com.banka.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.banka.accessControl.LoggedInCheck;
import com.banka.dbOperations.HesapDBOperator;
import com.banka.dbOperations.IslemDBOperator;
import com.banka.dbOperations.SubeDBOperator;
import com.banka.entity.Hesap;
import com.banka.entity.Islem;
import com.banka.entity.Sube;

@ManagedBean
@SessionScoped
public class MusteriEftBean {
	private List<Hesap> hesaplar = new ArrayList<Hesap>();
	
	private List<Sube> subeler = new ArrayList<Sube>();
	
	private Hesap selectedHesap;
	
	private Hesap havaleGonderenHesabi = new Hesap();
	
	private Hesap havaleAlanHesabi = new Hesap();

	private Sube hesapSilinecekSube = new Sube();
	
	private int havaleGonderenHesapno;
	private int havaleAlanHesapno;
	
	private int havaleMiktar;
	
	public MusteriEftBean() throws Exception{	
		HesapDBOperator hesapOperator = new HesapDBOperator();
		hesaplar = hesapOperator.getMusteriHesapKotu(LoggedInCheck.currentMusteri);
		SubeDBOperator subeOperator = new SubeDBOperator();
		subeler = subeOperator.getSube();
	}
	
	public List<Hesap> getHesaplar() throws Exception {
		return hesaplar;
	}

	public void setHesaplar(List<Hesap> hesaplar) {
		this.hesaplar = hesaplar;
	}
	
	public Hesap getSelectedHesap() {
		return selectedHesap;
	}

	public void setSelectedHesap(Hesap selectedHesap) {
		this.selectedHesap = selectedHesap;
	}

	public Hesap getHavaleGonderenHesabi() {
		return havaleGonderenHesabi;
	}

	public void setHavaleGonderenHesabi(Hesap havaleGonderenHesabi) {
		this.havaleGonderenHesabi = havaleGonderenHesabi;
	}

	public Sube getHesapEklenecekSube() {
		return hesapSilinecekSube;
	}

	public void setHesapEklenecekSube(Sube hesapSilinecekSube) {
		this.hesapSilinecekSube = hesapSilinecekSube;
	}

	public int getHesapEklenecekSubeId() {
		return havaleGonderenHesapno;
	}

	public void setHesapEklenecekSubeId(int havaleGonderenHesapno) {
		this.havaleGonderenHesapno = havaleGonderenHesapno;
	}
	
	public List<Sube> getSubeler() {
		return subeler;
	}

	public void setSubeler(List<Sube> subeler) {
		this.subeler = subeler;
	}

	public Hesap getHavaleAlanHesabi() {
		return havaleAlanHesabi;
	}

	public void setHavaleAlanHesabi(Hesap havaleAlanHesabi) {
		this.havaleAlanHesabi = havaleAlanHesabi;
	}

	public int getHavaleGonderenHesapno() {
		return havaleGonderenHesapno;
	}

	public void setHavaleGonderenHesapno(int havaleGonderenHesapno) {
		this.havaleGonderenHesapno = havaleGonderenHesapno;
	}

	public int getHavaleMiktar() {
		return havaleMiktar;
	}

	public void setHavaleMiktar(int havaleMiktar) {
		this.havaleMiktar = havaleMiktar;
	}

	public int getHavaleAlanHesapno() {
		return havaleAlanHesapno;
	}

	public void setHavaleAlanHesapno(int havaleAlanHesapno) {
		this.havaleAlanHesapno = havaleAlanHesapno;
	}

	private Hesap getHesapFromID(int par_nHesapno){
		Hesap sonuc = null;
		
		System.out.println( "hesaplar in boyu: " + hesaplar.size() );
		for (Hesap s:hesaplar) {
			if(s.getHesapno() == par_nHesapno){
				sonuc = s;
				break;
			}
		}
		return sonuc;
	}
	
	public String yonlendir(){
		String havaleGonderenHesabiId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("havaleGonderenHesapno");
		System.out.println("havaleGonderenHesapno " + havaleGonderenHesabiId);
		
		havaleGonderenHesabi = getHesapFromID(Integer.parseInt( havaleGonderenHesabiId ) );
		
		System.out.println("yonlendiriyorum");
		
		return ("p_musteri_eft_ayrinti");
	}
	
	public String hesapSil() throws Exception{
		String havaleGonderenHesabiId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("havaleGonderenHesapno");

		havaleGonderenHesabi = getHesapFromID(Integer.parseInt(havaleGonderenHesabiId));
		
		System.out.println( "havaleGonderenHesabiId-param : " + havaleGonderenHesabiId );
		System.out.println( "havaleGonderenHesabi: " + havaleGonderenHesabi.getHesapAdi() + " " + havaleGonderenHesabi.getHesapno() );		
		
		HesapDBOperator hesapOperator = new HesapDBOperator();
		
		hesapOperator.hesapSilWithEntity(havaleGonderenHesabi);
		
//		havaleGonderenHesabi.setSube(hesapSilinecekSube);
//		havaleGonderenHesabi.setHesapBalance(500);
//		havaleGonderenHesabi.setMusteri( LoggedInCheck.currentMusteri );
//		
//		HesapDBOperator hesapDBOperator = new HesapDBOperator();
//		hesapDBOperator.hesapEkle(havaleGonderenHesabi);
		
		hesaplar = hesapOperator.getMusteriHesapKotu(LoggedInCheck.currentMusteri);
		
		return ("p_musteri_hesap_sil");
	}
	
	public String eftYap() throws Exception{
		System.out.println("havale yap baþladý");
		
		HesapDBOperator hesapOperator = new HesapDBOperator();
		
//		String havaleAlanHesabiId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("havaleAlanHesapno");
//		System.out.println("havaleAlanHesapno " + havaleAlanHesabiId);
		System.out.println("havaleAlanHesapno " + havaleAlanHesabi.getHesapno());
		havaleAlanHesabi = hesapOperator.hesapBulWithIDHy( havaleAlanHesabi.getHesapno() );
		
		System.out.println("havale gonderen hesap : " + havaleGonderenHesabi.getHesapAdi() + " " + havaleGonderenHesabi.getHesapno());
		System.out.println("havale alan 	hesap : " + havaleAlanHesabi.getHesapAdi() + " " + havaleAlanHesabi.getHesapno());
		System.out.println("havaleMiktar : " + havaleMiktar);
		
		///////////*******************
//		Hesap gunlenmisGonderenHesabi = new Hesap();
//		gunlenmisGonderenHesabi.setHesapAdi(havaleGonderenHesabi.getHesapAdi());
//		gunlenmisGonderenHesabi.setHesapBalance(havaleGonderenHesabi.getHesapBalance() - havaleMiktar);
//		gunlenmisGonderenHesabi.setIslemler(havaleGonderenHesabi.getIslemler());
//		gunlenmisGonderenHesabi.setMusteri(havaleGonderenHesabi.getMusteri());
//		gunlenmisGonderenHesabi.setSube(havaleGonderenHesabi.getSube());
		if(havaleGonderenHesabi.getHesapBalance() - havaleMiktar < 0){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Bakiye yetersiz. EFT gerçekleþtirilemez."));		
			//////////////**************GROWL
			return null;
		}
		if(havaleMiktar < 0){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "EFT miktarý geçersiz bir deðer!"));		
			//////////////**************GROWL
			return null;
		}
		havaleGonderenHesabi.setHesapBalance(havaleGonderenHesabi.getHesapBalance() - havaleMiktar);
		hesapOperator.doHavale2(havaleGonderenHesabi);
////////////////////////////////////////////HAKAN
		System.out.println("iþlemlere ekliyorum");
		IslemDBOperator islemOperator = new IslemDBOperator();
		Islem eklenecekIslem = new Islem();
		eklenecekIslem.setCekilenMiktar(havaleMiktar);
		eklenecekIslem.setIslemTarihi( new Date().toString() );
//		eklenecekIslem.setIslemHesabi( havaleGonderenHesabi );**************************acccccccccccccccccccccc
//		havaleGonderenHesabi.getIslemler().add(eklenecekIslem);
		islemOperator.islemEkle(eklenecekIslem);
		//////////////////////////////////////////////HAKAN
		
		havaleAlanHesabi.setHesapBalance(havaleAlanHesabi.getHesapBalance() + havaleMiktar);
		hesapOperator.doHavale3(havaleAlanHesabi);
////////////////////////////////////////////HAKAN
		System.out.println("iþlemlere ekliyorum");
		eklenecekIslem = new Islem();
		eklenecekIslem.setYatanMiktar(havaleMiktar);
		eklenecekIslem.setIslemTarihi( new Date().toString() );
//		eklenecekIslem.setIslemHesabi( havaleAlanHesabi );**************************acccccccccccccccccccccc
//		havaleGonderenHesabi.getIslemler().add(eklenecekIslem);
		islemOperator.islemEkle(eklenecekIslem);
		//////////////////////////////////////////////HAKAN
		///////////*******************
		
//		hesapOperator.doHavale(havaleMiktar, havaleAlanHesabi, havaleGonderenHesabi);
		
//		havaleGonderenHesabi.setHesapBalance(havaleGonderenHesabi.getHesapBalance() - havaleMiktar);
//		havaleAlanHesabi.setHesapBalance(havaleAlanHesabi.getHesapBalance() + havaleMiktar);
		
		//////////////**************GROWL
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Baþarýlý", "EFT baþarý ile yapýldý"));		
		//////////////**************GROWL
		
		return ("p_musteri_main");
		
//		if( hesapOperator.doHavale(havaleMiktar, havaleAlanHesabi, havaleGonderenHesabi) ){
//			//////////////////////////////////////////////HAKAN
//			System.out.println("iþlemlere ekliyorum");
//			IslemDBOperator islemOperator = new IslemDBOperator();
//			Islem eklenecekIslem = new Islem();
//			eklenecekIslem.setCekilenMiktar(havaleMiktar);
//			eklenecekIslem.setIslemTarihi( new Date().toString() );
//			eklenecekIslem.setIslemHesabi( havaleGonderenHesabi );
////			havaleGonderenHesabi.getIslemler().add(eklenecekIslem);
//			islemOperator.islemEkle(eklenecekIslem);
//			//////////////////////////////////////////////HAKAN
//			System.out.println("havale yap bitti");
//			return ("p_musteri_main");
//		}
		
//		System.out.println("havale yap bitti-hatali bitti ama");
//		return ("_404");
		
	}
}
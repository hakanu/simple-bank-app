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
import com.banka.dbOperations.IslemUcretiDBOperator;
import com.banka.dbOperations.SubeDBOperator;
import com.banka.entity.Hesap;
import com.banka.entity.Islem;
import com.banka.entity.Sube;

@ManagedBean
@SessionScoped
public class MusteriHavaleBean {
	private List<Hesap> hesaplar = new ArrayList<Hesap>();
	
	private List<Sube> subeler = new ArrayList<Sube>();
	
	private Hesap selectedHesap;
	
	private Hesap havaleGonderenHesabi = new Hesap();
	
	private Hesap havaleAlanHesabi = new Hesap();

	private Sube hesapSilinecekSube = new Sube();
	
	private int havaleGonderenHesapno;
	private int havaleAlanHesapno;
	
	private int havaleMiktar;
	
	private double havaleIslemUcreti;
	
	public MusteriHavaleBean() throws Exception{	
		HesapDBOperator hesapOperator = new HesapDBOperator();
		hesaplar = hesapOperator.getMusteriHesapKotu(LoggedInCheck.currentMusteri);
		SubeDBOperator subeOperator = new SubeDBOperator();
		subeler = subeOperator.getSube();
		IslemUcretiDBOperator i = new IslemUcretiDBOperator();
		havaleIslemUcreti = i.getIslemUcretleri().getHavaleIslemUcreti();
	}
	
	public List<Hesap> getHesaplar() throws Exception {
		HesapDBOperator hesapOperator = new HesapDBOperator();
		hesaplar = hesapOperator.getMusteriHesapKotu(LoggedInCheck.currentMusteri);
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

	public Sube getHesapSilinecekSube() {
		return hesapSilinecekSube;
	}

	public void setHesapSilinecekSube(Sube hesapSilinecekSube) {
		this.hesapSilinecekSube = hesapSilinecekSube;
	}

	public double getHavaleIslemUcreti() {
		return havaleIslemUcreti;
	}

	public void setHavaleIslemUcreti(double havaleIslemUcreti) {
		this.havaleIslemUcreti = havaleIslemUcreti;
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
		
		return ("p_musteri_havale_ayrinti");
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
	
	public String havaleYap() throws Exception{
		System.out.println("havale yap baþladý");
		
		double havaleIslemUcreti = havaleIslemUcretiDB();
		
		HesapDBOperator hesapOperator = new HesapDBOperator();
		
//		String havaleAlanHesabiId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("havaleAlanHesapno");
//		System.out.println("havaleAlanHesapno " + havaleAlanHesabiId);
		System.out.println("havaleAlanHesapno " + havaleAlanHesabi.getHesapno());
		havaleAlanHesabi = hesapOperator.hesapBulWithIDHy( havaleAlanHesabi.getHesapno() );
		if(getHavaleAlanHesabi() == null){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Havale alan hesap numarasý geçersiz. Yeniden deneyiniz."));		
			//////////////**************GROWL
			return null;
		}
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
		if(havaleGonderenHesabi.getHesapBalance() - ( havaleMiktar + havaleIslemUcreti )< 0){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Bakiye yetersiz. Havale gerçekleþtirilemez."));		
			//////////////**************GROWL
			return null;
		}
		if(havaleMiktar < 0){
			//////////////**************GROWL
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Havale miktarý geçersiz bir deðer!"));		
			//////////////**************GROWL
			return null;
		}
		havaleGonderenHesabi.setHesapBalance(havaleGonderenHesabi.getHesapBalance() - (havaleMiktar + (int)(havaleIslemUcreti) ));
		hesapOperator.doHavale2(havaleGonderenHesabi);
		
////////////////////////////////////////////HAKAN
		IslemDBOperator islemOperator = new IslemDBOperator();
//		List<Islem> islemler = LoggedInCheck.currentMusteri.getIslemler();
		List<Islem> islemler = islemOperator.getMusteriIslemDBden(LoggedInCheck.currentMusteri);
		
		System.out.println("iþlemlere ekliyorum");
		Islem eklenecekIslem = new Islem();
		eklenecekIslem.setCekilenMiktar(havaleMiktar);
		eklenecekIslem.setIslemTarihi( new Date().toString() );
		eklenecekIslem.setIslemHesabi( havaleGonderenHesabi.getHesapAdi() );//**************************acccccccccccccccccccccc
		eklenecekIslem.setIslemHesapno( havaleGonderenHesabi.getHesapno() );//**************************acccccccccccccccccccccc
		eklenecekIslem.setHesapBakiye( havaleGonderenHesabi.getHesapBalance() );//**************************acccccccccccccccccccccc
		eklenecekIslem.setMusteri(LoggedInCheck.currentMusteri);//+++++++++++++++++++++++
		islemler.add(eklenecekIslem);//+++++++++++++++++++++++
//		havaleGonderenHesabi.getIslemler().add(eklenecekIslem);
		islemOperator.islemEkle(eklenecekIslem);//**************************acccccccccccccccccccccc
//		havaleGonderenHesabi.getIslemler().add(eklenecekIslem);
//		//////////////////////////////////////////////HAKAN
		
		havaleAlanHesabi.setHesapBalance(havaleAlanHesabi.getHesapBalance() + havaleMiktar);
		hesapOperator.doHavale3(havaleAlanHesabi);
////////////////////////////////////////////HAKAN
		System.out.println("iþlemlere ekliyorum");
		eklenecekIslem = new Islem();
		eklenecekIslem.setYatanMiktar(havaleMiktar);
		eklenecekIslem.setIslemTarihi( new Date().toString() );
		eklenecekIslem.setIslemHesabi( havaleAlanHesabi.getHesapAdi() );//**************************acccccccccccccccccccccc
		eklenecekIslem.setIslemHesapno( havaleAlanHesabi.getHesapno() );//**************************acccccccccccccccccccccc
		eklenecekIslem.setHesapBakiye( havaleAlanHesabi.getHesapBalance() );//**************************acccccccccccccccccccccc
		eklenecekIslem.setMusteri(LoggedInCheck.currentMusteri);//+++++++++++++++++++++++
		islemler.add(eklenecekIslem);//+++++++++++++++++++++++
//		havaleGonderenHesabi.getIslemler().add(eklenecekIslem);
		islemOperator.islemEkle(eklenecekIslem);//**************************acccccccccccccccccccccc
//		havaleGonderenHesabi.getIslemler().add(eklenecekIslem);
//		//////////////////////////////////////////////HAKAN
		
//////////////////////////////////////////////HAKAN
//		System.out.println("iþlemlere ekliyorum");
//		eklenecekIslem = new Islem();
//		eklenecekIslem.setYatanMiktar(havaleMiktar);
//		eklenecekIslem.setIslemTarihi( new Date().toString() );
//		eklenecekIslem.setIslemHesabi( havaleAlanHesabi );
//		islemOperator.islemEkle(eklenecekIslem);
//		havaleAlanHesabi.getIslemler().add(eklenecekIslem);
		//////////////////////////////////////////////HAKAN
		///////////*******************
		
//		hesapOperator.doHavale(havaleMiktar, havaleAlanHesabi, havaleGonderenHesabi);
		
//		havaleGonderenHesabi.setHesapBalance(havaleGonderenHesabi.getHesapBalance() - havaleMiktar);
//		havaleAlanHesabi.setHesapBalance(havaleAlanHesabi.getHesapBalance() + havaleMiktar);
		
		
		
		
		///////////////////*********************TEMIZLEME KODU KAPAT DENE OLMASSA
		eklenecekIslem = new Islem();
		havaleAlanHesabi = new Hesap();
		havaleGonderenHesabi = new Hesap();
		havaleMiktar = 0;
		hesaplar = hesapOperator.getMusteriHesapKotu(LoggedInCheck.currentMusteri);
		///////////////////*********************TEMIZLEME KODU KAPAT DENE OLMASSA

		//////////////**************GROWL
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Baþarýlý", "Havale baþarý ile yapýldý"));		
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
	
	private double havaleIslemUcretiDB(){
		IslemUcretiDBOperator i = new IslemUcretiDBOperator();
		return i.getIslemUcretleri().getHavaleIslemUcreti();
	}
}
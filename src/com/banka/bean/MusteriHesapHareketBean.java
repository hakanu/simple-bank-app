package com.banka.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.banka.accessControl.LoggedInCheck;
import com.banka.dbOperations.HesapDBOperator;
import com.banka.dbOperations.IslemDBOperator;
import com.banka.dbOperations.MusteriDBOperator;
import com.banka.entity.Hesap;
import com.banka.entity.Islem;
import com.banka.entity.Musteri;

@ManagedBean
@RequestScoped
public class MusteriHesapHareketBean {
	private List<Islem> islemler = new ArrayList<Islem>();
	private Islem selectedIslem = new Islem();
	private Musteri sistemdekiMusteri = new Musteri();
	
	public MusteriHesapHareketBean() throws Exception{
		System.out.println("MusteriHesapHareketBean yaratildi");
		sistemdekiMusteri = LoggedInCheck.currentMusteri;
		
		
		////////////////////////////////DENEME
//		Musteri m = new MusteriDBOperator().getMusteri().get(0);
//		for (Hesap h : m.getHesaplar()) {
//			System.out.println("hesap: " + h.getHesapAdi());
//		}
		
//		Musteri n = new MusteriDBOperator().musteriBulWithID(LoggedInCheck.currentMusteri.getId());
		/**
		 * KOTU COZUM
		 */
//		Musteri n = getMusteriFromId(LoggedInCheck.currentMusteri.getId());
//		for (Hesap h : n.getHesaplar()) {
//			System.out.println("hesap: " + h.getHesapAdi());
//		}
//		islemler = getIslemlerFromDB(n);
		
		
//		for (Hesap h : LoggedInCheck.currentMusteri.getHesaplar()) {
//			System.out.println("hesap: " + h.getHesapAdi());
//		}
		////////////////////////////////

		
		IslemDBOperator islemOperator = new IslemDBOperator();
//		islemler = islemOperator.getMusteriIslemWithHesapList(new HesapDBOperator().getMusteriHesapKotu(sistemdekiMusteri));
//		islemler = islemOperator.getMusteriIslem(LoggedInCheck.currentMusteri);///////////////******************ACCCCCCCCCCCCCCCCCCCCCCCCCCC
//		islemler = islemOperator.getMusteriIslemBasit(LoggedInCheck.currentMusteri);
		islemler = islemOperator.getMusteriIslemDBden(LoggedInCheck.currentMusteri);
//		islemler = getIslemlerFromDB(sistemdekiMusteri);
		
		
	}
	
	public MusteriHesapHareketBean(Musteri par_mMusteri){
		IslemDBOperator islemOperator = new IslemDBOperator();
//		islemler = islemOperator.getMusteriIslem(par_mMusteri);
		islemler = islemOperator.getMusteriIslemDBden(LoggedInCheck.currentMusteri);
	}
	
	public List<Islem> getIslemler() {
		return islemler;
	}
	public void setIslemler(List<Islem> islemler) {
		this.islemler = islemler;
	}
	public Islem getSelectedIslem() {
		return selectedIslem;
	}
	public void setSelectedIslem(Islem selectedIslem) {
		this.selectedIslem = selectedIslem;
	}
	public Musteri getSistemdekiMusteri() {
		return sistemdekiMusteri;
	}
	public void setSistemdekiMusteri(Musteri sistemdekiMusteri) {
		this.sistemdekiMusteri = sistemdekiMusteri;
	}
	
	
	public List<Islem> getIslemlerFromDB(Musteri par_mMusteri){
		IslemDBOperator islemOperator = new IslemDBOperator();
		
		System.out.println("iþlemleri dbden cekiyorum");
		
//		par_mMusteri = LoggedInCheck.currentMusteri;
//		System.out.println("sistemdeki müþteri::: " + sistemdekiMusteri.getUyeAdi() + " " + sistemdekiMusteri.getSifre());
//		System.out.println("müþterinin hesaplari(bean): ");
//		for (Hesap h : par_mMusteri.getHesaplar()) {
//			System.out.println("hesap: " + h.getHesapAdi());
//		}
		
		islemler = islemOperator.getMusteriIslem( par_mMusteri );
		System.out.println("iþlemleri dbden cektim bitti");
		
		return islemler;
	}
	
	public List<Islem> getIslemlerFromDB2(Musteri par_mMusteri){
		IslemDBOperator islemOperator = new IslemDBOperator();
		
		System.out.println("iþlemleri dbden cekiyorum");
		
//		par_mMusteri = LoggedInCheck.currentMusteri;
//		System.out.println("sistemdeki müþteri::: " + sistemdekiMusteri.getUyeAdi() + " " + sistemdekiMusteri.getSifre());
//		System.out.println("müþterinin hesaplari(bean): ");
//		for (Hesap h : par_mMusteri.getHesaplar()) {
//			System.out.println("hesap: " + h.getHesapAdi());
//		}
		
		islemler = islemOperator.getMusteriIslem( par_mMusteri );
		System.out.println("iþlemleri dbden cektim bitti");
		
		return islemler;
	}
	
//	public Musteri getMusteriFromId(int par_nMusteriId) throws Exception{
//		Musteri m = null;
//		MusteriDBOperator musteriOperator = new MusteriDBOperator();
//		
//		List<Musteri> musteriler = musteriOperator.getMusteri();
//		
//		for (Musteri musteri : musteriler) {
//			if(musteri.getId() == par_nMusteriId){
//				m = musteri;
//				break;
//			}
//		}
//		
//		return m;
//	}
	
}
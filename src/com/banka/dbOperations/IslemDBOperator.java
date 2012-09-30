package com.banka.dbOperations;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.banka.entity.Hesap;
import com.banka.entity.Islem;
import com.banka.entity.Musteri;
import com.banka.util.HibernateSession;

public class IslemDBOperator implements DBOperator{
	private Session session;

	public void baslat() {
		session = HibernateSession.openSession();
	}

	public void sonlandir() {
		HibernateSession.closeSession();
	}

	public Session getSession() {
		return session;
	}
	
	public boolean islemEkle(Islem par_mEklenecek) {
		baslat();

		HibernateSession.beginTransaction();

		session.save(par_mEklenecek);
		
		HibernateSession.commitTransaction();

		sonlandir();

		return true;
	}
	
	public void islemGunle(Islem par_mGunlenmisIslem){
		baslat();

		HibernateSession.beginTransaction();

		session.merge(par_mGunlenmisIslem);
		
		HibernateSession.commitTransaction();

		sonlandir();
	}
	
	public List<Islem> getIslem() throws Exception {

		System.out.println("");
		System.out.println("GET ISLEM");

		IslemDBOperator k = new IslemDBOperator();
		baslat();
		HibernateSession.beginTransaction();
		session = k.getSession();
		List<Islem> islemDonmelik = null;
		
		try{
			islemDonmelik = session.createQuery("select m from Islem as m").list();
		}
		catch(Exception e){
			System.out.println("boþ db");
		}
		
//		**************************acccccccccccccccccccccc
//		if(islemDonmelik != null){
//			for (Islem m : islemDonmelik) {
//				System.out.println("iþlem bilgileri : " + m.getIslemId() + " " + m.getIslemTarihi() + " " + m.getIslemSaati() + " "
//						+ m.getYatanMiktar() + " " + m.getCekilenMiktar() + " " + m.getIslemHesabi().getHesapAdi() 
//						+ m.getIslemHesabi().getHesapno() );
//			}
//		}
		
		HibernateSession.commitTransaction();
		
		sonlandir();
		
		System.out.println("GET ISLEM bitti");

		return islemDonmelik;
	}// end of

	public void islemListele() throws Exception {

		System.out.println("");
		System.out.println("ISLEM LISTELE");

		baslat();
		HibernateSession.beginTransaction();
		session = getSession();

		List<Islem> islemDonmelik = session.createQuery("select m from Islem as m").list();

//		**************************acccccccccccccccccccccc
//		for (Islem m : islemDonmelik) {
//			System.out.println("iþlem bilgileri : " + m.getIslemId() + " " + m.getIslemTarihi() + " " + m.getIslemSaati() + " "
//					+ m.getYatanMiktar() + " " + m.getCekilenMiktar() + " " + m.getIslemHesabi().getHesapAdi() 
//					+ m.getIslemHesabi().getHesapno() );
//		}

		HibernateSession.commitTransaction();
		sonlandir();
	
		System.out.println("ISLEM LISTELE BITTI");

	}// end of

	public List<Islem> getMusteriIslemBasit(Musteri par_mMusteri){
		return par_mMusteri.getIslemler();
	}
	
	public List<Islem> getMusteriIslem(Musteri par_mMusteri){
		System.out.println( "getMusteriIslem baþladý " + par_mMusteri.getAdi() + " " + par_mMusteri.getId() );
		
		
		List<Islem> musteriIslemleri = new ArrayList<Islem>();
		
		baslat();
		session = getSession();
		HibernateSession.beginTransaction();
		
		
		System.out.println("criteria yaratiyorum");
//		Criteria crit = session.createCriteria(Islem.class);
		System.out.println("criteria yarattim");
//		System.out.println("criterion yaratiyorum : " + par_mMusteri.getHesaplar().get(0).getHesapAdi());
		
		
		///////////////////////******************AÞÞASI ACIKTI ONCEKÝNDE
//		for (Hesap h : par_mMusteri.getHesaplar()) {
//			System.out.println( h.getHesapAdi() + h.getHesapno());
//
//		}
		
//		for (Hesap h : par_mMusteri.getHesaplar()) {
//			System.out.println("criteriondayim");
//			Criterion hesapID = Restrictions.eq( "islemHesabi.hesapno", h.getHesapno() );
//			crit.add(hesapID);
//		}
//		
//		for (Object o : crit.list()) {
//			musteriIslemleri.add((Islem)o);
//		}
		
		
		
		/////////////***************AÞÞASI ACIKTI ONCEKÝNDE
//		for (Hesap h : par_mMusteri.getHesaplar()) {
//			System.out.println("müþterinin hesabý: " + h.getHesapAdi() + " " + h.getHesapno());
//			for (Islem islem : h.getIslemler()) {
//				System.out.println("müþteri hesabýnýn iþlemi: " + islem.getIslemId() + " " + islem.getIslemTarihi());
//				musteriIslemleri.add(islem);
//			}
//		}
		/////////////***************YUKARISI ACIKTI ONCEKÝNDE

		musteriIslemleri = par_mMusteri.getIslemler();//**************************ac burayý ACCCCCCCCCCCCCCCCCCC
		
		
		HibernateSession.commitTransaction();
		
		sonlandir();
		
		System.out.println("getMusteriIslem bitti");
		
		return musteriIslemleri;
	}
	
	public List<Islem> getMusteriIslemDBden(Musteri par_mMusteri){
		System.out.println( "getMusteriIslem baþladý " + par_mMusteri.getAdi() + " " + par_mMusteri.getId() );
		
		
		List<Islem> musteriIslemleri = new ArrayList<Islem>();
		
		baslat();
		session = getSession();
		HibernateSession.beginTransaction();
		
		
		System.out.println("criteria yaratiyorum");
		Criteria crit = session.createCriteria(Islem.class);
		System.out.println("criteria yarattim");
		System.out.println("criterion yaratiyorum");
		
		Criterion hesapID = Restrictions.eq( "musteri", par_mMusteri );
		crit.add(hesapID);
		for (Object o : crit.list()) {
			musteriIslemleri.add((Islem)o);
			System.out.println("------\nislem: " + ((Islem)o).getIslemId() + " " + ((Islem)o).getIslemTarihi());
		}
		
		
		HibernateSession.commitTransaction();
		
		sonlandir();
		
		System.out.println("getMusteriIslem bitti");
		
		return musteriIslemleri;
	}
	
	public List<Islem> getMusteriIslemWithHesapList(List<Hesap> par_hHesaplar){
		System.out.println( "getMusteriIslemWithHesapList baþladý: " + par_hHesaplar.size() );
		
		
		List<Islem> musteriIslemleri = new ArrayList<Islem>();
		
		baslat();
		
		HibernateSession.beginTransaction();
		session = getSession();
		
		System.out.println("criteria yaratiyorum");
		Criteria crit = session.createCriteria(Islem.class);
		System.out.println("criteria yarattim");
//		System.out.println("criterion yaratiyorum : " + par_mMusteri.getHesaplar().get(0).getHesapAdi());
		
//		for (Hesap h : par_hHesaplar) {
//			System.out.println( h.getHesapAdi() );
//			System.out.println( h.getHesapno() );
//
//		}
		
		for (Hesap h : par_hHesaplar) {
			System.out.println("criteriondayim");
			System.out.println("aranan hesap: " + h.getHesapAdi() + " " + h.getHesapno());
			Criterion hesapID = Restrictions.eq( "islemHesabi.hesapno", h.getHesapno() );
			crit.add(hesapID);
		}
		
		for (Object o : crit.list()) {
			System.out.println("bulunan iþlem : " + ((Islem)o).getIslemId() + " " + ((Islem)o).getIslemTarihi() );
			musteriIslemleri.add((Islem)o);
		}
		
		HibernateSession.commitTransaction();
		
		sonlandir();
		
		System.out.println("getMusteriIslemWithHesapList bitti");
		
		return musteriIslemleri;
		
	}
	
	public List<Islem> getMusteriIslemWithLoadMethod(Musteri par_mMusteri){
		System.out.println( "getMusteriIslemWithLoadMethod baþladý" + par_mMusteri.getAdi() + " " + par_mMusteri.getId() );
		
		
		List<Islem> musteriIslemleri = new ArrayList<Islem>();
		
		baslat();
		
		HibernateSession.beginTransaction();
		session = getSession();
		
		System.out.println("criteria yaratiyorum");
		Criteria crit = session.createCriteria(Islem.class);
		System.out.println("criteria yarattim");
		System.out.println("criterion yaratiyorum : " + par_mMusteri.getHesaplar().get(0).getHesapAdi());
		
		for (Hesap h : par_mMusteri.getHesaplar()) {
			System.out.println( h.getHesapAdi() + h.getHesapno());

		}
		
		for (Hesap h : par_mMusteri.getHesaplar()) {
			
		}
		
		for (Hesap h : par_mMusteri.getHesaplar()) {
			System.out.println("criteriondayim");
			Criterion hesapID = Restrictions.eq( "islemHesabi", h );
			crit.add(hesapID);
		}
		
		for (Object o : crit.list()) {
			musteriIslemleri.add((Islem)o);
		}
		
		HibernateSession.commitTransaction();
		
//		sonlandir();
		
		System.out.println("getMusteriIslemWithLoadMethod bitti");
		
		return musteriIslemleri;
	}

	

}

package com.banka.dbOperations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.banka.entity.Musteri;
import com.banka.entity.Hesap;
import com.banka.util.HibernateSession;

public class HesapDBOperator {
	private Session session;

	private void baslat() {
		session = HibernateSession.openSession();
	}

	private void sonlandir() {
		HibernateSession.closeSession();
	}

	public Session getSession() {
		return session;
	}

	public boolean kayitEkleFromMain() {
		baslat();

		HibernateSession.beginTransaction();

		Hesap h = new Hesap();

		session.save(h);

		try {
			hesapListele();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HibernateSession.commitTransaction();

		sonlandir();

		return true;
	}

	public boolean hesapEkle(Hesap par_mEklenecek) {
		baslat();

		HibernateSession.beginTransaction();
		
		session = getSession();
		
		session.save(par_mEklenecek);

		HibernateSession.commitTransaction();

//		sonlandir();

		return true;
	}
	
	public List<Hesap> getHesap() throws Exception {

		System.out.println("");
		System.out.println("GET hesap");

		HesapDBOperator k = new HesapDBOperator();
		k.baslat();
		HibernateSession.beginTransaction();
		session = k.getSession();

		List<Hesap> hesapDonmelik = session.createQuery("select m from Hesap as m").list();

		for (Hesap m : hesapDonmelik) {
			System.out.println("hesap bilgileri : " + m.getHesapAdi() + " " + m.getHesapno());
		}

		HibernateSession.commitTransaction();

		System.out.println("GET hesap");

		return hesapDonmelik;
	}// end of

	public void hesapListele() throws Exception {

		System.out.println("");
		System.out.println("hesap LISTELE");

		HesapDBOperator k = new HesapDBOperator();
		k.baslat();
		HibernateSession.beginTransaction();
		session = k.getSession();

		List<Hesap> hesapDonmelik = session.createQuery("select m from Hesap as m").list();

		for (Hesap m : hesapDonmelik) {
			System.out.println("hesap bilgileri : " + m.getHesapAdi() + " " + m.getHesapno() );
			if(m.getMusteri() != null){
				System.out.println("hesap sahibi bilgileri: " + m.getMusteri().getAdi() + " " + m.getMusteri().getId());
			}
			if(m.getSube() != null){
				System.out.println("hesap þube bilgileri: " + m.getSube().getSubeAdi() + " " + m.getSube().getSubeId() + "\n");
			}
		}

		HibernateSession.commitTransaction();

		System.out.println("hesap LISTELE BITTI");

	}// end of
	
	public boolean hesapSilWithID(int par_nSilinecekhesapID) {
		System.out.println("hesap SIL WITH ID");
		
		baslat();
		
		HibernateSession.beginTransaction();

		Hesap hesap = hesapBulWithID(par_nSilinecekhesapID);
		System.out.println("Silinecek hesap\nHesap adi: "
				+ hesap.getHesapAdi() + "\nBalance: " + hesap.getHesapBalance() + "\nID: " 
				+ hesap.getHesapno());
		if(hesap.getMusteri() != null){
			hesap.getMusteri().getHesaplar().remove(hesap);
			hesap.setMusteri(null);
		}
		if(hesap.getSube() != null){
			hesap.setSube(null);
		}
		session.delete(hesap);

		HibernateSession.commitTransaction();

		System.out.println("hesap SIL WITH ID BITTI");

		return true;
	}
	public boolean hesapSilWithEntity(Hesap par_mSilinecekhesap) {
		System.out.println("hesap SIL WITH ENTITY");

		try {
			if(par_mSilinecekhesap.getHesapBalance() != 0)
				return false;
			baslat();
			HibernateSession.beginTransaction();
			session = getSession();
			if (session == null)
				System.out.println("session null");
			else {
				
//				if(par_mSilinecekhesap.getMusteri() != null){
//					if(par_mSilinecekhesap.getMusteri().getHesaplar() != null){
//						par_mSilinecekhesap.getMusteri().getHesaplar().remove(par_mSilinecekhesap);
//					}
//					par_mSilinecekhesap.setMusteri(null);
//				}
//				if(par_mSilinecekhesap.getSube() != null){
//					par_mSilinecekhesap.setSube(null);
//				}
				
				session.delete(par_mSilinecekhesap);
			}
			HibernateSession.commitTransaction();
			sonlandir();
			System.out.println("hesap SIL WITH ENTITY BITTI");
			return true;
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public List<Hesap> getMusteriHesap(Musteri par_mMusteri){
		List<Hesap> musteriHesaplar = new ArrayList<Hesap>();
		
		System.out.println( "getMusteriHesap " + par_mMusteri.getAdi() + " " + par_mMusteri.getId() );

		baslat();
		HibernateSession.beginTransaction();
		session = getSession();

		Criteria crit = session.createCriteria(Hesap.class);
		
		if (par_mMusteri != null) {
			System.out.println("kriterdeyim");
			try{
//				Criterion name = Restrictions.like("musteri", par_mMusteri);
				Criterion name = Restrictions.eq("musteri", par_mMusteri);
				crit.add(name);
			}
			catch(Exception e){
				System.out.println("hesap yok");
			}
		}
		
		try{
			List results = crit.list();
			for (Object o : results) {
				musteriHesaplar.add( (Hesap)o );
				System.out.println("\nsonuc hesaplar: " + ((Hesap) o).getHesapAdi() + " " + ((Hesap) o).getHesapno() + "\n");
			}
		}
		catch (Exception e) {
			System.out.println("hesap yok listelemedeyim");
		}
		HibernateSession.commitTransaction();
		sonlandir();
		
		System.out.println("\ngetMusteriHesap bitti");
		
		return musteriHesaplar;
	}

	public List<Hesap> getMusteriHesapKotu(Musteri par_mMusteri) throws Exception{
		List<Hesap> musteriHesaplar = new ArrayList<Hesap>();
		List<Hesap> butunHesaplar = new ArrayList<Hesap>();
		
		butunHesaplar = getHesap();
		
		for (Hesap hesap : butunHesaplar) {
			if(hesap.getMusteri().getId() == par_mMusteri.getId()){
				musteriHesaplar.add(hesap);
			}
		}
		
		return musteriHesaplar;
	}
	public Hesap hesapBulWithIDHy(int par_nBulunacakId){
		Hesap result;
		baslat();
		
		HibernateSession.beginTransaction();
		Criteria crit = session.createCriteria(Hesap.class);
		Criterion hesapID = Restrictions.eq("hesapno", par_nBulunacakId);
		crit.add(hesapID);
		if(crit.list().isEmpty())
			return null;
		result = (Hesap) (crit.list()).get(0);
		HibernateSession.commitTransaction();
		
		sonlandir();
		
		return result;
	}
	public Hesap hesapBulWithID(int par_nBulunacakId){
		Criteria crit = session.createCriteria(Hesap.class);
		Criterion hesapID = Restrictions.eq("hesapno", par_nBulunacakId);
		crit.add(hesapID);
		if(crit.list().isEmpty())
			return null;
		return (Hesap) (crit.list()).get(0);
	}
	public List<Hesap> hesapBulWithMusteriID(int par_nBulunacakId){
		List<Hesap> result;
		baslat();
		
		HibernateSession.beginTransaction();
		Criteria crit = session.createCriteria(Hesap.class);
		Criterion musteriID = Restrictions.eq("musteri.id", par_nBulunacakId);
		crit.add(musteriID);
		result = (List<Hesap>) crit.list();
		HibernateSession.commitTransaction();
		
		sonlandir();
		
		return result;
	}
	public boolean paraCek(int par_nGunlenecekMiktar,int par_mHesap ){
		
		IslemUcretiDBOperator i = new IslemUcretiDBOperator();
		double paraCekmeIslemUcreti = i.getIslemUcretleri().getParaCekmeIslemUcreti();
		
		baslat();
		
		HibernateSession.beginTransaction();
		Hesap hesap = hesapBulWithID(par_mHesap);
		if(hesap.getHesapBalance() < (par_nGunlenecekMiktar + paraCekmeIslemUcreti) || par_nGunlenecekMiktar < 0 ){
			HibernateSession.commitTransaction();
			
			sonlandir();
			return false;
		}
		hesap.setHesapBalance(hesap.getHesapBalance() - (par_nGunlenecekMiktar + (int)paraCekmeIslemUcreti ) );
		session.update(hesap);
		
		HibernateSession.commitTransaction();
		
		sonlandir();
		
		return true;
	}
	public boolean paraYatir(int par_nGunlenecekMiktar,int par_mHesap ){
		IslemUcretiDBOperator i = new IslemUcretiDBOperator();
		double paraYatirmaIslemUcreti = i.getIslemUcretleri().getParaYatirmaIslemUcreti();
		
		baslat();
		
		HibernateSession.beginTransaction();
		Hesap hesap = hesapBulWithID(par_mHesap);
		if(par_nGunlenecekMiktar <= 0 || par_nGunlenecekMiktar < paraYatirmaIslemUcreti){
			HibernateSession.commitTransaction();
			
			sonlandir();
			return false;
		}
		hesap.setHesapBalance(hesap.getHesapBalance() + (par_nGunlenecekMiktar - (int)paraYatirmaIslemUcreti));
		session.update(hesap);
		
		HibernateSession.commitTransaction();
		
		sonlandir();
		
		return true;
	}

	public boolean doHavale(int par_nGunlenecekMiktar, Hesap par_hAlanHesap, Hesap par_hGonderenHesap){
		System.out.println("doHavaledeyim");
		if(getSession() == null){
			System.out.println("session null");
			baslat();
		}
		sonlandir();
		baslat();
		
//		HibernateSession.beginTransaction();
		par_hAlanHesap.setHesapBalance(par_hAlanHesap.getHesapBalance() + par_nGunlenecekMiktar);
		session.update(par_hAlanHesap);
//		session.saveOrUpdate(par_hAlanHesap);
//		session.flush();
//		HibernateSession.commitTransaction();
		sonlandir();
		
		baslat();
//		HibernateSession.beginTransaction();
		par_hGonderenHesap.setHesapBalance(par_hGonderenHesap.getHesapBalance() - par_nGunlenecekMiktar);
		session.update(par_hGonderenHesap);
//		session.saveOrUpdate(par_hGonderenHesap);
//		session.flush();
//		HibernateSession.commitTransaction();
		sonlandir();
		
		System.out.println("doHavale bitti");
		
		return true;
	}
	
	public boolean doHavale2(Hesap par_hGunlenmisGonderenHesabi){
		System.out.println("havale2 baþladý");
//		HibernateSession.commitTransaction();
//		sonlandir();
		baslat();
		session = getSession();
		HibernateSession.beginTransaction();
		session.saveOrUpdate(par_hGunlenmisGonderenHesabi);	
		HibernateSession.commitTransaction();
		sonlandir();
		
//		baslat();
//		HibernateSession.beginTransaction();
//		session.saveOrUpdate(par_hGunlenmisAlanHesabi);				
//		HibernateSession.commitTransaction();
//		sonlandir();
		
		System.out.println("havale2 bitti");
		
		return true;
	}
	
	public boolean doHavale3(Hesap par_hGunlenmisAlanHesabi){
		System.out.println("havale3 baþladý");
		
		baslat();
		session = getSession();
		HibernateSession.beginTransaction();
		session.saveOrUpdate(par_hGunlenmisAlanHesabi);	
		HibernateSession.commitTransaction();
		sonlandir();
		
		System.out.println("havale3 bitti");
		
		return true;
	}
	
	public boolean doEft(int par_nGunlenecekMiktar, Hesap par_hAlanHesap, Hesap par_hGonderenHesap){
		
		baslat();
		HibernateSession.beginTransaction();
		par_hAlanHesap.setHesapBalance(par_hAlanHesap.getHesapBalance() + par_nGunlenecekMiktar);
		session.update(par_hAlanHesap);
		HibernateSession.commitTransaction();
		sonlandir();
		
		baslat();
		HibernateSession.beginTransaction();
		par_hGonderenHesap.setHesapBalance(par_hGonderenHesap.getHesapBalance() - par_nGunlenecekMiktar);
		session.update(par_hGonderenHesap);
		HibernateSession.commitTransaction();
		sonlandir();
		
		return true;
	}
}

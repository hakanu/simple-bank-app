package com.banka.dbOperations;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.banka.entity.BankaMemuru;
import com.banka.entity.Hesap;
import com.banka.entity.Musteri;
import com.banka.entity.Sube;
import com.banka.util.HibernateSession;

public class SubeDBOperator {
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
			subeListele();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HibernateSession.commitTransaction();

		sonlandir();

		return true;
	}

	public boolean subeEkle(Sube par_sEklenecek) {
		baslat();

		HibernateSession.beginTransaction();

		session = getSession();

		session.save(par_sEklenecek);

		HibernateSession.commitTransaction();

		sonlandir();

		return true;
	}

	public List<Sube> getSube() throws Exception {

		System.out.println("");
		System.out.println("GET sube");

		baslat();
		HibernateSession.beginTransaction();
		session = getSession();

		List<Sube> subeDonmelik = session.createQuery("select m from Sube as m").list();

		for (Sube s : subeDonmelik) {
			System.out.println("sube bilgileri : " + s.getSubeAdi() + " " + s.getSubeId());
		}

		HibernateSession.commitTransaction();
//		sonlandir();
		
		System.out.println("GET sube");

		return subeDonmelik;
	}// end of

	public void subeListele() throws Exception {

		System.out.println("");
		System.out.println("sube LISTELE");

		baslat();
		HibernateSession.beginTransaction();
		session = getSession();

		List<Sube> subeDonmelik = session.createQuery("select m from Sube as m").list();

		for (Sube s : subeDonmelik) {
			System.out.println("sube bilgileri : " + s.getSubeAdi() + " " + s.getSubeId());
		}

		HibernateSession.commitTransaction();
		sonlandir();
		
		System.out.println("sube LISTELE BITTI");

	}// end of

	public boolean subeSilWithID(int par_nSilinecekSubeID) {
		System.out.println("sube SIL WITH ID");
		
		baslat();
		
		HibernateSession.beginTransaction();
		
		List<Sube> subeler = session.createQuery("select m from Sube as m").list();

		for (Iterator iter = subeler.iterator(); iter.hasNext();) {
			Sube sube = (Sube) iter.next();
			if (sube.getSubeId() == par_nSilinecekSubeID) {
				System.out.println("Silinecek sube\nsube adi: "	+ sube.getSubeAdi() + " ID: " + sube.getSubeId());
				
//				memurVeHesaplarlaBaglantiyiKopar(sube);
				
				/*
				for (Hesap h : sube.getHesaplar()) {
					sube.getHesaplar().remove(h);
					h.setSube(null);
				}
				for (BankaMemuru b : sube.getBankaMemurlari()) {
					sube.getBankaMemurlari().remove(b);
					b.setSube(null);
				}
				*/
				if(sube.getBankaMemurlari().size() > 0 || sube.getHesaplar().size() > 0 ){
					System.out.println("Silinemiyor içinde hesaplar ve/veya banka memurlarý mevcut");
					return false;
				}
				else
					session.delete(sube);
				break;
			}
		}// end of for loop

		HibernateSession.commitTransaction();
		sonlandir();
		
		System.out.println("sube SIL WITH ID BITTI");

		return true;
	}

	public boolean subeSilWithEntity(Sube par_mSilinecekSube) {
		System.out.println("sube SIL WITH ENTITY");

		try {
			baslat();
			HibernateSession.beginTransaction();
			session = getSession();
			if (session == null)
				System.out.println("session null");
			else {
				session.delete(par_mSilinecekSube);
			}
			HibernateSession.commitTransaction();
			sonlandir();
			System.out.println("sube SIL WITH ENTITY BITTI");
			return true;
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void subeHesapListele()
	{
		System.out.println("");
		System.out.println("subeHesapListele LISTELE");

		baslat();
		HibernateSession.beginTransaction();
		session = getSession();

		List<Sube> subeDonmelik = session.createQuery("select m from Sube as m").list();

		for (Sube m : subeDonmelik) {
			System.out.println("sube bilgileri : " + m.getSubeAdi() + " " + m.getSubeId() + " ");
			System.out.println("hesap sayisi = " + m.getHesaplar().size());
			for (Object o : m.getHesaplar()) 
			{
				Hesap h = (Hesap)o;
				System.out.println("Hesap bilgileri: " + h.getHesapAdi() + " " + h.getHesapno() + "\n");
				
			}
		}

		HibernateSession.commitTransaction();
		sonlandir();
		
		System.out.println("subeHesapListele LISTELE BITTI");
	}
	
	public void subeBankaMemuruListele()
	{
		System.out.println("");
		System.out.println("subeBankaMemuruListele LISTELE");

		baslat();
		HibernateSession.beginTransaction();
		session = getSession();

		List<Sube> subeDonmelik = session.createQuery("select m from Sube as m").list();

		for (Sube m : subeDonmelik) {
			System.out.println("sube bilgileri : " + m.getSubeAdi() + " " + m.getSubeId() + " ");
			System.out.println("BankaMemuru sayisi = " + m.getHesaplar().size());
			for (Object o : m.getBankaMemurlari()) 
			{
				BankaMemuru h = (BankaMemuru)o;
				System.out.println("BankaMemuru bilgileri: " + h.getBankaMemuruAdi() + " " + h.getBankaMemuruId() + "\n");
				
			}
		}

		HibernateSession.commitTransaction();
		sonlandir();
		
		System.out.println("subeBankaMemuruListele LISTELE BITTI");
	}

	private void memurVeHesaplarlaBaglantiyiKopar(Sube par_sSube){
		System.out.println("sube SIL WITH ID");

		baslat();

		HibernateSession.beginTransaction();

		for (Hesap h : par_sSube.getHesaplar()) {
			par_sSube.getHesaplar().remove(h);
			h.setSube(null);
		}
		for (BankaMemuru b : par_sSube.getBankaMemurlari()) {
			par_sSube.getBankaMemurlari().remove(b);
			b.setSube(null);
		}
		
		
		HibernateSession.commitTransaction();
		sonlandir();

		System.out.println("sube SIL WITH ID BITTI");
	}
	
	public boolean subeGunleWithID(Sube par_sGunlenmisSube, int par_nGunlenecekId){
		System.out.println("sube GUNLE WITH ID");
		
		baslat();
		
		HibernateSession.beginTransaction();
		
		List<Sube> subeler = session.createQuery("select m from Sube as m").list();

		for (Iterator iter = subeler.iterator(); iter.hasNext();) {
			Sube sube = (Sube) iter.next();
			if (sube.getSubeId() == par_nGunlenecekId) {
				System.out.println("gunlenecek sube\nsube adi: "	+ sube.getSubeAdi() + " ID: " + sube.getSubeId());
				
				sube.setSubeAdi(par_sGunlenmisSube.getSubeAdi());
				sube.setSubeAssests(par_sGunlenmisSube.getSubeAssests());
				sube.setSubeSehir(par_sGunlenmisSube.getSubeSehir());
				
//				if(sube.getBankaMemurlari().size() > 0 && sube.getHesaplar().size() > 0 )
//					System.out.println("Gunlenemiyor içinde hesaplar ve/veya banka memurlarý mevcut");
//				else
					session.update(sube);
				break;
			}
		}// end of for loop

		HibernateSession.commitTransaction();
		
		sonlandir();
		
		System.out.println("sube GUNLE WITH ID BITTI");

		return true;
	}
}

package com.banka.dbOperations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.banka.entity.BankaMemuru;
import com.banka.entity.Musteri;
import com.banka.entity.Sube;
import com.banka.util.HibernateSession;
import com.banka.util.PasswordHash;

public class BankaMemuruDBOperator {
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

		BankaMemuru h = new BankaMemuru();

		session.save(h);

		try {
			bankaMemuruListele();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HibernateSession.commitTransaction();

		sonlandir();

		return true;
	}

	public boolean bankaMemuruEkle(BankaMemuru par_mEklenecek) {
		
		par_mEklenecek.setBankaMemuruSifre( PasswordHash.hashPassword(par_mEklenecek.getBankaMemuruSifre()) );
		
		baslat();

		HibernateSession.beginTransaction();

		session = getSession();

		session.save(par_mEklenecek);

		HibernateSession.commitTransaction();

		sonlandir();

		return true;
	}
	
	public boolean bankaMemuruEkle_uyeAdiKontrol(BankaMemuru par_mEklenecek) {
		
		par_mEklenecek.setBankaMemuruSifre( PasswordHash.hashPassword(par_mEklenecek.getBankaMemuruSifre()) );
		
		baslat();

		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();		
			
			session.save(par_mEklenecek);
	
			transaction.commit();
		}
		catch (HibernateException e) {
			transaction.rollback();
//			e.printStackTrace();
			System.out.println("ekleme sýrasýnda hata");
			return false;
		} finally {
			sonlandir();
		}
		return true;
	}

	public List<BankaMemuru> getBankaMemuru() throws Exception {

		System.out.println("");
		System.out.println("GET bankaMemuru");

		BankaMemuruDBOperator k = new BankaMemuruDBOperator();
		k.baslat();
		HibernateSession.beginTransaction();
		session = k.getSession();

		List<BankaMemuru> bankaMemuruDonmelik = session.createQuery("select m from BankaMemuru as m").list();

		for (BankaMemuru m : bankaMemuruDonmelik) {
			System.out.println("bankaMemuru bilgileri : " + m.getBankaMemuruAdi() + " "
					+ m.getBankaMemuruId());
		}

		HibernateSession.commitTransaction();

		System.out.println("GET bankaMemuru");

		return bankaMemuruDonmelik;
	}// end of

	public void bankaMemuruListele() throws Exception {

		System.out.println("");
		System.out.println("bankaMemuru LISTELE");

		BankaMemuruDBOperator k = new BankaMemuruDBOperator();
		k.baslat();
		HibernateSession.beginTransaction();
		session = k.getSession();

		List<BankaMemuru> bankaMemuruDonmelik = session.createQuery("select m from BankaMemuru as m").list();

		for (BankaMemuru m : bankaMemuruDonmelik) {
			System.out.println("bankaMemuru bilgileri : " + m.getBankaMemuruAdi() + " " + m.getBankaMemuruId());
			if (m.getSube() != null) {
				System.out.println("bankaMemuru þube bilgileri: "
						+ m.getSube().getSubeAdi() + " "
						+ m.getSube().getSubeId());
			}
		}

		HibernateSession.commitTransaction();

		System.out.println("bankaMemuru LISTELE BITTI");

	}// end of

	public boolean bankaMemuruSilWithID(int par_nSilinecekbankaMemuruID) {
		System.out.println("bankaMemuru SIL WITH ID");

		baslat();

		HibernateSession.beginTransaction();

		List kitaplar = session.createQuery("select m from BankaMemuru as m").list();

		for (Iterator iter = kitaplar.iterator(); iter.hasNext();) {
			BankaMemuru bankaMemuru = (BankaMemuru) iter.next();
			if (bankaMemuru.getBankaMemuruId() == par_nSilinecekbankaMemuruID) {
				System.out.println("Silinecek bankaMemuru\nBankaMemuru adi: "
						+ bankaMemuru.getBankaMemuruAdi() + "\nBalance: "
						+ bankaMemuru.getBankaMemuruId());
				if (bankaMemuru.getSube() != null) {
					bankaMemuru.setSube(null);
				}

				session.delete(bankaMemuru);
				break;
			}
		}// end of for loop

		HibernateSession.commitTransaction();

		System.out.println("bankaMemuru SIL WITH ID BITTI");

		return true;
	}

	public boolean bankaMemuruSilWithEntity(BankaMemuru par_bSilinecekbankaMemuru) {
		System.out.println("bankaMemuru SIL WITH ENTITY");

		try {
			baslat();
			HibernateSession.beginTransaction();
			session = getSession();
			if (session == null)
				System.out.println("session null");
			else {

				if (par_bSilinecekbankaMemuru.getSube() != null) {
					par_bSilinecekbankaMemuru.setSube(null);
				}

				session.delete(par_bSilinecekbankaMemuru);
			}
			HibernateSession.commitTransaction();
			sonlandir();
			System.out.println("bankaMemuru SIL WITH ENTITY BITTI");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean bankaMemuruGunleWithID(BankaMemuru par_sGunlenmisBankaMemuru, int par_nGunlenecekId){
		System.out.println("bankaMemuru GUNLE WITH ID");
		
		baslat();
		
		HibernateSession.beginTransaction();
		
		List<BankaMemuru> bankaMemurlari = session.createQuery("select m from BankaMemuru as m").list();

		for (Iterator iter = bankaMemurlari.iterator(); iter.hasNext();) {
			BankaMemuru bankaMemuru = (BankaMemuru) iter.next();
			if (bankaMemuru.getBankaMemuruId() == par_nGunlenecekId) {
				System.out.println("gunlenecek bankaMemuru\nbankaMemuru adi: "	+ bankaMemuru.getBankaMemuruAdi() + " ID: " + bankaMemuru.getBankaMemuruId());
				
				bankaMemuru.setBankaMemuruAdi(par_sGunlenmisBankaMemuru.getBankaMemuruAdi());
				bankaMemuru.setBankaMemuruBaslangicTarihi(par_sGunlenmisBankaMemuru.getBankaMemuruBaslangicTarihi());
				bankaMemuru.setSube(par_sGunlenmisBankaMemuru.getSube());
				
//				if(bankaMemuru.getBankaMemurlari().size() > 0 && bankaMemuru.getHesaplar().size() > 0 )
//					System.out.println("Gunlenemiyor içinde hesaplar ve/veya banka memurlarý mevcut");
//				else
					session.update(bankaMemuru);
				break;
			}
		}// end of for loop

		HibernateSession.commitTransaction();
		
		sonlandir();
		
		System.out.println("bankaMemuru GUNLE WITH ID BITTI");

		return true;
	}

	public List<BankaMemuru> bankaMemuruBul(BankaMemuru bankaMemuru){
		System.out.println("bankaMemuru BUL \nad: " + bankaMemuru.getBankaMemuruAdi() + "uyeadi: " + bankaMemuru.getBankaMemuruUyeAdi() 
				+ "id: " + bankaMemuru.getBankaMemuruId() + "mail: " + bankaMemuru.getBankaMemuruBaslangicTarihi() + "sifre: " +
				bankaMemuru.getBankaMemuruSifre() );
		List<BankaMemuru> result = new ArrayList<BankaMemuru>();
		
		baslat();
		
		HibernateSession.beginTransaction();
		session = getSession();
		if (session == null)
			System.out.println("session null");
		else {
			Criteria crit = session.createCriteria(BankaMemuru.class);
//			Criterion name;
			
			if(bankaMemuru.getBankaMemuruAdi() != ""){
				Criterion name = Restrictions.like("bankaMemuruAdi", bankaMemuru.getBankaMemuruAdi());
				crit.add(name);
			}
			
			if(bankaMemuru.getBankaMemuruSifre() != ""){
				Criterion name = Restrictions.like("bankaMemuruSoyadi", bankaMemuru.getBankaMemuruSoyadi());
				crit.add(name);
			}
			
			if(bankaMemuru.getBankaMemuruUyeAdi() != ""){
				Criterion name = Restrictions.like("bankaMemuruUyeAdi", bankaMemuru.getBankaMemuruUyeAdi());
				crit.add(name);
			}
			
			if(bankaMemuru.getBankaMemuruSifre() != ""){
				Criterion name = Restrictions.like("bankaMemuruMail", bankaMemuru.getBankaMemuruMail());
				crit.add(name);
			}
			
			if(bankaMemuru.getBankaMemuruSifre() != ""){
				Criterion name = Restrictions.like("bankaMemuruTelefon", bankaMemuru.getBankaMemuruTelefon());
				crit.add(name);
			}
			
			if(bankaMemuru.getBankaMemuruSifre() != ""){
				Criterion name = Restrictions.like("bankaMemuruBaslangicTarihi", bankaMemuru.getBankaMemuruBaslangicTarihi());
				crit.add(name);
			}
			
			result = crit.list();
		}
		HibernateSession.commitTransaction();
		
		sonlandir();
		System.out.println("----------");
		for (Object m : result) {
			System.out.println(((BankaMemuru) m).getBankaMemuruAdi());
		}
		System.out.println("----------");
		System.out.println("bankaMemuru BUL BITTI");
		return result;
	}
	
	public BankaMemuru bankaMemuruBulWithID(int par_nBulunacakId){
		Criteria crit = session.createCriteria(BankaMemuru.class);
		Criterion bankaMemuruID = Restrictions.eq("bankaMemuruId", par_nBulunacakId);
		crit.add(bankaMemuruID);
		return (BankaMemuru) (crit.list()).get(0);
	}
	
	public BankaMemuru checkBankaMemuruLoginInfo( String par_sBankaMemuruName, String par_sBankaMemuruPass){
		BankaMemuru currentBankaMemuru = null;

		System.out.println("checkBankaMemuruLoginInfo " + par_sBankaMemuruName + " " + par_sBankaMemuruPass);
		
		par_sBankaMemuruPass = PasswordHash.hashPassword(par_sBankaMemuruPass);
		
		System.out.println("hashten sonra checkUserLoginInfo " + par_sBankaMemuruPass + " " + par_sBankaMemuruName);
		
		baslat();
		HibernateSession.beginTransaction();
		session = getSession();

		Criteria crit = session.createCriteria(BankaMemuru.class);
		
		if (par_sBankaMemuruName != null && par_sBankaMemuruName != "") {
			System.out.println("girdim2");
			Criterion name = Restrictions.like("bankaMemuruUyeAdi", par_sBankaMemuruName);
			crit.add(name);
		}
		if (par_sBankaMemuruPass != null && par_sBankaMemuruPass != "") {
			System.out.println("girdim3");
			Criterion name = Restrictions.like("bankaMemuruSifre", par_sBankaMemuruPass);
			crit.add(name);
		}

		List results = crit.list();
		for (Object o : results) {
			currentBankaMemuru = (BankaMemuru)o;
			System.out.println("\nsonuc müþteri: " + ((BankaMemuru) o).getBankaMemuruUyeAdi() + " " + ((BankaMemuru) o).getBankaMemuruId() + "\n");
		}

		HibernateSession.commitTransaction();
		sonlandir();
		
		System.out.println("\ncheckBankaMemuruLoginInfo bitti");
		
		return currentBankaMemuru;
	}
}

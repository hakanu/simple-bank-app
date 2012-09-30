package com.banka.dbOperations;

import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.banka.entity.*;
import com.banka.util.HibernateSession;
import com.banka.util.PasswordHash;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class MusteriDBOperator implements DBOperator{
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

	public boolean kayitEkleFromMain() {
		baslat();

		HibernateSession.beginTransaction();

		Musteri u = new Musteri();
		u.setAdi("hakan");
		u.setMail("sdgs");
		u.setSifre("sdgds");
		u.setSoyadi("sdhs");
		u.setUyeAdi("sdhgs");
		u.setWeb("sds");

		session.save(u);

		try {
			musteriListele();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HibernateSession.commitTransaction();

		sonlandir();

		return true;
	}

	public String musteriEkle(Musteri par_mEklenecek) {

//		Musteri musteriCheck = new Musteri();
		if(!uyeAdiCheck(par_mEklenecek.getUyeAdi()))
			return "uyeAdi";
		if(!mailCheck(par_mEklenecek.getMail()))
			return "mail";
		
		
		par_mEklenecek.setSifre( PasswordHash.hashPassword(par_mEklenecek.getSifre()) );
		
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
			return "tckimlik";
		} finally {
			sonlandir();
		}

		return "done";
	}
	public boolean uyeAdiCheck(String name){
		Musteri musteriCheck = new Musteri();
		musteriCheck.setUyeAdi(name);
		musteriCheck.setSifre("");
		musteriCheck.setAdi("");
		musteriCheck.setSoyadi("");
		musteriCheck.setMail("");
		musteriCheck.setWeb("");
		if((musteriBul(musteriCheck)).isEmpty() == false){
			return false;
		}
		return true;
	}
	public boolean mailCheck(String mail){
		Musteri musteriCheck = new Musteri();
		musteriCheck.setUyeAdi("");
		musteriCheck.setSifre("");
		musteriCheck.setAdi("");
		musteriCheck.setSoyadi("");
		musteriCheck.setMail(mail);
		musteriCheck.setWeb("");
		if((musteriBul(musteriCheck)).isEmpty() == false){
			return false;
		}
		return true;
	}
	public boolean musteriEkle_uyeAdi_kontrol(Musteri par_mEklenecek) {
		
		par_mEklenecek.setSifre( PasswordHash.hashPassword(par_mEklenecek.getSifre()) );
		
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
	
	public List<Musteri> getMusteri() throws Exception {

		System.out.println("");
		System.out.println("GET MUSTERI");

		MusteriDBOperator k = new MusteriDBOperator();
		k.baslat();
		HibernateSession.beginTransaction();
		session = k.getSession();

		List<Musteri> musteriDonmelik = session.createQuery("select m from Musteri as m").list();

		for (Musteri m : musteriDonmelik) {
			System.out.println("müþteri bilgileri : " + m.getAdi() + " " + m.getId() + " ");
		}

		HibernateSession.commitTransaction();

		System.out.println("GET MUSTERI");

		return musteriDonmelik;
	}// end of

	public void musteriListele() throws Exception {

		System.out.println("");
		System.out.println("MUSTERI LISTELE");

		baslat();
		HibernateSession.beginTransaction();
		session = getSession();

		List<Musteri> musteriDonmelik = session.createQuery("select m from Musteri as m").list();

		for (Musteri m : musteriDonmelik) {
			System.out.println("müþteri bilgileri : " + m.getAdi() + " " + m.getId() + " " + " " + 
					m.getSifre() + " " + m.getMail());
		}

		HibernateSession.commitTransaction();
		sonlandir();
		
		System.out.println("MUSTERI LISTELE BITTI");

	}// end of
	
	public boolean musteriSilWithID(int par_nSilinecekMusteriID) {
		System.out.println("MUSTERI SIL WITH ID");
		
		baslat();
		
		HibernateSession.beginTransaction();

		List<Musteri> musteriler = session.createQuery("select m from Musteri as m").list();

		for (Iterator iter = musteriler.iterator(); iter.hasNext();) {
			Musteri musteri = (Musteri) iter.next();
			if (musteri.getId() == par_nSilinecekMusteriID) {
				System.out.println("Silinecek Kitap\nBaslik: " + musteri.getAdi() + "\nYazar: " + musteri.getSoyadi() + "\nID: " + musteri.getId());
				if(musteri.getHesaplar().size() > 0 ){
					System.out.println("silinemez! müþteri üzerine kayýtlý hesaplar var");
					return false;
				}
				else
					session.delete(musteri);
				break;
			}
		}// end of for loop

		HibernateSession.commitTransaction();

		System.out.println("MUSTERI SIL WITH ID BITTI");

		return true;
	}

	public boolean musteriSilWithEntity(Musteri par_mSilinecekMusteri) {
		System.out.println("MUSTERI SIL WITH ENTITY");

		try {
			baslat();
			HibernateSession.beginTransaction();
			session = getSession();
			if (session == null)
				System.out.println("session null");
			else {
				session.delete(par_mSilinecekMusteri);
			}
			HibernateSession.commitTransaction();
			sonlandir();
			System.out.println("MUSTERI SIL WITH ENTITY BITTI");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean musteriHesapEkleWithMusteriID(int par_nMusteriID, Hesap par_hEklenecekHesap){
		System.out.println();
		System.out.println("MUSTERI HESAP EKLE ID");
		
		baslat();
		HibernateSession.beginTransaction();
		
		session = getSession();

		List<Musteri> musteriDonmelik = session.createQuery("select m from Musteri as m").list();
		for (Musteri musteri : musteriDonmelik) {
			if(musteri.getId() == par_nMusteriID){
				musteri.getHesaplar().add(par_hEklenecekHesap);
				break;
			}
		}
		
		session.flush();

		HibernateSession.commitTransaction();

		sonlandir();
		
		System.out.println("MUSTERI HESAP EKLE ID BITTI");
		
		return true;
	}
	
	public boolean musteriHesapEkleWithMusteriEntity(Musteri par_mMusteriID, Hesap par_hEklenecekHesap){
		System.out.println();
		System.out.println("MUSTERI HESAP EKLE ENTITY");
		
		baslat();
		HibernateSession.beginTransaction();
		
		session = getSession();

		par_mMusteriID.getHesaplar().add(par_hEklenecekHesap);
		
		session.flush();

		HibernateSession.commitTransaction();

		sonlandir();
		
		System.out.println("MUSTERI HESAP EKLE ENTITY BITTI");
		
		return true;
	}
	
	public void musteriHesapListele(){
		System.out.println("");
		System.out.println("musteriHesapListele LISTELE");

		baslat();
		HibernateSession.beginTransaction();
		session = getSession();

		List<Musteri> musteriDonmelik = session.createQuery("select m from Musteri as m").list();

		for (Musteri m : musteriDonmelik) {
			System.out.println("müþteri bilgileri : " + m.getAdi() + " " + m.getId() + " ");
			System.out.println("hesap sayisi = " + m.getHesaplar().size());
			for (Object o : m.getHesaplar()) {
				Hesap h = (Hesap)o;
				System.out.println("Hesap bilgileri: " + h.getHesapAdi() + " " + h.getHesapno() + "\n");
				
			}
		}

		HibernateSession.commitTransaction();
		sonlandir();
		System.out.println("musteriHesapListele LISTELE BITTI");
	}

	public Musteri checkUserLoginInfo( String par_sUsername, String par_sUserPass){
		Musteri currentMusteri = null;

		System.out.println("checkUserLoginInfo " + par_sUserPass + " " + par_sUsername);
		
		par_sUserPass = PasswordHash.hashPassword(par_sUserPass);
		
		System.out.println("hashten sonra checkUserLoginInfo " + par_sUserPass + " " + par_sUsername);
		
		baslat();
		HibernateSession.beginTransaction();
		session = getSession();

		Criteria crit = session.createCriteria(Musteri.class);
		
		if (par_sUsername != null) {
			System.out.println("girdim2");
			Criterion name = Restrictions.like("uyeAdi", par_sUsername);
			crit.add(name);
		}
		if (par_sUserPass != null) {
			System.out.println("girdim3");
			Criterion name = Restrictions.like("sifre", par_sUserPass);
			crit.add(name);
		}

		List results = crit.list();
		if(results != null){
			for (Object o : results) {
				currentMusteri = (Musteri)o;
				System.out.println("\nsonuc müþteri: " + ((Musteri) o).getAdi() + " " + ((Musteri) o).getId() + "\n");
			}
		}
		
		HibernateSession.commitTransaction();
//		sonlandir();
		
		System.out.println("\ncheckUserLoginInfo bitti");
		
		return currentMusteri;
	}
	
	public void musteriHesapEkle(Musteri par_mMusteri, Hesap par_hEklenecekHesap){
		Musteri currentMusteri = null;

		System.out.println("musteriHesapEkle " + par_mMusteri.getAdi() + " " + par_mMusteri.getId());

		baslat();
		HibernateSession.beginTransaction();
		session = getSession();

		Criteria crit = session.createCriteria(Musteri.class);
		
		if (par_mMusteri != null) {
			System.out.println("girdim2");
			Criterion name = Restrictions.eq("id", par_mMusteri.getId());
			crit.add(name);
		}

		List results = crit.list();
		for (Object o : results) {
			currentMusteri = (Musteri)o;
			System.out.println("\nsonuc müþteri: " + ((Musteri) o).getAdi() + " " + ((Musteri) o).getId() + "\n");
		}
		
//		currentMusteri.getHesaplar().add(par_hEklenecekHesap);
		
		HibernateSession.commitTransaction();
		sonlandir();
		
		System.out.println("\nmusteriHesapEkle bitti");
		
	}
	
	public List<Musteri> musteriBul(Musteri musteri){
		System.out.println("musteri BUL \nad: " + musteri.getAdi() + "id: " + musteri.getId() + "mail: " + musteri.getMail() + "sifre: " +
				musteri.getSifre() + "soyad: " + musteri.getSoyadi() + "üyeadi: " + musteri.getUyeAdi() + "web: " + musteri.getWeb());
		List<Musteri> result = new ArrayList<Musteri>();
		
		baslat();
		
		HibernateSession.beginTransaction();
		session = getSession();
		if (session == null)
			System.out.println("session null");
		else {
			Criteria crit = session.createCriteria(Musteri.class);
//			Criterion name;
			
			if(musteri.getAdi() != ""){
				Criterion name = Restrictions.like("adi", musteri.getAdi());
				crit.add(name);
			}
			
			if(musteri.getSoyadi() != ""){
				Criterion name = Restrictions.like("soyadi", musteri.getSoyadi());
				crit.add(name);
			}
			
			if(musteri.getUyeAdi() != ""){
				Criterion name = Restrictions.like("uyeAdi", musteri.getUyeAdi());
				crit.add(name);
			}
			if(musteri.getMail() != ""){
				Criterion name = Restrictions.like("mail", musteri.getMail());
				crit.add(name);
			}
			if(musteri.getWeb() != ""){
				Criterion name = Restrictions.like("web", musteri.getWeb());
				crit.add(name);
			}
			
			result = crit.list();
		}
		HibernateSession.commitTransaction();
		
		sonlandir();
		System.out.println("----------");
		for (Object m : result) {
			System.out.println(((Musteri) m).getAdi());
		}
		System.out.println("----------");
		System.out.println("musteri BUL BITTI");
		return result;
	}
	
	public Musteri musteriBulWithID(int par_nBulunacakId){		
		System.out.println("musteriBulWithID: " + par_nBulunacakId);
		baslat();
		HibernateSession.beginTransaction();
		session = getSession();
		
		Criteria crit = session.createCriteria(Musteri.class);
		Criterion musteriID = Restrictions.eq("id", par_nBulunacakId);
		crit.add(musteriID);
		if(crit.list().isEmpty())
			return null;
		Musteri donmelik = (Musteri) (crit.list()).get(0); 
		HibernateSession.commitTransaction();
		//sonlandir();
		
		return donmelik;
//		return null;
	}
	public boolean uyeAdiCheckGunle(String name,int id){
		Musteri musteriCheck = new Musteri();
		musteriCheck.setUyeAdi(name);
		musteriCheck.setSifre("");
		musteriCheck.setAdi("");
		musteriCheck.setSoyadi("");
		musteriCheck.setMail("");
		musteriCheck.setWeb("");
		if((musteriBul(musteriCheck)).isEmpty() == false && (musteriBul(musteriCheck)).get(0).getId() != id){
			return false;
		}
		return true;
	}
	public boolean mailCheckGunle(String mail,int id){
		Musteri musteriCheck = new Musteri();
		musteriCheck.setUyeAdi("");
		musteriCheck.setSifre("");
		musteriCheck.setAdi("");
		musteriCheck.setSoyadi("");
		musteriCheck.setMail(mail);
		musteriCheck.setWeb("");
		if((musteriBul(musteriCheck)).isEmpty() == false && (musteriBul(musteriCheck)).get(0).getId() != id){
			return false;
		}
		return true;
	}
	
	public String musteriGunleWithID(Musteri par_sGunlenmisMusteri, int par_nGunlenecekId){
		System.out.println("musteri GUNLE WITH ID");
		
		
		if(!uyeAdiCheckGunle(par_sGunlenmisMusteri.getUyeAdi(),par_sGunlenmisMusteri.getId()) )
			return "uyeAdi";
		if(!mailCheckGunle(par_sGunlenmisMusteri.getMail(),par_sGunlenmisMusteri.getId()))
			return "mail";
		
		
		baslat();
		session = getSession();
		
		HibernateSession.beginTransaction();
		
//		Musteri musteri = musteriBulWithID(par_nGunlenecekId);
//
//		System.out.println( "gunlenecek musteri\nmusteri adi soyadi: "	+ musteri.getAdi() + " " + musteri.getSoyadi() + " " 
//				+ " ID: " + musteri.getUyeAdi() + "web: " + musteri.getWeb() );
//		System.out.println( "gunlenmis musteri\nmusteri adi soyadi: " + par_sGunlenmisMusteri.getAdi() + " " 
//				+ par_sGunlenmisMusteri.getSoyadi() + " " + " ID: " + par_sGunlenmisMusteri.getUyeAdi() + "web: " + par_sGunlenmisMusteri.getWeb() );
//		musteri.setAdi(par_sGunlenmisMusteri.getAdi());
//		musteri.setSoyadi(par_sGunlenmisMusteri.getSoyadi());
//		musteri.setMail(par_sGunlenmisMusteri.getMail());
//		musteri.setSifre(par_sGunlenmisMusteri.getSifre());
//		musteri.setUyeAdi(par_sGunlenmisMusteri.getUyeAdi());
//		musteri.setWeb(par_sGunlenmisMusteri.getWeb());
//		musteri.setYas(par_sGunlenmisMusteri.getYas());
		
//		session.update(musteri);
		session.saveOrUpdate(par_sGunlenmisMusteri);
//		session.flush();
		HibernateSession.commitTransaction();
		
//		sonlandir();
		
		System.out.println("musteri GUNLE WITH ID BITTI");

		return "done";
	}
	
	public Musteri musteriBulWithIDSessionKapatarak(int par_nBulunacakId){
		System.out.println("musteriBulWithID: " + par_nBulunacakId);
		baslat();
		HibernateSession.beginTransaction();
		session = getSession();
		
		Criteria crit = session.createCriteria(Musteri.class);
		Criterion musteriID = Restrictions.eq("id", par_nBulunacakId);
		crit.add(musteriID);
		
		Musteri donmelik = (Musteri) (crit.list()).get(0); 
		HibernateSession.commitTransaction();
		sonlandir();
		
		return donmelik;
	}

	public Musteri musteriBulWithUyeAdi(String par_sBulunacakUyeAdi){
		System.out.println("musteriBulWithUyeAdi: " + par_sBulunacakUyeAdi);
		baslat();
		HibernateSession.beginTransaction();
		session = getSession();
		
		Criteria crit = session.createCriteria(Musteri.class);
		Criterion musteriID = Restrictions.eq("uyeAdi", par_sBulunacakUyeAdi);
		crit.add(musteriID);
		if(crit.list().isEmpty())
			return null;
		Musteri donmelik = (Musteri) (crit.list()).get(0); 
		HibernateSession.commitTransaction();
		sonlandir();
		
		return donmelik;
	}
	
}
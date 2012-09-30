package com.banka.dbOperations;

import java.util.List;

import org.hibernate.Session;

import com.banka.entity.IslemUcreti;
import com.banka.entity.Musteri;
import com.banka.util.HibernateSession;

public class IslemUcretiDBOperator implements DBOperator{
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
	
	public boolean islemUcretiEkle(IslemUcreti par_iIslemUcreti){
		System.out.println("islemUcretiEkle");
		
		
		baslat();
		session = getSession();
		try{
			HibernateSession.beginTransaction();
			
//			session.update(par_sGunlenmisIslemUcreti);
			session.saveOrUpdate(par_iIslemUcreti);
//			session.flush();
			HibernateSession.commitTransaction();
		}
		catch(Exception e){
			System.out.println("islemUcretiEkleme esnasýnda hata oldu");
			return false;
		}
		finally{
			sonlandir();
		}
		
		System.out.println("islemUcretiEkle BITTI");

		return true;
	}
	
	public IslemUcreti getIslemUcretleri(){
		IslemUcreti donmelik = new IslemUcreti();
		
		baslat();
		HibernateSession.beginTransaction();

		List<IslemUcreti> islemUcretleri = session.createQuery("select m from IslemUcreti as m").list();
		
		HibernateSession.commitTransaction();
		sonlandir();
		
		if(islemUcretleri != null){
			for (IslemUcreti islemUcreti : islemUcretleri) {				
				System.out.println("islemUcretiID			 :" + islemUcreti.getIslemUcretiId());
				System.out.println("getEftIslemUcreti		 :" + islemUcreti.getEftIslemUcreti());
				System.out.println("getHavaleIslemUcreti	 :" + islemUcreti.getHavaleIslemUcreti());
				System.out.println("getParaCekmeIslemUcreti  :" + islemUcreti.getParaCekmeIslemUcreti());
				System.out.println("getParaYatirmaIslemUcreti:" + islemUcreti.getParaYatirmaIslemUcreti());
			}
			donmelik = islemUcretleri.get(0);
		}
		
		
		return donmelik;
	}
	
	
	public boolean islemUcretiGunleWithID(IslemUcreti par_sGunlenmisIslemUcreti){
		System.out.println("islemUcretiGunleWithID");
		
		
		baslat();
		session = getSession();
		try{
			HibernateSession.beginTransaction();
			
//			session.update(par_sGunlenmisIslemUcreti);
			session.saveOrUpdate(par_sGunlenmisIslemUcreti);
//			session.flush();
			HibernateSession.commitTransaction();
		}
		catch(Exception e){
			System.out.println("iþlem ücreti günleme esnasýnda hata oldu");
			return false;
		}
		finally{
			sonlandir();
		}
		
		System.out.println("islemUcretiGunleWithID BITTI");

		return true;
	}
}

package main;
/*
 * DENEME BIR IKI UC
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Array;

//import antlr.collections.List;

import com.banka.bean.MusteriHesapHareketBean;
import com.banka.dbOperations.BankaMemuruDBOperator;
import com.banka.dbOperations.HesapDBOperator;
import com.banka.dbOperations.IslemDBOperator;
import com.banka.dbOperations.IslemUcretiDBOperator;
import com.banka.dbOperations.MusteriDBOperator;
import com.banka.dbOperations.SubeDBOperator;
import com.banka.entity.BankaMemuru;
import com.banka.entity.Hesap;
import com.banka.entity.Islem;
import com.banka.entity.IslemUcreti;
import com.banka.entity.Musteri;
import com.banka.entity.Sube;
import com.banka.util.HibernateSession;
import org.hibernate.Session;

public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
/*
 * Bakalim burayi ekleyecek mi?
 */
		Session session = null;
		try {
			session = HibernateSession.getSessionFactory().openSession();
			System.out.println("Annotation deneme basladi");
			
			Main m = new Main();
//			m.musteriEklemeTesti();
//			m.musteriSilmeTestiWithEntity();
//			m.musteriSilmeTestiWithMusteriID();
			
//			m.hesapEklemeTesti();
//			m.hesapSilmeTestiWithHesapID();
//			m.hesapSilmeTestiWithEntity();
			
//			m.musteri_hesapEklemeTesti();
			
//			m.subeEklemeTesti();
//			m.subeSilmeTestiWithEntity();
//			m.subeSilmeTestiWithSubeID();
			
			
//			m.hesabaSubeEklemeTesti();
			
//			m.bankaMemuruEklemeTesti();
//			m.bankaMemuruSilmeTestiWithEntity();
//			m.bankaMemuruSilmeTestiWithSubeID();
//			m.bankaMemuru_SubeEklemeTesti();
			
//			m.sube_fullTest();
			
//			m.sube_hesapEklemeTesti();
			
//			m.musteri_hesapFullTest();//////////////////////
//			m.hesapSilmeTestiWithHesapID();
			
//			m.hesap_musteri_sube_fullTest();
			
			
//			m.musteri_arama_Test();
//			m.musteri_login_test();
			
			
//			m.musteri_hesap_listele_test();
			
//			m.islemEklemeTesti();
			
//			m.musteridenHesaplariniListeleTest();
			
//			m.musteriHesapHareketBeanKullanTest();
			
//			m.musteriIslemListeleTest();
			
//			m.islemEklemeTesti();
//			m.musteriIslemListeleTest_yeniMapping();
			
//			m.getMusteriIslemDBden_test();
			
//			m.uniqueUyeAdiDene();
			
			m.initializeTables();
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			session.close();
		}
		
		System.out.println("Annotation deneme bitti");
	}
	
	private void musteriEklemeTesti() throws Exception{
		System.out.println("ekleme testi basladi");
		MusteriDBOperator k = new MusteriDBOperator();
		//k.kayitEkleFromMain();
		
		Musteri m = new Musteri();
		m.setAdi("hakan3");
		m.setMail("haka@com");
		m.setSifre("fgsfg");
		m.setSoyadi("sdgss");
		m.setUyeAdi("sdghsd");
		m.setWeb("asdgdsag");
		m.setYas(18);
		
		k.musteriEkle(m);
		k.musteriListele();
		
		System.out.println("ekleme testi bitti");
	}
	
	private void musteriSilmeTestiWithEntity() throws Exception{
		System.out.println("silmeTestiWithEntity testi basladi");
		MusteriDBOperator k = new MusteriDBOperator();
		
		for (Musteri m : k.getMusteri()) {
			if(m.getAdi().equals("hakan3")){
				k.musteriSilWithEntity(m);
				break;
			}
		}

		k.musteriListele();
		
		System.out.println("silmeTestiWithEntity testi bitti");
	}
	
	private void musteriSilmeTestiWithMusteriID() throws Exception{
		System.out.println("silmeTestiWithMusteriID testi basladi");
		
		MusteriDBOperator k = new MusteriDBOperator();
		
		k.musteriListele();		
		k.musteriSilWithID(3);
		k.musteriListele();
		k.musteriHesapListele();
		
		System.out.println("silmeTestiWithMusteriID testi bitti");
	}

	private void hesapEklemeTesti() throws Exception{
		System.out.println("ekleme testi basladi");
		HesapDBOperator k = new HesapDBOperator();
		//k.kayitEkleFromMain();
		
		Hesap h = new Hesap();
		h.setHesapAdi("hesap2");
		h.setHesapBalance(40);
		
		k.hesapEkle(h);
		k.hesapListele();
		
		System.out.println("ekleme testi bitti");
	}
	
	private void hesapSilmeTestiWithEntity() throws Exception{
		System.out.println("silmeTestiWithEntity testi basladi");
		HesapDBOperator k = new HesapDBOperator();
		
		for (Hesap h : k.getHesap()) {
			if(h.getHesapno() == 6){
				k.hesapSilWithEntity(h);
				break;
			}
		}

		k.hesapListele();
		
		System.out.println("silmeTestiWithEntity testi bitti");
	}
	
	private void hesapSilmeTestiWithHesapID() throws Exception{
		System.out.println("silmeTestiWithMusteriID testi basladi");
		
		HesapDBOperator k = new HesapDBOperator();
		
		k.hesapListele();		
		k.hesapSilWithID(3);
		k.hesapListele();
		
		MusteriDBOperator m = new MusteriDBOperator();
		m.musteriHesapListele();
		
		System.out.println("silmeTestiWithMusteriID testi bitti");
	}

	private void musteri_hesapEklemeTesti() throws Exception{
		System.out.println("musteri_hesapEklemeTesti testi basladi");
		MusteriDBOperator k = new MusteriDBOperator();
		
		//Musteri oluþtur
		Musteri m = new Musteri();
		m.setAdi("hakan3");
		m.setMail("haka@com");
		m.setSifre("fgsfg");
		m.setSoyadi("sdgss");
		m.setUyeAdi("sdghsd");
		m.setWeb("asdgdsag");
		m.setYas(18);
		
		k.musteriEkle(m);
		
		Musteri m2 = new Musteri();
		m2.setAdi("hakan4");
		m2.setMail("haka@com");
		m2.setSifre("fgsfg");
		m2.setSoyadi("sdgss");
		m2.setUyeAdi("sdghsd");
		m2.setWeb("asdgdsag");
		m2.setYas(18);
		
		k.musteriEkle(m2);
		
		k.musteriListele();
		
		//Hesap oluþtur
		HesapDBOperator kd = new HesapDBOperator();
		Hesap h = new Hesap();
		h.setHesapAdi("hesap1");
		h.setHesapBalance(40);
		h.setMusteri(m);
		
		m.getHesaplar().add(h);
//		k.musteriHesapEkleWithMusteriEntity(m, h);
		
		kd.hesapEkle(h);
		kd.hesapListele();
		
		k.musteriHesapListele();
		
		
		System.out.println("musteri_hesapEklemeTesti testi bitti");
	}

	private void subeEklemeTesti() throws Exception{
		System.out.println("ekleme testi basladi");
		SubeDBOperator k = new SubeDBOperator();
		
		Sube s = new Sube();
		s.setSubeAdi("sube3");
		s.setSubeSehir("ankara");
		s.setSubeAssests(100);
		
		k.subeEkle(s);
		k.subeListele();
		
		System.out.println("ekleme testi bitti");
	}
	
	private void subeSilmeTestiWithEntity() throws Exception{
		System.out.println("silmeTestiWithEntity testi basladi");
		SubeDBOperator k = new SubeDBOperator();
		
		for (Sube m : k.getSube()) {
			if(m.getSubeAdi().equals("sube2")){
				k.subeSilWithEntity(m);
				break;
			}
		}

		k.subeListele();
		
		System.out.println("silmeTestiWithEntity testi bitti");
	}
	
	private void subeSilmeTestiWithSubeID() throws Exception{
		System.out.println("silmeTestiWithMusteriID testi basladi");
		
		SubeDBOperator k = new SubeDBOperator();
		
		k.subeListele();		
		k.subeSilWithID(4);
		k.subeListele();
		
		k.subeHesapListele();
		k.subeBankaMemuruListele();
		
		System.out.println("silmeTestiWithMusteriID testi bitti");
	}
	
	private void hesabaSubeEklemeTesti() throws Exception{
		System.out.println("ekleme testi basladi");
		HesapDBOperator k = new HesapDBOperator();
		
		Sube s = new Sube();
		s.setSubeAdi("sube3");
		s.setSubeSehir("ankara");
		s.setSubeAssests(100);
		
		SubeDBOperator k2 = new SubeDBOperator();
		k2.subeEkle(s);
		k2.subeListele();
		
		Hesap h = new Hesap();
		h.setHesapAdi("hesap3");
		h.setHesapBalance(40);
		h.setSube(s);
		
		k.hesapListele();
		k.hesapEkle(h);
		k.hesapListele();
		
		
		System.out.println("ekleme testi bitti");
	}

	private void bankaMemuruEklemeTesti() throws Exception{
		System.out.println("BankaMemuru ekleme testi basladi");
		BankaMemuruDBOperator k = new BankaMemuruDBOperator();
		
		BankaMemuru s = new BankaMemuru();
		s.setBankaMemuruAdi("memur1");
		s.setBankaMemuruBaslangicTarihi("123132");
		
		k.bankaMemuruEkle(s);
		k.bankaMemuruListele();
		
		System.out.println("BankaMemuru ekleme testi bitti");
	}
	
	private void bankaMemuruSilmeTestiWithEntity() throws Exception{
		System.out.println("BankaMemuru silmeTestiWithEntity testi basladi");
		BankaMemuruDBOperator k = new BankaMemuruDBOperator();
		
		for (BankaMemuru m : k.getBankaMemuru()) {
			if(m.getBankaMemuruAdi().equals("memur1")){
				k.bankaMemuruSilWithEntity(m);
				break;
			}
		}

		k.bankaMemuruListele();
		
		System.out.println("BankaMemuru silmeTestiWithEntity testi bitti");
	}
	
	private void bankaMemuruSilmeTestiWithSubeID() throws Exception{
		System.out.println("BankaMemuru silmeTestiWithMusteriID testi basladi");
		
		BankaMemuruDBOperator k = new BankaMemuruDBOperator();
		
		k.bankaMemuruListele();		
		k.bankaMemuruSilWithID(3);
		k.bankaMemuruListele();
		
		System.out.println("BankaMemuru silmeTestiWithMusteriID testi bitti");
	}
	
	private void bankaMemuru_SubeEklemeTesti() throws Exception{
		System.out.println("BankaMemuru_sube ekleme testi basladi");
		BankaMemuruDBOperator k = new BankaMemuruDBOperator();
		
		Sube s = new Sube();
		s.setSubeAdi("sube5");
		s.setSubeSehir("ankara");
		s.setSubeAssests(100);
		
		Sube s2 = new Sube();
		s2.setSubeAdi("sube4");
		s2.setSubeSehir("ankara");
		s2.setSubeAssests(100);
		
		SubeDBOperator k2 = new SubeDBOperator();
		k2.subeEkle(s);
		k2.subeListele();
		
		BankaMemuru h = new BankaMemuru();
		h.setBankaMemuruAdi("memur4");
		h.setBankaMemuruBaslangicTarihi("124141");
		h.setSube(s);
		
		k.bankaMemuruListele();
		k.bankaMemuruEkle(h);
		k.bankaMemuruListele();
		
		
		System.out.println("BankaMemuru_sube ekleme testi bitti");
	}
	
	private void sube_fullTest() throws Exception
	{
		System.out.println("sube_fullTest basladi");
		BankaMemuruDBOperator bmdb = new BankaMemuruDBOperator();
		
		Sube s1 = new Sube();
		s1.setSubeAdi("sube3");
		s1.setSubeSehir("ankara");
		s1.setSubeAssests(100);
		
		Sube s2 = new Sube();
		s2.setSubeAdi("sube2");
		s2.setSubeSehir("ankara");
		s2.setSubeAssests(100);
		
		SubeDBOperator k2 = new SubeDBOperator();
		k2.subeEkle(s1);
		k2.subeEkle(s2);
		k2.subeListele();
		
		
		Hesap h1 = new Hesap();
		h1.setHesapAdi("hesap1");
		h1.setHesapBalance(40);
		HesapDBOperator hdb = new HesapDBOperator();
		hdb.hesapEkle(h1);
		hdb.hesapListele();
		
		System.out.println();
		
		BankaMemuru bm = new BankaMemuru();
		bm.setBankaMemuruAdi("memur1");
		bm.setBankaMemuruBaslangicTarihi("124141");
		bm.setSube(s1);
//		s1.getBankaMemurlari().add(bm);
		
//		bmdb.bankaMemuruListele();
		bmdb.bankaMemuruEkle(bm);
		bmdb.bankaMemuruListele();
		
		System.out.println();
		System.out.println();
		System.out.println();
		k2.subeHesapListele();
		
		System.out.println("sube_fullTest bitti");
		
	}
	
	private void sube_hesapEklemeTesti() throws Exception{
		System.out.println("sube_hesapEklemeTesti testi basladi");
		
		Sube s1 = new Sube();
		s1.setSubeAdi("sube3");
		s1.setSubeSehir("ankara");
		s1.setSubeAssests(100);
		
		Sube s2 = new Sube();
		s2.setSubeAdi("sube4");
		s2.setSubeSehir("ankara");
		s2.setSubeAssests(100);
		
		SubeDBOperator k2 = new SubeDBOperator();
		k2.subeEkle(s1);
		k2.subeEkle(s2);
		k2.subeListele();
		
		
		//Hesap oluþtur
		HesapDBOperator kd = new HesapDBOperator();
		Hesap h = new Hesap();
		h.setHesapAdi("hesap3");
		h.setHesapBalance(40);
		h.setSube(s1);
		
		kd.hesapEkle(h);
		kd.hesapListele();
		
		BankaMemuruDBOperator bmdb = new BankaMemuruDBOperator();
		BankaMemuru bm = new BankaMemuru();
		bm.setBankaMemuruAdi("memur3");
		bm.setBankaMemuruBaslangicTarihi("124141");
		bm.setSube(s1);
		
		bmdb.bankaMemuruEkle(bm);
		bmdb.bankaMemuruListele();
		
		s1.getHesaplar().add(h);
		s1.getBankaMemurlari().add(bm);
		
		k2.subeHesapListele();
		k2.subeBankaMemuruListele();
		
		System.out.println("sube_hesapEklemeTesti testi bitti");
	}
	
	private void musteri_hesapFullTest() throws Exception{
		System.out.println("musteri_hesapEklemeTesti testi basladi");
		MusteriDBOperator k = new MusteriDBOperator();
		
		//Musteri oluþtur
		Musteri m = new Musteri();
		m.setAdi("hakan5");
		m.setMail("haka@com");
		m.setSifre("user2");
		m.setSoyadi("sdgss");
		m.setUyeAdi("user2");
		m.setWeb("asdgdsag");
		m.setYas(18);
		
		k.musteriEkle(m);
		
//		Musteri m2 = new Musteri();
//		m2.setAdi("hakan6");
//		m2.setMail("haka@com");
//		m2.setSifre("fgsfg");
//		m2.setSoyadi("sdgss");
//		m2.setUyeAdi("sdghsd");
//		m2.setWeb("asdgdsag");
//		m2.setYas(18);
//		
//		k.musteriEkle(m2);
		
		k.musteriListele();
		
		System.out.println("hesap ekleniyor");
		SubeDBOperator subeOp = new SubeDBOperator();
		
		Sube s = new Sube();
		s.setSubeAdi("sube5");
		s.setSubeSehir("ankara");
		s.setSubeAssests(100);
		
		subeOp.subeEkle(s);
		subeOp.subeListele();
		
		System.out.println("hesap ekleniyor bitti");
		
		//Hesap oluþtur
		HesapDBOperator kd = new HesapDBOperator();
		Hesap h = new Hesap();
		h.setHesapAdi("hesap2");
		h.setHesapBalance(40);
		h.setMusteri(m);
		h.setSube(s);
		
		m.getHesaplar().add(h);
//		k.musteriHesapEkleWithMusteriEntity(m, h);
		
		kd.hesapEkle(h);
		kd.hesapListele();
		
		k.musteriHesapListele();
		
		
		System.out.println("musteri_hesapEklemeTesti testi bitti");
	}
	
	private void hesap_musteri_sube_fullTest() throws Exception{
		System.out.println("musteri_hesapEklemeTesti testi basladi");
		MusteriDBOperator k = new MusteriDBOperator();
		
		//Musteri oluþtur
		Musteri m = new Musteri();
		m.setAdi("hakan5");
		m.setMail("haka@com");
		m.setSifre("fgsfg");
		m.setSoyadi("sdgss");
		m.setUyeAdi("sdghsd");
		m.setWeb("asdgdsag");
		m.setYas(18);
		
		k.musteriEkle(m);
		
		Musteri m2 = new Musteri();
		m2.setAdi("hakan6");
		m2.setMail("haka@com");
		m2.setSifre("fgsfg");
		m2.setSoyadi("sdgss");
		m2.setUyeAdi("sdghsd");
		m2.setWeb("asdgdsag");
		m2.setYas(18);
		
		k.musteriEkle(m2);
		
		k.musteriListele();
		
		
		
		//Hesap oluþtur
		HesapDBOperator kd = new HesapDBOperator();
		Hesap h = new Hesap();
		h.setHesapAdi("hesap2");
		h.setHesapBalance(40);
		h.setMusteri(m);
		
		m.getHesaplar().add(h);
//		k.musteriHesapEkleWithMusteriEntity(m, h);
		
		kd.hesapEkle(h);
		kd.hesapListele();
		
		k.musteriHesapListele();
		
		
		System.out.println("musteri_hesapEklemeTesti testi bitti");
	}

	private void musteri_arama_Test() throws Exception{
		System.out.println("musteri_arama_Test basladi");
		MusteriDBOperator k = new MusteriDBOperator();
		
		Musteri m = new Musteri();
		m.setAdi("hakan36");
		m.setMail("haka@com");
		m.setSifre("sifrebu");
		m.setSoyadi("sdgss");
		m.setUyeAdi("uyeadi3");
		m.setWeb("asdgdsag");
		m.setYas(18);
		
		k.musteriEkle(m);
		
		k.musteriListele();
		k.checkUserLoginInfo("uyeadi3", "sifrebu");
		
		System.out.println("musteri_arama_Test bitti");
	}
	
	private Musteri musteri_login_test() throws Exception{
		System.out.println("musteri_login_test basladi");
		MusteriDBOperator k = new MusteriDBOperator();
		
		//normal kullanýcý ekle
		Musteri m = new Musteri();
		m.setAdi("user");
		m.setMail("user@com");
		m.setSifre("user");
		m.setSoyadi("user");
		m.setUyeAdi("user");
		m.setWeb("user.com");
		m.setYas(18);
		
		//yönetici ekle
//		m.setAdi("admin");
//		m.setMail("admin@com");
//		m.setSifre("admin");
//		m.setSoyadi("admin");
//		m.setUyeAdi("admin");
//		m.setWeb("admin.com");
//		m.setYas(18);
//		m.setType(1);
		
		//memur ekle
		m.setAdi("memur");
		m.setMail("memur@com");
		m.setSifre("memur");
		m.setSoyadi("memur");
		m.setUyeAdi("memur");
		m.setWeb("memur.com");
		m.setYas(18);
		m.setType(2);
		
		
		k.musteriEkle(m);
		
		
		
		k.musteriListele();
		
		Musteri loginOlan = k.checkUserLoginInfo("memur", "memur");
		if(loginOlan != null){
			System.out.println( "login olan : " + loginOlan.getAdi() + " " + loginOlan.getUyeAdi() + " " + loginOlan.getSifre() + " " + loginOlan.getId() );
			System.out.println("login olan : " + loginOlan.getType());
			System.out.println("musteri_login_test bitti");
			return loginOlan;
		}
		if(loginOlan == null){
			System.out.println("user bulunamadý");
			System.out.println("musteri_login_test bitti");
			return null;
		}
		
		return loginOlan;
		
		
	}

	private void musteri_hesap_listele_test() throws Exception{
		System.out.println("musteri_hesap_listele_test basladi");
		MusteriDBOperator k = new MusteriDBOperator();
		List<Musteri> musteriler = new ArrayList<Musteri>();
		
		musteriler = k.getMusteri();
		
		for (Musteri m : musteriler) {
			System.out.println(m.getUyeAdi() + " " + m.getId() + " " + m.getSifre());
			for (Hesap h : m.getHesaplar()) {
				System.out.println(h.getHesapAdi() + " " + h.getHesapno());
			}
			
		}
		
		System.out.println("musteri_hesap_listele_test bitti");
	}

//	private void islemEklemeTesti() throws Exception{
//		System.out.println("islemEklemeTesti basladi");
//		IslemDBOperator k = new IslemDBOperator();
//		
//		Musteri m = new MusteriDBOperator().getMusteri().get(0);
//		System.out.println("müþteri: " + m.getAdi() + " " + m.getId());
//		Hesap hesap = new Hesap();
//		hesap.setHesapAdi("hesap0");
//		hesap.setHesapBalance(500);
//		hesap.setMusteri(m);
//		hesap.setSube(new SubeDBOperator().getSube().get(0));
//		
//		HesapDBOperator hesapOperator = new HesapDBOperator();
//		hesapOperator.hesapEkle(hesap);
//		
//		
//		for (Hesap h : m.getHesaplar()) {
//			System.out.println("hesap: " + h.getHesapAdi());
//		}
//		
//		Islem i = new Islem();
//		i.setCekilenMiktar(50);
//		i.setIslemHesabi(m.getHesaplar().get(0));
//		i.setIslemTarihi( new Date().toString().replace("EET", "") );
//		m.getHesaplar().get(0).getIslemler().add(i);
//		
//		k.islemEkle(i);
//		
//		
//		
//		
////		List<Islem> islemler = k.getMusteriIslem( new MusteriDBOperator().getMusteri().get(0) );
////		
////		for (Islem islem : islemler) {
////			System.out.println(islem.getIslemId() + " " + islem.getIslemTarihi() + " " + islem.getIslemHesabi().getHesapAdi());
////		}
//		
////		System.out.println("hesap: " + new MusteriDBOperator().getMusteri().get(0).getHesaplar().get(0).getHesapAdi() );
//		
//		
////		Date a = new Date();
////		System.out.println(a.toString().replace("EET", ""));
////		System.out.println(a.getDate());
////		System.out.println(a.getDay());
////		System.out.println(a.getMonth());
////		System.out.println(a.getTime());
////		System.out.println(a.getYear());
//		
//		System.out.println("islemEklemeTesti bitti");
//	}
	
	private void musteridenHesaplariniListeleTest() throws Exception{
		System.out.println("musteridenHesaplariniListeleTest baþladý");
		
		MusteriDBOperator musteriOperator = new MusteriDBOperator();
		int nMusteriID = 4;
		Musteri m = musteriOperator.musteriBulWithID(nMusteriID);
		
		System.out.println("m: " + m.getAdi() + " " + m.getId());
		
		for (Hesap h : m.getHesaplar()) {
			System.out.println("sdgs");
			System.out.println("hesap: " + h.getHesapAdi() + " " + h.getHesapno());
		}
		
		System.out.println("musteridenHesaplariniListeleTest bitti");
		
	}
	
//	private void musteriHesapHareketBeanKullanTest() throws Exception{
//		System.out.println("musteriHesapHareketBeanKullanTest baþladý");
//		
//		MusteriDBOperator musteriOperator = new MusteriDBOperator();
//
//		
//		int nMusteriID = 1;
//		Musteri m = musteriOperator.musteriBulWithID(nMusteriID);
//		System.out.println("bulunan müþteri : " + m.getAdi() + " " + m.getId());
//		for (Hesap h : m.getHesaplar()) {
//			System.out.println("hesap: " + h.getHesapAdi() + " " + h.getHesapno());
//		}
//		
//		for (Hesap h : m.getHesaplar()) {
//			System.out.println("hesaptayim");
//			for (Islem i : h.getIslemler()) {
//				System.out.println("islem: " + i.getIslemId() + " " + i.getIslemTarihi());
//			}
//		}
////		getIslemlerFromDB2(m);
//		
//		
//		
////		Musteri m2 = hiberLoadDene();
////		System.out.println("bulunan müþteri2 : " + m2.getAdi() + " " + m2.getId());
////		for (Hesap h : m2.getHesaplar()) {
////			System.out.println("hesap2: " + h.getHesapAdi() + " " + h.getHesapno());
////		}
//		
//		
//		System.out.println("musteriHesapHareketBeanKullanTest bitti");
//		
//	}
	
	public List<Islem> getIslemlerFromDB2(Musteri par_mMusteri){
		IslemDBOperator islemOperator = new IslemDBOperator();
		List<Islem> islemler = new ArrayList<Islem>();
		
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
	
	private Musteri hiberLoadDene(){
		Session session;
		session = HibernateSession.openSession();
		HibernateSession.beginTransaction();
		Musteri pk = (Musteri) session.load( Musteri.class, 4 );
		HibernateSession.closeSession();
		return pk;
	}

	private void musteriIslemListeleTest() throws Exception{
		HesapDBOperator hesapOperator = new HesapDBOperator();
		MusteriDBOperator musteriOperator = new MusteriDBOperator();
		IslemDBOperator islemOperator = new IslemDBOperator();
		
		Musteri m = new MusteriDBOperator().getMusteri().get(0);
		System.out.println("müþteri: " + m.getAdi() + " " + m.getId());
		
//		islemOperator.getMusteriIslem(m);
		
		MusteriHesapHareketBean bean = new MusteriHesapHareketBean(m);
		for (Islem i : bean.getIslemler()) {
			System.out.println("islem: " + i.getIslemId() + " " + i.getIslemTarihi()) ;
		}
		
	}
	
	private void islemEklemeTesti() throws Exception{
		
		System.out.println("islemEklemeTesti basladi");
		IslemDBOperator k = new IslemDBOperator();
		
		Musteri m = new MusteriDBOperator().getMusteri().get(3);//////////////////////////////////////////
		System.out.println("müþteri: " + m.getAdi() + " " + m.getId());
		Hesap hesap = new Hesap();
		hesap.setHesapAdi("hesap0");
		hesap.setHesapBalance(500);
		hesap.setMusteri(m);
		hesap.setSube(new SubeDBOperator().getSube().get(0));
		
		HesapDBOperator hesapOperator = new HesapDBOperator();
		hesapOperator.hesapEkle(hesap);
		
		
		for (Hesap h : m.getHesaplar()) {
			System.out.println("hesap: " + h.getHesapAdi());
		}
		
		Islem i = new Islem();
		i.setCekilenMiktar(50);
//		i.setIslemHesabi(m.getHesaplar().get(0));
		i.setIslemHesabi(m.getHesaplar().get(0).getHesapAdi());
		i.setIslemTarihi( new Date().toString().replace("EET", "") );
		i.setMusteri(m);
		m.getIslemler().add(i);
		
		k.islemEkle(i);

		
		System.out.println("islemEklemeTesti bitti");
	}
	
	private void musteriIslemListeleTest_yeniMapping() throws Exception{
		System.out.println("islem listeleme test yeni mapping");
		
		MusteriDBOperator musteriOperator = new MusteriDBOperator();
		
		List<Musteri> musteriler = musteriOperator.getMusteri();
		for (Musteri musteri : musteriler) {
			System.out.println("musteri: " + musteri.getUyeAdi() + " " + musteri.getId());
			System.out.println("musterinin iþlemleri: ");
			for (Islem i : musteri.getIslemler()) {
//				System.out.println("islem: " + i.getIslemId() + " " + i.getIslemHesabi().getHesapAdi() + " " + i.getIslemTarihi());
				System.out.println("islem: " + i.getIslemId() + " " + i.getIslemHesabi() + " " + i.getIslemTarihi());
			}
		}
		
//		musteriSilmeTest_deleteOrphan_ile(musteriOperator.getMusteri().get(2));
		System.out.println("islem listeleme test yeni mapping bitti");
		
	}
	
	private void musteriSilmeTest_deleteOrphan_ile(Musteri par_mSilinecekMusteri){
		System.out.println("musteriSilmeTest_deleteOrphan_ile baþladý");
		MusteriDBOperator musteriOperator = new MusteriDBOperator();
		
		musteriOperator.musteriSilWithEntity(par_mSilinecekMusteri);
		
		System.out.println("musteriSilmeTest_deleteOrphan_ile bitti");
	}
	
	private void musteriIslemListeleTest_criteria() throws Exception{
		System.out.println("criteriondayim");
		List<Islem> musteriIslemleri = new ArrayList<Islem>();
		Hesap h = null;
		
		Musteri m = new MusteriDBOperator().getMusteri().get(0);
		
		Session session = HibernateSession.getSessionFactory().openSession();
		HibernateSession.beginTransaction();
		
		
		System.out.println("criteria yaratiyorum");
		Criteria crit = session.createCriteria(Islem.class);
		
		Criterion hesapID = Restrictions.eq( "musteri", m );
		crit.add(hesapID);

		for (Object o : crit.list()) {
			musteriIslemleri.add((Islem)o);
		}
		
	}
	
	private void getMusteriIslemDBden_test() throws Exception{
		
		new IslemDBOperator().getMusteriIslemDBden(new MusteriDBOperator().getMusteri().get(0));
	}

	private void uniqueUyeAdiDene() throws Exception{
		System.out.println("uniqueUyeAdiDene basladi");
		MusteriDBOperator k = new MusteriDBOperator();
		
		//normal kullanýcý ekle
		Musteri m = new Musteri();
		m.setAdi("killanici");
		m.setMail("killanici@com");
		m.setSifre("killanici");
		m.setSoyadi("killanici");
		m.setUyeAdi("killanici");
		m.setWeb("killanici");
		m.setYas(18);
		
		Musteri m2 = new Musteri();
		m2.setAdi("killanici2");
		m2.setMail("killanici2@com");
		m2.setSifre("killanici2");
		m2.setSoyadi("killanici2");
		m2.setUyeAdi("killanici2");
		m2.setWeb("killanici2");
		m2.setYas(18);
		
//		Musteri m3 = new Musteri();
//		m3.setAdi("killanici2");
//		m3.setMail("killanici2@com");
//		m3.setSifre("killanici2");
//		m3.setSoyadi("killanici2");
//		m3.setUyeAdi("killanici2");
//		m3.setWeb("killanici2");
//		m3.setYas(18);
		
		//memur ekle
//		m.setAdi("memur");
//		m.setMail("memur@com");
//		m.setSifre("memur");
//		m.setSoyadi("memur");
//		m.setUyeAdi("memur");
//		m.setWeb("memur.com");
//		m.setYas(18);
//		m.setType(2);
		
		
		if( !(k.musteriEkle_uyeAdi_kontrol(m)) ){
			System.out.println("hata1");
		}
		
		if( !(k.musteriEkle_uyeAdi_kontrol(m2)) ){
			System.out.println("hata2");
		}
		
//		if( !(k.musteriEkle_uyeAdi_kontrol(m3)) ){
//			System.out.println("hata3");
//		}
		
		k.musteriListele();
		
		System.out.println("uniqueUyeAdiDene bitti");
	}
	
	private void initializeTables(){
		System.out.println("initializeTables basladi");
		MusteriDBOperator k = new MusteriDBOperator();
		
		//normal kullanýcý ekle
		Musteri m = new Musteri();
		m.setAdi("user");
		m.setMail("user@com");
		m.setSifre("user");
		m.setSoyadi("user");
		m.setUyeAdi("user");
		m.setWeb("user");
//		m.setCinsiyet("E");
		m.setTckimlik("124352624");
		m.setTelefon("4353453");
		m.setYas(18);
		
		Musteri m2 = new Musteri();
		m2.setAdi("admin");
		m2.setMail("admin@com");
		m2.setSifre("admin");
		m2.setSoyadi("admin");
		m2.setUyeAdi("admin");
		m2.setWeb("admin");
//		m2.setCinsiyet("E");
		m2.setTckimlik("111112");
		m2.setTelefon("1111");
		m2.setYas(18);
		m2.setType(1);
		
		Musteri m3 = new Musteri();
		m3.setAdi("user3");
		m3.setMail("user3@com");
		m3.setSifre("user3");
		m3.setSoyadi("user3");
		m3.setUyeAdi("user3");
		m3.setWeb("user3");
//		m3.setCinsiyet("E");
		m3.setTckimlik("111222");
		m3.setTelefon("11");
		m3.setYas(18);
		
		k.musteriEkle_uyeAdi_kontrol(m);
		k.musteriEkle_uyeAdi_kontrol(m2);
		k.musteriEkle_uyeAdi_kontrol(m3);
		
		IslemUcreti i = new IslemUcreti();
		i.setEftIslemUcreti(20);
		i.setHavaleIslemUcreti(20);
		i.setParaCekmeIslemUcreti(20);
		i.setParaYatirmaIslemUcreti(20);
		
		(new IslemUcretiDBOperator()).islemUcretiEkle(i);
		
		System.out.println("initializeTables basladi");

	}
}


package com.banka.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import org.hibernate.annotations.Entity;

@Entity
@Table(name = "Sube")
public class Sube {
	private int subeId;
	private String subeAdi;
	private String subeSehir;
	private double subeAssests;
	private List<Hesap> hesaplar = new ArrayList<Hesap>();
	private List<BankaMemuru> bankaMemurlari = new ArrayList<BankaMemuru>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subeid", nullable = false)
	public int getSubeId() {
		return subeId;
	}
	public void setSubeId(int subeId) {
		this.subeId = subeId;
	}
	
	@Column(name = "subeadi", nullable = false, length = 30)
	public String getSubeAdi() {
		return subeAdi;
	}
	public void setSubeAdi(String subeAdi) {
		this.subeAdi = subeAdi;
	}
	
	@Column(name = "subesehri", nullable = false, length = 30)
	public String getSubeSehir() {
		return subeSehir;
	}
	public void setSubeSehir(String subeSehir) {
		this.subeSehir = subeSehir;
	}
	
	@Column(name = "subeAssets")
	public double getSubeAssests() {
		return subeAssests;
	}
	public void setSubeAssests(double subeAssests) {
		this.subeAssests = subeAssests;
	}	
	
	@OneToMany(mappedBy="sube")
	public List<Hesap> getHesaplar() {
		return hesaplar;
	}

	public void setHesaplar(List<Hesap> hesaplar) {
		this.hesaplar = hesaplar;
	}
	
	@OneToMany(mappedBy="sube")
	public List<BankaMemuru> getBankaMemurlari() {
		return bankaMemurlari;
	}

	public void setBankaMemurlari(List<BankaMemuru> memurlar) {
		this.bankaMemurlari = memurlar;
	}
}
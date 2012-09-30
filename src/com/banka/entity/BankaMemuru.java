package com.banka.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bankamemuru")
public class BankaMemuru {
	private int bankaMemuruId;
	private String bankaMemuruAdi;
	private String bankaMemuruSoyadi;
	private String bankaMemuruBaslangicTarihi;
	private Sube sube;
	private String bankaMemuruMail;
	private String bankaMemuruTelefon;
	private String bankaMemuruUyeAdi;
	private String bankaMemuruSifre;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memurid", nullable = false)
	public int getBankaMemuruId() {
		return bankaMemuruId;
	}
	public void setBankaMemuruId(int memurId) {
		this.bankaMemuruId = memurId;
	}
	
	@Column(name = "memuradi", nullable = false)
	public String getBankaMemuruAdi() {
		return bankaMemuruAdi;
	}
	public void setBankaMemuruAdi(String memurAdi) {
		this.bankaMemuruAdi = memurAdi;
	}
	
	@Column(name = "memursoyadi", nullable = false)
	public String getBankaMemuruSoyadi() {
		return bankaMemuruSoyadi;
	}
	public void setBankaMemuruSoyadi(String bankaMemuruSoyadi) {
		this.bankaMemuruSoyadi = bankaMemuruSoyadi;
	}
	@Column(name = "memurbastarihi", nullable = false)
	public String getBankaMemuruBaslangicTarihi() {
		return bankaMemuruBaslangicTarihi;
	}
	public void setBankaMemuruBaslangicTarihi(String memurBaslangicTarihi) {
		this.bankaMemuruBaslangicTarihi = memurBaslangicTarihi;
	}
	
	@Column(name = "memurmail", nullable = false, unique = true)
	public String getBankaMemuruMail() {
		return bankaMemuruMail;
	}
	public void setBankaMemuruMail(String bankaMemuruMail) {
		this.bankaMemuruMail = bankaMemuruMail;
	}
	
	@Column(name = "memurtelefon", nullable = false)
	public String getBankaMemuruTelefon() {
		return bankaMemuruTelefon;
	}
	public void setBankaMemuruTelefon(String bankaMemuruTelefon) {
		this.bankaMemuruTelefon = bankaMemuruTelefon;
	}
	
	@Column(name = "memuruyeadi", nullable = false, unique = true)
	public String getBankaMemuruUyeAdi() {
		return bankaMemuruUyeAdi;
	}
	public void setBankaMemuruUyeAdi(String bankaMemuruUyeAdi) {
		this.bankaMemuruUyeAdi = bankaMemuruUyeAdi;
	}
	
	@Column(name = "memursifre", nullable = false)
	public String getBankaMemuruSifre() {
		return bankaMemuruSifre;
	}
	public void setBankaMemuruSifre(String bankaMemuruSifre) {
		this.bankaMemuruSifre = bankaMemuruSifre;
	}
	
	@ManyToOne
    @JoinColumn(name="subeid")
	public Sube getSube() {
		return sube;
	}
	public void setSube(Sube sube) {
		this.sube = sube;
	}
	
}

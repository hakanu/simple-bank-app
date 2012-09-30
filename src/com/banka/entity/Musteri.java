package com.banka.entity;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

/**
 * 
 * @author hakan
 */

@Entity
@Table(name = "Musteri")
public class Musteri {

	private int id;
	private String uyeAdi;
	private String sifre;
	private String mail;
	private String adi;
	private String soyadi;
	private String web;
	private Integer yas;
	private String tckimlik;
	private String telefon;
//	private String cinsiyet;
	private int type = 0;
    private List<Hesap> hesaplar = new ArrayList<Hesap>();
	private List<Islem> islemler = new ArrayList<Islem>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "musteriid", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "adi", nullable = false, length = 30)
	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	@Column(name = "mail", nullable = false, length = 100, unique = true)
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name = "sifre", nullable = false, length = 128)
	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	@Column(name = "soyadi", nullable = false, length = 30)
	public String getSoyadi() {
		return soyadi;
	}

	public void setSoyadi(String soyadi) {
		this.soyadi = soyadi;
	}

	@Column(name = "uyeadi", nullable = false, length = 50, unique = true)
	public String getUyeAdi() {
		return uyeAdi;
	}

	public void setUyeAdi(String uyeAdi) {
		this.uyeAdi = uyeAdi;
	}

	@Column(name = "url", nullable = false, length = 128)
	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}
	
	@Column(name = "yas", nullable = false)
	public Integer getYas() {
		return yas;
	}

	public void setYas(Integer yas) {
		this.yas = yas;
	}

	@Column(name = "type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "tckimlik", nullable = false, unique=true)
	public String getTckimlik() {
		return tckimlik;
	}

	public void setTckimlik(String tckimlik) {
		this.tckimlik = tckimlik;
	}

	@Column(name = "telefon", nullable = false, unique=false)
	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

//	@Column(name = "cinsiyet", nullable = false, unique=false)
//	public String getCinsiyet() {
//		return cinsiyet;
//	}
//
//	public void setCinsiyet(String cinsiyet) {
//		this.cinsiyet = cinsiyet;
//	}

	@OneToMany(mappedBy="musteri")
	public List<Hesap> getHesaplar() {
		return hesaplar;
	}

	public void setHesaplar(List<Hesap> hesaplar) {
		this.hesaplar = hesaplar;
	}

	@OneToMany(mappedBy="musteri")
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.ALL,
		org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.SAVE_UPDATE})

	public List<Islem> getIslemler() {
		return islemler;
	}

	public void setIslemler(List<Islem> islemler) {
		this.islemler = islemler;
	}

}
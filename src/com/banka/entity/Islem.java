package com.banka.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Islem")
public class Islem {
	
	private int islemId;
	private String islemTarihi;
	private String islemSaati;
	private double yatanMiktar = 0;
	private double cekilenMiktar = 0;
//	private Hesap islemHesabi;
	private String islemHesabi;
	private int islemHesapno;
	private double hesapBakiye;
	private Musteri musteri;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "islemid", nullable = false)
	public int getIslemId() {
		return islemId;
	}
	public void setIslemId(int islemId) {
		this.islemId = islemId;
	}
	
	@Column(name = "islemTarihi", nullable = false, length = 40)
	public String getIslemTarihi() {
		return islemTarihi;
	}
	public void setIslemTarihi(String islemTarihi) {
		this.islemTarihi = islemTarihi;
	}
	
	@Column(name = "islemSaati", length = 30)
	public String getIslemSaati() {
		return islemSaati;
	}
	public void setIslemSaati(String islemSaati) {
		this.islemSaati = islemSaati;
	}
	
	@Column(name = "yatanMiktar", nullable = false, length = 30)
	public double getYatanMiktar() {
		return yatanMiktar;
	}
	public void setYatanMiktar(double yatanMiktar) {
		this.yatanMiktar = yatanMiktar;
	}
	
	@Column(name = "cekilenMiktar", nullable = false, length = 30)
	public double getCekilenMiktar() {
		return cekilenMiktar;
	}
	public void setCekilenMiktar(double cekilenMiktar) {
		this.cekilenMiktar = cekilenMiktar;
	}
	
	
//	@OneToOne(cascade = CascadeType.ALL)
//	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.ALL,org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
//	public Hesap getIslemHesabi() {
//		return islemHesabi;
//	}
//	public void setIslemHesabi(Hesap islemHesabi) {
//		this.islemHesabi = islemHesabi;
//	}
	
	@Column(name = "islemhesabi", nullable = false, length = 30)
	public String getIslemHesabi() {
		return islemHesabi;
	}
	public void setIslemHesabi(String islemHesabi) {
		this.islemHesabi = islemHesabi;
	}
	
	@Column(name = "islemhesapno", nullable = false, length = 30)
	public int getIslemHesapno() {
		return islemHesapno;
	}
	public void setIslemHesapno(int islemHesapno) {
		this.islemHesapno = islemHesapno;
	}
	
	@Column(name = "hesapbakiye", nullable = false, length = 30)
	public double getHesapBakiye() {
		return hesapBakiye;
	}
	public void setHesapBakiye(double hesapBakiye) {
		this.hesapBakiye = hesapBakiye;
	}
	
	@ManyToOne
	@JoinColumn(name="musteriid")
    public Musteri getMusteri() {
		return musteri;
	}
	public void setMusteri(Musteri musteri) {
		this.musteri = musteri;
	}
	
}

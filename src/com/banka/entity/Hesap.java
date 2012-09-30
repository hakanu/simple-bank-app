package com.banka.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author hakan
 */

@Entity
@Table(name = "Hesap")
public class Hesap{
	private int hesapno;
	private String hesapAdi;
	private int hesapBalance;
	private Musteri musteri;
	private Sube sube;
//	private List<Islem> islemler = new ArrayList<Islem>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HESAPNO", nullable = false)
	public int getHesapno() {
		return hesapno;
	}
	public void setHesapno(int hesapno) {
		this.hesapno = hesapno;
	}
	
	@Column(name = "HESAPADI", nullable = false, length = 30)
	public String getHesapAdi() {
		return hesapAdi;
	}
	public void setHesapAdi(String hesapAdi) {
		this.hesapAdi = hesapAdi;
	}
	
	@Column(name = "HESAPBALANCE", nullable = false)
	public int getHesapBalance() {
		return hesapBalance;
	}
	public void setHesapBalance(int hesapBalance) {
		this.hesapBalance = hesapBalance;
	}
	
	@ManyToOne
    @JoinColumn(name="musteriid")
	public Musteri getMusteri() {
		return musteri;
	}
	public void setMusteri(Musteri musteri) {
		this.musteri = musteri;
	}
	
	@ManyToOne
    @JoinColumn(name="subeid")
	public Sube getSube() {
		return sube;
	}
	public void setSube(Sube sube) {
		this.sube = sube;
	}
	
//	@OneToMany(mappedBy="islemHesabi")
//	public List<Islem> getIslemler() {
//		return islemler;
//	}
//	public void setIslemler(List<Islem> islemler) {
//		this.islemler = islemler;
//	}
	
	
	
	
}//end of class

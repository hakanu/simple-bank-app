package com.banka.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IslemUcreti")
public class IslemUcreti {
	
	private int islemUcretiId;
	private double havaleIslemUcreti = 10;
	private double eftIslemUcreti = 12;
	private double paraCekmeIslemUcreti = 5;
	private double paraYatirmaIslemUcreti = 5;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "islemucretiid", nullable = false)
	public int getIslemUcretiId() {
		return islemUcretiId;
	}
	public void setIslemUcretiId(int islemUcretiId) {
		this.islemUcretiId = islemUcretiId;
	}
	
	@Column(name = "havaleUcret", nullable = true)
	public double getHavaleIslemUcreti() {
		return havaleIslemUcreti;
	}
	public void setHavaleIslemUcreti(double havaleIslemUcreti) {
		this.havaleIslemUcreti = havaleIslemUcreti;
	}
	
	@Column(name = "eftUcret", nullable = true)
	public double getEftIslemUcreti() {
		return eftIslemUcreti;
	}
	public void setEftIslemUcreti(double eftIslemUcreti) {
		this.eftIslemUcreti = eftIslemUcreti;
	}
	
	@Column(name = "paraCekmeUcret", nullable = true)
	public double getParaCekmeIslemUcreti() {
		return paraCekmeIslemUcreti;
	}
	public void setParaCekmeIslemUcreti(double paraCekmeIslemUcreti) {
		this.paraCekmeIslemUcreti = paraCekmeIslemUcreti;
	}
	
	@Column(name = "paraYatirmaUcret", nullable = true)
	public double getParaYatirmaIslemUcreti() {
		return paraYatirmaIslemUcreti;
	}
	public void setParaYatirmaIslemUcreti(double paraYatirmaIslemUcreti) {
		this.paraYatirmaIslemUcreti = paraYatirmaIslemUcreti;
	}
	
}

package com.fes.bean.encuesta.totales;

import java.io.Serializable;
import java.util.List;

public class DsCompte3 implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int uno;
	private int dos;
	private int tres;
	private int cuatro;
	private int cinco;
	private int totalOtros;
	private List<String> lstOtrosCompte3;
	
	
	public int getUno() {
		return uno;
	}
	public void setUno(int uno) {
		this.uno = uno;
	}
	public int getDos() {
		return dos;
	}
	public void setDos(int dos) {
		this.dos = dos;
	}
	public int getTres() {
		return tres;
	}
	public void setTres(int tres) {
		this.tres = tres;
	}
	public int getCuatro() {
		return cuatro;
	}
	public void setCuatro(int cuatro) {
		this.cuatro = cuatro;
	}
	public int getCinco() {
		return cinco;
	}
	public void setCinco(int cinco) {
		this.cinco = cinco;
	}
	public List<String> getLstOtrosCompte3() {
		return lstOtrosCompte3;
	}
	public void setLstOtrosCompte3(List<String> lstOtrosCompte3) {
		this.lstOtrosCompte3 = lstOtrosCompte3;
	}
	public int getTotalOtros() {
		return totalOtros;
	}
	public void setTotalOtros(int totalOtros) {
		this.totalOtros = totalOtros;
	}

	
}

package com.fes.bean.encuesta.totales;

import java.io.Serializable;
import java.util.List;

public class AnioSemestre implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int total;
	private List<String> lstSemestres;
	
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<String> getLstSemestres() {
		return lstSemestres;
	}
	public void setLstSemestres(List<String> lstSemestres) {
		this.lstSemestres = lstSemestres;
	}	
}

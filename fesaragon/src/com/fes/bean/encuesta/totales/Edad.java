package com.fes.bean.encuesta.totales;

import java.io.Serializable;
import java.util.List;

public class Edad implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int total;
	private List<String> lstEdades;
	
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<String> getLstEdades() {
		return lstEdades;
	}
	public void setLstEdades(List<String> lstEdades) {
		this.lstEdades = lstEdades;
	}
}

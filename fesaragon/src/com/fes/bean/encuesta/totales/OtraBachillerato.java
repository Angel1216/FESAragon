package com.fes.bean.encuesta.totales;

import java.io.Serializable;
import java.util.List;

public class OtraBachillerato implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int total;
	private List<String> lstNombreBachillerato;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<String> getLstNombreBachillerato() {
		return lstNombreBachillerato;
	}
	public void setLstNombreBachillerato(List<String> lstNombreBachillerato) {
		this.lstNombreBachillerato = lstNombreBachillerato;
	}

}

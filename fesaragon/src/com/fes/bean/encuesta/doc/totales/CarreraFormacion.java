package com.fes.bean.encuesta.doc.totales;

import java.io.Serializable;
import java.util.List;

public class CarreraFormacion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int totalCarreraFormacion;
	private List<String> lstCarreraFormacion;
	
	public int getTotalCarreraFormacion() {
		return totalCarreraFormacion;
	}
	public void setTotalCarreraFormacion(int totalCarreraFormacion) {
		this.totalCarreraFormacion = totalCarreraFormacion;
	}
	public List<String> getLstCarreraFormacion() {
		return lstCarreraFormacion;
	}
	public void setLstCarreraFormacion(List<String> lstCarreraFormacion) {
		this.lstCarreraFormacion = lstCarreraFormacion;
	}
}

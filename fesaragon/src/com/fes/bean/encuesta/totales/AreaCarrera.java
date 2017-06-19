package com.fes.bean.encuesta.totales;

import java.util.List;
import java.io.Serializable;

public class AreaCarrera implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int total;
	private List<String> lstAreasCarreras;
	
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<String> getLstAreasCarreras() {
		return lstAreasCarreras;
	}
	public void setLstAreasCarreras(List<String> lstAreasCarreras) {
		this.lstAreasCarreras = lstAreasCarreras;
	}
}

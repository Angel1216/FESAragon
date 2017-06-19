package com.fes.bean.encuesta.totales;

import java.io.Serializable;
import java.util.List;

public class SecuProc implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int totalSecundarias;
	private List<String> lstNombreSecundaria;
	
	
	public int getTotalSecundarias() {
		return totalSecundarias;
	}
	public void setTotalSecundarias(int totalSecundarias) {
		this.totalSecundarias = totalSecundarias;
	}
	public List<String> getLstNombreSecundaria() {
		return lstNombreSecundaria;
	}
	public void setLstNombreSecundaria(List<String> lstNombreSecundaria) {
		this.lstNombreSecundaria = lstNombreSecundaria;
	}
	
	
}

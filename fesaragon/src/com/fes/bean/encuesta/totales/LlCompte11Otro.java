package com.fes.bean.encuesta.totales;

import java.io.Serializable;
import java.util.List;

public class LlCompte11Otro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int total;
	private List<String> objOtroCompte11;
	
	public List<String> getObjOtroCompte11() {
		return objOtroCompte11;
	}
	public void setObjOtroCompte11(List<String> objOtroCompte11) {
		this.objOtroCompte11 = objOtroCompte11;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}

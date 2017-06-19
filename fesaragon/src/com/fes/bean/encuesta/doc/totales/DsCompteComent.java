package com.fes.bean.encuesta.doc.totales;

import java.io.Serializable;
import java.util.List;

public class DsCompteComent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int totalCompteComent;
	private List<String> lstCompteComent;
	
	public int getTotalCompteComent() {
		return totalCompteComent;
	}
	public void setTotalCompteComent(int totalCompteComent) {
		this.totalCompteComent = totalCompteComent;
	}
	public List<String> getLstCompteComent() {
		return lstCompteComent;
	}
	public void setLstCompteComent(List<String> lstCompteComent) {
		this.lstCompteComent = lstCompteComent;
	}
}

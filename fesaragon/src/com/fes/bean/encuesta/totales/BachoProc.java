package com.fes.bean.encuesta.totales;

import java.io.Serializable;

public class BachoProc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cch;
	private int enp;
	private OtraBachillerato otra;
	
	public int getCch() {
		return cch;
	}
	public void setCch(int cch) {
		this.cch = cch;
	}
	public int getEnp() {
		return enp;
	}
	public void setEnp(int enp) {
		this.enp = enp;
	}
	public OtraBachillerato getOtra() {
		return otra;
	}
	public void setOtra(OtraBachillerato otra) {
		this.otra = otra;
	}
	
}

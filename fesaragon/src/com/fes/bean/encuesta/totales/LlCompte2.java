package com.fes.bean.encuesta.totales;

import java.io.Serializable;

public class LlCompte2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int uno;
	private int dos;
	private LlCompte2a objllCompte2a;
	
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
	public LlCompte2a getObjllCompte2a() {
		return objllCompte2a;
	}
	public void setObjllCompte2a(LlCompte2a objllCompte2a) {
		this.objllCompte2a = objllCompte2a;
	}
}

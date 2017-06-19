package com.fes.bean.encuesta.doc.totales;

import java.io.Serializable;

public class Antiguedad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int uno;
	private int dos;
	private int tres;
	private int cuatro;
	
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
	public int getTres() {
		return tres;
	}
	public void setTres(int tres) {
		this.tres = tres;
	}
	public int getCuatro() {
		return cuatro;
	}
	public void setCuatro(int cuatro) {
		this.cuatro = cuatro;
	}
}

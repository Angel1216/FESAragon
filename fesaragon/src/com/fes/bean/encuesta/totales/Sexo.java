package com.fes.bean.encuesta.totales;

import java.io.Serializable;

public class Sexo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int femenino;
	private int masculino;
	
	
	public int getFemenino() {
		return femenino;
	}
	public void setFemenino(int femenino) {
		this.femenino = femenino;
	}
	public int getMasculino() {
		return masculino;
	}
	public void setMasculino(int masculino) {
		this.masculino = masculino;
	}
}

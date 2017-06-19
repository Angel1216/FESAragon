package com.fes.bean;

import java.io.Serializable;

public class dtoUsuario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int llUsuarioID;
	private String dsUsuario;
	private String dsPassword;
	private int llNivelID;
	private int llCampusID;
	public int getLlUsuarioID() {
		return llUsuarioID;
	}
	public void setLlUsuarioID(int llUsuarioID) {
		this.llUsuarioID = llUsuarioID;
	}
	public String getDsUsuario() {
		return dsUsuario;
	}
	public void setDsUsuario(String dsUsuario) {
		this.dsUsuario = dsUsuario;
	}
	public String getDsPassword() {
		return dsPassword;
	}
	public void setDsPassword(String dsPassword) {
		this.dsPassword = dsPassword;
	}
	public int getLlNivelID() {
		return llNivelID;
	}
	public void setLlNivelID(int llNivelID) {
		this.llNivelID = llNivelID;
	}
	public int getLlCampusID() {
		return llCampusID;
	}
	public void setLlCampusID(int llCampusID) {
		this.llCampusID = llCampusID;
	}

}

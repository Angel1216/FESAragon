package com.fes.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name="welcomeMB")
public class WelcomeMB {
	
	private String aniversario;
	
	@PostConstruct
	public void init(){
		
		// Validar que este Logeado
		validarSession();
	/*			
		// Session
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
	int encuesta = Integer.parseInt( session.getAttribute("banEncuesta")!=null?session.getAttribute("banEncuesta").toString():"0" );
		*/
		// Validar Mensajes
		this.aniversario = "40 Aniversario...";
		
		/*if(encuesta==1){
			facesContext.addMessage(null, new FacesMessage("Gracias", "Tu encuesta fue enviada con éxito.") );
			session.setAttribute("banEncuesta",0);
		}*/
	}
	
	public void validarSession(){
			// Validar que este Logeado
			FacesContext validarLogin = FacesContext.getCurrentInstance();
			HttpSession validarSession = (HttpSession) validarLogin.getExternalContext().getSession(false);
			
			if(validarSession==null)
				validarSession = (HttpSession) validarLogin.getExternalContext().getSession(true);
	}
	
	// setters
	public void setAniversario(String aniversario){
		this.aniversario = aniversario;
	}

	// getters
	public String getAniversario(){
		return aniversario;
	}
}

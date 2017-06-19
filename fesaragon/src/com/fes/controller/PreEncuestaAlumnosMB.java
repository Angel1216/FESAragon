package com.fes.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fes.DAO.DM.EjemploAccesoBD1;

import com.fes.DAO.PreEncuestaAlumnosDAO;

@ManagedBean(name="preEncuestaAlumnosMB")
@ViewScoped
public class PreEncuestaAlumnosMB extends PreEncuestaAlumnosDAO implements Serializable{

	private static final long serialVersionUID = 1L;

    private String nivel;   
    private String campus;
    private String entidad;
    private String cuenta;
    private boolean banEntidad;
    private Map<String,String> countries;
    private Map<String, String> mpCatalogoNivel = null;
    private Map<String,String> mpCatalogoCampus = null;
    private Map<String, String> mpCatalogoEntidad = null;
    private Map<String, String> mpTipoCatalogo = null;
    private Map<String, String> mpParametrosCatalogo = null;
   
    @PostConstruct
    public void init() {
    	// Ejemplo 
    	//EjemploAccesoBD1 objEjemploAccesoBD1 = new EjemploAccesoBD1();
    	//objEjemploAccesoBD1.insertar();
    	
    	
    	// Tipo de Catalogos
    	mpTipoCatalogo = new HashMap<String, String>();
    	mpTipoCatalogo.put("nivel", "nivel");
    	mpTipoCatalogo.put("campus", "campus");
    	mpTipoCatalogo.put("entidad", "entidad");

    	mpParametrosCatalogo = new HashMap<String, String>();
    	mpCatalogoNivel = new HashMap<String, String>();
    	mpCatalogoNivel = obtenCatalogoCombo(mpTipoCatalogo.get("nivel").toString(),mpParametrosCatalogo);  
    }
 
 
    public String getNivel() {
        return nivel;
    }
 
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
 
    public Map<String, String> getCountries() {
        return countries;
    }
 
    public Map<String, String> getMpCatalogoCampus() {
        return mpCatalogoCampus;
    }
 
    public void onNivelChange() {
        if(nivel !=null && !nivel.equals("")){
        	mpParametrosCatalogo = new HashMap<String, String>();
        	mpParametrosCatalogo.put("nivel", nivel);
        
        	mpCatalogoCampus = obtenCatalogoCombo(mpTipoCatalogo.get("campus").toString(), mpParametrosCatalogo);
        	mpCatalogoEntidad = new HashMap<String, String>();
        }else{
        	mpCatalogoCampus = new HashMap<String, String>();
        	mpCatalogoEntidad = new HashMap<String, String>();
        }
    }
    
    public void onCampusChange() {
        if(campus !=null && !campus.equals("")){
        	mpParametrosCatalogo = new HashMap<String, String>();
    		mpParametrosCatalogo.put("nivel", nivel);
    		mpParametrosCatalogo.put("campus", campus);
    		
    		if(Integer.parseInt(campus)==CAMPUS_BUNAM){
    			banEntidad=false;
    		} else{
    			banEntidad=true;
    		}
    		
        	mpCatalogoEntidad = obtenCatalogoCombo(mpTipoCatalogo.get("entidad").toString(), mpParametrosCatalogo);
        }
        else{
        	mpCatalogoEntidad = new HashMap<String, String>();
        }
    }
    

    public void llenarEncuesta() {
    	// Subir a sesion los datos a llenar en la encuesta
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		session.setAttribute("nivel", nivel);
		session.setAttribute("campus", campus);
		session.setAttribute("entidad", entidad);
		session.setAttribute("cuenta", cuenta);

		// Validacion Numero de Cuenta
		if(cuenta.length()<9){
			facesContext.addMessage(null, new FacesMessage("Atención", "Tu número de cuenta debe contener un mínimo de nueve caracteres numéricos.") );
			return;
		}
		
		if(Integer.parseInt(nivel)==NIVEL_LICENCIATURA){	// Nivel Licenciatura
			try {
				// validamos para redireccionar
				if(session!=null)
					session.setAttribute("banDisabledEnvEnc", 1);
					String sepSys = System.getProperty("file.separator");
					String url = sepSys+"fesaragon"+sepSys+"xhtml"+sepSys+"compositions"+sepSys+"general"+sepSys+"encuestas"+sepSys+"alumnos"+sepSys+"encuestaAlumnoLic.xhtml";
					facesContext.getExternalContext().redirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} if(Integer.parseInt(nivel)==NIVEL_BACHILLERATO){	// Nivel Bachillerato
			try {
				// validamos para redireccionar
				if(session!=null)
					session.setAttribute("banDisabledEnvEnc", 1);
					String sepSys = System.getProperty("file.separator");
					String url = sepSys+"fesaragon"+sepSys+"xhtml"+sepSys+"compositions"+sepSys+"general"+sepSys+"encuestas"+sepSys+"alumnos"+sepSys+"encuestaAlumnoBachillerato.xhtml";
					facesContext.getExternalContext().redirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

    
	public Map<String, String> getMpCatalogoNivel() {
		return mpCatalogoNivel;
	}

	public void setMpCatalogoNivel(Map<String, String> mpCatalogoNivel) {
		this.mpCatalogoNivel = mpCatalogoNivel;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public Map<String, String> getMpCatalogoEntidad() {
		return mpCatalogoEntidad;
	}

	public void setMpCatalogoEntidad(Map<String, String> mpCatalogoEntidad) {
		this.mpCatalogoEntidad = mpCatalogoEntidad;
	}

	public void setMpCatalogoCampus(Map<String, String> mpCatalogoCampus) {
		this.mpCatalogoCampus = mpCatalogoCampus;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public boolean isBanEntidad() {
		return banEntidad;
	}


	public void setBanEntidad(boolean banEntidad) {
		this.banEntidad = banEntidad;
	}
	
	
}

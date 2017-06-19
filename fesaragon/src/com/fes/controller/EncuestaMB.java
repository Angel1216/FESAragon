package com.fes.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.fes.DAO.EncuestaDAO;

@ManagedBean(name="encuestaMB")
public class EncuestaMB extends EncuestaDAO{

	private String secundariaProcedencia;
	private String bachilleratoProcedencia;
	private String bachilleratoProcedenciaOtra;
	private String edad;
	private String sexo;
	private String carrera;
	private String cicloEscolar;
	private String componente1;
	private String componente2;
	private String componente2a;
	private String[] componente3check;
	private String componente3Otro;	
	private String componente4;
	private String componente5;
	private String componente6;
	private String componente7;
	private String componente8;
	private String componente9;
	private String componente10;
	private String componente11a;
	private String componente11b;
	private String componente11c;
	private String componente11d;
	private String componente11e;
	private String componente11f;
	private String componente11g;
	private String componente11h;
	private String componente11i;
	private String componente11j;
	private String componente11k;
	private String componente11l;
	private String componente11m;
	private String componente11Otro;
	private String componente12a;
	private String componente12b;
	private String componente12c;
	private String componente12d;
	private String componente12e;
	private Map<String, String> mpCatalogoEntidad = null;
	private Map<String, String> mpParametrosCatalogo = null;
	
	// variables tipo bandera
	private boolean badBachilleratoProcedencia;
	private boolean banComponente2a;
	private boolean banComponente5;
	private boolean banEnviar;
	
	
	@PostConstruct
	public void init(){
		// 0.-	Descativado
		// 1.-	Activado
		
		// Session
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		
		if(session==null){
			// String url = "fesaragon" + System.getProperty("file.separator") + "xhtml" + System.getProperty("file.separator") + "compositions" + System.getProperty("file.separator") + "general" + System.getProperty("file.separator") + "welcome.xhtml";
			String sepSys = System.getProperty("file.separator");
			String url = ".."+sepSys+".."+sepSys+"welcome.xhtml";
			try{
				facesContext.getExternalContext().redirect(url);
			} catch(Exception e){
				e.getMessage();
			}
		} else{
			// Combo Carreras (Nivel medio Superior)
			int nivel = Integer.parseInt( ((session.getAttribute("nivel")!=null) && (session.getAttribute("nivel").equals("")==false)) ?session.getAttribute("nivel").toString():"0" );
			int campus = Integer.parseInt( ((session.getAttribute("campus")!=null) && (session.getAttribute("campus").equals("")==false)) ?session.getAttribute("campus").toString():"0" );
			int entidad = Integer.parseInt( ((session.getAttribute("entidad")!=null) && (session.getAttribute("entidad").equals("")==false)) ?session.getAttribute("entidad").toString():"0" );
			
			if(nivel==NIVEL_LICENCIATURA){
				mpParametrosCatalogo = new HashMap<String, String>();
	    		mpParametrosCatalogo.put("nivel", String.valueOf(nivel));
	    		mpParametrosCatalogo.put("campus", String.valueOf(campus));
	    		
				mpCatalogoEntidad = obtenCatalogoCombo("entidad", mpParametrosCatalogo);
			} if(nivel==NIVEL_BACHILLERATO){
				mpCatalogoEntidad = obtenCatalogoCombo("area", mpParametrosCatalogo);
			}
			
			// Control de Boton Enviar (Disabled)
			int disabledEnvEnc = Integer.parseInt( session.getAttribute("banDisabledEnvEnc")!=null?session.getAttribute("banDisabledEnvEnc").toString():"0" );
			
			if(disabledEnvEnc==0){
				banEnviar = true;
			} else {
				banEnviar = false;
			}
		}
	}
	
	public void bachilleratoProcedOtra() {
		if(bachilleratoProcedenciaOtra.length()==0)
			badBachilleratoProcedencia = false;
		else
			badBachilleratoProcedencia = true;
	}
	
	
	public void bachilleratoProced() {
		if(!bachilleratoProcedencia.equals(""))
			bachilleratoProcedenciaOtra = "";
	}
	
	
	public void pregunta2(){
		if(componente2.equals("2"))
			banComponente2a = true;
		else
			banComponente2a = false;
	}
	
	public void pregunta5(){
		if(componente5.equals("3"))
			banComponente5 = true;
		else
			banComponente5 = false;
	}
	
	public void enviar(){
		//banEnviar = true;
		System.out.println("");
//		// Session
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		
//		try{
//			//sleep 5 seconds
//			//Thread.sleep(9000);
//			//facesContext.getExternalContext().redirect("/fesaragon/xhtml/compositions/general/welcome.xhtml");
//		} catch(Exception ex){
//			ex.getMessage();
//		}
		
	}
	
	
	
	
	public void llenarEncuestaAlumno(){
		
		try{
			// Variables SQL
			String sql = "";
			
			// Session
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

			// Datos Encuesta
			int encuestaID=1;
			int versionEncuestaID=1;
			
			// Datos Encuestado
			int nivel = Integer.parseInt( ((session.getAttribute("nivel")!=null) && (session.getAttribute("nivel").equals("")==false)) ?session.getAttribute("nivel").toString():"0" );
			int campus = Integer.parseInt(  ((session.getAttribute("campus")!=null) && (session.getAttribute("campus").equals("")==false)) ? session.getAttribute("campus").toString():"0" );
			int entidad = Integer.parseInt( ((session.getAttribute("entidad")!=null) && (session.getAttribute("entidad").equals("")==false)) ? session.getAttribute("entidad").toString():"0" );
			int cuenta = Integer.parseInt( ((session.getAttribute("cuenta")!=null) && (session.getAttribute("cuenta").equals("")==false)) ?session.getAttribute("cuenta").toString():"0" );
			
			// Castear Componente 3
			String componente3checkCasteo = ""; //componente3check;
			int longitud = componente3check.length;
			for(int i=0; i<longitud; i++){
				if((i==0) && i==(longitud-1))
					componente3checkCasteo=componente3check[i];
				if(i==0)
					componente3checkCasteo=componente3check[i]+",";
				if(i==(longitud-1))
					componente3checkCasteo=componente3checkCasteo+componente3check[i];
				else
					componente3checkCasteo=componente3checkCasteo+componente3check[i]+",";
			}

			// Validar campos
			if(nivel==NIVEL_BACHILLERATO){
				if(secundariaProcedencia.equals("")){
					facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar el campo de secundaria") );
					return;
				}
			}
			
			if(nivel==NIVEL_LICENCIATURA){
				if(bachilleratoProcedencia.equals("") && bachilleratoProcedenciaOtra.equals("")){
					facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar el campo de bachillerato de procedencia") );
					return;
				}
			}
			
			if(edad.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar el campo de edad") );
				return;
			}
			
			if(sexo.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar el campo de sexo") );
				return;
			}
			
			// Validar campos
			if(carrera.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar el campo de carrera") );
				return;
			}
			
			// Validar campos
			if(cicloEscolar.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar el campo de ciclo escolar") );
				return;
			}
			
			// Validar campos opcionales
			if(componente2.equals("1")){
				if(componente2a.equals("")){
					facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 2a") );
					return;
				}
			}
			componente2a = componente2a.equals("")?null:componente2a;

			// Compte 3
			if(componente3checkCasteo.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 3") );
				return;
			}
			
			// Validar campos opcionales
			if(componente5.equals("1") || componente5.equals("2")){
				if(componente6.equals("")){
					facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 6") );
					return;
				}
			}
			componente6 = componente6.equals("")?null:componente6;
			
			// Validar numero de cuenta
			String resNumCuenta = validarCuenta(String.valueOf(cuenta));
			
			if(!resNumCuenta.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "El número de cuenta ya fue registrado. !Gracias por participar¡") );
				return;
			}
			
			// Validar Entidad con Carrera
			if(nivel==NIVEL_LICENCIATURA){
				if(entidad != Integer.parseInt(carrera)){
					facesContext.addMessage(null, new FacesMessage("Atención", "Tu carrera no coincide con tu entidad") );
					return;
				}
			}
			
			
			if(nivel==NIVEL_LICENCIATURA){
				// Generar SQL
				sql = "INSERT INTO encuestaRespuestasAlumnosVer1 (llTipoEncuestaID,llVerEncID,llnivel,llCampus,llEntidad,llcuenta,dsBachilleProc,dsBachilleProcOtra,dsEdad,dsSexo,dsCarrera,dsCicloEscolar,llCompte1,llCompte2,llCompte2a,dsCompte3,dsCompte3otro,llCompte4,llCompte5,llCompte6,llCompte7,llCompte8,llCompte9,llCompte10,llCompte11a,llCompte11b,llCompte11c,llCompte11d,llCompte11e,llCompte11f,llCompte11g,llCompte11h,llCompte11i,llCompte11j,llCompte11k,llCompte11l,llCompte11m,dsCompte11n,llCompte12a,llCompte12b,llCompte12c,llCompte12d,llCompte12e) " +
					  "VALUES ("+encuestaID+","+versionEncuestaID+","+nivel+","+campus+","+entidad+","+cuenta+",'"+bachilleratoProcedencia+"','"+bachilleratoProcedenciaOtra+"','"+edad+"','"+sexo+"','"+carrera+"','"+cicloEscolar+"',"+componente1+","+componente2+","+componente2a+",'"+componente3checkCasteo+"','"+componente3Otro+"',"+componente4+","+componente5+","+componente6+","+componente7+","+componente8+","+componente9+","+componente10+","+componente11a+","+componente11b+","+componente11c+","+componente11d+","+componente11e+","+componente11f+","+componente11g+","+componente11h+","+componente11i+","+componente11j+","+componente11k+","+componente11l+","+componente11m+",'"+componente11Otro+"',"+componente12a+","+componente12b+","+componente12c+","+componente12d+","+componente12e+")";
			} if (nivel==NIVEL_BACHILLERATO){
				// Generar SQL
				sql = "INSERT INTO encuestaRespuestasAlumnosVer1 (llTipoEncuestaID,llVerEncID,llnivel,llCampus,llEntidad,llcuenta,dsSecunProc,dsEdad,dsSexo,dsCarrera,dsCicloEscolar,llCompte1,llCompte2,llCompte2a,dsCompte3,dsCompte3otro,llCompte4,llCompte5,llCompte6,llCompte7,llCompte8,llCompte9,llCompte10,llCompte11a,llCompte11b,llCompte11c,llCompte11d,llCompte11e,llCompte11f,llCompte11g,llCompte11h,llCompte11i,llCompte11j,llCompte11k,llCompte11l,llCompte11m,dsCompte11n,llCompte12a,llCompte12b,llCompte12c,llCompte12d,llCompte12e) " +
					  "VALUES ("+encuestaID+","+versionEncuestaID+","+nivel+","+campus+","+entidad+","+cuenta+",'"+secundariaProcedencia+"','"+edad+"','"+sexo+"','"+carrera+"','"+cicloEscolar+"',"+componente1+","+componente2+","+componente2a+",'"+componente3checkCasteo+"','"+componente3Otro+"',"+componente4+","+componente5+","+componente6+","+componente7+","+componente8+","+componente9+","+componente10+","+componente11a+","+componente11b+","+componente11c+","+componente11d+","+componente11e+","+componente11f+","+componente11g+","+componente11h+","+componente11i+","+componente11j+","+componente11k+","+componente11l+","+componente11m+",'"+componente11Otro+"',"+componente12a+","+componente12b+","+componente12c+","+componente12d+","+componente12e+")";
			}

			int res = insertarEncuesta(sql);
			
			if(res>0){
				// Bandera
				session.setAttribute("banDisabledEnvEnc", 0);
				banEnviar = true;
				
				// Mensaje
				facesContext.addMessage(null, new FacesMessage("Gracias", "Tu encuesta fue enviada con éxito.") );
			} else{
				// Mensaje
				facesContext.addMessage(null, new FacesMessage("Atención", "La encuesta no fue enviada. Favor de llenar los todas las preguntas") );
			}
			//sleep 5 seconds
			//Thread.sleep(9000);
			//facesContext.getExternalContext().redirect("/fesaragon/xhtml/compositions/general/welcome.xhtml");
		} catch(Exception e){
			e.getMessage();
		}

	}



	public String getComponente1() {
		return componente1;
	}



	public void setComponente1(String componente1) {
		this.componente1 = componente1;
	}



	public String getComponente2() {
		return componente2;
	}



	public void setComponente2(String componente2) {
		this.componente2 = componente2;
	}



	public String getComponente2a() {
		return componente2a;
	}



	public void setComponente2a(String componente2a) {
		this.componente2a = componente2a;
	}



	public String[] getComponente3check() {
		return componente3check;
	}



	public void setComponente3check(String[] componente3check) {
		this.componente3check = componente3check;
	}



	public String getComponente3Otro() {
		return componente3Otro;
	}



	public void setComponente3Otro(String componente3Otro) {
		this.componente3Otro = componente3Otro;
	}



	public String getComponente4() {
		return componente4;
	}



	public void setComponente4(String componente4) {
		this.componente4 = componente4;
	}



	public String getComponente5() {
		return componente5;
	}



	public void setComponente5(String componente5) {
		this.componente5 = componente5;
	}



	public String getComponente6() {
		return componente6;
	}



	public void setComponente6(String componente6) {
		this.componente6 = componente6;
	}



	public String getComponente7() {
		return componente7;
	}



	public void setComponente7(String componente7) {
		this.componente7 = componente7;
	}



	public String getComponente8() {
		return componente8;
	}



	public void setComponente8(String componente8) {
		this.componente8 = componente8;
	}



	public String getComponente9() {
		return componente9;
	}



	public void setComponente9(String componente9) {
		this.componente9 = componente9;
	}



	public String getComponente10() {
		return componente10;
	}



	public void setComponente10(String componente10) {
		this.componente10 = componente10;
	}



	public String getComponente11a() {
		return componente11a;
	}



	public void setComponente11a(String componente11a) {
		this.componente11a = componente11a;
	}



	public String getComponente11b() {
		return componente11b;
	}



	public void setComponente11b(String componente11b) {
		this.componente11b = componente11b;
	}



	public String getComponente11c() {
		return componente11c;
	}



	public void setComponente11c(String componente11c) {
		this.componente11c = componente11c;
	}



	public String getComponente11d() {
		return componente11d;
	}



	public void setComponente11d(String componente11d) {
		this.componente11d = componente11d;
	}



	public String getComponente11e() {
		return componente11e;
	}



	public void setComponente11e(String componente11e) {
		this.componente11e = componente11e;
	}



	public String getComponente11f() {
		return componente11f;
	}



	public void setComponente11f(String componente11f) {
		this.componente11f = componente11f;
	}



	public String getComponente11g() {
		return componente11g;
	}



	public void setComponente11g(String componente11g) {
		this.componente11g = componente11g;
	}



	public String getComponente11h() {
		return componente11h;
	}



	public void setComponente11h(String componente11h) {
		this.componente11h = componente11h;
	}



	public String getComponente11i() {
		return componente11i;
	}



	public void setComponente11i(String componente11i) {
		this.componente11i = componente11i;
	}



	public String getComponente11j() {
		return componente11j;
	}



	public void setComponente11j(String componente11j) {
		this.componente11j = componente11j;
	}



	public String getComponente11k() {
		return componente11k;
	}



	public void setComponente11k(String componente11k) {
		this.componente11k = componente11k;
	}



	public String getComponente11l() {
		return componente11l;
	}



	public void setComponente11l(String componente11l) {
		this.componente11l = componente11l;
	}



	public String getComponente11m() {
		return componente11m;
	}



	public void setComponente11m(String componente11m) {
		this.componente11m = componente11m;
	}



	public String getComponente11Otro() {
		return componente11Otro;
	}



	public void setComponente11Otro(String componente11Otro) {
		this.componente11Otro = componente11Otro;
	}



	public String getComponente12a() {
		return componente12a;
	}



	public void setComponente12a(String componente12a) {
		this.componente12a = componente12a;
	}



	public String getComponente12b() {
		return componente12b;
	}



	public void setComponente12b(String componente12b) {
		this.componente12b = componente12b;
	}



	public String getComponente12c() {
		return componente12c;
	}



	public void setComponente12c(String componente12c) {
		this.componente12c = componente12c;
	}



	public String getComponente12d() {
		return componente12d;
	}



	public void setComponente12d(String componente12d) {
		this.componente12d = componente12d;
	}



	public String getComponente12e() {
		return componente12e;
	}



	public void setComponente12e(String componente12e) {
		this.componente12e = componente12e;
	}


	public boolean isBanComponente2a() {
		return banComponente2a;
	}


	public void setBanComponente2a(boolean banComponente2a) {
		this.banComponente2a = banComponente2a;
	}


	public boolean isBanComponente5() {
		return banComponente5;
	}


	public void setBanComponente5(boolean banComponente5) {
		this.banComponente5 = banComponente5;
	}

	public String getSecundariaProcedencia() {
		return secundariaProcedencia;
	}

	public void setSecundariaProcedencia(String secundariaProcedencia) {
		this.secundariaProcedencia = secundariaProcedencia;
	}

	public String getBachilleratoProcedencia() {
		return bachilleratoProcedencia;
	}

	public void setBachilleratoProcedencia(String bachilleratoProcedencia) {
		this.bachilleratoProcedencia = bachilleratoProcedencia;
	}

	public String getBachilleratoProcedenciaOtra() {
		return bachilleratoProcedenciaOtra;
	}

	public void setBachilleratoProcedenciaOtra(String bachilleratoProcedenciaOtra) {
		this.bachilleratoProcedenciaOtra = bachilleratoProcedenciaOtra;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getCicloEscolar() {
		return cicloEscolar;
	}

	public void setCicloEscolar(String cicloEscolar) {
		this.cicloEscolar = cicloEscolar;
	}

	public boolean isBadBachilleratoProcedencia() {
		return badBachilleratoProcedencia;
	}

	public void setBadBachilleratoProcedencia(boolean badBachilleratoProcedencia) {
		this.badBachilleratoProcedencia = badBachilleratoProcedencia;
	}


	public boolean isBanEnviar() {
		return banEnviar;
	}


	public void setBanEnviar(boolean banEnviar) {
		this.banEnviar = banEnviar;
	}

	public Map<String, String> getMpCatalogoEntidad() {
		return mpCatalogoEntidad;
	}

	public void setMpCatalogoEntidad(Map<String, String> mpCatalogoEntidad) {
		this.mpCatalogoEntidad = mpCatalogoEntidad;
	}	
	
	
}

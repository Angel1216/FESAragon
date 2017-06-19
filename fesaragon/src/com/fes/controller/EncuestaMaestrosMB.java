package com.fes.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.fes.DAO.EncuestaDAO;

@ManagedBean(name="encuestaMaestrosMB")
public class EncuestaMaestrosMB extends EncuestaDAO{

	// Atributos
	// Datos Personales
	private String antiguedad;
	private String sexo;
	private String carreraFormacion;
	private String nivelProfesor;
	private String[] carrerasImparteAsigProfe;
	private String licenciatura;
	private Date fechaTitulacionLic;
	private String strFechaTitulacionLic;
	private String especialidad;
	private Date fechaTitulacionEsp;
	private String strFechaTitulacionEsp;
	private String maestria;
	private Date fechaTitulacionMaestria;
	private String strFechaTitulacionMaestria;
	private String doctorado;
	private Date fechaTitulacionDoc;
	private String strFechaTitulacionDoc;
	
	// Componentes
	private String componente1;
	private String componente1Comen;
	private String componente2;
	private String componente2Comen;
	private String componente3;
	private String componente3Comen;
	private String componente4;
	private String componente4Comen;
	private String componente5;
	private String componente5Comen;
	private String componente6;
	private String componente6Comen;
	private String componente7;
	private String componente7Comen;
	private String componente8;
	private String componente8Comen;
	private String componente9;
	private String componente9Comen;
	private String componente10;
	private String componente10Comen;
	private String componente11;
	private String componente11Comen;
	private String componente12;
	private String componente12Comen;
	private String componente13;
	private String componente13Comen;
	private String componente14;
	private String componente14Comen;
	private String componente15;
	private String componente15Comen;

	// Utilidades
	private Map<String, String> mpParametrosCatalogo = null;
	private Map<String, String> mpCatalogoEntidad = null;
	private boolean banEnviar;
	
	// Metodos
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
			int nivel = Integer.parseInt( ((session.getAttribute("nivelMaestros")!=null) && (session.getAttribute("nivelMaestros").equals("")==false)) ?session.getAttribute("nivelMaestros").toString():"0" );
			int campus = Integer.parseInt( ((session.getAttribute("campusMaestros")!=null) && (session.getAttribute("campusMaestros").equals("")==false)) ?session.getAttribute("campusMaestros").toString():"0" );
//			int entidad = Integer.parseInt( ((session.getAttribute("entidad")!=null) && (session.getAttribute("entidad").equals("")==false)) ?session.getAttribute("entidad").toString():"0" );
			
			if(nivel==NIVEL_LICENCIATURA){
				mpParametrosCatalogo = new HashMap<String, String>();
	    		mpParametrosCatalogo.put("nivel", String.valueOf(nivel));
	    		mpParametrosCatalogo.put("campus", String.valueOf(campus));
	    		
				mpCatalogoEntidad = obtenCatalogoCombo("docentesLicenciatura", mpParametrosCatalogo);
			} if(nivel==NIVEL_BACHILLERATO){
				mpCatalogoEntidad = obtenCatalogoCombo("docentesBachillerato", mpParametrosCatalogo);
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

	public void nivelImpartido(){
		System.out.print("nivelProfesor: ");
		System.out.println(nivelProfesor);
		
	}
	
	public void confirmarEncuesta(){
		try{
			// Variables y Propiedades
			int longitud = carrerasImparteAsigProfe.length;
			
			// Obtener Formato Estandar de BD
			DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
			
			// Castear Fechas
			if(!licenciatura.trim().equals("") && fechaTitulacionLic!=null){
				strFechaTitulacionLic = "str_to_date('"+fecha.format(fechaTitulacionLic)+"', '%d/%m/%Y' )";
			} else {
				strFechaTitulacionLic = null;
			}
			
			if(!especialidad.trim().equals("") && fechaTitulacionEsp!=null){
				strFechaTitulacionEsp = "str_to_date('"+fecha.format(fechaTitulacionEsp)+"', '%d/%m/%Y' )";
			} else {
				strFechaTitulacionEsp = null;
			}

			if(!maestria.trim().equals("") && fechaTitulacionMaestria!=null){
				strFechaTitulacionMaestria = "str_to_date('"+fecha.format(fechaTitulacionMaestria)+"', '%d/%m/%Y' )";
			} else {
				strFechaTitulacionMaestria = null;
			}
			
			if(!doctorado.trim().equals("") && fechaTitulacionDoc!=null){
				strFechaTitulacionDoc = "str_to_date('"+fecha.format(fechaTitulacionDoc)+"', '%d/%m/%Y' )";
			} else {
				strFechaTitulacionDoc = null;
			}
			
			// Session
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
			
			// Validaciones
			
			if(antiguedad.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar el campo 'Antigüedad'"));
				return;
			}
			
			if(sexo.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar el campo 'Sexo'"));
				return;
			}
			
			if(carreraFormacion.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar el campo 'Carrera de formación'"));
				return;
			}
			
			if(longitud<=0){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de seleccionar las Carrera(s)/Área(s) que imparte"));
				return;
			}
			
			// Validar fechas de titulacion
			if(strFechaTitulacionLic == null){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de ingresar su licenciatura y año de titulación en el apartado 'Grado académico'"));
				return;
			}
			
			if(!especialidad.trim().equals("")){
				if(fechaTitulacionEsp==null){
					facesContext.addMessage(null, new FacesMessage("Atención", "Favor de ingresar el año de titulación de su especialidad en el apartado 'Grado académico'"));
					return;
				}
			}
			
			if(!maestria.trim().equals("")){
				if(fechaTitulacionMaestria==null){
					facesContext.addMessage(null, new FacesMessage("Atención", "Favor de ingresar el año de titulación de su maestría en el apartado 'Grado académico'"));
					return;
				}
			}

			if(!doctorado.trim().equals("")){
				if(fechaTitulacionDoc==null){
					facesContext.addMessage(null, new FacesMessage("Atención", "Favor de ingresar el año de titulación de su doctorado en el apartado 'Grado académico'"));
					return;
				}
			}
			
			// Componentes
			if(componente1.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 1"));
				return;
			}
			
			if(componente2.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 2"));
				return;
			}
			
			if(componente3.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 3"));
				return;
			}
			
			if(componente4.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 4"));
				return;
			}
			
			if(componente5.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 5"));
				return;
			}
			
			if(componente6.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 6"));
				return;
			}
			
			if(componente7.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 7"));
				return;
			}
			
			if(componente8.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 8"));
				return;
			}
			
			if(componente9.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 9"));
				return;
			}
			
			if(componente10.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 10"));
				return;
			}
			
			if(componente11.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 11"));
				return;
			}
			
			if(componente12.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 12"));
				return;
			}
			
			if(componente13.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 13"));
				return;
			}
			
			if(componente14.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 14"));
				return;
			}
			
			if(componente15.equals("")){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de contestar la pregunta 15"));
				return;
			}

			// Datos Encuestado
			int nivel = Integer.parseInt( ((session.getAttribute("nivelMaestros")!=null) && (session.getAttribute("nivelMaestros").equals("")==false)) ?session.getAttribute("nivelMaestros").toString():"0" );
			int campus = Integer.parseInt(  ((session.getAttribute("campusMaestros")!=null) && (session.getAttribute("campusMaestros").equals("")==false)) ? session.getAttribute("campusMaestros").toString():"0" );
			int cuenta = Integer.parseInt( ((session.getAttribute("cuentaMaestros")!=null) && (session.getAttribute("cuentaMaestros").equals("")==false)) ?session.getAttribute("cuentaMaestros").toString():"0" );
						
			if(nivel<=0){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de especificar a qué nivel imparte clases del profesor."));
				return;
			}
			
			if(campus<=0){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de especificar a qué plantel pertenece el profesor."));
				return;
			}
			
			if(cuenta<=0){
				facesContext.addMessage(null, new FacesMessage("Atención", "Favor de especificar el número de trabajador UNAM del profesor."));
				return;
			}
			
			// Abrir Dialogo
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlgEnvEncuestaProfe').show();");
			
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void llenarEncuestaMaestro(){
		
		try{
			
			// Obtener Formato Estandar de BD
			DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
			
			// Castear Fechas
			if(!licenciatura.trim().equals("") && fechaTitulacionLic!=null){
				strFechaTitulacionLic = "str_to_date('"+fecha.format(fechaTitulacionLic)+"', '%d/%m/%Y' )";
			} else {
				strFechaTitulacionLic = null;
			}
			
			if(!especialidad.trim().equals("") && fechaTitulacionEsp!=null){
				strFechaTitulacionEsp = "str_to_date('"+fecha.format(fechaTitulacionEsp)+"', '%d/%m/%Y' )";
			} else {
				strFechaTitulacionEsp = null;
			}

			if(!maestria.trim().equals("") && fechaTitulacionMaestria!=null){
				strFechaTitulacionMaestria = "str_to_date('"+fecha.format(fechaTitulacionMaestria)+"', '%d/%m/%Y' )";
			} else {
				strFechaTitulacionMaestria = null;
			}
			
			if(!doctorado.trim().equals("") && fechaTitulacionDoc!=null){
				strFechaTitulacionDoc = "str_to_date('"+fecha.format(fechaTitulacionDoc)+"', '%d/%m/%Y' )";
			} else {
				strFechaTitulacionDoc = null;
			}
						
			// Session
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
			String sql = "";
						
			// Castear Asignaturas asignadas
			int longitud = carrerasImparteAsigProfe.length;
			String carrerasImparteAsigProfeCasteo = ""; //componente3check;
			
			for(int i=0; i<longitud; i++){
				if((i==0) && i==(longitud-1))
					carrerasImparteAsigProfeCasteo=carrerasImparteAsigProfe[i];
				else if(i==0)
					carrerasImparteAsigProfeCasteo=carrerasImparteAsigProfe[i]+",";
				else if(i==(longitud-1))
					carrerasImparteAsigProfeCasteo=carrerasImparteAsigProfeCasteo+carrerasImparteAsigProfe[i];
				else
					carrerasImparteAsigProfeCasteo=carrerasImparteAsigProfeCasteo+carrerasImparteAsigProfe[i]+",";
			}

			// Datos Encuesta
			int encuestaID=2;
			int versionEncuestaID=1;
			// Datos Encuestado
			int nivel = Integer.parseInt( ((session.getAttribute("nivelMaestros")!=null) && (session.getAttribute("nivelMaestros").equals("")==false)) ?session.getAttribute("nivelMaestros").toString():"0" );
			int campus = Integer.parseInt(  ((session.getAttribute("campusMaestros")!=null) && (session.getAttribute("campusMaestros").equals("")==false)) ? session.getAttribute("campusMaestros").toString():"0" );
			int cuenta = Integer.parseInt( ((session.getAttribute("cuentaMaestros")!=null) && (session.getAttribute("cuentaMaestros").equals("")==false)) ?session.getAttribute("cuentaMaestros").toString():"0" );

			// Generar SQL
			sql = "INSERT INTO encuestaRespuestasMaestrosVer1 (llTipoEncuestaID,llVerEncID,llnivel,llCampus,llcuenta,llAntiguedad,llsexo,dsCarreraFormacion,dsCarrerasImparteAsigProfeCasteo,dsLicenciatura,dtfechaTitulacionLic,dsEspecialidad,dtFechaTitulacionEsp,dsMaestria,dtFechaTitulacionMaestria,dsDoctorado,dtFechaTitulacionDoc,llCompte1,dsCompte1Coment,llCompte2,dsCompte2Coment,llCompte3,dsCompte3Coment,llCompte4,dsCompte4Coment,llCompte5,dsCompte5Coment,llCompte6,dsCompte6Coment,llCompte7,dsCompte7Coment,llCompte8,dsCompte8Coment,llCompte9,dsCompte9Coment,llCompte10,dsCompte10Coment,llCompte11,dsCompte11Coment,llCompte12,dsCompte12Coment,llCompte13,dsCompte13Coment,llCompte14,dsCompte14Coment,llCompte15,dsCompte15Coment,fechaRegistro) " +
					"VALUES ("+encuestaID+","+versionEncuestaID+","+nivel+","+campus+","+cuenta+","+antiguedad+","+sexo+",'"+carreraFormacion+"','"+carrerasImparteAsigProfeCasteo+"','"+licenciatura+"',"+strFechaTitulacionLic+",'"+especialidad+"',"+strFechaTitulacionEsp+",'"+maestria+"',"+strFechaTitulacionMaestria+",'"+doctorado+"',"+strFechaTitulacionDoc+","+componente1+",'"+componente1Comen+"',"+componente2+",'"+componente2Comen+"',"+componente3+",'"+componente3Comen+"',"+componente4+",'"+componente4Comen+"',"+componente5+",'"+componente5Comen+"',"+componente6+",'"+componente6Comen+"',"+componente7+",'"+componente7Comen+"',"+componente8+",'"+componente8Comen+"',"+componente9+",'"+componente9Comen+"',"+componente10+",'"+componente10Comen+"',"+componente11+",'"+componente11Comen+"',"+componente12+",'"+componente12Comen+"',"+componente13+",'"+componente13Comen+"',"+componente14+",'"+componente14Comen+"',"+componente15+",'"+componente15Comen+"',SYSDATE())";

			System.out.print("sql: ");
			System.out.println(sql);
			
			System.out.print("Inicia Afectacion BD");
			// Validar Exc BD
			int res = insertarEncuesta(sql);
			System.out.print("Termina Afectacion BD");
			
			if(res>0){
				// Bandera
				session.setAttribute("banDisabledEnvEnc", 0);
				banEnviar = true;
				
				// Mensaje
				facesContext.addMessage(null, new FacesMessage("Gracias", "Tu encuesta fue enviada con éxito.") );
				
				// Redireccionar a Pagina de Inicio
				facesContext.getExternalContext().redirect("/fesaragon/xhtml/compositions/general/welcome.xhtml");
			} else{
				// Mensaje
				facesContext.addMessage(null, new FacesMessage("Atención", "La encuesta no fue enviada. Favor de llenar los todas las preguntas") );
			}

		} catch(Exception e){
			e.getMessage();
		}

	}
	
	
	// Getters and Setters
	public String getAntiguedad() {
		return antiguedad;
	}
	public void setAntiguedad(String antiguedad) {
		this.antiguedad = antiguedad;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getCarreraFormacion() {
		return carreraFormacion;
	}
	public void setCarreraFormacion(String carreraFormacion) {
		this.carreraFormacion = carreraFormacion;
	}
	public String getNivelProfesor() {
		return nivelProfesor;
	}
	public void setNivelProfesor(String nivelProfesor) {
		this.nivelProfesor = nivelProfesor;
	}
	public String[] getCarrerasImparteAsigProfe() {
		return carrerasImparteAsigProfe;
	}
	public void setCarrerasImparteAsigProfe(String[] carrerasImparteAsigProfe) {
		this.carrerasImparteAsigProfe = carrerasImparteAsigProfe;
	}
	public String getLicenciatura() {
		return licenciatura;
	}
	public void setLicenciatura(String licenciatura) {
		this.licenciatura = licenciatura;
	}
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	public String getMaestria() {
		return maestria;
	}
	public void setMaestria(String maestria) {
		this.maestria = maestria;
	}
	public String getDoctorado() {
		return doctorado;
	}
	public void setDoctorado(String doctorado) {
		this.doctorado = doctorado;
	}
	public String getComponente1() {
		return componente1;
	}
	public void setComponente1(String componente1) {
		this.componente1 = componente1;
	}
	public String getComponente1Comen() {
		return componente1Comen;
	}
	public void setComponente1Comen(String componente1Comen) {
		this.componente1Comen = componente1Comen;
	}
	public String getComponente2() {
		return componente2;
	}
	public void setComponente2(String componente2) {
		this.componente2 = componente2;
	}
	public String getComponente2Comen() {
		return componente2Comen;
	}
	public void setComponente2Comen(String componente2Comen) {
		this.componente2Comen = componente2Comen;
	}
	public String getComponente3() {
		return componente3;
	}
	public void setComponente3(String componente3) {
		this.componente3 = componente3;
	}
	public String getComponente3Comen() {
		return componente3Comen;
	}
	public void setComponente3Comen(String componente3Comen) {
		this.componente3Comen = componente3Comen;
	}
	public String getComponente4() {
		return componente4;
	}
	public void setComponente4(String componente4) {
		this.componente4 = componente4;
	}
	public String getComponente4Comen() {
		return componente4Comen;
	}
	public void setComponente4Comen(String componente4Comen) {
		this.componente4Comen = componente4Comen;
	}
	public String getComponente5() {
		return componente5;
	}
	public void setComponente5(String componente5) {
		this.componente5 = componente5;
	}
	public String getComponente5Comen() {
		return componente5Comen;
	}
	public void setComponente5Comen(String componente5Comen) {
		this.componente5Comen = componente5Comen;
	}
	public String getComponente6() {
		return componente6;
	}
	public void setComponente6(String componente6) {
		this.componente6 = componente6;
	}
	public String getComponente6Comen() {
		return componente6Comen;
	}
	public void setComponente6Comen(String componente6Comen) {
		this.componente6Comen = componente6Comen;
	}
	public String getComponente7() {
		return componente7;
	}
	public void setComponente7(String componente7) {
		this.componente7 = componente7;
	}
	public String getComponente7Comen() {
		return componente7Comen;
	}
	public void setComponente7Comen(String componente7Comen) {
		this.componente7Comen = componente7Comen;
	}
	public String getComponente8() {
		return componente8;
	}
	public void setComponente8(String componente8) {
		this.componente8 = componente8;
	}
	public String getComponente8Comen() {
		return componente8Comen;
	}
	public void setComponente8Comen(String componente8Comen) {
		this.componente8Comen = componente8Comen;
	}
	public String getComponente9() {
		return componente9;
	}
	public void setComponente9(String componente9) {
		this.componente9 = componente9;
	}
	public String getComponente9Comen() {
		return componente9Comen;
	}
	public void setComponente9Comen(String componente9Comen) {
		this.componente9Comen = componente9Comen;
	}
	public String getComponente10() {
		return componente10;
	}
	public void setComponente10(String componente10) {
		this.componente10 = componente10;
	}
	public String getComponente10Comen() {
		return componente10Comen;
	}
	public void setComponente10Comen(String componente10Comen) {
		this.componente10Comen = componente10Comen;
	}
	public String getComponente11() {
		return componente11;
	}
	public void setComponente11(String componente11) {
		this.componente11 = componente11;
	}
	public String getComponente11Comen() {
		return componente11Comen;
	}
	public void setComponente11Comen(String componente11Comen) {
		this.componente11Comen = componente11Comen;
	}
	public String getComponente12() {
		return componente12;
	}
	public void setComponente12(String componente12) {
		this.componente12 = componente12;
	}
	public String getComponente12Comen() {
		return componente12Comen;
	}
	public void setComponente12Comen(String componente12Comen) {
		this.componente12Comen = componente12Comen;
	}
	public String getComponente13() {
		return componente13;
	}
	public void setComponente13(String componente13) {
		this.componente13 = componente13;
	}
	public String getComponente13Comen() {
		return componente13Comen;
	}
	public void setComponente13Comen(String componente13Comen) {
		this.componente13Comen = componente13Comen;
	}
	public String getComponente14() {
		return componente14;
	}
	public void setComponente14(String componente14) {
		this.componente14 = componente14;
	}
	public String getComponente14Comen() {
		return componente14Comen;
	}
	public void setComponente14Comen(String componente14Comen) {
		this.componente14Comen = componente14Comen;
	}
	public String getComponente15() {
		return componente15;
	}
	public void setComponente15(String componente15) {
		this.componente15 = componente15;
	}
	public String getComponente15Comen() {
		return componente15Comen;
	}
	public void setComponente15Comen(String componente15Comen) {
		this.componente15Comen = componente15Comen;
	}
	public Map<String, String> getMpParametrosCatalogo() {
		return mpParametrosCatalogo;
	}
	public void setMpParametrosCatalogo(Map<String, String> mpParametrosCatalogo) {
		this.mpParametrosCatalogo = mpParametrosCatalogo;
	}
	public Map<String, String> getMpCatalogoEntidad() {
		return mpCatalogoEntidad;
	}
	public void setMpCatalogoEntidad(Map<String, String> mpCatalogoEntidad) {
		this.mpCatalogoEntidad = mpCatalogoEntidad;
	}
	public boolean isBanEnviar() {
		return banEnviar;
	}
	public void setBanEnviar(boolean banEnviar) {
		this.banEnviar = banEnviar;
	}

	public String getStrFechaTitulacionLic() {
		return strFechaTitulacionLic;
	}

	public void setStrFechaTitulacionLic(String strFechaTitulacionLic) {
		this.strFechaTitulacionLic = strFechaTitulacionLic;
	}

	public String getStrFechaTitulacionEsp() {
		return strFechaTitulacionEsp;
	}

	public void setStrFechaTitulacionEsp(String strFechaTitulacionEsp) {
		this.strFechaTitulacionEsp = strFechaTitulacionEsp;
	}

	public String getStrFechaTitulacionMaestria() {
		return strFechaTitulacionMaestria;
	}

	public void setStrFechaTitulacionMaestria(String strFechaTitulacionMaestria) {
		this.strFechaTitulacionMaestria = strFechaTitulacionMaestria;
	}

	public String getStrFechaTitulacionDoc() {
		return strFechaTitulacionDoc;
	}

	public void setStrFechaTitulacionDoc(String strFechaTitulacionDoc) {
		this.strFechaTitulacionDoc = strFechaTitulacionDoc;
	}

	public void setFechaTitulacionLic(Date fechaTitulacionLic) {
		this.fechaTitulacionLic = fechaTitulacionLic;
	}

	public void setFechaTitulacionEsp(Date fechaTitulacionEsp) {
		this.fechaTitulacionEsp = fechaTitulacionEsp;
	}

	public void setFechaTitulacionMaestria(Date fechaTitulacionMaestria) {
		this.fechaTitulacionMaestria = fechaTitulacionMaestria;
	}

	public void setFechaTitulacionDoc(Date fechaTitulacionDoc) {
		this.fechaTitulacionDoc = fechaTitulacionDoc;
	}

	public Date getFechaTitulacionLic() {
		return fechaTitulacionLic;
	}

	public Date getFechaTitulacionEsp() {
		return fechaTitulacionEsp;
	}

	public Date getFechaTitulacionMaestria() {
		return fechaTitulacionMaestria;
	}

	public Date getFechaTitulacionDoc() {
		return fechaTitulacionDoc;
	}
	
}
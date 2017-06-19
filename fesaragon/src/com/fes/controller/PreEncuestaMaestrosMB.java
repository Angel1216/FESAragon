package com.fes.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fes.DAO.DM.EjemploAccesoBD1;

import com.fes.DAO.PreEncuestaAlumnosDAO;
import com.fes.DAO.PreEncuestaMaestrosDAO;
import com.fes.bean.DtoEncuestaRespuestasMaestrosVer1;
import com.fes.bean.dtoUsuario;
import com.fes.bean.encuesta.doc.totales.DtoEncuRespDocVer1Totales;
import com.fes.bean.encuesta.totales.DtoEncuRespAlumVer1Totales;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;

@ManagedBean(name="preEncuestaMaestrosMB")
@SessionScoped
public class PreEncuestaMaestrosMB extends PreEncuestaMaestrosDAO implements Serializable{

	private static final long serialVersionUID = 1L;

    private String nivel;   
    private String campus;
//    private String entidad;
    private String cuenta;
    private String username;    
    private String password;
    private boolean banEntidad;
    private Map<String,String> countries;
    private Map<String, String> mpCatalogoNivel = null;
    private Map<String,String> mpCatalogoCampus = null;
    private Map<String, String> mpCatalogoEntidad = null;
    private Map<String, String> mpTipoCatalogo = null;
    private Map<String, String> mpParametrosCatalogo = null;
    private List<DtoEncuestaRespuestasMaestrosVer1> lstEncuestaRespuestasMaestrosVer1;
    private FacesContext facesContext;
    private List<DtoEncuRespDocVer1Totales> lstEncuRespDocVer1Totales;
    private List<dtoUsuario> lstUsuario;
    private StreamedContent file;
    private String realPath="";
    private String pathArchivo;
    
    @PostConstruct
    public void init() {
    	
    	// Tipo de Catalogos
    	mpTipoCatalogo = new HashMap<String, String>();
    	mpTipoCatalogo.put("nivel", "nivel");
    	mpTipoCatalogo.put("campus", "campus");
//    	mpTipoCatalogo.put("entidad", "entidad");

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
//        	mpCatalogoEntidad = new HashMap<String, String>();
        }else{
        	mpCatalogoCampus = new HashMap<String, String>();
//        	mpCatalogoEntidad = new HashMap<String, String>();
        }
    }
/*    
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
    		
//        	mpCatalogoEntidad = obtenCatalogoCombo(mpTipoCatalogo.get("entidad").toString(), mpParametrosCatalogo);
        }
        else{
        	mpCatalogoEntidad = new HashMap<String, String>();
        }
    }
*/    
    
    public int validarCamposLlenarEncuesta(){
    	// Variables
    	int exitoso=0;
    	int fallido=1;
    	int res=exitoso;
    	
    	// Faces
		facesContext = FacesContext.getCurrentInstance();
		
		// Validacion Numero de Cuenta
		if(cuenta.trim().equals("")){
			facesContext.addMessage(null, new FacesMessage("Atención", "Favor de ingresar tu número de trabajador UNAM") );
			res=fallido;
		}
		if(!cuenta.trim().equals("") && cuenta.trim().length()<5){
			facesContext.addMessage(null, new FacesMessage("Atención", "Tu número de trabajador UNAM debe contener un mínimo de cinco caracteres y un máximo de siete caracteres numéricos.") );
			res=fallido;
		}
		
		if(nivel.trim().equals("")){
			facesContext.addMessage(null, new FacesMessage("Atención", "Favor de ingresar tu nivel") );
			res=fallido;
		}
		
		if(campus.trim().equals("")){
			facesContext.addMessage(null, new FacesMessage("Atención", "Favor de ingresar tu campus") );
			res=fallido;
		}
		
		return res;
    }

    public void llenarEncuesta() {
    	
    	// Validar Campos Obligatorios
    	int fallido=1;
    	int res=validarCamposLlenarEncuesta();
    	
    	if(res==fallido)
    		return;
    	
    	// Subir a sesion los datos a llenar en la encuesta
		facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		session.setAttribute("nivelMaestros", nivel);
		session.setAttribute("campusMaestros", campus);
//		session.setAttribute("entidadMaestros", entidad);
		session.setAttribute("cuentaMaestros", cuenta);

		if(Integer.parseInt(nivel)==NIVEL_LICENCIATURA){	// Nivel Licenciatura
			try {
				// validamos para redireccionar
				if(session!=null)
					session.setAttribute("banDisabledEnvEnc", 1);
					String sepSys = System.getProperty("file.separator");
					String url = sepSys+"fesaragon"+sepSys+"xhtml"+sepSys+"compositions"+sepSys+"general"+sepSys+"encuestas"+sepSys+"maestros"+sepSys+"encuestaMaestro.xhtml";
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
					String url = sepSys+"fesaragon"+sepSys+"xhtml"+sepSys+"compositions"+sepSys+"general"+sepSys+"encuestas"+sepSys+"maestros"+sepSys+"encuestaMaestro.xhtml";
					facesContext.getExternalContext().redirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    /*
     * Metodos para Ingresar a las Encuestas respondidas de los Maestros
     */
    public int validarCamposLogin(){
    	// Variables
    	int exitoso=0;
    	int fallido=1;
    	int res=exitoso;
    	
    	// Faces
		facesContext = FacesContext.getCurrentInstance();
		
		// Validacion Numero de Cuenta
		if(username.trim().equals("")){
			facesContext.addMessage(null, new FacesMessage("Atención", "Favor de ingresar tu usuario") );
			res=fallido;
		}
		
		if(password.trim().equals("")){
			facesContext.addMessage(null, new FacesMessage("Atención", "Favor de ingresar tu contraseña") );
			res=fallido;
		}
		
		return res;
    }
    
    public void login(ActionEvent event) {
    	
    	// Validar Campos Obligatorios
    	int fallido=1;
    	int res=validarCamposLogin();
    	
    	if(res==fallido)
    		return;
    	
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
        lstUsuario = new ArrayList<dtoUsuario>();
        boolean banUsuario = false;
        boolean banPassword = false;
        lstEncuestaRespuestasMaestrosVer1 = null;
        facesContext = FacesContext.getCurrentInstance();
        
        try{
        	// Validar Usuario
            lstUsuario = validarUsuario(username,password);
            
        	if(lstUsuario.size()==0){
            	loggedIn = false;
            	message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "Usuario Invalido");
            	FacesContext.getCurrentInstance().addMessage(null, message);
    	        context.addCallbackParam("loggedIn", loggedIn);
            	return;
            } else {
            	banUsuario = true;
            }
        	
        	// Validar Password
        	if(lstUsuario.size()>0){
        		String passwordEncrypt = lstUsuario.get(0).getDsPassword();
        		String passwordSinEncrypt = validarPassword(passwordEncrypt);
        		
        		if(!password.equals(passwordSinEncrypt)){
        			loggedIn = false;
                	message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "Contraseña Invalida");
                	FacesContext.getCurrentInstance().addMessage(null, message);
        	        context.addCallbackParam("loggedIn", loggedIn);
                	return;
        		} else {
        			banPassword = true;
        		}
        	}
        	
	        if(banUsuario==true && banPassword==true) {
	        	String usuarioDocente="enc_docente_UNAM2016";
	        	if(username.equals(usuarioDocente)){
	        		
	        		/* // Redireccionar a la pagina Correcta
					String sepSys = System.getProperty("file.separator");
		            String url = sepSys+"fesaragon"+sepSys+"xhtml"+sepSys+"compositions"+sepSys+"general"+sepSys+"encuestas"+sepSys+"maestros"+sepSys+"preencuesta.xhtml";
		            facesContext.getExternalContext().redirect(url);*/
	        		
	        		loggedIn = false;
	            	message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "Usuario Invalido");
	            	FacesContext.getCurrentInstance().addMessage(null, message);
	    	        context.addCallbackParam("loggedIn", loggedIn);
	            	return;
	        		
	        	} else {
		            loggedIn = true;
		            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
		            //session = (HttpSession) facesContext.getExternalContext().getSession(false);
		            
					// Recuperar encuestas guardadas de acuerdo al perfil y permisos
					lstEncuestaRespuestasMaestrosVer1 = recuperarEncuestasMaestros(lstUsuario);
					
					if(lstEncuestaRespuestasMaestrosVer1!=null && lstEncuestaRespuestasMaestrosVer1.size()>0){
						System.out.println("El tamaño de la Lista de Encuestas de Maestros es de: " + lstEncuestaRespuestasMaestrosVer1.size());
						
						lstEncuRespDocVer1Totales = generarListaEncuestaGlobalDocentes(lstUsuario,lstEncuestaRespuestasMaestrosVer1);
						System.out.println("Tamaño de la lista de Globales (lstEncuRespDocVer1Totales): " + lstEncuRespDocVer1Totales.size());
						
						// Redireccionar a la pagina Correcta
						String sepSys = System.getProperty("file.separator");
			            String url = sepSys+"fesaragon"+sepSys+"xhtml"+sepSys+"compositions"+sepSys+"administrador"+sepSys+"encuestas"+sepSys+"consultar"+sepSys+"conEncDoc.xhtml";
			            facesContext.getExternalContext().redirect(url);
			            
			            return;
					} else{
						System.out.println("El campus no cuenta con Encuestas Registradas");
						
						lstEncuRespDocVer1Totales = null;
						message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención: No existen encuestas registradas para tu perfil.", "No existen encuestas registradas");
					}
	        	}
				
				
				
	        } else {
	            loggedIn = false;
	            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
	        }
	         
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        context.addCallbackParam("loggedIn", loggedIn);
        } catch(Exception ex){
        	ex.getMessage();
        }
    }
    
    /*
     * Metodo para generar la descarga del archivo de Excel con las encuestas de los maestros aplicadas
     */
    public void exportarExcel(){
    	System.out.println("Entra al metodo para generar el Excel y comenzar la descarga");
    	
    	// Llenar la cabecera y el contenido del excel
   	 	dataModelReporte(lstEncuestaRespuestasMaestrosVer1);
   	 	
   	 	// Hacer el excel con su cabecera y contenido
   	 	HSSFWorkbook hssfWorkbook = obtenerExcel(getDataModelContenido(),getDataModelCabecera(), "Encuesta 40 Aniversario");
   	 	
	   	 try {
	         // Obtener direccion absoluta del proyecto
	   	  ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	   	  realPath=(String) servletContext.getRealPath("/"); // Sustituye "/" por el directorio ej: "/upload"
	   	  System.out.println("realPath (maybe SOL): " + realPath);
	   	  String nombreArchivo = username+".xls";
	   	  pathArchivo = realPath +  nombreArchivo;
	   	  System.out.println("Ruta de almacenamiento del Reporte Excel: " + pathArchivo);
	   		 
	   	  // Guardar el Archivo
	   	  FileOutputStream fileOutputStream = new FileOutputStream(pathArchivo);
	   	  hssfWorkbook.write(fileOutputStream);
	   	  fileOutputStream.close();
	   	 
	         InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(System.getProperty("file.separator")+nombreArchivo);
	         file = new DefaultStreamedContent(stream, "application/xls", nombreArchivo);
	         
	   	  // Eliminar el fichero de la carpeta Temporal
			  //File tempFile = new File(pathArchivo);
			  //tempFile.deleteOnExit();
	   	 
	   	 } catch (Exception e) {
	   	  e.printStackTrace();
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

//	public String getEntidad() {
//		return entidad;
//	}
//
//	public void setEntidad(String entidad) {
//		this.entidad = entidad;
//	}

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


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<DtoEncuestaRespuestasMaestrosVer1> getLstEncuestaRespuestasMaestrosVer1() {
		return lstEncuestaRespuestasMaestrosVer1;
	}


	public void setLstEncuestaRespuestasMaestrosVer1(
			List<DtoEncuestaRespuestasMaestrosVer1> lstEncuestaRespuestasMaestrosVer1) {
		this.lstEncuestaRespuestasMaestrosVer1 = lstEncuestaRespuestasMaestrosVer1;
	}


	public List<DtoEncuRespDocVer1Totales> getLstEncuRespDocVer1Totales() {
		return lstEncuRespDocVer1Totales;
	}


	public void setLstEncuRespDocVer1Totales(
			List<DtoEncuRespDocVer1Totales> lstEncuRespDocVer1Totales) {
		this.lstEncuRespDocVer1Totales = lstEncuRespDocVer1Totales;
	}


	public void setCountries(Map<String, String> countries) {
		this.countries = countries;
	}

	public List<dtoUsuario> getLstUsuario() {
		return lstUsuario;
	}


	public void setLstUsuario(List<dtoUsuario> lstUsuario) {
		this.lstUsuario = lstUsuario;
	}


	public StreamedContent getFile() {
		return file;
	}


	public void setFile(StreamedContent file) {
		this.file = file;
	}


	public String getPathArchivo() {
		return pathArchivo;
	}


	public void setPathArchivo(String pathArchivo) {
		this.pathArchivo = pathArchivo;
	}
	
	
	
}

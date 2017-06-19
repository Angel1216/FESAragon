package com.fes.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.fes.DAO.ConsultarEncuestasDAO;
import com.fes.bean.DtoEncuestaRespuestasAlumnosVer1;
import com.fes.bean.DtoEncuestaRespuestasMaestrosVer1;
import com.fes.bean.dtoUsuario;
import com.fes.bean.encuesta.totales.DtoEncuRespAlumVer1Totales;

@ManagedBean(name="consultarEncuestasMB")
//@ViewScoped
@SessionScoped
public class ConsultarEncuestasMB extends ConsultarEncuestasDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String username;    
    private String password;
    private String pathArchivo;
    private int menuSelected;
    private FacesContext facesContext;
    private HttpSession session;
    private StreamedContent file;
    
    private String nivel;   
    private String campus;
    private String entidad;
    private String cuenta;
    private Map<String,String> countries;
    private Map<String, String> mpCatalogoNivel = null;
    private Map<String,String> mpCatalogoCampus = null;
    private Map<String, String> mpCatalogoEntidad = null;
    private Map<String, String> mpTipoCatalogo = null;
    private Map<String, String> mpParametrosCatalogo = null;
    
    private List<DtoEncuRespAlumVer1Totales> lstEncuRespAlumVer1Totales;
    private List<DtoEncuestaRespuestasAlumnosVer1> lstEncuestaRespuestasAlumnosVer1;
    
    
    private String ejemplo;
    private String realPath="";
    
    
    
    @PostConstruct
    public void init() {
    	// Tipo de Catalogos
    	mpTipoCatalogo = new HashMap<String, String>();
    	mpTipoCatalogo.put("nivel", "nivel");
    	mpTipoCatalogo.put("campus", "campus");
    	mpTipoCatalogo.put("entidad", "entidad");

    	mpParametrosCatalogo = new HashMap<String, String>();
    	mpCatalogoNivel = new HashMap<String, String>();
    	mpCatalogoNivel = obtenCatalogoCombo(mpTipoCatalogo.get("nivel").toString(),mpParametrosCatalogo);
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
    		
        	mpCatalogoEntidad = obtenCatalogoCombo(mpTipoCatalogo.get("entidad").toString(), mpParametrosCatalogo);
        }
        else{
        	mpCatalogoEntidad = new HashMap<String, String>();
        }
    }
    
    public void menu(String menu){
    	if(Integer.parseInt(menu)==MENU_ConsultarEncuestaAlumno){
    		System.out.println("MENU_ConsultarEncuestaAlumno");
    		menuSelected = MENU_ConsultarEncuestaAlumno;
    		System.out.println("menuSelected: " + menuSelected);
    	} else if(Integer.parseInt(menu)==MENU_LlenarEncuestaDocentes){
    		System.out.println("MENU_LlenarEncuestaDocentes");
    		menuSelected = MENU_LlenarEncuestaDocentes;
    		System.out.println("menuSelected: " + menuSelected);
    	} else if(Integer.parseInt(menu)==MENU_ConsultarEncuestaDocentes){
    		System.out.println("MENU_ConsultarEncuestaDocentes");
    		menuSelected = MENU_ConsultarEncuestaDocentes;
    		System.out.println("menuSelected: " + menuSelected);
    	}
    }
    
    public void loginMenu(ActionEvent event) {
    	if(menuSelected==MENU_ConsultarEncuestaAlumno){
    		System.out.println("MENU_ConsultarEncuestaAlumno");
//    		loginConEnc(event);
    	} else if(menuSelected==MENU_LlenarEncuestaDocentes){
    		System.out.println("MENU_LlenarEncuestaDocentes");
//    		loginConEnc(event);
    	} else if(menuSelected==MENU_ConsultarEncuestaDocentes){
    		System.out.println("MENU_ConsultarEncuestaDocentes");
//    		loginConEncDoc(event);
    	}
    }
    
    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
        List<dtoUsuario> lstUsuario = new ArrayList<dtoUsuario>();
        boolean banUsuario = false;
        boolean banPassword = false;
        lstEncuestaRespuestasAlumnosVer1 = null;
        facesContext = FacesContext.getCurrentInstance();
        
        System.out.print("Menu Seleccionado: ");
        System.out.println(menuSelected);
        
        try{
        	// Validar Usuario
            lstUsuario = validarUsuario(username,password);
            
        	if(lstUsuario.size()==0){
            	loggedIn = false;
            	message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario Invalido", "Usuario Invalido");
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
                	message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Contraseña Invalido", "Usuario Invalido");
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
	        		
	        		// Redireccionar a la pagina Correcta
					String sepSys = System.getProperty("file.separator");
		            String url = sepSys+"fesaragon"+sepSys+"xhtml"+sepSys+"compositions"+sepSys+"general"+sepSys+"encuestas"+sepSys+"maestros"+sepSys+"preencuesta.xhtml";
		            facesContext.getExternalContext().redirect(url);
		            return;
	        	} else {
		            loggedIn = true;
		            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
		            //session = (HttpSession) facesContext.getExternalContext().getSession(false);
		            
					// Recuperar encuestas guardadas de acuerdo al perfil y permisos
					lstEncuestaRespuestasAlumnosVer1 = recuperarEncuestas(lstUsuario);
					
					if(lstEncuestaRespuestasAlumnosVer1!=null && lstEncuestaRespuestasAlumnosVer1.size()>0){
						System.out.println("El tamaño de la Lista de Encuestas es de: " + lstEncuestaRespuestasAlumnosVer1.size());
						
						lstEncuRespAlumVer1Totales = generarListaEncuestaGlobal(lstUsuario,lstEncuestaRespuestasAlumnosVer1);
						System.out.println("Tamaño de la lista de Globales (lstEncuRespAlumVer1Totales): " + lstEncuRespAlumVer1Totales.size());
						
						// Redireccionar a la pagina Correcta
						String sepSys = System.getProperty("file.separator");
			            String url = sepSys+"fesaragon"+sepSys+"xhtml"+sepSys+"compositions"+sepSys+"administrador"+sepSys+"encuestas"+sepSys+"consultar"+sepSys+"consultarEncuestas.xhtml";
			            facesContext.getExternalContext().redirect(url);
			            return;
					} else{
						System.out.println("El campus no cuenta con Encuestas Registradas");
						lstEncuRespAlumVer1Totales = null;
						message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención: No existen encuestas registradas para tu perfil.", "No existen encuestas registradas");
				        FacesContext.getCurrentInstance().addMessage(null, message);
				        context.addCallbackParam("loggedIn", loggedIn);
				        return;
					}
	        	}
				
	        } else {
	            loggedIn = false;
	            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
	        }
	         
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        context.addCallbackParam("loggedIn", loggedIn);
	        return;
        } catch(Exception ex){
        	ex.getMessage();
        }
    }

    /*
     * Metodo principal para generar el reporte en excel
     */
    public void exportarExcel() {
    	 // Llenar la cabecera y el contenido del excel
    	 dataModelReporte(lstEncuestaRespuestasAlumnosVer1);
    	 
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

	public List<DtoEncuRespAlumVer1Totales> getLstEncuRespAlumVer1Totales() {
		return lstEncuRespAlumVer1Totales;
	}

	public void setLstEncuRespAlumVer1Totales(List<DtoEncuRespAlumVer1Totales> lstEncuRespAlumVer1Totales) {
		this.lstEncuRespAlumVer1Totales = lstEncuRespAlumVer1Totales;
	}

	public String getEjemplo() {
		return ejemplo;
	}

	public void setEjemplo(String ejemplo) {
		this.ejemplo = ejemplo;
	}

	public String getPathArchivo() {
		return pathArchivo;
	}

	public void setPathArchivo(String pathArchivo) {
		this.pathArchivo = pathArchivo;
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public int getMenuSelected() {
		return menuSelected;
	}

	public void setMenuSelected(int menuSelected) {
		this.menuSelected = menuSelected;
	}
	
}

package com.fes.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.fes.bean.DtoEncuestaRespuestasAlumnosVer1;
import com.fes.bean.DtoEncuestaRespuestasMaestrosVer1;
import com.fes.bean.dtoUsuario;
import com.fes.bean.encuesta.doc.totales.Antiguedad;
import com.fes.bean.encuesta.doc.totales.CarreraFormacion;
import com.fes.bean.encuesta.doc.totales.DsCompteComent;
import com.fes.bean.encuesta.doc.totales.DtoEncuRespDocVer1Totales;
import com.fes.bean.encuesta.doc.totales.LlCompte;
import com.fes.bean.encuesta.totales.AnioSemestre;
import com.fes.bean.encuesta.totales.AreaCarrera;
import com.fes.bean.encuesta.totales.BachoProc;
import com.fes.bean.encuesta.totales.DsCompte3;
import com.fes.bean.encuesta.totales.DtoEncuRespAlumVer1Totales;
import com.fes.bean.encuesta.totales.Edad;
import com.fes.bean.encuesta.totales.LlCompte1;
import com.fes.bean.encuesta.totales.LlCompte10;
import com.fes.bean.encuesta.totales.LlCompte11;
import com.fes.bean.encuesta.totales.LlCompte11Otro;
import com.fes.bean.encuesta.totales.LlCompte11a;
import com.fes.bean.encuesta.totales.LlCompte11b;
import com.fes.bean.encuesta.totales.LlCompte11c;
import com.fes.bean.encuesta.totales.LlCompte11d;
import com.fes.bean.encuesta.totales.LlCompte11e;
import com.fes.bean.encuesta.totales.LlCompte11f;
import com.fes.bean.encuesta.totales.LlCompte11g;
import com.fes.bean.encuesta.totales.LlCompte11h;
import com.fes.bean.encuesta.totales.LlCompte11i;
import com.fes.bean.encuesta.totales.LlCompte11j;
import com.fes.bean.encuesta.totales.LlCompte11k;
import com.fes.bean.encuesta.totales.LlCompte11l;
import com.fes.bean.encuesta.totales.LlCompte11m;
import com.fes.bean.encuesta.totales.LlCompte12;
import com.fes.bean.encuesta.totales.LlCompte12a;
import com.fes.bean.encuesta.totales.LlCompte12b;
import com.fes.bean.encuesta.totales.LlCompte12c;
import com.fes.bean.encuesta.totales.LlCompte12d;
import com.fes.bean.encuesta.totales.LlCompte12e;
import com.fes.bean.encuesta.totales.LlCompte2;
import com.fes.bean.encuesta.totales.LlCompte2a;
import com.fes.bean.encuesta.totales.LlCompte4;
import com.fes.bean.encuesta.totales.LlCompte5;
import com.fes.bean.encuesta.totales.LlCompte6;
import com.fes.bean.encuesta.totales.LlCompte7;
import com.fes.bean.encuesta.totales.LlCompte8;
import com.fes.bean.encuesta.totales.LlCompte9;
import com.fes.bean.encuesta.totales.OtraBachillerato;
import com.fes.bean.encuesta.totales.SecuProc;
import com.fes.bean.encuesta.doc.totales.Sexo;
import com.fes.utilities.StringEncrypt;

public class PreEncuestaMaestrosDAO extends DAO {

	private DataModel dataModelCabecera;
	private DataModel dataModelContenido;
	
	// MAPAS	
	private Map<String, String> mpllTipoEncuesta;
	
	
	
	// Obtener Catalogos
		public Map<String,String> obtenCatalogoCombo(String tipoCatalogo, Map<String, String> parametros){
			// Declaracion de Variables
			Map<String, String> mpCatalogo = new HashMap<String, String>();
			String sql = null;
			String key = null;
			String value = null;
			
			switch(tipoCatalogo){
			case "nivel":
				key="llNivelID";
				value="dsNivel";
				sql = "SELECT llNivelID, dsNivel FROM catalogoNivel";
				break;
			case "campus":
				key="llCampus";
				value="dsCampus";
				sql = "SELECT llCampus, dsCampus FROM catalogoCampus where llnivelID = " + parametros.get("nivel");
				break;
			case "entidad":
				key="llentidad";
				value="dsEntidad";
				sql = "SELECT llentidad, dsEntidad FROM catalogoEntidad WHERE llCampus = " + parametros.get("campus") + " and llNivelID = " + parametros.get("nivel");
				break;
			}
			
			// Obtenemos Catalogo
			mpCatalogo = cargaCombo(sql, key, value);
			
			return mpCatalogo;
		}
	
	public List<dtoUsuario> validarUsuario(String username, String password){
		List<dtoUsuario> lstUsuario = new ArrayList<dtoUsuario>();
		String sql = "SELECT usr.llUsuarioID,usr.dsUsuario,usr.dsPassword,campus.llNivelID,campUsr.llCampusID FROM usuarios usr " +
		"INNER JOIN campus_usuario campUsr ON (campUsr.llUsuarioID=usr.llUsuarioID) " +
		"INNER JOIN catalogoCampus campus  ON (campus.llCampus=campUsr.llCampusID) " +
		"WHERE dsUsuario='"+username+"';";
		
		try{
			// Consultar la BD para Validar Usuario
			lstUsuario = recuperarUsuario(sql);
		} catch(Exception ex){
			ex.getMessage();
		}
		
		return lstUsuario;
	}
	
	public String validarPassword(String passwordEncrypt){
		String passwordSinEncrypt="";
		
		try{
			passwordSinEncrypt = StringEncrypt.Desencriptar(passwordEncrypt);
		} catch(Exception ex){
			ex.getMessage();
		}
		
		return passwordSinEncrypt;
	}
	
	public List<DtoEncuestaRespuestasMaestrosVer1> recuperarEncuestasMaestros(List<dtoUsuario> lstUsuario){
		List<DtoEncuestaRespuestasMaestrosVer1> lstEncuestaRespuestasMaestrosVer1 = null;

		String sql = "SELECT llEncuestaID,llTipoEncuestaID,llVerEncID,llnivel,llCampus,llcuenta,llAntiguedad,llsexo,dsCarreraFormacion,dsCarrerasImparteAsigProfeCasteo,dsLicenciatura,CAST(DATE_FORMAT(dtfechaTitulacionLic, '%d/%m/%Y') AS char(10)) AS dtfechaTitulacionLic,dsEspecialidad,CAST(DATE_FORMAT(dtFechaTitulacionEsp, '%d/%m/%Y') AS char(10)) AS dtFechaTitulacionEsp,dsMaestria,CAST(DATE_FORMAT(dtFechaTitulacionMaestria, '%d/%m/%Y') AS char(10)) AS dtFechaTitulacionMaestria,dsDoctorado,CAST(DATE_FORMAT(dtFechaTitulacionDoc, '%d/%m/%Y') AS char(10)) AS dtFechaTitulacionDoc,llCompte1,dsCompte1Coment,llCompte2,dsCompte2Coment,llCompte3,dsCompte3Coment,llCompte4,dsCompte4Coment,llCompte5,dsCompte5Coment,llCompte6,dsCompte6Coment,llCompte7,dsCompte7Coment,llCompte8,dsCompte8Coment,llCompte9,dsCompte9Coment,llCompte10,dsCompte10Coment,llCompte11,dsCompte11Coment,llCompte12,dsCompte12Coment,llCompte13,dsCompte13Coment,llCompte14,dsCompte14Coment,llCompte15,dsCompte15Coment,CAST(DATE_FORMAT(fechaRegistro, '%d/%m/%Y') AS char(10)) AS fechaRegistro " +
				"FROM encuestaRespuestasMaestrosVer1 ";
		String in = "";
		String where = "";
		
		// Obtener el total de campus autorizados para el Perfil
		int countCampus = ((lstUsuario!=null && lstUsuario.size()>0)?lstUsuario.size():0);
		
		// Formar Consulta a BD
		if(countCampus==0){
			return lstEncuestaRespuestasMaestrosVer1;
		} 
		if(countCampus==1){
			where = " WHERE llCampus = " + lstUsuario.get(0).getLlCampusID() + ";";
		} 
		if(countCampus>1){
			for(int i=0; i<countCampus; i++){
				if(i==0)
					in = String.valueOf(lstUsuario.get(i).getLlCampusID())+",";
				if(i==(countCampus-1))
					in = in + String.valueOf(lstUsuario.get(i).getLlCampusID());
				else
					in = in + String.valueOf(lstUsuario.get(i).getLlCampusID()) + ",";
			}
			where = " WHERE llCampus in (" + in + ");";
		}
		
		// Concatenar Consulta
		sql = sql + where;
		
		// Recuperar Encuestas de BD
		lstEncuestaRespuestasMaestrosVer1 = recuperarEncuestasMaestrosDAO(sql);
		
		return lstEncuestaRespuestasMaestrosVer1;
	}
	
	/*
	 * Metodo para Generar los Totales de las encuestas Contestadas por los maestros
	 */
	public List<DtoEncuRespDocVer1Totales> generarListaEncuestaGlobalDocentes(List<dtoUsuario> lstUsuario, List<DtoEncuestaRespuestasMaestrosVer1> lstEncuestaRespuestasMaestrosVer1){
		
		// Lista e Instancia de los totales de la encuesta
		List<DtoEncuRespDocVer1Totales> lstEncuRespDocVer1Totales = new ArrayList<DtoEncuRespDocVer1Totales>();
		DtoEncuRespDocVer1Totales objEncuRespDocVer1Totales = new DtoEncuRespDocVer1Totales();
										
		try{
			
			// Crear estructura de Objeto
			
				///////////////////////////////////////// DATOS PERSONALES /////////////////////////////////////////
				// TotalEncuestas
					objEncuRespDocVer1Totales.setTotalEncuestas(0);
					
				// Sexo
					Sexo objSexo = new Sexo();
					objEncuRespDocVer1Totales.setObjSexo(objSexo);
					objEncuRespDocVer1Totales.getObjSexo().setFemenino(0);
					objEncuRespDocVer1Totales.getObjSexo().setMasculino(0);
			
				///////////////////////////////////////// DATOS PERSONALES /////////////////////////////////////////
				// Antiguedad
					Antiguedad objAntiguedad = new Antiguedad();
					objEncuRespDocVer1Totales.setObjAntiguedad(objAntiguedad);
					objEncuRespDocVer1Totales.getObjAntiguedad().setUno(0);
					objEncuRespDocVer1Totales.getObjAntiguedad().setDos(0);
					objEncuRespDocVer1Totales.getObjAntiguedad().setTres(0);
					objEncuRespDocVer1Totales.getObjAntiguedad().setCuatro(0);
			
				// CarreraFormacion
					CarreraFormacion objCarreraFormacion = new CarreraFormacion();
					List<String> lstCarreraFormacion = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjCarreraFormacion(objCarreraFormacion);
					objEncuRespDocVer1Totales.getObjCarreraFormacion().setTotalCarreraFormacion(0);
					objEncuRespDocVer1Totales.getObjCarreraFormacion().setLstCarreraFormacion(lstCarreraFormacion);
					
				///////////////////////////////////////// COMPONENTES /////////////////////////////////////////
				
				// Compte1
					LlCompte objLlCompte1 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte1(objLlCompte1);
					objEncuRespDocVer1Totales.getObjLlCompte1().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte1().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte1().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte1().setCuatro(0);
					
					DsCompteComent objDsCompte1Coment = new DsCompteComent();
					List<String> lstCompte1Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte1Coment(objDsCompte1Coment);
					objEncuRespDocVer1Totales.getObjDsCompte1Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte1Coment().setLstCompteComent(lstCompte1Coment);
					
				// Compte2
					LlCompte objLlCompte2 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte2(objLlCompte2);
					objEncuRespDocVer1Totales.getObjLlCompte2().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte2().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte2().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte2().setCuatro(0);
					
					DsCompteComent objDsCompte2Coment = new DsCompteComent();
					List<String> lstCompte2Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte2Coment(objDsCompte2Coment);
					objEncuRespDocVer1Totales.getObjDsCompte2Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte2Coment().setLstCompteComent(lstCompte2Coment);
			
				// Compte3
					LlCompte objLlCompte3 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte3(objLlCompte3);
					objEncuRespDocVer1Totales.getObjLlCompte3().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte3().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte3().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte3().setCuatro(0);
					
					DsCompteComent objDsCompte3Coment = new DsCompteComent();
					List<String> lstCompte3Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte3Coment(objDsCompte3Coment);
					objEncuRespDocVer1Totales.getObjDsCompte3Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte3Coment().setLstCompteComent(lstCompte3Coment);
			
				// Compte4
					LlCompte objLlCompte4 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte4(objLlCompte4);
					objEncuRespDocVer1Totales.getObjLlCompte4().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte4().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte4().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte4().setCuatro(0);
					
					DsCompteComent objDsCompte4Coment = new DsCompteComent();
					List<String> lstCompte4Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte4Coment(objDsCompte4Coment);
					objEncuRespDocVer1Totales.getObjDsCompte4Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte4Coment().setLstCompteComent(lstCompte4Coment);
			
				// Compte5
					LlCompte objLlCompte5 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte5(objLlCompte5);
					objEncuRespDocVer1Totales.getObjLlCompte5().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte5().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte5().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte5().setCuatro(0);
					
					DsCompteComent objDsCompte5Coment = new DsCompteComent();
					List<String> lstCompte5Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte5Coment(objDsCompte5Coment);
					objEncuRespDocVer1Totales.getObjDsCompte5Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte5Coment().setLstCompteComent(lstCompte5Coment);
			
				// Compte6
					LlCompte objLlCompte6 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte6(objLlCompte6);
					objEncuRespDocVer1Totales.getObjLlCompte6().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte6().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte6().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte6().setCuatro(0);
					
					DsCompteComent objDsCompte6Coment = new DsCompteComent();
					List<String> lstCompte6Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte6Coment(objDsCompte6Coment);
					objEncuRespDocVer1Totales.getObjDsCompte6Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte6Coment().setLstCompteComent(lstCompte6Coment);
			
				// Compte7
					LlCompte objLlCompte7 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte7(objLlCompte7);
					objEncuRespDocVer1Totales.getObjLlCompte7().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte7().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte7().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte7().setCuatro(0);
					
					DsCompteComent objDsCompte7Coment = new DsCompteComent();
					List<String> lstCompte7Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte7Coment(objDsCompte7Coment);
					objEncuRespDocVer1Totales.getObjDsCompte7Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte7Coment().setLstCompteComent(lstCompte7Coment);
			
				// Compte8
					LlCompte objLlCompte8 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte8(objLlCompte8);
					objEncuRespDocVer1Totales.getObjLlCompte8().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte8().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte8().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte8().setCuatro(0);
					
					DsCompteComent objDsCompte8Coment = new DsCompteComent();
					List<String> lstCompte8Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte8Coment(objDsCompte8Coment);
					objEncuRespDocVer1Totales.getObjDsCompte8Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte8Coment().setLstCompteComent(lstCompte8Coment);
			
				// Compte9
					LlCompte objLlCompte9 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte9(objLlCompte9);
					objEncuRespDocVer1Totales.getObjLlCompte9().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte9().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte9().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte9().setCuatro(0);
					
					DsCompteComent objDsCompte9Coment = new DsCompteComent();
					List<String> lstCompte9Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte9Coment(objDsCompte9Coment);
					objEncuRespDocVer1Totales.getObjDsCompte9Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte9Coment().setLstCompteComent(lstCompte9Coment);
			
				// Compte10
					LlCompte objLlCompte10 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte10(objLlCompte10);
					objEncuRespDocVer1Totales.getObjLlCompte10().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte10().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte10().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte10().setCuatro(0);
					
					DsCompteComent objDsCompte10Coment = new DsCompteComent();
					List<String> lstCompte10Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte10Coment(objDsCompte10Coment);
					objEncuRespDocVer1Totales.getObjDsCompte10Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte10Coment().setLstCompteComent(lstCompte10Coment);
			
				// Compte11
					LlCompte objLlCompte11 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte11(objLlCompte11);
					objEncuRespDocVer1Totales.getObjLlCompte11().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte11().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte11().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte11().setCuatro(0);
					
					DsCompteComent objDsCompte11Coment = new DsCompteComent();
					List<String> lstCompte11Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte11Coment(objDsCompte11Coment);
					objEncuRespDocVer1Totales.getObjDsCompte11Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte11Coment().setLstCompteComent(lstCompte11Coment);
			
				// Compte12
					LlCompte objLlCompte12 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte12(objLlCompte12);
					objEncuRespDocVer1Totales.getObjLlCompte12().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte12().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte12().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte12().setCuatro(0);
					
					DsCompteComent objDsCompte12Coment = new DsCompteComent();
					List<String> lstCompte12Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte12Coment(objDsCompte12Coment);
					objEncuRespDocVer1Totales.getObjDsCompte12Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte12Coment().setLstCompteComent(lstCompte12Coment);
			
				// Compte13
					LlCompte objLlCompte13 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte13(objLlCompte13);
					objEncuRespDocVer1Totales.getObjLlCompte13().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte13().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte13().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte13().setCuatro(0);
					
					DsCompteComent objDsCompte13Coment = new DsCompteComent();
					List<String> lstCompte13Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte13Coment(objDsCompte13Coment);
					objEncuRespDocVer1Totales.getObjDsCompte13Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte13Coment().setLstCompteComent(lstCompte13Coment);
			
				// Compte14
					LlCompte objLlCompte14 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte14(objLlCompte14);
					objEncuRespDocVer1Totales.getObjLlCompte14().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte14().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte14().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte14().setCuatro(0);
					
					DsCompteComent objDsCompte14Coment = new DsCompteComent();
					List<String> lstCompte14Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte14Coment(objDsCompte14Coment);
					objEncuRespDocVer1Totales.getObjDsCompte14Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte14Coment().setLstCompteComent(lstCompte14Coment);
			
				// Compte15
					LlCompte objLlCompte15 = new LlCompte();
					objEncuRespDocVer1Totales.setObjLlCompte15(objLlCompte15);
					objEncuRespDocVer1Totales.getObjLlCompte15().setUno(0);
					objEncuRespDocVer1Totales.getObjLlCompte15().setDos(0);
					objEncuRespDocVer1Totales.getObjLlCompte15().setTres(0);
					objEncuRespDocVer1Totales.getObjLlCompte15().setCuatro(0);
					
					DsCompteComent objDsCompte15Coment = new DsCompteComent();
					List<String> lstCompte15Coment = new ArrayList<String>();
					objEncuRespDocVer1Totales.setObjDsCompte15Coment(objDsCompte15Coment);
					objEncuRespDocVer1Totales.getObjDsCompte15Coment().setTotalCompteComent(0);
					objEncuRespDocVer1Totales.getObjDsCompte15Coment().setLstCompteComent(lstCompte15Coment);

			// Crear estructura de Objeto
				
				
			// Total de Encuestas Recuperadas de BD
			int totalEncuestas = lstEncuestaRespuestasMaestrosVer1.size();
			
			// Contador
			objEncuRespDocVer1Totales.setTotalEncuestas(totalEncuestas);
			
			// Iterar la totalidad de las encuestas
			for(int x=0; x<totalEncuestas; x++){
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
											//////////////////// DATOS PEROSNALES ////////////////////
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				/////////////////////////////////////////////////// Sexo ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlsexo()!=0){
					// Agregar Secundaria
					// objEncuRespAlumVer1Totales.getObjSecuProc().getLstNombreSecundaria().add(lstEncuestaRespuestasAlumnosVer1.get(x).getDsSecunProc());
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlsexo()==1){
						int totalFemenino = objEncuRespDocVer1Totales.getObjSexo().getFemenino();
						totalFemenino = totalFemenino + 1;
						objEncuRespDocVer1Totales.getObjSexo().setFemenino(totalFemenino);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlsexo()==2){
						int totalMasculino = objEncuRespDocVer1Totales.getObjSexo().getMasculino();
						totalMasculino = totalMasculino + 1;
						objEncuRespDocVer1Totales.getObjSexo().setMasculino(totalMasculino);
					}
				}
			
				/////////////////////////////////////////////////// Antiguedad ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlAntiguedad() !=0){
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlAntiguedad()==1){
						int total = objEncuRespDocVer1Totales.getObjAntiguedad().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjAntiguedad().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlAntiguedad()==2){
						int total = objEncuRespDocVer1Totales.getObjAntiguedad().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjAntiguedad().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlAntiguedad()==3){
						int total = objEncuRespDocVer1Totales.getObjAntiguedad().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjAntiguedad().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlAntiguedad()==4){
						int total = objEncuRespDocVer1Totales.getObjAntiguedad().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjAntiguedad().setCuatro(total);
					}
				}
				
				
				/////////////////////////////////////////////////// CarreraFormacion ////////////////////////////////////////////////////////
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCarreraFormacion()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCarreraFormacion().equals(""))){
					// Aumentar Contador
					int totalCarrera = objEncuRespDocVer1Totales.getObjCarreraFormacion().getTotalCarreraFormacion();
					totalCarrera = totalCarrera + 1;
					objEncuRespDocVer1Totales.getObjCarreraFormacion().setTotalCarreraFormacion(totalCarrera);
					
					// Agregamos nuevo bachillerato a la lista
					objEncuRespDocVer1Totales.getObjCarreraFormacion().getLstCarreraFormacion().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCarreraFormacion());
				}
				

				/////////////////////////////////////////////////// Componente 1 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte1() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte1()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte1().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte1().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte1()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte1().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte1().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte1()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte1().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte1().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte1()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte1().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte1().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte1Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte1Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte1Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte1Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte1Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte1Coment());
				}


				/////////////////////////////////////////////////// Componente 2 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte2() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte2()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte2().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte2().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte2()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte2().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte2().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte2()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte2().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte2().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte2()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte2().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte2().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte2Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte2Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte2Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte2Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte2Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte2Coment());
				}
				
				/////////////////////////////////////////////////// Componente 3 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte3() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte3()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte3().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte3().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte3()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte3().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte3().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte3()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte3().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte3().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte3()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte3().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte3().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte3Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte3Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte3Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte3Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte3Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte3Coment());
				}
					
				/////////////////////////////////////////////////// Componente 4 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte4() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte4()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte4().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte4().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte4()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte4().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte4().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte4()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte4().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte4().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte4()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte4().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte4().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte4Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte4Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte4Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte4Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte4Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte4Coment());
				}

				/////////////////////////////////////////////////// Componente 5 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte5() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte5()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte5().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte5().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte5()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte5().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte5().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte5()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte5().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte5().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte5()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte5().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte5().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte5Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte5Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte5Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte5Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte5Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte5Coment());
				}
				
				/////////////////////////////////////////////////// Componente 6 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte6() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte6()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte6().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte6().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte6()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte6().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte6().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte6()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte6().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte6().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte6()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte6().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte6().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte6Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte6Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte6Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte6Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte6Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte6Coment());
				}
				
				/////////////////////////////////////////////////// Componente 7 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte7() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte7()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte7().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte7().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte7()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte7().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte7().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte7()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte7().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte7().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte7()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte7().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte7().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte7Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte7Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte7Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte7Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte7Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte7Coment());
				}

				/////////////////////////////////////////////////// Componente 8 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte8() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte8()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte8().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte8().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte8()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte8().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte8().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte8()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte8().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte8().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte8()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte8().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte8().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte8Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte8Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte8Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte8Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte8Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte8Coment());
				}

				/////////////////////////////////////////////////// Componente 9 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte9() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte9()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte9().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte9().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte9()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte9().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte9().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte9()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte9().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte9().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte9()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte9().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte9().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte9Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte9Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte9Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte9Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte9Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte9Coment());
				}

				/////////////////////////////////////////////////// Componente 10 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte10() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte10()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte10().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte10().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte10()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte10().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte10().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte10()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte10().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte10().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte10()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte10().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte10().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte10Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte10Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte10Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte10Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte10Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte10Coment());
				}

				/////////////////////////////////////////////////// Componente 11 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte11() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte11()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte11().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte11().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte11()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte11().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte11().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte11()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte11().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte11().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte11()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte11().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte11().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte11Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte11Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte11Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte11Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte11Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte11Coment());
				}

				/////////////////////////////////////////////////// Componente 12 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte12() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte12()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte12().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte12().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte12()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte12().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte12().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte12()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte12().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte12().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte12()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte12().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte12().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte12Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte12Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte12Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte12Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte12Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte12Coment());
				}
				
				/////////////////////////////////////////////////// Componente 13 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte13() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte13()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte13().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte13().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte13()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte13().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte13().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte13()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte13().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte13().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte13()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte13().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte13().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte13Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte13Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte13Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte13Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte13Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte13Coment());
				}

				/////////////////////////////////////////////////// Componente 14 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte14() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte14()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte14().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte14().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte14()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte14().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte14().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte14()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte14().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte14().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte14()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte14().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte14().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte14Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte14Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte14Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte14Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte14Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte14Coment());
				}

				/////////////////////////////////////////////////// Componente 15 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte15() !=0){
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte15()==1){
						int total = objEncuRespDocVer1Totales.getObjLlCompte15().getUno();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte15().setUno(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte15()==2){
						int total = objEncuRespDocVer1Totales.getObjLlCompte15().getDos();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte15().setDos(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte15()==3){
						int total = objEncuRespDocVer1Totales.getObjLlCompte15().getTres();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte15().setTres(total);
					}
					
					if(lstEncuestaRespuestasMaestrosVer1.get(x).getLlCompte15()==4){
						int total = objEncuRespDocVer1Totales.getObjLlCompte15().getCuatro();
						total = total + 1;
						objEncuRespDocVer1Totales.getObjLlCompte15().setCuatro(total);
					}
				}
				
				
				if((lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte15Coment()!=null) && (!lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte15Coment().equals(""))){
					// Aumentar Contador
					int total = objEncuRespDocVer1Totales.getObjDsCompte15Coment().getTotalCompteComent();
					total = total + 1;
					objEncuRespDocVer1Totales.getObjDsCompte15Coment().setTotalCompteComent(total);
					
					// Agregamos comentario a la lista
					objEncuRespDocVer1Totales.getObjDsCompte15Coment().getLstCompteComent().add(lstEncuestaRespuestasMaestrosVer1.get(x).getDsCompte15Coment());
				}
				
			}	// FOR
			
			// Lista e Instancia de los totales de la encuesta
			lstEncuRespDocVer1Totales.add(objEncuRespDocVer1Totales);
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
		return lstEncuRespDocVer1Totales;
	}
	
	
	/*
	 * Metodo para crear los nombres de las cabeceras y el contenido de las mismas
	 */
		public void dataModelReporte(List<DtoEncuestaRespuestasMaestrosVer1> lstEncuestaRespuestasMaestrosVer1) {
			
		 // Metodo para respuestas
		cargaMapasComponentesDocentes();
		System.out.println("Entra a Metodo PARA CREAR Excel");

		List listaFinal = new ArrayList();
		 List listaCabeceras = new ArrayList();
		  
		 
		 listaCabeceras.add("Tipo de encuesta");
		 listaCabeceras.add("Nivel");
		 listaCabeceras.add("Campus");
		 listaCabeceras.add("Cuenta");
		 listaCabeceras.add("Antiguedad");
		 listaCabeceras.add("Sexo");
		 listaCabeceras.add("Carrera Formacion");
		 listaCabeceras.add("Carreras que imparte");
		 listaCabeceras.add("Licenciatura");
		 listaCabeceras.add("Fecha Titulacion Lic.");
		 listaCabeceras.add("Especialidad");
		 listaCabeceras.add("Fecha Titulacion Esp.");
		 listaCabeceras.add("Maestria");
		 listaCabeceras.add("Fecha Titulacion Maes.");
		 listaCabeceras.add("Doctorado");
		 listaCabeceras.add("Fecha Titulacion Doc.");
		 listaCabeceras.add("Pregunta 1");
		 listaCabeceras.add("Comentario Preg. 1");
		 listaCabeceras.add("Pregunta 2");
		 listaCabeceras.add("Comentario Preg. 2");
		 listaCabeceras.add("Pregunta 3");
		 listaCabeceras.add("Comentario Preg. 3");
		 listaCabeceras.add("Pregunta 4");
		 listaCabeceras.add("Comentario Preg. 4");
		 listaCabeceras.add("Pregunta 5");
		 listaCabeceras.add("Comentario Preg. 5");
		 listaCabeceras.add("Pregunta 6");
		 listaCabeceras.add("Comentario Preg. 6");
		 listaCabeceras.add("Pregunta 7");
		 listaCabeceras.add("Comentario Preg. 7");
		 listaCabeceras.add("Pregunta 8");
		 listaCabeceras.add("Comentario Preg. 8");
		 listaCabeceras.add("Pregunta 9");
		 listaCabeceras.add("Comentario Preg. 9");
		 listaCabeceras.add("Pregunta 10");
		 listaCabeceras.add("Comentario Preg. 10");
		 listaCabeceras.add("Pregunta 11");
		 listaCabeceras.add("Comentario Preg. 11");
		 listaCabeceras.add("Pregunta 12");
		 listaCabeceras.add("Comentario Preg. 12");
		 listaCabeceras.add("Pregunta 13");
		 listaCabeceras.add("Comentario Preg. 13");
		 listaCabeceras.add("Pregunta 14");
		 listaCabeceras.add("Comentario Preg. 14");
		 listaCabeceras.add("Pregunta 15");
		 listaCabeceras.add("Comentario Preg. 15");
		 listaCabeceras.add("Fecha Registro");

		 // Agregar cabeceras
		 this.setDataModelCabecera(new ListDataModel(listaCabeceras));
		 
		 try {
		  List listaFila = null;
		  
		  for (int i = 0; i < lstEncuestaRespuestasMaestrosVer1.size(); i++) {
		   listaFila = new ArrayList();
		   
		   /*
getMpllAntiguedad()		
getMpllSexo()
getMpllArea()
							getMpllCarrerasImparte()			
getMpllDocCompte1()
getMpllDocCompte2()
getMpllDocCompte3()
getMpllDocCompte4()
getMpllDocCompte5()
getMpllDocCompte6()
getMpllDocCompte7()
getMpllDocCompte8()
getMpllDocCompte9()			
getMpllDocCompte10()
getMpllDocCompte11()
getMpllDocCompte12()
getMpllDocCompte13()
getMpllDocCompte14()
getMpllDocCompte15()
		    */
		   
		   	listaFila.add(getMpllTipoEncuesta().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlTipoEncuestaID():0)));
		   	listaFila.add(getMpllNivel().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlnivel():0)));
		   	listaFila.add(getMpllCampus().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCampus():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlcuenta():0));
		   	listaFila.add(getMpllAntiguedad().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlAntiguedad():0)));
		   	listaFila.add(getMpllSexo().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlsexo():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCarreraFormacion()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCarreraFormacion():""));
		   	

		   	int bachillerato = 1;
		   	int licenciatura = 2;
		   	String carrerasImparteAsigProfeCasteo = "";
		   	String opcion[] = lstEncuestaRespuestasMaestrosVer1.get(i).getDsCarrerasImparteAsigProfeCasteo().split(",");
		   	
		   	// carrerasImparteAsigProfeCasteo.length()
		   	
		   	// carrerasImparteAsigProfeCasteo.substring(0, carrerasImparteAsigProfeCasteo.length()-1)
		   	
		   	// Carreras que Imparte
		   	if(lstEncuestaRespuestasMaestrosVer1.get(i).getLlnivel()==bachillerato){
		   		// Obtener descripcion por cada ID
		   		for(int l=0; l<opcion.length; l++){
		   			carrerasImparteAsigProfeCasteo=carrerasImparteAsigProfeCasteo + getMpllArea().get(opcion[l]) + ",";
		   		}
		   	}
		   	else if(lstEncuestaRespuestasMaestrosVer1.get(i).getLlnivel()==licenciatura){
		   	// Obtener descripcion por cada ID
		   		for(int l=0; l<opcion.length; l++){
		   			carrerasImparteAsigProfeCasteo=carrerasImparteAsigProfeCasteo + getMpllCarrerasImparte().get(opcion[l]) + ",";
		   		}
		   	}
		    	
		   	// listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCarrerasImparteAsigProfeCasteo()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCarrerasImparteAsigProfeCasteo():""));
		   	listaFila.add(String.valueOf(carrerasImparteAsigProfeCasteo.substring(0, carrerasImparteAsigProfeCasteo.length()-1)));
		   	
		   	
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsLicenciatura()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsLicenciatura():""));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDtfechaTitulacionLic()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDtfechaTitulacionLic():""));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsEspecialidad()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsEspecialidad():""));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDtFechaTitulacionEsp()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDtFechaTitulacionEsp():""));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsMaestria()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsMaestria():""));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDtFechaTitulacionMaestria()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDtFechaTitulacionMaestria():""));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsDoctorado()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsDoctorado():""));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDtFechaTitulacionDoc()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDtFechaTitulacionDoc():""));
		   	listaFila.add(getMpllDocCompte1().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte1():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte1Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte1Coment():""));
		   	listaFila.add(getMpllDocCompte2().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte2():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte2Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte2Coment():""));
		   	listaFila.add(getMpllDocCompte3().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte3():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte3Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte3Coment():""));
		   	listaFila.add(getMpllDocCompte4().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte4():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte4Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte4Coment():""));
		   	listaFila.add(getMpllDocCompte5().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte5():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte5Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte5Coment():""));
		   	listaFila.add(getMpllDocCompte6().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte6():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte6Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte6Coment():""));
		   	listaFila.add(getMpllDocCompte7().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte7():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte7Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte7Coment():""));
		   	listaFila.add(getMpllDocCompte8().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte8():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte8Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte8Coment():""));
		   	listaFila.add(getMpllDocCompte9().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte9():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte9Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte9Coment():""));
		   	listaFila.add(getMpllDocCompte10().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte10():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte10Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte10Coment():""));
		   	listaFila.add(getMpllDocCompte11().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte11():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte11Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte11Coment():""));
		   	listaFila.add(getMpllDocCompte12().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte12():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte12Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte12Coment():""));
		   	listaFila.add(getMpllDocCompte13().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte13():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte13Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte13Coment():""));
		   	listaFila.add(getMpllDocCompte14().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte14():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte14Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte14Coment():""));
		   	listaFila.add(getMpllDocCompte15().get(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getLlCompte15():0)));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte15Coment()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getDsCompte15Coment():""));
		   	listaFila.add(String.valueOf(lstEncuestaRespuestasMaestrosVer1!=null&&lstEncuestaRespuestasMaestrosVer1.get(i).getFechaRegistro()!=null?lstEncuestaRespuestasMaestrosVer1.get(i).getFechaRegistro():""));
		   
		   listaFinal.add(listaFila);
		  }

		  
		  
		  
		  
/*		  for (int i = 0; i < lstEncuestaRespuestasAlumnosVer1.size(); i++) {
		   listaFila = new ArrayList();
		   
		   	 listaFila.add(getMpllTipoEncuesta().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlTipoEncuestaID())));		// "Tipo de encuesta"
			 listaFila.add(getMpllNivel().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlnivel())));
			 listaFila.add(getMpllCampus().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCampus())));
			 listaFila.add(getMpllEntidad().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlEntidad())));
			 listaFila.add(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlcuenta()));
			 
			 if(lstEncuestaRespuestasAlumnosVer1.get(i).getLlnivel()==NIVEL_BACHILLERATO){
				 
				 try{
					 listaFila.add(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getDsSecunProc()));
				 } catch(Exception ex){
					 listaFila.add("");
				 }
				 
				 
				 listaFila.add("");
				 listaFila.add("");
			 } else if(lstEncuestaRespuestasAlumnosVer1.get(i).getLlnivel()==NIVEL_LICENCIATURA){
				 listaFila.add("");
				 
				 
				 try{
					 listaFila.add(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getDsBachilleProc()));
					 listaFila.add(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getDsBachilleProcOtra()));
				 } catch(Exception ex){
					 listaFila.add("");
					 listaFila.add("");
				 }
			 }
			 
			 listaFila.add(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getDsEdad()));
			 listaFila.add(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getDsSexo()));
			 
			 if(lstEncuestaRespuestasAlumnosVer1.get(i).getLlnivel()==NIVEL_BACHILLERATO){
				 listaFila.add(getMpllArea().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getDsCarrera())));
			 } else if(lstEncuestaRespuestasAlumnosVer1.get(i).getLlnivel()==NIVEL_LICENCIATURA){
				 listaFila.add(getMpllEntidad().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getDsCarrera())));
			 }

			 listaFila.add(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getDsCicloEscolar()));
			 
			 listaFila.add(getMpllCompte1().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte1())));
			 listaFila.add(getMpllCompte2().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte2())));
			 
			
			 try{
				 listaFila.add(getMpllCompte2a().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte2a())));
			 } catch(Exception ex){
				 listaFila.add("");
			 }
			 
			// Compte3
				// Aumentar Contador
				String[] opciones = lstEncuestaRespuestasAlumnosVer1.get(i).getDsCompte3().split(",");
				boolean uno = false;
				boolean dos = false;
				boolean tres = false;
				boolean cuatro = false;
				boolean cinco = false;
				String totalCompte3 = "";
	
				for(int y=0; y<opciones.length; y++){
					try{
						if(Integer.parseInt(opciones[y])==1 && y > 0)
							totalCompte3 = totalCompte3 + getMpdsCompte3().get(String.valueOf(opciones[y]));
						if(Integer.parseInt(opciones[y])==2  && y > 0)
							totalCompte3 = totalCompte3 + getMpdsCompte3().get(String.valueOf(opciones[y]));
						if(Integer.parseInt(opciones[y])==3  && y > 0)
							totalCompte3 = totalCompte3 + getMpdsCompte3().get(String.valueOf(opciones[y]));
						if(Integer.parseInt(opciones[y])==4  && y > 0)
							totalCompte3 = totalCompte3 + getMpdsCompte3().get(String.valueOf(opciones[y]));
						if(Integer.parseInt(opciones[y])==5  && y > 0)
							totalCompte3 = totalCompte3 + getMpdsCompte3().get(String.valueOf(opciones[y]));
						
						if(y != (opciones.length-1) && y != 0){
							totalCompte3 = totalCompte3 + ", ";
						}
					} catch(Exception ex){
						totalCompte3 = "";
					}
				}

			 listaFila.add(totalCompte3);
			 // Compte3
			 
			 listaFila.add( String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getDsCompte3otro()));
			 listaFila.add(getMpllCompte4().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte4())));
			 listaFila.add(getMpllCompte5().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte5())));
			 
			 try{
				 listaFila.add(getMpllCompte6().get(String.valueOf( lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte6() )));
			 } catch(Exception ex){
				 listaFila.add("");
			 }
			 
			 
			 listaFila.add(getMpllCompte7().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte7())));
			 listaFila.add(getMpllCompte8().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte8())));
			 listaFila.add(getMpllCompte9().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte9())));
			 listaFila.add(getMpllCompte10().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte10())));
			 listaFila.add(getMpllCompte11a().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte11a())));
			 listaFila.add(getMpllCompte11b().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte11b())));
			 listaFila.add(getMpllCompte11c().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte11c())));
			 listaFila.add(getMpllCompte11d().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte11d())));
			 listaFila.add(getMpllCompte11e().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte11e())));
			 listaFila.add(getMpllCompte11f().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte11f())));
			 listaFila.add(getMpllCompte11g().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte11g())));
			 listaFila.add(getMpllCompte11h().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte11h())));
			 listaFila.add(getMpllCompte11i().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte11i())));
			 listaFila.add(getMpllCompte11j().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte11j())));
			 listaFila.add(getMpllCompte11k().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte11k())));
			 listaFila.add(getMpllCompte11l().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte11l())));
			 listaFila.add(getMpllCompte11m().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte11m())));
			 listaFila.add(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getDsCompte11n()));
			 listaFila.add(getMpllCompte12a().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte12a())));
			 listaFila.add(getMpllCompte12b().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte12b())));
			 listaFila.add(getMpllCompte12c().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte12c())));
			 listaFila.add(getMpllCompte12d().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte12d())));
			 listaFila.add(getMpllCompte12e().get(String.valueOf(lstEncuestaRespuestasAlumnosVer1.get(i).getLlCompte12e())));
		   
		   listaFinal.add(listaFila);
		  }
*/		  
		  this.setDataModelContenido(new ListDataModel(listaFinal));
		 }
		 catch (Exception ex) {
		  ex.printStackTrace();
		 } finally {
		  listaFinal = new ArrayList();
		 }
		}
		
		
		/*
		 * Metodo que arma el Excel
		 */
		public static HSSFWorkbook obtenerExcel(DataModel contenidoCeldas,
		  DataModel cabecerasCeldas, String nombreHoja) {
		 
		 HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		 HSSFSheet hssfSheet = hssfWorkbook.createSheet(nombreHoja);
		 int numeroFila = 0;
		 int numeroColumna = 0;
		 
		 // Crear una nueva fila
		 HSSFRow hssfRow = hssfSheet.createRow(numeroFila++);
		 
		 // Estilo de la cabecera
		 HSSFCellStyle hssfCellStyleCabecera = hssfWorkbook.createCellStyle();
		 hssfCellStyleCabecera.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 hssfCellStyleCabecera.setFillBackgroundColor(new HSSFColor.BLACK()
		   .getIndex());
		 
		 // Crear la fuente de la cabecera
		 HSSFFont hssfFont = hssfWorkbook.createFont();
		 hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		 hssfFont.setColor(HSSFColor.WHITE.index);
		 
		 // Aplicarle la fuente al estilo de la cabecera
		 hssfCellStyleCabecera.setFont(hssfFont);
		 
		 String columnaCabecera;
		 
		 // Creacion de la celda
		 HSSFCell hssfCell = null;
		 
		 // Pasar los nombres de cabeceras a una lista
		 List cabecerasExcel = (List) cabecerasCeldas.getWrappedData();
		 
		 // Agregar los nombres de las cabeceras a el excel
		 for (int i = 0; i < cabecerasExcel.size(); i++) {
		  columnaCabecera = (String) cabecerasExcel.get(i);
		  hssfCell = hssfRow.createCell((short) numeroColumna++);
		  hssfCell.setCellValue(columnaCabecera);
		 
		  // Agregar el estilo que creamos antes
		  hssfCell.setCellStyle(hssfCellStyleCabecera);
		  //hssfCell.setEncoding(HSSFCell.ENCODING_UTF_16);
		 }
		 
		 // Pasar el contenido del excel a una lista
		 List contenidoExcel = (List) contenidoCeldas.getWrappedData();
		 List fila = null;
		 Object valor;
		 
		 for (int i = 0; i < contenidoExcel.size(); i++) {
		  // Obtener el contenido por fila
		  fila = (List) contenidoExcel.get(i);
		 
		  // Crear la fila
		  hssfRow = hssfSheet.createRow(numeroFila++);
		  numeroColumna = 0;
		  for (int x = 0; x < fila.size(); x++) {
		   // Obtener el valor de cada celda
		   valor = fila.get(x);
		 
		   // Insertarlo en la celda
		   hssfCell = hssfRow.createCell((short) numeroColumna++);
		   //hssfCell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   hssfCell.setCellValue((String) valor);
		  }
		 }
		 return hssfWorkbook;
		}
		
		

		public DataModel getDataModelCabecera() {
			return dataModelCabecera;
		}

		public void setDataModelCabecera(DataModel dataModelCabecera) {
			this.dataModelCabecera = dataModelCabecera;
		}

		public DataModel getDataModelContenido() {
			return dataModelContenido;
		}

		public void setDataModelContenido(DataModel dataModelContenido) {
			this.dataModelContenido = dataModelContenido;
		}

}

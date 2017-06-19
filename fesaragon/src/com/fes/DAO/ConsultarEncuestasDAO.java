package com.fes.DAO;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.commons.lang3.ObjectUtils.Null;
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
import com.fes.bean.encuesta.totales.OtroCompte11;
import com.fes.bean.encuesta.totales.SecuProc;
import com.fes.bean.encuesta.totales.Sexo;
import com.fes.utilities.StringEncrypt;

public class ConsultarEncuestasDAO extends DAO {
	
	private DataModel dataModelCabecera;
	private DataModel dataModelContenido;

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

	public List<DtoEncuestaRespuestasAlumnosVer1> recuperarEncuestas(List<dtoUsuario> lstUsuario){
		List<DtoEncuestaRespuestasAlumnosVer1> lstEncuestaRespuestasAlumnosVer1 = null;

		String sql = "SELECT llEncuestaID,llTipoEncuestaID,llVerEncID,llnivel,llCampus,llEntidad,llcuenta,dsSecunProc,dsBachilleProc,dsBachilleProcOtra,dsEdad,dsSexo,dsCarrera,dsCicloEscolar,llCompte1,llCompte2,llCompte2a,dsCompte3,dsCompte3otro,llCompte4,llCompte5,llCompte6,llCompte7,llCompte8,llCompte9,llCompte10,llCompte11a,llCompte11b,llCompte11c,llCompte11d,llCompte11e,llCompte11f,llCompte11g,llCompte11h,llCompte11i,llCompte11j,llCompte11k,llCompte11l,llCompte11m,dsCompte11n,llCompte12a,llCompte12b,llCompte12c,llCompte12d,llCompte12e " +
				"FROM encuestaRespuestasAlumnosVer1 ";
		String in = "";
		String where = "";
		
		// Obtener el total de campus autorizados para el Perfil
		int countCampus = ((lstUsuario!=null && lstUsuario.size()>0)?lstUsuario.size():0);
		
		// Formar Consulta a BD
		if(countCampus==0){
			return lstEncuestaRespuestasAlumnosVer1;
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
		lstEncuestaRespuestasAlumnosVer1 = recuperarEncuestasDAO(sql);
		
		return lstEncuestaRespuestasAlumnosVer1;
	}
	
	/*
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
	 */
	public List<DtoEncuRespAlumVer1Totales> generarListaEncuestaGlobal(List<dtoUsuario> lstUsuario, List<DtoEncuestaRespuestasAlumnosVer1> lstEncuestaRespuestasAlumnosVer1){
		
		// Lista e Instancia de los totales de la encuesta
		List<DtoEncuRespAlumVer1Totales> lstEncuRespAlumVer1Totales = new ArrayList<DtoEncuRespAlumVer1Totales>();
		DtoEncuRespAlumVer1Totales objEncuRespAlumVer1Totales = new DtoEncuRespAlumVer1Totales();
										
		try{
			
			// Crear estructura de Objeto
			
				//////////////////////////// DATOS PERSONALES //////////////////////////
				// Secundaria Procedencia
				SecuProc objSecuProc = new SecuProc();
				List<String> lstSecundaria = new ArrayList<String>();
				objEncuRespAlumVer1Totales.setObjSecuProc(objSecuProc);
				objEncuRespAlumVer1Totales.getObjSecuProc().setLstNombreSecundaria(lstSecundaria);
				objEncuRespAlumVer1Totales.getObjSecuProc().setTotalSecundarias(0);


				// Bachillerato Procedencia
				BachoProc objBachoProc = new BachoProc();
				OtraBachillerato objOtraBachillerato = new OtraBachillerato();
				List<String> lstBachillerato = new ArrayList<String>();
				objEncuRespAlumVer1Totales.setObjBachoProc(objBachoProc);
				objEncuRespAlumVer1Totales.getObjBachoProc().setOtra(objOtraBachillerato);
				objEncuRespAlumVer1Totales.getObjBachoProc().getOtra().setLstNombreBachillerato(lstBachillerato);
				objEncuRespAlumVer1Totales.getObjBachoProc().setCch(0);
				objEncuRespAlumVer1Totales.getObjBachoProc().setEnp(0);
				objEncuRespAlumVer1Totales.getObjBachoProc().getOtra().setTotal(0);
				
				
				// Edad
				Edad objEdad = new Edad();
				List<String> lstEdad = new ArrayList<String>(); 
				objEncuRespAlumVer1Totales.setObjEdad(objEdad);
				objEncuRespAlumVer1Totales.getObjEdad().setLstEdades(lstEdad);
				objEncuRespAlumVer1Totales.getObjEdad().setTotal(0);
				
				
				// Sexo
				Sexo objSexo = new Sexo();
				objEncuRespAlumVer1Totales.setObjSexo(objSexo);
				objEncuRespAlumVer1Totales.getObjSexo().setFemenino(0);
				objEncuRespAlumVer1Totales.getObjSexo().setMasculino(0);
				
				// Area / Carrera
				AreaCarrera objAreaCarrera = new AreaCarrera();
				List<String> lstAreaCarrera = new ArrayList<String>();
				objEncuRespAlumVer1Totales.setObjAreaCarrera(objAreaCarrera);
				objEncuRespAlumVer1Totales.getObjAreaCarrera().setLstAreasCarreras(lstAreaCarrera);
				objEncuRespAlumVer1Totales.getObjAreaCarrera().setTotal(0);
				
				// Anio Escolar / Semestre
				AnioSemestre objAnioSemestre = new AnioSemestre();
				List<String> lstAnioSemestre = new ArrayList<String>();
				objEncuRespAlumVer1Totales.setObjAnioSemestre(objAnioSemestre);
				objEncuRespAlumVer1Totales.getObjAnioSemestre().setLstSemestres(lstAnioSemestre);
				objEncuRespAlumVer1Totales.getObjAnioSemestre().setTotal(0);
				
				//////////////////////////// DATOS ENCUESTA //////////////////////////
				// llCompte1
				LlCompte1 objLlCompte1 = new LlCompte1();
				objEncuRespAlumVer1Totales.setObjllCompte1(objLlCompte1);
				objEncuRespAlumVer1Totales.getObjllCompte1().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte1().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte1().setTres(0);
				objEncuRespAlumVer1Totales.getObjllCompte1().setCuatro(0);
				objEncuRespAlumVer1Totales.getObjllCompte1().setCinco(0);
	
				// llCompte2
				LlCompte2 objLlCompte2 = new LlCompte2();
				LlCompte2a objLlCompte2a = new LlCompte2a();
				objEncuRespAlumVer1Totales.setObjllCompte2(objLlCompte2);
				objEncuRespAlumVer1Totales.getObjllCompte2().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte2().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte2().setObjllCompte2a(objLlCompte2a);
					objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setTres(0);
					objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setCuatro(0);
				
				// llCompte3
				DsCompte3 objDsCompte3 = new DsCompte3();
				List<String> lstDsCompte3 = new ArrayList<String>();
				objEncuRespAlumVer1Totales.setObjdsCompte3(objDsCompte3);
				objEncuRespAlumVer1Totales.getObjdsCompte3().setLstOtrosCompte3(lstDsCompte3);
				objEncuRespAlumVer1Totales.getObjdsCompte3().setUno(0);
				objEncuRespAlumVer1Totales.getObjdsCompte3().setDos(0);
				objEncuRespAlumVer1Totales.getObjdsCompte3().setTres(0);
				objEncuRespAlumVer1Totales.getObjdsCompte3().setCuatro(0);
				objEncuRespAlumVer1Totales.getObjdsCompte3().setCinco(0);
					objEncuRespAlumVer1Totales.getObjdsCompte3().setTotalOtros(0);
					
				// llCompte4
				LlCompte4 objLlCompte4 = new LlCompte4();	
				objEncuRespAlumVer1Totales.setObjllCompte4(objLlCompte4);	
				objEncuRespAlumVer1Totales.getObjllCompte4().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte4().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte4().setTres(0);
				objEncuRespAlumVer1Totales.getObjllCompte4().setCuatro(0);
				objEncuRespAlumVer1Totales.getObjllCompte4().setCinco(0);
				
				// llCompte5
				LlCompte5 objLlCompte5 = new LlCompte5();
				objEncuRespAlumVer1Totales.setObjllCompte5(objLlCompte5);
				objEncuRespAlumVer1Totales.getObjllCompte5().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte5().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte5().setTres(0);
				
				// llCompte6
				LlCompte6 objLlCompte6 = new LlCompte6();
				objEncuRespAlumVer1Totales.setObjllCompte6(objLlCompte6);
				objEncuRespAlumVer1Totales.getObjllCompte6().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte6().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte6().setTres(0);
				objEncuRespAlumVer1Totales.getObjllCompte6().setCuatro(0);
				
				// llCompte7
				LlCompte7 objLlCompte7 = new LlCompte7();
				objEncuRespAlumVer1Totales.setObjllCompte7(objLlCompte7);
				objEncuRespAlumVer1Totales.getObjllCompte7().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte7().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte7().setTres(0);
				objEncuRespAlumVer1Totales.getObjllCompte7().setCuatro(0);
				objEncuRespAlumVer1Totales.getObjllCompte7().setCinco(0);
				
				// llCompte8
				LlCompte8 objLlCompte8 = new LlCompte8();
				objEncuRespAlumVer1Totales.setObjllCompte8(objLlCompte8);
				objEncuRespAlumVer1Totales.getObjllCompte8().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte8().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte8().setTres(0);
				objEncuRespAlumVer1Totales.getObjllCompte8().setCuatro(0);
				objEncuRespAlumVer1Totales.getObjllCompte8().setCinco(0);
				
				// llCompte9
				LlCompte9 objLlCompte9 = new LlCompte9();
				objEncuRespAlumVer1Totales.setObjllCompte9(objLlCompte9);
				objEncuRespAlumVer1Totales.getObjllCompte9().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte9().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte9().setTres(0);
				objEncuRespAlumVer1Totales.getObjllCompte9().setCuatro(0);
				objEncuRespAlumVer1Totales.getObjllCompte9().setCinco(0);
				
				// llCompte10
				LlCompte10 objLlCompte10 = new LlCompte10();
				objEncuRespAlumVer1Totales.setObjllCompte10(objLlCompte10);
				objEncuRespAlumVer1Totales.getObjllCompte10().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte10().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte10().setTres(0);
				objEncuRespAlumVer1Totales.getObjllCompte10().setCuatro(0);
				
				// llCompte11
				LlCompte11 objLlCompte11 = new LlCompte11();
				LlCompte11a objLlCompte11a = new LlCompte11a();
				objEncuRespAlumVer1Totales.setObjllCompte11(objLlCompte11);
				objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11a(objLlCompte11a);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().setTres(0);

				LlCompte11b objLlCompte11b = new LlCompte11b();
				objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11b(objLlCompte11b);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().setTres(0);
				
				LlCompte11c objLlCompte11c = new LlCompte11c();
				objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11c(objLlCompte11c);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().setTres(0);
				
				LlCompte11d objLlCompte11d = new LlCompte11d();
				objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11d(objLlCompte11d);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().setTres(0);
				
				LlCompte11e objLlCompte11e = new LlCompte11e();
				objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11e(objLlCompte11e);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().setTres(0);
				
				LlCompte11f objLlCompte11f = new LlCompte11f();
				objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11f(objLlCompte11f);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().setTres(0);
				
				LlCompte11g objLlCompte11g = new LlCompte11g();
				objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11g(objLlCompte11g);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().setTres(0);
				
				LlCompte11h objLlCompte11h = new LlCompte11h();
				objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11h(objLlCompte11h);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().setTres(0);
				
				LlCompte11i objLlCompte11i = new LlCompte11i();
				objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11i(objLlCompte11i);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().setTres(0);
				
				LlCompte11j objLlCompte11j = new LlCompte11j();
				objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11j(objLlCompte11j);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().setTres(0);
				
				LlCompte11k objLlCompte11k = new LlCompte11k();
				objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11k(objLlCompte11k);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().setTres(0);
				
				LlCompte11l objLlCompte11l = new LlCompte11l();
				objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11l(objLlCompte11l);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().setTres(0);
				
				LlCompte11m objLlCompte11m = new LlCompte11m();
				objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11m(objLlCompte11m);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().setTres(0);
					LlCompte11Otro objLlCompte11Otro = new LlCompte11Otro();
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjOtro(objLlCompte11Otro);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjOtro().setTotal(0);
					List<String> lstCompte11Otro = new ArrayList<String>();
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjOtro().setObjOtroCompte11(lstCompte11Otro);
				
				// llCompte12
				LlCompte12 objLlCompte12 = new LlCompte12();
				LlCompte12a objLlCompte12a = new LlCompte12a();
				objEncuRespAlumVer1Totales.setObjllCompte12(objLlCompte12);
				objEncuRespAlumVer1Totales.getObjllCompte12().setObjllCompte12a(objLlCompte12a);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().setTres(0);

				LlCompte12b objLlCompte12b = new LlCompte12b();
				objEncuRespAlumVer1Totales.getObjllCompte12().setObjllCompte12b(objLlCompte12b);				
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().setTres(0);
				
				LlCompte12c objLlCompte12c = new LlCompte12c();
				objEncuRespAlumVer1Totales.getObjllCompte12().setObjllCompte12c(objLlCompte12c);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().setTres(0);
				
				LlCompte12d objLlCompte12d = new LlCompte12d();
				objEncuRespAlumVer1Totales.getObjllCompte12().setObjllCompte12d(objLlCompte12d);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().setTres(0);
				
				LlCompte12e objLlCompte12e = new LlCompte12e();
				objEncuRespAlumVer1Totales.getObjllCompte12().setObjllCompte12e(objLlCompte12e);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().setUno(0);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().setDos(0);
				objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().setTres(0);

			// Crear estructura de Objeto
				
				
			// Total de Encuestas Recuperadas de BD
			int totalEncuestas = lstEncuestaRespuestasAlumnosVer1.size();
			
			// Contador
			objEncuRespAlumVer1Totales.setTotalEncuestas(totalEncuestas);
			
			// Iterar la totalidad de las encuestas
			for(int x=0; x<totalEncuestas; x++){
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
											//////////////////// DATOS PEROSNALES ////////////////////
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				/////////////////////////////////////////////////// Secundaria Procedencia ////////////////////////////////////////////////////////
				if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsSecunProc()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsSecunProc().equals(""))){
					// Actualizar contador
					int totalSecundarias = objEncuRespAlumVer1Totales.getObjSecuProc().getTotalSecundarias();
					totalSecundarias = totalSecundarias + 1;
					objEncuRespAlumVer1Totales.getObjSecuProc().setTotalSecundarias(totalSecundarias);
					
					// Agregar Secundaria
					objEncuRespAlumVer1Totales.getObjSecuProc().getLstNombreSecundaria().add(lstEncuestaRespuestasAlumnosVer1.get(x).getDsSecunProc());
				}
				
				/////////////////////////////////////////////////// Secundaria Procedencia ////////////////////////////////////////////////////////
			
				/////////////////////////////////////////////////// Bachillerato Procedencia ////////////////////////////////////////////////////////
				if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProc()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProc().equals(""))){
					// Actualizar contador
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProc().equals("CCH")){
						int totalBachilleratoCCH = objEncuRespAlumVer1Totales.getObjBachoProc().getCch();
						totalBachilleratoCCH = totalBachilleratoCCH + 1;
						objEncuRespAlumVer1Totales.getObjBachoProc().setCch(totalBachilleratoCCH);
					} 
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProc().equals("ENP")){
						int totalBachilleratoENP = objEncuRespAlumVer1Totales.getObjBachoProc().getEnp();
						totalBachilleratoENP = totalBachilleratoENP + 1;
						objEncuRespAlumVer1Totales.getObjBachoProc().setEnp(totalBachilleratoENP);
					}
				}
				
				if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProcOtra()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProcOtra().equals(""))){
					// Aumentar Contador
					int totalBachilleratoOtros = objEncuRespAlumVer1Totales.getObjBachoProc().getOtra().getTotal();
					totalBachilleratoOtros = totalBachilleratoOtros + 1;
					objEncuRespAlumVer1Totales.getObjBachoProc().getOtra().setTotal(totalBachilleratoOtros);
					
					// Agregamos nuevo bachillerato a la lista
					objEncuRespAlumVer1Totales.getObjBachoProc().getOtra().getLstNombreBachillerato().add(lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProcOtra());
				}
				/////////////////////////////////////////////////// Bachillerato Procedencia ////////////////////////////////////////////////////////
			
				/////////////////////////////////////////////////// Edad ////////////////////////////////////////////////////////
				if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsEdad()!=null) &&(!lstEncuestaRespuestasAlumnosVer1.get(x).getDsEdad().equals(""))){
					int totalEdades = objEncuRespAlumVer1Totales.getObjEdad().getTotal();
					totalEdades = totalEdades + 1;
					objEncuRespAlumVer1Totales.getObjEdad().setTotal(totalEdades);
					
					// Agregamos nueva edad a la lista
					objEncuRespAlumVer1Totales.getObjEdad().getLstEdades().add(lstEncuestaRespuestasAlumnosVer1.get(x).getDsEdad());
				}
				/////////////////////////////////////////////////// Edad ////////////////////////////////////////////////////////
				
				/////////////////////////////////////////////////// Sexo ////////////////////////////////////////////////////////
				if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsSexo()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsSexo().equals(""))){
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getDsSexo().equals("F")){
						int totalFemenino = objEncuRespAlumVer1Totales.getObjSexo().getFemenino();
						totalFemenino = totalFemenino + 1;
						objEncuRespAlumVer1Totales.getObjSexo().setFemenino(totalFemenino);
					}
					
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getDsSexo().equals("M")){
						int totalMaculino = objEncuRespAlumVer1Totales.getObjSexo().getMasculino();
						totalMaculino = totalMaculino + 1;
						objEncuRespAlumVer1Totales.getObjSexo().setMasculino(totalMaculino);
					}
				}
				/////////////////////////////////////////////////// Sexo ////////////////////////////////////////////////////////
			
				/////////////////////////////////////////////////// Area/Carrera ////////////////////////////////////////////////////////
				if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsCarrera()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsCarrera().equals(""))){
					// Aumentar Contador
					int totalAreasCarreras = objEncuRespAlumVer1Totales.getObjAreaCarrera().getTotal();
					totalAreasCarreras = totalAreasCarreras + 1;
					objEncuRespAlumVer1Totales.getObjAreaCarrera().setTotal(totalAreasCarreras);
					
					String Carrera = "";
					int idCarrera = Integer.parseInt(lstEncuestaRespuestasAlumnosVer1.get(x).getDsCarrera());
					
					// Agregamos nueva Area o Carrera a la lista
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlnivel()==NIVEL_BACHILLERATO){
						if(idCarrera==AREA_FisicoMatematicasIngenierias)
							Carrera="FÍSICO-MATEMÁTICAS E INGENIERÍAS";
						if(idCarrera==AREA_BIOLOGICAS_DELASALUD)
							Carrera="BIOLÓGICAS Y DE LA SALUD";
						if(idCarrera==AREA_SOCIALES)
							Carrera="SOCIALES";
						if(idCarrera==AREA_HUMANIDADES_LASARTES)
							Carrera="HUMANIDADES Y LAS ARTES";
					}
					
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlnivel()==NIVEL_LICENCIATURA){
						Carrera = "";
						
						if(idCarrera==ENTIDAD_Actuaria)
							Carrera="Actuaría";
						if(idCarrera==ENTIDAD_IngCivil || idCarrera==ENTIDAD_IngCivil2)
							Carrera="Ing. Civil";
						if(idCarrera==ENTIDAD_MatematicasAplicComp)
							Carrera="Matemáticas Aplic. y Comp.";
						if(idCarrera==ENTIDAD_IngComputacion)	
							Carrera="Ing. en Computación";
						if(idCarrera==ENTIDAD_IngIndustrial || idCarrera==ENTIDAD_IngIndustrial2)
							Carrera="Ing. Industrial";
						if(idCarrera==ENTIDAD_IngElectricaElectronica)
							Carrera="Ing. Eléctrica y Electrónica";
						if(idCarrera==ENTIDAD_IngMecanica)
							Carrera="Ing. Mecánica";
						if(idCarrera==ENTIDAD_IngMecanicaElectrica || idCarrera==ENTIDAD_IngMecanicaElectrica2)
							Carrera="Ing. Mecánica Eléctrica";
						if(idCarrera==ENTIDAD_Tecnologia)
							Carrera="Tecnología";
						if(idCarrera==ENTIDAD_IngTelecomunicacionesSistElect)
							Carrera="Ing. en Telecomunicaciones, Sist. y Elect.";
					}
					objEncuRespAlumVer1Totales.getObjAreaCarrera().getLstAreasCarreras().add(Carrera);
				}
				/////////////////////////////////////////////////// Area/Carrera ////////////////////////////////////////////////////////
			
				/////////////////////////////////////////////////// Ciclo Escolar ////////////////////////////////////////////////////////
				if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsCicloEscolar()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsCicloEscolar().equals(""))){
					// Aumentar Contador
					int totalCicloEscolar = objEncuRespAlumVer1Totales.getObjAnioSemestre().getTotal();
					totalCicloEscolar = totalCicloEscolar + 1;
					objEncuRespAlumVer1Totales.getObjAnioSemestre().setTotal(totalCicloEscolar);
						
					// Agregamos nuevo Ciclo escolar a la lista
					objEncuRespAlumVer1Totales.getObjAnioSemestre().getLstSemestres().add(lstEncuestaRespuestasAlumnosVer1.get(x).getDsCicloEscolar());
				}
				/////////////////////////////////////////////////// Ciclo Escolar ////////////////////////////////////////////////////////
			
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
												//////////////////// DATOS ENCUESTA ////////////////////
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
				/////////////////////////////////////////////////// llCompte1 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte1()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte1();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte1().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte1().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte1().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte1().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte1().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte1().setTres(total);
					}
					if(res==4){
						int total = objEncuRespAlumVer1Totales.getObjllCompte1().getCuatro();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte1().setCuatro(total);
					}
					if(res==5){
						int total = objEncuRespAlumVer1Totales.getObjllCompte1().getCinco();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte1().setCinco(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte2 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte2()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte2();
				
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte2().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte2().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte2().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte2().setDos(total);
					}			
				}
				
				/////////////////////////////////////////////////// llCompte2a ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte2a()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte2a();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setTres(total);
					}
					if(res==4){
						int total = objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().getCuatro();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setCuatro(total);
					}
				}
				
	
				/////////////////////////////////////////////////// llCompte3 ////////////////////////////////////////////////////////
				if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte3() !=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte3().equals(""))){
					// Aumentar Contador
					String[] opciones = lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte3().split(",");
					boolean uno = false;
					boolean dos = false;
					boolean tres = false;
					boolean cuatro = false;
					boolean cinco = false;
		
					for(int y=0; y<opciones.length; y++){
						if(Integer.parseInt(opciones[y])==1)
							uno = true;
						if(Integer.parseInt(opciones[y])==2)
							dos = true;
						if(Integer.parseInt(opciones[y])==3)
							tres = true;
						if(Integer.parseInt(opciones[y])==4)
							cuatro = true;
						if(Integer.parseInt(opciones[y])==5)
							cinco = true;
					}
					
					if(uno == true){
						int total = objEncuRespAlumVer1Totales.getObjdsCompte3().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjdsCompte3().setUno(total);
					}
					if(dos == true){
						int total = objEncuRespAlumVer1Totales.getObjdsCompte3().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjdsCompte3().setDos(total);
					}
					if(tres == true){
						int total = objEncuRespAlumVer1Totales.getObjdsCompte3().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjdsCompte3().setTres(total);
					}
					if(cuatro == true){
						int total = objEncuRespAlumVer1Totales.getObjdsCompte3().getCuatro();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjdsCompte3().setCuatro(total);
					}
					if(cinco == true){
						int total = objEncuRespAlumVer1Totales.getObjdsCompte3().getCinco();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjdsCompte3().setCinco(total);
					}
				}
				
				
				/////////////////////////////////////////////////// llCompte3Otra ////////////////////////////////////////////////////////
				if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte3otro()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte3otro().equals(""))){
					// Aumentar Contador
					int total = objEncuRespAlumVer1Totales.getObjdsCompte3().getTotalOtros();
					total = total + 1;
					objEncuRespAlumVer1Totales.getObjdsCompte3().setTotalOtros(total);
					
					// Agregar Otro
					objEncuRespAlumVer1Totales.getObjdsCompte3().getLstOtrosCompte3().add(lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte3otro());
				}
				
				
				/////////////////////////////////////////////////// llCompte4 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte4()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte4();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte4().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte4().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte4().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte4().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte4().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte4().setTres(total);
					}
					if(res==4){
						int total = objEncuRespAlumVer1Totales.getObjllCompte4().getCuatro();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte4().setCuatro(total);
					}
					if(res==5){
						int total = objEncuRespAlumVer1Totales.getObjllCompte4().getCinco();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte4().setCinco(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte5 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte5()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte5();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte5().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte5().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte5().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte5().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte5().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte5().setTres(total);
					}
				}
				
				
				/////////////////////////////////////////////////// llCompte6 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte6()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte6();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte6().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte6().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte6().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte6().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte6().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte6().setTres(total);
					}
					if(res==4){
						int total = objEncuRespAlumVer1Totales.getObjllCompte6().getCuatro();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte6().setCuatro(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte7 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte7()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte7();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte7().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte7().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte7().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte7().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte7().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte7().setTres(total);
					}
					if(res==4){
						int total = objEncuRespAlumVer1Totales.getObjllCompte7().getCuatro();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte7().setCuatro(total);
					}
					if(res==5){
						int total = objEncuRespAlumVer1Totales.getObjllCompte7().getCinco();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte7().setCinco(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte8 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte8()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte8();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte8().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte8().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte8().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte8().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte8().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte8().setTres(total);
					}
					if(res==4){
						int total = objEncuRespAlumVer1Totales.getObjllCompte8().getCuatro();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte8().setCuatro(total);
					}
					if(res==5){
						int total = objEncuRespAlumVer1Totales.getObjllCompte8().getCinco();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte8().setCinco(total);
					}
				}
				
				
				/////////////////////////////////////////////////// llCompte9 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte9()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte9();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte9().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte9().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte9().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte9().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte9().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte9().setTres(total);
					}
					if(res==4){
						int total = objEncuRespAlumVer1Totales.getObjllCompte9().getCuatro();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte9().setCuatro(total);
					}
					if(res==5){
						int total = objEncuRespAlumVer1Totales.getObjllCompte9().getCinco();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte9().setCinco(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte10 ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte10()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte10();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte10().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte10().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte10().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte10().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte10().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte10().setTres(total);
					}
					if(res==4){
						int total = objEncuRespAlumVer1Totales.getObjllCompte10().getCuatro();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte10().setCuatro(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11a ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11a()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11a();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11b ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11b()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11b();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11c ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11c()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11c();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11d ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11d()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11d();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11e ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11e()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11e();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11f ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11f()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11f();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11g ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11g()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11g();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11h ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11h()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11h();
					
					if(res==1){
					int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().getUno();
					total = total + 1;
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().setUno(total);
					}
					if(res==2){
					int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().getDos();
					total = total + 1;
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().setDos(total);
					}
					if(res==3){
					int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().getTres();
					total = total + 1;
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11i ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11i()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11i();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11j ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11j()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11j();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11k ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11k()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11k();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11l ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11l()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11l();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11m ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11m()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11m();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte11n ////////////////////////////////////////////////////////
				if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte11n()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte11n().equals(""))){
					// Aumentar Contador
					String res = lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte11n();
					int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjOtro().getTotal();
					total = total+1;
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjOtro().setTotal(total);
					
					// Agregar a la lista de otros
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjOtro().getObjOtroCompte11().add(res);				
				}
				
				/////////////////////////////////////////////////// llCompte12a ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12a()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12a();
					
					if(res==1){
					int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().getUno();
					total = total + 1;
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().setUno(total);
					}
					if(res==2){
					int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().getDos();
					total = total + 1;
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().setDos(total);
					}
					if(res==3){
					int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().getTres();
					total = total + 1;
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte12b ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12b()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12b();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte12c ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12c()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12c();
					
					if(res==1){
					int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().getUno();
					total = total + 1;
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().setUno(total);
					}
					if(res==2){
					int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().getDos();
					total = total + 1;
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().setDos(total);
					}
					if(res==3){
					int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().getTres();
					total = total + 1;
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().setTres(total);
					}
				}
				
				/////////////////////////////////////////////////// llCompte12d ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12d()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12d();
					
					if(res==1){
					int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().getUno();
					total = total + 1;
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().setUno(total);
					}
					if(res==2){
					int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().getDos();
					total = total + 1;
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().setDos(total);
					}
					if(res==3){
					int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().getTres();
					total = total + 1;
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().setTres(total);
					}
				}
					
				/////////////////////////////////////////////////// llCompte12e ////////////////////////////////////////////////////////
				if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12e()!=0){
					// Aumentar Contador
					int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12e();
					
					if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().setUno(total);
					}
					if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().setDos(total);
					}
					if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().setTres(total);
					}
				}
				
			}	// FOR
			
			// Lista e Instancia de los totales de la encuesta
			lstEncuRespAlumVer1Totales.add(objEncuRespAlumVer1Totales);
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
		return lstEncuRespAlumVer1Totales;
	}
	
	
	/*
	 * Metodo para crear los nombres de las cabeceras y el contenido de las mismas
	 */
		public void dataModelReporte(List<DtoEncuestaRespuestasAlumnosVer1> lstEncuestaRespuestasAlumnosVer1) {
			
		 // Metodo para respuestas
		 cargaMapasComponentes();
			
		 List listaFinal = new ArrayList();
		 List listaCabeceras = new ArrayList();
		  
		 
		 listaCabeceras.add("Tipo de encuesta");
		 listaCabeceras.add("Nivel");
		 listaCabeceras.add("Campus");
		 listaCabeceras.add("Entidad");
		 listaCabeceras.add("Cuenta");
		 listaCabeceras.add("Secundaria procedencia");
		 listaCabeceras.add("Bachillerato procedencia");
		 listaCabeceras.add("Bachillerato procedencia (otro)");
		 listaCabeceras.add("Edad");
		 listaCabeceras.add("Sexo");
		 listaCabeceras.add("Carrera/Área");
		 listaCabeceras.add("Ciclo escolar");
		 listaCabeceras.add("Pregunta 1");
		 listaCabeceras.add("Pregunta 2");
		 listaCabeceras.add("Pregunta 2a");
		 listaCabeceras.add("Pregunta 3");
		 listaCabeceras.add("Pregunta 3 (otra)");
		 listaCabeceras.add("Pregunta 4");
		 listaCabeceras.add("Pregunta 5");
		 listaCabeceras.add("Pregunta 6");
		 listaCabeceras.add("Pregunta 7");
		 listaCabeceras.add("Pregunta 8");
		 listaCabeceras.add("Pregunta 9");
		 listaCabeceras.add("Pregunta 10");
		 listaCabeceras.add("Pregunta 11a");
		 listaCabeceras.add("Pregunta 11b");
		 listaCabeceras.add("Pregunta 11c");
		 listaCabeceras.add("Pregunta 11d");
		 listaCabeceras.add("Pregunta 11e");
		 listaCabeceras.add("Pregunta 11f");
		 listaCabeceras.add("Pregunta 11g");
		 listaCabeceras.add("Pregunta 11h");
		 listaCabeceras.add("Pregunta 11i");
		 listaCabeceras.add("Pregunta 11j");
		 listaCabeceras.add("Pregunta 11k");
		 listaCabeceras.add("Pregunta 11l");
		 listaCabeceras.add("Pregunta 11m");
		 listaCabeceras.add("Pregunta 11 (otra)");
		 listaCabeceras.add("Pregunta 12a");
		 listaCabeceras.add("Pregunta 12b");
		 listaCabeceras.add("Pregunta 12c");
		 listaCabeceras.add("Pregunta 12d");
		 listaCabeceras.add("Pregunta 12e");
		 
		 this.setDataModelCabecera(new ListDataModel(listaCabeceras));
		 
		 try {
		  List listaFila = null;
		  
		  for (int i = 0; i < lstEncuestaRespuestasAlumnosVer1.size(); i++) {
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
		
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
																/*		Encuesta Docentes		*/
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List<DtoEncuRespAlumVer1Totales> generarListaEncuestaGlobalDocentes(List<dtoUsuario> lstUsuario, List<DtoEncuestaRespuestasAlumnosVer1> lstEncuestaRespuestasAlumnosVer1){
			
			// Lista e Instancia de los totales de la encuesta
			List<DtoEncuRespAlumVer1Totales> lstEncuRespAlumVer1Totales = new ArrayList<DtoEncuRespAlumVer1Totales>();
			DtoEncuRespAlumVer1Totales objEncuRespAlumVer1Totales = new DtoEncuRespAlumVer1Totales();
											
			try{
				
				// Crear estructura de Objeto
				
					//////////////////////////// DATOS PERSONALES //////////////////////////
					// Secundaria Procedencia
					SecuProc objSecuProc = new SecuProc();
					List<String> lstSecundaria = new ArrayList<String>();
					objEncuRespAlumVer1Totales.setObjSecuProc(objSecuProc);
					objEncuRespAlumVer1Totales.getObjSecuProc().setLstNombreSecundaria(lstSecundaria);
					objEncuRespAlumVer1Totales.getObjSecuProc().setTotalSecundarias(0);


					// Bachillerato Procedencia
					BachoProc objBachoProc = new BachoProc();
					OtraBachillerato objOtraBachillerato = new OtraBachillerato();
					List<String> lstBachillerato = new ArrayList<String>();
					objEncuRespAlumVer1Totales.setObjBachoProc(objBachoProc);
					objEncuRespAlumVer1Totales.getObjBachoProc().setOtra(objOtraBachillerato);
					objEncuRespAlumVer1Totales.getObjBachoProc().getOtra().setLstNombreBachillerato(lstBachillerato);
					objEncuRespAlumVer1Totales.getObjBachoProc().setCch(0);
					objEncuRespAlumVer1Totales.getObjBachoProc().setEnp(0);
					objEncuRespAlumVer1Totales.getObjBachoProc().getOtra().setTotal(0);
					
					
					// Edad
					Edad objEdad = new Edad();
					List<String> lstEdad = new ArrayList<String>(); 
					objEncuRespAlumVer1Totales.setObjEdad(objEdad);
					objEncuRespAlumVer1Totales.getObjEdad().setLstEdades(lstEdad);
					objEncuRespAlumVer1Totales.getObjEdad().setTotal(0);
					
					
					// Sexo
					Sexo objSexo = new Sexo();
					objEncuRespAlumVer1Totales.setObjSexo(objSexo);
					objEncuRespAlumVer1Totales.getObjSexo().setFemenino(0);
					objEncuRespAlumVer1Totales.getObjSexo().setMasculino(0);
					
					// Area / Carrera
					AreaCarrera objAreaCarrera = new AreaCarrera();
					List<String> lstAreaCarrera = new ArrayList<String>();
					objEncuRespAlumVer1Totales.setObjAreaCarrera(objAreaCarrera);
					objEncuRespAlumVer1Totales.getObjAreaCarrera().setLstAreasCarreras(lstAreaCarrera);
					objEncuRespAlumVer1Totales.getObjAreaCarrera().setTotal(0);
					
					// Anio Escolar / Semestre
					AnioSemestre objAnioSemestre = new AnioSemestre();
					List<String> lstAnioSemestre = new ArrayList<String>();
					objEncuRespAlumVer1Totales.setObjAnioSemestre(objAnioSemestre);
					objEncuRespAlumVer1Totales.getObjAnioSemestre().setLstSemestres(lstAnioSemestre);
					objEncuRespAlumVer1Totales.getObjAnioSemestre().setTotal(0);
					
					//////////////////////////// DATOS ENCUESTA //////////////////////////
					// llCompte1
					LlCompte1 objLlCompte1 = new LlCompte1();
					objEncuRespAlumVer1Totales.setObjllCompte1(objLlCompte1);
					objEncuRespAlumVer1Totales.getObjllCompte1().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte1().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte1().setTres(0);
					objEncuRespAlumVer1Totales.getObjllCompte1().setCuatro(0);
					objEncuRespAlumVer1Totales.getObjllCompte1().setCinco(0);
		
					// llCompte2
					LlCompte2 objLlCompte2 = new LlCompte2();
					LlCompte2a objLlCompte2a = new LlCompte2a();
					objEncuRespAlumVer1Totales.setObjllCompte2(objLlCompte2);
					objEncuRespAlumVer1Totales.getObjllCompte2().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte2().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte2().setObjllCompte2a(objLlCompte2a);
						objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setUno(0);
						objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setDos(0);
						objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setTres(0);
						objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setCuatro(0);
					
					// llCompte3
					DsCompte3 objDsCompte3 = new DsCompte3();
					List<String> lstDsCompte3 = new ArrayList<String>();
					objEncuRespAlumVer1Totales.setObjdsCompte3(objDsCompte3);
					objEncuRespAlumVer1Totales.getObjdsCompte3().setLstOtrosCompte3(lstDsCompte3);
					objEncuRespAlumVer1Totales.getObjdsCompte3().setUno(0);
					objEncuRespAlumVer1Totales.getObjdsCompte3().setDos(0);
					objEncuRespAlumVer1Totales.getObjdsCompte3().setTres(0);
					objEncuRespAlumVer1Totales.getObjdsCompte3().setCuatro(0);
					objEncuRespAlumVer1Totales.getObjdsCompte3().setCinco(0);
						objEncuRespAlumVer1Totales.getObjdsCompte3().setTotalOtros(0);
						
					// llCompte4
					LlCompte4 objLlCompte4 = new LlCompte4();	
					objEncuRespAlumVer1Totales.setObjllCompte4(objLlCompte4);	
					objEncuRespAlumVer1Totales.getObjllCompte4().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte4().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte4().setTres(0);
					objEncuRespAlumVer1Totales.getObjllCompte4().setCuatro(0);
					objEncuRespAlumVer1Totales.getObjllCompte4().setCinco(0);
					
					// llCompte5
					LlCompte5 objLlCompte5 = new LlCompte5();
					objEncuRespAlumVer1Totales.setObjllCompte5(objLlCompte5);
					objEncuRespAlumVer1Totales.getObjllCompte5().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte5().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte5().setTres(0);
					
					// llCompte6
					LlCompte6 objLlCompte6 = new LlCompte6();
					objEncuRespAlumVer1Totales.setObjllCompte6(objLlCompte6);
					objEncuRespAlumVer1Totales.getObjllCompte6().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte6().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte6().setTres(0);
					objEncuRespAlumVer1Totales.getObjllCompte6().setCuatro(0);
					
					// llCompte7
					LlCompte7 objLlCompte7 = new LlCompte7();
					objEncuRespAlumVer1Totales.setObjllCompte7(objLlCompte7);
					objEncuRespAlumVer1Totales.getObjllCompte7().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte7().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte7().setTres(0);
					objEncuRespAlumVer1Totales.getObjllCompte7().setCuatro(0);
					objEncuRespAlumVer1Totales.getObjllCompte7().setCinco(0);
					
					// llCompte8
					LlCompte8 objLlCompte8 = new LlCompte8();
					objEncuRespAlumVer1Totales.setObjllCompte8(objLlCompte8);
					objEncuRespAlumVer1Totales.getObjllCompte8().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte8().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte8().setTres(0);
					objEncuRespAlumVer1Totales.getObjllCompte8().setCuatro(0);
					objEncuRespAlumVer1Totales.getObjllCompte8().setCinco(0);
					
					// llCompte9
					LlCompte9 objLlCompte9 = new LlCompte9();
					objEncuRespAlumVer1Totales.setObjllCompte9(objLlCompte9);
					objEncuRespAlumVer1Totales.getObjllCompte9().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte9().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte9().setTres(0);
					objEncuRespAlumVer1Totales.getObjllCompte9().setCuatro(0);
					objEncuRespAlumVer1Totales.getObjllCompte9().setCinco(0);
					
					// llCompte10
					LlCompte10 objLlCompte10 = new LlCompte10();
					objEncuRespAlumVer1Totales.setObjllCompte10(objLlCompte10);
					objEncuRespAlumVer1Totales.getObjllCompte10().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte10().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte10().setTres(0);
					objEncuRespAlumVer1Totales.getObjllCompte10().setCuatro(0);
					
					// llCompte11
					LlCompte11 objLlCompte11 = new LlCompte11();
					LlCompte11a objLlCompte11a = new LlCompte11a();
					objEncuRespAlumVer1Totales.setObjllCompte11(objLlCompte11);
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11a(objLlCompte11a);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().setTres(0);

					LlCompte11b objLlCompte11b = new LlCompte11b();
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11b(objLlCompte11b);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().setTres(0);
					
					LlCompte11c objLlCompte11c = new LlCompte11c();
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11c(objLlCompte11c);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().setTres(0);
					
					LlCompte11d objLlCompte11d = new LlCompte11d();
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11d(objLlCompte11d);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().setTres(0);
					
					LlCompte11e objLlCompte11e = new LlCompte11e();
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11e(objLlCompte11e);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().setTres(0);
					
					LlCompte11f objLlCompte11f = new LlCompte11f();
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11f(objLlCompte11f);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().setTres(0);
					
					LlCompte11g objLlCompte11g = new LlCompte11g();
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11g(objLlCompte11g);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().setTres(0);
					
					LlCompte11h objLlCompte11h = new LlCompte11h();
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11h(objLlCompte11h);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().setTres(0);
					
					LlCompte11i objLlCompte11i = new LlCompte11i();
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11i(objLlCompte11i);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().setTres(0);
					
					LlCompte11j objLlCompte11j = new LlCompte11j();
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11j(objLlCompte11j);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().setTres(0);
					
					LlCompte11k objLlCompte11k = new LlCompte11k();
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11k(objLlCompte11k);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().setTres(0);
					
					LlCompte11l objLlCompte11l = new LlCompte11l();
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11l(objLlCompte11l);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().setTres(0);
					
					LlCompte11m objLlCompte11m = new LlCompte11m();
					objEncuRespAlumVer1Totales.getObjllCompte11().setObjllCompte11m(objLlCompte11m);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().setTres(0);
						LlCompte11Otro objLlCompte11Otro = new LlCompte11Otro();
						objEncuRespAlumVer1Totales.getObjllCompte11().setObjOtro(objLlCompte11Otro);
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjOtro().setTotal(0);
						List<String> lstCompte11Otro = new ArrayList<String>();
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjOtro().setObjOtroCompte11(lstCompte11Otro);
					
					// llCompte12
					LlCompte12 objLlCompte12 = new LlCompte12();
					LlCompte12a objLlCompte12a = new LlCompte12a();
					objEncuRespAlumVer1Totales.setObjllCompte12(objLlCompte12);
					objEncuRespAlumVer1Totales.getObjllCompte12().setObjllCompte12a(objLlCompte12a);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().setTres(0);

					LlCompte12b objLlCompte12b = new LlCompte12b();
					objEncuRespAlumVer1Totales.getObjllCompte12().setObjllCompte12b(objLlCompte12b);				
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().setTres(0);
					
					LlCompte12c objLlCompte12c = new LlCompte12c();
					objEncuRespAlumVer1Totales.getObjllCompte12().setObjllCompte12c(objLlCompte12c);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().setTres(0);
					
					LlCompte12d objLlCompte12d = new LlCompte12d();
					objEncuRespAlumVer1Totales.getObjllCompte12().setObjllCompte12d(objLlCompte12d);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().setTres(0);
					
					LlCompte12e objLlCompte12e = new LlCompte12e();
					objEncuRespAlumVer1Totales.getObjllCompte12().setObjllCompte12e(objLlCompte12e);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().setUno(0);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().setDos(0);
					objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().setTres(0);

				// Crear estructura de Objeto
					
					
				// Total de Encuestas Recuperadas de BD
				int totalEncuestas = lstEncuestaRespuestasAlumnosVer1.size();
				
				// Contador
				objEncuRespAlumVer1Totales.setTotalEncuestas(totalEncuestas);
				
				// Iterar la totalidad de las encuestas
				for(int x=0; x<totalEncuestas; x++){
					
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
												//////////////////// DATOS PEROSNALES ////////////////////
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
					/////////////////////////////////////////////////// Secundaria Procedencia ////////////////////////////////////////////////////////
					if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsSecunProc()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsSecunProc().equals(""))){
						// Actualizar contador
						int totalSecundarias = objEncuRespAlumVer1Totales.getObjSecuProc().getTotalSecundarias();
						totalSecundarias = totalSecundarias + 1;
						objEncuRespAlumVer1Totales.getObjSecuProc().setTotalSecundarias(totalSecundarias);
						
						// Agregar Secundaria
						objEncuRespAlumVer1Totales.getObjSecuProc().getLstNombreSecundaria().add(lstEncuestaRespuestasAlumnosVer1.get(x).getDsSecunProc());
					}
					
					/////////////////////////////////////////////////// Secundaria Procedencia ////////////////////////////////////////////////////////
				
					/////////////////////////////////////////////////// Bachillerato Procedencia ////////////////////////////////////////////////////////
					if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProc()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProc().equals(""))){
						// Actualizar contador
						if(lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProc().equals("CCH")){
							int totalBachilleratoCCH = objEncuRespAlumVer1Totales.getObjBachoProc().getCch();
							totalBachilleratoCCH = totalBachilleratoCCH + 1;
							objEncuRespAlumVer1Totales.getObjBachoProc().setCch(totalBachilleratoCCH);
						} 
						if(lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProc().equals("ENP")){
							int totalBachilleratoENP = objEncuRespAlumVer1Totales.getObjBachoProc().getEnp();
							totalBachilleratoENP = totalBachilleratoENP + 1;
							objEncuRespAlumVer1Totales.getObjBachoProc().setEnp(totalBachilleratoENP);
						}
					}
					
					if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProcOtra()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProcOtra().equals(""))){
						// Aumentar Contador
						int totalBachilleratoOtros = objEncuRespAlumVer1Totales.getObjBachoProc().getOtra().getTotal();
						totalBachilleratoOtros = totalBachilleratoOtros + 1;
						objEncuRespAlumVer1Totales.getObjBachoProc().getOtra().setTotal(totalBachilleratoOtros);
						
						// Agregamos nuevo bachillerato a la lista
						objEncuRespAlumVer1Totales.getObjBachoProc().getOtra().getLstNombreBachillerato().add(lstEncuestaRespuestasAlumnosVer1.get(x).getDsBachilleProcOtra());
					}
					/////////////////////////////////////////////////// Bachillerato Procedencia ////////////////////////////////////////////////////////
				
					/////////////////////////////////////////////////// Edad ////////////////////////////////////////////////////////
					if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsEdad()!=null) &&(!lstEncuestaRespuestasAlumnosVer1.get(x).getDsEdad().equals(""))){
						int totalEdades = objEncuRespAlumVer1Totales.getObjEdad().getTotal();
						totalEdades = totalEdades + 1;
						objEncuRespAlumVer1Totales.getObjEdad().setTotal(totalEdades);
						
						// Agregamos nueva edad a la lista
						objEncuRespAlumVer1Totales.getObjEdad().getLstEdades().add(lstEncuestaRespuestasAlumnosVer1.get(x).getDsEdad());
					}
					/////////////////////////////////////////////////// Edad ////////////////////////////////////////////////////////
					
					/////////////////////////////////////////////////// Sexo ////////////////////////////////////////////////////////
					if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsSexo()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsSexo().equals(""))){
						if(lstEncuestaRespuestasAlumnosVer1.get(x).getDsSexo().equals("F")){
							int totalFemenino = objEncuRespAlumVer1Totales.getObjSexo().getFemenino();
							totalFemenino = totalFemenino + 1;
							objEncuRespAlumVer1Totales.getObjSexo().setFemenino(totalFemenino);
						}
						
						if(lstEncuestaRespuestasAlumnosVer1.get(x).getDsSexo().equals("M")){
							int totalMaculino = objEncuRespAlumVer1Totales.getObjSexo().getMasculino();
							totalMaculino = totalMaculino + 1;
							objEncuRespAlumVer1Totales.getObjSexo().setMasculino(totalMaculino);
						}
					}
					/////////////////////////////////////////////////// Sexo ////////////////////////////////////////////////////////
				
					/////////////////////////////////////////////////// Area/Carrera ////////////////////////////////////////////////////////
					if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsCarrera()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsCarrera().equals(""))){
						// Aumentar Contador
						int totalAreasCarreras = objEncuRespAlumVer1Totales.getObjAreaCarrera().getTotal();
						totalAreasCarreras = totalAreasCarreras + 1;
						objEncuRespAlumVer1Totales.getObjAreaCarrera().setTotal(totalAreasCarreras);
						
						String Carrera = "";
						int idCarrera = Integer.parseInt(lstEncuestaRespuestasAlumnosVer1.get(x).getDsCarrera());
						
						// Agregamos nueva Area o Carrera a la lista
						if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlnivel()==NIVEL_BACHILLERATO){
							if(idCarrera==AREA_FisicoMatematicasIngenierias)
								Carrera="FÍSICO-MATEMÁTICAS E INGENIERÍAS";
							if(idCarrera==AREA_BIOLOGICAS_DELASALUD)
								Carrera="BIOLÓGICAS Y DE LA SALUD";
							if(idCarrera==AREA_SOCIALES)
								Carrera="SOCIALES";
							if(idCarrera==AREA_HUMANIDADES_LASARTES)
								Carrera="HUMANIDADES Y LAS ARTES";
						}
						
						if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlnivel()==NIVEL_LICENCIATURA){
							Carrera = "";
							
							if(idCarrera==ENTIDAD_Actuaria)
								Carrera="Actuaría";
							if(idCarrera==ENTIDAD_IngCivil || idCarrera==ENTIDAD_IngCivil2)
								Carrera="Ing. Civil";
							if(idCarrera==ENTIDAD_MatematicasAplicComp)
								Carrera="Matemáticas Aplic. y Comp.";
							if(idCarrera==ENTIDAD_IngComputacion)	
								Carrera="Ing. en Computación";
							if(idCarrera==ENTIDAD_IngIndustrial || idCarrera==ENTIDAD_IngIndustrial2)
								Carrera="Ing. Industrial";
							if(idCarrera==ENTIDAD_IngElectricaElectronica)
								Carrera="Ing. Eléctrica y Electrónica";
							if(idCarrera==ENTIDAD_IngMecanica)
								Carrera="Ing. Mecánica";
							if(idCarrera==ENTIDAD_IngMecanicaElectrica || idCarrera==ENTIDAD_IngMecanicaElectrica2)
								Carrera="Ing. Mecánica Eléctrica";
							if(idCarrera==ENTIDAD_Tecnologia)
								Carrera="Tecnología";
							if(idCarrera==ENTIDAD_IngTelecomunicacionesSistElect)
								Carrera="Ing. en Telecomunicaciones, Sist. y Elect.";
						}
						objEncuRespAlumVer1Totales.getObjAreaCarrera().getLstAreasCarreras().add(Carrera);
					}
					/////////////////////////////////////////////////// Area/Carrera ////////////////////////////////////////////////////////
				
					/////////////////////////////////////////////////// Ciclo Escolar ////////////////////////////////////////////////////////
					if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsCicloEscolar()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsCicloEscolar().equals(""))){
						// Aumentar Contador
						int totalCicloEscolar = objEncuRespAlumVer1Totales.getObjAnioSemestre().getTotal();
						totalCicloEscolar = totalCicloEscolar + 1;
						objEncuRespAlumVer1Totales.getObjAnioSemestre().setTotal(totalCicloEscolar);
							
						// Agregamos nuevo Ciclo escolar a la lista
						objEncuRespAlumVer1Totales.getObjAnioSemestre().getLstSemestres().add(lstEncuestaRespuestasAlumnosVer1.get(x).getDsCicloEscolar());
					}
					/////////////////////////////////////////////////// Ciclo Escolar ////////////////////////////////////////////////////////
				
					
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
													//////////////////// DATOS ENCUESTA ////////////////////
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
					/////////////////////////////////////////////////// llCompte1 ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte1()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte1();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte1().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte1().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte1().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte1().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte1().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte1().setTres(total);
						}
						if(res==4){
							int total = objEncuRespAlumVer1Totales.getObjllCompte1().getCuatro();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte1().setCuatro(total);
						}
						if(res==5){
							int total = objEncuRespAlumVer1Totales.getObjllCompte1().getCinco();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte1().setCinco(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte2 ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte2()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte2();
					
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte2().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte2().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte2().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte2().setDos(total);
						}			
					}
					
					/////////////////////////////////////////////////// llCompte2a ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte2a()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte2a();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setTres(total);
						}
						if(res==4){
							int total = objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().getCuatro();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte2().getObjllCompte2a().setCuatro(total);
						}
					}
					
		
					/////////////////////////////////////////////////// llCompte3 ////////////////////////////////////////////////////////
					if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte3() !=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte3().equals(""))){
						// Aumentar Contador
						String[] opciones = lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte3().split(",");
						boolean uno = false;
						boolean dos = false;
						boolean tres = false;
						boolean cuatro = false;
						boolean cinco = false;
			
						for(int y=0; y<opciones.length; y++){
							if(Integer.parseInt(opciones[y])==1)
								uno = true;
							if(Integer.parseInt(opciones[y])==2)
								dos = true;
							if(Integer.parseInt(opciones[y])==3)
								tres = true;
							if(Integer.parseInt(opciones[y])==4)
								cuatro = true;
							if(Integer.parseInt(opciones[y])==5)
								cinco = true;
						}
						
						if(uno == true){
							int total = objEncuRespAlumVer1Totales.getObjdsCompte3().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjdsCompte3().setUno(total);
						}
						if(dos == true){
							int total = objEncuRespAlumVer1Totales.getObjdsCompte3().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjdsCompte3().setDos(total);
						}
						if(tres == true){
							int total = objEncuRespAlumVer1Totales.getObjdsCompte3().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjdsCompte3().setTres(total);
						}
						if(cuatro == true){
							int total = objEncuRespAlumVer1Totales.getObjdsCompte3().getCuatro();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjdsCompte3().setCuatro(total);
						}
						if(cinco == true){
							int total = objEncuRespAlumVer1Totales.getObjdsCompte3().getCinco();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjdsCompte3().setCinco(total);
						}
					}
					
					
					/////////////////////////////////////////////////// llCompte3Otra ////////////////////////////////////////////////////////
					if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte3otro()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte3otro().equals(""))){
						// Aumentar Contador
						int total = objEncuRespAlumVer1Totales.getObjdsCompte3().getTotalOtros();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjdsCompte3().setTotalOtros(total);
						
						// Agregar Otro
						objEncuRespAlumVer1Totales.getObjdsCompte3().getLstOtrosCompte3().add(lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte3otro());
					}
					
					
					/////////////////////////////////////////////////// llCompte4 ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte4()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte4();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte4().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte4().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte4().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte4().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte4().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte4().setTres(total);
						}
						if(res==4){
							int total = objEncuRespAlumVer1Totales.getObjllCompte4().getCuatro();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte4().setCuatro(total);
						}
						if(res==5){
							int total = objEncuRespAlumVer1Totales.getObjllCompte4().getCinco();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte4().setCinco(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte5 ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte5()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte5();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte5().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte5().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte5().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte5().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte5().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte5().setTres(total);
						}
					}
					
					
					/////////////////////////////////////////////////// llCompte6 ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte6()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte6();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte6().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte6().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte6().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte6().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte6().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte6().setTres(total);
						}
						if(res==4){
							int total = objEncuRespAlumVer1Totales.getObjllCompte6().getCuatro();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte6().setCuatro(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte7 ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte7()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte7();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte7().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte7().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte7().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte7().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte7().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte7().setTres(total);
						}
						if(res==4){
							int total = objEncuRespAlumVer1Totales.getObjllCompte7().getCuatro();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte7().setCuatro(total);
						}
						if(res==5){
							int total = objEncuRespAlumVer1Totales.getObjllCompte7().getCinco();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte7().setCinco(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte8 ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte8()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte8();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte8().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte8().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte8().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte8().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte8().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte8().setTres(total);
						}
						if(res==4){
							int total = objEncuRespAlumVer1Totales.getObjllCompte8().getCuatro();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte8().setCuatro(total);
						}
						if(res==5){
							int total = objEncuRespAlumVer1Totales.getObjllCompte8().getCinco();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte8().setCinco(total);
						}
					}
					
					
					/////////////////////////////////////////////////// llCompte9 ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte9()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte9();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte9().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte9().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte9().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte9().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte9().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte9().setTres(total);
						}
						if(res==4){
							int total = objEncuRespAlumVer1Totales.getObjllCompte9().getCuatro();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte9().setCuatro(total);
						}
						if(res==5){
							int total = objEncuRespAlumVer1Totales.getObjllCompte9().getCinco();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte9().setCinco(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte10 ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte10()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte10();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte10().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte10().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte10().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte10().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte10().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte10().setTres(total);
						}
						if(res==4){
							int total = objEncuRespAlumVer1Totales.getObjllCompte10().getCuatro();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte10().setCuatro(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11a ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11a()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11a();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11a().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11b ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11b()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11b();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11b().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11c ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11c()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11c();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11c().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11d ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11d()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11d();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11d().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11e ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11e()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11e();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11e().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11f ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11f()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11f();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11f().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11g ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11g()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11g();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11g().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11h ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11h()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11h();
						
						if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().setUno(total);
						}
						if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().setDos(total);
						}
						if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11h().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11i ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11i()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11i();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11i().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11j ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11j()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11j();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11j().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11k ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11k()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11k();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11k().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11l ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11l()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11l();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11l().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11m ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11m()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte11m();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte11().getObjllCompte11m().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte11n ////////////////////////////////////////////////////////
					if((lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte11n()!=null) && (!lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte11n().equals(""))){
						// Aumentar Contador
						String res = lstEncuestaRespuestasAlumnosVer1.get(x).getDsCompte11n();
						int total = objEncuRespAlumVer1Totales.getObjllCompte11().getObjOtro().getTotal();
						total = total+1;
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjOtro().setTotal(total);
						
						// Agregar a la lista de otros
						objEncuRespAlumVer1Totales.getObjllCompte11().getObjOtro().getObjOtroCompte11().add(res);				
					}
					
					/////////////////////////////////////////////////// llCompte12a ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12a()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12a();
						
						if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().setUno(total);
						}
						if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().setDos(total);
						}
						if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12a().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte12b ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12b()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12b();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12b().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte12c ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12c()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12c();
						
						if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().setUno(total);
						}
						if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().setDos(total);
						}
						if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12c().setTres(total);
						}
					}
					
					/////////////////////////////////////////////////// llCompte12d ////////////////////////////////////////////////////////
						if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12d()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12d();
						
						if(res==1){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().getUno();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().setUno(total);
						}
						if(res==2){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().getDos();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().setDos(total);
						}
						if(res==3){
						int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().getTres();
						total = total + 1;
						objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12d().setTres(total);
						}
					}
						
					/////////////////////////////////////////////////// llCompte12e ////////////////////////////////////////////////////////
					if(lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12e()!=0){
						// Aumentar Contador
						int res = lstEncuestaRespuestasAlumnosVer1.get(x).getLlCompte12e();
						
						if(res==1){
							int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().getUno();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().setUno(total);
						}
						if(res==2){
							int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().getDos();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().setDos(total);
						}
						if(res==3){
							int total = objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().getTres();
							total = total + 1;
							objEncuRespAlumVer1Totales.getObjllCompte12().getObjllCompte12e().setTres(total);
						}
					}
					
				}	// FOR
				
				// Lista e Instancia de los totales de la encuesta
				lstEncuRespAlumVer1Totales.add(objEncuRespAlumVer1Totales);
			} catch(Exception ex){
				System.out.println(ex.getMessage());
			}
			
			return lstEncuRespAlumVer1Totales;
		}
		
		
		/*
		 * Getters and Setters
		 */
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

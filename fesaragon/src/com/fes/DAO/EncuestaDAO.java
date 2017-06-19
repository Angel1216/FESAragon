package com.fes.DAO;

import java.util.HashMap;
import java.util.Map;

public class EncuestaDAO extends DAO {
	
	public int insertarEncuesta(String sql){
		int res = 0;
		try{
			// Insertar en BD
			res = actualizaBD(sql);
		} catch(Exception e){
			e.getMessage();
		}
		return res;
	}
	
	public String validarCuenta(String cuenta){
		String sql = "SELECT llcuenta FROM encuestaRespuestasAlumnosVer1 WHERE llcuenta = " + cuenta;
		String res = "";
		try{
			// Insertar en BD
			res = validaCuenta(sql);
		} catch(Exception e){
			e.getMessage();
		}
		return res;
	}
	
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
		case "area":
			key="llArea";
			value="dsArea";
			sql = "SELECT llArea, dsArea FROM catalogoArea";
			break;
		case "docentesBachillerato":
			key="llArea";
			value="dsArea";
			sql = "SELECT llArea, dsArea FROM catalogoArea";
			break;
		case "docentesLicenciatura":
			key="llEntidad";
			value="dsEntidad";
			sql="SELECT llEntidad, dsEntidad FROM catalogoEntidad WHERE llNivelID="+ parametros.get("nivel")+" and llCampus="+parametros.get("campus");
			break;
		}
		
		// Obtenemos Catalogo
		mpCatalogo = cargaCombo(sql, key, value);
		
		return mpCatalogo;
	}

}

package com.fes.DAO;

import java.util.HashMap;
import java.util.Map;
import com.fes.DAO.DAO;

public class PreEncuestaAlumnosDAO extends DAO{	
	
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
}

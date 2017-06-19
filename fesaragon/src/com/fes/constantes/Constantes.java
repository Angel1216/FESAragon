package com.fes.constantes;

import java.util.HashMap;
import java.util.Map;

public class Constantes{
	
	// CONSTATNTES
	
	// Tipo Menus
	protected final int MENU_ConsultarEncuestaAlumno = 1;
	protected final int MENU_LlenarEncuestaDocentes = 2;
	protected final int MENU_ConsultarEncuestaDocentes = 3;
	
	// Tipo de Encuesta
	protected final int TipoEncuesta_Alumno = 1;
	protected final int TipoEncuesta_Profesor = 2;

	// Niveles
	protected final int NIVEL_BACHILLERATO = 1;
	protected final int NIVEL_LICENCIATURA = 2;

	// Campus
	protected final int CAMPUS_CCH = 1;
	protected final int CAMPUS_ENP = 2;
	protected final int CAMPUS_BUNAM = 3;
	protected final int CAMPUS_FESACATLAN = 4;
	protected final int CAMPUS_FESARAGON = 5;
	protected final int CAMPUS_FESCUAUTITLAN = 6;

	// Entidad
	protected final int ENTIDAD_Sur = 1;
	protected final int ENTIDAD_Oriente = 2;
	protected final int ENTIDAD_Vallejo = 3;
	protected final int ENTIDAD_Azcapotzalco = 4;
	protected final int ENTIDAD_Naucalpan = 5;
	protected final int ENTIDAD_GabinoBarreda = 6;
	protected final int ENTIDAD_ErasmoCQuinto = 7;
	protected final int ENTIDAD_JustoSierra = 8;
	protected final int ENTIDAD_VidalCastanedaN = 9;
	protected final int ENTIDAD_JoseVasconcelos = 10;
	protected final int ENTIDAD_AntonioCaso = 11;
	protected final int ENTIDAD_EzequielAChavez = 12;
	protected final int ENTIDAD_MiguelESchulz = 13;
	protected final int ENTIDAD_PedroDeAlba = 14;
	protected final int ENTIDAD_Actuaria = 15;
	protected final int ENTIDAD_IngCivil = 16;
	protected final int ENTIDAD_MatematicasAplicComp = 17;
	protected final int ENTIDAD_IngComputacion = 18;
	protected final int ENTIDAD_IngIndustrial = 19;
	protected final int ENTIDAD_IngCivil2 = 20;
	protected final int ENTIDAD_IngElectricaElectronica = 21;
	protected final int ENTIDAD_IngMecanica = 22;
	protected final int ENTIDAD_IngMecanicaElectrica = 23;
	protected final int ENTIDAD_IngMecanicaElectrica2 = 24;
	protected final int ENTIDAD_Tecnologia = 25;
	protected final int ENTIDAD_IngIndustrial2 = 26;
	protected final int ENTIDAD_IngTelecomunicacionesSistElect = 27;

	// Areas
	protected final int AREA_FisicoMatematicasIngenierias = 1;
	protected final int AREA_BIOLOGICAS_DELASALUD = 2;
	protected final int AREA_SOCIALES = 3;
	protected final int AREA_HUMANIDADES_LASARTES = 4;
	
	// Componentes Alumnos	
	private Map<String, String> mpllTipoEncuesta;
	private Map<String, String> mpllNivel;
	private Map<String, String> mpllCampus;
	private Map<String, String> mpllEntidad;
	private Map<String, String> mpllArea;
	private Map<String, String> mpllCompte1;
	private Map<String, String> mpllCompte2;
	private Map<String, String> mpllCompte2a;
	private Map<String, String> mpdsCompte3;
	private Map<String, String> mpllCompte4;
	private Map<String, String> mpllCompte5;
	private Map<String, String> mpllCompte6;
	private Map<String, String> mpllCompte7;
	private Map<String, String> mpllCompte8;
	private Map<String, String> mpllCompte9;
	private Map<String, String> mpllCompte10;
	private Map<String, String> mpllCompte11a;
	private Map<String, String> mpllCompte11b;
	private Map<String, String> mpllCompte11c;
	private Map<String, String> mpllCompte11d;
	private Map<String, String> mpllCompte11e;
	private Map<String, String> mpllCompte11f;
	private Map<String, String> mpllCompte11g;
	private Map<String, String> mpllCompte11h;
	private Map<String, String> mpllCompte11i;
	private Map<String, String> mpllCompte11j;
	private Map<String, String> mpllCompte11k;
	private Map<String, String> mpllCompte11l;
	private Map<String, String> mpllCompte11m;
	private Map<String, String> mpllCompte12a;
	private Map<String, String> mpllCompte12b;
	private Map<String, String> mpllCompte12c;
	private Map<String, String> mpllCompte12d;
	private Map<String, String> mpllCompte12e;
	
	// Componentes Alumnos
	private Map<String, String> mpllAntiguedad;
	private Map<String, String> mpllSexo;
	private Map<String, String> mpllCarrerasImparte;
	private Map<String, String> mpllDocCompte1;
	private Map<String, String> mpllDocCompte2;
	private Map<String, String> mpllDocCompte3;
	private Map<String, String> mpllDocCompte4;
	private Map<String, String> mpllDocCompte5;
	private Map<String, String> mpllDocCompte6;
	private Map<String, String> mpllDocCompte7;
	private Map<String, String> mpllDocCompte8;
	private Map<String, String> mpllDocCompte9;
	private Map<String, String> mpllDocCompte10;
	private Map<String, String> mpllDocCompte11;
	private Map<String, String> mpllDocCompte12;
	private Map<String, String> mpllDocCompte13;
	private Map<String, String> mpllDocCompte14;
	private Map<String, String> mpllDocCompte15;
	
	
	// Metodos
	public void cargaMapasComponentes(){
	
		mpllArea = new HashMap<String, String>();
		mpllArea.put("1", "FÍSICO-MATEMÁTICAS E INGENIERÍAS");
		mpllArea.put("2", "BIOLÓGICAS Y DE LA SALUD");
		mpllArea.put("3", "SOCIALES");
		mpllArea.put("4", "HUMANIDADES Y LAS ARTES");
		
		
		mpllEntidad = new HashMap<String, String>();
		mpllEntidad.put("1", "Sur");
		mpllEntidad.put("2", "Oriente");
		mpllEntidad.put("3", "Vallejo");
		mpllEntidad.put("4", "Azcapotzalco");
		mpllEntidad.put("5", "Naucalpan");
		mpllEntidad.put("6", "Gabino Barreda");
		mpllEntidad.put("7", "Erasmo C. Quinto");
		mpllEntidad.put("8", "Justo Sierra");
		mpllEntidad.put("9", "Vidal Castañeda y N");
		mpllEntidad.put("10", "José Vasconcelos");
		mpllEntidad.put("11", "Antonio Caso");
		mpllEntidad.put("12", "Ezequiel A Chávez");
		mpllEntidad.put("13", "Miguel E Schulz");
		mpllEntidad.put("14", "Pedro de Alba");
		mpllEntidad.put("15", "Actuaría");
		mpllEntidad.put("16", "Ing. Civil");
		mpllEntidad.put("17", "Matemáticas Aplic. y Comp.");
		mpllEntidad.put("18", "Ing. en Computación");
		mpllEntidad.put("19", "Ing. Industrial");
		mpllEntidad.put("20", "Ing. Civil");
		mpllEntidad.put("21", "Ing. Eléctrica y Electrónica");
		mpllEntidad.put("22", "Ing. Mecánica");
		mpllEntidad.put("23", "Ing. Mecánica Eléctrica");
		mpllEntidad.put("24", "Ing. Mecánica Eléctrica");
		mpllEntidad.put("25", "Tecnología");
		mpllEntidad.put("26", "Ing. Industrial");
		mpllEntidad.put("27", "Ing. en Telecomunicaciones, Sist. y Elect.");
		
		
		mpllCampus = new HashMap<String, String>();
		mpllCampus.put("1", "CCH");
		mpllCampus.put("2", "ENP");
		mpllCampus.put("3", "B@UNAM");
		mpllCampus.put("4", "FES Acatlán");
		mpllCampus.put("5", "FES Aragón");
		mpllCampus.put("6", "FES Cuautitlán");
	
	
		// Tipo de Catalogos
		mpllTipoEncuesta = new HashMap<String, String>();
		mpllTipoEncuesta.put("1", "Alumno");
		mpllTipoEncuesta.put("2", "Profesor");
		
		// Niveles
		mpllNivel = new HashMap<String, String>();
		mpllNivel.put("1", "Bachillerato");
		mpllNivel.put("2", "Licenciatura");
		
		mpllCompte1 = new HashMap<String, String>();
		mpllCompte1.put("1", "Muy seguro");
		mpllCompte1.put("2", "Seguro");
		mpllCompte1.put("3", "No tan seguro");
		mpllCompte1.put("4", "Nada seguro");
		mpllCompte1.put("5", "Yo no quería esta área");
		
		mpllCompte2 = new HashMap<String, String>();
		mpllCompte2.put("1", "Si");
		mpllCompte2.put("2", "No (pasa a la pregunta 3)");
		
		mpllCompte2a = new HashMap<String, String>();
		mpllCompte2a.put("1", "10-30%");
		mpllCompte2a.put("2", "31-50%");
		mpllCompte2a.put("3", "51-70%");
		mpllCompte2a.put("4", "70-100%");
	
		mpdsCompte3 = new HashMap<String, String>();
		mpdsCompte3.put("1", "Al profesor");
		mpdsCompte3.put("2", "A un amigo");
		mpdsCompte3.put("3", "A un libro");
		mpdsCompte3.put("4", "Veo videos en Internet");
		mpdsCompte3.put("5", "No hago nada");

		mpllCompte4 = new HashMap<String, String>();
		mpllCompte4.put("1", "No se me dificulta");
		mpllCompte4.put("2", "Se me dificulta plantear el problema");
		mpllCompte4.put("3", "Se me dificulta resolver el problema");
		mpllCompte4.put("4", "Se me dificulta entender lo que se me pide");
		mpllCompte4.put("5", "Se me dificulta todo");
		
		mpllCompte5 = new HashMap<String, String>();
		mpllCompte5.put("1", "1 vez");
		mpllCompte5.put("2", "Más de una vez");
		mpllCompte5.put("3", "Ninguna (pasa a la pregunta 7)");
		
		mpllCompte6 = new HashMap<String, String>();
		mpllCompte6.put("1", "2 materias");
		mpllCompte6.put("2", "3 materias");
		mpllCompte6.put("3", "4 materias");
		mpllCompte6.put("4", "Ninguna");
		
		mpllCompte7 = new HashMap<String, String>();
		mpllCompte7.put("1", "Excelentes");
		mpllCompte7.put("2", "Buenos");
		mpllCompte7.put("3", "Suficientes");
		mpllCompte7.put("4", "Escasos");
		mpllCompte7.put("5", "Deficientes");
		
		mpllCompte8 = new HashMap<String, String>();
		mpllCompte8.put("1", "Muy útil");
		mpllCompte8.put("2", "Útil");
		mpllCompte8.put("3", "Poco útil");
		mpllCompte8.put("4", "Nada útil");
		mpllCompte8.put("5", "No sé");
		
		mpllCompte9 = new HashMap<String, String>();
		mpllCompte9.put("1", "½ hora – 1 hora");
		mpllCompte9.put("2", "1 – 2 horas");
		mpllCompte9.put("3", "2 o más horas");
		mpllCompte9.put("4", "Solo cuando hago tarea");
		mpllCompte9.put("5", "Nada");
		
		mpllCompte10 = new HashMap<String, String>();
		mpllCompte10.put("1", "Alto");
		mpllCompte10.put("2", "Bueno");
		mpllCompte10.put("3", "Regular");
		mpllCompte10.put("4", "Bajo");
		
		mpllCompte11a = new HashMap<String, String>();
		mpllCompte11a.put("1", "Lo conozco y manejo");
		mpllCompte11a.put("2", "Solo lo conozco");
		mpllCompte11a.put("3", "No lo conozco");

		mpllCompte11b = new HashMap<String, String>();
		mpllCompte11b.put("1", "Lo conozco y manejo");
		mpllCompte11b.put("2", "Solo lo conozco");
		mpllCompte11b.put("3", "No lo conozco");
		
		mpllCompte11c = new HashMap<String, String>();
		mpllCompte11c.put("1", "Lo conozco y manejo");
		mpllCompte11c.put("2", "Solo lo conozco");
		mpllCompte11c.put("3", "No lo conozco");
		
		mpllCompte11d = new HashMap<String, String>();
		mpllCompte11d.put("1", "Lo conozco y manejo");
		mpllCompte11d.put("2", "Solo lo conozco");
		mpllCompte11d.put("3", "No lo conozco");
		
		mpllCompte11e = new HashMap<String, String>();
		mpllCompte11e.put("1", "Lo conozco y manejo");
		mpllCompte11e.put("2", "Solo lo conozco");
		mpllCompte11e.put("3", "No lo conozco");
		
		mpllCompte11f = new HashMap<String, String>();
		mpllCompte11f.put("1", "Lo conozco y manejo");
		mpllCompte11f.put("2", "Solo lo conozco");
		mpllCompte11f.put("3", "No lo conozco");

		mpllCompte11g = new HashMap<String, String>();
		mpllCompte11g.put("1", "Lo conozco y manejo");
		mpllCompte11g.put("2", "Solo lo conozco");
		mpllCompte11g.put("3", "No lo conozco");
		
		mpllCompte11h = new HashMap<String, String>();
		mpllCompte11h.put("1", "Lo conozco y manejo");
		mpllCompte11h.put("2", "Solo lo conozco");
		mpllCompte11h.put("3", "No lo conozco");
		
		mpllCompte11i = new HashMap<String, String>();
		mpllCompte11i.put("1", "Lo conozco y manejo");
		mpllCompte11i.put("2", "Solo lo conozco");
		mpllCompte11i.put("3", "No lo conozco");
		
		mpllCompte11j = new HashMap<String, String>();
		mpllCompte11j.put("1", "Lo conozco y manejo");
		mpllCompte11j.put("2", "Solo lo conozco");
		mpllCompte11j.put("3", "No lo conozco");
		
		mpllCompte11k = new HashMap<String, String>();
		mpllCompte11k.put("1", "Lo conozco y manejo");
		mpllCompte11k.put("2", "Solo lo conozco");
		mpllCompte11k.put("3", "No lo conozco");
		
		mpllCompte11l = new HashMap<String, String>();
		mpllCompte11l.put("1", "Lo conozco y manejo");
		mpllCompte11l.put("2", "Solo lo conozco");
		mpllCompte11l.put("3", "No lo conozco");

		mpllCompte11m = new HashMap<String, String>();
		mpllCompte11m.put("1", "Lo conozco y manejo");
		mpllCompte11m.put("2", "Solo lo conozco");
		mpllCompte11m.put("3", "No lo conozco");
				
		mpllCompte12a = new HashMap<String, String>();
		mpllCompte12a.put("1", "Muy útil");
		mpllCompte12a.put("2", "Útil");
		mpllCompte12a.put("3", "Nada útil");
		
		mpllCompte12b = new HashMap<String, String>();
		mpllCompte12b.put("1", "Muy útil");
		mpllCompte12b.put("2", "Útil");
		mpllCompte12b.put("3", "Nada útil");
		
		mpllCompte12c = new HashMap<String, String>();
		mpllCompte12c.put("1", "Muy útil");
		mpllCompte12c.put("2", "Útil");
		mpllCompte12c.put("3", "Nada útil");
		
		mpllCompte12d = new HashMap<String, String>();
		mpllCompte12d.put("1", "Muy útil");
		mpllCompte12d.put("2", "Útil");
		mpllCompte12d.put("3", "Nada útil");
		
		mpllCompte12e = new HashMap<String, String>();
		mpllCompte12e.put("1", "Muy útil");
		mpllCompte12e.put("2", "Útil");
		mpllCompte12e.put("3", "Nada útil");
	}
	
	
	// Metodos
		public void cargaMapasComponentesDocentes(){
			
			// Campus
			mpllCampus = new HashMap<String, String>();
			mpllCampus.put("1", "CCH");
			mpllCampus.put("2", "ENP");
			mpllCampus.put("3", "B@UNAM");
			mpllCampus.put("4", "FES Acatlán");
			mpllCampus.put("5", "FES Aragón");
			mpllCampus.put("6", "FES Cuautitlán");
			
			// Tipo de Encuesta
			mpllTipoEncuesta = new HashMap<String, String>();
			mpllTipoEncuesta.put("1", "Alumno");
			mpllTipoEncuesta.put("2", "Profesor");
			
			// Niveles
			mpllNivel = new HashMap<String, String>();
			mpllNivel.put("1", "Bachillerato");
			mpllNivel.put("2", "Licenciatura");
		
			// Antiguedad
			mpllAntiguedad = new HashMap<String, String>();
			mpllAntiguedad.put("1", "1-5 años");
			mpllAntiguedad.put("2", "6-10 años");
			mpllAntiguedad.put("3", "11-15 años");
			mpllAntiguedad.put("4", "16 o más años");
			
			// Sexo
			mpllSexo = new HashMap<String, String>();
			mpllSexo.put("1", "Femenino");
			mpllSexo.put("2", "Masculino");
			
			// Carreras o areas dodne Imparte Clases
			mpllArea = new HashMap<String, String>();
			mpllArea.put("1", "FÍSICO-MATEMÁTICAS E INGENIERÍAS");
			mpllArea.put("2", "BIOLÓGICAS Y DE LA SALUD");
			mpllArea.put("3", "SOCIALES");
			mpllArea.put("4", "HUMANIDADES Y LAS ARTES");
			
			mpllCarrerasImparte = new HashMap<String, String>();
			mpllCarrerasImparte.put("15", "Actuaría");
			mpllCarrerasImparte.put("16", "Ing. Civil");
			mpllCarrerasImparte.put("17", "Matemáticas Aplic. y Comp.");
			mpllCarrerasImparte.put("18", "Ing. en Computación");
			mpllCarrerasImparte.put("19", "Ing. Industrial");
			mpllCarrerasImparte.put("20", "Ing. Civil");
			mpllCarrerasImparte.put("21", "Ing. Eléctrica y Electrónica");
			mpllCarrerasImparte.put("22", "Ing. Mecánica");
			mpllCarrerasImparte.put("23", "Ing. Mecánica Eléctrica");
			mpllCarrerasImparte.put("24", "Ing. Mecánica Eléctrica");
			mpllCarrerasImparte.put("25", "Tecnología");
			mpllCarrerasImparte.put("26", "Ing. Industrial");
			mpllCarrerasImparte.put("27", "Ing. en Telecomunicaciones, Sist. y Elect.");
			
			// Componente 1
			mpllDocCompte1 = new HashMap<String, String>();
			mpllDocCompte1.put("1", "Excelentes");
			mpllDocCompte1.put("2", "Buenos");
			mpllDocCompte1.put("3", "Escasos");
			mpllDocCompte1.put("4", "Deficientes");
			
			// Componente 2
			mpllDocCompte2 = new HashMap<String, String>();
			mpllDocCompte2.put("1", "Excelentes");
			mpllDocCompte2.put("2", "Buenas");
			mpllDocCompte2.put("3", "Escasas");
			mpllDocCompte2.put("4", "Deficientes");
			
			// Componente 3
			mpllDocCompte3 = new HashMap<String, String>();
			mpllDocCompte3.put("1", "Excelentes");
			mpllDocCompte3.put("2", "Buenas");
			mpllDocCompte3.put("3", "Escasas");
			mpllDocCompte3.put("4", "Deficientes");
			
			// Componente 4
			mpllDocCompte4 = new HashMap<String, String>();
			mpllDocCompte4.put("1", "Excelentes");
			mpllDocCompte4.put("2", "Buenas");
			mpllDocCompte4.put("3", "Escasas");
			mpllDocCompte4.put("4", "Deficientes");
			
			// Componente 5
			mpllDocCompte5 = new HashMap<String, String>();
			mpllDocCompte5.put("1", "Excelentes");
			mpllDocCompte5.put("2", "Buenas");
			mpllDocCompte5.put("3", "Escasas");
			mpllDocCompte5.put("4", "Deficientes");
			
			// Componente 6
			mpllDocCompte6 = new HashMap<String, String>();
			mpllDocCompte6.put("1", "Excelente");
			mpllDocCompte6.put("2", "Bueno");
			mpllDocCompte6.put("3", "Escaso");
			mpllDocCompte6.put("4", "Deficiente");
			
			// Componente 7
			mpllDocCompte7 = new HashMap<String, String>();
			mpllDocCompte7.put("1", "Excelente");
			mpllDocCompte7.put("2", "Buena");
			mpllDocCompte7.put("3", "Escasa");
			mpllDocCompte7.put("4", "Deficiente");
			
			// Componente 8
			mpllDocCompte8 = new HashMap<String, String>();
			mpllDocCompte8.put("1", "Excelente");
			mpllDocCompte8.put("2", "Buena");
			mpllDocCompte8.put("3", "Escasa");
			mpllDocCompte8.put("4", "Deficiente");
			
			// Componente 9
			mpllDocCompte9 = new HashMap<String, String>();
			mpllDocCompte9.put("1", "Excelente");
			mpllDocCompte9.put("2", "Buena");
			mpllDocCompte9.put("3", "Escasa");
			mpllDocCompte9.put("4", "Deficiente");
			
			// Componente 10
			mpllDocCompte10 = new HashMap<String, String>();
			mpllDocCompte10.put("1", "Excelente");
			mpllDocCompte10.put("2", "Buena");
			mpllDocCompte10.put("3", "Escasa");
			mpllDocCompte10.put("4", "Deficiente");
			
			// Componente 11
			mpllDocCompte11 = new HashMap<String, String>();
			mpllDocCompte11.put("1", "Muy alto");
			mpllDocCompte11.put("2", "Alto");
			mpllDocCompte11.put("3", "Bajo");
			mpllDocCompte11.put("4", "Muy bajo");
			
			// Componente 12
			mpllDocCompte12 = new HashMap<String, String>();
			mpllDocCompte12.put("1", "Muy alto");
			mpllDocCompte12.put("2", "Alto");
			mpllDocCompte12.put("3", "Bajo");
			mpllDocCompte12.put("4", "Muy bajo");
			
			// Componente 13
			mpllDocCompte13 = new HashMap<String, String>();
			mpllDocCompte13.put("1", "Muy alto");
			mpllDocCompte13.put("2", "Alto");
			mpllDocCompte13.put("3", "Bajo");
			mpllDocCompte13.put("4", "Muy bajo");
			
			// Componente 14
			mpllDocCompte14 = new HashMap<String, String>();
			mpllDocCompte14.put("1", "Muy de acuerdo");
			mpllDocCompte14.put("2", "De acuerdo");
			mpllDocCompte14.put("3", "En desacuerdo");
			mpllDocCompte14.put("4", "Muy en desacuerdo");
			
			// Componente 15
			mpllDocCompte15 = new HashMap<String, String>();
			mpllDocCompte15.put("1", "Muy de acuerdo");
			mpllDocCompte15.put("2", "De acuerdo");
			mpllDocCompte15.put("3", "En desacuerdo");
			mpllDocCompte15.put("4", "Muy en desacuerdo");
		}



	public Map<String, String> getMpllCompte1() {
		return mpllCompte1;
	}



	public void setMpllCompte1(Map<String, String> mpllCompte1) {
		this.mpllCompte1 = mpllCompte1;
	}



	public Map<String, String> getMpllCompte2() {
		return mpllCompte2;
	}



	public void setMpllCompte2(Map<String, String> mpllCompte2) {
		this.mpllCompte2 = mpllCompte2;
	}



	public Map<String, String> getMpllCompte2a() {
		return mpllCompte2a;
	}



	public void setMpllCompte2a(Map<String, String> mpllCompte2a) {
		this.mpllCompte2a = mpllCompte2a;
	}



	public Map<String, String> getMpdsCompte3() {
		return mpdsCompte3;
	}



	public void setMpdsCompte3(Map<String, String> mpdsCompte3) {
		this.mpdsCompte3 = mpdsCompte3;
	}



	public Map<String, String> getMpllCompte4() {
		return mpllCompte4;
	}



	public void setMpllCompte4(Map<String, String> mpllCompte4) {
		this.mpllCompte4 = mpllCompte4;
	}



	public Map<String, String> getMpllCompte5() {
		return mpllCompte5;
	}



	public void setMpllCompte5(Map<String, String> mpllCompte5) {
		this.mpllCompte5 = mpllCompte5;
	}



	public Map<String, String> getMpllCompte6() {
		return mpllCompte6;
	}



	public void setMpllCompte6(Map<String, String> mpllCompte6) {
		this.mpllCompte6 = mpllCompte6;
	}



	public Map<String, String> getMpllCompte7() {
		return mpllCompte7;
	}



	public void setMpllCompte7(Map<String, String> mpllCompte7) {
		this.mpllCompte7 = mpllCompte7;
	}



	public Map<String, String> getMpllCompte8() {
		return mpllCompte8;
	}



	public void setMpllCompte8(Map<String, String> mpllCompte8) {
		this.mpllCompte8 = mpllCompte8;
	}



	public Map<String, String> getMpllCompte9() {
		return mpllCompte9;
	}



	public void setMpllCompte9(Map<String, String> mpllCompte9) {
		this.mpllCompte9 = mpllCompte9;
	}



	public Map<String, String> getMpllCompte10() {
		return mpllCompte10;
	}



	public void setMpllCompte10(Map<String, String> mpllCompte10) {
		this.mpllCompte10 = mpllCompte10;
	}



	public Map<String, String> getMpllCompte11a() {
		return mpllCompte11a;
	}



	public void setMpllCompte11a(Map<String, String> mpllCompte11a) {
		this.mpllCompte11a = mpllCompte11a;
	}



	public Map<String, String> getMpllCompte11b() {
		return mpllCompte11b;
	}



	public void setMpllCompte11b(Map<String, String> mpllCompte11b) {
		this.mpllCompte11b = mpllCompte11b;
	}



	public Map<String, String> getMpllCompte11c() {
		return mpllCompte11c;
	}



	public void setMpllCompte11c(Map<String, String> mpllCompte11c) {
		this.mpllCompte11c = mpllCompte11c;
	}



	public Map<String, String> getMpllCompte11d() {
		return mpllCompte11d;
	}



	public void setMpllCompte11d(Map<String, String> mpllCompte11d) {
		this.mpllCompte11d = mpllCompte11d;
	}



	public Map<String, String> getMpllCompte11e() {
		return mpllCompte11e;
	}



	public void setMpllCompte11e(Map<String, String> mpllCompte11e) {
		this.mpllCompte11e = mpllCompte11e;
	}



	public Map<String, String> getMpllCompte11f() {
		return mpllCompte11f;
	}



	public void setMpllCompte11f(Map<String, String> mpllCompte11f) {
		this.mpllCompte11f = mpllCompte11f;
	}



	public Map<String, String> getMpllCompte11g() {
		return mpllCompte11g;
	}



	public void setMpllCompte11g(Map<String, String> mpllCompte11g) {
		this.mpllCompte11g = mpllCompte11g;
	}



	public Map<String, String> getMpllCompte11h() {
		return mpllCompte11h;
	}



	public void setMpllCompte11h(Map<String, String> mpllCompte11h) {
		this.mpllCompte11h = mpllCompte11h;
	}



	public Map<String, String> getMpllCompte11i() {
		return mpllCompte11i;
	}



	public void setMpllCompte11i(Map<String, String> mpllCompte11i) {
		this.mpllCompte11i = mpllCompte11i;
	}



	public Map<String, String> getMpllCompte11j() {
		return mpllCompte11j;
	}



	public void setMpllCompte11j(Map<String, String> mpllCompte11j) {
		this.mpllCompte11j = mpllCompte11j;
	}



	public Map<String, String> getMpllCompte11k() {
		return mpllCompte11k;
	}



	public void setMpllCompte11k(Map<String, String> mpllCompte11k) {
		this.mpllCompte11k = mpllCompte11k;
	}



	public Map<String, String> getMpllCompte11l() {
		return mpllCompte11l;
	}



	public void setMpllCompte11l(Map<String, String> mpllCompte11l) {
		this.mpllCompte11l = mpllCompte11l;
	}



	public Map<String, String> getMpllCompte11m() {
		return mpllCompte11m;
	}



	public void setMpllCompte11m(Map<String, String> mpllCompte11m) {
		this.mpllCompte11m = mpllCompte11m;
	}



	public Map<String, String> getMpllCompte12a() {
		return mpllCompte12a;
	}



	public void setMpllCompte12a(Map<String, String> mpllCompte12a) {
		this.mpllCompte12a = mpllCompte12a;
	}



	public Map<String, String> getMpllCompte12b() {
		return mpllCompte12b;
	}



	public void setMpllCompte12b(Map<String, String> mpllCompte12b) {
		this.mpllCompte12b = mpllCompte12b;
	}



	public Map<String, String> getMpllCompte12c() {
		return mpllCompte12c;
	}



	public void setMpllCompte12c(Map<String, String> mpllCompte12c) {
		this.mpllCompte12c = mpllCompte12c;
	}



	public Map<String, String> getMpllCompte12d() {
		return mpllCompte12d;
	}



	public void setMpllCompte12d(Map<String, String> mpllCompte12d) {
		this.mpllCompte12d = mpllCompte12d;
	}



	public Map<String, String> getMpllCompte12e() {
		return mpllCompte12e;
	}



	public void setMpllCompte12e(Map<String, String> mpllCompte12e) {
		this.mpllCompte12e = mpllCompte12e;
	}



	public Map<String, String> getMpllTipoEncuesta() {
		return mpllTipoEncuesta;
	}



	public void setMpllTipoEncuesta(Map<String, String> mpllTipoEncuesta) {
		this.mpllTipoEncuesta = mpllTipoEncuesta;
	}



	public Map<String, String> getMpllNivel() {
		return mpllNivel;
	}



	public void setMpllNivel(Map<String, String> mpllNivel) {
		this.mpllNivel = mpllNivel;
	}



	public Map<String, String> getMpllCampus() {
		return mpllCampus;
	}



	public void setMpllCampus(Map<String, String> mpllCampus) {
		this.mpllCampus = mpllCampus;
	}



	public Map<String, String> getMpllEntidad() {
		return mpllEntidad;
	}



	public void setMpllEntidad(Map<String, String> mpllEntidad) {
		this.mpllEntidad = mpllEntidad;
	}



	public Map<String, String> getMpllArea() {
		return mpllArea;
	}



	public void setMpllArea(Map<String, String> mpllArea) {
		this.mpllArea = mpllArea;
	}


	public Map<String, String> getMpllAntiguedad() {
		return mpllAntiguedad;
	}


	public void setMpllAntiguedad(Map<String, String> mpllAntiguedad) {
		this.mpllAntiguedad = mpllAntiguedad;
	}


	public Map<String, String> getMpllSexo() {
		return mpllSexo;
	}


	public void setMpllSexo(Map<String, String> mpllSexo) {
		this.mpllSexo = mpllSexo;
	}


	public Map<String, String> getMpllCarrerasImparte() {
		return mpllCarrerasImparte;
	}


	public void setMpllCarrerasImparte(Map<String, String> mpllCarrerasImparte) {
		this.mpllCarrerasImparte = mpllCarrerasImparte;
	}


	public Map<String, String> getMpllDocCompte1() {
		return mpllDocCompte1;
	}


	public void setMpllDocCompte1(Map<String, String> mpllDocCompte1) {
		this.mpllDocCompte1 = mpllDocCompte1;
	}


	public Map<String, String> getMpllDocCompte2() {
		return mpllDocCompte2;
	}


	public void setMpllDocCompte2(Map<String, String> mpllDocCompte2) {
		this.mpllDocCompte2 = mpllDocCompte2;
	}


	public Map<String, String> getMpllDocCompte3() {
		return mpllDocCompte3;
	}


	public void setMpllDocCompte3(Map<String, String> mpllDocCompte3) {
		this.mpllDocCompte3 = mpllDocCompte3;
	}


	public Map<String, String> getMpllDocCompte4() {
		return mpllDocCompte4;
	}


	public void setMpllDocCompte4(Map<String, String> mpllDocCompte4) {
		this.mpllDocCompte4 = mpllDocCompte4;
	}


	public Map<String, String> getMpllDocCompte5() {
		return mpllDocCompte5;
	}


	public void setMpllDocCompte5(Map<String, String> mpllDocCompte5) {
		this.mpllDocCompte5 = mpllDocCompte5;
	}


	public Map<String, String> getMpllDocCompte6() {
		return mpllDocCompte6;
	}


	public void setMpllDocCompte6(Map<String, String> mpllDocCompte6) {
		this.mpllDocCompte6 = mpllDocCompte6;
	}


	public Map<String, String> getMpllDocCompte7() {
		return mpllDocCompte7;
	}


	public void setMpllDocCompte7(Map<String, String> mpllDocCompte7) {
		this.mpllDocCompte7 = mpllDocCompte7;
	}


	public Map<String, String> getMpllDocCompte8() {
		return mpllDocCompte8;
	}


	public void setMpllDocCompte8(Map<String, String> mpllDocCompte8) {
		this.mpllDocCompte8 = mpllDocCompte8;
	}


	public Map<String, String> getMpllDocCompte9() {
		return mpllDocCompte9;
	}


	public void setMpllDocCompte9(Map<String, String> mpllDocCompte9) {
		this.mpllDocCompte9 = mpllDocCompte9;
	}


	public Map<String, String> getMpllDocCompte10() {
		return mpllDocCompte10;
	}


	public void setMpllDocCompte10(Map<String, String> mpllDocCompte10) {
		this.mpllDocCompte10 = mpllDocCompte10;
	}


	public Map<String, String> getMpllDocCompte11() {
		return mpllDocCompte11;
	}


	public void setMpllDocCompte11(Map<String, String> mpllDocCompte11) {
		this.mpllDocCompte11 = mpllDocCompte11;
	}


	public Map<String, String> getMpllDocCompte12() {
		return mpllDocCompte12;
	}


	public void setMpllDocCompte12(Map<String, String> mpllDocCompte12) {
		this.mpllDocCompte12 = mpllDocCompte12;
	}


	public Map<String, String> getMpllDocCompte13() {
		return mpllDocCompte13;
	}


	public void setMpllDocCompte13(Map<String, String> mpllDocCompte13) {
		this.mpllDocCompte13 = mpllDocCompte13;
	}


	public Map<String, String> getMpllDocCompte14() {
		return mpllDocCompte14;
	}


	public void setMpllDocCompte14(Map<String, String> mpllDocCompte14) {
		this.mpllDocCompte14 = mpllDocCompte14;
	}


	public Map<String, String> getMpllDocCompte15() {
		return mpllDocCompte15;
	}


	public void setMpllDocCompte15(Map<String, String> mpllDocCompte15) {
		this.mpllDocCompte15 = mpllDocCompte15;
	}
	
	
}

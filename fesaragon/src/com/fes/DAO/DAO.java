package com.fes.DAO;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.fes.bean.DtoEncuestaRespuestasAlumnosVer1;
import com.fes.bean.DtoEncuestaRespuestasMaestrosVer1;
import com.fes.bean.dtoUsuario;
import com.fes.constantes.Constantes;

public class DAO extends Constantes{
	
	//private static String servidor="jdbc:mysql://132.248.44.189:3306/encuesta40fcol";
	private static String servidor="jdbc:mysql://127.0.0.1:3306/encuesta40fcol";
	private static String user="root";
//	private static String pass="Qw3rtY";
	private static String pass="1234";
	private static String driver="com.mysql.jdbc.Driver";
	private static Connection conexion;
	
	
	/*
	 * Metodo para obtener la conexion Directa
	 */
	public Connection obtenerConexion(){
		Connection conexion = null;
        
        try {
            // Cargar el driver
            Class.forName(driver);

            // Se obtiene una conexión con la base de datos.
            conexion = DriverManager.getConnection(servidor, user, pass);
            System.out.println("Conexion realizada exitosamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return conexion;
	}
	
	public int cerrarConexion(Connection conexion){
        
		int exitoso=0;
		int fallido=-1;
		int res = exitoso;
		
		try {
        	if(conexion!=null){
        		conexion.close();
        	}
        } catch (SQLException e) {
        	res = fallido; 
            System.out.println(e.getMessage());
        }
		
        return res;
	}
	
	
	/*
	 * Metodo para obtener la conexion
	 */
	/*
	public Connection obtenerConexion(){
		Context initContext = null;
		Context envContext = null;
		DataSource ds = null;
		Connection conn = null;
		
		try {
			initContext = new InitialContext();
	        envContext = (Context) initContext.lookup("java:comp/env");
	        ds = (DataSource) envContext.lookup("jdbc/UsersDB");
	        conn = ds.getConnection();
        } catch (NamingException ex) {
            System.err.println("Error(NamingException) al Conectar con la Base de Datos: " + ex.getMessage());
        } catch (SQLException ex) {
            System.err.println("Error(SQLException) al Conectar con la Base de Datos: " + ex.getMessage());
        } catch (Exception ex){
        	System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        }
		
		return conn;
	} */
	
	/*
	 * Metodo para recuperar informacion de base de datos para cargar Combos
	 */
	public Map<String,String> cargaCombo(String sql, String key, String value){
		// Conexion
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		// Mapa
		Map<String, String> mpCatalogoNivel = new HashMap<String, String>();
		
		try {
	        conn = obtenerConexion();
	        statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            
            while (rs.next()) {
            	mpCatalogoNivel.put(rs.getString(value).toString(), rs.getString(key).toString());
			}
            
        } catch (SQLException ex) {
            System.err.println("Error(SQLException) al Conectar con la Base de Datos: " + ex.getMessage());
        } catch (Exception ex){
        	System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        } finally{
        	try{
        		if(rs!=null)
        			rs.close();
        		if(statement!=null)
        			statement.close();
        		if(conn!=null)
        			conn.close();
        	} catch(SQLException ex){
        		System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        	}
        }
		
		return mpCatalogoNivel;
    }

	/*
	 * Metodo para actualizar la base de datos o insertar nuevos registros
	 */
	public int actualizaBD(String sql){
		Context initContext = null;
		Context envContext = null;
		DataSource ds = null;
		Connection conn = null;
		Statement statement = null;
		int res = 0;
		
		try {
			conn = obtenerConexion();
	        statement = conn.createStatement();
            res = statement.executeUpdate(sql);
            
            if(res<=0){
            	System.err.println("Error al impactar la sentencia en Base de Datos (NO SE ACTUALIZARON NI INSERTARON REGISTROS): res=" + res);
            }
            
        } catch (SQLException ex) {
            System.err.println("Error(SQLException) al Conectar con la Base de Datos: " + ex.getMessage());
        } catch (Exception ex){
        	System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        } finally{
        	try{
        		if(statement!=null)
        			statement.close();
        		if(conn!=null)
        			conn.close();
        	} catch(SQLException ex){
        		System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        	}
        }
		return res;
    }
	
	
	//////////////////////////////////////////////////// Metodos Personalizados (ConsultarEncuestasDAO) ////////////////////////////////////////////////////
	/*
	 * Metodo para recuperar la informacion del Usuario de base de datos
	 */
	public List<dtoUsuario> recuperarUsuario(String sql){
		// Conexion
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		// Lista
		List<dtoUsuario> lstUsuario = new ArrayList<dtoUsuario>();
		
		try {
	        conn = obtenerConexion();
	        statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            
            while (rs.next()) {
            	dtoUsuario objUsuario = new dtoUsuario();
            	objUsuario.setLlUsuarioID(Integer.parseInt(rs.getString("llUsuarioID")));
            	objUsuario.setDsUsuario(rs.getString("dsUsuario").toString());
            	objUsuario.setDsPassword(rs.getString("dsPassword").toString());
            	objUsuario.setLlNivelID(Integer.parseInt(rs.getString("llNivelID")));
            	objUsuario.setLlCampusID(Integer.parseInt(rs.getString("llCampusID")));
            	
            	lstUsuario.add(objUsuario);
			}
            
        } catch (SQLException ex) {
            System.err.println("Error(SQLException) al Conectar con la Base de Datos: " + ex.getMessage());
        } catch (Exception ex){
        	System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        } finally{
        	try{
        		if(rs!=null)
        			rs.close();
        		if(statement!=null)
        			statement.close();
        		if(conn!=null)
        			conn.close();
        	} catch(SQLException ex){
        		System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        	}
        }
		
		return lstUsuario;
    }
	
	
	/*
	 * Metodo para recuperar la informacion del Usuario de base de datos
	 */
	public List<DtoEncuestaRespuestasAlumnosVer1> recuperarEncuestasDAO(String sql){
		// Conexion
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		// Lista
		List<DtoEncuestaRespuestasAlumnosVer1> lstEncuestaRespuestasAlumnosVer1 = new ArrayList<DtoEncuestaRespuestasAlumnosVer1>();
		
		try {
	        conn = obtenerConexion();
	        statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            
            while (rs.next()) {
            	// Instanciar Lista
            	DtoEncuestaRespuestasAlumnosVer1 objEncuestaRespuestasAlumnosVer1 = new DtoEncuestaRespuestasAlumnosVer1();
            	
            	objEncuestaRespuestasAlumnosVer1.setLlEncuestaID(rs.getString("llEncuestaID")!=null ? Integer.parseInt(rs.getString("llEncuestaID")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlTipoEncuestaID(rs.getString("llTipoEncuestaID")!=null ? Integer.parseInt(rs.getString("llTipoEncuestaID")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlVerEncID(rs.getString("llVerEncID")!=null ? Integer.parseInt(rs.getString("llVerEncID")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlnivel(rs.getString("llnivel")!=null ? Integer.parseInt(rs.getString("llnivel")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCampus(rs.getString("llCampus")!=null ? Integer.parseInt(rs.getString("llCampus")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlEntidad(rs.getString("llEntidad")!=null ? Integer.parseInt(rs.getString("llEntidad")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlcuenta(rs.getString("llcuenta")!=null ? Integer.parseInt(rs.getString("llcuenta")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setDsSecunProc(rs.getString("dsSecunProc"));
            	objEncuestaRespuestasAlumnosVer1.setDsBachilleProc(rs.getString("dsBachilleProc"));
            	objEncuestaRespuestasAlumnosVer1.setDsBachilleProcOtra(rs.getString("dsBachilleProcOtra"));
            	objEncuestaRespuestasAlumnosVer1.setDsEdad(rs.getString("dsEdad"));
            	objEncuestaRespuestasAlumnosVer1.setDsSexo(rs.getString("dsSexo"));
            	objEncuestaRespuestasAlumnosVer1.setDsCarrera(rs.getString("dsCarrera"));
            	objEncuestaRespuestasAlumnosVer1.setDsCicloEscolar(rs.getString("dsCicloEscolar"));
            	objEncuestaRespuestasAlumnosVer1.setLlCompte1(rs.getString("llCompte1")!=null ? Integer.parseInt(rs.getString("llCompte1")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte2(rs.getString("llCompte2")!=null ? Integer.parseInt(rs.getString("llCompte2")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte2a(rs.getString("llCompte2a")!=null ? Integer.parseInt(rs.getString("llCompte2a")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setDsCompte3(rs.getString("dsCompte3"));
            	objEncuestaRespuestasAlumnosVer1.setDsCompte3otro(rs.getString("dsCompte3otro"));
            	objEncuestaRespuestasAlumnosVer1.setLlCompte4(rs.getString("llCompte4")!=null ? Integer.parseInt(rs.getString("llCompte4")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte5(rs.getString("llCompte5")!=null ? Integer.parseInt(rs.getString("llCompte5")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte6(rs.getString("llCompte6")!=null ? Integer.parseInt(rs.getString("llCompte6")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte7(rs.getString("llCompte7")!=null ? Integer.parseInt(rs.getString("llCompte7")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte8(rs.getString("llCompte8")!=null ? Integer.parseInt(rs.getString("llCompte8")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte9(rs.getString("llCompte9")!=null ? Integer.parseInt(rs.getString("llCompte9")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte10(rs.getString("llCompte10")!=null ? Integer.parseInt(rs.getString("llCompte10")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte11a(rs.getString("llCompte11a")!=null ? Integer.parseInt(rs.getString("llCompte11a")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte11b(rs.getString("llCompte11b")!=null ? Integer.parseInt(rs.getString("llCompte11b")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte11c(rs.getString("llCompte11c")!=null ? Integer.parseInt(rs.getString("llCompte11c")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte11d(rs.getString("llCompte11d")!=null ? Integer.parseInt(rs.getString("llCompte11d")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte11e(rs.getString("llCompte11e")!=null ? Integer.parseInt(rs.getString("llCompte11e")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte11f(rs.getString("llCompte11f")!=null ? Integer.parseInt(rs.getString("llCompte11f")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte11g(rs.getString("llCompte11g")!=null ? Integer.parseInt(rs.getString("llCompte11g")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte11h(rs.getString("llCompte11h")!=null ? Integer.parseInt(rs.getString("llCompte11h")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte11i(rs.getString("llCompte11i")!=null ? Integer.parseInt(rs.getString("llCompte11i")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte11j(rs.getString("llCompte11j")!=null ? Integer.parseInt(rs.getString("llCompte11j")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte11k(rs.getString("llCompte11k")!=null ? Integer.parseInt(rs.getString("llCompte11k")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte11l(rs.getString("llCompte11l")!=null ? Integer.parseInt(rs.getString("llCompte11l")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte11m(rs.getString("llCompte11m")!=null ? Integer.parseInt(rs.getString("llCompte11m")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setDsCompte11n(rs.getString("dsCompte11n"));
            	objEncuestaRespuestasAlumnosVer1.setLlCompte12a(rs.getString("llCompte12a")!=null ? Integer.parseInt(rs.getString("llCompte12a")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte12b(rs.getString("llCompte12b")!=null ? Integer.parseInt(rs.getString("llCompte12b")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte12c(rs.getString("llCompte12c")!=null ? Integer.parseInt(rs.getString("llCompte12c")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte12d(rs.getString("llCompte12d")!=null ? Integer.parseInt(rs.getString("llCompte12d")) : 0);
            	objEncuestaRespuestasAlumnosVer1.setLlCompte12e(rs.getString("llCompte12e")!=null ? Integer.parseInt(rs.getString("llCompte12e")) : 0);
            	
            	// Agregar Registro a la Lista
            	lstEncuestaRespuestasAlumnosVer1.add(objEncuestaRespuestasAlumnosVer1);
			}
            
        } catch (SQLException ex) {
            System.err.println("Error(SQLException) al Conectar con la Base de Datos: " + ex.getMessage());
        } catch (Exception ex){
        	System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        } finally{
        	try{
        		if(rs!=null)
        			rs.close();
        		if(statement!=null)
        			statement.close();
        		if(conn!=null)
        			conn.close();
        	} catch(SQLException ex){
        		System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        	}
        }
		
		return lstEncuestaRespuestasAlumnosVer1;
    }
	
	/*
	 * Metodo para recuperar la informacion del Usuario de base de datos
	 */
	public List<DtoEncuestaRespuestasMaestrosVer1> recuperarEncuestasMaestrosDAO(String sql){
		// Conexion
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		// Lista
		List<DtoEncuestaRespuestasMaestrosVer1> lstEncuestaRespuestasMaestrosVer1 = new ArrayList<DtoEncuestaRespuestasMaestrosVer1>();
		
		try {
	        conn = obtenerConexion();
	        statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            
            while (rs.next()) {
            	// Instanciar Lista
            	DtoEncuestaRespuestasMaestrosVer1 objEncuestaRespuestasMaestrosVer1 = new DtoEncuestaRespuestasMaestrosVer1();

            	objEncuestaRespuestasMaestrosVer1.setLlEncuestaID(rs.getString("llEncuestaID")!=null ? Integer.parseInt(rs.getString("llEncuestaID")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setLlTipoEncuestaID(rs.getString("llTipoEncuestaID")!=null ? Integer.parseInt(rs.getString("llTipoEncuestaID")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setLlVerEncID(rs.getString("llVerEncID")!=null ? Integer.parseInt(rs.getString("llVerEncID")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setLlnivel(rs.getString("llnivel")!=null ? Integer.parseInt(rs.getString("llnivel")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setLlCampus(rs.getString("llCampus")!=null ? Integer.parseInt(rs.getString("llCampus")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setLlcuenta(rs.getString("llcuenta")!=null ? Integer.parseInt(rs.getString("llcuenta")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setLlAntiguedad(rs.getString("llAntiguedad")!=null ? Integer.parseInt(rs.getString("llAntiguedad")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setLlsexo(rs.getString("llsexo")!=null ? Integer.parseInt(rs.getString("llsexo")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCarreraFormacion(rs.getString("dsCarreraFormacion"));
            	objEncuestaRespuestasMaestrosVer1.setDsCarrerasImparteAsigProfeCasteo(rs.getString("dsCarrerasImparteAsigProfeCasteo"));
            	objEncuestaRespuestasMaestrosVer1.setDsLicenciatura(rs.getString("dsLicenciatura"));
            	objEncuestaRespuestasMaestrosVer1.setDtfechaTitulacionLic(rs.getString("dtfechaTitulacionLic"));
            	objEncuestaRespuestasMaestrosVer1.setDsEspecialidad(rs.getString("dsEspecialidad"));
            	objEncuestaRespuestasMaestrosVer1.setDtFechaTitulacionEsp(rs.getString("dtFechaTitulacionEsp"));
            	objEncuestaRespuestasMaestrosVer1.setDsMaestria(rs.getString("dsMaestria"));
            	objEncuestaRespuestasMaestrosVer1.setDtFechaTitulacionMaestria(rs.getString("dtFechaTitulacionMaestria"));
            	objEncuestaRespuestasMaestrosVer1.setDsDoctorado(rs.getString("dsDoctorado"));
            	objEncuestaRespuestasMaestrosVer1.setDtFechaTitulacionDoc(rs.getString("dtFechaTitulacionDoc"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte1(rs.getString("llCompte1")!=null ? Integer.parseInt(rs.getString("llCompte1")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte1Coment(rs.getString("dsCompte1Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte2(rs.getString("llCompte2")!=null ? Integer.parseInt(rs.getString("llCompte2")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte2Coment(rs.getString("dsCompte2Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte3(rs.getString("llCompte3")!=null ? Integer.parseInt(rs.getString("llCompte3")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte3Coment(rs.getString("dsCompte3Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte4(rs.getString("llCompte4")!=null ? Integer.parseInt(rs.getString("llCompte4")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte4Coment(rs.getString("dsCompte4Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte5(rs.getString("llCompte5")!=null ? Integer.parseInt(rs.getString("llCompte5")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte5Coment(rs.getString("dsCompte5Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte6(rs.getString("llCompte6")!=null ? Integer.parseInt(rs.getString("llCompte6")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte6Coment(rs.getString("dsCompte6Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte7(rs.getString("llCompte7")!=null ? Integer.parseInt(rs.getString("llCompte7")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte7Coment(rs.getString("dsCompte7Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte8(rs.getString("llCompte8")!=null ? Integer.parseInt(rs.getString("llCompte8")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte8Coment(rs.getString("dsCompte8Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte9(rs.getString("llCompte9")!=null ? Integer.parseInt(rs.getString("llCompte9")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte9Coment(rs.getString("dsCompte9Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte10(rs.getString("llCompte10")!=null ? Integer.parseInt(rs.getString("llCompte10")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte10Coment(rs.getString("dsCompte10Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte11(rs.getString("llCompte11")!=null ? Integer.parseInt(rs.getString("llCompte11")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte11Coment(rs.getString("dsCompte11Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte12(rs.getString("llCompte12")!=null ? Integer.parseInt(rs.getString("llCompte12")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte12Coment(rs.getString("dsCompte12Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte13(rs.getString("llCompte13")!=null ? Integer.parseInt(rs.getString("llCompte13")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte13Coment(rs.getString("dsCompte13Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte14(rs.getString("llCompte14")!=null ? Integer.parseInt(rs.getString("llCompte14")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte14Coment(rs.getString("dsCompte14Coment"));
            	objEncuestaRespuestasMaestrosVer1.setLlCompte15(rs.getString("llCompte15")!=null ? Integer.parseInt(rs.getString("llCompte15")) : 0);
            	objEncuestaRespuestasMaestrosVer1.setDsCompte15Coment(rs.getString("dsCompte15Coment"));
            	objEncuestaRespuestasMaestrosVer1.setFechaRegistro(rs.getString("fechaRegistro"));

            	// Agregar Registro a la Lista
            	lstEncuestaRespuestasMaestrosVer1.add(objEncuestaRespuestasMaestrosVer1);
			}
            
        } catch (SQLException ex) {
            System.err.println("Error(SQLException) al Conectar con la Base de Datos: " + ex.getMessage());
        } catch (Exception ex){
        	System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        } finally{
        	try{
        		if(rs!=null)
        			rs.close();
        		if(statement!=null)
        			statement.close();
        		if(conn!=null)
        			conn.close();
        	} catch(SQLException ex){
        		System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        	}
        }
		
		return lstEncuestaRespuestasMaestrosVer1;
    }
	
	/*
	 * Metodo para validar Numero de Cuenta
	 */
	public String validaCuenta(String sql){
		// Conexion
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		// Recuperar Cuenta
		String cuentaUsuario = "";
		
		try {
	        conn = obtenerConexion();
	        statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            
            while (rs.next()) {
            	cuentaUsuario = rs.getString("llcuenta");
			}
            
        } catch (SQLException ex) {
            System.err.println("Error(SQLException) al Conectar con la Base de Datos: " + ex.getMessage());
        } catch (Exception ex){
        	System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        } finally{
        	try{
        		if(rs!=null)
        			rs.close();
        		if(statement!=null)
        			statement.close();
        		if(conn!=null)
        			conn.close();
        	} catch(SQLException ex){
        		System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        	}
        }
		
		return cuentaUsuario;
    }
}

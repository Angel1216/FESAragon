package com.fes.DAO;

import javax.faces.bean.ManagedBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name="testConnection")
public class TestConnection {

	/**
	 * @param args
	 */
	public void consulta(){
		System.err.println("Ejemplo de PrimeFaces con actionListener");
		
		Context initContext = null;
		Context envContext = null;
		DataSource ds = null;
		Connection conn = null;
		Statement statement = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			initContext = new InitialContext();
	        envContext = (Context) initContext.lookup("java:comp/env");
	        ds = (DataSource) envContext.lookup("jdbc/UsersDB");
	        conn = ds.getConnection();
	        statement = conn.createStatement();
	        
            sql = "SELECT nombre, sinapsis, ruta FROM juegos;";
            rs = statement.executeQuery(sql);
            
            while (rs.next()) {
            	System.out.println("nombre: " + rs.getString("nombre").toString());
            	System.out.println("sinapsis: " + rs.getString("sinapsis").toString()); 
            }
            
        } catch (NamingException ex) {
            System.err.println("Error(NamingException) al Conectar con la Base de Datos: " + ex.getMessage());
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
        	} catch(Exception ex){
        		System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        	}
        }
		
		//return rs;
    }
	
	
	public void actualizaBD(){
		System.err.println("Ejemplo de PrimeFaces con actionListener");
		
		Context initContext = null;
		Context envContext = null;
		DataSource ds = null;
		Connection conn = null;
		Statement statement = null;
		String sql = null;
		
		try {
			initContext = new InitialContext();
	        envContext = (Context) initContext.lookup("java:comp/env");
	        ds = (DataSource) envContext.lookup("jdbc/UsersDB");
	        conn = ds.getConnection();
	        statement = conn.createStatement();
	        
            sql = "INSERT INTO JUEGOS VALUES(99,'AUTOMATICO','Prueba','C:',100.50)";
            int res = statement.executeUpdate(sql);
            
            if(res<=0){
            	System.err.println("Error al impactar la sentencia en Base de Datos (NO SE ACTUALIZARON NI INSERTARON REGISTROS): res=" + res);
            }
            
        } catch (NamingException ex) {
            System.err.println("Error(NamingException) al Conectar con la Base de Datos: " + ex.getMessage());
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
        	} catch(Exception ex){
        		System.err.println("Error(Exception) al Conectar con la Base de Datos: " + ex.getMessage());
        	}
        }
    }

	public void validarSession(){
		
		// Validar que este Logeado
		FacesContext validarLogin = FacesContext.getCurrentInstance();
		HttpSession validarSession = (HttpSession) validarLogin.getExternalContext().getSession(false);
		
		try {
			if(validarSession==null)
				validarLogin.getExternalContext().redirect(System.getProperty("file.separator")+"fesaragon"+System.getProperty("file.separator")+"xhtml"+System.getProperty("file.separator")+"login"+System.getProperty("file.separator")+"login.xhtml");
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute("antFisBan", "1");
		System.out.println("ejemplo de Session");
		System.out.println("Atributo de Session: " + session.getAttribute("antFisBan"));
		System.out.println("ejemplo de Session");
		
		
		FacesContext facesContextValidar = FacesContext.getCurrentInstance();
		HttpSession sessionValidar = (HttpSession) facesContextValidar.getExternalContext().getSession(false);
		
		System.out.println("ejemplo de Session Validar");
		System.out.println("Atributo de Session Validar: " + sessionValidar.getAttribute("antFisBan"));
		System.out.println("ejemplo de Session Validar");
	}
}


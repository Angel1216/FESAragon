package com.web.test;

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
import javax.faces.bean.ManagedBean;

@ManagedBean(name="testP")
public class Test {

	/**
	 * @param args
	 */
	public void testConnection() {
		// TODO Auto-generated method stub

		try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/UsersDB");
            Connection conn = ds.getConnection();
             
            Statement statement = conn.createStatement();
            String sql = "SELECT nombre, sinapsis, ruta FROM juegos";
            ResultSet rs = statement.executeQuery(sql);
            
		} catch (NamingException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
	}
}
	

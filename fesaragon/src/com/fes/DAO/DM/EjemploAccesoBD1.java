package com.fes.DAO.DM;

import java.sql.*;
public class EjemploAccesoBD1 {
	
	private static String servidor="jdbc:mysql://127.0.0.1:3306/encuesta40fcol";
	private static String user="root";
	private static String pass="1234";
	private static String driver="com.mysql.jdbc.Driver";
	private static Connection conexion;

    public void insertar(){
        Connection conexion = null;
        Statement s = null;
        
        try {
            // Cargar el driver
            Class.forName(driver);

            // Se obtiene una conexión con la base de datos.
            // En este caso nos conectamos a la base de datos prueba
            // con el usuario root y contraseña 1daw
            conexion = DriverManager.getConnection(servidor, user, pass);

            // Se crea un Statement, para realizar la consulta
            s = conexion.createStatement();

            // Se realiza la consulta. Los resultados se guardan en el ResultSet rs
            int res = s.executeUpdate("INSERT INTO catalogoNivel(llnivelID,dsNivel) VALUES (3,'EJEMPLO')");

            System.out.println("res(insert): " + res);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally { // Se cierra la conexión con la base de datos.
            try {
            	if(s != null){
            		s.close();
            	}
            	
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    
    public void consulta(){
        Connection conexion = null;
        Statement s = null;
        ResultSet rs = null;
        
        try {
            // Cargar el driver
            Class.forName(driver);

            // Se obtiene una conexión con la base de datos.
            // En este caso nos conectamos a la base de datos prueba
            // con el usuario root y contraseña 1daw
            conexion = DriverManager.getConnection(servidor, user, pass);

            // Se crea un Statement, para realizar la consulta
            s = conexion.createStatement();

            // Se realiza la consulta. Los resultados se guardan en el ResultSet rs
            rs = s.executeQuery("SELECT llEntidad, dsEntidad FROM catalogoEntidad");

            // Se recorre el ResultSet, mostrando por pantalla los resultados.
            while (rs.next()) {
                System.out.println(rs.getString("llEntidad") + " / " + rs.getString("dsEntidad"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally { // Se cierra la conexión con la base de datos.
            try {
            	if(rs != null){
            		rs.close();
            	}
            	
            	if(s != null){
            		s.close();
            	}
            	
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
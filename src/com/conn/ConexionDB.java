/**
 * 
 */
package com.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionDB {

	static Connection con = null;
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL_CONEXION = "jdbc:mysql://localhost/reservaSuministros";
	private static final String USUARIO = "daniel";
	private static final String PASSWORD = "daniel";

	public static Connection getConnection() {

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL_CONEXION, USUARIO, PASSWORD);
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
		}

		return con;

	}

	public void cerrarConexion() {
		try {
			con.close();
		} catch (SQLException ex) {
			Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}

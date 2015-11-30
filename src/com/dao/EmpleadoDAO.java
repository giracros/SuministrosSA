package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import com.conn.ConexionDB;
import com.dto.EmpleadoDTO;


/**
 * @author danielfuentes
 *
 */ 
public class EmpleadoDAO {

	/*
	 * Procedimientos Almacenados
	 */
	private static String insertarSP = "call SP_InsertarEmpleado(?,?,?,?,?,?,?)";
	private static String modificarSP = "call SP_ModificarEmpleado(?,?,?,?,?,?)";
	private static String eliminarSP = "call SP_EliminarEmpleado(?)";
	private static String consultarSP = "call SP_ConsultarEmpleado(?)";
	private static String listarSP = "call SP_ListadoEmpleado()";

	/*
	 * Mensajes
	 */
	private static String errorSqlInsertar = "No Inserto:  ";
	private static String errorSqlModificar = "No Modifico:  ";
	private static String errorSqlEliminar = "No Elimino:  ";
	private static String errorSqlConsultar = "No Consulto:  ";
	private static String errorSqlListar =  "No Listo:  ";

	/**
	 * @param cedula
	 * @category Insertar
	 */
	public static void insertar(EmpleadoDTO cedula) {
		Connection con = ConexionDB.getConnection();
		try {
			PreparedStatement registroEmpleado = con.prepareStatement(insertarSP);
			registroEmpleado.setString(1, cedula.getCedulaEmpleado());
			registroEmpleado.setString(2, cedula.getNombre());
			registroEmpleado.setString(3, cedula.getEmail());
			registroEmpleado.setString(4, cedula.getCelular());
			registroEmpleado.setString(5, "Activo");
			registroEmpleado.executeUpdate();
			System.out.println("Inserto");
		} catch (SQLException e) {
			System.out.println(errorSqlInsertar + e);
		}
	}

	/**
	 * @param cedula
	 * @category Modificar
	 */
	public void modificar(EmpleadoDTO cedula) {
		Connection cnn = ConexionDB.getConnection();
		try {
			PreparedStatement modificarEmpleado = cnn.prepareStatement(modificarSP);
			modificarEmpleado.setString (1, cedula.getCedulaEmpleado());
			modificarEmpleado.setString(2, cedula.getNombre());
			modificarEmpleado.setString(3, cedula.getEmail());
			modificarEmpleado.setString(4, cedula.getCelular());
			modificarEmpleado.executeUpdate();
			System.out.println("Modifico");
		} catch (SQLException e) {
			System.out.println(errorSqlModificar + e);
		}
	}

	/**
	 * @param cedula
	 * @category Eliminar
	 */
	public static void eliminar(String cedula) {
		Connection cnn = ConexionDB.getConnection();
		try {
			PreparedStatement eliminarEmpleado = cnn.prepareStatement(eliminarSP);
			eliminarEmpleado.setString(1, cedula);
			eliminarEmpleado.executeUpdate();
			System.out.println("Elimino");
		} catch (SQLException e) {
			System.out.println(errorSqlEliminar + e);
		}
	}

	/**
	 * @param cedula
	 * @category Consultar
	 */
	public EmpleadoDTO consultar(String cedula) {

		Connection cnn = ConexionDB.getConnection();
		EmpleadoDTO empleado = new EmpleadoDTO();

		try {
			PreparedStatement consultarEmpleado = cnn.prepareStatement(consultarSP);
			consultarEmpleado.setString(1, cedula);
			ResultSet rs = consultarEmpleado.executeQuery();
			System.out.println("Consulto");
			if (rs.next()) {

				empleado.setCedulaEmpleado(rs.getString("cedula"));
				empleado.setNombre(rs.getString("nombre"));
				empleado.setEmail(rs.getString("email"));
				empleado.setCelular(rs.getString("celular"));

			}
		} catch (SQLException e) {
			System.out.print(errorSqlConsultar + e);
		}
		return empleado;
	}

	/*
	 * Metodo Listar
	 * 
	 */
	public static ArrayList<EmpleadoDTO> listado() {

		ArrayList<EmpleadoDTO> empleados = new ArrayList<EmpleadoDTO>();
		Connection cnn = ConexionDB.getConnection();
		EmpleadoDTO empleado;

		try {
			PreparedStatement listarEmpleados = cnn.prepareStatement(listarSP);
			ResultSet rs = listarEmpleados.executeQuery();
			System.out.println("Listo");
			while (rs.next()) {
				empleado = new EmpleadoDTO();
				empleado.setCedulaEmpleado(rs.getString("cedula"));
				empleado.setNombre(rs.getString("nombre"));
				empleado.setEmail(rs.getString("email"));
				empleado.setCelular(rs.getString("celular"));
				empleados.add(empleado);
			}
		} catch (SQLException e) {
			System.out.print(errorSqlListar + e);
		}
		return empleados;

	}

}

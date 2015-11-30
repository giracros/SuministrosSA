package com.fachada;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.EmpleadoDAO;
import com.dto.EmpleadoDTO;

/**
 * Servlet implementation class FachadaEmpleado
 */
@WebServlet("/FachadaEmpleado")
public class FachadaEmpleado extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	private EmpleadoDTO empleado;
	private EmpleadoDAO empleadoDAO;
	private static final String LISTARGENERAL = "/ListarEmpleados.jsp";
	private static final String MODIFICAR = "/ModificarEmpleado.jsp";

	public FachadaEmpleado() {
		super();
		empleado = new EmpleadoDTO();
		empleadoDAO = new EmpleadoDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String opc = request.getParameter("opc");

		String accion = null;

		if (opc.equalsIgnoreCase("listado")) {

			try {
				accion = LISTARGENERAL;
				request.setAttribute("empleados", empleadoDAO.listado());

			} catch (Exception e) {
				System.out.print("Error en listar!!!" + e);
			}
		}

		else if (opc.equalsIgnoreCase("eliminar")) {

			String codigo = request.getParameter("codigo");

			empleadoDAO.eliminar(codigo);

			accion = LISTARGENERAL;

			request.setAttribute("empleados", empleadoDAO.listado());

		} else if (opc.equalsIgnoreCase("consultar")) {

			String codigo = request.getParameter("codigo");
			request.setAttribute("empleados", empleadoDAO.consultar(codigo));

			accion = MODIFICAR;
		} else
			accion = "modificar";

		try {
			RequestDispatcher view = request.getRequestDispatcher(accion);
			view.forward(request, response);
		} catch (Exception e) {
			System.out.print("Genero un error al listar: " + e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String opc = request.getParameter("opc");

		empleado.setCedulaEmpleado(request.getParameter("codigo"));
		empleado.setNombre(request.getParameter("nombre"));
		empleado.setEmail(request.getParameter("email"));
		empleado.setCelular(request.getParameter("celular"));
		
		if (opc.equals("insertar"))
			try {
				empleadoDAO.insertar(empleado);
			} catch (Exception e) {
				System.out.print("Error en insertar!!!" + e);
			}
		else if (opc.equals("eliminar")) {
			try {
				empleadoDAO.eliminar("codigo");
			} catch (Exception e) {
				System.out.print("Error al eliminar!!!" + e);
			}
		} else if (opc.equals("modificar")) {
			empleadoDAO.modificar(empleado);
		}
		RequestDispatcher view = request.getRequestDispatcher(LISTARGENERAL);
		request.setAttribute("empleados", EmpleadoDAO.listado());
		view.forward(request, response);
	}

}

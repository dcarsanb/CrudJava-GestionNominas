package com.mycompany.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycompany.dao.EmpleadoDAO;
import com.mycompany.model.DatosNoCorrectosException;
import com.mycompany.model.Empleado;

/**
 * Controlador de empleados. Según el parámetro "opcion" consulta la base de datos
 * y reenvía a la página JSP correspondiente.
 * GET: listar, meditar. POST: salario, buscar, editar.
 */
@WebServlet(description = "Administra las peticiones sobre empleados y nóminas", urlPatterns = { "/empleados" })
public class EmpleadoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmpleadoController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opcion = request.getParameter("opcion");
        RequestDispatcher requestDispatcher;

        if (opcion == null) {
            request.setAttribute("mensaje", "No se ha indicado ninguna opción.");
            requestDispatcher = request.getRequestDispatcher("/views/error.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        EmpleadoDAO empleadoDAO = new EmpleadoDAO();

        try {
            if (opcion.equals("listar")) {
                request.setAttribute("lista", empleadoDAO.obtenerEmpleados());
                requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
                requestDispatcher.forward(request, response);

            } else if (opcion.equals("meditar")) {
                String dni = request.getParameter("dni");
                Empleado e = empleadoDAO.obtenerEmpleado(dni);
                if (e == null) {
                    request.setAttribute("mensaje", "El empleado no existe.");
                    requestDispatcher = request.getRequestDispatcher("/views/error.jsp");
                } else {
                    request.setAttribute("empleado", e);
                    requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
                }
                requestDispatcher.forward(request, response);

            } else {
                request.setAttribute("mensaje", "Opción no válida: " + opcion);
                requestDispatcher = request.getRequestDispatcher("/views/error.jsp");
                requestDispatcher.forward(request, response);
            }

        } catch (SQLException e) {
            request.setAttribute("mensaje", "Error de base de datos: " + e.getMessage());
            requestDispatcher = request.getRequestDispatcher("/views/error.jsp");
            requestDispatcher.forward(request, response);
        } catch (DatosNoCorrectosException e) {
            request.setAttribute("mensaje", e.getMessage());
            requestDispatcher = request.getRequestDispatcher("/views/error.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String opcion = request.getParameter("opcion");
        RequestDispatcher requestDispatcher;

        if (opcion == null) {
            request.setAttribute("mensaje", "No se ha indicado ninguna opción.");
            requestDispatcher = request.getRequestDispatcher("/views/error.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        EmpleadoDAO empleadoDAO = new EmpleadoDAO();

        try {
            if (opcion.equals("salario")) {
                String dni = request.getParameter("dni").trim().toUpperCase();
                double sueldo = empleadoDAO.obtenerSalario(dni);

                if (sueldo == -1) {
                    request.setAttribute("mensaje", "No existe ninguna nómina para el DNI " + dni + ".");
                    requestDispatcher = request.getRequestDispatcher("/views/error.jsp");
                } else {
                    request.setAttribute("dni", dni);
                    request.setAttribute("sueldo", sueldo);
                    requestDispatcher = request.getRequestDispatcher("/views/mostrarSalario.jsp");
                }
                requestDispatcher.forward(request, response);

            } else if (opcion.equals("buscar")) {
                String nombre = request.getParameter("nombre").trim();
                String dni = request.getParameter("dni").trim().toUpperCase();
                String sexo = request.getParameter("sexo");
                String categoria = request.getParameter("categoria").trim();
                String anyos = request.getParameter("anyos").trim();

                request.setAttribute("lista", empleadoDAO.buscar(nombre, dni, sexo, categoria, anyos));
                requestDispatcher = request.getRequestDispatcher("/views/resultados.jsp");
                requestDispatcher.forward(request, response);

            } else if (opcion.equals("editar")) {
                String nombre = request.getParameter("nombre").trim();
                String dni = request.getParameter("dni");
                String sexo = request.getParameter("sexo");
                int categoria = Integer.parseInt(request.getParameter("categoria"));
                int anyos = Integer.parseInt(request.getParameter("anyos"));

                Empleado e = new Empleado(nombre, dni, sexo, categoria, anyos);
                double sueldo = empleadoDAO.editar(e);

                request.setAttribute("mensaje", "Empleado " + nombre
                        + " actualizado. Nuevo sueldo calculado: " + sueldo + " €.");
                requestDispatcher = request.getRequestDispatcher("/views/mensaje.jsp");
                requestDispatcher.forward(request, response);

            } else {
                request.setAttribute("mensaje", "Opción no válida: " + opcion);
                requestDispatcher = request.getRequestDispatcher("/views/error.jsp");
                requestDispatcher.forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("mensaje", "Categoría y años deben ser números enteros.");
            requestDispatcher = request.getRequestDispatcher("/views/error.jsp");
            requestDispatcher.forward(request, response);
        } catch (DatosNoCorrectosException e) {
            request.setAttribute("mensaje", e.getMessage());
            requestDispatcher = request.getRequestDispatcher("/views/error.jsp");
            requestDispatcher.forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("mensaje", "Error de base de datos: " + e.getMessage());
            requestDispatcher = request.getRequestDispatcher("/views/error.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
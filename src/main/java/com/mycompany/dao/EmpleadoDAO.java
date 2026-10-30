package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.conexion.Conexion;
import com.mycompany.model.DatosNoCorrectosException;
import com.mycompany.model.Empleado;
import com.mycompany.model.Nomina;

/** Acceso a las tablas empleados y nominas. */
public class EmpleadoDAO {

    /**
     * Obtiene todos los empleados ordenados por nombre.
     * @return lista de empleados
     * @throws SQLException si falla la consulta
     * @throws DatosNoCorrectosException si algún dato de la BD no es válido
     */
    public List<Empleado> obtenerEmpleados() throws SQLException, DatosNoCorrectosException {
        List<Empleado> lista = new ArrayList<>();

        Connection connection = Conexion.getConnection();
        String sql = "SELECT * FROM empleados ORDER BY NOMBRE";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Empleado e = new Empleado(resultSet.getString("NOMBRE"), resultSet.getString("DNI"),
                    resultSet.getString("SEXO"), resultSet.getInt("CATEGORIA"), resultSet.getInt("ANYOS"));
            lista.add(e);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return lista;
    }

    /**
     * Obtiene un empleado por su DNI.
     * @param dni DNI a buscar
     * @return el empleado, o null si no existe
     * @throws SQLException si falla la consulta
     * @throws DatosNoCorrectosException si algún dato de la BD no es válido
     */
    public Empleado obtenerEmpleado(String dni) throws SQLException, DatosNoCorrectosException {
        Empleado e = null;

        Connection connection = Conexion.getConnection();
        String sql = "SELECT * FROM empleados WHERE DNI = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dni);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            e = new Empleado(resultSet.getString("NOMBRE"), resultSet.getString("DNI"),
                    resultSet.getString("SEXO"), resultSet.getInt("CATEGORIA"), resultSet.getInt("ANYOS"));
        }

        resultSet.close();
        statement.close();
        connection.close();
        return e;
    }

    /**
     * Obtiene el sueldo guardado en la tabla nominas.
     * @param dni DNI del empleado
     * @return el sueldo, o -1 si no hay nómina para ese DNI
     * @throws SQLException si falla la consulta
     */
    public double obtenerSalario(String dni) throws SQLException {
        double sueldo = -1;

        Connection connection = Conexion.getConnection();
        String sql = "SELECT SUELDO FROM nominas WHERE DNI = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dni);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            sueldo = resultSet.getDouble("SUELDO");
        }

        resultSet.close();
        statement.close();
        connection.close();
        return sueldo;
    }

    /**
     * Busca empleados por cualquier campo. Si un campo viene vacío se usa "%",
     * que en SQL significa "cualquier valor", así que ese campo no filtra.
     * Nombre y DNI buscan por coincidencia parcial; sexo, categoría y años, por valor exacto.
     * @param nombre nombre o parte del nombre (puede ir vacío)
     * @param dni DNI o parte del DNI (puede ir vacío)
     * @param sexo "M" o "F" (puede ir vacío)
     * @param categoria categoría (puede ir vacío)
     * @param anyos años trabajados (puede ir vacío)
     * @return empleados encontrados, ordenados por nombre
     * @throws SQLException si falla la consulta
     * @throws DatosNoCorrectosException si algún dato de la BD no es válido
     */
    public List<Empleado> buscar(String nombre, String dni, String sexo, String categoria, String anyos)
            throws SQLException, DatosNoCorrectosException {
        List<Empleado> lista = new ArrayList<>();

        // Preparamos cada valor: si está vacío, "%" (cualquiera)
        if (nombre.equals("")) {
            nombre = "%";
        } else {
            nombre = "%" + nombre + "%";
        }
        if (dni.equals("")) {
            dni = "%";
        } else {
            dni = "%" + dni + "%";
        }
        if (sexo.equals("")) {
            sexo = "%";
        }
        if (categoria.equals("")) {
            categoria = "%";
        }
        if (anyos.equals("")) {
            anyos = "%";
        }

        Connection connection = Conexion.getConnection();
        String sql = "SELECT * FROM empleados WHERE NOMBRE LIKE ? AND DNI LIKE ? AND SEXO LIKE ? "
                + "AND CATEGORIA LIKE ? AND ANYOS LIKE ? ORDER BY NOMBRE";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nombre);
        statement.setString(2, dni);
        statement.setString(3, sexo);
        statement.setString(4, categoria);
        statement.setString(5, anyos);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Empleado e = new Empleado(resultSet.getString("NOMBRE"), resultSet.getString("DNI"),
                    resultSet.getString("SEXO"), resultSet.getInt("CATEGORIA"), resultSet.getInt("ANYOS"));
            lista.add(e);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return lista;
    }

    /**
     * Modifica un empleado y recalcula su sueldo en la tabla nominas.
     * El sueldo no lo escribe el usuario: se calcula con {@link Nomina}.
     * @param e empleado con los datos nuevos (el DNI identifica el registro)
     * @return el nuevo sueldo
     * @throws SQLException si falla la operación (se hace rollback)
     */
    public double editar(Empleado e) throws SQLException {
        Nomina nomina = new Nomina();
        double sueldo = nomina.sueldo(e);

        Connection connection = Conexion.getConnection();
        try {
            connection.setAutoCommit(false);

            String sql1 = "UPDATE empleados SET NOMBRE=?, SEXO=?, CATEGORIA=?, ANYOS=? WHERE DNI=?";
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setString(1, e.getNombre());
            statement1.setString(2, e.getSexo());
            statement1.setInt(3, e.getCategoria());
            statement1.setInt(4, e.getAnyos());
            statement1.setString(5, e.getDni());
            statement1.executeUpdate();

            String sql2 = "UPDATE nominas SET SUELDO=? WHERE DNI=?";
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setDouble(1, sueldo);
            statement2.setString(2, e.getDni());
            statement2.executeUpdate();

            connection.commit();
            statement1.close();
            statement2.close();
            connection.close();
        } catch (SQLException ex) {
            connection.rollback();
            connection.close();
            throw ex;
        }
        return sueldo;
    }
}
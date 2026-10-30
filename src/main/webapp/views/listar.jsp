<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Listado de empleados</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<div class="contenedor">
  <h1>Listado de empleados</h1>
  <c:choose>
    <c:when test="${empty lista}"><p class="aviso">No hay empleados registrados.</p></c:when>
    <c:otherwise>
      <table>
        <tr><th>Nombre</th><th>DNI</th><th>Sexo</th><th>Categoría</th><th>Años trabajados</th></tr>
        <c:forEach var="e" items="${lista}">
          <tr>
            <td><c:out value="${e.nombre}"/></td>
            <td><c:out value="${e.dni}"/></td>
            <td><c:out value="${e.sexo}"/></td>
            <td><c:out value="${e.categoria}"/></td>
            <td><c:out value="${e.anyos}"/></td>
          </tr>
        </c:forEach>
      </table>
    </c:otherwise>
  </c:choose>
  <button class="btn" type="button" onclick="history.back()">Volver</button>
</div>
</body>
</html>
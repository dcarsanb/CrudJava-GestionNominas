<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Salario</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<div class="contenedor">
  <h1>Salario del empleado</h1>
  <table>
    <tr><th>DNI</th><th>Sueldo</th></tr>
    <tr><td><c:out value="${dni}"/></td><td><c:out value="${sueldo}"/> €</td></tr>
  </table>
  <button class="btn" type="button" onclick="history.back()">Volver</button>
</div>
</body>
</html>
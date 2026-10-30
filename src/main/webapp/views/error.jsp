<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Error</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<div class="contenedor">
  <h1>Ha ocurrido un error</h1>
  <p class="error">
    <c:choose>
      <c:when test="${empty mensaje}">Se ha producido un error inesperado o la página no existe.</c:when>
      <c:otherwise><c:out value="${mensaje}"/></c:otherwise>
    </c:choose>
  </p>
  <button class="btn" type="button" onclick="history.back()">Volver</button>
</div>
</body>
</html>
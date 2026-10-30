<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Información</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<div class="contenedor">
  <h1>Operación realizada</h1>
  <p class="ok"><c:out value="${mensaje}"/></p>
  <a class="btn" href="${pageContext.request.contextPath}/index.html">Ir al menú</a>
  <button class="btn secundario" type="button" onclick="history.back()">Volver</button>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Consultar salario</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<div class="contenedor">
  <h1>Consultar salario</h1>
  <form action="${pageContext.request.contextPath}/empleados" method="post">
    <input type="hidden" name="opcion" value="salario">
    <label>DNI del empleado: <input type="text" name="dni" maxlength="9" required></label>
    <button class="btn" type="submit">Consultar</button>
  </form>
  <button class="btn secundario" type="button" onclick="history.back()">Volver</button>
</div>
</body>
</html>
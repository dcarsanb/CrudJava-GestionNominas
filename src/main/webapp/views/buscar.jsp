<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Buscar empleados</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<div class="contenedor">
  <h1>Buscar empleados</h1>
  <p>Rellena uno o varios campos; los vacíos se ignoran.</p>
  <form action="${pageContext.request.contextPath}/empleados" method="post">
    <input type="hidden" name="opcion" value="buscar"><br />
    <label>Nombre: <input type="text" name="nombre"></label><br />
    <label>DNI: <input type="text" name="dni"></label><br />
    <label>Sexo: <input type="text" name="sexo" maxlength="1"></label><br />
    <label>Categoría: <input type="text" name="categoria"></label><br />
    <label>Años trabajados: <input type="text" name="anyos"></label>
    <button class="btn" type="submit">Buscar</button>
  </form>
  <button class="btn secundario" type="button" onclick="history.back()">Volver</button>
</div>
</body>
</html>
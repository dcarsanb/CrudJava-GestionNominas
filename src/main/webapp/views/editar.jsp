<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Modificar empleado</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<div class="contenedor">
  <h1>Modificar empleado</h1>
  <form action="${pageContext.request.contextPath}/empleados" method="post">
    <input type="hidden" name="opcion" value="editar">
    <label>DNI: <input type="text" name="dni" value="<c:out value='${empleado.dni}'/>" readonly></label>
    <label>Nombre: <input type="text" name="nombre" value="<c:out value='${empleado.nombre}'/>" required></label>
    <label>Sexo:
      <select name="sexo">
        <option value="M" ${empleado.sexo == 'M' ? 'selected' : ''}>M</option>
        <option value="F" ${empleado.sexo == 'F' ? 'selected' : ''}>F</option>
      </select>
    </label>
    <label>Categoría: <input type="number" name="categoria" min="1" max="10" value="${empleado.categoria}" required></label>
    <label>Años trabajados: <input type="number" name="anyos" min="0" value="${empleado.anyos}" required></label>
    <p class="aviso">El sueldo no se puede modificar: se recalcula automáticamente al guardar.</p>
    <button class="btn" type="submit">Guardar</button>
  </form>
  <button class="btn secundario" type="button" onclick="history.back()">Volver</button>
</div>
</body>
</html>
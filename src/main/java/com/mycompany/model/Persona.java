package com.mycompany.model;

public class Persona {

    public String nombre, dni, sexo;

    public Persona(String nombre, String dni, String sexo) {
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
    }

    public Persona(String nombre, String sexo) {
        this.nombre = nombre;
        this.sexo = sexo;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void imprime(){
        System.out.println("Nombre: "+nombre+" Sexo: "+sexo);
    }
    public String getNombre(){
        return nombre;
    }
    public String getDni(){
        return dni;
    }
    public String getSexo(){
        return sexo;
    }
}

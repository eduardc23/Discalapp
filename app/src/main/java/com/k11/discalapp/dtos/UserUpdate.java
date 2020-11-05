package com.k11.discalapp.dtos;

import com.squareup.moshi.Json;

public class UserUpdate {
    @Json(name = "id")
    public Integer id;
    @Json(name = "nombreCompleto")
    public String nombreCompleto;
    @Json(name = "nombreUsuario")
    public String nombreUsuario;
    @Json(name = "edad")
    public Integer edad;
    @Json(name = "contrase\u00f1a")
    public String contraseA;
    @Json(name = "contrase\u00f1aConfirmada")
    public String contraseAConfirmada;

    public UserUpdate(Integer id, String nombreCompleto, String nombreUsuario, Integer edad, String contraseA, String contraseAConfirmada) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.nombreUsuario = nombreUsuario;
        this.edad = edad;
        this.contraseA = contraseA;
        this.contraseAConfirmada = contraseAConfirmada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getContraseA() {
        return contraseA;
    }

    public void setContraseA(String contraseA) {
        this.contraseA = contraseA;
    }

    public String getContraseAConfirmada() {
        return contraseAConfirmada;
    }

    public void setContraseAConfirmada(String contraseAConfirmada) {
        this.contraseAConfirmada = contraseAConfirmada;
    }
}

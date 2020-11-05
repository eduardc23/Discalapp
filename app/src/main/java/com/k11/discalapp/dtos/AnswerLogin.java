package com.k11.discalapp.dtos;

import com.squareup.moshi.Json;

public class AnswerLogin {
    @Json(name = "nombreCompleto")
    private String nombreCompleto;
    @Json(name = "edad")
    private Integer edad;
    @Json(name = "id")
    private Integer id;
    @Json(name = "nombreUsuario")
    private String nombreUsuario;
    @Json(name = "contrase\u00f1a")
    private String contraseA;

    public AnswerLogin(String nombreCompleto, Integer edad, Integer id, String nombreUsuario, String contraseA) {
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contraseA = contraseA;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseA() {
        return contraseA;
    }

    public void setContraseA(String contraseA) {
        this.contraseA = contraseA;
    }
}

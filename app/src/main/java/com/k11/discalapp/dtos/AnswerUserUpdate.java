package com.k11.discalapp.dtos;

import com.squareup.moshi.Json;

public class AnswerUserUpdate {
    @Json(name = "id")
    public Integer id;
    @Json(name = "nombreUsuario")
    public String nombreUsuario;
    @Json(name = "nombreCompleto")
    public String nombreCompleto;
    @Json(name = "edad")
    public Integer edad;

    public AnswerUserUpdate(Integer id, String nombreUsuario, String nombreCompleto, Integer edad) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
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
}

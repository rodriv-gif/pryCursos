package com.example.prycursos.Modelo

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class CorreoResponse(

    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("correo") val correo: String = ""
)
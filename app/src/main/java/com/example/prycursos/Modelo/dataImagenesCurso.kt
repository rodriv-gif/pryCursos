package com.example.prycursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataImagenesCurso(
    @SerialName("id") val id: Int = 0,
    @SerialName("id_curso") val idcurso: Int = 0,
    @SerialName("url_imagen") val urlImagen: String = ""
)

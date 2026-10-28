package com.example.prycursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataImagenesCurso(
    @SerialName("id") val id: Int = 0,
    @SerialName("curso_id") val cursoId: Int = 0,
    @SerialName("url_imagen") val url_imagen: String = ""
)

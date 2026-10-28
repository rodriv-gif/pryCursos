package com.example.prycursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataInsertarRegistro(
    @SerialName("id") val id: Int = 0,
    @SerialName("mensaje") val mensaje: String = ""
)
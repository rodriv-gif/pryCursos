package com.example.prycursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuscarRequest(
    @SerialName("matricula") val matricula: String = ""
)
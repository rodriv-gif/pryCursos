package com.example.prycursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCarreras(
    @SerialName("id") val id:Int = 0,
    @SerialName("carrera") val categoria: String=""
)
{
    override fun toString(): String {
        return categoria
    }
}

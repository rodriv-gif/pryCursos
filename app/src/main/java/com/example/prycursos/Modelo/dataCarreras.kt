package com.example.prycursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCarreras(
    @SerialName("carrera_id") val id:Int = 0,
    @SerialName("carrera") val carrera: String=""
)
{
    override fun toString(): String {
        return carrera
    }
}

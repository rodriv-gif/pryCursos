package com.example.prycursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCursos(
    @SerialName(value= "curso_id")val id:Int=0,
    @SerialName(value = "carrera_id") val carreraid:Int=0,
    @SerialName(value = "nombre") val nombre:String="",
    @SerialName(value = "salon") val salon: String="",
    @SerialName(value = "url_imagen") val urlimagenprincipal :String="",
)

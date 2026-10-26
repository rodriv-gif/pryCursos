package com.example.prycursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCursos(
    @SerialName(value= "curso_id")val id:Int=0,
    @SerialName(value = "carrera_id") val carreraid:Int=0,
    @SerialName(value = "nombre") val nombre:String="",
    @SerialName(value = "codigo") val codigo: String="",
    @SerialName(value = "descripcion") val descripcion: String="sin descripcion",
    @SerialName(value = "edificio") val edificio: String="",
    @SerialName(value = "salon") val salon: String="",
    @SerialName(value = "total_horas") val totalHoras: String="",
    @SerialName(value = "dias_atencion") val diasAtencion: String="",
    @SerialName(value = "horario") val horario: String="",
    @SerialName(value = "cupos") val cupos: Int = 0,
    @SerialName(value = "url_imagen") val urlimagenprincipal :String="",
)

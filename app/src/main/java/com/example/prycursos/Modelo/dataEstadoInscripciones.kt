package com.example.prycursos.Modelo

import kotlinx.serialization.Serializable

@Serializable
data class dataEstadoInscripciones(
    val curso_id: Int,
    val nombre: String,
    val cupos: Int,
    val inscritos: Int,
    val url_imagen: String? = null
)

@Serializable
data class RespuestaEstadoInscripciones(
    val total_cursos: Int,
    val total_inscritos: Int,
    val cursos: List<dataEstadoInscripciones>
)
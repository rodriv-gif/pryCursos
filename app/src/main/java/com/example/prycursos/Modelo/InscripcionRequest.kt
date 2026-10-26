package com.example.prycursos.Modelo

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
//data class es una clase que nos ayuda a guardar y transportar datos
data class InscripcionRequest(
    //SerialName asegura que el php recibe la variable
    @SerialName("matricula") val matricula: String="",
    @SerialName("correo") val correo: String="",
    @SerialName("cursoId") val cursoId: Int=0
)

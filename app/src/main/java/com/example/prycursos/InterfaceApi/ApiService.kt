package com.example.prycursos.InterfaceApi

import com.example.prycursos.Modelo.InscripcionRequest
import com.example.prycursos.Modelo.InscripcionResponse
import com.example.prycursos.Modelo.dataCarreras
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {
    @GET("obtener_carreras.php")
    suspend fun obtenerCarreras(): List<dataCarreras>

    @POST("registrar_inscripcion.php")
    suspend fun registrarInscripcion(@Body request: InscripcionRequest): InscripcionResponse

}
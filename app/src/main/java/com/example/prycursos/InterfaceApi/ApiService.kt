package com.example.prycursos.InterfaceApi

import com.example.prycursos.Modelo.dataCarreras
import retrofit2.http.GET

interface ApiService {
    @GET("obtener_carreras.php")
    suspend fun obtenerCarreras(): List<dataCarreras>
}
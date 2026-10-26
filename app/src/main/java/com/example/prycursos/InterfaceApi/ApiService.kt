package com.example.prycursos.InterfaceApi

import com.example.prycursos.Modelo.dataCarreras
import com.example.prycursos.Modelo.dataCursos
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("obtener_carreras.php")
    suspend fun obtenerCarreras(): List<dataCarreras>

    //Aqui agregamos los edpoint para el resicler y para la vista detalle
    @GET("obtener_cursos.php")
    suspend fun obtenerCursos(@Query("carrera_id") carreraId: Int): List<dataCursos>
}
package com.example.prycursos.InterfaceApi

import com.example.prycursos.Modelo.CorreoResponse
import com.example.prycursos.Modelo.InscripcionRequest
import com.example.prycursos.Modelo.InscripcionResponse
import com.example.prycursos.Modelo.RespuestaEstadoInscripciones
import com.example.prycursos.Modelo.dataCarreras
import com.example.prycursos.Modelo.dataCursos
import com.example.prycursos.Modelo.dataImagenesCurso
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {
    @GET("obtener_carreras.php")
    suspend fun obtenerCarreras(): List<dataCarreras>

    //Aqui agregamos los edpoint para el resicler y para la vista detalle
    @GET("obtener_cursos.php")
    suspend fun obtenerCursos(@Query("carrera_id") carreraId: Int): List<dataCursos>
    @GET("detalle_curso.php")
    suspend fun obtenerImagenesCurso(@Query("curso_id") cursoId: Int): List<dataImagenesCurso>
    @POST("registrar_inscripcion.php")
    suspend fun registrarInscripcion(@Body request: InscripcionRequest): InscripcionResponse
    @GET("consulta_registros.php")
    suspend fun obtenerEstadoInscripciones(@Query("carrera_id") carreraId: Int): RespuestaEstadoInscripciones
    @GET("buscar_alumno.php")
    suspend fun obtenerCorreo(@Query("matricula") matricula: String): CorreoResponse

}
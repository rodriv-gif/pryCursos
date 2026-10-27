package com.example.prycursos.Modelo

class clsEstadoInscripcionModelo {

    suspend fun consultarRegistros(carreraId: Int): RespuestaEstadoInscripciones {
        return RetrofitCliente.apiService.obtenerEstadoInscripciones(carreraId)
    }

    suspend fun obtenerCarreras(): List<dataCarreras> {
        return RetrofitCliente.apiService.obtenerCarreras()
    }
}
package com.example.prycursos.Modelo

class clsCursosModelo {
    suspend fun obtenerCursosPorCarrera(carreraId: Int): List<dataCursos> {
        return RetrofitCliente.apiService.obtenerCursos(carreraId)
    }
    suspend fun obtenerDetalleCurso(idCurso: Int): List<dataImagenesCurso> {
        return RetrofitCliente.apiService.obtenerImagenesCurso(idCurso)
    }
}
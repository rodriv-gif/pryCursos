package com.example.prycursos.Modelo

class clsCursosModelo {
    suspend fun obtenerCursosPorCarrera(carreraId: Int): List<dataCursos> {
        return RetrofitCliente.apiService.obtenerCursos(carreraId)
    }

    suspend fun obtenerDetalleCurso(cursoId: Int): List<dataImagenesCurso> {
        return RetrofitCliente.apiService.obtenerDetalleCurso(cursoId)
    }
}

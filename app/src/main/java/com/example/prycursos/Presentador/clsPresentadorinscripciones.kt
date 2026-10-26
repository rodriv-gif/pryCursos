package com.example.prycursos.Presentador

import com.example.prycursos.Modelo.CursoInscripcion
import com.example.prycursos.Modelo.clsModeloInscripciones

class clsPresentadorinscripciones {
    // Crear objeto del Modelo
    val modelo = clsModeloInscripciones()

    // Consultar los registros desde la API
    fun consultarRegistros(
        carreraId: Int,
        respuesta: (Int, List<CursoInscripcion>) -> Unit,
        error: (String) -> Unit
    ) {
        modelo.consultarRegistros(carreraId, respuesta, error)
    }
    // Obtener los lugares disponibles
    fun obtenerDisponibles(inscritos: Int): Int {
        return modelo.calcularDisponibles(inscritos)
    }
    // Obtener el porcentaje de inscritos
    fun obtenerPorcentaje(inscritos: Int): Int {
        return modelo.calcularPorcentaje(inscritos)
    }
    // Verificar si el curso está lleno
    fun verificarCursoLleno(inscritos: Int): Boolean {
        return modelo.cursoLleno(inscritos)
    }
}
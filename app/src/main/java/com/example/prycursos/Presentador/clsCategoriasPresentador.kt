package com.example.prycursos.Presentador

import com.example.prycursos.Modelo.clsCarrerasModelo
import com.example.prycursos.Modelo.clsCursosModelo
import com.example.prycursos.Modelo.dataCarreras
import com.example.prycursos.Modelo.dataCursos
import com.example.prycursos.Vista.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsCategoriasPresentador(val vista: MainActivity) {
    private val modeloCarreras = clsCarrerasModelo()
    private val modeloCursos = clsCursosModelo()
    private var listaCarreras: List<dataCarreras> = emptyList()

    fun iniciar(){
        // Se usa un Scope simple para no bloquear la interfaz
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // 1. Cargar la lista de categorías en el Spinner
                listaCarreras = modeloCarreras.obtenerCarreras()
                vista.mostrarCarreras(listaCarreras)

                // 2. Cargar TODOS los cursos por defecto
                cargarCursos(0)
            } catch (e: Exception) {
                vista.mostrarError("Error: " + e.localizedMessage)
            }
        }
    }
    fun seleccionarCarreras(posicion: Int) {
        if (posicion >= 0 && posicion < listaCarreras.size) {
            val carreras = listaCarreras[posicion]
            // Si curs.id es 0, la API traerá todos; si es > 0, filtrará por esa carreras
            cargarCursos(carreras.id)
        }
    }

    private fun cargarCursos(carreraId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val cursos = modeloCursos.obtenerCursosPorCarrera(carreraId)
                vista.mostrarCursos(cursos)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar productos: " + e.localizedMessage)
            }
        }
    }

    //fun seleccionarCurso(curso: dataCursos) {
        //val precioDouble = producto.precio.toDoubleOrNull() ?: 0.0
       // vista.abrirDetalleCurso(Curso.id, curso.nombre, curso.salon,)
   // }
}
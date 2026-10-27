package com.example.prycursos.Presentador

import com.example.prycursos.Modelo.clsEstadoInscripcionModelo
import com.example.prycursos.Vista.EstadoInscripcionesActivity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class clsEstadoInscripcionesPresentador(private val vista: EstadoInscripcionesActivity) {

    private val modelo = clsEstadoInscripcionModelo()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var consulta: Job? = null
    fun cargarCarreras() {
        scope.launch {
            try {
                val listaCarreras = modelo.obtenerCarreras()
                val nombres = listaCarreras.map { it.carrera }
                val ids = listaCarreras.map { it.id }
                vista.mostrarCarreras(nombres, ids)
            } catch (e: Exception) {
                android.util.Log.e("ESTADO_INSCRIPCIONES", "Error al cargar carreras", e)
                vista.mostrarError("Error al cargar carreras: ${e.localizedMessage}")
            }
        }
    }

    fun consultarRegistros(carreraId: Int) {
        consulta?.cancel()

        consulta = scope.launch {
            try {
                val respuesta = modelo.consultarRegistros(carreraId)

                vista.mostrarCursos(respuesta.cursos, respuesta.total_inscritos)

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                android.util.Log.e("ESTADO_INSCRIPCIONES", "Error al consultar registros", e)
                vista.mostrarError(e.localizedMessage ?: "Error al consultar registros")
            }
        }
    }
}
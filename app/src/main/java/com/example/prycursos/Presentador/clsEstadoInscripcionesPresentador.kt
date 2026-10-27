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

class clsEstadoInscripcionesPresentador( private val vista: EstadoInscripcionesActivity) {

    private val modelo = clsEstadoInscripcionModelo()

    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

    private var consulta: Job? = null

    fun consultarRegistros(carreraId: Int) {

        // Cancelar la consulta anterior si existe
        consulta?.cancel()

        consulta = scope.launch {

            try {

                val respuesta = modelo.consultarRegistros(carreraId)

                vista.mostrarCursos(
                    respuesta.cursos,
                    respuesta.total_inscritos
                )

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {

                // Registrar el error real
                android.util.Log.e(
                    "ESTADO_INSCRIPCIONES",
                    "Error al consultar registros",
                    e
                )

                // Mostrar el error sin utilizar componentes XML
                android.widget.Toast.makeText(
                    vista,
                    e.localizedMessage ?: "Error al consultar registros",
                    android.widget.Toast.LENGTH_LONG
                ).show()
            }
        }
        fun detener() {
            scope.cancel()
        }
    }
}
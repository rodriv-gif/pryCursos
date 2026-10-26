package com.example.prycursos.Presentador

import com.example.prycursos.Vista.RegistroActivity
import com.example.prycursos.Modelo.clsInscripcionModelo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Se recibe la pantalla (Vista) para poder mostrarle los resultados
class clsInscripcionPresentador(private val view: RegistroActivity) {

    // Se crea el modelo que se comunica con la API
    private val modelo = clsInscripcionModelo()

    fun realizarRegistro(matricula: String, correo: String, cursoId: Int) {

        // Se revisa que los campos no estén vacíos
        if (matricula.isEmpty() || correo.isEmpty()) {
            view.mostrarError("Por favor, ingresa tu matrícula y correo institucional")
            return
        }

        // Se abre un proceso en segundo plano para no congelar la pantalla
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Se envían los datos al servidor y se espera la respuesta
                val respuesta = withContext(Dispatchers.IO) {
                    modelo.registrarInscripcion(matricula, correo, cursoId)
                }

                //muestra los resultados del servidor
                if (respuesta.exito) {
                    view.registroExitoso(respuesta.mensaje)
                } else {
                    view.mostrarError(respuesta.mensaje)
                }

            } catch (e: Exception) {
                view.mostrarError("Error de conexión: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}
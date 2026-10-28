package com.example.prycursos.Presentador

import com.example.prycursos.Modelo.clsCursosModelo
import com.example.prycursos.Modelo.dataImagenesCurso
import com.example.prycursos.Vista.DetalleCursoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsDetalleCursoPresentador(private val vista: DetalleCursoActivity) {
    private val modelo = clsCursosModelo()

    fun cargarDetalle(idCurso: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val listaImagenes: List<dataImagenesCurso> = modelo.obtenerDetalleCurso(idCurso)
                vista.mostrarImagenes(listaImagenes)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar detalle: " + e.localizedMessage)
            }
        }
    }
}

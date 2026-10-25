package com.example.prycursos.Presentador

import com.example.prycursos.Modelo.clsCarrerasModelo
import com.example.prycursos.Modelo.dataCarreras
import com.example.prycursos.Vista.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsCategoriasPresentador(val vista: MainActivity) {
    private val modelo = clsCarrerasModelo()
    private var listaCategorias: List<dataCarreras> = emptyList()

    fun iniciar(){
        // Se usa un Scope simple para no bloquear la interfaz
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Aquí recibes el RETURN directo del modelo
                listaCategorias = modelo.obtenerCarreras()
                vista.mostrarCategorias(listaCategorias)
            } catch (e: Exception) {
                vista.mostrarError("Error: " + e.localizedMessage)
            }
        }
    }
    fun seleccionarCategoria(posicion: Int) {
        if (posicion > 0 && posicion < listaCategorias.size) {
            val categoria = listaCategorias[posicion]
            vista.mostrarMensajeExito(categoria.id, categoria.categoria)
        }
    }
}
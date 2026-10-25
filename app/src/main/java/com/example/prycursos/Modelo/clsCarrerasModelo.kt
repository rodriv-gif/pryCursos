package com.example.prycursos.Modelo

class clsCarrerasModelo {
    suspend fun obtenerCarreras(): List<dataCarreras>{
        val datos = RetrofitCliente.apiService.obtenerCarreras() //hace el pedido a la BD

        val listaFinal = mutableListOf(dataCarreras(0, "Seleccione una categoría"))
        listaFinal.addAll(datos)

        return listaFinal
    }
}
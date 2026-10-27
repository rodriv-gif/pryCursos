package com.example.prycursos.Modelo

import android.os.Handler
import android.os.Looper
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
class clsEstadoInscripcionModelo {

    suspend fun consultarRegistros(carreraId: Int): RespuestaEstadoInscripciones {

        return RetrofitCliente.apiService.obtenerEstadoInscripciones(carreraId)
    }
}
package com.example.prycursos.Modelo

import android.os.Handler
import android.os.Looper
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

// Información de cada curso
data class CursoInscripcion(val cursoId: Int, val nombre: String, val cupos: Int, val inscritos: Int, val disponibles: Int, val porcentaje: Int, val lleno: Boolean, val colorBarra: String, val urlImagen: String)

class clsModeloInscripciones {


    // Dirección de la API
    val urlApi = "http://192.168.0.66/cursos_api.php"
    val cupoMaximo = 50

    // Calcular lugares disponibles
    fun calcularDisponibles(inscritos: Int): Int {
        return (cupoMaximo - inscritos).coerceAtLeast(0)
    }
    // Calcular porcentaje de los inscritos
    fun calcularPorcentaje(inscritos: Int): Int {
        return ((inscritos * 100) / cupoMaximo).coerceIn(0, 100)
    }
    // Verificar si el curso ya no tiene espacio
    fun cursoLleno(inscritos: Int): Boolean {
        return inscritos >= cupoMaximo
    }
    // Consultar los registros por la api
    fun consultarRegistros(
        carreraId: Int,
        respuesta: (Int, List<CursoInscripcion>) -> Unit,
        error: (String) -> Unit
    ) {
        Thread {
            var conexion: HttpURLConnection? = null
            try {
                val separador = if (urlApi.contains("?")) "&" else "?"
                val url = URL("$urlApi${separador}carrera_id=$carreraId")

                conexion = url.openConnection() as HttpURLConnection

                conexion.requestMethod = "GET"
                conexion.connectTimeout = 10000
                conexion.readTimeout = 10000

                if (conexion.responseCode != 200) {
                    throw Exception("Error al consultar la API")
                }
                val datos = conexion.inputStream.bufferedReader().use {it.readText()}

                val json = JSONObject(datos)

                if (json.has("error")) {
                    throw Exception(json.getString("error"))
                }

                val totalInscritos = json.getInt("total_inscritos")
                val registros = json.getJSONArray("cursos")

                val cursos = mutableListOf<CursoInscripcion>()

                for (i in 0 until registros.length()) {

                    val curso = registros.getJSONObject(i)

                    val informacion = CursoInscripcion(
                        cursoId = curso.getInt("curso_id"),
                        nombre = curso.getString("nombre"),
                        cupos = curso.getInt("cupos"),
                        inscritos = curso.getInt("inscritos"),
                        disponibles = curso.getInt("disponibles"),
                        porcentaje = curso.getInt("porcentaje"),
                        lleno = curso.getBoolean("lleno"),
                        colorBarra = curso.getString("color_barra"),
                        urlImagen = obtenerUrlImagen(curso.optString("url_imagen", "")
                        )
                    )

                    cursos.add(informacion)
                }

                Handler(Looper.getMainLooper()).post {
                    respuesta(totalInscritos, cursos)
                }

            } catch (e: Exception) {

                Handler(Looper.getMainLooper()).post {
                    error(e.message ?: "No se pudieron cargar los registros")
                }

            } finally {
                conexion?.disconnect()
            }

        }.start()
    }
    // Obtener la dirección completa de la imagen
    fun obtenerUrlImagen(direccion: String): String {

        if (direccion.isBlank() || direccion == "null") {
            return ""
        }
        return URL(URL(urlApi), direccion).toString()
    }
}
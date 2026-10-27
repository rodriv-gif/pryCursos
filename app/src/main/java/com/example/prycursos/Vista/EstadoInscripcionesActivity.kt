package com.example.prycursos.Vista

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prycursos.R
import com.example.prycursos.Adaptador.EstadoInscripcionesAdaptador
import com.example.prycursos.Modelo.dataEstadoInscripciones
import com.example.prycursos.Presentador.clsEstadoInscripcionesPresentador

class EstadoInscripcionesActivity : AppCompatActivity() {

    private lateinit var presentador: clsEstadoInscripcionesPresentador
    private lateinit var adaptador: EstadoInscripcionesAdaptador
    private lateinit var spCarrera: Spinner
    private lateinit var txtTotalInscritos: TextView
    private lateinit var rvCursos: RecyclerView
    private lateinit var txtMensaje: TextView
    private lateinit var btnReintentar: Button
    private lateinit var btnVolverCatalogo: Button

    private var idsCarreras = listOf(0)
    private var carreraSeleccionada = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_inscripciones)

        // Ajustar el contenido a las barras del teléfono
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { vista, insets ->
            val barras = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )
            vista.setPadding(
                barras.left,
                barras.top,
                barras.right,
                barras.bottom
            )
            insets
        }
        // Componentes del XML
        spCarrera = findViewById(R.id.spCarrera)
        txtTotalInscritos = findViewById(R.id.txtTotalInscritos)
        rvCursos = findViewById(R.id.rvCursos)
        btnReintentar = findViewById(R.id.btnReintentar)
        btnVolverCatalogo = findViewById(R.id.btnVolverCatalogo)

        // Lista de cursos
        rvCursos.layoutManager = LinearLayoutManager(this)

        // Utilizar la misma URL base configurada por el equipo
        adaptador = EstadoInscripcionesAdaptador(
            obtenerUrlBase()
        )

        rvCursos.adapter = adaptador

        // Presentador
        presentador = clsEstadoInscripcionesPresentador(this)

        // Mostrar opción inicial
        mostrarCarreras(emptyList(), emptyList())

        // Consultar todos los cursos
        presentador.consultarRegistros(0)

        // Regresar
        btnVolverCatalogo.setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.txtRegresar).setOnClickListener {
            finish()
        }

        // Reintentar
        btnReintentar.setOnClickListener {
            presentador.consultarRegistros(carreraSeleccionada)
        }

        // Filtro por carrera
        spCarrera.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    val nuevoId = idsCarreras.getOrNull(position) ?: 0

                    if (nuevoId != carreraSeleccionada) {

                        carreraSeleccionada = nuevoId

                        presentador.consultarRegistros(nuevoId)
                    }
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {
                }
            }
    }

    // URL base compartida con Retrofit
    private fun obtenerUrlBase(): String {
        return com.example.prycursos.Modelo.RetrofitCliente.BASE_URL
    }

    // Cargar las carreras en el Spinner
    fun mostrarCarreras(
        nombresCarreras: List<String>,
        ids: List<Int>
    ) {

        if (nombresCarreras.size != ids.size) {
            return
        }

        val nombres = mutableListOf("Todas las carreras")
        nombres.addAll(nombresCarreras)

        idsCarreras = listOf(0) + ids

        val adaptadorCarreras = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            nombres
        )

        adaptadorCarreras.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spCarrera.adapter = adaptadorCarreras
    }

    /* // Mientras se realiza la consulta
     fun mostrarCargando() {

         txtMensaje.text = "Cargando cursos..."
         txtMensaje.visibility = View.VISIBLE
         btnReintentar.visibility = View.GONE
         rvCursos.visibility = View.GONE
     }*/

    // Mostrar resultados

    fun mostrarCursos(
        cursos: List<dataEstadoInscripciones>,
        total: Int
    ) {
        // Actualizar las tarjetas
        adaptador.actualizarCursos(cursos)

        // Actualizar el total de inscritos
        txtTotalInscritos.text = "Total: $total"

        // Mantener visible la lista de cursos
        rvCursos.visibility = View.VISIBLE
    }


    // Mostrar error de conexión
    fun mostrarError(mensaje: String) {

        rvCursos.visibility = View.GONE

        txtMensaje.text = mensaje
        txtMensaje.visibility = View.VISIBLE

        btnReintentar.visibility = View.VISIBLE
    }
}
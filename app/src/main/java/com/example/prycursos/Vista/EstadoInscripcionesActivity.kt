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
import android.view.ViewGroup
import com.example.prycursos.Modelo.RetrofitCliente

class EstadoInscripcionesActivity : AppCompatActivity() {


    private lateinit var presentador: clsEstadoInscripcionesPresentador
    private lateinit var adaptador: EstadoInscripcionesAdaptador
    private lateinit var spCarrera: Spinner
    private lateinit var txtTotalInscritos: TextView
    private lateinit var rvCursos: RecyclerView
    private lateinit var btnVolverCatalogo: Button
    private var idsCarreras = listOf(0)
    private var carreraSeleccionada = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_inscripciones)

        val contenedor = findViewById<ViewGroup>(android.R.id.content)
        val pantalla = contenedor.getChildAt(0)

        if (pantalla != null) {

            val izquierda = pantalla.paddingLeft
            val arriba = pantalla.paddingTop
            val derecha = pantalla.paddingRight
            val abajo = pantalla.paddingBottom

            ViewCompat.setOnApplyWindowInsetsListener(
                pantalla
            ) { vista, insets ->
                val barras = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )
                vista.setPadding(
                    izquierda + barras.left,
                    arriba + barras.top,
                    derecha + barras.right,
                    abajo + barras.bottom
                )
                insets
            }
            ViewCompat.requestApplyInsets(pantalla)
        }

        spCarrera = findViewById(R.id.spCarrera)
        txtTotalInscritos = findViewById(R.id.txtTotalInscritos)
        rvCursos = findViewById(R.id.rvCursos)
        btnVolverCatalogo = findViewById(R.id.btnVolverCatalogo)
        rvCursos.layoutManager = LinearLayoutManager(this)
        adaptador = EstadoInscripcionesAdaptador(RetrofitCliente.BASE_URL)

        rvCursos.adapter = adaptador

        presentador = clsEstadoInscripcionesPresentador(this)
        configurarSpinner()
        configurarBotones()
        presentador.consultarRegistros(0)
    }
    private fun configurarSpinner() {

        mostrarCarreras(
            emptyList(),
            emptyList()
        )
        spCarrera.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    val nuevoId =
                        idsCarreras.getOrNull(position) ?: 0

                    if (nuevoId != carreraSeleccionada) {

                        carreraSeleccionada = nuevoId

                        presentador.consultarRegistros(nuevoId)
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    fun mostrarCarreras(
        nombresCarreras: List<String>,
        ids: List<Int>
    ) {

        if (nombresCarreras.size != ids.size) {
            return
        }

        val nombres = mutableListOf(
            "Todas las carreras"
        )

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

        val posicion =
            idsCarreras.indexOf(carreraSeleccionada)

        spCarrera.setSelection(
            if (posicion >= 0) posicion else 0
        )
    }
    private fun configurarBotones() {
        btnVolverCatalogo.setOnClickListener {
            finish()
        }


        val txtRegresar =
            findViewById<TextView>(R.id.txtRegresar)

        txtRegresar?.setOnClickListener {
            finish()
        }
    }
    fun mostrarCursos(
        cursos: List<dataEstadoInscripciones>,
        total: Int
    ) {
        adaptador.actualizarCursos(cursos)
        txtTotalInscritos.text = "Total: $total"
        rvCursos.visibility = View.VISIBLE


        if (cursos.isEmpty()) {

            Toast.makeText(
                this,
                "No hay cursos registrados",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    fun mostrarError(mensaje: String) {
        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_LONG
        ).show()
    }
}
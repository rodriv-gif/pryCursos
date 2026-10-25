package com.example.prycursos.Vista

import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.prycursos.Modelo.CursoInscripcion
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prycursos.R
import java.net.HttpURLConnection
import java.net.URL
import com.example.prycursos.Presentador.clsPresentadorinscripciones

class clsVistainscripciones : AppCompatActivity() {

    // Presentador
    lateinit var presentador: clsPresentadorinscripciones

    // Componentes del XML
    lateinit var spCarrera: Spinner
    lateinit var txtTotalInscritos: TextView
    lateinit var llCursos: LinearLayout
    lateinit var btnVolverCatalogo: Button

    // Carrera seleccionada
    var carreraSeleccionada = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_inscripciones)

        // Crear el Presentador
        presentador = clsPresentadorinscripciones()

        // Identificar los componentes
        spCarrera = findViewById(R.id.spCarrera)
        txtTotalInscritos = findViewById(R.id.txtTotalInscritos)
        llCursos = findViewById(R.id.llCursos)
        btnVolverCatalogo = findViewById(R.id.btnVolverCatalogo)

        txtTotalInscritos.text = "Total: 0"

        // Preparar el Spinner
        mostrarCarreras(emptyList(), emptyList())

        // Botón para regresar
        btnVolverCatalogo.setOnClickListener {
            finish()
        }

        // Flecha para regresar
        val txtRegresar = findViewById<TextView>(R.id.txtRegresar)

        txtRegresar.setOnClickListener {
            finish()
        }
    }

    // CONSULTAR CURSOS DESDE LA API
    fun consultarCursos() {

        txtTotalInscritos.text = "Cargando registros..."

        presentador.consultarRegistros(
            carreraSeleccionada,

            { total, cursos ->
                mostrarCursos(cursos, total)
            },

            { mensaje ->
                llCursos.removeAllViews()

                txtTotalInscritos.text =
                    "No se pudieron cargar los registros"

                Toast.makeText(
                    this,
                    mensaje,
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }

    // MOSTRAR CURSOS
    fun mostrarCursos(
        cursos: List<CursoInscripcion>,
        total: Int
    ) {

        // Limpiar los cursos anteriores
        llCursos.removeAllViews()

        // Recorrer todos los cursos
        for (curso in cursos) {

            // Crear una tarjeta
            val tarjeta = LayoutInflater.from(this).inflate(R.layout.item_inscripcion, llCursos, false)

            // Componentes de la tarjeta
            val imgCurso = tarjeta.findViewById<ImageView>(R.id.imgCurso)

            val txtNombre = tarjeta.findViewById<TextView>(R.id.txtNombreCurso)

            val txtInscritos = tarjeta.findViewById<TextView>(R.id.txtInscritos)

            val txtCupo = tarjeta.findViewById<TextView>(R.id.txtCupo)

            val pbInscripciones = tarjeta.findViewById<ProgressBar>(R.id.pbInscripciones)

            // Mostrar la información
            txtNombre.text = curso.nombre
            txtInscritos.text = "${curso.inscritos} inscritos"
            txtCupo.text = "${curso.cupos} total"

            // Barra de progreso
            pbInscripciones.max = 100
            pbInscripciones.progress =
                curso.porcentaje.coerceIn(0, 100)

            // Cambiar color de la barra
            try {
                val color = Color.parseColor(curso.colorBarra)

                pbInscripciones.progressTintList =
                    ColorStateList.valueOf(color)

            } catch (e: Exception) {

                pbInscripciones.progressTintList =
                    ColorStateList.valueOf(Color.BLUE)
            }

            // Cargar imagen representativa
            cargarImagen(imgCurso, curso.urlImagen)

            // Agregar tarjeta a la pantalla
            llCursos.addView(tarjeta)
        }

        // Mostrar total de inscritos
        txtTotalInscritos.text = "Total: $total"
    }

    // MOSTRAR CARRERAS EN EL SPINNER
    fun mostrarCarreras(
        nombres: List<String>,
        ids: List<Int>
    ) {

        if (nombres.size != ids.size) {
            return
        }

        val listaNombres = mutableListOf("Todas las carreras")
        val listaIds = mutableListOf(0)

        for (i in nombres.indices) {

            if (ids[i] > 0) {
                listaNombres.add(nombres[i])
                listaIds.add(ids[i])
            }
        }

        // Crear adaptador
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listaNombres
        )

        adaptador.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spCarrera.adapter = adaptador

        // Detectar carrera seleccionada
        spCarrera.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    carreraSeleccionada = listaIds[position]

                    // Consultar los cursos
                    consultarCursos()
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>
                ) {
                    carreraSeleccionada = 0
                }
            }
    }

    // CARGAR IMAGEN DEL CURSO
    fun cargarImagen(imagen: ImageView, direccion: String) {

        if (direccion.isBlank() || direccion == "null") {
            return
        }

        Thread {

            var conexion: HttpURLConnection? = null

            try {

                val urlImagen = URL(direccion)

                conexion = urlImagen.openConnection()
                        as HttpURLConnection

                conexion.connectTimeout = 10000
                conexion.readTimeout = 10000

                if (conexion.responseCode != 200) {
                    throw Exception("No se pudo cargar la imagen")
                }

                val bitmap = conexion.inputStream.use {
                    BitmapFactory.decodeStream(it)
                }

                runOnUiThread {

                    if (!isFinishing && !isDestroyed && bitmap != null) {
                        imagen.setImageBitmap(bitmap)
                    }
                }

            } catch (e: Exception) {
                // Se conserva la imagen provisional
            } finally {
                conexion?.disconnect()
            }

        }.start()
    }
}
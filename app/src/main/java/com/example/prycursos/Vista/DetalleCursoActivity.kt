package com.example.prycursos.Vista

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.prycursos.Adaptador.GaleriaAdaptador
import com.example.prycursos.Modelo.dataImagenesCurso
import com.example.prycursos.Presentador.clsDetalleCursoPresentador
import com.example.prycursos.R

class DetalleCursoActivity : AppCompatActivity() {
    private lateinit var presenter: clsDetalleCursoPresentador
    private lateinit var vpGaleria: ViewPager2
    private lateinit var txtCodidoCurso: TextView
    private lateinit var txtNombreCuros: TextView
    private lateinit var txtDescripcion: TextView
    private lateinit var txtEspacio: TextView
    private lateinit var txtTotalHoras: TextView
    private lateinit var txtDiasAtencion: TextView
    private lateinit var txtHorasAtencion: TextView
    private lateinit var btnBack: Button
    private lateinit var adapterGaleria: GaleriaAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_curso)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        vpGaleria = findViewById(R.id.vpGaleria)
        txtCodidoCurso = findViewById(R.id.txtdetalleCodigo)
        txtNombreCuros = findViewById(R.id.txtTituloMateria)
        txtDescripcion = findViewById(R.id.txtdetalleDescripcion)
        txtEspacio = findViewById(R.id.txtdetalleEspacio)
        txtTotalHoras = findViewById(R.id.txtdetalleHoras)
        txtDiasAtencion = findViewById(R.id.txtdetalleDiasAtencion)
        txtHorasAtencion = findViewById(R.id.txtdetalleHorasAtencion)
        btnBack = findViewById(R.id.btnBack)
        adapterGaleria = GaleriaAdaptador(emptyList())
        vpGaleria.adapter = adapterGaleria

        btnBack.setOnClickListener {
            finish()
        }

        val id = intent.getIntExtra("ID_CURSO", 0)
        val codigoCurso = intent.getStringExtra("CODIGO_CURSO") ?: ""
        val nombreCurso = intent.getStringExtra("NOMBRE_CURSO") ?: ""
        val descripcion = intent.getStringExtra("DESCRIPCION") ?: ""
        val edificio = intent.getStringExtra("EDIFICIO") ?: ""
        val salon = intent.getStringExtra("SALON") ?: ""
        val totalHoras = intent.getStringExtra("TOTAL_HORAS") ?: ""
        val diasAtencion = intent.getStringExtra("DIAS_ATENCION") ?: ""
        val horasAtencion = intent.getStringExtra("HORAS_ATENCION") ?: ""

        txtCodidoCurso.text = codigoCurso
        txtNombreCuros.text = nombreCurso
        txtDescripcion.text = descripcion
        txtEspacio.text = "$edificio, $salon"
        txtTotalHoras.text = "$totalHoras Horas"
        txtDiasAtencion.text = diasAtencion
        txtHorasAtencion.text = "($horasAtencion hrs)"

        presenter = clsDetalleCursoPresentador(this)
        if (id != 0) {
            presenter.cargarDetalle(id)
        }
    }

    fun mostrarImagenes(imagenes: List<dataImagenesCurso>) {
        adapterGaleria.actualizarLista(imagenes)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}

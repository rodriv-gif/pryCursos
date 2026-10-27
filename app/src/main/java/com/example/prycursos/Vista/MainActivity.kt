package com.example.prycursos.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prycursos.Adaptador.CursosAdaptador
import com.example.prycursos.Modelo.dataCarreras
import com.example.prycursos.Modelo.dataCursos
import com.example.prycursos.Presentador.clsCategoriasPresentador
import com.example.prycursos.R

class MainActivity : AppCompatActivity() {
    private lateinit var presenter: clsCategoriasPresentador
    private lateinit var spinnerCarreras: Spinner
    private lateinit var recyclerCurso: RecyclerView
    private lateinit var adapterCurso: CursosAdaptador
    private lateinit var btnVerRegistro: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        spinnerCarreras = findViewById(R.id.spnCategorias)
        recyclerCurso = findViewById(R.id.rvLista)
        btnVerRegistro = findViewById(R.id.btnVerRegistros)

        recyclerCurso.layoutManager = GridLayoutManager(this,2)
        adapterCurso = CursosAdaptador(this, emptyList())
        recyclerCurso.adapter = adapterCurso
        presenter = clsCategoriasPresentador(this)

        configurarListeners()
        presenter.iniciar()
    }
    private fun configurarListeners() {
        spinnerCarreras.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.seleccionarCarreras(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        btnVerRegistro.setOnClickListener {
            val intent = Intent(this, EstadoInscripcionesActivity::class.java)
            startActivity(intent)
        }
    }
    fun mostrarCarreras(carreras: List<dataCarreras>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, carreras)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCarreras.adapter = adapter
    }
    fun mostrarCursos(productos: List<dataCursos>) {
        adapterCurso.actualizarLista(productos)
    }
    fun seleccionarCurso(producto: dataCursos) {
       // presenter.seleccionarCurso(producto)
    }
    fun registrarCurso(curso: dataCursos) {
        val intent = Intent(this, RegistroActivity::class.java)
        intent.putExtra("CURSO_ID", curso.id)
        intent.putExtra("CURSO_NOMBRE", curso.nombre)
        intent.putExtra("URL_IMAGEN", curso.urlimagenprincipal)
        startActivity(intent)
    }
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}

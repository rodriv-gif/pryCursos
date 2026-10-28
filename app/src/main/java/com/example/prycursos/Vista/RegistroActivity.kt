package com.example.prycursos.Vista

import android.os.Bundle
import android.os.PersistableBundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.prycursos.Presentador.clsInscripcionPresentador
import com.example.prycursos.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegistroActivity : AppCompatActivity() {
    private lateinit var imgVolver: ImageView
    private lateinit var txtSubtitulo: TextView
    private lateinit var imgCurso: ImageView
    private lateinit var txtNombreCurso: TextView
    private lateinit var txtMatricula: EditText
    private lateinit var txtCorreo : EditText
    private lateinit var txtFecha: EditText
    private var idCurso: Int=0
    private lateinit var btnConfirmarRegistro: Button
    private lateinit var btnCancelarRegistro: Button
    private lateinit var presenter: clsInscripcionPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState )
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()or WindowInsetsCompat.Type.ime())//WindowInsetsCompat nos ayuda a que el scroll y la pantalla se haga pequeño para que se vea mejor la pantalla
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imgVolver=findViewById(R.id.imgVolver)
        txtSubtitulo=findViewById(R.id.txtSubtitulo)
        imgCurso=findViewById(R.id.imgMiniaturaCurso)
        txtNombreCurso=findViewById(R.id.txtNombreCursoCard)
        txtMatricula=findViewById(R.id.txtMatricula)
        txtCorreo=findViewById(R.id.txtCorreo)
        txtFecha=findViewById(R.id.txtFecha)
        btnConfirmarRegistro=findViewById(R.id.btnConfirmarRegistro)
        btnCancelarRegistro=findViewById(R.id.btnCancelarRegistro)
        imgVolver=findViewById(R.id.imgVolver)

        //Se inicia el presentador
        presenter= clsInscripcionPresentador(this)

        //Se reciben los datos del curso que manda el catalogo
        idCurso=intent.getIntExtra("CURSO_ID", 0)
        val nombreCurso=intent.getStringExtra("CURSO_NOMBRE")?:""
        txtNombreCurso.text=nombreCurso
        txtSubtitulo.text="INSCRIPCIÓN:\n"+nombreCurso

        // Se recibe la ruta de la imagen y se arma la dirección completa
        val imagenCurso = intent.getStringExtra("URL_IMAGEN") ?: ""
        val baseUrl = "http://192.168.31.217/cursos_api/"
        val urlImagenCompleta = baseUrl + imagenCurso
        Glide.with(this)
            .load(urlImagenCompleta)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(imgCurso)

        //Es para mostrar la fecha en que se registra y no se puede editar
        txtFecha.setText(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()))
        txtFecha.isEnabled=false

        // El correo se llena solo al buscar la matrícula, el usuario no lo escribe
        txtCorreo.isEnabled = false

        // Se asignan las acciones de los botones
        txtMatricula.setOnEditorActionListener(this::buscarCorreo)
        btnConfirmarRegistro.setOnClickListener(this::ejecutarRegistro)
        btnCancelarRegistro.setOnClickListener { finish() }
        imgVolver.setOnClickListener { finish() }
    }

    // Se busca el correo cuando se presiona listo en el teclado
    fun buscarCorreo(v: TextView?, i: Int, event: KeyEvent?): Boolean {
        if (i == EditorInfo.IME_ACTION_DONE) {
            txtCorreo.setText("")
            presenter.buscarCorreo(txtMatricula.text.toString().trim())
        }
        return false
    }

    // Se muestra el correo que encontró el servidor
    fun mostrarCorreo(correo: String) {
        txtCorreo.setText(correo)
    }
    // Se leen los datos y se mandan al presentador
    fun ejecutarRegistro(v: View) {
        val matricula = txtMatricula.text.toString().trim()
        val correo = txtCorreo.text.toString().trim()
        presenter.realizarRegistro(matricula, correo, idCurso)
    }
    // Se muestra el mensaje de éxito y se regresa al catálogo
    fun registroExitoso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        finish()
    }
    // Se muestra el mensaje de error
    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }


}
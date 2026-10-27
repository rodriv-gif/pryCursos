package com.example.prycursos.Adaptador

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.prycursos.R
import com.example.prycursos.Modelo.dataEstadoInscripciones

import java.net.URL

class EstadoInscripcionesAdaptador(
    private val urlBase: String
) : RecyclerView.Adapter<EstadoInscripcionesAdaptador.CursoHolder>() {

    // Lista de cursos recibidos de la API
    private var listaCursos = listOf<dataEstadoInscripciones>()


    class CursoHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val imgCurso: ImageView =
            itemView.findViewById(R.id.imgCurso)

        val txtNombreCurso: TextView =
            itemView.findViewById(R.id.txtNombreCurso)

        val txtInscritos: TextView =
            itemView.findViewById(R.id.txtInscritos)

        val txtCupo: TextView =
            itemView.findViewById(R.id.txtCupo)

        val pbInscripciones: ProgressBar =
            itemView.findViewById(R.id.pbInscripciones)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CursoHolder {

        val vista = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_inscripciones,
                parent,
                false
            )

        return CursoHolder(vista)
    }


    override fun onBindViewHolder(
        holder: CursoHolder,
        position: Int
    ) {

        val curso = listaCursos[position]


        holder.txtNombreCurso.text = curso.nombre

                holder.txtInscritos.text =
            "${curso.inscritos} inscritos"


        holder.txtCupo.text =
            "${curso.cupos} total"


        val capacidad = curso.cupos.coerceAtLeast(1)

        holder.pbInscripciones.max = capacidad

        holder.pbInscripciones.progress =
            curso.inscritos.coerceIn(0, capacidad)


        val colorBarra = if (
            curso.cupos > 0 &&
            curso.inscritos >= curso.cupos
        ) {


            Color.parseColor("#2E9E4F")

        } else {


            Color.parseColor("#1F5FA8")
        }

        holder.pbInscripciones.progressTintList =
            ColorStateList.valueOf(colorBarra)


        val rutaImagen = curso.url_imagen.orEmpty().trim()

        val imagenCompleta = if (rutaImagen.isEmpty()) {

            null

        } else {

            try {

                URL(
                    URL(urlBase),
                    rutaImagen
                ).toString()

            } catch (e: Exception) {

                null
            }
        }


        Glide.with(holder.itemView.context)
            .load(imagenCompleta)
            .placeholder(
                android.R.drawable.ic_menu_gallery
            )
            .error(
                android.R.drawable.ic_menu_gallery
            )
            .centerCrop()
            .into(holder.imgCurso)
    }


    override fun getItemCount(): Int {

        return listaCursos.size
    }


    fun actualizarCursos(
        nuevosCursos: List<dataEstadoInscripciones>
    ) {

        listaCursos = nuevosCursos

        notifyDataSetChanged()
    }
}

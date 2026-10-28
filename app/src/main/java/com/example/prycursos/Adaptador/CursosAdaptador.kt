package com.example.prycursos.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.prycursos.Modelo.dataCursos
import com.example.prycursos.Vista.MainActivity
import com.example.prycursos.R

class CursosAdaptador(
    private val activity: MainActivity,
    private var listaCursos: List<dataCursos>
) : RecyclerView.Adapter<CursosAdaptador.CursoViewHolder>() {

    class CursoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.txtNombreCurso)
        val tvSalon: TextView = itemView.findViewById(R.id.txtSalonCurso)
        val imgCurso: ImageView = itemView.findViewById(R.id.imgCurso)
        val btnVerDetalle: Button = itemView.findViewById(R.id.btnVerDetalle)
        val btnRegistrarse: Button = itemView.findViewById(R.id.btnRegistrarse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_curso, parent, false)
        return CursoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        val curso = listaCursos[position]
        holder.tvNombre.text = curso.nombre
        holder.tvSalon.text = curso.salon
        val baseUrl = "http://10.191.45.71/cursos_api/"
        val urlImagenCompleta = baseUrl + curso.urlimagenprincipal
        Glide.with(holder.itemView.context)
            .load(urlImagenCompleta)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.imgCurso)

        holder.itemView.setOnClickListener {
            activity.seleccionarCurso(curso)
        }

        holder.btnVerDetalle.setOnClickListener {
            activity.seleccionarCurso(curso)
        }

        holder.btnRegistrarse.setOnClickListener {
            activity.registrarCurso(curso)
        }
    }

    override fun getItemCount(): Int {
        return listaCursos.size
    }

    fun actualizarLista(nuevaLista: List<dataCursos>) {
        listaCursos = nuevaLista
        notifyDataSetChanged()
    }
}
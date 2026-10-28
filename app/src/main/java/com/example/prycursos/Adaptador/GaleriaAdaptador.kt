package com.example.prycursos.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.prycursos.R
import com.example.prycursos.Modelo.dataImagenesCurso

class GaleriaAdaptador(private var listaImagenes: List<dataImagenesCurso>) :
    RecyclerView.Adapter<GaleriaAdaptador.GaleriaViewHolder>() {

    class GaleriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgGaleria: ImageView = itemView.findViewById(R.id.imgGaleria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GaleriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_galeria, parent, false)
        return GaleriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: GaleriaViewHolder, position: Int) {
        val imagen = listaImagenes[position]
        val baseUrl = "http://192.168.0.66/cursos_api/"
        val urlCompleto = baseUrl + imagen.urlImagen

        Glide.with(holder.itemView.context)
            .load(urlCompleto)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.imgGaleria)
    }

    override fun getItemCount(): Int {
        return listaImagenes.size
    }

    fun actualizarLista(nuevaLista: List<dataImagenesCurso>) {
        listaImagenes = nuevaLista
        notifyDataSetChanged()
    }
}
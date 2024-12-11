package com.example.tugasrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class adapterRecView (private val listTugas: ArrayList<tugas>) : RecyclerView
    .Adapter<adapterRecView.ListViewHolder>() {
        private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data:tugas)
        fun delData(pos: Int)
        fun editData(pos: Int)
        fun addToFav(pos : Int)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    inner class  ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var _judulTugas = itemView.findViewById<TextView>(R.id.judul)
        var _tanggal = itemView.findViewById<TextView>(R.id.tanggal)
        var _deskripsiTugas = itemView.findViewById<TextView>(R.id.deskripsi)
        var _btnHapus = itemView.findViewById<Button>(R.id.btnHapus)
        var _btnEdit = itemView.findViewById<Button>(R.id.edit)
        var _btnFav = itemView.findViewById<ImageView>(R.id.gbr1);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
       val view : View = LayoutInflater.from(parent.context)
           .inflate(R.layout.item_recycler,parent,false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTugas.size

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var tugas = listTugas[position]
        holder._judulTugas.setText(tugas.judul)
        holder._tanggal.setText(tugas.tanggal)
        holder._deskripsiTugas.setText(tugas.deskripsi)
        holder._judulTugas.setOnClickListener {
            Toast.makeText(holder.itemView.context,tugas.tanggal,Toast.LENGTH_LONG).show()
            onItemClickCallback.onItemClicked(listTugas[position])
        }
        holder._btnHapus.setOnClickListener {
            onItemClickCallback.delData(position)
        }
        holder._btnEdit.setOnClickListener {
            onItemClickCallback.editData(position)
        }
        holder._btnFav.setOnClickListener {
            onItemClickCallback.addToFav(position)

        }
    }
}
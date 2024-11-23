package com.example.tugasrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class adapterRecView (private val listTugas: ArrayList<tugas>) : RecyclerView
    .Adapter<adapterRecView.ListViewHolder>() {
        private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data:tugas)
        fun delData(pos: Int)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    inner class  ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var _judulTugas = itemView.findViewById<TextView>(R.id.judul)
        var _mataKuliahTugas = itemView.findViewById<TextView>(R.id.mataKuliah)
        var _deskripsiTugas = itemView.findViewById<TextView>(R.id.deskripsi)
        var _btnHapus = itemView.findViewById<TextView>(R.id.btnHapus)
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
        holder._mataKuliahTugas.setText(tugas.matkul)
        holder._deskripsiTugas.setText(tugas.deskripsi)
        holder._judulTugas.setOnClickListener {
            Toast.makeText(holder.itemView.context,tugas.matkul,Toast.LENGTH_LONG).show()
            onItemClickCallback.onItemClicked(listTugas[position])
        }
        holder._btnHapus.setOnClickListener {
            onItemClickCallback.delData(position)
        }
    }
}
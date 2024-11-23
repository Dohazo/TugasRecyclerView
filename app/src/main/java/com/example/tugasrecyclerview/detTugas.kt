package com.example.tugasrecyclerview

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detTugas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_det_tugas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val _judul = findViewById<TextView>(R.id.judul_det)
        val _matkul = findViewById<TextView>(R.id.matkul_det)
        val _deskripsi = findViewById<TextView>(R.id.deskripsi_det)
        val dataInten = intent.getParcelableExtra("data",tugas::class.java)
        if(dataInten != null){
            _judul.setText(dataInten.judul)
            _matkul.setText(dataInten.matkul)
            _deskripsi.setText(dataInten.deskripsi)
        }
    }
}
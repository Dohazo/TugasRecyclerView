package com.example.tugasrecyclerview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class addEdTugas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_ed_tugas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        val dataIn = intent.getParcelableArrayListExtra("dataTugas", arrayListOf<tugas>()::class.java)
        val dataIn: ArrayList<tugas>? = intent.getParcelableArrayListExtra("dataTugas")
        val inJudul = findViewById<EditText>(R.id.edJudul)
        val inTanggal = findViewById<EditText>(R.id.edTanggal)
        val inDeskripsi = findViewById<EditText>(R.id.edDeskripsi)
        val _addBtn = findViewById<Button>(R.id.addBtn)
        _addBtn.setOnClickListener {
            if (dataIn != null) {
                val newTugas = tugas(
                    judul = inJudul.text.toString(),
                    tanggal = inTanggal.text.toString(),
                    deskripsi = inDeskripsi.text.toString()
                )
                dataIn.add(newTugas)
                val resultIntent = Intent()
                resultIntent.putParcelableArrayListExtra("updatedDataTugas", dataIn)
                setResult(RESULT_OK, resultIntent)
                finish() // Kembali ke MainActivity
            }

        }
    }
}
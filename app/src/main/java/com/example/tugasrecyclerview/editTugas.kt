package com.example.tugasrecyclerview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class editTugas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_tugas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val _editJudul = findViewById<EditText>(R.id.editJudul)
        val _editMatkul = findViewById<EditText>(R.id.editMatkul)
        val _editDeskripsi = findViewById<EditText>(R.id.editDeskripsi)
        val _setBtn = findViewById<Button>(R.id.setEditBtn)
        val dataTugas = intent.getParcelableExtra<tugas>("data1_tugas")
        val dataPos =  intent.getIntExtra("dataPos", -1)


        _editJudul.setText(dataTugas?.judul)
        _editMatkul.setText(dataTugas?.matkul)
        _editDeskripsi.setText(dataTugas?.deskripsi)
        var isClicked = false
        _setBtn.setOnClickListener{
            Log.i("editTugas", "Button clicked, preparing result")
            val updatedTugas = tugas(
                _editJudul.text.toString(),
                _editMatkul.text.toString(),
                _editDeskripsi.text.toString()
            )
            if (!isClicked) {
                isClicked = true
                Log.i("editTugas", "Button clicked, preparing result")
            }
            val resultIntent = Intent()
            resultIntent.putExtra("editedData", updatedTugas) // Data yang diedit
            resultIntent.putExtra("dataPos", dataPos) // Posisi data yang diedit
            Log.i("editTugas", "Result set with updated data")
            _setBtn.isEnabled = false
            setResult(RESULT_OK, resultIntent)
            finish() // Tutup editTugas
        }
    }
}
package com.example.tugasrecyclerview

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
//    private lateinit var _judul : MutableList<String>
//    private lateinit var _matKul : MutableList<String>
//    private lateinit var _deskripsi  : MutableList<String>

    private var arTugas = arrayListOf<tugas>()
    private lateinit var _rvTugas : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val _btnTambah = findViewById<FloatingActionButton>(R.id.fab)
        _btnTambah.setOnClickListener {
            val inten = Intent(this@MainActivity, addEdTugas::class.java)
            inten.putParcelableArrayListExtra("dataTugas", arTugas)
            startActivityForResult(inten, REQUEST_CODE_ADD_TUGAS)

        }
       _rvTugas= findViewById<RecyclerView>(R.id.rvTugas)
//        siapkanData()
        tambahData()
        tampilkanData()

    }
    fun siapkanData(){
//        _judul = resources.getStringArray(R.array.judul).toMutableList()
//        _matKul = resources.getStringArray(R.array.matkul).toMutableList()
//        _deskripsi = resources.getStringArray(R.array.deskripsi).toMutableList()
    }
    fun tambahData(){
//        arTugas.clear()
//        for(pos in _judul.indices){
//            var data = tugas(
//                _judul[pos],
//                _matKul[pos],
//                _deskripsi[pos]
//            )
//            arTugas.add(data)
        }
    fun tampilkanData(){
        val adapterTugas = adapterRecView(arTugas)
        _rvTugas.adapter = adapterTugas

        adapterTugas.setOnItemClickCallback(object : adapterRecView.OnItemClickCallback{
            override fun onItemClicked(data: tugas) {
                Toast.makeText(this@MainActivity,data.judul,Toast.LENGTH_LONG).show()
                val inten =  Intent(this@MainActivity,detTugas::class.java)
                inten.putExtra("data",data)
                startActivity(inten)
            }

            override fun delData(pos: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Hapus Data?")
                    .setMessage("Apa Benar data dengan judul " + arTugas[pos].judul + " akan dihapus?")
                    .setPositiveButton(
                        "Hapus",
                        DialogInterface.OnClickListener{
                            dialog, which ->
                            arTugas.removeAt(pos)

                            tampilkanData()
                        }
                    )
                    .setNegativeButton(
                        "Batal",
                        DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(this@MainActivity,"Data batal dihapus",Toast.LENGTH_LONG)
                                .show()
                        }
                    ).show()
            }
        })
        _rvTugas.layoutManager = LinearLayoutManager(this)
//        _rvTugas.adapter = adapterRecView(arTugas)
    }
    companion object {
        const val REQUEST_CODE_ADD_TUGAS = 1
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_TUGAS && resultCode == RESULT_OK) {
            val updatedData = data?.getParcelableArrayListExtra<tugas>("updatedDataTugas")
            if (updatedData != null) {
                arTugas.clear()
                arTugas.addAll(updatedData)
                tampilkanData() // Perbarui tampilan RecyclerView
            }
        }
    }
}
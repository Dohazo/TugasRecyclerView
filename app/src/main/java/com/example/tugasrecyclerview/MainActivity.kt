package com.example.tugasrecyclerview

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
//    private lateinit var _judul : MutableList<String>
//    private lateinit var _matKul : MutableList<String>
//    private lateinit var _deskripsi  : MutableList<String>
    val db = Firebase.firestore
    lateinit var sp : SharedPreferences
    private var arTugas = arrayListOf<tugas>()
    private var arTugasFB = arrayListOf<tugas>()
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
        sp = getSharedPreferences("dataSP", MODE_PRIVATE)
        val gson = Gson()
        val isiSP = sp.getString("spTugas",null)
        val type = object : TypeToken<ArrayList<tugas>> (){}.type
        if(isiSP != null)
            arTugas = gson.fromJson(isiSP,type)

        val _btnTambah = findViewById<FloatingActionButton>(R.id.fab)
        _btnTambah.setOnClickListener {
            val inten = Intent(this@MainActivity, addEdTugas::class.java)
            inten.putParcelableArrayListExtra("dataTugas", arTugas)
            startActivityForResult(inten, REQUEST_CODE_ADD_TUGAS)

        }
       _rvTugas= findViewById<RecyclerView>(R.id.rvTugas)
        siapkanData(db)
        tambahData()
        tampilkanData()

    }
    fun siapkanData(db : FirebaseFirestore){
        val adapterTugas = adapterRecView(arTugasFB)
        _rvTugas.adapter = adapterTugas
        db.collection("tbTugas").get()
            .addOnSuccessListener {
                result ->
                arTugasFB.clear()
                for(document in result){
                    val readData = tugas(
                        document.data.get("judul").toString(),
                        document.data.get("tanggal").toString(),
                        document.data.get("deskripsi").toString()
                    )
                    arTugasFB.add(readData)
                }
                _rvTugas.adapter = adapterRecView(arTugasFB)
                // Beritahu adapter bahwa data telah berubah
                adapterTugas.notifyDataSetChanged()
                tampilkanData()
            }
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

            override fun editData(pos: Int) {
                val dataKirim = arTugas[pos]
                val inten = Intent(this@MainActivity,editTugas::class.java)
                inten.putExtra("data1_tugas",dataKirim)
                    .putExtra("dataPos",pos)

                startActivityForResult(inten, REQUEST_CODE_EDIT_TUGAS)
            }

            override fun addToFav(pos: Int) {
                val data = arTugas[pos]
                arTugas.add(arTugas[pos])
                arTugas.removeAt(pos)
                db.collection("tbTugas").document(data.judul).set(data)
                    .addOnSuccessListener {
                        Toast.makeText(this@MainActivity,"Data ditambahkan ke DB",Toast.LENGTH_LONG)
                            .show()
                        Log.d("Firebase","Data Berhasil Di simpan")
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@MainActivity,"Data fail ditambahkan ke DB",Toast.LENGTH_LONG)
                            .show()
                        Log.d("Firebase",it.message.toString())
                    }
            }
        })
        _rvTugas.layoutManager = LinearLayoutManager(this)
        _rvTugas.adapter = adapterRecView(arTugas)
    }
    companion object {
        const val REQUEST_CODE_ADD_TUGAS = 1
        const val REQUEST_CODE_EDIT_TUGAS = 2
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_TUGAS && resultCode == RESULT_OK) {
            val updatedData = data?.getParcelableArrayListExtra<tugas>("updatedDataTugas")
            if (updatedData != null) {
                arTugas.clear()
                arTugas.addAll(updatedData)
                val gson = Gson()
                val editor = sp.edit()
               val json = gson.toJson(arTugas)
                editor.putString("spTugas",json)
                editor.apply()
                siapkanData(db)
                tampilkanData()
            // Perbarui tampilan RecyclerView
            }
        }
        // Tangkap hasil edit
        if (requestCode == REQUEST_CODE_EDIT_TUGAS && resultCode == RESULT_OK) {
            val editedData = data?.getParcelableExtra<tugas>("editedData")
            val pos = data?.getIntExtra("dataPos", -1)?: -1

            if (editedData != null && pos != null && pos >= 0) {
                arTugas[pos] = editedData // Perbarui data di posisi tertentu
                siapkanData(db)
                tampilkanData() // Perbarui RecyclerView

            }
        }
    }
}
package com.example.tugasrecyclerview

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class tugas(
    var judul : String,
    var tanggal : String,
    var deskripsi : String
) : Parcelable

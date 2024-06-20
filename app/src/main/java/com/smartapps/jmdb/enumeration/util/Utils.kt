package com.tombra.ticket.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*



fun myDateFormatter(str: String): String {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = Date(str.toLong())
        format.format(date)!!
    } catch (e: Exception) {
        Log.d("ADAPTER", "TIME CONVERSION ERROR: ${e.message}")
        "..."
    }
}


fun encryptPassword(password: String): String{
    return password
}



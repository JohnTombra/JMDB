package com.smartapps.jmdb.enumeration.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity


import com.smartapps.jmdb.R


class CONGRATS_PAGE : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.congrats_page)
        val context: Context = this


        val login = findViewById<TextView>(R.id.home)



        login.setOnClickListener {
            startActivity(Intent(this, HomePageActivity::class.java))
            finish()
        }





    }

    override fun onBackPressed() {
       super.onBackPressed()
    }

}
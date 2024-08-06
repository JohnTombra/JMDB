package com.smartapps.jmdb.enumeration.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.smartapps.jmdb.R

class TrackNo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trackno)



        val continuee = findViewById<CardView>(R.id.continuee)
        val trackNoText = findViewById<EditText>(R.id.trackNoText)




        continuee.setOnClickListener {

            if(trackNoText.text.toString().isEmpty()){
                Toast.makeText(this,"Enter track no to continue registration", Toast.LENGTH_SHORT).show()
            }else{
                //download data and start
                SECTION = "REGISTER"
                startActivity(Intent(this, TIN_PAGE::class.java))
            }


        }

    }
}
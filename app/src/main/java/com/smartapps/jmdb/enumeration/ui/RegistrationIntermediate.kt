package com.smartapps.jmdb.enumeration.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.cardview.widget.CardView
import com.smartapps.jmdb.R

class RegistrationIntermediate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrationintermediate)


        val neww = findViewById<CardView>(R.id.neww)
        val continuee = findViewById<CardView>(R.id.continuee)
        val trackNo = findViewById<CardView>(R.id.trackNo)
        val trackNoText = findViewById<EditText>(R.id.trackNoText)



        neww.setOnClickListener {
            SECTION = "REGISTER"
            startActivity(Intent(this, TIN_PAGE::class.java))
            finish()
        }

        continuee.setOnClickListener {
                startActivity(Intent(this, TrackNo::class.java))
        }

    }
}
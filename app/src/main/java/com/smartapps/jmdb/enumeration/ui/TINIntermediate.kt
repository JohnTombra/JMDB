package com.smartapps.jmdb.enumeration.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.smartapps.jmdb.R

class TINIntermediate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tinintermediate)


        val individual = findViewById<CardView>(R.id.individual)
        val company = findViewById<CardView>(R.id.company)




        individual.setOnClickListener {
            startActivity(Intent(this, CreateTINActivity::class.java))
            finish()
        }

        company.setOnClickListener {
            startActivity(Intent(this, CreateTINActivity2::class.java))
            finish()
        }

    }
}
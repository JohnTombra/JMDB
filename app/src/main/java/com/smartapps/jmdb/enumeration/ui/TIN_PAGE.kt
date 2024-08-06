package com.smartapps.jmdb.enumeration.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.smartapps.jmdb.registration.MainActivity


import com.smartapps.jmdb.R
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.enumeration.util.Constants


var SECTION = ""

class TIN_PAGE : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tin_page)
        val context: Context = this

        val repository = JmdbRepository

        val loadingScreen = findViewById<CardView>(R.id.loadingScreen)

        val login = findViewById<TextView>(R.id.login)
        val tin = findViewById<EditText>(R.id.tin)

        val skip = findViewById<LinearLayoutCompat>(R.id.skip)



        skip.setOnClickListener {

            Constants.TIN = ""

          //  loadingScreen.isVisible = true

            repository.getTagAndPop({ tag ->
                Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context, JmdbNewBuildingActivity::class.java).putExtra("jmdb_number", tag))
                loadingScreen.isVisible = false
                finishAffinity()
            }){ error ->
                loadingScreen.isVisible = false
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }

        }

        login.setOnClickListener {

            if (TextUtils.isEmpty(tin.text.toString())) {
                Toast.makeText(context, "Enter Taxpayer Identification number", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            loadingScreen.isVisible = true




            repository.validatePhone2(tin.text.toString(), { tag ->
                //success
                Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
                if (SECTION == "CREATE") {
                    startActivity(Intent(context, JmdbNewBuildingActivity::class.java).putExtra("jmdb_number", tag))
                    loadingScreen.isVisible = false
                    finishAffinity()
                } else {
                    startActivity(Intent(context, MainActivity::class.java))
                    loadingScreen.isVisible = false
                    finish()
                }

            }, { itt->

                loadingScreen.isVisible = false
                Toast.makeText(context, itt, Toast.LENGTH_SHORT).show()


//                repository.validateTin(tin.text.toString(), {
//
//                    Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
//                    if (SECTION == "CREATE") {
//                        startActivity(Intent(context, JmdbNewBuildingActivity::class.java))
//                        loadingScreen.isVisible = false
//                        finish()
//                    } else {
//
//                        startActivity(Intent(context, MainActivity::class.java))
//                        loadingScreen.isVisible = false
//                        finish()
//                    }
//
//                }, {
//
////"Tin not found"
//                    loadingScreen.isVisible = false
//                    Toast.makeText(context, "Tin: " + it +", Phone: " + itt, Toast.LENGTH_SHORT).show()
//
//
//                }) {
//                    loadingScreen.isVisible = false
//                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
//                }


            }) {
                loadingScreen.isVisible = false
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()

            }


        }


        if (!isLocationEnabled(this)) {
            Toast.makeText(this, "Enable location first", Toast.LENGTH_SHORT).show()
            promptEnabledLocation(this)
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
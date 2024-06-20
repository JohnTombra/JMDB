package com.smartapps.jmdb.enumeration.authentication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Carriers.PASSWORD
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.smartapps.jmdb.MainActivity
import com.smartapps.jmdb.enumeration.registrationdata.model.LoginBody


import com.smartapps.jmdb.R
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.enumeration.data.repository.PASSWORDD
import com.smartapps.jmdb.enumeration.data.repository.USERNAME
import com.smartapps.jmdb.enumeration.ui.HomePageActivity
import com.smartapps.jmdb.enumeration.ui.JmdbNewBuildingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


var SECTION = ""

class TIN_PAGE : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tin_page)
        val context: Context = this

        val repository = JmdbRepository(this)

        val loadingScreen = findViewById<CardView>(R.id.loadingScreen)

        val login = findViewById<TextView>(R.id.login)
        val tin = findViewById<EditText>(R.id.tin)
        login.setOnClickListener {

            if (TextUtils.isEmpty(tin.text.toString())) {
                Toast.makeText(context, "Enter Taxpayer Identification number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
          loadingScreen.isVisible = true



            repository.validatePhone(tin.text.toString(),{
                //success
                Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
                if(SECTION == "CREATE"){
                    startActivity(Intent(context, JmdbNewBuildingActivity::class.java))
                    loadingScreen.isVisible = false
                    finish()
                }else{

                    startActivity(Intent(context, MainActivity::class.java))
                    loadingScreen.isVisible = false
                    finish()
                }

            }){

                //23320765908
                repository.validateTin(tin.text.toString(),{
                    //success
                    Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
                    if(SECTION == "CREATE"){
                        startActivity(Intent(context, JmdbNewBuildingActivity::class.java))
                        loadingScreen.isVisible = false
                        finish()
                    }else{

                        startActivity(Intent(context, MainActivity::class.java))
                        loadingScreen.isVisible = false
                        finish()
                    }

                }){ loadingScreen.isVisible = false
                    Toast.makeText(context, "Tin not found", Toast.LENGTH_SHORT).show()
                }
            }






        }





    }

    override fun onBackPressed() {
       super.onBackPressed()
    }

}
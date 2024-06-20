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
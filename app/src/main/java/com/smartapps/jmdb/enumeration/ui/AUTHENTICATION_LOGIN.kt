package com.smartapps.jmdb.enumeration.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.smartapps.jmdb.enumeration.registrationdata.model.LoginBody


import com.smartapps.jmdb.R
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository


class AUTHENTICATION_LOGIN : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

          setContentView(R.layout.activity_authentication_verify_screen)
          val context: Context = this

          val repository = JmdbRepository

            val password = findViewById<EditText>(R.id.password)
            val show = findViewById<TextView>(R.id.show)
            var shown = false
            show.setOnClickListener {
                if (shown) {
                    shown = false
                    password.transformationMethod = PasswordTransformationMethod.getInstance()
                    show.text = "Show password"
                } else {
                    shown = true
                    show.text = "Hide password"
                    password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
            }


            val userName = findViewById<EditText>(R.id.userName)
            val login = findViewById<TextView>(R.id.login)
            val progressText = findViewById<TextView>(R.id.progressText)
            val loadingScreen = findViewById<CardView>(R.id.loadingScreen)


            login.setOnClickListener {

                if (TextUtils.isEmpty(userName.text.toString())) {
                    Toast.makeText(context, "Fill in your username", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (TextUtils.isEmpty(password.text.toString())) {
                    Toast.makeText(context, "Fill in your password", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }


                    loadingScreen.isVisible = true
                    val loginBody = LoginBody(
                        username = userName.text.toString(),
                        password = password.text.toString()
                    )

                    repository.cleanLogin({
                        progressText.text = "Preparing offline setup" + "\n$it"
                    },loginBody,{
                        Log.d("LOGINX", "FINISHED 3")
                        startActivity(
                            Intent(
                                context,
                                HomePageActivity::class.java
                            )
                        )
                        finish()

                    }){
                        Toast.makeText(context,it, Toast.LENGTH_SHORT).show()
                        loadingScreen.isVisible = false
                    }

            }



    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}
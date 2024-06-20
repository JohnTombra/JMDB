package com.smartapps.jmdb.enumeration.authentication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

import com.smartapps.jmdb.R

import com.smartapps.jmdb.enumeration.model.User
import com.tombra.ticket.util.encryptPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class AUTHENTICATION_SIGNUP : AppCompatActivity() {


    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null



    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication_welcome_screen)


        context = this



        val userName = findViewById<EditText>(R.id.userName)
        val password = findViewById<EditText>(R.id.password)
        val verifyPassword = findViewById<EditText>(R.id.confirmPassword)
        val signup = findViewById<TextView>(R.id.signup)
        val gotoLogin = findViewById<TextView>(R.id.login)
        val loadingScreen = findViewById<ConstraintLayout>(R.id.loadingScreen)




        gotoLogin.setOnClickListener {
            startActivity(Intent(context, AUTHENTICATION_LOGIN::class.java))
        }


        signup.setOnClickListener {

            if (TextUtils.isEmpty(userName.text.toString())) {
                Toast.makeText(context, "Fill in your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!userName.text.toString().contains("@")) {
                Toast.makeText(context, "Invalid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password.text.toString())) {
                Toast.makeText(context, "Fill in your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(verifyPassword.text.toString())) {
                Toast.makeText(context, "Confirm your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.text.toString() != verifyPassword.text.toString()) {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loadingScreen.isVisible = true


            val password = encryptPassword(verifyPassword.text.toString())
            val user = User(email = userName.text.toString(), password = password)



            CoroutineScope(Dispatchers.Main).launch {
//                repository.addUser(user,{
//                    loadingScreen.isVisible = false
//                    Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT)
//                        .show()
//                    startActivity(Intent(context, AUTHENTICATION_LOGIN::class.java))
//                }){
//                    loadingScreen.isVisible = false
//                }
            }


//            //add check for if email already exists
//            mAuth.createUserWithEmailAndPassword("${userName.text.toString().trim()}@gmail.com", password.text.toString())
//                .addOnCompleteListener { task ->
//
//                    if (task.isSuccessful) {
//
//                        loadingScreen.isVisible = false
//                        Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT)
//                            .show()
//
//                        startActivity(Intent(context, AUTHENTICATION_LOGIN::class.java))
//
//                    } else {
//                        loadingScreen.isVisible = false
//                        Toast.makeText(
//                            context,
//                            "Registration failed ${task.exception!!.message}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                }


        }


    }


}
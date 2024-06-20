package com.smartapps.jmdb.enumeration.authentication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Carriers.PASSWORD
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.smartapps.jmdb.enumeration.registrationdata.model.LoginBody


import com.smartapps.jmdb.R
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.enumeration.data.repository.PASSWORDD
import com.smartapps.jmdb.enumeration.data.repository.USERNAME
import com.smartapps.jmdb.enumeration.ui.HomePageActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

var TYPE = "NEW"
class AUTHENTICATION_LOGIN : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val repository = JmdbRepository(this)



        if (false) { //repository.fetchAuthToken()!!.isNotEmpty()
            startActivity(
                Intent(
                    this,
                    HomePageActivity::class.java
                )
            )
        } else {

            setContentView(R.layout.activity_authentication_verify_screen)
            val context: Context = this


            val lgaSpinner = findViewById<Spinner>(R.id.lgas)


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


        val lgas = listOf("JMDB")

        val lgaAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            context,
            R.layout.spinner_item, lgas
        )

        lgaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        lgaSpinner.adapter = lgaAdapter





//                repository.getMetaData({
//                    Log.d("FRAGMENT", "META DATA COLLECTED")
//                }) {
//                    Log.d("FRAGMENT", "META DATA ERROR $it")
//                }


            val userName = findViewById<EditText>(R.id.userName)

            val tin = findViewById<EditText>(R.id.tin)
            val login = findViewById<TextView>(R.id.login)
            val gotoSignup = findViewById<TextView>(R.id.signup)

            val verifyScreen = findViewById<ConstraintLayout>(R.id.verifyScreen)
            val ok = findViewById<TextView>(R.id.ok)

            ok.setOnClickListener {
                verifyScreen.isVisible = false
            }

            val loadingScreen = findViewById<CardView>(R.id.loadingScreen)

            gotoSignup.setOnClickListener {
           //     startActivity(Intent(context, AUTHENTICATION_SIGNUP::class.java))
            }


            userName.setText(USERNAME)
            password.setText(PASSWORDD)


//            repository.getLastUser()?.let {
//                userName.setText(it)
//            }

            login.setOnClickListener {



//                if(true){
//
//                    val loginBody = LoginBody(
//                        username = "admin@demo",
//                        password = "1234567"
//                    )
//
//                    repository.login(loginBody,{
//
//                        repository.logIn()
//                        startActivity(
//                            Intent(
//                                context,
//                                HomePageActivity::class.java
//                            )
//                        )
//                    }){
//                        Toast.makeText(context,"Login Error: "+it, Toast.LENGTH_SHORT).show()
//                        loadingScreen.isVisible = false
//                    }
//
//
//                    return@setOnClickListener
//                }




                if (TextUtils.isEmpty(userName.text.toString())) {
                    Toast.makeText(context, "Fill in your username", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }



                if (TextUtils.isEmpty(password.text.toString())) {
                    Toast.makeText(context, "Fill in your password", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }





                if(true){
                    loadingScreen.isVisible = true
                    val loginBody = LoginBody(
                        username = userName.text.toString(),
                        password = password.text.toString()
                    )

                    USERNAME = userName.text.toString()
                    PASSWORDD = password.text.toString()

                    repository.login(loginBody,{

                        repository.logIn()
                        startActivity(
                            Intent(
                                context,
                                HomePageActivity::class.java
                            )
                        )
                    }){
                        Toast.makeText(context,"Login Error: "+it, Toast.LENGTH_SHORT).show()
                        loadingScreen.isVisible = false
                    }


                    return@setOnClickListener
                }


//                //the last username should be saved and put automatically
//                repository.sLogin(lgaSpinner.selectedItem.toString(),userName.text.toString(),password.text.toString(),{
//
//
//                    Repository.token = repository.fetchAuthToken()!!
//
//                    Log.d("OKHTTPZ","LOGIN SUCCESS")
//
//                    repository.saveLastUser(userName.text.toString())
//
//                        Log.d("OKHTTPZ","STAGE TWO SUCCESS ACTIVITY - S")
//                        repository.logIn()
//                        startActivity(
//                            Intent(
//                                context,
//                                HomePageActivity::class.java
//                            )
//                        )
//
//                    ///Features
//                    //offline use... initial setup page for offline use. happens only onesR
//                    //sync and unsyned indicator
//                    //dash board sync indicator
//                    //search
//                    //lat and long
//                    //map view
//                    //print
//
//
//
//               //     loadingScreen.isVisible = false
//
//                }){
//                    CoroutineScope(Dispatchers.Main).launch {
//
//
//                    Log.d("ACTIVITY","STAGE TWO ERROR ACTIVITY - S")
//                    Toast.makeText(context,"Login Error: "+it, Toast.LENGTH_SHORT).show()
//                        loadingScreen.isVisible = false
//                    }
//               //
//                }

                //get web users
                /*   repository.checkUser(userName.text.toString(), password.text.toString(),{
                       loadingScreen.isVisible = false
                       Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
                   }) { uid ->




                       repository.checkUser3(userName.text.toString(),{



                           if(Repository.myMetadata == null) {

                               repository.getMetaData({
                                   Log.d("FRAGMENT","META DATA COLLECTED")
                                   repository.logIn(uid)
                                   startActivity(
                                       Intent(
                                           context,
                                           HomePageActivity::class.java
                                       )
                                   )

                               }){
                                   Log.d("FRAGMENT","META DATA ERROR $it")
                                   loadingScreen.isVisible = false
                                   Toast.makeText(this,"Error getting required data: $it", Toast.LENGTH_SHORT).show()
                               }


                           }else{

                               repository.logIn(uid)
                               startActivity(
                                   Intent(
                                       context,
                                       HomePageActivity::class.java
                                   )
                               )
                           }



                       }){
                           loadingScreen.isVisible = false

                       }




                   }*/


//                repository.getUser(userName.text.toString(), password.text.toString(),{
//                       Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
//                            //get user data from database and save into shared preference
//                            repository.logIn()
//                            startActivity(
//                                Intent(
//                                    context,
//                                    MainActivity::class.java
//                                )
//                            )
//                }){
//                    loadingScreen.isVisible = false
//                }

//
//
//

//                repository.getLogins2(userName.text.toString(), password.text.toString(),{
//                    loadingScreen.isVisible = false
//                }){
//                    startActivity(
//                        Intent(
//                            context,
//                            MainActivity::class.java
//                        )
//                    )
//                }

//
//                mAuth.signInWithEmailAndPassword(
//                    "${userName.text.toString().trim()}@gmail.com",
//                    password.text.toString()
//                )
//                    .addOnCompleteListener { task ->
//
//
//                        if (task.isSuccessful) {
//
//
//
//
//
//
//                            repository.getLogins3(userName.text.toString(),{
//                                loadingScreen.isVisible = false
//                            }){
//
//                                repository.logIn()
//                                startActivity(
//                                    Intent(
//                                        context,
//                                        MainActivity::class.java
//                                    )
//                                )
//
//                            }
//
//
//
////                            repository.getLogins2(userName.text.toString(), password.text.toString(),{
////                                loadingScreen.isVisible = false
////                            }){
////                                startActivity(
////                                    Intent(
////                                        context,
////                                        MainActivity::class.java
////                                    )
////                                )
////                            }
////
//
//
//
//
//
//                         //   Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
//                            //get user data from database and save into shared preference
//
//
//
//
//                        } else {
//                            loadingScreen.isVisible = false
//                            Toast.makeText(
//                                context,
//                                "Login failed ${task.exception!!.message}",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//
//
//                    }


            }


        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}
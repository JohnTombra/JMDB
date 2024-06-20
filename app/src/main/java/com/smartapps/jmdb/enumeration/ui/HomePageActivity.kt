package com.smartapps.jmdb.enumeration.ui

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import com.smartapps.jmdb.MainActivity

import com.smartapps.jmdb.enumeration.authentication.AUTHENTICATION_LOGIN
import com.smartapps.jmdb.enumeration.authentication.TYPE
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.enumeration.data.repository.PASSWORDD
import com.smartapps.jmdb.enumeration.data.repository.USERNAME
import com.smartapps.jmdb.databinding.ActivityHomePageBinding
import com.smartapps.jmdb.enumeration.authentication.SECTION
import com.smartapps.jmdb.enumeration.authentication.TIN_PAGE
import com.smartapps.jmdb.enumeration.data.repository.TOKENN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val repository = JmdbRepository(this)


      //  repository.loginX()



      TOKENN = repository.fetchAuthToken()!!


//        repository.getStreets({
//
//        }){
//
//        }

//        repository.getIdentifications({
//
//        }){
//
//        }


        object: CountDownTimer(20000,1000){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {

             repository.printData()
            }

        }.start()

    //    if (Repository.myMetadata == null) {
//            repository.getMetaData({
//                Log.d("FRAGMENT", "META DATA COLLECTED")
//            }) {
//                Log.d("FRAGMENT", "META DATA ERROR $it")
//            }
    //    }



//        binding.one.setOnClickListener {
//            Toast.makeText(this,"Fetching business types", Toast.LENGTH_SHORT).show()
//            repository.getBusinessTypes()
//        }
//
//
//        binding.two.setOnClickListener {
//            Toast.makeText(this,"Fetching business categories", Toast.LENGTH_SHORT).show()
//            repository.getBusinessCategories()
//        }
//
//
//        binding.three.setOnClickListener {
//            Toast.makeText(this,"Fetching building types", Toast.LENGTH_SHORT).show()
//            repository.getBuildingTypes()
//        }



        binding.buildings.setOnClickListener {
            SECTION = "CREATE"
            startActivity(Intent(this, TIN_PAGE::class.java))
//            repository.cats()
//
//            if(true) return@setOnClickListener


            CoroutineScope(Dispatchers.Main).launch {

            //    repository.streetsCrawler()

                //        repository.createBizCat()
//                repository.syncOnce({
//                    Log.d("SYNC-ALL","SUCCESS")
//                }){
//                    Log.d("SYNC-ALL","ERROR: " + it)
//                }

         //      repository.getWards()

            //   repository.getBusinessCategories()

            }


       //     repository.getStreets()

        //    repository.createBiz()



        }





        binding.neww.setOnClickListener {
            SECTION = "REGISTER"
            startActivity(Intent(this, TIN_PAGE::class.java))
        }


        binding.business.setOnClickListener {

            startActivity(Intent(this, JmdbBuildingsListActivity::class.java))
        }





        binding.sync.setOnClickListener {
//            Toast.makeText(this,"Syncing", Toast.LENGTH_SHORT).show()
//            if (Repository.myMetadata == null) {
//                repository.getMetaData({
//                    Toast.makeText(this,"Synced!", Toast.LENGTH_SHORT).show()
//                    Log.d("FRAGMENT", "META DATA COLLECTED")
//                }) {
//                    Log.d("FRAGMENT", "META DATA ERROR $it")
//                    Toast.makeText(this,"Error Syncing: $it", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//
//            Toast.makeText(this,"Synced!", Toast.LENGTH_SHORT).show()

        }



        binding.enrollNew.setOnClickListener {
//            TYPE = "ENROLL"
//            repository.enrollNew()
//            startActivity(Intent(this, AUTHENTICATION_LOGIN::class.java))
//            finish()
        }

        binding.logout.setOnClickListener {
            TYPE = "NEW"
            USERNAME = ""
            PASSWORDD = ""
            repository.logOut()
            startActivity(Intent(this, AUTHENTICATION_LOGIN::class.java))
            finish()
        }



    }

    override fun onBackPressed() {
        super.moveTaskToBack(true)
    }

//http://3.9.45.117/OpenPaymentsApi/validate_tin/23320765908

    //http://3.9.45.117/OpenPaymentsApi/validate_phone/08136759553




//{ "status": "success", "message": "Unique TIN found!", "title": null, "name": null, "first_name": "Paul", "middle_name": " ", "surname": "Jude", "tin": "23320765908", "phoneNumber": "08136759553", "address": null, "account_type": "individual" }


    //
    //
    //
    //
    //
    //User-Agent.....PostmanRuntime/7.36.1
    //Accept...*/*
    //Accept-Encoding....gzip, deflate, br
    //Connection...keep-alive
}
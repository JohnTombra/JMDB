package com.novaapps.jmdb.data.repository

import android.util.Log
import com.novaapps.jmdb.data.model.CountriesResponse
import com.novaapps.jmdb.data.model.Form1Body
import com.novaapps.jmdb.data.model.Form2Body
import com.novaapps.jmdb.data.model.Form3Body
import com.novaapps.jmdb.data.model.Form4Body
import com.novaapps.jmdb.data.model.Form5Body
import com.novaapps.jmdb.data.model.Form6Body
import com.novaapps.jmdb.data.model.LoginBody
import com.novaapps.jmdb.data.model.LoginResponse
import com.novaapps.jmdb.data.model.StatesResponse
import com.novaapps.jmdb.data.model.TinVerificationResponse
import com.novaapps.jmdb.data.model.Form1Response
import com.novaapps.jmdb.data.model.Form2Response
import com.novaapps.jmdb.data.model.Form3Response
import com.novaapps.jmdb.data.model.Form4Response
import com.novaapps.jmdb.data.model.Form5Response
import com.novaapps.jmdb.data.model.Form6Response
import com.novaapps.jmdb.data.model.IdentificationResponse
import com.novaapps.jmdb.data.model.LandPurposeResponse
import com.novaapps.jmdb.data.model.LandUseResponse
import com.novaapps.jmdb.data.model.LgasResponse
import com.smartapps.jmdb.data.repository.ApiService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

var TOKEN = ""

class Repository {



    companion object {
        var BASE_URL = "http://jmdb.eu-north-1.elasticbeanstalk.com/"




        var image1: String = ""
        var image2: String = ""
        var image3: String = ""
        var image4: String = ""
        var image5: String = ""
        var image6: String = ""
        var image7: String = ""
        var image8: String = ""
        var image9: String = ""
        var image10: String = ""
        var image11: String = ""



    }


    val client = OkHttpClient.Builder()
        .addInterceptor(MyInterceptor())
        .connectTimeout(210, TimeUnit.SECONDS)
        .readTimeout(210, TimeUnit.SECONDS)
        .writeTimeout(210, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()}


    private val service: ApiService by lazy {   retrofit.create(ApiService::class.java) }




    fun uploadPdf(name: String, image: String){

    }


//      fun login(loginBody: LoginBody,success:(LoginResponse)->Unit, error:(String)->Unit){
//
//
//        val coroutineException = CoroutineExceptionHandler { _, throwable ->
//            throwable.printStackTrace()
//        }
//
//        CoroutineScope(Dispatchers.IO + coroutineException).launch {
//
//
//
//
//
//
//            Log.d("LOGINS", "CLICKED")
//
//            val response = service.login(loginBody)  //TODO("make authorization dynamic")
//
//            Log.d("LOGINS", "RESPONSE: $response")
//
//            if (response.isSuccessful) {
//                try {
//                    Log.d("LOGINS", "RESPONSE BODY: ${response.body()!!}")
//                    TOKEN = response.body()!!.token
//
//
//                } catch (e: Exception) {
//                    Log.d("LOGINS", "EXCEPTION: " + e.message.toString())
//                    CoroutineScope(Dispatchers.Main).launch {
//                        error(e.message.toString())
//                    }
//                }
//            } else {
//                Log.d("LOGINS", "ERROR: ${response}")
//                CoroutineScope(Dispatchers.Main).launch {
//                error(response.message())}
//            }
//
//
//        }
//
//
//    }



   fun verifyTin(tin: String,success:(/*TinVerificationResponse*/)->Unit, error:(String)->Unit){

/*

//       if(true){
//           success()
//           return
//       }

        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        Log.d("TINNER", "CLICKED: ${TOKEN}")
       Log.d("TINNER", "CLICKED2: ${TOKEN}")
        CoroutineScope(Dispatchers.IO + coroutineException).launch {

            val response =
                service.verifyTin(tin)  //TODO
            if (response.isSuccessful) {
                try {
                    Log.d("TINNER", "TRYING")
                    CoroutineScope(Dispatchers.Main).launch {
                        tinVerificationResponse = response.body()!!
                    success(*//*response.body()!!*//*)
                        Log.d("TINNER", "SUCCESS ${tinVerificationResponse}")
                    }
                } catch (e: Exception) {
                    CoroutineScope(Dispatchers.Main).launch {
                        Log.d("TINNER", "NETWORK ERROR A")
                    error(e.message!!)}
                }
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("TINNER", "NETWORK ERROR B")
                error(response.message())}
            }
        }*/

    }



    suspend fun getStates(url: String,success:(StatesResponse)->Unit, error:(String)->Unit){

        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        Log.d("COUNTRIES", "token: ${TOKEN}")
        CoroutineScope(Dispatchers.IO + coroutineException).launch {

            val response =
                service.getStates() //TODO
            if (response.isSuccessful) {
                try {
                    CoroutineScope(Dispatchers.Main).launch {
                    success(response.body()!!)}
                } catch (e: Exception) {
                    CoroutineScope(Dispatchers.Main).launch {
                    error(e.message!!)}
                }
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                error(response.message())}
            }
        }
    }




    fun submitForm1(form1Body: Form1Body,success:(Form1Response)->Unit, error:(String)->Unit){



        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        CoroutineScope(Dispatchers.IO + coroutineException).launch {

            Log.d("LOGINS", "CLICKED- $TOKEN")

            val response = service.form1(form1Body)  //TODO("make authorization dynamic")

            Log.d("LOGINS", "RESPONSE: $response")
            if (response.isSuccessful) {
                try {  CoroutineScope(Dispatchers.Main).launch {
                    success(response.body()!!)}
                    Log.d("LOGINS", "RESPONSE BODY: ${response.body()!!}")
                } catch (e: Exception) {
                    Log.d("LOGINS", "EXCEPTION: " + e.message.toString())
                    CoroutineScope(Dispatchers.Main).launch {
                        CoroutineScope(Dispatchers.Main).launch {
                        error(e.message.toString())}
                    }
                }
            } else {
                Log.d("LOGINS", "ERROR: ${response}")
                CoroutineScope(Dispatchers.Main).launch {
                error(response.message())}
            }


        }




    }


    fun submitForm2(form2Body: Form2Body, success:(Form2Response)->Unit, error:(String)->Unit){


        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        CoroutineScope(Dispatchers.IO + coroutineException).launch {

            Log.d("LOGINS", "CLICKED")

            val response = service.form2(form2Body)  //TODO("make authorization dynamic")

            Log.d("LOGINS", "RESPONSE: $response")
            if (response.isSuccessful) {
                try {
                    Log.d("LOGINS", "RESPONSE BODY: ${response.body()!!}")
                    CoroutineScope(Dispatchers.Main).launch {
                    success(response.body()!!)
                    }
                } catch (e: Exception) {
                    Log.d("LOGINS", "EXCEPTION: " + e.message.toString())
                    CoroutineScope(Dispatchers.Main).launch {
                        CoroutineScope(Dispatchers.Main).launch {
                        error(e.message.toString())}
                    }
                }
            } else {
                Log.d("LOGINS", "ERROR: ${response}")
                CoroutineScope(Dispatchers.Main).launch {
                error(response.message())}
            }


        }


    }



    fun submitForm3(form3Body: Form3Body,success:(Form3Response)->Unit, error:(String)->Unit){


        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        CoroutineScope(Dispatchers.IO + coroutineException).launch {

            Log.d("LOGINS", "CLICKED")

            val response = service.form3(form3Body)  //TODO("make authorization dynamic")

            Log.d("LOGINS", "RESPONSE: $response")
            if (response.isSuccessful) {
                try {
                    Log.d("LOGINS", "RESPONSE BODY: ${response.body()!!}")
                    CoroutineScope(Dispatchers.Main).launch {
                        success(response.body()!!)
                    }
                } catch (e: Exception) {
                    Log.d("LOGINS", "EXCEPTION: " + e.message.toString())
                    CoroutineScope(Dispatchers.Main).launch {
                        error(e.message.toString())
                    }
                }
            } else {
                Log.d("LOGINS", "ERROR: ${response}")
                CoroutineScope(Dispatchers.Main).launch {
                error(response.message())}
            }


        }


    }



    fun submitForm4(form4Body: Form4Body,success:(Form4Response)->Unit, error:(String)->Unit){

        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        CoroutineScope(Dispatchers.IO + coroutineException).launch {

            Log.d("LOGINS", "CLICKED")

            val response = service.form4(form4Body)  //TODO("make authorization dynamic")

            Log.d("LOGINS", "RESPONSE: $response")
            if (response.isSuccessful) {
                try {
                    CoroutineScope(Dispatchers.Main).launch {
                    success(response.body()!!)}
                    Log.d("LOGINS", "RESPONSE BODY: ${response.body()!!}")
                } catch (e: Exception) {
                    Log.d("LOGINS", "EXCEPTION: " + e.message.toString())
                    CoroutineScope(Dispatchers.Main).launch {
                        error(e.message.toString())
                    }
                }
            } else {
                Log.d("LOGINS", "ERROR: ${response}")
                CoroutineScope(Dispatchers.Main).launch {
                error(response.message())}
            }


        }


    }


    fun submitForm5(form5Body: Form5Body,success:(Form5Response)->Unit, error:(String)->Unit){


        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        CoroutineScope(Dispatchers.IO + coroutineException).launch {

            Log.d("LOGINS", "CLICKED")

            val response = service.form5(form5Body)  //TODO("make authorization dynamic")


            Log.d("LOGINS", "RESPONSE: $response")
            if (response.isSuccessful) {
                try {
                    CoroutineScope(Dispatchers.Main).launch {
                        success(response.body()!!)
                    }
                    Log.d("LOGINS", "RESPONSE BODY: ${response.body()!!}")
                } catch (e: Exception) {
                    Log.d("LOGINS", "EXCEPTION: " + e.message.toString())
                    CoroutineScope(Dispatchers.Main).launch {
                        error(e.message.toString())
                    }
                }
            } else {
                Log.d("LOGINS", "ERROR: ${response}")
                CoroutineScope(Dispatchers.Main).launch {
                    error(response.message())
                }
            }


        }


    }


    suspend fun submitForm6(url: String,form6Body: Form6Body,success:(Form6Response)->Unit, error:(String)->Unit){


        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        CoroutineScope(Dispatchers.IO + coroutineException).launch {

            Log.d("LOGINS", "CLICKED")

            val response = service.form6(form6Body)  //TODO("make authorization dynamic")

            Log.d("LOGINS", "RESPONSE: $response")
            if (response.isSuccessful) {
                try {
                    CoroutineScope(Dispatchers.Main).launch {
                        success(response.body()!!)
                    }
                    Log.d("LOGINS", "RESPONSE BODY: ${response.body()!!}")
                } catch (e: Exception) {
                    Log.d("LOGINS", "EXCEPTION: " + e.message.toString())
                    CoroutineScope(Dispatchers.Main).launch {
                        error(e.message.toString())
                    }
                }
            } else {
                Log.d("LOGINS", "ERROR: ${response}")
                CoroutineScope(Dispatchers.Main).launch {
                error(response.message())}
            }


        }


    }







}
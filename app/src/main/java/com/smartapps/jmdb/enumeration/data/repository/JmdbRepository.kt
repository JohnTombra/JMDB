package com.smartapps.jmdb.enumeration.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import com.google.gson.Gson
import com.novaapps.jmdb.data.model.CountriesResponse
import com.novaapps.jmdb.data.model.Form1Body
import com.novaapps.jmdb.data.model.Form1Response
import com.novaapps.jmdb.data.model.Form2Body
import com.novaapps.jmdb.data.model.Form2Response
import com.novaapps.jmdb.data.model.Form3Body
import com.novaapps.jmdb.data.model.Form3Response
import com.novaapps.jmdb.data.model.Form4Body
import com.novaapps.jmdb.data.model.Form4Response
import com.novaapps.jmdb.data.model.Form5Body
import com.novaapps.jmdb.data.model.Form5Response
import com.novaapps.jmdb.data.model.Form6Body
import com.novaapps.jmdb.data.model.Form6Response
import com.novaapps.jmdb.data.model.IdentificationResponse
import com.novaapps.jmdb.data.model.LandPurposeResponse
import com.novaapps.jmdb.data.model.LandUseResponse
import com.novaapps.jmdb.data.model.LgasResponse
import com.novaapps.jmdb.data.model.StatesResponse
import com.novaapps.jmdb.data.model.TinVerificationResponse
import com.smartapps.jmdb.data.model.AssessmentItemBodyNew
import com.smartapps.jmdb.data.model.IdentityType
import com.smartapps.jmdb.data.model.LandPurpose
import com.smartapps.jmdb.data.model.LandUse
import com.smartapps.jmdb.data.model.LandUseResponseNew
import com.smartapps.jmdb.enumeration.registrationdata.model.LoginBody
import com.smartapps.jmdb.enumeration.registrationdata.model.LoginResponse
import com.smartapps.jmdb.enumeration.data.local.jmdb.database.JmdbMyRoomDatabase
import com.smartapps.jmdb.enumeration.data.network.jmdb.JmdbApiService
import com.smartapps.jmdb.enumeration.data.network.jmdb.JmdbMyInterceptor
import com.smartapps.jmdb.enumeration.model.jmdb.AccessmentItemsResponse
import com.smartapps.jmdb.enumeration.model.jmdb.Data
import com.smartapps.jmdb.enumeration.model.jmdb.JmdbBuildingPostBody
import com.smartapps.jmdb.enumeration.model.jmdb.Street
import com.smartapps.jmdb.enumeration.model.jmdb.StreetsResponse
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


var TOKENN = ""

var OWNER_EMAIL = ""
var  OWNER_NUMBER = ""
var  OWNER_NUMBER2 = ""
var OWNER_NAME = ""
var OWNER_MIDDLE_NAME = ""
var OWNER_SURNAME = ""
var OWNER_TITLE = ""
var TIN = "Not set"


var USERNAME = ""
var PASSWORDD = ""

class JmdbRepository(val context: Context) {



    var baseUrl = "https://plateaujmdb.ng/"

    companion object {


        var states: StatesResponse? = null
        var countries: CountriesResponse? = null
        var lgas: LgasResponse? = null
        var identifications: IdentificationResponse? = null
        var landUses: LandUseResponse? = null
        var landPurposes: LandPurposeResponse? = null
        var tinVerificationResponse: TinVerificationResponse? = null



        var IMAGE_1 = ""
        var IMAGE_2 = ""
        var IMAGE_3 = ""
        var IMAGE_4 = ""
        var IMAGE_5 = ""
        var IMAGE_6 = ""
        var IMAGE_7 = ""
        var IMAGE_8 = ""
        var IMAGE_9 = ""
        var IMAGE_10 = ""
        var IMAGE_11 = ""


    }





    val localSet: SharedPreferences.Editor =
        context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).edit()
    val localGet: SharedPreferences =
        context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)



    val client2 = OkHttpClient.Builder()
        .addInterceptor(JmdbMyInterceptor())
        .connectTimeout(210, TimeUnit.SECONDS)
        .readTimeout(210, TimeUnit.SECONDS)
        .writeTimeout(210, TimeUnit.SECONDS)
        .build()


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client2)
            .addConverterFactory(GsonConverterFactory.create()).build()}


        private val service: JmdbApiService by lazy {   retrofit.create(JmdbApiService::class.java) }



    val room = Room.databaseBuilder(context, JmdbMyRoomDatabase::class.java, "my-room_db").build()



    suspend fun getLgas(): List<com.smartapps.jmdb.enumeration.model.jmdb.Lga> {
        return withContext(Dispatchers.IO) {
            room.lgaDao().getAll()
        }
    }

    suspend fun getLandUses(): List<LandUse> {
        return withContext(Dispatchers.IO) {
            room.landUseDao().getAll()
        }
    }

    suspend fun getLandPurposes(): List<LandPurpose> {
        return withContext(Dispatchers.IO) {
            room.landPurposeDao().getAll()
        }
    }

    suspend fun getAreas(lgaId: String): List<com.smartapps.jmdb.enumeration.model.jmdb.Area> {
        return withContext(Dispatchers.IO) {
            room.areaDao().getAll().filter { it.lga_id.toString() == lgaId }
        }
    }

    suspend fun getStreets2(): List<com.smartapps.jmdb.enumeration.model.jmdb.Street> {

        return withContext(Dispatchers.IO) {
            val STREETS = room.streetDao().getAll()
            Log.d("MYSTREETS", STREETS.toString())
            STREETS
        }
    }

    suspend fun getStreets(areaId: String): List<com.smartapps.jmdb.enumeration.model.jmdb.Street> {

        return withContext(Dispatchers.IO) {
          val STREETS = room.streetDao().getAll()
            Log.d("MYSTREETS", STREETS.toString() )
            STREETS.filter { it.area_id == areaId } }
        }

//        return withContext(Dispatchers.IO) {
//            if(areaId == "0"){
//                listOf(Street(1618,"Atiku Street Rayfield Jos","null",0,"1"),Street(1619,"Peace drive way","null",0,"1"))//TODO(REPLACE WITH LOCAL)
//            }else{
//
//            val all = listOf(Street(1618,"Atiku Street Rayfield Jos","null",0,"1"),Street(1619,"Peace drive way","null",0,"1"))//TODO(REPLACE WITH LOCAL)
//            all.filter { it.area_id == areaId } }
//      }
//    }


    suspend fun getBuildingCategories(): List<com.smartapps.jmdb.enumeration.model.jmdb.BuildingCategory> {
        return withContext(Dispatchers.IO) {
            room.buildingCategoryDao().getAll()
        }
    }


    suspend fun getBuildingTypes(): List<com.smartapps.jmdb.enumeration.model.jmdb.BuildingType> {
        return withContext(Dispatchers.IO) {
            room.buildingTypeDao().getAll()
        }
    }



    suspend fun getRevenueItems(): List<com.smartapps.jmdb.data.model.Result> {
        return withContext(Dispatchers.IO) {
            room.revenueItemDao().getAll()
        }
    }

    suspend fun getIdentificationItems(): List<IdentityType> {
        return withContext(Dispatchers.IO) {
            room.identityTypeDao().getAll()
        }
    }

    suspend fun getLandPurposeItems(): List<LandPurpose> {
        return withContext(Dispatchers.IO) {
            room.landPurposeDao().getAll()
        }
    }



    var buildings = listOf<com.smartapps.jmdb.enumeration.model.jmdb.Data>()
    fun searchBuildings(query: String, callback: (List<Data>) -> Unit) {

        if (query.isEmpty()) {
            callback(buildings.reversed())
        } else {
            callback(buildings.filter {
                it.building_number.lowercase()
                    .contains(query.lowercase()) || it.building_name.lowercase()
                    .contains(query.lowercase())
            })
        }
    }

    fun getSyncableBuildingsAndTotal(count: (Int, Int) -> Unit) {

        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        CoroutineScope(Dispatchers.IO + coroutineException).launch {
            val all = room.buildingDao().getAll()
            val syncable = all.filter {
                it.status == 1
            }
            Log.d("REPOSITORY", "Syncable: ${syncable.size}")
            count(all.size,syncable.size)
        }

    }


    fun validatePhone(phone: String,success:()->Unit, error: (String) -> Unit){


        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        Log.d("TINNER", "CLICKED: ${TOKENN}")
//        Log.d("TINNER", "CLICKED2: ${com.novaapps.jmdb.data.repository.TOKEN}")
        CoroutineScope(Dispatchers.IO + coroutineException).launch {

            val response =
                service.vPhone(phone)  //TODO
            if (response.isSuccessful) {
                try {
                    Log.d("TINNER", "TRYING")
                    CoroutineScope(Dispatchers.Main).launch {
                        val resp = response.body()!!

//
//
//
                        OWNER_NUMBER = resp.data.phoneNumber
                        OWNER_NAME = resp.data.first_name

                        if (resp.data.middle_name.isEmpty() || resp.data.middle_name == null){
                        //    OWNER_MIDDLE_NAME = "Not set"
                        }else{
                         //   OWNER_MIDDLE_NAME = resp.data.middle_name
                        }

                        OWNER_SURNAME = resp.data.surname
                        try{ OWNER_TITLE = resp.data.title.toString() }catch (e: Exception){/*OWNER_TITLE ="Mr/Mrs"*/}


                        TIN = resp.data.tin

                        success()
                        Log.d("TINNER", "SUCCESS ${resp}")
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
        }


    }



    fun validateTin(tin: String,success:()->Unit, error: (String) -> Unit){


        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

       Log.d("TINNER", "CLICKED: ${TOKENN}")
//        Log.d("TINNER", "CLICKED2: ${com.novaapps.jmdb.data.repository.TOKEN}")
        CoroutineScope(Dispatchers.IO + coroutineException).launch {

            val response =
                service.abcTin(tin)  //TODO
            if (response.isSuccessful) {
                try {
                    Log.d("TINNER", "TRYING")
                    CoroutineScope(Dispatchers.Main).launch {
                        val resp = response.body()!!


                        OWNER_NUMBER = resp.phoneNumber
                        OWNER_NAME = resp.first_name

                        if (resp.middle_name.trim().replace(" ","").isNotEmpty() && resp.middle_name != null){
                            OWNER_MIDDLE_NAME = resp.middle_name
                        }

                        OWNER_SURNAME = resp.surname
                        try{ OWNER_TITLE = resp.title.toString() }catch (e: Exception){"Mr/Mrs"}
                        TIN = resp.tin

                        success()
                        Log.d("TINNER", "SUCCESS ${resp}")
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
        }


    }







    suspend fun initializeData(success: () -> Unit, error: (String) -> Unit){


//
//        CoroutineScope(Dispatchers.IO).launch {
//            Log.d("TINTEST","INITIAL START")
//
//            validateTin("08136759553",{
//                Log.d("TINTEST","FINAL SUCCESS")
//            }){
//                Log.d("TINTEST","FINAL ERROR: ${it}")
//            }
//
//        }




        val stateId = "32"

        val lgas = service.getLgas(stateId)

        Log.d("Streets","${lgas.body()!!.data}")
        if (lgas.isSuccessful) {


            lgas.body()!!.data.forEach {
                room.lgaDao().insert(it)

                val areas = service.getAreas(it.lga_id.toString())

                Log.d("MYAREAS","My Areas: ${areas.body()!!}")


                var streets: Response<StreetsResponse>? = null

                areas.body()!!.data.forEach {

                    room.areaDao().insert(it)

                    streets = service.getStreets(it.id.toString())

                    streets!!.body()!!.data.forEach{
                        room.streetDao().insert(it)
                    }


                }

               Log.d("Streets!!!","MY-STREETS${streets}")

            }


            service.getBuildingCategories().body()!!.result.forEach {
                room.buildingCategoryDao().insert(it)
            }

            service.getBuildingTypes().body()!!.result.forEach {
                room.buildingTypeDao().insert(it)
            }

//
//
//





                for(x in 1..30) {

                    getRevenueItems(x, {

                    }) {

                    }

                }
                //    try {

//                        Log.d("MYREVENUEITEMS","INSIDE")
//
//                        val body = service.getAssessmentItems(2)//
//
//                        if (body.isSuccessful) {
//                            body.body()!!.data.forEach {
//                                  Log.d("MYREVENUEITEMS","INSERTED $it")
//                            //    room.revenueItemDao().insert(it)
//                            }
//                        }else{
//                            Log.d("MYREVENUEITEMS","Api Error: " + body.message())
//                        }
//
//                    }catch (e: Exception){
//                       Log.d("MYREVENUEITEMS","CATCHX: $.....${e.message}")
//                        error(e.message.toString())
//                    }

              //  }













            service.getIdentifications().body()!!.data.forEach {
                room.identityTypeDao().insert(it)
            }

           service.getPurpose().body()!!.data.forEach {
               room.landPurposeDao().insert(it)
           }


            getLandUse({

            }){
                //
            }


            CoroutineScope(Dispatchers.Main).launch {
                success()
            }
        }else{
            Log.d("Categories-error","${lgas.message()}")
            CoroutineScope(Dispatchers.Main).launch {
                error(lgas.message())
            }

        }

        //If successful









    }


/*
    fun getLgas(success: () -> Unit, error: (String) -> Unit){
//        service.getCountries()
//        service.getStates()
//        service.getLgas("32")

        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        CoroutineScope(Dispatchers.IO + coroutineException).launch {
            Log.d("MPLUGINS", "CLICKED")
            val response = service.getLgasx("32")  //TODO("make authorization dynamic")
            Log.d("MPLUGINS", "RESPONSE: $response")

            if (response.isSuccessful) {
                try {
                    Log.d("MPLUGINS", "RESPONSE BODY: ${response.body()!!}")
                    lgas = response.body()!!
                    success()
                } catch (e: Exception) {
                    error(e.message!!)
                    Log.d("MPLUGINS", "EXCEPTION: " + e.message.toString())
                }
            } else {
                error(response.message())
                Log.d("MPLUGINS", "ERROR: ${response}")
            }
        }



    }*/

/*    fun getStates(success: () -> Unit, error: (String) -> Unit){
//        service.getCountries()
//        service.getStates()
//        service.getLgas("32")

        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        CoroutineScope(Dispatchers.IO + coroutineException).launch {
            Log.d("MPLUGINS", "CLICKED")
            val response = service.getStatesx()  //TODO("make authorization dynamic")
            Log.d("MPLUGINS", "RESPONSE: $response")

            if (response.isSuccessful) {
                try {
                    Log.d("MPLUGINS", "RESPONSE BODY: ${response.body()!!}")
                    states = response.body()!!
                    success()
                } catch (e: Exception) {
                    error(e.message!!)
                    Log.d("MPLUGINS", "EXCEPTION: " + e.message.toString())
                }
            } else {
                error(response.message())
                Log.d("MPLUGINS", "ERROR: ${response}")
            }
        }
    }*/


  /*  fun getIdentifications(success: () -> Unit, error: (String) -> Unit){
//        service.getCountries()
//        service.getStates()
//        service.getLgas("32")

        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        CoroutineScope(Dispatchers.IO + coroutineException).launch {
            Log.d("MPLUGINS", "CLICKED")
            val response = service.getIdentifications()  //TODO("make authorization dynamic")
            Log.d("MPLUGINS", "RESPONSE: $response")

            if (response.isSuccessful) {
                try {
                    Log.d("MPLUGINS", "RESPONSE BODY: ${response.body()!!}")
                    identifications = response.body()!!
                    success()
                } catch (e: Exception) {
                    error(e.message!!)
                    Log.d("MPLUGINS", "EXCEPTION: " + e.message.toString())
                }
            } else {
                error(response.message())
                Log.d("MPLUGINS", "ERROR: ${response}")
            }
        }
    }
*/

  /*  fun getCountries(success: () -> Unit, error: (String) -> Unit){
//        service.getCountries()
//        service.getStates()
//        service.getLgas("32")

        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        CoroutineScope(Dispatchers.IO + coroutineException).launch {
            Log.d("MPLUGINS", "CLICKED")
            val response = service.getCountries()  //TODO("make authorization dynamic")
            Log.d("MPLUGINS", "RESPONSE: $response")

            if (response.isSuccessful) {
                try {
                    Log.d("MPLUGINS", "RESPONSE BODY: ${response.body()!!}")
                    countries = response.body()!!
                    success()
                } catch (e: Exception) {
                    error(e.message!!)
                    Log.d("MPLUGINS", "EXCEPTION: " + e.message.toString())
                }
            } else {
                error(response.message())
                Log.d("MPLUGINS", "ERROR: ${response}")
            }
        }



    }*/


    var okHttpClient: OkHttpClient = OkHttpClient().newBuilder().build()





    fun getRevenueItems(page: Int,success: () -> Unit, error: (String) -> Unit){
//        service.getCountries()
//        service.getStates()
//        service.getLgas("32")

//        val coroutineException = CoroutineExceptionHandler { _, throwable ->
//            throwable.printStackTrace()
//        }

        Log.d("MYREVEX","INSIDE")

        CoroutineScope(Dispatchers.IO).launch {



            val mediaType: MediaType = "text/plain".toMediaTypeOrNull()!!
            val request = Request.Builder()
                .url(baseUrl + "api/assessment_items?page=$page")//TODO TRY BR
                .addHeader("Authorization","Bearer $TOKENN")
                .build()

            val response: okhttp3.Response = okHttpClient.newCall(request).execute() //...
            val gson = Gson()

            try{

                Log.d("MYREVEX","TRYING")
                val body = response.body!!.string()
                Log.d("MYREVEX",body)
                val respObj = gson.fromJson(body, AssessmentItemBodyNew::class.java)


                Log.d("MYREVEOX",respObj.toString())

                respObj.data.result.forEach {

                    try{
                        Log.d("MYLOGGGG1-R","TRYING")
                        room.revenueItemDao().insert(it)
                    }catch(e: Exception){
                        Log.d("MYLOGGGG1-R","Exception" + e.message.toString())
                    }

                }

            }catch(e: Exception){
                Log.d("MYREVEX","Exception: ${e.message}")
            }



//
//
//            Log.d("MPLUGINS", "CLICKED")
//            val response = service.getLandUse()  //TODO("make authorization dynamic")
//            Log.d("MPLUGINS", "RESPONSE: $response")
//
//            response.body()!!.data.forEach {
//                room.landUseDao().insert(it)
//            }
//
//            if (response.isSuccessful) {
//                try {
//                    Log.d("MPLUGINS", "RESPONSE BODY: ${response.body()!!}")
//                    landUses = response.body()!!
//                    success()
//                } catch (e: Exception) {
//                    error(e.message!!)
//                    Log.d("MPLUGINS", "EXCEPTION: " + e.message.toString())
//                }
//            } else {
//                error(response.message())
//                Log.d("MPLUGINS", "ERROR: ${response}")
//            }
        }



    }



    fun getLandUse(success: () -> Unit, error: (String) -> Unit){
//        service.getCountries()
//        service.getStates()
//        service.getLgas("32")

//        val coroutineException = CoroutineExceptionHandler { _, throwable ->
//            throwable.printStackTrace()
//        }

        CoroutineScope(Dispatchers.IO).launch {



            val mediaType: MediaType = "text/plain".toMediaTypeOrNull()!!
            val request = Request.Builder()
                .url(baseUrl + "api/fetch_land_use")//TODO TRY BR
                .addHeader("Authorization","Bearer $TOKENN")
                .build()

            val response: okhttp3.Response = okHttpClient.newCall(request).execute() //...
            val gson = Gson()

            try{
                val body = response.body!!.string()
                Log.d("MYLOGGGG",body)
                val respObj = gson.fromJson(body, LandUseResponseNew::class.java)
                Log.d("MYLOGGGG","Done")
                Log.d("MYLOGGGG",respObj.toString())

                respObj.data.forEach {


                    try{
                        Log.d("MYLOGGGG1","TRYING")
                      room.landUseDao().insert(it)
                    }catch(e: Exception){
                        Log.d("MYLOGGGG1",e.message.toString())
                    }

            }

            }catch(e: Exception){
                Log.d("MYLOGGGG","Exception: ${e.message}")
            }



//
//
//            Log.d("MPLUGINS", "CLICKED")
//            val response = service.getLandUse()  //TODO("make authorization dynamic")
//            Log.d("MPLUGINS", "RESPONSE: $response")
//
//            response.body()!!.data.forEach {
//                room.landUseDao().insert(it)
//            }
//
//            if (response.isSuccessful) {
//                try {
//                    Log.d("MPLUGINS", "RESPONSE BODY: ${response.body()!!}")
//                    landUses = response.body()!!
//                    success()
//                } catch (e: Exception) {
//                    error(e.message!!)
//                    Log.d("MPLUGINS", "EXCEPTION: " + e.message.toString())
//                }
//            } else {
//                error(response.message())
//                Log.d("MPLUGINS", "ERROR: ${response}")
//            }
        }



    }







//
//    fun getLandPurpose(success: () -> Unit, error: (String) -> Unit){
////        service.getCountries()
////        service.getStates()
////        service.getLgas("32")
//
//        val coroutineException = CoroutineExceptionHandler { _, throwable ->
//            throwable.printStackTrace()
//        }
//        CoroutineScope(Dispatchers.IO + coroutineException).launch {
//            Log.d("MPLUGINS", "CLICKED")
//            val response = service.getPurpose()  //TODO("make authorization dynamic")
//            Log.d("MPLUGINS", "RESPONSE: $response")
//
//
//            response.body()!!.data.forEach {
//                room.landPurposeDao().insert(it)
//            }
//
//
//            if (response.isSuccessful) {
//                try {
//                    Log.d("MPLUGINS", "RESPONSE BODY: ${response.body()!!}")
//                    landPurposes = response.body()!!
//                    success()
//                } catch (e: Exception) {
//                    error(e.message!!)
//                    Log.d("MPLUGINS", "EXCEPTION: " + e.message.toString())
//                }
//            } else {
//                error(response.message())
//                Log.d("MPLUGINS", "ERROR: ${response}")
//            }
//        }
//
//
//
//    }
//



    fun checkBuildingSyncStatus(id: String,callback: (Boolean) -> Unit){
        callback(false)
//        CoroutineScope(Dispatchers.IO).launch {
//            if(room.buildingDao().getById(id).status == 1){
//                callback(false)
//            }else{
//                callback(true)
//            }
//        }
    }
    fun printData(){

        CoroutineScope(Dispatchers.IO).launch {

            Log.d(".......","\n......\n......\n.......\n")
            Log.d("Streets","${room.streetDao().getAll()}")
            Log.d(".......","\n......\n......\n.......\n")
            Log.d("Lga","${room.lgaDao().getAll()}")
            Log.d(".......","\n......\n......\n.......\n")
            Log.d("Area","${room.areaDao().getAll()}")
            Log.d(".......","\n......\n......\n.......\n")
            Log.d("Categories","${room.buildingCategoryDao().getAll()}")

            Log.d(".......","\n......\n......\n.......\n")
            Log.d("Types","${room.buildingTypeDao().getAll()}")

            Log.d(".......","\n......\n......\n.......\n")
            Log.d("RevenueItems","${room.revenueItemDao().getAll()}")






        }

    }




    fun login(loginBody: LoginBody, success:(LoginResponse?)->Unit, error:(String)->Unit){




        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        CoroutineScope(Dispatchers.IO + coroutineException).launch {





            Log.d("LOGINS", "CLICKED")

            val response = service.login(loginBody)  //TODO("make authorization dynamic")

            Log.d("LOGINS", "RESPONSE: $response")

            if (response.isSuccessful) {
                try { ////>>>>>>


                    Log.d("LOGINS", "RESPONSE BODY: ${response.body()!!}")
                    TOKENN = response.body()!!.token
                    if(fetchAuthToken()!!.isEmpty()){
                        //sync data
                        Log.d("LOGINTYPE", "NEW")
                    initializeData({
                        saveAuthToken(TOKENN)
                        CoroutineScope(Dispatchers.IO).launch {
//                            Log.d(".......", "\n......\n......\n.......\n")
//                            Log.d("Streets", "${room.streetDao().getAll()}")
//                            Log.d(".......", "\n......\n......\n.......\n")
//                            Log.d("Lga", "${room.lgaDao().getAll()}")
//                            Log.d(".......", "\n......\n......\n.......\n")
//                            Log.d("Area", "${room.areaDao().getAll()}")
//                            Log.d(".......", "\n......\n......\n.......\n")
//                            Log.d("Categories", "${room.buildingCategoryDao().getAll()}")
                        }


                        Log.d(".......", "\n......\n......\n.......\n")

                        success(response.body()!!)


                    }) {
                        CoroutineScope(Dispatchers.Main).launch {
                            error(it)
                        }
                    }

                }else{
                        Log.d("LOGINTYPE", "CONTINUE")
                        success(response.body()!!)
                }


                } catch (e: Exception) {
                    Log.d("LOGINS", "EXCEPTION LOGIN: " + e.message.toString())
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




    fun getAllBuildingsLocally(
        loading: () -> Unit,
        success: (List<Data>) -> Unit,
        error: (String) -> Unit
    ) {
        try {
            //TODO(collect as FLOW)
            CoroutineScope(Dispatchers.IO).launch {


                val all = room.buildingDao().getAll()
                buildings = all
                success(all)
                //     Log.d("TRACER","DATA" + room.buildingDao().getAll().toString())
            }

        } catch (e: Exception) {
            Log.d("TRACER", "ERROR" + e.message.toString())
            error(e.message.toString())
        }
    }


    fun loadBuildingLocally(id: String, success: (Data) -> Unit, error: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TRACKER", "$id")
            success(room.buildingDao().getById(id))
        }
    }



    fun createBuildingLocally(
        data: Data,
        success: () -> Unit,
        error: (String) -> Unit
    ) {



        Log.d("TRACKER", "${data.copy(building_image = "...")}")
        try {
            CoroutineScope(Dispatchers.IO).launch {
                room.buildingDao().insert(data)
            }
            success()
        } catch (e: Exception) {
            error(e.message.toString())
        }
    }




    fun getSyncables(count: (Int) -> Unit) {

        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        CoroutineScope(Dispatchers.IO + coroutineException).launch {
            val syncable = room.buildingDao().getAll().filter {
                it.status == 1
            }
            Log.d("REPOSITORY", "Syncable: ${syncable.size}")
            count(syncable.size)
        }

    }



    fun syncAllBuildings( error:(String)->Unit,success: (Int)->Unit){

        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        CoroutineScope(Dispatchers.IO + coroutineException).launch {


            val syncable = room.buildingDao().getAll().filter {
                it.status == 1
            }

            Log.d("REPOSITORY", "Syncable: ${syncable.size}")


            if (syncable.isEmpty()) {
                //return error. nothing to sync
                Log.d("REPOSITORY", "NOTHING TO SYNC")
                return@launch
            }



            val bulk = JmdbBuildingPostBody(
                data = syncable,
                page = 1,
                pageLength = syncable.size.toString(),
                total = syncable.size,
            )

            val response = service.postBuildings(bulk)


            if (response.isSuccessful) {
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("REPOSITORY", "RESPONSE SUCCESS S ${response.body()}")

                    //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                    //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                     //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                    //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                    //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                    CoroutineScope(Dispatchers.IO).launch {

                        success(response.body()!!.syncIds.size)

                       syncable.forEach { building ->
                            room.buildingDao().delete(building.copy(status = 0))
                            //     room.buildingDao().delete(building_num)
                        }}
//                    response.body()!!.syncIds.forEach { building_num ->
//                        room.buildingDao().insert(syncable.filter { it.building_number == building_num }[0].copy(status = 0))
//                        //     room.buildingDao().delete(building_num)
//                    }}

                }
            } else {
                error(response.message())

                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("REPOSITORY", "RESPONSE ERROR S $response")
                }
            }


        }






    }


    fun syncSingleBuildings( data: Data, error:(String)->Unit,success: (Int)->Unit){

        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        CoroutineScope(Dispatchers.IO + coroutineException).launch {


            Log.d("REPOSITORY-MYDATA", "MYDATA-${data.copy(building_image = "data:image/jpeg;base64,")}")


            val bulk = JmdbBuildingPostBody(
                data = listOf(data),
                page = 1,
                pageLength = "1",
                total = 1,
            )

            val response = service.postBuildings(bulk)


            if (response.isSuccessful) {
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("REPOSITORY", "RESPONSE SUCCESS S ${response.body()}")

                    //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                    //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                    //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                    //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                    //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                    CoroutineScope(Dispatchers.IO).launch {


                        room.buildingDao().delete(data.copy(status = 0))

                        success(response.body()!!.syncIds.size)

                        //save locally

                    }

                }
            } else {
                error(response.message())

                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("REPOSITORY", "RESPONSE ERROR S $response")
                }
            }


        }






    }



//
//
//    fun syncAllBuildings(error: (String) -> Unit, success: () -> Unit) {
//
//        val coroutineException = CoroutineExceptionHandler { _, throwable ->
//            throwable.printStackTrace()
//        }
//        CoroutineScope(Dispatchers.IO + coroutineException).launch {
//
//
//            val syncable = room.buildingDao().getAll().filter {
//                it.status == 1
//            }
//
//            Log.d("REPOSITORY", "Syncable: ${syncable.size}")
//
//
//            if (syncable.isEmpty()) {
//                //return error. nothing to sync
//                Log.d("REPOSITORY", "NOTHING TO SYNC")
//                return@launch
//            }
//
//
////            syncable.forEach {
////                room.buildingDao().insert(it.copy(status = 0))
////            }
////
////            if(true) return@launch
//
//            //  syncable.filter { it.building_number == it.building_number }[0].copy(status = 0))
//
//
//            val bulk = BuildingBulk(
//                data = syncable.map {
//                    DataXXX(
//                        apartment_type = it.apartment_type,
//                        building_category_id = it.building_category_id,
//                        building_id = it.building_id,
//                        building_image = it.building_image,
//                        building_name = it.building_name,
//                        building_number = it.building_number,
//                        id = it.id,
//                        latitude = it.latitude,
//                        lga = it.lga,
//                        longitude = it.longitude,
//                        no_of_apartments = it.no_of_apartments,
//                        owner_email = it.owner_email,
//                        owner_mobile_no = it.owner_mobile_no,
//                        owner_name = it.owner_name,
//                        state_id = it.state_id,
//                        status = 0,
//                        street_id = it.status,
//                        taxitem = it.taxitem,
//                        ward = it.ward
//                    )
//                },
//                page = 1,
//                pageLength = syncable.size.toString(),
//                total = syncable.size,
//            )
//
//
//            Log.d("REPOSITORY", "NETWORK CALLED")
//
//
//            val response = service.syncBulkBuildings(bulk)
//
//
//
//            if (response.isSuccessful) {
//                CoroutineScope(Dispatchers.Main).launch {
//                    Log.d("REPOSITORY", "RESPONSE SUCCESS S ${response.body()}")
//
//                    //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
//                    //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
//                    success() //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
//                    //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
//                    //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
//                    CoroutineScope(Dispatchers.IO).launch {
//
//
//                        response.body()!!.syncIds.forEach { building_num ->
//                            room.buildingDao().insert(syncable.filter { it.building_number == building_num }[0].copy(status = 0))
//                            //     room.buildingDao().delete(building_num)
//                        }}
//
//
//
//                }
//            } else {
//                error(response.message())
//
//                CoroutineScope(Dispatchers.Main).launch {
//                    Log.d("REPOSITORY", "RESPONSE ERROR S $response")
//                }
//            }
//
//        }
//
//    }
//





    fun saveAuthToken(token: String) {
        localSet.putString("USER_TOKEN", token)
        localSet.apply()
    }

    fun fetchAuthToken(): String {
        return localGet.getString("USER_TOKEN", "")!!
    }



    fun enrollNew() {
        saveAuthToken("")
        localSet.putString("loggedIn", "false")
        localSet.commit()
    }


    fun logOut() {
        saveAuthToken("")
        localSet.putString("loggedIn", "false")
        localSet.commit()
    }



    fun logIn() {
        localSet.putString("loggedIn", "true")
        localSet.commit()
    }









    fun submitForm1(form1Body: Form1Body, success:(Form1Response)->Unit, error:(String)->Unit){



        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        CoroutineScope(Dispatchers.IO + coroutineException).launch {

            Log.d("LOGINS", "CLICKED- ${com.novaapps.jmdb.data.repository.TOKEN}")

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



    fun submitForm3(form3Body: Form3Body, success:(Form3Response)->Unit, error:(String)->Unit){


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



    fun submitForm4(form4Body: Form4Body, success:(Form4Response)->Unit, error:(String)->Unit){

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


    fun submitForm5(form5Body: Form5Body, success:(Form5Response)->Unit, error:(String)->Unit){


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



    fun uploadDocuments(success: () -> Unit, error: (String) -> Unit){


//        object: CountDownTimer(3000,1000){
//            override fun onTick(millisUntilFinished: Long) {
//
//            }
//
//            override fun onFinish() {
//                success()
//            }
//
//        }.start()


//        {
//            "fileName": "T/oACtt0uK1sADCaxbpcZWkAAxUqpVoAAmtwNV0ABltaCWoABMWrDVAAoToAFIAAaAAAoAAAAAAAIAB/9k=", // base64 url
//            "track_no": "LVZRLK9O",
//            "fileUrl": "Passport",
//        }

//        CoroutineScope(Dispatchers.IO).launch {
//            Log.d("PDFTIME2","START")
//
//            val form6body = Form6Body(fileUrl = "data:application/pdf;base64,$IMAGE_1",fileName = "Copy of the certificate of occupancy", track_no = "LVZRLK9O")//LVZRLK9O
//            submitForm6(form6body,{
//                Log.d("PDFTIME2","")
//                success()
//            }){
//                Log.d("PDFTIME2","$it")
//                error(it)
//            }
//
//        }






        val form6body = Form6Body(fileUrl = "data:application/pdf;base64,$IMAGE_1",fileName = "Copy of the certificate of occupancy", track_no = "LVZRLK9O")//LVZRLK9O
        submitForm6(form6body,{

            Log.d("COMPLETED!!!","1 COMPLETE")

            //1
            val form6body = Form6Body(fileUrl = "data:application/pdf;base64,$IMAGE_2",fileName = "Copy of MLS TP Acknowledge Letter", track_no = "LVZRLK9O")//LVZRLK9O


            submitForm6(form6body,{
                Log.d("COMPLETED!!!","2 COMPLETE")
                //2
                val form6body = Form6Body(fileUrl = "data:application/pdf;base64,$IMAGE_3",fileName = "Copy of Structural Drawing and Details", track_no = "LVZRLK9O")//LVZRLK9O

                submitForm6(form6body,{
                        //3
                    Log.d("COMPLETED!!!","3 COMPLETE")

                    val form6body = Form6Body(fileUrl = "data:application/pdf;base64,$IMAGE_4",fileName = "Copy of Structural Calculations", track_no = "LVZRLK9O")//LVZRLK9O

                    submitForm6(form6body,{
                        //4
                        Log.d("COMPLETED!!!","4 COMPLETE")

                        val form6body = Form6Body(fileUrl = "data:application/pdf;base64,$IMAGE_5",fileName = "Copy of Architectural Drawing and Details", track_no = "LVZRLK9O")//LVZRLK9O

                        submitForm6(form6body,{
                        //5
                            Log.d("COMPLETED!!!","5 COMPLETE")

                            val form6body = Form6Body(fileUrl = "data:application/pdf;base64,$IMAGE_6",fileName = "Copy of Mechanical Electrical Drawings and Details", track_no = "LVZRLK9O")//LVZRLK9O

                            submitForm6(form6body,{
                                    //6
                                Log.d("COMPLETED!!!","6 COMPLETE")

                                val form6body = Form6Body(fileUrl = "data:application/pdf;base64,$IMAGE_7",fileName = "Site Analysis Report", track_no = "LVZRLK9O")//LVZRLK9O

                                submitForm6(form6body,{
                                        //7
                                    Log.d("COMPLETED!!!","7 COMPLETE")

                                    val form6body = Form6Body(fileUrl = "data:application/pdf;base64,$IMAGE_8",fileName = "Copy of EIAR", track_no = "LVZRLK9O")//LVZRLK9O
                                    submitForm6(form6body,{
                                            //8
                                        Log.d("COMPLETED!!!","8 COMPLETE")

                                        val form6body = Form6Body(fileUrl = "data:application/pdf;base64,$IMAGE_9",fileName = "Copy of BLOCK Plan", track_no = "LVZRLK9O")//LVZRLK9O

                                        submitForm6(form6body,{
                                                //9
                                            Log.d("COMPLETED!!!","9 COMPLETE")

                                            val form6body = Form6Body(fileUrl = "data:application/pdf;base64,$IMAGE_10",fileName = "Soil Investigation Report", track_no = "LVZRLK9O")//LVZRLK9O

                                            submitForm6(form6body,{
                                                    //10
                                                Log.d("COMPLETED!!!","10 COMPLETE")

                                                val form6body = Form6Body(fileUrl = "data:application/pdf;base64,$IMAGE_11",fileName = "Copy of Service Approvals", track_no = "LVZRLK9O")//LVZRLK9O

                                                submitForm6(form6body,{

                                                    Log.d("COMPLETED!!!","11 COMPLETE")

                                                        success()
                                                }){
                                                    error(it)
                                                }


                                            }){
                                                error(it)
                                            }

                                        }){
                                            error(it)
                                        }


                                    }){
                                        error(it)
                                    }

                                }){
                                    error(it)
                                }

                            }){
                                error(it)
                            }


                        }){
                            error(it)
                        }


                    }){
                        error(it)
                    }


                }){
                    error(it)
                }


            }){
                error(it)
            }



        }){
            error(it)
        }




    }

    fun submitForm6(form6Body: Form6Body, success:(Form6Response)->Unit, error:(String)->Unit){


        val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        CoroutineScope(Dispatchers.IO + coroutineException).launch {

            Log.d("LOGINS", "CLICKED")

            val response = service.form6(form6Body)  //TODO("make authorization dynamic")

            Log.d("LOGINS", "RESPONSE: $response")
            if (response.isSuccessful) {

                    CoroutineScope(Dispatchers.Main).launch {
                       success(response.body()!!)
                    }
                    Log.d("LOGINS", "RESPONSE BODY: ${response.body()!!}")

            } else {
                Log.d("LOGINS", "ERROR: ${response}")
                CoroutineScope(Dispatchers.Main).launch {
                    error(response.message())}
            }


        }


    }




}
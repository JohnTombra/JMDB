package com.smartapps.jmdb.enumeration.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
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
import com.smartapps.jmdb.enumeration.util.Util
import com.smartapps.jmdb.data.model.AssessmentItemBodyNew
import com.smartapps.jmdb.data.model.IdentityType
import com.smartapps.jmdb.data.model.IndividualTin
import com.smartapps.jmdb.data.model.IndividualTinResponse
import com.smartapps.jmdb.data.model.LandPurpose
import com.smartapps.jmdb.data.model.LandUse
import com.smartapps.jmdb.data.model.LandUseResponseNew
import com.smartapps.jmdb.data.model.LoginError
import com.smartapps.jmdb.data.model.LoginMessage
import com.smartapps.jmdb.data.model.MainTinResponse
import com.smartapps.jmdb.data.model.MyCompany
import com.smartapps.jmdb.data.model.MyCompanyTinResponse
import com.smartapps.jmdb.data.model.Result
import com.smartapps.jmdb.enumeration.registrationdata.model.LoginBody
import com.smartapps.jmdb.enumeration.registrationdata.model.LoginResponse
import com.smartapps.jmdb.enumeration.data.local.jmdb.database.JmdbMyRoomDatabase
import com.smartapps.jmdb.enumeration.data.model.jmdb.Area
import com.smartapps.jmdb.enumeration.data.model.jmdb.AreasResponse
import com.smartapps.jmdb.enumeration.data.model.jmdb.BuildingCategoriesResponse
import com.smartapps.jmdb.enumeration.data.model.jmdb.BuildingCategory
import com.smartapps.jmdb.enumeration.data.model.jmdb.BuildingType
import com.smartapps.jmdb.enumeration.data.model.jmdb.BuildingTypeResponse
import com.smartapps.jmdb.enumeration.data.model.jmdb.Data
import com.smartapps.jmdb.enumeration.data.model.jmdb.DataTagged
import com.smartapps.jmdb.enumeration.data.model.jmdb.JmdbBuildingPostBody
import com.smartapps.jmdb.enumeration.data.model.jmdb.JmdbBuildingPostResponse
import com.smartapps.jmdb.enumeration.data.model.jmdb.Lga
import com.smartapps.jmdb.enumeration.data.model.jmdb.LgaResponse
import com.smartapps.jmdb.enumeration.data.model.jmdb.Street
import com.smartapps.jmdb.enumeration.data.model.jmdb.StreetsResponse
import com.smartapps.jmdb.enumeration.data.model.jmdb.Tag
import com.smartapps.jmdb.enumeration.data.model.jmdb.Time
import com.smartapps.jmdb.enumeration.data.network.jmdb.JmdbApiService
import com.smartapps.jmdb.enumeration.data.network.jmdb.JmdbMyInterceptor
import com.smartapps.jmdb.enumeration.util.Constants
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit



object JmdbRepository {


        var BLOCK_SYNC = false

        var FORM_1: Form1Body? = null
        var FORM_2: Form2Body? = null
        var FORM_3: Form3Body? = null
        var FORM_4: Form4Body? = null
        var FORM_5: Form5Body? = null

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



    var OWNER_NUMBER = ""
    var OWNER_NAME = ""
    var OWNER_MIDDLE_NAME = ""
    var OWNER_SURNAME = ""
    var OWNER_TITLE = ""

    private lateinit var localSet: SharedPreferences.Editor
    private lateinit var localGet: SharedPreferences
    lateinit var room: JmdbMyRoomDatabase


    /*
     val coroutineException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
     }
     */

   var ioDispatcher = CoroutineScope(Dispatchers.IO)
   var mainDispatcher = CoroutineScope(Dispatchers.Main)


    fun initializeSharedPreferences(context: Context){
        localSet = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).edit()
        localGet = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
    }





    fun initilizeRoom(context:Context){
         room = Room.databaseBuilder(context, JmdbMyRoomDatabase::class.java, "my-room_db")
            .fallbackToDestructiveMigration().build()
    }

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest


    fun initializeMap(context: Context){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationRequest =
            LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(100)
                .setFastestInterval(100)
    }

    val client2 by lazy {
         OkHttpClient.Builder()
        .addInterceptor(JmdbMyInterceptor())
        .connectTimeout(210, TimeUnit.SECONDS)
        .readTimeout(210, TimeUnit.SECONDS)
        .writeTimeout(210, TimeUnit.SECONDS)
        .build() }


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .client(client2)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }



    private val service: JmdbApiService by lazy { retrofit.create(JmdbApiService::class.java) }


    var okHttpClient: OkHttpClient =
        OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build()


    val firebase = FirebaseDatabase.getInstance().getReference()


    val mediaType: MediaType = "application/json".toMediaTypeOrNull()!!

    val gson = Gson() //haha....nnn


    fun getLgas(callback: (List<Lga>) -> Unit) {
        ioDispatcher.launch {
            val data = room.lgaDao().getAll()
            mainDispatcher.launch {
                callback(data)
            }
        }
    }

    fun getLandUses(callback: (List<LandUse>) -> Unit) {
        ioDispatcher.launch {
            val data = room.landUseDao().getAll()
            mainDispatcher.launch {
                callback(data)
            }
        }
    }

    fun getAreas(
        lgaId: String,
        callback: (List<Area>) -> Unit
    ) {
        ioDispatcher.launch {
            val data = room.areaDao().getAll()
            mainDispatcher.launch {
                if(lgaId == "0"){
                    callback(data)
                }else{
                    callback(data.filter { it.lga_id.toString() == lgaId })
                }
            }
        }
    }

    fun getStreets2(callback: (List<Street>) -> Unit) {
        ioDispatcher.launch {
            val data = room.streetDao().getAll()
            mainDispatcher.launch {
                callback(data)
            }
        }
    }

    fun getStreets(
        areaId: String,
        callback: (List<Street>) -> Unit
    ) {
        ioDispatcher.launch {
            val data = room.streetDao().getAll()
            mainDispatcher.launch {
                callback(data.filter { it.area_id == areaId })
            }
        }
    }


    fun getBuildingCategories(callback: (List<BuildingCategory>) -> Unit) {
        ioDispatcher.launch {
            val data = room.buildingCategoryDao().getAll()
            mainDispatcher.launch {
                callback(data)
            }
        }
    }


    fun getBuildingTypes(id: Int,callback: (List<BuildingType>) -> Unit) {
        ioDispatcher.launch {
            val data = if(id == 0){
                room.buildingTypeDao().getAll()
            }else{
                room.buildingTypeDao().getAll().filter{ it.category == id }
            }

            mainDispatcher.launch {
                callback(data)
            }
        }
    }


    var revenueItems = listOf<Result>()

    fun getRevenueItems(callback: (List<Result>) -> Unit) {
        ioDispatcher.launch {
            val data = room.revenueItemDao().getAll()
            revenueItems = data
            mainDispatcher.launch {
                callback(data)
            }
        }
    }


    fun searchRevenueItems(query: String, callback: (List<Result>) -> Unit) {
        if (query.isEmpty()) {
            callback(revenueItems)
        } else {
            callback(revenueItems.filter {
                it.assessment_item_name.lowercase().trim().replace(" ", "").replace("  ", "")
                    .contains(query.lowercase().trim().replace(" ", "").replace("  ", ""))
            })
        }
    }


    fun getIdentificationItems(callback: (List<IdentityType>) -> Unit) {
        ioDispatcher.launch {
            val data = room.identityTypeDao().getAll()
            mainDispatcher.launch {
                callback(data)
            }
        }
    }

    fun getLandPurposeItems(callback: (List<LandPurpose>) -> Unit) {
        ioDispatcher.launch {
            val data = room.landPurposeDao().getAll()
            mainDispatcher.launch {
                callback(data)
            }
        }

    }



    var buildings = listOf<Data>()
    fun searchBuildings(query: String, callback: (List<Data>) -> Unit) {
        if (query.isEmpty()) {
            callback(buildings.reversed())
        } else {
            callback(buildings.filter {
                it.building_name.lowercase()
                    .contains(query.lowercase()) || it.tin.lowercase()
                    .contains(query.lowercase()) || it.owner_mobile_no.lowercase()
                    .contains(query.lowercase()) || it.owner_email.lowercase()
                    .contains(query.lowercase())
            })
        }
    }


    fun getSyncableBuildingsAndTotal(count: (Int, Int) -> Unit) {

        ioDispatcher.launch {
            val all = room.buildingDao().getAll()
            val syncable = all.filter {
                it.status == 1
            }
            Log.d("REPOSITORY", "Syncable: ${syncable.size}")
            count(all.size, syncable.size)
        }
    }










    fun _100Tagifier(){ //check every five minute if internet is on... or every 1 minute
        //called when synchronize is called
        //called during initial sync also
        ioDispatcher.launch {
        val current = room.timeDao().getAll().size
        var remainder = 100 - current
            Log.d("MYREVEX", "TAGGIFIR_REMAINDER_C $current")
            Log.d("MYREVEX", "TAGGIFIR_REMAINDER_R $remainder")
        while(remainder > 0){

            remainder--
            Log.d("MYREVEX", "TAGGIFIR_REMAINDER_IN $remainder")

            getTag({ tag->

                ioDispatcher.launch {
                room.tagDao().insert(Tag(true, tag, "tagifier"))
                }

            }){
            }

        }
        }
    }


    fun getTagAndPop(success: (String) -> Unit, error:(String)->Unit){


        ioDispatcher.launch {
            val all = room.tagDao().getAll()
            Log.d("TAGGIFIER","$all")
            Log.d("TAGGIFIER","${all.size}")
            if(all.size < 1){
                mainDispatcher.launch {
                    error("You are out of JMDB TAGS. Please Synchronize")
                }
                return@launch
            }

            val tag = all[0]
            Log.d("TAGGIFIER","${room.tagDao().getAll().size}")

            mainDispatcher.launch {
                success(tag.tag)
            }
        }


    }

    fun getTag(success: (String) -> Unit, error:(String)->Unit){


        //get from list. pop the list after collection




         ioDispatcher.launch {
            try {

                val tagRequest = Request.Builder()
                    .url(Constants.baseUrl + "api/get_tag_number")
                    .addHeader("Authorization", "Bearer ${Constants.TOKENN}")

                val response: okhttp3.Response = okHttpClient.newCall(tagRequest.build()).execute()
                val body = response.body!!.string()
                Log.d("MYREVEX", "TAGGIFIR $body")
                val resp = gson.fromJson(body, Tag::class.java)

                try {

                if (resp.status) {
                    mainDispatcher.launch {
                        success(resp.tag)
                    }

                } else {

                    Log.d("MYREVEX", "Error ${body}")

                    mainDispatcher.launch {
                        error(resp.message)
                    }

                }
                }catch(e: Exception){
                    mainDispatcher.launch {
                    error("Api Error: Try Again") }
                }

            } catch (e: Exception) {
                mainDispatcher.launch {
                    when (e) {
                        is UnknownHostException -> {
                            error("Api Error: No Internet Connection")
                        }

                        is ConnectException -> {
                            error("Api Error: No Internet Connection.")
                        }

                        is SocketTimeoutException -> {
                            error("Api Error: Timeout")
                        }

                        else -> {
                            error("Api Error: Unknown")
                        }
                    }
                }
                Log.d("MYREVEX", "Exception: ${e.message}")
            }

        }


    }







    fun validatePhone2(
        phone: String,
        success: (String) -> Unit,
        error: (String) -> Unit,
        error2: (String) -> Unit
    ) {




        ioDispatcher.launch {

            Log.d("MYREVEX", "CLICKED")

            try {
                val validateTinRequest = Request.Builder()
                .addHeader("Authorization", "Bearer ${Constants.TOKENN}").url(Constants.baseUrl + "api/verify_tin_phone?phone=$phone")
                val response: okhttp3.Response = okHttpClient.newCall(validateTinRequest.build()).execute() //...


                val body = response.body!!.string()
                Log.d("MYREVEX", "BODY $body")
                val resp = gson.fromJson(body, MainTinResponse::class.java)
                if (resp.status) {


                    //check if company or individual

                    Log.d("MYREVEX", "RESPONSE SUCCESS S ${body}")


//
                    Log.d("MYREVEX", "RESPONSE SUCCESS V ${resp}")
                    OWNER_NUMBER = resp.data.phoneNumber

                    OWNER_NAME = if (resp.data.name.isEmpty()) {
                        resp.data.first_name
                    } else {
                        resp.data.name
                    }

                    if (resp.data.middle_name.trim().replace(" ", "")
                            .isNotEmpty() && resp.data.middle_name != null
                    ) {
                        OWNER_MIDDLE_NAME = resp.data.middle_name
                    }

                    OWNER_SURNAME = resp.data.surname
                    try {
                        OWNER_TITLE = resp.data.title.toString()
                    } catch (e: Exception) {
                        ""
                    }

                    Constants.TIN = resp.data.tin



                    getTagAndPop({ tag ->

                        mainDispatcher.launch {
                            success(tag)
                        }

                    }){ error ->
                        mainDispatcher.launch {
                        error(error)
                        }
                    }






                } else {

                    Log.d("MYREVEX", "Error ${body}")

                    mainDispatcher.launch {
                        error(resp.message)
                    }

                }

            } catch (e: Exception) {
                mainDispatcher.launch {
                    when (e) {
                        is UnknownHostException -> {
                            error("Api Error: No Internet Connection")
                        }

                        is ConnectException -> {
                            error2("Api Error: No Internet Connection.")
                        }

                        is SocketTimeoutException -> {
                            error2("Api Error: Timeout")
                        }

                        else -> {
                            error2("Api Error: Unknown")
                        }
                    }
                }
                Log.d("MYREVEX", "Exception: ${e.message}")
            }

        }




    }












    val categoriesRequest = Request.Builder()
        .url(Constants.baseUrl + "api/fetch_building_categories")//TODO TRY BR




    //TODO TRY BR



    val identificationRequest = Request.Builder()
        .url(Constants.baseUrl + "api/fetch_identification")//TODO TRY BR



    val purposeRequest = Request.Builder()
        .url(Constants.baseUrl + "api/fetch_purpose")//TODO TRY BR




    fun initializeData(progressor: (String)->Unit,success: () -> Unit, error: (String) -> Unit) {

        mainDispatcher.launch {
            progressor("0%")
        }

        //residential

        ioDispatcher.launch {

            val stateId = "32"
            Log.d("LOGINX", "INITIALIZE CALLED")


            val lgaRequest = Request.Builder()
                .addHeader("Authorization", "Bearer ${Constants.TOKENN}").url(Constants.baseUrl + "api/fetch_lga?state_id=$stateId")
            val response: okhttp3.Response = okHttpClient.newCall(lgaRequest.build()).execute() //...
            val body = response.body!!.string()

            Log.d("LOGINX", "INITIALIZE STARTED $body")

            if (response.isSuccessful) {
                Log.d("LOGINX", "SUCCESS")

                try {

                    val lgas = gson.fromJson(body, LgaResponse::class.java)

                          Log.d("LOGINX", "ALL LGAS RAW ${body}")
                    Log.d("LOGINX", "ALL LGAS ${lgas}")

                    mainDispatcher.launch {
                        progressor("10%")
                    }

                    lgas.data.forEach {

                        room.lgaDao().insert(it)
                        Log.d("LOGINX", "lga inserted $it")

                        try {

                            Log.d("LOGINX", "area in")



                            val areaRequest = Request.Builder().url(Constants.baseUrl + "api/fetch_areas?lga_id=${it.lga_id}")
                            .addHeader("Authorization", "Bearer ${Constants.TOKENN}").build()


                            val response: okhttp3.Response =
                                okHttpClient.newCall(areaRequest).execute()
                            val body = response.body!!.string()

                            Log.d("LOGINX", "area $body")
                      //k      response.close()


                            if (response.isSuccessful) {
                                mainDispatcher.launch {
                                    progressor("20%")
                                }
                                Log.d("LOGINX", "before")
                                val areas = gson.fromJson(body, AreasResponse::class.java)
                                Log.d("LOGINX", "after")
                                areas.data.forEach {
                                    Log.d("LOGINX", "area")
                                    try {
                                    room.areaDao().insert(it)

                                    Log.d("LOGINX", "area inserted $it")

                                    val streetRequest = Request.Builder().addHeader("Authorization", "Bearer ${Constants.TOKENN}").url(Constants.baseUrl + "api/fetch_street?areaId=${it.id.toString()}")//TODO TRY BR


                                    val response: okhttp3.Response =
                                        okHttpClient.newCall(streetRequest.build()).execute() //...
                                    val body = response.body!!.string()
                                    if (response.isSuccessful) {
                                        mainDispatcher.launch {
                                            progressor("55%")
                                        }
                                            Log.d("LOGINX", "each street $body")
                                            val streets =
                                                gson.fromJson(body, StreetsResponse::class.java)

                                            streets!!.data.map{it.copy(city_id = "0")}.forEach {
                                                try {
                                                room.streetDao().insert(it)
                                            }catch(e:Exception){
                                                Log.d("LOGINX", "CRASH_STREET($it) + ${e.message}")
                                            }
                                            }

                                    } else {

                                    }

                                }catch(e: Exception){
                                        Log.d("LOGINX", "CRASH_AREA($it) + ${e.message}")
                                }

                                }


                            } else {

                            }

                            //
                        }catch(e: Exception){
                            Log.d("LOGINX", "CRASH_LGA($it) + ${e.message}")
                        }

                    }


                    Log.d("MYLOGS", "PAST CRITICAL CODE") //

                    mainDispatcher.launch {
                        progressor("80%")
                    }


                    val response: okhttp3.Response = okHttpClient.newCall(categoriesRequest.addHeader("Authorization", "Bearer ${Constants.TOKENN}").build()).execute() //...
                    val body = response.body!!.string()
                    if (response.isSuccessful) {

                        mainDispatcher.launch {
                            progressor("90%")
                        }
                        val respp = gson.fromJson(body, BuildingCategoriesResponse::class.java)
                        Log.d("LOGINX", "Categories SUCCESS $respp")
                        respp.result.forEach {

                            room.buildingCategoryDao().insert(it)


                   val buildingTypesRequest = Request.Builder()
                    .url(Constants.baseUrl + "api/fetch_building_type?id=${it.idbuilding_category}")
                    .addHeader("Authorization", "Bearer ${Constants.TOKENN}")


                    val response2: okhttp3.Response = okHttpClient.newCall(buildingTypesRequest.build()).execute() //...
                    val body2 = response2.body!!.string()
                    if (response2.isSuccessful) {
                        Log.d("LOGINX", "Types SUCCESS $body2... $it")
                        gson.fromJson(body2, BuildingTypeResponse::class.java).result.forEach {
                            room.buildingTypeDao().insert(it)
                        }
                    }

                        }


                    }else{
                        Log.d("LOGINX", "Categories Failure ${response.message} ${response.code}")
                    }




                    mainDispatcher.launch {
                        progressor("95%")
                    }


                    try {
                        for (x in 1..30) {
                            try {
                                getRevenueItems(x)
                            } catch (e: Exception) {
                            }
                        }
                    } catch (e: Exception) {

                    }



                    val response4: okhttp3.Response = okHttpClient.newCall(identificationRequest .addHeader("Authorization", "Bearer ${Constants.TOKENN}")
                        .build()).execute() //...
                    val body4 = response4.body!!.string()
                    if (response4.isSuccessful) {

                        gson.fromJson(body4, IdentificationResponse::class.java).data.forEach {
                            room.identityTypeDao().insert(it)
                        }
                    }



                    val response3: okhttp3.Response = okHttpClient.newCall(purposeRequest.addHeader("Authorization", "Bearer ${Constants.TOKENN}")
                        .build()).execute() //...
                    val body3 = response3.body!!.string()
                    if (response3.isSuccessful) {

                        gson.fromJson(body3, LandPurposeResponse::class.java).data.forEach {
                            room.landPurposeDao().insert(it)
                        }
                    }

                    mainDispatcher.launch {
                        progressor("99%, Downloading JMDB Tags!")
                    }

                         _100Tagifier()

                    mainDispatcher.launch {
                        progressor("99.9%, Hello Jmdb!")
                    }

                    getLandUse()

                    mainDispatcher.launch {
                        progressor("100%, Complete")
                    }

                    mainDispatcher.launch {
                        Log.d("LOGINX", "FINISHED 2")

                        success()
                    }



                } catch (e: Exception) {

                }





            } else {

                mainDispatcher.launch {
                    error(response.message)
                }

            }


        }


    }





    fun getRevenueItems(page: Int) {


        try {
            val revenueItemsRequest = Request.Builder()
            .addHeader("Authorization", "Bearer ${Constants.TOKENN}").url(Constants.baseUrl + "api/assessment_items?page=$page")
            val response: okhttp3.Response = okHttpClient.newCall(revenueItemsRequest.build()).execute() //...

            val body = response.body!!.string()

        //    Log.d("MYREVEX", "ITEM $body")

            val respObj = gson.fromJson(body, AssessmentItemBodyNew::class.java)

         //   Log.d("MYREVEX", "ITEM $respObj")

            respObj.data.result.forEach {

                try {
                    room.revenueItemDao().insert(it)
                } catch (e: Exception) {
                }

            }

        } catch (e: Exception) {

        }


    }


    fun createIndividualTin(
        individualTin: IndividualTin,
        success: (String) -> Unit,
        error: (String) -> Unit
    ) {


        Log.d("MYREVEX", "Clicked $individualTin")

//        if(true){
//            return
//        }


        ioDispatcher.launch {


            try {


                val jsonBody = gson.toJson(individualTin)


                val bod = jsonBody.toRequestBody(mediaType)
                //   val body = RequestBody.create(mediaType, jsonBody)


                val request = Request.Builder()
                    .url(Constants.baseUrl + "api/generate_individual_tin_psirs")//TODO TRY BR
                    .addHeader("Authorization", "Bearer ${Constants.TOKENN}")
                    .post(bod)
                    .build()

                val response: okhttp3.Response = okHttpClient.newCall(request).execute() //...
                val body = response.body!!.string()
                val respObj = gson.fromJson(body, IndividualTinResponse::class.java)
                Log.d("MYREVEX", "TRYING $jsonBody")

                if (respObj.status) {


                    Log.d("MYREVEX", body)
                    mainDispatcher.launch {
                        success(respObj.tin)
                    }
                    //   val respObj = gson.fromJson(body, AssessmentItemBodyNew::class.java)


                    //  Log.d("MYREVEOX",respObj.toString())

                } else {
                    //   Log.d("MYREVEX","Error: Invalid Phone number or Email")
                    mainDispatcher.launch {
                        error(respObj.message)
                    }
                }

            } catch (e: Exception) {
                mainDispatcher.launch {
                    when (e) {
                        is UnknownHostException -> {
                            error("Error: No Internet Connection")
                        }

                        is ConnectException -> {
                            error("Api Error: No Internet Connection.")
                        }

                        is SocketTimeoutException -> {
                            error("Api Error: Timeout")
                        }

                        else -> {
                            error("Error: Unknown Error")
                        }
                    }

                }
                Log.d("MYREVEX", "Exception: ${e.message}")
            }


        }

    }


    fun createCompanyTin(
        companyTin: MyCompany,
        success: (String) -> Unit,
        error: (String) -> Unit
    ) {

        ioDispatcher.launch {


            Log.d("MYREVEX", "Clicked $companyTin")

            try {


                val jsonBody = gson.toJson(companyTin)


                val bod = jsonBody.toRequestBody(mediaType)
                //   val body = RequestBody.create(mediaType, jsonBody)


                Log.d("MYREVEX", " $jsonBody")

                val request = Request.Builder()

                    .url("https://plateaujmdb.ng/api/generate_company_tin_psirs")//TODO TRY BR
                    //  .addHeader("Content-Type","application/json")
                    .addHeader("Authorization", "Bearer ${Constants.TOKENN}")
//                    .addHeader("Cache-Control","no-cache")
//                    .addHeader("Accept","*/*")
//                    .addHeader("Accept-Encoding","gzip, deflate, br")
//                    .addHeader("Connection","keep-alive")
                    .post(bod)
                    .build()

                val response: okhttp3.Response = okHttpClient.newCall(request).execute() //...
                val body = response.body!!.string()
                Log.d("MYREVEX", "TRYING-1 $jsonBody")
                val respObj = gson.fromJson(body, MyCompanyTinResponse::class.java)
                Log.d("MYREVEX", "TRYING $jsonBody")

                Log.d("MYREVEX", "TRYING2 ${body}")

                if (respObj.status) { //08132759911


                    mainDispatcher.launch {
                        success(respObj.data.tin)
                    }
                    //   val respObj = gson.fromJson(body, AssessmentItemBodyNew::class.java)


                    //  Log.d("MYREVEOX",respObj.toString())

                } else {
                    mainDispatcher.launch {
                        error(respObj.message)
                    }
                }

            } catch (e: Exception) {
                mainDispatcher.launch {
                    when (e) {
                        is UnknownHostException -> {
                            error("Error: No Internet Connection")
                        }

                        is ConnectException -> {
                            error("Api Error: No Internet Connection.")
                        }

                        is SocketTimeoutException -> {
                            error("Api Error: Timeout")
                        }

                        else -> {
                            error("Error: Unknown Error")
                        }
                    }
                }
                Log.d("MYREVEX", "Exception: ${e.message}")
            }


        }

    }


    val landRequest = Request.Builder()
        .url(Constants.baseUrl + "api/fetch_land_use")//TODO TRY BR


    fun getLandUse() {



        val response: okhttp3.Response = okHttpClient.newCall(landRequest.addHeader("Authorization", "Bearer ${Constants.TOKENN}").build()).execute() //...


        try {

            val body = response.body!!.string()
            Log.d("MYLOGGGG", body)
            val respObj = gson.fromJson(body, LandUseResponseNew::class.java)
            Log.d("MYLOGGGG", "Done")
            Log.d("MYLOGGGG", respObj.toString())
            respObj.data.forEach {
                try {
                    Log.d("MYLOGGGG1", "TRYING")
                    room.landUseDao().insert(it)
                } catch (e: Exception) {
                    Log.d("MYLOGGGG1", e.message.toString())
                }
            }
        } catch (e: Exception) {
            Log.d("MYLOGGGG", "Exception: ${e.message}")
        }

    }




    fun checkBuildingSyncStatus(id: String, callback: (Boolean) -> Unit) {
        callback(false)
    }


    fun quickSync(areaId: String, success:()->Unit, error:(String)->Unit) {

    //    Log.d("LOGINX", "area inserted $it")

        ioDispatcher.launch {

        try {

            val request = Request.Builder()
                .url(Constants.baseUrl + "api/fetch_street?areaId=${areaId}")//TODO TRY BR
                .addHeader("Authorization", "Bearer ${Constants.TOKENN}")
                .build()
            val response: okhttp3.Response =
                okHttpClient.newCall(request).execute() //...
            val body = response.body!!.string()
            val streets =
                gson.fromJson(body, StreetsResponse::class.java)
            if (response.isSuccessful) {


                Log.d("LOGINX", "each street $body")


                streets!!.data.map { it.copy(city_id = "0") }.forEach {
                    try {
                        room.streetDao().insert(it)
                        Log.d("LOGINX", "inserted street $it")
                    } catch (e: Exception) {
                        Log.d("LOGINX", "CRASH_STREET($it) + ${e.message}")
                    }
                }

                mainDispatcher.launch {
                    success()
                }

            } else {
                mainDispatcher.launch {
                error(streets.message)
                }
            }
        } catch (e: IOException) {
            mainDispatcher.launch {


                when (e) {
                    is UnknownHostException -> {
                        error("Error: No Internet Connection")
                    }

                    is ConnectException -> {
                        error("Api Error: No Internet Connection.")
                    }

                    is SocketTimeoutException -> {
                        error("Api Error: Timeout")
                    }

                    else -> {
                        error("Api Error: Unknown")
                    }
                }


            }
        }
    }
    }



    fun cleanLogin(progress: (String) -> Unit, loginBody: LoginBody, success: () -> Unit, error: (String) -> Unit) {

        Constants.USER_EMAIL = loginBody.username

        ioDispatcher.launch {

            try {


                val jsonBody = gson.toJson(loginBody)


                val jsn = jsonBody.toRequestBody(mediaType)

                val request = Request.Builder()
                    .url(Constants.baseUrl + "api/login")//TODO TRY BR
                    .post(jsn)
                    .build()

                val response: okhttp3.Response = okHttpClient.newCall(request).execute()
                val body = response.body!!.string()
                Log.d("LOGINX", "Body---" + body)



                try {

                    val respObj = gson.fromJson(body, LoginMessage::class.java)

                    if (respObj.message != "error") {

                        val respObj = gson.fromJson(body, LoginResponse::class.java)

                        Constants.TOKENN = respObj.token

                        //  Log.d("LOGINX", TOKENN)


                        if (fetchAuthToken().isNotEmpty()) {
                            _100Tagifier()
                            Log.d("LOGINX", "CONTINUED")
                            mainDispatcher.launch {
                                saveAuthToken("LOGGED_IN")
                                success()
                            }

                        } else {
                            Log.d("LOGINX", "HERE....1 ")


                            try {

                            firebase.child(
                                "jmdb/enumerationCount/${
                                    Constants.USER_EMAIL.replace(
                                        ".",
                                        "_"
                                    )
                                }"
                            )
                                .get()
                                .addOnSuccessListener {
                                    if (it.exists()) {
                                        it.children.forEach {
                                            ioDispatcher.launch {
                                                room.timeDao()
                                                    .insert(Time(it.child("timeId").value.toString()))
                                            }
                                        }
                                    }
                                }

                        }catch(e: Exception){

                        }


                        Log.d("LOGINX", "HERE.... 2")

                            initializeData({
                                mainDispatcher.launch {
                                           progress(it)
                                }
                            },{
                                Log.d("LOGINX", "NEW")
                                saveAuthToken("LOGGED_IN")
                                mainDispatcher.launch {
                                success()
                                }
                            }) {
                                mainDispatcher.launch {
                                error(it) }
                            }
                        }

                    } else {
                        val respObj = gson.fromJson(body, LoginError::class.java)
                        mainDispatcher.launch {
                            error(respObj.data)
                        }
                    }
                } catch (e: Exception) {
                    mainDispatcher.launch {
                        error("Api Error: Try again")
                    }
                }

            } catch (e: IOException) {
                mainDispatcher.launch {


                    when (e) {
                        is UnknownHostException -> {
                            error("Error: No Internet Connection")
                        }

                        is ConnectException -> {
                            error("Api Error: No Internet Connection.")
                        }

                        is SocketTimeoutException -> {
                            error("Api Error: Timeout")
                        }

                        else -> {
                            error("Api Error: Unknown")
                        }
                    }


                }
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
            ioDispatcher.launch {


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
        ioDispatcher.launch {

            val building = room.buildingDao().getById(id)

            mainDispatcher.launch {
                success(building)
            }
        }
    }


    fun createBuildingLocally(
        data: Data,
        success: () -> Unit,
        error: (String) -> Unit
    ) {

        Log.d("TRACKER", "${data.copy(building_image = "...")}")
        ioDispatcher.launch {  Log.d("TAGGIFIER", "${room.tagDao().getAll()}")
        Log.d("TAGGIFIER", "${room.tagDao().getAll().size}") }
        try {
            ioDispatcher.launch {
                room.buildingDao().insert(data)
                room.tagDao().delete(Tag(true, data.owner_email, "tagifier"))
                Log.d("TAGGIFIER", "${room.tagDao().getAll()}")
                Log.d("TAGGIFIER", "${room.tagDao().getAll().size}")
            }
            mainDispatcher.launch {
             success()
            }

        } catch (e: Exception) {
            mainDispatcher.launch {
            error(e.message.toString())
            }
        }
    }


    fun getSyncables(count: (Int) -> Unit) {


        ioDispatcher.launch {
            val syncable = room.buildingDao().getAll().filter {
                it.status == 1
            }
            Log.d("REPOSITORY", "Syncable: ${syncable.size}")
            count(syncable.size)
        }

    }








    fun syncSingle(buildings: List<Data>, error: (String) -> Unit,success: (Int) -> Unit) {

        val deletables = buildings

        Log.d("MYREVEX","Bearer ${Constants.TOKENN}")

        Log.d("MYREVEX","${buildings.map { it.copy(building_image = "xcv") }}")



        ioDispatcher.launch {

        try {

            val bulk = JmdbBuildingPostBody(
                data = buildings.map { it ->

                    DataTagged(
                        building_tag = it.owner_email,
                        apartment_type = it.apartment_type,
                        approval_code = it.approval_code,
                        building_category_id = it.building_category_id,
                        building_id = "",
                        building_image = it.building_image,
                        building_name = it.building_name,
                        building_number = it.building_number,
                        id = it.id,
                        latitude = it.latitude,
                        lga = it.lga,
                        longitude = it.longitude,
                        no_of_apartments = "Not set",
                        owner_email = "Not set",
                        owner_mobile_no = "Not set",
                        owner_name = "Not set",
                        state_id = it.state_id,
                        status = it.status,
                        street_id = it.street_id,
                        taxitem = it.taxitem, //tax items
                        tin = if (it.tin.isEmpty()) {
                            "Not set"
                        } else {
                            it.tin
                        },
                        ward = it.ward,
                        line = it.line,
                    )

                }
                /*.filter{ it.line == "Area line 12"}*/,
                page = 10,
                pageLength = "20",
                total = buildings.size,
            )

            val mediaType: MediaType = "application/json".toMediaTypeOrNull()!!

            val gson = Gson()

            val jsonBody =
                gson.toJson(bulk.copy(data = bulk.data.map { it.copy(building_id = "") }))


            val syncRequest = Request.Builder()
                .addHeader("Authorization", "Bearer ${Constants.TOKENN}")
                .url(Constants.baseUrl + "api/create_buildings")

            val jsn = jsonBody.toRequestBody(mediaType)
              syncRequest.post(jsn)


            val response: okhttp3.Response = okHttpClient.newCall(syncRequest.build()).execute() //...



            Log.d(
                "MYREVEX",
                "BUILDING_BODY---> ${
                    gson.toJson(bulk.copy(data = bulk.data.map {
                        it.copy(building_image = "data:image/jpeg;base64,")
                    }))
                }\n\n" + bulk.copy(data = bulk.data.map { it.copy(building_image = "data:image/jpeg;base64,") })
                    .toString()
            )


            val body = response.body!!.string()
            Log.d("REPOSITORY", "Body--->" + body)
            val respObj = gson.fromJson(body, JmdbBuildingPostResponse::class.java)

            if (response.isSuccessful) {


                Log.d("REPOSITORY", "RESPONSE SUCCESS S ${respObj}")

                //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                //TODO("THIS IS PRONE TO ERROR BECAUSE IT MIGHT BE SUCCESS EVEN THO THERE IS ERROR")
                ioDispatcher.launch {

                    Log.d("DELETION","$deletables")

                    //check if returned buildingnumber is same as building number
                    deletables.forEach { building ->

                        val instant = Time(Util.getDate())


                        Log.d("DELETION","$building")

                        room.buildingDao().delete(building.copy(status = 0))

                        Log.d("DELETION","DELETE PASSED")

                        try {
                            firebase.child(
                                "jmdb/enumerationCount/${
                                    Constants.USER_EMAIL.replace(
                                        ".",
                                        "_"
                                    )
                                }/${System.currentTimeMillis()}"
                            )
                                .setValue(instant).addOnSuccessListener {
                                    ioDispatcher.launch {
                                        room.timeDao().insert(instant)
                                    }
                                }
                        } catch (e: Exception) {

                        }

                    }

                    mainDispatcher.launch {
                        success(respObj.syncIds.size)
                    }


                }


            } else {

                Log.d("MYREVEX", "Error ${response.message}")
                mainDispatcher.launch {
                    error(respObj.message)
                }

            }

        } catch (e: Exception) {
            mainDispatcher.launch {
                when (e) {
                    is UnknownHostException -> {
                        error("Error: No Internet Connection")
                    }

                    is ConnectException -> {
                        error("Api Error: No Internet Connection.")
                    }

                    is SocketTimeoutException -> {
                        error("Api Error: Timeout")
                    }

                    else -> {
                        error("Api Error: Unknown")
                    }
                }
            }
            Log.d("MYREVEX", "Exception: ${e.message}")
        }


    }
    }

    fun syncMultipleBuildings(context: Context,all: List<Data> = listOf(),preparer:(Int, Int, Int, Int)->Unit, synchronizer: ()->Unit, error: (String) -> Unit, success: (Int) -> Unit) {

        JmdbRepository.BLOCK_SYNC = false

        //,context: Context, percentager:(Int, Int, Int, Int)->Unit

        var syncable = listOf<Data>()






            ioDispatcher.launch {





            if(all.isEmpty()){
            syncable = room.buildingDao().getAll().filter {
                it.status == 1
            }}else{
              syncable = all
            }





            Log.d("MYREVEX", "Clicked")


                var all = syncable.size
                var failed = 0
                var succ = 0
                var count = 0


                var deletables = mutableListOf<Data>()



                syncable.forEach { data->

                    cloudinary(data,{ url ->

                        try{



                        count++
                        succ++
                        deletables.add(data)
                        preparer(all,count, succ, failed)


                        if(count == all){

                            if(deletables.isNotEmpty()){

                         //       synchronizer()

                                success(deletables.size)

//                                sync({
//                                    success(deletables.size)
//                                }){ error ->
//                                    error(error)
//                                }
                                //sync here and call success or error
                                //delete all in detables
                                //total number of buildings synced = total number of deletables
                            }else{
                                error("Preparation failed")
                            }


                        }

                        }catch(e: Exception){
                            error("Sync failed: Unknown error 01")
                        }



                    }){ error ->

                        if(error.contains("GENERIC_ERROR 03")){
                            error(error.replace("GENERIC_ERROR 03",""))
                            return@cloudinary
                        }

                        try {
                        count++
                        failed++
                        preparer(all,count, succ, failed)


                        if(count == all){

                            if(deletables.isNotEmpty()){

                                success(deletables.size)
//                                synchronizer()
//                                sync({
//                                    success(deletables.size)
//                                }){error ->
//                                    error(error)
//                                }
                            }else{
                                error("Preparation failed\n$error")
                            }
                        }
                    }catch(e: Exception){
                    error("Sync failed: Unknown error 01")
                }
                    }

                }







        }




    }





    //setup process (16%.... 80%)
    //finalizing upload





    //add a callback
    fun cloudinary(data: Data, success: (String) -> Unit, error: (String) -> Unit) {


        if(!BLOCK_SYNC) {
            syncSingle(listOf(data.copy(building_image = data.owner_name, owner_name = "Not set")), {
                error("$it")
            }) {
                success("$it")
            }
        }else{

        }

    }



    fun getTimes(callback: (List<Time>) -> Unit) {

        ioDispatcher.launch {

            val times = room.timeDao().getAll()

            mainDispatcher.launch {
                if (times.size > 0) {
                    callback(times)
                }
            }
        }
    }


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


    fun submitForm1(
        form1Body: Form1Body,
        success: (Form1Response) -> Unit,
        error: (String) -> Unit
    ) {


        ioDispatcher.launch {
            try {
                Log.d("LOGINS", "CLICKED- ${Constants.TOKENN}")
                Log.d("FORM1X", "success 1 $form1Body")


                val jsonBody = gson.toJson(form1Body)

                val body = jsonBody.toRequestBody(mediaType)

                val request = Request.Builder()
                    .url("https://plateaujmdb.ng/api/step1")
                    .addHeader("Authorization", "Bearer ${Constants.TOKENN}")
                    .post(body)
                    .build()
                val response: okhttp3.Response = okHttpClient.newCall(request).execute() //...
                val resBody = response.body!!.string()
                Log.d("FORM1X", "success 2 $resBody")
                val respObj = gson.fromJson(resBody, Form1Response::class.java)

                if (respObj.status) {

                    Log.d("FORM1X", "success 2 $resBody")

                    Log.d("FORM1X", "success 3 $respObj")
                    mainDispatcher.launch {
                        success(respObj)
                    }

                } else {
                    mainDispatcher.launch {
                        error("Box 1 Error: " + respObj.message)
                    }


                }
            } catch (e: Exception) {
                mainDispatcher.launch {
                    when (e) {
                        is UnknownHostException -> {
                            error("Error: No Internet Connection")
                        }

                        is ConnectException -> {
                            error("Api Error: No Internet Connection.")
                        }

                        is SocketTimeoutException -> {
                            error("Api Error: Timeout")
                        }

                        else -> {
                            Log.d("FORM1X", "${e.message}")
                            error("Api Error: Unknown")
                        }
                    }
                }
            }
        }


    }


    fun submitForm2(
        form2Body: Form2Body,
        success: (Form2Response) -> Unit,
        error: (String) -> Unit
    ) {



        ioDispatcher.launch {
            try {

                Log.d("LOGINS", "CLICKED- ${Constants.TOKENN}")
                Log.d("FORM2X", "success 1 $form2Body")

                val jsonBody = gson.toJson(form2Body)

                val body = jsonBody.toRequestBody(mediaType)

                val request = Request.Builder()
                    .url("https://plateaujmdb.ng/api/step2")
                    .addHeader("Authorization", "Bearer ${Constants.TOKENN}")
                    .post(body)
                    .build()
                val response: okhttp3.Response = okHttpClient.newCall(request).execute() //...
                val resBody = response.body!!.string()
                Log.d("FORM2X", "success 2 $resBody")
                val respObj = gson.fromJson(resBody, Form2Response::class.java)

                if (respObj.status) {

                    Log.d("FORM2X", "success 2 $resBody")

                    Log.d("FORM2X", "success 3 $respObj")
                    mainDispatcher.launch {
                        success(respObj)
                    }

                } else {
                    mainDispatcher.launch {
                        error("Box 2 Error: " + respObj.message)
                    }


                }
            } catch (e: Exception) {
                mainDispatcher.launch {
                    when (e) {
                        is UnknownHostException -> {
                            error("Error: No Internet Connection")
                        }

                        is ConnectException -> {
                            error("Api Error: No Internet Connection.")
                        }

                        is SocketTimeoutException -> {
                            error("Api Error: Timeout")
                        }

                        else -> {
                            Log.d("FORM1X", "${e.message}")
                            error("Api Error: Unknown")
                        }
                    }
                }
            }
        }
    }


    fun submitForm3(
        form3Body: Form3Body,
        success: (Form3Response) -> Unit,
        error: (String) -> Unit
    ) {




        ioDispatcher.launch {
            try {
                Log.d("LOGINS", "CLICKED- ${Constants.TOKENN}")
                Log.d("FORM3X", "success 1 $form3Body")

                val jsonBody = gson.toJson(form3Body)

                val body = jsonBody.toRequestBody(mediaType)

                val request = Request.Builder()
                    .url("https://plateaujmdb.ng/api/step3")
                    .addHeader("Authorization", "Bearer ${Constants.TOKENN}")
                    .post(body)
                    .build()
                val response: okhttp3.Response = okHttpClient.newCall(request).execute() //...
                val resBody = response.body!!.string()
                Log.d("FORM3X", "success 2 $resBody")
                val respObj = gson.fromJson(resBody, Form3Response::class.java)

                if (respObj.status) {

                    Log.d("FORM3X", "success 2 $resBody")

                    Log.d("FORM3X", "success 3 $respObj")
                    mainDispatcher.launch {
                        success(respObj)
                    }

                } else {
                    mainDispatcher.launch {
                        error("Box 3 Error: " + respObj.message)
                    }


                }
            } catch (e: Exception) {
                mainDispatcher.launch {
                    when (e) {
                        is UnknownHostException -> {
                            error("Error: No Internet Connection")
                        }

                        is ConnectException -> {
                            error("Api Error: No Internet Connection.")
                        }

                        is SocketTimeoutException -> {
                            error("Api Error: Timeout")
                        }

                        else -> {
                            Log.d("FORM1X", "${e.message}")
                            error("Api Error: Unknown")
                        }
                    }
                }
            }
        }


    }


    fun submitForm4(
        form4Body: Form4Body,
        success: (Form4Response) -> Unit,
        error: (String) -> Unit
    ) {


        ioDispatcher.launch {
            try {
                Log.d("LOGINS", "CLICKED- ${Constants.TOKENN}")
                Log.d("FORM4X", "success 1 $form4Body")

                val jsonBody = gson.toJson(form4Body)
                val body = jsonBody.toRequestBody(mediaType)

                val request = Request.Builder()
                    .url("https://plateaujmdb.ng/api/step4")
                    .addHeader("Authorization", "Bearer ${Constants.TOKENN}")
                    .post(body)
                    .build()
                val response: okhttp3.Response = okHttpClient.newCall(request).execute() //...
                val resBody = response.body!!.string()
                Log.d("FORM4X", "success 2 $resBody")
                val respObj = gson.fromJson(resBody, Form4Response::class.java)

                if (respObj.status) {

                    Log.d("FORM4X", "success 2 $resBody")

                    Log.d("FORM4X", "success 3 $respObj")
                    mainDispatcher.launch {
                        success(respObj)
                    }

                } else {
                    mainDispatcher.launch {
                        error("Box 4 Error: " + respObj.message)
                    }


                }
            } catch (e: Exception) {
                mainDispatcher.launch {
                    when (e) {
                        is UnknownHostException -> {
                            error("Error: No Internet Connection")
                        }

                        is ConnectException -> {
                            error("Api Error: No Internet Connection.")
                        }

                        is SocketTimeoutException -> {
                            error("Api Error: Timeout")
                        }

                        else -> {
                            Log.d("FORM1X", "${e.message}")
                            error("Api Error: Unknown")
                        }
                    }
                }
            }
        }
    }


    fun submitForm5(
        form5Body: Form5Body,
        success: (Form5Response) -> Unit,
        error: (String) -> Unit
    ) {



        ioDispatcher.launch {
            try {
                Log.d("LOGINS", "CLICKED- ${Constants.TOKENN}")
                Log.d("FORM5X", "success 1 $form5Body")

                val jsonBody = gson.toJson(form5Body)

                val body = jsonBody.toRequestBody(mediaType)

                val request = Request.Builder()
                    .url("https://plateaujmdb.ng/api/step5")
                    .addHeader("Authorization", "Bearer ${Constants.TOKENN}")
                    .post(body)
                    .build()
                val response: okhttp3.Response = okHttpClient.newCall(request).execute() //...
                val resBody = response.body!!.string()
                Log.d("FORM5X", "success 2 $resBody")
                val respObj = gson.fromJson(resBody, Form5Response::class.java)

                if (respObj.status) {

                    Log.d("FORM5X", "success 2 $resBody")

                    Log.d("FORM5X", "success 3 $respObj")
                    mainDispatcher.launch {
                        success(respObj)
                    }

                } else {
                    mainDispatcher.launch {
                        error("Box 5 Error: " + respObj.message)
                    }


                }
            } catch (e: Exception) {
                mainDispatcher.launch {
                    when (e) {
                        is UnknownHostException -> {
                            error("Error: No Internet Connection")
                        }

                        is ConnectException -> {
                            error("Api Error: No Internet Connection.")
                        }

                        is SocketTimeoutException -> {
                            error("Api Error: Timeout")
                        }

                        else -> {
                            Log.d("FORM1X", "${e.message}")
                            error("Api Error: Unknown")
                        }
                    }
                }
            }
        }

    }


    fun uploadDocuments(success: () -> Unit, error: (String) -> Unit) {



        val form6body = Form6Body(
            fileUrl = "data:application/pdf;base64,$IMAGE_1",
            fileName = "Copy of the certificate of occupancy",
            track_no = Constants.TRACK_NO,
            image = IMAGE_1
        )//LVZRLK9O
        submitForm6(form6body, {

            Log.d("COMPLETED!!!", "1 COMPLETE")

            //1
            val form6body = Form6Body(
                fileUrl = "data:application/pdf;base64,$IMAGE_2",
                fileName = "Copy of MLS TP Acknowledge Letter",
                track_no = Constants.TRACK_NO,
                image = IMAGE_2
            )//LVZRLK9O


            submitForm6(form6body, {
                Log.d("COMPLETED!!!", "2 COMPLETE")
                //2
                val form6body = Form6Body(
                    fileUrl = "data:application/pdf;base64,$IMAGE_3",
                    fileName = "Copy of Structural Drawing and Details",
                    track_no = Constants.TRACK_NO,
                    image = IMAGE_3
                )//LVZRLK9O

                submitForm6(form6body, {
                    //3
                    Log.d("COMPLETED!!!", "3 COMPLETE")

                    val form6body = Form6Body(
                        fileUrl = "data:application/pdf;base64,$IMAGE_4",
                        fileName = "Copy of Structural Calculations",
                        track_no = Constants.TRACK_NO,
                        image = IMAGE_4
                    )//LVZRLK9O

                    submitForm6(form6body, {
                        //4
                        Log.d("COMPLETED!!!", "4 COMPLETE")

                        val form6body = Form6Body(
                            fileUrl = "data:application/pdf;base64,$IMAGE_5",
                            fileName = "Copy of Architectural Drawing and Details",
                            track_no = Constants.TRACK_NO,
                            image = IMAGE_5
                        )//LVZRLK9O

                        submitForm6(form6body, {
                            //5
                            Log.d("COMPLETED!!!", "5 COMPLETE")

                            val form6body = Form6Body(
                                fileUrl = "data:application/pdf;base64,$IMAGE_6",
                                fileName = "Copy of Mechanical Electrical Drawings and Details",
                                track_no = Constants.TRACK_NO,
                                image = IMAGE_6
                            )//LVZRLK9O

                            submitForm6(form6body, {
                                //6
                                Log.d("COMPLETED!!!", "6 COMPLETE")

                                val form6body = Form6Body(
                                    fileUrl = "data:application/pdf;base64,$IMAGE_7",
                                    fileName = "Site Analysis Report",
                                    track_no = Constants.TRACK_NO,
                                    image = IMAGE_7
                                )//LVZRLK9O

                                submitForm6(form6body, {
                                    //7
                                    Log.d("COMPLETED!!!", "7 COMPLETE")

                                    val form6body = Form6Body(
                                        fileUrl = "data:application/pdf;base64,$IMAGE_8",
                                        fileName = "Copy of EIAR",
                                        track_no = Constants.TRACK_NO,
                                        image = IMAGE_8
                                    )//LVZRLK9O
                                    submitForm6(form6body, {
                                        //8
                                        Log.d("COMPLETED!!!", "8 COMPLETE")

                                        val form6body = Form6Body(
                                            fileUrl = "data:application/pdf;base64,$IMAGE_9",
                                            fileName = "Copy of BLOCK Plan",
                                            track_no = Constants.TRACK_NO,
                                            image = IMAGE_9
                                        )//LVZRLK9O

                                        submitForm6(form6body, {
                                            //9
                                            Log.d("COMPLETED!!!", "9 COMPLETE")

                                            val form6body = Form6Body(
                                                fileUrl = "data:application/pdf;base64,$IMAGE_10",
                                                fileName = "Soil Investigation Report",
                                                track_no = Constants.TRACK_NO,
                                                image = IMAGE_10
                                            )//LVZRLK9O

                                            submitForm6(form6body, {
                                                //10
                                                Log.d("COMPLETED!!!", "10 COMPLETE")

                                                val form6body = Form6Body(
                                                    fileUrl = "data:application/pdf;base64,$IMAGE_11",
                                                    fileName = "Copy of Service Approvals",
                                                    track_no = Constants.TRACK_NO,
                                                    image = IMAGE_11
                                                )//LVZRLK9O

                                                submitForm6(form6body, {

                                                    Log.d("COMPLETED!!!", "11 COMPLETE")

                                                    success()
                                                }) {
                                                    error(it)
                                                }


                                            }) {
                                                error(it)
                                            }

                                        }) {
                                            error(it)
                                        }


                                    }) {
                                        error(it)
                                    }

                                }) {
                                    error(it)
                                }

                            }) {
                                error(it)
                            }


                        }) {
                            error(it)
                        }


                    }) {
                        error(it)
                    }


                }) {
                    error(it)
                }


            }) {
                error(it)
            }


        }) {
            error(it)
        }


    }

    fun submitForm6(
        form6Body: Form6Body,
        success: (Form6Response?) -> Unit,
        error: (String) -> Unit
    ) {

        if (form6Body.image.isEmpty()) {

            success(null)
            return
        }



        ioDispatcher.launch {

            try {
                Log.d("LOGINS", "CLICKED")


                val response = service.form6(form6Body)  //TODO("make authorization dynamic")

                Log.d("LOGINS", "RESPONSE: $response")
                if (response.isSuccessful) {

                    mainDispatcher.launch {
                        success(response.body()!!)
                    }
                    Log.d("LOGINS", "RESPONSE BODY: ${response.body()!!}")

                } else {
                    Log.d("LOGINS", "ERROR: ${response}")
                    mainDispatcher.launch {
                        error(response.message())
                    }
                }

            } catch (e: Exception) {
                mainDispatcher.launch {
                    when (e) {
                        is UnknownHostException -> {
                            error("Error: No Internet Connection")
                        }

                        is ConnectException -> {
                            error("Api Error: No Internet Connection.")
                        }

                        is SocketTimeoutException -> {
                            error("Api Error: Timeout")
                        }

                        else -> {
                            Log.d("FORM1X", "${e.message}")
                            error("Api Error: Unknown")
                        }
                    }
                }
            }
        }


    }


}
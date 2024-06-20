package com.smartapps.jmdb.data.repository


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
import com.novaapps.jmdb.data.model.LoginBody
import com.novaapps.jmdb.data.model.LoginResponse
import com.novaapps.jmdb.data.model.StatesResponse
import com.novaapps.jmdb.data.model.TinVerificationResponse
import com.smartapps.jmdb.data.model.LGA
import com.smartapps.jmdb.data.model.TinResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {




    @POST("api/login")
    suspend fun login(
        @Body loginBody: LoginBody
    ): Response<LoginResponse>



    @GET("api/verify_tin_phone?phone=")
    suspend fun verifyTin(@Query("phone") phone: String): Response<TinVerificationResponse>


    @GET("api/verify_tin_phone?phone=")
    suspend fun tinner(@Query("phone") phone: String): Response<TinResponse>




    @GET("api/fetch_countries")
    suspend fun getCountries(): Response<CountriesResponse>



    @GET("api/fetch_states")
    suspend fun getStates(): Response<StatesResponse>


    @GET("api/fetch_identification")
    suspend fun getIdentifications(): Response<IdentificationResponse>



    @GET("api/fetch_land_use")
    suspend fun getLandUse(): Response<LandUseResponse>



    @GET("api/fetch_purpose")
    suspend fun getPurpose(): Response<LandPurposeResponse>





    @GET("api/fetch_lga?state_id=")
    suspend fun getLgas(@Query("state_id") state_id: String): Response<LgasResponse>




    @POST("api/step1")
    suspend fun form1(
        @Body form1Body: Form1Body
    ): Response<Form1Response>



    @POST("api/step2")
    suspend fun form2(
        @Body form2Body: Form2Body
    ): Response<Form2Response>



    @POST("api/step3")
    suspend fun form3(
        @Body form3Body: Form3Body
    ): Response<Form3Response>



    @POST("api/step4")
    suspend fun form4(
        @Body form4Body: Form4Body
    ): Response<Form4Response>



    @POST("api/step5")
    suspend fun form5(
        @Body form5Body: Form5Body
    ): Response<Form5Response>



    @POST("api/step6")
    suspend fun form6(
        @Body form6Body: Form6Body
    ): Response<Form6Response>





}
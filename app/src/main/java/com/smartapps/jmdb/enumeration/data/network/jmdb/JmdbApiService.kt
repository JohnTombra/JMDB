package com.smartapps.jmdb.enumeration.data.network.jmdb


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
import com.smartapps.jmdb.data.model.ABC
import com.smartapps.jmdb.data.model.MainTinResponse
import com.smartapps.jmdb.data.model.MyCompany
import com.smartapps.jmdb.data.model.MyDocument
import com.smartapps.jmdb.data.model.TinResponse
import com.smartapps.jmdb.enumeration.registrationdata.model.LoginBody
import com.smartapps.jmdb.enumeration.data.model.CountriesResponse
import com.smartapps.jmdb.enumeration.data.model.LoginBodyX
import com.smartapps.jmdb.enumeration.data.model.LoginResponseX
import com.smartapps.jmdb.enumeration.data.model.User
import com.smartapps.jmdb.enumeration.data.model.jmdb.AccessmentItemsResponse
import com.smartapps.jmdb.enumeration.data.model.jmdb.AreasResponse
import com.smartapps.jmdb.enumeration.data.model.jmdb.BuildingCategoriesResponse
import com.smartapps.jmdb.enumeration.data.model.jmdb.BuildingTypeResponse
import com.smartapps.jmdb.enumeration.data.model.jmdb.JmdbBuildingPostBody
import com.smartapps.jmdb.enumeration.data.model.jmdb.JmdbBuildingPostResponse
import com.smartapps.jmdb.enumeration.data.model.jmdb.LgaResponse
import com.smartapps.jmdb.enumeration.data.model.jmdb.StreetsResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface JmdbApiService {


    @GET("https://plateaujmdb.ng/api/verify_tin_phone?phone=")
    suspend fun vPhone(@Query("phone") phone: String): Response<TinVerificationResponse>





    @POST("https://plateaujmdb.ng/api/step1")
    suspend fun form1(
        @Body form1Body: Form1Body
    ): Response<Form1Response>






//http://3.9.45.117/OpenPaymentsApi/validate_tin/23320765908

    //http://3.9.45.117/OpenPaymentsApi/validate_phone/08136759553





    @GET("api/verify_tin_phone?phone=")
    suspend fun abcTin(@Query("phone") phone: String): Response<ABC>



    @GET("http://3.9.45.117/OpenPaymentsApi/validate_tin/{phone}"/*"api/verify_tin_phone?phone="*/)
    suspend fun abcPhone(@Path("phone") phone: String/*@Query("phone") phone: String*/): Response<ABC/*MainTinResponse*/>




    @POST("http://jmdb.eu-north-1.elasticbeanstalk.com/api/step6")
    suspend fun form6(
        @Body form6Body: Form6Body
    ): Response<Form6Response>









    @GET("api/verify_tin_phone?phone=")
    suspend fun tinner(@Query("phone") phone: String): Response<TinResponse>



    @GET("api/verify_tin_phone?phone=")
    suspend fun verifyTin(@Query("phone") phone: String): Response<TinVerificationResponse>


    @POST("api/create_buildings")
    suspend fun postBuildings(
        @Body jmdbBuildingPostBody: JmdbBuildingPostBody
    ): Response<JmdbBuildingPostResponse>



    @POST("api/login")
    suspend fun login(
        @Body loginBody: LoginBody
    ): Response<com.smartapps.jmdb.enumeration.registrationdata.model.LoginResponse>



//https://plateaujmdb.ng/
    @POST("api/generate_company_tin_psirs")
    suspend fun generateTin(
        @Body myCompany: MyCompany
    ): Response<com.smartapps.jmdb.enumeration.registrationdata.model.LoginResponse>





    //////
    //////
    //////
    //////



    @GET("api/fetch_states")
    suspend fun getStates(): Response<StatesResponse>


    @GET("api/fetch_lga?state_id=")
    suspend fun getLgas(@Query("state_id") state_id: String): Response<LgaResponse>


    @GET("api/fetch_areas?lga_id=")
    suspend fun getAreas(@Query("lga_id") lga_id: String): Response<AreasResponse>


    @GET("api/fetch_street?areaId=")
    suspend fun getStreets(@Query("areaId") areaId: String): Response<StreetsResponse>




    @GET("api/fetch_building_categories")
    suspend fun getBuildingCategories(): Response<BuildingCategoriesResponse>



    @GET("api/fetch_building_type")
    suspend fun getBuildingTypes(): Response<BuildingTypeResponse>





    @GET("api/assessment_items?page=")
    suspend fun getAssessmentItems(@Query("page") page: Int): Response<com.smartapps.jmdb.enumeration.data.model.jmdb.AccessmentItemsResponse>



/////////////......





    @GET("api/fetch_countries")
    suspend fun getCountries(): Response<com.novaapps.jmdb.data.model.CountriesResponse>



    @GET("api/fetch_states")
    suspend fun getStatesx(): Response<com.novaapps.jmdb.data.model.StatesResponse>


    @GET("api/fetch_identification")
    suspend fun getIdentifications(): Response<IdentificationResponse>



    @GET("api/fetch_land_use")
    suspend fun getLandUse(): Response<LandUseResponse>



    @GET("api/fetch_purpose")
    suspend fun getPurpose(): Response<LandPurposeResponse>





    @GET("api/fetch_lga?state_id=")
    suspend fun getLgasx(@Query("state_id") state_id: String): Response<LgasResponse>







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







}
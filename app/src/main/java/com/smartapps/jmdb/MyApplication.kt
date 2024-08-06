package com.smartapps.jmdb

import android.app.Application
import com.cloudinary.android.MediaManager
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository

class MyApplication: Application() {


    override fun onCreate() {
        super.onCreate()


        val context = applicationContext

        JmdbRepository.initializeSharedPreferences(context)
        //also read for new jmdb number here



        JmdbRepository.initilizeRoom(context)


        JmdbRepository.initializeMap(context)

        val config = HashMap<String,String>()

        config["cloud_name"] = "dwn0jrlfd"

        MediaManager.init(context, config)

    }

}
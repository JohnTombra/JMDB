package com.smartapps.jmdb.enumeration.data.local.jmdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.smartapps.jmdb.data.model.IdentityType
import com.smartapps.jmdb.data.model.LandPurpose
import com.smartapps.jmdb.data.model.LandUse
import com.smartapps.jmdb.enumeration.data.local.jmdb.dao.BuildingCategoryDao
import com.smartapps.jmdb.enumeration.data.local.jmdb.dao.BuildingTypeDao
import com.smartapps.jmdb.enumeration.data.local.jmdb.dao.IdentityTypeDao
import com.smartapps.jmdb.enumeration.data.local.jmdb.dao.JmdbAreaDao
import com.smartapps.jmdb.enumeration.data.local.jmdb.dao.JmdbBuildingDao
import com.smartapps.jmdb.enumeration.data.local.jmdb.dao.JmdbLgaDao
import com.smartapps.jmdb.enumeration.data.local.jmdb.dao.JmdbStateDao
import com.smartapps.jmdb.enumeration.data.local.jmdb.dao.JmdbStreetDao
import com.smartapps.jmdb.enumeration.data.local.jmdb.dao.LandPurposeDao
import com.smartapps.jmdb.enumeration.data.local.jmdb.dao.LandUseDao
import com.smartapps.jmdb.enumeration.data.local.jmdb.dao.RevenueItemDao
import com.smartapps.jmdb.enumeration.data.local.jmdb.dao.TagDao
import com.smartapps.jmdb.enumeration.data.local.jmdb.dao.TimeDao
import com.smartapps.jmdb.enumeration.data.model.jmdb.Area
import com.smartapps.jmdb.enumeration.data.model.jmdb.BuildingCategory
import com.smartapps.jmdb.enumeration.data.model.jmdb.BuildingType
import com.smartapps.jmdb.enumeration.data.model.jmdb.Data
import com.smartapps.jmdb.enumeration.data.model.jmdb.Lga
import com.smartapps.jmdb.enumeration.data.model.jmdb.State
import com.smartapps.jmdb.enumeration.data.model.jmdb.Street
import com.smartapps.jmdb.enumeration.data.model.jmdb.Tag
import com.smartapps.jmdb.enumeration.data.model.jmdb.Time


class MyTypeConverter {
   @TypeConverter
   fun fromListIntToString(intList: List<Int>): String = intList.toString()

   @TypeConverter
   fun toListIntFromString(stringList: String): List<Int> {
      var result = ArrayList<Int>()
      val split = stringList
         .replace("[", "")
         .replace("]", "")
         .replace(" ", "")
         .split(",")

      for (n in split) {
         try {
            result.add(n.toInt())
         } catch (e: Exception) {
         }
      }
      return result
   }
}





@TypeConverters(MyTypeConverter::class)
@Database(entities = [Data::class, Street::class, Lga::class, BuildingCategory::class, State::class, Area::class, BuildingType::class, com.smartapps.jmdb.data.model.Result::class, LandPurpose::class, LandUse::class, IdentityType::class,  Time::class, Tag::class], version = 6)
abstract class JmdbMyRoomDatabase: RoomDatabase() {

   abstract fun tagDao(): TagDao

   abstract fun timeDao(): TimeDao
   abstract fun buildingDao(): JmdbBuildingDao

   abstract fun streetDao(): JmdbStreetDao
   abstract fun buildingCategoryDao(): BuildingCategoryDao
   abstract fun buildingTypeDao(): BuildingTypeDao
   abstract fun areaDao(): JmdbAreaDao
   abstract fun lgaDao(): JmdbLgaDao
   abstract fun stateDao(): JmdbStateDao
   abstract fun revenueItemDao(): RevenueItemDao
   abstract fun landPurposeDao(): LandPurposeDao
   abstract fun landUseDao(): LandUseDao
   abstract fun identityTypeDao(): IdentityTypeDao
}
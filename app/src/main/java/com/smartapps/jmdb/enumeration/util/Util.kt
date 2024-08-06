package com.smartapps.jmdb.enumeration.util

import java.util.Calendar


enum class TOKENS {

    THIS_YEAR, LAST_YEAR, THIS_MONTH, LAST_MONTH, THIS_WEEK, LAST_WEEK, TODAY, YESTERDAY, NONE

}
class Util {

    companion object {

        fun getDate(): String{



                val calender = Calendar.getInstance()

                val year = calender.get(Calendar.YEAR)
                val month = calender.get(Calendar.MONTH)
                val day = calender.get(Calendar.DAY_OF_MONTH)

                val weekOfMonth = calender.get(Calendar.WEEK_OF_MONTH)


              return "$year,$month,$day,$weekOfMonth,${System.currentTimeMillis()}"



        }








        fun messagesDateParser(date: String): Map<String, String>{



               val dateTime = date.split(",")

                val year = dateTime[0]
                val month = dateTime[1]
                val day =  dateTime[2]
                val weekOfMonth = dateTime[3]


                val now = getDate().split(",")

                val _year = now[0]
                val _month = now[1]
                val _day =  now[2]
                val _weekOfMonth = now[3]



                val difference = _day.toInt() - day.toInt()


                var token = mutableMapOf<String,String>(Pair("year", TOKENS.NONE.toString()),Pair("month",
                    TOKENS.NONE.toString()),Pair("week", TOKENS.NONE.toString()),Pair("day",
                    TOKENS.NONE.toString()))



                if(_year == year){

                    token["year"] = TOKENS.THIS_YEAR.toString()



                    if(_month == month){
                        token["month"] =  TOKENS.THIS_MONTH.toString()



                        if(_weekOfMonth == weekOfMonth){
                            token["week"] = TOKENS.THIS_WEEK.toString()

                        if(_day == day){
                            token["day"] = TOKENS.TODAY.toString()
                        }else if(difference == 1){
                            token["day"] = TOKENS.YESTERDAY.toString()
                        }

                        }else if((_weekOfMonth.toInt() -  weekOfMonth.toInt()) == 1){
                            token["week"] =  TOKENS.LAST_WEEK.toString()
                        }





                    }else if((_month.toInt() - month.toInt()) == 1){
                        token["month"] = TOKENS.LAST_MONTH.toString()
                    }







                }else if((_year.toInt() - year.toInt()) == 1){
                    token["year"] = TOKENS.LAST_YEAR.toString()
                }


            return token
        }



    }



}



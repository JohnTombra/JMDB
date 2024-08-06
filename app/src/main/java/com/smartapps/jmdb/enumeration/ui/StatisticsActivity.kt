package com.smartapps.jmdb.enumeration.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.smartapps.jmdb.R
import com.smartapps.jmdb.enumeration.util.TOKENS
import com.smartapps.jmdb.enumeration.util.Util
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository

class StatisticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)


        val barChart = findViewById<BarChart>(R.id.barChart)


        findViewById<CardView>(R.id.back).setOnClickListener {
            onBackPressed()
        }


        val repository = JmdbRepository

        val _lastYear = findViewById<TextView>(R.id.lastYear)
        val _thisYear = findViewById<TextView>(R.id.thisYear)
        val _lastMonth = findViewById<TextView>(R.id.lastMonth)
        val _thisMonth = findViewById<TextView>(R.id.thisMonth)

        val _lastWeek = findViewById<TextView>(R.id.lastWeek)
        val _thisWeek = findViewById<TextView>(R.id.thisWeek)

        val _yesterday = findViewById<TextView>(R.id.yesterday)
        val _today = findViewById<TextView>(R.id.today)


        var today = 0
    //    var yesterday = 0
        var thisWeek = 0
     //   var lastWeek = 0
        var thisMonth = 0
     //   var lastMonth = 0
        var thisYear = 0
   //     var lastYear = 0




        repository.getTimes {

            Log.d("STATS", "$it")

            it.forEach {
                val tokens = Util.messagesDateParser(it.timeId)
                for (token in tokens) {
                    when (token.value) {
                        TOKENS.THIS_YEAR.toString() -> thisYear++
                     //   TOKENS.LAST_YEAR.toString() -> lastYear++
                        TOKENS.THIS_MONTH.toString() -> thisMonth++
                     //   TOKENS.LAST_MONTH.toString() -> lastMonth++
                        TOKENS.THIS_WEEK.toString() -> thisWeek++
                     //   TOKENS.LAST_WEEK.toString() -> lastWeek++
                        TOKENS.TODAY.toString() -> today++
                     //   TOKENS.YESTERDAY.toString() -> yesterday++
                    }

                }
            }


       //     _lastYear.text = "Last year :" + lastYear.toString()
            _thisYear.text = "This year :" + thisYear.toString()

        //    _lastMonth.text = "Last month :" + lastMonth.toString()
            _thisMonth.text = "This month :" + thisMonth.toString()

        //    _lastWeek.text = "Last week :" + lastWeek.toString()
            _thisWeek.text = "This week :" + thisWeek.toString()

        //    _yesterday.text = "Yesterday :" + yesterday.toString()
            _today.text = "Today :" + today.toString()



            val entries = mutableListOf<BarEntry>(
                BarEntry(0f, today.toFloat()),
                BarEntry(1f, thisWeek.toFloat()),
                BarEntry(2f, thisMonth.toFloat()),
                BarEntry(3f, thisYear.toFloat())
        /*        BarEntry(4f, yesterday.toFloat()),
                BarEntry(5f, lastWeek.toFloat()),
                BarEntry(6f, lastMonth.toFloat()),
                BarEntry(7f, lastYear.toFloat())*/
            )

            val dataSet = BarDataSet(entries, "")

            val data = BarData(dataSet)

            barChart.data = data

            barChart.description.text = ""

            var xAxis = barChart.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(
                listOf(
                    "Today",
                    "This week",
                    "This month",
                    "This year",
                    "Yesterday",
                    "Last week",
                    "Last month",
                    "Last year"
                )
            )
            //   xAxis.setCenterAxisLabels(true)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setGranularity(1f)
            xAxis.setGranularityEnabled(true)
            barChart.setDragEnabled(true)
            barChart.setVisibleXRangeMaximum(6f)

            data.barWidth = 0.7f
            //     barChart.setBackgroundColor(R.color.myGreen)
            //       barChart.xAxis.axisMinimum = 0.3f
            //    barChart.xAxis.bar


            barChart.animateY(1000)


        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}
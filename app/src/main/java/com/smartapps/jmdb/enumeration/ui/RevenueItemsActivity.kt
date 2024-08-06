package com.smartapps.jmdb.enumeration.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartapps.jmdb.R
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.enumeration.ui.adapters.BuildingAdapter
import com.smartapps.jmdb.enumeration.ui.adapters.RevenueAdapter
import com.smartapps.jmdb.data.model.Result
import com.smartapps.jmdb.enumeration.util.Constants

class RevenueItemsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revenue_items)


        val context = this



        val repository = JmdbRepository

        val recyclerView = findViewById<RecyclerView>(R.id.items)
        val searchBox = findViewById<EditText>(R.id.searchBox)


        val clearAll = findViewById<TextView>(R.id.clearAll)
        val cancel = findViewById<TextView>(R.id.cancel)
        val ok = findViewById<TextView>(R.id.ok)

        Constants.SELECTED_ITEMS = mutableListOf()

        var revenueItems = listOf<Result>()
        var revenueItemsCache = listOf<Result>()

        val itemsAdapter = RevenueAdapter({
val itemId = revenueItems[it].assessment_item_id
            Constants.SELECTED_ITEMS.add(itemId)
            Log.d("RESULSTS","ID $itemId")
            Log.d("RESULSTS","ADDED ${Constants.SELECTED_ITEMS}")
        }){
            val itemId = revenueItems[it].assessment_item_id
            Constants.SELECTED_ITEMS.remove(itemId)
            Log.d("RESULSTS","ID $itemId")
            Log.d("RESULSTS","REMOVED ${Constants.SELECTED_ITEMS}")
        }

        recyclerView.apply {
            adapter = itemsAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }



        Log.d("RESULSTS","ITEM IN")

        repository.getRevenueItems {
            Log.d("RESULSTS","ITEMS $it")
            revenueItemsCache = it
            revenueItems = it
            itemsAdapter.submitList(it)
        }



        cancel.setOnClickListener {
            onBackPressed()
        }


        clearAll.isVisible = false
        clearAll.setOnClickListener {
            revenueItems = revenueItemsCache
            itemsAdapter.submitList(revenueItemsCache)
        }



        val intent = Intent()
        intent.putExtra("resultKey","...")
        ok.setOnClickListener {
           setResult(RESULT_OK, intent)
            finish()
        }


        searchBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                repository.searchRevenueItems(searchBox.text.toString()) {
                    Log.d("SEARCH","$it")
                    revenueItems = it
                    itemsAdapter.submitList(it)
                }
            }
        })


        //add search feature here










    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
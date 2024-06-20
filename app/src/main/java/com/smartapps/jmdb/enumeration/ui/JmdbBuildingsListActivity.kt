package com.smartapps.jmdb.enumeration.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartapps.jmdb.R
import com.smartapps.jmdb.databinding.ActivityBuildingsListBinding
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository

import com.smartapps.jmdb.enumeration.model.jmdb.Data

import com.smartapps.jmdb.enumeration.ui.adapters.BuildingAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JmdbBuildingsListActivity : AppCompatActivity() {


    lateinit var repository: JmdbRepository

    lateinit var buildings: List<Data>

    lateinit var buildingAdapter: BuildingAdapter

    lateinit var binding: ActivityBuildingsListBinding

    var i = 0

    var s = false

    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuildingsListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        context = this

        repository = JmdbRepository(this)

        buildings = listOf<Data>()

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.synced.text = "Synced: ..."
        binding.notSynced.text = "Not Synced: "+ "..."

        binding.ok.setOnClickListener {
            sync() {}
            binding.completeCard.isVisible = false
        }


        buildingAdapter = BuildingAdapter {
//            Log.d("TRACKER", "building ----${buildings[it].copy(building_image = "opps")}")
            startActivity(
                Intent(this, JmdbBuildingInfoActivity::class.java).putExtra(
                    "id",
                    buildings[it].building_id
                )
            )
        }



        binding.buildingsRecycler.apply {
            adapter = buildingAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }





        binding.neww.setOnClickListener {

            Log.d("Tracker","Clicked")

            repository.getSyncables{ items ->

                Log.d("Tracker","Clicked $items")

                CoroutineScope(Dispatchers.Main).launch {
                    AlertDialog.Builder(context).setMessage("$items building${if(items == 1){""}else{"s"}} ready to be synced")
                        .setPositiveButton(if(items == 0){"Ok"}else{"Proceed"}, object : DialogInterface.OnClickListener {
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                if(items == 0){
                                    p0!!.dismiss()
                                }else{

                                    //show syncing dialog
                                    binding.syncingLayout.isVisible = true
                                    repository.syncAllBuildings({
                                        CoroutineScope(Dispatchers.Main).launch {
                                            binding.syncingLayout.isVisible = false
                                            Toast.makeText(context,it, Toast.LENGTH_SHORT).show()
                                            binding.completeCard.isVisible = true
                                            binding.status.text = "Error"
                                            binding.message.text = it
                                            binding.statusIndicator.setImageResource(R.drawable.baseline_close_24)
                                        }
                                    }){
                                        CoroutineScope(Dispatchers.Main).launch {
                                            sync() {}
                                            //add synced success dialog
                                            binding.completeCard.isVisible = true
                                            binding.syncingLayout.isVisible = false
                                            binding.status.text = "Success"
                                            binding.message.text = "Successfully synced $items building${if(items == 1){""}else{"s"}}"
                                            binding.statusIndicator.setImageResource(R.drawable.round_verified_24)
                                        }
                                    }
                                }
                            }
                        }).setNegativeButton(if(items == 0){""}else{"Cancel"}, object : DialogInterface.OnClickListener {
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                p0!!.dismiss()
                            }
                        }).show()
                }


           }





        }


        binding.searchBar2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                repository.searchBuildings(binding.searchBar2.text.toString()) {
                    Log.d("SEARCH","$it")
                    buildings = it
                    buildingAdapter.submitList(it)

                }
            }
        })




        binding.options.setOnClickListener {
            if (i == 0) {
                i = 1
                binding.optionImage.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                binding.optionsLayout.isVisible = true
            } else {
                i = 0
                binding.optionImage.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                binding.optionsLayout.isVisible = false
            }
        }





//        binding.add.setOnClickListener {
//            //     startActivity(Intent(this, NewBuildingActivity::class.java))
//        }




        binding.sync.setOnClickListener {
            //   Toast.makeText(this,"Syncing", Toast.LENGTH_SHORT).show()
            s = true
            sync() {
                Toast.makeText(this, "Synced", Toast.LENGTH_SHORT).show()
            }
        }

        binding.pull.setOnClickListener {
            //     Toast.makeText(this,"Pulling", Toast.LENGTH_SHORT).show()
            s = true
            sync() {
                Toast.makeText(this, "Pulled", Toast.LENGTH_SHORT).show()
            }
        }


    //   sync() {}


//        binding.search.setOnClickListener {
////            repository.searchBuildings(binding.searchBar.text.toString()){
////                buildingAdapter.submitList(it)
////            }
//        }





    }


    override fun onResume() {
        super.onResume()
        sync() {}
    }



    private fun sync(callback: () -> Unit) {



        repository.getAllBuildingsLocally({
           binding.add.isVisible = false
            binding.progress.isVisible = true
            binding.buildingsRecycler.isVisible = false
            //loading
        }, {
            val dt = it
            CoroutineScope(Dispatchers.Main).launch {


                object: CountDownTimer(500,1000){
                    override fun onTick(p0: Long) {
                    }
                    override fun onFinish() {
                        if (dt.isEmpty()) {
                            binding.add.isVisible = true}
                        binding.progress.isVisible = false


                        repository.getSyncableBuildingsAndTotal{ total,syncable ->
                            CoroutineScope(Dispatchers.Main).launch {
                                binding.synced.text = "Synced: ${total - syncable}"
                                binding.notSynced.text = "Not Synced: "+ syncable.toString()
                            }
                        }

                        if (s) {
                            s = false
                            callback()
                        }

                        //success
                        if (it.isEmpty()) {

//                binding.searchBar.isEnabled = false
//                binding.searchBar.setText("BUILDINGS")
                        } else {
                            Log.d("TRACER","DATA IN" + it.size)


                            binding.buildingsRecycler.isVisible = true
                            //  binding.add.isVisible = false
                            buildings = it.reversed()
                            buildingAdapter.submitList(it.reversed())


                        }
                    }
                }.start()



        }

        }) {
            binding.progress.isVisible = false
            Toast.makeText(this, "Error $it", Toast.LENGTH_SHORT).show()
            //error
            //error
        }


    }


    override fun onPause() {
        super.onPause()
        binding.optionsLayout.isVisible = false
        i = 0
        binding.optionImage.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)

    }


}
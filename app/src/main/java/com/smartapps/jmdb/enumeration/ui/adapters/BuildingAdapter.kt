package com.smartapps.jmdb.enumeration.ui.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smartapps.jmdb.R
import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.databinding.BuildingBinding
import com.smartapps.jmdb.enumeration.model.jmdb.Data
import com.smartapps.jmdb.enumeration.model.jmdb.Street

import com.smartapps.jmdb.enumeration.ui.prefix
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BuildingAdapter(val callback: (Int)-> Unit) :
    ListAdapter<Data, BuildingAdapter.ChatViewHolder>(DiffCallBack()) {

    lateinit var context: Context


    lateinit var repository: JmdbRepository

    data class MyStreet(
        val street_id: String,
        val street: String,
    )
    lateinit var streets: MutableList<Street>

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ChatViewHolder {

        context = viewGroup.context
        repository = JmdbRepository(context)
        val inflater = LayoutInflater.from(context)
        val binding = BuildingBinding.inflate(inflater, viewGroup, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {




        with(holder) {


            binding.time.text = ""
            binding.buildingType.text = ""
            binding.phoneNumber.text = ""
            binding.buildingCategory.text = ""
            binding.buildingSize.text = ""
            binding.address.text = ""

            with(getItem(position)) {

                binding.image.setImageResource(R.color.mydrk)

//                Glide.with(context).load("").placeholder(R.color.black).centerCrop().fitCenter()
//                    .into(binding.image)

                binding.time.text = ""
                binding.buildingType.text = ""
                binding.phoneNumber.text = ""
                binding.buildingCategory.text = ""
                binding.buildingSize.text = ""
                binding.address.text = ""


                binding.indicator.isVisible = false//setCardBackgroundColor(Color.TRANSPARENT)


//                Glide.with(context).load(building_image).placeholder(R.color.black).centerCrop().fitCenter()
//                    .into(binding.image)

                try{
                    val byte = Base64.decode(building_image.replaceFirst(prefix,""), Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(byte, 0, byte.size)
                    binding.image.setImageBitmap(bitmap)
                }catch (e: Exception){

                }

                var types: List<com.smartapps.jmdb.enumeration.model.jmdb.BuildingType> = listOf()
                var category: List<com.smartapps.jmdb.enumeration.model.jmdb.BuildingCategory> = listOf()
                var street: List<Street> = listOf()

                CoroutineScope(Dispatchers.IO).launch {


                    repository.checkBuildingSyncStatus(building_id){
                            CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                                if (it) {

                                    binding.indicator.isVisible = true//setCardBackgroundColor(Color.GREEN)
                                } else {
                                    binding.indicator.isVisible = false//.setCardBackgroundColor(Color.DKGRAY)
                                }
                            }
                        }



                    types = repository.getBuildingTypes()
                    category =  repository.getBuildingCategories()

              //      val area = repository.getAreas(lga.toString())


                    Log.d("MYSTREETIDS","$street_id")




                    CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {

                        try {
                            binding.address.text = repository.getStreets2().toMutableList()
                                .filter { it.idstreet == street_id }[0].street
                        }catch (e:Exception){

                        }
                        binding.buildingType.text = building_name
                        binding.phoneNumber.text =  owner_mobile_no
                      binding.buildingCategory.text = category.filter { it.idbuilding_category.toString() == building_category_id.toString() }[0].building_category
                        binding.buildingSize.text = owner_name



//
//                        val strs = streetWithWard.filter { it.wardId == state_id.toString() }[0].streets
//
//
//                        streets = mutableListOf()
//
//                        strs.split("##").forEach {
//
//                            if(it.isNotEmpty()){
//
//                                Log.d("TRACER", "STREET: ${it}" )
//                                try {
//                                    val inner = it.split("@@")
//                                    streets.add(
//                                        MyStreet(
//                                            inner[0],
//                                            inner[1],
//                                        )
//                                    )
//
//
//                                } catch (e: Exception) {
//                                    Log.d("TRACER", "${e.message.toString()}" )
//                                } }
//                        }
//                        binding.address.setText(streets.filter { it.street_id == street_id.toString() }[0].street)

                    }
                }



            //    binding.address.text = repository._getWards().filter { it.city_id.toString() == state_id.toString() }[0].city

            }
        }
    }


    class DiffCallBack() : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data) =
            oldItem.building_number == newItem.building_number

        override fun areContentsTheSame(oldItem: Data, newItem: Data) =
            oldItem == newItem
    }

    inner class ChatViewHolder(val binding: BuildingBinding) : RecyclerView.ViewHolder(binding.root){


        init{
            binding.root.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                        callback(position)
                }
            }
        }


    }

}
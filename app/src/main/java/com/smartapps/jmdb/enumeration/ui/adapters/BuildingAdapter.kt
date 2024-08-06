package com.smartapps.jmdb.enumeration.ui.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
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
import com.smartapps.jmdb.enumeration.data.model.jmdb.Data
import com.smartapps.jmdb.enumeration.data.model.jmdb.Street
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BuildingAdapter(val callback: (Int) -> Unit) :
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
        repository = JmdbRepository
        val inflater = LayoutInflater.from(context)
        val binding = BuildingBinding.inflate(inflater, viewGroup, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {


        with(holder) {


            binding.time.text = ""
            binding.area.text = ""
            binding.lga.text = ""
            binding.tag.text = ""
            binding.tin.text = ""
            binding.address.text = ""

            with(getItem(position)) {

                binding.image.setImageResource(R.color.mydrk)

//                Glide.with(context).load("").placeholder(R.color.black).centerCrop().fitCenter()
//                    .into(binding.image)

                binding.time.text = ""
                binding.area.text = ""
                binding.lga.text = ""
                binding.tag.text = ""
                binding.tin.text = ""
                binding.address.text = ""


                binding.indicator.isVisible = false//setCardBackgroundColor(Color.TRANSPARENT)


//                try {
//                    val byte =
//                        Base64.decode(building_image.replaceFirst(prefix, ""), Base64.DEFAULT)
//                    val bitmap = BitmapFactory.decodeByteArray(byte, 0, byte.size)
//
//

                    Glide.with(context).load(Uri.parse(building_image)).placeholder(R.color.mydrk).centerCrop().fitCenter()
                  .into(binding.image)

//
//
//                } catch (e: Exception) {
//
//                }




                try {
                repository.checkBuildingSyncStatus(building_id) {
                    CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                        if (it) {
                            binding.indicator.isVisible = true//setCardBackgroundColor(Color.GREEN)
                        } else {
                            binding.indicator.isVisible =
                                false//.setCardBackgroundColor(Color.DKGRAY)
                        }
                    }
                }
            } catch (e: Exception) {

        }

                try {
                    repository.getStreets2 {
                        binding.address.text = it.filter { it.idstreet == street_id }[0].street
                    }
                } catch (e: Exception) {
                    binding.address.text = "..."
                }

                binding.tag.text = owner_email
                binding.area.text = owner_mobile_no


                try {
                repository.getAreas("0") {
                    binding.area.setText(it.filter { it.id == ward }[0].area)
                }
            } catch (e: Exception) {
                    binding.area.setText("...")
        }

                try {
                repository.getLgas {
                    binding.lga.setText(it.filter { it.lga_id == lga }[0].lga)
                }
            } catch (e: Exception) {
                    binding.lga.setText("...")
        }


                binding.tin.text = if (tin.isEmpty()) {
                    "Tin not set"
                } else {
                    tin
                }


            }
        }
    }

    //multiple buildings
    //street name issue
    //ability to create users
    class DiffCallBack() : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data) =
            oldItem.building_number == newItem.building_number

        override fun areContentsTheSame(oldItem: Data, newItem: Data) =
            oldItem == newItem
    }

    inner class ChatViewHolder(val binding: BuildingBinding) :
        RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    callback(position)
                }
            }
        }


    }

}
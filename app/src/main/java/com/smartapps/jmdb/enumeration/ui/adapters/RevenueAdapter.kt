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
import com.smartapps.jmdb.data.model.Result
import com.smartapps.jmdb.databinding.RevenueitemBinding
import com.smartapps.jmdb.enumeration.data.model.jmdb.Street
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RevenueAdapter(val addd: (Int)-> Unit,val removee: (Int)-> Unit) :
    ListAdapter<Result, RevenueAdapter.ChatViewHolder>(DiffCallBack()) {

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
        val binding = RevenueitemBinding.inflate(inflater, viewGroup, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {




        with(holder) {

            binding.checkBox.text = ""

            with(getItem(position)) {


                binding.checkBox.text = this.assessment_item_name
                //add checked change listener here



            }
        }
    }

//multiple buildings
    //street name issue
    //ability to create users
    class DiffCallBack() : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result) =
            oldItem.assessment_item_id == newItem.assessment_item_id

        override fun areContentsTheSame(oldItem: Result, newItem: Result) =
            oldItem == newItem
    }

    inner class ChatViewHolder(val binding: RevenueitemBinding) : RecyclerView.ViewHolder(binding.root){


        init{

            binding.checkBox.setOnCheckedChangeListener{_,isChecked ->
                val position: Int = adapterPosition
                Log.d("RESULSTS","CHANGED")
                if(isChecked){
                    addd(position)
                }else{
                    removee(position)
                }
            }

        }


    }

}
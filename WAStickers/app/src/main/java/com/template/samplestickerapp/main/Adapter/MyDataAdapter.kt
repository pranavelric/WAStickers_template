package com.template.samplestickerapp.main.Adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.template.samplestickerapp.R
import com.template.samplestickerapp.databinding.ItemMyDataListBinding
import com.template.samplestickerapp.main.data.model.App

class MyDataAdapter() :
        ListAdapter<App, MyDataAdapter.MyViewHolder>(AnimeItemDiffCallback()) {




    inner class MyViewHolder(private val binding: ItemMyDataListBinding) :
            RecyclerView.ViewHolder(binding.root) {
        var myDataItemAdapter: MyDataItemAdapter = MyDataItemAdapter()

        fun bind(more: App) {
            binding.setVariable(BR.app, more)
            binding.executePendingBindings()

            more.more_apps?.let {
                Log.d("RRR", "bind:moreapp size ${it.size}")
                myDataItemAdapter.submitList(it)
            }


            binding.rvMydata.apply {
                adapter = myDataItemAdapter
            }
            myDataItemAdapter.setOnItemClickListener { moreApp ->
                val moreintent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(moreApp.app_link)
                )
                binding.root.context.startActivity(moreintent)
            }
//            binding.myDataCard.setOnClickListener {
//                onItemClickListener?.let { click ->
//                    click(more)
//                }
//
//            }
        }


    }


    class AnimeItemDiffCallback : DiffUtil.ItemCallback<App>() {
        override fun areItemsTheSame(oldItem: App, newItem: App): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: App, newItem: App): Boolean {
            return oldItem.name == newItem.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemMyDataListBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_my_data_list,
                parent,
                false
        )

        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.bind(getItem(position))


    }


    private var onItemClickListener: ((App) -> Unit)? = null

    fun setOnItemClickListener(listener: (App) -> Unit) {
        onItemClickListener = listener
    }


}
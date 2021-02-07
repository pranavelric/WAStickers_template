package com.template.samplestickerapp.main.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.template.samplestickerapp.R
import com.template.samplestickerapp.databinding.ItemMyDataBinding
import com.template.samplestickerapp.databinding.ItemMyDataListBinding
import com.template.samplestickerapp.main.data.model.App
import com.template.samplestickerapp.main.data.model.MoreApps

class MyDataItemAdapter() :
        ListAdapter<MoreApps, MyDataItemAdapter.MyViewHolder>(AnimeItemDiffCallback()) {

    inner class MyViewHolder(private val binding: ItemMyDataBinding) :
            RecyclerView.ViewHolder(binding.root) {


        fun bind(more: MoreApps) {
            binding.setVariable(BR.moreApp, more)
            binding.executePendingBindings()
            binding.rating.rating = more.app_rating?.toFloat() ?: 0.0F


            binding.myDataCard.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(more)
                }

            }


        }


    }


    class AnimeItemDiffCallback : DiffUtil.ItemCallback<MoreApps>() {
        override fun areItemsTheSame(oldItem: MoreApps, newItem: MoreApps): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MoreApps, newItem: MoreApps): Boolean {
            return oldItem.app_link == newItem.app_link
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemMyDataBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_my_data,
                parent,
                false
        )

        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.bind(getItem(position))


    }


    private var onItemClickListener: ((MoreApps) -> Unit)? = null

    fun setOnItemClickListener(listener: (MoreApps) -> Unit) {
        onItemClickListener = listener
    }


}
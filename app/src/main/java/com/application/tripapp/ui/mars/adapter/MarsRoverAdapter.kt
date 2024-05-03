package com.application.tripapp.ui.mars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.application.tripapp.databinding.ItemRoverPictureBinding
import com.application.tripapp.model.MarsImage

class MarsRoverAdapter (private val onPictureClick: (id: String) -> Unit) :
    ListAdapter<MarsImage, MarsRoverViewHolder>(object :
        DiffUtil.ItemCallback<MarsImage>() {
        override fun areItemsTheSame(oldItem: MarsImage, newItem: MarsImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsImage, newItem: MarsImage): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsRoverViewHolder {
        return MarsRoverViewHolder(
            ItemRoverPictureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MarsRoverViewHolder, position: Int) {
        holder.bind(getItem(position), onPictureClick)
    }
}

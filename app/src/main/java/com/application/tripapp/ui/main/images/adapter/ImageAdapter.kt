package com.application.tripapp.ui.main.images.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.application.tripapp.databinding.ItemPicturesBinding
import com.application.tripapp.model.Picture

class ImageAdapter(private val onPictureClick: (id: String) -> Unit) : PagingDataAdapter<Picture, ImageViewHolder>(
    object : DiffUtil.ItemCallback<Picture>(){
        override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem == newItem
        }

    }
) {
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onPictureClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemPicturesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
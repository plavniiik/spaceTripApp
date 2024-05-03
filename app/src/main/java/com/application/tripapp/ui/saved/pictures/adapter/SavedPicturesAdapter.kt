package com.application.tripapp.ui.saved.pictures.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.application.tripapp.databinding.ItemPictureBinding
import com.application.tripapp.db.PictureEntity

class SavedPicturesAdapter(private val onPictureClick: (id: String) -> Unit) :
    ListAdapter<PictureEntity, SavedPicturesViewHolder>(object :
        DiffUtil.ItemCallback<PictureEntity>() {
        override fun areItemsTheSame(oldItem: PictureEntity, newItem: PictureEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PictureEntity, newItem: PictureEntity): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedPicturesViewHolder {
        return SavedPicturesViewHolder(
            ItemPictureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SavedPicturesViewHolder, position: Int) {
        holder.bind(getItem(position), onPictureClick)
    }
}

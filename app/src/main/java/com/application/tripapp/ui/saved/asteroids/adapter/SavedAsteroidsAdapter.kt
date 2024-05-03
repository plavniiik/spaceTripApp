package com.application.tripapp.ui.saved.asteroids.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.application.tripapp.databinding.ItemMyAsteroidsBinding
import com.application.tripapp.db.AsteroidEntity

class SavedAsteroidsAdapter(private val onPictureClick: (id: Long) -> Unit) :
    ListAdapter<AsteroidEntity, SavedAsteroidsViewHolder>(object :
        DiffUtil.ItemCallback<AsteroidEntity>() {
        override fun areItemsTheSame(oldItem: AsteroidEntity, newItem: AsteroidEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AsteroidEntity, newItem: AsteroidEntity): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedAsteroidsViewHolder {
        return SavedAsteroidsViewHolder(
            ItemMyAsteroidsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SavedAsteroidsViewHolder, position: Int) {
        holder.bind(getItem(position), onPictureClick)
    }
}

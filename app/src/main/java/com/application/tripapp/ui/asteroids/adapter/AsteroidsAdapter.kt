package com.application.tripapp.ui.mars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.application.tripapp.databinding.ItemAsteroidBinding
import com.application.tripapp.databinding.ItemRoverPictureBinding
import com.application.tripapp.model.Asteroid
import com.application.tripapp.model.MarsImage

class AsteroidsAdapter (private val onPictureClick: (id: String) -> Unit) :
    ListAdapter<Asteroid, AsteroidsViewHolder>(object :
        DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidsViewHolder {
        return AsteroidsViewHolder(
            ItemAsteroidBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AsteroidsViewHolder, position: Int) {
        holder.bind(getItem(position), onPictureClick)
    }
}

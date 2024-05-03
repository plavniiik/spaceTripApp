package com.application.tripapp.ui.mars.adapter

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.application.tripapp.R
import com.application.tripapp.databinding.ItemAsteroidBinding
import com.application.tripapp.model.Asteroid
import com.application.tripapp.model.MarsImage
import com.bumptech.glide.Glide

class AsteroidsViewHolder(private val binding: ItemAsteroidBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(asteroid: Asteroid, onPictureClick: (id: String) -> Unit) {
        binding.run {
            toDiscover.setOnClickListener {
                onPictureClick(asteroid.id.toString())
            }
            title.text = asteroid.name
            absoluteMagnitude.text =
                binding.root.context.getString(R.string.magnitude) + asteroid.absolute_magnitude_h.toString()
            diameter.text =
                binding.root.context.getString(R.string.diameter) + asteroid.estimated_diameter_max.toString()
            if (!asteroid.is_potentially_hazardous_asteroid) {
                pottentHazardous.setImageResource(R.drawable.white_asteroid)
            }
            if (asteroid.is_potentially_hazardous_asteroid) {
                pottentHazardous.setImageResource(R.drawable.red_asteroid)
            }
        }

    }
}
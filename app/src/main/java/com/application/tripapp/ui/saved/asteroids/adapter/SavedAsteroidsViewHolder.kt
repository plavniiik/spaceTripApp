package com.application.tripapp.ui.saved.asteroids.adapter

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.application.tripapp.R
import com.application.tripapp.databinding.ItemMyAsteroidsBinding
import com.application.tripapp.db.AsteroidEntity

class SavedAsteroidsViewHolder (private val binding: ItemMyAsteroidsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(asteroid: AsteroidEntity, onPictureClick: (id: Long) -> Unit) {
        binding.like.setOnClickListener {
            onPictureClick(asteroid.id)
        }
        binding.title.text = asteroid.name
        binding.absoluteMagnitude.text = binding.root.context.getString(R.string.magnitude) + asteroid.absolute_magnitude_h.toString()
        binding.diameter.text = binding.root.context.getString(R.string.diameter) + asteroid.estimated_diameter_max.toString()
        binding.url.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(asteroid?.nasa_jpl_url)
            binding.root.context.startActivity(openURL)
        }
        if(!asteroid.is_potentially_hazardous_asteroid){
            binding.pottentHazardous.setImageResource(R.drawable.white_asteroid)
        }
        if(asteroid.is_potentially_hazardous_asteroid){
            binding.pottentHazardous.setImageResource(R.drawable.red_asteroid)
        }
    }
}
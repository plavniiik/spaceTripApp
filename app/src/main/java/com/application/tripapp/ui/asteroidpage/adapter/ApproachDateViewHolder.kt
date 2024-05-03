package com.application.tripapp.ui.asteroidpage.adapter

import androidx.recyclerview.widget.RecyclerView
import com.application.tripapp.R
import com.application.tripapp.databinding.ItemCloseApproachDateBinding
import com.application.tripapp.network.asteroid.CloseApproachData

class ApproachDateViewHolder(private val binding: ItemCloseApproachDateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(closeApproachData: CloseApproachData) {
        binding.run {
            closeApproachDateFull.text = closeApproachData.close_approach_date_full
            orbitingBody.text = closeApproachData.orbiting_body
            relativeVelocity.text =
                closeApproachData.relative_velocity.kilometers_per_second + binding.root.context.getString(
                    R.string.km_s
                )
        }

    }
}
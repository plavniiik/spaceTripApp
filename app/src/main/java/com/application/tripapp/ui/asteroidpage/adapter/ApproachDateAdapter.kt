package com.application.tripapp.ui.asteroidpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.application.tripapp.databinding.ItemCloseApproachDateBinding
import com.application.tripapp.network.asteroid.CloseApproachData


class ApproachDateAdapter (private val onPictureClick: (id: String) -> Unit) :
    ListAdapter<CloseApproachData, ApproachDateViewHolder>(object :
        DiffUtil.ItemCallback<CloseApproachData>() {
        override fun areItemsTheSame(oldItem: CloseApproachData, newItem: CloseApproachData): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: CloseApproachData, newItem: CloseApproachData): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApproachDateViewHolder {
        return ApproachDateViewHolder(
            ItemCloseApproachDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ApproachDateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

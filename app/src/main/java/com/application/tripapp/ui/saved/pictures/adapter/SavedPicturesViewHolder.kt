package com.application.tripapp.ui.saved.pictures.adapter

import androidx.recyclerview.widget.RecyclerView
import com.application.tripapp.databinding.ItemPictureBinding
import com.application.tripapp.db.PictureEntity
import com.bumptech.glide.Glide

class SavedPicturesViewHolder(private val binding: ItemPictureBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(picture: PictureEntity, onPictureClick: (id: String) -> Unit) {
        binding.title.text = picture.title
        binding.imageView.setOnClickListener {
            onPictureClick(picture.id)
        }
        binding?.imageView.let {
            if (it != null) {
                Glide.with(binding.root.context)
                    .load(picture.url)
                    .into(it)
            }
        }
    }
}
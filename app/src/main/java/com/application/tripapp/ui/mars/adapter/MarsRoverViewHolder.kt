package com.application.tripapp.ui.mars.adapter

import androidx.recyclerview.widget.RecyclerView
import com.application.tripapp.databinding.ItemPictureBinding
import com.application.tripapp.databinding.ItemRoverPictureBinding
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.model.MarsImage
import com.bumptech.glide.Glide

class MarsRoverViewHolder (private val binding: ItemRoverPictureBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(picture: MarsImage, onPictureClick: (id: String) -> Unit) {
        binding.title.text = picture.full_name
        binding.imageView.setOnClickListener {

        }
        binding?.imageView.let {
            if (it != null) {
                Glide.with(binding.root.context)
                    .load(picture.img_src)
                    .error(Glide.with(it.context).load("https://mars.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FRB_486615455EDR_F0481570FHAZ00323M_.JPG"))
                    .into(it)
            }
        }
    }
}
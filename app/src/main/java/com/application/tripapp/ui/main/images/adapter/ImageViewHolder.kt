package com.application.tripapp.ui.main.images.adapter

import androidx.recyclerview.widget.RecyclerView
import com.application.tripapp.databinding.ItemPicturesBinding
import com.application.tripapp.model.Picture
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class ImageViewHolder(private val binding: ItemPicturesBinding):RecyclerView.ViewHolder(binding.root) {

    fun bind(picture: Picture, onPictureClick: (id: String) -> Unit){
        binding.image.setOnClickListener {
            onPictureClick(picture.id.toString())
        }
        binding?.image.let {
            if (it != null) {
                Glide.with(binding.root.context)
                    .load(picture.link)
                    .error(Glide.with(it.context).load("https://mars.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FRB_486615455EDR_F0481570FHAZ00323M_.JPG"))
                    .into(it)
            }

        }
    }
}
package com.application.tripapp.model

import com.application.tripapp.network.picture.Data
import com.application.tripapp.network.picture.Link

data class Picture
    (
    val data: List<Data>,
    val href: String,
    val links: List<Link>,
    val id: String,
    val title: String,
    val type: String,
    val description: String,
    val link: String
)
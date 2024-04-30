package com.application.tripapp.network.picture

data class Item(
    val data: List<Data>,
    val href: String,
    val links: List<Link>
)
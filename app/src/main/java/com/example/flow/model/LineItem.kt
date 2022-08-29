package com.example.flow.model


data class LineItems(
    @Json(name = "id")
    val id: Int = 0 ,
    @Json(name = "name")
    val name: String,
    @Json(name = "product_id")
    val productId: Int,
    @Json(name = "quantity")
    var quantity: Int,
    @Json(name = "price")
    val price: String
)
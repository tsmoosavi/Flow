package com.example.flow.model


data class Order(
    val id: Int,
    val lineItems: List<LineItems>
    )

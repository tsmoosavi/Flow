package com.example.data.network.deserializers

import androidx.room.Index
import com.example.data.network.deserializers.utils.safeGetInt
import com.example.data.network.deserializers.utils.safeGetJsonArray
import com.example.data.network.deserializers.utils.safeGetString
import com.example.flow.model.LineItems
import com.example.flow.model.Order
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class OrderItemDeserializer : JsonDeserializer<Order?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Order? {
        return json?.asJsonObject?.let {
            Order(
                id = it.safeGetInt("id"),
                lineItems = it.safeGetJsonArray("line_items")?.map {
                    val item = it.asJsonObject
                    LineItems(
                        id = item.safeGetInt("id"),
                        name = item.safeGetString("name"),
                        productId = item.safeGetInt("product_id"),
                        quantity = item.safeGetInt("quantity"),
                        price = item.safeGetString("price")
                    )
                }.orEmpty()
            )
        }
    }
}
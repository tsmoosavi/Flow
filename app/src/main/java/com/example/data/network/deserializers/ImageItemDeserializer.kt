package com.example.data.network.deserializers

import com.example.flow.model.ImageItem
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ImageItemDeserializer : JsonDeserializer<List<ImageItem>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<ImageItem> {
        return json?.asJsonArray?.map {
            it.asJsonObject
        }?.map {
            ImageItem(
                author = it.get("author").asString,
                downloadUrl = it.get("download_url").asString,
                height = it.get("height").asInt,
                width = it.get("width").asInt,
                id = it.get("id").asString,
                url = it.get("url").asString
            )
        }.orEmpty()
    }
}
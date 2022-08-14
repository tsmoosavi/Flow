package com.example.data.network.deserializers

import com.example.data.network.deserializers.utils.safeGet
import com.example.data.network.deserializers.utils.safeGetInt
import com.example.data.network.deserializers.utils.safeGetString
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
                author = it.safeGet(memberName = "author")?.asString ?: "nothing",
                downloadUrl = it.safeGetString(memberName = "download_url").ifBlank { "nothing" },
                height = it.safeGetInt(memberName = "height"),
                width = it.get("width").asInt,
                id = it.get("id").asString,
                url = it.get("url").asString
            )
        }.orEmpty()
    }
}
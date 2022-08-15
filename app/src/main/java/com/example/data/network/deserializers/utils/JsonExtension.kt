package com.example.data.network.deserializers.utils

import com.example.util.orMinus
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

fun JsonObject.safeGet(memberName: String): JsonElement? {
    if (!has(memberName))
        return null
    val member = get(memberName)
    return if (member.isJsonNull) null else member
}

//fun JsonObject.safeGetString(memberName: String): String? = safeGet(memberName)?.asString
fun JsonObject.safeGetString(memberName: String): String = safeGet(memberName)?.asString.orEmpty()
fun JsonObject.safeGetInt(memberName: String): Int = safeGet(memberName)?.asInt.orMinus()
fun JsonObject.safeGetBoolean(memberName: String): Boolean = safeGet(memberName)?.asBoolean ?: false
fun JsonObject.safeGetJsonArray(memberName: String): JsonArray? = safeGet(memberName)?.asJsonArray
fun JsonObject.safeGetJsonObject(memberName: String): JsonObject? =
    safeGet(memberName)?.asJsonObject
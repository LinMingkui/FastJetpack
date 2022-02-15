package com.lmk.architecture.net.base

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

/**
 * @author 再战科技
 * @date 2022/2/15
 * @description
 */
class MoshiDefaultAdapterFactory : JsonAdapter.Factory {
//    companion object {
//
//        val FACTORY: JsonAdapter.Factory = object : JsonAdapter.Factory {
//            override fun create(
//                type: Type,
//                annotations: MutableSet<out Annotation>,
//                moshi: Moshi
//            ): JsonAdapter<*>? {
//                if (annotations.isNotEmpty()) return null
//                if (type == String::class.java) {
//                    return STRING_JSON_ADAPTER
//                }
//                return null
//            }
//        }
//
//        val STRING_JSON_ADAPTER: JsonAdapter<String> = object : JsonAdapter<String>() {
//
//            override fun fromJson(reader: JsonReader): String? {
//                // 替换null为""
//                if (reader.peek() != JsonReader.Token.NULL) {
//                    return reader.nextString();
//                }
//                reader.nextNull<String>()
//                return ""
//            }
//
//            override fun toJson(writer: JsonWriter, value: String?) {
//                writer.value(value)
//            }
//
//            override fun toString(): String {
//                return "JsonAdapter(String)";
//            }
//        }
//
//        val ANY_JSON_ADAPTER: JsonAdapter<Any> = object : JsonAdapter<Any>() {
//
//            override fun fromJson(reader: JsonReader): Any? {
//                val blob1 = reader.readJsonValue()
//                try {
//                    val blob = blob1 as Map<String, Any?>
//                    val noNulls = blob.filterValues { it != null }
//                    return delegate.fromJsonValue(noNulls)
//                } catch (e: Exception) {
//                    return delegate.fromJsonValue(blob1)
//                }
//            }
//
//            override fun toJson(writer: JsonWriter, value: Any?) {
//                return delegate.toJson(writer, value)
//            }
//        }
//    }

    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*> {
        val delegate = moshi.nextAdapter<Any>(this, type, annotations)
        return object : JsonAdapter<Any>() {
            override fun fromJson(reader: JsonReader): Any? {
                val blob = reader.readJsonValue()
                var noNulls: Any? = null
                if (blob is List<*>) {
                    noNulls = blob.filter { it != null }
                        .map { data ->
                            if (data is Map<*, *>) {
                                data.filterValues { it != null }
                            } else {
                                data
                            }
                        }
                } else if (blob is Map<*, *>) {
                    noNulls = blob.filterValues { it != null }
                }
                return if (noNulls != null) {
                    delegate.fromJsonValue(noNulls)
                } else {
                    delegate.fromJsonValue(blob)
                }
            }

            override fun toJson(writer: JsonWriter, value: Any?) {
                return delegate.toJson(writer, value)
            }
        }
    }
}
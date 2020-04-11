package com.eugene.pekutovskiy.restaurantslistapp.core.util

import android.content.Context
import androidx.annotation.RawRes
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter
import javax.inject.Inject

class ParseJsonHelper @Inject constructor(
    private val context: Context
) {
    fun <T> constructWithGson(@RawRes id: Int, type: Class<T>): T {
        val jsonString = openRawResource(id)
        return with(GsonBuilder().create()) {
            fromJson(jsonString, type)
        }
    }

    private fun openRawResource(@RawRes id: Int): String {
        val resourceReader = context.resources.openRawResource(id)
        val writer = StringWriter()
        BufferedReader(
            InputStreamReader(
                resourceReader,
                CHARSET_NAME
            )
        ).use {
            var line: String? = it.readLine()
            while (line != null) {
                writer.write(line)
                line = it.readLine()
            }
        }
        return writer.toString()
    }

    companion object {
        private const val CHARSET_NAME = "UTF-8"
    }
}
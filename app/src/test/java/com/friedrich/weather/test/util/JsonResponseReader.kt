package com.friedrich.weather.test.util

import androidx.test.platform.app.InstrumentationRegistry
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class JsonResponseReader(val fileName:String) {
    val content: String
//    = loadJSONFromAsset(fileName)
    init {
        try {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val inputStream = context.assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            content = builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }

//    private fun loadJSONFromAsset(name:String): String? {
//        val json: String = try {
//            val context = InstrumentationRegistry.getInstrumentation().targetContext
//            val input: InputStream = context.assets.open(name)
//            val size: Int = input.available()
//            val buffer = ByteArray(size)
//            input.read(buffer)
//            input.close()
//            String(buffer)
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//            return null
//        }
//        return json
//    }
}
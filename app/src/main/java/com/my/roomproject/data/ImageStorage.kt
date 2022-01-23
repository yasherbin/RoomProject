package com.my.roomproject.data

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.*
import java.lang.Exception

class ImageStorage {
    companion object {
        fun saveToInternalStorage(context: Context?, bitmapImage: Bitmap, fileName: String): String? {
            val cw = ContextWrapper(context)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            val mypath = File(directory, fileName)
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(mypath)
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    if (fos != null) {
                        fos.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return directory.absolutePath
        }

        fun loadImageFromStorage(path: String, name:String): Bitmap? {
            try {
                val f = File(path, "$name.jpg")
                val b = BitmapFactory.decodeStream(FileInputStream(f))
                return b
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return null
            }
        }
    }
}
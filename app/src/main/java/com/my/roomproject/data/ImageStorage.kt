package com.my.roomproject.data

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.*

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
                    fos?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return directory.absolutePath
        }

        fun loadImageFromStorage(path: String, name:String): Bitmap? {
            return try {
                val f = File(path, "$name.jpg")
                val b = BitmapFactory.decodeStream(FileInputStream(f))
                b
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                null
            }
        }
        fun deleteImage(path: String, name:String) {
            try {
                val fileName = "$name.jpg"
                val fileToBeDeleted = File( path, fileName)
                fileToBeDeleted.delete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
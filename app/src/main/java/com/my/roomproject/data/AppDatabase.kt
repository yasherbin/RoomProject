package com.my.roomproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.my.roomproject.data.model.Phone

@Database(entities = arrayOf(Phone::class), version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun phoneDao(): PhoneDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .fallbackToDestructiveMigration()
//                    .addCallback(object : Callback() {
//                        override fun onOpen(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
//                            db.execSQL("DELETE FROM phones")
//                        }
//                    })
                    .build()
                INSTANCE = instance

                instance
            }
        }

    }
}
package com.my.roomproject.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.my.roomproject.data.model.Phone

@Dao
interface PhoneDao {

    @Insert
    suspend fun insertPhone(phone: Phone)

    @Delete
    suspend fun deletePhone(phone: Phone)

    @Update
    suspend fun updatePhone(phone: Phone)

    @Query("SELECT * FROM phones ORDER BY name")
    fun getAllPhones(): LiveData<List<Phone>>

}
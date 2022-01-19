package com.my.roomproject.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "phones")
@Parcelize
data class Phone(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val number: String
) : Parcelable
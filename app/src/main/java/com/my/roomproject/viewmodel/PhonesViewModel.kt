package com.my.roomproject.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.my.roomproject.data.AppDatabase
import com.my.roomproject.data.model.Phone
import kotlinx.coroutines.launch

class PhonesViewModel(application: Application) : AndroidViewModel(application) {
    private val db:AppDatabase = AppDatabase.getDatabase(application)
    internal val allPhones : LiveData<List<Phone>> = db.phoneDao().getAllPhones()
    fun addPhone(phone: Phone) =
        viewModelScope.launch {
            db.phoneDao().insertPhone(phone)
        }

    fun updatePhone(phone: Phone) =
        viewModelScope.launch {
            db.phoneDao().updatePhone(phone)
        }

    fun deletePhone(phone: Phone) =
        viewModelScope.launch {
            db.phoneDao().deletePhone(phone)
        }
}

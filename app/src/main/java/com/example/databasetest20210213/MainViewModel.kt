package com.example.databasetest20210213

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.databasetest20210213.database.UserDatabaseDao

class MainViewModel(
    val database: UserDatabaseDao,
    application: Application) : AndroidViewModel(application) {


}
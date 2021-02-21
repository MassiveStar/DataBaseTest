package com.example.databasetest20210213

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.databasetest20210213.database.User
import com.example.databasetest20210213.database.UserDatabaseDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class MainViewModel(
    val database: UserDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var lastUser = MutableLiveData<User?>()

    init {
        initializeLastUser()
    }

    private fun initializeLastUser() {
        viewModelScope.launch {
            lastUser.value = getLastUserFromDatabase()
        }
    }

    private suspend fun getLastUserFromDatabase(): User? {
        var user = database.getLastUser()
        return user
    }

    fun onSaveUser(name: String, password: String) {
        viewModelScope.launch {
            val newUser = User(name = name, password = password)
            insert(newUser)
            lastUser.value = database.getLastUser()
        }
    }

    private suspend fun insert(user: User) {
        database.insert(user)
    }


}
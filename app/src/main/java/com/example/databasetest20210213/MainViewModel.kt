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
import androidx.lifecycle.Transformations
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
    private val users = database.getAllUsers()

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

    fun onClearUsers(){
        viewModelScope.launch {
            database.clear()
        }
    }

    val usersString = Transformations.map(users){users ->
        formatUsers(users, application.resources)
    }


    private fun formatUsers(users: List<User>, resources: Resources): Spanned {
        val sb = StringBuilder()
        sb.apply {
            append(resources.getString(R.string.title))
            users.forEach {
                append("<br>")

                append(resources.getString(R.string.name))
                append("\t${it.name}<br>")

                append(resources.getString(R.string.password))
                append("\t${it.password}<br>")

                append(resources.getString(R.string.reg_time))
                append("\t${convertLongToDateString(it.timeMilli)}<br>")
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
        } else {
            return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    /**
     * Take the Long milliseconds returned by the system and stored in Room,
     * and convert it to a nicely formatted string for display.
     *
     * EEEE - Display the long letter version of the weekday
     * MMM - Display the letter abbreviation of the nmotny
     * dd-yyyy - day in month and full year numerically
     * HH:mm - Hours and minutes in 24hr format
     */
    @SuppressLint("SimpleDateFormat")
    private fun convertLongToDateString(systemTime: Long): String {
        return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
            .format(systemTime).toString()
    }

}
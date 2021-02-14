package com.example.databasetest20210213

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.databasetest20210213.database.UserDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val application = requireNotNull(this).application
        val dataSource = UserDatabase.getInstance(application).userDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)

        mainViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)

    }
}
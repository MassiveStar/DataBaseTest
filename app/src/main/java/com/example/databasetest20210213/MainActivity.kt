package com.example.databasetest20210213

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.databasetest20210213.database.UserDatabase
import com.example.databasetest20210213.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val application = requireNotNull(this).application
        val dataSource = UserDatabase.getInstance(application).userDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)

        mainViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)

        binding.mainViewModel = mainViewModel

        binding.lifecycleOwner = this

        binding.saveButton.setOnClickListener {
            // Hide the keyboard.
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.saveButton.windowToken, 0)

            if (validateData()) {
                mainViewModel.onSaveUser(
                    binding.nameEdit.text.toString(),
                    binding.passwordEdit.text.toString()
                )
                binding.nameEdit.text.clear()
                binding.passwordEdit.text.clear()
            }
        }

        binding.clearButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setMessage("¿Quieres borrar todo el registro de datos?")
            alertDialog.setPositiveButton("OK") { _, _ ->
                mainViewModel.onClearUsers()
            }
            alertDialog.setNegativeButton("CANCELAR") { _, _ ->

            }
            alertDialog.show()
        }
    }

    private fun validateData(): Boolean {
        if (binding.nameEdit.text.toString().trim().isEmpty()) {
            binding.nameEdit.text.clear()
            Toast.makeText(this, "Debe ingresar un nombre", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.passwordEdit.text.toString().trim().isEmpty()) {
            binding.passwordEdit.text.clear()
            Toast.makeText(this, "Debe ingresar un password", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}
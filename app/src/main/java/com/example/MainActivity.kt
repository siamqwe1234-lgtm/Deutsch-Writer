package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.data.AppDatabase
import com.example.data.LanguageRepository
import com.example.ui.AppNavigation
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.LanguageViewModel
import com.example.ui.viewmodel.LanguageViewModelFactory

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    // Initialize backend structures
    val database = AppDatabase.getDatabase(applicationContext)
    val dao = database.languageDao()
    val repository = LanguageRepository(dao)

    // Provide ViewModel safely with factory configuration
    val viewModelFactory = LanguageViewModelFactory(application, repository)
    val viewModel = ViewModelProvider(this, viewModelFactory)[LanguageViewModel::class.java]

    setContent {
      MyApplicationTheme {
        Surface(
          modifier = Modifier.fillMaxSize()
        ) {
          AppNavigation(viewModel = viewModel)
        }
      }
    }
  }
}

package com.example.gazpromtask

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.gazpromtask.data.CityRemoteDS
import com.example.gazpromtask.navigation.NavigationComponent
import com.example.gazpromtask.ui.template.CitiesList
import com.example.gazpromtask.ui.theme.GazpromTaskTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val result = async {
                (application as MyApplication).appContainer.cityRepos.fetchCities()
            }.await()
            //Log.e("Network Result", "${result.size}")
        }
        setContent {
            GazpromTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationComponent()
                }
            }
        }
    }
}

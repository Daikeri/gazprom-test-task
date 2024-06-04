package com.example.gazpromtask.dependcy

import com.example.gazpromtask.data.CityRemoteDS
import com.example.gazpromtask.data.CityRepository

class AppContainer {
    private val cityRemoteDS = CityRemoteDS()
    val cityRepos = CityRepository(cityRemoteDS)
}
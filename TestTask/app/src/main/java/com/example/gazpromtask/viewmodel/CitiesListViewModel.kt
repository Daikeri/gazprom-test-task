package com.example.gazpromtask.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.gazpromtask.MyApplication
import com.example.gazpromtask.data.City
import com.example.gazpromtask.data.CityRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import kotlin.coroutines.CoroutineContext

class CitiesListViewModel(
    private val cityRepos: CityRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableLiveData<Map<Char, List<String>>>()
    val uiState = _uiState

    private val _loadUiState = MutableLiveData<errorUiState>()
    val loadUiState = _loadUiState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        // Обработка исключения
        println("Caught $exception")
        _loadUiState.postValue(errorUiState(success = false, message = exception.message))
    }

    fun fetchCities() {
        viewModelScope.launch {
            try {
                val networkResult = cityRepos.fetchCities()
                _uiState.value = toHash(networkResult)
                _loadUiState.value = errorUiState(success = true)
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    404 -> "Запрашиваемый ресурс не найден"
                    500 -> "Внутренняя ошибка сервера. Пожалуйста, попробуйте позже"
                    else -> "Произошла ошибка: ${e.message()}"
                }
                Log.e("Error","1${e.message}")
                _loadUiState.value = errorUiState(success = false, message)

            } catch (e: IOException) {
                val message = "Проблемы с подключением к сети. Пожалуйста, проверьте ваше интернет-соединение"
                Log.e("Error","2${e.message}")
                _loadUiState.value = errorUiState(success = false, message)

            } catch (e: SocketTimeoutException) {
                val message = "Время ожидания истекло. Сервер не отвечает. Пожалуйста, попробуйте позже"
                Log.e("Error","3${e.message}")
                _loadUiState.value = errorUiState(success = false, message)

            } catch (e: ConnectException) {
                val message = "Не удалось подключиться к серверу. Пожалуйста, проверьте ваше интернет-подключение и попробуйте снова."
                Log.e("Error","4${e.message}")
                _loadUiState.value = errorUiState(success = false, message)

            } catch (e: Exception) {
                Log.e("Error","5${e.message}")
                _loadUiState.value = errorUiState(success = false, e.message)
            }
        }
    }

    private suspend fun toHash(items: List<City>): Map<Char, List<String>> {
        return withContext(Dispatchers.Default) {
            val alphabet = ('А'..'Я').associateWith {
                mutableListOf<String>()
            }.toMutableMap()

            val onlyString = items
                .mapNotNull { it.city }
                .filter { it != ""}

            for (item in onlyString) {
                val firstLetter = item[0].uppercaseChar()
                alphabet[firstLetter]?.add(item)
            }

            alphabet
                .filter { it.value.isNotEmpty() }
                .mapValues { it.value.sorted() }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                val savedStateHandle = extras.createSavedStateHandle()

                return CitiesListViewModel(
                    (application as MyApplication).appContainer.cityRepos,
                    savedStateHandle
                ) as T
            }
        }
    }
}

data class errorUiState(
    val success: Boolean,
    val message: String?=null
)
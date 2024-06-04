package com.example.gazpromtask.data

import android.util.Log
import com.example.gazpromtask.viewmodel.errorUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

class CityRemoteDS(
    private val source: String = "https://gist.github.com/Stronger197/"
) {

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(this.source)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiImplement = retrofitBuilder.create(GitHubGist::class.java)
    // https://gist.github.com/Stronger197/764f9886a1e8392ddcae2521437d5a3b

    suspend fun fetchCities(): List<City> {
        try {
            return apiImplement.getCities()
        } catch (e: HttpException) {
            throw e

        } catch (e: IOException) {
            Log.e("Sone", "${e.stackTrace}")
            throw e

        } catch (e: SocketTimeoutException) {
            throw e

        } catch (e: ConnectException) {
            throw e

        } catch (e: Exception) {
            throw e
        }
    }


    /*
    try {


    } catch (e: HttpException) {
        val message = when (e.code()) {
            404 -> "Запрашиваемый ресурс не найден"
            500 -> "Внутренняя ошибка сервера. Пожалуйста, попробуйте позже"
            else -> "Произошла ошибка: ${e.message()}"
        }
        Log.e("Error","1${e.message}")
        return listOf(City(city = "Краснодар"))

    } catch (e: IOException) {
        val message = "Проблемы с подключением к сети. Пожалуйста, проверьте ваше интернет-соединение"
        Log.e("Error","2${e.message}")
        return listOf(City(city = "Краснодар"))

    } catch (e: SocketTimeoutException) {
        val message = "Время ожидания истекло. Сервер не отвечает. Пожалуйста, попробуйте позже"
        Log.e("Error","3${e.message}")
        return listOf(City(city = "Краснодар"))

    } catch (e: ConnectException) {
        val message = "Не удалось подключиться к серверу. Пожалуйста, проверьте ваше интернет-подключение и попробуйте снова."
        Log.e("Error","4${e.message}")
        return listOf(City(city = "Краснодар"))

    } catch (e: Exception) {
        Log.e("Error","5${e.message}")
        return listOf(City(city = "Краснодар"))
    }

     */
}

interface GitHubGist {
    @GET("764f9886a1e8392ddcae2521437d5a3b/raw/65164ea1af958c75c81a7f0221bead610590448e/cities.json")
    suspend fun getCities(): List<City>
}

data class City(
    val id: String?=null,
    val city: String?=null,
    val latitude: String?=null,
    val longitude: String?=null
)


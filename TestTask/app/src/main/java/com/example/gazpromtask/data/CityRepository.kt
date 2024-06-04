package com.example.gazpromtask.data

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

class CityRepository(
    private val remoteDS: CityRemoteDS
) {
    private lateinit var cities: List<City>

    suspend fun fetchCities() : List<City> {
        val scope = CoroutineScope(Dispatchers.IO)
        val networkResult = scope.async {
            try {
                remoteDS.fetchCities()
            } catch (e: HttpException) {
                throw e

            } catch (e: IOException) {
                Log.e("in repos", "${e.stackTrace}")
                throw e

            } catch (e: SocketTimeoutException) {
                throw e

            } catch (e: ConnectException) {
                throw e

            } catch (e: Exception) {
                throw e
            }
        }.await()
        this.cities = networkResult
        return this.cities

        /*
        val networkResult = withContext(Dispatchers.IO) {
            try {
                remoteDS.fetchCities()
            } catch (e: HttpException) {
                throw e

            } catch (e: IOException) {
                throw e


            } catch (e: SocketTimeoutException) {
                throw e

            } catch (e: ConnectException) {
                throw e

            } catch (e: Exception) {
                throw e
            }
        }
        this.cities = networkResult
        return this.cities

         */
    }
}
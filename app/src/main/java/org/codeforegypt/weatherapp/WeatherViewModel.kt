package org.codeforegypt.weatherapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.codeforegypt.weatherapp.api.Constants
import org.codeforegypt.weatherapp.api.RetrofitInstance
import org.codeforegypt.weatherapp.api.WeatherModel
import org.codeforegypt.weatherapp.ui.NetworkResponse
import java.lang.Exception

class WeatherViewModel: ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: MutableLiveData<NetworkResponse<WeatherModel>> get() = _weatherResult

    fun getData(city: String) {
        try {
            _weatherResult.value = NetworkResponse.Loading
            Log.i("City name: ", city)
            viewModelScope.launch {
                val response =  weatherApi.getWeather(Constants.apiKey, city)
                if (response.isSuccessful){
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }

                }else{
                    _weatherResult.value = NetworkResponse.Error("Failed  to load data")

                }

            }
        }catch (e: Exception){
            _weatherResult.value = NetworkResponse.Error("Failed to load data")
        }
    }
}
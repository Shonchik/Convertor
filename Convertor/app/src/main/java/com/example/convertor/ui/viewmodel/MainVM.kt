package com.example.convertor.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convertor.network.ValuteApiService
import com.example.convertor.network.model.ValCurs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val apiService: ValuteApiService,
) : ViewModel() {

    val valCurs = MutableLiveData<ValCurs>()

    fun getValute() {
        viewModelScope.launch {
            try {
                valCurs.value = apiService.getValute()
            } catch (e: Exception) {
                Log.d("dbbd", e.toString())
            }
        }
    }

}
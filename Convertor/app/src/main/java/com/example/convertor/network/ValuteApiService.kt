package com.example.convertor.network

import com.example.convertor.network.model.ValCurs
import retrofit2.http.GET

interface ValuteApiService {

    @GET("XML_daily.asp")
    suspend fun getValute(
    ): ValCurs
}
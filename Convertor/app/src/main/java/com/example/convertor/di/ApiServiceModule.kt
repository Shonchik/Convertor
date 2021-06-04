package com.example.convertor.di

import com.example.convertor.network.ValuteApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import nl.adaptivity.xmlutil.serialization.XML
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://www.cbr.ru/scripts/")
            .addConverterFactory(XML().asConverterFactory(MediaType.get("application/xml; charset=utf-8")))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ValuteApiService {
        return retrofit.create()
    }
}
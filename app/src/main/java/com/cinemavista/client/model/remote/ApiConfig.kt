package com.cinemavista.client.model.remote

import com.cinemavista.client.model.Constants.KEY.Companion.TMDBAPI_BEARER
import com.cinemavista.client.model.Constants.URL_CONSTANTS.Companion.TMDBAPI_URL
import de.hdodenhof.circleimageview.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(): ApiService{
            val loggingInterceptor = if(BuildConfig.DEBUG){
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            }else{
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(AuthInterceptor(TMDBAPI_BEARER))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(TMDBAPI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
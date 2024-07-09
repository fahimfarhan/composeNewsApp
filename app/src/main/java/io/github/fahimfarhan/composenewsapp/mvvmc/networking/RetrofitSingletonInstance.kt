package io.github.fahimfarhan.composenewsapp.mvvmc.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitSingletonInstance {
  companion object {
    private const val BASE_URL = "https://newsapi.org"

    private var _newsApiInstance: NewsApiService? = null

    private fun createLoggingInterceptor(): OkHttpClient {
      val logging = HttpLoggingInterceptor()
      logging.setLevel(Level.BODY)
      val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
      return client
    }

    fun getNewsApiInstance(): NewsApiService {
      if(_newsApiInstance == null) {
        synchronized(this) {
          if(_newsApiInstance == null) {
            val mGsonConverterFactory = GsonConverterFactory.create()
            val mRetrofit = Retrofit
              .Builder()
              .client(createLoggingInterceptor())
              .baseUrl(BASE_URL)
              .addConverterFactory(mGsonConverterFactory)
              .build()

            _newsApiInstance = mRetrofit.create(NewsApiService::class.java)
          }
        }
      }
      return _newsApiInstance!!
    }
  }
}
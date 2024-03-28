package eu.tutorial.fetchnewsapi.repository

import eu.tutorial.fetchnewsapi.api.NewsApiService
import eu.tutorial.fetchnewsapi.model.NewsItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository {

    private val apiService: NewsApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.thenewsapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(NewsApiService::class.java)
    }

    suspend fun getTopNews(apiToken: String, locale: String, limit: Int): List<NewsItem> {
        val response = apiService.getTopNews(apiToken, locale, limit)
        return response.data
    }
}
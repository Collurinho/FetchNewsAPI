package eu.tutorial.fetchnewsapi.api

import eu.tutorial.fetchnewsapi.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v1/news/top")
    suspend fun getTopNews(
        @Query("api_token") apiToken: String,
        @Query("locale") locale: String,
        @Query("limit") limit: Int
    ): NewsResponse
}
package eu.tutorial.fetchnewsapi.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("data") val data: List<NewsItem>
)

data class NewsItem(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("source") val source: String,
    @SerializedName("categories") val categories: List<String>
)

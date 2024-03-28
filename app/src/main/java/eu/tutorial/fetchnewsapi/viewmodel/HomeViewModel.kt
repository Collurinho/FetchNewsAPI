package eu.tutorial.fetchnewsapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorial.fetchnewsapi.model.NewsItem
import eu.tutorial.fetchnewsapi.repository.NewsRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _newsList = MutableLiveData<List<NewsItem>>()
    val newsList: LiveData<List<NewsItem>> get() = _newsList

    fun fetchTopNews(apiToken: String, locale: String, limit: Int) {
        viewModelScope.launch {
            try {
                val response = newsRepository.getTopNews(apiToken, locale, limit)
                _newsList.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
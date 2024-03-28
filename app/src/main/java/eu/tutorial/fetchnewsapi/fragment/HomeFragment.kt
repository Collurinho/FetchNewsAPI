package eu.tutorial.fetchnewsapi.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.tutorial.fetchnewsapi.adapter.NewsAdapter
import eu.tutorial.fetchnewsapi.databinding.FragmentHomeBinding
import eu.tutorial.fetchnewsapi.model.NewsItem
import eu.tutorial.fetchnewsapi.repository.NewsRepository
import eu.tutorial.fetchnewsapi.viewmodel.HomeViewModel
import eu.tutorial.fetchnewsapi.viewmodel.HomeViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsAdapter: NewsAdapter
    private var isReturningFromHome: Boolean = false

    private val apiToken = "JUGjDHxy57o7StLMHDrOgepI8hARnJ58sh2UpDYV"
    private var locale = ""
    private val limit = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        isReturningFromHome = sharedPreferences.getBoolean("isReturningFromHome", false)

        val selectedLanguage = arguments?.getString("selectedLanguage")
        locale = when (selectedLanguage) {
            "Italiano" -> "it"
            "Inglese" -> "us"
            "Tedesco" -> "de"
            "Russo" -> "ru"
            else -> ""
        }

        // Initialize the repository with parameters
        newsRepository = NewsRepository()

        // Init viewmodel
        viewModel = ViewModelProvider(this, HomeViewModelFactory(newsRepository)).get(HomeViewModel::class.java)

        // Fetch top news
        viewModel.fetchTopNews(apiToken, locale, limit)

        setupRecyclerView()

        viewModel.newsList.observe(viewLifecycleOwner, Observer { news ->
            newsAdapter.submitList(news)
        })

        binding.idRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    // scroll down
                    binding.idFabExit.visibility = View.VISIBLE
                } else if (dy < 0 && binding.idFabExit.visibility == View.VISIBLE) {
                    // scroll up
                    binding.idFabExit.visibility = View.INVISIBLE
                }
            }
        })

        binding.idFabExit.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWelcomeFragment("false")
            findNavController().navigate(action)
        }

        binding.idFabExit.setOnClickListener {
            // Check if isReturningFromHome is true
            if (isReturningFromHome) {
                // Set isReturningFromHome false
                isReturningFromHome = false
                // Salva il valore aggiornato di isReturningFromHome nelle preferenze condivise
                // Save current value of isReturningFromHome on shared preferences
                with(sharedPreferences.edit()) {
                    putBoolean("isReturningFromHome", isReturningFromHome)
                    apply()
                }
            }
            val action = HomeFragmentDirections.actionHomeFragmentToWelcomeFragment("")
            findNavController().navigate(action)
        }

        return view
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(requireContext(), emptyList(), object : NewsAdapter.OnItemClickListener {
            override fun onItemClick(newsItem: NewsItem) {
                val category = newsItem.categories.firstOrNull()
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    newsItem.title,
                    newsItem.imageUrl,
                    newsItem.description,
                    newsItem.url,
                    category ?: "N/A",
                    newsItem.source,
                    newsItem.publishedAt,
                )
                findNavController().navigate(action)
            }
        })
        binding.idRecyclerview.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}
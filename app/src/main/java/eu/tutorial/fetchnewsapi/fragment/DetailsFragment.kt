package eu.tutorial.fetchnewsapi.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import eu.tutorial.fetchnewsapi.R
import eu.tutorial.fetchnewsapi.databinding.FragmentDetailsBinding
import eu.tutorial.utils.Utils

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        // Get arguments from home
        val args = arguments
        val title = args?.getString("title")
        val image_url = args?.getString("imageurl")
        val description = args?.getString("description")
        val url = args?.getString("url")
        val categories = args?.getString("categories")
        val source = args?.getString("source")
        val published_at = args?.getString("published_at")

        binding.idIncludeTopDetails.idTitleTextview.text = title
        binding.idIncludeCenterDetails.idTextDescription.text = description
        binding.idIncludeBottomDetails.idOutCategories.text = categories
        binding.idIncludeBottomDetails.idOutPublishedBy.text = source

        image_url?.let {
            Utils.loadImage(requireContext(), it, binding.idIncludeTopDetails.idImageview,
                R.drawable.baseline_warning_amber_24
            )
        }

        // convert date
        val dateString = published_at
        val formattedDate = dateString?.let { Utils.formatDate(it) }
        binding.idIncludeBottomDetails.idOutPublishedTimeBy.text = formattedDate

        // Open link on browser
        binding.idIncludeCenterDetails.idTextLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        return view


    }
}
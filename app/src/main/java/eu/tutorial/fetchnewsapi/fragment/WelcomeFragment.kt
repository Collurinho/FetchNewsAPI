package eu.tutorial.fetchnewsapi.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import eu.tutorial.fetchnewsapi.R
import eu.tutorial.fetchnewsapi.adapter.WelcomeSpinnerAdapter
import eu.tutorial.fetchnewsapi.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private var initialLanguage = ""
    private var isReturningFromHome: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val sharedPreferencesReturn = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // read value of isReturningFromHome
        isReturningFromHome = sharedPreferencesReturn.getBoolean("isReturningFromHome", false)

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        initialLanguage = sharedPreferences.getString("initialLanguage", "") ?: ""

        val spinnerData = listOf(
            "Italiano" to R.drawable.it_flag,
            "Inglese" to R.drawable.us_flag,
            "Tedesco" to R.drawable.de_flag,
            "Russo" to R.drawable.ru_flag,
        )

        val adapter = WelcomeSpinnerAdapter(requireContext(), spinnerData)
        binding.idSpinnerChooseLanguages.adapter = adapter

        binding.idCheckboxSavePreference.setOnCheckedChangeListener { buttonView, isChecked ->
            saveCheckboxState(isChecked, spinnerData)
        }

        binding.idButtonStart.setOnClickListener {
            val selectedLanguage = spinnerData[binding.idSpinnerChooseLanguages.selectedItemPosition].first

            initialLanguage = selectedLanguage

            isReturningFromHome = true
            val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putString("initialLanguage", initialLanguage)
                putBoolean("isReturningFromHome", isReturningFromHome)
                apply()
            }
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment(initialLanguage)
            findNavController().navigate(action)
        }

        if (isReturningFromHome == true) {
            checkIfChecked(spinnerData)
        } else {
            // isReturningFromHome is false show home screen
        }
        return view
    }

    private fun saveCheckboxState(isChecked: Boolean, spinnerData: List<Pair<String, Int>>) {
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("savePreference", isChecked)

            // Save item spinner
            val selectedPosition = binding.idSpinnerChooseLanguages.selectedItemPosition
            val selectedLanguage = spinnerData[selectedPosition].first
            putString("selectedLanguage", selectedLanguage)

            apply()
        }
    }

    private fun checkIfChecked(spinnerData: List<Pair<String, Int>>) {

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isChecked = sharedPreferences.getBoolean("savePreference", false)

        if (isChecked) {
            // Set checkbox state
            binding.idCheckboxSavePreference.isChecked = isChecked
            // Check if spinner is select value
            val selectedPosition = binding.idSpinnerChooseLanguages.selectedItemPosition
            if (selectedPosition != -1 && selectedPosition < spinnerData.size) {
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment(initialLanguage)
                findNavController().navigate(action)
            } else {

            }
        } else {

        }
    }
}
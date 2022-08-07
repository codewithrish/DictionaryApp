package com.codewithrish.dictionaryapp.presentation.home

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.codewithrish.dictionaryapp.R
import com.codewithrish.dictionaryapp.data.remote.dto.general.WordInfoDtoItem
import com.codewithrish.dictionaryapp.data.remote.dto.general.toWordInfo
import com.codewithrish.dictionaryapp.databinding.FragmentHomeBinding
import com.codewithrish.dictionaryapp.domain.model.WordInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val navController: NavController? by lazy { view?.findNavController() }

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getWordInfo(getString(R.string.welcome))
        binding.etWord.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchWord()
            }
            false
        }

        searchWordObserver()
    }

    private fun searchWordObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.channelWorkMeaning.collectLatest { wordMeaningState: WordMeaningState ->
                    if (wordMeaningState.wordInfoDto != null) {
                        binding.tvError.visibility = View.GONE
                        val response: WordInfo = wordMeaningState.wordInfoDto[0].toWordInfo()
                        binding.tvWord.text = response.word.uppercase()

                        // Phonetics
                        val phoneticAdapter = PhoneticAdapter()
                        phoneticAdapter.submitList(response.phonetics)
                        binding.rvPhonetics.adapter = phoneticAdapter

                        phoneticAdapter.speakPhonetic = { phoneUrl ->
                            val mp = MediaPlayer()
                            try {
                                mp.setDataSource(phoneUrl)
                                mp.prepare()
                                mp.start()
                            } catch (e: IOException) {
                                Toast.makeText(requireContext(), getString(R.string.not_available), Toast.LENGTH_SHORT).show()
                            }
                        }

                        // Meanings
                        val meaningAdapter = MeaningAdapter()
                        meaningAdapter.submitList(response.meanings)
                        binding.rvMeanings.adapter = meaningAdapter

                    }
                    if (!wordMeaningState.error.isNullOrEmpty()) {
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = wordMeaningState.error
                        binding.layoutHome.visibility = View.GONE
                    }
                    if (wordMeaningState.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.layoutHome.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        if (wordMeaningState.error.isNullOrEmpty()) {
                            binding.layoutHome.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun searchWord() {
        val query = binding.etWord.text.toString()
        if (query.isNotEmpty())
            homeViewModel.getWordInfo(query)
    }

}
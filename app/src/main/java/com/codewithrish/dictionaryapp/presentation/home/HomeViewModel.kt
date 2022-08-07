package com.codewithrish.dictionaryapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithrish.dictionaryapp.common.Resource
import com.codewithrish.dictionaryapp.data.remote.dto.response.WordInfoDto
import com.codewithrish.dictionaryapp.domain.use_case.GetWordInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWordInfoUseCase: GetWordInfoUseCase
) : ViewModel() {

    private val _channelWorkMeaning = Channel<WordMeaningState>()
    val channelWorkMeaning = _channelWorkMeaning.receiveAsFlow()

    fun getWordInfo(
        word: String
    ) {
        getWordInfoUseCase(word).onEach { result ->
            when(result) {
                is Resource.Success -> _channelWorkMeaning.send(WordMeaningState(wordInfoDto = result.data))
                is Resource.Error -> _channelWorkMeaning.send(WordMeaningState(error = result.message))
                is Resource.Loading -> _channelWorkMeaning.send(WordMeaningState(isLoading = true))
            }
        }.launchIn(viewModelScope)
    }
}

data class WordMeaningState(
    val isLoading: Boolean = false,
    val wordInfoDto: WordInfoDto? = null,
    val error: String? = ""
)
package com.codewithrish.dictionaryapp.domain.repository

import com.codewithrish.dictionaryapp.data.remote.dto.response.WordInfoDto

interface DictionaryRepository {
    suspend fun getWordInfo(word: String): WordInfoDto
}
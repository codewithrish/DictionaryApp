package com.codewithrish.dictionaryapp.data.repository

import com.codewithrish.dictionaryapp.data.remote.DictionaryApi
import com.codewithrish.dictionaryapp.data.remote.dto.response.WordInfoDto
import com.codewithrish.dictionaryapp.domain.repository.DictionaryRepository
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val api: DictionaryApi
) : DictionaryRepository {
    override suspend fun getWordInfo(word: String): WordInfoDto {
        return api.getWordInfo(word)
    }
}
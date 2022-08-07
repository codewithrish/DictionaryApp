package com.codewithrish.dictionaryapp.data.remote

import com.codewithrish.dictionaryapp.data.remote.dto.response.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("api/v2/entries/en/{word}")
    suspend fun getWordInfo(@Path("word") word: String): WordInfoDto
}
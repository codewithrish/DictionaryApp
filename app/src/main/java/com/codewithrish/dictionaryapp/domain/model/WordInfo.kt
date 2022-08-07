package com.codewithrish.dictionaryapp.domain.model

import com.codewithrish.dictionaryapp.data.remote.dto.general.Meaning
import com.codewithrish.dictionaryapp.data.remote.dto.general.Phonetic

data class WordInfo(
    val meanings: List<Meaning>? = null,
    val phonetic: String? = null,
    val phonetics: List<Phonetic>? = null,
    val word: String
)

package com.codewithrish.dictionaryapp.data.remote.dto.general

import com.codewithrish.dictionaryapp.domain.model.WordInfo

data class WordInfoDtoItem(
    val license: License,
    val meanings: List<Meaning>,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>,
    val word: String
)

fun WordInfoDtoItem.toWordInfo() : WordInfo {
    return WordInfo(
        meanings = meanings,
        phonetic = phonetic,
        phonetics = phonetics,
        word = word
    )
}
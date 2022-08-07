package com.codewithrish.dictionaryapp.data.remote.dto.general

data class Meaning(
    val antonyms: List<String>,
    val definitions: List<Definition>,
    val partOfSpeech: String,
    val synonyms: List<String>
)
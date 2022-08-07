package com.codewithrish.dictionaryapp.data.remote.dto.general

data class Definition(
    val antonyms: List<String>,
    val definition: String,
    val example: String?,
    val synonyms: List<String>
)
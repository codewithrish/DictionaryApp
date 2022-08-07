package com.codewithrish.dictionaryapp.common

import com.codewithrish.dictionaryapp.data.remote.dto.general.ErrorDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.HttpException

fun HttpException.parseError(responseBody: ResponseBody): ErrorDto {
    val gson = Gson()
    val type = object: TypeToken<ErrorDto>() {}.type
    return gson.fromJson(responseBody.charStream(), type)
}
package com.codewithrish.dictionaryapp.domain.use_case

import com.codewithrish.dictionaryapp.App
import com.codewithrish.dictionaryapp.R
import com.codewithrish.dictionaryapp.common.Resource
import com.codewithrish.dictionaryapp.common.parseError
import com.codewithrish.dictionaryapp.data.remote.dto.response.WordInfoDto
import com.codewithrish.dictionaryapp.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWordInfoUseCase @Inject constructor(
    private val repository: DictionaryRepository
) {
    operator fun invoke(
        word: String
    ): Flow<Resource<WordInfoDto>> = flow {
        try {
            emit(Resource.Loading<WordInfoDto>())
            val wordInfoResult = repository.getWordInfo(word = word)
            emit(Resource.Success<WordInfoDto>(wordInfoResult))
        } catch (e: HttpException) {
            val errorDto = e.parseError(responseBody = e.response()?.errorBody()!!)
            emit(Resource.Error<WordInfoDto>(errorDto.message))
        } catch (e: IOException) {
            emit(Resource.Error<WordInfoDto>(App.instance.res.getString(R.string.internet_not_available)))
        } catch (e: Exception) {
            emit(Resource.Error<WordInfoDto>(App.instance.res.getString(R.string.unexpected_error_occurred)))
        }
    }
}
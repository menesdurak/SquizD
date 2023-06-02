package com.menesdurak.squizd.domain.use_case.words

import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.domain.repository.LocalRepository
import java.io.IOException
import javax.inject.Inject

class UpdateWordWithIdUseCase @Inject constructor(
    private val localRepository: LocalRepository,
) {

    suspend operator fun invoke(wordId: Long, wordName: String, wordMeaning: String) {
        try {
            Resource.Success(localRepository.updateWordWithId(wordId, wordName, wordMeaning))
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}
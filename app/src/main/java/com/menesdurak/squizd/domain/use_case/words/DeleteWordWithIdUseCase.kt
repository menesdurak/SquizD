package com.menesdurak.squizd.domain.use_case.words

import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.domain.repository.LocalRepository
import java.io.IOException
import javax.inject.Inject

class DeleteWordWithIdUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(wordId: Long) {
        try {
            Resource.Success(localRepository.deleteWordWithId(wordId))
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}
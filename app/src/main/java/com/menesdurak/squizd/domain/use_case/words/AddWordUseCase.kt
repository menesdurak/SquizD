package com.menesdurak.squizd.domain.use_case.words

import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.data.local.entity.Word
import com.menesdurak.squizd.domain.repository.LocalRepository
import java.io.IOException
import javax.inject.Inject

class AddWordUseCase @Inject constructor(
    private val localRepository: LocalRepository,
) {

    suspend operator fun invoke(word: Word) {
        try {
            Resource.Success(localRepository.addWord(word))
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}
package com.menesdurak.squizd.domain.use_case.words

import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.data.local.entity.Word
import com.menesdurak.squizd.domain.repository.LocalRepository
import java.io.IOException
import javax.inject.Inject

class GetAllWordsUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(): Resource<List<Word>> {
        return try {
            Resource.Success(localRepository.getAllWords())
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}
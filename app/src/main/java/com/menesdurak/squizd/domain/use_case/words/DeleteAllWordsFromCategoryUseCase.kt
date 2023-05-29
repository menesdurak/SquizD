package com.menesdurak.squizd.domain.use_case.words

import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.domain.repository.LocalRepository
import java.io.IOException
import javax.inject.Inject

class DeleteAllWordsFromCategoryUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(categoryId: Int) {
        try {
            Resource.Success(localRepository.deleteAllWordsFromCategory(categoryId))
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}
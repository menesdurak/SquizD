package com.menesdurak.squizd.domain.use_case.categories

import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.domain.repository.LocalRepository
import java.io.IOException
import javax.inject.Inject

class UpdateCategoryWithIdUseCase @Inject constructor(
    private val localRepository: LocalRepository,
) {

    suspend operator fun invoke(categoryName: String, categoryId: Int) {
        try {
            Resource.Success(localRepository.updateCategoryWithId(categoryName, categoryId))
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}
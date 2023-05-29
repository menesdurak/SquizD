package com.menesdurak.squizd.domain.use_case.categories

import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.data.local.entity.Category
import com.menesdurak.squizd.domain.repository.LocalRepository
import java.io.IOException
import javax.inject.Inject

class DeleteCategoryWithIdUseCase @Inject constructor(
    private val localRepository: LocalRepository,
) {

    suspend operator fun invoke(categoryId: Int) {
        try {
            Resource.Success(localRepository.deleteCategoryWithId(categoryId))
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}
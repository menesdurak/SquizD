package com.menesdurak.squizd.domain.use_case.categories

import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.data.local.entity.Category
import com.menesdurak.squizd.domain.repository.LocalRepository
import java.io.IOException
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(
    private val localRepository: LocalRepository,
) {

    suspend operator fun invoke(category: Category) {
        try {
            Resource.Success(localRepository.addCategory(category))
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}
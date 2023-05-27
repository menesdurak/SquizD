package com.menesdurak.squizd.domain.use_case.categories

import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.data.local.entity.Category
import com.menesdurak.squizd.domain.repository.LocalRepository
import java.io.IOException
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(): Resource<List<Category>> {
        return try {
            Resource.Success(localRepository.getAllCategories())
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}
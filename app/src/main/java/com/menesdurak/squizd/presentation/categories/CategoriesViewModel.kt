package com.menesdurak.squizd.presentation.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.data.local.entity.Category
import com.menesdurak.squizd.domain.use_case.categories.AddCategoryUseCase
import com.menesdurak.squizd.domain.use_case.categories.DeleteCategoryUseCase
import com.menesdurak.squizd.domain.use_case.categories.GetAllCategoriesUseCase
import com.menesdurak.squizd.domain.use_case.categories.UpdateCategoryWithIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val updateCategoryWithIdUseCase: UpdateCategoryWithIdUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : ViewModel() {

    private val _categoriesList = MutableLiveData<Resource<List<Category>>>(Resource.Loading)
    val categoriesList: LiveData<Resource<List<Category>>> = _categoriesList

    fun getAllCategories() {
        viewModelScope.launch {
            _categoriesList.value = Resource.Loading
            _categoriesList.value = getAllCategoriesUseCase()!!
        }
    }

    fun addCategory(category: Category) {
        viewModelScope.launch {
            addCategoryUseCase(category)
        }
    }

    fun updateCategoryWithId(categoryName: String, categoryId: Int) {
        viewModelScope.launch {
            updateCategoryWithIdUseCase(categoryName, categoryId)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            deleteCategoryUseCase(category)
        }
    }
}
package com.menesdurak.squizd.presentation.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.menesdurak.squizd.R
import com.menesdurak.squizd.data.local.entity.Category
import com.menesdurak.squizd.databinding.FragmentAddOrEditCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddOrEditCategoryFragment : Fragment() {

    private var _binding: FragmentAddOrEditCategoryBinding? = null
    private val binding get() = _binding!!
    private val categoriesViewModel: CategoriesViewModel by viewModels()
    private var categoryId = -1
    private var categoryName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddOrEditCategoryBinding.inflate(inflater, container, false)
        val view = binding.root

        val args: AddOrEditCategoryFragmentArgs by navArgs()
        categoryId = args.categoryId
        categoryName = args.categoryName ?: ""

        if (categoryId != -1) {
            binding.btnAdd.text = getString(R.string.edit_category)
            binding.etCategoryName.setText(categoryName)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (categoryId == -1) {
            binding.btnAdd.setOnClickListener {
                if (binding.etCategoryName.text.isNotBlank()) {
                    val category = Category(binding.etCategoryName.text.toString())
                    categoriesViewModel.addCategory(category)
                    val action =
                        AddOrEditCategoryFragmentDirections.actionAddOrEditCategoryFragmentToCategoriesFragment()
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(context, "Please enter a valid name.", Toast.LENGTH_SHORT).show()
                }

            }
        } else {
            binding.btnAdd.setOnClickListener {
                if (binding.etCategoryName.text.isNotBlank()) {
                    categoryName = binding.etCategoryName.text.toString()
                    categoriesViewModel.updateCategoryWithId(categoryName, categoryId)
                    val action =
                        AddOrEditCategoryFragmentDirections.actionAddOrEditCategoryFragmentToCategoriesFragment()
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(context, "Please enter a valid name.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
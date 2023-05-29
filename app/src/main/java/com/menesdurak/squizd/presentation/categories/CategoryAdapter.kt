package com.menesdurak.squizd.presentation.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.menesdurak.squizd.data.local.entity.Category
import com.menesdurak.squizd.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onItemClick: (Int) -> Unit,
    private val onEditClick: (Int) -> Unit,
    private val onDeleteClick: (Int, Category) -> Unit,
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    private val itemList = mutableListOf<Category>()

    inner class CategoryHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.tvCategoryName.text = category.categoryName

            binding.root.setOnClickListener {
                onItemClick.invoke(category.categoryId)
            }
            binding.ivEditCategory.setOnClickListener {
                onEditClick.invoke(category.categoryId)
            }
            binding.ivDeleteCategory.setOnClickListener {
                onDeleteClick.invoke(adapterPosition, category)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryAdapter.CategoryHolder {
        val bind = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(bind)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun updateCategoryList(newList: List<Category>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int, category: Category) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }
}
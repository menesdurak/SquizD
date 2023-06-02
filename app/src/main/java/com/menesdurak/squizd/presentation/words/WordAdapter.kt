package com.menesdurak.squizd.presentation.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.menesdurak.squizd.data.local.entity.Word
import com.menesdurak.squizd.databinding.ItemWordBinding

class WordAdapter(
    private val onItemClick: (Long, String, String) -> Unit,
    private val onItemLongClick: (Long) -> Unit
) : RecyclerView.Adapter<WordAdapter.WordHolder>() {

    private val itemList = mutableListOf<Word>()

    inner class WordHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(word: Word) {
                binding.apply {
                    this.tvWordName.text = word.wordName
                    this.tvWordMeaning.text = word.wordMeaning
                }

                binding.root.setOnClickListener {
                    onItemClick.invoke(word.wordId, word.wordName, word.wordMeaning)
                }

                binding.root.setOnLongClickListener {
                    onItemLongClick.invoke(word.wordId)
                    true
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val bind = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordHolder(bind)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun updateWordList(newList: List<Word>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }
}
package com.menesdurak.squizd.presentation.words

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.menesdurak.squizd.data.local.entity.Word
import com.menesdurak.squizd.databinding.FragmentAddOrEditWordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddOrEditWordFragment : Fragment() {

    private var _binding: FragmentAddOrEditWordBinding? = null
    private val binding get() = _binding!!
    private val wordsViewModel: WordsViewModel by viewModels()
    private var wordId: Long = -1
    private var wordName = ""
    private var wordMeaning = ""
    private var categoryId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddOrEditWordBinding.inflate(inflater, container, false)
        val view = binding.root

        val args: AddOrEditWordFragmentArgs by navArgs()
        wordId = args.wordId
        wordName = args.wordName ?: ""
        wordMeaning = args.wordMeaning ?: ""
        categoryId = args.categoryId

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (wordId == -1L) {
            binding.btnAdd.setOnClickListener {
                if (binding.etWordName.text.isNotBlank() && binding.etWordMeaning.text.isNotBlank()) {
                    val word = Word(
                        wordName = binding.etWordName.text.toString(),
                        wordMeaning = binding.etWordMeaning.text.toString(),
                        categoryOwnerId = categoryId
                    )
                    wordsViewModel.addWord(word)
                    val action =
                        AddOrEditWordFragmentDirections.actionAddOrEditWordFragmentToWordsFragment(
                            categoryId
                        )
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(
                        context,
                        "Please enter a valid name and meaning.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.menesdurak.squizd.presentation.words

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.data.local.entity.Word
import com.menesdurak.squizd.databinding.FragmentWordsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordsFragment : Fragment() {

    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!
    private val wordsViewModel: WordsViewModel by viewModels()
    private val wordAdapter by lazy { WordAdapter() }
    private var categoryId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordsBinding.inflate(inflater, container, false)
        val view = binding.root

        val args : WordsFragmentArgs by navArgs()
        categoryId = args.categoryId

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val word = Word("Iki", "2", categoryId)
//        wordsViewModel.addWord(word)
        wordsViewModel.getAllWordsFromCategory(categoryId)

        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = wordAdapter
        }

        wordsViewModel.wordsList.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    wordAdapter.updateWordList(it.data)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                }
                Resource.Loading -> {
                    Toast.makeText(requireContext(), "LOADING", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
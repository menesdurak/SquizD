package com.menesdurak.squizd.presentation.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.menesdurak.squizd.databinding.FragmentQuizStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizStartFragment : Fragment() {

    private var _binding: FragmentQuizStartBinding? = null
    private val binding get() = _binding!!
    private var categoryId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizStartBinding.inflate(inflater, container, false)
        val view = binding.root

        //Receiving clicked category's ID
        val args: QuizStartFragmentArgs by navArgs()
        categoryId = args.categoryId

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnQuizStart.setOnClickListener {
            val action =
                QuizStartFragmentDirections.actionQuizStartFragmentToQuizFragment(categoryId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
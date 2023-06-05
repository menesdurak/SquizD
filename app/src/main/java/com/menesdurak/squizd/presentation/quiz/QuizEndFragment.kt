package com.menesdurak.squizd.presentation.quiz

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.menesdurak.squizd.databinding.FragmentQuizEndBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizEndFragment : Fragment() {

    private var _binding: FragmentQuizEndBinding? = null
    private val binding get() = _binding!!

    private var categoryId = 0
    private var totalScore = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizEndBinding.inflate(inflater, container, false)
        val view = binding.root

        //Receiving clicked category's ID and total score
        val args: QuizEndFragmentArgs by navArgs()
        categoryId = args.categoryId
        totalScore = args.totalScore

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textQuizFinishTotalScore.text = "$totalScore / 5"

        binding.buttonQuizFinishRestart.setOnClickListener {
            val action =
                QuizEndFragmentDirections.actionQuizEndFragmentToQuizStartFragment(categoryId)
            findNavController().navigate(action)
        }

        binding.buttonQuizFinishGoToCategories.setOnClickListener {
            val action = QuizEndFragmentDirections.actionQuizEndFragmentToCategoriesFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
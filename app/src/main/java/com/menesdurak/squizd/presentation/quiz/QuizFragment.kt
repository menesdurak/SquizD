package com.menesdurak.squizd.presentation.quiz

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.menesdurak.squizd.R
import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.data.local.entity.Word
import com.menesdurak.squizd.databinding.FragmentQuizBinding
import com.menesdurak.squizd.presentation.words.WordsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private var categoryId = -1
    private var passedQuestionCount = 0
    private val QUESTION_COUNT = 5
    private var totalScore = 0

    private lateinit var randomIntArray: List<Int>
    private lateinit var questionList: List<String>
    private lateinit var rightAnswerList: List<String>
    private lateinit var answersList: List<List<String>>

    private val wordsViewModel: WordsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        val view = binding.root

        //Receiving clicked category's ID
        val args: QuizFragmentArgs by navArgs()
        categoryId = args.categoryId

        //Empty these variables because of restart quiz function
        passedQuestionCount = 0
        totalScore = 0
        randomIntArray = emptyList()
        questionList = emptyList()
        rightAnswerList = emptyList()
        answersList = emptyList()


        wordsViewModel.getAllWordsFromCategory(categoryId)

        wordsViewModel.wordsList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    //Create an array of random integers between 0 and list size - 1
                    randomIntArray = setRandomIntArray(it.data)

                    //Create questions for quiz
                    val word1: List<String> =
                        listOf(it.data[randomIntArray[0]].wordName, it.data[randomIntArray[0]].wordMeaning)
                    val word2: List<String> =
                        listOf(it.data[randomIntArray[1]].wordName, it.data[randomIntArray[1]].wordMeaning)
                    val word3: List<String> =
                        listOf(it.data[randomIntArray[2]].wordName, it.data[randomIntArray[2]].wordMeaning)
                    val word4: List<String> =
                        listOf(it.data[randomIntArray[3]].wordName, it.data[randomIntArray[3]].wordMeaning)
                    val word5: List<String> =
                        listOf(it.data[randomIntArray[4]].wordName, it.data[randomIntArray[4]].wordMeaning)

                    questionList = listOf(word1[0], word2[0], word3[0], word4[0], word5[0])
                    rightAnswerList = listOf(word1[1], word2[1], word3[1], word4[1], word5[1])

                    //Create an answer lists and shuffle them
                    var answer1: List<String> = listOf(word1[1], word2[1], word3[1], word4[1])
                    answer1 = answer1.shuffled()
                    var answer2: List<String> = listOf(word2[1], word3[1], word1[1], word5[1])
                    answer2 = answer2.shuffled()
                    var answer3: List<String> = listOf(word3[1], word2[1], word1[1], word4[1])
                    answer3 = answer3.shuffled()
                    var answer4: List<String> = listOf(word4[1], word1[1], word5[1], word2[1])
                    answer4 = answer4.shuffled()
                    var answer5: List<String> = listOf(word5[1], word4[1], word3[1], word1[1])
                    answer5 = answer5.shuffled()
                    answersList = listOf(answer1, answer2, answer3, answer4, answer5)

                    //Set up first question
                    setQuestion(
                        questionList[passedQuestionCount],
                        answersList[passedQuestionCount]
                    )
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                }

                Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textAnswer1.setOnClickListener {
            disableClickable()
            //If clicked answer is wrong, set background color red
            if (binding.textAnswer1.text.toString() != rightAnswerList[passedQuestionCount - 1]) {
                binding.textAnswer1.setBackgroundColor(resources.getColor(R.color.red, null))
            }
            //If answer is true, increase total score
            showTrueAnswer(rightAnswerList[passedQuestionCount - 1])
            if (rightAnswerList[passedQuestionCount - 1] == binding.textAnswer1.text.toString()) {
                totalScore++
            }
            //Set new question until reaching maximum question number
            if (passedQuestionCount < QUESTION_COUNT) {
                //Added delay
                //System will highlight true answer's background
                Handler(Looper.getMainLooper()).postDelayed({
                    resetBackgroundColors()
                    setQuestion(
                        questionList[passedQuestionCount],
                        answersList[passedQuestionCount]
                    )
                }, 2000)
            } else {
                passedQuestionCount--
                //If answer is true, increase total score
                if (rightAnswerList[passedQuestionCount - 1] == binding.textAnswer1.text.toString()) {
                    totalScore++
                }
                //Navigate to Quiz End Fragment
                val action = QuizFragmentDirections
                    .actionQuizFragmentToQuizEndFragment(categoryId, totalScore)
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(action)
                }, 2000)
            }
        }

        binding.textAnswer2.setOnClickListener {
            disableClickable()
            if (binding.textAnswer2.text.toString() != rightAnswerList[passedQuestionCount - 1]) {
                binding.textAnswer2.setBackgroundColor(resources.getColor(R.color.red, null))
            }
            showTrueAnswer(rightAnswerList[passedQuestionCount - 1])
            if (rightAnswerList[passedQuestionCount - 1] == binding.textAnswer2.text.toString()) {
                totalScore++
            }
            if (passedQuestionCount < QUESTION_COUNT) {
                Handler(Looper.getMainLooper()).postDelayed({
                    resetBackgroundColors()
                    setQuestion(
                        questionList[passedQuestionCount],
                        answersList[passedQuestionCount]
                    )
                }, 2000)
            } else {
                passedQuestionCount--
                if (rightAnswerList[passedQuestionCount - 1] == binding.textAnswer2.text.toString()) {
                    totalScore++
                }
                val action = QuizFragmentDirections
                    .actionQuizFragmentToQuizEndFragment(categoryId, totalScore)
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(action)
                }, 2000)
            }
        }

        binding.textAnswer3.setOnClickListener {
            disableClickable()
            if (binding.textAnswer3.text.toString() != rightAnswerList[passedQuestionCount - 1]) {
                binding.textAnswer3.setBackgroundColor(resources.getColor(R.color.red, null))
            }
            showTrueAnswer(rightAnswerList[passedQuestionCount - 1])
            if (rightAnswerList[passedQuestionCount - 1] == binding.textAnswer3.text.toString()) {
                totalScore++
            }
            if (passedQuestionCount < QUESTION_COUNT) {
                Handler(Looper.getMainLooper()).postDelayed({
                    resetBackgroundColors()
                    setQuestion(
                        questionList[passedQuestionCount],
                        answersList[passedQuestionCount]
                    )
                }, 2000)
            } else {
                passedQuestionCount--
                if (rightAnswerList[passedQuestionCount - 1] == binding.textAnswer3.text.toString()) {
                    totalScore++
                }
                val action = QuizFragmentDirections
                    .actionQuizFragmentToQuizEndFragment(categoryId, totalScore)
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(action)
                }, 2000)
            }
        }

        binding.textAnswer4.setOnClickListener {
            disableClickable()
            if (binding.textAnswer4.text.toString() != rightAnswerList[passedQuestionCount - 1]) {
                binding.textAnswer4.setBackgroundColor(resources.getColor(R.color.red, null))
            }
            showTrueAnswer(rightAnswerList[passedQuestionCount - 1])
            if (rightAnswerList[passedQuestionCount - 1] == binding.textAnswer4.text.toString()) {
                totalScore++
            }
            if (passedQuestionCount < QUESTION_COUNT) {
                Handler(Looper.getMainLooper()).postDelayed({
                    resetBackgroundColors()
                    setQuestion(
                        questionList[passedQuestionCount],
                        answersList[passedQuestionCount]
                    )
                }, 2000)
            } else {
                passedQuestionCount--
                if (rightAnswerList[passedQuestionCount - 1] == binding.textAnswer4.text.toString()) {
                    totalScore++
                }
                val action = QuizFragmentDirections
                    .actionQuizFragmentToQuizEndFragment(categoryId, totalScore)
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(action)
                }, 2000)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Creating an array from random int numbers. Max number is list.size-1.
    private fun setRandomIntArray(list: List<Word>): List<Int> {
        val intList = mutableListOf<Int>()

        while (intList.size < QUESTION_COUNT) {
            val randomInt = Random.nextInt(0, list.size)
            if (!intList.contains(randomInt))
                intList.add(randomInt)
        }
        return intList
    }

    //Set the next question
    private fun setQuestion(question: String, answersList: List<String>) {
        binding.textQuestion.text = question
        binding.textAnswer1.text = answersList[0]
        binding.textAnswer2.text = answersList[1]
        binding.textAnswer3.text = answersList[2]
        binding.textAnswer4.text = answersList[3]
        passedQuestionCount++
        enableClickable()
    }

    //Highlight the background of true answer
    private fun showTrueAnswer(answer: String) {
        when (answer) {
            binding.textAnswer1.text.toString() -> {
                binding.textAnswer1.setBackgroundColor(resources.getColor(R.color.green, null))
            }
            binding.textAnswer2.text.toString() -> {
                binding.textAnswer2.setBackgroundColor(resources.getColor(R.color.green, null))
            }
            binding.textAnswer3.text.toString() -> {
                binding.textAnswer3.setBackgroundColor(resources.getColor(R.color.green, null))
            }
            binding.textAnswer4.text.toString() -> {
                binding.textAnswer4.setBackgroundColor(resources.getColor(R.color.green, null))
            }
        }
    }

    private fun resetBackgroundColors() {
        binding.textAnswer1.setBackgroundColor(resources.getColor(R.color.sub3, null))
        binding.textAnswer2.setBackgroundColor(resources.getColor(R.color.sub3, null))
        binding.textAnswer3.setBackgroundColor(resources.getColor(R.color.sub3, null))
        binding.textAnswer4.setBackgroundColor(resources.getColor(R.color.sub3, null))
    }

    private fun disableClickable() {
        binding.textAnswer1.isClickable = false
        binding.textAnswer2.isClickable = false
        binding.textAnswer3.isClickable = false
        binding.textAnswer4.isClickable = false
    }

    private fun enableClickable() {
        binding.textAnswer1.isClickable = true
        binding.textAnswer2.isClickable = true
        binding.textAnswer3.isClickable = true
        binding.textAnswer4.isClickable = true
    }

}
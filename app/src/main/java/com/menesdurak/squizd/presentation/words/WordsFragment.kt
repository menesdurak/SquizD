package com.menesdurak.squizd.presentation.words

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.menesdurak.squizd.R
import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.data.local.entity.Word
import com.menesdurak.squizd.databinding.FragmentWordsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordsFragment : Fragment() {

    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!
    private val wordsViewModel: WordsViewModel by viewModels()
    private val wordAdapter by lazy { WordAdapter(::onWordClick, ::onWordLongClick) }

    private var categoryId = -1
    private val QUESTION_COUNT = 5
    private var wordsCount = 0

    private var mInterstitialAd: InterstitialAd? = null

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.to_bottom_anim
        )
    }
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadInter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWordsBinding.inflate(inflater, container, false)
        val view = binding.root

        val args: WordsFragmentArgs by navArgs()
        categoryId = args.categoryId

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wordsViewModel.getAllWordsFromCategory(categoryId)

        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = wordAdapter
        }

        binding.fabAddWord.setOnClickListener {
            val action =
                WordsFragmentDirections.actionWordsFragmentToAddOrEditWordFragment(categoryId = categoryId)
            findNavController().navigate(action)
        }

        wordsViewModel.wordsList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    wordsCount = it.data.size
                    wordAdapter.updateWordList(it.data)
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                }

                Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        binding.fabMain.setOnClickListener {
            onMainFabClicked()
        }

        binding.fabAddWord.setOnClickListener {
            val action =
                WordsFragmentDirections.actionWordsFragmentToAddOrEditWordFragment(categoryId)
            findNavController().navigate(action)
        }

        binding.fabGoToQuiz.setOnClickListener {

            if (wordsCount >= QUESTION_COUNT) {
                //Show add to user every three quizzes
                val sharedPref =
                    activity?.getSharedPreferences(
                        "addTimeComePreference",
                        Context.MODE_PRIVATE
                    )
                var addTime = sharedPref?.getInt("timesWorked", 2)
                val editor = sharedPref?.edit()
                if (addTime != null) {
                    if (addTime % 3 == 0) {
                        editor?.putInt("timesWorked", 1)
                        editor?.apply()
                        showInter()
                    } else {
                        addTime++
                        editor?.putInt("timesWorked", addTime)
                        editor?.apply()
                        val action = WordsFragmentDirections.actionWordsFragmentToQuizStartFragment(
                            categoryId
                        )
                        findNavController().navigate(action)
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "You should have at least $QUESTION_COUNT words to take the quiz.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun onMainFabClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)

        clicked = !clicked
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            binding.fabAddWord.isClickable = true
            binding.fabGoToQuiz.isClickable = true
        } else {
            binding.fabAddWord.isClickable = false
            binding.fabGoToQuiz.isClickable = false
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.fabAddWord.visibility = View.VISIBLE
            binding.fabGoToQuiz.visibility = View.VISIBLE
        } else {
            binding.fabAddWord.visibility = View.INVISIBLE
            binding.fabGoToQuiz.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.fabMain.startAnimation(rotateOpen)
            binding.fabAddWord.startAnimation(fromBottom)
            binding.fabGoToQuiz.startAnimation(fromBottom)
        } else {
            binding.fabGoToQuiz.startAnimation(toBottom)
            binding.fabAddWord.startAnimation(toBottom)
            binding.fabMain.startAnimation(rotateClose)
        }
    }


    private fun onWordLongClick(position: Int, word: Word) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Do you want to delete")
            .setPositiveButton("Yes") { _, _ ->
                wordsViewModel.deleteWordWithId(word.wordId)
                wordAdapter.deleteItem(position, word)
                wordsCount--
            }
            .setNegativeButton("No") { _, _ ->

            }
        builder.create()
        builder.show()
    }

    private fun onWordClick(wordId: Long, wordName: String, wordMeaning: String) {
        val action = WordsFragmentDirections.actionWordsFragmentToAddOrEditWordFragment(
            categoryId,
            wordId,
            wordName,
            wordMeaning
        )
        findNavController().navigate(action)
    }

    //Load ad before showing it to user
    private fun loadInter() {

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            requireContext(),
            "ca-app-pub-5100329894812782/7842679424",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    //Show loaded ad to user
    private fun showInter() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()

                    val action =
                        WordsFragmentDirections.actionWordsFragmentToQuizStartFragment(
                            categoryId
                        )
                    findNavController().navigate(action)
                }

            }
            mInterstitialAd?.show(requireActivity())
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
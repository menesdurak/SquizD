package com.menesdurak.squizd.presentation.quiz

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.menesdurak.squizd.databinding.FragmentQuizEndBinding
import com.menesdurak.squizd.presentation.words.WordsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizEndFragment : Fragment() {

    private var _binding: FragmentQuizEndBinding? = null
    private val binding get() = _binding!!

    private var categoryId = 0
    private var totalScore = 0

    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadInter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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

            //After selected count of quizzes show user an ad
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
                    val action = QuizEndFragmentDirections.actionQuizEndFragmentToQuizStartFragment(
                        categoryId
                    )
                    findNavController().navigate(action)
                }
            }

            binding.buttonQuizFinishGoToCategories.setOnClickListener {
                val action = QuizEndFragmentDirections.actionQuizEndFragmentToCategoriesFragment()
                findNavController().navigate(action)
            }
        }
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
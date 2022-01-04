package com.example.pomodorolike.ui.main_page

import android.content.res.Resources
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.databinding.DataBindingUtil
import com.example.pomodorolike.R
import com.example.pomodorolike.databinding.MainPageFragmentBinding
import android.widget.RelativeLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.Navigation


class MainPageFragment : Fragment(R.layout.main_page_fragment) {
    private lateinit var viewModel: MainPageViewModel
    private lateinit var binding: MainPageFragmentBinding
    lateinit var navController: NavController
    private var timerLengthMSeconds = 0L
    private var timerLengthSeconds = 0L
    private var timerLengthMinutes = 1
    private var numberOfCycles = 4
    private var numberOfCompleteCycles = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.main_page_fragment)
        navController = Navigation.findNavController(requireView())
    }

    companion object {
        fun newInstance() = MainPageFragment()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            requireActivity().window.statusBarColor =
                ContextCompat.getColor(requireActivity(), R.color.white)
        }

        binding.timerTxt.text = "25:00"

        arguments?.getInt("cycle_count")?.let {
            addIVCycleWorkPage(numberOfCycles, it)
            numberOfCompleteCycles = it
        }

//        binding.fiveMinBtn.setOnClickListener {
//            binding.timerTxt.text = "05:00"
//            timerLengthMinutes = 5
//
//        }
//        binding.tenMinBtn.setOnClickListener {
//            Log.e("TAG", "10")
//            binding.timerTxt.text = "10:00"
//            timerLengthMinutes = 10
//        }
//        binding.fifteenMinBtn.setOnClickListener {
//            Log.e("TAG", "15")
//            binding.timerTxt.text = "15:00"
//            timerLengthMinutes = 15
//
//        }
//        binding.twentyMinBtn.setOnClickListener {
//            Log.e("TAG", "twenty")
//            binding.timerTxt.text = "20:00"
//            timerLengthMinutes = 20
//
//        }
        timerLengthSeconds = timerLengthMinutes * 60L
        timerLengthMSeconds = timerLengthMinutes * 60000L
        binding.playBtn.setOnClickListener {
            viewModel.startTimer(1000)
            updateCountdownUI()
            updateButtonActiveState()
        }
        binding.pauseBtn.setOnClickListener {
            viewModel.pauseTimer()
            viewModel._mSecondsRemaining.observe(viewLifecycleOwner) {
                timerLengthMSeconds = it
            }
            updateButtonActiveState()
        }


    }

    private fun addIVCycleWorkPage(numberOfCycles: Int, xthCycle: Int) {
        var i = 1
        while (i < numberOfCycles + 1) {
            var id = i
            val ivWorkCycle = ImageView(requireContext())
            binding.circleGroup.addView(ivWorkCycle)
            ivWorkCycle.id = id
            ivWorkCycle.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            ivWorkCycle.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            params.addRule(RelativeLayout.RIGHT_OF, id - 1)
            ivWorkCycle.layoutParams = params
            ivWorkCycle.setMargins(left = 5.px, right = 5.px)
            if (i < xthCycle + 1) {
                ivWorkCycle.setImageDrawable(resources.getDrawable(R.drawable.ic_full_cycle_work))

            } else {
                ivWorkCycle.setImageDrawable(resources.getDrawable(R.drawable.ic_empty_cycle_work))
            }
            i++
        }
    }

    fun View.setMargins(
        left: Int = this.marginLeft,
        top: Int = this.marginTop,
        right: Int = this.marginRight,
        bottom: Int = this.marginBottom,
    ) {
        layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
            setMargins(left, top, right, bottom)
        }
    }

    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    fun updateCountdownUI() {
        viewModel._mSecondsRemaining.observe(viewLifecycleOwner) {
            var minutesUntilFinished = it / 60000
            var secondInMinuteUntilFinished = (it / 1000) - minutesUntilFinished * 60
            var secondsStr = secondInMinuteUntilFinished.toString()
            binding.timerTxt.text = "$minutesUntilFinished:${
                if (secondsStr.length == 2) secondsStr
                else "0" + secondsStr
            }"
            Log.e("TAG", (it / 1000).toInt().toString())
            binding.progressCountdown.max = timerLengthSeconds.toInt()
            binding.progressCountdown.progress = (it / 1000).toInt()
        }
    }

    fun updateButtonActiveState() {
        viewModel._timerState.observe(viewLifecycleOwner) {
            when (it) {
                MainPageViewModel.TimerState.Running -> {
                    binding.pauseBtn.isEnabled = true
                    binding.pauseBtn.isVisible = true
                    binding.playBtn.isEnabled = false

                }
                MainPageViewModel.TimerState.Paused -> {
                    binding.pauseBtn.isEnabled = false
                    binding.playBtn.isEnabled = true
                    binding.pauseBtn.isVisible = false
                    binding.playBtn.isVisible = true
                }
                else -> {
                    navController.navigate(R.id.action_mainPageFragment2_to_restPageFragment2,
                        bundleOf("cycle_count" to numberOfCompleteCycles))
                }
            }
        }

    }
}
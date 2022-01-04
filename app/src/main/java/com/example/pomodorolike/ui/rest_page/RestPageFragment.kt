package com.example.pomodorolike.ui.rest_page

import android.content.res.Resources
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.pomodorolike.R
import com.example.pomodorolike.databinding.RestPageFragmentBinding

class RestPageFragment : Fragment(R.layout.rest_page_fragment) {
    private lateinit var binding: RestPageFragmentBinding
    private lateinit var viewModel: RestPageViewModel
    lateinit var navController: NavController

    private var timerLengthMSeconds = 0L
    private var timerLengthSeconds = 0L
    private var timerLengthMinutes = 5
    private var numberOfCycles = 4


    companion object {
        fun newInstance() = RestPageFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAg", "test")
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.rest_page_fragment)
        navController = Navigation.findNavController(view)

        getView()?.setBackgroundColor(resources.getColor(R.color.orange))

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            requireActivity().window.statusBarColor =
                ContextCompat.getColor(requireActivity(), R.color.orange)
        }
        setBreakText(timerLengthMinutes)

        binding.timerTxt.text = "05:00"

        viewModel = ViewModelProvider(this).get(RestPageViewModel::class.java)

        getPrevCycleCount()
        addIVCycleWorkPage(numberOfCycles,viewModel.initialNumber)

        timerLengthSeconds = timerLengthMinutes * 60L
        timerLengthMSeconds = timerLengthMinutes * 60000L
        viewModel.startTimer(timerLengthMSeconds)
        updateCountdownUI()
        binding.pauseBtn.setOnClickListener {
            viewModel.pauseTimer()
            viewModel._mSecondsRemaining.observe(viewLifecycleOwner) {
                timerLengthMSeconds = it
            }
            updateButtonActiveState()
        }
        binding.playBtn.setOnClickListener {
            viewModel.startTimer(timerLengthMSeconds)
            updateCountdownUI()
            updateButtonActiveState()
        }
        updateButtonActiveState()
    }

    fun getPrevCycleCount() {
        viewModel.initialNumber = arguments?.getInt("cycle_count")!!
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
                ivWorkCycle.setImageDrawable(resources.getDrawable(R.drawable.ic_full_cycle_rest))

            } else {
                ivWorkCycle.setImageDrawable(resources.getDrawable(R.drawable.ic_empty_cycle_rest))
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
                RestPageViewModel.TimerState.Running -> {
                    binding.pauseBtn.isEnabled = true
                    binding.pauseBtn.isVisible = true
                    binding.playBtn.isEnabled = false

                }
                RestPageViewModel.TimerState.Paused -> {
                    binding.pauseBtn.isEnabled = false
                    binding.playBtn.isEnabled = true
                    binding.pauseBtn.isVisible = false
                    binding.playBtn.isVisible = true
                }
                else -> {
                    viewModel._completeCycleCount.value = ++viewModel.initialNumber
                    viewModel._completeCycleCount.observe(viewLifecycleOwner) {
                        navController.navigate(
                            R.id.action_restPageFragment2_to_mainPageFragment2,
                            bundleOf("cycle_count" to it)
                        )

                    }
                }
            }
        }

    }
    fun setBreakText(length: Int){
        if(length == 5){
            binding.workStateTxt.text = resources.getText(R.string.break_state_short)
        }else{
            binding.workStateTxt.text = resources.getText(R.string.break_state_long)

        }
    }
}
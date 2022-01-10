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
import com.example.pomodorolike.data.preferences.PrefRepository
import com.example.pomodorolike.databinding.RestPageFragmentBinding

class RestPageFragment : Fragment(R.layout.rest_page_fragment) {
    private lateinit var binding: RestPageFragmentBinding
    private lateinit var viewModel: RestPageViewModel
    lateinit var navController: NavController
    private val prefRepository by lazy { PrefRepository(requireContext()) }

    private var timerLengthMSeconds = 0L
    private var timerLengthSeconds = 0L
    private var timerLengthMinutes = 0L
    private var timerLengthHours = 0L
    private var longBreakLengthMSeconds = 0L
    private var longBreakLengthSeconds = 0L
    private var longBreakMinutes = 0L
    private var longBreakLengthHours = 0L
    private var autoStartTimer= true
    private var numberOfCycles = 0


    companion object {
        fun newInstance() = RestPageFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAg", "test")
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.rest_page_fragment)
        navController = Navigation.findNavController(view)
        setPageBackgroundColor()
        if(prefRepository.getShortBreakTimerLengthHours() == 0L && prefRepository.getShortBreakTimerLengthMinutes() == 0L){
            timerLengthMinutes = 5L
            numberOfCycles = 4
            longBreakMinutes = 15L
        }else{
            initializeVariables()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RestPageViewModel::class.java)
        setStatusBar()
        binding.timerTxt.text = "$timerLengthMinutes:00"
        openSettings()
        getPrevCycleCount()
        addIVCycleWorkPage(numberOfCycles, viewModel.initialNumber)


        if (viewModel.initialNumber == numberOfCycles - 1) {
            binding.workStateTxt.text = resources.getText(R.string.break_state_long)
            binding.progressCountdown.max = longBreakLengthSeconds.toInt()
            longBreakMinutes += (longBreakLengthHours * 60L)
            longBreakLengthSeconds = longBreakMinutes * 60L
            longBreakLengthMSeconds = longBreakMinutes * 60000L
            if (prefRepository.getAutoStartBreaks()) {
                updateButtonActiveState()
                viewModel.startTimer(longBreakLengthMSeconds)

            } else {
                binding.playBtn.setOnClickListener {
                    viewModel.startTimer(longBreakLengthMSeconds)
                    updateCountdownUI()
                    updateButtonActiveState()
                }
            }
        } else {
            binding.workStateTxt.text = resources.getText(R.string.break_state_short)
            timerLengthMinutes += (timerLengthHours * 60L)
            timerLengthSeconds = timerLengthMinutes * 60L
            timerLengthMSeconds = timerLengthMinutes * 60000L
            if (prefRepository.getAutoStartBreaks()) {
                viewModel.startTimer(timerLengthMSeconds)
            } else {
                binding.playBtn.setOnClickListener {
                    viewModel.startTimer(timerLengthMSeconds)
                    updateCountdownUI()
                    updateButtonActiveState()
                }
            }
        }
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
            if (viewModel.initialNumber == numberOfCycles - 1) {
                binding.progressCountdown.max = longBreakLengthSeconds.toInt()
            } else {
                binding.progressCountdown.max = timerLengthSeconds.toInt()

            }
            binding.progressCountdown.progress = (it / 1000).toInt()
        }
    }

    private fun updateButtonActiveState() {
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
                            R.id.action_restPageFragment_to_mainPageFragment,
                            bundleOf("cycle_count" to it)
                        )

                    }
                }
            }
        }

    }

    private fun setBreakText() {
        if (viewModel.initialNumber == numberOfCycles - 1) {

        } else {

        }
    }

    private fun openSettings() {
        binding.toolBarSettingsBtn.setOnClickListener {
            viewModel.pauseTimer()
            navController.navigate(
                R.id.action_restPageFragment_to_settingsPageFragment,
                bundleOf()
            )

        }
    }

    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            requireActivity().window.statusBarColor =
                ContextCompat.getColor(requireActivity(), R.color.orange)
        }
    }

    private fun setPageBackgroundColor() {
        view?.setBackgroundColor(resources.getColor(R.color.orange))
    }

    private fun initializeVariables() {
        timerLengthMSeconds = prefRepository.getShortBreakTimerLengthMSeconds()
        timerLengthSeconds = prefRepository.getShortBreakTimerLengthSeconds()
        timerLengthMinutes = prefRepository.getShortBreakTimerLengthMinutes()
        timerLengthHours = prefRepository.getShortBreakTimerLengthHours()
        numberOfCycles = prefRepository.getNumberOfCycles()
        longBreakLengthSeconds = prefRepository.getLongBreakTimerLengthSeconds()
        longBreakLengthHours = prefRepository.getLongBreakTimerLengthHours()
        longBreakLengthMSeconds = prefRepository.getLongBreakTimerLengthMSeconds()
        longBreakMinutes = prefRepository.getLongBreakTimerLengthMinutes()
        autoStartTimer = prefRepository.getAutoStartBreaks()
    }
}
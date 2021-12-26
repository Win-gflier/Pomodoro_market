package com.example.pomodorolike.ui.main_page

import android.nfc.Tag
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.pomodorolike.R
import com.example.pomodorolike.databinding.MainPageFragmentBinding

class MainPageFragment : Fragment(R.layout.main_page_fragment) {
    private lateinit var viewModel: MainPageViewModel
    private lateinit var binding: MainPageFragmentBinding
    private lateinit var timerState: MainPageViewModel.TimerState
    private var timerLengthMSeconds = 0L
    private var timerLengthSeconds = 0L
    private var timerLengthMinutes = 25


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.main_page_fragment)
    }

//    override fun onResume() {
//        super.onResume()
//        initTimer()
//    }
//
//    fun initTimer(){
//        viewModel.timerLengthSeconds().observe(viewLifecycleOwner) {
//            timerLengthSeconds = it
//            if (timerState == MainPageViewModel.TimerState.Stopped){
//                viewModel.setNewTimerLength(timerLengthMinutes)
//                binding.progressCountdown.max = timerLengthSeconds.toInt()
//            }
//            else{
//                binding.progressCountdown.max = timerLengthSeconds.toInt()
//            }
//            if (timerState == MainPageViewModel.TimerState.Running || timerState == MainPageViewModel.TimerState.Paused){
//                viewModel.secondsRemaining().observe(viewLifecycleOwner, Observer {
//                    secondsRemaining = it
//                })
//            }
//            else{
//                secondsRemaining = timerLengthSeconds
//            }
//
//            if(timerState == MainPageViewModel.TimerState.Running){
//                viewModel.startTimer(secondsRemaining * 1000)
//            }
//            viewModel.updateButtons()
//            updateCountdownUI()
//        }
//
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if (timerState == MainPageViewModel.TimerState.Running){
//            viewModel.pauseTimer()
//            //StartBackgroundTimer
//        }
//        else if(timerState == MainPageViewModel.TimerState.Paused){
//            //show notif and pass the state
//        }
//
//    }
//    fun getLastTime(){
//
//    }
//

//
//    companion object {
//        fun newInstance() = MainPageFragment()
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)
        binding.timerTxt.text = "25:00"
        binding.fiveMinBtn.setOnClickListener {
            binding.timerTxt.text = "05:00"
            timerLengthMinutes = 5

        }
        binding.tenMinBtn.setOnClickListener {
            Log.e("TAG", "10")
            binding.timerTxt.text = "10:00"
            timerLengthMinutes = 10
        }
        binding.fifteenMinBtn.setOnClickListener {
            Log.e("TAG", "15")
            binding.timerTxt.text = "15:00"
            timerLengthMinutes = 15

        }
        binding.twentyMinBtn.setOnClickListener {
            Log.e("TAG", "twenty")
            binding.timerTxt.text = "20:00"
            timerLengthMinutes = 20

        }
        binding.playBtn.setOnClickListener {
            Log.e("TAG", "play")
            timerLengthSeconds = timerLengthMinutes * 60L
            timerLengthMSeconds = timerLengthMinutes * 60000L
            viewModel.startTimer(timerLengthMSeconds)
            updateCountdownUI()
            updateButtonActiveState()
        }
        binding.pauseBtn.setOnClickListener {
            viewModel.pauseTimer()
            viewModel.mSecondsRemaining().observe(viewLifecycleOwner){
                timerLengthMSeconds = it
            }
            updateButtonActiveState()
        }
        binding.stopBtn.setOnClickListener {
            viewModel.stopTimer()
            binding.progressCountdown.progress = 0
            binding.timerTxt.text = "25:00"
            timerLengthMinutes = 25
            updateButtonActiveState()

        }


    }

    fun updateCountdownUI() {
        viewModel.mSecondsRemaining().observe(viewLifecycleOwner) {
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
        viewModel.timerState().observe(viewLifecycleOwner) {
            when (it) {
                MainPageViewModel.TimerState.Running -> {
                    binding.stopBtn.isEnabled = true
                    binding.pauseBtn.isEnabled = true
                    binding.playBtn.isEnabled = false
                    binding.fiveMinBtn.isEnabled = false
                    binding.tenMinBtn.isEnabled = false
                    binding.fifteenMinBtn.isEnabled = false
                    binding.twentyMinBtn.isEnabled = false
                }
                MainPageViewModel.TimerState.Paused -> {
                    binding.stopBtn.isEnabled = true
                    binding.pauseBtn.isEnabled = false
                    binding.playBtn.isEnabled = true
                }
                else -> {
                    binding.stopBtn.isEnabled = false
                    binding.pauseBtn.isEnabled = true
                    binding.playBtn.isEnabled = true
                    binding.fiveMinBtn.isEnabled = true
                    binding.tenMinBtn.isEnabled = true
                    binding.fifteenMinBtn.isEnabled = true
                    binding.twentyMinBtn.isEnabled = true
                }
            }
        }

    }

}
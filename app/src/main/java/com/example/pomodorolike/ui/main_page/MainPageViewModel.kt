package com.example.pomodorolike.ui.main_page

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainPageViewModel : ViewModel() {
    private lateinit var timer: CountDownTimer
    val _mSecondsRemaining: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }
    val _timerState: MutableLiveData<TimerState> by lazy{
        MutableLiveData<TimerState>(TimerState.Uninitialized)
    }


    enum class TimerState {
        Stopped, Paused, Running, Uninitialized
    }


    fun startTimer(time: Long) {
        _timerState.value = TimerState.Running
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(p0: Long) {
                _mSecondsRemaining.value = p0
            }
            override fun onFinish() = stopTimer()
        }.start()
    }


    fun pauseTimer() {
        _timerState.value = TimerState.Paused
        timer.cancel()
    }

    fun stopTimer() {
        _timerState.value = TimerState.Stopped
        timer.cancel()
    }

}
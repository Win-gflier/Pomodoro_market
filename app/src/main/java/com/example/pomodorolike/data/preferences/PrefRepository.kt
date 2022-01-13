package com.example.pomodorolike.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.pomodorolike.data.*

class PrefRepository(
    context: Context
) {


    private val preference: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor = preference.edit()


    //Focus Time Setters

    fun setFocusTimerLengthHours(hours:Long){
        FOCUS_TIMER_LENGTH_HOURS.put(hours)
    }
    fun setFocusTimerLengthMinutes(minutes:Long){
        FOCUS_TIMER_LENGTH_MINUTES.put(minutes)
    }
    fun setFocusTimerLengthSeconds(seconds:Long){
        FOCUS_TIMER_LENGTH_SECONDS.put(seconds)
    }
    fun setFocusTimerLengthMSeconds(mSeconds:Long){
        FOCUS_TIMER_LENGTH_MILLI_SECONDS.put(mSeconds)
    }

    //Focus Time Getters

    fun getFocusTimerLengthHours() = FOCUS_TIMER_LENGTH_HOURS.getLong()

    fun getFocusTimerLengthMinutes() = FOCUS_TIMER_LENGTH_MINUTES.getLong()

    fun getFocusTimerLengthSeconds() = FOCUS_TIMER_LENGTH_SECONDS.getLong()

    fun getFocusTimerLengthMSeconds() = FOCUS_TIMER_LENGTH_MILLI_SECONDS.getLong()


    //Short Break Time Setters

    fun setShortBreakTimerLengthHours(hours:Long){
        SHORT_BREAK_TIMER_LENGTH_HOURS.put(hours)
    }
    fun setShortBreakTimerLengthMinutes(minutes:Long){
        SHORT_BREAK_TIMER_LENGTH_MINUTES.put(minutes)
    }
    fun setShortBreakTimerLengthSeconds(seconds:Long){
        SHORT_BREAK_TIMER_LENGTH_SECONDS.put(seconds)
    }
    fun setShortBreakTimerLengthMSeconds(mSeconds:Long){
        SHORT_BREAK_TIMER_LENGTH_MILLI_SECONDS.put(mSeconds)
    }

    //Short Break Time Getters
    fun getShortBreakTimerLengthHours() = SHORT_BREAK_TIMER_LENGTH_HOURS.getLong()

    fun getShortBreakTimerLengthMinutes() = SHORT_BREAK_TIMER_LENGTH_MINUTES.getLong()

    fun getShortBreakTimerLengthSeconds() = SHORT_BREAK_TIMER_LENGTH_SECONDS.getLong()

    fun getShortBreakTimerLengthMSeconds() = SHORT_BREAK_TIMER_LENGTH_MILLI_SECONDS.getLong()

    //Long Break Time Setters

    fun setLongBreakTimerLengthHours(hours:Long){
        LONG_BREAK_TIMER_LENGTH_HOURS.put(hours)
    }
    fun setLongBreakTimerLengthMinutes(minutes:Long){
        LONG_BREAK_TIMER_LENGTH_MINUTES.put(minutes)
    }
    fun setLongBreakTimerLengthSeconds(seconds:Long){
        LONG_BREAK_TIMER_LENGTH_SECONDS.put(seconds)
    }
    fun setLongBreakTimerLengthMSeconds(mSeconds:Long){
        LONG_BREAK_TIMER_LENGTH_MILLI_SECONDS.put(mSeconds)
    }

    //Short Break Time Getters
    fun getLongBreakTimerLengthHours() = LONG_BREAK_TIMER_LENGTH_HOURS.getLong()

    fun getLongBreakTimerLengthMinutes() = LONG_BREAK_TIMER_LENGTH_MINUTES.getLong()

    fun getLongBreakTimerLengthSeconds() = LONG_BREAK_TIMER_LENGTH_SECONDS.getLong()

    fun getLongBreakTimerLengthMSeconds() = LONG_BREAK_TIMER_LENGTH_MILLI_SECONDS.getLong()


    //Number of Cycles Setters
    fun setNumberOfCycles(cycleCount:Int){
        NUMBER_OF_CYCLES.put(cycleCount)
    }

    //Number of Cycles Getters
    fun getNumberOfCycles() = NUMBER_OF_CYCLES.getInt()

    //Auto Start Setters
    fun setAutoStartBreaks(bool:Boolean){
        AUTO_START_BREAKS.put(bool)
    }
    fun setAutoStartWorkTime(bool:Boolean){
        AUTO_START_WORK_TIME.put(bool)
    }

    //Auto Start Getters
    fun getAutoStartBreaks() = AUTO_START_BREAKS.getBoolean()
    fun getAutoStartWorkTime() = AUTO_START_WORK_TIME.getBoolean()




    fun clearData() {
        editor.clear()
        editor.commit()
    }

    //KOTLIN EXTENSION FUNCTIONS
    private fun String.put(long: Long) {
        editor.putLong(this, long)
        editor.commit()
    }

    private fun String.put(int: Int) {
        editor.putInt(this, int)
        editor.commit()
    }

    private fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    private fun String.put(boolean: Boolean) {
        editor.putBoolean(this, boolean)
        editor.commit()
    }

    private fun String.getLong() = preference.getLong(this, 0)

    private fun String.getInt() = preference.getInt(this, 0)

    private fun String.getString() = preference.getString(this, "")!!

    private fun String.getBoolean() = preference.getBoolean(this, false)
}
package com.example.pomodorolike.ui.settings_page

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.pomodorolike.R
import com.example.pomodorolike.data.preferences.PrefRepository
import com.example.pomodorolike.databinding.SettingsPageFragmentBinding
import com.super_rabbit.wheel_picker.OnValueChangeListener
import com.super_rabbit.wheel_picker.WheelPicker
import android.widget.EditText

import android.widget.NumberPicker
import java.lang.IllegalArgumentException
import java.lang.reflect.Field


class SettingsPageFragment : Fragment(R.layout.settings_page_fragment) {
    private lateinit var viewModel: SettingsPageViewModel
    private lateinit var binding: SettingsPageFragmentBinding
    lateinit var navController: NavController
    private val prefRepository by lazy { PrefRepository(requireContext()) }


    companion object {
        fun newInstance() = SettingsPageFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.settings_page_fragment)
        navController = Navigation.findNavController(view)
       // setPageBackgroundColor()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SettingsPageViewModel::class.java)
        setStatusBar()
        onEndBreakSoundChooseClick()
        onEndFocusSoundChooseClick()
//        setNumberPickerProperties()
        setOrangeText()
        onFocusClick()
        onShortBreakClick()
        onLongBreakClick()
        onCycleCountClick()
        onFocusTimeSelected()
        onShortBreakTimeSelected()
        onLongBreakTimeSelected()
        cycleCountSelection()
        onAutoStartBreakTimeSwitch()
        onAutoStartWorkTimeSwitch()
        updateBreakSwitchState()
        updateWorkSwitchState()
        onToolbarBackBtnClick()

    }

    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            requireActivity().window.statusBarColor =
                ContextCompat.getColor(requireActivity(), R.color.orange_light)
        }
    }

    private fun onEndFocusSoundChooseClick() {
        binding.dropdownEndFocusSoundBtn.setOnClickListener {
            navController.navigate(R.id.action_settingsPageFragment_to_focusSoundPageFragment)
        }
    }

    private fun onEndBreakSoundChooseClick() {
        binding.dropdownEndBreakSoundBtn.setOnClickListener {
            navController.navigate(R.id.action_settingsPageFragment_to_endSoundPageFragment)
        }
    }

    private fun onFocusClick() {
        binding.relativeLayoutDropdownFocus.setOnClickListener {
            if (binding.focusTimePicker.visibility == View.GONE) {
                binding.focusTimePicker.visibility = View.VISIBLE
                binding.dropdownFocusBtn.setBackgroundResource(R.drawable.ic_up_arrow)
            } else {
                binding.focusTimePicker.visibility = View.GONE
                binding.dropdownFocusBtn.setBackgroundResource(R.drawable.ic_down_arrow)

            }
        }
    }

    private fun onShortBreakClick() {
        binding.relativeLayoutDropdownLongBreak.setOnClickListener {
            if (binding.longBreakPicker.visibility == View.GONE) {
                binding.longBreakPicker.visibility = View.VISIBLE
                binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_up_arrow)
            } else {
                binding.longBreakPicker.visibility = View.GONE
                binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)

            }
        }
    }

    private fun onLongBreakClick() {
        binding.relativeLayoutDropdownShortBreak.setOnClickListener {
            if (binding.shortBreakPicker.visibility == View.GONE) {
                binding.shortBreakPicker.visibility = View.VISIBLE
                binding.dropdownShortBreakBtn.setBackgroundResource(R.drawable.ic_up_arrow)
            } else {
                binding.shortBreakPicker.visibility = View.GONE
                binding.dropdownShortBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)

            }
        }
    }

    private fun onCycleCountClick() {
        binding.relativeLayoutDropdownNumberOfCycles.setOnClickListener {
            if (binding.cycleCountPicker.visibility == View.GONE) {
                binding.cycleCountPicker.visibility = View.VISIBLE
                binding.dropdownCycleCountBtn.setBackgroundResource(R.drawable.ic_up_arrow)
            } else {
                binding.cycleCountPicker.visibility = View.GONE
                binding.dropdownCycleCountBtn.setBackgroundResource(R.drawable.ic_down_arrow)

            }
        }
    }

    private fun onFocusTimeSelected() {
        binding.focusPickerHours.setOnValueChangedListener { _, _, newVal ->
            val newValue = newVal.toLong()
            prefRepository.setFocusTimerLengthHours(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getFocusTimerLengthHours().toString() + " hours",
                binding.dropdownFocusHoursTxt
            )

        }


        binding.focusPickerMinutes.setOnValueChangedListener { _, _, newVal ->
            val newValue = newVal.toLong()
            prefRepository.setFocusTimerLengthMinutes(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getFocusTimerLengthMinutes().toString() + " min",
                binding.dropdownFocusMinutesTxt
            )
        }
    }

    private fun onShortBreakTimeSelected() {
        binding.shortBreakPickerHours.setOnValueChangedListener { _, _, newVal ->
            val newValue: Long = newVal.toLong()
            prefRepository.setShortBreakTimerLengthHours(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getShortBreakTimerLengthHours().toString() + " hours",
                binding.dropdownShortBreakHoursTxt
            )
        }
        binding.shortBreakPickerMinutes.setOnValueChangedListener { _, _, newVal ->
            val newValue = newVal.toLong()
            prefRepository.setShortBreakTimerLengthMinutes(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getShortBreakTimerLengthMinutes().toString() + " min",
                binding.dropdownShortBreakMinutesTxt
            )
        }
    }

    private fun onLongBreakTimeSelected() {
        binding.longBreakPickerHours.setOnValueChangedListener { picker, oldVal, newVal ->
            val newValue = newVal.toLong()
            prefRepository.setLongBreakTimerLengthHours(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getLongBreakTimerLengthHours().toString() + " hours",
                binding.dropdownLongBreakHoursTxt
            )
        }
        binding.longBreakPickerMinutes.setOnValueChangedListener { picker, oldVal, newVal ->
            val newValue = newVal.toLong()
            prefRepository.setLongBreakTimerLengthMinutes(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getLongBreakTimerLengthMinutes().toString() + " min",
                binding.dropdownLongBreakMinutesTxt
            )
        }
    }

    private fun cycleCountSelection() {
        var numberOfCycles = prefRepository.getNumberOfCycles()
        binding.subtractCycleCountBtn.setOnClickListener {
            if (numberOfCycles > 0) {
                prefRepository.setNumberOfCycles(--numberOfCycles)
                binding.cycleCountInsideTxt.text = prefRepository.getNumberOfCycles().toString()
                if (numberOfCycles == 0) {
                    binding.dropdownCycleCountTxt.visibility = View.GONE
                    prefRepository.setNumberOfCycles(numberOfCycles)
                } else {
                    binding.dropdownCycleCountTxt.visibility = View.VISIBLE
                    prefRepository.setNumberOfCycles(numberOfCycles)
                    binding.dropdownCycleCountTxt.text =
                        prefRepository.getNumberOfCycles().toString()
                }
            }
        }
        binding.addCycleCountBtn.setOnClickListener {
            if (numberOfCycles >= 0) {
                prefRepository.setNumberOfCycles(++numberOfCycles)
                binding.cycleCountInsideTxt.text = prefRepository.getNumberOfCycles().toString()
                if (numberOfCycles == 0) {
                    binding.dropdownCycleCountTxt.visibility = View.GONE
                    prefRepository.setNumberOfCycles(numberOfCycles)
                } else {
                    binding.dropdownCycleCountTxt.visibility = View.VISIBLE
                    prefRepository.setNumberOfCycles(numberOfCycles)
                    binding.dropdownCycleCountTxt.text =
                        prefRepository.getNumberOfCycles().toString()

                }
            }
            prefRepository.setNumberOfCycles(numberOfCycles)


        }
    }

/*    private fun setNumberPickerProperties() {
        //focus time
//        binding.focusPickerHours.minValue = 0
//        binding.focusPickerMinutes.minValue = 0
//        binding.focusPickerHours.maxValue = 24
//        binding.focusPickerMinutes.maxValue = 59

        //short break
        binding.shortBreakPickerHours.minValue = 0
        binding.shortBreakPickerMinutes.minValue = 0
        binding.shortBreakPickerHours.maxValue = 24
        binding.shortBreakPickerMinutes.maxValue = 59
        //long break
        binding.longBreakPickerHours.minValue = 0
        binding.longBreakPickerMinutes.minValue = 0
        binding.longBreakPickerHours.maxValue = 24
        binding.longBreakPickerMinutes.maxValue = 59
        //cycleCount
    }*/

    private fun setOrangeText() {

        binding.cycleCountInsideTxt.text = prefRepository.getNumberOfCycles().toString()

        var focusHours = prefRepository.getFocusTimerLengthHours()
        var focusMinutes = prefRepository.getFocusTimerLengthMinutes()
        var shortBreakHours = prefRepository.getShortBreakTimerLengthHours()
        var shortBreakMinutes = prefRepository.getShortBreakTimerLengthMinutes()
        var longBreakHours = prefRepository.getLongBreakTimerLengthHours()
        var longBreakMinutes = prefRepository.getLongBreakTimerLengthMinutes()
        var cycleCount = prefRepository.getNumberOfCycles()


        viewModel.updateOrangeTextsLong(
            focusHours,
            focusHours.toString() + " hours",
            binding.dropdownFocusHoursTxt
        )

        viewModel.updateOrangeTextsLong(
            focusMinutes,
            focusMinutes.toString() + " min",
            binding.dropdownFocusMinutesTxt
        )

        viewModel.updateOrangeTextsLong(
            shortBreakHours,
            shortBreakHours.toString() + " hours",
            binding.dropdownShortBreakHoursTxt
        )

        viewModel.updateOrangeTextsLong(
            shortBreakMinutes,
            shortBreakMinutes.toString() + " min",
            binding.dropdownShortBreakMinutesTxt
        )

        viewModel.updateOrangeTextsLong(
            longBreakHours,
            longBreakHours.toString() + " hours",
            binding.dropdownLongBreakHoursTxt
        )

        viewModel.updateOrangeTextsLong(
            longBreakMinutes,
            longBreakMinutes.toString() + " min",
            binding.dropdownLongBreakMinutesTxt
        )

        viewModel.updateOrangeTextsInt(
            cycleCount,
            cycleCount.toString(),
            binding.dropdownCycleCountTxt
        )

    }

    private fun onAutoStartBreakTimeSwitch() {
        binding.autoBreakSwitch.setOnClickListener {
            prefRepository.setAutoStartBreaks(binding.autoBreakSwitch.isChecked)
        }
    }

    private fun onAutoStartWorkTimeSwitch() {
        binding.autoWorkSwitch.setOnClickListener {
            prefRepository.setAutoStartWorkTime(binding.autoWorkSwitch.isChecked)
        }
    }

    private fun updateBreakSwitchState() {
        binding.autoBreakSwitch.isChecked = prefRepository.getAutoStartBreaks()
    }

    private fun updateWorkSwitchState() {
        binding.autoWorkSwitch.isChecked = prefRepository.getAutoStartWorkTime()
    }

    private fun onToolbarBackBtnClick() {
        binding.toolBarBackBtn.setOnClickListener {
            navController.navigate(R.id.action_settingsPageFragment_to_mainPageFragment)
        }
    }

/*    private fun setPageBackgroundColor() {
        binding.nestedScrollView.setBackgroundColor(resources.getColor(R.color.orange_light))
        binding.toolBarSettingsPage.setBackgroundColor(resources.getColor(R.color.orange_light))
        binding.relativeLayoutDropdownNumberOfCycles.setBackgroundColor(resources.getColor(R.color.white))
        binding.relativeLayoutDropdownLongBreak.setBackgroundColor(resources.getColor(R.color.white))
        binding.relativeLayoutDropdownAutoBreak.setBackgroundColor(resources.getColor(R.color.white))
        binding.relativeLayoutDropdownAutoWork.setBackgroundColor(resources.getColor(R.color.white))
        binding.relativeLayoutDropdownEndBreakSound.setBackgroundColor(resources.getColor(R.color.white))
        binding.relativeLayoutDropdownEndFocusSound.setBackgroundColor(resources.getColor(R.color.white))
        binding.relativeLayoutDropdownFocus.setBackgroundColor(resources.getColor(R.color.white))
        binding.relativeLayoutDropdownNotifications.setBackgroundColor(resources.getColor(R.color.white))
        binding.relativeLayoutDropdownShortBreak.setBackgroundColor(resources.getColor(R.color.white))
    }*/

    /*    @SuppressLint("SoonBlockedPrivateApi")
    fun setSelectedTextColor(np: NumberPicker, colorRes: Int) {
        val count = np.childCount
        for (i in 0 until count) {
            val child = np.getChildAt(i)
            if (child is EditText) {
                try {
                    val selectorWheelPaintField =
                        np.javaClass.getDeclaredField("mSelectorWheelPaint")
                    selectorWheelPaintField.isAccessible = true
                    child.setTextColor(requireContext().getResources().getColor(colorRes))
                    np.performClick()
                } catch (e: NoSuchFieldException) {
                } catch (e: IllegalArgumentException) {
                }
            }
        }
    }*/

/*
    fun setNumberPickerTextColor(numberPicker: NumberPicker) {
        val color: Int = resources.getColor(R.color.orange)
        val count = numberPicker.childCount
        for (i in 0 until count) {
            val child = numberPicker.getChildAt(i)
            if (child is EditText) {
                try {
                    val selectorWheelPaintField =
                        numberPicker.javaClass.getField("mSelectorWheelPaint")
                    selectorWheelPaintField.isAccessible = true
                    (selectorWheelPaintField[numberPicker] as Paint).color = color
                    child.setTextColor(color)
                    numberPicker.invalidate()
                } catch (e: NoSuchFieldException) {
                    Log.e("setNumberPickerColor1", "" + e)
                } catch (e: IllegalAccessException) {
                    Log.e("setNumberPickerColor2", "" + e)
                } catch (e: IllegalArgumentException) {
                    Log.e("setNumberPickerColor3", "" + e)
                }
            }
        }
    }*/
}
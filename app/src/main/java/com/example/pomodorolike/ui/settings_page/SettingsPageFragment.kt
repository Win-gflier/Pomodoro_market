package com.example.pomodorolike.ui.settings_page

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.pomodorolike.R
import com.example.pomodorolike.databinding.SettingsPageFragmentBinding
import com.super_rabbit.wheel_picker.OnValueChangeListener
import com.super_rabbit.wheel_picker.WheelPicker

class SettingsPageFragment : Fragment(R.layout.settings_page_fragment) {
    private lateinit var viewModel: SettingsPageViewModel
    private lateinit var binding: SettingsPageFragmentBinding
    lateinit var navController: NavController

    var initialCycleCount = 0


    companion object {
        fun newInstance() = SettingsPageFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.settings_page_fragment)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsPageViewModel::class.java)
        setStatusBar()
        binding.dropdownEndBreakSoundTxt.setOnClickListener {
            navController.navigate(R.id.action_settingsPageFragment_to_endSoundPageFragment)
        }
        binding.dropdownEndFocusSoundTxt.setOnClickListener {
            navController.navigate(R.id.action_settingsPageFragment_to_focusSoundPageFragment)
        }
        setNumberPickerProperties()
        onFocusClick()
        onShortBreakClick()
        onLongBreakClick()
        onCycleCountClick()
        onFocusTimeSelected()
        onShortBreakTimeSelected()
        onLongBreakTimeSelected()
        cycleCountSelection()

    }

    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            requireActivity().window.statusBarColor =
                ContextCompat.getColor(requireActivity(), R.color.white)
        }
    }

    private fun onToolBarBackBtnClick(){
        binding.toolBarBackBtn.setOnClickListener {
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
        binding.focusPickerHours.setOnValueChangeListener(object : OnValueChangeListener {
            override fun onValueChange(picker: WheelPicker, oldVal: String, newVal: String) {
                if (newVal.toInt() == 0) {
                    binding.dropdownFocusHoursTxt.visibility = View.GONE
                } else {
                    binding.dropdownFocusHoursTxt.visibility = View.VISIBLE
                    binding.dropdownFocusHoursTxt.text = newVal + " hours"
                }
            }
        })
        binding.focusPickerMinutes.setOnValueChangeListener(object : OnValueChangeListener {
            override fun onValueChange(picker: WheelPicker, oldVal: String, newVal: String) {
                if (newVal.toInt() == 0) {
                    binding.dropdownFocusMinutesTxt.visibility = View.GONE
                } else {
                    binding.dropdownFocusMinutesTxt.visibility = View.VISIBLE
                    binding.dropdownFocusMinutesTxt.text = newVal + " min"
                }
            }
        })
    }

    private fun onShortBreakTimeSelected() {
        binding.shortBreakPickerHours.setOnValueChangeListener(object : OnValueChangeListener {
            override fun onValueChange(picker: WheelPicker, oldVal: String, newVal: String) {
                if (newVal.toInt() == 0) {
                    binding.dropdownShortBreakHoursTxt.visibility = View.GONE
                } else {
                    binding.dropdownShortBreakHoursTxt.visibility = View.VISIBLE
                    binding.dropdownShortBreakHoursTxt.text = newVal + " hours"
                }
            }
        })
        binding.shortBreakPickerMinutes.setOnValueChangeListener(object : OnValueChangeListener {
            override fun onValueChange(picker: WheelPicker, oldVal: String, newVal: String) {
                if (newVal.toInt() == 0) {
                    binding.dropdownShortBreakMinutesTxt.visibility = View.GONE
                } else {
                    binding.dropdownShortBreakMinutesTxt.visibility = View.VISIBLE
                    binding.dropdownShortBreakMinutesTxt.text = newVal + " min"
                }
            }
        })
    }

    private fun onLongBreakTimeSelected() {
        binding.longBreakPickerHours.setOnValueChangeListener(object : OnValueChangeListener {
            override fun onValueChange(picker: WheelPicker, oldVal: String, newVal: String) {
                if (newVal.toInt() == 0) {
                    binding.dropdownLongBreakHoursTxt.visibility = View.GONE
                } else {
                    binding.dropdownLongBreakHoursTxt.visibility = View.VISIBLE
                    binding.dropdownLongBreakHoursTxt.text = newVal + " hours"
                }
            }
        })
        binding.longBreakPickerMinutes.setOnValueChangeListener(object : OnValueChangeListener {
            override fun onValueChange(picker: WheelPicker, oldVal: String, newVal: String) {
                if (newVal.toInt() == 0) {
                    binding.dropdownLongBreakMinutesTxt.visibility = View.GONE
                } else {
                    binding.dropdownLongBreakMinutesTxt.visibility = View.VISIBLE
                    binding.dropdownLongBreakMinutesTxt.text = newVal + " min"
                }
            }
        })
    }

    private fun cycleCountSelection() {
        binding.subtractCycleCountBtn.setOnClickListener {
            if (initialCycleCount >0) {
                initialCycleCount -= 1
                binding.cycleCountInsideTxt.text = initialCycleCount.toString()
                if (initialCycleCount == 0) {
                    binding.dropdownCycleCountTxt.visibility = View.GONE
                } else {
                    binding.dropdownCycleCountTxt.visibility = View.VISIBLE
                    binding.dropdownCycleCountTxt.text = initialCycleCount.toString()
                }
            }
        }
        binding.addCycleCountBtn.setOnClickListener {
            if (initialCycleCount >= 0) {
                initialCycleCount += 1
                binding.cycleCountInsideTxt.text = initialCycleCount.toString()
                if (initialCycleCount == 0) {
                    binding.dropdownCycleCountTxt.visibility = View.GONE
                } else {
                    binding.dropdownCycleCountTxt.visibility = View.VISIBLE
                    binding.dropdownCycleCountTxt.text = initialCycleCount.toString()

                }
            }

        }
    }



    private fun setNumberPickerProperties() {
        //focus time
        binding.focusPickerHours.setMin(0)
        binding.focusPickerMinutes.setMin(0)
        binding.focusPickerHours.setMax(24)
        binding.focusPickerMinutes.setMax(59)

        //short break
        binding.shortBreakPickerHours.setMin(0)
        binding.shortBreakPickerMinutes.setMin(0)
        binding.shortBreakPickerHours.setMax(24)
        binding.shortBreakPickerMinutes.setMax(59)
        //long break
        binding.longBreakPickerHours.setMin(0)
        binding.longBreakPickerMinutes.setMin(0)
        binding.longBreakPickerHours.setMax(24)
        binding.longBreakPickerMinutes.setMax(59)
        //cycleCount
        binding.cycleCountInsideTxt.text = initialCycleCount.toString()
    }

}
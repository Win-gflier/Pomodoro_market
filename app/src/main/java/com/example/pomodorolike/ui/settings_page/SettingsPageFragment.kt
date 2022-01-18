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
import com.example.pomodorolike.data.preferences.PrefRepository
import com.example.pomodorolike.databinding.SettingsPageFragmentBinding
import android.widget.Toast



class SettingsPageFragment : Fragment(R.layout.settings_page_fragment) {
    private lateinit var viewModel: SettingsPageViewModel
    private lateinit var binding: SettingsPageFragmentBinding
    lateinit var navController: NavController
    private val prefRepository by lazy { PrefRepository(requireContext()) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.settings_page_fragment)
        navController = Navigation.findNavController(view)
        setPageBackgroundColor()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsPageViewModel::class.java)
        setStatusBar()
        onEndBreakSoundChooseClick()
        onEndFocusSoundChooseClick()
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
                ContextCompat.getColor(requireActivity(), R.color.grey_very_light)
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
/*            var initialFocusTimeHours = prefRepository.getFocusTimerLengthHours()
            var initialFocusTimeMinutes = prefRepository.getFocusTimerLengthMinutes()
            if(binding.focusPickerHours.value == 0 && binding.focusPickerMinutes.value == 0 ){
                binding.focusPickerHours.value = initialFocusTimeHours.toInt()
                binding.focusPickerMinutes.value = initialFocusTimeMinutes.toInt()
            }*/
            if (binding.focusTimePicker.visibility == View.GONE) {
                binding.focusTimePicker.visibility = View.VISIBLE
                binding.focusPickerHours.value = prefRepository.getFocusTimerLengthHours().toInt()
                binding.focusPickerMinutes.value = prefRepository.getFocusTimerLengthMinutes().toInt()
                binding.dropdownFocusBtn.setBackgroundResource(R.drawable.ic_up_arrow)
            } else {
                binding.focusTimePicker.visibility = View.GONE
                binding.dropdownFocusBtn.setBackgroundResource(R.drawable.ic_down_arrow)

            }
        }
    }

    private fun onLongBreakClick() {
        binding.relativeLayoutDropdownLongBreak.setOnClickListener {
            if (binding.longBreakPicker.visibility == View.GONE) {
                binding.longBreakPicker.visibility = View.VISIBLE
                binding.longBreakPickerHours.value = prefRepository.getLongBreakTimerLengthHours().toInt()
                binding.longBreakPickerMinutes.value = prefRepository.getLongBreakTimerLengthMinutes().toInt()
                binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_up_arrow)
            } else {
                binding.longBreakPicker.visibility = View.GONE
                binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)

            }
        }
    }

    private fun onShortBreakClick() {
        binding.relativeLayoutDropdownShortBreak.setOnClickListener {
            if (binding.shortBreakPicker.visibility == View.GONE) {
                binding.shortBreakPicker.visibility = View.VISIBLE
                binding.shortBreakPickerHours.value = prefRepository.getShortBreakTimerLengthHours().toInt()
                binding.shortBreakPickerMinutes.value = prefRepository.getShortBreakTimerLengthMinutes().toInt()
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
            var initialFocusTimeHours = prefRepository.getFocusTimerLengthHours()
            var initialFocusTimeMinutes = prefRepository.getFocusTimerLengthMinutes()
            var newValue = newVal.toLong()
            if(newValue == 0L && initialFocusTimeMinutes == 0L){
                newValue = initialFocusTimeHours
                binding.focusPickerHours.value = newValue.toInt()
                Toast.makeText(requireContext(),"Focus Time Can't Be 0", Toast.LENGTH_SHORT).show()
            }
/*            hoursNewValue = newValue
            if(minutesNewValue == 0L && hoursNewValue == 0L){
                Log.e("TAG", initialFocusTimeMinutes.toString() + " minutes" + initialFocusTimeHours + " hours")
                newValue = initialFocusTimeHours
                prefRepository.setFocusTimerLengthHours(newValue)
                if(minutesNewValue == 0L){
                    prefRepository.setFocusTimerLengthMinutes(minutesNewValue)
                    viewModel.updateOrangeTextsLong(
                        minutesNewValue,
                        "$minutesNewValue min",
                        binding.dropdownFocusMinutesTxt
                    )
                }
            }*/
            prefRepository.setFocusTimerLengthHours(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getFocusTimerLengthHours().toString() + " hours",
                binding.dropdownFocusHoursTxt
            )

        }
        binding.focusPickerMinutes.setOnValueChangedListener { _, _, newVal ->
            var initialFocusTimeHours = prefRepository.getFocusTimerLengthHours()
            var initialFocusTimeMinutes = prefRepository.getFocusTimerLengthMinutes()
            var newValue = newVal.toLong()
            if(newValue == 0L && initialFocusTimeHours == 0L){
                newValue = initialFocusTimeMinutes
                binding.focusPickerMinutes.value = newValue.toInt()
                Toast.makeText(requireContext(),"Focus Time Can't Be 0", Toast.LENGTH_SHORT).show()
            }
/*            minutesNewValue = newValue
            if(minutesNewValue == 0L && hoursNewValue == 0L){
                Log.e("TAG", initialFocusTimeMinutes.toString() + " minutes" + initialFocusTimeHours + " hours")
                newValue = initialFocusTimeMinutes
                prefRepository.setFocusTimerLengthMinutes(newValue)
                if(hoursNewValue == 0L){
                    prefRepository.setFocusTimerLengthHours(hoursNewValue)
                    viewModel.updateOrangeTextsLong(
                        hoursNewValue,
                        "$hoursNewValue hours",
                        binding.dropdownFocusHoursTxt
                    )
                }
            }*/
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
            var initialShortBreakTimeHours = prefRepository.getShortBreakTimerLengthHours()
            var initialShortBreakTimeMinutes = prefRepository.getShortBreakTimerLengthMinutes()
            var newValue: Long = newVal.toLong()
            if(newValue == 0L && initialShortBreakTimeMinutes == 0L){
                newValue = initialShortBreakTimeHours
                binding.shortBreakPickerHours.value = newValue.toInt()
                Toast.makeText(requireContext(),"Short Break Time Can't Be 0", Toast.LENGTH_SHORT).show()
            }
            prefRepository.setShortBreakTimerLengthHours(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getShortBreakTimerLengthHours().toString() + " hours",
                binding.dropdownShortBreakHoursTxt
            )
        }
        binding.shortBreakPickerMinutes.setOnValueChangedListener { _, _, newVal ->
            var initialShortBreakTimeHours = prefRepository.getShortBreakTimerLengthHours()
            var initialShortBreakTimerMinutes = prefRepository.getShortBreakTimerLengthMinutes()
            var newValue = newVal.toLong()
            if(newValue == 0L && initialShortBreakTimeHours ==0L){
                newValue = initialShortBreakTimerMinutes
                binding.shortBreakPickerMinutes.value = newValue.toInt()
                Toast.makeText(requireContext(),"Short Break Time Can't Be 0", Toast.LENGTH_SHORT).show()
            }
            prefRepository.setShortBreakTimerLengthMinutes(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getShortBreakTimerLengthMinutes().toString() + " min",
                binding.dropdownShortBreakMinutesTxt
            )
        }
    }

    private fun onLongBreakTimeSelected() {
        binding.longBreakPickerHours.setOnValueChangedListener { _, _, newVal ->
            var initialLongBreakTimeHours = prefRepository.getLongBreakTimerLengthHours()
            var initialLongBreakTimeMinutes = prefRepository.getLongBreakTimerLengthMinutes()
            var newValue = newVal.toLong()
            if(newValue == 0L && initialLongBreakTimeMinutes == 0L){
                newValue = initialLongBreakTimeHours
                binding.longBreakPickerHours.value = newValue.toInt()
                Toast.makeText(requireContext(),"Long Break Time Can't Be 0", Toast.LENGTH_SHORT).show()
            }
            prefRepository.setLongBreakTimerLengthHours(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getLongBreakTimerLengthHours().toString() + " hours",
                binding.dropdownLongBreakHoursTxt
            )
        }
        binding.longBreakPickerMinutes.setOnValueChangedListener { _, _, newVal ->
            var initialLongBreakTimeHours = prefRepository.getLongBreakTimerLengthHours()
            var initialLongBreakTimeMinutes = prefRepository.getLongBreakTimerLengthMinutes()
            var newValue = newVal.toLong()
            if(newValue == 0L && initialLongBreakTimeHours == 0L){
                newValue = initialLongBreakTimeMinutes
                binding.longBreakPickerMinutes.value = newValue.toInt()
                Toast.makeText(requireContext(),"Long Break Time Can't Be 0", Toast.LENGTH_SHORT).show()
            }
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
            if(numberOfCycles == 1){
                Toast.makeText(requireContext(),"Minimum Cycle Count Is 1", Toast.LENGTH_SHORT).show()
            }
            if (numberOfCycles > 1) {
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
            if (numberOfCycles == 8){
                Toast.makeText(requireContext(),"Maximum Cycle Count Is 8", Toast.LENGTH_SHORT).show()
            }
            if (numberOfCycles in 1..7) {
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
            "$focusHours hours",
            binding.dropdownFocusHoursTxt
        )

        viewModel.updateOrangeTextsLong(
            focusMinutes,
            "$focusMinutes min",
            binding.dropdownFocusMinutesTxt
        )

        viewModel.updateOrangeTextsLong(
            shortBreakHours,
            "$shortBreakHours hours",
            binding.dropdownShortBreakHoursTxt
        )

        viewModel.updateOrangeTextsLong(
            shortBreakMinutes,
            "$shortBreakMinutes min",
            binding.dropdownShortBreakMinutesTxt
        )

        viewModel.updateOrangeTextsLong(
            longBreakHours,
            "$longBreakHours hours",
            binding.dropdownLongBreakHoursTxt
        )

        viewModel.updateOrangeTextsLong(
            longBreakMinutes,
            "$longBreakMinutes min",
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

    private fun setPageBackgroundColor() {
        binding.nestedScrollView.setBackgroundColor(resources.getColor(R.color.grey_very_light))
        binding.toolBarSettingsPage.setBackgroundColor(resources.getColor(R.color.grey_very_light))
        binding.relativeLayoutDropdownAutoBreak.setBackgroundColor(resources.getColor(R.color.grey_very_light))
        binding.relativeLayoutDropdownAutoWork.setBackgroundColor(resources.getColor(R.color.grey_very_light))
        binding.relativeLayoutDropdownEndBreakSound.setBackgroundColor(resources.getColor(R.color.grey_very_light))
        binding.relativeLayoutDropdownEndFocusSound.setBackgroundColor(resources.getColor(R.color.grey_very_light))
        binding.relativeLayoutDropdownNotifications.setBackgroundColor(resources.getColor(R.color.grey_very_light))
    }

}
package com.encro.pomodoro.ui.settings_page

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.pomodorolike.R
import com.encro.pomodoro.data.preferences.PrefRepository
import com.example.pomodorolike.databinding.SettingsPageFragmentBinding
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible


class SettingsPageFragment : Fragment(R.layout.settings_page_fragment) {
    private lateinit var viewModel: SettingsPageViewModel
    private lateinit var binding: SettingsPageFragmentBinding
    lateinit var navController: NavController
    private val prefRepository by lazy { PrefRepository(requireContext()) }
    var initialFocusTimeMinutes = 0L
    var initialFocusTimeHours = 0L
    var initialShortBreakTimeMinutes = 0L
    var initialShortBreakTimeHours = 0L
    var initialLongBreakTimeMinutes = 0L
    var initialLongBreakTimeHours = 0L
    var numberofCompleteCyclesFromRestOrMain = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.settings_page_fragment)
        navController = Navigation.findNavController(view)
        setPageBackgroundColor()
        initialFocusTimeMinutes = prefRepository.getFocusTimerLengthMinutes()
        initialFocusTimeHours = prefRepository.getFocusTimerLengthHours()
        initialShortBreakTimeMinutes = prefRepository.getShortBreakTimerLengthMinutes()
        initialShortBreakTimeHours = prefRepository.getShortBreakTimerLengthHours()
        initialLongBreakTimeMinutes = prefRepository.getLongBreakTimerLengthMinutes()
        initialLongBreakTimeHours = prefRepository.getLongBreakTimerLengthHours()
        numberofCompleteCyclesFromRestOrMain = arguments?.getInt("cycle_count")!!

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsPageViewModel::class.java)
        setStatusBar()
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
        onBackButtonClick()
        handleDropdownsOpenCloseCase()
    }

    private fun onBackButtonClick() {
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (prefRepository.getFocusTimerLengthHours() == 0L && prefRepository.getFocusTimerLengthMinutes() == 0L) {
                when {
                    initialFocusTimeHours == 0L -> {
                        prefRepository.setFocusTimerLengthMinutes(initialFocusTimeMinutes)

                    }
                    initialFocusTimeMinutes == 0L -> {
                        prefRepository.setFocusTimerLengthHours(initialFocusTimeHours)
                    }
                    else -> {
                        prefRepository.setFocusTimerLengthHours(initialFocusTimeHours)
                        prefRepository.setFocusTimerLengthMinutes(initialFocusTimeMinutes)
                    }
                }
                Toast.makeText(requireContext(), "Focus Time Can't Be 0", Toast.LENGTH_SHORT).show()
            }
            if (prefRepository.getLongBreakTimerLengthHours() == 0L && prefRepository.getLongBreakTimerLengthMinutes() == 0L) {
                when {
                    initialLongBreakTimeHours == 0L -> {
                        prefRepository.setLongBreakTimerLengthMinutes(initialLongBreakTimeMinutes)

                    }
                    initialLongBreakTimeMinutes == 0L -> {
                        prefRepository.setLongBreakTimerLengthHours(initialLongBreakTimeHours)
                    }
                    else -> {
                        prefRepository.setLongBreakTimerLengthHours(initialLongBreakTimeHours)
                        prefRepository.setLongBreakTimerLengthMinutes(initialLongBreakTimeMinutes)

                    }
                }
                Toast.makeText(requireContext(), "Long Break Time Can't Be 0", Toast.LENGTH_SHORT)
                    .show()
            }
            if (prefRepository.getShortBreakTimerLengthHours() == 0L && prefRepository.getShortBreakTimerLengthMinutes() == 0L) {
                when {
                    initialShortBreakTimeHours == 0L -> {
                        prefRepository.setShortBreakTimerLengthMinutes(initialShortBreakTimeMinutes)

                    }
                    initialShortBreakTimeMinutes == 0L -> {
                        prefRepository.setShortBreakTimerLengthHours(initialShortBreakTimeHours)
                    }
                    else -> {
                        prefRepository.setShortBreakTimerLengthHours(initialShortBreakTimeHours)
                        prefRepository.setShortBreakTimerLengthMinutes(initialShortBreakTimeMinutes)

                    }
                }
                Toast.makeText(requireContext(), "Short Break Time Can't Be 0", Toast.LENGTH_SHORT)
                    .show()

            }
            if(prefRepository.getPreviousPageIsRest()){
                navController.navigate(R.id.action_settingsPageFragment_to_restPageFragment,
                    bundleOf("cycle_count" to numberofCompleteCyclesFromRestOrMain)
                )
            }else{
                prefRepository.setIsComingFromRest(false)
                navController.navigate(R.id.action_settingsPageFragment_to_mainPageFragment,
                    bundleOf("cycle_count" to numberofCompleteCyclesFromRestOrMain))
            }
        }
    }

    private fun onToolbarBackBtnClick() {
        binding.toolBarBackBtn.setOnClickListener {
            if (prefRepository.getFocusTimerLengthHours() == 0L && prefRepository.getFocusTimerLengthMinutes() == 0L) {
                when {
                    initialFocusTimeHours == 0L -> {
                        prefRepository.setFocusTimerLengthMinutes(initialFocusTimeMinutes)

                    }
                    initialFocusTimeMinutes == 0L -> {
                        prefRepository.setFocusTimerLengthHours(initialFocusTimeHours)
                    }
                    else -> {
                        prefRepository.setFocusTimerLengthHours(initialFocusTimeHours)
                        prefRepository.setFocusTimerLengthMinutes(initialFocusTimeMinutes)
                    }
                }
                Toast.makeText(requireContext(), "Focus Time Can't Be 0", Toast.LENGTH_SHORT).show()
            }
            if (prefRepository.getLongBreakTimerLengthHours() == 0L && prefRepository.getLongBreakTimerLengthMinutes() == 0L) {
                when {
                    initialLongBreakTimeHours == 0L -> {
                        prefRepository.setLongBreakTimerLengthMinutes(initialLongBreakTimeMinutes)

                    }
                    initialLongBreakTimeMinutes == 0L -> {
                        prefRepository.setLongBreakTimerLengthHours(initialLongBreakTimeHours)
                    }
                    else -> {
                        prefRepository.setLongBreakTimerLengthHours(initialLongBreakTimeHours)
                        prefRepository.setLongBreakTimerLengthMinutes(initialLongBreakTimeMinutes)

                    }
                }
                Toast.makeText(requireContext(), "Long Break Time Can't Be 0", Toast.LENGTH_SHORT)
                    .show()
            }
            if (prefRepository.getShortBreakTimerLengthHours() == 0L && prefRepository.getShortBreakTimerLengthMinutes() == 0L) {
                when {
                    initialShortBreakTimeHours == 0L -> {
                        prefRepository.setShortBreakTimerLengthMinutes(initialShortBreakTimeMinutes)

                    }
                    initialShortBreakTimeMinutes == 0L -> {
                        prefRepository.setShortBreakTimerLengthHours(initialShortBreakTimeHours)
                    }
                    else -> {
                        prefRepository.setShortBreakTimerLengthHours(initialShortBreakTimeHours)
                        prefRepository.setShortBreakTimerLengthMinutes(initialShortBreakTimeMinutes)

                    }
                }


                Toast.makeText(requireContext(), "Short Break Time Can't Be 0", Toast.LENGTH_SHORT)
                    .show()

            }
            if(prefRepository.getPreviousPageIsRest()){
                navController.navigate(R.id.action_settingsPageFragment_to_restPageFragment,
                    bundleOf("cycle_count" to numberofCompleteCyclesFromRestOrMain))
            }else{
                prefRepository.setIsComingFromRest(false)
                navController.navigate(R.id.action_settingsPageFragment_to_mainPageFragment,
                    bundleOf("cycle_count" to numberofCompleteCyclesFromRestOrMain))
            }        }
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

    private fun setPageBackgroundColor() {
        binding.nestedScrollView.setBackgroundColor(resources.getColor(R.color.grey_very_light))
        binding.toolBarSettingsPage.setBackgroundColor(resources.getColor(R.color.grey_very_light))
        binding.relativeLayoutDropdownAutoBreak.setBackgroundColor(resources.getColor(R.color.grey_very_light))
        binding.relativeLayoutDropdownAutoWork.setBackgroundColor(resources.getColor(R.color.grey_very_light))

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



    private fun onFocusClick() {
        binding.relativeLayoutDropdownFocus.setOnClickListener {
            if (!prefRepository.getShortBreakDropdownIsOpen()
                && !prefRepository.getLongBreakDropdownIsOpen()
                && !prefRepository.getCycleCountDropdownIsOpen()
            ) {
                if (binding.focusTimePicker.visibility == View.GONE) {
                    binding.focusTimePicker.visibility = View.VISIBLE
                    binding.focusPickerHours.value =
                        prefRepository.getFocusTimerLengthHours().toInt()
                    binding.focusPickerMinutes.value =
                        prefRepository.getFocusTimerLengthMinutes().toInt()
                    binding.dropdownFocusBtn.setBackgroundResource(R.drawable.ic_up_arrow)
                    prefRepository.setFocusDropdownIsOpen(true)

                } else {
                    onFocusValueSetToZero()
                    binding.focusTimePicker.visibility = View.GONE
                    binding.dropdownFocusBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setFocusDropdownIsOpen(false)

                }
            } else {
                viewModel.focusWantsToOpen.value = true
            }

        }
    }

    private fun onFocusTimeSelected() {
        binding.focusPickerHours.setOnValueChangedListener { _, _, newVal ->
            var newValue = newVal.toLong()
            prefRepository.setFocusTimerLengthHours(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getFocusTimerLengthHours().toString() + " hours",
                binding.dropdownFocusHoursTxt
            )

        }
        binding.focusPickerMinutes.setOnValueChangedListener { _, _, newVal ->
            var newValue = newVal.toLong()
            prefRepository.setFocusTimerLengthMinutes(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getFocusTimerLengthMinutes().toString() + " min",
                binding.dropdownFocusMinutesTxt
            )
        }
    }

    private fun onFocusValueSetToZero(){
        if (prefRepository.getFocusTimerLengthHours() == 0L && prefRepository.getFocusTimerLengthMinutes() == 0L) {
            when {
                initialFocusTimeHours == 0L -> {
                    binding.dropdownFocusMinutesTxt.isVisible = true
                    binding.dropdownFocusMinutesTxt.text =
                        "$initialFocusTimeMinutes min"
                    prefRepository.setFocusTimerLengthMinutes(initialFocusTimeMinutes)

                }
                initialFocusTimeMinutes == 0L -> {
                    binding.dropdownFocusHoursTxt.isVisible = true
                    binding.dropdownFocusHoursTxt.text = "$initialFocusTimeHours hours"
                    prefRepository.setFocusTimerLengthHours(initialFocusTimeHours)
                }
                else -> {
                    binding.dropdownFocusHoursTxt.isVisible = true
                    binding.dropdownFocusHoursTxt.text = "$initialFocusTimeHours hours"
                    binding.dropdownFocusMinutesTxt.isVisible = true
                    binding.dropdownFocusMinutesTxt.text =
                        "$initialFocusTimeMinutes min"
                    prefRepository.setFocusTimerLengthHours(initialFocusTimeHours)
                    prefRepository.setFocusTimerLengthMinutes(initialFocusTimeMinutes)

                }
            }


            Toast.makeText(
                requireContext(),
                "Focus Time Can't Be 0",
                Toast.LENGTH_SHORT
            )
                .show()

        }
    }


    private fun onShortBreakClick() {
        binding.relativeLayoutDropdownShortBreak.setOnClickListener {
            if (!prefRepository.getLongBreakDropdownIsOpen()
                && !prefRepository.getFocusDropdownIsOpen()
                && !prefRepository.getCycleCountDropdownIsOpen()
            ) {
                if (binding.shortBreakPicker.visibility == View.GONE) {
                    binding.shortBreakPicker.visibility = View.VISIBLE
                    binding.shortBreakPickerHours.value =
                        prefRepository.getShortBreakTimerLengthHours().toInt()
                    binding.shortBreakPickerMinutes.value =
                        prefRepository.getShortBreakTimerLengthMinutes().toInt()
                    binding.dropdownShortBreakBtn.setBackgroundResource(R.drawable.ic_up_arrow)
                    prefRepository.setShortBreakDropdownIsOpen(true)
                } else {
                    onShortBreakValueSetToZero()
                    binding.shortBreakPicker.visibility = View.GONE
                    binding.dropdownShortBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setShortBreakDropdownIsOpen(false)
                }
            } else {
                viewModel.shortBreakWantsToOpen.value = true
            }

        }
    }

    private fun onShortBreakTimeSelected() {
        binding.shortBreakPickerHours.setOnValueChangedListener { _, _, newVal ->
            var newValue: Long = newVal.toLong()
            prefRepository.setShortBreakTimerLengthHours(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getShortBreakTimerLengthHours().toString() + " hours",
                binding.dropdownShortBreakHoursTxt
            )
        }
        binding.shortBreakPickerMinutes.setOnValueChangedListener { _, _, newVal ->
            var newValue = newVal.toLong()
            prefRepository.setShortBreakTimerLengthMinutes(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getShortBreakTimerLengthMinutes().toString() + " min",
                binding.dropdownShortBreakMinutesTxt
            )
        }
    }

    private fun onShortBreakValueSetToZero(){
        if (prefRepository.getShortBreakTimerLengthHours() == 0L && prefRepository.getShortBreakTimerLengthMinutes() == 0L) {
            when {
                initialShortBreakTimeHours == 0L -> {
                    binding.dropdownShortBreakMinutesTxt.isVisible = true
                    binding.dropdownShortBreakMinutesTxt.text =
                        "$initialShortBreakTimeMinutes min"
                    prefRepository.setShortBreakTimerLengthMinutes(
                        initialShortBreakTimeMinutes
                    )

                }
                initialShortBreakTimeMinutes == 0L -> {
                    binding.dropdownShortBreakHoursTxt.isVisible = true
                    binding.dropdownShortBreakHoursTxt.text =
                        "$initialShortBreakTimeHours hours"
                    prefRepository.setShortBreakTimerLengthHours(
                        initialShortBreakTimeHours
                    )
                }
                else -> {
                    binding.dropdownShortBreakHoursTxt.isVisible = true
                    binding.dropdownShortBreakHoursTxt.text =
                        "$initialShortBreakTimeHours hours"
                    binding.dropdownShortBreakMinutesTxt.isVisible = true
                    binding.dropdownShortBreakMinutesTxt.text =
                        "$initialShortBreakTimeMinutes min"
                    prefRepository.setShortBreakTimerLengthHours(
                        initialShortBreakTimeHours
                    )
                    prefRepository.setShortBreakTimerLengthMinutes(
                        initialShortBreakTimeMinutes
                    )

                }
            }

            Toast.makeText(
                requireContext(),
                "Short Break Time Can't Be 0",
                Toast.LENGTH_SHORT
            ).show()

        }
    }


    private fun onLongBreakClick() {
        binding.relativeLayoutDropdownLongBreak.setOnClickListener {
            if (!prefRepository.getShortBreakDropdownIsOpen()
                && !prefRepository.getFocusDropdownIsOpen()
                && !prefRepository.getCycleCountDropdownIsOpen()
            ) {
                if (binding.longBreakPicker.visibility == View.GONE) {
                    binding.longBreakPicker.visibility = View.VISIBLE
                    binding.longBreakPickerHours.value =
                        prefRepository.getLongBreakTimerLengthHours().toInt()
                    binding.longBreakPickerMinutes.value =
                        prefRepository.getLongBreakTimerLengthMinutes().toInt()
                    binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_up_arrow)
                    prefRepository.setLongBreakDropdownIsOpen(true)
                } else {
                    onLongBreakValueSetToZero()
                    binding.longBreakPicker.visibility = View.GONE
                    binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setLongBreakDropdownIsOpen(false)
                }
            } else {
                viewModel.longBreakWantsToOpen.value = true
            }

        }
    }

    private fun onLongBreakTimeSelected() {
        binding.longBreakPickerHours.setOnValueChangedListener { _, _, newVal ->
            var newValue = newVal.toLong()
            prefRepository.setLongBreakTimerLengthHours(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getLongBreakTimerLengthHours().toString() + " hours",
                binding.dropdownLongBreakHoursTxt
            )
        }
        binding.longBreakPickerMinutes.setOnValueChangedListener { _, _, newVal ->
            var newValue = newVal.toLong()
            prefRepository.setLongBreakTimerLengthMinutes(newValue)
            viewModel.updateOrangeTextsLong(
                newValue,
                prefRepository.getLongBreakTimerLengthMinutes().toString() + " min",
                binding.dropdownLongBreakMinutesTxt
            )
        }
    }

    private fun onLongBreakValueSetToZero(){
        if (prefRepository.getLongBreakTimerLengthHours() == 0L && prefRepository.getLongBreakTimerLengthMinutes() == 0L) {
            when {
                initialLongBreakTimeHours == 0L -> {
                    binding.dropdownLongBreakMinutesTxt.isVisible = true
                    binding.dropdownLongBreakMinutesTxt.text =
                        "$initialLongBreakTimeMinutes min"
                    prefRepository.setLongBreakTimerLengthMinutes(
                        initialLongBreakTimeMinutes
                    )

                }
                initialLongBreakTimeMinutes == 0L -> {
                    binding.dropdownLongBreakHoursTxt.isVisible = true
                    binding.dropdownLongBreakHoursTxt.text =
                        "$initialLongBreakTimeHours hours"
                    prefRepository.setLongBreakTimerLengthHours(
                        initialLongBreakTimeHours
                    )
                }
                else -> {
                    binding.dropdownLongBreakHoursTxt.isVisible = true
                    binding.dropdownLongBreakHoursTxt.text =
                        "$initialLongBreakTimeHours hours"
                    binding.dropdownLongBreakMinutesTxt.isVisible = true
                    binding.dropdownLongBreakMinutesTxt.text =
                        "$initialLongBreakTimeMinutes min"
                    prefRepository.setLongBreakTimerLengthHours(
                        initialLongBreakTimeHours
                    )
                    prefRepository.setLongBreakTimerLengthMinutes(
                        initialLongBreakTimeMinutes
                    )

                }
            }


            Toast.makeText(
                requireContext(),
                "Long Break Time Can't Be 0",
                Toast.LENGTH_SHORT
            ).show()

        }
    }


    private fun onCycleCountClick() {
        binding.relativeLayoutDropdownNumberOfCycles.setOnClickListener {
            if (!prefRepository.getShortBreakDropdownIsOpen()
                && !prefRepository.getFocusDropdownIsOpen()
                && !prefRepository.getLongBreakDropdownIsOpen()
            ) {
                if (binding.cycleCountPicker.visibility == View.GONE) {
                    binding.cycleCountPicker.visibility = View.VISIBLE
                    binding.dropdownCycleCountBtn.setBackgroundResource(R.drawable.ic_up_arrow)
                    prefRepository.setCycleCountDropdownIsOpen(true)
                } else {
                    binding.cycleCountPicker.visibility = View.GONE
                    binding.dropdownCycleCountBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setCycleCountDropdownIsOpen(false)
                }
            }else{
                viewModel.cyclePickerWantsToOpen.value = true
            }

        }
    }

    private fun cycleCountSelection() {
        var numberOfCycles = prefRepository.getNumberOfCycles()
        binding.subtractCycleCountBtn.setOnClickListener {
            if (numberOfCycles == 1) {
                Toast.makeText(requireContext(), "Minimum Cycle Count Is 1", Toast.LENGTH_SHORT)
                    .show()
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
            if (numberOfCycles == 8) {
                Toast.makeText(requireContext(), "Maximum Cycle Count Is 8", Toast.LENGTH_SHORT)
                    .show()
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


    private fun handleDropdownsOpenCloseCase() {
        viewModel.focusWantsToOpen.observe(viewLifecycleOwner) {
            if (it) {
                if (binding.focusTimePicker.visibility == View.GONE) {
                    when {
                        prefRepository.getShortBreakDropdownIsOpen() -> {
                            onShortBreakValueSetToZero()
                            binding.shortBreakPicker.visibility = View.GONE
                            binding.dropdownShortBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setShortBreakDropdownIsOpen(false)
                            viewModel.focusWantsToOpen.value = false
                        }
                        prefRepository.getLongBreakDropdownIsOpen() -> {
                            onLongBreakValueSetToZero()
                            binding.longBreakPicker.visibility = View.GONE
                            binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setLongBreakDropdownIsOpen(false)
                            viewModel.focusWantsToOpen.value = false
                        }
                        prefRepository.getCycleCountDropdownIsOpen() -> {
                            binding.cycleCountPicker.visibility = View.GONE
                            binding.dropdownCycleCountBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setCycleCountDropdownIsOpen(false)
                            viewModel.focusWantsToOpen.value = false
                        }
                    }
                    binding.focusTimePicker.visibility = View.VISIBLE
                    binding.focusPickerHours.value =
                        prefRepository.getFocusTimerLengthHours().toInt()
                    binding.focusPickerMinutes.value =
                        prefRepository.getFocusTimerLengthMinutes().toInt()
                    binding.dropdownFocusBtn.setBackgroundResource(R.drawable.ic_up_arrow)
                    prefRepository.setFocusDropdownIsOpen(true)
                } else {
                    onFocusValueSetToZero()
                    binding.focusTimePicker.visibility = View.GONE
                    binding.dropdownFocusBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setFocusDropdownIsOpen(false)
                }
            }
        }
        viewModel.cyclePickerWantsToOpen.observe(viewLifecycleOwner) {
            if (it) {
                if (binding.cycleCountPicker.visibility == View.GONE) {
                    when {
                        prefRepository.getShortBreakDropdownIsOpen() -> {
                            onShortBreakValueSetToZero()
                            binding.shortBreakPicker.visibility = View.GONE
                            binding.dropdownShortBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setShortBreakDropdownIsOpen(false)
                            viewModel.cyclePickerWantsToOpen.value = false
                        }
                        prefRepository.getLongBreakDropdownIsOpen() -> {
                            onLongBreakValueSetToZero()
                            binding.longBreakPicker.visibility = View.GONE
                            binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setLongBreakDropdownIsOpen(false)
                            viewModel.cyclePickerWantsToOpen.value = false
                        }
                        prefRepository.getFocusDropdownIsOpen() -> {
                            onFocusValueSetToZero()
                            binding.focusTimePicker.visibility = View.GONE
                            binding.dropdownFocusBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setFocusDropdownIsOpen(false)
                            viewModel.cyclePickerWantsToOpen.value = false
                        }
                    }
                    binding.cycleCountPicker.visibility = View.VISIBLE
                    binding.dropdownCycleCountBtn.setBackgroundResource(R.drawable.ic_up_arrow)
                    prefRepository.setCycleCountDropdownIsOpen(true)
                } else {
                    binding.cycleCountPicker.visibility = View.GONE
                    binding.dropdownCycleCountBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setCycleCountDropdownIsOpen(false)
                }
            }

        }
        viewModel.longBreakWantsToOpen.observe(viewLifecycleOwner) {
            if (it) {
                if (binding.longBreakPicker.visibility == View.GONE) {
                    when {
                        prefRepository.getShortBreakDropdownIsOpen() -> {
                            onShortBreakValueSetToZero()
                            binding.shortBreakPicker.visibility = View.GONE
                            binding.dropdownShortBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setShortBreakDropdownIsOpen(false)
                            viewModel.longBreakWantsToOpen.value = false
                        }
                        prefRepository.getCycleCountDropdownIsOpen() -> {
                            binding.cycleCountPicker.visibility = View.GONE
                            binding.dropdownCycleCountBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setCycleCountDropdownIsOpen(false)
                            viewModel.longBreakWantsToOpen.value = false
                        }
                        prefRepository.getFocusDropdownIsOpen() -> {
                            onFocusValueSetToZero()
                            binding.focusTimePicker.visibility = View.GONE
                            binding.dropdownFocusBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setFocusDropdownIsOpen(false)
                            viewModel.longBreakWantsToOpen.value = false
                        }
                    }
                    binding.longBreakPicker.visibility = View.VISIBLE
                    binding.longBreakPickerHours.value =
                        prefRepository.getLongBreakTimerLengthHours().toInt()
                    binding.longBreakPickerMinutes.value =
                        prefRepository.getLongBreakTimerLengthMinutes().toInt()
                    binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_up_arrow)
                    prefRepository.setLongBreakDropdownIsOpen(true)
                } else {
                    onLongBreakValueSetToZero()
                    binding.longBreakPicker.visibility = View.GONE
                    binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setLongBreakDropdownIsOpen(false)
                }
            }
        }
        viewModel.shortBreakWantsToOpen.observe(viewLifecycleOwner) {
            if (it) {
                if (binding.shortBreakPicker.visibility == View.GONE) {
                    when {
                        prefRepository.getCycleCountDropdownIsOpen() -> {
                            binding.cycleCountPicker.visibility = View.GONE
                            binding.dropdownCycleCountBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setCycleCountDropdownIsOpen(false)
                            viewModel.cyclePickerWantsToOpen.value = false
                        }
                        prefRepository.getLongBreakDropdownIsOpen() -> {
                            onLongBreakValueSetToZero()
                            binding.longBreakPicker.visibility = View.GONE
                            binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setLongBreakDropdownIsOpen(false)
                            viewModel.longBreakWantsToOpen.value = false
                        }
                        prefRepository.getFocusDropdownIsOpen() -> {
                            onFocusValueSetToZero()
                            binding.focusTimePicker.visibility = View.GONE
                            binding.dropdownFocusBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setFocusDropdownIsOpen(false)
                            viewModel.focusWantsToOpen.value = false
                        }
                    }
                    binding.shortBreakPicker.visibility = View.VISIBLE
                    binding.shortBreakPickerHours.value =
                        prefRepository.getShortBreakTimerLengthHours().toInt()
                    binding.shortBreakPickerMinutes.value =
                        prefRepository.getShortBreakTimerLengthMinutes().toInt()
                    binding.dropdownShortBreakBtn.setBackgroundResource(R.drawable.ic_up_arrow)
                    prefRepository.setShortBreakDropdownIsOpen(true)

                } else {
                    onShortBreakValueSetToZero()
                    binding.shortBreakPicker.visibility = View.GONE
                    binding.dropdownShortBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setShortBreakDropdownIsOpen(false)
                }
            }
        }
    }


    private fun onAutoStartBreakTimeSwitch() {
        binding.autoBreakSwitch.setOnClickListener {
            prefRepository.setAutoStartBreaks(binding.autoBreakSwitch.isChecked)
        }
    }

    private fun updateBreakSwitchState() {
        binding.autoBreakSwitch.isChecked = prefRepository.getAutoStartBreaks()
    }


    private fun onAutoStartWorkTimeSwitch() {
        binding.autoWorkSwitch.setOnClickListener {
            prefRepository.setAutoStartWorkTime(binding.autoWorkSwitch.isChecked)
        }
    }

    private fun updateWorkSwitchState() {
        binding.autoWorkSwitch.isChecked = prefRepository.getAutoStartWorkTime()
    }


}
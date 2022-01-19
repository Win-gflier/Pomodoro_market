package com.example.pomodorolike.ui.start_page

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.pomodorolike.R
import com.example.pomodorolike.data.preferences.PrefRepository
import com.example.pomodorolike.databinding.StartPageFragmentBinding

class StartPageFragment : Fragment(R.layout.start_page_fragment) {
    private lateinit var binding: StartPageFragmentBinding
    private lateinit var viewModel: StartPageViewModel
    private val prefRepository by lazy { PrefRepository(requireContext()) }
    lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.start_page_fragment)
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this).get(StartPageViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setStatusBar()
        setPageBackgroundColor()
        setOrangeText()
        onFocusClick()
        onCycleCountClick()
        onShortBreakClick()
        onLongBreakClick()
        onStartButtonClick()
        onFocusTimeSelected()
        onShortBreakTimeSelected()
        onLongBreakTimeSelected()
        cycleCountSelection()
        handleDropdownsOpenCloseCase()

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

    }

    private fun onStartButtonClick() {
        binding.startBtn.setOnClickListener {
            when {
                prefRepository.getFocusTimerLengthMinutes() == 0L
                        && prefRepository.getFocusTimerLengthHours() == 0L -> {
                    Toast.makeText(
                        requireContext(),
                        "You have not chosen Time for Focus",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                prefRepository.getShortBreakTimerLengthMinutes() == 0L
                        && prefRepository.getShortBreakTimerLengthHours() == 0L -> {
                    Toast.makeText(
                        requireContext(),
                        "You have not chosen Time for Short Break",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                prefRepository.getLongBreakTimerLengthMinutes() == 0L
                        && prefRepository.getLongBreakTimerLengthHours() == 0L -> {
                    Toast.makeText(
                        requireContext(),
                        "You have not chosen Time for Long Break",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                prefRepository.getNumberOfCycles() == 0 -> {
                    Toast.makeText(
                        requireContext(),
                        "You have not chosen number of Cycles",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                prefRepository.getNumberOfCycles() == 0
                        && prefRepository.getLongBreakTimerLengthMinutes() == 0L
                        && prefRepository.getShortBreakTimerLengthMinutes() == 0L
                        && prefRepository.getFocusTimerLengthMinutes() == 0L -> {
                    Toast.makeText(
                        requireContext(),
                        "Fill in all the required fields",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                else -> {
                    prefRepository.setOpenWithStartPage(true)
                    prefRepository.setFocusDropdownIsOpen(false)
                    prefRepository.setShortBreakDropdownIsOpen(false)
                    prefRepository.setLongBreakDropdownIsOpen(false)
                    prefRepository.setCycleCountDropdownIsOpen(false)
                    viewModel.focusWantsToOpen.value = false
                    viewModel.shortBreakWantsToOpen.value = false
                    viewModel.longBreakWantsToOpen.value = false
                    viewModel.cyclePickerWantsToOpen.value = false
                    navController.navigate(R.id.action_startPageFragment_to_mainPageFragment)
                }
            }
        }
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
                    binding.focusTimePicker.visibility = View.GONE
                    binding.dropdownFocusBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setFocusDropdownIsOpen(false)
                }
            } else {
                viewModel.focusWantsToOpen.value = true
            }
/*                Toast.makeText(
                    requireContext(),
                    "Close the current dropdown and then try again",
                    Toast.LENGTH_SHORT
                ).show()*/

        }
    }

    private fun handleDropdownsOpenCloseCase() {
        viewModel.focusWantsToOpen.observe(viewLifecycleOwner) {
            if (it) {
                if (binding.focusTimePicker.visibility == View.GONE) {
                    when {
                        prefRepository.getShortBreakDropdownIsOpen() -> {
                            binding.shortBreakPicker.visibility = View.GONE
                            binding.dropdownShortBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setShortBreakDropdownIsOpen(false)
                            viewModel.focusWantsToOpen.value = false
                        }
                        prefRepository.getLongBreakDropdownIsOpen() -> {
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
                    binding.focusTimePicker.visibility = View.GONE
                    binding.dropdownFocusBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setFocusDropdownIsOpen(false)
//                    viewModel.focusWantsToOpen.value = false
                }
            }
        }
        viewModel.cyclePickerWantsToOpen.observe(viewLifecycleOwner) {
            if (it) {
                if (binding.cycleCountPicker.visibility == View.GONE) {
                    when {
                        prefRepository.getShortBreakDropdownIsOpen() -> {
                            binding.shortBreakPicker.visibility = View.GONE
                            binding.dropdownShortBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setShortBreakDropdownIsOpen(false)
                            viewModel.cyclePickerWantsToOpen.value = false
                        }
                        prefRepository.getLongBreakDropdownIsOpen() -> {
                            binding.longBreakPicker.visibility = View.GONE
                            binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setLongBreakDropdownIsOpen(false)
                            viewModel.cyclePickerWantsToOpen.value = false
                        }
                        prefRepository.getFocusDropdownIsOpen() -> {
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
//                    viewModel.focusWantsToOpen.value = false
                }
            }

        }
        viewModel.longBreakWantsToOpen.observe(viewLifecycleOwner) {
            if (it) {
                if (binding.longBreakPicker.visibility == View.GONE) {
                    when {
                        prefRepository.getShortBreakDropdownIsOpen() -> {
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
                    binding.longBreakPicker.visibility = View.GONE
                    binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setLongBreakDropdownIsOpen(false)
//                    viewModel.longBreakWantsToOpen.value = false
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
                            binding.longBreakPicker.visibility = View.GONE
                            binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                            prefRepository.setLongBreakDropdownIsOpen(false)
                            viewModel.longBreakWantsToOpen.value = false
                        }
                        prefRepository.getFocusDropdownIsOpen() -> {
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
                    binding.shortBreakPicker.visibility = View.GONE
                    binding.dropdownShortBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setShortBreakDropdownIsOpen(false)
//                    viewModel.shortBreakWantsToOpen.value = false
                }
            }
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
                    binding.longBreakPicker.visibility = View.GONE
                    binding.dropdownLongBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setLongBreakDropdownIsOpen(false)

                }
            } else {
                viewModel.longBreakWantsToOpen.value = true
            }

//                Toast.makeText(
//                    requireContext(),
//                    "Close the current dropdown and then try again",
//                    Toast.LENGTH_SHORT
//                ).show()
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
                    binding.shortBreakPicker.visibility = View.GONE
                    binding.dropdownShortBreakBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                    prefRepository.setShortBreakDropdownIsOpen(false)
                }
            } else {
                viewModel.shortBreakWantsToOpen.value = true
            }

/*                Toast.makeText(
                    requireContext(),
                    "Close the current dropdown and then try again",
                    Toast.LENGTH_SHORT
                ).show()*/
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
            } else {
                viewModel.cyclePickerWantsToOpen.value = true
            }
/*                Toast.makeText(
                    requireContext(),
                    "Close the current dropdown and then try again",
                    Toast.LENGTH_SHORT
                ).show()*/
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
        binding.longBreakPickerHours.setOnValueChangedListener { picker, oldVal, newVal ->
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
        binding.longBreakPickerMinutes.setOnValueChangedListener { picker, oldVal, newVal ->
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


}
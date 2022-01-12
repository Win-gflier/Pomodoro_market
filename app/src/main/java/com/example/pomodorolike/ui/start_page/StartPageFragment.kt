package com.example.pomodorolike.ui.start_page

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
        onStartButtonClick()
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
        binding.relativeLayoutDropdownNumberOfCycles.setBackgroundColor(resources.getColor(R.color.white))
        binding.relativeLayoutDropdownLongBreak.setBackgroundColor(resources.getColor(R.color.white))
        binding.relativeLayoutDropdownFocus.setBackgroundColor(resources.getColor(R.color.white))
        binding.relativeLayoutDropdownShortBreak.setBackgroundColor(resources.getColor(R.color.white))
    }

    private fun onStartButtonClick(){
        binding.startBtn.setOnClickListener {
            prefRepository.setOpenWithStartPage(true)
            navController.navigate(R.id.action_startPageFragment_to_mainPageFragment)
        }
    }
}
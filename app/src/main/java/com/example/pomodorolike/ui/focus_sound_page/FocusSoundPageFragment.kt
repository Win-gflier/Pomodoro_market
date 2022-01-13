package com.example.pomodorolike.ui.break_sound_page

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
import com.example.pomodorolike.databinding.FocusSoundPageFragmentBinding
import com.example.pomodorolike.ui.focus_sound_page.FocusSoundPageViewModel

class FocusSoundPageFragment : Fragment(R.layout.focus_sound_page_fragment) {
    private lateinit var viewModel: FocusSoundPageViewModel
    private lateinit var binding: FocusSoundPageFragmentBinding
    lateinit var navController: NavController

    companion object {
        fun newInstance() = FocusSoundPageFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FocusSoundPageViewModel::class.java)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.focus_sound_page_fragment)
        navController = Navigation.findNavController(view)

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setStatusBar()
        onToolBarBackBtnClick()
        // TODO: Use the ViewModel
    }

    private fun onToolBarBackBtnClick(){
        binding.toolBarExitBtn.setOnClickListener {
            navController.navigate(R.id.action_focusSoundPageFragment_to_settingsPageFragment)
        }
    }
    private fun setStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            requireActivity().window.statusBarColor =
                ContextCompat.getColor(requireActivity(), R.color.white)
        }
    }

}
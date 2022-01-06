package com.example.pomodorolike.ui.break_sound_page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
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


        // TODO: Use the ViewModel
    }

}
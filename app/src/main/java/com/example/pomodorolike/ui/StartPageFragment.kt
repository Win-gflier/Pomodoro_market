package com.example.pomodorolike.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.pomodorolike.R
import com.example.pomodorolike.data.preferences.PrefRepository
import com.example.pomodorolike.databinding.EndSoundPageFragmentBinding
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
        // TODO: Use the ViewModel
    }

}
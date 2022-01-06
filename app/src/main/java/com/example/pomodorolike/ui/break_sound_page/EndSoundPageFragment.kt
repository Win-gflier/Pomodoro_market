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
import com.example.pomodorolike.databinding.EndSoundPageFragmentBinding

class EndSoundPageFragment : Fragment(R.layout.end_sound_page_fragment) {
    private lateinit var viewModel: EndSoundPageViewModel
    private lateinit var binding: EndSoundPageFragmentBinding
    lateinit var navController: NavController

    companion object {
        fun newInstance() = EndSoundPageFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EndSoundPageViewModel::class.java)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.end_sound_page_fragment)
        navController = Navigation.findNavController(view)

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setStatusBar()

        // TODO: Use the ViewModel
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
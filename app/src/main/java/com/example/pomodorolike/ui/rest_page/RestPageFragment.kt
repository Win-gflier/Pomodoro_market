package com.example.pomodorolike.ui.rest_page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.pomodorolike.R
import com.example.pomodorolike.databinding.MainPageFragmentBinding
import com.example.pomodorolike.databinding.RestPageFragmentBinding
import com.example.pomodorolike.ui.main_page.MainPageFragment

class RestPageFragment : Fragment(R.layout.rest_page_fragment) {
    private lateinit var binding: RestPageFragmentBinding
    private lateinit var viewModel: RestPageViewModel



    companion object {
        fun newInstance() = RestPageFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAg", "test")
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.rest_page_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RestPageViewModel::class.java)
        // TODO: Use the ViewModel
        binding.textBtn.setOnClickListener {
            val mainPageFragment = MainPageFragment()
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, mainPageFragment)
                commit()
            }
        }
    }

}
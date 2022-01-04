package com.example.pomodorolike.ui.rest_page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.pomodorolike.R
import com.example.pomodorolike.databinding.RestPageFragmentBinding

class RestPageFragment : Fragment(R.layout.rest_page_fragment) {
    private lateinit var binding: RestPageFragmentBinding
    private lateinit var viewModel: RestPageViewModel
    lateinit var navController: NavController




    companion object {
        fun newInstance() = RestPageFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAg", "test")
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.rest_page_fragment)
        navController = Navigation.findNavController(view)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RestPageViewModel::class.java)
        getPrevCycleCount()
        binding.textBtn.setOnClickListener {
            viewModel._completeCycleCount.value = ++viewModel.initialNumber
            viewModel._completeCycleCount.observe(viewLifecycleOwner){
                navController.navigate(R.id.action_restPageFragment2_to_mainPageFragment2, bundleOf("cycle_count" to it))

            }
        }
    }
    fun getPrevCycleCount(){
        viewModel.initialNumber = arguments?.getInt("cycle_count")!!
    }

}
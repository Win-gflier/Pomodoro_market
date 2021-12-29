package com.example.pomodorolike.ui.rest_page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pomodorolike.R

class RestPageFragment : Fragment() {

    companion object {
        fun newInstance() = RestPageFragment()
    }

    private lateinit var viewModel: RestPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rest_page_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RestPageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
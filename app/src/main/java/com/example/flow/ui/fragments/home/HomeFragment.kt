package com.example.flow.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.flow.R
import com.example.flow.databinding.FragmentHomeBinding
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HomeFragment:Fragment(R.layout.fragment_home) {
   private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
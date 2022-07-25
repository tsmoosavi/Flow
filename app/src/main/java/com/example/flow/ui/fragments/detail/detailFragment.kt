package com.example.flow.ui.fragments.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.flow.R
import com.example.flow.databinding.FragmentDetailBinding

class detailFragment: Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
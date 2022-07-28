package com.example.flow.ui.fragments.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.Status
import com.example.flow.R
import com.example.flow.databinding.FragmentHomeBinding
import com.example.util.gone
import com.example.util.showToastMessage
import com.example.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val vm: HomeViewModel by viewModels()
    private val adapter: HomeImageAdapter by lazy { HomeImageAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        init()
        valueObservation()
        clickListener()
    }

    private fun clickListener() {
        binding.retryButton.setOnClickListener {
            vm.requestToNetwork()
        }
    }

    private fun init() = binding.apply {
        imageRecycler.adapter = adapter
    }

    private fun valueObservation() {
        vm.imageList.observe(viewLifecycleOwner) {
            when (it.status){
                Status.SERVER_ERROR ->errorManager(it.message)
                Status.NETWORK_ERROR -> errorManager(it.message)
                Status.SUCCESSFUL ->{adapter.submitList(it.data)
                  successfulStatus()}
                Status.NOT_FOUND ->errorManager(it.message)
                Status.LOADING ->loadingStatus()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun errorManager (message: String){
        binding.loadingAnimation.gone()
        binding.retryButton.visible()
        binding.imageRecycler.gone()
        showToastMessage(message)
    }

    private fun loadingStatus(){
        binding.loadingAnimation.visible()
        binding.retryButton.gone()
        binding.imageRecycler.gone()
    }
    private fun successfulStatus(){
        binding.loadingAnimation.gone()
        binding.retryButton.gone()
        binding.imageRecycler.visible()
    }

}
package com.example.flow.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.data.network.util.Cause
import com.example.data.network.util.NetworkResult
import com.example.flow.R
import com.example.flow.databinding.FragmentHomeBinding
import com.example.util.gone
import com.example.util.loadImage
import com.example.util.showToastMessage
import com.example.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

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
        clickListener()
        collects()
    }

    private fun collects() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.imageList.collect {
                when (it) {
                    is NetworkResult.Error -> errorManager(it.cause)
                    is NetworkResult.Loading -> loadingStatus()
                    is NetworkResult.Success -> {
                        adapter.submitList(it.data)
                        successfulStatus()
                        binding.image.loadImage(
                            receiver = requireContext(),
                            data = it.data?.firstOrNull()?.downloadUrl,
                            isCircular = true
                        )
                    }
                }
            }
        }
    }

    private fun clickListener() {
        binding.retryButton.setOnClickListener {
            vm.fetchItems()
        }
    }

    private fun init() = binding.apply {
        imageRecycler.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.imageRecycler.adapter = null
        _binding = null
    }

    private fun errorManager(cause: Cause?) {
        binding.loadingAnimation.gone()
        binding.retryButton.visible()
        binding.imageRecycler.gone()
        val message = if (cause?.msg.isNullOrBlank())
            getString(cause?.msgResId ?: R.string.default_error_message)
        else
            cause?.msg.orEmpty()
        showToastMessage(message)
    }

    private fun loadingStatus() {
        binding.loadingAnimation.visible()
        binding.retryButton.gone()
        binding.imageRecycler.gone()
    }

    private fun successfulStatus() {
        binding.loadingAnimation.gone()
        binding.retryButton.gone()
        binding.imageRecycler.visible()
    }

}
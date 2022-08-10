package com.example.flow.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.data.network.util.Cause
import com.example.data.network.util.NetworkResult
import com.example.flow.R
import com.example.flow.databinding.FragmentHomeBinding
import com.example.flow.model.ImageItem
import com.example.util.gone
import com.example.util.loadImage
import com.example.util.showErrorMessage
import com.example.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val vm: HomeViewModel by viewModels()
    private val adapter: HomeImageAdapter by lazy {
        HomeImageAdapter { image: ImageItem ->
            goToDetailFragment(image)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        init()
        collects()
    }

    private fun collects() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.imageList.collect {
                when (it) {
                    is NetworkResult.Error -> {
                        binding.loadingView.onError {
                            vm.fetchItems()
                        }
                        binding.imageRecycler.gone()
                        showErrorMessage(it.cause)
                    }
                    is NetworkResult.Loading -> {
                        binding.loadingView.onLoading()
                        binding.imageRecycler.gone()

                    }

                    is NetworkResult.Success -> {
                        binding.loadingView.onSuccessOrEmpty(it.data.isNullOrEmpty())
                        adapter.submitList(it.data)
                        binding.imageRecycler.visible()
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


    private fun init() = binding.apply {
        imageRecycler.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.imageRecycler.adapter = null
        _binding = null
    }


    private fun goToDetailFragment(image: ImageItem) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(image)
        findNavController().navigate(action)
    }

}
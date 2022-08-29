package com.example.flow.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.flow.R
import com.example.flow.databinding.FragmentHomeBinding
import com.example.flow.model.ImageItem
import com.example.util.collectOnScope
import com.example.util.launchScope
import com.example.util.loadImage
import com.example.util.safeCollectOnScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : androidx.fragment.app.Fragment(R.layout.fragment_home) {

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

    private fun collects() = viewLifecycleOwner.lifecycleScope.launch{
        vm.imageList.safeCollectOnScope(
            loadingView = binding.loadingView,
            dataView = binding.imageRecycler,
            context = requireContext(),
            scope = this,
            onRetry = { vm.fetchItems() }
        ) {
            binding.image.loadImage(
                receiver = requireContext(),
                data = it.firstOrNull()?.downloadUrl,
                isCircular = true
            )
            adapter.submitList(it)
        }
        vm.secondImageList.collectOnScope(
            this
        ){}
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
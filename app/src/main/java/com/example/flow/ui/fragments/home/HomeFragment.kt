package com.example.flow.ui.fragments.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.data.network.util.NetworkResult
import com.example.flow.R
import com.example.flow.databinding.FragmentHomeBinding
import com.example.flow.model.ImageItem
import com.example.util.collectOnScope
import com.example.util.repeatOnScope
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
        Log.d("zzz", "onViewCreated")
        init()

        collects2()
        listener()
    }

    private fun listener()= binding.apply {
        image.setOnClickListener {
            if (vm.isNightMode){
                vm.isNightMode = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }else{
                vm.isNightMode = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun collects2() = viewLifecycleOwner.lifecycleScope.launch{
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            vm.imageList.collectOnScope(this){
                when (it){
                    is NetworkResult.Error -> {
                        binding.TV.paint.isUnderlineText = false
                        binding.TV.text = "Error Text"
                        binding.TV.setTextColor(Color.RED)
                    }
                    is NetworkResult.Loading -> {
                        binding.TV.paint.isUnderlineText = false
                        binding.TV.text = "Loading Text"
                        binding.TV.setTextColor(Color.BLUE)
                    }
                    is NetworkResult.Success -> {
                        binding.TV.paint.isUnderlineText = true
                        binding.TV.text = "Success text"
                        binding.TV.setTextColor(Color.GREEN)
                    }
                }
            }
        }

//        vm.imageList.safeCollectOnScope(
//            loadingView = binding.loadingView,
//            dataView = binding.imageRecycler,
//            context = requireContext(),
//            scope = this,
//            onRetry = { vm.fetchItems() }
//        ) {
//            binding.image.loadImage(
//                receiver = requireContext(),
//                data = it.firstOrNull()?.downloadUrl,
//                isCircular = true
//            )
//            adapter.submitList(it)
//        }
    }
//    private fun collects() = viewLifecycleOwner.lifecycleScope.launch {
//        viewLifecycleOwner.lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
//            vm.imageList.safeCollectOnScope(
//                loadingView = binding.loadingView,
//                dataView = binding.imageRecycler,
//                context = requireContext(),
//                scope = this,
//                onRetry = { vm.fetchItems() }
//            ) {
//                binding.image.loadImage(
//                    receiver = requireContext(),
//                    data = it.firstOrNull()?.downloadUrl,
//                    isCircular = true
//                )
//                adapter.submitList(it)
//            }
//        }
//
//    }

    private fun init() = binding.apply {
        imageRecycler.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.imageRecycler.adapter = null
        _binding = null
        Log.d("zzz", "onDestroyView")
    }

    override fun onStart() {
        super.onStart()
        Log.d("zzz", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("zzz", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("zzz", "onStop")
    }


    private fun goToDetailFragment(image: ImageItem) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(image)
        findNavController().navigate(action)
    }

}
package com.example.flow.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.flow.R
import com.example.flow.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val vm: HomeViewModel by viewModels()
    private val  adapter : HomeImageAdapter by lazy { HomeImageAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        init()
        valueObservation()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init() = binding.apply {
        imageRecycler.adapter = adapter


    }

    private fun valueObservation() {
        vm.imageList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
//            when(it.status){
//                Status.SERVER_ERROR -> Log.d("myStatus",it.message)
//                Status.NETWORK_ERROR -> Log.d("myStatus",it.message)
//                Status.SUCCESSFUL -> adapter.submitList(it.data)
//                Status.NOT_FOUND -> Log.d("myStatus",it.message)
//            }

        }
    }


}
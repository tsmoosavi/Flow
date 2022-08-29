package com.example.flow.ui.fragments.detail

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.flow.R
import com.example.flow.databinding.FragmentDetailBinding
import com.example.util.isNotNull
import com.example.util.loadImage
import com.example.util.textOrEmpty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : androidx.fragment.app.Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val vm: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        init()
        binding.title.addTextChangedListener {
            vm.title = it.textOrEmpty()
        }
//        binding.title click{
//            Toast.makeText(requireContext(), binding.title.text, Toast.LENGTH_SHORT).show()
//        }
    }

    private fun init() {
        val imageItem = vm.imageItem
        if (imageItem.isNotNull()) {
            binding.title.text = imageItem.author
            binding.image.loadImage(
                requireContext(),
                imageItem.downloadUrl,
                isRoundedCorner = true
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
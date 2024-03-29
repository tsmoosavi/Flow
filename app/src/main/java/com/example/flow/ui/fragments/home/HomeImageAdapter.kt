package com.example.flow.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flow.databinding.ImageItemBinding
import com.example.flow.model.ImageItem
import com.example.util.loadImage

typealias  clickManger= (image: ImageItem) -> Unit
class HomeImageAdapter(val clickHandler: clickManger) :
    ListAdapter<ImageItem, HomeImageAdapter.ItemHolder>(ImageDiffCallback) {

    inner class ItemHolder(private val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageItem: ImageItem)= binding.apply{
            title.text = imageItem.author
            image.loadImage(receiver = root, data = imageItem.downloadUrl, isRoundedCorner = true)
            itemView.setOnClickListener{
                clickHandler(imageItem)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: ImageItemBinding = ImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object ImageDiffCallback : DiffUtil.ItemCallback<ImageItem>() {

    override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem == newItem
    }
}
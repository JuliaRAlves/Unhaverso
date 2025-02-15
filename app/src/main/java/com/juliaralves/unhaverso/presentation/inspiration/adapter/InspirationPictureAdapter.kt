package com.juliaralves.unhaverso.presentation.inspiration.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.databinding.ItemInspirationPictureBinding
import com.juliaralves.unhaverso.domain.model.InspirationPictureVO

class InspirationPictureAdapter(
    private val onPictureClicked: (InspirationPictureVO) -> Unit
) : ListAdapter<InspirationPictureVO, InspirationPictureAdapter.InspirationPictureViewHolder>(
    InspirationPictureDiffCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InspirationPictureViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemInspirationPictureBinding.inflate(layoutInflater, parent, false)
        return InspirationPictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InspirationPictureViewHolder, position: Int) {
        holder.bind(getItem(position), onPictureClicked)
    }

    class InspirationPictureViewHolder(
        private val binding: ItemInspirationPictureBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(picture: InspirationPictureVO, onPictureClicked: (InspirationPictureVO) -> Unit) {
            Glide.with(binding.root.context)
                .load(picture.imageUrl)
                .placeholder(R.drawable.bg_broken)
                .into(binding.imgPicture)
            binding.imgPicture.setOnClickListener { onPictureClicked.invoke(picture) }
        }

    }

    object InspirationPictureDiffCallback : DiffUtil.ItemCallback<InspirationPictureVO>() {
        override fun areItemsTheSame(
            oldItem: InspirationPictureVO,
            newItem: InspirationPictureVO
        ): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(
            oldItem: InspirationPictureVO,
            newItem: InspirationPictureVO
        ): Boolean {
            return oldItem == newItem
        }
    }
}
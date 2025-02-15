package com.juliaralves.unhaverso.presentation.inspiration.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.databinding.ItemInspirationPictureBinding
import com.juliaralves.unhaverso.domain.model.InspirationPictureVO

class InspirationPictureAdapter(
    private val onDownloadClicked: (InspirationPictureVO) -> Unit,
    private val onShareClicked: (InspirationPictureVO) -> Unit
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
        holder.bind(getItem(position), onDownloadClicked, onShareClicked)
    }

    class InspirationPictureViewHolder(
        private val binding: ItemInspirationPictureBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            picture: InspirationPictureVO,
            onDownloadClicked: (InspirationPictureVO) -> Unit,
            onShareClicked: (InspirationPictureVO) -> Unit
        ) {
            with(binding) {
                Glide.with(root.context)
                    .load(picture.imageUrl)
                    .placeholder(R.drawable.bg_broken)
                    .into(imgPicture)
                imgPicture.setOnClickListener { ctnOptions.isVisible = true }
                icDownload.setOnClickListener {
                    onDownloadClicked.invoke(picture)
                    ctnOptions.isVisible = false
                }
                icShare.setOnClickListener {
                    onShareClicked.invoke(picture)
                    ctnOptions.isVisible = false
                }
                ctnOptions.setOnClickListener { ctnOptions.isVisible = false }
            }
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
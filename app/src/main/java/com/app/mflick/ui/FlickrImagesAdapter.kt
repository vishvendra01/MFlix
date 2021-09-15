package com.app.mflick.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.mflick.databinding.ItemPhotoBinding
import com.app.mflick.model.Photo
import com.app.mflick.utils.buildImageUrlFromPhoto
import com.bumptech.glide.Glide

class FlickrImagesAdapter : PagingDataAdapter<Photo, FlickrHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrHolder {
        return FlickrHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FlickrHolder, position: Int) {
        val photo = getItem(position) ?: return

        Glide.with(holder.itemView)
            .load(buildImageUrlFromPhoto(photo))
            .into(holder.itemPhotoBinding.pictureImageView)

        holder.itemPhotoBinding.titleImageView.text = photo.title
    }
}

class FlickrHolder(val itemPhotoBinding: ItemPhotoBinding) :
    RecyclerView.ViewHolder(itemPhotoBinding.root)


private val COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}
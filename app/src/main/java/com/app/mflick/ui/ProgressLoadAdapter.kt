package com.app.mflick.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.mflick.databinding.ItemPageFooterBinding

class ProgressLoadAdapter : LoadStateAdapter<ProgressLoadAdapter.ProgressViewHolder>() {

    class ProgressViewHolder(val itemPageFooterBinding: ItemPageFooterBinding) :
        RecyclerView.ViewHolder(itemPageFooterBinding.root) {

    }

    override fun onBindViewHolder(holder: ProgressViewHolder, loadState: LoadState) {
        with(holder.itemPageFooterBinding) {
            retryButton.isVisible = loadState !is LoadState.Loading
            errorMessageTextView.isVisible = loadState !is LoadState.Loading
            progressBar.isVisible = loadState is LoadState.Loading

            if (loadState is LoadState.Error) {
                errorMessageTextView.text = loadState.error.localizedMessage
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ProgressViewHolder {
        return ItemPageFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .run {
                ProgressViewHolder(this)
            }
    }
}
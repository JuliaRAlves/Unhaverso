package com.juliaralves.unhaverso.presentation.inspiration

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class InspirationFragment : Fragment() {

    private val viewModel: InspirationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.onViewCreated()
    }

    private fun setupObservers() {
        viewModel.downloadLiveData.observe(viewLifecycleOwner, ::downloadImageFromUrl)
        viewModel.shareLiveData.observe(viewLifecycleOwner, ::shareImageFromUrl)
    }

    private fun downloadImageFromUrl(url: String) {
        // save image from url
    }

    private fun shareImageFromUrl(url: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("image/jpeg")
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url))
        startActivity(Intent.createChooser(intent, "Share Image"))
    }
}
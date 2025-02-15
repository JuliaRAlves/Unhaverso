package com.juliaralves.unhaverso.presentation.inspiration

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.juliaralves.unhaverso.databinding.FragmentInspirationBinding
import com.juliaralves.unhaverso.domain.model.InspirationPictureVO
import com.juliaralves.unhaverso.presentation.inspiration.adapter.InspirationPictureAdapter


class InspirationFragment : Fragment() {

    private var _binding: FragmentInspirationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InspirationViewModel by viewModels()

    private val pictureAdapter by lazy {
        InspirationPictureAdapter(
            viewModel::onDownloadPictureClicked,
            viewModel::onSharePictureClicked
        )
    }
    private val mainAdapter = ConcatAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInspirationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        viewModel.onViewCreated()
    }

    private fun setupRecyclerView() {
        binding.rvContent.adapter = mainAdapter
        binding.rvContent.layoutManager = StaggeredGridLayoutManager(
            2,
            LinearLayoutManager.VERTICAL
        )
    }

    private fun setupObservers() {
        viewModel.pictureListLiveData.observe(viewLifecycleOwner, ::setPictureList)
        viewModel.downloadLiveData.observe(viewLifecycleOwner, ::downloadImageFromUrl)
        viewModel.shareLiveData.observe(viewLifecycleOwner, ::shareImageFromUrl)
    }

    private fun setPictureList(pictureList: List<InspirationPictureVO>) {
        pictureAdapter.submitList(pictureList)
        mainAdapter.addAdapter(pictureAdapter)
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
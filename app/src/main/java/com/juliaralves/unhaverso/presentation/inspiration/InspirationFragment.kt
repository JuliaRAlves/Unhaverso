package com.juliaralves.unhaverso.presentation.inspiration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.juliaralves.unhaverso.databinding.FragmentInspirationBinding
import com.juliaralves.unhaverso.domain.model.InspirationPictureVO
import com.juliaralves.unhaverso.presentation.inspiration.adapter.InspirationPictureAdapter

class InspirationFragment : Fragment() {

    private var _binding: FragmentInspirationBinding? = null
    private val binding get() = _binding!!

    private val pictureAdapter by lazy { InspirationPictureAdapter { _ -> } }
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

        val pictureList = listOf(
            "https://m.media-amazon.com/images/I/81hwPfRNT7L._SL1500_.jpg",
            "https://www.byrdie.com/thmb/buMlOI4jQxZI2NSLrezikkpuiME=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/neutralanails_recirc-2d3a9cda11dd4d94be17a5fafff54880.jpg",
            "https://media.glamourmagazine.co.uk/photos/66d1b1ad94d5017f0a75866d/1:1/w_1081,h_1081,c_limit/BOW%20NAILS%20300824%20MAIN.jpg",
            "https://negociosdebeleza.beautyfair.com.br/wp-content/uploads/2023/09/tendencias-nail-art-2024-3-1.png",
            "https://www.refinery29.com/images/11798078.jpg",
            "https://www.instyle.com/thmb/Q0PMTVFmvHLDoyr8f0aOQhe_H9M=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/366441869_296892119600354_6448618165582235578_n-9de5af90aa044dc09494925896fb3832.jpg",
            "https://static.stealthelook.com.br/wp-content/uploads/2022/10/tendencias-de-nail-art-abstrato-verde-20221018201922.jpg",
            "https://www.coloramaesmaltes.com.br/-/media/Project/Loreal/Brand-Sites/Essie/MASTER/DMI/articles/tips_trends/2023/jelly-nails/jelly-nails.jpg",
            "https://belezamoderna.com.br/wp-content/uploads/2023/04/glass-nail-2-e1682517372696.webp",
            "https://harpersbazaar.uol.com.br/wp-content/uploads/2023/05/nail-art-cromada-@carolina-683x1024.jpg"
        ).map { InspirationPictureVO(it) }

        pictureAdapter.submitList(pictureList)
        mainAdapter.addAdapter(pictureAdapter)
    }

    private fun setupRecyclerView() {
        val manager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvContent.adapter = mainAdapter
        binding.rvContent.layoutManager = manager
    }
}
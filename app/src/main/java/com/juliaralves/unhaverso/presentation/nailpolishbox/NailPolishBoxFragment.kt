package com.juliaralves.unhaverso.presentation.nailpolishbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juliaralves.unhaverso.databinding.FragmentNailPolishBoxBinding

class NailPolishBoxFragment : Fragment() {
    private var _binding: FragmentNailPolishBoxBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNailPolishBoxBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
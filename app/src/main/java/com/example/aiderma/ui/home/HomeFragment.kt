package com.example.aiderma.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aiderma.R
import com.example.aiderma.api.response.DiseasesItem
import com.example.aiderma.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvDisease.layoutManager =layoutManager

        val viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.getListDisease()

        viewModel.listDisease.observe(viewLifecycleOwner) { listDisease ->
            val adapter = DiseaseAdapter(listDisease)

            adapter.setOnItemClickCallback(object : DiseaseAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DiseasesItem) {

                }
            })
            binding.rvDisease.adapter = adapter
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading : Boolean){
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
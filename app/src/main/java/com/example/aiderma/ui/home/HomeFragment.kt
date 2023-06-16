package com.example.aiderma.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aiderma.R
import com.example.aiderma.api.response.DiseasesItem
import com.example.aiderma.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: DiseaseAdapter

    private val binding get() = _binding!!
    private var isSearching = false


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

        adapter = DiseaseAdapter(emptyList())
        adapter.setOnItemClickCallback(object : DiseaseAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DiseasesItem) {
                // Logika yang sesuai saat item di RecyclerView diklik
            }
        })
        binding.rvDisease.adapter = adapter

        viewModel.listDisease.observe(viewLifecycleOwner) { listDisease ->
            val data = if (isSearching) viewModel.searchedListDisease.value else listDisease
            adapter.updateData(data ?: emptyList())
        }

        viewModel.searchedListDisease.observe(viewLifecycleOwner) { searchedListDisease ->
            if (isSearching) {
                adapter.updateData(searchedListDisease ?: emptyList())
            }
        }


        // searchview
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    viewModel.search(query)
                    isSearching = true
                } else {
                    isSearching = false
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (isSearching && newText.isEmpty()) {
                    isSearching = false
                    adapter.updateData(viewModel.listDisease.value ?: emptyList())
                }
                return true
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
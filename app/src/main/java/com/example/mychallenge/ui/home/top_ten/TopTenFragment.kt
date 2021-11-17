package com.example.mychallenge.ui.home.top_ten

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mychallenge.databinding.FragmentTopTenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopTenFragment: Fragment() {
    private val topTensViewModel: TopTenViewModel by viewModels()
    private var _binding: FragmentTopTenBinding? = null
    private val binding get() = _binding!!
    private var mAdapter: TopTensAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopTenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerView()

        topTensViewModel.progressVisible.observe(viewLifecycleOwner, { visible ->
            binding.progressCircular.isVisible = visible
        })

        topTensViewModel.topTensList.observe(viewLifecycleOwner, { topTenList ->
            getUpdateTable(topTenList)
        })
    }

    private fun setUpRecyclerView(){
        binding.recyclerTopTens.layoutManager = LinearLayoutManager(context)
        mAdapter = TopTensAdapter(listOf())
        binding.recyclerTopTens.adapter = mAdapter
    }


    private fun getUpdateTable(listTopTens: List<Any>){
        mAdapter?.setUpdateData(listTopTens)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
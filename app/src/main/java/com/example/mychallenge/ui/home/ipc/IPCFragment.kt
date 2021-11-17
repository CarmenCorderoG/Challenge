package com.example.mychallenge.ui.home.ipc

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mychallenge.R
import com.example.mychallenge.common.Extensions.getTime
import com.example.mychallenge.databinding.FragmentIpcBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IPCFragment: Fragment() {
    private val ipcViewModel: IPCViewModel by viewModels()
    private var _binding: FragmentIpcBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIpcBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        ipcViewModel.progressVisible.observe(viewLifecycleOwner, {
            binding.progressCircular.isVisible = it
        })

        ipcViewModel.textDate.observe(viewLifecycleOwner, { date ->
            binding.titleTime.text = date
            binding.title.isVisible = true
            binding.titleGrafic.isVisible = true
            binding.titleTime.isVisible = true
        })

        ipcViewModel.showGraph.observe(viewLifecycleOwner, {
            binding.title.text = getString(R.string.label_title_ipc)
            binding.titleGrafic.text = getString(R.string.label_graph_ipc)
            binding.lineChart.isVisible = it
        })

        ipcViewModel.ipcList.observe(viewLifecycleOwner, { valuesXY ->
            getListData(valuesXY)
        })
    }

    private fun getListData(listIPC: MutableMap<Long, Float>){
        val arrayListData: ArrayList<Entry> = arrayListOf()

        listIPC.forEach{
            arrayListData.add(Entry(it.key.toFloat(), it.value))
        }


        val lineDataSet = LineDataSet(arrayListData, getString(R.string.label_for_values))
        val lineData = LineData(lineDataSet)

        binding.lineChart.data = lineData
        binding.lineChart.notifyDataSetChanged()
        binding.lineChart.description.isEnabled = false
        Log.d("valuesX", "getListData: " + binding.lineChart.xAxis.valueFormatter )
        /*
         binding.lineChart.xAxis.valueFormatter =  IAxisValueFormatter()
        /*binding.lineChart.xAxis.valueFormatter = IAxisValueFormatter { value, axis ->
            val index = value.toInt()
            Log.d("valuesX", "getListData: " + value + " - " + axis).toString()

        } as ValueFormatter?*/
        * */
        binding.lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(listIPC.keys.getTime())
        binding.lineChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

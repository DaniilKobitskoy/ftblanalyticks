package com.app.eva.footbalanalyticks.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.eva.footbalanalyticks.R
import com.app.eva.footbalanalyticks.databinding.FragmentAnalyticksBinding
import com.app.eva.footbalanalyticks.view.adapter.StandingsAdapter
import com.app.eva.footbalanalyticks.viewmodel.StandingsViewModel


class AnalyticksFragment : Fragment() {
lateinit var binding: FragmentAnalyticksBinding
    private lateinit var viewModel: StandingsViewModel
    private lateinit var standingsAdapter: StandingsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnalyticksBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(StandingsViewModel::class.java)

        val competitionId = requireArguments().getString("competitionId") ?: "" // Получаем competitionId из аргументов Bundle
        viewModel.fetchStandings(competitionId) // Используем competitionId в fetchStandings методе
        Log.d("standings2", "standings.toString()")

        viewModel.standings.observe(viewLifecycleOwner, Observer { standings ->
            Log.d("standings", standings.toString())
            standingsAdapter = StandingsAdapter(standings)
            binding.recyclerViewStandings.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = standingsAdapter
            }
        })
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AnalyticksFragment().apply {
                arguments = Bundle().apply {
                    putString("competitionId", param1) // Помещаем значение param1 в Bundle с ключом "competitionId"
                }
            }
    }
}
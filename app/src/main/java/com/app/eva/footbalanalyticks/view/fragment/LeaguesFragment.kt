package com.app.eva.footbalanalyticks.view.fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.eva.footbalanalyticks.databinding.FragmentLeaguesBinding
import com.app.eva.footbalanalyticks.model.Match
import com.app.eva.footbalanalyticks.view.activity.bindingMain
import com.app.eva.footbalanalyticks.view.adapter.MatchesAdapter
import com.app.eva.footbalanalyticks.viewmodel.MatchesViewModel
class LeaguesFragment : Fragment(), MatchesAdapter.OnItemClickListener {

    private lateinit var binding: FragmentLeaguesBinding
    private lateinit var viewModel: MatchesViewModel
    private lateinit var matchesAdapter: MatchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLeaguesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MatchesViewModel::class.java)
        setupRecyclerView()

        viewModel.matches.observe(viewLifecycleOwner, Observer { matches ->
            Log.d("Matches", matches.toString())
            matchesAdapter.submitList(matches)
        })

        // Проверяем, были ли уже получены данные, если нет, то запускаем запрос
        if (viewModel.matches.value == null) {
            binding.progressBar2.visibility = View.GONE
            binding.recyclerViewMatches.visibility = View.VISIBLE
            viewModel.fetchMatches()
        }
    }

    private fun setupRecyclerView() {
        matchesAdapter = MatchesAdapter(this)
        binding.recyclerViewMatches.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = matchesAdapter
        }
    }

    override fun onItemClick(match: Match) {
        val analyticksFragment = AnalyticksFragment.newInstance(match.competition.code, "")
        parentFragmentManager.beginTransaction()
            .replace(bindingMain.container.id, analyticksFragment)
            .addToBackStack(null)
            .commit()
    }
}

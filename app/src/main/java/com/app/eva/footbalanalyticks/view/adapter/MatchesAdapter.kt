package com.app.eva.footbalanalyticks.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.eva.footbalanalyticks.R
import com.app.eva.footbalanalyticks.model.Match
import com.squareup.picasso.Picasso

class MatchesAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<MatchesAdapter.MatchViewHolder>() {

    private var matches = listOf<Match>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
        holder.bind(match)
    }

    override fun getItemCount(): Int = matches.size

    inner class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val competitionTextView: TextView = itemView.findViewById(R.id.text_view_competition)
        private val homeTeamTextView: TextView = itemView.findViewById(R.id.text_view_home_team)
        private val awayTeamTextView: TextView = itemView.findViewById(R.id.text_view_away_team)
        private val homeIcon: ImageView = itemView.findViewById(R.id.homeImage)
        private val awayIcon: ImageView = itemView.findViewById(R.id.awayImage)
        private val scoreHome: TextView = itemView.findViewById(R.id.scoreHome)
        private val scoreAway: TextView = itemView.findViewById(R.id.scoreAway)
        private val legueImage: ImageView = itemView.findViewById(R.id.compImage)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(match: Match) {
            competitionTextView.text = match.competition.name
            homeTeamTextView.text = match.homeTeam.name
            awayTeamTextView.text = match.awayTeam.name
            val homeScore = match.score.fullTime.home?.toString() ?: "0"
            val awayScore = match.score.fullTime.away?.toString() ?: "0"
            scoreHome.text = homeScore
            scoreAway.text = awayScore
            awayTeamTextView.text = match.awayTeam.name
            Picasso.get().load(match.homeTeam.crest).into(homeIcon)
            Picasso.get().load(match.awayTeam.crest).into(awayIcon)
            Picasso.get().load(match.competition.emblem).into(legueImage)

        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val match = matches[position]
                listener.onItemClick(match)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(match: Match)
    }

    fun submitList(matches: List<Match>) {
        this.matches = matches
        notifyDataSetChanged()
    }
}
package com.app.eva.footbalanalyticks.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.eva.footbalanalyticks.R
import com.app.eva.footbalanalyticks.model.Standing
import com.app.eva.footbalanalyticks.model.TeamStanding
import com.squareup.picasso.Picasso

class StandingsAdapter(private val standings: List<Standing>) : RecyclerView.Adapter<StandingsAdapter.StandingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_standings, parent, false)
        return StandingViewHolder(view)
    }

    override fun onBindViewHolder(holder: StandingViewHolder, position: Int) {
        val standing = standings[position]
        holder.bind(standing)
    }

    override fun getItemCount(): Int {
        return standings.size
    }

    class StandingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val teamNameTextView: TextView = itemView.findViewById(R.id.teamNameTextView)
        private val positionTextView: TextView = itemView.findViewById(R.id.positionTextView)
        private val pointsTextView: TextView = itemView.findViewById(R.id.pointsTextView)
        private val groupTextView: TextView = itemView.findViewById(R.id.group)
        private val teamIconImageView: ImageView = itemView.findViewById(R.id.iconteam)
        fun bind(standing: Standing) {
            positionTextView.text = standing.table.first().position.toString()
            teamNameTextView.text = standing.table.first().team.name
            pointsTextView.text = standing.table.first().points.toString()
            groupTextView.text = standing.group
            Picasso.get().load(standing.table.first().team.crest).into(teamIconImageView)
        }

//        fun bind(standing: Standing) {
//            val teamStanding = standing.table.first() // Берем первый элемент из таблицы
//            positionTextView.text = teamStanding.position.toString()
//            teamNameTextView.text = teamStanding.team.name
//            pointsTextView.text = teamStanding.points.toString()
//            groupTextView.text = standing.group
//            Picasso.get().load(teamStanding.team.crest).into(teamIconImageView)
//        }
    }
}


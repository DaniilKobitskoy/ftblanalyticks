package com.app.eva.footbalanalyticks.view.adapter



import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.app.eva.footbalanalyticks.R
import com.app.eva.footbalanalyticks.model.FiltersData
import com.app.eva.footbalanalyticks.model.Match
import com.app.eva.footbalanalyticks.model.MatchData


class MatchAdapter(private val matches: List<MatchData>) : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match2, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
//        Log.d("MainData4", match.toString())
        // Загрузка изображений для домашней и гостевой команд
        Picasso.get().load(match.homeTeam.crest).into(holder.homeTeamImageView)
        Picasso.get().load(match.awayTeam.crest).into(holder.awayTeamImageView)

        holder.homeTeamTextView.text = match.homeTeam.name
        holder.awayTeamTextView.text = match.awayTeam.name

        // Установка счета матча
        val score = if (match.score.fullTime.home == null && match.score.fullTime.away == null){
            "0 : 0"
        } else {
            "${match.score.fullTime.home} : ${match.score.fullTime.away}"
        }
        holder.matchScoreTextView.text = score

        // Установка информации о матче
        holder.matchInfoTextView.text = match.status
    }

    override fun getItemCount(): Int {
        return matches.size
    }

    inner class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val homeTeamImageView: ImageView = itemView.findViewById(R.id.image_home_team)
        val awayTeamImageView: ImageView = itemView.findViewById(R.id.image_away_team)
        val homeTeamTextView: TextView = itemView.findViewById(R.id.text_home_team)
        val awayTeamTextView: TextView = itemView.findViewById(R.id.text_away_team)
        val matchScoreTextView: TextView = itemView.findViewById(R.id.text_match_score)
        val matchInfoTextView: TextView = itemView.findViewById(R.id.text_match_info)
    }
}

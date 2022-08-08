package com.endlessloopsoftwares.skrate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.endlessloopsoftwares.skrate.R
import com.endlessloopsoftwares.skrate.models.DashboardStats
import com.endlessloopsoftwares.skrate.models.UpcomingSession

class UpcomingRVAdapter(private val upcomingSessions: List<UpcomingSession>, private val currContext: Context) :
    RecyclerView.Adapter<UpcomingRVAdapter.UpcomingRVViewHolder>() {
    inner class UpcomingRVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sessionName: TextView = itemView.findViewById(R.id.sessionName)
        val sessionTime: TextView = itemView.findViewById(R.id.sessionTime)
        val sessionTitle: TextView = itemView.findViewById(R.id.sessionTitle)
        val sessionDate: TextView = itemView.findViewById(R.id.sessionDate)
        val sessionType: Button = itemView.findViewById(R.id.sessionType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingRVViewHolder {
        return UpcomingRVViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.upcoming_session_rv_items,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UpcomingRVViewHolder, position: Int) {
        val currData = upcomingSessions[position]
        holder.sessionName.text = currData.mentor_name
        holder.sessionTime.text = currData.timings
        holder.sessionTitle.text = if(position % 2 == 0) "Flutter" else "Django"
        holder.sessionDate.text = currData.date
        holder.sessionType.text = currData.session_type
        if(currData.session_type == "Mentorship") holder.sessionType.setBackgroundColor(currContext.getColor(R.color.mentorBg))
        else holder.sessionType.setBackgroundColor(currContext.getColor(R.color.bgColor1))
    }

    override fun getItemCount(): Int {
        return upcomingSessions.size
    }
}
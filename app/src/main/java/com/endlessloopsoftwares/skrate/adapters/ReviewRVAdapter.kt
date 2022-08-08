package com.endlessloopsoftwares.skrate.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.endlessloopsoftwares.skrate.R
import com.endlessloopsoftwares.skrate.models.DashboardStats

class ReviewRVAdapter(private val dashboardStats: DashboardStats) :
    RecyclerView.Adapter<ReviewRVAdapter.ReviewRVViewHolder>() {
    inner class ReviewRVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val overviewTitle: TextView = itemView.findViewById(R.id.eachOverviewTitle)
        val overviewCount: TextView = itemView.findViewById(R.id.eachOverviewCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewRVViewHolder {
        return ReviewRVViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.overview_rv_items,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReviewRVViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.overviewTitle.text = "Profile Views"
                holder.overviewCount.text = dashboardStats.profile_views.toString()
            }
            1 -> {
                holder.overviewTitle.text = "Mentorship\nSessions"
                holder.overviewCount.text = dashboardStats.mentorship_sessions.toString()
            }
            2 -> {
                holder.overviewTitle.text = "Jobs Applied"
                holder.overviewCount.text = dashboardStats.jobs_applied.toString()
            }
            3 -> {
                holder.overviewTitle.text = "Skills Verified"
                holder.overviewCount.text = dashboardStats.skills_verified.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return 4
    }
}
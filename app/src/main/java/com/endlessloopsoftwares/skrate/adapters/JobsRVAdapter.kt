package com.endlessloopsoftwares.skrate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.endlessloopsoftwares.skrate.R
import com.endlessloopsoftwares.skrate.models.JobPosting
import org.w3c.dom.Text

class JobsRVAdapter(private val jobPostings: List<JobPosting>, private val currContext: Context) :
    RecyclerView.Adapter<JobsRVAdapter.JobsRVViewHolder>() {
    inner class JobsRVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jobCompany: TextView = itemView.findViewById(R.id.jobRole)
        val jobPosted: TextView = itemView.findViewById(R.id.jobPosted)
        val jobInfo: TextView = itemView.findViewById(R.id.jobInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsRVViewHolder {
        return JobsRVViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.jobs_rv_items,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: JobsRVViewHolder, position: Int) {
        val currData = jobPostings[position]
        holder.jobCompany.text = currData.role
        holder.jobInfo.text = currContext.getString(R.string.jobInfoStr,currData.organization_name,currData.location)
        holder.jobPosted.text = "2hrs Ago"
    }

    override fun getItemCount(): Int {
        return jobPostings.size
    }
}
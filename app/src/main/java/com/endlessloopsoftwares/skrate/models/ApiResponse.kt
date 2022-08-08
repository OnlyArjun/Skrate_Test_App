package com.endlessloopsoftwares.skrate.models

data class ApiResponse(
    val dashboard_stats: DashboardStats,
    val full_name: String,
    val job_postings: List<JobPosting>,
    val upcoming_sessions: List<UpcomingSession>
)
package com.endlessloopsoftwares.skrate.viewUtils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.endlessloopsoftwares.skrate.adapters.JobsRVAdapter
import com.endlessloopsoftwares.skrate.adapters.ReviewRVAdapter
import com.endlessloopsoftwares.skrate.adapters.UpcomingRVAdapter
import com.endlessloopsoftwares.skrate.databinding.ActivityHomeBinding
import com.endlessloopsoftwares.skrate.repository.Repository
import com.endlessloopsoftwares.skrate.responseUtils.ResponseStates
import com.endlessloopsoftwares.skrate.viewModelUtils.SkrateVMProvider
import com.endlessloopsoftwares.skrate.viewModelUtils.SkrateViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var myAuthInstance: FirebaseAuth
    private lateinit var myViewBinder: ActivityHomeBinding
    private lateinit var myViewModel: SkrateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewBinder = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(myViewBinder.root)

        myAuthInstance = FirebaseAuth.getInstance()
        val vmProvider = SkrateVMProvider(application, Repository())
        myViewModel = ViewModelProvider(this, vmProvider)[SkrateViewModel::class.java]

        setUpData()
    }

    private fun setUpData() {
        Glide.with(this).load(myAuthInstance.currentUser!!.photoUrl).into(myViewBinder.userImage)

        myViewModel.apiResponsesObject.observe(this) { responseState ->
            if (responseState is ResponseStates.SuccessCL) {
                myViewBinder.overviewRV.apply {
                    adapter = ReviewRVAdapter(responseState.responseData!!.dashboard_stats)
                    layoutManager = LinearLayoutManager(this@HomeActivity)
                }

                myViewBinder.upcomingRV.apply {
                    adapter = UpcomingRVAdapter(responseState.responseData!!.upcoming_sessions, this@HomeActivity)
                    layoutManager = LinearLayoutManager(this@HomeActivity)
                }

                myViewBinder.jobsRV.apply {
                    adapter = JobsRVAdapter(responseState.responseData!!.job_postings,this@HomeActivity)
                    layoutManager = LinearLayoutManager(this@HomeActivity)
                }
            } else {
                Snackbar.make(
                    myViewBinder.root,
                    responseState.responseMessage ?: "Api Error Occurred",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}
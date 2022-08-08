package com.endlessloopsoftwares.skrate.viewUtils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.endlessloopsoftwares.skrate.adapters.JobsRVAdapter
import com.endlessloopsoftwares.skrate.adapters.ReviewRVAdapter
import com.endlessloopsoftwares.skrate.adapters.UpcomingRVAdapter
import com.endlessloopsoftwares.skrate.databinding.ActivityHomeBinding
import com.endlessloopsoftwares.skrate.models.DashboardStats
import com.endlessloopsoftwares.skrate.models.JobPosting
import com.endlessloopsoftwares.skrate.models.UpcomingSession
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
    private var overViewData: DashboardStats? = null
    private var upcomingData: List<UpcomingSession>? = null
    private var jobData: List<JobPosting>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewBinder = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(myViewBinder.root)

        // getting the instance of firebase
        myAuthInstance = FirebaseAuth.getInstance()

        // initialising the ViewModel with the ViewModelProvider Factory
        val vmProvider = SkrateVMProvider(application, Repository())
        myViewModel = ViewModelProvider(this, vmProvider)[SkrateViewModel::class.java]

        // calling the function to setup the data
        setUpData()
    }

    private fun setUpData() {
        // setting up the profile photo of the logged in
        // user's gmail-id
        Glide.with(this).load(myAuthInstance.currentUser!!.photoUrl).into(myViewBinder.userImage)

        // observing the changes made due to api call and
        // setting up the Adapter as required
        myViewModel.apiResponsesObject.observe(this) { responseState ->
            if (responseState is ResponseStates.SuccessCL) {
                overViewData = responseState.responseData!!.dashboard_stats
                upcomingData = responseState.responseData.upcoming_sessions
                jobData = responseState.responseData.job_postings

                myViewBinder.overviewRV.apply {
                    adapter = ReviewRVAdapter(responseState.responseData.dashboard_stats)
                    layoutManager = LinearLayoutManager(this@HomeActivity)
                }

                myViewBinder.upcomingRV.apply {
                    adapter = UpcomingRVAdapter(responseState.responseData.upcoming_sessions, this@HomeActivity)
                    layoutManager = LinearLayoutManager(this@HomeActivity)
                }

                myViewBinder.jobsRV.apply {
                    adapter = JobsRVAdapter(responseState.responseData.job_postings,this@HomeActivity)
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

        // applying the drag drop feature
        val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                        ItemTouchHelper.START or ItemTouchHelper.END,
                0
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder, target: ViewHolder
                ): Boolean {
                    val fromPos = viewHolder.adapterPosition
                    val toPos = target.adapterPosition
                    recyclerView.adapter!!.notifyItemMoved(fromPos,toPos)
                    return true
                }

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                    // remove from adapter
                }
            })
        mIth.attachToRecyclerView(myViewBinder.overviewRV)
    }
}
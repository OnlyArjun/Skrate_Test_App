package com.endlessloopsoftwares.skrate.viewModelUtils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.endlessloopsoftwares.skrate.repository.Repository

class SkrateVMProvider(
    val app: Application,
    private val repoVar: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SkrateViewModel(app, repoVar) as T
    }
}
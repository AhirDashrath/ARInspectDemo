package com.arinspect.facts.base

import android.arch.lifecycle.ViewModel
import com.arinspect.facts.injection.component.DaggerViewModelInjector
import com.arinspect.facts.injection.component.ViewModelInjector
import com.arinspect.facts.injection.module.NetworkModule
import com.arinspect.facts.ui.facts.FactsListViewModel
import com.arinspect.facts.ui.facts.FactsViewModel

abstract class BaseViewModel:ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is FactsListViewModel -> injector.inject(this)
            is FactsViewModel -> injector.inject(this)
        }
    }
}
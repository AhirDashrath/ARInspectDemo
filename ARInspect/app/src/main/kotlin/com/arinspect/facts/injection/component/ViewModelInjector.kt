package com.arinspect.facts.injection.component

import dagger.Component
import com.arinspect.facts.injection.module.NetworkModule
import com.arinspect.facts.ui.facts.FactsListViewModel
import com.arinspect.facts.ui.facts.FactsViewModel
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified FactsListViewModel.
     * @param factsListViewModel FactsListViewModel in which to inject the dependencies
     */
    fun inject(factsListViewModel: FactsListViewModel)
    /**
     * Injects required dependencies into the specified FactsViewModel.
     * @param factsViewModel FactsViewModel in which to inject the dependencies
     */
    fun inject(factsViewModel: FactsViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}
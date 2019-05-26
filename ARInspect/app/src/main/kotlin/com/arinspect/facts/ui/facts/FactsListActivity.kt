package com.arinspect.facts.ui.facts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.arinspect.facts.R
import com.arinspect.facts.databinding.ActivityFactListBinding
import com.arinspect.facts.injection.ViewModelFactory
import com.arinspect.facts.utils.SharedPreferenceHelper

/**
 * Activity for list of facts
 */
class FactsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFactListBinding
    private lateinit var viewModel: FactsListViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenceHelper.initPreference(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_fact_list)
        binding.factList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.factList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        //pull to refresh
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.loadFacts()
        }
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(FactsListViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel
    }

    /**
     * Snackbar to show when error fetching facts
     */
    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}
package com.arinspect.facts.ui.facts

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.view.View
import com.arinspect.facts.R
import com.arinspect.facts.base.BaseViewModel
import com.arinspect.facts.model.CanadaFacts
import com.arinspect.facts.model.FactDao
import com.arinspect.facts.model.RowsData
import com.arinspect.facts.network.FactsApi
import com.arinspect.facts.utils.SharedPreferenceHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class FactsListViewModel(private val factDao: FactDao) : BaseViewModel() {
    @Inject
    lateinit var factsApi: FactsApi
    val factsListAdapter: FactsListAdapter = FactsListAdapter()

    //variable to manage visibility of progress bar while fetching facts
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    //variable to show error message
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    //variable to update the app bar title once fetched from api call
    val appBarTitle: MutableLiveData<String> = MutableLiveData()

    //variable to handle pull to refreshing property
    var isRefreshing: ObservableBoolean = ObservableBoolean()
    val errorClickListener = View.OnClickListener { loadFacts() }

    private lateinit var subscription: Disposable

    init {
        loadFacts()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    /**
     * function to load facts, once it will fetch it from api call and then store in Room database
     * and will fetch it from there to make it work offline
     */
    fun loadFacts() {
        subscription = Observable.fromCallable { factDao.all }
                .concatMap { dbFactsList ->
                    if (dbFactsList.isEmpty())
                        factsApi.getFacts().concatMap { dbFactsList ->
                            factDao.insertAll(*dbFactsList.rows!!.toTypedArray())
                            Observable.just(dbFactsList)
                        }
                    else
                        Observable.just(dbFactsList)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveFactListStart() }
                .doOnTerminate { onRetrieveFactListFinish() }
                .subscribe(
                        { result -> onRetrieveFactListSuccess(getResult(result)) },
                        { onRetrieveFactListError() }
                )
    }

    /**
     * Method to manage data coming from api call or database
     */
    private fun getResult(result: Any): List<RowsData> {
        if (result is CanadaFacts) {
            SharedPreferenceHelper.storeTitle(if (result.title != null) result.title else "About Canada")
            return result.rows!!
        } else if (result is List<*>) {
            return result as List<RowsData>
        } else {
            return ArrayList()
        }
    }

    /**
     * Method to show progress bar when fetching data
     */
    private fun onRetrieveFactListStart() {
        loadingVisibility.value = View.VISIBLE
        isRefreshing.set(true)
        errorMessage.value = null
    }

    /**
     * Method to show once data fetched.
     */
    private fun onRetrieveFactListFinish() {
        loadingVisibility.value = View.GONE
        isRefreshing.set(false)
    }

    /**
     * Method to update list once data is fetched from api call or database
     */
    private fun onRetrieveFactListSuccess(factList: List<RowsData>) {
        appBarTitle.value = SharedPreferenceHelper.getTitle()
        factsListAdapter.updateFactList(factList)
    }

    /**
     * Method to show error when error fetching data
     */
    private fun onRetrieveFactListError() {
        errorMessage.value = R.string.fact_error
        loadingVisibility.value = View.GONE
        isRefreshing.set(false)
    }

}
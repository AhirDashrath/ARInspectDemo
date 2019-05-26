package com.arinspect.facts.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import com.arinspect.facts.model.database.AppDatabase
import com.arinspect.facts.ui.facts.FactsListViewModel

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FactsListViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "facts").build()
            @Suppress("UNCHECKED_CAST")
            return FactsListViewModel(db.factDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}
package com.arinspect.facts.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface FactDao {
    @get:Query("SELECT * FROM rowsdata")
    val all: List<RowsData>

    @Insert
    fun insertAll(vararg users: RowsData)
}
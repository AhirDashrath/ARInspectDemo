package com.arinspect.facts.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.arinspect.facts.model.FactDao
import com.arinspect.facts.model.RowsData

@Database(entities = [RowsData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun factDao(): FactDao
}
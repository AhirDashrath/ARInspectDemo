package com.arinspect.facts.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
data class RowsData(
        @PrimaryKey(autoGenerate = true)
        val id: Int?,
        val title: String? = "",
        val description: String? = "No Description",
        val imageHref: String? = ""
)
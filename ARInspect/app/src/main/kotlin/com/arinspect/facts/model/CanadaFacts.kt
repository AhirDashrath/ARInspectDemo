package com.arinspect.facts.model



data class CanadaFacts(
        val title:String?= "title",
        val rows: List<RowsData>?= ArrayList()
)
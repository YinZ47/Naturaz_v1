package com.naturaz.bd.domain.model

data class Currency(
    val code: String,
    val symbol: String,
    val localeTag: String
) {
    companion object {
        val BDT = Currency(code = "BDT", symbol = "\u09F3", localeTag = "bn_BD")
    }
}

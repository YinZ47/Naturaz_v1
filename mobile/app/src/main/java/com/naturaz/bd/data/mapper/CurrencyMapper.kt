package com.naturaz.bd.data.mapper

import com.naturaz.bd.domain.model.Currency

fun String.toCurrency(): Currency = when (uppercase()) {
    "BDT" -> Currency.BDT
    else -> Currency(code = this, symbol = "\u09F3", localeTag = "bn_BD")
}

fun Currency.toCode(): String = code

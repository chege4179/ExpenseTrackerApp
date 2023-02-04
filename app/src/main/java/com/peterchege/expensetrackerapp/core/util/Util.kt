package com.peterchege.expensetrackerapp.core.util



fun isNumeric(toCheck: String): Boolean {
    return toCheck.all { char -> char.isDigit() }
}
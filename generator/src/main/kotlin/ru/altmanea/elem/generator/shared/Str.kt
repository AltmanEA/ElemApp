package ru.altmanea.elem.generator.shared

import java.util.*

fun ufl(string: String) =
    string.substring(0, 1).uppercase(Locale.getDefault()) + string.substring(1)

fun lfl(string: String) =
    string.substring(0, 1).lowercase(Locale.getDefault()) + string.substring(1)
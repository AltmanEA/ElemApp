package ru.altmanea.elem.generator.utils

import java.util.*

fun cfl(string: String) =
    string.substring(0, 1).uppercase(Locale.getDefault()) + string.substring(1)
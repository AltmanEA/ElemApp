package ru.altmanea.elem.generator.shared

import java.util.*

val String.upperFirstLetter
    get() = this.substring(0, 1).uppercase(Locale.getDefault()) + this.substring(1)

val String.lowerFirstLetter
    get() = this.substring(0, 1).lowercase(Locale.getDefault()) + this.substring(1)
package ru.altmanea.elem.generator

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class ElemProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment) =
        ElemProcessor(
            environment.options,
            environment.logger,
            environment.codeGenerator
        )
}
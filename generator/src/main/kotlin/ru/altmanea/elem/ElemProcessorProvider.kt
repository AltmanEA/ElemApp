package ru.altmanea.elem

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import ru.altmanea.elem.ElemProcessor

class ElemProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment) =
        ElemProcessor(
            environment.options,
            environment.logger,
            environment.codeGenerator
        )
}
package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.poet.block

private val respondHtml = MemberName("io.ktor.server.html", "respondHtml")
private val htmlHead = MemberName("kotlinx.html", "head")
private val htmlMeta = MemberName("kotlinx.html", "meta")
private val htmlTitle = MemberName("kotlinx.html", "title")
private val htmlBody = MemberName("kotlinx.html", "body")
private val htmlScript = MemberName("kotlinx.html", "script")
private val staticFun = MemberName("io.ktor.server.http.content", "static")
private val resourceFun = MemberName("io.ktor.server.http.content", "resource")

fun Generator.index(): FileSpec {
    val index = FunSpec.builder("index").run {
        receiver(Ktor.RouteClass)
        block("%M", Ktor.get) {
            block("%M.%M(%M.OK)", Ktor.callObject, respondHtml, Ktor.statusCodeClass) {
                block("%M", htmlHead) {
                    block("%M", htmlMeta) {
                        addStatement(" attributes += \"charset\" to \"UTF-8\"")
                    }
                    block("%M", htmlTitle) {
                        addStatement("+\"Web App Client\"")
                    }
                }
                block("%M", htmlBody) {
                    block("%M (\"text/javascript\", \"client.js\")", htmlScript) {
                    }
                }
            }
        }
        block("%M", staticFun){
            addStatement("%M(\"/client.js\",\"client.js\")", resourceFun)
            addStatement("%M(\"/client.js.map\",\"client.js.map\")", resourceFun)
        }
    }

    return FileSpec
        .builder(packageName, "index")
        .addFunction(index.build())
        .build()
}
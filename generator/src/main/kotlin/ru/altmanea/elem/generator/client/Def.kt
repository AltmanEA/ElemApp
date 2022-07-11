package ru.altmanea.elem.generator.client

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import ru.altmanea.elem.generator.config.ElemDescription


class Browser {
    companion object {
        val document = MemberName("kotlinx.browser", "document")
        val INPUT = MemberName("org.w3c.dom", "HTMLInputElement")
    }
}

class React {
    companion object {
        val FC = ClassName("react", "FC")
        val Props = ClassName("react", "Props")
        val create = MemberName("react", "create")
        val createRoot = MemberName("react.dom.client", "createRoot")

        val useRef = MemberName("react", "useRef")

        val ul = MemberName("react.dom.html.ReactHTML", "ul")
        val li = MemberName("react.dom.html.ReactHTML", "li")
        val div = MemberName("react.dom.html.ReactHTML", "div")
        val details = MemberName("react.dom.html.ReactHTML", "details")
        val summary = MemberName("react.dom.html.ReactHTML", "summary")
        val input = MemberName("react.dom.html.ReactHTML", "input")

        val NavLink = MemberName("react.router.dom", "NavLink")
        val HashRouter = ClassName("react.router.dom", "HashRouter")
    }
}

class ReactRouter {
    companion object {
        val Routes = ClassName("react.router", "Routes")
        val Route = ClassName("react.router", "Route")
    }
}

class ReactQuery {
    companion object {
        val QueryClientProvider = ClassName("react.query", "QueryClientProvider")
        val QueryClient = ClassName("react.query", "QueryClient")
        val useQuery = MemberName("react.query", "useQuery")
        val QueryKey = ClassName("react.query", "QueryKey")

    }
}
class JSLib {
    companion object {
        val fetch = MemberName("ru.altmanea.elem.jslib.kfetch", "fetchText")
    }
}

val ElemDescription.comp
    get() = "${this.name}Comp"

val ElemDescription.queryComp
    get() = "${this.name}Query"

//val ElemDescription.tableComp
//    get() = "${this.name}TableComp"
//
//val ElemDescription.tableProps
//    get() = "${this.name}TableProps"
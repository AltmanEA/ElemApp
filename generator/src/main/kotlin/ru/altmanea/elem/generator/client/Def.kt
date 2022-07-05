package ru.altmanea.elem.generator.client

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName

class React {
    companion object {
        val FC = ClassName("react", "FC")
        val Props = ClassName("react", "Props")

        val create = MemberName("react", "create")
        val createRoot = MemberName("react.dom.client", "createRoot")

        val ul = MemberName("react.dom.html.ReactHTML", "ul")
        val li = MemberName("react.dom.html.ReactHTML", "li")

        val NavLink = MemberName("react.router.dom", "NavLink")
        val HashRouter = MemberName("react.router.dom", "HashRouter")
    }
}

class Browser {
    companion object {
        val document = MemberName("kotlinx.browser", "document")
    }
}

class ReactQuery {
    companion object {
        val useQuery = MemberName("react.query", "useQuery")
        val QueryKey = ClassName("react.query", "QueryKey")

    }
}
class JSLib {
    companion object {
        val fetch = MemberName("ru.altmanea.elem.jslib.kfetch", "fetchText")
    }
}
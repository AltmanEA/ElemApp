import io.kotest.core.spec.style.StringSpec
import org.gradle.testkit.runner.GradleRunner
import java.io.File
import java.nio.file.Paths

class FunctionalPluginTest : StringSpec({
    val testDir = Paths.get("test")

    "elem generation" {
        GradleRunner.create()
            .withProjectDir(testDir.toFile())
            .withArguments("generation")
            .withPluginClasspath()
            .build()
    }
})
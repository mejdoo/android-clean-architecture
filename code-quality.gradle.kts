subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    afterEvaluate {
        fun configureExtension(name: String, vararg methods: Pair<String, Any>) {
            extensions.findByName(name)?.runCatching {
                val cls = javaClass
                methods.forEach { (method, value) ->
                    cls.getMethod(method, value::class.java).invoke(this, value)
                }
            }
        }

        configureExtension(
            "ktlint",
            "setAndroid" to true,
            "setIgnoreFailures" to false
        )

        configureExtension(
            "detekt",
            "setBuildUponDefaultConfig" to true,
            "setAllRules" to true,
            "setIgnoreFailures" to false,
            "setConfig" to files("$rootDir/detekt.yml")
        )
    }
}

tasks.register("codeQualityCheck") {
    dependsOn(
        subprojects.flatMap {
            listOf(
                it.tasks.named("ktlintCheck"),
                it.tasks.named("detekt")
            )
        }
    )
}

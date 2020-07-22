package com.github.jengelman.gradle.plugins.shadow

import com.github.jengelman.gradle.plugins.shadow.util.PluginSpecification

class ConfigurationCacheSpec extends PluginSpecification {

    def "supports configuration cache"() {
        when:
        runner.withArguments('--configuration-cache', 'shadowJar').build()
        def result = runner.withArguments('--configuration-cache', 'shadowJar').build()

        then:
        result.output.contains("Reusing configuration cache.")
    }
}

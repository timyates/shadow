package com.github.jengelman.gradle.plugins.shadow

import com.github.jengelman.gradle.plugins.shadow.util.PluginSpecification

class ConfigurationCacheSpec extends PluginSpecification {

    def "supports configuration cache"() {
        given:
        repo.module('shadow', 'a', '1.0')
                .insertFile('a.properties', 'a')
                .insertFile('a2.properties', 'a2')
                .publish()

        file('src/main/java/myapp/Main.java') << """
            package myapp;
            public class Main {
               public static void main(String[] args) {
                   System.out.println("TestApp: Hello World! (" + args[0] + ")");
               }
            }
        """.stripIndent()

        buildFile << """
            apply plugin: 'application'

            mainClassName = 'myapp.Main'
            
            dependencies {
               compile 'shadow:a:1.0'
            }
            
            runShadow {
               args 'foo'
            }
        """.stripIndent()

        settingsFile << "rootProject.name = 'myapp'"

        when:
        runner.withArguments('--configuration-cache', 'shadowJar').build()
        def result = runner.withArguments('--configuration-cache', 'shadowJar').build()

        then:
        result.output.contains("Reusing configuration cache.")
    }
}

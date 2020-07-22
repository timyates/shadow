package com.github.jengelman.gradle.plugins.shadow.internal

import groovy.util.logging.Slf4j
import org.gradle.api.Project
import org.gradle.api.artifacts.ResolvedDependency
import org.gradle.api.artifacts.result.ResolvedArtifactResult

@Slf4j
class DefaultDependencyFilter extends AbstractDependencyFilter {

    DefaultDependencyFilter(Project project) {
        super(project)
    }

    @Override
    protected void resolve(Set<ResolvedDependency> dependencies,
                           Set<ResolvedDependency> includedDependencies,
                           Set<ResolvedDependency> excludedDependencies) {
        dependencies.each {
            if (isIncluded(it) ? includedDependencies.add(it) : excludedDependencies.add(it)) {
                resolve(it.children, includedDependencies, excludedDependencies)
            }
        }
    }

    @Override
    protected void resolveArtifacts(Set<ResolvedArtifactResult> dependencies,
                                    Set<ResolvedArtifactResult> includedDependencies,
                                    Set<ResolvedArtifactResult> excludedDependencies) {
        dependencies.each {
            if (isIncluded(it)) {
                includedDependencies.add(it)
            } else {
                excludedDependencies.add(it)
            }
        }
    }
}

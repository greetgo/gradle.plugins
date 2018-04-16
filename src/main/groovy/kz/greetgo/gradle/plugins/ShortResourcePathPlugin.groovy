package kz.greetgo.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class ShortResourcePathPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    project.pluginManager.apply('java')

    project.compileJava.options.encoding = 'UTF-8'
    project.compileTestJava.options.encoding = 'UTF-8'

    project.sourceSets.main.resources.srcDirs = ["src_resources"]
    project.sourceSets.test.resources.srcDirs = ["test_resources"]
  }
}

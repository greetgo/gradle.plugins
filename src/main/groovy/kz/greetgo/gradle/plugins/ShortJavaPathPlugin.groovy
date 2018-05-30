package kz.greetgo.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class ShortJavaPathPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {

    project.pluginManager.apply('java')
    project.pluginManager.apply('maven')

    project.compileJava.options.encoding = 'UTF-8'
    project.compileTestJava.options.encoding = 'UTF-8'

    project.sourceSets.main.java.srcDirs = ["src"]
    project.sourceSets.test.java.srcDirs = ["test_src"]
    project.sourceSets.main.resources.srcDirs = ["src_resources"]
    project.sourceSets.test.resources.srcDirs = ["test_resources"]

    project.sourceSets.main.resources {
      srcDirs += project.sourceSets.main.java.srcDirs
      exclude '**/*.java'
    }
    project.sourceSets.test.resources {
      srcDirs += project.sourceSets.test.java.srcDirs
      exclude '**/*.java'
    }

  }
}

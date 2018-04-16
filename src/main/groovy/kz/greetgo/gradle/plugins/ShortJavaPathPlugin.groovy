package kz.greetgo.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class ShortJavaPathPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {

    project.pluginManager.apply('java')
    project.pluginManager.apply('maven')
    project.pluginManager.apply(ShortResourcePathPlugin)

    project.sourceSets.main.java.srcDirs = ["src"]
    project.sourceSets.test.java.srcDirs = ["test_src"]

    project.processResources {
      with project.copySpec {
        from "src"
        exclude "**/*.java"
      }
    }
    project.processTestResources {
      with project.copySpec {
        from "test_src"
        exclude "**/*.java"
      }
    }
  }
}

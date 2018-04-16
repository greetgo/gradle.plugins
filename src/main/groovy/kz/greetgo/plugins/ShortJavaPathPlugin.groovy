package kz.greetgo.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class ShortJavaPathPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {

    project.pluginManager.apply(ShortResourcePathPlugin)
    project.pluginManager.apply('java')

    project.sourceSets.main.java.srcDirs = ["src"]
    project.sourceSets.test.java.srcDirs = ["test_src"]

    project.processResources {
      with copySpec {
        from "src"
        exclude "**/*.java"
      }
    }
    project.processTestResources {
      with copySpec {
        from "test_src"
        exclude "**/*.java"
      }
    }
  }
}

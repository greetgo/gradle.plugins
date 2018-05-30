package kz.greetgo.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class ConfigureIdeaPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {

    project.pluginManager.apply('idea')

    project.idea.module.inheritOutputDirs = false
    def buildDir = project.getBuildDir().getAbsolutePath()
    project.idea.module.outputDir = project.file(buildDir + "/classes/main/")
    project.idea.module.testOutputDir = project.file(buildDir + "/classes/test/")
  }
}

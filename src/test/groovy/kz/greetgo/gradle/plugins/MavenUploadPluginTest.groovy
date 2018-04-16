package kz.greetgo.gradle.plugins

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class MavenUploadPluginTest extends GroovyTestCase {
  void testName() {
    Project project = ProjectBuilder.builder().build()

    project.pluginManager.apply(MavenUploadPlugin.class)

//    def uploadToMavenCentral = project.tasks.uploadToMavenCentral
//    println "uploadToMavenCentral = $uploadToMavenCentral"
  }
}

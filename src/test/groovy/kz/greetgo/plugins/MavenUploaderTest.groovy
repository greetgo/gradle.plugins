package kz.greetgo.plugins

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class MavenUploaderTest extends GroovyTestCase {
  void testName() {
    Project project = ProjectBuilder.builder().build()

    project.pluginManager.apply(MavenUploader.class)

    def uploadToMaven = project.tasks.uploadToMaven
    println "uploadToMaven = $uploadToMaven"
  }
}

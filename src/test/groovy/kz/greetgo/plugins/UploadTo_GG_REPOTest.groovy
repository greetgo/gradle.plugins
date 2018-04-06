package kz.greetgo.plugins

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class UploadTo_GG_REPOTest extends GroovyTestCase {

  void test_apply() {
    Project project = ProjectBuilder.builder().build()

    project.pluginManager.apply(UploadTo_GG_REPO.class)

    def uploadArchives = project.tasks.uploadArchives
    println 'uploadArchives = ' + uploadArchives
  }
}

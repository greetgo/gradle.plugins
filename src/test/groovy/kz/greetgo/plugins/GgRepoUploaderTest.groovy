package kz.greetgo.plugins

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class GgRepoUploaderTest extends GroovyTestCase {

  void test_apply() {
    Project project = ProjectBuilder.builder().build()

    project.pluginManager.apply(GgRepoUploader.class)

    def uploadToGgRepo = project.tasks.uploadToGgRepo
    println "uploadToGgRepo = ${uploadToGgRepo}"

//    def uploadArchives = project.tasks.uploadArchives
//    println 'uploadArchives = ' + uploadArchives
  }
}

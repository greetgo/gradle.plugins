package kz.greetgo.plugins

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class GgRepoUploadPluginTest extends GroovyTestCase {

  void test_apply() {
    Project project = ProjectBuilder.builder().build()

    project.pluginManager.apply(GgRepoUploadPlugin.class)

    def uploadToGgRepo = project.tasks.uploadToGgRepo
    println "uploadToGgRepo = ${uploadToGgRepo}"

//    def uploadArchives = project.tasks.uploadArchives
//    println 'uploadArchives = ' + uploadArchives
  }
}

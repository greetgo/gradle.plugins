package kz.greetgo.gradle.plugins

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class SignGreetgoPluginTest extends GroovyTestCase {
  void testName() {
    Project project = ProjectBuilder.builder().build()

    project.tasks.create("asd") {
      doLast {
        println("Worked task asd")
      }
    }

    project.pluginManager.apply(SignGreetgoPlugin.class)

//    def uploadToMaven = project.tasks.uploadToMaven
//    println "uploadToMaven = $uploadToMaven"

    println "project.tasks.size() = " + project.tasks.size()

    project.tasks.forEach {
      println it
    }


  }
}

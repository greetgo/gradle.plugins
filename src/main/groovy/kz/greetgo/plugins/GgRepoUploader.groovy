package kz.greetgo.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Upload
import org.gradle.api.tasks.bundling.Jar

class GgRepoUploader implements Plugin<Project> {

  void apply(Project project) {

    if (Env.ggRepoUrl() == null) {
      MessageUtil.printNoRepoError()
      return
    }

    project.pluginManager.apply("java")
    project.pluginManager.apply("maven")

    def sourcesJar = project.tasks.findByName("sourcesJar")
    if (sourcesJar == null) {
      sourcesJar = project.tasks.create("sourcesJar", Jar.class) {
        group = "documentation"
        from project.sourceSets.main.allSource
        classifier = 'sources'
      }
    }

    def javadocJar = project.tasks.findByName("javadocJar")
    if (javadocJar == null) {
      javadocJar = project.tasks.create("javadocJar", Jar.class) {
        group = "documentation"
        from project.tasks.javadoc
        classifier = 'javadoc'
      }
    }

    project.artifacts {
      archives javadocJar, sourcesJar
    }

    project.tasks.create("uploadToGgRepo", Upload) {
      group 'upload'

      configuration = project.configurations.archives

      repositories.mavenDeployer {

        repository(url: Env.ggRepoUrl()) {
          if (Env.ggRepoUsername() != null) {
            authentication(userName: Env.ggRepoUsername(), password: Env.ggRepoPassword())
          }
        }
        pom.project {
          name project.name
          packaging 'jar'
        }
      }
    }

  }
}

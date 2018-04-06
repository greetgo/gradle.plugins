package kz.greetgo.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar

class UploadTo_GG_REPO implements Plugin<Project> {
  @Override
  void apply(Project project) {

    project.pluginManager.apply("java")
    project.pluginManager.apply("maven")

    def sourcesJar = project.tasks.create("sourcesJar", Jar.class) {
      group = "documentation"
      from project.sourceSets.main.allSource
      classifier = 'sources'
    }

    def javadocJar = project.tasks.create("javadocJar", Jar.class) {
      group = "documentation"
      from project.tasks.javadoc
      classifier = 'javadoc'
    }

    project.artifacts {
      archives javadocJar, sourcesJar
    }

    project.uploadArchives {
      repositories.mavenDeployer {
        configuration = project.configurations.archives
        repository(url: "http://localhost:8080/repository/internal/") {
          authentication(userName: 'admin', password: 'asd1')
        }

        pom.project {
          name project.name
          packaging 'jar'
        }
      }
    }
  }
}

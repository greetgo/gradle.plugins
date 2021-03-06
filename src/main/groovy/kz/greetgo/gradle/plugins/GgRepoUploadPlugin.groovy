package kz.greetgo.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Upload

class GgRepoUploadPlugin implements Plugin<Project> {

  void apply(Project project) {

    if (Env.ggRepoUrl() == null) {
      LocalUtil.printNoRepoError()
      return
    }

    LocalUtil.registerSourcesJavadocJarTasks(project)

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

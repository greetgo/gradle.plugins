package kz.greetgo.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.plugins.signing.Sign

class SignGreetgoPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    if (false
      || Env.signGpgKeyId() == null
      || Env.signGpgKeyPassword() == null
      || Env.signGpgKeyLocation() == null
    ) {
      LocalUtil.printNoSignEnv()
      return
    }

    project.pluginManager.apply("signing")

    project.gradle.taskGraph.whenReady { taskGraph ->
      if (taskGraph.allTasks.any { it instanceof Sign }) {
        project.gradle.allprojects { ext."signing.keyId" = Env.signGpgKeyId() }
        project.gradle.allprojects { ext."signing.secretKeyRingFile" = Env.signGpgKeyLocation() }
        project.gradle.allprojects { ext."signing.password" = Env.signGpgKeyPassword() }
      }
    }

    project.signing {
      sign project.configurations.archives
    }

  }
}

package kz.greetgo.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class MavenUploader implements Plugin<Project> {
  @Override
  void apply(Project project) {

    if (false
      || Env.sonatypeAccountId() == null
      || Env.sonatypeAccountPassword() == null
    ) {
      LocalUtil.printNoSonatypeCredentials()
      return
    }

    LocalUtil.registerSourcesJavadocJarTasks(project)
  }
}

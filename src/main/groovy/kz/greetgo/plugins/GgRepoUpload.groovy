package kz.greetgo.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Upload
import org.gradle.api.tasks.bundling.Jar

import java.util.stream.Collectors

class GgRepoUpload implements Plugin<Project> {

  void apply(Project project) {

    if (Env.ggRepoUrl() == null) {
      printNoRepoError()
      return
    }

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

  private static void printNoRepoError() {
    def list = new ArrayList<String>()

    list += "You do not define environment variable ${Env.GG_REPO_URL}"
    list += "You can define it in file ~/.pam_environment"
    list += "Also, you can define environment variables ${Env.GG_REPO_USERNAME}, ${Env.GG_REPO_PASSWORD} to authenticate repository access."
    list += ""
//    list += "Another way: write in build.gradle:"
//    list += ""
//    list += "     ggRepoUpload {"
//    //noinspection SpellCheckingInspection
//    list += "       url = 'http://HOST:8080/repository/internal/' // for archiva"
//    list += "       username = 'pushkin' // Не знаешь что говорить, говори: \"Пушкин\". (С) Что? Где? Когда?"
//    list += "       password = 'secret'"
//    list += "     }"
//    list += ""

    def left = "*** "

    def s = "*" * (2 * left.length() + list.stream().mapToInt { it -> it.length() }.max().orElseThrow())

    def message = list.stream().map { it -> left + it }.collect(Collectors.joining("\n", s + "\n", "\n" + s))

    System.err.println(message)
  }
}

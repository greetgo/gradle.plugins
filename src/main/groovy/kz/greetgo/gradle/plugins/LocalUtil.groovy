package kz.greetgo.gradle.plugins

import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar

import java.util.stream.Collectors

import static kz.greetgo.gradle.plugins.MavenUploadPlugin.UPLOAD_TASK_NAME

class LocalUtil {
  static void printNoRepoError() {
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

    printListToStderr(list)
  }

  private static void printListToStderr(List<String> list) {
    def left = "*** "

    def s = "*" * (2 * left.length() + list.stream().mapToInt { it -> it.length() }.max().orElseThrow())

    def message = list.stream().map { it -> left + it }.collect(Collectors.joining("\n", s + "\n", "\n" + s))

    System.err.println(message)
  }

  static void registerSourcesJavadocJarTasks(Project project) {
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
  }

  static def printNoSignEnv() {
    def list = new ArrayList<String>()

    list += "You ask to sign artifacts, but no data to sign"
    list += "Any of needed environment variables do not define"
    list += ""
    list += "You need define the following environment variables:"
    list += ""
    list += "  ${Env.LIB_SIGN_GPG_KEY_ID}       - GPG key sign id"
    list += "  ${Env.LIB_SIGN_GPG_KEY_PASSWORD} - GPG key sign password"
    list += "  ${Env.LIB_SIGN_GPG_KEY_LOCATION} - Location of file with GPG key"
    list += ""
    list += "You can define its in file ~/.pam_environment"

    printListToStderr(list)
  }

  static def printNoSonatypeCredentials() {
    def list = new ArrayList<String>()

    list += "You ask to upload artifacts to maven, but no account data"
    list += "Any of needed environment variables do not define"
    list += ""
    list += "You need define the following environment variables:"
    list += ""
    list += "  ${Env.LIB_SONATYPE_ACCOUNT_HASH_ID}       - Your Sonatype account id"
    list += "  ${Env.LIB_SONATYPE_ACCOUNT_HASH_PASSWORD} - Your Sonatype account password"
    list += ""
    list += "You can define its in file ~/.pam_environment"

    printListToStderr(list)
  }

  static def printExtUploadToMavenCentral() {
    def list = new ArrayList<String>()

    list += "You need to define block '$UPLOAD_TASK_NAME' in build.gradle"
    list += ""
    list += "For example (short variant):"
    list += ""
    list += "  $UPLOAD_TASK_NAME {"
    list += "    description = 'Description of this module: it will appear in MavenCentral'"
    list += "    url         = 'https://github.com/greetgo/test_project'"
    list += "  }"
    list += ""
    list += "For example (middle variant):"
    list += ""
    list += "  $UPLOAD_TASK_NAME {"
    list += "    description = 'Description of this module: it will appear in MavenCentral'"
    list += "    url         = 'https://tech.greetgo.kz/test_project.php'"
    list += "    scm {"
    list += "      url = 'https://github.com/greetgo/test_project'"
    list += "    }"
    list += "    developer {"
    list += "      id    = 'devId'"
    list += "      name  = 'devName'"
    list += "      email = 'devEmail@host.kz'"
    list += "    }"
    list += "  }"
    list += ""
    list += "For example (full variant):"
    list += ""
    list += "  $UPLOAD_TASK_NAME {"
    list += "    description = 'Description of this module: it will appear in MavenCentral'"
    list += "    url         = 'https://tech.greetgo.kz/test_project.php'"
    list += "    scm {"
    list += "      url           = 'https://github.com/greetgo/test_project'"
    list += "      connection    = 'scm:git:https://github.com/greetgo/test_project'"
    list += "      devConnection = 'scm:git:https://github.com/greetgo/test_project'"
    list += "    }"
    list += "    developer {"
    list += "      id    = 'devId'"
    list += "      name  = 'devName'"
    list += "      email = 'devEmail@host.kz'"
    list += "    }"
    list += "    developer {"
    list += "      id    = 'dev2Id'"
    list += "      name  = 'dev2Name'"
    list += "      email = 'dev2Email@host.kz'"
    list += "    }"
    list += "    //or more developers"
    list += "  }"
    list += ""

    printListToStderr(list)
  }
}
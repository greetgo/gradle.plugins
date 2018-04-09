package kz.greetgo.plugins

import java.util.stream.Collectors

class MessageUtil {
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

    def left = "*** "

    def s = "*" * (2 * left.length() + list.stream().mapToInt { it -> it.length() }.max().orElseThrow())

    def message = list.stream().map { it -> left + it }.collect(Collectors.joining("\n", s + "\n", "\n" + s))

    System.err.println(message)
  }
}
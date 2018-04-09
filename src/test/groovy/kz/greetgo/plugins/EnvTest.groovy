package kz.greetgo.plugins

class EnvTest extends GroovyTestCase {
  void test_ggRepo() {
    println "Env.ggRepoUrl()      = " + Env.ggRepoUrl()
    println "Env.ggRepoUsername() = " + Env.ggRepoUsername()
    println "Env.ggRepoPassword() = " + Env.ggRepoPassword()
  }
}

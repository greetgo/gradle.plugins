package kz.greetgo.gradle.plugins.model

import org.gradle.util.ConfigureUtil

class MavenUploadPluginExtension {
  boolean activateTestPlugin = false;
  String description
  private String url
  private ScmModel scm

  List<DeveloperModel> developers = new ArrayList<>()

  ScmModel getScm() {
    if (this.scm == null) this.scm = new ScmModel();
    return this.scm
  }

  @SuppressWarnings("GroovyUnusedDeclaration")
  void setScm(ScmModel value) {
    this.scm = value;
  }

  @SuppressWarnings("GroovyUnusedDeclaration")
  void scm(Closure closure) {
    ConfigureUtil.configure(closure, getScm())
  }

  @SuppressWarnings("GroovyUnusedDeclaration")
  String getUrl() {
    return url
  }

  @SuppressWarnings("GroovyUnusedDeclaration")
  void setUrl(String url) {
    this.url = url
    def x = getScm()
    if (x.url == null) x.url = url
    if (x.connection == null) x.connection = 'scm:git:' + url
    if (x.devConnection == null) x.devConnection = 'scm:git:' + url
  }

  @SuppressWarnings("GroovyUnusedDeclaration")
  void developer(Closure closure) {
    def d = new DeveloperModel()
    developers.add(d)
    ConfigureUtil.configure(closure, d)
  }
}

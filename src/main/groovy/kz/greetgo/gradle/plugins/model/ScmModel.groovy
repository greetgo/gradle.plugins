package kz.greetgo.gradle.plugins.model

class ScmModel {
  private String url
  private String connection
  private String devConnection

  String getUrl() {
    return url
  }

  @SuppressWarnings("GroovyUnusedDeclaration")
  void setUrl(String url) {
    this.url = url
    if (connection == null && url != null) setConnection("scm:git:" + url)
  }

  String getConnection() {
    return connection
  }

  void setConnection(String connection) {
    this.connection = connection
    if (devConnection == null && connection != null) setDevConnection(connection)
  }

  String getDevConnection() {
    return devConnection
  }

  void setDevConnection(String devConnection) {
    this.devConnection = devConnection
  }

  @Override
  String toString() {
    return "ScmModel{" +
      "url='" + url + '\'' +
      ", connection='" + connection + '\'' +
      ", devConnection='" + devConnection + '\'' +
      '}';
  }
}

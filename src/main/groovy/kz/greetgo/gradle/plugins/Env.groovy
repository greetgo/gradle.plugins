package kz.greetgo.gradle.plugins

class Env {
  static String getEnv(String name) {
    String value = System.getenv(name);
    if (value == null) return null
    value = value.trim()
    return value.length() > 0 ? value : null
  }

  static final String GG_REPO_URL = 'GG_REPO_URL'
  static final String GG_REPO = 'GG_REPO'

  static String ggRepoUrl() {
    def url1 = getEnv(GG_REPO_URL)
    if (url1 != null) return url1
    return getEnv(GG_REPO)
  }

  static final String GG_REPO_USERNAME = 'GG_REPO_USERNAME'

  static String ggRepoUsername() {
    return getEnv(GG_REPO_USERNAME)
  }

  static final String GG_REPO_PASSWORD = 'GG_REPO_PASSWORD'

  static String ggRepoPassword() {
    return getEnv(GG_REPO_PASSWORD)
  }

  static final String LIB_SIGN_GPG_KEY_ID = 'LIB_SIGN_GPG_KEY_ID'

  static String signGpgKeyId() {
    return getEnv(LIB_SIGN_GPG_KEY_ID);
  }

  static final String LIB_SIGN_GPG_KEY_PASSWORD = 'LIB_SIGN_GPG_KEY_PASSWORD'

  static String signGpgKeyPassword() {
    return getEnv(LIB_SIGN_GPG_KEY_PASSWORD);
  }

  static final String LIB_SIGN_GPG_KEY_LOCATION = 'LIB_SIGN_GPG_KEY_LOCATION'

  static String signGpgKeyLocation() {
    return getEnv(LIB_SIGN_GPG_KEY_LOCATION);
  }

  static final String LIB_SONATYPE_ACCOUNT_HASH_ID = 'LIB_SONATYPE_ACCOUNT_HASH_ID'

  static String sonatypeAccountId() {
    return getEnv(LIB_SONATYPE_ACCOUNT_HASH_ID);
  }

  static final String LIB_SONATYPE_ACCOUNT_HASH_PASSWORD = 'LIB_SONATYPE_ACCOUNT_HASH_PASSWORD'

  static String sonatypeAccountPassword() {
    return getEnv(LIB_SONATYPE_ACCOUNT_HASH_PASSWORD);
  }
}

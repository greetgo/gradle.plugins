package kz.greetgo.plugins

class Env {
  static String getEnv(String name) {
    String value = System.getenv(name);
    if (value == null) return null;
    value = value.trim();
    return value.length() > 0 ? value : null;
  }

  static String ggRepo() {
    return getEnv("GG_REPO");
  }

  static String signGpgKeyId() {
    return getEnv("LIB_SIGN_GPG_KEY_ID");
  }

  static String signGpgKeyPassword() {
    return getEnv("LIB_SIGN_GPG_KEY_PASSWORD");
  }

  static String signGpgKeyLocation() {
    return getEnv("LIB_SIGN_GPG_KEY_LOCATION");
  }

  static String sonatypeAccountId() {
    return getEnv("LIB_SONATYPE_ACCOUNT_HASH_ID");
  }

  static String sonatypeAccountPassword() {
    return getEnv("LIB_SONATYPE_ACCOUNT_HASH_PASSWORD");
  }
}

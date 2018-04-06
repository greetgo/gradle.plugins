package kz.greetgo.plugins;

public class EnvJava {

  public static String getEnv(String name) {
    String value = System.getenv(name);
    if (value == null) return null;
    value = value.trim();
    return value.length() > 0 ? value : null;
  }

  public static String ggRepo() {
    return getEnv("GG_REPO");
  }

  public static String signGpgKeyId() {
    return getEnv("LIB_SIGN_GPG_KEY_ID");
  }

  public static String signGpgKeyPassword() {
    return getEnv("LIB_SIGN_GPG_KEY_PASSWORD");
  }

  public static String signGpgKeyLocation() {
    return getEnv("LIB_SIGN_GPG_KEY_LOCATION");
  }

  public static String sonatypeAccountId() {
    return getEnv("LIB_SONATYPE_ACCOUNT_HASH_ID");
  }

  public static String sonatypeAccountPassword() {
    return getEnv("LIB_SONATYPE_ACCOUNT_HASH_PASSWORD");
  }
}

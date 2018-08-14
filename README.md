# gradle.plugins

Plugins using in greetgo!

### Plugin to "shorting" source paths

Includes with:

```groovy
plugins {
  id "kz.greetgo.short-paths" version "0.0.5"
}
```

Or

```groovy
buildscript {
  repositories {
    mavenLocal()
    if (System.getenv('GG_REPO')?.trim()) maven { url System.getenv('GG_REPO') }
    mavenCentral()
  }

  dependencies {
    classpath "kz.greetgo.plugins:greetgo.gradle.plugins:0.0.5"
  }
}

apply plugin: kz.greetgo.gradle.plugins.ShortJavaPathPlugin
```

(look: https://plugins.gradle.org/plugin/kz.greetgo.short-paths)

Makes following paths:

* `PROJECT_ROOT/src/` - Java sources (production) + resources for production (*.java excludes)
* `PROJECT_ROOT/src_resources/` - resources for production
* `PROJECT_ROOT/test_src/` - Java sources (tests) + resources for tests (*.java excludes)
* `PROJECT_ROOT/test_resources/` - resources for tests

Note that you can store resources in `src` and `test_src`.

Note standard maven paths moves:

* `PROJECT_ROOT/src/main/java/` ⟶ `PROJECT_ROOT/src/`
* `PROJECT_ROOT/src/main/resources/` ⟶ `PROJECT_ROOT/src_resources/`
* `PROJECT_ROOT/src/test/java/` ⟶ `PROJECT_ROOT/test_src/`
* `PROJECT_ROOT/src/test/resources/` ⟶ `PROJECT_ROOT/test_resources/`

And paths `PROJECT_ROOT/src/main/java/`, `PROJECT_ROOT/src/test/java/` cannot contain resources, but
new paths: `PROJECT_ROOT/src/`, `PROJECT_ROOT/test_src/` - can.

### Plugin uploads artifacts to greetgo! repository (GG_REPO)

Includes with:

```groovy
plugins {
  id "kz.greetgo.upload-to-gg-repo" version "0.0.5"
}
```

or

```groovy
buildscript {
  repositories {
    mavenLocal()
    if (System.getenv('GG_REPO')?.trim()) maven { url System.getenv('GG_REPO') }
    mavenCentral()
  }

  dependencies {
    classpath "kz.greetgo.plugins:greetgo.gradle.plugins:0.0.5"
  }
}

apply plugin: kz.greetgo.gradle.plugins.GgRepoUploadPlugin
```

(look: https://plugins.gradle.org/plugin/kz.greetgo.upload-to-gg-repo)

Adds task `uploadToGgRepo`, to upload artifacts to local greetgo! repository (GG_REPO)

Plugin needs environment variable:
```
GG_REPO=URL of repo
```

or

```
GG_REPO_URL=URL of repo
GG_REPO_USERNAME=username for repo
GG_REPO_PASSWORD=secret
```

### Plugin uploads to Maven Central Repository

Includes with:

```groovy
plugins {
  id "kz.greetgo.upload-to-maven" version "0.0.5"
}
```

or

```groovy
buildscript {
  repositories {
    mavenLocal()
    if (System.getenv('GG_REPO')?.trim()) maven { url System.getenv('GG_REPO') }
    mavenCentral()
  }

  dependencies {
    classpath "kz.greetgo.plugins:greetgo.gradle.plugins:0.0.5"
  }
}

apply plugin: kz.greetgo.gradle.plugins.MavenUploadPlugin
```

(look: https://plugins.gradle.org/plugin/kz.greetgo.upload-to-maven)

Adds task `uploadToMavenCentral`, to upload artifacts to Maven Central Repository

Plugin need parameters:

* Short variant:
```groovy
  uploadToMavenCentral {
    description = 'Description of this module: it will appear in MavenCentral'
    url         = 'https://github.com/greetgo/test_project'
  }
```
* Middle variant:
```groovy
  uploadToMavenCentral {
    description = 'Description of this module: it will appear in MavenCentral'
    url         = 'https://tech.greetgo.kz/test_project.php'
    scm {
      url = 'https://github.com/greetgo/test_project'
    }
    developer {
      id    = 'devId'
      name  = 'devName'
      email = 'devEmail@host.kz'
    }
  }
```
* Full variant:
```groovy
  uploadToMavenCentral {
    description = 'Description of this module: it will appear in MavenCentral'
    url         = 'https://tech.greetgo.kz/test_project.php'
    scm {
      url           = 'https://github.com/greetgo/test_project'
      connection    = 'scm:git:https://github.com/greetgo/test_project'
      devConnection = 'scm:git:https://github.com/greetgo/test_project'
    }
    developer {
      id    = 'devId'
      name  = 'devName'
      email = 'devEmail@host.kz'
    }
    developer {
      id    = 'dev2Id'
      name  = 'dev2Name'
      email = 'dev2Email@host.kz'
    }
    //or more developers
  }
```

Also plugin needs environments variables:
```
LIB_SIGN_GPG_KEY_ID=111
LIB_SIGN_GPG_KEY_PASSWORD=111
LIB_SIGN_GPG_KEY_LOCATION=/path/to/secring.gpg

LIB_SONATYPE_ACCOUNT_HASH_ID=account of https://oss.sonatype.org
LIB_SONATYPE_ACCOUNT_HASH_PASSWORD=secret password
```

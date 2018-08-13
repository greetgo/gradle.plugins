# gradle.plugins

Plugins using in greetgo!

### Плагин "укорочения" src-директорий

Подключается с помощью кода:
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

Делает следующие пути исходников:

* `src` - исходники для Java (продакшн) + ресурсы для продакшна (*.java исключаются)
* `src_resources` - ресурсы для продакшна
* `test_src` - исходники для Java (тесты) + ресурсы для тестов (*.java исключаются)
* `test_resources` - ресурсы для тестов

Обратите внимание, что в `src` и `test_src` можно ложить ресурсы.

### Плагин выгрузки в локальный репозиторий greetgo! (GG_REPO)

Подключается с помощью кода:
```groovy
buildscript {
  repositories {
    mavenLocal()
    if (System.getenv('GG_REPO')?.trim()) maven { url System.getenv('GG_REPO') }
    mavenCentral()
  }

  dependencies {
    classpath "kz.greetgo.plugins:greetgo.gradle.plugins:0.0.4"
  }
}

apply plugin: kz.greetgo.gradle.plugins.GgRepoUploadPlugin
```

Добавляет в проект таску `uploadToGgRepo`, которая выгружает в локальный репозиторий greetgo!

### Плагин выгрузки в центральный репозиторий Maven

Подключается с помощью кода:
```groovy
buildscript {
  repositories {
    mavenLocal()
    if (System.getenv('GG_REPO')?.trim()) maven { url System.getenv('GG_REPO') }
    mavenCentral()
  }

  dependencies {
    classpath "kz.greetgo.plugins:greetgo.gradle.plugins:0.0.4"
  }
}

apply plugin: kz.greetgo.gradle.plugins.MavenUploadPlugin
```

Добавляет в проект таску `uploadToMavenCentral`, которая выгружает в центральный репозиторий Maven.

Для работы плагина требуется заполнить параметры:

* Короткий вариант:
```groovy
  uploadToMavenCentral {
    description = 'Description of this module: it will appear in MavenCentral'
    url         = 'https://github.com/greetgo/test_project'
  }
```
* Средний вариант:
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
* Полный вариант:
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

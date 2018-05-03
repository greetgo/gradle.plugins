package kz.greetgo.gradle.plugins

import kz.greetgo.gradle.plugins.model.MavenUploadPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.maven.MavenDeployment
import org.gradle.api.tasks.Upload
import org.gradle.configuration.project.ProjectConfigurationActionContainer

import javax.inject.Inject

class MavenUploadPlugin implements Plugin<Project> {

  public static final String UPLOAD_TASK_NAME = "uploadToMavenCentral"

  private final ProjectConfigurationActionContainer configurationActionContainer

  @Inject
  MavenUploadPlugin(ProjectConfigurationActionContainer configurationActionContainer) {
    this.configurationActionContainer = configurationActionContainer
  }


  @Override
  void apply(Project project) {

    MavenUploadPluginExtension ext = project.extensions.create(UPLOAD_TASK_NAME, MavenUploadPluginExtension)

    if (false
      || Env.sonatypeAccountId() == null
      || Env.sonatypeAccountPassword() == null
    ) {
      LocalUtil.printNoSonatypeCredentials()
      return
    }

    LocalUtil.registerSourcesJavadocJarTasks(project)

    project.pluginManager.apply(SignGreetgoPlugin)

    configurationActionContainer.add {

      if (ext.description == null) {
        LocalUtil.printExtUploadToMavenCentral()
        throw new RuntimeException("uploadToMavenCentral.description == null")
      }
      if (ext.url == null) {
        LocalUtil.printExtUploadToMavenCentral()
        throw new RuntimeException("uploadToMavenCentral.url == null")
      }

      if (ext.activateTestPlugin) {
        project.tasks.create("testTask", DefaultTask) {
          doLast {
            println "MASSAGE[[Hello from test task]]"
          }
        }
        println "PARAM[[ext.description=$ext.description]]"
        println "PARAM[[ext.url=$ext.url]]"
        println "PARAM[[ext.scm.url=$ext.scm.url]]"
        println "PARAM[[ext.scm.connection=$ext.scm.connection]]"
        println "PARAM[[ext.scm.devConnection=$ext.scm.devConnection]]"
        println "PARAM[[ext.developers.size()=" + ext.developers.size() + "]]"
        def i = 0
        ext.developers.forEach { d ->
          println "PARAM[[developer${i}.id=${d.id}]]"
          println "PARAM[[developer${i}.name=${d.name}]]"
          println "PARAM[[developer${i}.email=${d.email}]]"
          i++
        }
      }

      project.tasks.create(UPLOAD_TASK_NAME, Upload.class) {
        group = "upload"
        configuration = project.configurations.archives

        repositories {
          mavenDeployer {
            beforeDeployment { MavenDeployment deployment -> project.signing.signPom(deployment) }

            repository(url: "https://oss.sonatype.org/service/local/staging/deploy/maven2/") {
              authentication(
                userName: Env.sonatypeAccountId(),
                password: Env.sonatypeAccountPassword()
              )
            }

            snapshotRepository(url: "https://oss.sonatype.org/content/repositories/snapshots/") {
              authentication(
                userName: Env.sonatypeAccountId(),
                password: Env.sonatypeAccountPassword()
              )
            }

            pom.project {
              name project.name
              packaging 'jar'

              description ext.description
              url ext.url

              scm {
                url ext.scm.url
                connection ext.scm.connection
                developerConnection ext.scm.devConnection
              }

              licenses {
                license {
                  name 'The Apache License, Version 2.0'
                  url 'http://www.apache.org/licenses/LICENSE-2.0.txt'
                }
              }

              developers {

                for (int i = 0; i < ext.developers.size(); i++) {
                  def d = ext.developers[i]
                  developer {
                    id d.id
                    name d.name
                    email d.email
                  }
                }

              }
            }
          }
        }
      }

    }
  }
}

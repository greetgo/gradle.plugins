package kz.greetgo.gradle.plugins

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Unroll

import static kz.greetgo.gradle.plugins.UsingGradleVersions.getUsingGradleVersions
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS
import static org.hamcrest.core.Is.is
import static org.hamcrest.core.IsNot.not
import static org.hamcrest.core.IsNull.nullValue
import static org.junit.Assert.assertThat

class MavenUploadPluginFunctionalTest extends Specification {
  @Rule
  final TemporaryFolder testProjectDir = new TemporaryFolder()
  File buildFile

  List<File> pluginClasspath

  def setup() {
    buildFile = testProjectDir.newFile('build.gradle')

    def pluginClasspathResource = getClass().classLoader.findResource("plugin-classpath.txt")
    if (pluginClasspathResource == null) {
      throw new IllegalStateException("Did not find plugin classpath resource, run `testClasses` build task.")
    }

    pluginClasspath = pluginClasspathResource.readLines().collect { new File(it) }
  }

  @Unroll
  def "test uploadToMavenCentral parameters (Short variant)"() {
    given:
    def classpathString = pluginClasspath
      .collect { it.absolutePath.replace('\\', '\\\\') } // escape backslashes in Windows paths
      .collect { "'$it'" }
      .join(", ")

    buildFile << """
      buildscript {
        dependencies {
          classpath files($classpathString)
        }
      }

      import kz.greetgo.gradle.plugins.MavenUploadPlugin

      apply plugin: MavenUploadPlugin

      uploadToMavenCentral {
        activateTestPlugin = true
        description = "test description"
        url = 'https://github.com/greetgo/test_project'
        developer {
          id = 'dev1'
          name = 'dev1 name'
          email = 'dev1 email'
        }
        developer {
          id = 'dev2'
          name = 'dev2 name'
          email = 'dev2 email'
        }
      }

    """

    when:
    BuildResult result = GradleRunner.create()
      .withGradleVersion(gradleVersion)
      .withProjectDir(testProjectDir.root)
      .withArguments('testTask')
      .withPluginClasspath(pluginClasspath)
      .build()

    then:
    println result.output
    result.output.contains('PARAM[[ext.description=test description]]')
    result.output.contains('PARAM[[ext.url=https://github.com/greetgo/test_project]]')
    result.output.contains('PARAM[[ext.scm.url=https://github.com/greetgo/test_project]]')
    result.output.contains('PARAM[[ext.scm.connection=scm:git:https://github.com/greetgo/test_project]]')
    result.output.contains('PARAM[[ext.scm.devConnection=scm:git:https://github.com/greetgo/test_project]]')
    result.output.contains('PARAM[[developer0.id=dev1]]')
    result.output.contains('PARAM[[developer0.name=dev1 name]]')
    result.output.contains('PARAM[[developer0.email=dev1 email]]')
    result.output.contains('PARAM[[developer1.id=dev2]]')
    result.output.contains('PARAM[[developer1.name=dev2 name]]')
    result.output.contains('PARAM[[developer1.email=dev2 email]]')
    result.output.contains('MASSAGE[[Hello from test task]]')
    result.task(":testTask").outcome == SUCCESS

    where:
    gradleVersion << usingGradleVersions
  }


  @Unroll
  def "view error of no uploadToMavenCentral"() {
    given:
    def classpathString = pluginClasspath
      .collect { it.absolutePath.replace('\\', '\\\\') } // escape backslashes in Windows paths
      .collect { "'$it'" }
      .join(", ")

    buildFile << """
      buildscript {
        dependencies {
          classpath files($classpathString)
        }
      }

      import kz.greetgo.gradle.plugins.MavenUploadPlugin

      apply plugin: MavenUploadPlugin

    """

    when:
    BuildResult result = null
    RuntimeException error = null
    boolean wentOk = false

    try {
      result = GradleRunner.create()
        .withGradleVersion(gradleVersion)
        .withProjectDir(testProjectDir.root)
        .withArguments('uploadToMavenCentral')
        .withPluginClasspath(pluginClasspath)
        .build()
      wentOk = true
    } catch (RuntimeException e) {
      error = e
    }

    then:
    assertThat wentOk, is(false)
    assertThat error, is(not(nullValue()))
    assertThat result, is(nullValue())
    error.message.contains("description = 'Description of this module: it will appear in MavenCentral'")

    where:
    gradleVersion << usingGradleVersions
  }
}

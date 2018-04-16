package kz.greetgo.plugins

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Unroll

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class ShortJavaPathPluginTest extends Specification {
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
  def "Compile class Example"() {
    given:
    buildFile << """
      buildscript {
        dependencies {
          classpath files($classpathString)
        }
      }
      
      apply plugin: kz.greetgo.plugins.ShortJavaPathPlugin
    """

    testProjectDir.newFile("src/asd/Example.java") << """
      package asd;
      public class Example {
        public void asd() {
          System.out.println("Hi")
        }
      }
    """

    when:
    def result = GradleRunner.create()
      .withGradleVersion(gradleVersion)
      .withProjectDir(testProjectDir.root)
      .withArguments('compileJava')
      .build()

    then:
    result.output.contains('Hello world!')
    result.task(":helloWorld").outcome == SUCCESS

    where:
    gradleVersion << ['4.5']
  }
}

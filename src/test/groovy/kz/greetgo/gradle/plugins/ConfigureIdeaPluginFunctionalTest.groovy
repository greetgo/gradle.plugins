package kz.greetgo.gradle.plugins

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Unroll

import static kz.greetgo.gradle.plugins.UsingGradleVersions.usingGradleVersions
import static org.fest.assertions.api.Assertions.assertThat
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class ConfigureIdeaPluginFunctionalTest extends Specification {
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

  def printTree(File file) {
    printTreeOffset file, 0
  }

  def printTreeOffset(File file, int offset) {
    String s = ""
    for (int i = 0; i < offset; i++) s += "  "

    if (file.isFile()) {
      println s + file.name + " (size " + file.length() + " bytes)"
    } else {
      println s + file.name + "/"
      file.listFiles().collect { subFile ->
        printTreeOffset subFile, offset + 1
      }
    }
  }

  @Unroll
  def "Test configure idea plugin"() {
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
      
      import kz.greetgo.gradle.plugins.ConfigureIdeaPlugin
      
      apply plugin: kz.greetgo.gradle.plugins.ConfigureIdeaPlugin
      apply plugin: kz.greetgo.gradle.plugins.ShortJavaPathPlugin
      apply plugin: 'java'
      apply plugin: 'maven'
    """

    testProjectDir.newFolder("src", "asd")
    testProjectDir.newFile("src/asd/Example.java") << """
      package asd;
      public class Example {
        public void asd() {
          System.out.println("Hi");
        }
      }
    """
    testProjectDir.newFile("src/asd/some_resource_from_src.txt") << """
      Hello World!!!
    """
    testProjectDir.newFolder("src_resources", "wow")
    testProjectDir.newFile("src_resources/wow/r2.txt") << """
      Hello World!!!
    """

    testProjectDir.newFolder("test_src", "dsa")
    testProjectDir.newFile("test_src/dsa/TestExample.java") << """
      package dsa;
      import asd.Example;
      
      public class TestExample {
        public void dsa() {
          Example e = new Example();
          e.asd();
          System.out.println("By test");
        }
      }
    """
    testProjectDir.newFile("test_src/dsa/r3.txt") << """
      Hello
    """
    testProjectDir.newFolder("test_resources", "dsa")
    testProjectDir.newFile("test_resources/dsa/r4.txt") << """
      Hello
    """

    when:
    def result = GradleRunner.create()
      .withGradleVersion(gradleVersion)
      .withProjectDir(testProjectDir.root)
      .withArguments('idea', 'testClasses')
      .build()

    then:
//    println '[[' + result.output + ']]'
    result.task(":idea").outcome == SUCCESS

//    printTree testProjectDir.getRoot()
//    result.tasks.collect { task ->
//      println "Executed task $task"
//    }

//    def p = ("ls -laF " + testProjectDir.getRoot().toPath()).execute()
//    def outputStream = new StringBuffer();
//    p.waitForProcessOutput(outputStream, System.err)
//    println outputStream

    boolean iwsFileExists = false
    boolean iprFileExists = false
    boolean imlFileExists = false

    for (File f : testProjectDir.getRoot().listFiles()) {
      if (f.getName().toLowerCase().endsWith(".iws")) iwsFileExists = true
      if (f.getName().toLowerCase().endsWith(".ipr")) iprFileExists = true
      if (f.getName().toLowerCase().endsWith(".iml")) imlFileExists = true
    }

    assertThat(iwsFileExists).isTrue()
    assertThat(iprFileExists).isTrue()
    assertThat(imlFileExists).isTrue()

    result.output.contains('BUILD SUCCESSFUL')

    where:
    gradleVersion << usingGradleVersions
  }
}

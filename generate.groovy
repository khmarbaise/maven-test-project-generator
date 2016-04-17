#!/usr/bin/env groovy

class PomFile {
  def pluginList = [
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-clean-plugin',
      'version' : '3.0.0',
    ],
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-resources-plugin',
      'version' : '2.7',
    ],
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-compiler-plugin',
      'version' : '3.5.1',
    ],
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-jar-plugin',
      'version' : '2.6',
    ],
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-install-plugin',
      'version' : '2.5.2',
    ],
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-deploy-plugin',
      'version' : '2.8.2',
    ],
  ]
  String groupId
  String artifactId
  String version
  File folder

  PomFile (File folder, String groupId, String artifactId, String version) {
    this.folder = folder
    this.groupId = groupId
    this.artifactId = artifactId
    this.version = version
  }

  def writePomFile (Map parentPar) {
    writePomFile(parentPar, null)
  }
  def writePomFile () {
    writePomFile(null, null)
  }
  def writePomFile (Map parentPar, modulesPar) {
    new File(this.folder,'pom.xml').withWriter('utf-8') { writer ->
        def builder = new groovy.xml.MarkupBuilder(writer)
        builder.getMkp().xmlDeclaration( 'version':'1.0', 'encoding':'UTF-8')
        builder.project ( 
              'xmlns':"http://maven.apache.org/POM/4.0.0",
              'xmlns:xsi':"http://www.w3.org/2001/XMLSchema-instance",
              'xsi:schemaLocation':"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"
            ) {
            modelVersion("4.0.0")
            if (parentPar != null) { 
              parent {
                groupId(parentPar['groupId'])
                artifactId(parentPar['artifactId'])
                version(this.version)
              }
            } else {
              version(this.version)
            }
            groupId(this.groupId)
            artifactId(this.artifactId)

            if (modulesPar != null) {
              packaging('pom')
              modules {
                modulesPar.each {
                  module(it)
                }
              }
              properties {
                'project.build.sourceEncoding'('UTF-8')
              }
              'build' {
                pluginManagement {
                  plugins {
                    pluginList.each { value ->
                      plugin {
                        groupId(value['groupId'])
                        artifactId(value['artifactId'])
                        version(value['version'])
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


def folder = new File (".", "reactor")

if ( folder.exists() ) {
  folder.deleteDir()
}
folder.mkdirs()

def levelList = []
(1..1000).each { level ->

  def levelFormat = sprintf ("%04d", level)
  def levelModuleName = 'level-' + levelFormat
  println "Level: ${levelModuleName}"

  levelFolder = new File (folder, levelModuleName);
  levelFolder.mkdirs()

  PomFile pf = new PomFile (levelFolder, "org.test.level", levelModuleName, "1.0-SNAPSHOT")

  pf.writePomFile(['groupId':'org.test.parent', 'artifactId':'reactor-parent'])
   
  levelList << levelModuleName 

}

pf = new PomFile (folder, "org.test.parent", "reactor-parent", "1.0-SNAPSHOT")

pf.writePomFile( null, levelList)

